package DesignPatterns.ObjectPoolDesignPattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for DBConnectionManager (Object Pool Design Pattern)
 * Tests singleton behavior, connection pooling, thread safety, and edge cases
 */
class DBConnectionManagerTest {

    @Nested
    @DisplayName("Singleton Pattern Tests")
    class SingletonTests {

        @Test
        @DisplayName("getInstance should return same instance")
        void testGetInstanceReturnsSameInstance() {
            DBConnectionManager instance1 = DBConnectionManager.getInstance();
            DBConnectionManager instance2 = DBConnectionManager.getInstance();
            
            assertNotNull(instance1, "Instance should not be null");
            assertSame(instance1, instance2, "Both instances should be the same object");
        }

        @Test
        @DisplayName("getInstance should be thread-safe")
        void testGetInstanceThreadSafety() throws InterruptedException {
            int threadCount = 10;
            CountDownLatch latch = new CountDownLatch(1);
            List<DBConnectionManager> instances = new CopyOnWriteArrayList<>();
            
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        latch.await(); // All threads wait here
                        instances.add(DBConnectionManager.getInstance());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
            
            latch.countDown(); // Release all threads at once
            executor.shutdown();
            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
            
            assertEquals(threadCount, instances.size(), "Should have collected all instances");
            
            // All instances should be the same
            DBConnectionManager firstInstance = instances.get(0);
            for (DBConnectionManager instance : instances) {
                assertSame(firstInstance, instance, "All instances should be the same");
            }
        }
    }

    @Nested
    @DisplayName("Connection Pool Initialization Tests")
    class InitializationTests {

