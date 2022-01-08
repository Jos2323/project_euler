//Euler Problem 63 (http://projecteuler.net/problem=63 )

/* Problem:	
	Powerful digit counts

The 5-digit number, 16807=7^5, is also a fifth power. 
Similarly, the 9-digit number, 134217728=8^9, is a ninth power.

How many n-digit positive integers exist which are also an nth power?



*/

/* Lösung (Nr. 33502, 2017/10/22):

	49

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Euler63 {
	
	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		int solution = 0;
		
		List<BigInteger> sol = new ArrayList<>();
		
		for (int i = 1; i < 10; i++) {
			int exp = 1;
			BigInteger base = new BigInteger(Integer.toString(i));
			BigInteger val = base;
			while (val.toString().length() == exp) {
				sol.add(val);
				solution++;
				val = val.multiply(base);
				exp++;
			}
		}
	
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	}
	
	static int digitNum (long x) {
		return Long.toString(x).length();
	}

	
}


/* Erklärung:
	1. Grundüberlegungen:
		- es muss ein n geben, so dass fuer m > n die Zahl k^m nicht m Ziffern haben kann.
		  - 2^m waechst am langsamsten
		    -> finde m mit 2^m mehr als m Zeichen.
		  - 10^m waechst mit einer Ziffer pro m
		    -> zB.: 10^2 = 100 -> 3 Ziffern. 10^5 = 100000 -> 6 Ziffern
		    -> fuer n > 10 gilt: es existiert k>0 mit k+10 = n und n^2 = 10^2 + 2*10*k + k^2. 
		    -> Da 10^2 schon drei Ziffern hat, hat auch n^2 mind drei Ziffern.
		    -> die Basis kann hoechstens 9 sein.
		  - 3^m waechst mit einer Ziffer fuer mindestens zwei m
		    -> Grund: 10*3=30, 3*30=90 
		       -> wenn man mit einmal drei multiplizieren auf die naechste Ziffer kommt, so
		          hat man einen Wert kleiner 30 und damit bei der naechsten Multiplikation mit 
		          drei noch keine neue Ziffer
		  - Sobald eine Zahl unter 10 nicht mehr m Ziffern fuer k^m hat, so kann sie das nie wieder aufholen
		    -> Grund: x*9 < x*10, aber x*10 ist nur eine Ziffer mehr    
	2. Brute Force Prinzip:
		a) zaehle fuer alle einstelligen Zahlen die Potenzen hoch
		b) sobald Ziffernzahl kleiner als Exponent, hoere auf
		c) addiere Anzahlen
	3. Optimierungen: 
		- 
	4. Problematik:
		- 
		

*/
