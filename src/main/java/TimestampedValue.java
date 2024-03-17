import lombok.Getter;

@Getter
public class TimestampedValue<V> {

	private final V value;

	private final long timestamp;

	public TimestampedValue(V value) {

		this.value = value;

		this.timestamp = System.currentTimeMillis();

	}

	public boolean cacheIsValid(Clockable clock, long ttl) {
		return TimeUtils.checkTtlStartTime(
				this.getTimestamp(),
				clock,
				ttl
		);
	}

}