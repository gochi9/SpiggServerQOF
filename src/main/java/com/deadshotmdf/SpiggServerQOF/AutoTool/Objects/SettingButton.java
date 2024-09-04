package com.deadshotmdf.SpiggServerQOF.AutoTool.Objects;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SettingButton {

    private final String name;
    private final List<String> lore;
    private final int slot;

    private static final String REPLACE = "{status}";

    public SettingButton(String name, List<String> lore, int slot) {
        this.name = name;
        this.lore = lore;
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore(String status){
        List<String> newList = new ArrayList<>(lore.size());

        for(String s : lore)
            newList.add(StringUtils.replace(s, REPLACE, status));

        return newList;
    }

    public int getSlot(){
        return slot;
    }

}
