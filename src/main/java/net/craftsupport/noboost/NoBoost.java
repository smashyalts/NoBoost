package net.craftsupport.noboost;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public final class NoBoost extends JavaPlugin implements Listener {
MiniMessage mm = MiniMessage.miniMessage();
    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
saveDefaultConfig();
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler
    public void onBoost(PlayerElytraBoostEvent event) {
        if (getConfig().getString("message") != null && getConfig().getBoolean("send-message")) {
            Component message = mm.deserialize(getConfig().getString("message"));
            event.getPlayer().sendMessage(message);
            event.setCancelled(true);
        }
        event.setCancelled(true);
    }
}