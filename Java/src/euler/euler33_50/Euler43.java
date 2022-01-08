//Euler Problem 43 (http://projecteuler.net/problem=43)

/* Problem:	
	Sub-string divisibility

The number, 1406357289, is a 0 to 9 pandigital number because it is made up of 
each of the digits 0 to 9 in some order, but it also has a rather interesting sub-string divisibility property.
Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we note the following:
d2d3d4=406 is divisible by 2
d3d4d5=063 is divisible by 3
d4d5d6=635 is divisible by 5
d5d6d7=357 is divisible by 7
d6d7d8=572 is divisible by 11
d7d8d9=728 is divisible by 13
d8d9d10=289 is divisible by 17
Find the sum of all 0 to 9 pandigital numbers with this property.



*/

/* Lösung (Nr. 39199, 2016/04/29):

	16695334890

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.NumArr;
import utils.Pandigital;

public class Euler43 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();

		long solution = 0;
		
		List<ArrayList<int[]>> triples = new ArrayList<ArrayList<int[]>>();

		triples.add((ArrayList<int[]>) createTriple(13,1000,9,new int[]{0,0,0,0,0,0,0,0,0,0}));
		triples.add((ArrayList<int[]>) createTriple(11,1000,8,new int[]{0,0,0,0,0,0,0,0,0,0}));
		triples.add((ArrayList<int[]>) createTriple(7,1000,7,new int[]{0,0,0,0,0,0,0,0,0,0}));
		triples.add((ArrayList<int[]>) createTriple(5,1000,6,new int[]{0,0,0,0,0,0,0,0,0,0}));
		triples.add((ArrayList<int[]>) createTriple(3,1000,5,new int[]{0,0,0,0,0,0,0,0,0,0}));
		triples.add((ArrayList<int[]>) createTriple(2,1000,4,new int[]{0,0,0,0,0,0,0,0,0,0}));
		
		List<int[]> solutions = createTriple(17,1000,10,new int[]{0,0,0,0,0,0,0,0,0,0});
		
		int counter = 8;
		
		for (ArrayList<int[]>triple : triples)
		{
			List<int[]> tempSolutions = new ArrayList<int[]>();
			for (int[] oneSolution : solutions)
			{
				for (int[] oneTriple : triple)
				{
					if ( (oneSolution[counter] == oneTriple[counter]) && (oneSolution[counter-1] == oneTriple[counter-1]) )
					{
						int[] temp = Arrays.copyOf(oneSolution, oneSolution.length);
						temp[counter-2] = oneTriple[counter-2];
						tempSolutions.add(temp);
					}
				}
			}
			solutions.clear();
			solutions.addAll(tempSolutions);
			counter--;
		}
		
		int solcounter = 0;
		for (int[] oneSolution : solutions)
		{
			for (int i = 0; i < 10; i++)
			{
				oneSolution[0]=i;
				if (Pandigital.isPandigital(oneSolution, true))
				{
					solcounter++;
					System.out.println("Found Solution Nr." + solcounter + " : " + Arrays.toString(oneSolution));
					solution += NumArr.arrayToLong(oneSolution, false);
					break;
				}
			}
		}

		System.out.println("Result: " + solution);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static List<int[]> createTriple(int base, int max, int end, int[] val)
	{
		List<int[]> res = new ArrayList<int[]>();
		
		int temp = base;
		while (temp < max)
		{
			int[] newVal = Arrays.copyOf(val, val.length);
			int[] tempArr = NumArr.toArray(temp);
			for (int i = 1; i <= tempArr.length; i++)
				newVal[end-i] = tempArr[tempArr.length-i];
			if (Pandigital.isPandigital(newVal,true,end-3,end)) 
				res.add(newVal);
			temp += base;
		}

		return res;
	}
	

}






/* Erklärung:
	1. Grundüberlegungen:
		- Die folgenden Bedingungen gelten:
		  - d6 ist 0 oder 5
		    - aber: wenn d6 0 wäre, so müsste d7d8 durch 11 teilbar und damit gleich sein
		    -> d6 ist 5
		    -> die moeglichen Varianten fuer d7d8 sind 06, 17, 28, 39, 61, 72, 83, 94
		  - d4 ist gerade
		  - d3+d4+d5 ist durch 3 teilbar
		  - d9 ist durch die möglichen Lösungen für d7d8 schon stark eingeschränkt
		  - damit ist dann auch d10 stark eingeschränkt
	2. Brute Force Prinzip:
		a) Setze ein Array mit d6=5
		b) durchlaufe die Varianten für d7d8
		c) durchlaufe die Varianten für d9
		d) durchlaufe die Varianten für d10
		e) durchlaufe die restlichen Varianten für die d1-d5 und teste alle Bedingungen
	3. Optimierungen:
		- 
		
*/
