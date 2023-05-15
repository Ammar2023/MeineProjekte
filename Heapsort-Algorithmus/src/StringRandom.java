import java.util.Random;
import java.util.Scanner;

import javax.management.RuntimeErrorException;
/**
 * in dieser Klasse wird eine zufällige Zeichenkette erzeugt.
 *
 */
public class StringRandom {
	
	public static void main(String[] args) {
		
		 
		
		System.out.println("bitte geben die Länge der Zeichenkette ein : ");
		Scanner scan= new Scanner(System.in);
		int lenge=scan.nextInt();
		StringRandom random= new StringRandom();
		;
		System.out.println("Randomstring : "+ random.stringRandom(lenge));
	}

	/**
	 * generiert Random Strings aus utf8 mit der Länge len, die von dem Benutzer
	 * eingegeben wird
	 * 
	 * @param length
	 * @return
	 */
	public String stringRandom(int length) {

		String str = "abcedghlmnobskroslmmzuertouiposadfgfyxcvtuziuwruzoirtnbthfkltrzhg";
		StringBuilder sb = new StringBuilder();
		try {

			Random random = new Random();

			for (int i = 0; i < length; i++) {

				// generate random index number
				int zufallsZahlen = random.nextInt(str.length());

				char randomChar = str.charAt(zufallsZahlen);
				sb.append(randomChar);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String returnString = sb.toString();

		return returnString;
	}

}
