package metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

/**
 * @author lzn
 * @date 2023/12/27 13:39
 * @description
 */
public class GcMetricController {

    static Logger log = LoggerFactory.getLogger(GcMetricController.class);

    private final MetricsManager metricsManager = new MetricsManager();

    public Flux<List<Meter>> getAllGcMetricKeys() {
//        Gauge gauge = metricsManager.getMemoryRegistry().get("jvm.memory.used").tag("area", "nonheap").gauge();
//        Collection<Gauge> gauges = metricsManager.getMemoryRegistry().get("jvm.memory.used").gauges();
////        log.info("Guage: {}", gauge.value());
//        for (Gauge gauge1 : gauges) {
//            log.info("Guage: {}", gauge1.value());
//        }
        List<Meter> gcMeters = metricsManager.getGcRegistry().getMeters();

        for (Meter meter : gcMeters) {
            log.info("GC Meter: {}", meter.getId());
            log.info("GC Unit: {}", meter.getId().getBaseUnit());
            log.info("GC Value: {}", meter.measure().iterator().next().getValue());
        }
        return Flux.just(metricsManager.getGcRegistry().getMeters());
    }

    public Flux<List<Meter>> getAllMemoryMetricKeys() {
        List<Meter> memMeters = metricsManager.getMemoryRegistry().getMeters();
        for (Meter meter : memMeters) {
            log.info("Memory Meter: {}", meter.getId());
            log.info("Memory Unit: {}", meter.getId().getBaseUnit());
            log.info("Memory Value: {}", meter.measure().iterator().next().getValue());
        }
        return Flux.just(metricsManager.getMemoryRegistry().getMeters());
    }

    public Mono<Double> getGcMetric(String name, String tag1, String area, String tag2, String type) {
        return metricsManager.getGcMetric(name, tag1, area, tag2, type);
    }

    public Mono<Double> getMemoryMetric(String name, String tag1, String area, String tag2, String type) {
        return metricsManager.getMemoryMetric(name, tag1, area, tag2, type);
    }

    public Mono<Collection<Meter>> getMemoryMetrics(String name, String tag1, String area) {
        return metricsManager.getMemoryMetrics(name, tag1, area);
    }

    public static void main(String[] args) {
        GcMetricController gcMetricController = new GcMetricController();
        gcMetricController.getAllMemoryMetricKeys()
                .doOnNext(meters -> {
                    for (Meter meter : meters) {
                        log.info("Memory Meter: {}", meter.getId());
                        log.info("Memory Unit: {}", meter.getId().getBaseUnit());
                        log.info("Memory Value: {}", meter.measure().iterator().next().getValue());
                    }
                })
                .onErrorContinue((e, o) -> {
                    log.error("Error: {}, error indicator: {}", e.toString(), o);
                })
                .doOnComplete(() -> {
                    log.info("Completed");
                })
                .doFinally(o -> {
                    log.info("Finally：{}", o);
                })
                .subscribe();

        gcMetricController.getMemoryMetric("jvm.memory.max", "area", "heap", "id", "PS Old Gen")
                .doOnSuccess(value -> {
                    log.info("Metric: {}", value);
                })
                .doOnError(error -> {
                    log.info("Error on getting metric: {}", error.toString());
                }).subscribe();

        gcMetricController.getMemoryMetrics("jvm.memory.max", "area", "heap")
                .doOnSuccess(meters -> {
                    for (Meter meter : meters) {
                        log.info("id: {}, metric: {}", meter.getId().getTag("id"), meter.measure().iterator().next().getValue());
                    }
                })
                .doOnError(error -> {
                    log.info("Error on getting metrics: {}", error.toString());
                }).subscribe();
    }
}
