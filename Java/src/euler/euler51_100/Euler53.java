//Euler Problem 53 (http://projecteuler.net/problem=53	)

/* Problem:	
	Combinatoric selections

There are exactly ten ways of selecting three from five, 12345:
123, 124, 125, 134, 135, 145, 234, 235, 245, and 345
In combinatorics, we use the notation, 5C3 = 10.
In general,
nCr =	
n!
r!(n-r)!
,where r <= n, n! = n×(n-1)×...×3×2×1, and 0! = 1.
It is not until n = 23, that a value exceeds one-million: 23C10 = 1144066.
How many, not necessarily distinct, values of  nCr, for 1 <= n <= 100, are greater than one-million?



*/

/* Lösung (Nr. 40980, 2016/06/26):

	4075

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

public class Euler53 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		final int limit = 1_000_000; 
		
		int solution = 0;
		
		long[][] num = new long[2][52];
		
		num[0][0] = num[0][1] = num[1][0] = 1;
		
		for (int n = 2; n <= 100; n++)
		{
			
			for (int r = 1; r <= n/2; r++)
			{
				num[1][r] = num[0][r-1] + num[0][r];
				if (num[1][r] > limit) 
				{
					solution += 2 * (n/2 - r + 1) - ((n+1)%2);
					if (n%2 == 0)
						num[1][r+1] = num[1][r];
					break;
				}
			}
			num[1][n/2 + 1] = num[1][n/2];
			System.arraycopy(num[1], 0, num[0], 0, num[0].length);
		}
			
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
}






/* Erklärung:
	1. Grundüberlegungen:
		- gesucht ist die Anzahl von n ueber r (mit 1 <= n <= 100 und r von 0 bis n) groesser als 1_000_000
		- es gilt: n ueber r ist parallel fuer r, dh. genau dann wenn n ueber r > 1_000_000, dann ist es auch n ueber n-r
		- es gilt: ist n ueber r > 1_000_000, so sind auch alle n ueber r zwischen r und n/2 groesser 1_000_000 
	2. Brute Force Prinzip:
		a) Durchlaufe n von 1 bis 100
		b) durchlaufe n ueber r fuer jedes n, wobei r von 0 hochlaeuft
		c) wenn n ueber r > 1_000_000, so zaehle n/2 (abgerundet) - r hinzu. Danach multipliziere mit 2 
			und ziehe 1 ab, wenn n gerade ist (da das mittlere Element nur einmal existiert)
	3. Optimierungen: 
		- obiges funktioniert nicht, wegen dem Problem aus 4.
		- zweite Loesungsmoeglichkeit:
			- nutze die Eigenschaft, dass die Dreieckszahlen sich aus den vorherigen berechnen lassen
			- Formel: n ueber r = (n-1) ueber r + (n-1) ueber (r-1), wobei n ueber n als 1 gesetzt wird
			- Dann: berechne nach dieser Formel fuer jedes n bis zu 1_000_000 oder bis zur Mitte aus den vorherigen und zaehle wie bei 2.
	4. Problematik:
		- die Berechnung von n ueber r erfordert im Direktfall die Berechnung von 100!, was zu gross ist.
		

*/
