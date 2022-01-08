package utils;

import java.util.Arrays;


/* ArrayNum Class
 * 
 * Represents an integer number as an Array of digits. The array itself is the field num (minimum length 20).
 * The sign is handled by the field sign.
 * The variable lead shows the index of the largest non-null digit
 * 
 * Constructors:
 *   Arguments: none                          -> create as zero
 *   Arguments: long numVal                   -> create as numVal
 *   Arguments: long numVal, int len          -> create as numVal, pad to length len, if possible
 *   Arguments: int[] num                     -> create from Array (becomes positive)
 *   Arguments: int[] num, boolean sign       -> create from Array and set sign
 *   Arguments: ArrayNum                      -> create from another ArrayNum
 * 

 * 
 *  Public Methods:
 *  
 *  -Setters:
 *    setArrayNum(long x)  [(casts up automatically from int, if needed)]
 *      -> sets ArrayNumber to x 
 *      
 *    setArrayNum (int[] ar)
 *      -> sets ArrayNumber to ar (as positive number)  
 *      
 *    setArrayNum (int[] ar, boolean sign)
 *      -> sets ArrayNumber to ar with sign as signum
 *      
 *    setArrayNum(ArrayNumber x)
 *      -> sets as a copy of ArrayNumber x
 *      
 *    set(int i, int x)
 *      -> sets digit at position i to value x
 *   
 *      
 *  -Getters:
 *     long getNumberAsLong()
 *       -> gives back ArrayNumber as long 
 *      
 *     int getNumberAsInt()
 *       -> gives back ArrayNumber as int 
 *           
 *     int get(int i)
 *       -> gives back digit at position i
 *
 *
 *  -In-/Decrementors:
 *     increment()
 *       -> increases ArrayNumber by 1
 *       
 *     decrement()
 *       -> decreases ArrayNumber by 1     
 *
 *
 *          
 *  -Math-Operations:           
 *     add(ArrayNumber x)
 *       -> adds another ArrayNumber
 *       
 *     add(long x)
 *       -> adds a long
 *       
 *     subtract(ArrayNumber x)
 *       -> subtracts another ArrayNumber
 *       
 *     subtract(long x)
 *       -> subtracts a long
 *       
 *     mult(ArrayNumber x)
 *       -> multiplies another ArrayNumber
 *       
 *     mult(long x)
 *       -> multiplies a long
 *       
 *     int digitSum()
 *       -> calculates digit sum of ArrayNum (only one time)
 *     
 *     int digitSumFinal()
 *       -> calculates digit sum of ArrayNum to one digit
 *       
 *       
 *  Private Methods:
 *    setLead()
 *      -> sets lead to correct value
 *      
 *    writeLongToNum(long numVal)
 *      -> writes numVal in num. Does not resize (pads values above with zeros). Sets lead and sign.
 *      
 *      
 *  Static Private Methods:
 *     writeLongToArray(int[] arr, long numVal)
 *       -> writes numVal in the array arr.
 * 
 */

public class ArrayNum implements Comparable<ArrayNum> {
	
	// The array consisting of the digits. The smallest one is in num[0];
	// num should always have minimum length 20, to take long without problem 
	private int[] num;
	
	// value of the largest non-null index of num (=length of the Number - 1)
	private int lead;
	
	// sign of the number (true is positive, false is negative)
	private boolean sign;
	
	
	// constructors
	
	// default constructor: set as 0
	public ArrayNum() {
		this(0, 20);
	}
	// set from long with fixed length (min 20)
	public ArrayNum(long numVal) {
		this(numVal, 20);
	}
	// set from long with fixed length (min 20)
	public ArrayNum(long numVal, int len) {
		if (len < 20) {
			System.err.println("Length parameter too small. Ignoring, setting to 20.");
			len = 20;
		}
		num = new int[len];
		setArrayNum(numVal);
	}
	// set from other ArrayNum
	public ArrayNum(ArrayNum arrayNum) {
		setArrayNum(arrayNum);
	}
	// set from Array (sign is true)
	public ArrayNum(int[] num) {
		setArrayNum(num,true);
	}
	// set from Array and sign
	public ArrayNum(int[] num, boolean sign) {
		setArrayNum(num,sign);
	}
	
	
	// public methods (mathematical operations)
	
