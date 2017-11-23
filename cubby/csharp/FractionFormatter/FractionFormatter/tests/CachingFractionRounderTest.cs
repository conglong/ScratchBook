using System;
using NUnit.Framework;

namespace FractionFormatter
{
	[TestFixture()]
	public class CachingFractionRounderTest
	{
	    [Test()]
	    public void double_1_2_down() {
	        CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
	                new DoubleFractionRounder(2));
	        Assert.AreEqual("1.5", rounder.round(1.6d));
	    }

	    [Test()]
	    public void double_1_2() {
	        CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
	                new DoubleFractionRounder(2));
	        Assert.AreEqual("1.5", rounder.round(1.5d));
	    }

	    [Test()]
	    public void double_1_2_up() {
	        CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
	                new DoubleFractionRounder(2));
	        Assert.AreEqual("2", rounder.round(1.9d));
	    }

	    [Test()]
	    public void double_1_32_down() {
	        CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
	                new DoubleFractionRounder(32));
	        Assert.AreEqual("1.28125", rounder.round(1.2819d));
	    }

	    [Test()]
	    public void double_1_32() {
	        CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
	                new DoubleFractionRounder(32));
	        Assert.AreEqual("1.28125", rounder.round(1.28125d));
	    }

	    [Test()]
	    public void double_1_32_up() {
	        CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
	                new DoubleFractionRounder(32));
	        Assert.AreEqual("2", rounder.round(1.99d));
	    }
	}
}

