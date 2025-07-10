package me.bukkit.Infernaton.store;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.builder.clock.GameRunnable;
import net.minecraft.server.v1_8_R3.MerchantRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.bukkit.Infernaton.store.CoordStorage.worldName;

import java.util.*;

/**
 * Class which regroup each variable we need in our project
 */
public class Constants {

    public final static int appleSpawningCooldown = 15;
    public final static float mobSpawnChance = 1; // Base % to spawn each second
    public final static float mobSpawnChanceMultiplier = 0.1f; // pts to add to upgrade the percentage of mob spawning

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

    /**
     * define all the cooldown for blocks
     * 
     * @todo define this on config file or option menu
     * @return Map<Material, Integer> Material-> Break Block, Integer-> Countdown in
     *         second
     */
    public static Map<Material, Integer> coolDownBlock() {
        Map<Material, Integer> cd = new HashMap<>();
        cd.put(Material.LOG, 10);
        cd.put(Material.COBBLESTONE, 10);
        cd.put(Material.COAL_ORE, 10);
        cd.put(Material.IRON_ORE, 20);
        cd.put(Material.DIAMOND_ORE, 45);
        cd.put(Material.LAPIS_ORE, 25);
        return cd;
    }

    public static Map<String, MerchantRecipe> getAllTrade() {
        Map<String, MerchantRecipe> trade = new HashMap<>();
        trade.put(StringConfig.pnjWood(),
                CustomMerchantRecipe.tradingKey(new ItemStack(Material.LOG, 10),
                        new ItemStack(Material.COBBLESTONE, 10)));
        trade.put(StringConfig.pnjCoal(), CustomMerchantRecipe.tradingKey(new ItemStack(Material.COAL_BLOCK, 3)));
        trade.put(StringConfig.pnjGold1(), CustomMerchantRecipe.defaultTrade());
        trade.put(StringConfig.pnjGold2(), CustomMerchantRecipe.tradingKey(new ItemStack(Material.GOLD_NUGGET, 50)));
        trade.put(StringConfig.pnjIron(),
                CustomMerchantRecipe.tradingKey(new ItemStack(Material.IRON_BLOCK, 2),
                        new ItemStack(Material.IRON_INGOT, 5)));
        trade.put(StringConfig.pnjDiam(),
                CustomMerchantRecipe.tradingKey(new ItemStack(Material.DIAMOND, 6), new ItemStack(Material.COAL, 12)));
        trade.put(StringConfig.pnjLapis(),
                CustomMerchantRecipe.tradingKey(new ItemStack(Material.LAPIS_BLOCK, 6), CustomItem.goldSword()));

        return trade;
    }

    public static MerchantRecipe getTrade(String pnjName) {
        return getAllTrade().get(pnjName);
    }

    public static List<Location> getAllCopiesDoors() {
        List<Location> locations = new ArrayList<>();
        String path = "coordinates.doorCoord.copies";
        for (String key : FightToSurvive.GetConfig().getConfigurationSection(path).getKeys(false)) {
            Location door = new Location(Bukkit.getWorld(worldName),
                    FightToSurvive.GetConfig().getDouble(path + "." + key + ".x"),
                    FightToSurvive.GetConfig().getDouble(path + "." + key + ".y"),
                    FightToSurvive.GetConfig().getDouble(path + "." + key + ".z"));
            locations.add(door);
        }
        return locations;
    }

    // #region Team related
    public static Team getRedTeam() {
        return Team.getTeamByName(StringConfig.redTeamName());
    }

    public static Team getBlueTeam() {
        return Team.getTeamByName(StringConfig.blueTeamName());
    }

    public static Team getSpectators() {
        return Team.getTeamByName(StringConfig.spectatorName());
    }

    public static List<Player> getAllTeamsPlayer() {
        List<Player> allPlayers = getBlueTeam().getPlayers();
        allPlayers.addAll(getRedTeam().getPlayers());
        return allPlayers;
    }

    public static List<Player> getAllPlayers() {
        List<Player> allPlayers = getBlueTeam().getPlayers();
        allPlayers.addAll(getRedTeam().getPlayers());
        allPlayers.addAll(getSpectators().getPlayers());
        return allPlayers;
    }
    // #endregion

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
