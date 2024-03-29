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
		String result = "";
		try {
			result = input();
			if (!result.equals(""))   getMessage(calc(result));
		} catch (ManageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getMessage("Введенная строка ошибочна");
		}
	}
	
	/**
	 * 
	 * @param strMss
	 */
	public void getMessage(String strMss) {
		System.out.println(strMss);
	}
	
	// method reads a string from the keyboard
    public String input() throws ManageException {
		getMessage("Введите строку символов для вычисления или \"#\", чтобы прервать процесс :");
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String result = "";
		String str = "";
		try {
			str = read.readLine();
			if (str.equals("") == true)   throw new ManageException("т.к. Отправленная строка пуста...");
			else   result = str.replaceAll("\\s", "");
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
	
    /**
     * 
     * @param input
     * @return
     * @throws ManageException
     */
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
								throw new ManageException("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
						}
					}
				}
			}
		}
    	
		String res = "";
		if((!partLeft.equals("") && operation.equals("") && partRight.equals("")) || 
				(!partLeft.equals("") && !operation.equals("") && partRight.equals("")) || 
				    (partLeft.equals("") && !operation.equals("") && partRight.equals("")) || 
				        (partLeft.equals("") && !operation.equals("") && !partRight.equals("")))
			throw new ManageException("т.к. строка не является математической операцией");
		else {
			if((romanNumber(partLeft) == true) && (romanNumber(partRight) == true)) {
				if((romanIsCalculable(partLeft) == false) || (romanIsCalculable(partRight) == false))
					throw new ManageException("т.к. Ваши римские цифры не должны быть > X");
				else    res = selectRomanOp(partLeft, partRight, operation);
			} else {
				if((((romanNumber(partLeft) == true) && (arabicNumber(partRight) == true)) || 
					((arabicNumber(partLeft) == true) && (romanNumber(partRight) == true))) ||
				   (((romanNumber(partLeft) == false) && (arabicNumber(partRight) == false)) || 
					((arabicNumber(partLeft) == false) && (romanNumber(partRight) == false)))) {
					throw new ManageException("т.к. Используются одновременно разные системы счисления");
				} else {
					if((arabicNumber(partLeft) == true) && (arabicNumber(partRight) == true)) {
						if((Integer.parseInt(partLeft) > 10) || (Integer.parseInt(partRight) > 10))
							throw new ManageException("т.к. Ваши числа не должны быть > 10");
						else    res += selectArabicOp(Integer.parseInt(partLeft), Integer.parseInt(partRight), operation);
					}
				}
			}
		}
		return res;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public boolean isOperator(String str) {
		switch(str) {
			case "+":	return true;
			case "-":	return true;
			case "*":	return true;
			case "/":	return true;
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
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

	/**
	 * 
	 * @param str
	 * @return
	 */
	public boolean isRoman(String str) {
		switch(str) {
			case "I":	return true;
			case "V":	return true;
			case "X":	return true;
			case "L":	return true;
			case "C":	return true;
			case "D":	return true;
			case "M":	return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public boolean romanNumber(String str) {
		int i = 0;
		while(i < str.length()) {
			if(isRoman(str.substring(i, (i + 1))) == false)   return false;
			else   i++;
		}
		return true;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public boolean arabicNumber(String str) {
		int i = 0;
		while(i < str.length()) {
			if(isInteger(str.substring(i, (i + 1))) == false)   return false;
			else   i++;
		}
		return true;
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @param oper
	 * @return
	 * @throws ManageException
	 */
	public String selectRomanOp(String strLeft, String strRight, String oper) throws ManageException {
		switch(oper) {
			case "+":	return romanAdd(strLeft, strRight);
			case "-":	return romanSub(strLeft, strRight);
			case "*":	return romanMult(strLeft, strRight);
			case "/":	return romanDiv(strLeft, strRight);
		}
		return "Error";
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @param oper
	 * @return
	 */
	public int selectArabicOp(int strLeft, int strRight, String oper) {
		switch(oper) {
			case "+":	return arabicAdd(strLeft, strRight);
			case "-":	return arabicSub(strLeft, strRight);
			case "*":	return arabicMult(strLeft, strRight);
			case "/":	return arabicDiv(strLeft, strRight);
		}
		return 0;
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @return
	 */
	public String romanAdd(String strLeft, String strRight) {
		int res = getArrabic(strLeft) + getArrabic(strRight);
		return correspondRoman(res);
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @return
	 * @throws ManageException
	 */
	public String romanSub(String strLeft, String strRight) throws ManageException {
		int res = getArrabic(strLeft) - getArrabic(strRight);
		if(res < 0)	  throw new ManageException("т.к. в римской системе нет отрицательных чисел");
		else   return correspondRoman(res);
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @return
	 */
	public String romanMult(String strLeft, String strRight) {
		int res = getArrabic(strLeft) * getArrabic(strRight);
		return correspondRoman(res);
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @return
	 */
	public String romanDiv(String strLeft, String strRight) {
		int res = getArrabic(strLeft) / getArrabic(strRight);
		return correspondRoman(res);
	}

	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @return
	 */
	public int arabicAdd(int strLeft, int strRight) {
		return (strLeft + strRight);
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @return
	 */
	public int arabicSub(int strLeft, int strRight) {
		return (strLeft - strRight);
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @return
	 */
	public int arabicMult(int strLeft, int strRight) {
		return (strLeft * strRight);
	}
	
	/**
	 * 
	 * @param strLeft
	 * @param strRight
	 * @return
	 */
	public int arabicDiv(int strLeft, int strRight) {
		return (strLeft / strRight);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public String correspondRoman(int value) {
		String strRoman = "";
		if(value < 10)	 strRoman = getRoman(value);
		else {
			String str = "" + value;
			if(str.substring(1, 2).equals("0"))	  strRoman += getRoman(value);
			else {
				do {
					int tail = str.length();
					int first = Integer.parseInt(str.substring(0, 1));
					if(tail == 2) {
						strRoman += getRoman(first * 10);
					} else {
						if(tail == 3) {
							strRoman += getRoman(first * 100);
						}
					}
					str = str.substring(1, str.length());
				} while(str.length() >= 2);
				strRoman += getRoman(Integer.parseInt(str.substring(0, 1)));
			}
		}
		return strRoman;
	}
	
	
	public boolean romanIsCalculable(String str_value) {
		if(getArrabic(str_value.substring(0, 1)) > 10)   return false;
		else {
			if(str_value.length() > 1) {
				if(getArrabic(str_value.substring(0, 1)) == 10)   return false;
				else   return true;
			} else   return true;
		}
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
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
			case 200:	return "CC";
			case 300:	return "CCC";
			case 400:	return "CD";
			case 500:	return "D";
			case 600:	return "DC";
			case 700:	return "DCC";
			case 800:	return "DCCC";
			case 900:	return "CM";
			case 1000:	return "M";
			case 2000:	return "MM";
			case 3000:	return "MMM";
			case 4000:	return "MMMM";
		}
		return "";
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
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
			case "XX":	return 20;
			case "XXX":	return 30;
			case "XL":	return 40;
			case "L":	return 50;
			case "LX":	 return 60;
			case "LXX":	  return 70;
			case "LXXX":	return 80;
			case "XC":	return 90;
			case "C":	return 100;
			case "CC":	return 200;
			case "CCC":	return 300;
			case "CD":	return 400;
			case "D":	return 500;
			case "DC":	 return 600;
			case "DCC":	  return 700;
			case "DCCC":	return 800;
			case "CM":	return 900;
			case "M":	return 1000;
			case "MM":	 return 2000;
			case "MMM":	  return 3000;
			case "MMMM":	return 4000;
		}
		return 0;
	}
}