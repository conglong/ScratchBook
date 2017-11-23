/* 
 * File:   main.cpp
 * Author: martin
 *
 * Created on December 18, 2012, 4:28 PM
 */

#include "fraction_rounder.hpp"
#include "cached_fraction_rounder.hpp"

#include <boost/random/mersenne_twister.hpp>
#include <boost/random/uniform_real_distribution.hpp>

#include <boost/chrono/chrono.hpp>
#include <boost/type_traits.hpp>
#include <iostream>


int main(int argc, char **argv) {
    const int max = 1000;
    boost::random::mt19937 generator;
    boost::random::uniform_real_distribution<double> distribution(0.0, 100.0);
    double * numbers = new double[max];

    for (int i = 0; i < max; i++) {
        numbers[i] = distribution(generator);
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
    
    count = 10000;
    cached_fraction_rounder c_rounder(32);
    for (int n = 1; n < 101; n *= 10) {
        count *= 10;
        boost::chrono::steady_clock::time_point start = boost::chrono::steady_clock::now();
        for (int i = 0; i < count; i++) {
            c_rounder.round(numbers[i % max]);
        }
        boost::chrono::duration<double> sec = boost::chrono::steady_clock::now() - start;
        std::cout << "Rounder, time taken cached (" << count << ") " << sec.count() << " seconds" << std::endl;
    }

    delete [] numbers;
    return 0;
}
