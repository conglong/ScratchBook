package org.acm.mbaechi.fractions;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.time.StopWatch;

public class Speedometer {

    private static final Logger logger = Logger.getLogger(Speedometer.class
            .getCanonicalName());
    private static final int max = 1000;

    /**
     * @param args
     */
    public static void main(String[] args) {
        double numbers[] = new double[max];
        for (int n = 0; n < max; n++) {
            numbers[n] = Double.valueOf(String.format("%.9f", Math.random() * 100d));
        }
        formatters(numbers);
        rounders(numbers);
    }

    static void formatters(final double numbers[]) {
        int count = 10000;
        NumberFormatter formatter = FractionNumberFormatter
                .getFractionFormatter(32);
        for (int n = 1; n < 101; n *= 10) {
            count *= n;
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (int i = 0; i < count; i++) {
                formatter.format(numbers[i % max]);
            }
            stopWatch.stop();
            logger.log(Level.INFO, "Time taken uncached ({0}) {1}", new Object[]{count, stopWatch.toString()});
        }
        count = 10000;
        formatter = FractionNumberFormatter
                .getFractionFormatter(32, true);
        for (int n = 1; n < 101; n *= 10) {
            count *= n;
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (int i = 0; i < count; i++) {
                formatter.format(numbers[i % max]);
            }
            stopWatch.stop();
            logger.log(Level.INFO, "Time taken cached ({0}: {1}) {2}", new Object[]{count, ((FractionNumberFormatter) formatter).getCallHitRatio(), stopWatch.toString()});
        }
    }

    static void rounders(final double numbers[]) {
        int count = 10000;
        DoubleFractionRounder dRounder = new DoubleFractionRounder(32);
        for (int n = 1; n < 101; n *= 10) {
            count *= n;
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (int i = 0; i < count; i++) {
                dRounder.round(numbers[i % max]);
            }
            stopWatch.stop();
            logger.log(Level.INFO, "Rounder, time taken uncached ({0}) {1}", new Object[]{count, stopWatch.toString()});
        }
        count = 10000;
        CachingFractionRounder<Double> cRounder = new CachingFractionRounder<>(
                new DoubleFractionRounder(32));
        for (int n = 1; n < 101; n *= 10) {
            count *= n;
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for (int i = 0; i < count; i++) {
                cRounder.round(numbers[i % max]);
            }
            stopWatch.stop();
            logger.log(Level.INFO, "Rounder, time taken cached ({0}: {1}) {2}", new Object[]{count, cRounder.getCallHitRatio(), stopWatch.toString()});
        }
    }
}
