public class TimeUtils {

	public static long getTimeFromStartToCurrent(long startTime, long currentTime) {
		return currentTime - startTime;
	}

	public static long getTimeFromStartClock(long startTime, Clockable clock) {
		return getTimeFromStartToCurrent(
				startTime,
				clock.getTime()
		);
	}

	public static boolean checkTtlExpiredTime(long expiredTime, long ttl) {
		return expiredTime < ttl;
	}

	public static boolean checkTtlStartTime(long startTime, Clockable clock, long ttl) {
		return checkTtlExpiredTime(
				getTimeFromStartClock(startTime, clock),
				ttl
		);
	}

}
