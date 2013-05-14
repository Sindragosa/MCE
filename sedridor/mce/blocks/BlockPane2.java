package sedridor.mce.blocks;

import sedridor.mce.*;
import sedridor.mce.proxy.ClientProxy;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPane2 extends Block
{
	public static final String[] textureTypes = new String[] {"glass", "glass_frame", "glass_side"};
	private Icon[] iconArray;

	/**
	 * If this field is true, the pane block drops itself when destroyed (like the iron fences), otherwise, it's just
	 * destroyed (like glass panes)
	 */
	private final boolean canDropItself;

	private int direction;

	public BlockPane2(int par1, Material par2Material, boolean par3)
	{
		super(par1, par2Material);
		this.canDropItself = par3;
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public Icon getIcon(int par1, int par2)
	{
		if (ClientProxy.renderPass == 0)
		{
			return this.iconArray[1];
		}
		else
		{
			return this.iconArray[0];
		}
	}

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if (ClientProxy.renderPass == 0)
		{
			return this.iconArray[1];
		}
		else
		{
			return this.iconArray[0];
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return !this.canDropItself ? 0 : super.idDropped(par1, par2Random, par3);
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
		return MCE_Items.thinGlassModelID;
	}

	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	@Override
	public int getRenderBlockPass()
	{
		return 1;
	}

	/**
	 * Determines if this block should render in this pass.
	 */
	@Override
	public boolean canRenderInPass(int pass)
	{
		ClientProxy.renderPass = pass;
		return true;
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		if (par9 == 0)
		{
			boolean var8 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
			boolean var9 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
			boolean var10 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
			boolean var11 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));

			if ((var8 || var9) && (!var10 && !var11) || (var8 && var9) && (!var10 || !var11))
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		return par9;
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
			boolean var8 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
			boolean var9 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
			boolean var10 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
			boolean var11 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));
			int var7 = par1World.getBlockMetadata(par2, par3, par4);

			if ((var8 || var9) && (!var10 && !var11) || (var8 && var9) && (!var10 || !var11))
			{
				if (var7 != 1)
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
				}
			}
			else if (var7 != 0)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
			}
		}

	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
		int var7 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		if (var6 == this.blockID)
		{
			if (par5 == 2)
			{
				int var8 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1);
				if (var7 != var8 || var7 == 0)
				{
					return true;
				}
			}
			else if (par5 == 3)
			{
				int var8 = par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1);
				if (var7 != var8 || var7 == 0)
				{
					return true;
				}
			}
			else if (par5 == 4)
			{
				int var8 = par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4);
				if (var7 != var8 || var7 == 1)
				{
					return true;
				}
			}
			else if (par5 == 5)
			{
				int var8 = par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4);
				if (var7 != var8 || var7 == 1)
				{
					return true;
				}
			}
			else if (par5 == 0)
			{
				int var8 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
				if (var7 != var8)
				{
					return true;
				}
			}
			else if (par5 == 1)
			{
				int var8 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
				if (var7 != var8)
				{
					return true;
				}
			}
			return false;
		}

		return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		boolean var8 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
		boolean var9 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
		boolean var10 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
		boolean var11 = this.canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));

		if ((var8 || var9) && (!var10 && !var11) || (var8 && var9) && (!var10 || !var11))
		{
			this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
	}

	/**
	 * Sets the block's bounds for rendering it as an item
	 */
	@Override
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		boolean var5 = this.canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 - 1));
		boolean var6 = this.canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 + 1));
		boolean var7 = this.canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 - 1, par3, par4));
		boolean var8 = this.canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 + 1, par3, par4));

		if ((var5 || var6) && (!var7 && !var8) || (var5 && var6) && (!var7 || !var8))
		{
			this.setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
		}

	}

	/**
	 * Returns the texture index of the thin side of the pane.
	 */
	public Icon getSideTextureIndex()
	{
		return this.iconArray[2];
	}

	/**
	 * Gets passed in the blockID of the block adjacent and supposed to return true if its allowed to connect to the
	 * type of blockID passed in. Args: blockID
	 */
	public final boolean canThisPaneConnectToThisBlockID(int par1)
	{
		return Block.opaqueCubeLookup[par1] || par1 == this.blockID || par1 == Block.glass.blockID || par1 == Block.thinGlass.blockID;
	}

	/**
	 * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
	 */
	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}

	/**
	 * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(this.blockID, 1, par1);
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.iconArray = new Icon[BlockPane2.textureTypes.length];
		for (int var2 = 0; var2 < this.iconArray.length; ++var2)
		{
			this.iconArray[var2] = par1IconRegister.registerIcon(BlockPane2.textureTypes[var2]);
		}
	}
}
