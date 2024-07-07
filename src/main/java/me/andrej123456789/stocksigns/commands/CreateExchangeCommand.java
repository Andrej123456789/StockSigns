package me.andrej123456789.stocksigns.commands;

import me.andrej123456789.stocksigns.stock_market.CreateExchange;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import javax.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Class handling command for stock market creation.
 * Real work is done in `CreateExchange` class in
 * 
 * /create_exchange <name> <code> <max_companies>
 */
public class CreateExchangeCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 3) {
            return false;
        }

        String name = args[0];
        String code = args[1];
        Integer max_companies = 0;
        
        try {
            max_companies = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            // TODO: handle exception
        }

        new CreateExchange(sender, name, code, max_companies);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>(); /* null = online players */
    }
}
