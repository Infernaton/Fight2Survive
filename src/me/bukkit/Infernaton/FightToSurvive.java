package me.bukkit.Infernaton;

import me.bukkit.Infernaton.handler.*;
import me.bukkit.Infernaton.handler.Store.StringHandler;
import me.bukkit.Infernaton.handler.scoreboard.ScoreboardManager;
import me.bukkit.Infernaton.listeners.*;
import me.bukkit.Infernaton.commands.*;
import me.bukkit.Infernaton.builder.*;
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

import static me.bukkit.Infernaton.handler.SpatialHandler.worldName;

public class FightToSurvive extends JavaPlugin {

    private static FightToSurvive self;

    public static FightToSurvive Instance() {
        if (self == null) {
            self = new FightToSurvive();
        }
        return self;
    }

    // #region HANDLER
    private ConstantHandler constH;
    private final HandlePlayerState HP = new HandlePlayerState(this);
    private final HandleItem HI = new HandleItem(this);
    private FinalPhaseHandler finalPhase;
    private final MobsHandler mobsHandler = new MobsHandler(this);
    private final DoorHandler doorHandler = new DoorHandler(this);
    private final HandleMerchantRecipe handleMR = new HandleMerchantRecipe(this);
    private final BlockHandler BH = new BlockHandler();
    private final SpatialHandler SH = new SpatialHandler(this);

    public ConstantHandler constH() {
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

    public BlockHandler BH() {
        return BH;
    }

    public SpatialHandler SH() {
        return SH;
    }
    // #endregion

    // #region Game Timer
    private GameRunnable gameTimer;

    public GameRunnable getTimer() {
        return gameTimer;
    }
    // #endregion

    private ScoreboardManager scoreboardManager;

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public void addingTeam(Team team, Player player) {
        team.add(player);
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
            ChatHandler.sendError(sender, StringHandler.needOp());
            return;
        }
        // Party already launched
        if (!constH.isState(GState.WAITING)) {
            ChatHandler.sendError(sender, StringHandler.alreadyLaunched());
            return;
        }

        List<Player> redPlayers = constH.getRedTeam().getPlayers();
        List<Player> bluePlayers = constH.getBlueTeam().getPlayers();

        // Compare if there the same numbers of players in each team
        // Not enough player
        if (redPlayers.size() != bluePlayers.size() || redPlayers.size() == 0) {
            ChatHandler.sendError(sender, StringHandler.needPlayers());
            return;
        }

        // Clear all players that attend to play
        redPlayers.addAll(bluePlayers); // All players in one variable
        constH.setState(GState.STARTING);

        ChatHandler.sendInfoMessage(sender, StringHandler.launched());
        CountDown.newCountDown(this, 10L);
        doorHandler.setAllDoors();
    }

    public void start() {
        ChatHandler.sendMessageListPlayer(constH().getAllTeamsPlayer(), StringHandler.start());
        gameTimer = GameRunnable.newCountDown(this);

        ServerListener.resetAFKList();

        List<Player> allPlayers = constH.getAllTeamsPlayer();
        for (Player player : allPlayers) {
            HP.clear(player);
            player.teleport(SH.getBaseLocation(Team.getTeam(player)));
            HP.giveStarterPack(player);
            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }
        constH.setState(GState.PLAYING);

        // Spawning the villager after the player begin the party. Its to make sure all
        // entity are set.
        // We have certain problem with entity that don't appear because of not loaded
        // chunk
        new BukkitRunnable() {
            @Override
            public void run() {
                mobsHandler.setAllPnj();
            }
        }.runTaskLater(this, 8);
    }

    public void cancelStart() {
        constH.setState(GState.WAITING);
        CountDown.stopAllCountdown(this);
        ChatHandler.sendMessageListPlayer(constH.getAllTeamsPlayer(), StringHandler.cancelStart());
    }

    public void cancel() {
        Bukkit.getWorld(worldName).setTime(1000);
        List<Player> players = constH.getAllPlayers();
        ChatHandler.sendMessageListPlayer(players, StringHandler.cancel());

        for (Player player : players) {
            HP.setPlayer(player);
        }
        constH.setState(GState.WAITING);
        doorHandler.setAllDoors();
        BH.resetContainers();
        MH().resetMob();
        ServerListener.resetAFKList();
    }

    public void finish() {
        ChatHandler.toAllPlayer(StringHandler.end());
        constH.setState(GState.FINISH);
        new BukkitRunnable() {
            @Override
            public void run() {
                ChatHandler.toAllPlayer(StringHandler.teleport());
                FightToSurvive.this.cancel();
            }
        }.runTaskLater(this, 5 * 20);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.constH = new ConstantHandler(this);
        this.finalPhase = new FinalPhaseHandler(this);
        this.scoreboardManager = new ScoreboardManager(this);

        constH.setState(GState.WAITING);

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
        enableCommand(debugCommand, new DebugCommand(this));

        String[] partyCommand = { "start", "cancelStart", "reset", "forceFinal", "endgame" };
        enableCommand(partyCommand, new PartyCommand(this));

        String[] debugMob = { "mob_zombie" };
        enableCommand(debugMob, new SpawnMobs(this));
        // #endregion

        constH.setScoreboard(getServer().getScoreboardManager().getMainScoreboard());

        new Team(StringHandler.redTeamName(), constH.getScoreboard()).setTeamColor(ChatColor.RED);
        new Team(StringHandler.blueTeamName(), constH.getScoreboard()).setTeamColor(ChatColor.BLUE);
        new Team(StringHandler.spectatorName(), constH.getScoreboard()).setTeamColor(ChatColor.GRAY);

        new CustomRecipe(this);
    }

    @Override
    public void onDisable() {
    }
}
