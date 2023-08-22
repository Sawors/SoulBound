package io.github.sawors.soulbound.healing;

import io.github.sawors.simplesit.PlayerSitEvent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PassiveHealingManager implements Listener {
    
    @EventHandler
    public static void campfireHealing(PlayerSitEvent event){
        Player p = event.getPlayer();
        Block seat = event.getSeat();
        Location seatLoc = seat.getLocation();
        World w = p.getWorld();
        int radius = 4;
        
        // Could be optimized to check first for most probable positions, but I don't think there is a significant performance impact.
        if(!CampfireHealEffect.isTracked(p)){
            for(int x = 0; x<(radius*2)+1; x++){
                for(int z = 0; z<(radius*2)+1; z++){
                    Block relative = w.getBlockAt(seatLoc.add(x,0,z));
                    if(relative.getBlockData() instanceof Campfire campfire && campfire.isLit()){
                        relative.getWorld().spawnParticle(Particle.VILLAGER_ANGRY,relative.getLocation().add(.5,1.5,.5),8,.25,.25,.25,0);
                        CampfireHealEffect healing = new CampfireHealEffect(p,relative);
                        healing.start();
                        break;
                    } else {
                        relative.getWorld().spawnParticle(Particle.ASH,relative.getLocation().add(.5,1.5,.5),8,.25,.25,.25,0);
                    }
                }
            }
        }
    }
}
