package org.acm.mbaechi.fractions;

public abstract class AbstractFractionRounder<T> implements FractionRounder<T> {
protected final double divisor;
	
	AbstractFractionRounder(int fraction) {
		this.divisor = 1d / fraction;		
	}
}
