package utils;

/*
 * Utility class: Fract
 * 
 * Klasse zur Manipulation von Brüchen
 * 
 * Methoden:
 * 
 * public static int gcd (int x, int y)
 * berechnet den größten gemeinsamen Teiler (greatest common denominator) von x und y
 * Implementation via Euklidischer Algorithmus
 * Methode wird als static Methode angeboten.
 * 
 * public static int[] reduce (int x, int y)
 * kürzt den Bruch x/y.
 * Rückgabe ist ein zweistelliges Array mit array[0]/array[1]
 * Methode wird als static Methode angeboten.
 * 
 */

public class Fract {
	
	public Fract()
	{
		
	}
	
	// calculate gcd
	public static int gcd (int x, int y)
	{
		if (y == 0)
			return x;
		else 
			return gcd (y, x%y);
	}
	
	// calculate gcd
	public static long gcd (long x, long y)
	{
		if (y == 0)
			return x;
		else 
			return gcd (y, x%y);
	}

	// reduce fraction
	public static int[] reduce (int x, int y)
	{
		int gcd_val;
		int[] result = {x, y};
		while ( (gcd_val = gcd(result[0],result[1]) ) > 1)
			{
				result[0] /= gcd_val;
				result[1] /= gcd_val;
			}
		return result;
	}
	
	// reduce fraction
	public static long[] reduce (long x, long y)
	{
		long gcd_val;
		long[] result = {x, y};
		while ( (gcd_val = gcd(result[0],result[1]) ) > 1)
			{
				result[0] /= gcd_val;
				result[1] /= gcd_val;
			}
		return result;
	}
	
	
}
