package sedridor.mce.items;

import sedridor.mce.*;
import sedridor.mce.entities.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShuriken extends Item
{
	public int timer;

	public ItemShuriken(int par1)
	{
		super(par1);
		this.maxStackSize = 64;
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (this.timer == 0 && par3EntityPlayer.inventory.hasItem(MCE_Items.NinjaStar.itemID))
		{
			EntityShuriken var4 = new EntityShuriken(par2World, par3EntityPlayer, 2.0F);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!par2World.isRemote)
			{
				par2World.spawnEntityInWorld(var4);
				this.timer = 20;
			}

			--par1ItemStack.stackSize;
		}

		return par1ItemStack;
	}

	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if (this.timer > 0)
		{
			--this.timer;
		}
	}
}
