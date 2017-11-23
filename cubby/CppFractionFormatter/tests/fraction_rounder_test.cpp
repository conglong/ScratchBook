/*
 * File:   fraction_rounder_test.cpp
 * Author: martin
 *
 * Created on Dec 18, 2012, 11:32:29 PM
 */

#include "fraction_rounder_test.h"
#include "../fraction_rounder.hpp"

CPPUNIT_TEST_SUITE_REGISTRATION(fraction_rounder_test);

fraction_rounder_test::fraction_rounder_test() {
}

fraction_rounder_test::~fraction_rounder_test() {
}

void fraction_rounder_test::setUp() {
}

void fraction_rounder_test::tearDown() {
}

void fraction_rounder_test::round_1_2_down() {
    fraction_rounder rounder(2);
    CPPUNIT_ASSERT_DOUBLES_EQUAL(1.5d, rounder.round(1.6d), 1e-10);
}

void fraction_rounder_test::round_1_2() {
    fraction_rounder rounder(2);
    CPPUNIT_ASSERT_DOUBLES_EQUAL(1.5d, rounder.round(1.5d), 1e-10);
}

void fraction_rounder_test::round_1_2_up() {
    fraction_rounder rounder(2);
    CPPUNIT_ASSERT_DOUBLES_EQUAL(2.0d, rounder.round(1.9d), 1e-10);
}

void fraction_rounder_test::round_1_32_down() {
    fraction_rounder rounder(32);
    CPPUNIT_ASSERT_DOUBLES_EQUAL(1.28125d, rounder.round(1.2819d), 1e-10);
}

void fraction_rounder_test::round_1_32() {
    fraction_rounder rounder(32);
    CPPUNIT_ASSERT_DOUBLES_EQUAL(1.28125d, rounder.round(1.28125d), 1e-10);
}

void fraction_rounder_test::round_1_32_up() {
    fraction_rounder rounder(32);
    CPPUNIT_ASSERT_DOUBLES_EQUAL(2.0d, rounder.round(1.99d), 1e-10);
}
