//Euler Problem 47 (http://projecteuler.net/problem=47)

/* Problem:	
	Distinct primes factors

The first two consecutive numbers to have two distinct prime factors are:
14 = 2 × 7
15 = 3 × 5
The first three consecutive numbers to have three distinct prime factors are:
644 = 2² × 7 × 23
645 = 3 × 5 × 43
646 = 2 × 17 × 19.
Find the first four consecutive integers to have four distinct prime factors. What is the first of these numbers?



*/

/* Lösung (Nr. 38409, 2016/05/12):

	134043

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import utils.Prime;

public class Euler47 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		long solution = 5;
		
		long[] primes = new long[175];
		primes[0] = 2;
		
		// create prime list to speed lookup
		for (int i = 1; i < primes.length; i++)
		{
			primes[i] = Prime.getNextPrime(primes[i-1]);
		}
		
		int counter;
		
		int len = 4;
		
		full:
		while (solution < 1_000_000)
		{
			counter = 0;
			
			while (hasDistinctPrimeFactorsLookup(solution,len,primes))
			{
				counter++;
				
				if (counter >= len)
					break full;
				solution--;
			}

			solution += len;
			
		}
		
		System.out.println("Result: " + solution);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static boolean hasDistinctPrimeFactorsLookup(long x, int num, long[] primes)
	{
		int counter = 0;
		
		for (int i = 0; i < primes.length; i++)
		{
			if (x < 2 * primes[i])
				return false;
			
			if (x % primes[i] == 0)
				counter++;
			
			if (counter >= num)
				return true;
		}
		
		return false;
		
	}
	
	static boolean hasDistinctPrimeFactors(long x, int num)
	{
		long factor = 2;
		int counter = 0;
		
		while (factor <= x)
		{
			if (x % factor == 0)
				counter++;
			
			if (counter >= num)
				return true;
			
			factor = Prime.getNextPrime(factor);
		}
		
		return false;
		
	}
		
}






/* Erklärung:
	1. Grundüberlegungen:
		- Wenn eine Zahl nicht moeglich ist, so gehe vier weiter und test und gehe ggf. rueckwaerts
	2. Brute Force Prinzip:
		a) Durchlaufe die Zahlen und teste, ob vier Primfaktoren
		b) Wenn nein, gehe 4 weiter
		c) Wenn ja, teste jeweils die davor. 
		   -> wenn dabei einmal nein, dann gehe von dieser vier weiter
	3. Optimierungen: Obiges Brute Force ist VIEL zu langsam
		- Berechne zunaechst die ersten 1000 Primzahlen und loope dann ueber diese Liste
		- Nicht gemacht: Man koennte auch noch machen, dass man nicht vier weiter geht und wieder alle testet, 
		  sondern dass man sich merkt, ob Teile schon getestet wurden

*/
