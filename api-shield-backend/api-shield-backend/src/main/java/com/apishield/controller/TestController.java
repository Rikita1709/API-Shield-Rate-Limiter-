package com.apishield.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apishield.logging.RequestLogService;
import com.apishield.ratelimiter.RateLimiterService;
import com.apishield.security.ApiKeyService;
import com.apishield.service.StatisticsService;

@RestController
@CrossOrigin(origins = "*")
public class TestController {

    private final ApiKeyService apiKeyService;
    private final RateLimiterService rateLimiter;
    private final RequestLogService logService;
    private final StatisticsService statisticsService;

    public TestController(
            ApiKeyService apiKeyService,
            RateLimiterService rateLimiter,
            RequestLogService logService,
            StatisticsService statisticsService) {

        this.apiKeyService = apiKeyService;
        this.rateLimiter = rateLimiter;
        this.logService = logService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/api/test")
    public String test(@RequestParam String apiKey) {

        if (!apiKeyService.isValid(apiKey)) {
            return "❌ Invalid API Key";
        }

        if (!rateLimiter.isAllowed(apiKey)) {
            return "❌ Rate limit exceeded!";
        }

        logService.log(apiKey);

        return "✅ Request successful!";
    }

    @GetMapping("/api/logs")
    public Object getLogs() {
        return logService.getLogs();
    }

    @GetMapping("/api/stats")
    public Map<String, Integer> getStats() {

        Map<String, Integer> stats = new HashMap<>();

        stats.put("totalRequests",
                statisticsService.getTotalRequests());

        stats.put("allowedRequests",
                statisticsService.getAllowedRequests());

        stats.put("blockedRequests",
                statisticsService.getBlockedRequests());

        return stats;
    }
}
