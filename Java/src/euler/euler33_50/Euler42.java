//Euler Problem 42 (http://projecteuler.net/problem=42)

/* Problem:	
	Coded triangle numbers

The nth term of the sequence of triangle numbers is given by, tn = ½n(n+1); 
so the first ten triangle numbers are:
1, 3, 6, 10, 15, 21, 28, 36, 45, 55, ...
By converting each letter in a word to a number corresponding to its alphabetical position 
and adding these values we form a word value. 
For example, the word value for SKY is 19 + 11 + 25 = 55 = t10. 
If the word value is a triangle number then we shall call the word a triangle word.

Using words.txt (right click and 'Save Link/Target As...'), 
a 16K text file containing nearly two-thousand common English words, how many are triangle words?



*/

/* Lösung (Nr. 50828, 2016/04/29):

	162

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler33_50;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Euler42 {
	
	public static void main(String[] args) throws IOException {

		long timer = System.currentTimeMillis();

		int solution = 0;
		
		//Define the first 30 Triangular Values (enough for most words)
		int[] triVal = new int[30];
		for (int i = 0; i < triVal.length; i++)
			triVal[i]=(i+1)*(i+2)/2;
		
		Scanner scanner = new Scanner(new File("res/p042_words.txt"));
		scanner.useDelimiter("\",\"|^\"");
		
		while(scanner.hasNext())
		{
			
			String word = scanner.next();
			
			int val;
			
			if ( (val = isTriangularWord(word, triVal)) > 0 )
			{
				solution++;
				System.out.println("Found Word. Nr." + solution + " Word: " + word + " Value: " + val);
			}
			
		}
		
		scanner.close();

		System.out.println("Result: " + solution);		
		
		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	//Version that calculates triangular values every time
	static int isTriangularWord(String word)
	{
		int ret = 0;
		
		for (int i = 0; i < word.length(); i++)
		{
			ret += word.charAt(i) - 'A' + 1;
		}
		
		int tri = 0, triCnt = 0;
		while (tri < ret)
		{
			triCnt++;
			tri += triCnt;
		}
		
		if (tri == ret)
			return ret;
		
		return 0;
	}
	
	// version that takes an array of the first triangular values and does binary search (and only calculates, if too large)
	static int isTriangularWord(String word, int[] triVal)
	{
		int ret = 0;
		
		for (int i = 0; i < word.length(); i++)
		{
			ret += word.charAt(i) - 'A' + 1;
		}
		
		int testTri = 0;
		if ((testTri = Arrays.binarySearch(triVal, ret)) >= 0)
			return ret;
		
		if (testTri == -triVal.length - 1)
		{
		
			int tri = triVal[triVal.length-1], triCnt = triVal.length-1;
			while (tri < ret)
			{
				triCnt++;
				tri += triCnt;
			}
		
			if (tri == ret)
				return ret;
		}
		
		return -1;
	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- Zunächst braucht man eine Methode, um die Worte Stück für Stück einzulesen
		- Anschließend müssen die Worte auf Triangularität untersucht werden
	2. Brute Force Prinzip:
		a) Lese Wort ein
		b) Durchlaufe Buchstaben und addiere Buchstabenwert
		c) Prüfe, ob trianguläre Zahl
	3. Optimierungen:
		- 
	4. Bemerkung:
		- Es ist ziemlich genau gleich schnell, ob man jedes Mal die Triangulärwerte berechnet oder ein
		  Array übergibt, dass diese Werte gespeichert hat und dann Binary Search gemacht wird
		
*/