	// add other ArrayNum
	public ArrayNum add(ArrayNum x) {
		// check, if signs are equal. if not, do subtraction
		if(this.sign != x.sign) {
			ArrayNum xtmp = x.clone();
			if(this.sign)
				xtmp.sign = true;
			else 
				xtmp.sign = false;
			subtract(xtmp);	
			return this;
		}
		
		// resize num, if not long enough. To be sure, make 1 longer than maximum length of both summands
		if (this.num.length < Math.max(this.lead+2, x.lead+2)) {
			resizeTo(Math.max(this.lead+2, x.lead+2));
		}
		int[] xnum = Arrays.copyOf(x.num,this.num.length);
		
		for (int i = 0; i < this.num.length-1; i++) {
			num[i] += xnum[i];
			if (num[i] >= 10) {
				num[i] %= 10;
				num[i+1]++;
			}
		}
		setLead();
		return this;
	}
	// add long
	public ArrayNum add(long x) {
		ArrayNum xtmp = new ArrayNum(x);
		add(xtmp);
		return this;
	}
	// if save is false, only give out result but do not save
	// if save is true, do normal add (save and give out)
	public ArrayNum add(ArrayNum x, boolean save) {
		if (save) 
			return add(x);
		
		ArrayNum ret = new ArrayNum(this);
		return ret.add(x);
	}
	
	// subtract other ArrayNum
	public ArrayNum subtract(ArrayNum x) {
		// check, if signs are equal. if not, do addition
		if(this.sign != x.sign) {
			ArrayNum xtmp = x.clone();
			if(this.sign)
				xtmp.sign = true;
			else 
				xtmp.sign = false;
			add(xtmp);	
			return this;
		}
		
		// if x is larger, then do subtract in reverse order and reverse sign
		if (compareToAbs(x) < 0) {
			ArrayNum tmp = this.clone();
			setArrayNum(x);
			subtract(tmp);
			setSign(!sign);
			return this;
		}
		
		int[] xnum = Arrays.copyOf(x.num,this.num.length);
		
		for (int i = 0; i < this.num.length-1; i++) {
			num[i] -= xnum[i];
			if (num[i] < 0) {
				num[i] += 10;
				num[i+1]--;
			}
		}
		
		setLead();
		return this;
	}
	// subtract long
	public ArrayNum subtract(long x) {
		ArrayNum xtmp = new ArrayNum(x);
		subtract(xtmp);
		return this;
	}
	// if save is false, only give out result but do not save
	// if save is true, do normal subtract (save and give out)
	public ArrayNum subtract(ArrayNum x, boolean save) {
		if (save) 
			return subtract(x);
		
		ArrayNum ret = new ArrayNum(this);
		return ret.subtract(x);
	}
	
	
	// multiply other ArrayNum
	public ArrayNum mult(ArrayNum x) {
		setSign((this.sign && x.sign) || (!this.sign && !x.sign));
		int[] tmp1; 
		ArrayNum tmp = new ArrayNum();
		
		for (int i = 0; i <= this.lead; i++) {
			tmp1 = new int[this.lead+x.lead+2];
			for (int j = 0; j <= x.lead; j++) {
				tmp1[i+j] += this.num[i] * x.num[j];
				if (tmp1[i+j] >= 10) {
					tmp1[i+j+1] += tmp1[i+j] / 10;
					tmp1[i+j] %= 10;
				}
			}
			tmp.add(new ArrayNum(tmp1));
		}
		this.num = Arrays.copyOf(tmp.num, tmp.num.length);
		setLead();
		return this;
	}
	// multiply long
	public ArrayNum mult(long x) {
		ArrayNum xtmp = new ArrayNum(x);
		mult(xtmp);
		return this;
	}
	// if save is false, only give out result but do not save
	// if save is true, do normal mult (save and give out)
	public ArrayNum mult(ArrayNum x, boolean save) {
		if (save) 
			return subtract(x);
		
		ArrayNum ret = new ArrayNum(this);
		return ret.mult(x);
	}
	
	
	// calculate digit sum (only once) (ignores sign)
	public int digitSum() {
		int ret = 0;
		for (int i = 0; i <= lead; i++)
			ret += num[i];
		return ret;
	}
	// calculate final digit sum (reduce until one digit) (ignores sign)
	public int digitSumFinal() {
		int ret = digitSum();
		while(ret / 10 > 0) {
			ret = new ArrayNum(ret).digitSum();
		}
		return ret;
	}	
	
	
	
