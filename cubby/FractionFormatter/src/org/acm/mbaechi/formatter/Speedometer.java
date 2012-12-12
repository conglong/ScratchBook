package org.acm.mbaechi.formatter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.StopWatch;

public class Speedometer {
	private static Logger logger = Logger.getLogger(Speedometer.class
			.getCanonicalName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long count = 1000l;
		for (long n = 1; n < 101; n *= 10) {
			count *= n;
			NumberFormatter formatter = FractionNumberFormatter
					.getFractionFormatter(32);
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			for (long i = 0; i < count; i++) {
				formatter.format(Math.random() * 100d);
			}
			stopWatch.stop();
			logger.log(Level.INFO, "Time taken uncached (" + count + ") "
					+ stopWatch.toString());
		}
		count = 1000l;
		for (long n = 1; n < 101; n *= 10) {
			count *= n;
			NumberFormatter formatter = FractionNumberFormatter
					.getFractionFormatter(32, true);
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			for (long i = 0; i < count; i++) {
				formatter.format(Math.random() * 100d);
			}
			stopWatch.stop();
			logger.log(Level.INFO, "Time taken cached (" + count + ": "
					+ ((FractionNumberFormatter) formatter).getCallHitRatio()
					+ ") " + stopWatch.toString());
		}
	}

}
