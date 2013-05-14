package sedridor.mce.items;

import sedridor.mce.entities.*;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTwilightWand extends Item
{
	private int cooldown;

	public ItemTwilightWand(int par1)
	{
		super(par1);
		this.maxStackSize = 1;
		this.cooldown = 0;
		this.setMaxDamage(99);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (this.cooldown == 0 && par1ItemStack.getItemDamage() < this.getMaxDamage())
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		else
		{
			par3EntityPlayer.stopUsingItem();
		}

		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		if (this.cooldown > 0 || par1ItemStack.getItemDamage() >= this.getMaxDamage())
		{
			par3EntityPlayer.stopUsingItem();
		}
		else
		{
			par2World.playSoundAtEntity(par3EntityPlayer, "mob.ghast.fireball", 1.0F, (par2World.rand.nextFloat() - par2World.rand.nextFloat()) * 0.2F + 1.0F);
			if (!par2World.isRemote)
			{
				this.cooldown = 200;
				par2World.spawnEntityInWorld(new EntityTwilightBolt(par2World, par3EntityPlayer));
				par1ItemStack.damageItem(1, par3EntityPlayer);
			}
		}
	}

	/**
	 * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	 * update it's contents.
	 */
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if (this.cooldown > 0)
		{
			--this.cooldown;
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	/**
	 * Return an item rarity from EnumRarity
	 */
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return EnumRarity.rare;
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, List par2)
	{
		par2.add(par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() + 1 + " charges left");
	}
}
