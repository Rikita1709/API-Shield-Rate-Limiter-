package com.apishield.ratelimiter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();
private final Map<String, Long> timestamps = new ConcurrentHashMap<>();

    private static final int LIMIT = 5;
    private static final long TIME_WINDOW = 60000; // 1 minute

    public boolean isAllowed(String apiKey) {

        long currentTime = System.currentTimeMillis();

        timestamps.putIfAbsent(apiKey, currentTime);

        if (currentTime - timestamps.get(apiKey) > TIME_WINDOW) {
            requestCounts.put(apiKey, 0);
            timestamps.put(apiKey, currentTime);
        }

        requestCounts.put(apiKey, requestCounts.getOrDefault(apiKey, 0) + 1);

        return requestCounts.get(apiKey) <= LIMIT;
    }
}
