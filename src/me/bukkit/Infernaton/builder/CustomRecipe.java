package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
import me.bukkit.Infernaton.handler.store.CustomItem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Iterator;

public class CustomRecipe {

    private final FightToSurvive main;

    public CustomRecipe(FightToSurvive fightToSurvive) {
        this.main = fightToSurvive;
        definedCustomTools();
    }

    private void definedCustomTools() {
        woodPickaxe();
        stonePickaxe();
        goldPickaxe();
        ironPickaxe();
        diamondPickaxe();

        woodAxe();
        stoneAxe();
        goldAxe();
        ironAxe();
        diamondAxe();

        goldSword();
        goldShovel();
        goldHoe();
    }

    private void removeRecipeByMateriel(Material item) {
        Iterator<Recipe> it = main.getServer().recipeIterator();
        Recipe recipe;
        while (it.hasNext()) {
            recipe = it.next();
            if (recipe != null && recipe.getResult().getType() == item) {
                it.remove();
            }
        }
    }

    private void woodPickaxe() {
        pickaxeShape(CustomItem.woodPickaxe(), Material.WOOD);
    }

    private void stonePickaxe() {
        pickaxeShape(CustomItem.stonePickaxe(), Material.COBBLESTONE);
    }

    private void goldPickaxe() {
        pickaxeShape(CustomItem.goldPickaxe(), Material.GOLD_INGOT);
    }

    private void ironPickaxe() {
        pickaxeShape(CustomItem.ironPickaxe(), Material.IRON_INGOT);
    }

    private void diamondPickaxe() {
        pickaxeShape(CustomItem.diamondPickaxe(), Material.DIAMOND);
    }

    private void woodAxe() {
        axeShape(CustomItem.woodAxe(), Material.WOOD);
    }

    private void stoneAxe() {
        axeShape(CustomItem.stoneAxe(), Material.COBBLESTONE);
    }

    private void goldAxe() {
        axeShape(CustomItem.goldAxe(), Material.GOLD_INGOT);
    }

    private void ironAxe() {
        axeShape(CustomItem.ironAxe(), Material.IRON_INGOT);
    }

    private void diamondAxe() {
        axeShape(CustomItem.diamondAxe(), Material.DIAMOND);
    }

    private void goldSword() {
        swordShape(CustomItem.goldSword(), Material.GOLD_INGOT);
    }

    private void goldShovel() {
        shovelShape(CustomItem.goldShovel(), Material.GOLD_INGOT);
    }

    private void goldHoe() {
        hoeShape(CustomItem.goldHoe(), Material.GOLD_INGOT);
    }

    private void commonToolRecipeMaterial(ItemStack it, Material material, String[] shape) {
        ShapedRecipe recipe = new ShapedRecipe(it);
        removeRecipeByMateriel(it.getType());

        recipe.shape(shape[0], shape[1], shape[2]);

        recipe.setIngredient('M', material);
        recipe.setIngredient('S', Material.STICK);
        main.getServer().addRecipe(recipe);
    }

    private void pickaxeShape(ItemStack pickaxe, Material mainMaterial) {
        String[] shape = { "MMM", " S ", " S " };
        commonToolRecipeMaterial(pickaxe, mainMaterial, shape);
    }

    private void axeShape(ItemStack axe, Material mainMaterial) {
        String[] shape = { "MM ", "MS ", " S " };
        commonToolRecipeMaterial(axe, mainMaterial, shape);

        String[] shape2 = { " MM", " SM", " S " };
        commonToolRecipeMaterial(axe, mainMaterial, shape2);
    }

    private void swordShape(ItemStack sword, Material mainMaterial) {
        String[] shape1 = { " M ", " M ", " S " };
        commonToolRecipeMaterial(sword, mainMaterial, shape1);

        String[] shape2 = { "M  ", "M  ", "S  " };
        commonToolRecipeMaterial(sword, mainMaterial, shape2);

        String[] shape3 = { "  M", "  M", "  S" };
        commonToolRecipeMaterial(sword, mainMaterial, shape3);
    }

    private void shovelShape(ItemStack shovel, Material mainMaterial) {
        String[] shape1 = { " M ", " S ", " S " };
        commonToolRecipeMaterial(shovel, mainMaterial, shape1);

        String[] shape2 = { "M  ", "S  ", "S  " };
        commonToolRecipeMaterial(shovel, mainMaterial, shape2);

        String[] shape3 = { "  M", "  S", "  S" };
        commonToolRecipeMaterial(shovel, mainMaterial, shape3);
    }

    private void hoeShape(ItemStack hoe, Material mainMaterial) {
        String[] shape = { "MM ", " S ", " S " };
        commonToolRecipeMaterial(hoe, mainMaterial, shape);

        String[] shape2 = { " MM", " S ", " S " };
        commonToolRecipeMaterial(hoe, mainMaterial, shape2);

        String[] shape3 = { "MM ", "S  ", "S  " };
        commonToolRecipeMaterial(hoe, mainMaterial, shape3);

        String[] shape4 = { " MM", "  S", "  S" };
        commonToolRecipeMaterial(hoe, mainMaterial, shape4);
    }
}
