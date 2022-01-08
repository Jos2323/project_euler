//Euler Problem 46 (http://projecteuler.net/problem=46)

/* Problem:	
	Goldbach's other conjecture

It was proposed by Christian Goldbach that every odd composite number can be written as the sum of a prime and twice a square.
9 = 7 + 2×12
15 = 7 + 2×22
21 = 3 + 2×32
25 = 7 + 2×32
27 = 19 + 2×22
33 = 31 + 2×12
It turns out that the conjecture was false.
What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?



*/

/* Lösung (Nr. 40433, 2016/05/12):

	5777

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import utils.Prime;

public class Euler46 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();

		int solution = 9;
		
		while(!isGoldbach(solution))
		{
			solution+=2;
			while (Prime.isPrime(solution))
				solution+=4;
		}
		
		System.out.println("Result: " + solution);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static boolean isGoldbach(int x)
	{

		for (int i = 1; i * i < x; i++)
		{
			if (Prime.isPrime(x-2*i*i))
				return false;
		}
		
		return true;
	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- Composite Numbers sind nichtprime Zahlen. 
		- Gesucht ist die kleinste ungerade nichtprime Zahl, die nicht Summe einer Primzahl plus das Doppelte einer Quadratzahl ist
		- wenn x prim ist, dann ist die nachfolgende ungerade Zahl
	2. Brute Force Prinzip:
		a) Laufe die ungeraden Zahlen hoch
		b) Ueberspringe Primzahlen und die direkten Nachfolger von Primzahlen
		c) Laufe die Quadratzahlen hoch, subtrahiere und schaue ob Rest eine Primzahl ist
	3. Optimierungen:
		- 

*/
