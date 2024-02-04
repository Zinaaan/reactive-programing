package examples;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lzn
 * @date 2023/12/24 17:17
 * @description
 */
public class Example {

    Flux<Integer> just = Flux.just(1, 2, 3, 4);

    Mono<Integer> mono = Mono.just(1);

    Publisher<String> publisher = Mono.just("data");

    static List<Integer> elements = new ArrayList<>();

    public static void main(String[] args) {
        // The data won't start flowing until we subscribe
//        Flux.just(1, 2, 3, 4).log().subscribe(elements::add);

//        Flux.just(1, 2, 3, 4).log().subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                s.request(Long.MAX_VALUE);
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                elements.add(integer);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//        Flux.interval(Duration.ofMillis(1))
//                .log()
//                .concatMap(x -> Mono.delay(Duration.ofMillis(100)))
//                .blockLast();
        Flux.interval(Duration.ofMillis(1))
                .onBackpressureDrop()
                .concatMap(a -> Mono.delay(Duration.ofMillis(100)).thenReturn(a))
                .doOnNext(a -> System.out.println("Thread: " + Thread.currentThread().getName() + ", Element kept by consumer: " + a))
                .subscribe();

        try {
            TimeUnit.SECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
