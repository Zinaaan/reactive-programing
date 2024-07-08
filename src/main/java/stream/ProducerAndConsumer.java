package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author lzn
 * @date 2024/07/07 16:46
 * @description
 */
public class ProducerAndConsumer {

    private final Consumer<List<String>> batchConsumer = this::flushToDatabase;

    private void flushToDatabase(List<String> data) {
        System.out.println("Current thread: " + Thread.currentThread().getName());
        System.out.println("Meet the capacity, flush to the database..." + data);
    }

    public final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void write(String message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public void consume() {
        try {
            List<String> buffer = new ArrayList<>();
            while (true) {
                if (queue.isEmpty()) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    continue;
                }
                int batchSize = queue.size();
                for (int i = 0; i < batchSize; i++) {
                    buffer.add(queue.take());
                }
                if (!buffer.isEmpty()) {
                    batchConsumer.accept(buffer);
                }
                buffer.clear();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer producerAndConsumer = new ProducerAndConsumer();
        Thread producer = new Thread(() -> {
            while (true) {
                producerAndConsumer.write(UUID.randomUUID().toString());
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Producer");
        Thread consumer = new Thread(producerAndConsumer::consume, "Consumer");
        producer.start();
        consumer.start();
    }
}
