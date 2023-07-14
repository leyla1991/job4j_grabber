package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {
    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public final void put(K key, V value) {
        if (!cache.containsKey(key)) {
            SoftReference<V> softReference = new SoftReference<>(value);
            cache.put(key, softReference);
        }
    }

    public final V get(K key) {
        V value = null;
        SoftReference<V> v = cache.get(key);
        if (v != null) {
            value = v.get();
        }
        return value;
    }

    protected abstract V load(K key);
}
