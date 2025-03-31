package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.DepositRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {


        @Cacheable(value = "depositRequest")
        public Long cacheRequestId(DepositRequest depositRequest) {
            return depositRequest.getRequestId();

        }
}
