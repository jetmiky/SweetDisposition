package utils;

public class StringHelper {

	private StringHelper() {

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
}
