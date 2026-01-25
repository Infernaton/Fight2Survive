package me.bukkit.Infernaton.store;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.store.options.GameTime;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private static FileConfiguration config() {
        return FightToSurvive.GetConfig();
    }

    //#region Getter
    protected static String getString(String key) {
        return ChatColor.translateAlternateColorCodes('§',
                config().getString("string." + key));
    }

    protected static int getInt(String key, int _default) {
        return config().getInt(key, _default);
    }

    protected static int getInt(String key) {
        return getInt(key, 0);
    }

    protected static float getFloat(String key, float _default) {
        return (float)config().getDouble(key, _default);
    }

    protected static float getFloat(String key) {
        return  getFloat(key, 0);
    }

    protected static boolean getBool(String key) {
        return config().getBoolean(key, false);
    }

    protected static byte getByte(String key) {
        return (byte) config().getInt(key, 0);
    }
    //#endregion

    protected static void setValue(String key, Object newValue) {
        config().set(key, newValue);
        FightToSurvive.Instance().saveConfig();
    }

    //#region setup

    //#region cooldown related
    private static int getCoolDown(String key) {
        return getInt("setup.cooldown." + key, getInt("cooldown.default"));
    }
    /**
     * define all the cooldown for blocks
     * @return Integer Countdown in second
     */
    public static int getCoolDownBlock(Material block) {
        switch (block) {
            case IRON_ORE: return getCoolDown("ironOre");
            case DIAMOND_ORE: return getCoolDown("diamondOre");
            case LAPIS_ORE: return getCoolDown("lapisOre");

            case LOG:
            case COBBLESTONE:
            case COAL_ORE:
            default:
                return getCoolDown("default");
        }
    }

    public static int getAppleSpawnCoolDown() {
        return getCoolDown("apple");
    }
    //#endregion

    //#region mob spawning

    /**
     * Base % to spawn each second
     */
    public static float getMobSpawnChance() {
        return getFloat("setup.mobSpawn.spawnChance");
    }

    /**
     * pts to add to increase the percentage of mob spawning
     */
    public static float getMobSpawnChanceMultiplier() {
        return getFloat("setup.mobSpawn.chanceMultiplier");
    }

    //endregion

    //#endregion

    private static boolean getOptionsState(String option) {
        return getBool("options." + option);
    }
    private static void toggleOption(String option) {
        setValue("options." + option, !getOptionsState(option));
    }

    /**
     * No need to use Furnace anymore, Ore will directly be smelt when mine
     */
    public static boolean getAutoSmeltState() {
        return getOptionsState("autoSmelt");
    }

    /**
     * Deactivate the passive regeneration in minecraft before the final phase (will always be true during that time)
     */
    public static boolean getUHCState() {
        return getOptionsState("uhc");
    }

    /**
     * Define if mob will spawn during the final phase (and kill all the remaining upon activation)
     */
    public static boolean getDeathMatchState() {
        return getOptionsState("deathMatch");
    }

    /**
     *  Define the time of day during the game
     */
    public static GameTime getGameTimeState() {
        byte option = getByte("options.gameTime");
        switch (option) {
            case 2: return GameTime.Night;
            case 1: return GameTime.Day;
            default:
            case 0: return GameTime.Off;
        }
    }

    public static void toggleAutoSmelt() {
        toggleOption("autoSmelt");
    }
    public static void toggleUHC() {
        toggleOption("uhc");
    }
    public static void toggleDeathMatch() {
        toggleOption("deathMatch");
    }

    public static void toggleGameTime() {
        byte option = getByte("options.gameTime");
        option++;
        if (option > 2) option = 0;
        setValue("options.gameTime", option);
    }
}
