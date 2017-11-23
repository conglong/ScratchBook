/*
 * untitled.cxx
 * 
 * Copyright 2012 Martin Baechi <martin@eno>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */

#include <boost/random/mersenne_twister.hpp>
#include <boost/random/uniform_real_distribution.hpp>

#include <boost/chrono/chrono.hpp>
#include <boost/type_traits.hpp>
#include <iostream>
#include <cmath>


class fraction_rounder {
private:
    double divisor;
    
	double round_up(double number) {
		return floor(number + 0.5);
	}
public:
    fraction_rounder(int fraction) {
        divisor = 1.0 / fraction;
    }
    ~fraction_rounder() {}
    double round(double number) {
        return round_up(number / divisor) * divisor;
	}	
};

int main(int argc, char **argv)
{
	const int max=1000;
	boost::random::mt19937 generator;
	boost::random::uniform_real_distribution<double> distribution(0.0,100.0);
	double * numbers = new double[max];

	for (int i=0; i<max; i++) {
		numbers[i]=distribution(generator);
	}

	int count = 10000;
	fraction_rounder rounder(32);
	for (int n = 1; n < 101; n *= 10) {
		count *= 10;
		boost::chrono::steady_clock::time_point start = boost::chrono::steady_clock::now();
		for (int i = 0; i < count; i++) {
			rounder.round(numbers[i % max]);
		}
		boost::chrono::duration<double> sec = boost::chrono::steady_clock::now() - start;
		std::cout << "Rounder, time taken uncached (" << count << ") " << sec.count() << " seconds" << std::endl;
	}

	delete [] numbers;
	return 0;
}
