//Euler Problem 59 (http://projecteuler.net/problem=59 )

/* Problem:	
	XOR decryption

Each character on a computer is assigned a unique code and the preferred standard is ASCII 
(American Standard Code for Information Interchange). 
For example, uppercase A = 65, asterisk (*) = 42, and lowercase k = 107.

A modern encryption method is to take a text file, convert the bytes to ASCII, 
then XOR each byte with a given value, taken from a secret key. 
The advantage with the XOR function is that using the same encryption key on the cipher text, 
restores the plain text; for example, 65 XOR 42 = 107, then 107 XOR 42 = 65.

For unbreakable encryption, the key is the same length as the plain text message, 
and the key is made up of random bytes. 
The user would keep the encrypted message and the encryption key in different locations, 
and without both "halves", it is impossible to decrypt the message.

Unfortunately, this method is impractical for most users, so the modified method is to use a password as a key. 
If the password is shorter than the message, which is likely, the key is repeated cyclically throughout the message. 
The balance for this method is using a sufficiently long password key for security, but short enough to be memorable.

Your task has been made easy, as the encryption key consists of three lower case characters. 
Using cipher.txt (right click and 'Save Link/Target As...'), a file containing the encrypted ASCII codes, 
and the knowledge that the plain text must contain common English words, 
decrypt the message and find the sum of the ASCII values in the original text.



*/

/* Lösung (Nr. 30048, 2016/11/25):

	107359

*/


// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Euler59Alt {

	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		int solution = 0;
				
		String inFile = "res/p059_cipher.txt";
		
		int spaceNum = 0;

		int[] keys = new int[3];
		
		label1:
		for (int keys1 = 97; keys1 <= 122; keys1++) {	
			keys[0] = keys1;
			
			label2:
			for (int keys2 = 97; keys2 <= 122; keys2++) {
				keys[1] = keys2;
				
				label3:
				for (int keys3 = 97; keys3 <= 122; keys3++) {
					keys[2] = keys3;
	
					int counter = 0;
					try (Scanner scanner = new Scanner(new File(inFile)); ) {
						scanner.useDelimiter(",");
						StringBuilder sb = new StringBuilder(1200);
						while (scanner.hasNextInt()) {
							int c = scanner.nextInt();	
							int cc = 0;
							cc = xor(c,keys[counter]);
							if (cc == 127 || cc < 32)
							{
								if (counter == 0)
									continue label1;
								else if (counter == 1)
									continue label2;
								else
									continue label3;
							} 
							sb.append((char)cc);
							counter++;
							counter %= 3;
						} 
						String s = sb.toString();
						int count = s.length() - s.replace(" ", "").length();
						if (count > spaceNum) {
							spaceNum = count;
							System.out.println("Amount: " + count + " Key: " + (char)keys[0] + " , " + (char)keys[1] + " , " + (char)keys[2] + " . ");
							solution = 0;
							for (int i = 0; i < s.length(); i++) {
								solution += sb.charAt(i);
							}
						}
					} catch (FileNotFoundException e) {
						System.out.println("File does not exist.");
						e.printStackTrace();
					}
				}
			}
		}
			
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	
	static private int xor(int x, int y) {
		return x^y;
	}

}






/* Erklärung:
	1. Grundüberlegungen:
		- 
	2. Brute Force Prinzip:
		a) durchlaufe alle Key-Moeglichkeiten
		b) breche ab, wenn ein nicht verwendbares Zeichen auftritt
		c) breche ab, wenn ein Wort ohne Vokale oder mit mehrere Sonderzeichen auftaucht 
	3. Optimierungen: 
		- folgende Variante geht schneller
		a) durchlaufe alle Key-Moeglichkeiten
		b) breche ab, wenn ein nicht verwendbares Zeichen auftritt
		c) teste, ob die aktuelle Variante am oeftesten das Leerzeichen ergibt. Wenn ja: wahrscheinlich der Key
	4. Problematik:
		- 
		

*/
