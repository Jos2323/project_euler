//Euler Problem 38 (http://projecteuler.net/problem=38)

/* Problem:	
	Pandigital multiples

Take the number 192 and multiply it by each of 1, 2, and 3:
192 × 1 = 192
192 × 2 = 384
192 × 3 = 576
By concatenating each product we get the 1 to 9 pandigital, 192384576. 
We will call 192384576 the concatenated product of 192 and (1,2,3)
The same can be achieved by starting with 9 and multiplying by 1, 2, 3, 4, and 5, 
giving the pandigital, 918273645, which is the concatenated product of 9 and (1,2,3,4,5).
What is the largest 1 to 9 pandigital 9-digit number that can be formed 
as the concatenated product of an integer with (1,2, ... , n) where n > 1?



*/

/* Lösung (Nr. 41820, 2016/04/20):

	932718654

*/


// Erklärung siehe unterhalb des Programms

// 

package euler.euler33_50;

import java.util.Arrays;

public class Euler38 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int baseNum = 9476;
		
		while (!isPandigital(createConcatenate(baseNum)))
		{
			do
			{
				baseNum--;
			}
			while (!preTest(baseNum));
			
			if (baseNum < 9183)
			{
				baseNum = 9;
				break;
			}
		}
		
		int[] solution = createConcatenate(baseNum);
		
		if (baseNum == 9)
			solution = new int[]{9, 1, 8, 2, 7, 3, 6, 4, 5};

		System.out.println("Result: " + Arrays.toString(solution) + " Base Number: " + baseNum);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	static boolean isPandigital(int[] x)
	{
		boolean[] validator = new boolean[10];
		
		Arrays.fill(validator, false);
		
		for (int i : x)
		{
			if ( (validator[i]) || (i==0) )
				return false;
			else 
				validator[i]=true;
		}
		
		return true;
		
	}
	
	static boolean preTest(int x)
	{
		String temp = Integer.toString(x);
		
		for (int i = 0; i < 4; i++)
		{
			int c = Character.digit(temp.charAt(i), 10);
			
			if ( (c == 0) || ( c == 8))
					return false;
		}
		
		return true;
	}
	
	static int[] createConcatenate(int x)
	{
		String temp = Integer.toString(x) + Integer.toString(2*x);
		
		int[] ret = new int[9];
		
		for (int i = 0; i < 9; i++)
		{
			ret[i] = Character.digit(temp.charAt(i), 10);
		}
		
		return ret;
	}
	
}






/* Erklärung:
	1. Grundüberlegungen:
		- Wir wissen aus der Aufgabenstellung bereits, dass 918273645 eine untere Grenze ist
		- Vorne steht die Grundzahl, da bei der Concatenation vor die Multiplikation mit 1 steht
		- 9-Pandigital bedeutet neun Zeichen. Da n>1 sein muss, kann die Grundzahl hoechstens vierstellig sein.
		- Um ueber der obigen Untergrenze zu liegen, muss die Grundzahl vorne eine 9 haben.
		- Moegliche Varianten: 
		   -> Grundzahl hat 4 Ziffern und n=2 
		       -> Zweite Zahl muss 5-stellig sein 
		       -> funktioniert, wegen der 9 vorne
		   -> Grundzahl hat 3 Ziffern und n=3 
		       -> Alle Zahlen muessen 3-stellig sein 
		       -> funktioniert nicht, wegen der 9 vorne ist die zweite schon 4-stellig 
		       -> 3+4+4=11
		   -> Grundzahl hat 2 Ziffern und n=4
		       -> Nur die letzte Zahl ist 3-stellig
		       ->  funktioniert nicht, wegen der 9 vorne ist die zweite schon 3-stellig
		       -> 2+3+3+3=11 (2+3+4 funktioniert auch nicht, weil der Faktor 3 keine 4-stellige Zahl ergibt
		- Also: Es muss eine 4-stellige Zahl sein, hinter welche ihr doppeltes steht.
		- Da hinten das doppelte steht, ist die letzte Ziffer gerade.
		- Da eine 4-stellige Zahl verdoppelt wird, steht an fuenfter Stelle eine 1
		- Da die Grundzahl vorne eine 9 hat, kann diese nicht in der doppelten Zahl vorkommen.
		   -> Die doppelte Zahl kann also hoechstens 18765 sein, damit kann die Grundzahl hoechstens 9482 sein.
		   -> Es reicht, die Zahlen 9183 bis 9482 zu testen		  
	2. Brute Force Prinzip:
		a) Durchlaufe die Zahlen von 9482 runter bis 9183
		b) Teste, ob die Grundzahl mit ihrem doppelten dahinter pandigital ist
		c) wenn ja: fertig
	3. Optimierungen:
		- Teste bereits fuer die Grundzahl, ob sie keine doppelten Zeichen enthaelt
		- Ueberspringe alle Zahlen, die eine 8 oder eine 0 enthalten
		
*/
