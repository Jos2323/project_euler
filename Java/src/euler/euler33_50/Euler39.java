//Euler Problem 39 (http://projecteuler.net/problem=39)

/* Problem:	
	Integer right triangles

If p is the perimeter of a right angle triangle with integral length sides, 
{a,b,c}, there are exactly three solutions for p = 120.
{20,48,52}, {24,45,51}, {30,40,50}
For which value of p <= 1000, is the number of solutions maximised?



*/

/* Lösung (Nr. 48573, 2016/04/24):

	840

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import java.util.Arrays;

public class Euler39 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		// first value is p, second value is number of solutions
		int[] solution = {0,0};
		
		for (int p = 2; p <=1000; p+=2)
		{
			int solNum = solutionNumber(p);
			if (solNum > solution[1])
			{
				solution[0] = p;
				solution[1] = solNum;
//				System.out.println("New Max: " + solution[0] + " Number of Solutions: " + solution[1]);	
			}		
		}

		System.out.println("Result: " + solution[0] + " Number of Solutions: " + solution[1]);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static int solutionNumber(int p)
	{
		int sol = 0;
		
		for (int c = p/3; c <= p/2; c++)
		{
			for (int a = c-1; a >= (p-c) / 2; a--)
			{
				int b = p - c - a;
				if ( (c*c) == (a*a)+(b*b) )
					sol++;
			}
		}
		
		return sol;
	}
		
}






/* Erklärung:
    0. Definition:
       Ohne Einschraenkung seien a,b die Katheten und c die Hypotenuse
	1. Grundüberlegungen:
		- In rechtwinkligen Dreiecken gilt Pythagoras, also a^2 + b^2 = c^2
		- c kann nicht groesser als p/2 sein, da ansonsten a+b < c und damit gilt der Pythagoras nicht mehr
		- c muss groesser als max(a,b) sein, da es sonst nicht mehr die Hypotenuse ist
		   -> c ist mindestens p/3
	2. Brute Force Prinzip:
		a) Laufe p durch bis 1000 
		b) Laufe c durch von p/3 bis p/2
		c) Laufe a hoch von 1 bis (p-c)/2 (mehr muss nicht getestet werden, da mann sonst die selben Loesungen erhaelt)
		c) Teste, ob es a, b gibt mit a+b = p-c und a^2+b^2=c^2 
	3. Optimierungen:
		- Wenn p gerade ist, so gilt:
		  - ist c gerade, so sind a und b beide gerade oder ungerade
		    -> dann ist c^2 gerade, und a^2 und b^2 sind beide gerade oder ungerade -> ok
		  - ist c ungerade, so ist a gerade und b ungerade oder andersherum
		    -> dann ist c^2 ungerade, a^2 gerade und b^2 ungerade oder andersherum -> ok 
	    - Wenn p ungerade ist, so gilt:
	      - ist c gerade, so ist a gerade und b ungerade oder andersherum
		    -> dann ist c^2 gerade, a^2 gerade und b^2 ungerade oder andersherum -> a^2 + b^2 ist ungerade -> NG 
	      - ist c ungerade, so sind a und b beide gerade oder ungerade
		    -> dann ist c^2 ungerade, und a^2 und b^2 sind beide gerade oder ungerade -> a^2 + b^2 ist gerade -> NG 
		-> ungerade p werden uebersprungen
		
*/
