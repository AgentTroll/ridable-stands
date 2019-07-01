package com.gmail.woodyc40.ridablestands.command;

import com.gmail.woodyc40.ridablestands.RidableStandsNms;
import com.google.inject.Inject;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MountStandCommand implements CommandExecutor {
    private final RidableStandsNms rsn;

    @Inject
    public MountStandCommand(RidableStandsNms rsn) {
        this.rsn = rsn;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender,
                             @NonNull Command command,
                             @NonNull String label,
                             @NonNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You are not a Player!");
            return true;
        }

        Player player = (Player) sender;

        Location spawnLocation = player.getLocation();
        ArmorStand as = this.rsn.spawnNewEntity(spawnLocation);
        as.addPassenger(player);

        player.sendMessage("A new armor stand has been spawned. You are now mounted on it.");

        return true;
    }
}
