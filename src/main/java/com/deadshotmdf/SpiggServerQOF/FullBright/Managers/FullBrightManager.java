package com.deadshotmdf.SpiggServerQOF.FullBright.Managers;

import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Utils.GeneralUtils;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.InformationHolder;
import com.deadshotmdf.SpiggServerQOF.SSQOF;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FullBrightManager extends InformationHolder {

    private final HashSet<UUID> fullBright;

    public FullBrightManager(SSQOF main) {
        super(main, "FullBrightList.yml");
        fullBright = new HashSet<>();

        loadPlayers();
    }

    public boolean isFullBright(UUID uuid) {
        return fullBright.contains(uuid);
    }

    public void triggerFullBright(Player player) {
        UUID uuid = player.getUniqueId();

        if(fullBright.contains(uuid)) {
            fullBright.remove(uuid);
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(ConfigSettings.getTurnOffFullBright());
            return;
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false, true));
        fullBright.add(uuid);
        player.sendMessage(ConfigSettings.getTurnOnFullBright());
    }


    public void loadPlayers() {
        ConfigurationSection sec = config.getConfigurationSection("Players");

        if(sec == null)
            return;

        Set<String> keys = sec.getKeys(false);

        if(keys.isEmpty())
            return;

        for(String u : keys) {
            UUID uuid = GeneralUtils.getUUID(u);

            if(uuid == null)
                continue;

            fullBright.add(uuid);
        }

        config.set("Players", null);
        saveC();
    }

    public void savePlayers() {
        for(UUID uuid : fullBright)
            config.set("Players." + uuid.toString(), 1);

        saveC();
    }

}
