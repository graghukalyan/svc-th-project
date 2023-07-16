package com.assignment.processor.service.helper;

import com.assignment.processor.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DataModificationHelper {

    @Autowired
    CacheManager cacheManager;
    private final static Logger logger = LoggerFactory.getLogger(DataModificationHelper.class);
    public final static String STATUS_OK = "OK";
    public final static String STATUS_ERROR = "ERROR";


    public String processPutRequest(String key, String value) {
        try {
            logger.info (String.format("Create or Overwrite entry with key : %s value : %s",key, value));
            cacheManager.createStagingCacheEntry(key, value);
            return STATUS_OK;
        } catch (Exception e) {
            logger.error(String.format("Error encountered while creating or overwriting key : %s", key), e.getCause());
            return STATUS_ERROR;
        }
    }

    public static String processGetRequest(String key) {
        logger.info (String.format("Looking up entries for key : %s",key));
        return CacheManager.getCacheEntry(key).orElse(HttpStatus.NOT_FOUND.name());
    }

    public String processDeleteRequest(String key) {
        try {
            logger.info (String.format("Looking to delete entry with key : %s",key));
            cacheManager.markForDelete(key);
            return STATUS_OK;
        } catch (Exception e) {
            logger.error(String.format("Error encountered while deleting key : %s", key), e.getCause());
            return STATUS_ERROR;
        }
    }
}


