package sedridor.mce.entities;

import sedridor.mce.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGoblinNinja extends EntityMobMCE
{
	private static final ItemStack defaultHeldItem = new ItemStack(MCE_Items.KatanaSword, 1);
	int hide;
	boolean hidden = false;

	public EntityGoblinNinja(World par1World)
	{
		super(par1World);
		this.texture = "/MCE/mobs/GoblinNinja.png";
		this.setSize(1.0F, 1.0F);
	}

	@Override
	public int getMaxHealth()
	{
		return 18;
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	@Override
	public int getAttackStrength(Entity par1Entity)
	{
		return 3;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2)
	{
		double var3;
		double var5;

		if (par2 < 2.5F)
		{
			var3 = par1Entity.posX - this.posX;

			for (var5 = par1Entity.posZ - this.posZ; var3 * var3 + var5 * var5 < 1.0E-4D; var5 = (Math.random() - Math.random()) * 0.01D)
			{
				var3 = (Math.random() - Math.random()) * 0.01D;
			}

			this.knockBack(par1Entity, 5, var3, var5);
		}

		if (par2 < 12.0F && par2 > 4.0F)
		{
			var3 = par1Entity.posX - this.posX;
			var5 = par1Entity.posZ - this.posZ;

			if (this.attackTime == 0)
			{
				EntityShuriken var7 = new EntityShuriken(this.worldObj, this, 1.0F);
				double var8 = par1Entity.posY + par1Entity.getEyeHeight() - 0.7D - var7.posY;
				float var10 = MathHelper.sqrt_double(var3 * var3 + var5 * var5) * 0.2F;
				this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
				this.worldObj.spawnEntityInWorld(var7);
				var7.setThrowableHeading(var3, var8 + var10, var5, 1.6F, 12.0F);
				this.attackTime = 80;
			}

			this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0D / Math.PI) - 90.0F;
			this.hasAttacked = true;
		}

		if (par2 < 4.0F && this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
		{
			this.attackTime = 20;
			this.attackEntityAsMob(par1Entity);
		}
	}

	private void getTeleport()
	{
		int var1 = this.rand.nextInt(16) + 10;
		int var2 = this.rand.nextInt(16) + 10;
		int var3 = MathHelper.floor_double(this.posX);
		int var4 = MathHelper.floor_double(this.posZ);
		int var5 = MathHelper.floor_double(this.boundingBox.minY);
		this.setLocationAndAngles(var3 + var1 + 0.5F, var5, var4 + var2 + 0.5F, this.rotationYaw, this.rotationPitch);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1)
	{
		super.writeEntityToNBT(par1);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1)
	{
		super.readEntityFromNBT(par1);
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return null;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return null;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return null;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return MCE_Items.NinjaStar.itemID;
	}

	/**
	 * Drop 0-2 items of this living's type
	 * recentlyHit > 0, lootingModifier
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = this.rand.nextInt(5) + 4;

		for (int var4 = 0; var4 < var3; ++var4)
		{
			this.dropItem(MCE_Items.NinjaStar.itemID, 1);
		}
	}

	/**
	 * Drop rare items of this living's type
	 * superRare = 1, rare = 0
	 */
	@Override
	protected void dropRareDrop(int par1)
	{
		if (par1 > 0)
		{
			this.dropItem(MCE_Items.KatanaSword.itemID, 1);
		}
		else
		{
			this.dropItem(MCE_Items.ObsidiumDagger.itemID, 1);
		}
	}

	/**
	 * Returns the entity's probability to spawn.
	 */
	@Override
	public boolean getSpawnRarity(int par1)
	{
		return this.rand.nextInt(100) < par1;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere()
	{
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY) - 1;
		int var3 = MathHelper.floor_double(this.posZ);
		return this.getSpawnRarity(2) && this.worldObj.getBlockId(var1, var2, var3) != Block.grass.blockID && this.worldObj.getBlockId(var1, var2, var3) != Block.sand.blockID && this.worldObj.getBlockId(var1, var2, var3) != Block.gravel.blockID && (this.worldObj.getBlockId(var1, var2, var3) != Block.dirt.blockID || this.rand.nextInt(20) != 0) ? false : super.getCanSpawnHere();
	}
}
