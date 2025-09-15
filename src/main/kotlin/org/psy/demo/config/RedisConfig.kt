package org.psy.demo.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
class RedisConfig(private val objectMapper: ObjectMapper) {

    // IP 주소와 포트 설정
    @Value("\${spring.data.redis.host}")
    private lateinit var  redisHost : String // 원하는 IP 주소로 변경
    @Value("\${spring.data.redis.port}")
    private  var  redisPort = 6379


    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisHost, redisPort)
    }


    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        val cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(5)) //일단 5분, 추후 관리자에 의해 변경되는것과 그렇지 않은 거 구분해서 개별 정책 적용 필요
            .disableCachingNullValues()
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    GenericJackson2JsonRedisSerializer()
                )
            )

        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(cacheConfiguration)
            .build()
    }

    /*@Bean
    fun cacheManager(): RedisCacheManager {
        val defaultCacheConfig: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofHours(1))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer<Any>(
                    GenericJackson2JsonRedisSerializer()
                )
            )

        val shortLivedCacheConfig: RedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer<Any>(
                    GenericJackson2JsonRedisSerializer()
                )
            )

        return RedisCacheManager.builder(redisConnectionFactory())
            .cacheDefaults(defaultCacheConfig)
            .withCacheConfiguration("shortLivedCache", shortLivedCacheConfig)
            .build()
    }*/


    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()


        // Use the custom ObjectMapper
        //isLike 가 like로 저장이 됨
        val serializer = GenericJackson2JsonRedisSerializer(objectMapper)

        // "\xac\xed\x00" 같은 불필요한 해시값을 보지 않기 위해 serializer 설정
        template.connectionFactory = redisConnectionFactory()
        template.valueSerializer = serializer
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()
        return template
    }
    /*@Bean
    fun redisTemplate(): RedisTemplate<*, *> {
        return RedisTemplate<Any, Any>().apply {
            this.connectionFactory = redisConnectionFactory()


            this.keySerializer = StringRedisSerializer()
            this.hashKeySerializer = StringRedisSerializer()
            this.valueSerializer = StringRedisSerializer()
        }
    }
*/

}
