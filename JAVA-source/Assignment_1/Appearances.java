import java.util.*;
import java.util.Objects;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		HashMap<Integer, Integer> frequency1 = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> frequency2 = new HashMap<Integer, Integer>();

		/* Go through the both string and save the number of times each Character appears in String */
		for (Iterator<T> iterator = a.iterator(); iterator.hasNext();) {
			Integer curr = Objects.hash(iterator.next());
			if(!frequency1.containsKey(curr)){
				frequency1.put(curr, 1);
			}else{
				int value = frequency1.get(curr);
				frequency1.put(curr, value + 1);
			}
		}

		for (Iterator<T> iterator = b.iterator(); iterator.hasNext();) {
			Integer curr = Objects.hash(iterator.next());
			if(!frequency2.containsKey(curr)){
				frequency2.put(curr, 1);
			}else{
				int value = frequency2.get(curr);
				frequency2.put(curr, value + 1);
			}
		}
		/* Go through the frequency1 and detect for each map key elements if their values equals
		*  the value of key elements of frequency2 (while checking: key1 must equals key2) */
		return count(frequency1, frequency2);
	}


	static int count(HashMap<Integer, Integer> frequency1, HashMap<Integer, Integer> frequency2){
		int result = 0;
		for (Map.Entry<Integer, Integer> entry : frequency1.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();
			if(frequency2.get(key) == value) {
				result++;
			}
		}
		return result;
	}
}
