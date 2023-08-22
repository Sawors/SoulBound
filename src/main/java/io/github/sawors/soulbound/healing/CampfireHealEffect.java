package io.github.sawors.soulbound.healing;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CampfireHealEffect extends PassiveHealEffect {
    
    private static final Map<UUID, CampfireHealEffect> trackedPlayer = new HashMap<>();
    
    final private static int HEALING_RADIUS = 4;
    final private static int HEALING_PERIOD = 4;

    
    private final Block campfire;
    
    public CampfireHealEffect(Player player, Block campfire) {
        super(HEALING_PERIOD*20, player);
        this.campfire = campfire;
    }
    
    @Override
    public void start() {
        super.start();
        trackedPlayer.put(player.getUniqueId(),this);
    }
    
    @Override
    public void stop() {
        super.stop();
        trackedPlayer.remove(player.getUniqueId());
    }
    
    @Override
    public boolean check() {
        return
                player.getLocation().distance(campfire.getLocation().add(.5,.5,.5)) <= HEALING_RADIUS
                &&
                campfire.getBlockData() instanceof Campfire data && data.isLit()
        ;
    }
    
    @Override
    public void apply() {
        AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        player.setHealth(Math.min(player.getHealth()+1, maxHealth != null ? maxHealth.getValue() : 20));
    }
    
    public static boolean isTracked(Player player){
        return trackedPlayer.containsKey(player.getUniqueId());
    }
}
