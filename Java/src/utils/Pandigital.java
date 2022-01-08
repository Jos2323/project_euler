package utils;

import java.util.Arrays;

public class Pandigital {
	
	public static boolean isPandigital(int[] x)
	{
		boolean[] validator = new boolean[x.length+1];
		
		Arrays.fill(validator, false);
		
		for (int i : x)
		{
			if (  (i==0) || (i>x.length) || (validator[i]) )
				return false;
			else 
				validator[i]=true;
		}
		
		return true;
	}
	
	public static boolean isPandigital(int[] x, int len)
	{
		if (x.length != len)
			return false;
		
		boolean[] validator = new boolean[x.length];
		
		Arrays.fill(validator, false);
		
		for (int i : x)
		{
			if ( (i==0) || (i > len)  || (validator[i]) )
				return false;
			else 
				validator[i]=true;
		}
		
		return true;
	}
	
	public static boolean isPandigital(int x)
	{
		int[] xArr = NumArr.toArray(x);
		
		return isPandigital(xArr);
	}
	
	// if zero is true, include 0 into check, if false do not include
	public static boolean isPandigital(int[] x, boolean zero)
	{
		// use non-zero method, if false
		if (!zero)
			return isPandigital(x);
		
		boolean[] validator = new boolean[x.length];
		
		Arrays.fill(validator, false);
		
		for (int i : x)
		{
			if ( (i>x.length) || (validator[i]) )
				return false;
			else 
				validator[i]=true;
		}
		
		return true;
	}
	
	// only check part between start and end
	// if zero is true, include 0 into check, if false do not include
	public static boolean isPandigital(int[] x, boolean zero, int start, int end)
	{
		if (start < 0 || end > x.length || start > end)
		{
			System.out.println("Indices input has invalid format. Ingoring");
			start = 0;
			end = x.length;
		}
		
		boolean[] validator = new boolean[x.length];
		Arrays.fill(validator, false);
		
		for (int i = start; i < end; i++)
		{
			if ( ((!zero) && (x[i]==0)) || (x[i]>x.length) || (validator[x[i]]) )
				return false;
			else 
				validator[x[i]]=true;
		}
		
		return true;
	}
	
	// if zero is true, include 0 into check, if false do not include
	public static boolean isPandigitalPreset(int[] x, boolean[] preset, boolean zero)
	{
		
		boolean[] validator = null;
		if (x.length != preset.length)
		{
			System.out.println("Preset input has invalid format. Ingoring");
			validator = new boolean[x.length];
			Arrays.fill(validator, false);
		}
		else
		{
			validator = Arrays.copyOf(preset, preset.length);
		}
		
		for (int i : x)
		{
			if ( ((!zero) && (i==0)) || (i>x.length) || (validator[i]) )
				return false;
			else 
				validator[i]=true;
		}
		
		return true;
	}
	
	// only check part between start and end
	// if zero is true, include 0 into check, if false do not include
	public static boolean isPandigitalPreset(int[] x, boolean[] preset, boolean zero, int start, int end)
	{
		if (start < 0 || end > x.length || start > end)
		{
			System.out.println("Indices input has invalid format. Ingoring");
			start = 0;
			end = x.length;
		}
		
		boolean[] validator = null;
		if (x.length != preset.length)
		{
			System.out.println("Preset input has invalid format. Ingoring");
			validator = new boolean[x.length];
			Arrays.fill(validator, false);
		}
		else
		{
			validator = Arrays.copyOf(preset, preset.length);
		}
		
		for (int i = start; i < end; i++)
		{
			if ( ((!zero) && (x[i]==0)) || (x[i]>x.length) || (validator[x[i]]) )
				return false;
			else 
				validator[x[i]]=true;
		}
		
		return true;
	}

}
