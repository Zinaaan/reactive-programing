package examples;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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

        Flux.just(1, 2, 3, 4).log().subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                elements.add(integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
