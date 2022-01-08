package utils;

import java.math.BigInteger;
import java.util.Arrays;

public class NumArr {
	
	public static int[] toArray(int x) {
		return toArray((long)x);
	}
	
	public static int[] toArray(long x) {
		return toArray(x,false);
	}
	
	// backwards: true -> Array starts with smallest digit in 0 and counting upwards
	// backwards: false -> Array starts with largest digit in 0 and counting downwards (= as is written normally)
	public static int[] toArray(long x, boolean backwards) {
		String temp = Long.toString(x);
		
		int[] ret = new int[temp.length()];
		for (int i = 0; i < temp.length(); i++)
		{ 
			if (backwards)
				ret[ret.length-1-i] = temp.charAt(i) - '0';
			else
				ret[i] = temp.charAt(i) - '0';
		}
		return ret;
	}
	
	public static int[] toArray(BigInteger x) {
		return toArray(x,false);
	}
	
	// backwards: true -> Array starts with smallest digit in 0 and counting upwards
	// backwards: false -> Array starts with largest digit in 0 and counting downwards (= as is written normally)
	public static int[] toArray(BigInteger x, boolean backwards) {
		String temp = x.toString();
		
		int[] ret = new int[temp.length()];
		for (int i = 0; i < temp.length(); i++)
		{ 
			if (backwards)
				ret[ret.length-1-i] = temp.charAt(i) - '0';
			else
				ret[i] = temp.charAt(i) - '0';
		}
		return ret;
	}
	
	// backwards: true -> 0 is evaluated as smallest digit and going upwards
	// backwards: false -> 0 is evaluated as largest digit and going downwards (= as is written normally)
	public static long arrayToLong(int[] x, boolean backwards) {
		long ret = 0;
		
		for (int i = 0; i < x.length; i++)
		{
			if(backwards)
				ret += x[i]*(long)Math.pow(10, i);
			else
				ret += x[x.length-1-i]*(long)Math.pow(10, i);
		}
		
		return ret;
	}

	
	public static int[] add(int[] x, int[] y) {
		return add(x,y,false);
	}
	
	// add two numbers as array
	public static int[] add(int[] x, int[] y, boolean backwards) {

		int[] res;
		
		if (backwards)
		{
			if (x.length > y.length)
				y = Arrays.copyOf(y, x.length);
			else if (x.length < y.length)
				x = Arrays.copyOf(x, y.length);
			
			res = new int[x.length];
			for (int i = 0; i < x.length; i++)
			{
				res[i]+= x[i] + y[i];
				if (res[i] > 9)
				{
					if (i == x.length-1)
						res = Arrays.copyOf(res, x.length+1);
					res[i+1]++;
					res[i]%=10;
				}
			}
		}
		else
		{
			if (x.length > y.length)
			{
				int[] tmp = Arrays.copyOf(y, y.length);
				y = new int[x.length];
				System.arraycopy(tmp, 0, y, y.length-tmp.length, tmp.length);
			}
			else if (x.length < y.length)
			{
				int[] tmp = Arrays.copyOf(x, x.length);
				x = new int[y.length];
				System.arraycopy(tmp, 0, x, x.length-tmp.length, tmp.length);	
			}
			
			res = new int[x.length+1];
			for (int i = x.length-1; i >= 0; i--)
			{
				res[i+1]+= x[i] + y[i];
				if (res[i+1] > 9)
				{
					res[i]++;
					res[i+1]%=10;
				}
				else if (i == 0)
					res = Arrays.copyOfRange(res, 1, x.length+1);
			}
		}	
		return res;
	}
	
}