	// public methods (helper)
	
	// increment (increase by 1)
	public ArrayNum increment() {
		if (sign)
			incrementAbs();
		else
			decrementAbs();
		
		setLead();
		return this;
	}
	
	// decrement (decrease by 1)
	public ArrayNum decrement() {
		if (sign)
			decrementAbs();
		else
			incrementAbs();
		
		setLead();
		return this;
	}
	//helper methods for increment/decrement
	private void incrementAbs() {
		int ind = 0;
		num[ind]++;
		while (num[ind] == 10) {
			num[ind] = 0;
			ind++;
			if(ind >= num.length)
				resizeTo(num.length+1);
			num[ind]++;
		}
	}
	private void decrementAbs() {
		if (lead == 0 && get(0) == 0)
		{
			set(0,1);
			setSign(false);
			return;
		}
		int ind = 0;
		num[ind]--;
		while (num[ind] == -1) {
			num[ind] = 9;
			ind++;
			num[ind]--;
		}
		if (lead == 0 && get(0) == 0)
			setSign(true);
	}
	
	// get digit at position i
	// returns -1, if index out of bounds
	public int get(int i) {
		try {
			return num[i];
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Argument [" + i + "] is out of bounds. Returning -1.");
			e.printStackTrace();
			return -1;
		}
	}
	// set digit at position i to value x
	public boolean set(int i, int x) throws IllegalArgumentException {
		if ((x<0) || (x>9))
			throw new IllegalArgumentException("Argument [" + x + "] is not a digit.");
		try {
			num[i] = x;
			return true;
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Argument [" + i + "] is out of bounds. Ignoring.");
			e.printStackTrace();
			return false;
		}
	}
	
	// set ArrayNum from long
	public void setArrayNum(long numVal) {
		// set sign
		this.sign = (numVal >= 0);
		// get absolute value of numVal to set into array
		numVal = Math.abs(numVal);
		// set num
		this.num = new int[this.num.length];
		try {
			writeLongToArray(num,numVal);
		} catch(IllegalArgumentException e) {
			System.err.println(e.getMessage() + e.getStackTrace());
		}
		// set lead
		setLead();
	}
	
	// set ArrayNum from other ArrayNum
	public void setArrayNum(ArrayNum arrayNum) {
		this.num = Arrays.copyOf(arrayNum.num, arrayNum.num.length);
		this.lead = arrayNum.lead;
		this.sign = arrayNum.sign;
	}
	
	// set ArrayNum from Array and sign
	public void setArrayNum(int[] num, boolean sign) {
		this.num = Arrays.copyOf(num, num.length);
		this.sign = sign;
		setLead();
	}
	
	// set ArrayNum from Array (sign is true)
	public void setArrayNum(int[] num) {
		setArrayNum(num,true);
	}
	
	// get num as int
	public int getAsInt() {
		long ret = getAsLong();
		if (ret > Integer.MAX_VALUE || ret < Integer.MIN_VALUE)
			throw new ArithmeticException("Integer Overflow");
		return (int)ret;
	}
	
