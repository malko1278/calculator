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
		
		try {
			String result = input();
			if (!result.equals(""))	   System.out.println(calc(result));
		} catch (ManageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Введенная строка ошибочна");
		}
	}
	
	// method reads a string from the keyboard
    public String input() throws ManageException {

		System.out.println("Введите строку символов для вычисления :");
    	// that the method can throw MyException
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String result = "";
		String str = "";
		try {
			str = read.readLine();
			if (str.equals("") == true)   throw new ManageException("т.к. Отправленная строка пуста...");
			else   result = str;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ManageException("т.к. Ошибка чтения с клавиатуры...");
		} finally {
			try {
				read.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
    	return result;
    }
	
    
    public String calc(String input) throws ManageException {
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
								// return "throws Exception //";
								throw new ManageException("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
						}
					}
				}
			}
		}
    	
		String res = "";
		if((romanNumber(partLeft) == true) && (romanNumber(partRight) == true)) {
			res = selectRomanOp(partLeft, partRight, operation);
		} else {
			if((((romanNumber(partLeft) == true) && (arabicNumber(partRight) == true)) || 
				((arabicNumber(partLeft) == true) && (romanNumber(partRight) == true))) ||
			   (((romanNumber(partLeft) == false) && (arabicNumber(partRight) == false)) || 
				((arabicNumber(partLeft) == false) && (romanNumber(partRight) == false)))) {
				throw new ManageException("т.к. используются одновременно разные системы счисления");
			} else {
				if((arabicNumber(partLeft) == true) && (arabicNumber(partRight) == true)) {
					res += selectArabicOp(Integer.parseInt(partLeft), Integer.parseInt(partRight), operation);
				}
			}
		}
		return res;
	}
	
	
	public boolean isOperator(String str) {
		switch(str) {
			case "+":	return true;
			case "-":	return true;
			case "*":	return true;
			case "/":	return true;
		}
		return false;
	}

	
	public boolean isInteger(String str) {
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
	
	
	public boolean romanNumber(String str) {
		boolean exist = true;
		int i = 0;
		while((i < str.length()) && (exist == true)) {
			if((isInteger(str.substring(i, (i + 1)))) == true)   return false;
			else  i++;
		}
		return true;
	}
	
	
	public boolean arabicNumber(String str) {
		boolean exist = true;
		int i = 0;
		while((i < str.length()) && (exist == true)) {
			if((isInteger(str.substring(i, (i + 1)))) == false)   return false;
			else  i++;
		}
		return true;
	}
	
	
	public String selectRomanOp(String strLeft, String strRight, String oper) throws ManageException {
		
		switch(oper) {
			case "+":	return romanAdd(strLeft, strRight);
			case "-":	return romanSub(strLeft, strRight);
			case "*":	return romanMult(strLeft, strRight);
			case "/":	return romanDiv(strLeft, strRight);
		}
		return "Error";
	}
	
	
	public int selectArabicOp(int strLeft, int strRight, String oper) {
		switch(oper) {
			case "+":	return arabicAdd(strLeft, strRight);
			case "-":	return arabicSub(strLeft, strRight);
			case "*":	return arabicMult(strLeft, strRight);
			case "/":	return arabicDiv(strLeft, strRight);
		}
		return 0;
	}
	
	
	public String romanAdd(String strLeft, String strRight) {
		int res = transformArrabic(strLeft) + transformArrabic(strRight);
		return correspondRoman(res);
	}
	
	
	public String romanSub(String strLeft, String strRight) throws ManageException {
		int res = transformArrabic(strLeft) - transformArrabic(strRight);
		System.out.println("res = " + res);
		if(res < 0)	  throw new ManageException("т.к. в римской системе нет отрицательных чисел");
		else   return correspondRoman(res);
	}
	
	
	public String romanMult(String strLeft, String strRight) {
		int res = transformArrabic(strLeft) * transformArrabic(strRight);
		return correspondRoman(res);
	}
	
	
	public String romanDiv(String strLeft, String strRight) {
		int res = transformArrabic(strLeft) / transformArrabic(strRight);
		return correspondRoman(res);
	}

	
	public int arabicAdd(int strLeft, int strRight) {
		return (strLeft + strRight);
	}
	
	
	public int arabicSub(int strLeft, int strRight) {
		return (strLeft - strRight);
	}
	
	
	public int arabicMult(int strLeft, int strRight) {
		return (strLeft * strRight);
	}
	
	
	public int arabicDiv(int strLeft, int strRight) {
		return (strLeft / strRight);
	}
	
	
	public String correspondRoman(int value) {
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
	
	
	public int transformArrabic(String strValue) {
		int val = 0;
		if((strValue.length() > 1) && (getArrabic(strValue.substring(0, 1)) >= 10)) {
			int i = 0;
			boolean end = false;
			do {
				int ent = getArrabic(strValue.substring(i, i + 1));
				if(ent == 10) {
					if(strValue.length() > (i + 1)) {
						int entP = getArrabic(strValue.substring(i+1, i + 2));
						if(entP > 10) {
							if(entP == 50)	ent = 40;
							if(entP == 100)	ent = 90;
							i +=2;
						} else	i++;
					} else	 end = true;
					val += ent;
				} else {
					if(ent > 10) {
						val += ent;
						i++;
					} else {
						val += getArrabic(strValue.substring(i, strValue.length()));
						end = true;
					}
				}
			}while((i < strValue.length()) && (end == false));
		} else   val = getArrabic(strValue);
		return val;
	}
	
	
	public String getRoman(int value) {
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
			case "L":	return 50;
			case "C":	return 100;
		}
		return 0;
	}
}