package utils;

import java.util.Arrays;

// DO NOT USE

/* ArrayNumber Class
 * 
 * Represents an integer number as an Array of digits. The array itself is the field num.
 * The sign is handled by the field signum.
 * The variable lead shows the index of the largest non-null digit
 * 
 * Constructors:
 *   Arguments: none                          -> create as zero (1 digit)
 *   Arguments: long initNum                  -> create as initNum
 *   Arguments: long initNum, int size        -> create as initNum, pad to length size, if possible
 *   Arguments: int[] initArray               -> create from Array (becomes positive)
 *   Arguments: int[] initArray, boolean sign -> create from Array and set sign
 *   Arguments: ArrayNumber                   -> create from another ArrayNumber
 * 
 *  Private Methods:
 *    setlead()
 *      -> sets lead to correct value
 * 
 *  Public Methods:
 *  
 *  -Setters:
 *    setFromNumber(long x)  [(casts up automatically from int, if needed)]
 *      -> sets ArrayNumber to x 
 *      
 *    setFromArray (int[] ar)
 *      -> sets ArrayNumber to ar (as positive number)  
 *      
 *    setFromArray (int[] ar, boolean sign)
 *      -> sets ArrayNumber to ar with sign as signum
 *      
 *    set(int i, int x)
 *      -> sets digit at position i to value x
 *      
 *    copy(ArrayNumber x)
 *      -> sets as a copy of ArrayNumber x
 *   
 *      
 *  -Getters:
 *     long getNumberAsLong()
 *       -> gives back ArrayNumber as long 
 *      
 *     int getNumberAsInt()
 *       -> gives back ArrayNumber as int 
 *       
 *     int[] getNumberAsArray()
 *       -> gives back absolute value of ArrayNumber as Array
 *       
 *     boolean getSignum()
 *       -> gives back Signum
 *       
 *     boolean getLead()
 *       -> gives back Index of largest not-null digit
 *              
 *     int get(int i)
 *       -> gives back digit at position i
 *
 *
 *  -In-/Decrementors:
 *     increase()
 *       -> increases ArrayNumber by 1
 *       
 *     decrease()
 *       -> decreases ArrayNumber by 1     
 *
 *
 *  -Compares:     
 *     boolean isEqual(ArrayNumber x)
 *       -> checks, if ArrayNumber is equal to another ArrayNumber
 *          gives back true, if equal. gives back false, if inequal.
 *     
 *     boolean isLargerAbs(ArrayNumber x)
 *       -> checks, if the absolute value of ArrayNumber is larger than the absolute value of another ArrayNumber
 *          gives back true, if larger. gives back false, if smaller or equal.
 *          
 *     boolean isLarger(ArrayNumber x)
 *       -> checks, if ArrayNumber is larger than another ArrayNumber
 *          gives back true, if larger. gives back false, if smaller or equal.
 *          
 *  -Math-Operations:           
 *     add(ArrayNumber x)
 *       -> adds another ArrayNumber (resize ArrayNumber, if needed)
 *       
 *     add(int x)
 *       -> adds an Integer (resize ArrayNumber, if needed)
 *       
 *     int digitSum()
 *       -> calculates digit sum of ArrayNumber (only one time)
 *     
 *     int digitSumFinal()
 *       -> calculates digit sum of ArrayNumber to one digit
 * 
 */

public class ArrayNumber {
	
	// The array consisting of the digits. The smallest one is in num[0];
	private int[] num;
	
	// The Index of the largest Not-Null digit 
	private int lead;
	
	// The sign of the number. true = +, false = -
	private boolean signum;
	
	
	// constructors
	
	//if no arguments, create as zero (1 digit)
	public ArrayNumber()
	{
		setFromNumber(0);
	}

	// create from int/long number
	public ArrayNumber(long initNum)
	{
		setFromNumber(initNum);
	}
	
	// create from int/long number, with fixed length (only applied, when longer than the number needs)
	public ArrayNumber(long initNum, int size)
	{
		setFromNumber(initNum);
		if (size > num.length)
			num = Arrays.copyOf(num,size);
	}
	
	// if only an array is given, initialize as positive
	public ArrayNumber(int[] initArray)
	{
		setFromArray(initArray,true);
	}
	
	// initialize with absolute number in Array and sign
	public ArrayNumber(int[] initArray, boolean sign)
	{
		setFromArray(initArray,sign);
	}
	
	// initialize from another ArrayNumber (= copy)
	public ArrayNumber(ArrayNumber x)
	{
		copy(x);
	}
	

	// public methods
	
	// set from number x. is long here, but gets casted up from int automatically
	public void setFromNumber (long x)
	{
		if ( x < 0)
		{
			signum = false;
			x = -x;
		}
		else
			signum = true;
		
		int counter = 1;
		long xtemp = x;
		while ((xtemp/=10) > 0)
		{
			counter++;
		}
		num = new int[counter];
		
		int i = 0;
		while ( x > 0)
		{
			num[i] = (int) (x % 10);
			x /= 10;
			i++;
		}
		setLead();
	}
	