	// get num as long
	public long getAsLong() throws ArithmeticException {
		if (lead > 19)
			throw new ArithmeticException("Long Overflow");
		
		long ret = 0;
		for (int i = 0; i <= lead; i++)
		{
			ret += num[i]*(long)Math.pow(10, i);
			// as the array only consists of positive values and the largest digit of Long.MAX_VALUE > 5,
			// an overflow leads to a negative value.
			if (ret < 0)
				throw new ArithmeticException("Long Overflow");	
		}
		if (!sign)
			ret *= -1;
		return ret;
	}

	
	// private methods
	
	// set lead value
	private void setLead() {
		for (int i = num.length-1; i >= 0; i--) {
			if (num[i] > 0) {
				lead = i;
				return;
			}
		}
		lead = 0;
	}
	
	// resize num array to lead + 1
	private void resizeToMin() {
		num = Arrays.copyOf(num,Math.max(lead+1,20));
	}
	// resize num array to fixed length (only if >= max(lead+1,20)
	private void resizeTo(int len) {
		if (len < lead+1) {
			System.err.println("Length parameter too small for number. Not resizing.");
			return;
		}
		if (len < 20) {
			System.err.println("Length parameter too small. Ignoring, setting to 20.");
			len = 20;
		}
		num = Arrays.copyOf(num,len);
	}
		
	
	// static helper methods
	
	// write numVal in arr. starts from position 0, but does not overwrite, if values above numVal exist in arr.
	// throws IllegalArgumentException, if arr is not long enough.
	static private void writeLongToArray(int[] arr, long numVal) throws IllegalArgumentException {
		int ind = 0;
		long numValOrig = numVal;
		while(numVal != 0) {
			if (ind >= arr.length)
				throw new IllegalArgumentException("Array to short to write number. Length: " + arr.length + " Number: " + numValOrig);
			arr[ind] = (int) (numVal % 10);
			numVal /= 10;
			ind++;
		}
	}
	
	// compare method
	@Override
	public int compareTo(ArrayNum x) {
		// first check for signs
		if (this.sign && !x.sign)
			return 1;
		if (!this.sign && x.sign)
			return -1;
		
		// if signs are same, check for length
		if (this.lead > x.lead)
			return sign ? 1 : -1;
		if (this.lead < x.lead)
			return sign ? -1 : 1;
		
		// if lengths are the same, check digits from largest going down
		for (int i = lead; i >=0; i--) {
			if (this.num[i] > x.num[i])
				return sign ? 1 : -1;
			if (this.num[i] < x.num[i])
				return sign ? -1 : 1;
		}
		return 0;
	}
	// compare absolute values
	public int compareToAbs(ArrayNum x) {
		//check for length
		if (this.lead > x.lead)
			return 1;
		if (this.lead < x.lead)
			return -1;
		
		// if lengths are the same, check digits from largest going down
		for (int i = lead; i >=0; i--) {
			if (this.num[i] > x.num[i])
				return 1;
			if (this.num[i] < x.num[i])
				return -1;
		}
		return 0;
	}
	
	// standard methods
	
	// clone method
	@Override
	public ArrayNum clone() {
		return new ArrayNum(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lead;
		result = prime * result + Arrays.hashCode(Arrays.copyOf(num,lead+1)); // changed
		result = prime * result + (sign ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrayNum other = (ArrayNum) obj;
		if (lead != other.lead)
			return false;
		if (!Arrays.equals(Arrays.copyOf(num,lead+1), Arrays.copyOf(other.num,other.lead+1))) // changed
			return false;
		if (sign != other.sign)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ArrayNum [sign=" + sign + ", num=" + Arrays.toString(Arrays.copyOf(num,lead+1)) +  "]";
	}
	
	// Getters and Setters
	
	public int[] getNum() {
		return num;
	}
	public void setNum(int[] num) {
		this.num = num;
		setLead(); // set lead here
	}
	public int getLead() {
		return lead;
	}
	// do not allow setting of lead (needs to be done in setNum
	public boolean isSign() {
		return sign;
	}
	public void setSign(boolean sign) {
		this.sign = sign;
	}
	
	
		
	
	

}
