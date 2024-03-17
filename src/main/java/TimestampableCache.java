public interface TimestampableCache<K, V> {
	void cache(K key, V value);
	V getCachedValue(K key, long ttl);
	void invalidate(K key);
	void clear();
}