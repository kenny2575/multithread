import lombok.Setter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

	static class Fraction implements Fractionable {

		@Setter
		private int numerator;

		@Setter
		private int denominator;

		public int callCount = 0;

		public Fraction(int numerator, int denominator) {

			this.numerator = numerator;

			this.denominator = denominator;

		}

		@Override
		public double doubleValue() {

			synchronized (this) {
				callCount++;
			}

			return (double) this.numerator / this.denominator;

		}

	}

	private static class Clock implements Clockable {

		long time;

		public Clock() {
			this.time = System.currentTimeMillis();
		}

		@Override
		public long getTime() {
			return this.time;
		}

	}

	@Test
	public void cachedDoubleValueTest() {

		Fraction fraction = new Fraction(4, 7);

		Fractionable cachedFraction = CacheUtils.cache(fraction, new Clock());

		Assertions.assertEquals(fraction.doubleValue(), cachedFraction.doubleValue());

	}

	@Test
	public void callCountSimpleTest() {

		Fraction fraction = new Fraction(4, 7);

		Fractionable cachedFraction = CacheUtils.cache(fraction, new Clock());

		cachedFraction.doubleValue();

		cachedFraction.doubleValue();

		Assertions.assertEquals(1, fraction.callCount);

	}

	@Test
	public void callCountExpiredCacheTest() {

		Fraction fraction = new Fraction(4, 7);

		Clock clock = new Clock();

		Fractionable cachedFraction = CacheUtils.cache(fraction, clock);

		cachedFraction.doubleValue();

		clock.time += 1200L;

		cachedFraction.doubleValue();

		Assertions.assertEquals(2, fraction.callCount);

	}

	@Test
	public void callCountResetCacheTest() {

		Fraction fraction = new Fraction(4, 7);

		Fractionable cachedFraction = CacheUtils.cache(fraction, new Clock());

		cachedFraction.doubleValue();

		cachedFraction.doubleValue();

		cachedFraction.setNumerator(1);

		cachedFraction.doubleValue();

		cachedFraction.doubleValue();

		Assertions.assertEquals(2, fraction.callCount);

	}

}