package me.bukkit.Infernaton.store;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.builder.clock.GameRunnable;
import net.minecraft.server.v1_8_R3.MerchantRecipe;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Class which regroup each variable we need in our project
 */
public class Constants {

    public static String[] pnjName() {
        return new String[] {
                // Team Blue
                StringConfig.pnjWood(),
                StringConfig.pnjCoal(),
                // StringHandler.pnjGold1(),
                StringConfig.pnjGold2(),
                StringConfig.pnjIron(),
                StringConfig.pnjDiam(),
                StringConfig.pnjLapis(),

                // Team Red
                StringConfig.pnjWood(),
                StringConfig.pnjCoal(),
                // StringHandler.pnjGold1(),
                StringConfig.pnjGold2(),
                StringConfig.pnjIron(),
                StringConfig.pnjDiam(),
                StringConfig.pnjLapis()
        };
    }

    public static Map<String, MerchantRecipe> getAllTrade() {
        Map<String, MerchantRecipe> trade = new HashMap<>();
        trade.put(StringConfig.pnjWood(), CustomMerchantRecipe.defaultTrade());
//                CustomMerchantRecipe.tradingKey(new ItemStack(Material.LOG, 10), new ItemStack(Material.COBBLESTONE, 10)));
        trade.put(StringConfig.pnjCoal(), CustomMerchantRecipe.defaultTrade());
//                CustomMerchantRecipe.tradingKey(new ItemStack(Material.COAL_BLOCK, 3)));
        trade.put(StringConfig.pnjGold1(), CustomMerchantRecipe.defaultTrade());
        trade.put(StringConfig.pnjGold2(), CustomMerchantRecipe.defaultTrade());
//                CustomMerchantRecipe.tradingKey(new ItemStack(Material.GOLD_NUGGET, 50)));
        trade.put(StringConfig.pnjIron(), CustomMerchantRecipe.defaultTrade());
//                CustomMerchantRecipe.tradingKey(new ItemStack(Material.IRON_BLOCK, 2),
//                        new ItemStack(Material.IRON_INGOT, 5)));
        trade.put(StringConfig.pnjDiam(), CustomMerchantRecipe.defaultTrade());
//                CustomMerchantRecipe.tradingKey(new ItemStack(Material.DIAMOND, 6), new ItemStack(Material.COAL, 12)));
        trade.put(StringConfig.pnjLapis(), CustomMerchantRecipe.defaultTrade());
//                CustomMerchantRecipe.tradingKey(new ItemStack(Material.LAPIS_BLOCK, 6),
//                        new ItemStack(Material.OBSIDIAN, 6)));

        return trade;
    }

    public static MerchantRecipe getTrade(String pnjName) {
        return getAllTrade().get(pnjName);
    }

    //#region Team related
    public static Team getRedTeam() {
        return Team.getTeamByName(StringConfig.redTeamName());
    }

    public static Team getBlueTeam() {
        return Team.getTeamByName(StringConfig.blueTeamName());
    }

    public static Team getRandomTeam() {
        return Team.getTeamByName(StringConfig.randomTeamName());
    }

    public static Team getSpectators() {
        return Team.getTeamByName(StringConfig.spectatorName());
    }

    public static void addDefaultTeam(Player player) {
        if (FightToSurvive.isGameState(GState.WAITING))
            //If the game isn't started yet, we add them in the random team, that way he will play the game
            getRandomTeam().add(player);
        else
            //If not, we can't add them to a playable team, so we add them in the spectator team
            getSpectators().add(player);
    }

    public static List<Player> getAllTeamsPlayer() {
        List<Player> allPlayers = getBlueTeam().getPlayers();
        allPlayers.addAll(getRedTeam().getPlayers());
        return allPlayers;
    }

    public static List<Player> getAllPlayers() {
        List<Team> teamList = Team.getAllTeams();
        List<Player> allPlayers = new ArrayList<>();
        for (Team team : teamList) {
            allPlayers.addAll(team.getPlayers());
        }
        return allPlayers;
    }

    public static List<Team> getPlayableTeam() {
        List<Team> teams = new ArrayList<>();
        teams.add(getBlueTeam());
        teams.add(getRedTeam());
        return teams;
    }
    //#endregion

    /**
     * Each line of our scoreboard are stored here
     * 
     * @todo it's not this function which need to display the timer
     * @return the scoreboard line by line
     */
    public static String[] getScoreboardLines() {
        GameRunnable gm = FightToSurvive.getTimer();
        String timer = "00:00";
        if (gm != null)
            timer = FightToSurvive.getTimer().stringTimer();

        return new String[] {
                "§a",
                "§7Timer: " + timer,
                "§1",
                "§4Red Team (" + Constants.getRedTeam().getPlayers().size() + ")",
                "§1Blue Team (" + Constants.getBlueTeam().getPlayers().size() + ")",
                "§b",
                "§6----------------"
        };
    }
}
