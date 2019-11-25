package ext.java;

import java.util.HashMap;

public class ADT {
	public static <E, T> T getOrDefault(HashMap<E, T> map, E key, T def) {
		T o = map.get(key);
		return o == null ? def : o;
	}
}
