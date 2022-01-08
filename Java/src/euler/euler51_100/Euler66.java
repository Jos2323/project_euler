//Euler Problem 66 (http://projecteuler.net/problem=66 )

/* Problem:	
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


*/

/* Lösung (Nr. , ): (difficulty rating 25%)

	

*/

// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.util.ArrayList;
import java.util.List;

public class Euler66 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();

		int solution = 0;
		
		int max = 1_000_000;
		
		int D = 2;
		while (D <= 100) {
			if (!isSquare(D)) {
				boolean found = false;
				for (int x = 2; x < max; x++) {
					if((x*x-1)%D == 0 && isSquare((x*x-1)/D)) {
						System.out.println("D: " + D + " x: " + x);
						if (solution < x)
							solution = x;
						found = true;
						break;
					}
				}
				if (!found)
					System.out.println("Overflow at " + D);
			}
			D++;
		}
		
		System.out.println("Result: " + solution);

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");
	}
	
	static boolean isSquare (int x) {
		int s = (int)Math.sqrt(x);
		if (s*s == x) 
			return true;
		else 
			return false;
	}
	
}

/*
Erklärung: 
	1. Grundüberlegungen: 
		- 
	2. Brute Force Prinzip: 
		a) Laufe (x^2 - 1)/D hoch
		b) schaue, wann dies eine Quadratzahl ist 
	3. Optimierungen: 
		- 
	4. Problematik: 
		- x wird sehr schnell sehr gross (> 1000000 fuer D=73, 89)

 */
