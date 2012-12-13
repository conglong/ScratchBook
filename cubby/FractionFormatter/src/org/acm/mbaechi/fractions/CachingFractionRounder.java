package org.acm.mbaechi.fractions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CachingFractionRounder<T> {

    private static final Logger logger = Logger
            .getLogger(CachingFractionRounder.class.getCanonicalName());
    private static Map<String, String> cache = new ConcurrentHashMap<>();
    private double calls;
    private double hits;
    private final FractionRounder<T> fractionRounder;

    CachingFractionRounder(FractionRounder<T> fractionRounder) {
        this.fractionRounder = fractionRounder;
    }

    public double getCallHitRatio() {
        synchronized (this) {
            logger.log(Level.INFO, "calls={0}, hits={1}", new Object[]{Double.valueOf(calls).intValue(), Double.valueOf(hits).intValue()});
            return hits / (calls == 0 ? 1d : calls);
        }
    }

    public String round(T number) {
        synchronized (this) {
            calls++;
            final String key = number.toString();
            if (cache.containsKey(key)) {
                hits++;
                return cache.get(key);
            } else {
                String val = fractionRounder.round(number).toString();
                cache.put(key, val);
                return val;
            }
        }
    }
}
