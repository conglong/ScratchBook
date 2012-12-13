package org.acm.mbaechi.fractions;

import java.math.BigDecimal;
import static org.junit.Assert.*;
import org.junit.Test;

public class BigDecimalFractionRounderTest {

    @Test
    public void round_1_2_down() {
        assertEquals(BigDecimal.valueOf(1.5d), new BigDecimalFractionRounder(2).round(BigDecimal.valueOf(1.6d)));
    }

    @Test
    public void round_1_2() {
        assertEquals(BigDecimal.valueOf(1.5d), new BigDecimalFractionRounder(2).round(BigDecimal.valueOf(1.5d)));
    }

    @Test
    public void round_1_2_up() {
        assertEquals(BigDecimal.valueOf(2.0d), new BigDecimalFractionRounder(2).round(BigDecimal.valueOf(1.9d)));
    }

    @Test
    public void round_1_32_down() {
        assertEquals(BigDecimal.valueOf(1.28125d), new BigDecimalFractionRounder(32).round(BigDecimal.valueOf(1.2819d)));
    }

    @Test
    public void round_1_32() {
        assertEquals(BigDecimal.valueOf(1.28125d), new BigDecimalFractionRounder(32).round(BigDecimal.valueOf(1.28125d)));
    }

    @Test
    public void round_1_32_up() {
        assertEquals(new BigDecimal("2.00000"), new BigDecimalFractionRounder(32).round(BigDecimal.valueOf(1.99d)));
    }
}
