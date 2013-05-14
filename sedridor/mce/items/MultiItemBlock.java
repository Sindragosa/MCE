package sedridor.mce.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class MultiItemBlock extends ItemBlock
{
	public MultiItemBlock(int par1)
	{
		super(par1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	/**
	 * Gets an icon index based on an item's damage value
	 */
	@Override
	public Icon getIconFromDamage(int par1)
	{
		return Block.blocksList[this.getBlockID()].getIcon(2, par1);
	}

	/**
	 * Gets an icon index based on an item's damage value
	 */
	public int getColorFromDamage(int par1, int par2)
	{
		return Block.blocksList[this.getBlockID()].getRenderColor(par1);
	}

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}

	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
	}
}
