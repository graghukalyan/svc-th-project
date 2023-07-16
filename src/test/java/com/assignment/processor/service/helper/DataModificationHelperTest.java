package com.assignment.processor.service.helper;

import com.assignment.processor.cache.CacheManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataModificationHelperTest {

    @Mock
    private CacheManager cacheManager;

    private DataModificationHelper dataModificationHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataModificationHelper = new DataModificationHelper();
        dataModificationHelper.cacheManager = cacheManager;

    }

    @Test
    void testProcessPutRequest_Success() {
        String key = "key";
        String value = "value";
        assertEquals(DataModificationHelper.STATUS_OK, dataModificationHelper.processPutRequest(key, value));
        verify(cacheManager).createStagingCacheEntry(key, value);
    }

    @Test
    void testProcessPutRequest_ExceptionThrown() {
        String key = "key";
        String value = "value";
        doThrow(new RuntimeException()).when(cacheManager).createStagingCacheEntry(key, value);
        assertEquals(DataModificationHelper.STATUS_ERROR, dataModificationHelper.processPutRequest(key, value));
        verify(cacheManager).createStagingCacheEntry(key, value);
    }

    @Test
    void testProcessDeleteRequest_Success() {
        String key = "key";
        assertEquals(DataModificationHelper.STATUS_OK, dataModificationHelper.processDeleteRequest(key));
        verify(cacheManager).markForDelete(key);
    }

    @Test
    void testProcessDeleteRequest_ExceptionThrown() {
        String key = "key";
        doThrow(new RuntimeException()).when(cacheManager).markForDelete(key);
        assertEquals(DataModificationHelper.STATUS_ERROR, dataModificationHelper.processDeleteRequest(key));
        verify(cacheManager).markForDelete(key);
    }
}