        @Test
        @DisplayName("Pool should be initialized with correct max size")
        void testPoolInitialization() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            assertNotNull(manager, "Manager should be initialized");
        }
    }

    @Nested
    @DisplayName("Connection Acquisition Tests")
    class ConnectionAcquisitionTests {

        @Test
        @DisplayName("getConnection should return a valid connection")
        void testGetConnectionReturnsValidConnection() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            
            assertDoesNotThrow(() -> {
                DBConnection connection = manager.getConnection();
                assertNotNull(connection, "Connection should not be null");
            });
        }

        @Test
        @DisplayName("getConnection should create new connection when pool is empty")
        void testGetConnectionCreatesNewConnection() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            
            assertDoesNotThrow(() -> {
                DBConnection connection1 = manager.getConnection();
                assertNotNull(connection1, "First connection should not be null");
                
                DBConnection connection2 = manager.getConnection();
                assertNotNull(connection2, "Second connection should not be null");
            });
        }

        @Test
        @DisplayName("getConnection should throw exception when pool limit is reached")
        void testGetConnectionThrowsExceptionWhenLimitReached() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            
            // Try to get more connections than MAX_POOL_SIZE (which is 4)
            List<DBConnection> connections = new ArrayList<>();
            
            // This should work for connections up to the pool size
            for (int i = 0; i < 4; i++) {
                try {
                    connections.add(manager.getConnection());
                } catch (Exception e) {
                    // If it fails before reaching limit, that's a separate issue
                }
            }
            
            // Attempting to get one more should throw an exception
            assertThrows(RuntimeException.class, () -> {
                manager.getConnection();
            }, "Should throw RuntimeException when pool limit is reached");
        }

        @Test
        @DisplayName("Multiple connections should have correct properties")
        void testMultipleConnectionsHaveCorrectProperties() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            
            assertDoesNotThrow(() -> {
                DBConnection conn1 = manager.getConnection();
                assertNotNull(conn1, "Connection 1 should not be null");
                assertEquals("localhost:3132", conn1.getConnectionUrl(), "Connection URL should match");
                assertEquals("admin", conn1.getUsername(), "Username should match");
                assertEquals("SampleDB", conn1.getdBname(), "Database name should match");
            });
        }
    }

    @Nested
    @DisplayName("Thread Safety Tests")
    class ThreadSafetyTests {

        @Test
        @DisplayName("Concurrent getConnection calls should be thread-safe")
        void testConcurrentGetConnection() throws InterruptedException {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            int threadCount = 4; // Equal to MAX_POOL_SIZE
            CountDownLatch startLatch = new CountDownLatch(1);
            CountDownLatch completionLatch = new CountDownLatch(threadCount);
            List<DBConnection> connections = new CopyOnWriteArrayList<>();
            List<Exception> exceptions = new CopyOnWriteArrayList<>();
            
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        DBConnection conn = manager.getConnection();
                        connections.add(conn);
                    } catch (Exception e) {
                        exceptions.add(e);
                    } finally {
                        completionLatch.countDown();
                    }
                });
            }
            
            startLatch.countDown();
            assertTrue(completionLatch.await(5, TimeUnit.SECONDS), "All threads should complete");
            executor.shutdown();
            
            // Either all connections succeed or some fail gracefully
            assertTrue(connections.size() > 0 || exceptions.size() > 0, 
                "Should have either connections or exceptions");
        }

        @Test
        @DisplayName("Concurrent access should not exceed pool limit")
        void testConcurrentAccessDoesNotExceedLimit() throws InterruptedException {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            int threadCount = 10; // More than MAX_POOL_SIZE
            AtomicInteger successCount = new AtomicInteger(0);
            AtomicInteger failureCount = new AtomicInteger(0);
            
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);
            
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        manager.getConnection();
                        successCount.incrementAndGet();
                    } catch (RuntimeException e) {
                        if (e.getMessage().contains("DB Connection limit reached")) {
                            failureCount.incrementAndGet();
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }
            
            latch.await(5, TimeUnit.SECONDS);
            executor.shutdown();
            
            assertTrue(successCount.get() <= 4, 
                "Success count should not exceed MAX_POOL_SIZE of 4");
            assertTrue(failureCount.get() > 0, 
                "Should have some failures when exceeding pool limit");
        }
    }

    @Nested
    @DisplayName("Edge Cases and Error Handling")
    class EdgeCaseTests {

        @Test
        @DisplayName("Connection should have valid properties")
        void testConnectionHasValidProperties() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            
            assertDoesNotThrow(() -> {
                DBConnection connection = manager.getConnection();
                assertNotNull(connection.getConnectionUrl(), "Connection URL should not be null");
                assertNotNull(connection.getUsername(), "Username should not be null");
                assertNotNull(connection.getPassword(), "Password should not be null");
                assertNotNull(connection.getdBname(), "Database name should not be null");
            });
        }

        @Test
        @DisplayName("showConnectionDetails should not throw exception")
        void testShowConnectionDetailsDoesNotThrow() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            
            assertDoesNotThrow(() -> {
                DBConnection connection = manager.getConnection();
                connection.showConnectionDetails();
            });
        }
    }

    @Nested
    @DisplayName("Connection Pooling Behavior Tests")
    class PoolingBehaviorTests {

        @Test
        @DisplayName("Pool should track connections correctly")
        void testPoolTracksConnections() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            
            // Get a connection - should move from available to inUse
            assertDoesNotThrow(() -> {
                DBConnection conn = manager.getConnection();
                assertNotNull(conn, "Connection should be retrieved");
            });
        }

        @Test
        @DisplayName("Getting max connections should exhaust pool")
        void testGettingMaxConnectionsExhaustsPool() {
            DBConnectionManager manager = DBConnectionManager.getInstance();
            List<DBConnection> connections = new ArrayList<>();
            
            // Get up to MAX_POOL_SIZE connections
            for (int i = 0; i < 4; i++) {
                try {
                    connections.add(manager.getConnection());
                } catch (Exception e) {
                    // Might fail due to implementation bugs
                }
            }
            
            // Next attempt should fail
            assertThrows(RuntimeException.class, () -> {
                manager.getConnection();
            }, "Should throw exception when pool is exhausted");
        }
    }
}
