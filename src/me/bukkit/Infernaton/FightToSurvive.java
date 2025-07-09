package me.bukkit.Infernaton;

import me.bukkit.Infernaton.handler.*;
import me.bukkit.Infernaton.handler.scoreboard.ScoreboardManager;
import me.bukkit.Infernaton.listeners.*;
import me.bukkit.Infernaton.store.Constants;
import me.bukkit.Infernaton.store.CoordStorage;
import me.bukkit.Infernaton.store.Mobs;
import me.bukkit.Infernaton.store.StringConfig;
import me.bukkit.Infernaton.commands.*;
import me.bukkit.Infernaton.builder.*;
import me.bukkit.Infernaton.builder.clock.CountDown;
import me.bukkit.Infernaton.builder.clock.GameRunnable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

import java.util.List;

public class FightToSurvive extends JavaPlugin {

    private static FightToSurvive self;

    public static FightToSurvive Instance() {
        if (self == null) {
            self = new FightToSurvive();
        }
        return self;
    }

    public static FileConfiguration GetConfig() {
        return self.getConfig();
    }

    // #region Game Timer
    private GameRunnable gameTimer;

    public static GameRunnable getTimer() {
        return self.gameTimer;
    }
    // #endregion

    // #region Game State
    private GState state;

    private void setGameState(GState newState) {
        this.state = newState;
    }

    public static boolean isGameState(GState state) {
        return self.state == state;
    }
    // #endregion

    // #region HANDLER
    private final HandlePlayerState HP = new HandlePlayerState();
    private final BlockHandler BH = new BlockHandler();

    public HandlePlayerState HP() {
        return HP;
    }

    public BlockHandler BH() {
        return BH;
    }
    // #endregion

    private ScoreboardManager scoreboardManager;

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public void enableCommand(String[] commandsName, CommandExecutor executor) {
        for (String command : commandsName) {
            getCommand(command).setExecutor(executor);
        }
    }

    private void registerEvent(Listener[] listeners) {
        PluginManager pm = getServer().getPluginManager();
        for (Listener listener : listeners) {
            pm.registerEvents(listener, this);
        }
    }

    public void onStarting(Player sender) {
        // Need OP
        if (!sender.isOp()) {
            ChatHandler.sendError(sender, StringConfig.needOp());
            return;
        }
        // Party already launched
        if (!isGameState(GState.WAITING)) {
            ChatHandler.sendError(sender, StringConfig.alreadyLaunched());
            return;
        }

        List<Player> redPlayers = Constants.getRedTeam().getPlayers();
        List<Player> bluePlayers = Constants.getBlueTeam().getPlayers();

        // Compare if there the same numbers of players in each team
        // Not enough player
        if (redPlayers.size() != bluePlayers.size() || redPlayers.size() == 0) {
            ChatHandler.sendError(sender, StringConfig.needPlayers());
            return;
        }

        // Clear all players that attend to play
        redPlayers.addAll(bluePlayers); // All players in one variable
        setGameState(GState.STARTING);

        ChatHandler.sendInfoMessage(sender, StringConfig.launched());
        CountDown.newCountDown(this, 10L);
        DoorHandler.setAllDoors();
    }

    public void start() {
        ChatHandler.sendMessageListPlayer(Constants.getAllTeamsPlayer(), StringConfig.start());
        gameTimer = GameRunnable.newCountDown(this);

        ServerListener.resetAFKList();

        List<Player> allPlayers = Constants.getAllTeamsPlayer();
        for (Player player : allPlayers) {
            HP.clear(player);
            player.teleport(CoordStorage.getBaseLocation(Team.getTeam(player)));
            HP.giveStarterPack(player);
            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }
        setGameState(GState.PLAYING);

        // Spawning the villager after the player begin the party. Its to make sure all
        // entity are set.
        // We have certain problem with entity that don't appear because of not loaded
        // chunk
        new BukkitRunnable() {
            @Override
            public void run() {
                Mobs.setAllPnj();
            }
        }.runTaskLater(this, 8);
    }

    public void cancelStart() {
        setGameState(GState.WAITING);
        CountDown.stopAllCountdown(this);
        ChatHandler.sendMessageListPlayer(Constants.getAllTeamsPlayer(), StringConfig.cancelStart());
    }

    public void cancel() {
        Bukkit.getWorld(worldName).setTime(1000);
        List<Player> players = Constants.getAllPlayers();
        ChatHandler.sendMessageListPlayer(players, StringConfig.cancel());

        for (Player player : players) {
            HP.setPlayer(player);
        }
        setGameState(GState.WAITING);
        DoorHandler.setAllDoors();
        BH.resetContainers();
        WaveHandler.Instance().resetMob();
        ServerListener.resetAFKList();
    }

    public void finish() {
        ChatHandler.toAllPlayer(StringConfig.end());
        setGameState(GState.FINISH);
        new BukkitRunnable() {
            @Override
            public void run() {
                ChatHandler.toAllPlayer(StringConfig.teleport());
                FightToSurvive.this.cancel();
            }
        }.runTaskLater(this, 5 * 20);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.scoreboardManager = new ScoreboardManager(this);

        setGameState(GState.WAITING);

        // #region set all listeners
        Listener[] listeners = {
                new PlayerListeners(this),
                new DoorListeners(this),
                new BlockListener(this),
                new EntityListeners(),
                new ServerListener(this)
        };
        registerEvent(listeners);
        // #endregion

        // #region command declaration
        String[] debugCommand = { "mob_villager", "setPlayer", "getDoors", "deleteDoors", "getKey", "setVillagers",
                "killPnj" };
        enableCommand(debugCommand, new DebugCommand());

        String[] partyCommand = { "start", "cancelStart", "reset", "forceFinal", "endgame" };
        enableCommand(partyCommand, new PartyCommand());

        String[] debugMob = { "mob_zombie" };
        enableCommand(debugMob, new SpawnMobs());
        // #endregion

        Scoreboard sb = getServer().getScoreboardManager().getMainScoreboard();

        new Team(StringConfig.redTeamName(), sb).setTeamColor(ChatColor.RED);
        new Team(StringConfig.blueTeamName(), sb).setTeamColor(ChatColor.BLUE);
        new Team(StringConfig.spectatorName(), sb).setTeamColor(ChatColor.GRAY);

        new CustomRecipe(this);
    }

    @Override
    public void onDisable() {
    }
}
