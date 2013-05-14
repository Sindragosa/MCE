package sedridor.mce.items;

import sedridor.mce.*;
import sedridor.mce.tileentities.TileEntitySignMCE;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSign;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSignMCE extends ItemSign
{
	public ItemSignMCE(int par1)
	{
		super(par1);
		this.maxStackSize = 16;
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (par7 == 0)
		{
			return false;
		}
		else if (!par3World.getBlockMaterial(par4, par5, par6).isSolid())
		{
			return false;
		}
		else
		{
			if (par7 == 1)
			{
				++par5;
			}

			if (par7 == 2)
			{
				--par6;
			}

			if (par7 == 3)
			{
				++par6;
			}

			if (par7 == 4)
			{
				--par4;
			}

			if (par7 == 5)
			{
				++par4;
			}

			if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
			{
				return false;
			}
			else if (!Block.signPost.canPlaceBlockAt(par3World, par4, par5, par6))
			{
				return false;
			}
			else
			{
				if (par7 == 1)
				{
					int var11 = MathHelper.floor_double((par2EntityPlayer.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 15;
					par3World.setBlock(par4, par5, par6, Block.signPost.blockID, var11, 2);
				}
				else
				{
					par3World.setBlock(par4, par5, par6, Block.signWall.blockID, par7, 2);
				}

				--par1ItemStack.stackSize;
				TileEntitySignMCE var12 = (TileEntitySignMCE)par3World.getBlockTileEntity(par4, par5, par6);

				if (var12 != null)
				{
					this.displayGUIEditSign(var12, par2EntityPlayer);
					//((EntityPlayerSP)par2EntityPlayer).displayGUIEditSign(var12);
				}

				return true;
			}
		}
	}

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
