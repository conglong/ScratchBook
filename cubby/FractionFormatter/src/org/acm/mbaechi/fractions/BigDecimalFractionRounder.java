package org.acm.mbaechi.fractions;

import java.math.BigDecimal;

public class BigDecimalFractionRounder extends AbstractFractionRounder<BigDecimal> {

    BigDecimalFractionRounder(int fraction) {
        super(fraction);
    }

    @Override
    public BigDecimal round(BigDecimal number) {
        return number.divide(BigDecimal.valueOf(divisor))
                .setScale(0, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(divisor));
    }
}
