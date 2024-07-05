package me.andrej123456789.stocksigns;

import org.bukkit.plugin.java.JavaPlugin;

import me.andrej123456789.stocksigns.events.SignUpdated;

public final class StockSigns extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(new SignUpdated(), this);

        getServer().getConsoleSender().sendMessage("[StockSigns] Initialization of StockSigns is done!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