	// set from Array ar as positive ArrayNumber
	public void setFromArray (int[] ar)
	{
		setFromArray(ar,true);
	}
	
	// set from Array ar and sets signum from boolean sign
	public void setFromArray (int[] ar, boolean sign)
	{
		num = ar;
		setLead();
		signum = sign;
	}
	
	
	// set digit at position i to value x
	public void set(int i, int x)
	{
		boolean validFlag = true;
		if (i < 0)
		{
			System.out.println("Negative Index.");
			validFlag = false;
		}
		if ( (x < 0) || (x > 9) )
		{
			System.out.println("Value not in range (0,...,9).");
			validFlag = false;
		}
		if (validFlag)
		{
			if (i >= num.length)
			{
				System.out.println("Resize Array to fit inserted value.");
				num = Arrays.copyOf(num,i+1);
			}
			num[i] = x;
			setLead();
		}
	}
	
	// copy from another ArrayNumber
	public void copy(ArrayNumber init)
	{
		this.num = Arrays.copyOf(init.num,init.num.length);
		this.signum = init.signum;
		this.lead = init.lead;
	}
	
	
	// returns number as int. when overflow, gives out message and returns 0
	public int getNumberAsInt()
	{
		long res = getNumberAsLong();
		
		if (Math.abs(res) < 2147483647 )
			return (int) res;
		
		else
		{
			System.out.println("Int Overflow. Return 0. Consider calling getNumberAsLong");
			return 0;
		}
			
//			Alternative:
//			
//				int res = 0;
//				for (int i = 0; i <= lead; i++)
//				{
//					if ( (i == 9) && 
//							( (num[i] > 2) || 
//									( (res >= 147483647) && (num[i] == 2)) ) )
//						{
//							System.out.println("Int Overflow. Return -1. Consider calling getNumberAsLong");
//							return 0;
//						}
//					else
//						res += num[i]*Math.pow(10,i);
	//
//				}
//				
//				if (!signum)
//					res = -res;
//				
//				return res;
	}
		
		// returns number as long. when overflow, gives out message
	public long getNumberAsLong()
	{
		long res = 0L;
		for (int i = 0; i <= lead; i++)
		{
			if ( (i == 18) && 
					(num[i] == 9) && 
						(res >= 223372036854775807L) )
				{
					System.out.println("Long Overflow. Return 0");
					return 0;
				}
			else
			{
//					System.out.println("Before" + res + " " + lead + " " + num.length);
				res += num[i]*Math.pow(10,i);
//					System.out.println("After" + res + " " + lead + " " + num.length);
			}

		}
			
		if (!signum)
			res = -res;
		
		return res;
			
	}
	
	
	// gives back num
	public int[] getNumberAsArray()
	{
		return num;
	}
	
	// gives back signum
	public boolean getSignum()
	{
		return signum;
	}
	
	// gives back lead
	public int getLead()
	{
		return lead;
	}
	
	// gives back value of NumberArray at position i. if i is negative, gives back -1.
	public int get(int i)
	{
		if (i < 0)
		{
			System.out.println("Negative Index.");
			return -1;
		}
					
		if ( i <= lead )
			return num[i];		
		else
			return 0;
	}
	
		
	
	
	
	// increase ArrayNumber by One
	public void increase()
	{
		if (signum)
		{
			num[0]++;
			int i = 0;
			while ( (num[i] == 10) && ( i < (num.length-1)) )
			{
				num[i] = 0;
				num[i+1]++;
				i++;
				if (i > lead)
					lead++;
			}
			if(num[num.length-1] == 10)
			{
				System.out.println("Resizing ArrayNumber size (+1)");
				num = new int[num.length+1];
				num[num.length-1] = 1;
				lead = num.length-1;
			}	
		}
		else
		{
			signum = true;
			decrease();
			signum = false;
			if (getNumberAsInt() == 0 )
				signum = true;
		}
	}
	
	// decrease ArrayNumber by One
	public void decrease()
	{
		if (getNumberAsInt() == 0 )
		{
			signum = false;
			num[0]=1;
			return;
		}
		else
			if (signum)
			{
				num[0]--;
				int i = 0;
				while ( (num[i] == -1) && ( i < (num.length-1)) )
				{
					num[i] = 9;
					num[i+1]--;
					i++;
				}
				setLead();
			}
			else	
			{
				signum = true;
				increase();
				signum = false;
				if (getNumberAsInt() == 0 )
					signum = true;
			}
	}
	
	
	public boolean isEqual(ArrayNumber x)
	{
		// cannot be equal, if signum is different (null has signum true, so is included in this check)
		if (this.signum != x.signum)
			return false;
		else
		{
			// cannot be equal, if number of digits is different
			if (this.lead != x.lead)
				return false;
			else
			{
				for (int i = 0; i <= lead; i++)
				{
					// is not equal, if at a position the digits are different
					if (this.num[i] != x.num[i])
						return false;
				}
				return true;
			}
		}
	}
	
	
	
	
	// gives true, if num is larger than x, gives false, if num is smaller or equal than x. 
	public boolean isLargerAbs(ArrayNumber x)
	{
		if (this.lead > x.lead)
			return true;
		if (this.lead < x.lead)
			return false;
		
		for (int i = this.lead; i >= 0; i--)
		{
			if (this.num[i] > x.num[i])
				return true;
			if (this.num[i] < x.num[i])
				return false;
		}
		
		// if x = num, none of the above catches, so give back false.
		return false;
	}
	
