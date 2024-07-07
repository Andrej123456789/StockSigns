package me.andrej123456789.stocksigns;

import me.andrej123456789.stocksigns.config.Config;
import me.andrej123456789.stocksigns.commands.CreateExchangeCommand;
import me.andrej123456789.stocksigns.events.SignEvent;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class StockSigns extends JavaPlugin {

    private static Config stocks_toml;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Check and copy "scrape_copper.toml"
        File stocksFile = new File(getDataFolder(), "stocks.toml");
        if (!stocksFile.exists()) {
            saveResource("stocks.toml", false);
        }

        stocks_toml = new Config(getDataFolder() + "/stocks.toml");

        // Register event for signs
        getServer().getPluginManager().registerEvents(new SignEvent(), this);

        // Register commands
        getCommand("create_exchange").setExecutor(new CreateExchangeCommand());

        // Send message that initialization is done
        getServer().getConsoleSender().sendMessage("[StockSigns] Initialization of StockSigns is done!");
    }

    public static Config getStocksToml() {
        return stocks_toml;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
