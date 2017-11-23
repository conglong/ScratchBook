using System;

namespace FractionFormatter
{
	public abstract class AbstractFractionRounder<T> : FractionRounder<T> {

	    protected readonly double divisor;

	    protected AbstractFractionRounder(int fraction) {
	        this.divisor = 1d / fraction;
	    }

		public abstract T round(T number);
	}
}

