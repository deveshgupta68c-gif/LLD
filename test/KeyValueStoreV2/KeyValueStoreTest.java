package KeyValueStoreV2;

import org.junit.jupiter.api.*;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for KeyValueStore
 * Tests basic operations, TTL functionality, concurrency, and edge cases
 */
class KeyValueStoreTest {

    private KeyValueStore<String, String> store;
    private static final int DEFAULT_PARTITIONS = 4;
    private static final long DEFAULT_CLEANUP_INTERVAL = 1000L; // 1 second

    @BeforeEach
    void setUp() {
        store = new KeyValueStore<>(DEFAULT_CLEANUP_INTERVAL, DEFAULT_PARTITIONS);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (store != null) {
            store.close();
        }
    }

    // ==================== Basic Operations Tests ====================

    @Test
    @DisplayName("Test basic put and get operation")
    void testBasicPutAndGet() {
        store.put("key1", "value1", 5000L);
        String result = store.get("key1");
        assertEquals("value1", result, "Should retrieve the correct value");
    }

    @Test
    @DisplayName("Test put with multiple keys")
    void testMultiplePutOperations() {
        store.put("key1", "value1", 5000L);
        store.put("key2", "value2", 5000L);
        store.put("key3", "value3", 5000L);

        assertEquals("value1", store.get("key1"));
        assertEquals("value2", store.get("key2"));
        assertEquals("value3", store.get("key3"));
    }

    @Test
    @DisplayName("Test overwriting existing key")
    void testOverwriteKey() {
        store.put("key1", "value1", 5000L);
        store.put("key1", "value2", 5000L);
        
        String result = store.get("key1");
        assertEquals("value2", result, "Should return the updated value");
    }

    @Test
    @DisplayName("Test with different data types - Integer keys and values")
    void testIntegerKeyValue() {
        KeyValueStore<Integer, Integer> intStore = new KeyValueStore<>(DEFAULT_CLEANUP_INTERVAL, DEFAULT_PARTITIONS);
        try {
            intStore.put(1, 100, 5000L);
            intStore.put(2, 200, 5000L);
            
            assertEquals(100, intStore.get(1));
            assertEquals(200, intStore.get(2));
        } finally {
            try {
                intStore.close();
            } catch (Exception e) {
                fail("Failed to close store");
            }
        }
    }

    @Test
    @DisplayName("Test with complex objects as values")
    void testComplexObjectValues() {
        KeyValueStore<String, List<String>> listStore = new KeyValueStore<>(DEFAULT_CLEANUP_INTERVAL, DEFAULT_PARTITIONS);
        try {
            List<String> list1 = new ArrayList<>();
            list1.add("item1");
            list1.add("item2");
            
            listStore.put("list1", list1, 5000L);
            List<String> result = listStore.get("list1");
            
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("item1", result.get(0));
        } finally {
            try {
                listStore.close();
            } catch (Exception e) {
                fail("Failed to close store");
            }
        }
    }

    // ==================== TTL (Time To Live) Tests ====================

    @Test
    @DisplayName("Test TTL expiration - value should expire after TTL")
    void testTTLExpiration() throws InterruptedException {
        store.put("expiring-key", "expiring-value", 1000L); // 1 second TTL
        
        // Value should exist immediately
        assertEquals("expiring-value", store.get("expiring-key"));
        
        // Wait for expiration
        Thread.sleep(1100L);
        
        // Value should be null after expiration
        assertNull(store.get("expiring-key"), "Value should be null after TTL expiration");
    }

    @Test
    @DisplayName("Test TTL - value accessible before expiration")
    void testTTLBeforeExpiration() throws InterruptedException {
        store.put("key1", "value1", 2000L); // 2 seconds TTL
        
        Thread.sleep(500L); // Wait 500ms
        assertEquals("value1", store.get("key1"), "Value should still be accessible");
        
        Thread.sleep(500L); // Wait another 500ms (total 1s)
        assertEquals("value1", store.get("key1"), "Value should still be accessible");
    }

    @Test
    @DisplayName("Test TTL with very short expiration")
    void testVeryShortTTL() throws InterruptedException {
        store.put("short-lived", "value", 100L); // 100ms TTL
        
        Thread.sleep(150L);
        assertNull(store.get("short-lived"), "Value should expire quickly");
    }

    @Test
    @DisplayName("Test TTL with very long expiration")
    void testLongTTL() {
        store.put("long-lived", "value", 3600000L); // 1 hour TTL
        
        assertEquals("value", store.get("long-lived"), "Value should be accessible with long TTL");
    }

