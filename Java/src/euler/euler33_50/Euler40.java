//Euler Problem 40 (http://projecteuler.net/problem=40)

/* Problem:	
	Champernowne's constant

An irrational decimal fraction is created by concatenating the positive integers:
0.123456789101112131415161718192021...
It can be seen that the 12th digit of the fractional part is 1.
If dn represents the nth digit of the fractional part, find the value of the following expression.
d1 × d10 × d100 × d1000 × d10000 × d100000 × d1000000



*/

/* Lösung (Nr. 53843, 2016/04/24):

	210

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import java.util.Arrays;

public class Euler40 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();

		int solution = 1;
		
		for (int i = 1; i <= 1_000_000; i*=10)
			solution *= getDigitAtPosition(i);

		System.out.println("Result: " + solution);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	

	}
	
	static int getDigitAtPosition(int pos)
	{
		int maxPos = 7;
		
		// start positions of 10^Index
		int[] startPos = createStartPositions(maxPos);
		
		int ciphers = Arrays.binarySearch(startPos, pos);
		if (ciphers < 0)
			ciphers = -1 * ciphers - 1;
		else
			ciphers = ciphers + 1;
		
		int startPosition = startPos[ciphers-1];
		
		int numberVal = (pos - startPosition) / ciphers;
		int numberRest = (pos - startPosition) % ciphers;
		
		int digit = String.valueOf((int)Math.pow(10, ciphers-1) + numberVal).charAt(numberRest) - '0';
		
		return digit;
	}
	
	static int[] createStartPositions(int len)
	{
		// start positions of 10^Index
		int[] startPos = new int[len];
		startPos[0]=1;
		for (int i = 1; i < startPos.length; i++)
		{
			startPos[i] = startPos[i-1] + i * 9 * (int)Math.pow(10, i-1);  
		}
		
		return startPos;
	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- Einstellige Zahlen beginnen an der ersten Stelle, danach:
		   - 2stellig: 10
		   - 3stellig: 190
		   - etc., jeweils Stellenzahl * 9 * 10^(Stellenzahl - 1)
		- Fuer die Ziffer an der Stelle: Gehe zur naechstkleineren Startposition, Teile durch Laenge, gehe Rest weiter
	2. Brute Force Prinzip:
		a) 
	3. Optimierungen:
		- 
		
*/
