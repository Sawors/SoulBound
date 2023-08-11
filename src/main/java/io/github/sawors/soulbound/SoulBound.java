package io.github.sawors.soulbound;

import io.github.sawors.slogger.SLogger;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SoulBound extends JavaPlugin {

    private static Plugin instance;
    private static SLogger logger;
    
    @Override
    public void onEnable() {
        instance = this;
        logger = new SLogger(getPlugin());
        // Plugin startup logic
        saveDefaultConfig();
        
        FileConfiguration config = getConfig();
        if(config.getBoolean("enforce-no-natural-regeneration",true)){
            for(World w : Bukkit.getWorlds()){
                w.setGameRule(GameRule.NATURAL_REGENERATION, false);
            }
        }
        
        if(!config.getBoolean("prevent-instant-respawn",false)){
            for(World w : Bukkit.getWorlds()){
                w.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,true);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    
    public static Plugin getPlugin() {
        return instance;
    }
    
    public static SLogger logger() {
        return logger;
    }
}
