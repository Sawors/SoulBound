package io.github.sawors.soulbound;

import io.github.sawors.simplesit.PlayerSitEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PassiveHealingManager implements Listener {
    
    @EventHandler
    public static void campfireHealing(PlayerSitEvent event){
        Player p = event.getPlayer();
        Block seat = event.getSeat();
        Location seatLoc = seat.getLocation();
        World w = p.getWorld();
        int radius = 4;
        
        // Could be optimized to check first for most probable positions, but I don't think there is a significant performance impact.
        for(int x = 0; x<(radius*2)+1; x++){
            for(int z = 0; z<(radius*2)+1; z++){
                Block relative = w.getBlockAt(seatLoc.add(x,0,z));
                if(relative.getBlockData() instanceof Campfire campfire && campfire.isLit()){
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            if(relative.getBlockData() instanceof Campfire campfireCheck && campfireCheck.isLit() && relative.getLocation().distance(p.getLocation()) <= radius){
                                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,30,0,true,false,true));
                            } else {
                                this.cancel();
                                p.removePotionEffect(PotionEffectType.REGENERATION);
                            }
                        }
                    }.runTaskTimer(SoulBound.getPlugin(),0,20);
                }
            }
        }
    }
}
