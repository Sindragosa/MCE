package sedridor.mce.tileentities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class BlastFurnaceRecipes
{
	private static final BlastFurnaceRecipes smeltingBase = new BlastFurnaceRecipes();

	/** The list of smelting results. */
	private HashMap<List<Integer>, ItemStack> metaSmeltingList1 = new HashMap<List<Integer>, ItemStack>();
	private HashMap<List<Integer>, ItemStack> metaSmeltingList2 = new HashMap<List<Integer>, ItemStack>();
	private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

	/**
	 * Used to call methods addSmelting and getSmeltingResult.
	 */
	public static final BlastFurnaceRecipes smelting()
	{
		return smeltingBase;
	}

	private BlastFurnaceRecipes()
	{
	}

	/**
	 * Adds a smelting recipe.
	 */
	public void addSmelting(int par1, ItemStack par2ItemStack, float par3)
	{
		FurnaceRecipes.smelting().addSmelting(par1, par2ItemStack, par3);
	}

	/**
	 * Returns the smelting result of an item.
	 * Deprecated in favor of a metadata sensitive version
	 */
	@Deprecated
	public ItemStack getSmeltingResult(int par1)
	{
		return (ItemStack)FurnaceRecipes.smelting().getSmeltingList().get(Integer.valueOf(par1));
	}

	public Map getSmeltingList()
	{
		return FurnaceRecipes.smelting().getSmeltingList();
	}

	@Deprecated //In favor of ItemStack sensitive version
	public float getExperience(int par1)
	{
		return FurnaceRecipes.smelting().getExperience(par1);
	}

	/**
	 * A metadata sensitive version of adding a furnace recipe.
	 */
	public void addSmelting(int itemID, int metadata, ItemStack itemstack, float experience)
	{
		FurnaceRecipes.smelting().addSmelting(itemID, metadata, itemstack, experience);
	}

	/**
	 * A metadata sensitive version of adding a furnace recipe.
	 */
	public void addSmelting(ItemStack item, ItemStack itemstack, float experience)
	{
		FurnaceRecipes.smelting().addSmelting(item.itemID, item.getItemDamage(), itemstack, experience);
	}

	/**
	 * A metadata sensitive version of adding a furnace recipe.
	 */
	public void addSmelting(ItemStack item1, ItemStack item2, ItemStack itemstack, float experience)
	{
		metaSmeltingList1.put(Arrays.asList(item1.itemID, item1.getItemDamage()), itemstack);
		metaSmeltingList2.put(Arrays.asList(item2.itemID, item2.getItemDamage()), itemstack);
		metaExperience.put(Arrays.asList(itemstack.itemID, itemstack.getItemDamage()), experience);
	}

	/**
	 * Used to get the resulting ItemStack form a source ItemStack
	 * @param item The Source ItemStack1
	 * @param item The Source ItemStack2
	 * @return The result ItemStack
	 */
	public ItemStack getSmeltingResult(ItemStack item1, ItemStack item2)
	{
		if (item1 == null && item2 == null)
		{
			return null;
		}
		if (item1 == null && item2 != null || item1 != null && item2 == null)
		{
			ItemStack var4 = item1 != null ? item1 : item2;
			ItemStack ret = getMetaSmeltingList().get(Arrays.asList(var4.itemID, var4.getItemDamage()));
			if (ret != null)
			{
				return ret;
			}
			return (ItemStack)getSmeltingList().get(Integer.valueOf(var4.itemID));
		}
		if (item1 != null && item2 != null)
		{
			if (item1.isItemEqual(item2))
			{
				ItemStack ret = getMetaSmeltingList().get(Arrays.asList(item1.itemID, item1.getItemDamage()));
				if (ret != null)
				{
					ItemStack var5 = ret.copy();
					var5.stackSize *= 2;
					return var5;
				}
				ItemStack var5 = (ItemStack)getSmeltingList().get(Integer.valueOf(item1.itemID));
				if (var5 != null)
				{
					ItemStack var6 = var5.copy();
					var6.stackSize *= 2;
					return var6;
				}
			}
			else
			{
				ItemStack ret1 = metaSmeltingList1.get(Arrays.asList(item1.itemID, item1.getItemDamage()));
				ItemStack ret2 = metaSmeltingList2.get(Arrays.asList(item2.itemID, item2.getItemDamage()));

				if (ret1 != null && ret2 != null && ret1.isItemEqual(ret2))
				{
					return ret1;
				}
			}
		}
		return null;
	}

	/**
	 * Grabs the amount of base experience for this item to give when pulled from the furnace slot.
	 */
	public float getExperience(ItemStack item)
	{
		if (item == null || item.getItem() == null)
		{
			return 0;
		}
		float ret = item.getItem().getSmeltingExperience(item);
		if (ret < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage())))
		{
			ret = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage()));
		}
		else
		{
			ret = FurnaceRecipes.smelting().getExperience(item);
		}
		return (ret < 0 ? 0 : ret);
	}

	public Map<List<Integer>, ItemStack> getMetaSmeltingList()
	{
		return FurnaceRecipes.smelting().getMetaSmeltingList();
	}

	public Map<List<Integer>, ItemStack> getDualMetaSmeltingList1()
	{
		return metaSmeltingList1;
	}

	public Map<List<Integer>, ItemStack> getDualMetaSmeltingList2()
	{
		return metaSmeltingList2;
	}
}
