package sedridor.mce.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemMetalBow extends Item
{
	public static final String[] bowPullIconNameArray = new String[] {"_pull_0", "_pull_1", "_pull_2"};
	private Icon[] iconArray;

	private float power;
	public ItemMetalBow(int par1)
	{
		this(par1, 384, 2.0F);
	}
	public ItemMetalBow(int par1, int par2)
	{
		this(par1, par2, 2.0F);
	}
	public ItemMetalBow(int par1, int par2, float par3)
	{
		super(par1);
		this.maxStackSize = 1;
		this.power = par3;
		this.setMaxDamage(par2 - 1);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	/**
	 * called when the player releases the use item button. Args: par1ItemStack, par2World, par3EntityPlayer, itemInUseCount
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		boolean var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

		if (var5 || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
		{
			int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
			float var7 = var6 / 20.0F;
			var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

			if (var7 < 0.1D)
			{
				return;
			}

			if (var7 > 1.0F)
			{
				var7 = 1.0F;
			}

			EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, var7 * this.power);

			System.out.println("ItemMetalBow: power " + (var7 * this.power) + ", par1ItemStack " + par1ItemStack.getItem().getUnlocalizedName());
			if (var7 == 1.0F)
			{
				var8.setIsCritical(true);
			}

			int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

			if (var9 > 0)
			{
				var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
			}

			int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

			if (var10 > 0)
			{
				var8.setKnockbackStrength(var10);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
			{
				var8.setFire(100);
			}

			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

			if (var5)
			{
				var8.canBePickedUp = 2;
			}
			else
			{
				par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
			}

			if (!par2World.isRemote)
			{
				par2World.spawnEntityInWorld(var8);
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
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
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Item.arrow.itemID))
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}

		return par1ItemStack;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getItemEnchantability()
	{
		return 1;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		this.iconArray = new Icon[bowPullIconNameArray.length];

		for (int var2 = 0; var2 < this.iconArray.length; ++var2)
		{
			this.iconArray[var2] = par1IconRegister.registerIcon(this.getUnlocalizedName().substring(5) + bowPullIconNameArray[var2]);
		}
	}

	public Icon getIcon(int par1)
	{
		return this.iconArray[par1];
	}
}
