package me.bukkit.Infernaton;

import me.bukkit.Infernaton.handler.*;
import me.bukkit.Infernaton.handler.scoreboard.ScoreboardManager;
import me.bukkit.Infernaton.listeners.*;
import me.bukkit.Infernaton.store.*;
import me.bukkit.Infernaton.builder.*;
import me.bukkit.Infernaton.builder.clock.CountDown;
import me.bukkit.Infernaton.builder.clock.GameRunnable;
import me.bukkit.Infernaton.commands.DebugCommand;
import me.bukkit.Infernaton.commands.PartyCommand;
import me.bukkit.Infernaton.commands.SpawnMobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

import java.util.Collections;
import java.util.List;

public class FightToSurvive extends JavaPlugin {

    private static FightToSurvive self;

    public static FightToSurvive Instance() {
        return self;
    }

    public static FileConfiguration GetConfig() {
        return Instance().getConfig();
    }

    //#region Game Timer
    private GameRunnable gameTimer;

    public static GameRunnable getTimer() {
        return Instance().gameTimer;
    }
    //#endregion

    //#region Game State
    private GState state;

    private void setGameState(GState newState) {
        this.state = newState;
    }

    public static boolean isGameState(GState state) {
        return Instance().state == state;
    }
    //#endregion

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

        List<Player> randomPlayers = Constants.getRandomTeam().getPlayers();
        Team redTeam = Constants.getRedTeam();
        Team blueTeam = Constants.getBlueTeam();

        //Make random team
        Collections.shuffle(randomPlayers);
        for (Player rPlayer: randomPlayers) {
            if (redTeam.getPlayers().size() < blueTeam.getPlayers().size()) {
                Constants.getRedTeam().add(rPlayer);
            } else {
                Constants.getBlueTeam().add(rPlayer);
            }
        }

        // Compare if there the same numbers of players in each team
        // Not enough player
        if ((redTeam.getPlayers().size() != blueTeam.getPlayers().size() || redTeam.getPlayers().size() == 0)
                && Constants.getRandomTeam().getPlayers().isEmpty()) {
            ChatHandler.sendError(sender, StringConfig.needPlayers());
            return;
        }

        setGameState(GState.STARTING);

        ChatHandler.sendInfoMessage(sender, StringConfig.launched());
        new CountDown(10) {
            @Override
            public void newRun() {
                //If the GState is back to WAITING, it meaning the CancelStart methods has been called
                if (FightToSurvive.isGameState(GState.WAITING)) {
                    stopCountdown(id);
                    return;
                }

                if (time == 0) {
                    FightToSurvive.Instance().start();
                } else if (time % 10 == 0 || time <= 5) {
                    Sounds.tickTimerSound();
                    ChatHandler.toAllPlayer(StringConfig.secondLeft((int) time));
                }
            }
        };
        DoorHandler.setAllDoors();
    }

    public void start() {
        List<Player> allPlayers = Constants.getAllTeamsPlayer();

        ChatHandler.sendMessageListPlayer(allPlayers, StringConfig.start());
        gameTimer = GameRunnable.newCountDown(this);

        ServerListener.resetAFKList();

        for (Player player : allPlayers) {
            HandlePlayerState.clear(player);
            player.teleport(CoordStorage.getBaseLocation(Team.getTeam(player)));
            HandlePlayerState.giveStarterPack(player);
            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }
        setGameState(GState.PLAYING);

        // Spawning the villager after the player begin the party. Its to make sure all
        // entity are set.
        // We have certain problem with entity that don't appear because of not loaded
        // chunk
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                Mobs.setAllPnj();
//            }
//        }.runTaskLater(this, 8);
    }

    public void cancelStart() {
        //By changing the GState to Waiting, it will automatically stop the starting countdown
        setGameState(GState.WAITING);
        ChatHandler.sendMessageListPlayer(Constants.getAllTeamsPlayer(), StringConfig.cancelStart());
    }

    public void reset() {
        List<Player> players = Constants.getAllPlayers();
        ChatHandler.sendMessageListPlayer(players, StringConfig.reset());
        ServerListener.resetAFKList();
        Bukkit.getWorld(worldName).setTime(1000);

        setGameState(GState.WAITING);
        DoorHandler.deleteAllDoors();

        for (Player player : players) {
            HandlePlayerState.setPlayer(player);
        }

        BlockHandler.resetContainers();
        WaveHandler.Instance().resetSpawnedEntity();
        FinalPhaseHandler.Instance().off();
    }

    public void finish() {
        Team winner = null;
        for (Team team : Constants.getPlayableTeam()) {
            if (!team.getPlayers().isEmpty()) {
                winner = team;
                break;
            }
        }
        setGameState(GState.FINISH);

        if (winner != null) {
            ChatHandler.toAllPlayer(StringConfig.end(winner));
            TitleHandler.toAllPlayer(StringConfig.end(winner));
        }
        else {
            ChatHandler.toAllPlayer("No winning team this time ... All players dies");
            ChatHandler.toAllPlayer(StringConfig.teleport());
            FightToSurvive.this.reset();
            return;
        }

        Team finalWinner = winner;
        FireworkEffect.Builder fwB = FireworkEffect.builder();
        FireworkEffect fwe = fwB.trail(true).withColor(Color.WHITE).build();

        new CountDown(15) {
            @Override
            public void newRun() {
                if (time == 0) {
                    ChatHandler.toAllPlayer(StringConfig.teleport());
                    FightToSurvive.this.reset();
                }
                for (Player p : finalWinner.getPlayers()) {
                    Location highest = CoordStorage.getRandomHighestAround(p.getLocation(), 5, 5);
                    if (highest == null)
                        highest = p.getLocation();

                    Firework fw = (Firework) highest.getWorld().spawnEntity(highest, EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    fwm.addEffect(fwe);
                    fw.setFireworkMeta(fwm);
                }
            }
        };
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        self = this;
        this.scoreboardManager = new ScoreboardManager(this);

        setGameState(GState.WAITING);

        //#region set all listeners
        Listener[] listeners = {
                new PlayerListeners(this),
                new DoorListeners(this),
                new BlockListener(this),
                new EntityListeners(),
                new ServerListener(this)
        };
        registerEvent(listeners);
        //#endregion

        //#region command declaration
        String[] partyCommand = { "start", "cancelStart", "reset", "forceFinal" };
        enableCommand(partyCommand, new PartyCommand());

        String[] debugCommand = { "debug" };
        enableCommand(debugCommand, new DebugCommand());

        String[] debugMob = { "mob_zombie", "hologram", "killhologram" };
        enableCommand(debugMob, new SpawnMobs());
        //#endregion

        Scoreboard sb = getServer().getScoreboardManager().getMainScoreboard();

        new Team(StringConfig.redTeamName(), sb).setTeamColor(ChatColor.RED);
        new Team(StringConfig.blueTeamName(), sb).setTeamColor(ChatColor.BLUE);
        new Team(StringConfig.spectatorName(), sb).setTeamColor(ChatColor.GRAY);
        new Team(StringConfig.randomTeamName(), sb).setTeamColor(ChatColor.DARK_GRAY);

        new CustomRecipe(this);
    }

    @Override
    public void onDisable() {
    }
}
