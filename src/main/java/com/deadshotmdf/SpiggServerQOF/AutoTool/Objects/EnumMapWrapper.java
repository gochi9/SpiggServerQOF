package com.deadshotmdf.SpiggServerQOF.AutoTool.Objects;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

/*
Why does this class even exist? Well, I made that whole fuckton of a mess in AutoToolUtils, and then I realised that some material can be broken by multiple tools... crazy right?
So now then I had to change the map to take in a list argument. I'm not doing that, so I just made this, it creates a set internally, and it adds the item to the set
Done and easy, now I don't have so much work anymore
 */
public class EnumMapWrapper <K extends Enum<K>, T> {

    private final EnumMap<K, List<T>> map;

    public EnumMapWrapper(Class<K> keyType) {
        map = new EnumMap<>(keyType);
    }

    public void put(K key, T value) {
        map.computeIfAbsent(key, k -> new LinkedList<>()).add(value);
    }

    public void put(K key, List<T> values) {
        map.computeIfAbsent(key, k -> new LinkedList<>()).addAll(values);
    }

    public List<T> get(K key){
        return map.get(key);
    }
}
