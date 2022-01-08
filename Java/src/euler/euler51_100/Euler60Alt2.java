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

public class Euler60Alt2 {
	
	static int setLength = 5;
	
	static int size = 1_000;
	
	static long[] primes = Prime.getPrimeArrayOfLength(size);
	
	static boolean[][] notPrime = new boolean[size][size];

	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		long solution = 0;
		
		
		
		
		
////		boolean[][] pairs = new boolean[size][];
//		
////		boolean[][][][][] sets = new boolean[size][][][][];
//		
		// create boolean array of possible pairs
		// skip 2, as it cannot be in a solution set
		outerloop:
		for (int i = 1; i < size; i++) {
			// skip 5, as it cannot be in a solution set
			if (i == 2)
				continue;
			
			if (solution > 0 && primes[i] * setLength > solution)
				break;
			long tempSum = primes[i];
			for (int j = i+1; j < size; j++) {
				if (j == 2)
					continue;
				if (solution > 0 && primes[j] * setLength-1 + tempSum > solution)
					break;
				tempSum = primes[j] + primes[i];
				if (isPrimeToAll(primes[j],primes[i])) {
					for (int k = j+1; k < size; k++) {
						if (solution > 0 && primes[k] * setLength-2 + tempSum > solution)
							break;
						tempSum = primes[j] + primes[i] + primes[k];
						if (isPrimeToAll(primes[k],primes[j],primes[i])) {		
							for (int m = k+1; m < size; m++) {
								if (solution > 0 && primes[m] * setLength-3 + tempSum > solution)
									break;
								tempSum = primes[j] + primes[i] + primes[k] + primes[m];
								if (isPrimeToAll(primes[m],primes[k],primes[j],primes[i])) {
									for (int n = m+1; n < size; n++) {
										if (solution > 0 && primes[n] * setLength-4 + tempSum > solution)
											break;
										//tempSum += primes[m];
										if (isPrimeToAll(primes[n],primes[m],primes[k],primes[j],primes[i])) {
											long temp = primes[n] + primes[m] + primes[k] + primes[j] + primes[i];
											if (solution == 0 || temp < solution)
												solution = primes[m] + primes[k] + primes[j] + primes[i];
										}
									//break outerloop;
									}
								}
							}
						}
					}
				} else {
					notPrime[i][j] = true;
				}
			}
			if (System.currentTimeMillis() - timer > 10_000) {
				System.out.println(i);
				break;
			}
		}
		
		
		
//		int index = 1;
//		while (solution == 0 || primes[index] < solution) {
//			long temp = testSets(index, new long[]{primes[index]});
//			if (temp > 0 && (solution == 0 || temp < solution)) {
//				solution = temp;
//				break;
//			}
//			index++;
//			if (index >= size) {
//				System.out.println("No solution found inside the first " + size + " primes." );
//				break;
//			}
//			if (System.currentTimeMillis() - timer > 10_000) {
//				
//System.out.println(index);
//System.out.println(index);
//				break;
//			}
//			break;
//		}
//		for (int i = 122; i < size; i++) {
//			if (Prime.isPrime(conNum(673,primes[i])) && Prime.isPrime(conNum(primes[i],673))) {
//				System.out.println(primes[i]);	
//				if (Prime.isPrime(conNum(3,primes[i])) && Prime.isPrime(conNum(primes[i],3))) {
//					System.out.println("to 109");		
//				}
//			}
//		}
		
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static long testSets (int ind, long[] args) {
		long ret = 0;
		long sol = 0;
		for (int i = ind + 1; i < size; i++) {
			if (i == 2)
				continue;
			if (args[args.length-1]%3 + primes[i]%3 == 0)
				continue;
			if (isPrimeToAll(primes[i],args)) {
				if (args.length == setLength - 1) {
					ret = primes[i];
					for (long j : args)
						ret += j;
					break;
				} else {
					long[] cand = Arrays.copyOf(args,args.length+1);
					cand[cand.length-1] = primes[i];
					ret = testSets(i, cand);

				}
			}
			if (sol == 0 || ret < sol)
				sol = ret;
		}
		return ret;
	}
	
	static long testSets2 (int ind, long x, long[] args) {
		long ret = 0;
		for (int i = ind + 1; i < size; i++) {
			if (i == 2)
				continue;
			if (isPrimeToAll(x,args)) {
				if (args.length == setLength - 1) {
					ret = x;
					for (long j : args)
						ret += j;
				} else {
					long[] cand = Arrays.copyOf(args,args.length+1);
					cand[cand.length-1] = x;
					ret = testSets2(i, primes[i],cand);
				}
			}
		}
		return ret;
	}

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
