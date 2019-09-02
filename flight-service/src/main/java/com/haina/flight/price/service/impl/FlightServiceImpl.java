package com.haina.flight.price.service.impl;

import com.google.common.base.Joiner;
import com.haina.flight.price.dao.FlightPriceMapper;
import com.haina.flight.price.manager.ICacheManager;
import com.haina.flight.price.model.FlightPrice;
import com.haina.flight.price.service.FlightService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FlightServiceImpl implements FlightService {

    @Resource
    private FlightPriceMapper flightPriceMapper;

    @Resource
    private ICacheManager iCacheManager;


    @Override
    public List<FlightPrice> getFlightByODAndDate(String origin,
               String dest, String departDate) {

//        从缓存中取出
        String key = generateKey(origin,dest,departDate);
        List<FlightPrice> cherData = iCacheManager.getFromCache(key);
        if (CollectionUtils.isNotEmpty(cherData)){
            return cherData;
        }

        List<FlightPrice> dbData = flightPriceMapper.selectByODAndDepartDate(origin,
                dest, departDate);

//        存入缓存
        if (CollectionUtils.isNotEmpty(dbData)){
            System.out.println("read from db,save to cache");
            iCacheManager.saveToCache(key,dbData);
        }

        return dbData;
    }

    /**
     * 根据出发-到达-出发时间，生成缓存key
     * BJS-BKK-2019-05-20
     * @param origin
     * @param dest
     * @param departDate
     * @return
     */
    private String generateKey(String origin,String dest, String departDate){
        return Joiner.on("-").join(origin,dest,departDate);
    }
}
