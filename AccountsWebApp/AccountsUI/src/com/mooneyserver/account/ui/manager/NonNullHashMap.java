package com.mooneyserver.account.ui.manager;

import java.util.HashMap;

class NonNullHashMap<K, V> extends HashMap<K, V> {


	private static final long serialVersionUID = 1L;
	
	@Override
	public V put(K key, V value) {
		if (key == null || value == null)
			return null;
		
		return super.put(key, value);
	}
	
	/**
	 * TODO
	 * @param value
	 * @return
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