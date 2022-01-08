package utils;

import java.math.BigInteger;

public class Permutation {
	
	// check for Permutation (ignore leading zeros) (false for x == y)
	public static boolean isRealPermutation(long x, long y) {
		if (x == y)
			return false;
		
		int[] xx = NumArr.toArray(x);
		int[] yy = NumArr.toArray(y);
		
//		if (xx.length != yy.length)
//			return false;
		
//		boolean[] used = new boolean[xx.length];
//		
//		for (int i = 0; i < xx.length; i++)
//		{
//			for (int j = 0; j < yy.length; j++)
//			{
//				if (!used[j] && xx[i] == yy[j])
//				{
//					used[j] = true;
//					break;
//				}
//				else 
//				{
//					if (j == yy.length-1)
//						return false;
//				}
//			}
//		}
//		
//		for (int i = 0; i < used.length; i++)
//			if (!used[i])
//				return false;
//		
//		return true;
		return isPermutation(xx,yy); 
	}
	
	// check for Permutation (true for x == y)
	public static boolean isPermutation(int[] x, int[] y) {

		// no Permutation, if length is different
		if (x.length != y.length)
			return false;
		
		// no Permutation, if sum of digits is different
		int xcross = 0;
		for (int xx : x)
			xcross += xx;
		int ycross = 0;
		for (int yy : y)
			ycross += yy;
		if (xcross != ycross)
			return false;
			
		
		boolean[] used = new boolean[x.length];
		
		for (int i = 0; i < x.length; i++)
		{
			for (int j = 0; j < y.length; j++)
			{
				if (!used[j] && x[i] == y[j])
				{
					used[j] = true;
					break;
				}
				else 
				{
					if (j == y.length-1)
						return false;
				}
			}
		}
		
		for (int i = 0; i < used.length; i++)
			if (!used[i])
				return false;
		
		return true;
	}
	
	// check for Permutation of BigInteger (true for x == y)
	public static boolean isPermutation (BigInteger x, BigInteger y) {
		if (x.equals(y))
			return true;
		
		int[] xx = NumArr.toArray(x);
		int[] yy = NumArr.toArray(y);
		
		return isPermutation(xx,yy);
	}
	
	// check for Permutation of BigInteger (true for x == y)
		public static boolean isRealPermutation (BigInteger x, BigInteger y) {
			if (x.equals(y))
				return false;
			
			int[] xx = NumArr.toArray(x);
			int[] yy = NumArr.toArray(y);
			
			return isPermutation(xx,yy);
		}


}
