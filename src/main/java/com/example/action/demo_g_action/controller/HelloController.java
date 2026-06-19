package com.example.action.demo_g_action.controller;

import java.util.Map;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of(
            "message", "Hello from Github Actions!",
            "status", "OK"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @PostMapping("/hello")
    public ResponseEntity<Map<String, String>> addHello(
            @RequestBody Map<String, String> rqBody
    ) {
        return ResponseEntity.ok(rqBody);
    }

    @GetMapping("/hello/one/{id}")
    public ResponseEntity<Map<String, String>> getHelloOne(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "id", String.valueOf(id)
        ));
    }

    @GetMapping("/hello/query")
    public ResponseEntity<Map<String, String>> getHelloQuery(
            @RequestParam String name,
            @RequestParam String email
    ) {
        return ResponseEntity.ok(
                Map.of(
                        "name",name,
                        "email",email
                )
        );
    }
}
