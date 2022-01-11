package me.bukkit.Infernaton;

import me.bukkit.Infernaton.commands.CommandTest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FightToSurvive extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("Hello test!");
        getCommand("hello").setExecutor(new CommandTest());
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabled.");
    }
}
