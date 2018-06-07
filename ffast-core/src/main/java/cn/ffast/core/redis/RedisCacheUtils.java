package cn.ffast.core.redis;


import java.util.Set;
import java.util.concurrent.TimeUnit;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


/**
 * @description: redis缓存工具类
 * @copyright:
 * @createTime: 2017/11/13 15:23
 * @author：dzy
 * @version：1.0
 */
@Component
public class RedisCacheUtils {
    private static Logger logger = LoggerFactory.getLogger(RedisCacheUtils.class);
    private final static Boolean REDIS_ENABLE = true;

    @Value("${spring.redis.serializerType}")
    private int serializerType;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    RedisConnectionFactory redisConnectionFactory;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisCacheUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 缓存任意对象
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return缓存的对象   
     */
    public boolean setCacheObject(String key, Object value) {
        return setCacheObject(key, value, null, 0);
    }

    /**
     * 缓存任意对象
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param seconds 秒
     * @return
     */
    public boolean setCacheObject(String key, Object value, long seconds) {
        return setCacheObject(key, value, null, seconds);
    }

    /**
     * 缓存任意对象
     *
     * @param key
     * @param value
     * @param redisSerializerType 序列号类型
     * @param seconds 秒
     * @return
     */
    public boolean setCacheObject(String key, Object value, RedisSerializerType redisSerializerType, long seconds) {
        if (!REDIS_ENABLE) {
            return false;
        }
        logger.debug("存入缓存 key:" + key);
        RedisTemplate template = null;
        if (redisSerializerType == null) {
            template = redisTemplate;
        } else {
            template = getRedisSerializer(value.getClass(), redisSerializerType.getType());
        }
        try {
            ValueOperations<String, Object> operation = template.opsForValue();
            if (seconds > 0) {
                operation.set(key, value, seconds, TimeUnit.SECONDS);
            } else {
                operation.set(key, value);
            }
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    /**
     * 根据pattern匹配清除缓存
     * @param pattern
     */
    public void clear(String pattern) {
        if (!REDIS_ENABLE) {
            return;
        }
        logger.debug("清除缓存 pattern:" + pattern);
        try {
            ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
            RedisOperations<String, Object> redisOperations = valueOper.getOperations();
            redisOperations.keys(pattern);
            Set<String> keys = redisOperations.keys(pattern);
            for (String key : keys) {
                redisOperations.delete(key);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return;
        }
    }

    /**
     * 根据key清除缓存
     *
     * @param key
     */
    public void delete(String key) {
        if (!REDIS_ENABLE) {
            return;
        }
        logger.debug("删除缓存 key:" + key);
        try {
            ValueOperations<String, Object> valueOper = redisTemplate.opsForValue();
            RedisOperations<String, Object> redisOperations = valueOper.getOperations();
            redisOperations.delete(key);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return;
        }
    }


    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     *   
     */
    public Object getCacheObject(String key) {
        if (!REDIS_ENABLE) {
            return null;
        }
        logger.debug("获取缓存 key:" + key);
        try {
            ValueOperations<String, Object> operation = redisTemplate.opsForValue();
            return operation.get(key);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     *   
     */
    public <T> T getCacheObject(String key, Class<T> clazz) {
        return getCacheObject(key, clazz, null);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     *   
     */
    public <T> T getCacheObject(String key, Class<T> clazz, RedisSerializerType redisSerializerType) {
        if (!REDIS_ENABLE) {
            return null;
        }
        logger.debug("获取缓存 key:" + key);
        int serializerTypeId = serializerType;
        if (redisSerializerType != null) {
            serializerTypeId = redisSerializerType.getType();
        }
        RedisTemplate template = getRedisSerializer(clazz, serializerTypeId);

        try {
            ValueOperations<String, T> operation = template.opsForValue();
            Object object = operation.get(key);
            T result = null;
            if (object != null) {
                result = (T) object;
            }
            return result;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }



    private <T> RedisTemplate getRedisSerializer(Class<T> clazz, int serializerTypeId) {
        RedisSerializer redisSerializer = null;
        switch (serializerTypeId) {
            case 2:
                //使用Msgpack序列化
                Jackson2JsonRedisSerializer Jackson2Serializer = new Jackson2JsonRedisSerializer(clazz);
                final ObjectMapper msgpackMapper = new ObjectMapper(new MessagePackFactory());
                Jackson2Serializer.setObjectMapper(msgpackMapper);
                redisSerializer = Jackson2Serializer;
                break;
            case 1:
                //使用FastJSON序列化
                redisSerializer = new FastJsonRedisSerializer(clazz);
                break;
            default:
                //使用Jackson序列化
                redisSerializer = new Jackson2JsonRedisSerializer(clazz);
                break;
        }
        RedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        template.setValueSerializer(redisSerializer);
        return template;
    }
}