package com.deadshotmdf.SpiggServerQOF.AutoTool.Objects;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.ToolType;

public class Tool {

    private final ToolType type;
    private final int hardness;
    private final int power;

    public Tool(ToolType type, int hardness, int power) {
        this.type = type;
        this.hardness = hardness;
        this.power = power;
    }

    public ToolType getType() {
        return type;
    }

    public int getHardness() {
        return hardness;
    }

    public int getPower() {
        return power;
    }

}
