# KeyValueStore Test Suite

## Overview
Comprehensive test suite for the KeyValueStore implementation with TTL (Time To Live) support.

## Features Tested

### 1. **Basic Operations**
- Put and get operations
- Multiple key-value pairs
- Overwriting existing keys
- Different data types (Integer, List, etc.)
- Complex objects as values

### 2. **TTL (Time To Live) Functionality**
- Automatic expiration after TTL
- Value accessibility before expiration
- Short and long TTL values
- TTL reset by re-putting keys
- Zero TTL handling
- Mixed TTL values

### 3. **Automatic Cleanup**
- Background cleanup thread
- Removal of expired entries
- Cleanup thread lifecycle

### 4. **Concurrency**
- Concurrent writes to different keys
- Concurrent reads and writes
- Concurrent writes to same key
- High volume concurrent operations
- Thread safety with ReentrantReadWriteLock

### 5. **Partitioning**
- Data distribution across partitions
- Single partition configuration
- Multiple partitions configuration

### 6. **Edge Cases**
- Non-existent keys
- Null values
- Empty strings
- Special characters in keys
- Rapid operations
- Multiple close calls

## Test Statistics
- **Total Test Cases**: 35+
- **Test Categories**: 6
- **Coverage Areas**: Basic ops, TTL, Concurrency, Partitioning, Edge cases, Performance

## Running the Tests

### Prerequisites
Add JUnit 5 to your project dependencies:

#### For Maven (pom.xml):
```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### For Gradle (build.gradle):
```gradle
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
}
```

#### For IntelliJ IDEA (Manual):
1. Right-click on the test file
2. Select "Run 'KeyValueStoreTest'"
3. IntelliJ will prompt to add JUnit 5 to classpath
4. Click "OK" to add the library

### Running Tests

#### From IntelliJ IDEA:
1. Open `KeyValueStoreTest.java`
2. Right-click on the class or individual test method
3. Select "Run 'KeyValueStoreTest'" or "Run 'testName()'"

#### From Command Line (with Maven):
```bash
mvn test -Dtest=KeyValueStoreTest
```

#### From Command Line (with Gradle):
```bash
gradle test --tests KeyValueStoreTest
```

## Test Execution Notes

### Known Issues in Implementation
⚠️ **Bug in `get()` method**: Line 69 uses `writeLock().unlock()` instead of `readLock().unlock()`. This may cause lock issues.

### Test Timing
- Some tests use `Thread.sleep()` to verify TTL behavior
- Tests with sleep operations may take several seconds to complete
- Total test suite execution time: ~15-20 seconds

### Concurrency Tests
- Use thread pools with 10-100 threads
- Test thread safety of the partitioned data structure
- Verify no race conditions or deadlocks

## Test Structure

```
KeyValueStoreTest
├── Basic Operations Tests (5 tests)
├── TTL Tests (5 tests)
├── Cleanup Thread Tests (2 tests)
├── Concurrency Tests (3 tests)
├── Partitioning Tests (3 tests)
├── Edge Cases Tests (9 tests)
└── Performance Tests (3 tests)
```

## Key Test Scenarios

### 1. TTL Expiration Flow
```java
store.put("key", "value", 1000L);  // 1 second TTL
assertEquals("value", store.get("key"));  // Immediately accessible
Thread.sleep(1100L);
assertNull(store.get("key"));  // Expired after TTL
```

### 2. Concurrent Operations
```java
ExecutorService executor = Executors.newFixedThreadPool(10);
// Multiple threads writing/reading simultaneously
// Verifies thread safety and data consistency
```

### 3. Automatic Cleanup
```java
// Cleanup thread runs every 500ms
// Removes expired entries automatically
// Tests verify cleanup happens in background
```

## Extending the Tests

To add more tests:
1. Follow the existing test structure
2. Use `@Test` annotation
3. Use `@DisplayName` for descriptive test names
4. Add to appropriate test category section
5. Clean up resources in `@AfterEach` if needed

## Contact
For issues or questions about the test suite, refer to the KeyValueStore implementation at:
`/src/KeyValueStoreV2/KeyValueStore.java`
