package org.acm.mbaechi.fractions;

import static org.junit.Assert.*;
import org.junit.Test;

public class DoubleFractionRounderTest {

    @Test
    public void round_1_2_down() {
        assertEquals(1.5d, new DoubleFractionRounder(2).round(1.6d), 1e-10);
    }

    @Test
    public void round_1_2() {
        assertEquals(1.5d, new DoubleFractionRounder(2).round(1.5d), 1e-10);
    }

    @Test
    public void round_1_2_up() {
        assertEquals(2.0d, new DoubleFractionRounder(2).round(1.9d), 1e-10);
    }

    @Test
    public void round_1_32_down() {
        assertEquals(1.28125d, new DoubleFractionRounder(32).round(1.2819d), 1e-10);
    }

    @Test
    public void round_1_32() {
        assertEquals(1.28125d, new DoubleFractionRounder(32).round(1.28125d), 1e-10);
    }

    @Test
    public void round_1_32_up() {
        assertEquals(2.0d, new DoubleFractionRounder(32).round(1.99d), 1e-10);
    }
}
