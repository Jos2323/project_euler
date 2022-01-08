//Euler Problem 41 (http://projecteuler.net/problem=41)

/* Problem:	
	Pandigital prime

We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once. 
For example, 2143 is a 4-digit pandigital and is also prime.

What is the largest n-digit pandigital prime that exists?



*/

/* Lösung (Nr. 45288, 2016/04/24):

	7652413

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import utils.Pandigital;
import utils.Prime;

public class Euler41 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();

		int solution = 7654323;
		
		while (!Pandigital.isPandigital(solution) || !Prime.isPrime(solution))
		{ 
			solution -= 2;
			
			int skip = solution % 1_000_000 / 100_000;
			
			switch (skip)
			{
				case 0 : solution -= 100_000;
				case 9 : solution -= 100_000;
				case 8 : solution -= 100_000;
					break;
				default :
			}
			
			if (solution < 1234567)
				break;
		}

		System.out.println("Result: " + solution);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	

	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- 9-pandigital-prime besteht aus den Ziffern 1-9. Die Summer der Ziffern ist 45, also durch 3 teilbar -> nicht prime
		- Die Quersumme von 8-pandigital ist 36, also durch 3 teilbar -> nicht prime
		- Die Quersumme von 7-pandigital ist 28, also nicht durch 3 teilbar -> ggf. prime
		- Die Quersumme von 6-pandigital ist 21, also durch 3 teilbar -> nicht prime
	2. Brute Force Prinzip:
		a) teste alle 7-stelligen Primzahlen von oben runter, ob sie pandigital sind
	3. Optimierungen:
		- es kann bei 7654321 gestartet werden
		- wenn eine 9 gefunden wird, kann gleich uebersprungen werden.
		
*/
