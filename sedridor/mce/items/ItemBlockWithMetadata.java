package sedridor.mce.items;

import net.minecraft.item.ItemBlock;

public class ItemBlockWithMetadata extends ItemBlock
{
	public ItemBlockWithMetadata(int par1)
	{
		super(par1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}
}
