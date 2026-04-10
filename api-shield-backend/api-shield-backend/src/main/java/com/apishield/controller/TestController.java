package com.apishield.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apishield.logging.RequestLogService;
import com.apishield.ratelimiter.RateLimiterService;
import com.apishield.security.ApiKeyService;

@RestController
@CrossOrigin(origins = "*")
public class TestController {
//Constructor Injection
    private final ApiKeyService apiKeyService;
    private final RateLimiterService rateLimiter;
    private final RequestLogService logService;
    public TestController(ApiKeyService apiKeyService,RateLimiterService rateLimiter,RequestLogService logService){
        this.apiKeyService = apiKeyService;
        this.rateLimiter = rateLimiter;
        this.logService = logService;
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

    // 👇 ADD HERE
    @GetMapping("/api/logs")
    public Object getLogs() {
        return logService.getLogs();
    }
}