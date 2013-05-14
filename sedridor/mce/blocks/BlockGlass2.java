package sedridor.mce.blocks;

import sedridor.mce.*;
import sedridor.mce.proxy.*;
import java.util.Random;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

public class BlockGlass2 extends BlockBreakable
{
	public static final String[] textureTypes = new String[] {"glass", "glass_frame", "glass_side"};
	private Icon[] iconArray;

	public BlockGlass2(int par1, Material par2Material, boolean par3)
	{
		super(par1, "glass", par2Material, par3);
		this.setCreativeTab(CreativeTabs.tabBlock);
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
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return MCE_Items.glassCubeModelID;
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
		//Set the static var in the proxy
		ClientProxy.renderPass = pass;
		//the block can render in both passes, so return true always
		return true;
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
	 * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
	 */
	@Override
	public boolean canSilkHarvest()
	{
		return true;
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.iconArray = new Icon[textureTypes.length];
		for (int var2 = 0; var2 < textureTypes.length; ++var2)
		{
			this.iconArray[var2] = par1IconRegister.registerIcon(textureTypes[var2]);
		}
	}
}
