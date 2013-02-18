package com.mooneyserver.account.ui.manager;

import java.util.HashMap;

/**
 * Implementation of HashMap which disallows
 * nulls. Utility method for easily removing a 
 * values has also been added. It is enforced
 * that there are no duplicate values
 * 
 * @author brian_mooney
 *
 * @param <K>
 * @param <V>
 */
class NonNullHashMap<K, V> extends HashMap<K, V> {


	private static final long serialVersionUID = 1L;
	
	@Override
	public V put(K key, V value) {
		if (key == null || value == null)
			return null;
		
		if (containsValue(value))
			return null;
		
		return super.put(key, value);
	}
	
	/**
	 * Remove the value from the map
	 * and return its corresponding key.
	 * Return null if there was no value.
	 * 
	 * @param value
	 * 	<V>
	 * 
	 * @return
	 * 	<K> key 
	 */
	public K removeValue(V value) {
		for (K key : keySet()) {
			if (get(key).equals(value)) {
				remove(key);
				return key;
			}
		}
		
		return null;
	}
}