package me.bukkit.Infernaton.handler.scoreboardTest;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.GameRunnable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 *
 * @author TheDarven
 */
public class ScoreboardPlayer {

    private Scoreboard scoreboard;
    private final UUID uuid;
    private final FightToSurvive main;

    public ScoreboardPlayer(FightToSurvive context, Player player) {
        this.uuid = player.getUniqueId();
        this.scoreboard = new Scoreboard("sidebar", "§6FightToSurvive");
        this.scoreboard.addReceiver(player);
        this.main = context;
        setLines();
    }

    public void removeScoreboard(Player player) {
        this.scoreboard.removeReceiver(player);
        this.scoreboard = null;
    }

    public String[] getLines(){
        GameRunnable timer = main.getTimer();
        int intTimer = 0;
        if (timer != null){
            intTimer = timer.getTime();
        }
        return new String[]{
                "§§a",
                "§7Timer: "+intTimer,
                "§§1",
                "§4Red Team ("+ main.constH().getRedTeam().getPlayers().size() +")",
                "§1Blue Team ("+ main.constH().getBlueTeam().getPlayers().size() +")",
                "§§§1",
                "§6----------------"
        };
    }

    public void setLines() {

        for (int i = 0; i < getLines().length; i++) {
            scoreboard.setLine(i, getLines()[i]);
        }
        update();
    }
    public void update(){
        scoreboard.updateLines();
    }

}

