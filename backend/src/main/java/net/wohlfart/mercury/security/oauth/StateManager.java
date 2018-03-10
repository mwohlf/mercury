package net.wohlfart.mercury.security.oauth;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StateManager {

    // TODO: keys are easy to guess, might need to change that
    AtomicInteger nextState = new AtomicInteger(1);

    private final LoadingCache<String, State> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build(new CacheLoader<String, State>() {
                @Override
                public State load(String key) {
                    return new State();
                }});

    public boolean hasState(String key) {
        return cache.asMap().containsKey(key);
    }

    public State getState(String key) {
        try {
            if (!hasState(key)) {
                throw new IllegalArgumentException("state doesn't exists");
            }
            return cache.get(key);
        } catch (ExecutionException ex) {
            throw new IllegalArgumentException("can't find state", ex);
        }
    }

    public HashMap.Entry<String, State> createState() {
        try {
            String key = nextKey();
            if (hasState(key)) {
                throw new IllegalArgumentException("state exists");
            }
            return new AbstractMap.SimpleEntry(key, cache.get(key));
        } catch (ExecutionException ex) {
            throw new IllegalArgumentException("can't find state", ex);
        }
    }

    private String nextKey() {
        String next =  Integer.toString(nextState.incrementAndGet(),36);
        return next;
    }

    public static class State {
    }

}
