package sedridor.mce.entities;

import sedridor.mce.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGoblinShaman extends EntityMobMCE implements IMob
{
	private static final ItemStack defaultHeldItem = new ItemStack(MCE_Items.StaffLightning, 1);
	double targetedEntityX;
	double targetedEntityY;
	double targetedEntityZ;
	public boolean spellChooser;
	private int teleportCounter;
	public int courseChangeCooldown;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private int spellType;
	private Entity targetedEntity;
	private int aggroCooldown;
	public int prevAttackCounter;
	public int attackCounter;

	public EntityGoblinShaman(World par1World)
	{
		super(par1World);
		this.isImmuneToFire = true;
		this.texture = "/MCE/mobs/GoblinShaman.png";
		this.setSize(1.0F, 1.0F);
		this.moveSpeed = 0.0F;
		this.teleportCounter = 0;
		this.spellChooser = false;
	}

	@Override
	public int getMaxHealth()
	{
		return 45;
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	@Override
	public int getAttackStrength(Entity par1Entity)
	{
		return 1;
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn()
	{
		return true;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		this.onEntityUpdate();
		byte var1 = this.dataWatcher.getWatchableObjectByte(16);

		int arrowHitTempCounter = this.getArrowCountInEntity();
		if (arrowHitTempCounter > 0)
		{
			if (this.arrowHitTimer <= 0)
			{
				this.arrowHitTimer = 60;
			}

			--this.arrowHitTimer;

			if (this.arrowHitTimer <= 0)
			{
				this.setArrowCountInEntity(arrowHitTempCounter - 1);
			}
		}

		this.onLivingUpdate();
		double var2 = this.posX - this.prevPosX;
		double var4 = this.posZ - this.prevPosZ;
		float var6 = MathHelper.sqrt_double(var2 * var2 + var4 * var4);
		float var7 = this.renderYawOffset;
		float var8 = 0.0F;
		this.field_70768_au = this.field_70766_av;
		float var9 = 0.0F;

		if (var6 > 0.05F)
		{
			var9 = 1.0F;
			var8 = var6 * 3.0F;
			var7 = (float)Math.atan2(var4, var2) * 180.0F / (float)Math.PI - 90.0F;
		}

		if (this.swingProgress > 0.0F)
		{
			var7 = this.rotationYaw;
		}

		if (!this.onGround)
		{
			var9 = 0.0F;
		}

		this.field_70766_av += (var9 - this.field_70766_av) * 0.3F;
		float var10;

		for (var10 = var7 - this.renderYawOffset; var10 < -180.0F; var10 += 360.0F)
		{
			;
		}

		while (var10 >= 180.0F)
		{
			var10 -= 360.0F;
		}

		this.renderYawOffset += var10 * 0.3F;
		float var11;

		for (var11 = this.rotationYaw - this.renderYawOffset; var11 < -180.0F; var11 += 360.0F)
		{
			;
		}

		while (var11 >= 180.0F)
		{
			var11 -= 360.0F;
		}

		boolean var12 = var11 < -90.0F || var11 >= 90.0F;

		if (var11 < -75.0F)
		{
			var11 = -75.0F;
		}

		if (var11 >= 75.0F)
		{
			var11 = 75.0F;
		}

		this.renderYawOffset = this.rotationYaw - var11;

		if (var11 * var11 > 2500.0F)
		{
			this.renderYawOffset += var11 * 0.2F;
		}

		if (var12)
		{
			var8 *= -1.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F)
		{
			this.prevRenderYawOffset -= 360.0F;
		}

		while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F)
		{
			this.prevRenderYawOffset += 360.0F;
		}

		while (this.rotationPitch - this.prevRotationPitch < -180.0F)
		{
			this.prevRotationPitch -= 360.0F;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		this.field_70764_aw += var8;

		if (!this.worldObj.isRemote && this.worldObj.difficultySetting == 0)
		{
			this.moveSpeed = 0.0F;
		}
		else
		{
			this.moveSpeed = 0.65F;
		}
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2)
	{
		if (par2 < 20.0F)
		{
			this.moveSpeed = 0.65F;
		}
	}

	@Override
	protected void updateEntityActionState()
	{
		if (this.worldObj.difficultySetting != 0)
		{
			if (!this.spellChooser)
			{
				this.spellType = this.rand.nextInt(5);
				this.spellChooser = true;
			}

			if (this.targetedEntity != null && this.targetedEntity.isDead)
			{
				this.targetedEntity = null;
			}

			if (this.targetedEntity == null || this.aggroCooldown-- <= 0)
			{
				this.targetedEntity = this.worldObj.getClosestPlayerToEntity(this, 100.0D);

				if (this.targetedEntity != null)
				{
					this.aggroCooldown = 20;
				}
			}

			double var1 = 16.0D;
			this.targetedEntity = this.worldObj.getClosestPlayerToEntity(this, 100.0D);

			if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < var1 * var1)
			{
				double var3 = this.targetedEntity.posX - this.posX;
				double var5 = this.targetedEntity.boundingBox.minY + this.targetedEntity.height / 2.0F - (this.posY + this.height / 2.0F);
				double var7 = this.targetedEntity.posZ - this.posZ;
				this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(var3, var7)) * 180.0F / (float)Math.PI;

				if (this.canEntityBeSeen(this.targetedEntity))
				{
					double var9 = 8.0D;

					if (this.teleportCounter == 90)
					{
						this.motionY = 0.8D;
						this.fallDistance = -25.0F;
					}

					String var11;
					int var12;
					double var13;
					double var15;
					double var17;

					if (this.teleportCounter == 100)
					{
						var11 = "explode";

						for (var12 = 0; var12 < 7; ++var12)
						{
							var13 = this.rand.nextGaussian() * 0.01D;
							var15 = this.rand.nextGaussian() * 0.01D;
							var17 = this.rand.nextGaussian() * 0.01D;
							this.worldObj.spawnParticle(var11, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var13, var15, var17);
						}

						this.getTeleport(this.targetedEntity);
						this.teleportCounter = 0;
					}

					++this.teleportCounter;

					if (this.spellType == 0 || this.spellType == 2 || this.spellType == 1)
					{
						if (this.attackCounter == 10)
						{
							this.moveSpeed = 0.2F;
						}

						if (this.attackCounter == 20)
						{
							this.moveSpeed = 0.1F;
						}

						if (this.attackCounter == 30)
						{
							var11 = "smoke";

							for (var12 = 0; var12 < 7; ++var12)
							{
								var13 = this.rand.nextGaussian() * 0.01D;
								var15 = this.rand.nextGaussian() * 0.01D;
								var17 = this.rand.nextGaussian() * 0.01D;
								this.worldObj.spawnParticle(var11, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var13, var15, var17);
							}

							this.worldObj.playSoundAtEntity(this, "mob.ghast.charge", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
							this.moveSpeed = 0.0F;
							this.texture = "/MCE/mobs/GoblinShamanB.png";
						}

						++this.attackCounter;

						if (this.attackCounter == 40)
						{
							this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
							EntityLightball var20 = new EntityLightball(this.worldObj, this, 4.0F);
							double var21 = 1.0D;
							Vec3 var14 = this.getLook(1.0F);
							var20.posX = this.posX + var14.xCoord * var21;
							var20.posY = this.posY + this.height / 2.0F + 0.4D;
							var20.posZ = this.posZ + var14.zCoord * var21;
							this.worldObj.spawnEntityInWorld(var20);
							this.attackCounter = -20;
							this.moveSpeed = 0.65F;
							this.texture = "/MCE/mobs/GoblinShaman.png";
							this.spellChooser = false;
						}
					}

					if (this.spellType == 3)
					{
						if (this.attackCounter == 10)
						{
							this.moveSpeed = 0.1F;
						}

						if (this.attackCounter == 15)
						{
							this.targetedEntityX = this.targetedEntity.posX;
							this.targetedEntityY = this.targetedEntity.posY;
							this.targetedEntityZ = this.targetedEntity.posZ;
						}

						if (this.attackCounter == 20)
						{
							this.moveSpeed = 0.0F;
						}

						if (this.attackCounter == 30)
						{
							var11 = "smoke";

							for (var12 = 0; var12 < 7; ++var12)
							{
								var13 = this.rand.nextGaussian() * 0.01D;
								var15 = this.rand.nextGaussian() * 0.01D;
								var17 = this.rand.nextGaussian() * 0.01D;
								this.worldObj.spawnParticle(var11, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var13, var15, var17);
							}

							this.worldObj.playSoundAtEntity(this, "mob.ghast.charge", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
							this.moveSpeed = 0.0F;
							this.texture = "/MCE/mobs/GoblinShamanB.png";
						}

						++this.attackCounter;

						if (this.attackCounter == 40)
						{
							this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

							if (this.targetedEntity != null && !(this.targetedEntity instanceof EntityMob))
							{
								this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.targetedEntityX, this.targetedEntityY, this.targetedEntityZ));
							}

							this.spellChooser = false;
							this.attackCounter = -20;
							this.moveSpeed = 0.65F;
							this.texture = "/MCE/mobs/GoblinShaman.png";
						}
					}

					if (this.spellType == 4)
					{
						if (this.attackCounter == 10)
						{
							this.moveSpeed = 0.1F;
						}

						if (this.attackCounter == 20)
						{
							this.moveSpeed = 0.0F;
						}

						if (this.attackCounter == 30)
						{
							var11 = "smoke";

							for (var12 = 0; var12 < 7; ++var12)
							{
								var13 = this.rand.nextGaussian() * 0.01D;
								var15 = this.rand.nextGaussian() * 0.01D;
								var17 = this.rand.nextGaussian() * 0.01D;
								this.worldObj.spawnParticle(var11, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var13, var15, var17);
							}

							this.worldObj.playSoundAtEntity(this, "mob.ghast.charge", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
							this.moveSpeed = 0.0F;
							this.texture = "/MCE/mobs/GoblinShamanB.png";
						}

						++this.attackCounter;

						if (this.attackCounter == 40)
						{
							this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

							if (this.targetedEntity != null && !(this.targetedEntity instanceof EntityMob))
							{
								this.targetedEntity.motionY = 1.1D;
							}
						}

						if (this.attackCounter == 43)
						{
							this.spellChooser = false;
							this.attackCounter = -20;
							this.moveSpeed = 0.65F;
							this.texture = "/MCE/mobs/GoblinShaman.png";

							if (this.targetedEntity != null && !(this.targetedEntity instanceof EntityMob))
							{
								this.targetedEntity.motionX = this.rand.nextInt(5) - 2 + 0.1D;
								this.targetedEntity.motionZ = this.rand.nextInt(5) - 2 + 0.1D;
								this.targetedEntity.motionY = 0.6D;
							}
						}
					}
				}
				else if (this.attackCounter > 0)
				{
					--this.attackCounter;
				}
			}
			else
			{
				this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI;

				if (this.attackCounter > 0)
				{
					--this.attackCounter;
				}
			}

			if (!this.worldObj.isRemote)
			{
				byte var19 = this.dataWatcher.getWatchableObjectByte(16);
				byte var4 = (byte)(this.attackCounter <= 10 ? 0 : 1);

				if (var19 != var4)
				{
					this.dataWatcher.updateObject(16, Byte.valueOf(var4));
				}
			}
		}

		super.updateEntityActionState();
	}

	public void getTeleport(Entity par1Entity)
	{
		int var2 = this.rand.nextInt(16) - 8;
		int var3 = this.rand.nextInt(16) - 8;
		int var4 = MathHelper.floor_double(par1Entity.posX);
		int var5 = MathHelper.floor_double(par1Entity.posZ);
		int var6 = MathHelper.floor_double(par1Entity.boundingBox.minY);
		this.setLocationAndAngles(var4 + var2 * 1.5F + 0.5F, var6, var5 + var3 * 1.5F + 0.5F, this.rotationYaw, this.rotationPitch);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		//par1NBTTagCompound.setInteger("texture", this.dataWatcher.getWatchableObjectInt(17));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		if (par1NBTTagCompound.hasKey("texture"))
		{
			//this.dataWatcher.updateObject(17, Integer.valueOf(par1NBTTagCompound.getInteger("texture")));
		}
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "goblinsounds.goblinlord";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "goblinsounds.goblinlordhurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "goblinsounds.goblinlorddead";
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
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return Item.lightStoneDust.itemID;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = this.rand.nextInt(2);
		if (var3 == 0)
		{
			//this.dropItem(MCE_Items.powderB.shiftedIndex, 1);
		}

		var3 = this.rand.nextInt(2);
		for (int var4 = 0; var4 < var3; ++var4)
		{
			this.dropItem(Item.lightStoneDust.itemID, 1);
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
			this.dropItem(MCE_Items.StaffLightning.itemID, 1);
		}
		else
		{
			this.dropItem(Item.diamond.itemID, 1);
		}
	}

	/**
	 * Returns the entity's probability to spawn.
	 */
	@Override
	public boolean getSpawnRarity(int par1)
	{
		return this.rand.nextInt(1000) < par1;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere()
	{
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY);
		int var3 = MathHelper.floor_double(this.posZ);
		return this.getSpawnRarity(2) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8;
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
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	@Override
	public ItemStack getHeldItem()
	{
		return defaultHeldItem;
	}
}
