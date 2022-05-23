package me.bukkit.Infernaton.handler;

import me.bukkit.Infernaton.FightToSurvive;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Iterator;

public class CustomRecipeHandler {

    private final FightToSurvive main;

    public CustomRecipeHandler(FightToSurvive fightToSurvive) {
        this.main = fightToSurvive;
        definedCustomTools();
    }

    private void definedCustomTools(){
        woodPickaxe();
        stonePickaxe();
        ironPickaxe();
        diamondPickaxe();

        woodAxe();
        stoneAxe();
        ironAxe();
        diamondAxe();
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
    private void ironAxe(){
        axeShape(main.HI().ironAxe(), Material.IRON_INGOT);
    }
    private void diamondAxe(){
        axeShape(main.HI().ironAxe(), Material.DIAMOND);
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
}
