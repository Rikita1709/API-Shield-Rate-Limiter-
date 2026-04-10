package com.apishield.logging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RequestLogService {

    private final List<String> logs = new ArrayList<>();

    public void log(String apiKey) {
        logs.add("API Key: " + apiKey + " at " + System.currentTimeMillis());
    }

    public List<String> getLogs() {
        return logs;
    }
}