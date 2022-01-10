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

""" Solution (Nr. 19862, 2022/01/10): (difficulty rating 25%)

    662

"""


# For considerations, see below program

from math import sqrt
from math import modf
import time

min_sols = {}

start = time.time()

def is_pell_solution(x, y, D):
    if (x == 1 and y == 0):
        return False
    return x * x - D * y * y == 1

D_max = 1000
for D in range(D_max + 1):

    # skip for square roots (as cannot be solved)
    if (sqrt(D).is_integer()):
        continue

    d_sqrt = sqrt(D)
  
    # a_i is the continuous fraction value. starting with the largest integer below sqrt(D)
    a = int(modf(d_sqrt)[1])

    # b_i always has the form D - ... and will be the part next to the sqrt(D) - b_i
    # start with same value as a_i
    b = a

    # c_i will be the part above the sqrt(D) - b_i
    c = 1

    # see Euler64 problem (http://projecteuler.net/problem=64)
    
    # p and q are the dividend and divisor of the convergent and will become x and y in the Pell Equation
    # p_b and q_b are the values one step before (has two-step recursion rule)
    p_b = 1
    q_b = 0
    p = a
    q = 1

    counter = 0
    while(not is_pell_solution(p, q, D)):
        
        # save the values from one step before
        a_tmp = a
        b_tmp = b
        c_tmp = c

        c = (D - b_tmp * b_tmp) // c_tmp
        a = int(modf((d_sqrt + b_tmp) / c)[1])
        b = a * c - b_tmp

        p_tmp = a * p + p_b
        p_b = p
        p = p_tmp

        q_tmp = a * q + q_b
        q_b = q
        q = q_tmp

        # if D == 61:
        #     print(counter)
        #     print("p = {}".format(p))
        #     print("q = {}".format(q))
        #     print(type(a_1))
        #     print(type(b_1))
        #     print(type(c_1))

        if counter > 1000:
            print("break for D = {}".format(D))
            print(p)
            print(q)
            break
        
        counter += 1

    min_sols[D] = p

largest_min_sol_D = max(min_sols, key=min_sols.get)
largest_min_sol_x = min_sols[largest_min_sol_D]


end = time.time()

print(end - start)

print("solution : " + str(largest_min_sol_D))
print("x : " + str(largest_min_sol_x))
# print(min_sols)


""" Considerations:

1. Basic: 
	- This is the Pell Equation
2. Brute Force Principle: 
	- this problem is impossible by Brute Force
    - for incorrect tries, see euler66_bruteforce.py
3. Solution without Brute Force:
    - One can show, that the fundamental solution for the Pell Equation will be a covvergents of continued fractions for sqrt(D)
        https://crypto.stanford.edu/pbc/notes/contfrac/pell.html
    - That leads to the algorithm:
        1. run through the covergents of of continued fractions for sqrt(D)
        2. check if the dividend and divisor are solutions for the Pell Equation
    - The convergents can be recursively calculated by the algorithm from Euler 64, and then recursive calculation of the p and q
        https://crypto.stanford.edu/pbc/notes/contfrac/definition.html
        
"""