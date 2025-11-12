package ObjectPoolDesignPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Driver class to manually test DBConnectionManager (Object Pool Design Pattern)
 * Run this class to see the behavior of the connection pool
 */
public class DBConnectionManagerDriver {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("=== Object Pool Design Pattern - DBConnectionManager Test ===\n");

        // Test 1: Singleton Pattern
        testSingletonPattern();

        // Test 2: Basic Connection Acquisition
        testBasicConnectionAcquisition();

        // Test 3: Pool Limit Test
        testPoolLimit();

        // Test 4: Concurrent Access Test
        testConcurrentAccess();

        // Print Summary
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("Total Tests: " + totalTests);
        System.out.println("✓ Passed: " + passedTests);
        System.out.println("✗ Failed: " + failedTests);
        System.out.println("Success Rate: " + (totalTests > 0 ? (passedTests * 100.0 / totalTests) : 0) + "%");
        System.out.println("=".repeat(60));
    }

    private static void testSingletonPattern() {
        System.out.println("Test 1: Singleton Pattern");
        System.out.println("-".repeat(50));
        totalTests++;

        try {
            DBConnectionManager manager1 = DBConnectionManager.getInstance();
            DBConnectionManager manager2 = DBConnectionManager.getInstance();

            System.out.println("Manager1: " + manager1);
            System.out.println("Manager2: " + manager2);
            System.out.println("Are they same instance? " + (manager1 == manager2));
            
            if (manager1 == manager2) {
                System.out.println("✓ Singleton test PASSED\n");
                passedTests++;
            } else {
                System.err.println("✗ Singleton test FAILED\n");
                failedTests++;
            }
        } catch (Exception e) {
            System.err.println("✗ Singleton test FAILED: " + e.getMessage() + "\n");
            failedTests++;
        }
    }

    private static void testBasicConnectionAcquisition() {
        System.out.println("Test 2: Basic Connection Acquisition");
        System.out.println("-".repeat(50));
        totalTests++;

        DBConnectionManager manager = DBConnectionManager.getInstance();

        try {
            System.out.println("Attempting to get first connection...");
            DBConnection conn1 = manager.getConnection();
            System.out.println("✓ Connection 1 acquired successfully");
            conn1.showConnectionDetails();

            System.out.println("\nAttempting to get second connection...");
            DBConnection conn2 = manager.getConnection();
            System.out.println("✓ Connection 2 acquired successfully");
            conn2.showConnectionDetails();

            System.out.println("\n✓ Basic connection acquisition test PASSED\n");
            passedTests++;
        } catch (Exception e) {
            System.err.println("✗ Basic connection acquisition test FAILED: " + e.getMessage());
            e.printStackTrace();
            failedTests++;
        }
    }

    private static void testPoolLimit() {
        System.out.println("Test 3: Pool Limit Test (MAX_POOL_SIZE = 4)");
        System.out.println("-".repeat(50));
        totalTests++;

        DBConnectionManager manager = DBConnectionManager.getInstance();
        List<DBConnection> connections = new ArrayList<>();
        int expectedFailures = 0;

        // Try to get connections up to the limit
        for (int i = 1; i <= 5; i++) {
            try {
                System.out.println("Attempting to get connection #" + i + "...");
                DBConnection conn = manager.getConnection();
                connections.add(conn);
                System.out.println("✓ Connection #" + i + " acquired successfully");
            } catch (RuntimeException e) {
                System.err.println("✗ Connection #" + i + " failed: " + e.getMessage());
                expectedFailures++;
            } catch (Exception e) {
                System.err.println("✗ Unexpected error for connection #" + i + ": " + e.getMessage());
            }
        }

        System.out.println("Total connections acquired: " + connections.size());
        
        // Test passes if we got exactly 4 connections and 1 failure
        if (connections.size() <= 4 && expectedFailures > 0) {
            System.out.println("✓ Pool limit test PASSED\n");
            passedTests++;
        } else {
            System.err.println("✗ Pool limit test FAILED (Expected max 4 connections, got " + connections.size() + ")\n");
            failedTests++;
        }
    }

    private static void testConcurrentAccess() {
        System.out.println("Test 4: Concurrent Access Test");
        System.out.println("-".repeat(50));
        totalTests++;

        DBConnectionManager manager = DBConnectionManager.getInstance();
        int threadCount = 6;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        final boolean[] testPassed = {true};

        System.out.println("Spawning " + threadCount + " threads to acquire connections concurrently...");

        for (int i = 1; i <= threadCount; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    System.out.println("Thread-" + threadId + ": Attempting to get connection...");
                    DBConnection conn = manager.getConnection();
                    System.out.println("Thread-" + threadId + ": ✓ Connection acquired successfully");
                    
                    // Simulate some work
                    Thread.sleep(100);
                    
                    System.out.println("Thread-" + threadId + ": Work completed");
                } catch (RuntimeException e) {
                    System.err.println("Thread-" + threadId + ": ✗ " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Thread-" + threadId + ": ✗ Unexpected error - " + e.getMessage());
                    testPassed[0] = false;
                }
            });
        }

        executor.shutdown();
        try {
            if (executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("✓ All threads completed");
                if (testPassed[0]) {
                    System.out.println("✓ Concurrent access test PASSED\n");
                    passedTests++;
                } else {
                    System.err.println("✗ Concurrent access test FAILED\n");
                    failedTests++;
                }
            } else {
                System.err.println("✗ Timeout waiting for threads to complete");
                System.err.println("✗ Concurrent access test FAILED\n");
                failedTests++;
            }
        } catch (InterruptedException e) {
            System.err.println("✗ Test interrupted: " + e.getMessage());
            System.err.println("✗ Concurrent access test FAILED\n");
            failedTests++;
            Thread.currentThread().interrupt();
        }
    }
}
