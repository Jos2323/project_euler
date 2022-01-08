//Euler Problem 58 (http://projecteuler.net/problem=58 )

/* Problem:	
	Spiral primes

Starting with 1 and spiralling anticlockwise in the following way, a square spiral with side length 7 is formed.

37 36 35 34 33 32 31
38 17 16 15 14 13 30
39 18  5  4  3 12 29
40 19  6  1  2 11 28
41 20  7  8  9 10 27
42 21 22 23 24 25 26
43 44 45 46 47 48 49

It is interesting to note that the odd squares lie along the bottom right diagonal, but what is more interesting is 
that 8 out of the 13 numbers lying along both diagonals are prime; that is, a ratio of 8/13 ~ 62%.

If one complete new layer is wrapped around the spiral above, a square spiral with side length 9 will be formed. 
If this process is continued, what is the side length of the square spiral for which the ratio of primes 
along both diagonals first falls below 10%?



*/

/* Lösung (Nr. 28337, 2016/11/19):

	26241

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import utils.Prime;

public class Euler58 {

	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		int len = 1;
		long val = 1;
		int primeCounter = 0;
		
		do {
			len+=2;
			for (int i = 0; i < 4; i++) {
				val+=(len - 1);
				if (Prime.isPrime(val))
					primeCounter++;
			}
		} while (primeCounter * 10 > (2*len - 1));
		
		System.out.println("Result: " + len);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- von der rechten unteren Ecke der Diagonale bei Seitelaenge n kommt man zu den naechsten 4, indem man viermal n-1 addiert
		- die Anzahl der Diagonalzahlen ist fuer Seitelaenge n : 2*n-1
	2. Brute Force Prinzip:
		a) laufe jeweils vier weiter, pruefe ob prim, zaehle hoch und berechne Anteil
	3. Optimierungen: 
		- 
	4. Problematik:
		- 
		

*/
