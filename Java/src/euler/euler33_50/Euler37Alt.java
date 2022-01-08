//Euler Problem 37Alternative (http://projecteuler.net/problem=35)
// !!alternative Version with Numbers saved as ArrayNumber

/* Problem:	


The number 3797 has an interesting property. Being prime itself, it is possible to continuously remove digits from left to right, 
and remain prime at each stage: 3797, 797, 97, and 7. Similarly we can work from right to left: 3797, 379, 37, and 3.
Find the sum of the only eleven primes that are both truncatable from left to right and right to left.
NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.



*/

/* Lösung (Nr. 48873, 2016/03/30):

	748317

*/


// Erklärung siehe unterhalb des Programms

// 

package euler.euler33_50;

import utils.Prime;
import utils.ArrayNumber;

public class Euler37Alt {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int solution = 0;
		int countSolutions = 0;
		
		ArrayNumber x = new ArrayNumber(11);
		
		// repeat until eleven solutions are found
		while (countSolutions < 11)
		{
			if (isTruncatablePrime(x))
			{
				System.out.println("Truncatable Prime: " + x.getNumberAsInt());
				solution += x.getNumberAsInt();
				countSolutions++;
			}

			x.increase();

		}

		System.out.println("Result:" + solution);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	// check for left-/right-truncatable prime for ArrayNumber
	static boolean isTruncatablePrime(ArrayNumber x)
	{
		for (int i = 0; i <= x.getLead(); i++)
		{
			
		}
		
		return true;
	}
	
	// check, if x is left- and right-truncatable prime
	static boolean isTruncatablePrime(int x)
	{
		int y = x;
		int xLen = 0;
		
		// test if left-truncatable: cut of the last part in every step
		while (y > 0)
		{
			if (!Prime.isPrime(y))
				return false;
			y /= 10;
			// count length of number, in order to test right-truncatable
			xLen++;
		}
		
		y = x;
		
		// test if right-truncatable: cut of first part in ever step (length was found in loop above)
		while(xLen > 1)
		{
			y %= (int)Math.pow(10, xLen-1);
			if (!Prime.isPrime(y))
				return false;
			xLen--;
		}
		return true;
	}
	
	// create next candidate. last cipher must be 3 or 7. 
	// If last part is 7, go 16 ahead, because middle ciphers may only be 1,3,7,9 (5 will be checked needlessly)
	// If first part is not 2,3,5,7 jump to next possible (is 8 or 9, go to next 21...)
	static int createNextCandidate(int x)
	{
	
		// if x has 3 as last cipher, change to 7 (that is, add 4 to number)
		if (x % 10 == 3)
		{
			 x += 4;
		}
		else
		{
			// needed because starting at 23 -> would jump over 37 from 27
			if (x < 50)
				x += 6;
			else
				x += 16;
		}
		
		int ret = x;
		
		// skip all number, that do not have 2,3,5,7 at the front
		int xLen = 0;
		while (x > 10)
		{
			x /= 10;
			xLen++;
		}
		
		switch (x)
		{
		case 1 : 
		case 4 :
		case 6 : ret += (int)Math.pow(10, xLen); break;
		case 0 : ret += 2 * (int)Math.pow(10, xLen); break;
		case 9 : ret += 12 * (int)Math.pow(10, xLen); break;
		case 8 : ret += 13 * (int)Math.pow(10, xLen); break;
		default : break;
		}
		
		return ret;
	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- Um Truncatable Prime zu sein, muss die Zahl hinten 1,3,7,9 sein (ansonsten durch 2 oder 5 teilbar)
		- Da von links runterlaufen wird, muss die letzte Ziffer einzeln prim sein, es bleiben also nur 3 und 7
		- Genauer müssen sogar alle Ziffern ausser der ersten 1,3,7,9 sein, da ansonsten von rechts hochlaufend diese Ziffer hinten steht
		- Vorne kann es 2,3,5,7 sein, da der Rest keine Primzahlen sind
		- Problemstellung erfordert, dass mit zweistelligen Zahlen begonnen werden muss
		- Problemstellung gibt an, dass es nur 11 Truncatable Primes gibt
	2. Brute Force Prinzip:
		a) Laufe nach obigem Prinzip durch die Zahlen und prüfe ob Prim: Beginne mit 23, dann 27, dann 33, dann 37, etc.
		b) Baue Zahlen via: Hinten zwischen 3 und 7 wechseln. Danach zwischen 1,3,7,9 wechseln. Vorne zwischen 2,3,5,7 wechseln.
	3. Optimierungen:
		- Man könnte alle hinteren zwei Kandidaten für mehrstellige Zahlen suchen und nur mit diesen weiterarbeiten  
	4. Alternative mit Array: 
		- Speichere Zahl in Array und teste jeweils die Teilarrays hiervon
		
*/
