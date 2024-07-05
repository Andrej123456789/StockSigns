package me.andrej123456789.stocksigns.events;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.andrej123456789.stocksigns.stock_market.CreateCompany;

/**
 * Class handling sign event
 * This event is triggered when closing sign menu
 */
public class SignEvent implements Listener {
    
    @EventHandler
    public void onSignEvent(SignChangeEvent e) {

        // Get block type
        Block block = e.getBlock();

        // Get actual sign
        Sign sign = (Sign) block.getState();

        // Get all lines on the sign
        String[] lines = sign.getLines();

        // Create company from sign
        new CreateCompany(lines);
    }
}
