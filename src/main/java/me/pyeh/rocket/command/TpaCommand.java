package me.pyeh.rocket.command;

import me.pyeh.rocket.RocketTP;
import me.pyeh.rocket.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Configuration configuration = RocketTP.instance.getConfig();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Util.translate(configuration.getString("command.only_players")));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(Util.translate(configuration.getString("command.usage").replace("<command>", label + " <player>")));
            return false;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(Util.translate(configuration.getString("command.player_no_found").replace("<player>", args[0])));
            return false;
        }

        if (RocketTP.instance.tpaList.get(target.getUniqueId()) != player.getUniqueId()) {
            sender.sendMessage(Util.translate(configuration.getString("command.tpa_target").replace("<player>", target.getName())));
            return false;
        }

        target.sendMessage(Util.translate(configuration.getString("command.tpa_request").replace("<player>", player.getName())));
        sender.sendMessage(Util.translate(configuration.getString("command.tpa_send").replace("<player>", target.getName())));


        Util.sync(() -> {
            target.sendMessage(Util.translate(configuration.getString("command.tpa_expired")));
            player.sendMessage(Util.translate(configuration.getString("command.tpa_expired")));

            if (RocketTP.instance.tpaList.get(target.getUniqueId()) == player.getUniqueId()) {
                RocketTP.instance.tpaList.remove(target.getUniqueId(), player.getUniqueId());
            }
        }, 20L * 10);
        return false;
    }
}
