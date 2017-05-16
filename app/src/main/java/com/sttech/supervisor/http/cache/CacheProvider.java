package com.sttech.supervisor.http.cache;


import com.sttech.supervisor.dto.MobileResponse;
import com.sttech.supervisor.dto.ProjectPageDto;
import com.sttech.supervisor.entity.Daily;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;


public interface CacheProvider {

//    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
//    Observable<ApiResponse<TestBean>> getDatas(Observable<ApiResponse<TestBean>> oRepos, EvictProvider evictDynamicKey);


//    @LifeCache(duration = 365, timeUnit = TimeUnit.DAYS)
//    Observable<Daily> getDaily(Observable<Daily> oRepos, EvictProvider evictDynamicKey);
//
//
//    @LifeCache(duration = 365, timeUnit = TimeUnit.DAYS)
//    Observable<Daily> getDaily2(Observable<Daily> oRepos, EvictDynamicKey evictDynamicKey, DynamicKey dynamicKey);


    @LifeCache(duration = 30, timeUnit = TimeUnit.DAYS)
    Observable<MobileResponse<ProjectPageDto>> getProjectDetail(Observable<MobileResponse<ProjectPageDto>> observable, EvictDynamicKey evictDynamicKey, DynamicKey dynamicKey);
}