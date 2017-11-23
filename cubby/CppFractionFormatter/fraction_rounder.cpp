/* 
 * File:   fraction_rounder.cpp
 * Author: martin
 * 
 * Created on December 18, 2012, 5:41 PM
 */

#include "fraction_rounder.hpp"
#include <cmath>

fraction_rounder::fraction_rounder() {
    divisor = 1.0 / 4;
}

fraction_rounder::fraction_rounder(const fraction_rounder& orig) {
    divisor = orig.divisor;
}

fraction_rounder::fraction_rounder(int fraction) {
    divisor = 1.0 / fraction;
}

fraction_rounder::~fraction_rounder() {
}

double fraction_rounder::round(double number) {
    return round_up(number / divisor) * divisor;
}

double fraction_rounder::round_up(double number) {
    return floor(number + 0.5);
}
