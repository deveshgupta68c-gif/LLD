package KeyValueStore.KeyValueStoreWithReentrantLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InMemoryKeyValueStore<K, V> implements KeyValueStore<K, V> {
	private final Map<K, V> storage;
	private final Class<K> keyClass;
	private final Lock lock;
	public InMemoryKeyValueStore(Class<K> keyClass){
		ensureHashable(keyClass);
		this.keyClass = keyClass;
		storage = new HashMap<>();
		lock = new ReentrantLock();
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
		lock.lock();
		try{
			storage.put(key, value);
		} finally {
			lock.unlock();
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
		lock.lock();
		V value = null;
		try {
			value = storage.get(key);
		} finally {
			lock.unlock();
		}
		return value;
	}

	@Override
	public void delete(K key) {
		validateKey(key);
		lock.lock();
		try{
			storage.remove(key);
		} finally {
			lock.unlock();
		}
	}
}
