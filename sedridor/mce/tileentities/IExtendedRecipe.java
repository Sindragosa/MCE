package sedridor.mce.tileentities;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IExtendedRecipe
{
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	boolean matches(InventoryCrafting inventorycrafting, World world);

	/**
	 * Returns an Item that is the result of this recipe
	 */
	ItemStack getCraftingResult(InventoryCrafting inventorycrafting);

	/**
	 * Returns the size of the recipe area
	 */
	int getRecipeSize();

	ItemStack getRecipeOutput();
}
