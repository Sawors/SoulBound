package io.github.sawors.soulbound;

import io.github.sawors.slogger.SLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathManager implements Listener {
    
    private final static SLogger log = SoulBound.logger();
    
    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) {
        log.log(event.getPlayer().getName());
    }
    
    @EventHandler
    public static void onPlayerResurrect(EntityResurrectEvent event){
    
    }
}
