package me.pyeh.rocket.command;

import me.pyeh.rocket.RocketTP;
import me.pyeh.rocket.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class TpAcceptCommand  implements CommandExecutor {

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

        if (RocketTP.instance.tpaList.get(target.getUniqueId()) != player.getUniqueId() || RocketTP.instance.tpaList.get(target.getUniqueId()) == null) {
            sender.sendMessage(Util.translate(configuration.getString("command.tpa_no_exist").replace("<player>", target.getName())));
            return false;
        }

        sender.sendMessage(Util.translate(configuration.getString("command.tpa_successfully").replace("<player>", target.getName())));
        Util.sync(() -> {
            target.teleport(player.getLocation());
            RocketTP.instance.tpaList.remove(player.getUniqueId(), target.getUniqueId());
        }, 3 * 20L);
        return false;
    }
}
