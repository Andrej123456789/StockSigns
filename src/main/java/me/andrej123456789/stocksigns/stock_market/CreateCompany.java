package me.andrej123456789.stocksigns.stock_market;

import me.andrej123456789.stocksigns.StockSigns;
import static me.andrej123456789.stocksigns.StockSigns.getStocksToml;
import me.andrej123456789.stocksigns.config.Config;

import org.bukkit.ChatColor;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ConversationAbandonedListener;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Class handling creation of new company
 */
public class CreateCompany {

    private static final Plugin plugin = JavaPlugin.getProvidingPlugin(StockSigns.class);
    private Config stocks_toml;

    private String confirmation_message_question = "";
    private String confirmation_message_answer = "";

    /* -------------------------------- */

    private String company_name = "";
    private String company_owner = "";

    private String company_code = "";
    private String stock_exchange_code = "";

    private Integer max_stocks = 0;
    private Integer owner_stocks = 0;
    private Integer stock_price = 0;

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

        stocks_toml = getStocksToml();
        stocks_toml.reload();

        // Check if player wants to create a company
        if (lines[0].startsWith("c:")) {
            company_name = lines[0].substring(2, lines[0].length());
        } else {
            return;
        }

        company_owner = player.getName();

        // Parse line 1
        try {
            company_code = lines[1].split(",", 2)[0].toUpperCase();
            stock_exchange_code = lines[1].split(",", 2)[1].toUpperCase();
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

        confirmation_message_question = ChatColor.LIGHT_PURPLE + "Confirm following settings: \n" + ChatColor.RESET + 
                                "Name: " + company_name + "\n" + 
                                "Code: " + company_code + "\n" + 
                                "Number of stocks: " + max_stocks.toString() + "\n" + 
                                "Owner '" + company_owner + "' owning " + owner_stocks.toString() + "\n" + 
                                "Price per stock: " + stock_price.toString() + "\n" + 
                                ChatColor.AQUA + "You can cancel operation by typing 'cancel' or change owner by typing " + 
                                "'o:new_owner'.\nIf however, everything is ok, just send random letter within next 10 seconds.";

        AfterConversation afterConversation = new AfterConversation(player);
        ConversationFactory factory = new ConversationFactory(plugin)
            .withModality(true)
            .withLocalEcho(false)
            .withEscapeSequence("cancel")
            .withTimeout(10)
            .thatExcludesNonPlayersWithMessage("Only players can use this conversation")
            .addConversationAbandonedListener(afterConversation)
            .withFirstPrompt(new QuestionPrompt());
            
        Conversation conversation = factory.buildConversation(player);
        conversation.begin();
    }

    /**
     * Class handling stuff after confirmation input.
     * Write data to .toml file
     */
    private class AfterConversation implements ConversationAbandonedListener {
        Player player = null;

        public AfterConversation(Player _player) {
            player = _player;
        }

        @Override
        public void conversationAbandoned(ConversationAbandonedEvent event) {
            if (confirmation_message_answer.startsWith("o:")) {
                company_owner = confirmation_message_answer.substring(2, confirmation_message_answer.length());
            }
    
            player.sendMessage(company_owner);

            class Company {
                Map<String, Object> map = new HashMap<String, Object>();
            }

            class Stocks {
                Map<String, Object> map = new HashMap<String, Object>();
            }

            Company company = new Company();
            Stocks stocks = new Stocks();

            company.map.put("name", company_name);
            company.map.put("code", company_code.toUpperCase());
            company.map.put("number_of_stocks", max_stocks);
            company.map.put("price_per_stock", stock_price);

            stocks.map.put(company_owner, owner_stocks);
            
            String result = stocks_toml.writeTable(company, "map", stock_exchange_code.toLowerCase() + "." + company_code.toLowerCase());
            String result2 = stocks_toml.writeTable(stocks, "map", stock_exchange_code.toLowerCase() + "." + company_code.toLowerCase() + ".stocks");

            if (result == "" && result2 == "") {
                player.sendMessage(ChatColor.GREEN + "Company successfully created.");
            } else if (result != "") {
                player.sendMessage(ChatColor.RED + result);
            } else if (result2 != "") {
                player.sendMessage(ChatColor.RED + result2);
            }
        }
    }

    /**
     * Ask question and send to user that input has been received if received.
     */
    private class QuestionPrompt extends StringPrompt {
        @Override
        public String getPromptText(ConversationContext context) {
            return confirmation_message_question;
        }

        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            confirmation_message_answer = input;

            context.getForWhom().sendRawMessage(ChatColor.DARK_AQUA + "Confirmed!");
            return END_OF_CONVERSATION;
        }
    }
}
