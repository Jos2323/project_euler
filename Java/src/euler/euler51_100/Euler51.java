//Euler Problem 51 (http://projecteuler.net/problem=51)

/* Problem:	
	Prime digit replacements

By replacing the 1st digit of the 2-digit number *3, it turns out 
that six of the nine possible values: 13, 23, 43, 53, 73, and 83, are all prime.
By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit number 
is the first example having seven primes among the ten generated numbers, 
yielding the family: 56003, 56113, 56333, 56443, 56663, 56773, and 56993. 
Consequently 56003, being the first member of this family, is the smallest prime with this property.
Find the smallest prime which, by replacing part of the number (not necessarily adjacent digits) 
with the same digit, is part of an eight prime value family.



*/

/* Lösung (Nr. 21737, 2016/06/25):

	121313

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import utils.NumArr;
import utils.Prime;

public class Euler51 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		long upperLimit = 1_000_000;
		
		long solution = 0;
		int count = 0;
		
		long[] primes = Prime.getPrimeArray(1000, upperLimit);
		
		outerLoop:
		for (int i = 0; i < primes.length; i++)
		{
			int[] x = NumArr.toArray(primes[i]);
			
			// for now, only test with 3 repeats. include 6, when setting limit higher	
			for (int j = 0; j <= 2; j++)
			{
				if (getDigitCounts(x)[j] == 3)
				{
					count = countPrimesInReplacements(x,j,3);
					if (count >= 8)
					{
						solution = primes[i];
						break outerLoop;
					}
				}
			}
		}
		
		
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static int[] getDigitCounts(int[] x) {

		int[] counter = new int[10];
		for (int i = 0; i < x.length; i++)
		{
			counter[x[i]]++;
		}
		
		return counter;
	}
	
	static int countPrimesInReplacements(int[] x, int startDigit, int repeats) {
		
		// stop, if last digit is to be replaced, as maximum 5 will appear
		if (x[x.length-1] == startDigit)
			return 0;
		
		int[] positions = new int[repeats];
		int posInd = 0;
		for (int i = 0; i < x.length-1; i++ )
			if(x[i] == startDigit)
				positions[posInd++] = i;

		int counter = 1;
		
		for (int digit = startDigit; digit < 9; digit++)
		{
			for (int i = 0; i < repeats; i++)
			{
				x[positions[i]]++;
			}
			if (Prime.isPrime(NumArr.arrayToLong(x, false)))
				counter++;
		}	
		return counter;
	}
	
	
}






/* Erklärung:
	1. Grundüberlegungen:
		- die letzte Ziffer kann nicht ersetzbar sein, da sonst die geraden Ziffernersetzungen nicht prim werden
		- 8 von 10 Zahlen sollen prim sein
			-> die zu ersetzende Anzahl Ziffern muss 3 oder sechs sein, da ansonsten jede dritte durch 3 teilbar ist und es somit max. 7 geben kann 
	2. Brute Force Prinzip:
		a) durchlaufe die Primzahlen von unten nach oben
		b) stoppe, sobald bei einer Zahl genau drei 0, 1 oder 2 stehen
		c) pruefe, ob acht der Zahlen auch prim sind  
	3. Optimierungen: 
		- 
		

*/
