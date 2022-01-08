//Euler Problem 60 (http://projecteuler.net/problem=60 )

/* Problem:	
	Prime pair sets

The primes 3, 7, 109, and 673, are quite remarkable. 
By taking any two primes and concatenating them in any order the result will always be prime. 
For example, taking 7 and 109, both 7109 and 1097 are prime. 
The sum of these four primes, 792, represents the lowest sum for a set of four primes with this property.

Find the lowest sum for a set of five primes for which any two primes concatenate to produce another prime.



*/

/* Lösung (Nr. 19754, 2017/10/05):

	26033

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import utils.Prime;

public class Euler60 {

	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		int setLength = 5;
		
		long solution = 0;
		
		long[] primes = Prime.getPrimeArray(10000L);
		
		int size = primes.length;
//		int size = 1_060;
		
//		long[] primes = Prime.getPrimeArrayOfLength(size);
		
		boolean[][] primePair = new boolean[size][size];
		
		// create pairwise test
		for (int i = 1; i < size-1; i++) {
			for (int j = i+1; j < size; j++) {
				primePair[i][j] = testPair(primes[i],primes[j]);
			}
		}

		// run through all prime numbers
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
				if (primePair[i][j]) {
					for (int k = j+1; k < size; k++) {
						if (solution > 0 && primes[k] * setLength-2 + tempSum > solution)
							break;
						tempSum = primes[j] + primes[i] + primes[k];
						if (primePair[i][k] && primePair[j][k]) {
							for (int m = k+1; m < size; m++) {
								if (solution > 0 && primes[m] * setLength-3 + tempSum > solution)
									break;
								tempSum = primes[j] + primes[i] + primes[k] + primes[m];
								if (primePair[i][m] && primePair[j][m] && primePair[k][m] ) {
//										long temp = primes[m] + primes[k] + primes[j] + primes[i];
//										if (solution == 0 || temp < solution)
//											solution = primes[m] + primes[k] + primes[j] + primes[i];
									
									for (int n = m+1; n < size; n++) {
										if (solution > 0 && primes[n] * setLength-4 + tempSum > solution)
											break;
//											if (isPrimeToAll(primes[n],primes[m],primes[k],primes[j],primes[i])) {
										if (primePair[i][n] && primePair[j][n] && primePair[k][n] && primePair[m][n] ) {
											long temp = primes[n] + primes[m] + primes[k] + primes[j] + primes[i];
											if (solution == 0 || temp < solution) {
												solution = primes[n] + primes[m] + primes[k] + primes[j] + primes[i];
												System.out.println("Candidates: " + 
														primes[n] + " " + primes[m] + " " + primes[k] + " " + primes[j] + " " + primes[i] );
												System.out.println("Sum: " + solution );
												
											}
										}
									}
								}
							}
						}
					}
				} 
			}
			if (System.currentTimeMillis() - timer > 20_000) {
				System.out.println(i);
				break;
			}
		}
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static boolean testPair(long x, long y) {
		if ( (x%3 + y%3) == 3)
			return false;
		if (!Prime.isPrime(conNum(y,x)) || !Prime.isPrime(conNum(x,y))) 
			return false;
		else
			return true;
	}
	
	//	concatenate numbers
	static long conNum (long x, long y) {
		String ret = Long.toString(x) + Long.toString(y);
		return Long.parseLong(ret);
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
		  a) baue 2000 x 2000 Matrix (boolean), welche die ersten 2000 Primzahlen beschreibt (Dreiecksmatrix reicht)
		  b) Teste alle Primzahlen paarweise auf Verbindungsmoeglichkeit
		  c) Danach durchlaufe die Zahlen hoch und mache keine Tests, sondern schaue in Testmatrix  
		  d) Wenn true, dann gehe hier weiter hinein
	4. Problematik:
		- Das ist immer noch VIEL zu lange...
		

*/
