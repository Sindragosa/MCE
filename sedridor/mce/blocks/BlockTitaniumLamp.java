package sedridor.mce.blocks;

import sedridor.mce.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTitaniumLamp extends Block
{
	/** Whether the redstone torch is currently active or not. */
	private boolean lampActive = false;
	private int side = 0;
	private int meta = 0;

	/** Map of ArrayLists of RedstoneUpdateInfo. Key of map is World. */
	private static Map redstoneUpdateInfoCache = new HashMap();

	public BlockTitaniumLamp(int par1, boolean par2)
	{
		super(par1, Material.circuits);
		this.lampActive = par2;
		this.setTickRandomly(true);
		this.setCreativeTab((CreativeTabs)null);
		if (par2)
		{
			this.setLightValue(1.0F);
		}
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
		return MCE_Items.titaniumLampModelID;
	}

	/**
	 * Gets if we can place a torch on a block.
	 */
	private boolean canPlaceTorchOn(World par1World, int par2, int par3, int par4)
	{
		if (par1World.doesBlockHaveSolidTopSurface(par2, par3, par4))
		{
			return true;
		}
		else
		{
			int var5 = par1World.getBlockId(par2, par3, par4);
			return var5 == Block.fence.blockID || var5 == Block.netherFence.blockID || var5 == Block.glass.blockID;
		}
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) ? true : (par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) ? true : (par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) ? true : (par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) ? true : (par1World.isBlockNormalCubeDefault(par2, par3 + 1, par4, true) && par1World.isAirBlock(par2, par3 - 1, par4) && par1World.isAirBlock(par2, par3 - 2, par4) ? true : this.canPlaceTorchOn(par1World, par2, par3 - 1, par4)))));
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		//        if (!par1World.isRemote)
		//        {
		//            System.out.println("MC onBlockPlaced... " + (par9) + ", side... " + (par5));
		//        }
		int var9 = par9;
		if (par9 == 0)
		{
			this.side = par5;
			if (par5 == 1 && this.canPlaceTorchOn(par1World, par2, par3 - 1, par4))
			{
				var9 = 5;
			}

			if (par5 == 2 && par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
			{
				var9 = 4;
			}

			if (par5 == 3 && par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
			{
				var9 = 3;
			}

			if (par5 == 4 && par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
			{
				var9 = 2;
			}

			if (par5 == 5 && par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
			{
				var9 = 1;
			}

			if (par5 == 0 && par1World.isBlockNormalCubeDefault(par2, par3 + 1, par4, true))
			{
				var9 = 6;
			}
			this.meta = var9;
		}

		return var9;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		//        if (!par1World.isRemote)
		//        {
		//            System.out.println("MC onBlockAdded... " + (par1World.getBlockMetadata(par2, par3, par4)) + ", side... " + (this.side));
		//        }
		if (!this.lampActive)
		{
			par1World.setBlock(par2, par3, par4, MCE_Items.TitaniumLampActive.blockID, this.meta, 2);
		}
		this.dropTorchIfCantStay(par1World, par2, par3, par4);

		if (this.lampActive)
		{
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		/*if (!par1World.isRemote)
        {
            if (this.lampActive && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
            {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
            }
            else if (!this.lampActive && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
            {
                par1World.setBlockAndMetadataWithNotify(par2, par3, par4, MCE_Items.LightBulbActive.blockID, par1World.getBlockMetadata(par2, par3, par4));
            }
        }*/

		if (this.dropTorchIfCantStay(par1World, par2, par3, par4))
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			boolean var7 = false;

			if (!par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) && var6 == 1)
			{
				var7 = true;
			}

			if (!par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) && var6 == 2)
			{
				var7 = true;
			}

			if (!par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) && var6 == 3)
			{
				var7 = true;
			}

			if (!par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) && var6 == 4)
			{
				var7 = true;
			}

			if (!this.canPlaceTorchOn(par1World, par2, par3 - 1, par4) && var6 == 5)
			{
				var7 = true;
			}

			if (!this.canPlaceTorchOn(par1World, par2, par3 + 1, par4) && var6 == 6)
			{
				var7 = true;
			}

			if (var7)
			{
				this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}

	/**
	 * Tests if the block can remain at its current location and will drop as an item if it is unable to stay. Returns
	 * True if it can stay and False if it drops. Args: world, x, y, z
	 */
	private boolean dropTorchIfCantStay(World par1World, int par2, int par3, int par4)
	{
		if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
		{
			if (par1World.getBlockId(par2, par3, par4) == this.blockID)
			{
				this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockToAir(par2, par3, par4);
			}

			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * ejects contained items into the world, and notifies neighbours of an update, as appropriate
	 */
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		if (this.lampActive)
		{
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
		}
	}

	/**
	 * Returns true if the block is emitting indirect/weak redstone power on the specified side. If isBlockNormalCube
	 * returns true, standard redstone propagation rules will apply instead and this will not be called. Args: World, X,
	 * Y, Z, side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
	 */
	@Override
	public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if (!this.lampActive)
		{
			return 0;
		}
		else
		{
			int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
			return var6 == 5 && par5 == 1 ? 0 : (var6 == 3 && par5 == 3 ? 0 : (var6 == 4 && par5 == 2 ? 0 : (var6 == 1 && par5 == 5 ? 0 : (var6 == 2 || par5 == 4 ? 0 : 15))));
		}
	}

	/**
	 * Returns true or false based on whether the block the torch is attached to is providing indirect power.
	 */
	private boolean isIndirectlyPowered(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		return var5 == 5 && par1World.getIndirectPowerOutput(par2, par3 - 1, par4, 0) ? true : (var5 == 3 && par1World.getIndirectPowerOutput(par2, par3, par4 - 1, 2) ? true : (var5 == 4 && par1World.getIndirectPowerOutput(par2, par3, par4 + 1, 3) ? true : (var5 == 1 && par1World.getIndirectPowerOutput(par2 - 1, par3, par4, 4) ? true : var5 == 2 && par1World.getIndirectPowerOutput(par2 + 1, par3, par4, 5))));
	}

	/**
	 * Returns true if the block is emitting direct/strong redstone power on the specified side. Args: World, X, Y, Z,
	 * side. Note that the side is reversed - eg it is 1 (up) when checking the bottom of the block.
	 */
	@Override
	public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 0 ? this.isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5) : 0;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return MCE_Items.TitaniumLampIdle.blockID;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this change based on its state.
	 */
	@Override
	public boolean canProvidePower()
	{
		return true;
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
	}

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return MCE_Items.TitaniumLampIdle.blockID;
	}

	/**
	 * Returns true if the given block ID is equivalent to this one. Example: redstoneTorchOn matches itself and
	 * redstoneTorchOff, and vice versa. Most blocks only match themselves.
	 */
	@Override
	public boolean isAssociatedBlockID(int par1)
	{
		return par1 == MCE_Items.TitaniumLampIdle.blockID || par1 == MCE_Items.TitaniumLampActive.blockID;
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		if (this.lampActive)
		{
			this.blockIcon = par1IconRegister.registerIcon("TitaniumLampActive");
		}
		else
		{
			this.blockIcon = par1IconRegister.registerIcon("TitaniumLampIdle");
		}
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	 * x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		int var7 = par1World.getBlockMetadata(par2, par3, par4) & 7;
		float var8 = 0.15F;

		if (var7 == 1)
		{
			this.setBlockBounds(0.0F, 0.2F, 0.5F - var8, var8 * 2.0F, 0.8F, 0.5F + var8);
		}
		else if (var7 == 2)
		{
			this.setBlockBounds(1.0F - var8 * 2.0F, 0.2F, 0.5F - var8, 1.0F, 0.8F, 0.5F + var8);
		}
		else if (var7 == 3)
		{
			this.setBlockBounds(0.5F - var8, 0.2F, 0.0F, 0.5F + var8, 0.8F, var8 * 2.0F);
		}
		else if (var7 == 4)
		{
			this.setBlockBounds(0.5F - var8, 0.2F, 1.0F - var8 * 2.0F, 0.5F + var8, 0.8F, 1.0F);
		}
		else if (var7 == 5)
		{
			var8 = 0.1F;
			this.setBlockBounds(0.5F - var8, 0.0F, 0.5F - var8, 0.5F + var8, 0.6F, 0.5F + var8);
		}
		else
		{
			var8 = 0.1F;
			this.setBlockBounds(0.5F - var8, 0.4F, 0.5F - var8, 0.5F + var8, 1.0F, 0.5F + var8);
		}

		return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	}
}
