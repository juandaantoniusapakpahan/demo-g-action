package com.example.action.demo_g_action.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.action.demo_g_action.service.RateLimiterService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private RateLimiterService rateLimiterService;

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestHeader("X-User-Id") String userId) {
        if (!rateLimiterService.isAllowed(userId)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded");
        }
        return ResponseEntity.ok("Data untuk " + userId);
    }
}