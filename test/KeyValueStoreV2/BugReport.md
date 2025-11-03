# Bug Report: KeyValueStore Implementation

## Critical Bug in `get()` Method

### Location
**File**: `/src/KeyValueStoreV2/KeyValueStore.java`  
**Line**: 69  
**Method**: `get(K key)`

### Issue
The `get()` method acquires a **read lock** on line 59 but releases a **write lock** on line 69.

### Current Code (Lines 56-72)
```java
public V get(K key){
    int partitionNumber = key.hashCode() % NUMBER_OF_PARTITIONS;
    V value = null;
    lock[partitionNumber].readLock().lock();  // Line 59: Acquires READ lock
    try{
        ValueHolder<V> valueHolder = database[partitionNumber].get(key);
        if(valueHolder.ttl < System.currentTimeMillis()){
            database[partitionNumber].remove(key);
        } else{
            value = valueHolder.value;
        }
    } finally {
        lock[partitionNumber].readLock().unlock();  // Line 69: Releases WRITE lock ❌
    }
    return value;
}
```

### Problem
1. **Lock Mismatch**: Acquiring a read lock but releasing a write lock will cause:
   - The read lock to never be released (resource leak)
   - Attempting to release a write lock that was never acquired (IllegalMonitorStateException)
   - Potential deadlocks in concurrent scenarios

2. **Additional Issue**: The `get()` method performs a **write operation** (`database[partitionNumber].remove(key)` on line 63) while holding only a read lock. This violates the read-write lock semantics.

### Impact
- **Severity**: Critical
- **Symptoms**:
  - `IllegalMonitorStateException` when calling `get()`
  - Deadlocks under concurrent access
  - Memory leaks from unreleased read locks
  - Data corruption from write operations under read lock

### Recommended Fix

#### Option 1: Upgrade to Write Lock (Recommended)
Since the method performs a write operation (removing expired keys), it should use a write lock:

```java
public V get(K key){
    int partitionNumber = key.hashCode() % NUMBER_OF_PARTITIONS;
    V value = null;
    lock[partitionNumber].writeLock().lock();  // Use write lock
    try{
        ValueHolder<V> valueHolder = database[partitionNumber].get(key);
        if(valueHolder.ttl < System.currentTimeMillis()){
            database[partitionNumber].remove(key);
        } else{
            value = valueHolder.value;
        }
    } finally {
        lock[partitionNumber].writeLock().unlock();  // Release write lock
    }
    return value;
}
```

#### Option 2: Check-Then-Act Pattern
Use read lock first, then upgrade to write lock if needed:

```java
public V get(K key){
    int partitionNumber = key.hashCode() % NUMBER_OF_PARTITIONS;
    V value = null;
    
    // First, try with read lock
    lock[partitionNumber].readLock().lock();
    try{
        ValueHolder<V> valueHolder = database[partitionNumber].get(key);
        if(valueHolder == null){
            return null;
        }
        
        if(valueHolder.ttl >= System.currentTimeMillis()){
            // Not expired, return value
            return valueHolder.value;
        }
    } finally {
        lock[partitionNumber].readLock().unlock();
    }
    
    // If expired, acquire write lock to remove
    lock[partitionNumber].writeLock().lock();
    try{
        ValueHolder<V> valueHolder = database[partitionNumber].get(key);
        if(valueHolder != null && valueHolder.ttl < System.currentTimeMillis()){
            database[partitionNumber].remove(key);
        }
    } finally {
        lock[partitionNumber].writeLock().unlock();
    }
    
    return null;
}

### Additional Issues

#### Null Pointer Exception Risk
**Line 62**: `valueHolder.ttl` will throw `NullPointerException` if the key doesn't exist.

**Fix**: Add null check:
```java
ValueHolder<V> valueHolder = database[partitionNumber].get(key);
if(valueHolder == null){
    return null;
}
if(valueHolder.ttl < System.currentTimeMillis()){
    database[partitionNumber].remove(key);
} else{
    value = valueHolder.value;
}

### Testing Impact
The test suite in `KeyValueStoreTest.java` may fail or hang due to these bugs. Fix the implementation before running the full test suite.

### Priority
🔴 **HIGH PRIORITY** - This bug will cause the application to fail in production under concurrent load.
