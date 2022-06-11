package me.bukkit.Infernaton.handler.scoreboard;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.ScoreboardPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author TheDarven
 */
public class ScoreboardManager {

    private final static HashMap<UUID, ScoreboardPlayer> scoreboardPlayerHashMap = new HashMap<>();
    private final FightToSurvive main;

    public ScoreboardManager(FightToSurvive main) {
        this.main = main;
    }

    public ScoreboardPlayer getScoreboard(Player player) {
        if (scoreboardPlayerHashMap.containsKey(player.getUniqueId())) {
            return scoreboardPlayerHashMap.get(player.getUniqueId());
        }
        return addScoreboard(player);
    }

    public ScoreboardPlayer addScoreboard(Player player) {
        ScoreboardPlayer scoreboardPlayer = new ScoreboardPlayer(main, player);
        scoreboardPlayerHashMap.put(player.getUniqueId(), scoreboardPlayer);
        return scoreboardPlayer;
    }

    public static void removeScoreboard(Player player) {
        ScoreboardPlayer scoreboardPlayer = scoreboardPlayerHashMap.get(player.getUniqueId());
        if (scoreboardPlayer != null) {
            scoreboardPlayer.removeScoreboard(player);
        }
        scoreboardPlayerHashMap.remove(player.getUniqueId());
    }

}
