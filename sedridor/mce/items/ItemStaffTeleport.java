package sedridor.mce.items;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemStaffTeleport extends Item
{
	public int timer;
	private Random random;

	/** The new X position to be applied to the entity. */
	private int newPosX;

	/** The new Y position to be applied to the entity. */
	private int newPosY;

	/** The new Z position to be applied to the entity. */
	private int newPosZ;

	public boolean spawnParticles;

	public ItemStaffTeleport(int par1)
	{
		super(par1);
		this.setMaxDamage(53);
		this.timer = 0;
		this.random = new Random();
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (this.timer == 0)
		{
			this.newPosX = MathHelper.floor_double(par3EntityPlayer.posX);
			this.newPosY = MathHelper.floor_double(par3EntityPlayer.posY);
			this.newPosZ = MathHelper.floor_double(par3EntityPlayer.posZ);
			for (int var32 = 0; var32 < 32; ++var32)
			{
				par2World.spawnParticle("portal", par3EntityPlayer.posX, par3EntityPlayer.posY + this.random.nextDouble() * 2.0D, par3EntityPlayer.posZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
			}
			if (!par2World.isRemote)
			{
				this.timer = 20;
				this.teleportRandom(par2World, par3EntityPlayer);
			}
			this.spawnParticles = true;
			par1ItemStack.damageItem(1, par3EntityPlayer);
			return par1ItemStack;
		}
		else
		{
			return par1ItemStack;
		}
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
		if (this.spawnParticles && par3Entity instanceof EntityPlayer && par2World.isRemote)
		{
			EntityPlayer var6 = (EntityPlayer)par3Entity;
			int var7 = MathHelper.floor_double(var6.posX);
			int var8 = MathHelper.floor_double(var6.posY);
			int var9 = MathHelper.floor_double(var6.posZ);
			double var10 = MathHelper.sqrt_double((this.newPosX - var7) * (this.newPosX - var7) + (this.newPosY - var8) * (this.newPosY - var8) + (this.newPosZ - var9) * (this.newPosZ - var9));
			if (var10 > 4.0D)
			{
				this.spawnParticles = false;
				for (int var32 = 0; var32 < 32; ++var32)
				{
					par2World.spawnParticle("portal", var6.posX, var6.posY + this.random.nextDouble() * 2.0D, var6.posZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
				}
			}
		}
	}

	/**
	 * Teleport the enderman to a random nearby position
	 */
	protected void teleportRandom(World par1World, EntityPlayer par2EntityPlayer)
	{
		int var4 = MathHelper.floor_double(par2EntityPlayer.posX);
		int var5 = MathHelper.floor_double(par2EntityPlayer.posZ);
		int var6 = MathHelper.floor_double(par2EntityPlayer.boundingBox.minY);
		int var7 = this.random.nextInt(34) - 17;
		int var8 = this.random.nextInt(34) - 17;

		if (var7 < 0 && var7 > -5 || var7 < 5 && var7 > 0)
		{
			var7 *= 2;
		}

		if (var8 < 0 && var8 > -5 || var8 < 5 && var8 > 0)
		{
			var8 *= 2;
		}

		if (par2EntityPlayer instanceof EntityPlayerMP)
		{
			EntityPlayerMP var10 = (EntityPlayerMP)par2EntityPlayer;
			if (!var10.playerNetServerHandler.connectionClosed && var10.worldObj == par1World)
			{
				if (!this.teleportTo(par1World, par2EntityPlayer, var4 + var7 + 0.5D, var6 + 1, var5 + var8 + 0.5D))
				{
					this.teleportRandom(par1World, par2EntityPlayer);
				}
				par2EntityPlayer.fallDistance = 0.0F;
			}
		}
	}

	/**
	 * Teleport the enderman
	 */
	protected boolean teleportTo(World par1World, EntityPlayer par2EntityPlayer, double posX, double posY, double posZ)
	{
		double var1 = par2EntityPlayer.posX;
		double var2 = par2EntityPlayer.posY;
		double var3 = par2EntityPlayer.posZ;
		boolean var4 = false;
		int var5 = MathHelper.floor_double(posX);
		int var6 = MathHelper.floor_double(posY);
		int var7 = MathHelper.floor_double(posZ);
		int var8;

		if (par1World.blockExists(var5, var6, var7)) // block is in loaded chunk
		{
			boolean var9 = false;
			while (!var9 && var6 > 0)
			{
				var8 = par1World.getBlockId(var5, var6 + 1, var7);
				++posY;
				++var6;
				if (var8 == 0 && par1World.getBlockId(var5, var6 + 1, var7) == 0)
				{
					var9 = true;
				}
			}

			var9 = false;
			while (!var9 && var6 > 0)
			{
				var8 = par1World.getBlockId(var5, var6 - 1, var7);
				if (var8 != 0 && Block.blocksList[var8].blockMaterial.blocksMovement() && par1World.getBlockId(var5, var6 + 1, var7) == 0 || posY < var2 - 20)
				{
					var9 = true;
				}
				else
				{
					--posY;
					--var6;
				}
			}

			if (var9)
			{
				par2EntityPlayer.setPositionAndUpdate(posX, posY, posZ);
				if (par1World.getCollidingBoundingBoxes(par2EntityPlayer, par2EntityPlayer.boundingBox).isEmpty() && !par1World.isAnyLiquid(par2EntityPlayer.boundingBox))
				{
					var4 = true;
				}
			}
		}

		if (!var4)
		{
			par2EntityPlayer.setPositionAndUpdate(var1, var2, var3);
			return false;
		}
		else
		{
			short var30 = 128;
			for (var8 = 0; var8 < var30; ++var8)
			{
				double var19 = var8 / (var30 - 1.0D);
				float var21 = (this.random.nextFloat() - 0.5F) * 0.2F;
				float var22 = (this.random.nextFloat() - 0.5F) * 0.2F;
				float var23 = (this.random.nextFloat() - 0.5F) * 0.2F;
				double var24 = var1 + (posX - var1) * var19 + (this.random.nextDouble() - 0.5D) * par2EntityPlayer.width * 2.0D;
				double var26 = var2 + (posY - var2) * var19 + this.random.nextDouble() * par2EntityPlayer.height;
				double var28 = var3 + (posZ - var3) * var19 + (this.random.nextDouble() - 0.5D) * par2EntityPlayer.width * 2.0D;
				par1World.spawnParticle("portal", var24, var26, var28, var21, var22, var23);
			}

			par1World.playSoundEffect(var1, var2, var3, "mob.ghast.fireball", 0.5F, 1.0F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
			par1World.playSoundAtEntity(par2EntityPlayer, "mob.ghast.fireball", 0.5F, 1.0F / (Item.itemRand.nextFloat() * 0.4F + 0.8F));
			//par1World.playSoundEffect(var1, var2, var3, "mob.endermen.portal", 1.0F, 1.0F);
			//par1World.playSoundAtEntity(par2EntityPlayer, "mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}

	/**
	 * Teleport the enderman
	 */
	/*protected boolean teleportTo(double par1, double par3, double par5)
    {
        double var7 = this.posX;
        double var9 = this.posY;
        double var11 = this.posZ;
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        boolean var13 = false;
        int var14 = MathHelper.floor_double(this.posX);
        int var15 = MathHelper.floor_double(this.posY);
        int var16 = MathHelper.floor_double(this.posZ);
        int var18;

        if (this.worldObj.blockExists(var14, var15, var16))
        {
            boolean var17 = false;

            while (!var17 && var15 > 0)
            {
                var18 = this.worldObj.getBlockId(var14, var15 - 1, var16);

                if (var18 != 0 && Block.blocksList[var18].blockMaterial.blocksMovement())
                {
                    var17 = true;
                }
                else
                {
                    --this.posY;
                    --var15;
                }
            }

            if (var17)
            {
                this.setPosition(this.posX, this.posY, this.posZ);

                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox))
                {
                    var13 = true;
                }
            }
        }

        if (!var13)
        {
            this.setPosition(var7, var9, var11);
            return false;
        }
        else
        {
            short var30 = 128;

            for (var18 = 0; var18 < var30; ++var18)
            {
                double var19 = (double)var18 / ((double)var30 - 1.0D);
                float var21 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float var22 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float var23 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                double var24 = var7 + (this.posX - var7) * var19 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                double var26 = var9 + (this.posY - var9) * var19 + this.rand.nextDouble() * (double)this.height;
                double var28 = var11 + (this.posZ - var11) * var19 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
                this.worldObj.spawnParticle("portal", var24, var26, var28, (double)var21, (double)var22, (double)var23);
            }

            this.worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
            this.worldObj.playSoundAtEntity(this, "mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }*/
}
