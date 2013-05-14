package sedridor.mce.items;

import sedridor.mce.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStaffBlue extends Item
{
	public int timer;

	public ItemStaffBlue(int par1)
	{
		super(par1);
		this.setMaxDamage(79);
		this.timer = 0;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (this.timer == 0)
		{
			EntityArcaneCharge var4 = new EntityArcaneCharge(par2World, par3EntityPlayer, 2.0F); // 1.0F power = 72km
			par2World.playSoundAtEntity(par3EntityPlayer, "mob.ghast.fireball", 0.5F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!par2World.isRemote)
			{
				par2World.spawnEntityInWorld(var4);
				par1ItemStack.damageItem(1, par3EntityPlayer);
				this.timer = 30;
			}
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
