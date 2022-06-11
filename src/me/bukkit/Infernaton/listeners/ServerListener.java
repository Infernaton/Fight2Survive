package me.bukkit.Infernaton.listeners;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.ChatHandler;
import me.bukkit.Infernaton.handler.InterfaceHandler;
import me.bukkit.Infernaton.handler.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ServerListener implements Listener {

    private final FightToSurvive main;
    private Map<Player, Team> afkList;

    public ServerListener(FightToSurvive main) {
        this.main = main;
        afkList = new HashMap<>();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        main.getScoreboardManager().addScoreboard(player);

        //If the player was originally in the game, we put him in it team
        if(afkList.containsKey(player)){
            Team rightTeam = afkList.get(player);
            if (rightTeam != null) rightTeam.add(player);
            afkList.remove(player);
        }
        //If it's the first time he join, the player don't have a team yet, so we forced him to join one
        else if (!Team.hasTeam(player)) {
            main.constH().getSpectators().add(player);
        }

        //We test if the player is currently in game when he join,
        // if the game crashed client side, it would be a shame if he can't rejoin the party
        boolean isCurrentlyIG = !main.constH().isState(GState.WAITING) &&
                !Team.getTeam(player).getTeamName().equalsIgnoreCase(main.stringH().spectatorName());

        //And, if the player is in creative, we don't need to reset is position
        if (!isCurrentlyIG && player.getGameMode() != GameMode.CREATIVE) {
            main.HP().resetPlayerState(player);
            player.teleport(main.constH().getSpawnCoordinate());
        }
    }

    /**
     * If a player quit the server, the event will trigger
     * in case a player quit when a party is running, we will, after some time, force him to retire from the game
     * If the player rejoin the server before the countdown stop, he will still be part of the game
     * @param event
     */
    @EventHandler
    public void onQuit (PlayerQuitEvent event){
        final Player player = event.getPlayer();
        if (main.constH().isState(GState.PLAYING)) {

            //Setting the quitting player in "AFK", to have a memory of who has quit during the game
            if (Team.hasTeam(player) && Team.getTeam(player) != main.constH().getSpectators()) {
                afkList.put(player, Team.getTeam(player));
                System.out.println(afkList);
                Team.getTeam(player).remove(player);
            }

            // its means that the player who disconnect during a party have 10 seconds, before he will be
            // declared offline by the plugin: testing afterward if the game has to be declared finished
            BukkitRunnable run = new BukkitRunnable() {
                @Override
                public void run() {
                    if (afkList.containsKey(player)
                            && afkList.get(player).getPlayers().isEmpty()){
                        ChatHandler.toAllPlayer(main.stringH().quitingReset());
                        main.finish();
                    }
                }
            };
            run.runTaskLaterAsynchronously(main, 10);
        }
        else if (main.constH().isState(GState.WAITING)){
            Team t = Team.getTeam(player);
            if (t != null) t.remove(player);
        }
        ScoreboardManager.removeScoreboard(player);
    }
}
