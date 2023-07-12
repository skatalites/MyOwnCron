package edu.dev.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class Main {

    public static void main(String[] args) {

        ScheduledExecutorService service = null;

        try {

            service = Executors.newSingleThreadScheduledExecutor();

            Runnable print = (() ->
            {
                var startTime = printStartTime();
                log.info("Starting using a Runnable...");
                printEndTime(startTime);
            });

            Callable<Integer> counter = (() -> {

                log.info("Starting using a Callable...");
                var startTime = printStartTime();

                int total = 1;
                for (int i = 0; i <= 100; i++) {
                    log.info("Number: {}, and total is: {}", i, total);
                    total += i;
                }
                printEndTime(startTime);
                return total;

            });

            service.schedule(print, 1, TimeUnit.SECONDS);
            service.schedule(counter, 3, TimeUnit.SECONDS);

        } catch (Exception exception) {
            log.error("Exception: {}", exception.getMessage());
        } finally {
            Objects.requireNonNull(service).shutdown();
        }
    }

    private static long printStartTime() {
        var start = System.currentTimeMillis();
        log.info("Current Thread: {}", Thread.currentThread().getName());
        return start;
    }

    private static void printEndTime(long startTime) {

        var endTime = System.currentTimeMillis();
        log.info("Current Thread: {}, final time: {}", Thread.currentThread().getName(), endTime - startTime);
    }

}