	public boolean isLarger(ArrayNumber x)
	{
		
		if (this.signum == x.signum)
		{
			if (signum)
				return isLargerAbs(x); 
			else
			{
				if (isEqual(x))
					return false;
				else
					return !isLargerAbs(x);
			}			
		}
				//( !isLargerAbs(x) ^ signum );
		else
			return signum;	
	}
	
	
	public void add (ArrayNumber x)
	{

		// if x is longer than num, fill rest with zeros
		if (this.num.length < x.num.length)
		{
			this.num = Arrays.copyOf(num,x.num.length);	
			System.out.println("Resizing ArrayNumber size (to length of input)");
		}
		if (this.num.length > x.num.length)
			x.num = Arrays.copyOf(x.num,this.num.length);	
		
		if (this.signum == x.signum)
		{
			
			for (int i = 0; i < x.num.length; i++)
			{
				this.num[i] += x.num[i];
				if (this.num[i] >= 10)
				{
					this.num[i] %= 10;
					if (i < this.num.length-1)
						this.num[i+1]++;
					else
					{
						System.out.println("Resizing ArrayNumber size (+1)");
						this.num = Arrays.copyOf(this.num,this.num.length+1);	
						this.num[this.num.length-1] = 1;
					}
				}
			}
		}
		else
		{
			if (this.isLargerAbs(x))
			{			
				for (int i = 0; i < x.num.length; i++)
				{
					this.num[i] -= x.num[i];
					if (this.num[i] < 0)
					{
						this.num[i] += 10;
						this.num[i+1]--;
						
					}
				}
			}
			else
			{
				ArrayNumber xhelp = new ArrayNumber(x); 
				xhelp.add(this);
				this.num = Arrays.copyOf(xhelp.num,this.num.length);
//				this.num = xhelp.num;
				this.signum = x.signum;
			}

		}
		setLead();
		if (getNumberAsInt() == 0 )
			signum = true;
			
	}
	
	public void mult (ArrayNumber x) {
		ArrayNumber tmp = new ArrayNumber(0,this.lead+1);
		ArrayNumber ret = new ArrayNumber(0,this.lead+1);
		
		for (int i = 0; i <= this.lead; i++)
		{
			for (int j = 0; j <= x.lead; j++)
			{
				tmp.add(multSingleDigit(this, x.get(j)));
			}	
			ret.add(x);
		}
	}
	
	// if ArrayNumber exists as long, then add as Integer. if not, use ArrayNumber Addition
	public void add (long x)
	{

		long val;
		if ( ( ( val = getNumberAsLong() ) != 0 ) || (lead < 18) )
		{
			val += x;
			setFromNumber(val);
		}
		else
		{
			ArrayNumber x_temp = new ArrayNumber(x);
		
			this.add(x_temp);
		}
	}
	
	
	// calculate digit sum (cross sum). Only calculates once, so result may be multiple digits long
	public int digitSum()
	{
		int res = 0;
		for (int i = 0; i <= lead; i++)
			res += num[i];
		return res;		
	}
	
	// calculate digit sum (cross sum) until one-digit result is calculated (if needed, redo digit sum)
	public int digitSumFinal()
	{
		ArrayNumber temp = new ArrayNumber(this);
		do
		{
			temp.setFromNumber(temp.digitSum());
		}
		while (temp.lead != 0);
		
		return temp.getNumberAsInt();		
	}
	
	
	// helper methods

	// change lead to actual value
	private void setLead()
	{
		lead = 0;
		for (int i = 0; i < num.length; i++)
		{
			if (num[i] != 0)
				lead = i;
		}	
	}
	// set length of num, so that no leading zero exist
	private void reduceArrayLength() {
		if (num.length-1 > lead)
			num = Arrays.copyOf(num, lead+1);
	}
	// set length of num to specified length (works only, if specified length is at least long enough)	
	private void setArrayLength(int len) {
		if (num.length-1 < len)
			num = Arrays.copyOf(num, len);
	}
	
	// multiply ArrayNumber to SingleDigit
	private ArrayNumber multSingleDigit (ArrayNumber x, int s) {
		int tmp = 0;
		for (int i = 0; i <= lead; i++)
		{
			int tmp2 = s * x.get(i);
			x.set(i, tmp2/10 + tmp);
			tmp = tmp2%10;
		}
		if (tmp > 0 && (lead < x.num.length-1))
		{
			x.setArrayLength(lead+1);
			x.set(lead+1, tmp);
			x.setLead();
		}
		return x;
	}

}
