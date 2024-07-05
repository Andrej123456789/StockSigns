package me.andrej123456789.stocksigns;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import me.andrej123456789.stocksigns.events.SignEvent;

public final class StockSigns extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Check and copy "scrape_copper.toml"
        File stocksFile = new File(getDataFolder(), "stocks.toml");
        if (!stocksFile.exists()) {
            saveResource("stocks.toml", false);
        }

        // Register event for signs
        getServer().getPluginManager().registerEvents(new SignEvent(), this);

        // Send message that initialization is done
        getServer().getConsoleSender().sendMessage("[StockSigns] Initialization of StockSigns is done!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
