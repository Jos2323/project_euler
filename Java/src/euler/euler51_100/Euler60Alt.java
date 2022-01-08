//Euler Problem 60 (http://projecteuler.net/problem=60 )

/* Problem:	
	Prime pair sets

The primes 3, 7, 109, and 673, are quite remarkable. 
By taking any two primes and concatenating them in any order the result will always be prime. 
For example, taking 7 and 109, both 7109 and 1097 are prime. 
The sum of these four primes, 792, represents the lowest sum for a set of four primes with this property.

Find the lowest sum for a set of five primes for which any two primes concatenate to produce another prime.



*/

/* Lösung (Nr. , ):

	

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.util.Arrays;

import utils.Prime;

public class Euler60Alt {

	static final int[] START = {3,7,109,673,(int)Prime.getNextPrime(673)};
	
	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		int solution = 0;
				
		long[] cand = {START[0],START[1],START[2],START[3],START[4]}; 
		long[] solutionSet = new long[5];
		
		while (solution == 0 || cand[cand.length-1] < solution) {
			
			while(!setNextGroup(cand));
			if (isSolution(cand)) {
				int tempSol = getArraySum(cand);
				if (solution == 0 || tempSol < solution) {
					solution = tempSol;
					solutionSet = cand.clone();
				}
			}
			
			if (cand[4] > 500)
				break;
		}
		
		System.out.println("Result Set: " + solutionSet);	
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static boolean setNextGroup (long[] cand) {
		
		long mod = cand[cand.length-1]%3;
		int i = 0;
		while (i < cand.length) {
			do {
				cand[i] = getNextPrimeMod(cand[i],mod);
			} while (i > 0 && cand[i] < cand[i-1]);
			
			if (i == cand.length-1 || cand[i] < cand[i+1]) 
				break;
			else
			{
				cand[i] = START[i];
				i++;
			}
		}
		return true;
	}
	
	// create own getNextPrime, which ignores 5
	static long getNextPrimeMod1(long x) {
		long ret = Prime.getNextPrime(x);
		while(ret%3 == 2)
			ret = Prime.getNextPrime(ret);
		return ret;
	}
	static long getNextPrimeMod(long x, long mod) {
		long ret = Prime.getNextPrime(x);
		while(ret == 5 || ret%3 != mod)
			ret = Prime.getNextPrime(ret);
		return ret;
	}	static long getNextPrimeMod2(long x) {
		long ret = Prime.getNextPrime(x);
		while(ret == 5 || ret%3 == 1)
			ret = Prime.getNextPrime(ret);
		return ret;
	}

	static boolean isSolution (long[] cand) {
		
		for (int i = 0; i < cand.length-1; i++) {
			for (int j = i+1; j < cand.length; j++) {
				// test if two elements add to 3
				if (getChecksum(cand[i]+cand[j]) == 3)
					return false;
				// test, if concatenations are prime
				if (!Prime.isPrime(conNum(cand[i],cand[j])) || !Prime.isPrime(conNum(cand[j],cand[i])))
					return false;
			}
		}
		return true;
	}
	
	// concatenate numbers
	static long conNum (long x, long y) {
		String ret = Long.toString(x) + Long.toString(y);
		return Long.parseLong(ret);
	}
	
	static int getArraySum (long[] cand) {
		int ret = 0;
		for (long x : cand)
			ret += x;
		return ret;
	}
	
	// get check sum 
	static int getChecksum (long x) {
		// when x is negative, use abs
		if (x < 0)
			x = -x;
		// calculate checksum (first order)
		int ret = 0;
		while (x > 0) {
			ret += x%10;
			x /= 10;
		}
		// if checksum is still larger than 9, repeat
		while (ret >= 10) {
			ret = getChecksum(ret);
		}
		
		return ret;
	}
	
}	






/* Erklärung:
	1. Grundüberlegungen:
		- durchlaufe Sets von fuenf Primzahlen
		- die Anzahl moeglicher Concatenations ist 2*(n*(n-1))/2 = n*(n-1). Fuer n=5 -> 20
		- es muss also getestet werden, ob all diese 20 Werte auch Primzahlen sind. 
		- es muss groesser als die Werte fuer die 4-Gruppe sein, sonst gaebe es dort eine kleinere
	2. Brute Force Prinzip:
		a) Starte mit 2,3,5,7,11
		b) Teste die 20 Verbindungen
		c) gehe zur naechstgroesseren Fuenfergruppe
		   -> das ist die Schwierigkeit. 
		      die simpelste Option ist: Wenn eine Loesung gefunden wurde, durchsuche alle anderen Fuenfergruppen mit geringerer Summe.
		   -> die nacheste Gruppe wird mit folgendem Algorithmus bestimmt:
		      1) versuche Wert 1 auf die naechste Primzahl zu setzen
		      2) wenn moeglich, nehme diese Gruppe
		      3) wenn nicht moeglich, versuche Wert 2 auf die naechste Primzahl zu setzen
		      4) wenn moeglich, setze die Werte links davon auf ihre niedrigsten Werte zurueck
	3. Optimierungen: 
		- 2 und 5 koennen ignoriert werden
		- jede Gruppe, indem die Quersumme von mindestens einem Paar 3 ist, kann ignoriert werden (Bsp. 7 und 11 zusammen)
		- jede Gruppe kann (ausser der 3) immer nur Werte mit dem selben Modulu 3 haben 
		- neuer Versuch:
		  a) baue 1000 x 1000 Matrix (boolean), welche die ersten 1000 Primzahlen beschreibt (Dreiecksmatrix reicht)
		  b) Teste alle Primzahlen paarweise auf Verbindungsmoeglichkeit
		  c) Zaehle spaltenweise, ob es Eintraege mit 4 true gibt 
		  d) wenn ja, nehme die Gruppe mit der geringsten Summe
		  e) wenn nein, wiederhole mit groesserer Matrix
	4. Problematik:
		- Das ist VIEL zu lange...
		

*/
