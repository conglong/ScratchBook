/*
 * File:   fraction_rounder_test.h
 * Author: martin
 *
 * Created on Dec 18, 2012, 11:32:29 PM
 */

#ifndef FRACTION_ROUNDER_TEST_H
#define	FRACTION_ROUNDER_TEST_H

#include <cppunit/extensions/HelperMacros.h>

class fraction_rounder_test : public CPPUNIT_NS::TestFixture {
    CPPUNIT_TEST_SUITE(fraction_rounder_test);

    CPPUNIT_TEST(round_1_2_down);
    CPPUNIT_TEST(round_1_2);
    CPPUNIT_TEST(round_1_2_up);
    CPPUNIT_TEST(round_1_32_down);
    CPPUNIT_TEST(round_1_32);
    CPPUNIT_TEST(round_1_32_up);

    CPPUNIT_TEST_SUITE_END();

public:
    fraction_rounder_test();
    virtual ~fraction_rounder_test();
    void setUp();
    void tearDown();

private:
    void round_1_2_down();
    void round_1_2();
    void round_1_2_up();
    void round_1_32_down();
    void round_1_32();
    void round_1_32_up();
};

#endif	/* FRACTION_ROUNDER_TEST_H */

