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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Prime;

public class Euler60Alt3 {
	
	static int setLength = 5;
	
	static long lim = 1_000_000;
	
	//static long[] primes = Prime.getPrimeArrayOfLength(size);
	static long[] primes = Prime.getPrimeArray(lim);

	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		long solution = 0;
		
		int cut = 1000;
		
		long[] primesB = new long[primes.length];
		long[] primesF = new long[primes.length];
		
		for (int i = 1; i < primes.length; i++) {
			primesB[i] = primes[i]%cut;
			primesF[i] = primes[i]/cut;
		}
		
		long val = Prime.getNextPrime(100);
		
		Map<Long,List<Long>> cand = new HashMap<Long,List<Long>>();
		
		while (val < 200) {
			List<Long> candList = new ArrayList<Long>();
			int count = 0;
			for (int i = 0; i < primesB.length; i++) {
				if (primesB[i] == val) {
					if (Prime.isPrime(primesF[i])) {
						if (Prime.isPrime(conNum(primesB[i],primesF[i]))) {
							count++;
							candList.add(primesF[i]);
						}
					}
				}					
			}
			if (count >= 3)
				cand.put(val, candList);
			val = Prime.getNextPrime(val);
		}
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
//	static long testSets (int ind, long[] args) {
//		long ret = 0;
//		long sol = 0;
//		for (int i = ind + 1; i < size; i++) {
//			if (i == 2)
//				continue;
//			if (args[args.length-1]%3 + primes[i]%3 == 0)
//				continue;
//			if (isPrimeToAll(primes[i],args)) {
//				if (args.length == setLength - 1) {
//					ret = primes[i];
//					for (long j : args)
//						ret += j;
//					break;
//				} else {
//					long[] cand = Arrays.copyOf(args,args.length+1);
//					cand[cand.length-1] = primes[i];
//					ret = testSets(i, cand);
//
//				}
//			}
//			if (sol == 0 || ret < sol)
//				sol = ret;
//		}
//		return ret;
//	}
	
	//	concatenate numbers
	static long conNum (long x, long y) {
		String ret = Long.toString(x) + Long.toString(y);
		return Long.parseLong(ret);
	}
	
	static boolean isPrimeToAll (long x, long ... val) {
		for (long i : val) {
			if (!Prime.isPrime(conNum(i,x)) || !Prime.isPrime(conNum(x,i))) 
				return false;
		}
		return true;
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
