Reactor programming
-----------------------

One of the main goals of this is to address the problem of backpressure. If we have a producer which is emitting events to a consumer faster than it can process them, then eventually the consumer will be overwhelmed with events, running out of system resources.

**Backpressure means** that our consumer should be able to tell the producer how much data to send in order to prevent this, and this is what is laid out in the specification.

Note:
1. Flux and Mono are implementations of the Reactive Streams Publisher interface.
2. The core difference between Java8 streams and Reactive is that **Reactive is a push model**, whereas the **Java 8 Streams are a pull model**. In a reactive approach, events are pushed to the subscribers as they come in.