package com.deadshotmdf.SpiggServerQOF;

import com.deadshotmdf.SpiggServerQOF.AutoTool.Commands.AutoToolToggleCommand;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Commands.ManageBlockCommand;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Listeners.BlockChatMessagesListener;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Listeners.BlockMessagesJoinQuitListener;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Listeners.BlockPrivateMessageListener;
import com.deadshotmdf.SpiggServerQOF.BlockChat.Managers.BlockChatManager;
import com.deadshotmdf.SpiggServerQOF.FullBright.Commands.TriggerFullBright;
import com.deadshotmdf.SpiggServerQOF.FullBright.Listeners.PotionEffectListener;
import com.deadshotmdf.SpiggServerQOF.FullBright.Managers.FullBrightManager;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Commands.ReloadConfigCommand;
import com.deadshotmdf.SpiggServerQOF.GeneralFixes.Listeners.FixEndCrystalDmg;
import com.deadshotmdf.SpiggServerQOF.GeneralFixes.Listeners.FixPeopleRoofListener;
import com.deadshotmdf.SpiggServerQOF.GeneralFixes.Listeners.SpawnerListener;
import com.deadshotmdf.SpiggServerQOF.GeneralFixes.Managers.GeneralFixesManager;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Listeners.GUIListener;
import com.deadshotmdf.SpiggServerQOF.SSQOFGeneral.Managers.GUIManager;
import com.deadshotmdf.SpiggServerQOF.SkeletonTrap.Listeners.SkeletonTrapSpawnLis;
import com.deadshotmdf.SpiggServerQOF.Thrash.Commands.ThrashCommand;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Listeners.*;
import com.deadshotmdf.SpiggServerQOF.AutoTool.Managers.AutoToolManager;
import com.deadshotmdf.SpiggServerQOF.Thrash.Listeners.ThrashListener;
import com.deadshotmdf.SpiggServerQOF.Thrash.Managers.ThrashManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SSQOF extends JavaPlugin {

    private GUIManager guiManager;
    private AutoToolManager autoToolManager;
    private ThrashManager thrashManager;
    private FullBrightManager fullBrightManager;
    private GeneralFixesManager generalFixesManager;
  //  private BlockChatManager blockChatManager;

    @Override
    public void onEnable() {
        ConfigSettings.reloadConfig(this);

        this.guiManager = new GUIManager();
        this.autoToolManager = new AutoToolManager(this, guiManager);
        this.thrashManager = new ThrashManager();
        this.fullBrightManager = new FullBrightManager(this);
        this.generalFixesManager = new GeneralFixesManager(this);
    //    this.blockChatManager = new BlockChatManager(this, guiManager);

        PluginManager pm = Bukkit.getPluginManager();

        //Auto tool
        pm.registerEvents(new AutoToolSwitchListener(autoToolManager), this);
        pm.registerEvents(new AutoToolBlockListener(autoToolManager), this);
        pm.registerEvents(new PreventToolBreakListener(autoToolManager), this);
        pm.registerEvents(new ItemDurabilityWarningListener(autoToolManager), this);
        pm.registerEvents(new BucketListener(autoToolManager), this);
        pm.registerEvents(new JoinQuitListener(autoToolManager), this);
        pm.registerEvents(new AutoCropListener(this, autoToolManager), this);

        //Thrash
        pm.registerEvents(new ThrashListener(thrashManager), this);

        //Full bright
        pm.registerEvents(new PotionEffectListener(fullBrightManager), this);

        //Skeleton trap (Adds zombie horses to the skeleton trap)
        pm.registerEvents(new SkeletonTrapSpawnLis(), this);

        //General fixes
        pm.registerEvents(new FixEndCrystalDmg(generalFixesManager), this);
        pm.registerEvents(new FixPeopleRoofListener(generalFixesManager), this);
        pm.registerEvents(new SpawnerListener(), this);

        //General
        pm.registerEvents(new GUIListener(guiManager), this);

        //Block messages
   //     pm.registerEvents(new BlockMessagesJoinQuitListener(blockChatManager), this);
   //     if(pm.isPluginEnabled("Essentials"))
  //          pm.registerEvents(new BlockPrivateMessageListener(blockChatManager), this);
   //     pm.registerEvents(new BlockChatMessagesListener(blockChatManager), this);


        //Auto tool
        AutoToolToggleCommand autoToolToggleCommand = new AutoToolToggleCommand(autoToolManager);
        this.getCommand("sautotool").setExecutor(autoToolToggleCommand);
        this.getCommand("sautotool").setTabCompleter(autoToolToggleCommand);

        //Thrash
        this.getCommand("strash").setExecutor(new ThrashCommand(thrashManager));

        //Full bright
        this.getCommand("sfullbright").setExecutor(new TriggerFullBright(fullBrightManager));

        //General
        this.getCommand("ssqof").setExecutor(new ReloadConfigCommand(this, autoToolManager));

        //Block messages
    //    this.getCommand("schatsettings").setExecutor(new ManageBlockCommand(this, blockChatManager));
    }

    @Override
    public void onDisable(){
        this.fullBrightManager.savePlayers();
        this.autoToolManager.savePlayers();
       // this.blockChatManager.saveAll();
    }
}
