package metrics;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.Collection;

/**
 * @author lzn
 * @date 2023/12/27 12:58
 * @description
 */
public class MetricsManager {
    private final MeterRegistry memoryRegistry = new SimpleMeterRegistry();
    private final MeterRegistry gcRegistry = new SimpleMeterRegistry();
    private final MeterRegistry classLoaderRegistry = new SimpleMeterRegistry();
    private final MeterRegistry processorRegistry = new SimpleMeterRegistry();
    private final MeterRegistry threadRegistry = new SimpleMeterRegistry();

    public MetricsManager() {
        new ClassLoaderMetrics().bindTo(classLoaderRegistry);

        new JvmMemoryMetrics().bindTo(memoryRegistry);

        new JvmGcMetrics().bindTo(gcRegistry);

        new ProcessorMetrics().bindTo(processorRegistry);

        new JvmThreadMetrics().bindTo(threadRegistry);
    }

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
