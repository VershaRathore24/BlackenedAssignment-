package com.example.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Long incrementViralityScore(Long postId, int points) {

        String key = "post:" + postId + ":virality_score";

        return redisTemplate.opsForValue().increment(key, points);
    }

    public Long incrementBotCount(Long postId) {

        String key = "post:" + postId + ":bot_count";

        return redisTemplate.opsForValue().increment(key);
    }

    public boolean setCooldown(Long botId, Long humanId) {

        String key = "cooldown:bot_" + botId + ":human_" + humanId;

        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "locked", 10, TimeUnit.MINUTES);

        return Boolean.TRUE.equals(success);
    }
}