package me.bukkit.Infernaton.store;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.FinalPhaseHandler;
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

    protected static boolean getBool(String key) {
        return config().getBoolean(key, false);
    }
    //#endregion

    protected static void setValue(String key, Object newValue) {
        config().set(key, newValue);
        FightToSurvive.Instance().saveConfig();
    }

    //#region cooldown related
    private static int getCoolDown(String key) {
        return getInt("cooldown." + key, getInt("cooldown.default"));
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

    private static boolean getOptionsState(String option) {
        return getBool("options." + option);
    }
    private static void toggleOption(String option) {
        setValue("options." + option, !getOptionsState(option));
    }

    public static boolean getAutoSmeltState() {
        return getOptionsState("autoSmelt");
    }
    public static boolean getUHCState() {
        return getOptionsState("uhc");
    }
    public static boolean getDeathMatchState() {
        return getOptionsState("deathMatch");
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
}
