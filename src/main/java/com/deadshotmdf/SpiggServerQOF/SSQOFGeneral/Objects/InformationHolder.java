package com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects;

import com.deadshotmdf.SpiggServerQOF.SSQOF;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class InformationHolder {

    protected File file;
    protected FileConfiguration config;
    private static final Set<String> KEYS = Collections.unmodifiableSet(new HashSet<>());

    public InformationHolder(SSQOF main, String name) {
        file = new File(main.getDataFolder(), name);

        if(!file.exists())
            main.saveResource(file.getName(), false);

        config = YamlConfiguration.loadConfiguration(file);
    }

    public Set<String> getKeys(String path, boolean deep){
        ConfigurationSection sec = this.config.getConfigurationSection(path);

        if(sec == null)
            return KEYS;

        return sec.getKeys(deep);
    }

    public void saveC(){
        try {
            this.config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadC(){
        try {
            this.config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
