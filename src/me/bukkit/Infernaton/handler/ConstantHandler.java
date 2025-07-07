package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.GState;
import me.bukkit.Infernaton.builder.GameRunnable;
import me.bukkit.Infernaton.builder.Team;
import net.minecraft.server.v1_8_R3.MerchantRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.block.Block;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

import static me.bukkit.Infernaton.handler.SpatialHandler.worldName;

/**
 * Class which regroup each variable we need in our project
 */
public class ConstantHandler {

    private GState state;
    private final FightToSurvive main;
    private Scoreboard scoreboard;
    private InterfaceHandler IH;

    public ConstantHandler(FightToSurvive main) {
        this.main = main;
        this.IH = new InterfaceHandler(main);
    }

    public int getCoolDownAppleSpawn() {
        return 15;
    }

    public int getInitMobWaveCD() {
        return 61;
    }

    public String[] pnjName() {
        return new String[] {
                // Team Blue
                main.stringH().pnjWood(),
                main.stringH().pnjCoal(),
                // main.stringH().pnjGold1(),
                main.stringH().pnjGold2(),
                main.stringH().pnjIron(),
                main.stringH().pnjDiam(),
                main.stringH().pnjLapis(),

                // Team Red
                main.stringH().pnjWood(),
                main.stringH().pnjCoal(),
                // main.stringH().pnjGold1(),
                main.stringH().pnjGold2(),
                main.stringH().pnjIron(),
                main.stringH().pnjDiam(),
                main.stringH().pnjLapis()
        };
    }

    // List of block type where a monster can spawn
    public List<Material> spawnableBlocks() {
        return Arrays.asList(
                Material.GRASS,
                Material.DIRT,
                Material.STONE,
                Material.SOUL_SAND,
                Material.COBBLESTONE,
                Material.HARD_CLAY,
                Material.STAINED_CLAY);
    }

    public List<EntityType> aggressiveMob(int lvl) {
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

    public List<EntityType> spawnedMobs() {
        List<EntityType> list = new ArrayList<>(Arrays.asList(
                EntityType.VILLAGER,
                EntityType.EXPERIENCE_ORB,
                EntityType.DROPPED_ITEM));
        list.addAll(aggressiveMob(5));
        return list;
    }

    /**
     *
     * @return Map<Material, Integer> Material-> Break Block, Integer-> Countdown in
     *         second
     */
    public Map<Material, Integer> coolDownBlock() {
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
        trade.put(main.stringH().pnjWood(),
                main.MR().tradingKey(new ItemStack(Material.LOG, 10), new ItemStack(Material.COBBLESTONE, 10)));
        trade.put(main.stringH().pnjCoal(), main.MR().tradingKey(new ItemStack(Material.COAL_BLOCK, 3)));
        trade.put(main.stringH().pnjGold1(), main.MR().defaultTrade());
        trade.put(main.stringH().pnjGold2(), main.MR().tradingKey(new ItemStack(Material.GOLD_NUGGET, 50)));
        trade.put(main.stringH().pnjIron(),
                main.MR().tradingKey(new ItemStack(Material.IRON_BLOCK, 2), new ItemStack(Material.IRON_INGOT, 5)));
        trade.put(main.stringH().pnjDiam(),
                main.MR().tradingKey(new ItemStack(Material.DIAMOND, 6), new ItemStack(Material.COAL, 12)));
        trade.put(main.stringH().pnjLapis(),
                main.MR().tradingKey(new ItemStack(Material.LAPIS_BLOCK, 6), main.HI().goldSword()));

        return trade;
    }

    public MerchantRecipe getTrade(String pnjName) {
        return getAllTrade().get(pnjName);
    }

    public List<Location> getAllCopiesDoors() {
        List<Location> locations = new ArrayList<>();
        String path = "coordinates.doorCoord.copies";
        for (String key : main.getConfig().getConfigurationSection(path).getKeys(false)) {
            Location door = new Location(Bukkit.getWorld(worldName),
                    main.getConfig().getDouble(path + "." + key + ".x"),
                    main.getConfig().getDouble(path + "." + key + ".y"),
                    main.getConfig().getDouble(path + "." + key + ".z"));
            locations.add(door);
        }
        return locations;
    }

    public void setState(GState state) {
        this.state = state;
    }

    public boolean isState(GState state) {
        return this.state == state;
    }

    public GState getState() {
        return this.state;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public Team getRedTeam() {
        return Team.getTeamByName(main.stringH().redTeamName());
    }

    public Team getBlueTeam() {
        return Team.getTeamByName(main.stringH().blueTeamName());
    }

    public Team getSpectators() {
        return Team.getTeamByName(main.stringH().spectatorName());
    }

    public List<Player> getAllTeamsPlayer() {
        List<Player> allPlayers = this.getBlueTeam().getPlayers();
        allPlayers.addAll(this.getRedTeam().getPlayers());
        return allPlayers;
    }

    public List<Player> getAllPlayers() {
        List<Player> allPlayers = this.getBlueTeam().getPlayers();
        allPlayers.addAll(this.getRedTeam().getPlayers());
        allPlayers.addAll(this.getSpectators().getPlayers());
        return allPlayers;
    }

    /**
     * Each line of our scoreboard are stored here
     * 
     * @return the scoreboard line by line
     */
    public String[] getScoreboardLines() {
        GameRunnable gm = main.getTimer();
        String timer = "00:00";
        if (gm != null)
            timer = main.getTimer().stringTimer();

        return new String[] {
                "§a",
                "§7Timer: " + timer,
                "§1",
                "§4Red Team (" + main.constH().getRedTeam().getPlayers().size() + ")",
                "§1Blue Team (" + main.constH().getBlueTeam().getPlayers().size() + ")",
                "§b",
                "§6----------------"
        };
    }
}
