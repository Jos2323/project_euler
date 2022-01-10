# -*- coding: utf-8 -*-

# Euler Problem 66 (http://projecteuler.net/problem=66 )

""" Problem:	
	Diophantine equation

Consider quadratic Diophantine equations of the form:

x^2 – Dy^2 = 1

For example, when D=13, the minimal solution in x is 649^2 - 13×180^2 = 1.

It can be assumed that there are no solutions in positive integers when D is square.

By finding minimal solutions in x for D = {2, 3, 5, 6, 7}, we obtain the following:

3^2 - 2×2^2 = 1
2^2 - 3×1^2 = 1
9^2 - 5×4^2 = 1
5^2 - 6×2^2 = 1
8^2 - 7×3^2 = 1

Hence, by considering minimal solutions in x for D <= 7, the largest x is obtained when D=5.

Find the value of D <= 1000 in minimal solutions of x for which the largest value of x is obtained.

"""

#  Solution (Nr. , ): (difficulty rating 25%)

# note: brute force does not work here. for correct solution using covergents of continuous fraction, see euler66.py


# For considerations, see below program

from math import sqrt
import time

strategy = "c"

min_sols = {}

start = time.time()



if strategy == "a":

    # create list of x^2 - 1 values
    # cut off for now at arbitrary value
    max_x = 1_00_000_000
    x_squares = [x * x - 1 for x in range(2, max_x)]

    max_D = 1001
    for D in range(max_D):

        if sqrt(D).is_integer():
            continue

        has_found = False
        for i, x_s in enumerate(x_squares):

            if (x_s % D != 0):
                continue

            y_s = x_s / D
        
            if sqrt(y_s).is_integer():
                min_sols[D] = i + 2
                has_found = True
                break
        
        if not has_found:
            print("No solution for D = {}".format(D))

        
    largest_min_sol_D = max(min_sols, key=min_sols.get)
    largest_min_sol_x = min_sols[largest_min_sol_D]

if strategy == "b":

    # create list of x^2 values
    # cut off for now at arbitrary value
    max_squares = 1_000_000_000
    squares = [x * x for x in range(1, max_squares)]

    max_D = 1001
    for D in range(max_D):

        # skip if D is squared
        if sqrt(D).is_integer():
            continue

        has_found = False
        for y_s in squares:

            x_s = D * y_s + 1

            x = sqrt(x_s) 
            if x.is_integer():
                min_sols[D] = x
                has_found = True
                break

        if not has_found:
            print("No solution for D = {}".format(D))
    
    largest_min_sol_D = max(min_sols, key=min_sols.get)
    largest_min_sol_x = min_sols[largest_min_sol_D]


if strategy == "c":

    D_max = 1000 + 1
    D = [d for d in range(1,D_max) if not sqrt(d).is_integer()]

    min_sols = {}

    x = 1
    while(True):
        
        x_s = x * x - 1

        remove_from_D = []
        for d in D:
            
            if (x_s % d != 0):
                continue

            y_s = x_s / d
        
            if sqrt(y_s).is_integer():
                remove_from_D.append(d)
                min_sols[d] = x
                break
        
        D = [d for d in D if d not in remove_from_D]

        if len(D) == 1:
            break

        if len(D) == 0:
            print("Multiple D with maximum min solution")
            print(remove_from_D)
            break

        x += 1

        if x > 10_000_000_000:
            break




end = time.time()

print(end - start)

print(D[0])

# print("solution : " + str(largest_min_sol_D))
# print("x : " + str(largest_min_sol_x))
print(min_sols)







""" Considerations:

1. Basic: 
	- (x^2 - 1) / D = y^2
    - x^2 = D * y^2 + 1
    - if x has same divisor as D or y (say: x = ab, D = ac, with a > 1), then x^2 - D*y^2 = 1 <=> a * (a*b^2 - c*y^2) = 1  (analog if y = ac)
        -> this cannot be solved for integers
        -> so: for certain D and y, can choose x to be from different prime factors
2. Brute Force Principle: 
	a) Increase x and see, if (x^2 - 1) / D is square
	b) Option two: go the opposite way:
        for each D, run up the multiples of D (but only for the squares, that is 1, 4, 9, ...), add 1, check if square
    c) Approach the other way round:
        - Run up x, but each time try all D. 
        - When value is found, that D does need no further test.
        - Keep going until last D is standing
3. Optimizations: 
    a)
	    - If x^2 - 1 cannot be divided by D, x can be skipped
            -> for even D can skip even x
        - Better: skip all x that have same prime factor as D
        - Do not calculate x^2 - 1 every time, but build list of all values and just run through those
4. Problematics: 
	a)
        - x is getting too large too quickly
            -> especially 61 is a problem
            -> need a way to skip over values


"""