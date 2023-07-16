package com.assignment.processor.service.helper;

import com.assignment.processor.cache.CacheManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionControlHelperTest {

    @Mock
    private CacheManager cacheManager;
    private TransactionControlHelper transactionControlHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionControlHelper = new TransactionControlHelper();
        transactionControlHelper.cacheManager = cacheManager;
    }

    @Test
    void testProcessTransactionCommit_Success() {
        CacheManager spy = Mockito.spy(cacheManager);
        assertTrue(transactionControlHelper.processTransactionCommit());
        verify(cacheManager).retrieveStagingCacheEntries();
        //  doNothing().when(CacheManager.createPrimaryCacheEntry(anyString(), anyString()));
        //  verify(CacheManager.createPrimaryCacheEntry(anyString(), anyString()));
        //  Mockito.doNothing().when(spy).createPrimaryCacheEntry(anyString(), anyString());
        //  Mockito.doNothing().when(spy).deleteCacheEntry(anyString());
        //  verify(CacheManager.deleteCacheEntry(anyString()));

        verify(cacheManager).clearStagingCache();
    }

    @Test
    void testProcessTransactionCommit_ExceptionThrown() {
        when(cacheManager.retrieveStagingCacheEntries()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> transactionControlHelper.processTransactionCommit());
        verify(cacheManager).retrieveStagingCacheEntries();
    }

    @Test
    void testProcessTransactionRollback_Success() {
        assertTrue(transactionControlHelper.processTransactionRollback());
        verify(cacheManager).clearStagingCache();
    }

    @Test
    void testProcessTransactionRollback_ExceptionThrown() {
        doThrow(new RuntimeException()).when(cacheManager).clearStagingCache();
        assertThrows(RuntimeException.class, () -> transactionControlHelper.processTransactionRollback());
        verify(cacheManager).clearStagingCache();
    }

    @Test
    void testProcessTransactionStart() {
        transactionControlHelper.processTransactionStart();
        verify(cacheManager).initializeStagingCache();
    }
}
