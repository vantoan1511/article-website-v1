package com.webtintuc.util;

import java.util.Map;

public interface Cache<K, V> {
    void put(K key, V value);

    V get(K key);

    void invalidate(K key);

    void invalidateAll();

    int size();

    Map<K, V> asMap();
}
