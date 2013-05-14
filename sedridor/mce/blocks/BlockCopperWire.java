package sedridor.mce.blocks;

import sedridor.mce.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCopperWire extends Block
{
	/**
	 * When false, power transmission methods do not look at other redstone wires. Used internally during
	 * updateCurrentStrength.
	 */
	private boolean wiresProvidePower = true;
	private Set blocksNeedingUpdate = new HashSet();
	private static Icon iconWireCopper_cross;
	private static Icon iconWireCopper_line;
	private static Icon iconWireCopper_cross_overlay;
	private static Icon iconWireCopper_line_overlay;

	public BlockCopperWire(int par1)
	{
		super(par1, Material.circuits);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		this.disableStats();
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return this.blockIcon;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
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
		return MCE_Items.copperWireModelID;
	}

	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0x9a622f;
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return this.doesBlockHaveSolidBottomSurface(par1World, par2, par3 + 1, par4) || par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || par1World.getBlockId(par2, par3 - 1, par4) == Block.glowStone.blockID;
	}

	/**
	 * Returns true if the block at the given coordinate has a solid (buildable) top surface.
	 */
	public boolean doesBlockHaveSolidBottomSurface(World par1World, int par2, int par3, int par4)
	{
		Block var5 = Block.blocksList[par1World.getBlockId(par2, par3, par4)];
		return var5 == null ? false : (var5.blockMaterial.isOpaque() && var5.renderAsNormalBlock() ? true : (var5 instanceof BlockStairs ? (par1World.getBlockMetadata(par2, par3, par4) & 4) == 0 : (var5 instanceof BlockHalfSlab ? (par1World.getBlockMetadata(par2, par3, par4) & 8) == 0 : false)));
	}

	/**
	 * Returns true if the block at the given coordinate has a solid (buildable) top surface.
	 */
	public static boolean doesBlockHaveSolidBottomSurface(IBlockAccess blockAccess, int par2, int par3, int par4)
	{
		Block var5 = Block.blocksList[blockAccess.getBlockId(par2, par3, par4)];
		return var5 == null ? false : (var5.blockMaterial.isOpaque() && var5.renderAsNormalBlock() ? true : (var5 instanceof BlockStairs ? (blockAccess.getBlockMetadata(par2, par3, par4) & 4) == 0 : (var5 instanceof BlockHalfSlab ? (blockAccess.getBlockMetadata(par2, par3, par4) & 8) == 0 : false)));
	}

	/**
	 * Sets the strength of the wire current (0-15) for this block based on neighboring blocks and propagates to
	 * neighboring redstone wires
	 */
	private void updateAndPropagateCurrentStrength(World par1World, int par2, int par3, int par4)
	{
		this.calculateCurrentChanges(par1World, par2, par3, par4, par2, par3, par4);
		ArrayList var5 = new ArrayList(this.blocksNeedingUpdate);
		this.blocksNeedingUpdate.clear();
		Iterator var6 = var5.iterator();

		while (var6.hasNext())
		{
			ChunkPosition var7 = (ChunkPosition)var6.next();
			par1World.notifyBlocksOfNeighborChange(var7.x, var7.y, var7.z, this.blockID);
		}
	}

	private void calculateCurrentChanges(World par1World, int par2, int par3, int par4, int par5, int par6, int par7)
	{
		int var8 = par1World.getBlockMetadata(par2, par3, par4);
		byte var9 = 0;
		int var15 = this.getMaxCurrentStrength(par1World, par5, par6, par7, var9);
		this.wiresProvidePower = false;
		int var10 = par1World.getStrongestIndirectPower(par2, par3, par4);
		this.wiresProvidePower = true;

		if (var10 > 0 && var10 > var15 - 1)
		{
			var15 = var10;
		}

		int var11 = 0;

		for (int var12 = 0; var12 < 4; ++var12)
		{
			int var13 = par2;
			int var14 = par4;

			if (var12 == 0)
			{
				var13 = par2 - 1;
			}

			if (var12 == 1)
			{
				++var13;
			}

			if (var12 == 2)
			{
				var14 = par4 - 1;
			}

			if (var12 == 3)
			{
				++var14;
			}

			if (var13 != par5 || var14 != par7)
			{
				var11 = this.getMaxCurrentStrength(par1World, var13, par3, var14, var11);
			}

			if (par1World.isBlockNormalCube(var13, par3, var14) && !par1World.isBlockNormalCube(par2, par3 + 1, par4))
			{
				if ((var13 != par5 || var14 != par7) && par3 >= par6)
				{
					var11 = this.getMaxCurrentStrength(par1World, var13, par3 + 1, var14, var11);
				}
			}
			else if (!par1World.isBlockNormalCube(var13, par3, var14) && (var13 != par5 || var14 != par7) && par3 <= par6)
			{
				var11 = this.getMaxCurrentStrength(par1World, var13, par3 - 1, var14, var11);
			}
		}

		if (var11 > var15)
		{
			var15 = var11 - 1;
		}
		else if (var15 > 0)
		{
			--var15;
		}
		else
		{
			var15 = 0;
		}

		if (var10 > var15 - 1)
		{
			var15 = var10;
		}

		if (var8 != var15)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var15, 2);
			this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4));
			this.blocksNeedingUpdate.add(new ChunkPosition(par2 - 1, par3, par4));
			this.blocksNeedingUpdate.add(new ChunkPosition(par2 + 1, par3, par4));
			this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3 - 1, par4));
			this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3 + 1, par4));
			this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4 - 1));
			this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4 + 1));
		}
	}

	/**
	 * Calls World.notifyBlocksOfNeighborChange() for all neighboring blocks, but only if the given block is a redstone
	 * wire.
	 */
	 private void notifyWireNeighborsOfNeighborChange(World par1World, int par2, int par3, int par4)
	{
		 if (par1World.getBlockId(par2, par3, par4) == this.blockID)
		 {
			 par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
		 }
	}

	 /**
	  * Called whenever the block is added into the world. Args: world, x, y, z
	  */
	 @Override
	 public void onBlockAdded(World par1World, int par2, int par3, int par4)
	 {
		 super.onBlockAdded(par1World, par2, par3, par4);

		 if (!par1World.isRemote)
		 {
			 this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
			 par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
			 this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
			 this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
			 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
			 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);

			 if (par1World.isBlockNormalCube(par2 - 1, par3, par4))
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
			 }
			 else
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
			 }

			 if (par1World.isBlockNormalCube(par2 + 1, par3, par4))
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
			 }
			 else
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
			 }

			 if (par1World.isBlockNormalCube(par2, par3, par4 - 1))
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
			 }
			 else
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
			 }

			 if (par1World.isBlockNormalCube(par2, par3, par4 + 1))
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
			 }
			 else
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
			 }
		 }
	 }

	 /**
	  * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	  */
	 @Override
	 public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	 {
		 super.breakBlock(par1World, par2, par3, par4, par5, par6);

		 if (!par1World.isRemote)
		 {
			 par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
			 par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
			 this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
			 this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
			 this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
			 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
			 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);

			 if (par1World.isBlockNormalCube(par2 - 1, par3, par4))
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
			 }
			 else
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
			 }

			 if (par1World.isBlockNormalCube(par2 + 1, par3, par4))
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
			 }
			 else
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
			 }

			 if (par1World.isBlockNormalCube(par2, par3, par4 - 1))
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
			 }
			 else
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
			 }

			 if (par1World.isBlockNormalCube(par2, par3, par4 + 1))
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
			 }
			 else
			 {
				 this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
			 }
		 }
	 }

	 /**
	  * Returns the current strength at the specified block if it is greater than the passed value, or the passed value
	  * otherwise. Signature: (world, x, y, z, strength)
	  */
	 private int getMaxCurrentStrength(World par1World, int par2, int par3, int par4, int par5)
	 {
		 if (par1World.getBlockId(par2, par3, par4) != this.blockID)
		 {
			 return par5;
		 }
		 else
		 {
			 int var6 = par1World.getBlockMetadata(par2, par3, par4);
			 return var6 > par5 ? var6 : par5;
		 }
	 }

	 /**
	  * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	  * their own) Args: x, y, z, neighbor blockID
	  */
	 @Override
	 public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	 {
		 if (!par1World.isRemote)
		 {
			 boolean var6 = this.canPlaceBlockAt(par1World, par2, par3, par4);

			 if (var6)
			 {
				 this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
			 }
			 else
			 {
				 this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
				 par1World.setBlockToAir(par2, par3, par4);
			 }

			 super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		 }
	 }

	 /**
	  * Returns the ID of the items to drop on destruction.
	  */
	 @Override
	 public int idDropped(int par1, Random par2Random, int par3)
	 {
		 return MCE_Items.CopperWireItem.itemID;
	 }

	 /**
	  * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
	  * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
	  */
	 @Override
	 public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	 {
		 return !this.wiresProvidePower ? 0 : this.isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5);
	 }

	 /**
	  * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
	  * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
	  * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
	  */
	 @Override
	 public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	 {
		 if (!this.wiresProvidePower)
		 {
			 return 0;
		 }
		 else
		 {
			 int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

			 if (var6 == 0)
			 {
				 return 0;
			 }
			 else if (par5 == 1)
			 {
				 return var6;
			 }
			 else
			 {
				 boolean var7 = isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3, par4, 1) || !par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 - 1, par4, -1);
				 boolean var8 = isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3, par4, 3) || !par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 - 1, par4, -1);
				 boolean var9 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 - 1, 2) || !par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 - 1, -1);
				 boolean var10 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 + 1, 0) || !par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 + 1, -1);

				 if (!par1IBlockAccess.isBlockNormalCube(par2, par3 + 1, par4))
				 {
					 if (par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 + 1, par4, -1))
					 {
						 var7 = true;
					 }

					 if (par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 + 1, par4, -1))
					 {
						 var8 = true;
					 }

					 if (par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 - 1, -1))
					 {
						 var9 = true;
					 }

					 if (par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 + 1, -1))
					 {
						 var10 = true;
					 }
				 }

				 return !var9 && !var8 && !var7 && !var10 && par5 >= 2 && par5 <= 5 ? var6 : (par5 == 2 && var9 && !var7 && !var8 ? var6 : (par5 == 3 && var10 && !var7 && !var8 ? var6 : (par5 == 4 && var7 && !var9 && !var10 ? var6 : (par5 == 5 && var8 && !var9 && !var10 ? var6 : 0))));
			 }
		 }
	 }

	 /**
	  * Is this block indirectly powering the block on the specified side
	  */
	 public boolean isIndirectlyPoweringTo(World par1World, int par2, int par3, int par4, int par5)
	 {
		 return !this.wiresProvidePower ? false : this.isPoweringTo(par1World, par2, par3, par4, par5);
	 }

	 /**
	  * Is this block powering the block on the specified side
	  */
	 public boolean isPoweringTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	 {
		 if (!this.wiresProvidePower)
		 {
			 return false;
		 }
		 else if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) == 0)
		 {
			 return false;
		 }
		 else if (par5 == 1) // give power to block above
		 {
			 return true;
		 }
		 else
		 {
			 boolean var6 = isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3, par4, 1) || !par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 - 1, par4, -1);
			 boolean var7 = isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3, par4, 3) || !par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 - 1, par4, -1);
			 boolean var8 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 - 1, 2) || !par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 - 1, -1);
			 boolean var9 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 + 1, 0) || !par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 + 1, -1);

			 if (!par1IBlockAccess.isBlockNormalCube(par2, par3 + 1, par4))
			 {
				 if (par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 + 1, par4, -1))
				 {
					 var6 = true;
				 }

				 if (par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 + 1, par4, -1))
				 {
					 var7 = true;
				 }

				 if (par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 - 1, -1))
				 {
					 var8 = true;
				 }

				 if (par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 + 1, -1))
				 {
					 var9 = true;
				 }
			 }

			 //System.out.println("MC isPoweringTo... var6 " + var6 + ", var7 " + var7 + ", var8 " + var8 + ", var9 " + var9 + ", par5 " + par5);
			 return true;
			 //return par5 >= 2 && par5 <= 5 ? true : (par5 == 2 && var8 && !var6 && !var7 ? true : (par5 == 3 && var9 && !var6 && !var7 ? true : (par5 == 4 && var6 && !var8 && !var9 ? true : par5 == 5 && var7 && !var8 && !var9)));
			 //return !var8 && !var7 && !var6 && !var9 && par5 >= 2 && par5 <= 5 ? true : (par5 == 2 && var8 && !var6 && !var7 ? true : (par5 == 3 && var9 && !var6 && !var7 ? true : (par5 == 4 && var6 && !var8 && !var9 ? true : par5 == 5 && var7 && !var8 && !var9)));
		 }
	 }

	 /**
	  * Can this block provide power. Only wire currently seems to have this change based on its state.
	  */
	 @Override
	 public boolean canProvidePower()
	 {
		 return this.wiresProvidePower;
	 }

	 /**
	  * A randomly called display update to be able to add particles or other items for display
	  */
	 @Override
	 public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	 {
		 int var6 = par1World.getBlockMetadata(par2, par3, par4);

		 if (var6 > 0)
		 {
			 double var7 = par2 + 0.5D + (par5Random.nextFloat() - 0.5D) * 0.2D;
			 double var9 = par3 + 0.0625F;
			 double var11 = par4 + 0.5D + (par5Random.nextFloat() - 0.5D) * 0.2D;
			 float var13 = var6 / 15.0F;
			 float var14 = var13 * 0.6F + 0.4F;

			 if (var6 == 0)
			 {
				 var14 = 0.0F;
			 }

			 float var15 = var13 * var13 * 0.7F - 0.5F;
			 float var16 = var13 * var13 * 0.6F - 0.7F;

			 if (var15 < 0.0F)
			 {
				 var15 = 0.0F;
			 }

			 if (var16 < 0.0F)
			 {
				 var16 = 0.0F;
			 }

			 //par1World.spawnParticle("reddust", var7, var9, var11, (double)var14, (double)var15, (double)var16);
		 }
	 }

	 /**
	  * Returns true if redstone wire can connect to the specified block. Params: World, X, Y, Z, side (not a normal
	  * notch-side, this can be 0, 1, 2, 3 or -1)
	  */
	 public static boolean isPowerProviderOrWire(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4)
	 {
		 int var5 = par0IBlockAccess.getBlockId(par1, par2, par3);

		 if (var5 == MCE_Items.CopperWire.blockID)
		 {
			 return true;
		 }
		 else if (var5 == 0)
		 {
			 return false;
		 }
		 else if (!Block.redstoneRepeaterIdle.func_94487_f(var5))
		 {
			 return Block.blocksList[var5].canProvidePower() && par4 != -1;
		 }
		 else
		 {
			 int var6 = par0IBlockAccess.getBlockMetadata(par1, par2, par3);
			 return par4 == (var6 & 3) || par4 == Direction.rotateOpposite[var6 & 3];
		 }
	 }

	 /**
	  * Returns true if the block coordinate passed can provide power, or is a redstone wire, or if its a repeater that
	  * is powered.
	  */
	 public static boolean isPoweredOrRepeater(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4)
	 {
		 if (isPowerProviderOrWire(par0IBlockAccess, par1, par2, par3, par4))
		 {
			 return true;
		 }
		 else
		 {
			 int var5 = par0IBlockAccess.getBlockId(par1, par2, par3);

			 if (var5 == Block.redstoneRepeaterActive.blockID)
			 {
				 int var6 = par0IBlockAccess.getBlockMetadata(par1, par2, par3);
				 return par4 == (var6 & 3);
			 }
			 else
			 {
				 return false;
			 }
		 }
	 }

	 /**
	  * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	  */
	 @Override
	 public int idPicked(World par1World, int par2, int par3, int par4)
	 {
		 return MCE_Items.CopperWireItem.itemID;
	 }

	 /**
	  * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	  * is the only chance you get to register icons.
	  */
	 @Override
	 public void registerIcons(IconRegister par1IconRegister)
	 {
		 BlockCopperWire.iconWireCopper_cross = par1IconRegister.registerIcon("WireCopper_cross");
		 BlockCopperWire.iconWireCopper_line = par1IconRegister.registerIcon("WireCopper_line");
		 BlockCopperWire.iconWireCopper_cross_overlay = par1IconRegister.registerIcon("WireCopper_cross_overlay");
		 BlockCopperWire.iconWireCopper_line_overlay = par1IconRegister.registerIcon("WireCopper_line_overlay");
		 this.blockIcon = BlockCopperWire.iconWireCopper_cross;
	 }

	 public static Icon getIcon(String par0Str)
	 {
		 return par0Str == "WireCopper_cross" ? BlockCopperWire.iconWireCopper_cross : (par0Str == "WireCopper_line" ? BlockCopperWire.iconWireCopper_line : (par0Str == "WireCopper_cross_overlay" ? BlockCopperWire.iconWireCopper_cross_overlay : (par0Str == "WireCopper_line_overlay" ? BlockCopperWire.iconWireCopper_line_overlay : null)));
	 }

	 /**
	  * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	  * x, y, z, startVec, endVec
	  */
	 @Override
	 public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	 {
		 if (this.doesBlockHaveSolidBottomSurface(par1World, par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4))
		 {
			 this.setBlockBounds(0.0F, 0.95F, 0.0F, 1.0F, 1.0F, 1.0F);
		 }
		 else
		 {
			 this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.05F, 1.0F);
		 }

		 return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	 }
	 private static int brightnessTopLeft;
	 private static int brightnessBottomLeft;
	 private static int brightnessBottomRight;
	 private static int brightnessTopRight;
	 private static float colorRedTopLeft;
	 private static float colorRedBottomLeft;
	 private static float colorRedBottomRight;
	 private static float colorRedTopRight;
	 private static float colorGreenTopLeft;
	 private static float colorGreenBottomLeft;
	 private static float colorGreenBottomRight;
	 private static float colorGreenTopRight;
	 private static float colorBlueTopLeft;
	 private static float colorBlueBottomLeft;
	 private static float colorBlueBottomRight;
	 private static float colorBlueTopRight;

	 public static boolean RenderBlockInWorld(RenderBlocks renderBlocks, IBlockAccess blockAccess, int posX, int posY, int posZ, BlockCopperWire block)
	 {
		 Tessellator var5 = Tessellator.instance;

		 boolean powerOverlay = MCE_Settings.PowerOverlay.equalsIgnoreCase("yes");
		 boolean powerTexture = MCE_Settings.PowerTexture.equalsIgnoreCase("yes");

		 int var6 = blockAccess.getBlockMetadata(posX, posY, posZ);
		 //int var7 = block.getBlockTextureFromSideAndMetadata(1, var6);
		 Icon var41 = BlockCopperWire.getIcon("WireCopper_cross");
		 Icon var42 = BlockCopperWire.getIcon("WireCopper_line");
		 Icon var43 = BlockCopperWire.getIcon("WireCopper_cross_overlay");
		 Icon var44 = BlockCopperWire.getIcon("WireCopper_line_overlay");

		 var5.setBrightness(block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ));
		 float var8 = 1.0F;
		 float var9 = var6 / 15.0F;
		 float var10;
		 float var11;
		 float var12;

		 if (powerOverlay)
		 {
			 var10 = var9 * 0.6F + 0.4F;

			 if (var6 == 0)
			 {
				 var10 = 0.3F;
			 }

			 var11 = var9 * var9 * 0.7F - 0.5F;
			 var12 = var9 * var9 * 0.6F - 0.7F;
		 }
		 else
		 {
			 var10 = var9 * 0.2F + 0.4F;		//0x9a622f 154 98 47 0.600 0.380 0.180
			 var11 = var9 * 0.12F + 0.26F;	//0x9a622f 102 66 32 0.400 0.260 0.125
			 var12 = var9 * 0.055F + 0.125F;
		 }

		 if (var11 < 0.0F)
		 {
			 var11 = 0.0F;
		 }

		 if (var12 < 0.0F)
		 {
			 var12 = 0.0F;
		 }
		 if (powerTexture && !powerOverlay)
		 {
			 getAmbientOcclusionLight(var5, blockAccess, posX, posY, posZ, block);
		 }

		 var5.setColorOpaque_F(var10, var11, var12);
		 boolean var23 = BlockCopperWire.isPowerProviderOrWire(blockAccess, posX - 1, posY, posZ, 1) || !blockAccess.isBlockNormalCube(posX - 1, posY, posZ) && BlockCopperWire.isPowerProviderOrWire(blockAccess, posX - 1, posY - 1, posZ, -1);
		 boolean var24 = BlockCopperWire.isPowerProviderOrWire(blockAccess, posX + 1, posY, posZ, 3) || !blockAccess.isBlockNormalCube(posX + 1, posY, posZ) && BlockCopperWire.isPowerProviderOrWire(blockAccess, posX + 1, posY - 1, posZ, -1);
		 boolean var25 = BlockCopperWire.isPowerProviderOrWire(blockAccess, posX, posY, posZ - 1, 2) || !blockAccess.isBlockNormalCube(posX, posY, posZ - 1) && BlockCopperWire.isPowerProviderOrWire(blockAccess, posX, posY - 1, posZ - 1, -1);
		 boolean var26 = BlockCopperWire.isPowerProviderOrWire(blockAccess, posX, posY, posZ + 1, 0) || !blockAccess.isBlockNormalCube(posX, posY, posZ + 1) && BlockCopperWire.isPowerProviderOrWire(blockAccess, posX, posY - 1, posZ + 1, -1);

		 byte var40 = 0;
		 if (!blockAccess.isBlockNormalCube(posX, posY + 1, posZ))
		 {
			 if (blockAccess.isBlockNormalCube(posX - 1, posY, posZ) && BlockCopperWire.isPowerProviderOrWire(blockAccess, posX - 1, posY + 1, posZ, -1))
			 {
				 var23 = true;
			 }

			 if (blockAccess.isBlockNormalCube(posX + 1, posY, posZ) && BlockCopperWire.isPowerProviderOrWire(blockAccess, posX + 1, posY + 1, posZ, -1))
			 {
				 var24 = true;
			 }

			 if (blockAccess.isBlockNormalCube(posX, posY, posZ - 1) && BlockCopperWire.isPowerProviderOrWire(blockAccess, posX, posY + 1, posZ - 1, -1))
			 {
				 var25 = true;
			 }

			 if (blockAccess.isBlockNormalCube(posX, posY, posZ + 1) && BlockCopperWire.isPowerProviderOrWire(blockAccess, posX, posY + 1, posZ + 1, -1))
			 {
				 var26 = true;
			 }
		 }
		 else if (!blockAccess.isBlockNormalCube(posX, posY - 1, posZ) && doesBlockHaveSolidBottomSurface(blockAccess, posX, posY + 1, posZ))
		 {
			 var40 = 1;
		 }

		 float var27 = posX + 0;
		 float var28 = posX + 1;
		 float var29 = posZ + 0;
		 float var30 = posZ + 1;
		 byte var31 = 0;

		 if ((var23 || var24) && !var25 && !var26)
		 {
			 var31 = 1;
		 }

		 if ((var25 || var26) && !var24 && !var23)
		 {
			 var31 = 2;
		 }

		 if (var40 == 1)
		 {
			 int var46 = 0;
			 int var47 = 0;
			 int var48 = 16;
			 int var49 = 16;

			 if (var31 == 0)
			 {
				 if (!var23)
				 {
					 var27 += 0.4375F;
				 }

				 if (!var23)
				 {
					 var46 += 7;
				 }

				 if (!var24)
				 {
					 var28 -= 0.5F;
				 }

				 if (!var24)
				 {
					 var48 -= 8;
				 }

				 if (!var25)
				 {
					 var29 += 0.4375F;
				 }

				 if (!var25)
				 {
					 var47 += 7;
				 }

				 if (!var26)
				 {
					 var30 -= 0.5F;
				 }

				 if (!var26)
				 {
					 var49 -= 8;
				 }

				 var5.addVertexWithUV(var27, posY + 0.984375D, var30, var41.getInterpolatedU(var46), var41.getInterpolatedV(var49));
				 var5.addVertexWithUV(var27, posY + 0.984375D, var29, var41.getInterpolatedU(var46), var41.getInterpolatedV(var47));
				 var5.addVertexWithUV(var28, posY + 0.984375D, var29, var41.getInterpolatedU(var48), var41.getInterpolatedV(var47));
				 var5.addVertexWithUV(var28, posY + 0.984375D, var30, var41.getInterpolatedU(var48), var41.getInterpolatedV(var49));
				 if (powerTexture)
				 {
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(var27, posY + 0.984375D, var30, var43.getInterpolatedU(var46), var43.getInterpolatedV(var49));
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(var27, posY + 0.984375D, var29, var43.getInterpolatedU(var46), var43.getInterpolatedV(var47));
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(var28, posY + 0.984375D, var29, var43.getInterpolatedU(var48), var43.getInterpolatedV(var47));
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(var28, posY + 0.984375D, var30, var43.getInterpolatedU(var48), var43.getInterpolatedV(var49));
				 }
			 }
			 else if (var31 == 1)
			 {
				 var5.addVertexWithUV(var27, posY + 0.984375D, var30, var42.getMinU(), var42.getMaxV());
				 var5.addVertexWithUV(var27, posY + 0.984375D, var29, var42.getMinU(), var42.getMinV());
				 var5.addVertexWithUV(var28, posY + 0.984375D, var29, var42.getMaxU(), var42.getMinV());
				 var5.addVertexWithUV(var28, posY + 0.984375D, var30, var42.getMaxU(), var42.getMaxV());
				 if (powerTexture)
				 {
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(var27, posY + 0.984375D, var30, var44.getMinU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(var27, posY + 0.984375D, var29, var44.getMinU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(var28, posY + 0.984375D, var29, var44.getMaxU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(var28, posY + 0.984375D, var30, var44.getMaxU(), var44.getMaxV());
				 }
			 }
			 else if (var31 == 2)
			 {
				 var5.addVertexWithUV(var27, posY + 0.984375D, var30, var42.getMaxU(), var42.getMinV());
				 var5.addVertexWithUV(var27, posY + 0.984375D, var29, var42.getMinU(), var42.getMinV());
				 var5.addVertexWithUV(var28, posY + 0.984375D, var29, var42.getMinU(), var42.getMaxV());
				 var5.addVertexWithUV(var28, posY + 0.984375D, var30, var42.getMaxU(), var42.getMaxV());
				 if (powerTexture)
				 {
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(var27, posY + 0.984375D, var30, var44.getMaxU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(var27, posY + 0.984375D, var29, var44.getMinU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(var28, posY + 0.984375D, var29, var44.getMinU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(var28, posY + 0.984375D, var30, var44.getMaxU(), var44.getMaxV());
				 }
			 }
		 }
		 else
		 {
			 int var46 = 0;
			 int var47 = 0;
			 int var48 = 16;
			 int var49 = 16;

			 if (var31 == 0)
			 {
				 if (!var23)
				 {
					 var27 += 0.4375F;
				 }

				 if (!var23)
				 {
					 var46 += 7;
				 }

				 if (!var24)
				 {
					 var28 -= 0.5F;
				 }

				 if (!var24)
				 {
					 var48 -= 8;
				 }

				 if (!var25)
				 {
					 var29 += 0.4375F;
				 }

				 if (!var25)
				 {
					 var47 += 7;
				 }

				 if (!var26)
				 {
					 var30 -= 0.5F;
				 }

				 if (!var26)
				 {
					 var49 -= 8;
				 }

				 var5.addVertexWithUV(var28, posY + 0.015625D, var30, var41.getInterpolatedU(var48), var41.getInterpolatedV(var49));
				 var5.addVertexWithUV(var28, posY + 0.015625D, var29, var41.getInterpolatedU(var48), var41.getInterpolatedV(var47));
				 var5.addVertexWithUV(var27, posY + 0.015625D, var29, var41.getInterpolatedU(var46), var41.getInterpolatedV(var47));
				 var5.addVertexWithUV(var27, posY + 0.015625D, var30, var41.getInterpolatedU(var46), var41.getInterpolatedV(var49));
				 if (powerTexture)
				 {
					 //var5.setColorOpaque_F(var8, var8, var8);
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(var28, posY + 0.015625D, var30, var43.getInterpolatedU(var48), var43.getInterpolatedV(var49));
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(var28, posY + 0.015625D, var29, var43.getInterpolatedU(var48), var43.getInterpolatedV(var47));
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(var27, posY + 0.015625D, var29, var43.getInterpolatedU(var46), var43.getInterpolatedV(var47));
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(var27, posY + 0.015625D, var30, var43.getInterpolatedU(var46), var43.getInterpolatedV(var49));
				 }
			 }
			 else if (var31 == 1)
			 {
				 var5.addVertexWithUV(var28, posY + 0.015625D, var30, var42.getMaxU(), var42.getMaxV());
				 var5.addVertexWithUV(var28, posY + 0.015625D, var29, var42.getMaxU(), var42.getMinV());
				 var5.addVertexWithUV(var27, posY + 0.015625D, var29, var42.getMinU(), var42.getMinV());
				 var5.addVertexWithUV(var27, posY + 0.015625D, var30, var42.getMinU(), var42.getMaxV());
				 if (powerTexture)
				 {
					 //var5.setColorOpaque_F(var8, var8, var8);
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(var28, posY + 0.015625D, var30, var44.getMaxU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(var28, posY + 0.015625D, var29, var44.getMaxU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(var27, posY + 0.015625D, var29, var44.getMinU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(var27, posY + 0.015625D, var30, var44.getMinU(), var44.getMaxV());
				 }
			 }
			 else if (var31 == 2)
			 {
				 var5.addVertexWithUV(var28, posY + 0.015625D, var30, var42.getMaxU(), var42.getMaxV());
				 var5.addVertexWithUV(var28, posY + 0.015625D, var29, var42.getMinU(), var42.getMaxV());
				 var5.addVertexWithUV(var27, posY + 0.015625D, var29, var42.getMinU(), var42.getMinV());
				 var5.addVertexWithUV(var27, posY + 0.015625D, var30, var42.getMaxU(), var42.getMinV());
				 if (powerTexture)
				 {
					 //var5.setColorOpaque_F(var8, var8, var8);
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(var28, posY + 0.015625D, var30, var44.getMaxU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(var28, posY + 0.015625D, var29, var44.getMinU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(var27, posY + 0.015625D, var29, var44.getMinU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(var27, posY + 0.015625D, var30, var44.getMaxU(), var44.getMinV());
				 }
			 }
		 }

		 if (!blockAccess.isBlockNormalCube(posX, posY + 1, posZ))
		 {
			 if (blockAccess.isBlockNormalCube(posX - 1, posY, posZ) && blockAccess.getBlockId(posX - 1, posY + 1, posZ) == MCE_Items.CopperWire.blockID)
			 {
				 // West
				 var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
				 var5.addVertexWithUV(posX + 0.015625D, posY + 1 + 0.021875F, posZ + 1, var42.getMinU(), var42.getMaxV());
				 var5.addVertexWithUV(posX + 0.015625D, posY + 0, posZ + 1, var42.getMaxU(), var42.getMaxV());
				 var5.addVertexWithUV(posX + 0.015625D, posY + 0, posZ + 0, var42.getMaxU(), var42.getMinV());
				 var5.addVertexWithUV(posX + 0.015625D, posY + 1 + 0.021875F, posZ + 0, var42.getMinU(), var42.getMinV());
				 if (powerTexture)
				 {
					 //var5.setColorOpaque_F(var8, var8, var8);
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(posX + 0.015625D, posY + 1 + 0.021875F, posZ + 1, var44.getMinU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(posX + 0.015625D, posY + 0, posZ + 1, var44.getMaxU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(posX + 0.015625D, posY + 0, posZ + 0, var44.getMaxU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(posX + 0.015625D, posY + 1 + 0.021875F, posZ + 0, var44.getMinU(), var44.getMinV());
				 }
			 }

			 if (blockAccess.isBlockNormalCube(posX + 1, posY, posZ) && blockAccess.getBlockId(posX + 1, posY + 1, posZ) == MCE_Items.CopperWire.blockID)
			 {
				 // East
				 var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
				 var5.addVertexWithUV(posX + 1 - 0.015625D, posY + 0, posZ + 1, var42.getMinU(), var42.getMaxV());
				 var5.addVertexWithUV(posX + 1 - 0.015625D, posY + 1 + 0.021875F, posZ + 1, var42.getMaxU(), var42.getMaxV());
				 var5.addVertexWithUV(posX + 1 - 0.015625D, posY + 1 + 0.021875F, posZ + 0, var42.getMaxU(), var42.getMinV());
				 var5.addVertexWithUV(posX + 1 - 0.015625D, posY + 0, posZ + 0, var42.getMinU(), var42.getMinV());
				 if (powerTexture)
				 {
					 //var5.setColorOpaque_F(var8, var8, var8);
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(posX + 1 - 0.015625D, posY + 0, posZ + 1, var44.getMinU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(posX + 1 - 0.015625D, posY + 1 + 0.021875F, posZ + 1, var44.getMaxU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(posX + 1 - 0.015625D, posY + 1 + 0.021875F, posZ + 0, var44.getMaxU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(posX + 1 - 0.015625D, posY + 0, posZ + 0, var44.getMinU(), var44.getMinV());
				 }
			 }

			 if (blockAccess.isBlockNormalCube(posX, posY, posZ - 1) && blockAccess.getBlockId(posX, posY + 1, posZ - 1) == MCE_Items.CopperWire.blockID)
			 {
				 // North
				 var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
				 var5.addVertexWithUV(posX + 1, posY + 0, posZ + 0.015625D, var42.getMinU(), var42.getMaxV());
				 var5.addVertexWithUV(posX + 1, posY + 1 + 0.021875F, posZ + 0.015625D, var42.getMaxU(), var42.getMaxV());
				 var5.addVertexWithUV(posX + 0, posY + 1 + 0.021875F, posZ + 0.015625D, var42.getMaxU(), var42.getMinV());
				 var5.addVertexWithUV(posX + 0, posY + 0, posZ + 0.015625D, var42.getMinU(), var42.getMinV());
				 if (powerTexture)
				 {
					 //var5.setColorOpaque_F(var8, var8, var8);
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(posX + 1, posY + 0, posZ + 0.015625D, var44.getMinU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(posX + 1, posY + 1 + 0.021875F, posZ + 0.015625D, var44.getMaxU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(posX + 0, posY + 1 + 0.021875F, posZ + 0.015625D, var44.getMaxU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(posX + 0, posY + 0, posZ + 0.015625D, var44.getMinU(), var44.getMinV());
				 }
			 }

			 if (blockAccess.isBlockNormalCube(posX, posY, posZ + 1) && blockAccess.getBlockId(posX, posY + 1, posZ + 1) == MCE_Items.CopperWire.blockID)
			 {
				 // South
				 var5.setColorOpaque_F(var8 * var10, var8 * var11, var8 * var12);
				 var5.addVertexWithUV(posX + 1, posY + 1 + 0.021875F, posZ + 1 - 0.015625D, var42.getMinU(), var42.getMaxV());
				 var5.addVertexWithUV(posX + 1, posY + 0, posZ + 1 - 0.015625D, var42.getMaxU(), var42.getMaxV());
				 var5.addVertexWithUV(posX + 0, posY + 0, posZ + 1 - 0.015625D, var42.getMaxU(), var42.getMinV());
				 var5.addVertexWithUV(posX + 0, posY + 1 + 0.021875F, posZ + 1 - 0.015625D, var42.getMinU(), var42.getMinV());
				 if (powerTexture)
				 {
					 //var5.setColorOpaque_F(var8, var8, var8);
					 var5.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
					 var5.setBrightness(brightnessTopLeft);
					 var5.addVertexWithUV(posX + 1, posY + 1 + 0.021875F, posZ + 1 - 0.015625D, var44.getMinU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
					 var5.setBrightness(brightnessBottomLeft);
					 var5.addVertexWithUV(posX + 1, posY + 0, posZ + 1 - 0.015625D, var44.getMaxU(), var44.getMaxV());
					 var5.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
					 var5.setBrightness(brightnessBottomRight);
					 var5.addVertexWithUV(posX + 0, posY + 0, posZ + 1 - 0.015625D, var44.getMaxU(), var44.getMinV());
					 var5.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
					 var5.setBrightness(brightnessTopRight);
					 var5.addVertexWithUV(posX + 0, posY + 1 + 0.021875F, posZ + 1 - 0.015625D, var44.getMinU(), var44.getMinV());
				 }
			 }
		 }

		 return true;
	 }

	 public static void RenderBlockInInv(RenderBlocks renderBlocks, Block block, int metaID, int modelID)
	 {
	 }

	 private static float lightValueOwn;
	 private static float aoLightValueYPos;
	 private static float aoLightValueScratchXYZNNN;
	 private static float aoLightValueScratchXYNN;
	 private static float aoLightValueScratchXYZNNP;
	 private static float aoLightValueScratchYZNN;
	 private static float aoLightValueScratchYZNP;
	 private static float aoLightValueScratchXYZPNN;
	 private static float aoLightValueScratchXYPN;
	 private static float aoLightValueScratchXYZPNP;
	 private static float aoLightValueScratchXYZNPN;
	 private static float aoLightValueScratchXYNP;
	 private static float aoLightValueScratchXYZNPP;
	 private static float aoLightValueScratchYZPN;
	 private static float aoLightValueScratchXYZPPN;
	 private static float aoLightValueScratchXYPP;
	 private static float aoLightValueScratchYZPP;
	 private static float aoLightValueScratchXYZPPP;
	 private static float aoLightValueScratchXZNN;
	 private static float aoLightValueScratchXZPN;
	 private static float aoLightValueScratchXZNP;
	 private static float aoLightValueScratchXZPP;
	 private static int aoBrightnessXYZNNN;
	 private static int aoBrightnessXYNN;
	 private static int aoBrightnessXYZNNP;
	 private static int aoBrightnessYZNN;
	 private static int aoBrightnessYZNP;
	 private static int aoBrightnessXYZPNN;
	 private static int aoBrightnessXYPN;
	 private static int aoBrightnessXYZPNP;
	 private static int aoBrightnessXYZNPN;
	 private static int aoBrightnessXYNP;
	 private static int aoBrightnessXYZNPP;
	 private static int aoBrightnessYZPN;
	 private static int aoBrightnessXYZPPN;
	 private static int aoBrightnessXYPP;
	 private static int aoBrightnessYZPP;
	 private static int aoBrightnessXYZPPP;
	 private static int aoBrightnessXZNN;
	 private static int aoBrightnessXZPN;
	 private static int aoBrightnessXZNP;
	 private static int aoBrightnessXZPP;
	 private static boolean aoGrassXYZCPN;
	 private static boolean aoGrassXYZPPC;
	 private static boolean aoGrassXYZNPC;
	 private static boolean aoGrassXYZCPP;
	 private static boolean aoGrassXYZNCN;
	 private static boolean aoGrassXYZPCP;
	 private static boolean aoGrassXYZNCP;
	 private static boolean aoGrassXYZPCN;
	 private static boolean aoGrassXYZCNN;
	 private static boolean aoGrassXYZPNC;
	 private static boolean aoGrassXYZNNC;
	 private static boolean aoGrassXYZCNP;

	 /**
	  * Get ambient occlusion brightness
	  */
	  private static int getAoBrightness(int par1, int par2, int par3, int par4)
	  {
		  if (par1 == 0)
		  {
			  par1 = par4;
		  }

		  if (par2 == 0)
		  {
			  par2 = par4;
		  }

		  if (par3 == 0)
		  {
			  par3 = par4;
		  }

		  return par1 + par2 + par3 + par4 >> 2 & 16711935;
	  }

	  private static boolean getAmbientOcclusionLight(Tessellator var5, IBlockAccess blockAccess, int posX, int posY, int posZ, BlockCopperWire block)
	  {
		  //int var40 = block.colorMultiplier(blockAccess, posX, posY, posZ);
		  int var40 = 0xffffff;
		  float var41 = (var40 >> 16 & 255) / 255.0F;
		  float var42 = (var40 >> 8 & 255) / 255.0F;
		  float var43 = (var40 & 255) / 255.0F;

		  float aoLightValueYPos = block.getAmbientOcclusionLightValue(blockAccess, posX, posY + 1, posZ);

		  float var60 = aoLightValueYPos;
		  float var59 = aoLightValueYPos;
		  float var58 = aoLightValueYPos;
		  float var57 = aoLightValueYPos;
		  brightnessTopLeft = brightnessBottomLeft = brightnessBottomRight = brightnessTopRight = block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ);

		  var5.setBrightness(983055);
		  aoGrassXYZPPC = Block.canBlockGrass[blockAccess.getBlockId(posX + 1, posY + 1, posZ)];
		  aoGrassXYZPNC = Block.canBlockGrass[blockAccess.getBlockId(posX + 1, posY - 1, posZ)];
		  aoGrassXYZPCP = Block.canBlockGrass[blockAccess.getBlockId(posX + 1, posY, posZ + 1)];
		  aoGrassXYZPCN = Block.canBlockGrass[blockAccess.getBlockId(posX + 1, posY, posZ - 1)];
		  aoGrassXYZNPC = Block.canBlockGrass[blockAccess.getBlockId(posX - 1, posY + 1, posZ)];
		  aoGrassXYZNNC = Block.canBlockGrass[blockAccess.getBlockId(posX - 1, posY - 1, posZ)];
		  aoGrassXYZNCN = Block.canBlockGrass[blockAccess.getBlockId(posX - 1, posY, posZ - 1)];
		  aoGrassXYZNCP = Block.canBlockGrass[blockAccess.getBlockId(posX - 1, posY, posZ + 1)];
		  aoGrassXYZCPP = Block.canBlockGrass[blockAccess.getBlockId(posX, posY + 1, posZ + 1)];
		  aoGrassXYZCPN = Block.canBlockGrass[blockAccess.getBlockId(posX, posY + 1, posZ - 1)];
		  aoGrassXYZCNP = Block.canBlockGrass[blockAccess.getBlockId(posX, posY - 1, posZ + 1)];
		  aoGrassXYZCNN = Block.canBlockGrass[blockAccess.getBlockId(posX, posY - 1, posZ - 1)];

		  aoBrightnessXYNP = block.getMixedBrightnessForBlock(blockAccess, posX - 1, posY, posZ);
		  aoBrightnessXYPP = block.getMixedBrightnessForBlock(blockAccess, posX + 1, posY, posZ);
		  aoBrightnessYZPN = block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ - 1);
		  aoBrightnessYZPP = block.getMixedBrightnessForBlock(blockAccess, posX, posY, posZ + 1);
		  aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(blockAccess, posX - 1, posY, posZ);
		  aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(blockAccess, posX + 1, posY, posZ);
		  aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(blockAccess, posX, posY, posZ - 1);
		  aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(blockAccess, posX, posY, posZ + 1);

		  if (!aoGrassXYZCPN && !aoGrassXYZNPC)
		  {
			  aoLightValueScratchXYZNPN = aoLightValueScratchXYNP;
			  aoBrightnessXYZNPN = aoBrightnessXYNP;
		  }
		  else
		  {
			  aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(blockAccess, posX - 1, posY, posZ - 1);
			  aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockAccess, posX - 1, posY, posZ - 1);
		  }

		  if (!aoGrassXYZCPN && !aoGrassXYZPPC)
		  {
			  aoLightValueScratchXYZPPN = aoLightValueScratchXYPP;
			  aoBrightnessXYZPPN = aoBrightnessXYPP;
		  }
		  else
		  {
			  aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(blockAccess, posX + 1, posY, posZ - 1);
			  aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockAccess, posX + 1, posY, posZ - 1);
		  }

		  if (!aoGrassXYZCPP && !aoGrassXYZNPC)
		  {
			  aoLightValueScratchXYZNPP = aoLightValueScratchXYNP;
			  aoBrightnessXYZNPP = aoBrightnessXYNP;
		  }
		  else
		  {
			  aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(blockAccess, posX - 1, posY, posZ + 1);
			  aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockAccess, posX - 1, posY, posZ + 1);
		  }

		  if (!aoGrassXYZCPP && !aoGrassXYZPPC)
		  {
			  aoLightValueScratchXYZPPP = aoLightValueScratchXYPP;
			  aoBrightnessXYZPPP = aoBrightnessXYPP;
		  }
		  else
		  {
			  aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(blockAccess, posX + 1, posY, posZ + 1);
			  aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockAccess, posX + 1, posY, posZ + 1);
		  }

		  var60 = (aoLightValueScratchXYZNPP + aoLightValueScratchXYNP + aoLightValueScratchYZPP + aoLightValueYPos) / 4.0F;
		  var57 = (aoLightValueScratchYZPP + aoLightValueYPos + aoLightValueScratchXYZPPP + aoLightValueScratchXYPP) / 4.0F;
		  var58 = (aoLightValueYPos + aoLightValueScratchYZPN + aoLightValueScratchXYPP + aoLightValueScratchXYZPPN) / 4.0F;
		  var59 = (aoLightValueScratchXYNP + aoLightValueScratchXYZNPN + aoLightValueYPos + aoLightValueScratchYZPN) / 4.0F;
		  brightnessTopRight = getAoBrightness(aoBrightnessXYZNPP, aoBrightnessXYNP, aoBrightnessYZPP, brightnessTopRight);
		  brightnessTopLeft = getAoBrightness(aoBrightnessYZPP, aoBrightnessXYZPPP, aoBrightnessXYPP, brightnessTopLeft);
		  brightnessBottomLeft = getAoBrightness(aoBrightnessYZPN, aoBrightnessXYPP, aoBrightnessXYZPPN, brightnessBottomLeft);
		  brightnessBottomRight = getAoBrightness(aoBrightnessXYNP, aoBrightnessXYZNPN, aoBrightnessYZPN, brightnessBottomRight);

		  colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = var41;
		  colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = var42;
		  colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = var43;
		  colorRedTopLeft *= var57;
		  colorGreenTopLeft *= var57;
		  colorBlueTopLeft *= var57;
		  colorRedBottomLeft *= var58;
		  colorGreenBottomLeft *= var58;
		  colorBlueBottomLeft *= var58;
		  colorRedBottomRight *= var59;
		  colorGreenBottomRight *= var59;
		  colorBlueBottomRight *= var59;
		  colorRedTopRight *= var60;
		  colorGreenTopRight *= var60;
		  colorBlueTopRight *= var60;

		  return true;
	  }
}
