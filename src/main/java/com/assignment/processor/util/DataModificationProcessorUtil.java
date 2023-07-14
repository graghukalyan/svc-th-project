package com.assignment.processor.util;

import com.assignment.processor.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataModificationProcessorUtil {
    static Logger logger = LoggerFactory.getLogger(DataModificationProcessorUtil.class);

    public static void processPutRequest(String key, String value) {

        logger.info (String.format("Create or Overwrite entry with key : %s value : %s",key, value));
        CacheManager.createStagingCacheEntry(key, value);
    }

    public static String processGetRequest(String key) {
        logger.info (String.format("Looking up entries for key : %s",key));
        return CacheManager.getCacheEntry(key);
    }

    public static void processDeleteRequest(String key) {
        try {
            logger.info (String.format("Looking to delete entry with key : %s",key));
            CacheManager.deleteCacheEntry(key);
        } catch (Exception e) {
            logger.error(String.format("Error encountered while deleting key : %s", key), e.getCause());
        }
    }

}


