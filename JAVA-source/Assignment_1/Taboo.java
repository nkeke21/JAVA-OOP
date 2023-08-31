
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;
import java.util.Collection;
public class Taboo<T> {

	HashMap<T, HashSet<T>> map;
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {

		/* Go through the List of rules and save the specified character as key in the map and
		*  the set of characters, which should be after this specified character */
		this.map = new HashMap<T, HashSet<T>>();
		for(int i = 0; i < rules.size()-1; i ++){

			/* next lines are for saving the information in map, we should not take into account the
			*  situation when we meet the null in the list of rules */
			if(rules.get(i) != null && rules.get(i + 1) != null) {
				if (map.containsKey(rules.get(i))) {
					map.get(rules.get(i)).add(rules.get(i + 1));
				} else {
					HashSet<T> s = new HashSet<T>();
					s.add(rules.get(i + 1));
					map.put(rules.get(i), s);
				}
			}
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(map.containsKey(elem)) return  map.get(elem);
		HashSet<T> result = new HashSet<T>();
		return result; // YOUR CODE HERE
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		for (int i = 1; i < list.size(); i ++){
			if(map.containsKey(list.get(i-1))){

				/* Checks if the current Character is in the value of previous Character,
				*  If it is we should remove this Character from the list, otherwise do nothing */
				if(map.get(list.get(i-1)).contains(list.get(i))){
					list.remove(i);
					i --;
				}
			}
		}
	}
}
