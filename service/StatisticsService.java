package com.apishield.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final AtomicInteger totalRequests = new AtomicInteger(0);
    private final AtomicInteger allowedRequests = new AtomicInteger(0);
    private final AtomicInteger blockedRequests = new AtomicInteger(0);

    public void incrementTotal() {
        totalRequests.incrementAndGet();
    }

    public void incrementAllowed() {
        allowedRequests.incrementAndGet();
    }

    public void incrementBlocked() {
        blockedRequests.incrementAndGet();
    }

    public int getTotalRequests() {
        return totalRequests.get();
    }

    public int getAllowedRequests() {
        return allowedRequests.get();
    }

    public int getBlockedRequests() {
        return blockedRequests.get();
    }
}
