package metrics;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * @author lzn
 * @date 2023/12/27 12:58
 * @description
 */
public class MetricsManager {
    private final Logger log = LoggerFactory.getLogger(MetricsManager.class);
    private final MeterRegistry memoryRegistry = new SimpleMeterRegistry();
    private final MeterRegistry gcRegistry = new SimpleMeterRegistry();
    //    private final Map<JvmIndicator, Double> metricMap;

    public MetricsManager() {
        JvmMemoryMetrics memoryMetrics = new JvmMemoryMetrics();
        JvmGcMetrics gcMetrics = new JvmGcMetrics();
        memoryMetrics.bindTo(memoryRegistry);
        gcMetrics.bindTo(gcRegistry);
//        metricMap = new HashMap<>(memoryRegistry.getMeters().size());
//        memoryRegistry.getMeters().forEach(meter -> {
//            metricMap.put(new JvmIndicator(meter.getId().getName(), meter.getId().getTag("area"), meter.getId().getTag("id"), meter.getId().getDescription()), meter.measure().iterator().next().getValue());
//        });

//        log.info("Metrics: {}", metricMap);
    }

//    public Map<JvmIndicator, Double> getMetricMap() {
//        return metricMap;
//    }

    public MeterRegistry getMemoryRegistry() {
        return memoryRegistry;
    }

    public MeterRegistry getGcRegistry() {
        return gcRegistry;
    }

    public double getGcMetric(JvmIndicator indicator) {
        return gcRegistry.get(indicator.getName()).tags(indicator.getTag1(), indicator.getArea(), indicator.getTag2(), indicator.getId()).gauge().value();
    }

    public double getMemoryMetric(JvmIndicator indicator) {
        return memoryRegistry.get(indicator.getName()).tags(indicator.getTag1(), indicator.getArea(), indicator.getTag2(), indicator.getId()).gauge().value();
    }

    public Collection<Meter> getMemoryMetrics(JvmIndicator indicator) {
        return memoryRegistry.get(indicator.getName()).tag(indicator.getTag1(), indicator.getArea()).meters();
    }
}
