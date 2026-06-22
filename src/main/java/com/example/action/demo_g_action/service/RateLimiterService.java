package com.example.action.demo_g_action.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {
    private final ConcurrentHashMap<String, AtomicInteger> requestCount = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> windowStart = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_MS = 60_000;


    public boolean isAllowed(String userId) {
        long now = System.currentTimeMillis();

        windowStart.computeIfAbsent(userId, k -> now);
        requestCount.computeIfAbsent(userId, k -> new AtomicInteger(0));

        long startTime = windowStart.get(userId);

        // Reset window jika sudah lewat 1 menit
        if (now - startTime > WINDOW_MS) {
            windowStart.put(userId, now);
            requestCount.get(userId).set(0);
        }

        int count = requestCount.get(userId).incrementAndGet();
        return count <= MAX_REQUESTS;
    }
}
