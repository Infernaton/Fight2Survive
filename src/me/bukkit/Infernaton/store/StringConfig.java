package me.bukkit.Infernaton.store;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StringConfig {
    public static List<String> makePlayerList(List<Player> playerList) {
        List<String> str = new ArrayList<>();
        for (Player p : playerList) {
            str.add("- " + p.getName());
        }
        return str;
    }

    // #region get Data from config
    private static String getDataString(String key) {
        return ChatColor.translateAlternateColorCodes('&',
                FightToSurvive.GetConfig().getString("string." + key));
    }

    // #region PNJ Name
    private static String pnjName(String zone) {
        return getDataString("pnjName." + zone);
    }

    public static final String pnjWood() {
        return pnjName("wood");
    }

    public static final String pnjCoal() {
        return pnjName("coal");
    }

    public static final String pnjGold1() {
        return pnjName("gold1");
    }

    public static final String pnjGold2() {
        return pnjName("gold2");
    }

    public static final String pnjIron() {
        return pnjName("iron");
    }

    public static final String pnjDiam() {
        return pnjName("diam");
    }

    public static final String pnjLapis() {
        return pnjName("lapis");
    }
    // #endregion

    // #region item name
    public static final String compassName() {
        return getDataString("navCompass");
    }

    public static final String keyName() {
        return getDataString("keyItem");
    }

    // #region menu item
    private static String clickItem(String itemName) {
        return getDataString("clickInventory." + itemName);
    }

    public static final String launch() {
        return clickItem("launchGame");
    }

    public static final String redTeamItem() {
        return clickItem("red");
    }

    public static final String blueTeamItem() {
        return clickItem("blue");
    }

    public static final String spectatorsItem() {
        return clickItem("spectator");
    }

    public static final String optionItem() {
        return clickItem("option");
    }

    public static final String setupItem() {
        return clickItem("setup");
    }

    public static final String returnItem() {
        return clickItem("return");
    }

    public static final String cancelItem() {
        return clickItem("cancelGame");
    }
    // #endregion

    // #endregion

    // #region Inventory name
    private static String inventoryName(String invType) {
        return getDataString("inventoryName." + invType);
    }

    public static final String teamInventory() {
        return inventoryName("team");
    }

    public static final String cancelInventory() {
        return inventoryName("cancelStart");
    }

    public static final String optionInventory() {
        return inventoryName("options");
    }

    public static final String setupInventory() {
        return inventoryName("setup");
    }
    // #endregion

    // #region team name
    private static String teamName(String team) {
        return getDataString("team." + team);
    }

    public static final String redTeamName() {
        return teamName("red");
    }

    public static final String blueTeamName() {
        return teamName("blue");
    }

    public static final String spectatorName() {
        return teamName("spec");
    }
    // #endregion

    // #region sentence
    private static String sentence(String type) {
        return getDataString("sentence." + type);
    }

    public static final String secondLeft(int second) {
        return sentence("secondLeft").replace("<x>", second + "");
    }

    public static final String needOp() {
        return sentence("needOp");
    }

    public static final String start() {
        return sentence("start");
    }

    public static final String cancel() {
        return sentence("cancel");
    }

    public static final String end() {
        return sentence("end");
    }

    public static final String teleport() {
        return sentence("teleport");
    }

    public static final String avoidBreak() {
        return sentence("avoidBreaking");
    }

    public static final String openDoors() {
        return sentence("opDoors");
    }

    public static final String finalPhase() {
        return sentence("finalPhase");
    }

    // #region Start response
    private static String checkStart(String action) {
        return sentence("checkStart." + action);
    }

    public static final String launched() {
        return checkStart("launched");
    }

    public static final String countDownStarted() {
        return checkStart("countdownStarted");
    }

    public static final String needPlayers() {
        return checkStart("needPlayers");
    }

    public static final String alreadyLaunched() {
        return checkStart("alreadyLaunched");
    }

    public static final String cancelStart() {
        return checkStart("cancelStart");
    }

    public static final String noCountdown() {
        return checkStart("noCountdown");
    }

    public static final String noGame() {
        return checkStart("noGame");
    }

    public static final String quitingReset() {
        return checkStart("quitingReset");
    }
    // #endregion

    // #region time passing by
    private static String timescales(String moment) {
        return sentence("timescales." + moment);
    }

    public static final String nearNight() {
        return timescales("nearNight");
    }

    public static final String nearDay() {
        return timescales("nearDay");
    }

    public static final String day() {
        return timescales("day");
    }

    public static final String night() {
        return timescales("night");
    }
    // #endregion

    // #region debug command
    private static String debugCommand(String command) {
        return sentence("debug." + command);
    }

    private static String preSetPlayer(String playerName, String be) {
        return debugCommand("setPlayer").replace("<player>", playerName).replace("<be>", be);
    }

    public static final String setPlayer(String playerName, String be) {
        return preSetPlayer(playerName, be);
    }

    public static final String getDoors() {
        return debugCommand("getDoors");
    }

    public static final String delDoors() {
        return debugCommand("delDoors");
    }

    public static final String setVill() {
        return debugCommand("setVill");
    }

    public static final String killVill() {
        return debugCommand("killVill");
    }

    public static final String giveKey() {
        return debugCommand("giveKey");
    }

    public static final String notPlayer() {
        return debugCommand("notPlayer");
    }

    public static final String tooManyArgument() {
        return debugCommand("tooManyArgument");
    }

    public static final String correctUsage(String commandName, String args) {
        return debugCommand("correctUsage").replace("<command>", commandName).replace("<args>", args);
    }

    public static final String cantWhilePlaying() {
        return debugCommand("cantWhilePlaying");
    }

    public static final String cantFromConsole() {
        return debugCommand("cantFromConsole");
    }
    // #endregion
    // #endregion
    // #endregion
}
