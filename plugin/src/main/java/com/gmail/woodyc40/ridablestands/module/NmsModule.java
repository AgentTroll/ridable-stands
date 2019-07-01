package com.gmail.woodyc40.ridablestands.module;

import com.gmail.woodyc40.ridablestands.RidableStandsNms;
import com.gmail.woodyc40.ridablestands.RidableStandsNms1_14_2_R01;
import com.google.inject.AbstractModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class NmsModule extends AbstractModule {
    private final Plugin plugin;

    public NmsModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        String versionString = Bukkit.getBukkitVersion();

        // Technically it is discouraged to have logic in
        // Guice Modules, but honestly I don't really care
        if (versionString.startsWith("1.14.2")) {
            this.bind(RidableStandsNms.class).to(RidableStandsNms1_14_2_R01.class).asEagerSingleton();
        } else {
            this.plugin.getLogger().warning(String.format("RidableStandsNms is incompatible with Bukkit '%s'", versionString));
            return;
        }

        this.plugin.getLogger().info(String.format("Registered NMS module for Bukkit '%s'", versionString));
    }
}
