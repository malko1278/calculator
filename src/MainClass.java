/**
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainClass();
	}
	
	public MainClass() {
		super();
	
		System.out.println("Enter the character string to calculate :");
		BufferedReader read = new BufferedReader (new InputStreamReader(System.in));
		String result = "";
		String str = "";
		try {
			str = read.readLine();
			result = calc(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
	}
	
	public static String calc(String input) {
		String partLeft = "";
		String partRight = "";
		String operation = "";
		int pos = 0;
		for(int i = 0; i < input.length(); i++)	{
			if((pos == 0) && (isOperator(input.substring(i, (i + 1))) == false)) {
				if(!input.substring(i, (i + 1)).equals(" "))   partLeft += input.substring(i, (i + 1));
			} else {
				if((pos == 0) && (isOperator(input.substring(i, (i + 1))) == true)) {
					operation += input.substring(i, (i + 1));
					pos = 1;
				} else {
					if((pos == 1) && (isOperator(input.substring(i, (i + 1))) == false)) {
						if(!input.substring(i, (i + 1)).equals(" "))   partRight += input.substring(i, (i + 1));
					} else {
						if((pos == 1) && (isOperator(input.substring(i, (i + 1))) == true)) {
							if(!operation.equals(""))
								return "throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)";
						}
					}
				}
			}
		}
		String res = "";
		if((romanNumber(partLeft) == true) && (romanNumber(partRight) == true)) {
			res = selectRomanOp(partLeft, partRight, operation);
		} else {
			if(((romanNumber(partLeft) == true) && (arabicNumber(partRight) == true)) || 
					((arabicNumber(partLeft) == true) && (romanNumber(partRight) == true))) {
				res = "throws Exception //т.к. используются одновременно разные системы счисления";
			} else {
				if((arabicNumber(partLeft) == true) && (arabicNumber(partRight) == true)) {
					res += selectArabicOp(Integer.parseInt(partLeft), Integer.parseInt(partRight), operation);
				}
			}
		}
		return res;
	}
	
	
	public static boolean arabicNumber(String str) {
		boolean exist = true;
		int i = 0;
		while((i < str.length()) && (exist == true)) {
			if((isInteger(str.substring(i, (i + 1)))) == false)   return false;
			else  i++;
		}
		return true;
	}
	
	
	public static boolean romanNumber(String str) {
		boolean exist = true;
		int i = 0;
		while((i < str.length()) && (exist == true)) {
			if((isInteger(str.substring(i, (i + 1)))) == true)   return false;
			else  i++;
		}
		
		return true;
	}
	
	
	public static boolean isOperator(String str) {
		switch(str) {
			case "+":	return true;
			case "-":	return true;
			case "*":	return true;
			case "/":	return true;
		}
		return false;
	}
	
	
	public static boolean isInteger(String str) {
		switch(str) {
			case "0":	return true;
			case "1":	return true;
			case "2":	return true;
			case "3":	return true;
			case "4":	return true;
			case "5":	return true;
			case "6":	return true;
			case "7":	return true;
			case "8":	return true;
			case "9":	return true;
		}
		return false;
	}
	
	
	public static int arabicAdd(int strLeft, int strRight) {
		return (strLeft + strRight);
	}
	
	
	public static int arabicSub(int strLeft, int strRight) {
		return (strLeft - strRight);
	}
	
	
	public static int arabicMult(int strLeft, int strRight) {
		return (strLeft * strRight);
	}
	
	
	public static int arabicDiv(int strLeft, int strRight) {
		return (strLeft / strRight);
	}
	
	
	public static int selectArabicOp(int strLeft, int strRight, String oper) {
		switch(oper) {
			case "+":	return arabicAdd(strLeft, strRight);
			case "-":	return arabicSub(strLeft, strRight);
			case "*":	return arabicMult(strLeft, strRight);
			case "/":	return arabicDiv(strLeft, strRight);
		}
		return 0;
	}
	
	
	public static String romanAdd(String strLeft, String strRight) {
		int res = getArrabic(strLeft) + getArrabic(strRight);
		return correspondRoman(res);
	}
	
	
	public static String romanSub(String strLeft, String strRight) {
		int res = getArrabic(strLeft) - getArrabic(strRight);
		System.out.println("res = " + res);
		if(res < 0)  return "throws Exception //т.к. в римской системе нет отрицательных чисел";
		else   return correspondRoman(res);
	}
	
	
	public static String romanMult(String strLeft, String strRight) {
		int res = getArrabic(strLeft) * getArrabic(strRight);
		return correspondRoman(res);
	}
	
	
	public static String romanDiv(String strLeft, String strRight) {
		int res = getArrabic(strLeft) / getArrabic(strRight);
		return correspondRoman(res);
	}
	
	
	public static String selectRomanOp(String strLeft, String strRight, String oper) {
		
		switch(oper) {
			case "+":	return romanAdd(strLeft, strRight);
			case "-":	return romanSub(strLeft, strRight);
			case "*":	return romanMult(strLeft, strRight);
			case "/":	return romanDiv(strLeft, strRight);
		}
		return "Error";
	}
	
	
	public static String correspondRoman(int value) {
		String strRoman = "";
		if(value < 10)	 strRoman = getRoman(value);
		else {
			String str = "" + value;
			if(str.substring(1, 2).equals("0")) {
				strRoman += getRoman(value);
			} else {
				int first = Integer.parseInt(str.substring(0, 1));
				strRoman = getRoman(first * 10);
				strRoman += getRoman(Integer.parseInt(str.substring(1, 2)));
			}
		}
		return strRoman;
	}
	
	
	public static String getRoman(int value) {
		switch(value) {
			case 1:	   return "I";
			case 2:	   return "II";
			case 3:	   return "III";
			case 4:	   return "IV";
			case 5:	   return "V";
			case 6:	   return "VI";
			case 7:	   return "VII";
			case 8:	   return "VIII";
			case 9:	   return "IX";
			case 10:	return "X";
			case 20:	return "XX";
			case 30:	return "XXX";
			case 40:	return "XL";
			case 50:	return "L";
			case 60:	return "LX";
			case 70:	return "LXX";
			case 80:	return "LXXX";
			case 90:	return "XC";
			case 100:	return "C";
		}
		return "";
	}
	
	
	public static int getArrabic(String str) {
		switch(str) {
			case "I":	return 1;
			case "II":	return 2;
			case "III":	return 3;
			case "IV":	return 4;
			case "V":	return 5;
			case "VI":	return 6;
			case "VII":	return 7;
			case "VIII":	return 8;
			case "IX":	return 9;
			case "X":	return 10;
		}
		return 0;
	}
}