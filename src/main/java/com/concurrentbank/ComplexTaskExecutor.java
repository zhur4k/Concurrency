package com.concurrentbank;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ComplexTaskExecutor {
    private final Integer taskCount;

    public ComplexTaskExecutor(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public void executeTasks() {
        ExecutorService service = Executors.newFixedThreadPool(taskCount);
        CyclicBarrier barrier = new CyclicBarrier(taskCount);

        for (int j = 0; j < taskCount; j++) {
            service.submit(new ComplexTask(barrier, j));
        }
        service.shutdown();
        try {
            if (!service.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("Executor did not terminate in the expected time.");
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            service.shutdownNow();
        }
    }
}
