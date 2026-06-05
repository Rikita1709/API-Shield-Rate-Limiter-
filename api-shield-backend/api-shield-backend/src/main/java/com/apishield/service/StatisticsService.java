package com.apishield.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private int totalRequests = 0;
    private int allowedRequests = 0;
    private int blockedRequests = 0;

    private final Map<String, Integer> apiKeyRequests = new ConcurrentHashMap<>();

    public void incrementTotal() {
        totalRequests++;
    }

    public void incrementAllowed() {
        allowedRequests++;
    }

    public void incrementBlocked() {
        blockedRequests++;
    }

    public void incrementApiKeyRequests(String apiKey) {
        apiKeyRequests.put(
                apiKey,
                apiKeyRequests.getOrDefault(apiKey, 0) + 1
        );
    }

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new ConcurrentHashMap<>();

        stats.put("totalRequests", totalRequests);
        stats.put("allowedRequests", allowedRequests);
        stats.put("blockedRequests", blockedRequests);

        return stats;
    }

    public Map<String, Integer> getApiKeyRequests() {
        return apiKeyRequests;
    }
}