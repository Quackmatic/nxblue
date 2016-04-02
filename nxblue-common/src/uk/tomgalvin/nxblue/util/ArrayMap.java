package uk.tomgalvin.nxblue.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A replacement for HashMap which doesn't exist in leJOS.
 * 
 * @author Tom Galvin
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 */
public class ArrayMap<K, V> {
	private ArrayList<K> keys;
	private ArrayList<V> values;
	
	/**
	 * Create a new {@link ArrayMap} with nothing in it.
	 */
	public ArrayMap() {
		keys = new ArrayList<K>();
		values = new ArrayList<V>();
	}
	
	/**
	 * Puts a value into the map, replacing the value associated
	 * with the key if the key already exists.
	 * 
	 * @param key The key to add.
	 * @param value The value to associate to {@code key}.
	 */
	public void put(K key, V value) {
		int index = keys.indexOf(key);
		
		if(index >= 0) {
			values.set(index, value);
		} else {
			keys.add(key);
			values.add(value);
		}
	}
	
	/**
	 * Gets the value associated with {@code key}, or returns {@code null}
	 * if the value does not exist in the map.
	 * 
	 * @param key The key whose value to return.
	 * @return The value associated with {@code key}, or {@code null} of it does
	 * not exist.
	 */
	public V get(K key) {
		int index = keys.indexOf(key);
		if(index >= 0) {
			return values.get(index);
		} else {
			return null;
		}
	}
	
	/**
	 * Determines if {@code key} exists as a key in the map.
	 * 
	 * @param key The key to search for.
	 * @return Whether the key exists in the map or not.
	 */
	public boolean containsKey(K key) {
		return keys.contains(key);
	}
	
	/**
	 * Determines if {@code value} exists as a value in the map. Note that
	 * values in maps are not unique, so if this methos returns true, then
	 * the value may occur one or more times in the map, associated with
	 * different keys.
	 * 
	 * @param value The value to search for.
	 * @return Whether the value occurs in the map or not.
	 */
	public boolean containsValue(V value) {
		return values.contains(value);
	}
	
	/**
	 * Removes the given key, and the associated value, from the map.
	 * 
	 * @param key The key to remove from the map.
	 * @throws IllegalArgumentException When the given key does not exist
	 * in the map.
	 */
	public void remove(K key) {
		int index = keys.indexOf(key);
		if(index >= 0) {
			keys.remove(index);
			values.remove(index);
		} else {
			throw new IllegalArgumentException(
					"The key " + key.toString() + " does not exist in the map."
				);
		}
	}
	
	/**
	 * Gets a list of all of the keys in the map.
	 * The keys occur in no particular order.
	 * 
	 * @return All of the keys that occur in the map.
	 */
	public List<K> keySet() {
		return keys;
	}
}
