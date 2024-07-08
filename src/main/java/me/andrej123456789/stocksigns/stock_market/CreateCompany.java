package me.andrej123456789.stocksigns.stock_market;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.bukkit.plugin.java.JavaPlugin;

import me.andrej123456789.stocksigns.StockSigns;

/**
 * Class handling creation of new company
 */
public class CreateCompany {

    private static final Plugin plugin = JavaPlugin.getProvidingPlugin(StockSigns.class);

    /**
     * Create a company
     * @param lines all lines on the sign
     * 
     * Line 0 - c:name
     * Line 1 - company code, stock exchange code
     * Line 2 - max_stocks, owner_stocks
     * Line 3 - stock_price
     */
    public CreateCompany(Player player, String[] lines) {
        String company_name = "";

        // Check if player wants to create a company
        if (lines[0].startsWith("c:")) {
            company_name = lines[0].substring(2, lines[0].length());
        } else {
            return;
        }

        String company_code = "";
        String stock_exchange_code = "";

        Integer max_stocks = 0;
        Integer owner_stocks = 0;
        Integer stock_price = 0;

        // Parse line 1
        try {
            company_code = lines[1].split(",", 2)[0];
            stock_exchange_code = lines[1].split(",", 2)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            player.sendMessage(ChatColor.RED + "There was error splitting second line.\n" + 
                                "Please check if comma is included!");

            return;
        }

        // Parse line 2
        try {
            max_stocks = Integer.parseInt(lines[2].split(",", 2)[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "There was error converting string into number.\n" + 
                                "Please check if value is a number!");

            return;
        }

        try {
            owner_stocks = Integer.parseInt(lines[2].split(",", 2)[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "There was error converting string into number.\n" + 
                                "Please check if value is a number!");

            return;
        }

        // Parse line 3
        try {
            stock_price = Integer.parseInt(lines[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "There was error converting string into number.\n" + 
                                "Please check if value is a number!");

            return;
        }

        if (company_code.length() != 4) {
            player.sendMessage(ChatColor.YELLOW + "Company code is less or more than 4 characters!");
            return;
        }
    
        if (stock_exchange_code.length() != 4) {
            player.sendMessage(ChatColor.YELLOW + "Stock exchange code is less or more than 4 characters!");
            return;
        }

        if (max_stocks < 1) {
            player.sendMessage(ChatColor.YELLOW + "Number of stocks should be greater than zero!");
            return;
        }

        if (max_stocks < 1) {
            player.sendMessage(ChatColor.YELLOW + "Number of stocks should be greater than zero!");
            return;
        }

        if (stock_price < 1) {
            player.sendMessage(ChatColor.YELLOW + "Stock price should be greater than zero!");
            return;
        }

        player.sendMessage(company_name);
        player.sendMessage(company_code + " | " + stock_exchange_code);
        player.sendMessage(max_stocks.toString() + " | " + owner_stocks.toString());
        player.sendMessage(stock_price.toString());
    }
}
