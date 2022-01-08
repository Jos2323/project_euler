//Euler Problem 65 (http://projecteuler.net/problem=65 )

/* Problem:	
	Convergents of e

The square root of 2 can be written as an infinite continued fraction.

sqrt(2) = 1 + ( 1 / 2 + ( 1 / 2 + ( 1 / 2 + ( 1 + ...

The infinite continued fraction can be written, sqrt(2) = [1;(2)], 
(2) indicates that 2 repeats ad infinitum. In a similar way, sqrt(23) = [4;(1,3,1,8)].

It turns out that the sequence of partial values of continued fractions 
for square roots provide the best rational approximations. 
Let us consider the convergents for sqrt(2).

1 +	1 / 2 = 3/2

1 +	1 / ( 2 + 1 / 2 ) = 7/5

1 + 1 / ( 2 + 1 / ( 2 + 1 / 2 )) = 17/12

1 + 1 / ( 2 + 1 / ( 2 + 1 / ( 2 + 1 / 2 ))) = 41/29
 
Hence the sequence of the first ten convergents for sqrt(2) are:

1, 3/2, 7/5, 17/12, 41/29, 99/70, 239/169, 577/408, 1393/985, 3363/2378, ...
What is most surprising is that the important mathematical constant,
e = [2; 1,2,1, 1,4,1, 1,6,1 , ... , 1,2k,1, ...].

The first ten terms in the sequence of convergents for e are:

2, 3, 8/3, 11/4, 19/7, 87/32, 106/39, 193/71, 1264/465, 1457/536, ...
The sum of digits in the numerator of the 10th convergent is 1+4+5+7=17.

Find the sum of digits in the numerator of the 100th convergent of the continued fraction for e.


*/

/* Lösung (Nr. 24062, 2018/03/11): (difficulty rating 15%)

	272

*/

// Erklärung siehe unterhalb des Programms

//

package euler.euler51_100;

import java.util.ArrayList;
import java.util.List;

import utils.ArrayNum;
import utils.Fract;

public class Euler65 {

	public static void main(String[] args) {

		long timer = System.currentTimeMillis();

		int solution = 0;
		
		int conv = 100;
		
		List<Integer> fracList = getFracList(conv);

//		long v = 1;
//		long w = fracList.get(conv-1);
		
		ArrayNum v = new ArrayNum(1);
		ArrayNum w = new ArrayNum(fracList.get(conv-1));
		
		for (int i = conv-2; i >=0; i--) {
			ArrayNum v_tmp = new ArrayNum(w);
			v_tmp.mult(fracList.get(i).longValue());
			v_tmp.add(v);
			v.setArrayNum(w);
			w.setArrayNum(v_tmp);
			w = v_tmp;
			
//			long[] frac_tmp = Fract.reduce(v,w); 
//			v = frac_tmp[0];
//			w = frac_tmp[1];
		}
		
		System.out.println("v : " + w.toString());
		System.out.println("w : " + v.toString());
	
		solution = w.digitSum();
		
//		while (w > 0) {
//			solution += w % 10;
//			w /= 10;
//		}
		


		System.out.println("Result: " + solution);

		System.out.println("Elapsed time: " + (System.currentTimeMillis() - timer) + "ms");
	}
	
	static List<Integer> getFracList(int len) {
		List<Integer> ret = new ArrayList<>();
		ret.add(2);		
		for (int i = 1; i < len; i++) {
			if (i%3 == 2) {
				ret.add(2 * (i+1)/3);
			} else {
				ret.add(1);
			}
		}
		return ret;
	}
	
}

/*
Erklärung: 
	1. Grundüberlegungen: 
		- nehme List der Zeichen fuer die Convergents und durchlaufe sie
		- fange ganz hinten an, berechne Teilbruch, nehme Kehrwert, rechne weiter
	2. Brute Force Prinzip: 
		a) einfach durchrechnen 
	3. Optimierungen: 
		- 
	4. Problematik: 
		-

 */
