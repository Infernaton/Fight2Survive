package me.bukkit.Infernaton;

import me.bukkit.Infernaton.listeners.DoorListeners;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.commands.DebugCommand;
import me.bukkit.Infernaton.commands.SpawnVillager;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.ConstantHandler;
import me.bukkit.Infernaton.handler.HandleItem;
import me.bukkit.Infernaton.handler.HandlePlayerState;
import me.bukkit.Infernaton.listeners.BlockListener;
import me.bukkit.Infernaton.listeners.PlayerListeners;
import me.bukkit.Infernaton.listeners.TradeMenuListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class FightToSurvive extends JavaPlugin {

    private ConstantHandler constH;
    private HandlePlayerState HP = new HandlePlayerState(this);
    private HandleItem HI = new HandleItem(this);

    public ConstantHandler constH(){ return constH; }
    public HandlePlayerState HP() { return HP; }
    public HandleItem HI() { return HI; }

    public void enableCommand(String[] commandsName, CommandExecutor executor){
        for(String command: commandsName){
            getCommand(command).setExecutor(executor);
        }
    }

    public void start(){
        ChatHandler.broadcast("§eStart the Game!");
        List<Player> redPlayers = constH.getRedTeam().getPlayers();
        List<Player> bluePlayers = constH.getBlueTeam().getPlayers();

        for(Player player: redPlayers){
            player.teleport(constH.getRedBase());
            player.getInventory().addItem(new ItemStack(Material.STONE_AXE));
        }

        for(Player player: bluePlayers){
            player.teleport(constH.getBlueBase());
            player.getInventory().addItem(new ItemStack(Material.STONE_AXE));
        }
    }

    public void cancel(){

    }

    public void finish(){

    }

    @Override
    public void onEnable(){
        saveDefaultConfig();
        this.constH = new ConstantHandler(this);

        constH.setState(GState.WAITING);
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerListeners(this), this);
        getServer().getPluginManager().registerEvents(new DoorListeners(this), this);
        pm.registerEvents(new TradeMenuListener(),this);
        pm.registerEvents(new BlockListener(this), this);

        String[] debugCommand = {"setPlayer", "start", "cancelStart"};
        enableCommand(debugCommand, new DebugCommand(this));

        String[] spawnCommand = {"mob_villager", "trade"};
        enableCommand(spawnCommand, new SpawnVillager());
        constH.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());

        new Team("Red", constH.getScoreboard()).setTeamColor(ChatColor.RED);
        new Team("Blue", constH.getScoreboard()).setTeamColor(ChatColor.BLUE);
        new Team("Spectators", constH.getScoreboard()).setTeamColor(ChatColor.GRAY);
    }

    @Override
    public void onDisable(){ }
}
