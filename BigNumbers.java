import java.util.Scanner;

public class BigNumber {
	
	private String num;
	private int sign;
	BigNumber(String n){
		sign = 0;
		num = n;
		if(n.charAt(0) == '-') {
			sign = 1;
			num = n.substring(1, num.length());
		}
	}
	public BigNumber Add(BigNumber b) {
		int n1, n2, carry = 0, s, borrow = 10;
		boolean bflag = false, fbflag = false;
		BigNumber sum = new BigNumber("0"), tmp = new BigNumber(b.num), tmp2 = new BigNumber(num);
		//adding zeros to make both bigNumbers have the same length
		if(num.length() > b.num.length()) {
			for(int i = b.num.length(); i < num.length(); i++)
				b.num = '0' + b.num; 
		}
		else {
			for(int i = num.length(); i < b.num.length(); i++)
				num = '0' + num;
		}
		if(sign == b.sign) {
			if(sign == 1)
				fbflag = true;
			for(int i = 1; i <= num.length(); i++) {
				n1 = num.charAt(num.length() - i) - '0'; //converting from a char to int
				n2 = b.num.charAt(b.num.length() - i) - '0';
				s = n1 + n2 + carry;
				carry = 0;
				if(s >= 10) carry = 1; //in the addition the carry is either 0 or 1
				sum.num = s % 10 + sum.num;
			}
			if(carry == 1) //if we finish adding and we still have carry
				sum.num = carry + sum.num; 
		}
		if(sign == 1 && b.sign == 0) {
			if(b.CompareTo(tmp2) == -1) { // different sign and b.num.length < this.num.length
				fbflag = true;
				for(int i = 1; i <= num.length(); i++) {
					n1 = num.charAt(num.length() - i) - '0';
					n2 = b.num.charAt(b.num.length() - i) - '0';
					if(bflag)
						n1--;
					if(n1 < n2) {
						s = (n1 + borrow) - n2;
						bflag = true;
						}
					else {
						s = n1 - n2;
						bflag = false;
					}
					sum.num = s % 10 + sum.num;
				}
			}
			else { // different sign and b.num.legth > this.num.leght
					for(int i = 1; i <= num.length(); i++) {
					n2 = num.charAt(num.length() - i) - '0';
					n1 = b.num.charAt(b.num.length() - i) - '0';
					if(bflag)
						n1--;
					if(n1 < n2) {
						s = (n1 + borrow) - n2;
						bflag = true;
						}
					else {
						s = n1 - n2;
						bflag = false;
					}
					sum.num = s % 10 + sum.num;
					}
				}
		}
		if(sign == 0 && b.sign == 1) {
			if(this.CompareTo(tmp) == -1) {
				fbflag = true;
				for(int i = 1; i <= num.length(); i++) {
					n2 = num.charAt(num.length() - i) - '0';
					n1 = b.num.charAt(b.num.length() - i) - '0';
					if(bflag)
						n1--;
					if(n1 < n2) {
						s = (n1 + borrow) - n2;
						bflag = true;
						}
					else {
						s = n1 - n2;
						bflag = false;
					}
					sum.num = s % 10 + sum.num;
				}
			}
			else {
					for(int i = 1; i <= num.length(); i++) {
					n1 = num.charAt(num.length() - i) - '0';
					n2 = b.num.charAt(b.num.length() - i) - '0';
					if(bflag)
						n1--;
					if(n1 < n2) {
						s = (n1 + borrow) - n2;
						bflag = true;
						}
					else {
						s = n1 - n2;
						bflag = false;
					}
					sum.num = s % 10 + sum.num;
					}
				}
		}
		if(fbflag) //if the overall addition is negative
			sum.num = '-' + sum.num;
		sum.num = sum.num.substring(0, sum.num.length() - 1);
		return sum;
	}
	public int CompareTo(BigNumber b) {
		int n1, n2;
		if(sign < b.sign)
			return 1;
		if(sign > b.sign)
			return -1;
		if(sign == 0) {
			if(num.length() > b.num.length())
				return 1;
			if(num.length() < b.num.length())
				return -1;
			for(int i = 0; i < num.length(); i++) {
				n1 = num.charAt(i) - '0';
				n2 = b.num.charAt(i) - '0';
				if(n1 > n2)
					return 1;
				if(n1 < n2)
					return -1;
			}
		}
		if(sign == 1) {
			if(num.length() > b.num.length())
				return 1;
			if(num.length() < b.num.length())
				return -1;
			for(int i = 0; i < num.length(); i++) {
				n1 = num.charAt(i) - '0';
				n2 = b.num.charAt(i) - '0';
				if(n1 > n2)
					return -1;
				if(n1 < n2)
					return 1;
			}
		}
		return 0;
	}
	public String toString() {
		return num;
	}
	public BigNumber multi(BigNumber b) {
		//using the function singleMulti to multiply a char with a string
		//using add function to add after each singleMulti call
		BigNumber result = new BigNumber("0"), multi = new BigNumber("0");
		if(b.num.length() < num.length()) {
			for(int i = 1, j = 0; i <= b.num.length(); i++, j++) {
				multi = this.singleMulti(b.num.charAt(b.num.length() - i), j);
				result = result.Add(multi);
			}
		}
		else
			for(int i = 1, j = 0; i <= num.length(); i++, j++) {
				multi = b.singleMulti(num.charAt(num.length() - i), j);
				result = result.Add(multi);
			}
		if(b.sign != sign)
			result.num = '-' + result.num;
		return result;
	}
	private BigNumber singleMulti(char c,int offset) {
		//this function multiply a digit (char) with a number (string)
		int n1, n2, result = 0, carry = 0;
		String returnValue = "";
		for(int i = 0; i < offset; i++)
			returnValue = returnValue + '0';
		//what does the offset do ..

		///////////////////////////////////////////////////////////////
		// Example:                                                  //
		//                                                           //
		//  123                                                      //
		//  *                                                        //
		//  123                                                      //
		//  ----                                                     //
		//   369        (we did not add any zero here)               //
		//   +                                                       //
		//  2460           (we added 1 zero here)                    //
		//   +                                                       //
		// 12300        (we added 2 zeros here)                      //
		//                                                           //
		//  the offset is how many zeros we should add before        //
		//  we begin the singleMulti function                        //
		///////////////////////////////////////////////////////////////

		n1 = c - '0';
		for(int i = 1; i <= num.length(); i++) {
			n2 = num.charAt(num.length() - i) - '0';
			result = n1 * n2 + carry;
			carry = result / 10;
			result %= 10;
			returnValue = result + returnValue;
		}
		if(carry > 0)
			returnValue = carry + returnValue;
		return new BigNumber(returnValue);
	}
	public BigNumber pow(int e) {
		BigNumber result = this;
		boolean sflag = false;
		if(sign == 1) { sflag = true; sign = 0;}
		for(int i = 1; i < e; i++) {
			result = result.multi(this);
		}
		if(sflag && e % 2 != 0)
			result.num = '-' + result.num;
		return result;
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String s;
		String [] k = new String[3];
		while(true) {
			s = input.nextLine();
			if(s.compareTo("quit") == 0)
				break;
			k = s.split(" ");
			BigNumber b1 = new BigNumber(k[0]);
			BigNumber b2 = new BigNumber(k[2]);
			switch(k[1]) {
				case "*" : System.out.println(b1.multi(b2));
							break;
				case "+" : System.out.println(b1.Add(b2));
							break;
				case "^" : System.out.println(b1.pow(Integer.parseInt(k[2])));
							break;
			}
		}
	}
}
