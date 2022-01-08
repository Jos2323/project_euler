//Euler Problem 48 (http://projecteuler.net/problem=48)

/* Problem:	
	Self powers

The series, 1^1 + 2^2 + 3^3 + ... + 10^10 = 10405071317.
Find the last ten digits of the series, 1^1 + 2^2 + 3^3 + ... + 1000^1000.



*/

/* Lösung (Nr. 79176, 2016/05/27):

	9110846700

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

public class Euler48 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		long solution = 0;
		long oneSum;
		
		for (int i = 1; i <= 1000; i++)
		{
			if (i % 10 == 0)
				continue;
			
			oneSum = i;
			
			for (int j = 1; j < i; j++)
			{
				oneSum *= i;
				oneSum %= 10_000_000_000L;
			}
			
			solution += oneSum;
			solution %= 10_000_000_000L;
				
		}
		
		System.out.println("Result: " + solution);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
		
}






/* Erklärung:
	1. Grundüberlegungen:
		- Da nur die letzten 10 Ziffern gesucht werden, kann einfach nach jeder Multiplikation alles oberhalb abgeschnitten werden
		- also: nutze long
	2. Brute Force Prinzip:
		a) Laufe von 1 bis 1000
		b) Multipliziere 1 bis 1000 mal mit sich selbst (entsprechen)
		c) verwerfe alles ab 10,000,000,000 (also % 10,000,000,000)
		d) Summiere
	3. Optimierungen: 
		- Man kann weitere Berechnungen verwerfen, wenn hinten nur Nullen stehen. 
		- Dies ist fuer alle Folgenglieder, welche Vielfache von 10 sind, der Fall, hier kann also gleich weitergegangen werden 
		- Beginne gleich mit der Summe bis 10^10
		

*/