    @Test
    @DisplayName("Test updating TTL by re-putting the same key")
    void testUpdateTTL() throws InterruptedException {
        store.put("key1", "value1", 1000L); // 1 second TTL
        
        Thread.sleep(800L); // Wait 800ms
        
        // Re-put with new TTL
        store.put("key1", "value1", 2000L); // 2 seconds TTL
        
        Thread.sleep(500L); // Wait another 500ms (total 1.3s from first put)
        
        // Should still be accessible because we reset the TTL
        assertEquals("value1", store.get("key1"), "Value should be accessible after TTL reset");
    }

    // ==================== Cleanup Thread Tests ====================

    @Test
    @DisplayName("Test automatic cleanup of expired entries")
    void testAutomaticCleanup() throws InterruptedException {
        KeyValueStore<String, String> quickCleanupStore = new KeyValueStore<>(500L, DEFAULT_PARTITIONS);
        try {
            quickCleanupStore.put("key1", "value1", 300L); // 300ms TTL
            quickCleanupStore.put("key2", "value2", 300L);
            quickCleanupStore.put("key3", "value3", 5000L); // Long TTL
            
            // Wait for expiration and cleanup
            Thread.sleep(1000L);
            
            // Expired keys should return null
            assertNull(quickCleanupStore.get("key1"));
            assertNull(quickCleanupStore.get("key2"));
            
            // Non-expired key should still exist
            assertEquals("value3", quickCleanupStore.get("key3"));
        } finally {
            quickCleanupStore.close();
        }
    }

    @Test
    @DisplayName("Test cleanup thread stops after close")
    void testCleanupThreadStopsAfterClose() throws Exception {
        KeyValueStore<String, String> testStore = new KeyValueStore<>(100L, DEFAULT_PARTITIONS);
        testStore.put("key1", "value1", 5000L);
        
        testStore.close();
        
        // After close, the store should stop running
        // This is a basic test to ensure close() doesn't throw exceptions
        assertTrue(true, "Store closed successfully");
    }

    // ==================== Concurrency Tests ====================

    @Test
    @DisplayName("Test concurrent writes to different keys")
    void testConcurrentWrites() throws InterruptedException {
        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    store.put("key" + index, "value" + index, 5000L);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Verify all writes succeeded
        for (int i = 0; i < numThreads; i++) {
            assertEquals("value" + i, store.get("key" + i));
        }
    }

