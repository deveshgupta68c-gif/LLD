package KeyValueStoreV2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demo class with main method to test KeyValueStore functionality
 * Tests basic operations, TTL, and concurrency
 */
public class KeyValueStoreDemo {

    public static void main(String[] args) {
        System.out.println("=== KeyValueStore Demo ===\n");

        try {
            testBasicOperations();
            testTTLExpiration();
            testTTLBeforeExpiration();
            testOverwriteKey();
            testConcurrentOperations();
            testMultipleTTLValues();
            testNonExistentKey();
            testAutomaticCleanup();

            System.out.println("\n=== All Tests Completed Successfully ===");
        } catch (Exception e) {
            System.err.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test 1: Basic put and get operations
     */
    private static void testBasicOperations() throws Exception {
        System.out.println("Test 1: Basic Put and Get Operations");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            store.put("name", "John Doe", 10000L);
            store.put("city", "New York", 10000L);
            store.put("country", "USA", 10000L);

            String name = store.get("name");
            String city = store.get("city");
            String country = store.get("country");

            System.out.println("  Retrieved: name=" + name + ", city=" + city + ", country=" + country);
            assert "John Doe".equals(name) : "Name mismatch";
            assert "New York".equals(city) : "City mismatch";
            assert "USA".equals(country) : "Country mismatch";

            System.out.println("  ✓ Basic operations test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test 2: TTL expiration - value should expire after TTL
     */
    private static void testTTLExpiration() throws Exception {
        System.out.println("Test 2: TTL Expiration");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            store.put("temp-key", "temp-value", 1000L); // 1 second TTL

            String valueBefore = store.get("temp-key");
            System.out.println("  Value before expiration: " + valueBefore);
            assert valueBefore != null : "Value should exist before expiration";

            System.out.println("  Waiting for TTL to expire (1.2 seconds)...");
            Thread.sleep(1200L);

            String valueAfter = store.get("temp-key");
            System.out.println("  Value after expiration: " + valueAfter);
            assert valueAfter == null : "Value should be null after expiration";

            System.out.println("  ✓ TTL expiration test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test 3: Value accessible before TTL expiration
     */
    private static void testTTLBeforeExpiration() throws Exception {
        System.out.println("Test 3: Value Accessible Before TTL Expiration");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            store.put("key1", "value1", 3000L); // 3 seconds TTL

            Thread.sleep(500L);
            String value1 = store.get("key1");
            System.out.println("  After 0.5s: " + value1);
            assert value1 != null : "Value should exist after 0.5s";

            Thread.sleep(500L);
            String value2 = store.get("key1");
            System.out.println("  After 1.0s: " + value2);
            assert value2 != null : "Value should exist after 1.0s";

            Thread.sleep(500L);
            String value3 = store.get("key1");
            System.out.println("  After 1.5s: " + value3);
            assert value3 != null : "Value should exist after 1.5s";

            System.out.println("  ✓ TTL before expiration test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test 4: Overwriting existing key
     */
    private static void testOverwriteKey() throws Exception {
        System.out.println("Test 4: Overwrite Existing Key");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            store.put("status", "pending", 10000L);
            String value1 = store.get("status");
            System.out.println("  Initial value: " + value1);

            store.put("status", "completed", 10000L);
            String value2 = store.get("status");
            System.out.println("  Updated value: " + value2);

            assert "completed".equals(value2) : "Value should be updated";
            System.out.println("  ✓ Overwrite key test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test 5: Concurrent operations
     */
    private static void testConcurrentOperations() throws Exception {
        System.out.println("Test 5: Concurrent Operations");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            int numThreads = 10;
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            System.out.println("  Starting " + numThreads + " concurrent write operations...");

            for (int i = 0; i < numThreads; i++) {
                final int index = i;
                executor.submit(() -> {
                    store.put("thread-key-" + index, "thread-value-" + index, 10000L);
                    System.out.println("    Thread " + index + " wrote key-" + index);
                });
            }

            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);

            System.out.println("  Verifying all writes...");
            for (int i = 0; i < numThreads; i++) {
                String value = store.get("thread-key-" + i);
                assert ("thread-value-" + i).equals(value) : "Thread " + i + " value mismatch";
            }

            System.out.println("  ✓ Concurrent operations test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test 6: Multiple values with different TTLs
     */
    private static void testMultipleTTLValues() throws Exception {
        System.out.println("Test 6: Multiple Values with Different TTLs");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            store.put("short-lived", "expires-soon", 800L);    // 0.8 seconds
            store.put("medium-lived", "expires-later", 2000L); // 2 seconds
            store.put("long-lived", "expires-much-later", 10000L); // 10 seconds

            System.out.println("  Initial state:");
            System.out.println("    short-lived: " + store.get("short-lived"));
            System.out.println("    medium-lived: " + store.get("medium-lived"));
            System.out.println("    long-lived: " + store.get("long-lived"));

            Thread.sleep(1000L);
            System.out.println("  After 1 second:");
            System.out.println("    short-lived: " + store.get("short-lived") + " (should be null)");
            System.out.println("    medium-lived: " + store.get("medium-lived") + " (should exist)");
            System.out.println("    long-lived: " + store.get("long-lived") + " (should exist)");

            Thread.sleep(1200L);
            System.out.println("  After 2.2 seconds:");
            System.out.println("    medium-lived: " + store.get("medium-lived") + " (should be null)");
            System.out.println("    long-lived: " + store.get("long-lived") + " (should exist)");

            assert store.get("short-lived") == null : "Short-lived should be expired";
            assert store.get("medium-lived") == null : "Medium-lived should be expired";
            assert store.get("long-lived") != null : "Long-lived should still exist";

            System.out.println("  ✓ Multiple TTL values test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test 7: Non-existent key returns null
     */
    private static void testNonExistentKey() throws Exception {
        System.out.println("Test 7: Non-Existent Key");
        KeyValueStore<String, String> store = new KeyValueStore<>(1000L, 4);

        try {
            String value = store.get("non-existent-key");
            System.out.println("  Value for non-existent key: " + value);
            assert value == null : "Non-existent key should return null";

            System.out.println("  ✓ Non-existent key test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Test 8: Automatic cleanup by background thread
     */
    private static void testAutomaticCleanup() throws Exception {
        System.out.println("Test 8: Automatic Cleanup");
        KeyValueStore<String, String> store = new KeyValueStore<>(500L, 4); // Cleanup every 500ms

        try {
            // Add multiple keys with short TTL
            for (int i = 0; i < 5; i++) {
                store.put("cleanup-key-" + i, "cleanup-value-" + i, 400L); // 400ms TTL
            }

            System.out.println("  Added 5 keys with 400ms TTL");
            System.out.println("  Waiting for automatic cleanup (1 second)...");

            Thread.sleep(1000L);

            System.out.println("  Checking if keys were cleaned up:");
            int nullCount = 0;
            for (int i = 0; i < 5; i++) {
                String value = store.get("cleanup-key-" + i);
                if (value == null) {
                    nullCount++;
                }
            }

            System.out.println("  Keys cleaned up: " + nullCount + "/5");
            assert nullCount == 5 : "All keys should be cleaned up";

            System.out.println("  ✓ Automatic cleanup test passed\n");
        } finally {
            store.close();
        }
    }

    /**
     * Bonus: Test with Integer keys and values
     */
    private static void testIntegerKeyValue() throws Exception {
        System.out.println("Bonus Test: Integer Keys and Values");
        KeyValueStore<Integer, Integer> store = new KeyValueStore<>(1000L, 4);

        try {
            store.put(1, 100, 10000L);
            store.put(2, 200, 10000L);
            store.put(3, 300, 10000L);

            Integer val1 = store.get(1);
            Integer val2 = store.get(2);
            Integer val3 = store.get(3);

            System.out.println("  Retrieved: 1=" + val1 + ", 2=" + val2 + ", 3=" + val3);
            assert val1 == 100 && val2 == 200 && val3 == 300 : "Integer values mismatch";

            System.out.println("  ✓ Integer key-value test passed\n");
        } finally {
            store.close();
        }
    }
}
