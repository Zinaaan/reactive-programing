package metrics;

import io.micrometer.core.instrument.Meter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Mono<List<Meter>> getAllGcMetricKeys() {
        List<Meter> gcMeters = metricsManager.getGcRegistry().getMeters();

        for (Meter meter : gcMeters) {
            log.info("GC Meter: {}", meter.getId());
            log.info("GC Unit: {}", meter.getId().getBaseUnit());
            log.info("GC Value: {}", meter.measure().iterator().next().getValue());
        }
        return Mono.just(metricsManager.getGcRegistry().getMeters());
    }

    public Mono<List<Meter>> getAllMemoryMetricKeys() {
        List<Meter> memMeters = metricsManager.getMemoryRegistry().getMeters();
        for (Meter meter : memMeters) {
            log.info("Memory Meter: {}", meter.getId());
            log.info("Memory Unit: {}", meter.getId().getBaseUnit());
            log.info("Memory Value: {}", meter.measure().iterator().next().getValue());
        }
        return Mono.just(metricsManager.getMemoryRegistry().getMeters());
    }

    public Mono<Double> getGcMetric(JvmIndicator indicator) {
        return Mono.just(metricsManager.getGcMetric(indicator));
    }

    public Mono<Double> getMemoryMetric(JvmIndicator indicator) {
        return Mono.just(metricsManager.getMemoryMetric(indicator));
    }

    public Mono<Collection<Meter>> getMemoryMetrics(JvmIndicator indicator) {
        return Mono.just(metricsManager.getMemoryMetrics(indicator));
    }

    public static void main(String[] args) {
        GcMetricController gcMetricController = new GcMetricController();
        gcMetricController.getAllMemoryMetricKeys()
                .doOnSuccess(meters -> {
                    for (Meter meter : meters) {
                        log.info("Memory Meter: {}", meter.getId());
                        log.info("Memory Unit: {}", meter.getId().getBaseUnit());
                        log.info("Memory Value: {}", meter.measure().iterator().next().getValue());
                    }
                })
                .onErrorContinue((e, o) -> {
                    log.error("Error: {}, error indicator: {}", e.toString(), o);
                })
                .subscribe();
        JvmIndicator indicator = JvmIndicator.with("jvm.memory.max", "area", "heap", "id", "PS Old Gen");
        gcMetricController.getMemoryMetric(indicator)
                .doOnSuccess(value -> {
                    log.info("Metric: {}", value);
                })
                .doOnError(error -> {
                    log.info("Error on getting metric: {}", error.toString());
                }).subscribe();

        indicator.clear();
        indicator.setName("jvm.memory.max");
        indicator.setTag1("area");
        indicator.setArea("heap");
        gcMetricController.getMemoryMetrics(indicator)
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
