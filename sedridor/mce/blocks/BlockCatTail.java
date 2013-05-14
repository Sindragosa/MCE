/**
 * Copyright (c) Scott Killen and MisterFiber, 2012
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license
 * located in /MMPL-1.0.txt
 */

package sedridor.mce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockCatTail extends BlockFlower
{
	public BlockCatTail(int par1, Material par3Material)
	{
		super(par1, par3Material);
		this.disableStats();
		float var4 = 0.375F;
		this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, 1.0F, 0.5F + var4);
	}

	public BlockCatTail(int par1)
	{
		this(par1, Material.plants);
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int i = par1World.getBlockId(par2, par3 - 1, par4);

		if (i != Block.grass.blockID && i != Block.dirt.blockID)
		{
			return false;
		}

		if (par1World.getBlockMaterial(par2 - 1, par3 - 1, par4) == Material.water)
		{
			return true;
		}

		if (par1World.getBlockMaterial(par2 + 1, par3 - 1, par4) == Material.water)
		{
			return true;
		}

		if (par1World.getBlockMaterial(par2, par3 - 1, par4 - 1) == Material.water)
		{
			return true;
		}

		return par1World.getBlockMaterial(par2, par3 - 1, par4 + 1) == Material.water;
	}

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
	 * blockID passed in. Args: blockID
	 */
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return par1 == Block.grass.blockID && par1 == Block.dirt.blockID && par1 == Block.sand.blockID;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int idNeighbor) {
		if (!this.canBlockStay(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int x, int y, int z)
	{
		return this.canPlaceBlockAt(par1World, x, y, z);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int x, int y, int z)
	{
		return null;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 6;
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	//public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	//{
	//    par3List.add(new ItemStack(par1));
	//}
}
