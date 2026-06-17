package com.apishield.ratelimiter;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apishield.service.StatisticsService;

@Service
public class RateLimiterService {

    // Stores request timestamps for each API key
    private final Map<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();

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

        // Get request history for API key
        Deque<Long> requests =
                requestLogs.computeIfAbsent(
                        apiKey,
                        k -> new LinkedList<>()
                );

        // Remove requests that are outside the sliding window
        while (!requests.isEmpty()
                && currentTime - requests.peekFirst() >= timeWindow) {
            requests.pollFirst();
        }

        boolean allowed = requests.size() < limit;

        if (allowed) {
            requests.addLast(currentTime);
            statisticsService.incrementAllowed();
        } else {
            statisticsService.incrementBlocked();
        }

        return allowed;
    }
}