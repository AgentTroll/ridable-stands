package com.gmail.woodyc40.ridablestands.listener;

import com.gmail.woodyc40.ridablestands.RidableStandsNms;
import com.google.inject.Inject;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class InteractListener implements Listener {
    private final RidableStandsNms rsn;

    @Inject
    public InteractListener(RidableStandsNms rsn) {
        this.rsn = rsn;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Entity clicked = event.getRightClicked();
        if (clicked instanceof ArmorStand) {
            ArmorStand as = (ArmorStand) clicked;
            if (this.rsn.canMount(as)) {
                Player player = event.getPlayer();
                as.addPassenger(player);
            }
        }
    }
}
