package com128.kzf.p.viewdistance.viewdistance.commands;

import com128.kzf.p.viewdistance.viewdistance.CustomViewDistance;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResetViewCommand implements CommandExecutor {
    public int defaultViewDistance = Bukkit.getViewDistance();
    private final CustomViewDistance plugin;

    PlayerList playerList = MinecraftServer.getServer().getPlayerList();

    public ResetViewCommand(CustomViewDistance plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if ("server".equalsIgnoreCase(args[0])) {
                return resetServerViewDistance(sender, plugin.getServer());
            }
        }
        return false;
    }

    private boolean resetServerViewDistance(CommandSender sender, Server server) {
        if (server == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.reset.server") && !sender.hasPermission("viewdistance.reset.server." + server.getName())) {
            sender.sendMessage("You have no permission to use this command.");
            return true;
        }
        try {
            // NMS
            this.playerList.a(defaultViewDistance);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("Server view distance reset. It's now " + server.getViewDistance());
        return true;
    }
}
