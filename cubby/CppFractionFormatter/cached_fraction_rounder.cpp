/* 
 * File:   cached_fraction_rounder.cpp
 * Author: martin
 * 
 * Created on December 18, 2012, 5:49 PM
 */

#include "cached_fraction_rounder.hpp"

cached_fraction_rounder::cached_fraction_rounder() {
}

cached_fraction_rounder::cached_fraction_rounder(int fraction) : fraction_rounder::fraction_rounder(fraction) {
}

cached_fraction_rounder::cached_fraction_rounder(const cached_fraction_rounder& orig) : fraction_rounder::fraction_rounder(orig) {
}

cached_fraction_rounder::~cached_fraction_rounder() {
}

double cached_fraction_rounder::get_call_hit_ratio() {
    return hits / (calls == 0 ? 1.0 : calls);
}

double cached_fraction_rounder::round(double number) {
    boost::unordered_map<double,double>::iterator it = cache.find(number);
    if (it != cache.end()) {
        hits++;
        return (*it).second;
    } else {
        double val = fraction_rounder::round(number);
        cache.insert(std::make_pair(number, val));
        return val;
    }
}