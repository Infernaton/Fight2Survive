package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @todo set static function
 */
public class StringHandler {
    private final FightToSurvive main;

    public StringHandler(FightToSurvive context) {
        this.main = context;
    }

    public List<String> makePlayerList(List<Player> playerList) {
        List<String> str = new ArrayList<>();
        for (Player p : playerList) {
            str.add("- " + p.getName());
        }
        return str;
    }

    public String mobWaveKey() {
        return "_mobWaveCD_";
    }

    // #region get Data from config
    private String getDataString(String key) {
        return ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("string." + key));
    }

    public final String worldName() {
        return getDataString("worldName");
    }

    public final String compassName() {
        return getDataString("navCompass");
    }

    public final String keyName() {
        return getDataString("keyItem");
    }

    private String pnjName(String zone) {
        return getDataString("pnjName." + zone);
    }

    public final String pnjWood() {
        return pnjName("wood");
    }

    public final String pnjCoal() {
        return pnjName("coal");
    }

    public final String pnjGold1() {
        return pnjName("gold1");
    }

    public final String pnjGold2() {
        return pnjName("gold2");
    }

    public final String pnjIron() {
        return pnjName("iron");
    }

    public final String pnjDiam() {
        return pnjName("diam");
    }

    public final String pnjLapis() {
        return pnjName("lapis");
    }

    private String clickItem(String itemName) {
        return getDataString("clickInventory." + itemName);
    }

    public final String launch() {
        return clickItem("launchGame");
    }

    public final String redTeamItem() {
        return clickItem("red");
    }

    public final String blueTeamItem() {
        return clickItem("blue");
    }

    public final String spectatorsItem() {
        return clickItem("spectator");
    }

    public final String optionItem() {
        return clickItem("option");
    }

    public final String returnItem() {
        return clickItem("return");
    }

    public final String cancelItem() {
        return clickItem("cancelGame");
    }

    private String inventoryName(String invType) {
        return getDataString("inventoryName." + invType);
    }

    public final String teamInventory() {
        return inventoryName("team");
    }

    public final String cancelInventory() {
        return inventoryName("cancelStart");
    }

    public final String optionInventory() {
        return inventoryName("options");
    }

    private String teamName(String team) {
        return getDataString("team." + team);
    }

    public final String redTeamName() {
        return teamName("red");
    }

    public final String blueTeamName() {
        return teamName("blue");
    }

    public final String spectatorName() {
        return teamName("spec");
    }

    private String sentence(String type) {
        return getDataString("sentence." + type);
    }

    public final String secondLeft(int second) {
        return sentence("secondLeft").replace("<x>", second + "");
    }

    public final String needOp() {
        return sentence("needOp");
    }

    public final String start() {
        return sentence("start");
    }

    public final String cancel() {
        return sentence("cancel");
    }

    public final String end() {
        return sentence("end");
    }

    public final String teleport() {
        return sentence("teleport");
    }

    public final String avoidBreak() {
        return sentence("avoidBreaking");
    }

    public final String openDoors() {
        return sentence("opDoors");
    }

    public final String finalPhase() {
        return sentence("finalPhase");
    }

    private String checkStart(String action) {
        return sentence("checkStart." + action);
    }

    public final String launched() {
        return checkStart("launched");
    }

    public final String countDownStarted() {
        return checkStart("countdownStarted");
    }

    public final String needPlayers() {
        return checkStart("needPlayers");
    }

    public final String alreadyLaunched() {
        return checkStart("alreadyLaunched");
    }

    public final String cancelStart() {
        return checkStart("cancelStart");
    }

    public final String noCountdown() {
        return checkStart("noCountdown");
    }

    public final String noGame() {
        return checkStart("noGame");
    }

    public final String quitingReset() {
        return checkStart("quitingReset");
    }

    private String timescales(String moment) {
        return sentence("timescales." + moment);
    }

    public final String nearNight() {
        return timescales("nearNight");
    }

    public final String nearDay() {
        return timescales("nearDay");
    }

    public final String day() {
        return timescales("day");
    }

    public final String night() {
        return timescales("night");
    }

    private String debugCommand(String command) {
        return sentence("debug." + command);
    }

    private String preSetPlayer(String playerName, String be) {
        return debugCommand("setPlayer").replace("<player>", playerName).replace("<be>", be);
    }

    public final String setPlayer(String playerName, String be) {
        return preSetPlayer(playerName, be);
    }

    public final String getDoors() {
        return debugCommand("getDoors");
    }

    public final String delDoors() {
        return debugCommand("delDoors");
    }

    public final String setVill() {
        return debugCommand("setVill");
    }

    public final String killVill() {
        return debugCommand("killVill");
    }

    public final String giveKey() {
        return debugCommand("giveKey");
    }

    public final String notPlayer() {
        return debugCommand("notPlayer");
    }

    public final String tooManyArgument() {
        return debugCommand("tooManyArgument");
    }

    public final String correctUsage(String commandName, String args) {
        return debugCommand("correctUsage").replace("<command>", commandName).replace("<args>", args);
    }

    public final String cantWhilePlaying() {
        return debugCommand("cantWhilePlaying");
    }

    public final String cantFromConsole() {
        return debugCommand("cantFromConsole");
    }
    // #endregion
}
