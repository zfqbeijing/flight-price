package com.haina.flight.price.manager;

import com.haina.flight.price.model.FlightPrice;

import java.util.List;

public interface ICacheManager {

    /**
     * 保存数据到缓存
     * @param key
     * @param list
     */
    void saveToCache(String key, List<FlightPrice> list);

    /**
     * 从缓存中读取数据
     * @param key
     * @return
     */
    List<FlightPrice> getFromCache(String key);
}
