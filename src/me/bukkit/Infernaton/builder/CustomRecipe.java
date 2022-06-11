package me.bukkit.Infernaton.builder;

import me.bukkit.Infernaton.FightToSurvive;
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

    private void definedCustomTools(){
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
        while(it.hasNext()) {
            recipe = it.next();
            if (recipe != null && recipe.getResult().getType() == item) {
                it.remove();
            }
        }
    }

    private void woodPickaxe(){
        pickaxeShape(main.HI().woodPickaxe(), Material.WOOD);
    }
    private void stonePickaxe(){
        pickaxeShape(main.HI().stonePickaxe(), Material.COBBLESTONE);
    }
    private void goldPickaxe(){
        pickaxeShape(main.HI().goldPickaxe(), Material.GOLD_INGOT);
    }
    private void ironPickaxe(){
        pickaxeShape(main.HI().ironPickaxe(), Material.IRON_INGOT);
    }
    private void diamondPickaxe(){
        pickaxeShape(main.HI().diamondPickaxe(), Material.DIAMOND);
    }

    private void woodAxe(){
        axeShape(main.HI().woodAxe(), Material.WOOD);
    }
    private void stoneAxe(){
        axeShape(main.HI().stoneAxe(), Material.COBBLESTONE);
    }
    private void goldAxe(){
        axeShape(main.HI().goldAxe(), Material.GOLD_INGOT);
    }
    private void ironAxe(){
        axeShape(main.HI().ironAxe(), Material.IRON_INGOT);
    }
    private void diamondAxe(){
        axeShape(main.HI().diamondAxe(), Material.DIAMOND);
    }

    private void goldSword(){ swordShape(main.HI().goldSword(), Material.GOLD_INGOT); }
    private void goldShovel(){ shovelShape(main.HI().goldShovel(), Material.GOLD_INGOT); }
    private void goldHoe(){
        hoeShape(main.HI().goldHoe(), Material.GOLD_INGOT);
    }

    private void commonToolRecipeMaterial(ItemStack it, Material material, String[] shape){
        ShapedRecipe recipe = new ShapedRecipe(it);
        removeRecipeByMateriel(it.getType());

        recipe.shape(shape[0], shape[1], shape[2]);

        recipe.setIngredient('M', material);
        recipe.setIngredient('S', Material.STICK);
        main.getServer().addRecipe(recipe);
    }

    private void pickaxeShape(ItemStack pickaxe, Material mainMaterial){
        String[] shape = {"MMM", " S ", " S "};
        commonToolRecipeMaterial(pickaxe, mainMaterial, shape);
    }
    private void axeShape(ItemStack axe, Material mainMaterial){
        String[] shape = {"MM ", "MS ", " S "};
        commonToolRecipeMaterial(axe, mainMaterial, shape);

        String[] shape2 = {" MM", " SM", " S "};
        commonToolRecipeMaterial(axe, mainMaterial, shape2);
    }
    private void swordShape(ItemStack sword, Material mainMaterial){
        String[] shape1 = {" M ", " M ", " S "};
        commonToolRecipeMaterial(sword, mainMaterial, shape1);

        String[] shape2 = {"M  ", "M  ", "S  "};
        commonToolRecipeMaterial(sword, mainMaterial, shape1);

        String[] shape3 = {"  M", "  M", "  S"};
        commonToolRecipeMaterial(sword, mainMaterial, shape1);
    }
    private void shovelShape(ItemStack shovel, Material mainMaterial){
        String[] shape1 = {" M ", " S ", " S "};
        commonToolRecipeMaterial(shovel, mainMaterial, shape1);

        String[] shape2 = {"M  ", "S  ", "S  "};
        commonToolRecipeMaterial(shovel, mainMaterial, shape1);

        String[] shape3 = {"  M", "  S", "  S"};
        commonToolRecipeMaterial(shovel, mainMaterial, shape1);
    }
    private void hoeShape(ItemStack hoe, Material mainMaterial){
        String[] shape = {"MM ", " S ", " S "};
        commonToolRecipeMaterial(hoe, mainMaterial, shape);

        String[] shape2 = {" MM", " S ", " S "};
        commonToolRecipeMaterial(hoe, mainMaterial, shape2);

        String[] shape3 = {"MM ", "S  ", "S  "};
        commonToolRecipeMaterial(hoe, mainMaterial, shape);

        String[] shape4 = {" MM", "  S", "  S"};
        commonToolRecipeMaterial(hoe, mainMaterial, shape2);
    }
}
