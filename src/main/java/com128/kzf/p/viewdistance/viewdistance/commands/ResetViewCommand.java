package com128.kzf.p.viewdistance.viewdistance.commands;

import com128.kzf.p.viewdistance.viewdistance.CustomViewDistance;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ResetViewCommand implements CommandExecutor {
    public int defaultViewDistance = Bukkit.getViewDistance();
    private final CustomViewDistance plugin;

    PlayerList playerList = MinecraftServer.getServer().getPlayerList();

    public ResetViewCommand(CustomViewDistance plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return resetViewDistance(sender, plugin.getServer());
    }

    private boolean resetViewDistance(CommandSender sender, Server server) {
        if (server == null) {
            return false;
        }
        if (!sender.hasPermission("viewdistance.reset")) {
            sender.sendMessage("You have no permission to use this command.");
            return true;
        }
        try {
            // NMS
            this.playerList.a(defaultViewDistance);
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        sender.sendMessage("Server view distance reset. It's now " + server.getViewDistance());
        if (sender instanceof Player) {
            CraftPlayer player = (CraftPlayer) sender;
            MinecraftServer.getServer().getWorldServer(0).getPlayerChunkMap().updateViewDistance(player.getHandle(), defaultViewDistance);
            sender.sendMessage("Player " + player.getName() + " 's view distance reset to " + defaultViewDistance);
        }
        return true;
    }
}
