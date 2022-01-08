//Euler Problem 33 (http://projecteuler.net/problem=33)

/* Problem:	
	Digit cancelling fractions

The fraction 49/98 is a curious fraction, as an inexperienced mathematician in attempting to simplify it 
may incorrectly believe that 49/98 = 4/8, which is correct, is obtained by cancelling the 9s.
We shall consider fractions like, 30/50 = 3/5, to be trivial examples.
There are exactly four non-trivial examples of this type of fraction, less than one in value, 
and containing two digits in the numerator and denominator.
If the product of these four fractions is given in its lowest common terms, find the value of the denominator.



*/

/* L�sung (Nr. 46633, 2016/01/31):

	100

*/


// Erkl�rung siehe unterhalb des Programms

// Programm wird ohne Eingabem�glichkeit f�r die Zahlen gemacht.

package euler.euler33_50;

import utils.Fract;

public class Euler33 {

	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		int a, b, c;
		int[][] res = new int[4][2];
		
		int counter = 0;
		
		for (b = 2; b <= 9; b++)
		{
			for (a = 1; a < b; a++)
			{
				for (c = 1; c <= 9; c++)
				{
					if ( (c < a) || (c > b) )
					{
						if ( (10*b*(a-c)) == (a*(b-c))  )
						{
							res[counter][0] = 10*c+a;
							res[counter][1] = 10*b+c;
							System.out.println("Found Fraction: " + res[counter][0] + " / " + res[counter][1] + " = " + a + " / " + b);
							counter++;
						}
						else if ( 10*a*(b-c) == b*(a-c) )
						{
							res[counter][0] = 10*a+c;
							res[counter][1] = 10*c+b;
							System.out.println("Found Fraction: " + res[counter][0] + " / " + res[counter][1] + " = " + a + " / " + b);
							counter++;
						}
					}
				}
				
			}
		}
		
		int numer = 1, denom = 1;
		for (int i = 0; i < 4; i++)
		{
			numer *= res[i][0];
			denom *= res[i][1];
		}
		int solution = Fract.reduce(numer,denom)[1];
		
		System.out.println("Solution: " + numer + " " + denom + " " + solution);
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");

	}

}






/* Erkl�rung:
	1. Die Verk�rzung nimmt jeweils einmal die selbe Ziffer aus Z�hler und Nenner.
	   Damit haben die Br�che also folgende Form: 
	   F�r a / b, mit a,b aus {1,...,9} und a < b, und c aus {1,...,9}, all jene a,b,c f�r die gilt:
	    a) (a*10+c) / (b*10+c) = a / b  -> dieser Fall kann nicht eintreten, siehe 4.a)
	    b) (c*10+a) / (b*10+c) = a / b  -> hierbei muss a < b < c oder c < a < b, siehe 4.c)
	    c) (a*10+c) / (c*10+b) = a / b  -> hierbei muss a < b < c oder c < a < b, siehe 4.d)
	    d) (c*10+a) / (c*10+b) = a / b  -> dieser Fall kann nicht eintreten, siehe 4.b)
	    
	2. Folgende Einschr�nkungen k�nnen gemacht werden:
	    a) a != 9 (wegen a < b) und b != 1
	    b) Wegen a < b gilt allgemein, dass a und b eingeschr�nkt sind 
	    c) c kann nicht 0 sein, da es ansonsten als "triviales Beispiel" gilt
	    d) 
	    
	3. Brute Force h�tte das folgende Aussehen:
	    Schleife 1: Durchlaufe b von 2 nach 9
	    Schleife 2: Durchlaufe a von 1 nach b
	    Schleife 3: Durchlaufe c von 1 nach 9 und teste obige vier Bedingungen
	    
	4. Nenbenbetrachtung:
	   a)
	    (a*10+c) / (b*10+c) = a / b
	    <=> b*a*10 + b*c = a*b*10 + a*c
	    <=> b = a (denn c != 0)
	    Widerspruch -> 2.a) kann nicht eintreten
	   b)
	     (c*10+a) / (c*10+b) = a / b
	    <=> b*c*10 + b*a = c*a*10 + a*b
	    <=> b = a (denn c != 0)
	    Widerspruch -> 2.d) kann nicht eintreten
	   c)
	     (c*10+a) / (b*10+c) = a / b
	    <=> b*c*10 + b*a = a*b*10 + a*c
	    <=> 10*b*(a-c) = a*(b-c)
		angenommen, nun w�re a <= c <= b, dann w�re die linke Seite negativ und die rechte Seite positiv (oder eine Seite Null)
		also: a < b < c oder c < a < b
	   d)
	     (a*10+c) / (c*10+b) = a / b
	    <=> a*b*10 + b*c = a*c*10 + a*b
	    <=> 10*a*(b-c) = b*(a-c)
		angenommen, nun w�re a <= c <= b, dann w�re die linke Seite negativ und die rechte Seite positiv (oder eine Seite Null)
		also: a < b < c oder c < a < b
		
	   Mit diesen �berlegungen, kann man in Brute Force die n�tigen Tests f�r die dritte Schleife reduzieren auf
	   Test f�r c < a oder c > b und Gleichung b), c)
	   
	5. Programmierung:
		Da Test auf Gleichheit f�r Flie�komma schlecht ist, sollten die umgeformten Gleichungen aus 4.c),d) gestestet werden.

*/