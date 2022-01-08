//Euler Problem 55 (http://projecteuler.net/problem=55 )

/* Problem:	
	Lychrel numbers

If we take 47, reverse and add, 47 + 74 = 121, which is palindromic.

Not all numbers produce palindromes so quickly. For example,

349 + 943 = 1292,
1292 + 2921 = 4213
4213 + 3124 = 7337

That is, 349 took three iterations to arrive at a palindrome.

Although no one has proved it yet, it is thought that some numbers, like 196, never produce a palindrome. 
A number that never forms a palindrome through the reverse and add process is called a Lychrel number. 
Due to the theoretical nature of these numbers, and for the purpose of this problem, 
we shall assume that a number is Lychrel until proven otherwise. 
In addition you are given that for every number below ten-thousand, it will either 
  (i) become a palindrome in less than fifty iterations, or, 
  (ii) no one, with all the computing power that exists, has managed so far to map it to a palindrome. 
In fact, 10677 is the first number to be shown to require over fifty iterations before producing a palindrome: 
4668731596684224866951378664 (53 iterations, 28-digits).

Surprisingly, there are palindromic numbers that are themselves Lychrel numbers; the first example is 4994.

How many Lychrel numbers are there below ten-thousand?



*/

/* Lösung (Nr. 37415, 2016/07/31):

	249

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.util.Arrays;

import utils.NumArr;

public class Euler55 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int limit = 10_000;
		
		int solution = 0;
		
		int[] num;
		int counter;
		
		for (int n = 1; n < limit; n++)
		{
			num = NumArr.toArray(n,true);
//			num = NumArr.longToArray(n);
			
			counter = 0;
			do 
			{
//				num = add(num,createReverse(num));
				num = NumArr.add(num,createReverse(num),true);
//				num = NumArr.add(num,createReverse(num));
				counter++;
			}
			while (counter < 50 && !isPalindrom(num));
				
			if (counter == 50)
			{
				solution++;
//				System.out.println("Nr." + solution + " : " + n);
			}
		}
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static boolean isPalindrom (int[] x) {
		
		for (int i = 0; i < x.length/2; i++)
		{
			if (x[i] != x[x.length-1-i])
				return false;
		}
		return true;
	}
	
	static int[] createReverse (int[] x) {
		int[] res = new int[x.length];
		for (int i = 0; i < x.length; i++)
		{
			res[i] = x[x.length-1-i];
		}
		return res;
	}
	
//	static int[] add(int[] x, int[] y) {
//		if (x.length > y.length)
//			y = Arrays.copyOf(y, x.length);
//		else if (x.length < y.length)
//			x = Arrays.copyOf(x, y.length);
//		
//		int[] res = new int[x.length];
//		
//		for (int i = 0; i < x.length; i++)
//		{
//			res[i]+= x[i] + y[i];
//			if (res[i] > 9)
//			{
//				if (i == x.length-1)
//					res = Arrays.copyOf(res, x.length+1);
//				res[i+1]++;
//				res[i]%=10;
//			}
//		}
//		
//		return res;
//	}
	
}






/* Erklärung:
	1. Grundüberlegungen:
		- Schreibe Zahl als Array und dann einfach Brute Force
	2. Brute Force Prinzip:
		a) simples Brute Force: zaehle die Zahlen hoch und addiere jeweils solange den Kehrwert, bis 50 erreicht wurde oder ein Palindrom
	3. Optimierungen: 
		- 
	4. Problematik:
		- 
		

*/
