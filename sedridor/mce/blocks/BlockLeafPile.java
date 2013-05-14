package sedridor.mce.blocks;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLeafPile extends Block
{
	public BlockLeafPile(int par1)
	{
		super(par1, Material.vine);
		float f = 0.5F;
		float f1 = 0.015625F;
		this.setTickRandomly(true);
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		Block.setBurnProperties(par1, 30, 60);
	}

	@Override
	public int getBlockColor()
	{
		return ColorizerFoliage.getFoliageColorBasic();
	}

	@Override
	public int getRenderColor(int metadata)
	{
		return this.getBlockColor();
	}

	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		return blockAccess.getBiomeGenForCoords(x, z).getBiomeFoliageColor();
	}

	private static boolean canThisPlantGrowOnThisBlockID(int blockId)
	{
		return blockId == Block.grass.blockID || blockId == Block.dirt.blockID;
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4)));
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return super.canPlaceBlockAt(par1World, par2, par3, par4) && BlockLeafPile.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborId)
	{
		super.onNeighborBlockChange(world, x, y, z, neighborId);
		this.checkFlowerChange(world, x, y, z);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		this.checkFlowerChange(par1World, par2, par3, par4);
	}

	private void checkFlowerChange(World par1World, int par2, int par3, int par4)
	{
		if (!this.canBlockStay(par1World, par2, par3, par4))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public boolean isBlockReplaceable(World par1World, int par2, int par3, int par4)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
}
