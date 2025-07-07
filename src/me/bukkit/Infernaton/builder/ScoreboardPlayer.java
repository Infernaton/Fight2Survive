package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.scoreboard.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Setting a special scoreboard for a Player
 *
 * @author TheDarven
 *         And modifyed by us
 */
public class ScoreboardPlayer {

    private Scoreboard scoreboard;
    private final FightToSurvive main;
    private final BukkitRunnable updating;

    public ScoreboardPlayer(FightToSurvive context, Player player) {
        this.scoreboard = new Scoreboard("sidebar", "§6FightToSurvive");
        this.scoreboard.addReceiver(player);
        this.main = context;
        setLines();

        updating = new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        };
        updating.runTaskTimerAsynchronously(main, 1, 1);
    }

    public void removeScoreboard(Player player) {
        this.scoreboard.removeReceiver(player);
        this.scoreboard = null;
        updating.cancel();
    }

    /**
     * Setting all the lines in the sideBar scoreboard
     */
    public void setLines() {
        String[] scLines = main.constH().getScoreboardLines();

        for (int i = 0; i < scLines.length; i++) {
            scoreboard.setLine(i, scLines[i]);
        }
        scoreboard.updateLines();
    }

    /**
     * To actualise the scoreboard, just a reset of all the lines is enough
     */
    private void update() {
        setLines();
    }
}
