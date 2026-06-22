package com.example.action.demo_g_action.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.action.demo_g_action.service.RateLimiterService;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateLimiterService rateLimiterService;

    @Test
    void getData_whenAllowed_shouldReturn200() throws Exception {
        when(rateLimiterService.isAllowed("user123")).thenReturn(true);

        mockMvc.perform(get("/api/data").header("X-User-Id", "user123"))
        .andExpect(status().isOk())
        .andExpect(content().string("Data untuk user123"));
    }
    
    @Test 
    void getData_whenRateLimit_shouldReturn() throws Exception {
        when(rateLimiterService.isAllowed("user123")).thenReturn(false);

        mockMvc.perform(get("/api/data")
        .header("X-User-Id", "user123"))
        .andExpect(status().isTooManyRequests())
        .andExpect(content().string("Rate limit exceeded"));
    }
}
