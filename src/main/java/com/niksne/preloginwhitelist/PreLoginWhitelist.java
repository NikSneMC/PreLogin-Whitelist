package com.niksne.preloginwhitelist;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class PreLoginWhitelist extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        if (!getServer().hasWhitelist()) { return; }
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @EventHandler
    void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        getServer().reloadWhitelist();
        String n = e.getName();
        UUID u = e.getUniqueId();
        for (OfflinePlayer p: getServer().getWhitelistedPlayers()) { if (p.getUniqueId().equals(u) || p.getName().equals(n)) { return; } }
        reloadConfig();
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, getConfig().getString("message"));
    }
}
