package com.assignment.processor.util;

import com.assignment.processor.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionControlProcessorUtil {
    static Logger logger = LoggerFactory.getLogger(TransactionControlProcessorUtil.class);

    public static boolean processTransactionCommit() {

        logger.info ("Starting transaction commit workflow for the request");
        CacheManager.retrieveStagingCacheEntries().forEach(e -> {
            try {
                CacheManager.createPrimaryCacheEntry(e.getKey(), e.getValue());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        logger.info (" Transaction commit successful ");

        CacheManager.clearStagingCache();
        logger.info (" Staging cache cleared ");
        return true;
    }

    public static boolean processTransactionRollback() {

        logger.info (" Starting transaction rollout workflow for the request. " +
                "Clearing staging cache without committing to the primary cache ");
        try {
            CacheManager.clearStagingCache();
        } catch (Exception e) {
            throw new RuntimeException("Transaction rollback failed", e.getCause());
        }

        logger.info (" Staging cache cleared. Transaction rollback successful ");
        return true;
    }

}


