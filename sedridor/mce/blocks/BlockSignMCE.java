package sedridor.mce.blocks;

import sedridor.mce.*;
import sedridor.mce.tileentities.*;

import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSignMCE extends BlockContainer
{
	private Class signEntityClass;

	/** Whether this is a freestanding sign or a wall-mounted sign */
	private boolean isFreestanding;

	public BlockSignMCE(int par1, Class par2Class, boolean par3)
	{
		super(par1, Material.wood);
		this.isFreestanding = par3;
		this.signEntityClass = par2Class;
		this.disableStats();
		float var4 = 0.25F;
		float var5 = 1.0F;
		this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var5, 0.5F + var4);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2)
	{
		return Block.planks.getBlockTextureFromSide(par1);
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

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		if (!this.isFreestanding)
		{
			int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
			float var6 = 0.28125F;
			float var7 = 0.78125F;
			float var8 = 0.0F;
			float var9 = 1.0F;
			float var10 = 0.125F;
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

			if (var5 == 2)
			{
				this.setBlockBounds(var8, var6, 1.0F - var10, var9, var7, 1.0F);
			}

			if (var5 == 3)
			{
				this.setBlockBounds(var8, var6, 0.0F, var9, var7, var10);
			}

			if (var5 == 4)
			{
				this.setBlockBounds(1.0F - var10, var6, var8, 1.0F, var7, var9);
			}

			if (var5 == 5)
			{
				this.setBlockBounds(0.0F, var6, var8, var10, var7, var9);
			}
		}
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return -1;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
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
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World)
	{
		try
		{
			return (TileEntity)this.signEntityClass.newInstance();
		}
		catch (Exception var3)
		{
			throw new RuntimeException(var3);
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.sign.itemID;
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		boolean var6 = false;

		if (this.isFreestanding)
		{
			if (!par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid())
			{
				var6 = true;
			}
		}
		else
		{
			int var7 = par1World.getBlockMetadata(par2, par3, par4);
			var6 = true;

			if (var7 == 2 && par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid())
			{
				var6 = false;
			}

			if (var7 == 3 && par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid())
			{
				var6 = false;
			}

			if (var7 == 4 && par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid())
			{
				var6 = false;
			}

			if (var7 == 5 && par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid())
			{
				var6 = false;
			}
		}

		// ------------------------------------------------ MCE
		if (par5 > 0 && Block.blocksList[par5].canProvidePower())
		{
			TileEntitySignMCE var8 = (TileEntitySignMCE)par1World.getBlockTileEntity(par2, par3, par4);
			var8.redStone = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
		}
		// ------------------------------------------------ MCE

		if (var6)
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}

		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}

	// ------------------------------------------------ MCE
	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (!par5EntityPlayer.isSneaking())
		{
			TileEntitySignMCE var10 = (TileEntitySignMCE)par1World.getBlockTileEntity(par2, par3, par4);

			if (var10 != null)
			{
				this.displayGUIEditSign(var10, par5EntityPlayer);
				//((EntityPlayerSP)par5EntityPlayer).displayGUIEditSign(var10);
			}
		}

		return true;
	}
	// ------------------------------------------------ MCE

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.sign.itemID;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister) {}

	/**
	 * Displays the GUI for editing a sign. Args: tileEntitySign
	 */
	private void displayGUIEditSign(TileEntity par1TileEntity, EntityPlayer par2EntityPlayer)
	{
		if (par1TileEntity instanceof TileEntitySignMCE)
		{
			if (par2EntityPlayer instanceof EntityPlayerSP)
			{
				EntityPlayerSP var2 = (EntityPlayerSP)par2EntityPlayer;
				Minecraft mc = (Minecraft)MCE_Reflect.getValue(EntityPlayerSP.class, var2, 1);
				if (mc != null)
				{
					mc.displayGuiScreen(new GuiEditSignMCE((TileEntitySignMCE)par1TileEntity));
				}
			}
		}
	}
}
