//Euler Problem 62 (http://projecteuler.net/problem=62 )

/* Problem:	
	Cubic permutations

The cube, 41063625 (345^3), can be permuted to produce two other cubes: 56623104 (384^3) and 66430125 (405^3). 
In fact, 41063625 is the smallest cube which has exactly three permutations of its digits which are also cube.

Find the smallest cube for which exactly five permutations of its digits are cube.



*/

/* Lösung (Nr. 23896, 2017/10/21):

	127035954683

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Euler62 {
	
	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		long solution = 0;
		
		List<Long> cubes = getCubes();
		
		Map<String,List<Long>> countPerm = new HashMap<>();	
		
		int digitNum = 0;
		
		for (long cube : cubes) {
			char[] tmpc = Long.toString(cube).toCharArray();
			Arrays.sort(tmpc);
			String tmp = new String(tmpc);
			if (digitNum > 0 && tmp.length() > digitNum)
				break;

			if (!countPerm.containsKey(tmp)){
				countPerm.put(tmp, new ArrayList<Long>());
			} 
			countPerm.get(tmp).add(cube);
			
			if (countPerm.get(tmp).size() == 5) {
				digitNum = tmp.length(); 	
			}
		}
		
		for (Map.Entry<String, List<Long>> entry : countPerm.entrySet()) {
			if (entry.getValue().size() == 5) {
				if (solution == 0 || solution > entry.getValue().get(0))
					solution = entry.getValue().get(0);
			}
		}
		
		
		
//		for (long cube : cubes) {
//			ArrayList<Integer> digits = new ArrayList<>();
//			long tmp = cube;
//			while (tmp > 0) {
//				digits.add((int)(tmp%10));
//				tmp /= 10;
//			}
//			Collections.sort(digits);
//			Collections.reverse(digits);
//			
//			for (int i = 0; i < digits.size(); i++) {
//				tmp *= 10;
//				tmp += digits.get(i);
//			}
//			int count = 1;
//			if (countPermutations.containsKey(tmp)){
//				count = countPermutations.get(tmp) + 1;
//			}
//			countPermutations.put(tmp, count);
//			
//			if (count == 5) {
//				break;
//			}
//		}
		
//		for (Map.Entry<Long, Integer> entry : countPermutations.entrySet()) {
//			if (entry.getValue() == 5) {
//				solution = entry.getKey();
//			}
//		}
		
//		List<Long> ignore = new ArrayList<>();
//		
//		for (int i = 0; i < cubes.size(); i++) {
//			long sol = cubes.get(i);
//			if (ignore.contains(sol))
//				continue;
//			int solLen = Long.toString(sol).length();
//			int counter = 1;
//			for (int j = i+1; j < cubes.size(); j++) {
//				if (solLen < Long.toString(cubes.get(j)).length()) 
//					break;
//				if (Permutation.isRealPermutation(sol, cubes.get(j))) {
//					counter++;
//					ignore.add(cubes.get(j));
//				}
//			}
//			if (counter == 5) {
//				solution = sol;
//				break;
//			}
//		}
		

		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	}
	
//	static final int LEN = 13;
	
//	static List<BigInteger> getCubes() {
//		List<BigInteger> cubes = new ArrayList<>();
//		
//		BigInteger base = BigInteger.ONE;
//		BigInteger val = BigInteger.ONE;
//		while (val.toString().length() < LEN) {
//				val = base.multiply(base).multiply(base);
//				cubes.add(val);
//				base = base.add(BigInteger.ONE);
//		}
//		
//		return cubes;
//	}
	
	static List<Long> getCubes() {
		List<Long> cubes = new ArrayList<>();
		
		long base = 1;
		long val = 1;
		while (true) {
			try {
			val = Math.multiplyExact(base*base,base);
			cubes.add(val);
			base++;
			} catch (ArithmeticException e) {
				break;
			}
		}
		
		return cubes;
	}

	
}


/* Erklärung:
	1. Grundüberlegungen:
		- 
	2. Brute Force Prinzip:
		a) bilde eine Liste aller Cubes (bis zu einer gewissen Laenge
		b) laufe die Liste hoch und vergleiche jede Cube mit allen groesseren einer gewissen Laenge, ob Permutation
	3. Optimierungen: 
		- ignoriere Werte, die bereits einmal als Permutation gefunden wurden, aber nicht fuenfmal
		- berechne nicht jedesmal die Stringlaenge des gegenwaertigen Wertes neu
		- neue Variante:
		  - durchlaufe die cubes hoch
		  - ordne Ziffern von klein nach gross (dadurch erhaelt man eine eindeutige Permutation)
		  - zaehle jede dieser Permutationen hoch
		  - stoppe wenn 5 gefunden wurden und die naechstgroessere Anzahl Ziffern vorliegt
		  - die kleineste Permutation aller gefundenen Kandidaten mit 5 Permutationen ist die Loesung
	4. Problematik:
		- Brute Force ist langsam
		

*/
