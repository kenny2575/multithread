public class TimestampedCache<K, V> implements TimestampableCache<K, V> {

	private final Cache<K, TimestampedValue<V>> cache;
	private final Clockable clock;

	public TimestampedCache(Clockable clock) {
		this.clock = clock;
		this.cache = new Cache<>();
	}

	@Override
	public void cache(K key, V value) {
		this.cache.cache(key, new TimestampedValue<>(value));
	}

	@Override
	public V getCachedValue(K key, long ttl) {

		if (!this.cache.exists(key))
			return null;

		TimestampedValue<V> timestampedValue = this.cache.getCachedValue(key);

		if (ttl == 0)

			return timestampedValue.getValue();

		else {

			if (timestampedValue.cacheIsValid(this.clock, ttl))

				return timestampedValue.getValue();

			else {

				this.invalidate(key);

				return null;

			}

		}

	}

	@Override
	public void invalidate(K key) {
		this.cache.invalidateCache(key);
	}

	@Override
	public void clear() {
		this.cache.clear();
	}

}
