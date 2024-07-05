package me.andrej123456789.stocksigns.stock_market;

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
     */
    public CreateCompany(String[] lines) {
        plugin.getServer().getConsoleSender().sendMessage(lines);

        String company_name = "";

        // Check if player wants to create a company
        if (lines[0].startsWith("c:")) {
            company_name = lines[0].substring(2, lines[0].length());
        } else {
            return;
        }

        String stock_exchange = "";

        Integer number_of_stocks = 0;
        Integer owner_stocks = 0;
        Integer one_stock_price = 0;

        stock_exchange = lines[1];

        // Split line two into maximum number of stocks and number of owners' stock
        String[] parts = lines[2].split(",", 2);

        try {
            number_of_stocks = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            /* print error */
        }

        try {
            owner_stocks = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            /* print error */
        }

        try {
            one_stock_price = Integer.parseInt(lines[3]);
        } catch (NumberFormatException e) {
            /* print error */
        }
    }
}
