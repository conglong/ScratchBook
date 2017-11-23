using System;
using NUnit.Framework;

namespace FractionFormatter
{
	[TestFixture()]
	public class DoubleFractionRounderTest
	{


		[Test()]
	    public void round_1_2_down() {
	        Assert.AreEqual(1.5d, new DoubleFractionRounder(2).round(1.6d), 1e-10);
	    }

	    [Test()]
	    public void round_1_2() {
	        Assert.AreEqual(1.5d, new DoubleFractionRounder(2).round(1.5d), 1e-10);
	    }

	   [Test()]
	    public void round_1_2_up() {
	        Assert.AreEqual(2.0d, new DoubleFractionRounder(2).round(1.9d), 1e-10);
	    }

	    [Test()]
	    public void round_1_32_down() {
	        Assert.AreEqual(1.28125d, new DoubleFractionRounder(32).round(1.2819d), 1e-10);
	    }

	    [Test()]
	    public void round_1_32() {
	        Assert.AreEqual(1.28125d, new DoubleFractionRounder(32).round(1.28125d), 1e-10);
	    }

	    [Test()]
	    public void round_1_32_up() {
	        Assert.AreEqual(2.0d, new DoubleFractionRounder(32).round(1.99d), 1e-10);
	    }
	}
	
}

