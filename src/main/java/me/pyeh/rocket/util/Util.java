package me.pyeh.rocket.util;

import me.pyeh.rocket.RocketTP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Util {

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }


    public static void sync(Callable callable, long delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(RocketTP.instance, callable::call, delay);
    }

    public interface Callable {
        void call();
    }
}
