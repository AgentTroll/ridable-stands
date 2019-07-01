package com.gmail.woodyc40.ridablestands;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface RidableStandsNms {
    void registerEntity();

    boolean canMount(@NonNull ArmorStand as);

    @NonNull
    ArmorStand spawnNewEntity(@NonNull Location spawnLocation);
}
