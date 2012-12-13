package org.acm.mbaechi.fractions;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class FractionNumberFormatterTest {

	@Test
	public void bigDecimal_1_2_down() {		
		assertEquals("1.5", FractionNumberFormatter.getFractionFormatter(2).format(BigDecimal.valueOf(1.6d)));
	}
	
	@Test
	public void double_1_2_down() {		
		assertEquals("1.5", FractionNumberFormatter.getFractionFormatter(2).format(1.6d));
	}
	@Test
	public void bigDecimal_1_2() {		
		assertEquals("1.5", FractionNumberFormatter.getFractionFormatter(2).format(BigDecimal.valueOf(1.5d)));
	}
	
	@Test
	public void double_1_2() {		
		assertEquals("1.5", FractionNumberFormatter.getFractionFormatter(2).format(1.5d));
	}
	@Test
	public void bigDecimal_1_2_up() {		
		assertEquals("2.0", FractionNumberFormatter.getFractionFormatter(2).format(BigDecimal.valueOf(1.9d)));
	}
	
	@Test
	public void double_1_2_up() {		
		assertEquals("2.0", FractionNumberFormatter.getFractionFormatter(2).format(1.9d));
	}

	@Test
	public void bigDecimal_1_32_down() {		
		assertEquals("1.28125", FractionNumberFormatter.getFractionFormatter(32).format(BigDecimal.valueOf(1.2819d)));
	}
	
	@Test
	public void double_1_32_down() {		
		assertEquals("1.28125", FractionNumberFormatter.getFractionFormatter(32).format(1.2819d));
	}
	@Test
	public void bigDecimal_1_32() {		
		assertEquals("1.28125", FractionNumberFormatter.getFractionFormatter(32).format(BigDecimal.valueOf(1.28125d)));
	}
	
	@Test
	public void double_1_32() {		
		assertEquals("1.28125", FractionNumberFormatter.getFractionFormatter(32).format(1.28125d));
	}
	@Test
	public void bigDecimal_1_32_up() {		
		assertEquals("2.00000", FractionNumberFormatter.getFractionFormatter(32).format(BigDecimal.valueOf(1.99d)));
	}
	
	@Test
	public void double_1_32_up() {		
		assertEquals("2.0", FractionNumberFormatter.getFractionFormatter(32).format(1.99d));
	}
}
