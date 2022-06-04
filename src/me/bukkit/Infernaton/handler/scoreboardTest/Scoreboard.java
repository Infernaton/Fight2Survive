package me.bukkit.Infernaton.handler.scoreboardTest;

import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Creates and displays a custom sidebar Scoreboard
 *
 * @author TheDarven
 */
public class Scoreboard {

    //#region static field
    private static final String FIELD_SCOREBOARD_NAME = "a";
    private static final String FIELD_SCOREBOARD_DISPLAY_NAME = "b";
    private static final String FIELD_SCOREBOARD_HEALTH_FORMAT = "c";
    private static final String FIELD_SCOREBOARD_ACTION = "d";

    private static final String FIELD_DISPLAY_LOCATION = "a";
    private static final String FIELD_DISPLAY_NAME = "b";

    private static final String FIELD_SCORE_NAME = "a";
    private static final String FIELD_SCORE_SCOREBOARD_NAME = "b";
    private static final String FIELD_SCORE_VALUE = "c";
    private static final String FIELD_SCORE_ACTION = "d";
    //#endregion

    private String name;
    private final String displayName;
    private final List<OfflinePlayer> receivers = new ArrayList<>();
    private final HashMap<Integer, String> lines = new HashMap<>();
    private final ConcurrentLinkedQueue<ScoreboardLine> synchronizedLines = new ConcurrentLinkedQueue<>();

    public Scoreboard(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;

        for (int i = 0; i < 19; i++) {
            lines.put(i, null);
        }
    }

    /**
     * Adds a Player to the scoreboard
     *
     * @param player player
     */
    public void addReceiver(Player player) {
        if (!player.isOnline()) {
            return;
        }

        this.receivers.add(player);

        sendScoreboardPacket(player, this.name, this.displayName, ScoreboardObjectiveActionEnum.CREATE);
        sendDisplayScoreboardPacket(player);
        updateLines(player);
    }

    /**
     * Removes a player from the scoreboard
     *
     * @param player
     */
    public void removeReceiver(Player player) {
        this.receivers.remove(player);
        if (player.isOnline()) {
            sendScoreboardPacket(player, this.name, this.displayName, ScoreboardObjectiveActionEnum.REMOVE);
        }
    }

    /**
     * Modifies the content of a line
     *
     * @param nbLine
     * @param content
     */
    public void setLine(int nbLine, String content){
        ScoreboardLine oldLine = this.getSynchronizedLine(this.lines.get(nbLine));
        this.synchronizedLines.remove(oldLine);

        ScoreboardLine newLine = this.getSynchronizedLine(content);
        newLine.setScore(nbLine);

        this.lines.put(nbLine, content);
    }

    /**
     * Deletes a line
     *
     * @param nbLine
     */
    public void removeLine(int nbLine) {
        ScoreboardLine oldLine = this.getSynchronizedLine(this.lines.get(nbLine));
        this.synchronizedLines.remove(oldLine);
    }

    /**
     * Sends the lines to the players
     */
    public void updateLines() {
        String oldName = changeNameBeforeDeleting();

        this.receivers.stream()
                .filter(OfflinePlayer::isOnline)
                .forEach(receiver ->  {
                    Player player = receiver.getPlayer();
                    sendScoreboardPacket(player, this.name, this.displayName, ScoreboardObjectiveActionEnum.CREATE);
                    updateLines(player);
                    sendDisplayScoreboardPacket(player);

                    sendScoreboardPacket(player, oldName, "", ScoreboardObjectiveActionEnum.REMOVE);
                });
    }

    /**
     * Changes the name of the scoreboard before deleting it
     *
     * @return
     */
    private String changeNameBeforeDeleting() {
        String oldName = this.name;

        if (this.name.endsWith("♠")) {
            this.name = this.name.substring(0, this.name.length() - 1);
        } else {
            this.name += "♠";
        }

        return oldName;
    }

    /**
     * Finds the synchronized line from its content
     * If it doesn't exist, the line is created
     *
     * @param content
     * @return
     */
    private ScoreboardLine getSynchronizedLine(String content) {
        for (ScoreboardLine synchronizedLine: this.synchronizedLines) {
            if (synchronizedLine.getContent().equals(content)) {
                return synchronizedLine;
            }
        }

        ScoreboardLine synchronizedLine = new ScoreboardLine(content, 0);
        this.synchronizedLines.add(synchronizedLine);

        return synchronizedLine;
    }

    /**
     * Updates the lines of a Player
     *
     * @param player
     */
    private void updateLines(Player player) {
        for (ScoreboardLine scoreboardLine: this.synchronizedLines) {
            sendScoreboardScorePacket(player, PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE, scoreboardLine.getContent(), this.synchronizedLines.size() - scoreboardLine.getScore() - 1);
        }
    }



    /**
     * Sends a scoreboard packet to a Player
     *
     * @param player
     * @param action
     */
    private void sendScoreboardPacket(Player player, String name, String displayName, ScoreboardObjectiveActionEnum action) {
        PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();
        try {
            Reflection.setValue(packet, FIELD_SCOREBOARD_NAME, name);
            Reflection.setValue(packet, FIELD_SCOREBOARD_DISPLAY_NAME, displayName);
            Reflection.setValue(packet, FIELD_SCOREBOARD_HEALTH_FORMAT, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
            Reflection.setValue(packet, FIELD_SCOREBOARD_ACTION, action.getValue());

            Reflection.sendPackets(player, packet);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the scoreboard to a Player
     *
     * @param player
     */
    private void sendDisplayScoreboardPacket(Player player) {
        PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();
        try {
            Reflection.setValue(packet, FIELD_DISPLAY_LOCATION, DisplayScoreboardEnum.SIDEBAR.getValue());
            Reflection.setValue(packet, FIELD_DISPLAY_NAME, this.name);

            Reflection.sendPackets(player, packet);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a scoreboard's score packet to a Player
     *
     * @param player
     * @param action
     * @param scoreName
     * @param scoreValue
     */
    private void sendScoreboardScorePacket(Player player, PacketPlayOutScoreboardScore.EnumScoreboardAction action, String scoreName, int scoreValue) {
        PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();
        try {
            Reflection.setValue(packet, FIELD_SCORE_NAME, scoreName);
            Reflection.setValue(packet, FIELD_SCORE_SCOREBOARD_NAME, this.name);
            Reflection.setValue(packet, FIELD_SCORE_VALUE, scoreValue);
            Reflection.setValue(packet, FIELD_SCORE_ACTION, action);
            Reflection.sendPackets(player, packet);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    /**
     * Synchronized scoreboard's line class
     */
    private static class ScoreboardLine {
        private final String content;
        private int score;

        public ScoreboardLine(String content, int score) {
            this.content = content;
            this.score = score;
        }

        public int getScore() {
            return this.score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getContent() {
            return this.content;
        }
    }

    private enum ScoreboardObjectiveActionEnum {
        CREATE(0),
        REMOVE(1),
        UPDATE(2);

        private final int value;

        ScoreboardObjectiveActionEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    private enum DisplayScoreboardEnum {
        LIST(0),
        SIDEBAR(1),
        BELOW_NAME(2);

        private final int value;

        DisplayScoreboardEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

}

