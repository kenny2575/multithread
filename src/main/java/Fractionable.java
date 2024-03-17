public interface Fractionable {
	@Mutator
	void setNumerator(int numerator);
	@Mutator
	void setDenominator(int denominator);
	@Cached(ttl = 1000)
	double doubleValue();
}
