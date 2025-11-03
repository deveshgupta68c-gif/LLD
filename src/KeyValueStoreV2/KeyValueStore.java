package KeyValueStoreV2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class KeyValueStore<K, V> implements AutoCloseable{
	private final Map<K, ValueHolder<V>>[] database;
	private final ReentrantReadWriteLock[] lock;
	private final Thread cleanerThread;
	private final Long cleanUpTimeInMillis;
	private volatile boolean running = true;
	private final Integer NUMBER_OF_PARTITIONS;

	@SuppressWarnings("unchecked")
	public KeyValueStore(Long cleanUpTimeInMillis, Integer NUMBER_OF_PARTITIONS) {
		this.NUMBER_OF_PARTITIONS = NUMBER_OF_PARTITIONS;
		this.database = new Map[NUMBER_OF_PARTITIONS];
		this.lock = new ReentrantReadWriteLock[NUMBER_OF_PARTITIONS];
		this.cleanUpTimeInMillis = cleanUpTimeInMillis;
		for(int i = 0; i < NUMBER_OF_PARTITIONS; i++){
			database[i] = new HashMap<K, ValueHolder<V>>();
			lock[i] = new ReentrantReadWriteLock();
		}
		this.cleanerThread = new Thread(this::cleanUp, "KeyValueStoreDB-janitor");
		this.cleanerThread.setDaemon(true);
		this.cleanerThread.start();
	}

	@Override
	public void close() throws Exception {
		running = false;
		cleanerThread.interrupt();
	}

	private static class ValueHolder<V>{
		V value;
		Long ttl;

		ValueHolder(V value, Long ttl) {
			this.value = value;
			this.ttl = ttl;
		}
	}

	public void put(K key, V value, Long timeInMillis){
		int partitionNumber = key.hashCode() % NUMBER_OF_PARTITIONS;
		lock[partitionNumber].writeLock().lock();
		try{
			Long ttl = System.currentTimeMillis() + timeInMillis;
			database[partitionNumber].put(key, new ValueHolder<>(value, ttl));
		} finally {
			lock[partitionNumber].writeLock().unlock();
		}
	}
	public  V get(K key){
		int partitionNumber = key.hashCode() % NUMBER_OF_PARTITIONS;
		V value = null;
		lock[partitionNumber].readLock().lock();
		try{
			ValueHolder<V> valueHolder = database[partitionNumber].get(key);
			if(valueHolder.ttl < System.currentTimeMillis()){
				database[partitionNumber].remove(key);

			} else{
				value = valueHolder.value;
			}
		} finally {
			lock[partitionNumber].writeLock().unlock();
		}
		return value;
	}

	public void cleanUp(){
		while(running){
			try{
				Thread.sleep(cleanUpTimeInMillis);
				for(int i = 0; i < NUMBER_OF_PARTITIONS; i++){
					lock[i].writeLock().lock();
					Long time = System.currentTimeMillis();
					try{
						database[i].entrySet().removeIf(entry -> entry.getValue().ttl < time);
					} finally {
						lock[i].writeLock().unlock();
					}
				}
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}



}
