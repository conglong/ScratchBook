using System;

namespace FractionFormatter
{
	public class DoubleFractionRounder : AbstractFractionRounder<Double> {

	    public DoubleFractionRounder(int fraction) : base(fraction) {
	    }

	    override public Double round(Double number) {
	        return Math.Round(number / divisor) * divisor;
	    }
	}
}


