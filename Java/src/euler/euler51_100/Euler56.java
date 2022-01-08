//Euler Problem 56 (http://projecteuler.net/problem=56 )

/* Problem:	
	Powerful digit sum

A googol (10^100) is a massive number: one followed by one-hundred zeros; 
100^100 is almost unimaginably large: one followed by two-hundred zeros. 
Despite their size, the sum of the digits in each number is only 1.

Considering natural numbers of the form, a^b, where a, b < 100, what is the maximum digital sum?



*/

/* Lösung (Nr. 41344, 2016/09/22):

	972

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import utils.ArrayNum;

public class Euler56 {
	
	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int solution = 0;
		int tmp;
		ArrayNum num = new ArrayNum();
		
		for (int i = 99; i >= 10; i--) {
			num.setArrayNum(i);
			for (int j = 2; j < 100; j++) {	
				num.mult(i);
				if (9*num.getLead() < solution)
					continue;
				tmp = num.digitSum();
				if (tmp > solution){
					solution = tmp;	
					System.out.println("New Solution: " + solution + " Number: " + i + "^" + j);
				}
			}
		}
		
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
}






/* Erklärung:
	1. Grundüberlegungen:
		- da a^b < 100^100 mit 200 Stellen ist, muss die Hoechstsumme kleiner als 1800 (=200*9) sein
		- a <= 10 muss nicht betrachtet werden, da dann a^b = (a^2)^(b/2) mit a^2<=100 es auch enthaelt
	2. Brute Force Prinzip:
		a) Fange bei 99^99 an und laufe runter. Sobald die Anzahl Stellen * 9 kleiner als das bisher groesste Ergebnis ist, kann aufgehoert werden
	3. Optimierungen: 
		- 
	4. Problematik:
		- Braucht eine Multiplikation fuer grosse Zahlen
		  -> verwende ArrayNum
		

*/
