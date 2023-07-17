package com128.kzf.p.viewdistance.viewdistance;

import com128.kzf.p.viewdistance.viewdistance.commands.ResetViewCommand;
import com128.kzf.p.viewdistance.viewdistance.commands.SetViewCommand;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerList;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomViewDistance extends JavaPlugin {

    PlayerList playerList = MinecraftServer.getServer().getPlayerList();

    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();
        System.out.println(pdf.getName() + " version " + pdf.getVersion() + " is disabled!");
    }

    public void onEnable() {
        PluginDescriptionFile pdf = this.getDescription();
        System.out.println( pdf.getName() + " version " + pdf.getVersion() + " is enabled!" );

        getCommand("setview").setExecutor(new SetViewCommand(this));
        getCommand("resetview").setExecutor(new ResetViewCommand(this));
    }

    public boolean setPlayerViewDistance(CraftPlayer player, int viewDistance) throws IllegalArgumentException {
        if (player == null) {
            return false;
        }
        // Spigot API sucks here
        MinecraftServer.getServer().getWorldServer(0).getPlayerChunkMap().updateViewDistance(player.getHandle(), viewDistance);
        return true;
    }

    public boolean setServerViewDistance(int viewDistance) throws IllegalArgumentException {
        // Get it from WagYourTail's mapping viewer
        // These are real getViewDistance() & setViewDistance() methods, IDK why Spigot didn't map it
        if (viewDistance != this.playerList.s()) {
            System.out.println("Changing view distance to " + viewDistance + ", from " + this.playerList.s());
            this.playerList.a(viewDistance);
        }
        return true;
    }
}
