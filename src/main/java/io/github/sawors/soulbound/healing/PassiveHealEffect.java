package io.github.sawors.soulbound.healing;

import io.github.sawors.soulbound.SoulBound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class PassiveHealEffect {

    protected final int period;
    protected final Player player;
    protected BukkitTask task = null;
    
    public PassiveHealEffect(int period, Player player){
        this.period = period;
        this.player = player;
    }
    
    public void start() {
        task = new BukkitRunnable(){
            @Override
            public void run() {
                if(check()){
                    apply();
                } else {
                    stop();
                }
            }
        }.runTaskTimer(SoulBound.getPlugin(),0,period);
    }
    
    public void stop() {
        task.cancel();
    }
    
    public abstract boolean check();
    
    public abstract void apply();
}
