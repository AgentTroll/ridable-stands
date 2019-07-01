package com.gmail.woodyc40.ridablestands;

import com.gmail.woodyc40.ridablestands.command.MountStandCommand;
import com.gmail.woodyc40.ridablestands.listener.InteractListener;
import com.gmail.woodyc40.ridablestands.module.NmsModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public class RidableStands extends JavaPlugin {
    private Injector injector;

    @Override
    public void onLoad() {
        this.injector = Guice.createInjector(new NmsModule(this));

        RidableStandsNms rsn = this.injector.getInstance(RidableStandsNms.class);
        rsn.registerEntity();

        this.getLogger().info("Registered the custom EntityArmorStand implementation");
    }

    @Override
    public void onEnable() {
        this.registerListener(InteractListener.class);
        this.registerCommmand("mountstand", MountStandCommand.class);
    }

    private void registerListener(@NonNull Class<? extends Listener> cls) {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this.injector.getInstance(cls), this);

        this.getLogger().info(String.format("Registering listener '%s'", cls.getSimpleName()));
    }

    private void registerCommmand(@NonNull String commandName, @NonNull Class<? extends CommandExecutor> cls) {
        PluginCommand cmd = this.getCommand(commandName);
        if (cmd == null) {
            this.getLogger().warning(String.format("Command '%s' hasn't been registered in the plugin.yml file", commandName));
            return;
        }

        this.getLogger().info(String.format("Registering command '%s' to executor '%s'", commandName, cls.getSimpleName()));
        cmd.setExecutor(this.injector.getInstance(cls));
    }
}
