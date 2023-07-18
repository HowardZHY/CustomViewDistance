package com128.kzf.p.viewdistance.viewdistance.commands;

import com128.kzf.p.viewdistance.viewdistance.CustomViewDistance;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SetViewCommand implements CommandExecutor {
    private final CustomViewDistance plugin;

    public SetViewCommand(CustomViewDistance plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                return false;
            }
            Player player = (Player) sender;
            return setPlayerViewDistance(sender, args[0], player);
        }
        if (args.length > 1) {
            if ("server".equalsIgnoreCase(args[0])) {
                return setServerViewDistance(sender, args[1], plugin.getServer());
            }
        }
        if (args.length == 2) {
            if (!(sender instanceof Player)) {
                return false;
            }
             if ("player".equalsIgnoreCase(args[0])) {
                Player player = (Player) sender;
                return setPlayerViewDistance(sender, args[1], player);
            }
        }
        if (args.length > 2) {
            if ("player".equalsIgnoreCase(args[0])) {
                Player player = plugin.getServer().getPlayer(args[1]);
                return setPlayerViewDistance(sender, args[2], player);
            }
        }
        return false;
    }

    private boolean setServerViewDistance(CommandSender sender, String viewDistance, Server server) {
        if (server == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.set.server") && !sender.hasPermission("viewdistance.set.server." + server.getName())) {
            sender.sendMessage("You have no permission to use this command.");
            return true;
        }
        try {
            if (Integer.parseInt(viewDistance) <= 1) {
                sender.sendMessage("View Distance should more than 2.");
                return false;
            }
            if (plugin.setServerViewDistance(Integer.parseInt(viewDistance))) {
                sender.sendMessage("Server view distance set to "+viewDistance);
                return true;
            }
        } catch (NumberFormatException ignored) {
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return true;
        }
        return false;
    }

    private boolean setPlayerViewDistance(CommandSender sender, String viewDistance, Player player) {
        if (player == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.set.player") && !sender.hasPermission("viewdistance.set.player." + player.getName()) && (!sender.hasPermission("viewdistance.set.self") && player.equals(sender))) {
            sender.sendMessage("You have no permission to use this command.");
            return true;
        }
        try {
            if (plugin.setPlayerViewDistance((CraftPlayer) player, Integer.parseInt(viewDistance))) {
                sender.sendMessage("Player \"" + player.getName() + "\" view distance set to " + viewDistance);
                return true;
            }
        } catch (NumberFormatException ignored) {
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return true;
        }
        return false;
    }

}
