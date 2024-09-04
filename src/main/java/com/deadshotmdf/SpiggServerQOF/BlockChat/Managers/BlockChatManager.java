package com.deadshotmdf.SpiggServerQOF.BlockChat.Managers;

import com.deadshotmdf.SpiggServerQOF.BlockChat.Objects.BlockChatGUI;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Objects.BlockProfile;
import com.deadshotmdf.SpiggServerQOF.SSQOF;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Managers.GUIManager;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Objects.InformationHolder;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class BlockChatManager extends InformationHolder {

    private final SSQOF main;
    private final HashMap<UUID, HashMap<UUID, BlockProfile>> profiles;
    private final GUIManager guiManager;
    private final HashMap<String, String> offlinePlayers;

    public BlockChatManager(SSQOF main, GUIManager guiManager) {
        super(main, "BlockList.yml");
        this.main = main;
        this.guiManager = guiManager;
        this.profiles = new HashMap<>();
        this.offlinePlayers = new HashMap<>();

        for(OfflinePlayer player : Bukkit.getOfflinePlayers())
            this.offlinePlayers.put(player.getName().toLowerCase(), player.getName());
    }

    public void openGUI(Player player, OfflinePlayer target){
        HashMap<UUID, BlockProfile> profiles = this.profiles.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>());
        BlockProfile profile = profiles.computeIfAbsent(target.getUniqueId(), k -> new BlockProfile(true, true));

        BlockChatGUI gui = new BlockChatGUI(target, profile, guiManager);
        guiManager.registerNewGUI(gui);
        player.openInventory(gui.getInventory());
    }

    public void loadPlayerAsync(Player player){
        this.offlinePlayers.put(player.getName().toLowerCase(), player.getName());
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> loadPlayer(player.getUniqueId()));
    }

    public void loadPlayer(UUID uuid){
        String id = "Blocks." + uuid.toString();
        Set<String> keys = getKeys(id, false);

        if(keys == null)
            return;

        HashMap<UUID, BlockProfile> profile = new HashMap<>();
        for(String key : keys){
            UUID target = GeneralUtils.getUUID(key);

            if(target == null)
                continue;

            String path = id + "." + key;
            boolean chat = this.config.getBoolean(path + ".chat", true);
            boolean privateChat = this.config.getBoolean(path + ".privateChat", true);
            profile.put(target, new BlockProfile(chat, privateChat));
        }

        if(!profile.isEmpty())
            profiles.put(uuid, profile);
    }

    public void savePlayerAsync(UUID uuid){
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> savePlayer(uuid));
    }

    public void saveAll(){
        for(UUID uuid : this.profiles.keySet())
            savePlayer(uuid);
    }

    public void savePlayer(UUID uuid){
        HashMap<UUID, BlockProfile> profiles = this.profiles.get(uuid);

        if(profiles == null)
            return;

        String id = uuid.toString();
        for(Map.Entry<UUID, BlockProfile> entry : profiles.entrySet()){
            String path = "Blocks." + id + "." + entry.getKey().toString();
            BlockProfile profile = entry.getValue();
            this.config.set(path + ".chat", profile.isChatAllowed());
            this.config.set(path + ".privateChat", profile.isPrivateChatAllowed());
        }

        saveC();
    }

    public String getOfflinePlayer(String string){
        return offlinePlayers.get(string != null ? string : "");
    }

    public BlockProfile getBlockProfile(UUID player, UUID target){
        HashMap<UUID, BlockProfile> profiles = this.profiles.get(player);

        if(profiles == null)
            return null;

        return profiles.get(target);
    }

}
