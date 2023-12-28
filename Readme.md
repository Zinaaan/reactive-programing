Reactor programming
-----------------------

One of the main goals of this is to address the problem of backpressure. If we have a producer which is emitting events to a consumer faster than it can process them, then eventually the consumer will be overwhelmed with events, running out of system resources.

**Backpressure means** that our consumer should be able to tell the producer how much data to send in order to prevent this, and this is what is laid out in the specification.

Note:
1. Flux and Mono are implementations of the Reactive Streams Publisher interface.
2. The core difference between Java8 streams and Reactive is that **Reactive is a push model**, whereas the **Java 8 Streams are a pull model**. In a reactive approach, events are pushed to the subscribers as they come in.

## JVM and System Metrics

Micrometer provides several binders for monitoring the JVM:

new ClassLoaderMetrics().bindTo(registry); (1)

new JvmMemoryMetrics().bindTo(registry); (2)

new JvmGcMetrics().bindTo(registry); (3)

new ProcessorMetrics().bindTo(registry); (4)

new JvmThreadMetrics().bindTo(registry); (5)

1. Gauges loaded and unloaded classes.

2. Gauges buffer and memory pool utilization.

3. Gauges max and live data size, promotion and allocation rates, and times GC pauses (or concurrent phase time in the case of CMS).

4. Gauges current CPU total and load average.

5. Gauges thread peak, number of daemon threads, and live threads.

Micrometer also provides a meter binder for ExecutorService. You can instrument your ExecutorService as follows:

new ExecutorServiceMetrics(executor, executorServiceName, tags).bindTo(registry);
Metrics created from the binder vary based on the type of ExecutorService.

For ThreadPoolExecutor, the following metrics are provided:

executor.completed (FunctionCounter): The approximate total number of tasks that have completed execution.

executor.active (Gauge): The approximate number of threads that are actively executing tasks.

executor.queued (Gauge): The approximate number of tasks that are queued for execution.

executor.pool.size (Gauge): The current number of threads in the pool.

For ForkJoinPool, the following metrics are provided:

executor.steals (FunctionCounter): Estimate of the total number of tasks stolen from one thread’s work queue by another. The reported value underestimates the actual total number of steals when the pool is not quiescent.

executor.queued (Gauge): An estimate of the total number of tasks currently held in queues by worker threads.

executor.active (Gauge): An estimate of the number of threads that are currently stealing or executing tasks.

executor.running (Gauge): An estimate of the number of worker threads that are not blocked but are waiting to join tasks or for other managed synchronization threads.