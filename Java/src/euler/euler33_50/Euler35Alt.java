//Euler Problem 35Alternative (http://projecteuler.net/problem=35)
// !!alternative Version without ArrayNumber.class!!

/* Problem:	


The number, 197, is called a circular prime because all rotations of the digits: 197, 971, and 719, are themselves prime.
There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.
How many circular primes are there below one million?



*/

/* Lösung (Nr. 55995):

	55

*/


// Erklärung siehe unterhalb des Programms

// 

package euler.euler33_50;

import utils.Prime;

import java.util.Arrays;
import java.lang.Math;

public class Euler35Alt {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int solution = 4;
		
		int num = 11;
		int[] ar = new int[] {1,1,0,0,0,0};
//		ArrayNumber num = new ArrayNumber(11,6);
		
		jump:
		while (num < 1_000_000)
		{	
			int lead = getLead(ar);
//			System.out.println("start: " + num + " " + lead);
			for (int i = 0; i <= lead; i++)
			{
//				System.out.println("values: " + num.get(i) + " " + (num.get(i) % 2) );
				if ( (ar[i] % 2 == 0) || (ar[i] == 5) )
				{
					num += 2;
					add2(ar);
//					System.out.println("jump");
					continue jump;
				}
			}
//			System.out.println(num.getNumberAsInt());
			if (num % 3 != 0)
			{
//				System.out.println("rein fuer " + num);
				int[] sol;
				if ((sol = checkCircle(ar))[0] > 0)
				{
					solution += sol[0];
					for (int i = 1; i < sol.length; i++)
					{
						if (sol[i] > -1)
							System.out.println("found: " + sol[i]);
					}
					System.out.println("Found until now: " + solution);
				}		
			}
			
			num += 2;
			add2(ar);
			
		
		}
		
		
		System.out.println("Solution: " + solution);
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");

		
	}
	
	
	static void add2(int[] arr)
	{
		arr[0] += 2;
		for (int i = 0; i < 6; i++)
		{
			if( arr[i] >= 10 )
			{
				arr[i] %= 10;
				if (i+1 < 6)
				arr[i+1]++;
			}
		}
	}

	
//	static boolean primTest (ArrayNumber x)
//	{
//		int p = x.getNumberAsInt();
	static boolean primTest (int p)
	{	
		for (int i = 7; i < p/2; i +=2 )
		{
			if ( (p % i) == 0 )
				return false;
		}
		return true;
	}
	
	// give back 0, if the Number has been already checked (-> if a smaller circle permutation exists)
	// if not give number of circle permutations
	static int[] checkCircle(int[] y)
	{



		int lead = getLead(y);
		
		int[] res = new int[lead+2];
		
		int[] x = Arrays.copyOf(y,6);


		// give back false, if there is a digit smaller than largest digit
		int c = x[lead];
		for (int i = 0; i < lead; i++)
		{
			if (x[i] < c)
			{
				res[0] = 0;
				return res;
			}
		}

		int[] circle = createCircle(x);
		
		
		for (int i = 0; i <= lead; i++)
		{
			if ((res[i+1] = circle[i]) == -1)
			{
				res[0] = i;
				return res;
			}
//			if (!primTest(circle[i]))
			if (!Prime.isPrime(circle[i]))
			{
				res[0] = 0;
				return res;
			}
		}
//		res = x.getLead()+1;
		res[0] = lead+1;
		return res;
		
//		int checkVal = x.getNumberAsInt();
//		
//		for (int i = 1; i <= x.getLead(); i++)
//		{
//			// circle permutation
//			for (int j = x.getLead(); j > 0; j--)
//			{
//				x.set(j,x.get(j-1));
//			}
//			x.set(0,c);
//			c = x.get(x.getLead());
//			
//			// if smaller value than permutation exists, break
//			if (x.getNumberAsInt() < checkVal)
//				return 0;
//
//			// count numbers of permutations -> if there is an equal permutation, break
//			if (checkVal == x.getNumberAsInt())
//			{
//				return i;
//			}
//
//			
//		}
//		
//		return x.getLead()+1;

	}
	
	static int getNum(int[] x)
	{
		int res = 0;
		for (int i = 0; i < 6; i++)
			res += x[i]*Math.pow(10,i);
		return res;
	}
	
	static int getLead(int[] x)
	{
		int res = 0;
		for (int i = 0; i < 6; i++)
			if (x[i] != 0)
				res = i;
		return res;
	}
	
	// input: ArrayNumber. ouput: all permutations. 
	// if permutation circle exists -> first output element -1
	// if repetition occurs: set -1 at this element
	static int[] createCircle(int[] y)
	{

		int[] x = Arrays.copyOf(y,6);
		int lead = getLead(y);
		int[] res = new int[lead+1];
		
		res[0] = getNum(x);
		
		
		for (int i = 1; i <= lead; i++)
		{
			int c = x[lead];
			// circle permutation
			for (int j = lead; j > 0; j--)
			{
				x[j] = x[j-1];
			}
			x[0] = c;
			
			res[i] = getNum(x);
			
			// if smaller value than permutation exists, break
			if (res[i] < res[0])
			{
				res[0] = -1;
				return res;
			}

			// count numbers of permutations -> if there is an equal permutation, break
			if (res[0] == res[i])
			{
				res[i] = -1;
				return res;
			}

		}
		return res;
		
	}

}






/* Erklärung:
	1. Grundüberlegungen:
	   Es geht um Zahlen, bei denen alle Permutationen im Kreis (dh. Reihenfolge bleibt gleich) Primzahlen sind.
	   Damit können die Ziffern 0,2,4,5,6,8 nicht vorkommen, da diese dann einmal hinten stehen und damit nicht prim sein können.
	   -> Die Zahlen bauen sich aus den Ziffern 1,3,7,9 auf
	2. Brute Force Prinzip:
	   a) Zähle alle Zahlen hoch, die sich aus obigen Ziffern aufbauen lassen. 
	   b) Überspringe, wenn die Zahl bereits als Permutation im Kreis geprüft wurden
	   c) Prüfe, ob Primzahl
	3. Bzgl. Permutation im Kreis:
	   Wenn man die Zahlen von unten aus prüft, müsste es klappen. dafür gilt
	    a) Jede Kreispermutation hat ein kleinstes Elemente
	    b) Wenn man von unten nach oben durchläuft, so gilt: gibt es eine Kreispermutation, die kleiner ist, so wurde dies schon getestet.
	    c) Man muss also die Permutationen durchlaufen und prüfen, ob es eine kleinere gibt.
	    d) Verkürzung: wenn es eine Ziffer gibt, die kleiner ist als die erste, so wurde schon getestet
	4. Bzgl. Primzahlprüfung:
	   Das wurde bereits in einem früheren Euler-Problem gefordert...

*/
