package me.andrej123456789.stocksigns.events;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.andrej123456789.stocksigns.StockSigns;

public class SignUpdated implements Listener {

    private static final Plugin plugin = JavaPlugin.getProvidingPlugin(StockSigns.class);
    
    @EventHandler
    public void onSignEvent(SignChangeEvent e) {
        Block sign = e.getBlock();

        plugin.getServer().getConsoleSender().sendMessage(sign.getType().toString());
        
    }
}
