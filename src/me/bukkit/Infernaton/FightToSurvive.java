package me.bukkit.Infernaton;

import me.bukkit.Infernaton.builder.CountDown;
import me.bukkit.Infernaton.builder.DayNightCycle;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.commands.DebugCommand;
import me.bukkit.Infernaton.commands.PartyCommand;
import me.bukkit.Infernaton.commands.SpawnMobs;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.ConstantHandler;
import me.bukkit.Infernaton.handler.HandleItem;
import me.bukkit.Infernaton.handler.HandlePlayerState;
import me.bukkit.Infernaton.handler.*;
import me.bukkit.Infernaton.listeners.BlockListener;
import me.bukkit.Infernaton.listeners.DoorListeners;
import me.bukkit.Infernaton.listeners.EntityListeners;
import me.bukkit.Infernaton.listeners.PlayerListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static me.bukkit.Infernaton.handler.ConstantHandler.worldName;

public class FightToSurvive extends JavaPlugin {

    //#region HANDLER
    private ConstantHandler constH;
    private final HandlePlayerState HP = new HandlePlayerState(this);
    private final HandleItem HI = new HandleItem(this);
    private FinalPhaseHandler finalPhase;
    private final MobsHandler mobsHandler = new MobsHandler(this);
    private final DoorHandler doorHandler = new DoorHandler(this);
    private final HandleMerchantRecipe handleMR = new HandleMerchantRecipe(this);
    private final BlockHandler BH = new BlockHandler();
    private final StringHandler stringH = new StringHandler(this);

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
    public MobsHandler MH() {
        return mobsHandler;
    }
    public DoorHandler DH() {
        return doorHandler;
    }
    public HandleMerchantRecipe MR() {
        return handleMR;
    }
    public BlockHandler BH(){
        return BH;
    }
    public StringHandler stringH(){
        return stringH;
    }
    //#endregion

    public void enableCommand(String[] commandsName, CommandExecutor executor){
        for(String command: commandsName){
            getCommand(command).setExecutor(executor);
        }
    }
    private void registerEvent(Listener[] listeners){
        PluginManager pm = getServer().getPluginManager();
        for (Listener listener: listeners){
            pm.registerEvents(listener, this);
        }
    }

    public void onStarting(Player sender){
        if (sender.isOp()){
            if (constH.isState(GState.WAITING)) {
                List<Player> redPlayers = constH.getRedTeam().getPlayers();
                List<Player> bluePlayers = constH.getBlueTeam().getPlayers();

                if (Bukkit.getScheduler().getPendingTasks().size() > 0) {
                    ChatHandler.sendError(sender, stringH.countDownStarted());
                } //CountDown started
                //Compare if there the same numbers of players in each team
                else if (redPlayers.size() == bluePlayers.size() && redPlayers.size() != 0) {
                    //Clear all players that attend to play
                    redPlayers.addAll(bluePlayers); //All players in one variable
                    constH.setState(GState.STARTING);

                    ChatHandler.sendInfoMessage(sender, stringH.launch());
                    CountDown.newCountDown(this, 10L);
                    doorHandler.setAllDoors();
                } else {
                    ChatHandler.sendError(sender, stringH.needPlayers());
                } //Not enough player
            } else {
                ChatHandler.sendError(sender, stringH.alreadyLaunched());
            } //Party already launched
        }else {
            ChatHandler.sendError(sender, stringH.needOp());
        } //Need OP
    }

    public void start(){
        ChatHandler.sendMessageListPlayer(constH().getAllTeamsPlayer(), stringH.start());
        DayNightCycle.newCountDown(this);
        List<Player> allPlayers = constH.getAllTeamsPlayer();
        for (Player player: allPlayers) {
            HP.clear(player);
            player.teleport(constH.getBaseLocation(Team.getTeam(player)));
            HP.giveStarterPack(player);
            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }

        MH().setAllPnj();
        constH.setState(GState.PLAYING);
    }

    public void cancelStart() {
        constH.setState(GState.WAITING);
        CountDown.stopAllCountdown(this);
        ChatHandler.sendMessageListPlayer(constH.getAllTeamsPlayer(), stringH.cancelStart());
    }

    public void cancel(){
        Bukkit.getWorld(worldName).setTime(1000);
        List<Player> players = constH.getAllPlayers();
        ChatHandler.sendMessageListPlayer(players, stringH.cancel());

        for (Player player: players) {
            HP.setPlayer(player);
        }
        constH.setState(GState.WAITING);
        doorHandler.setAllDoors();
        BH.resetContainers();
        MH().resetMob();
    }

    public void finish(){
        ChatHandler.toAllPlayer(stringH.end());
        constH.setState(GState.FINISH);
        new BukkitRunnable() {
            @Override
            public void run() {
                ChatHandler.toAllPlayer(stringH.teleport());
                FightToSurvive.this.cancel();
            }
        }.runTaskLater(this, 5*20);
    }

    @Override
    public void onEnable(){
        saveDefaultConfig();
        this.constH = new ConstantHandler(this);
        this.finalPhase = new FinalPhaseHandler(this);

        constH.setState(GState.WAITING);

        //#region set all listeners
        Listener[] listeners = {
                new PlayerListeners(this),
                new DoorListeners(this),
                new BlockListener(this),
                new EntityListeners()
        };
        registerEvent(listeners);
        //#endregion

        //#region command declaration
        String[] debugCommand = {"mob_villager", "setPlayer", "getDoors", "deleteDoors", "getKey", "setVillagers", "killPnj"};
        enableCommand(debugCommand, new DebugCommand(this));

        String[] partyCommand = {"start", "cancelStart", "reset", "forceFinal", "endgame"};
        enableCommand(partyCommand, new PartyCommand(this));

        String[] debugMob = {"mob_zombie"};
        enableCommand(debugMob, new SpawnMobs(this));
        //#endregion

        constH.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());

        new Team(stringH.redTeamName(), constH.getScoreboard()).setTeamColor(ChatColor.RED);
        new Team(stringH.blueTeamName(), constH.getScoreboard()).setTeamColor(ChatColor.BLUE);
        new Team(stringH.spectatorName(), constH.getScoreboard()).setTeamColor(ChatColor.GRAY);

        new CustomRecipeHandler(this);
    }

    @Override
    public void onDisable(){ }
}
