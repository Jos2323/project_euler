//Euler Problem 52 (http://projecteuler.net/problem=52)

/* Problem:	
	Permuted multiples

It can be seen that the number, 125874, and its double, 251748, 
contain exactly the same digits, but in a different order.
Find the smallest positive integer, x, such that 2x, 3x, 4x, 5x, and 6x, contain the same digits.



*/

/* Lösung (Nr. 45246, 2016/06/26):

	142857

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import utils.Permutation;

public class Euler52 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		long solution = 1;
		int len = 1;
		
		outerLoop:
		while (solution < 1_000_000)
		{
			while (6*solution < Math.pow(10, len+1))
			{
				for (int i = 2; i <= 6; i++)
				{
					if (!Permutation.isRealPermutation(solution, i*solution))
						break;
					if (i == 6)
					{
						break outerLoop;
					}
				}
				solution++;
			}
			
			len++;
			solution = (long)Math.pow(10, len);
		}
		
				
		
		System.out.println("Result: " + solution);	
		for (int i = 2; i <= 6; i++)
			System.out.println(i + "*" + solution + ": " + i*solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
}






/* Erklärung:
	1. Grundüberlegungen:
		-  damit die selben Ziffern enthalten sind, darf 6*x nicht laenger werden
			-> x ist kleiner als 10^k/6
	2. Brute Force Prinzip:
		a) 
	3. Optimierungen: 
		- 
		

*/
