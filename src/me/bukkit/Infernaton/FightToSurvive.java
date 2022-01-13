package me.bukkit.Infernaton;

import me.bukkit.Infernaton.commands.DebugCommand;
import me.bukkit.Infernaton.listeners.PlayerListeners;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FightToSurvive extends JavaPlugin {

    ConstantHandler constH = new ConstantHandler();

    public ConstantHandler constH(){
        return constH;
    }

    @Override
    public void onEnable(){
        constH.setState(GState.WAITING);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListeners(this), this);
        getCommand("setPlayer").setExecutor(new DebugCommand());

        constH.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());

        new Team("Red", constH.getScoreboard()).setTeamColor(ChatColor.RED);
        new Team("Blue", constH.getScoreboard()).setTeamColor(ChatColor.BLUE);
        new Team("Spectators", constH.getScoreboard()).setTeamColor(ChatColor.GRAY);
    }

    @Override
    public void onDisable(){ }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if(cmd.getName().equalsIgnoreCase("mob") && sender instanceof Player){

            Player player = (Player)sender;
            Location location = new Location(player.getWorld(), 85.976,56,177.573);

            Villager villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
            villager.setCustomName("Passe Partout");
            villager.setCustomNameVisible(true);

            return true;
        }
        if(cmd.getName().equalsIgnoreCase("hello") && sender instanceof Player){

            Player player = (Player)sender;

            player.sendMessage("Hello, "+ player.getName());

            return true;
        }

        if(cmd.getName().equalsIgnoreCase("trade") && sender instanceof Player){
            Player p = (Player)sender;
            if(args.length == 0){
                OpenMenuTrade trade = new OpenMenuTrade("shop");
                trade.addTrade(new ItemStack(Material.COBBLESTONE, 10),new ItemStack(Material.WOOL, 30));
                trade.addTrade(new ItemStack(Material.STICK, 2),new ItemStack(Material.WOOD, 3), new ItemStack(Material.WOOD_AXE, 1));
                trade.openTrade(p);
            }
        }
        return false;
    }
}
