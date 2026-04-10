package com.apishield.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class ApiKeyService {

    private final Set<String> validKeys = new HashSet<>();

    public ApiKeyService() {
        validKeys.add("key123");
        validKeys.add("admin456");
    }

    public boolean isValid(String apiKey) {
        return validKeys.contains(apiKey);
    }
}