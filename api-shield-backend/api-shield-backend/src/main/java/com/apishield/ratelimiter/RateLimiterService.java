package com.apishield.ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apishield.service.StatisticsService;

@Service
public class RateLimiterService {

    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> timestamps = new ConcurrentHashMap<>();

    @Value("${rate.limit}")
    private int limit;

    @Value("${rate.window}")
    private long timeWindow;

    @Autowired
    private StatisticsService statisticsService;

    public boolean isAllowed(String apiKey) {

        statisticsService.incrementTotal();
        statisticsService.incrementApiKeyRequests(apiKey);

        long currentTime = System.currentTimeMillis();

        // Initialize for first-time API key
        timestamps.putIfAbsent(apiKey, currentTime);
        requestCounts.putIfAbsent(apiKey, 0);

        // Reset window if expired
        if (currentTime - timestamps.get(apiKey) > timeWindow) {
            requestCounts.put(apiKey, 0);
            timestamps.put(apiKey, currentTime);
        }

        // Count current request
        requestCounts.put(apiKey, requestCounts.get(apiKey) + 1);

        boolean allowed = requestCounts.get(apiKey) <= limit;

        if (allowed) {
            statisticsService.incrementAllowed();
        } else {
            statisticsService.incrementBlocked();
        }

        return allowed;
    }
}