public class Clock implements Clockable {
	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}
}
