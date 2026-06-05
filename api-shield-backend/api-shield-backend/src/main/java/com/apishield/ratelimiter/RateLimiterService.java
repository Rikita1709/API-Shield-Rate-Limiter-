package com.apishield.ratelimiter;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();
private final Map<String, Long> timestamps = new ConcurrentHashMap<>();
@Value("${rate.limit}")
private int limit;

@Value("${rate.window}")
private long timeWindow;

    public boolean isAllowed(String apiKey) {

        long currentTime = System.currentTimeMillis();

        timestamps.putIfAbsent(apiKey, currentTime);

       if (currentTime - timestamps.get(apiKey) > timeWindow){
            requestCounts.put(apiKey, 0);
            timestamps.put(apiKey, currentTime);
        }

        requestCounts.put(apiKey, requestCounts.getOrDefault(apiKey, 0) + 1);

        return requestCounts.get(apiKey) <= limit;
    }
}
