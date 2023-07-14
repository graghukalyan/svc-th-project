package com.assignment.processor.service.helper;

import com.assignment.processor.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class DataModificationHelper {
    private final static Logger logger = LoggerFactory.getLogger(DataModificationHelper.class);

    public static void processPutRequest(String key, String value) {

        logger.info (String.format("Create or Overwrite entry with key : %s value : %s",key, value));
        CacheManager.createStagingCacheEntry(key, value);
    }

    public static String processGetRequest(String key) {
        logger.info (String.format("Looking up entries for key : %s",key));
        return CacheManager.getCacheEntry(key).orElse(HttpStatus.NOT_FOUND.name());
    }

    public static void processDeleteRequest(String key) {
        try {
            logger.info (String.format("Looking to delete entry with key : %s",key));
            CacheManager.markForDelete(key);
        } catch (Exception e) {
            logger.error(String.format("Error encountered while deleting key : %s", key), e.getCause());
        }
    }

}


