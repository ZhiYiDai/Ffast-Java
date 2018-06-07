package cn.ffast.web.config;

import cn.ffast.core.redis.GenericMsgpackRedisSerializer;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.time.Duration;


@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.serializerType}")
    private int serializerType;

    @Value("${spring.redis.expirationSecond}")
    private int expirationSecondTime;



    static {

    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        RedisSerializer redisSerializer = null;
        switch (serializerType) {
            case 2:
                //使用Msgpack序列化
                ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());
                Jackson2JsonRedisSerializer Jackson2Serializer = new Jackson2JsonRedisSerializer(Object.class);
                Jackson2Serializer.setObjectMapper(mapper);
                redisSerializer = Jackson2Serializer;
                break;
            case 1:
                //使用FastJSON序列化
                redisSerializer = new FastJsonRedisSerializer(Object.class);
                break;
            default:
                //使用Jackson序列化
                redisSerializer = new Jackson2JsonRedisSerializer(Object.class);
                break;
        }
        template.setValueSerializer(redisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }


    /**
     * 设置 redis 数据默认过期时间
     * 设置@cacheable 序列化方式
     *
     * @return
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {

        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getRedisSerializer()));
        if (expirationSecondTime > 0) {
            configuration = configuration.entryTtl(Duration.ofSeconds(expirationSecondTime));
        }
        return configuration;
    }

    private RedisSerializer getRedisSerializer(){
        RedisSerializer redisSerializer = null;
        switch (serializerType) {
            case 2:
                //使用Msgpack序列化
                redisSerializer = new GenericMsgpackRedisSerializer();
                break;
            case 1:
                //使用FastJSON序列化
                redisSerializer = new GenericFastJsonRedisSerializer();
                break;
            default:
                //使用Jackson序列化
                redisSerializer = new GenericJackson2JsonRedisSerializer();
                break;
        }
        return redisSerializer;
    }

}