package com.concurrentbank;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ComplexTask extends Thread {
    private final CyclicBarrier cyclicBarrier;
    private final Integer taskId;

    public ComplexTask(CyclicBarrier cyclicBarrier, Integer taskId) {
        this.cyclicBarrier = cyclicBarrier;
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task " + taskId + " started.");
        try {
            execute();
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        System.out.println("Task " + taskId + " ended.");
    }

    public void execute() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 3000));
    }
}
