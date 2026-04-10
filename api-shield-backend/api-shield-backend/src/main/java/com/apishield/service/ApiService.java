package com.apishield.service;

import org.springframework.stereotype.Service;

@Service
public class ApiService {

    public String getTestMessage() {
        return "API Shield Backend Running 🚀";
    }

    public String getHelloMessage(String name) {
        return "Hello " + name + " 👋";
    }
}