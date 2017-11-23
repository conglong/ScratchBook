using System;
using System.Collections.Generic;

namespace FractionFormatter
{
	public class CachingFractionRounder<T> {

	    private static Dictionary<String, String> cache = new Dictionary<String, String>();
	    private double calls;
	    private double hits;
	    private readonly FractionRounder<T> fractionRounder;

	    public CachingFractionRounder(FractionRounder<T> fractionRounder) {
	        this.fractionRounder = fractionRounder;
	    }

	    public double getCallHitRatio() {
	        lock (cache) {
	            return hits / (calls == 0 ? 1d : calls);
	        }
	    }

	    public String round(T number) {
	        lock (cache) {
	            calls++;
	            String key = number.ToString();
	            if (cache.ContainsKey(key)) {
	                hits++;
	                return cache[key];
	            } else {
	                String val = fractionRounder.round(number).ToString();
	                cache[key] = val;
	                return val;
	            }
	        }
	    }
	}
}


