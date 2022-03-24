package me.bukkit.Infernaton;

import me.bukkit.Infernaton.builder.CountDown;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.commands.DebugCommand;
import me.bukkit.Infernaton.handler.*;
import me.bukkit.Infernaton.listeners.BlockListener;
import me.bukkit.Infernaton.listeners.DoorListeners;
import me.bukkit.Infernaton.listeners.PlayerListeners;
import me.bukkit.Infernaton.listeners.TradeMenuListener;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class FightToSurvive extends JavaPlugin {

    private ConstantHandler constH;
    private final HandlePlayerState HP = new HandlePlayerState(this);
    private final HandleItem HI = new HandleItem(this);
    private FinalPhaseHandler finalPhase;

    public ConstantHandler constH(){
        return constH;
    }
    public HandlePlayerState HP() {
        return HP;
    }
    public HandleItem HI() {
        return HI;
    }
    public FinalPhaseHandler FP() {
        return finalPhase;
    }

    public void enableCommand(String[] commandsName, CommandExecutor executor){
        for(String command: commandsName){
            getCommand(command).setExecutor(executor);
        }
    }

    public void onStarting(Player sender){
        if (constH.isState(GState.WAITING)) {
            List<Player> redPlayers = constH.getRedTeam().getPlayers();
            List<Player> bluePlayers = constH.getBlueTeam().getPlayers();

            if (Bukkit.getScheduler().getPendingTasks().size() > 0) {
                ChatHandler.sendError(sender, "CountDown already launch .");
            } //CountDown started
            //Compare if there the same numbers of players in each team
            else if (redPlayers.size() == bluePlayers.size() && redPlayers.size() != 0) {
                //Clear all players that attend to play
                redPlayers.addAll(bluePlayers); //All players in one variable
                for(Player player: redPlayers){
                    HP.clear(player);
                }
                constH.setState(GState.STARTING);

                ChatHandler.sendInfoMessage(sender, "Initialize the countdown...");
                CountDown.newCountDown(this, 10L);
                constH.setAllDoors();
            } else {
                ChatHandler.sendError(sender, "Not enough players.");
            } //Not enough player
        } else {
            ChatHandler.sendError(sender, "Party already started !");
        } //Party already launched
    }

    public void start(){
        ChatHandler.sendMessageListPlayer(constH().getAllTeamsPlayer(), "§eStart the Game!");

        List<Player> redPlayers = constH.getRedTeam().getPlayers();
        List<Player> bluePlayers = constH.getBlueTeam().getPlayers();
        net.minecraft.server.v1_8_R3.ItemStack WOOD_AXE = CraftItemStack.asNMSCopy(new ItemStack(Material.WOOD_AXE, 1));
        NBTTagList idsTag1 = new NBTTagList();
        idsTag1.add(new NBTTagString("minecraft:log"));
        NBTTagCompound tag1 = WOOD_AXE.hasTag() ? WOOD_AXE.getTag() : new NBTTagCompound();
        tag1.set("CanDestroy", idsTag1);
        WOOD_AXE.setTag(tag1);

        for(Player player: redPlayers){
            player.teleport(constH.getRedBase());
            player.getInventory().addItem(CraftItemStack.asBukkitCopy(WOOD_AXE));
            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }

        for(Player player: bluePlayers){
            player.teleport(constH.getBlueBase());
            player.getInventory().addItem(CraftItemStack.asBukkitCopy(WOOD_AXE));
            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }

        constH().setState(GState.PLAYING);
    }

    public void cancel(){

    }

    public void finish(){

    }

    @Override
    public void onEnable(){
        saveDefaultConfig();
        this.constH = new ConstantHandler(this);
        this.finalPhase = new FinalPhaseHandler(this);

        constH.setState(GState.WAITING);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListeners(this), this);
        pm.registerEvents(new DoorListeners(this), this);
        pm.registerEvents(new TradeMenuListener(this),this);
        pm.registerEvents(new BlockListener(this), this);

        String[] debugCommand = {"mob_villager", "setPlayer", "start", "cancelStart", "reset", "forceFinal" , "manage_time", "getDoors", "deleteDoors"};
        enableCommand(debugCommand, new DebugCommand(this));

        constH.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());

        new Team("Red", constH.getScoreboard()).setTeamColor(ChatColor.RED);
        new Team("Blue", constH.getScoreboard()).setTeamColor(ChatColor.BLUE);
        new Team("Spectators", constH.getScoreboard()).setTeamColor(ChatColor.GRAY);

        new CustomRecipeHandler(this);
    }

    @Override
    public void onDisable(){ }
}
