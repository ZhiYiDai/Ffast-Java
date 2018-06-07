package cn.ffast.web.controller;

import cn.ffast.core.annotations.Logined;
import cn.ffast.core.auth.OperatorBase;
import cn.ffast.core.redis.RedisCacheUtils;
import cn.ffast.core.auth.OperatorUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    RedisCacheUtils redisCacheUtils;
    @Resource
    OperatorUtils operatorUtils;

    @GetMapping("/test")
    @Logined
    public Object test() {
        return  operatorUtils.getUser(OperatorBase.class);
    }

    @GetMapping("/test2")
    public Object test2() {
        return  "test2";
    }

    @GetMapping("/test3")
    public Object test4() {
        return  1/0;
    }


//    @GetMapping("/getCache")
//    public Object getCache() {
//        List<String> result = redisCacheUtils.getCacheObject("list_cache", new ArrayList<HashMap<String, String>>(0).getClass());
//        return result;
//    }
//
//    @GetMapping("/setCache")
//    public Object setCache() {
//        List<Map> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Map map = new HashMap<String, String>();
//            map.put("id", i);
//            map.put("name", "index=" + i);
//            list.add(map);
//        }
//        return redisCacheUtils.setCacheObject("list_cache", list, RedisSerializerType.Msgpack);
//    }
//
//    @GetMapping("/testCache")
//    public Object testCache() {
//        List<Map> list = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            Map map = new HashMap<String, String>();
//            map.put("id", i);
//            map.put("name", "index=" + i);
//            map.put("sex", "man");
//            map.put("abc", "cvb");
//            list.add(map);
//        }
//        redisCacheUtils.setCacheObject("list_cache2", list, RedisSerializerType.FastJSON);
//
//
//        redisCacheUtils.setCacheObject("list_cache2", list, RedisSerializerType.FastJSON);
//        redisCacheUtils.getCacheObject("list_cache2", new ArrayList<HashMap<String, String>>(0).getClass(), RedisSerializerType.FastJSON);
//
//        redisCacheUtils.setCacheObject("list_cache3", list, RedisSerializerType.JackJson);
//        redisCacheUtils.getCacheObject("list_cache3", new ArrayList<HashMap<String, String>>(0).getClass(), RedisSerializerType.JackJson);
//
//        redisCacheUtils.setCacheObject("list_cache1", list, RedisSerializerType.Msgpack);
//        redisCacheUtils.getCacheObject("list_cache1", new ArrayList<HashMap<String, String>>(0).getClass(), RedisSerializerType.Msgpack);
//
//        return null;
//    }
}