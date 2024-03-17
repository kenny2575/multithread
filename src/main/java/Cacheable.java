public interface Cacheable<K, V> {
	void cache(K key, V value);
	V getCachedValue(K key);
	void invalidateCache(K key);
	boolean exists(K key);
	void clear();
}