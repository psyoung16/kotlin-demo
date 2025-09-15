package org.psy.demo.common

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit


@Component
class RedisUtil(private val redisTemplate: RedisTemplate<String, Any>) {

    //any 대신 다른 방법이 있을까..?
    fun setValue(key: String, value: String) {
//        redisTemplate.opsForValue().set(key, Json.encodeToString(value))
        redisTemplate.opsForValue().set(key, value)
    }
    fun setValue(key: String, value: String, time: Long) {
        //여기서 time은 초단위
//        redisTemplate.opsForValue().set(key, Json.encodeToString(value), time, TimeUnit.SECONDS)
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS)
    }

    fun getValue(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }

    fun deleteKey(key: String) {
        redisTemplate.delete(key)
    }

    fun hasKey(key: String): Boolean {
        return redisTemplate.hasKey(key) == true
    }
}

