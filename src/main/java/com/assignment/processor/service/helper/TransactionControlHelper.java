package com.assignment.processor.service.helper;

import com.assignment.processor.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionControlHelper {

    @Autowired
    CacheManager cacheManager;
    private final static Logger logger = LoggerFactory.getLogger(TransactionControlHelper.class);

    public boolean processTransactionCommit() {

        logger.info ("Starting transaction commit workflow for the request");
        cacheManager.retrieveStagingCacheEntries().forEach(e -> {
            try {
                e.getValue().ifPresentOrElse( (value)
                        -> { CacheManager.createPrimaryCacheEntry(e.getKey(), value); },
                        () -> { CacheManager.deleteCacheEntry(e.getKey()); }
                );
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        logger.info (" Transaction commit successful ");

        cacheManager.clearStagingCache();
        logger.info (" Staging cache cleared ");
        return true;
    }

    public boolean processTransactionRollback() {

        logger.info (" Starting transaction rollout workflow for the request. " +
                "Clearing staging cache without committing to the primary cache ");
        try {
            cacheManager.clearStagingCache();
        } catch (Exception e) {
            throw new RuntimeException("Transaction rollback failed", e.getCause());
        }

        logger.info (" Staging cache cleared. Transaction rollback successful ");
        return true;
    }

    //ToDO : Make it request specific & tie it to RequestUUID or CorrelationID
    public void processTransactionStart() {

        cacheManager.initializeStagingCache();
    }
}


