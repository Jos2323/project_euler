//Euler Problem 50 (http://projecteuler.net/problem=50)

/* Problem:	
	Consecutive prime sum

The prime 41, can be written as the sum of six consecutive primes:
41 = 2 + 3 + 5 + 7 + 11 + 13
This is the longest sum of consecutive primes that adds to a prime below one-hundred.
The longest sum of consecutive primes below one-thousand that adds to a prime, contains 21 terms, and is equal to 953.
Which prime, below one-million, can be written as the sum of the most consecutive primes?



*/

/* Lösung (Nr. 41400, 2016/05/27):
 * Btw: Level2 erreicht als Nr. 37131

	997651

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import java.util.ArrayList;
import java.util.List;

import utils.Prime;

public class Euler50 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int solution = 0;
		int len = 0;
		
		int[] primes = Prime.getPrimeArray(50000);
		
		// sums has in the i-Element the sum of the first (i+1) prime numbers
		List<Integer> sums = new ArrayList<Integer>();
		int primeSum = 0;
		for (int i = 0; i < primes.length; i++)
		{
			primeSum += primes[i];
			sums.add(primeSum);
			if (primeSum < 1000000 && Prime.isPrime(primeSum))
			{
				len = i;
				solution = primeSum;
//				System.out.println("Candidate: " + primeSum + " Start: 2 " + "End: " + primes[i] + " Length: " + len);
			}
			int tempSum;
			for (int j = 0; j < i - len; j++)
			{
				tempSum = primeSum - sums.get(j);
				if(tempSum < 1000000 && Prime.isPrime(tempSum))
				{
					len = i-j;
					solution = tempSum;
//					System.out.println("Candidate: " + tempSum + " Start: " + primes[j+1]  + " End: " + primes[i] + " Length: " + len);
				}
			}

		}
		
		System.out.println("Result: " + solution + ", Length: " + len);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	
}






/* Erklärung:
	1. Grundüberlegungen:
		- es existiert schon eine Summe mit 21 Gliedern. Dann kann die Summe insgesamt nicht groesser als 50000 sein
	2. Brute Force Prinzip:
		a) Summiere m Primzahlen ab 2 (mind. 21)
		b) Subtrahiere die ersten n Primzahlen, soweit bis m-n groesser als bisher laengste Summe
		c) gehe wieder weiter mit Summe ab 2
	3. Optimierungen: 
		- 
		

*/
