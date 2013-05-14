package sedridor.mce.blocks;

import sedridor.mce.*;
import java.util.Random;
import net.minecraft.block.BlockOre;

public class BlockCrystal extends BlockOre
{
	public BlockCrystal(int par1)
	{
		super(par1);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return MCE_Items.CrystalShard.itemID;
	}
}
