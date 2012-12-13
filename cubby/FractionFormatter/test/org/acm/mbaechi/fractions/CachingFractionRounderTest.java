package org.acm.mbaechi.fractions;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class CachingFractionRounderTest {
	@Test
	public void bigDecimal_1_2_down() {
		CachingFractionRounder<BigDecimal> rounder = new CachingFractionRounder<BigDecimal>(
				new BigDecimalFractionRounder(2));
		assertEquals("1.5", rounder.round(BigDecimal.valueOf(1.6d)));
	}

	@Test
	public void double_1_2_down() {
		CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
				new DoubleFractionRounder(2));
		assertEquals("1.5", rounder.round(1.6d));
	}

	@Test
	public void bigDecimal_1_2() {
		CachingFractionRounder<BigDecimal> rounder = new CachingFractionRounder<BigDecimal>(
				new BigDecimalFractionRounder(2));
		assertEquals("1.5", rounder.round(BigDecimal.valueOf(1.5d)));
	}

	@Test
	public void double_1_2() {
		CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
				new DoubleFractionRounder(2));
		assertEquals("1.5", rounder.round(1.5d));
	}

	@Test
	public void bigDecimal_1_2_up() {
		CachingFractionRounder<BigDecimal> rounder = new CachingFractionRounder<BigDecimal>(
				new BigDecimalFractionRounder(2));
		assertEquals("2.0", rounder.round(BigDecimal.valueOf(1.9d)));
	}

	@Test
	public void double_1_2_up() {
		CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
				new DoubleFractionRounder(2));
		assertEquals("2.0", rounder.round(1.9d));
	}

	@Test
	public void bigDecimal_1_32_down() {
		CachingFractionRounder<BigDecimal> rounder = new CachingFractionRounder<BigDecimal>(
				new BigDecimalFractionRounder(32));
		assertEquals("1.28125", rounder.round(BigDecimal.valueOf(1.2819d)));
	}

	@Test
	public void double_1_32_down() {
		CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
				new DoubleFractionRounder(32));
		assertEquals("1.28125", rounder.round(1.2819d));
	}

	@Test
	public void bigDecimal_1_32() {
		CachingFractionRounder<BigDecimal> rounder = new CachingFractionRounder<BigDecimal>(
				new BigDecimalFractionRounder(32));
		assertEquals("1.28125", rounder.round(BigDecimal.valueOf(1.28125d)));
	}

	@Test
	public void double_1_32() {
		CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
				new DoubleFractionRounder(32));
		assertEquals("1.28125", rounder.round(1.28125d));
	}

	@Test
	public void bigDecimal_1_32_up() {
		CachingFractionRounder<BigDecimal> rounder = new CachingFractionRounder<BigDecimal>(
				new BigDecimalFractionRounder(32));
		assertEquals("2.00000", rounder.round(BigDecimal.valueOf(1.99d)));
	}

	@Test
	public void double_1_32_up() {
		CachingFractionRounder<Double> rounder = new CachingFractionRounder<Double>(
				new DoubleFractionRounder(32));
		assertEquals("2.00000", rounder.round(1.99d));
	}

}
