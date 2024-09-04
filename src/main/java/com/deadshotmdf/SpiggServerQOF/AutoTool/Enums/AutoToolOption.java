package com.deadshotmdf.SpiggServerQOF.AutoTool.Enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum AutoToolOption {

    WARN_TOOL_LOW_DURABILITY_MESSAGE(new ArrayList<>(Arrays.asList(OptionStatus.ALWAYS, OptionStatus.ONLY_ON_AUTO_MODE, OptionStatus.NEVER))),
    SELECT_BEST_TOOL(new ArrayList<>(Arrays.asList(OptionStatus.ONLY_ON_AUTO_MODE,OptionStatus.NEVER))),
    PREVENT_TOOL_BREAK(new ArrayList<>(Arrays.asList(OptionStatus.ALWAYS, OptionStatus.ONLY_ON_AUTO_MODE, OptionStatus.NEVER))),
    AUTO_BLOCK_REFILL(new ArrayList<>(Arrays.asList(OptionStatus.ONLY_ON_AUTO_MODE, OptionStatus.NEVER))),
    AUTO_CROP_PLACE(new ArrayList<>(Arrays.asList(OptionStatus.ONLY_ON_AUTO_MODE, OptionStatus.NEVER))),
    SAVE_AUTO_TOOL_STATUS_ON_REJOIN(new ArrayList<>(Arrays.asList(OptionStatus.ALWAYS, OptionStatus.NEVER))),
    SUGGEST_MORE(new ArrayList<>(Collections.singletonList(OptionStatus.SUGGEST)));

    private final ArrayList<OptionStatus> types;
    private final int size;

    AutoToolOption(ArrayList<OptionStatus> es) {
        types = es;
        this.size = types.size();
    }

    public OptionStatus getNewStatus(OptionStatus status, boolean next) {
        int index = getCurrentIndex(status);

        return next ? (++index < size ? types.get(index) : types.get(0)) : (--index > 0 ? types.get(index) : types.get(size - 1));
    }

    private int getCurrentIndex(OptionStatus status){
        for(int i = 0; i < size; i++)
            if(types.get(i) == status)
                return i;

        return 0;
    }

    public static AutoToolOption getFromString(String value){
        if(value == null)
            return null;

        switch(value.toUpperCase()){
            case "WARN_TOOL_LOW_DURABILITY_MESSAGE":
                return WARN_TOOL_LOW_DURABILITY_MESSAGE;

            case "SELECT_BEST_TOOL":
                return SELECT_BEST_TOOL;

            case "PREVENT_TOOL_BREAK":
                return PREVENT_TOOL_BREAK;

            case "AUTO_BLOCK_REFILL":
                return AUTO_BLOCK_REFILL;

            case "AUTO_CROP_PLACE":
                return AUTO_CROP_PLACE;

            case "SAVE_AUTO_TOOL_STATUS_ON_REJOIN":
                return SAVE_AUTO_TOOL_STATUS_ON_REJOIN;
                
            case "SUGGEST_MORE":
                return SUGGEST_MORE;
                
            default:
                return null;
        }
    }
}
