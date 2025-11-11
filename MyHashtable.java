package ch.zhaw.ads;

import java.util.*;

public class MyHashtable<K,V> implements java.util.Map<K,V> {
    private K[] keys =   (K[]) new Object[10];
    private V[] values = (V[]) new Object[10];

    private int hash(Object k) {
        if(k == null) return 0; // mayybe remove again
        int h = Math.abs(k.hashCode());
        return h % keys.length;
    }

    public MyHashtable(int size) {
        keys = (K[]) new Object[size];
        values = (V[]) new Object[size];
    }

    //  Removes all mappings from this map (optional operation). 
    public void clear() {
        // to be done
        Arrays.fill(keys, null);
        Arrays.fill(values, null);
    }

    public void resize() {
        K [] oldKeys = keys;
        V [] oldValues = values;
        keys = (K[]) new Object[oldKeys.length * 2];
        values = (V[]) new Object[oldValues.length * 2];

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null) {
                // Use internal put to avoid recursion
                int index = Math.abs(oldKeys[i].hashCode()) % keys.length;
                while (keys[index] != null) {
                    index = (index + 1) % keys.length;
                }
                keys[index] = oldKeys[i];
                values[index] = oldValues[i];
            }
        }
    }

    //  Associates the specified value with the specified key in this map (optional operation).
    public V put(K key, V value) {
        // to be done
        if(keys.length == size()) {
            resize();
        }

        int index = hash(key);
        int startIndex = index;

        while(keys[index] != null) {
            if(keys[index].equals(key)) {
                values[index] = value;
                return value;
            }
            index = (index + 1) % keys.length;
            if(index == startIndex) throw new RuntimeException("Hashtable full");
        }

        keys[index] = key;
        values[index] = value;
        return value;
    }

    //  Returns the value to which this map maps the specified key. 
    public V get(Object key) {
        // to be done
        int index = hash(key);
        int startIndex = index;

        while(keys[index] != key) {
            index = (index + 1) % keys.length;
            if(index == startIndex) return null;
        }

        return values[index];
    }

    //  Returns true if this map contains no key-value mappings. 
    public boolean isEmpty() {
        // to be done
        return size() == 0;
    }

    //  Removes the mapping for this key from this map if present (optional operation).
    public V remove(Object key) {
        // to be done (Aufgabe 3)
        int index = hash(key);
        int startIndex = index;

        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                V oldValue = values[index];
                keys[index] = null;
                values[index] = null;
                // Rehash subsequent keys to fill the gap
                int next = (index + 1) % keys.length;
                while (keys[next] != null) {
                    K rehashKey = keys[next];
                    V rehashValue = values[next];
                    keys[next] = null;
                    values[next] = null;
                    put(rehashKey, rehashValue);
                    next = (next + 1) % keys.length;
                }
                return oldValue;
            }
            index = (index + 1) % keys.length;
            if (index == startIndex) break;
        }
        return null;

    }

    //  Returns the number of key-value mappings in this map. 
    public int size() {
        // to be done
        int count = 0;
        for(K key : keys) {
            if (key != null) count++;
        }
        return count;
    }

    // =======================================================================
    //  Returns a set view of the keys contained in this map. 
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    //  Copies all of the mappings from the specified map to this map (optional operation). 
    public void putAll(Map t) {
        throw new UnsupportedOperationException();
    }

    //  Returns a collection view of the values contained in this map. 
    public Collection values() {
        throw new UnsupportedOperationException();
    }
    //  Returns true if this map contains a mapping for the specified key.
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }
    //  Returns true if this map maps one or more keys to the specified value. 
    public boolean containsValue(Object value)  {
        throw new UnsupportedOperationException();
    }
    //  Returns a set view of the mappings contained in this map.
    public Set entrySet() {
        throw new UnsupportedOperationException();
    }
    //  Compares the specified object with this map for equality. 
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }
    //  Returns the hash code value for this map.
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

}