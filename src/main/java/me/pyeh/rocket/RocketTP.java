package me.pyeh.rocket;

import me.pyeh.rocket.command.TpAcceptCommand;
import me.pyeh.rocket.command.TpaCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class RocketTP extends JavaPlugin {

    public static RocketTP instance;
    public Map<UUID, UUID> tpaList;

    @Override
    public void onEnable() {
        instance = this;
        this.saveConfig();

        this.tpaList = new HashMap<>();
        this.registerCommand();
    }


    @Override
    public void onDisable() {
        this.tpaList.clear();
        instance = null;
    }

    private void registerCommand() {
        this.getCommand("tpa").setExecutor(new TpaCommand());
        this.getCommand("tpaccept").setExecutor(new TpAcceptCommand());
    }
}
