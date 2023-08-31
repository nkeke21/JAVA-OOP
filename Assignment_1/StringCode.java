import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {
	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		int result = 0; // Saves the max amount of adajcent chars

		for(int i = 0; i < str.length(); i ++){
			/* Saves the maximum amount of specified adjacent character from the i */
			int count = maxFrom(i, str, str.charAt(i));
			if(count > result) {
				result = count;
				i += count - 1; // By increasing i with count - 1, we do not need review extra cases
			}
		}
		return result;
	}

	/* Returns the maximum amount of specified adjacent character from the start */
	static int maxFrom(int start, String source, char ch){
		int result = 0;
		for(int i = start; i < source.length(); i ++){
			if(source.charAt(i) != ch) break;
			else result ++;
		}
		return result;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		/* String result which will be returned after some computation*/
		String result = "";

		/* Go through the String and modify it*/
		for(int i = 0; i < str.length(); i ++){
			/* Condition when we meet the character which is number */
			if(str.charAt(i) >= '0' && str.charAt(i) <= '9' && i < str.length() - 1){
				/* Converting the character to int and add the result count amount of next indexed character */
				int count = str.charAt(i) - '0';
				for(int j = 0; j < count; j ++){
					result += str.charAt(i + 1);
				}
				/* In this case we only need to add specified Chacater */
			} else if(str.charAt(i) < '0' || str.charAt(i) > '9'){
				result += str.charAt(i);
			}
		}
		return result;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		/* Necessary condition which must be checked. a and b lengths must be greater than len */
		if (a.length() < len || b.length() < len) {
			return false;
		}

		/* Initizialize Variables */
		long prime = 1000000007L;
		long base = 26L;

		long hash1 = 0L, hash2 = 0L;
		long power = 1L;

		/* Set contains hashed Strings */
		HashSet<Long> set = new HashSet<>();

		/* Get known hashes of first substrings of a and b with length len */
		for (int i = 0; i < len; i++) {
			hash1 = (hash1 * base + a.charAt(i)) % prime;
			hash2 = (hash2 * base + b.charAt(i)) % prime;
			power = (power * base) % prime;
		}

		/* Condition when the first substrings of a and b with length len equals each other */
		set.add(hash1);
		if(set.contains(hash2)) return true;

		/* Go through a String and add hashed substring in set */
		for (int i = len; i < a.length(); i++) {
			hash1 = (hash1 * base - a.charAt(i - len) * power % prime + prime) % prime;
			hash1 = (hash1 + a.charAt(i)) % prime;
			set.add(hash1);
		}

		/* Go through b String and check if current hashed substring[i - len, i] is in the set */
		for (int i = len; i < b.length(); i++) {
			hash2 = (hash2 * base - b.charAt(i - len) * power % prime + prime) % prime;
			hash2 = (hash2 + b.charAt(i)) % prime;
			if (set.contains(hash2)) {
				return true;
			}
		}

		/* There is not any matching substrings in a and b*/
		return false;
	}
}
