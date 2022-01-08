//Euler Problem 64 (http://projecteuler.net/problem=64 )

/* Problem:	
	Odd period square roots

All square roots are periodic when written as continued fractions and can be written in the form:
sqrt(N) = a0 + 1 / (a1 + 1 / (a2 + 1 / (a3 + ...    )

For example, let us consider sqrt(23):
sqrt(23) = 4 + sqrt(23) - 4 = 4 + 1 / (1 / ( sqrt(23)—4 ) = 4 + (1 / (1 + (sqrt(23) - 3) / 7 ) )

If we continue we would get the following expansion:
sqrt(23) = 4 + 1 / (1 + 1 / (3 + 1 / (1 + 1 / ( 8 + ...    )

The process can be summarised as follows:
a0 = 4, 1 / (sqrt(23) - 4) = 1 * (sqrt(23) + 4) /  7 = 1 + (sqrt(23) - 3) / 7
a1 = 1,	7 / (sqrt(23) - 3) = 7 * (sqrt(23) + 3) / 14 = 3 + (sqrt(23) - 3) / 2
a2 = 3, 2 / (sqrt(23) - 3) = 2 * (sqrt(23) + 3) / 14 = 1 + (sqrt(23) - 4) / 7
a3 = 1,	7 / (sqrt(23) - 4) = 7 * (sqrt(23) + 4) /  7 = 8 + (sqrt(23) - 4) / 1
a4 = 8, 1 / (sqrt(23) - 4) = 1 * (sqrt(23) + 4) /  7 = 1 + (sqrt(23) - 3) / 7
a5 = 1,	7 / (sqrt(23) - 3) = 7 * (sqrt(23) + 3) / 14 = 3 + (sqrt(23) - 3) / 2
a6 = 3, 2 / (sqrt(23) - 3) = 2 * (sqrt(23) + 3) / 14 = 1 + (sqrt(23) - 4) / 7
a7 = 1,	7 / (sqrt(23) - 4) = 7 * (sqrt(23) + 4) /  7 = 8 + (sqrt(23) - 4) / 1

It can be seen that the sequence is repeating. 
For conciseness, we use the notation sqrt(23) = [4;(1,3,1,8)], to indicate that the block (1,3,1,8) repeats indefinitely.

The first ten continued fraction representations of (irrational) square roots are:

sqrt(2)=[1;(2)], period=1
sqrt(3)=[1;(1,2)], period=2
sqrt(5)=[2;(4)], period=1
sqrt(6)=[2;(2,4)], period=2
sqrt(7)=[2;(1,1,1,4)], period=4
sqrt(8)=[2;(1,4)], period=2
sqrt(10)=[3;(6)], period=1
sqrt(11)=[3;(3,6)], period=2
sqrt(12)= [3;(2,6)], period=2
sqrt(13)=[3;(1,1,1,1,6)], period=5

Exactly four continued fractions, for N <= 13, have an odd period.

How many continued fractions for N <= 10000 have an odd period?





*/

/* Lösung (Nr. 17475, 2018/03/11): (difficulty rating 20%)

	1322

*/

// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Euler64 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();

		int solution = 0;

		for (int val = 1; val <= 10000; val++) {

			int b = (int) Math.sqrt(val);
			
			// skip square roots
			if (b*b == val) {
				continue;
			}
			
			// initial settings
			int a0 = b;
			int v0 = 1;
			int w0 = a0;

			int contFracLen = 0;
			Set<List<Integer>> contFracs = new HashSet<>();
			
			// do loop (break at 100 for now
			for (int i = 0; i < 500; i++) {
				int v1 = (val - w0 * w0) / v0;
				int a1 = (b + w0) / v1;
				int w1 = a1 * v1 - w0;

				a0 = a1;
				v0 = v1;
				w0 = w1;

				List<Integer> contFrac = new ArrayList<>();
				contFrac.add(a1);
				contFrac.add(v1);
				contFrac.add(w1);
				
				// if set of a1, v1, w1 already exists, stop
				if (!contFracs.add(contFrac)) {
					contFracLen = i;
					break;
				}
			}

			if (contFracLen % 2 == 1) {
				solution++;
			}
			if (contFracLen == 0) {
				System.out.println("continued fraction not found for val = " + val);
				break;
			}
		}

		System.out.println("Result: " + solution);

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");
	}

}

/*
 Erklärung: 
 	1. Grundüberlegungen: 
		- die erste Zahl ist die groesste ganze Zahl hinter dem Bruch 
 		- diese hat dann das Aussehen x = a0 + 1 / 1 / (x - a0) 
 		- danach wird 1 / x - a0 = (x + a0) / (x^2 - a0^2) = a1 + (x + b1) / c1 = a1 + 1 / 1/ ((x + b1) / c1) 
 		- danach wird dann mit 1 / ((x + b1) / c1) = c1 / (x + b1) weitergemacht: 
 		- also: c1 / (x + b1) = c1 * (x - b1) / (x^2 - b1^2) = a2 + (x + b2) / c2 
 	2. Brute Force Prinzip: 
 		a) einfach durchrechnen 
 	3. Optimierungen: 
 		- 
 	4. Problematik: 
 		-
 
  */
