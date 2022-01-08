//Euler Problem 57 (http://projecteuler.net/problem=57 )

/* Problem:	
	Square root convergents

It is possible to show that the square root of two can be expressed as an infinite continued fraction.

sqrt (2) = 1 + 1/(2 + 1/(2 + 1/(2 + ... ))) = 1.414213...

By expanding this for the first four iterations, we get:

1 + 1/2 = 3/2 = 1.5
1 + 1/(2 + 1/2) = 7/5 = 1.4
1 + 1/(2 + 1/(2 + 1/2)) = 17/12 = 1.41666...
1 + 1/(2 + 1/(2 + 1/(2 + 1/2))) = 41/29 = 1.41379...

The next three expansions are 99/70, 239/169, and 577/408, but the eighth expansion, 1393/985, 
is the first example where the number of digits in the numerator exceeds the number of digits in the denominator.

In the first one-thousand expansions, how many fractions contain a numerator with more digits than denominator?



*/

/* Lösung (Nr. 28956, 2016/10/23):

	153

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import utils.ArrayNum;

public class Euler57 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int solution = 0;
		
		ArrayNum a = new ArrayNum(5);
		ArrayNum b = new ArrayNum(2);
		
		for (int i = 3; i <= 1000; i++)  // starting at the third expansion
		{
			ArrayNum temp = new ArrayNum(a);
			a.add(a).add(b);
			b.setArrayNum(temp);
			
			if (a.add(b,false).getLead() > a.getLead())
				solution++;		
		}
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- von innen nach aussen laufen: ( ... ( (5/2)^(-1) + 2)^(-1) + ... ) ^(-1) + 1
		- dann: es beginnt mit a_0/b_0 = 5/2. -> val_0 = (a_0+b_0)/a_0
		        -> a_1 = b_0 + 2*a_0, b_1 = a_0 -> a_1 = 2 + 2*5 = 12, b_1 = 5 -> 12/5 -> val_1 = 12+5/12 = 17/12
		        also: val_i = (a_i + b_i) / a_i mit a_i = 2*a_(i-1) + b_(i-1) , b_i = a_(i-1) und a_0 = 5 , b_0 = 2
	2. Brute Force Prinzip:
		a) laufe durch alle durch und zaehle Ziffernlaenge
	3. Optimierungen: 
		- 
	4. Problematik:
		- 
		

*/
