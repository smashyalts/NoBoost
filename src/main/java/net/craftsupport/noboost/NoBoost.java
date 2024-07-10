package net.craftsupport.noboost;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;

public final class NoBoost extends JavaPlugin implements Listener {
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
    }

    @EventHandler
    public void onBoost(PlayerElytraBoostEvent event) {
        if (this.getConfig().getBoolean("per-world")) {
            List<?> allowedWorlds = Optional.ofNullable(this.getConfig().getList("allowed-worlds")).orElse(List.of());
            boolean allowWorld = allowedWorlds.contains(event.getPlayer().getWorld().getName());
            if (allowWorld) return;
        }

        event.setCancelled(true);

        if (this.getConfig().getBoolean("send-message")) {
            Component cancelMessage = Optional
                .ofNullable(this.getConfig().getString("message"))
                .map(NoBoost.MINI_MESSAGE::deserialize)
                .orElse(Component.empty());
            event.getPlayer().sendMessage(cancelMessage);
        }
    }
}
