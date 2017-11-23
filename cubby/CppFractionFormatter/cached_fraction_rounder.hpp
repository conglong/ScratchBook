/* 
 * File:   cached_frachtion_rounder.hpp
 * Author: martin
 *
 * Created on December 18, 2012, 5:49 PM
 */

#ifndef CACHED_FRACTION_ROUNDER_HPP
#define	CACHED_FRACTION_ROUNDER_HPP
#include <boost/unordered_map.hpp>
#include "fraction_rounder.hpp"

class cached_fraction_rounder : public fraction_rounder {
public:
    cached_fraction_rounder();
    cached_fraction_rounder(int fraction);
    cached_fraction_rounder(const cached_fraction_rounder& orig);
    virtual ~cached_fraction_rounder();
    double get_call_hit_ratio();
    double round(double number);
private:
    double calls;
    double hits;
    boost::unordered_map<double, double> cache;
};

#endif	/* CACHED_FRACTION_ROUNDER_HPP */

