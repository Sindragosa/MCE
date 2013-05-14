package sedridor.mce.items;

import sedridor.mce.*;
import sedridor.mce.entities.*;
import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMissileLauncher extends Item
{
	private float power;
	private int cooldown;

	public ItemMissileLauncher(int par1)
	{
		this(par1, 100, 2.0F);
	}
	public ItemMissileLauncher(int par1, int par2, float par3)
	{
		super(par1);
		this.maxStackSize = 1;
		this.power = par3;
		this.cooldown = 0;
		this.setMaxDamage(par2 - 1);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (this.cooldown == 0 && par1ItemStack.getItemDamage() < this.getMaxDamage() && (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(MCE_Items.Missile.itemID)))
		{
			par2World.playSoundAtEntity(par3EntityPlayer, "missile", 0.5F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!par3EntityPlayer.capabilities.isCreativeMode)
			{
				par3EntityPlayer.inventory.consumeInventoryItem(MCE_Items.Missile.itemID);
			}
			if (!par2World.isRemote)
			{
				this.cooldown = 40;
				par2World.spawnEntityInWorld(new EntityMissile(par2World, par3EntityPlayer, this.power));
				par1ItemStack.damageItem(1, par3EntityPlayer);
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
		if (this.cooldown > 0 && !par2World.isRemote)
		{
			--this.cooldown;
		}
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	public void addInformation(ItemStack par1ItemStack, List par2)
	{
		par2.add(par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() + 1 + " charges left");
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		/*this.iconArray = new Icon[bowPullIconNameArray.length];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon(this.getUnlocalizedName().substring(5) + bowPullIconNameArray[var2]);
        }*/
	}

	/*public Icon func_94599_c(int par1)
    {
        return this.iconArray[par1];
    }*/
}
