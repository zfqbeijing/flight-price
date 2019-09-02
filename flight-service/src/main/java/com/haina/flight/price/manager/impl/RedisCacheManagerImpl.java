package com.haina.flight.price.manager.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.haina.flight.price.manager.ICacheManager;
import com.haina.flight.price.model.FlightPrice;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheManagerImpl implements ICacheManager {

    @Resource
    private RedisTemplate<String, List<FlightPrice>> redisTemplate;

    //    private Map<String,List<FlightPrice>> cacheMap = new HashMap<>();
//    存入本地缓存 new CacheLoader<String, List<FlightPrice>>()匿名内部类
    private LoadingCache<String, List<FlightPrice>> cache = CacheBuilder.newBuilder()
            .maximumSize(100).expireAfterWrite(80, TimeUnit.SECONDS)
            .concurrencyLevel(10).build(new CacheLoader<String, List<FlightPrice>>() {
        @Override
        public List<FlightPrice> load(String s) throws Exception {
            return Lists.newArrayList();
        }
    });

    @Override
    public void saveToCache(String key, List<FlightPrice> list) {
//        cacheMap.put(key,list);
        redisTemplate.opsForValue().set(key, list, 60, TimeUnit.SECONDS);
    }

    @Override
    public List<FlightPrice> getFromCache(String key) {
//        存入到本地缓存
//        List<FlightPrice> mapData = cacheMap.get(key);
//        if (CollectionUtils.isNotEmpty(mapData)){
//            System.out.println("read from local");
//            return mapData;
//        }


        List<FlightPrice> locaDaata = null;
        try {
            locaDaata = cache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(CollectionUtils.isNotEmpty(locaDaata)){
            System.out.println("read from local");
            return locaDaata;
        }
        List<FlightPrice> cacheData = redisTemplate.opsForValue().get(key);
        if(CollectionUtils.isNotEmpty(cacheData)){
            cache.put(key,cacheData);
            System.out.println("read from cache");
        }
        return redisTemplate.opsForValue().get(key);
    }
}
