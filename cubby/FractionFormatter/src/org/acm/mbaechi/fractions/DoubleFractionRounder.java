package org.acm.mbaechi.fractions;

public class DoubleFractionRounder extends AbstractFractionRounder<Double> {

    DoubleFractionRounder(int fraction) {
        super(fraction);
    }

    @Override
    public Double round(Double number) {
        return Math.round(number / divisor) * divisor;
    }
}
