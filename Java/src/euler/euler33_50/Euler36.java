//Euler Problem 36 (http://projecteuler.net/problem=36)

/* Problem:	
	Double-base palindromes

The decimal number, 585 = 10010010012 (binary), is palindromic in both bases.
Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.
(Please note that the palindromic number, in either base, may not include leading zeros.)



*/

/* Lösung (Nr. 60167, 2016/03/29):

	872187

*/


// Erklärung siehe unterhalb des Programms

// 

package euler.euler33_50;

public class Euler36 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();
		
		int solution = 0;
		
		for (int counter = 0; counter < 1000; counter++ )
		{
		solution = 0;
		
		for(int i = 1; i <= 6; i++)
			solution += createOuter(i);
		}
		System.out.println("Result:" + solution);
		
		long timerResult = (System.currentTimeMillis() - timer);
		System.out.println("Elapsed time: " + timerResult + "ms");
		System.out.println("Median time for single run: " + (double)timerResult/1000 + "ms");
		
	//	System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	

		
	}
	
	
	
	
	// createOuter(int i) ->  gives back the sum of all double palindroms of length i
	// createOuter builds the outermost part of the palindrom (first and last cipher)
	static int createOuter( int i )
	{
		int ret = 0; 	// return value	
		int x;			// palindrom number (to be created)
		
		// for one or two ciphers, create odd palindroms (1,3,5,7,9 respectively, 11,33,55,77,99)
		if (i <= 2)
		{
			for (int j = 1; j <= 9; j+=2)
			{
				// create Palindrom
				x = j + j*10*(i/2);
				
				// test if binary Palindrom and if yes add to sum
				if (testPalBin(x))
				{
				//	System.out.println("Double Palindrom:" + x);
					ret += x;
				}
			}
		}
		// for more than two ciphers, compose with method for inner creation
		else
		{
			for (int j = 1; j <= 9; j+=2)
			{
				// create outermost parts
				x = j + j*(int)Math.pow(10,i-1);
				
				// at this point, x has the form a0a, a00a, a000a, a0000a. 
				// createInner loops over all inner parts and sums up the double palindroms
				// the borders for createInner are given as i-1 (the second cipher to last) and 2 (the second cipher from the front)
				ret += createInner(x,i-1,2);
			}
			
		}
		return ret;
	}
	
	// createInner(int x, int upper, int lower) -> gives back the sum of all double palindroms with given outer part in x
	// x is created by createOuter or create inner -> has the form a0a, a00a, a000a, a0000a (if from createOuter) or ab0ba, ab00ba (if from createInner) 
	// upper and inner give the first unset term (0) from the back (upper) and the front (lower)
	static int createInner(int x, int upper, int lower)
	{
		int ret = 0;	// return value
		int y;			// palindrom number (to be created)
		
		for (int i = 0; i <= 9; i++)
		{
			// create inner part from below
			y = x + i*(int)Math.pow(10, lower-1);
			
			// only create part from above, wenn not the same as from below (-> do NOT, when odd number of ciphers and innermost reached (that is, lower = upper)
			if (upper > lower)
				y += i*(int)Math.pow(10,upper-1);
			
			// if there are still unset inner parts left, call createInner for these borders again
			if (upper - lower > 1)
			{
				ret += createInner(y,upper-1,lower+1);
			}
			else
			{
				// test if binary Palindrom and if yes add to sum
				if (testPalBin(y))
				{
					//System.out.println("Double Palindrom:" + y);
					ret += y;
				}
			}
		}
		return ret;	
	}
	
	// testPalBin -> test if x is a binary Palindrom. give back true, if yes, else false
	static boolean testPalBin(int x)
	{
		// Array of booleans to store binary representation of x
		boolean[] bin = new boolean[21];
		
		int i = 0;
		
		// create binary 
		while (x > 0)
		{
			if (x%2 == 1)
				bin[i]=true;
			else
				bin[i]=false;
			
			x /= 2;
			
			i++;
			
		}
		i--;
		
		// test if binary is palindrom (if not, break and give false)
		for (int j = 0; j <= i/2; j++ )
		{
			if (bin[j] != bin[i-j])
				return false;
		}
		
		return true;
	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- Man kann entweder im Zweiersystem oder im Zehnersystem hochlaufen
		- Um binär ein Palindrom zu sein, muss hinten eine Eins stehen. Damit muss die Zahl ungerade sein.
		- 2^20 = 1,048,576 -> Palindrom hat maximal 20 Ziffern (2^0 mitzählen)
	2. Brute Force Prinzip:
		a) Laufe über die ungeraden Zahlen von 1 bis 1 Million
		b) Prüfe, ob Zahl ein Palindrom ist
		c) Wenn ja, wandle in Zweiersystem um
		d) Prüfe, ob binäre Zahl ein Palindrom ist
	3. Optimierungen:
		a) Weil die Zahl ungerade ist, kann man alle mit 2, 4, 6, 8 vorne überspringen (44.4% weniger), da sonst kein Palindrom 
	4. Alternative Variante: Erstelle Palindrome und teste, ob ihre Binärdarstellung ein Palindrom ist
		a) Beachte: Palindrome haben die Form a, aa, aba, abba, abcba, abccba
		b) a kann dann nur 2,4,6,8 sein (siehe oben)
		c) Test auf binäres Palindrom funktioniert, indem man ein boolean-Array erstellt und auf Gleichheit von vorne nach hinten testet
	5. Algorithmus zur Erstellung der Binärdarstellung
		a) Prüfe, ob Zahl ungerade oder gerade. Wenn ungerade, schreibe 1 in letzte Stelle
		b) Teile Zahl durch 2 und runde ggf. ab. Dann gilt: Wenn teilbar durch 2 aber nicht durch 4, dann ist das Ergebnis ungerade.
		c) Gehe zu Schritt a) und schreibe das Ergebnis in die nächste Stelle 
*/
