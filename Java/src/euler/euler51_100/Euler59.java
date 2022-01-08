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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Euler59 {

	public static void main(String[] args) {
		
		long timer = System.currentTimeMillis();
		
		int solution = 0;
				
		String inFile = "res/p059_cipher.txt";
		String outFile = "res/p059_output_text.txt";

		
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),Charset.forName("UTF-8")))) {
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
								if (eval(sb.toString()))
									continue label3;
								counter++;
								counter %= 3;
							} 
							writer.write(sb.toString());
							writer.newLine();
							System.out.println("Key: " + (char)keys[0] + " , " + (char)keys[1] + " , " + (char)keys[2] + " . ");
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		try (Scanner scanner = new Scanner(new File(outFile));) {
			if (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (scanner.hasNextInt()) {
					System.out.println("Multiple Results");
				} else {
					System.out.println(line);
					for (int i = 0; i < line.length(); i++) {
//						System.out.println((int)line.charAt(i));
						solution += line.charAt(i);
					}
				}
			} else {
				System.out.println("No result found");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist.");
			e.printStackTrace();
		}
		
		System.out.println("Result: " + solution);	

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");	
	
	}
	
	
	static private int xor(int x, int y) {
		return x^y;
	}
	
	static private boolean eval(String s) {
		Pattern word = Pattern.compile(" \\S+ ");
		Matcher m1 = word.matcher(s);
		if (m1.find()) {
			Pattern noVocal = Pattern.compile(" [^aeiouAEIOU0-9]+ ");
			Pattern multSpec = Pattern.compile("(\\S*\\p{Punct}\\S*\\p{Punct}\\S*)+");
			Matcher m2 = noVocal.matcher(s);
			Matcher m3 = multSpec.matcher(s);
			if (m2.find() || m3.find()) 
				return true; 
		} else if (s.length() > 20)
			return true;
		
		return false;
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
		- 
	4. Problematik:
		- 
		

*/
