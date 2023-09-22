package com.webtintuc.util;

import com.webtintuc.service.impl.UserSession;

import javax.enterprise.context.ApplicationScoped;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
public class SessionCache implements Cache<String, UserSession> {
    private Map<String, UserSession> cacheMap = new LinkedHashMap<>();
    private int maxEntries = 100;

    @Override
    public void put(String key, UserSession value) {
        if (cacheMap.size() >= maxEntries) {
            cacheMap.remove(cacheMap.keySet().iterator().next());
        }
        cacheMap.put(key, value);
    }

    @Override
    public UserSession get(String key) {
        return cacheMap.get(key);
    }

    @Override
    public void invalidate(String key) {
        cacheMap.remove(key);
    }

    @Override
    public void invalidateAll() {
        cacheMap.clear();
    }

    @Override
    public int size() {
        return cacheMap.size();
    }

    @Override
    public Map<String, UserSession> asMap() {
        return cacheMap;
    }
}