    @Test
    @DisplayName("Test concurrent reads and writes")
    void testConcurrentReadsAndWrites() throws InterruptedException {
        store.put("shared-key", "initial-value", 10000L);
        
        int numThreads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        List<String> readValues = new CopyOnWriteArrayList<>();
        
        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    if (index % 2 == 0) {
                        // Write operation
                        store.put("shared-key", "value" + index, 10000L);
                    } else {
                        // Read operation
                        String value = store.get("shared-key");
                        if (value != null) {
                            readValues.add(value);
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Verify that reads returned some values (no crashes)
        assertFalse(readValues.isEmpty(), "Should have read some values");
    }

    @Test
    @DisplayName("Test concurrent writes to same key")
    void testConcurrentWritesToSameKey() throws InterruptedException {
        int numThreads = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    store.put("same-key", "value" + index, 5000L);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Should have some value (last write wins)
        String result = store.get("same-key");
        assertNotNull(result, "Should have a value after concurrent writes");
        assertTrue(result.startsWith("value"), "Value should be in expected format");
    }

    // ==================== Partitioning Tests ====================

    @Test
    @DisplayName("Test data distribution across partitions")
    void testPartitioning() {
        // Add many keys to test distribution
        for (int i = 0; i < 100; i++) {
            store.put("key" + i, "value" + i, 10000L);
        }
        
        // Verify all keys are retrievable
        for (int i = 0; i < 100; i++) {
            assertEquals("value" + i, store.get("key" + i));
        }
    }

    @Test
    @DisplayName("Test with single partition")
    void testSinglePartition() {
        KeyValueStore<String, String> singlePartitionStore = new KeyValueStore<>(DEFAULT_CLEANUP_INTERVAL, 1);
        try {
            singlePartitionStore.put("key1", "value1", 5000L);
            singlePartitionStore.put("key2", "value2", 5000L);
            
            assertEquals("value1", singlePartitionStore.get("key1"));
            assertEquals("value2", singlePartitionStore.get("key2"));
        } finally {
            try {
                singlePartitionStore.close();
            } catch (Exception e) {
                fail("Failed to close store");
            }
        }
    }

    @Test
    @DisplayName("Test with many partitions")
    void testManyPartitions() {
        KeyValueStore<String, String> manyPartitionStore = new KeyValueStore<>(DEFAULT_CLEANUP_INTERVAL, 16);
        try {
            for (int i = 0; i < 50; i++) {
                manyPartitionStore.put("key" + i, "value" + i, 5000L);
            }
            
            for (int i = 0; i < 50; i++) {
                assertEquals("value" + i, manyPartitionStore.get("key" + i));
            }
        } finally {
            try {
                manyPartitionStore.close();
            } catch (Exception e) {
                fail("Failed to close store");
            }
        }
    }

    // ==================== Edge Cases and Error Handling ====================

    @Test
    @DisplayName("Test get on non-existent key returns null")
    void testGetNonExistentKey() {
        assertNull(store.get("non-existent-key"), "Should return null for non-existent key");
    }

    @Test
    @DisplayName("Test put and get with null value")
    void testNullValue() {
        store.put("null-key", null, 5000L);
        assertNull(store.get("null-key"), "Should handle null values");
    }

    @Test
    @DisplayName("Test with empty string key and value")
    void testEmptyStrings() {
        store.put("", "", 5000L);
        assertEquals("", store.get(""), "Should handle empty string key and value");
    }

    @Test
    @DisplayName("Test with special characters in keys")
    void testSpecialCharacterKeys() {
        store.put("key@#$%", "value1", 5000L);
        store.put("key with spaces", "value2", 5000L);
        store.put("key\nwith\nnewlines", "value3", 5000L);
        
        assertEquals("value1", store.get("key@#$%"));
        assertEquals("value2", store.get("key with spaces"));
        assertEquals("value3", store.get("key\nwith\nnewlines"));
    }

    @Test
    @DisplayName("Test with zero TTL")
    void testZeroTTL() throws InterruptedException {
        store.put("zero-ttl", "value", 0L);
        
        // Should expire immediately
        Thread.sleep(50L);
        assertNull(store.get("zero-ttl"), "Value with zero TTL should expire immediately");
    }

    @Test
    @DisplayName("Test rapid put and get operations")
    void testRapidOperations() {
        for (int i = 0; i < 1000; i++) {
            store.put("rapid-key" + i, "rapid-value" + i, 10000L);
        }
        
        for (int i = 0; i < 1000; i++) {
            assertEquals("rapid-value" + i, store.get("rapid-key" + i));
        }
    }

    // ==================== Performance and Stress Tests ====================

    @Test
    @DisplayName("Test high volume concurrent operations")
    void testHighVolumeConcurrentOperations() throws InterruptedException {
        int numOperations = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(numOperations);
        
        for (int i = 0; i < numOperations; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    store.put("key" + index, "value" + index, 10000L);
                    store.get("key" + (index % 100));
                } finally {
                    latch.countDown();
                }
            });
        }
        
        boolean completed = latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();
        
        assertTrue(completed, "All operations should complete within timeout");
    }

    @Test
    @DisplayName("Test mixed TTL values")
    void testMixedTTLValues() throws InterruptedException {
        store.put("short", "value1", 500L);
        store.put("medium", "value2", 2000L);
        store.put("long", "value3", 10000L);
        
        // Check immediately
        assertEquals("value1", store.get("short"));
        assertEquals("value2", store.get("medium"));
        assertEquals("value3", store.get("long"));
        
        // Wait for short to expire
        Thread.sleep(600L);
        assertNull(store.get("short"));
        assertEquals("value2", store.get("medium"));
        assertEquals("value3", store.get("long"));
        
        // Wait for medium to expire
        Thread.sleep(1500L);
        assertNull(store.get("medium"));
        assertEquals("value3", store.get("long"));
    }

    @Test
    @DisplayName("Test store behavior after multiple close calls")
    void testMultipleCloseCalls() throws Exception {
        KeyValueStore<String, String> testStore = new KeyValueStore<>(DEFAULT_CLEANUP_INTERVAL, DEFAULT_PARTITIONS);
        testStore.put("key1", "value1", 5000L);
        
        testStore.close();
        testStore.close(); // Should not throw exception
        
        assertTrue(true, "Multiple close calls should not cause errors");
    }
}
