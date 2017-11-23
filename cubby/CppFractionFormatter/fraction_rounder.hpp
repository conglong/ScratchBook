/* 
 * File:   fraction_rounder.hpp
 * Author: martin
 *
 * Created on December 18, 2012, 5:41 PM
 */

#ifndef FRACTION_ROUNDER_HPP
#define	FRACTION_ROUNDER_HPP

class fraction_rounder {
public:
    fraction_rounder();
    fraction_rounder(int fraction);
    fraction_rounder(const fraction_rounder& orig);
    virtual ~fraction_rounder();
    virtual double round(double number);
private:
    double divisor;
    double round_up(double number);
};

#endif	/* FRACTION_ROUNDER_HPP */

