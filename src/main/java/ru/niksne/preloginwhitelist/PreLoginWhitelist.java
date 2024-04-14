package ru.niksne.preloginwhitelist;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        getServer().reloadWhitelist();
        for (OfflinePlayer p: getServer().getWhitelistedPlayers()) { if (p.getUniqueId().equals(e.getUniqueId()) || p.getName().equals(e.getName())) { return; } }
        reloadConfig();
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, getConfig().getString("message"));
    }
}
