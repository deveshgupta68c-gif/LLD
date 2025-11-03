package KeyValueStoreV2;

/**
 * Simple individual test cases with main methods
 * Each test can be run independently
 */
public class SimpleTests {

    /**
     * Test 1: Basic Put and Get
     */
    public static void main(String[] args) throws Exception {
        System.out.println("=== Running Simple Test ===\n");
        
        // Uncomment the test you want to run:
        testBasicPutGet();
        // testTTLExpiration();
        // testConcurrency();
        // testPartitioning();
        // testEdgeCases();
    }

    /**
     * Test basic put and get operations
     */
    public static void testBasicPutGet() throws Exception {
        System.out.println("Test: Basic Put and Get");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            // Put some values
            store.put("user:1", "Alice", 5000L);
            store.put("user:2", "Bob", 5000L);
            store.put("user:3", "Charlie", 5000L);

            // Get values
            System.out.println("user:1 = " + store.get("user:1"));
            System.out.println("user:2 = " + store.get("user:2"));
            System.out.println("user:3 = " + store.get("user:3"));

            // Update a value
            store.put("user:1", "Alice Updated", 5000L);
            System.out.println("user:1 (updated) = " + store.get("user:1"));

            System.out.println("✓ Test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test TTL expiration
     */
    public static void testTTLExpiration() throws Exception {
        System.out.println("Test: TTL Expiration");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            // Put value with 2 second TTL
            store.put("session:abc123", "active", 2000L);
            
            System.out.println("Immediately after put: " + store.get("session:abc123"));
            
            System.out.println("Waiting 1 second...");
            Thread.sleep(1000L);
            System.out.println("After 1 second: " + store.get("session:abc123"));
            
            System.out.println("Waiting another 1.5 seconds...");
            Thread.sleep(1500L);
            System.out.println("After 2.5 seconds total: " + store.get("session:abc123"));

            System.out.println("✓ Test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test concurrent access
     */
    public static void testConcurrency() throws Exception {
        System.out.println("Test: Concurrent Access");
        KeyValueStore<String, Integer> store = new KeyValueStore<>(1000L, 4);

        try {
            // Create 5 threads that write and read
            Thread[] threads = new Thread[5];
            
            for (int i = 0; i < 5; i++) {
                final int threadId = i;
                threads[i] = new Thread(() -> {
                    // Each thread writes 10 values
                    for (int j = 0; j < 10; j++) {
                        String key = "thread" + threadId + ":key" + j;
                        store.put(key, threadId * 100 + j, 10000L);
                    }
                    
                    // Each thread reads its values
                    for (int j = 0; j < 10; j++) {
                        String key = "thread" + threadId + ":key" + j;
                        Integer value = store.get(key);
                        System.out.println("Thread " + threadId + " read " + key + " = " + value);
                    }
                });
                threads[i].start();
            }

            // Wait for all threads to complete
            for (Thread thread : threads) {
                thread.join();
            }

            System.out.println("✓ Test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test partitioning with different partition counts
     */
    public static void testPartitioning() throws Exception {
        System.out.println("Test: Partitioning");

        // Test with 1 partition
        System.out.println("Testing with 1 partition:");
        KeyValueStore<String, String> store1 = new KeyValueStore<>(1000L, 1);
        try {
            for (int i = 0; i < 10; i++) {
                store1.put("key" + i, "value" + i, 5000L);
            }
            System.out.println("  Stored 10 keys in 1 partition");
            System.out.println("  key5 = " + store1.get("key5"));
        } finally {
            store1.close();
        }

        // Test with 8 partitions
        System.out.println("Testing with 8 partitions:");
        KeyValueStore<String, String> store8 = new KeyValueStore<>(1000L, 8);
        try {
            for (int i = 0; i < 10; i++) {
                store8.put("key" + i, "value" + i, 5000L);
            }
            System.out.println("  Stored 10 keys in 8 partitions");
            System.out.println("  key5 = " + store8.get("key5"));
        } finally {
            store8.close();
        }

        System.out.println("✓ Test passed\n");
    }

    /**
     * Test edge cases
     */
    public static void testEdgeCases() throws Exception {
        System.out.println("Test: Edge Cases");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            // Test 1: Empty string key and value
            store.put("", "", 5000L);
            System.out.println("Empty string key: '" + store.get("") + "'");

            // Test 2: Special characters in key
            store.put("key@#$%^&*()", "special-value", 5000L);
            System.out.println("Special char key: " + store.get("key@#$%^&*()"));

            // Test 3: Very long key
            String longKey = "key" + "x".repeat(1000);
            store.put(longKey, "long-key-value", 5000L);
            System.out.println("Long key (1000 chars): " + store.get(longKey));

            // Test 4: Null value
            store.put("null-value-key", null, 5000L);
            System.out.println("Null value: " + store.get("null-value-key"));

            // Test 5: Non-existent key
            System.out.println("Non-existent key: " + store.get("does-not-exist"));

            // Test 6: Zero TTL
            store.put("zero-ttl", "expires-immediately", 0L);
            Thread.sleep(100L);
            System.out.println("Zero TTL (after 100ms): " + store.get("zero-ttl"));

            System.out.println("✓ Test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Performance test - measure put/get operations
     */
    public static void testPerformance() throws Exception {
        System.out.println("Test: Performance");
        KeyValueStore<Integer, Integer> store = new KeyValueStore<>(5000L, 8);

        try {
            int numOperations = 10000;

            // Measure put operations
            long startPut = System.currentTimeMillis();
            for (int i = 0; i < numOperations; i++) {
                store.put(i, i * 2, 60000L);
            }
            long endPut = System.currentTimeMillis();
            System.out.println("Put " + numOperations + " keys in " + (endPut - startPut) + "ms");
            System.out.println("Average: " + ((endPut - startPut) * 1000.0 / numOperations) + " microseconds per put");

            // Measure get operations
            long startGet = System.currentTimeMillis();
            for (int i = 0; i < numOperations; i++) {
                store.get(i);
            }
            long endGet = System.currentTimeMillis();
            System.out.println("Get " + numOperations + " keys in " + (endGet - startGet) + "ms");
            System.out.println("Average: " + ((endGet - startGet) * 1000.0 / numOperations) + " microseconds per get");

            System.out.println("✓ Test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test cleanup behavior
     */
    public static void testCleanup() throws Exception {
        System.out.println("Test: Automatic Cleanup");
        KeyValueStore<String, String> store = new KeyValueStore<>(500L, 4); // Cleanup every 500ms

        try {
            // Add 20 keys with 1 second TTL
            System.out.println("Adding 20 keys with 1 second TTL...");
            for (int i = 0; i < 20; i++) {
                store.put("cleanup-key-" + i, "value-" + i, 1000L);
            }

            // Check immediately
            System.out.println("Immediately after: key-0 = " + store.get("cleanup-key-0"));

            // Wait for expiration
            System.out.println("Waiting 1.5 seconds for cleanup...");
            Thread.sleep(1500L);

            // Check after cleanup
            int nullCount = 0;
            for (int i = 0; i < 20; i++) {
                if (store.get("cleanup-key-" + i) == null) {
                    nullCount++;
                }
            }
            System.out.println("Keys cleaned up: " + nullCount + "/20");

            System.out.println("✓ Test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Real-world scenario: Session management
     */
    public static void testSessionManagement() throws Exception {
        System.out.println("Test: Session Management Scenario");
        KeyValueStore<String, String> sessionStore = new KeyValueStore<>(2000L, 4);

        try {
            // Simulate user sessions with 5 second TTL
            sessionStore.put("session:user1", "Alice", 5000L);
            sessionStore.put("session:user2", "Bob", 5000L);
            sessionStore.put("session:user3", "Charlie", 5000L);

            System.out.println("Active sessions:");
            System.out.println("  user1: " + sessionStore.get("session:user1"));
            System.out.println("  user2: " + sessionStore.get("session:user2"));
            System.out.println("  user3: " + sessionStore.get("session:user3"));

            // User1 activity - refresh session
            System.out.println("\nUser1 active - refreshing session...");
            sessionStore.put("session:user1", "Alice", 5000L);

            // Wait 3 seconds
            System.out.println("Waiting 3 seconds...");
            Thread.sleep(3000L);

            System.out.println("\nSessions after 3 seconds:");
            System.out.println("  user1: " + sessionStore.get("session:user1") + " (refreshed, should exist)");
            System.out.println("  user2: " + sessionStore.get("session:user2") + " (should exist)");
            System.out.println("  user3: " + sessionStore.get("session:user3") + " (should exist)");

            // Wait another 3 seconds
            System.out.println("\nWaiting another 3 seconds...");
            Thread.sleep(3000L);

            System.out.println("\nSessions after 6 seconds total:");
            System.out.println("  user1: " + sessionStore.get("session:user1") + " (should exist)");
            System.out.println("  user2: " + sessionStore.get("session:user2") + " (should be expired)");
            System.out.println("  user3: " + sessionStore.get("session:user3") + " (should be expired)");

            System.out.println("✓ Test passed\n");
        } finally {
            sessionStore.close();
        }
    }
}
