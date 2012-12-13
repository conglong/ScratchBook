package org.acm.mbaechi.formatter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FractionNumberFormatter implements NumberFormatter {
	private static Logger logger = Logger
			.getLogger(FractionNumberFormatter.class.getCanonicalName());
	private final int fraction;
	private final double divisor;
	private final int significantDecimals;
	private final boolean doCache;
	private double calls;
	private double hits;
	private static Map<String, String> formatLookup = new ConcurrentHashMap<String, String>(
			10000);

	public double getCallHitRatio() {
		logger.log(Level.INFO, "calls=" + calls + ", hits=" + hits);
		return hits / (calls == 0 ? 1d : calls);
	}

	private FractionNumberFormatter(int fraction, boolean doCache) {
		this.fraction = fraction;
		this.divisor = 1d / fraction;
		int countScaling = 1;
		double scalar = divisor * 10d;
		while (scalar - Double.valueOf(scalar).intValue() > 1e-10) {
			scalar *= 10d;
			countScaling++;
		}
		significantDecimals = countScaling;

		this.doCache = doCache;
	}

	public static NumberFormatter getFractionFormatter(int fraction,
			boolean doCache) {
		return new FractionNumberFormatter(fraction, doCache);
	}

	public static NumberFormatter getFractionFormatter(int fraction) {
		return new FractionNumberFormatter(fraction, false);
	}

	private static String numberToKey(int significantDecimals, Double number) {
		return number.toString();
//		return String.format("%." + significantDecimals + "f", number);
	}

	public String format(BigDecimal number) {
		calls++;
		if (doCache && formatLookup.containsKey(number.toString())) {
			hits++;
			return formatLookup.get(number.toString());
		} else if (doCache) {
			String val = internalFormat(number).toString();
			formatLookup.put(number.toString(), val);
			return val;
		}
		return internalFormat(number).toString();
	}

	public String format(Double number) {
		calls++;
		if (doCache) {
			final String key = numberToKey(significantDecimals, number);
			if (formatLookup.containsKey(key)) {
				hits++;
				return formatLookup.get(number.toString());
			} else {
				String val = internalFormat(number).toString();
				formatLookup.put(key, val);
				return val;
			}
		}
		return internalFormat(number).toString();
	}

	private Double internalFormat(Double number) {
		double result = Math.round(number / divisor) * divisor;
		logger.log(Level.FINEST, "Formatted " + number + " in 1/" + fraction
				+ " results in " + result);
		return result;
	}

	private BigDecimal internalFormat(BigDecimal number) {
		BigDecimal result = number.divide(BigDecimal.valueOf(divisor))
				.setScale(0, BigDecimal.ROUND_HALF_UP)
				.multiply(BigDecimal.valueOf(divisor));
		logger.log(Level.FINEST, "Formatted " + number + " in 1/" + fraction
				+ " results in " + result);
		return result;
	}
}
