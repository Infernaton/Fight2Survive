package me.bukkit.Infernaton.handler.scoreboardTest;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 *
 *
 * @author TheDarven
 */
public class ScoreboardPlayer {

    private Scoreboard scoreboard;
    private final UUID uuid;

    public ScoreboardPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.scoreboard = new Scoreboard("sidebar", "Test");
        this.scoreboard.addReceiver(player);
        setLines();
    }

    public void removeScoreboard(Player player) {
        this.scoreboard.removeReceiver(player);
        this.scoreboard = null;
    }

    public void setLines() {
        scoreboard.setLine(0, "Test");
        scoreboard.setLine(1, "Test 2");
        scoreboard.setLine(2, "§2Salut");
        scoreboard.setLine(3, "§1");
        scoreboard.setLine(4, "§cSalut2");
        scoreboard.updateLines();
    }

}

