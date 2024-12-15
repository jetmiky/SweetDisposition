package utils;

public class StringHelper {

	public static boolean isValidEmail(String email) {
		for (String ch : email.split("")) {
			if (ch.equals("@")) return true;
		}
		
		return false;
	}
	
	public static String hash(String text) {
		int MAX_LENGTH = 254;
		String result = "";

		for (int i = 0; result.length() < MAX_LENGTH; i++) {
			char ch = text.charAt(i);
			result += (char) (ch + 2);

			if (i == text.length() - 1)
				i = 0;
		}

		return result;
	}
	
	public static String capitalizeFirstLetter(String string) {
	    String letter = string.substring(0, 1).toUpperCase();
	    return letter + string.substring(1);
	}
}
