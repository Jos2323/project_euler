//Euler Problem 49 (http://projecteuler.net/problem=49)

/* Problem:	
	Prime permutations

The arithmetic sequence, 1487, 4817, 8147, in which each of the terms increases by 3330, is unusual in two ways: 
(i) each of the three terms are prime, and, 
(ii) each of the 4-digit numbers are permutations of one another.
There are no arithmetic sequences made up of three 1-, 2-, or 3-digit primes, exhibiting this property, 
but there is one other 4-digit increasing sequence.
What 12-digit number do you form by concatenating the three terms in this sequence?



*/

/* Lösung (Nr. 38581, 2016/05/27):

	296962999629

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import utils.NumArr;
import utils.Prime;

public class Euler49 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int[] primes = Prime.getPrimeArray(1000, 10000);
		
		outerLoop:
		for (int i = 0; i < primes.length; i++)
		{
			for (int j = i; j < primes.length; j++)
			{
				if (isRealPermutation(primes[i],primes[j]))
				{
					for (int k = j; k < primes.length; k++)
					{
						if (isRealPermutation(primes[i],primes[k]))
						{
							if (primes[j] - primes[i] == primes[k] - primes[j])
							{
								System.out.println("Result: " + primes[i] + ", " + primes[j] + ", " + primes[k]);
								if (primes[i] > 1487)
									break outerLoop;
							}
						}
					}
				}
			}
		}

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	// check for Permutation (ignore leading zeros) (false for x == y)
	static boolean isRealPermutation (int x, int y)
	{
		if (x == y)
			return false;
		
		int[] xx = NumArr.toArray(x);
		int[] yy = NumArr.toArray(y);
		
		if (xx.length != yy.length)
			return false;
		
		boolean[] used = new boolean[xx.length];
		
		for (int i = 0; i < xx.length; i++)
		{
			for (int j = 0; j < yy.length; j++)
			{
				if (!used[j] && xx[i] == yy[j])
				{
					used[j] = true;
					break;
				}
				else 
				{
					if (j == yy.length-1)
						return false;
				}
			}
		}
		
		for (int i = 0; i < used.length; i++)
			if (!used[i])
				return false;
		
		return true;
	}
}






/* Erklärung:
	1. Grundüberlegungen:
		- 
	2. Brute Force Prinzip:
		a) Durchlaufe die Primzahlen von 1000 bis 10000
		b) Permutiere und schaue, ob (mind.) zwei Permutationen ebenfalls enthalten sind.
		c) Schaue, ob die Differenzen von den groesseren zwei und den kleineren zwei gleich sind
	3. Optimierungen: 
		- 
		

*/
