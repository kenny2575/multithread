import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<K, V> implements Cacheable<K, V> {

	private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();

	@Override
	public void cache(K key, V value) {
		this.cache.put(key, value);
	}

	@Override
	public V getCachedValue(K key) {
		return this.cache.get(key);
	}

	@Override
	public void invalidateCache(K key) {
		this.cache.remove(key);
	}

	@Override
	public boolean exists(K key) {
		return this.cache.containsKey(key);
	}

	@Override
	public void clear() {
		this.cache.clear();
	}

}
