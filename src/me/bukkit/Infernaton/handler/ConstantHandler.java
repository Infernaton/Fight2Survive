package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.GameRunnable;
import me.bukkit.Infernaton.builder.Team;
import me.bukkit.Infernaton.handler.store.StringConfig;
import net.minecraft.server.v1_8_R3.MerchantRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import static me.bukkit.Infernaton.handler.store.CoordStorage.worldName;

import java.util.*;

/**
 * Class which regroup each variable we need in our project
 * 
 * @todo make static function
 */
public class ConstantHandler {

    private GState state;
    private final FightToSurvive main;
    private Scoreboard scoreboard;

    public final static int appleSpawningCooldown = 15;
    public final static int mobWaveCooldown = 61;

    public ConstantHandler(FightToSurvive main) {
        this.main = main;
    }

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

    // List of block type where a monster can spawn
    public static List<Material> spawnableBlocks() {
        return Arrays.asList(
                Material.GRASS,
                Material.DIRT,
                Material.STONE,
                Material.SOUL_SAND,
                Material.COBBLESTONE,
                Material.HARD_CLAY,
                Material.STAINED_CLAY);
    }

    public static List<EntityType> aggressiveMob(int lvl) {
        List<EntityType> list = new ArrayList<>();
        if (lvl > 5)
            lvl = 5;
        switch (lvl) {
            case 5:
                list.add(EntityType.SKELETON);
            case 4:
            case 3:
                list.add(EntityType.SPIDER);
            case 2:
            case 1:
                list.add(EntityType.ZOMBIE);
                break;
        }
        return list;
    }

    public static List<EntityType> spawnedMobs() {
        List<EntityType> list = new ArrayList<>(Arrays.asList(
                EntityType.VILLAGER,
                EntityType.EXPERIENCE_ORB,
                EntityType.DROPPED_ITEM));
        list.addAll(aggressiveMob(5));
        return list;
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

    public Map<String, MerchantRecipe> getAllTrade() {
        Map<String, MerchantRecipe> trade = new HashMap<>();
        trade.put(StringConfig.pnjWood(),
                main.MR().tradingKey(new ItemStack(Material.LOG, 10), new ItemStack(Material.COBBLESTONE, 10)));
        trade.put(StringConfig.pnjCoal(), main.MR().tradingKey(new ItemStack(Material.COAL_BLOCK, 3)));
        trade.put(StringConfig.pnjGold1(), main.MR().defaultTrade());
        trade.put(StringConfig.pnjGold2(), main.MR().tradingKey(new ItemStack(Material.GOLD_NUGGET, 50)));
        trade.put(StringConfig.pnjIron(),
                main.MR().tradingKey(new ItemStack(Material.IRON_BLOCK, 2), new ItemStack(Material.IRON_INGOT, 5)));
        trade.put(StringConfig.pnjDiam(),
                main.MR().tradingKey(new ItemStack(Material.DIAMOND, 6), new ItemStack(Material.COAL, 12)));
        trade.put(StringConfig.pnjLapis(),
                main.MR().tradingKey(new ItemStack(Material.LAPIS_BLOCK, 6), main.HI().goldSword()));

        return trade;
    }

    public MerchantRecipe getTrade(String pnjName) {
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

    // #region Game State
    public void setState(GState state) {
        this.state = state;
    }

    public boolean isState(GState state) {
        return this.state == state;
    }

    public GState getState() {
        return this.state;
    }
    // #endregion

    // #region Scoreboard
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }
    // #endregion

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
                "§4Red Team (" + ConstantHandler.getRedTeam().getPlayers().size() + ")",
                "§1Blue Team (" + ConstantHandler.getBlueTeam().getPlayers().size() + ")",
                "§b",
                "§6----------------"
        };
    }
}
