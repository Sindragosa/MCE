package sedridor.mce.blocks;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockSlab extends Block
{
	public BlockSlab(int i, Material material)
	{
		super(i, material);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		this.setLightOpacity(255);
		Block.useNeighborBrightness[i] = true;
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World world, int i, int j, int k, int l, float f, float f1, float f2, int i1)
	{
		if (l == 0 || l != 1 && f1 > 0.5D)
		{
			i1 = this.SetIsUpsideDownInMetadata(i1, true);
		}

		return i1;
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		if (this.GetIsUpsideDown(iblockaccess, i, j, k))
		{
			this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List list, Entity entity)
	{
		this.setBlockBoundsBasedOnState(world, i, j, k);
		super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, list, entity);
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

	public boolean HasCenterHardPointToFacing(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		boolean flag = (iblockaccess.getBlockMetadata(i, j, k) & 8) != 0;
		if (l == 0 && !this.GetIsUpsideDown(iblockaccess, i, j, k))
		{
			return true;
		}
		else
		{
			return iblockaccess.isBlockNormalCube(i, j, k) || l == 1 && BlockSlab.DoesBlockHaveSolidTopSurface(iblockaccess, i, j, k);
		}
	}

	public static boolean DoesBlockHaveSolidTopSurface(IBlockAccess iblockaccess, int i, int j, int k)
	{
		Block block = Block.blocksList[iblockaccess.getBlockId(i, j, k)];
		return block == null ? false : (block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ? true : (block instanceof BlockStairs ? (iblockaccess.getBlockMetadata(i, j, k) & 4) == 4 : (block instanceof BlockHalfSlab ? (iblockaccess.getBlockMetadata(i, j, k) & 8) == 8 : false)));
	}

	public float SnowRestingOnVisualOffset(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return !this.GetIsUpsideDown(iblockaccess, i, j, k) ? -0.5F : 0.0F;
	}

	public boolean CanSnowOnBlock(World world, int i, int j, int k)
	{
		return true;
	}

	public boolean DoesBlockHaveSolidTop(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return this.GetIsUpsideDown(iblockaccess, i, j, k);
	}

	public abstract boolean GetIsUpsideDown(IBlockAccess iblockaccess, int i, int j, int k);

	public abstract void SetIsUpsideDown(World world, int i, int j, int k, boolean flag);

	public abstract int SetIsUpsideDownInMetadata(int i, boolean flag);
}
