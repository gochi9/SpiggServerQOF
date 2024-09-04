package com.deadshotmdf.SpiggServerQOF.AutoTool.Objects;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.AutoToolOption;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Enums.OptionStatus;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.SSQOFGUI;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class AutoToolProfile {

    private final EnumMap<AutoToolOption, OptionStatus> options;
    private final UUID uuid;
    private SSQOFGUI gui;

    public AutoToolProfile(UUID uuid) {
        this.options = new EnumMap<>(AutoToolOption.class);
        this.uuid = uuid;
        fillDefaultValues();
    }

    public AutoToolProfile(UUID uuid, FileConfiguration config, Set<String> keys) {
        this.options = new EnumMap<>(AutoToolOption.class);
        this.uuid = uuid;

        if(config == null || keys == null || keys.isEmpty()){
            fillDefaultValues();
            return;
        }

        for(String key : keys){
            AutoToolOption option = AutoToolOption.getFromString(key);
            OptionStatus status = OptionStatus.getFromString(config.getString("Players." + uuid + ".options." + key));

            if(option != null && status != null)
                options.put(option, status);
        }

        fillDefaultValues();
    }

    public void setGUI(SSQOFGUI gui){
        this.gui = gui;
    }

    public SSQOFGUI getGUI(){
        return gui;
    }

    public void setOptionValue(AutoToolOption option, OptionStatus value) {
        this.options.put(option, value);
    }

    public OptionStatus getOptionValue(AutoToolOption option) {
        return option != AutoToolOption.SUGGEST_MORE ? this.options.computeIfAbsent(option, k -> OptionStatus.NEVER) : OptionStatus.SUGGEST;
    }

    public void toFile(FileConfiguration config) {
        for(Map.Entry<AutoToolOption, OptionStatus> entry : options.entrySet())
            config.set("Players." + uuid + ".options." + entry.getKey(), entry.getValue().toString());
    }

    public void fillDefaultValues() {
        options.putIfAbsent(AutoToolOption.WARN_TOOL_LOW_DURABILITY_MESSAGE, OptionStatus.ALWAYS);
        options.putIfAbsent(AutoToolOption.SELECT_BEST_TOOL, OptionStatus.ONLY_ON_AUTO_MODE);
        options.putIfAbsent(AutoToolOption.PREVENT_TOOL_BREAK, OptionStatus.ONLY_ON_AUTO_MODE);
        options.putIfAbsent(AutoToolOption.AUTO_BLOCK_REFILL, OptionStatus.ONLY_ON_AUTO_MODE);
        options.putIfAbsent(AutoToolOption.AUTO_CROP_PLACE, OptionStatus.ONLY_ON_AUTO_MODE);
        options.putIfAbsent(AutoToolOption.SAVE_AUTO_TOOL_STATUS_ON_REJOIN, OptionStatus.ALWAYS);
    }

}
