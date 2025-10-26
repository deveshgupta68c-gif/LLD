package KeyValueStore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InMemoryKeyValueStore<K, V> implements KeyValueStore<K, V>{
	private final Map<K, V> storage;
	private final ReentrantReadWriteLock.WriteLock writeLock;
	private final ReentrantReadWriteLock.ReadLock readLock;
	private final Class<K> keyClass;
	public InMemoryKeyValueStore(Class<K> keyClass){
		ensureHashable(keyClass);
		this.keyClass = keyClass;
		storage = new HashMap<>();
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		writeLock = lock.writeLock();
		readLock = lock.readLock();
	}

	private void ensureHashable(Class<K> keyClass) {
		try {
			if (keyClass.getMethod("hashCode").getDeclaringClass() == Object.class) {
				throw new IllegalArgumentException("Only supports classes which are hashable");
			}
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Error while verifying hashability for class: " + keyClass.getName(), e);
		}
	}

	@Override
	public void put(K key, V value) {
		validateKey(key);
		writeLock.lock();
		try{
			storage.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	private void validateKey(K key) {
		if(!keyClass.isInstance(key)){
			throw new RuntimeException("Wrong data type key received from the user");
		}
	}

	@Override
	public V get(K key) {
		validateKey(key);
		readLock.lock();
		V value = null;
		try {
			value = storage.get(key);
		} finally {
			readLock.unlock();
		}
		return value;
	}

	@Override
	public void delete(K key) {
		validateKey(key);
		writeLock.lock();
		try{
			storage.remove(key);
		} finally {
			writeLock.unlock();
		}
	}
}
