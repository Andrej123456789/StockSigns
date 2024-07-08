package me.andrej123456789.stocksigns.stock_market;

import me.andrej123456789.stocksigns.config.Config;
import static me.andrej123456789.stocksigns.StockSigns.getStocksToml;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class implementing creation of stock exchange
 */
public class CreateExchange {

    /**
     * Entry function
     * @param sender player or console running this command
     * @param name name of future stock exchanges
     * @param code code of future stock exchange 
     * @param max_companies number of companies allowed on future stock exchange
     */
    public CreateExchange(@NotNull CommandSender sender, String name, String code, Integer max_companies) {
        Config stocks_toml = getStocksToml();
        stocks_toml.reload();

        ArrayList<String> current_stock_exchanges = stocks_toml.getTables();

        /* Perform checks */

        if (current_stock_exchanges.contains(code)) {
            sender.sendMessage(ChatColor.YELLOW + "Stock exchange with code '" + code + "' already exists!");
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

        /* Create class containing data and send it to toml writer function */

        /** 
         * Class containing data to be written to .TOML file
         */
        class StockExchange {
            Map<String, Object> map = new HashMap<String, Object>();
        };

        StockExchange stockExchange = new StockExchange();

        stockExchange.map.put("name", name);
        stockExchange.map.put("code", code.toUpperCase()); // Code will be upper case
        stockExchange.map.put("max_companies", max_companies);

        // Table name will be lowercase
        String result = stocks_toml.writeTable(stockExchange, code.toLowerCase());

        if (result == "") {
            sender.sendMessage(ChatColor.GREEN + "Stock exchange successfully created.");
        } else {
            sender.sendMessage(ChatColor.RED + result);
        }
    }
}
