package me.andrej123456789.stocksigns.commands;

import me.andrej123456789.stocksigns.config.Config;
import static me.andrej123456789.stocksigns.StockSigns.getStocksToml;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to list all stock exchanges
 */
public class ListExchangesCommand implements CommandExecutor, TabExecutor {
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Config stocks_toml = getStocksToml();
        String message = "List of stock exchange: \n";

        List<String> stock_exchanges = stocks_toml.getTables();

        for (String stock_exchange : stock_exchanges) {
            String subString = stocks_toml.geString(stock_exchange + ".name") + " [" + stock_exchange.toUpperCase() + "]\n";
            message = message + subString;
        }

        sender.sendMessage(message);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
