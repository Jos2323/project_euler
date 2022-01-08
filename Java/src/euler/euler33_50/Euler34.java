//Euler Problem 34 (http://projecteuler.net/problem=34)

/* Problem:	
	Digit factorials

145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
Find the sum of all numbers which are equal to the sum of the factorial of their digits.
Note: as 1! = 1 and 2! = 2 are not sums they are not included.



*/

/* Lösung (Nr. 61558, 2016/01/31):

	40730

*/


// Erklärung siehe unterhalb des Programms

// 

package euler.euler33_50;

import java.util.Arrays;

public class Euler34 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		final int limit = 1_854_722;
		
		int[] num = {0,1,0,0,0,0,0};
		int lead = 2;
		final int[] fac = {1,1,2,6,24,120,720,5_040,40_320,362_880};
		int curNum;
		
		int solution = 0;
		
		 while ((curNum = toNumber(num)) < limit)
		 {
			 if (curNum % fac[9] < 241_920)
			 {
			 
				 int facSum = calcFactSum(Arrays.copyOf(num,lead),fac);
			 
				 if (facSum == curNum)
				 {
					 System.out.println("Found sum: " + curNum);
					 solution += curNum;
				 }
			 }
			 
			 lead = next(num);
			
		 }
		 
		System.out.println("Solution: " + solution);
		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");
		
		
		
	}
	
	static int next (int [] a)
	{
		int lead = 0;
		a[0]++;
		for (int i = 0; i < 7; i++)
		{
			if (a[i] == 10)
			{
				a[i]=0;
				a[i+1]++;
			}
			if (a[i] != 0)
				lead = i+1;
		}
		
		return lead;
		
	}
	
	
	static int toNumber (int [] a)
	{
		int res = 0;
		for (int i = 0; i < 7; i++)
			res += a[i]*Math.pow(10,i);
		return res;
	}
	
	
	static int calcFactSum (int [] a, int[] fac)
	{
		int sum = 0;
		for (int i : a)
			sum += fac[i];
		
		return sum;
		
	}
	
	

}






/* Erklärung:
	1. Grundüberlegungen:
		Es geht um die Summe der Fakultäten der Ziffern.
		Es sind 9! = 362,880 und 8 * 9! =2,903,040.
		Mit 8 Ziffern ist also keine Darstellung mehr möglich. Genauer, ist keine Zahl größer als 2,903,040 möglich.
	2. Verkürzung der möglichen Ergebnisse
		Auch 2,903,040 ist nicht möglich, da Null enthalten sind, welche das Ergebnis nicht vergrößern. 
		Zudem ist die erste Ziffer 2, und 2,999,999 ist nicht möglich. 
		Dann: 2 + 8! + 5 * 9! =1,854,722, also ist die Grenze 1,854,722.
	3. Mögliche Summen:
		5!, 6!, 7!, 8!, 9! sind alles Vielfache von 10. Damit ergibt sich die letzte Stelle zusammengesetzt aus
		0! = 1, 1! = 1, 2! = 2, 3! = 6, 4! = 24 (-> 4).
	4. Eingschränkter Brute Force:
		Nimm ein Array1[7] und setze es als {0,1,0,0,0,0,0} für die Zahl 10.
		Nimm ein zweites Array2[10] und setze es als {1,1,2,6,24,120,720,5040,40320,362880}.
		Berechne Array1 als Zahl und berechne die Summe via Array2.
	5. Verkürzungen:
		a) Betrachte für die Summe nur die Werte aus Array1, die kleiner als 5 sind und vergleiche die letzte Stelle
		   also: for (i : Array1) if (i < 5) sum += Array(i); und dann sum % 10 == Array1[0].
		   Wenn ja, dann rechne weiter
		b) Wenn eine Ziffer enthalten ist, so muss die Zahl mindestens deren Fakultät entsprechen. 
		   Wenn das nicht gegeben ist, kann man gleich weiterspringen
		c) 6 * 8! = 241,920. Da mehr mit Ziffern < 9 nicht möglich ist, können die Werte zwischen 241,920 und 362,880
		   übersprungen werden. Das gilt oberhalb jedem Vielfachen von 362,880 
	6. Ergebnis: 
		Es gibt tatsächlich nur zwei solcher Zahlen, 145 und 40585
	
*/