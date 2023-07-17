package com.example.plugin.antibot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ConnectionDelayPlugin extends JavaPlugin implements Listener {

    private Set<UUID> playersInDelay;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        playersInDelay = new HashSet<>();
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (playersInDelay.contains(playerUUID)) {
            event.disallow(Result.KICK_OTHER, ChatColor.RED + "Devi aspettare 30 secondi prima di riconnetterti!");
            return;
        }

        playersInDelay.add(playerUUID);

        new BukkitRunnable() {
            @Override
            public void run() {
                playersInDelay.remove(playerUUID);
            }
        }.runTaskLater(this, 600); // 600 ticks = 30 secondi
    }
}
