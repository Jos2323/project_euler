package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Prime Class
 * 
 * Gives various tools (mostly static) to support handling cases with Prime Numbers.
 * 
 * Methods
 * 
 */

public class Prime {
	
	public static int[] pTo200 = new int[]{2,3,5,7,11,13,17,19,23, 29, 31, 37, 
			41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 
			109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 
			181, 191, 193, 197, 199};
	
	
	// public methods
	
		// returns next prime larger than x (gives NOT x, if x is prime)
		public static long getNextPrime(long x)
		{
			if (x < 2)
				return 2;
			
			if (x % 2 == 0)
				x++;
			else
				x+=2;

			while (!isPrime(x))
				x+=2;
			
			return x;
			
		}

		// test if x is prime
		public static boolean isPrime (long x)
		{
			if (x < 200)
				return testIn200(x);
			
			if (!testTo200(x))
				return false;
			
			if (!testToSixMult(x,203))
				return false;
			
			return true;		
		}
		
		// Primes between limLow and limUp, including limLow but excluding limUp
		// Input and Output as long
		public static List<Long> getPrimeList (long limLow, long limUp)
		{
			long prime = getNextPrime(limLow-1);
			
			if (prime >= limUp)
				return null;
			
			List<Long> primeList = new ArrayList<Long>();
			
			while (prime < limUp)
			{
				primeList.add(prime);
				prime = getNextPrime(prime);
			}
			
			return primeList;
		}
		
		// Primes between limLow and limUp, including limLow but excluding limUp
		// Input and Output as int
		public static List<Integer> getPrimeList (int limLow, int limUp)
		{
			int prime = (int)getNextPrime(limLow-1);
			
			if (prime >= limUp)
				return null;
			
			List<Integer> primeList = new ArrayList<Integer>();
			
			while (prime < limUp)
			{
				primeList.add(prime);
				prime = (int)getNextPrime(prime);
			}
			
			return primeList;
		}
		
		// Primes lower than lim (excluding)
		// Input and Output as long
		public static List<Long> getPrimeList (long lim)
		{
			return getPrimeList(2L,lim);
		}
		
		// Primes lower than lim (excluding)
		// Input and Output as int
		public static List<Integer> getPrimeList (int lim)
		{
			return getPrimeList(2,lim);
		}
		
		// Primes between limLow and limUp, including limLow but excluding limUp
		// Input and Output as long
		public static long[] getPrimeArray (long limLow, long limUp)
		{
			List<Long> primeList = getPrimeList(limLow, limUp);
			
			if (primeList == null)
				return null;
			
			long[] primeArray = new long[primeList.size()];
			
			for (int i = 0; i < primeArray.length; i++)
			{
				primeArray[i] = primeList.get(i);
			}
			
			return primeArray;
		}
		
		// Primes between limLow and limUp, including limLow but excluding limUp
		// Input and Output as int
		public static int[] getPrimeArray (int limLow, int limUp)
		{
			List<Integer> primeList = getPrimeList(limLow, limUp);
			
			if (primeList == null)
				return null;
			
			int[] primeArray = new int[primeList.size()];
			
			for (int i = 0; i < primeArray.length; i++)
			{
				primeArray[i] = primeList.get(i);
			}
			
			return primeArray;
		}
		
		public static long[] getPrimeArray (long lim)
		{
			return getPrimeArray(2L,lim);
		}
		
		public static int[] getPrimeArray (int lim)
		{
			return getPrimeArray(2,lim);
		}
		

		// The first len primes
		public static long[] getPrimeArrayOfLength (int len)
		{
			if (len < 1)
				return null;
			
			long[] primelist = new long[len];
			
			primelist[0] = 2;
			
			for (int i = 1; i < len; i++)
			{
				primelist[i] = getNextPrime(primelist[i-1]);
			}
			
			return primelist;
		}
		
		// get prime factors of x
		public static long[][] factorization (long x)
		{
			long[][] ret = null;
			
			if (isPrime(x))
			{
				ret = new long[1][2];
				ret[0][0] = x;
				ret[0][1] = 1;
			}
			else
			{
				List<long[]> factors = new ArrayList<long[]>();
				long factorCand = 2;
				long[] counter;
				
				while (factorCand <= x)
				{
					counter = new long[]{0,0};
				
					while (x % factorCand == 0)
					{
						counter[0] = factorCand;
						counter[1]++;
						x /= factorCand;
					}
					if (counter[1] > 0)
						factors.add(counter);
					factorCand = getNextPrime(factorCand);
				}
				
				ret = new long[factors.size()][2];
				int i = 0;
				for (long[] factor : factors)
				{
					ret[i] = factor;
					i++;
				}
			}
				
			return ret;
		}
		
	
	// private methods
	
	// test, if number a prime number below 200
	private static boolean testIn200(long x)
	{
		for (int i : pTo200)
		{
			if (x == i)
				return true;
		}
		return false;
	}
	
	// test, if number is divisible by prime number below 200. gives false, if divisible. gives true, if not divisible
	private static boolean testTo200(long x)
	{
		for (int i : pTo200)
		{
			if (x % i == 0)
				return false;
		}
		return true;
	}
	
	// test all Numbers 6k-1 and 6k+1 larger than start until sqrt(x), if x is divisible to them
	private static boolean testToSixMult(long x,int start)
	{
		if (start < 3)
			if (x % 2 == 0)
				return false;
		if (start < 4)
			if (x % 3 == 0)
				return false;
		if (start < 5)
			start = 5;
		
		int stepsize = 0;
		switch (start % 6)
		{
		case 0 : 
			start ++; 
		case 1 : 
			stepsize = 2;
			break;
		case 2 : 
			start ++; 
		case 3 :
			start ++; 
		case 4 : 
			start ++; 
		case 5 :
			stepsize = 4;
			break;
		}
		
		
		for (long i = start; i*i <= x; i += stepsize)
		{
			if (stepsize == 2)
				stepsize = 4;
			else
				stepsize = 2;

			if (x % i == 0)
				return false;

		}

		return true;
	}
	
	// test all Numbers 6k-1 and 6k+1 until sqrt(x), if x is divisible to them
	private static boolean testToSixMult(long x)
	{
		return testToSixMult(x,2);
	}
	
	

}
