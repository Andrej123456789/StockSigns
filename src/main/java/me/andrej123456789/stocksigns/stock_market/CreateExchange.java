package me.andrej123456789.stocksigns.stock_market;

import me.andrej123456789.stocksigns.config.Config;
import static me.andrej123456789.stocksigns.StockSigns.getStocksToml;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CreateExchange {
    public CreateExchange(@NotNull CommandSender sender, String name, String code, Integer max_companies) {
        Config stocks_toml = getStocksToml();

        ArrayList<String> current_stock_exchanges = stocks_toml.getTables();

        if (current_stock_exchanges.contains(name)) {
            sender.sendMessage(ChatColor.YELLOW + "Stock exchange '" + name + "' already exists!");
            return;            
        }

        if (code.length() != 4) {
            sender.sendMessage(ChatColor.YELLOW + "Code should contain four letters!");
            return;
        }

        if (max_companies < 1) {
            sender.sendMessage(ChatColor.YELLOW + "Number of companies should be greater than zero!");
            return;
        }

        String result = stocks_toml.writeEmptyTable(name);

        if (result == "") {
            sender.sendMessage(ChatColor.GREEN + "Stock exchange successfully created.");
        } else {
            sender.sendMessage(ChatColor.RED + result);
        }
    }
}
