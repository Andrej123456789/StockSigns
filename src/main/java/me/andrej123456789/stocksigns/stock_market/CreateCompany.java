package me.andrej123456789.stocksigns.stock_market;

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

import me.andrej123456789.stocksigns.StockSigns;

/**
 * Class handling creation of new company
 */
public class CreateCompany {

    private static final Plugin plugin = JavaPlugin.getProvidingPlugin(StockSigns.class);

    private String confirmation_message_question = "";
    private String confirmation_message_answer = "";

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

        String owner = player.getName();

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
                                "Owner '" + owner + "' owning " + owner_stocks.toString() + "\n" + 
                                "Price per stock: " + stock_price.toString() + "\n" + 
                                ChatColor.AQUA + "You can cancel operation by typing 'cancel' or change owner by typing " + 
                                "'o:new_owner'.\nIf however, everything is ok, just send random letter within next 10 seconds.";

        ConversationFactory factory = new ConversationFactory(plugin)
            .withModality(true)
            .withFirstPrompt(new QuestionPrompt())
            .withEscapeSequence("cancel")
            .withLocalEcho(false)
            .thatExcludesNonPlayersWithMessage("Only players can use this conversation")
            .withTimeout(10);
            
        Conversation conversation = factory.buildConversation(player);
        conversation.begin();

        if (confirmation_message_answer.startsWith("o:")) {
            owner = confirmation_message_answer.substring(2, confirmation_message_answer.length());
        }

        player.sendMessage(owner);
    }

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
