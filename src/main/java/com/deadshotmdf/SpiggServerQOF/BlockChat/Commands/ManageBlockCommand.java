package com.deadshotmdf.SpiggServerQOF.BlockChat.Commands;

import com.deadshotmdf.SpiggServerQOF.BlockChat.Managers.BlockChatManager;
import com.deadshotmdf.SpiggServerQOF.ConfigSettings;
import com.deadshotmdf.SpiggServerQOF.SSQOF;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.UUID;

public class ManageBlockCommand implements CommandExecutor {

    private final SSQOF main;
    private final BlockChatManager blockChatManager;
    public final static HashSet<UUID> executing = new HashSet<>();

    public ManageBlockCommand(SSQOF main, BlockChatManager blockChatManager) {
        this.main = main;
        this.blockChatManager = blockChatManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only a player may execute this command.");
            return true;
        }

        if(args.length < 1){
            sender.sendMessage(ConfigSettings.getBlockCmdUsage());
            return true;
        }

        if(args[0].toLowerCase().equals(sender.getName().toLowerCase()))
            return true;

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        if(executing.contains(uuid)){
            sender.sendMessage(ConfigSettings.getCannotExecuteYet());
            return true;
        }

        String name = blockChatManager.getOfflinePlayer(args[0].toLowerCase());

        if(name == null){
            sender.sendMessage(ConfigSettings.getNeverPlayed(args[0]));
            return true;
        }

        executing.add(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> getPlayer(player, name));

        return true;
    }

    private void getPlayer(Player player, String name){
        OfflinePlayer target = null;

        try{
            target = Bukkit.getOfflinePlayer(name);
        }
        catch(Throwable e){}

        OfflinePlayer finalPlayer = target;
        Bukkit.getScheduler().runTask(main, () -> execute(player, finalPlayer));
    }

    private void execute(Player player, OfflinePlayer target){
        executing.remove(player.getUniqueId());

        if(target == null){
            player.sendMessage(ConfigSettings.getBlockCmdError());
            return;
        }

        blockChatManager.openGUI(player, target);
    }
}
