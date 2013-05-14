package sedridor.mce.entities;

import sedridor.mce.*;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMissile extends Entity implements IProjectile
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	private int inData = 0;
	private boolean inGround = false;

	private double prevPosX; // MCE - testing
	private double prevPosY;
	private double prevPosZ;

	/** 1 if the player can pick up the arrow */
	public int canBePickedUp = 0;

	/** Seems to be some sort of timer for animating an arrow. */
	public int arrowShake = 0;

	/** The owner of this arrow. */
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir = 0;
	private double damage = 4.0D; // MCE - more dmg

	/** The amount of knockback an arrow applies when it hits a mob. */
	private int knockbackStrength;

	public EntityMissile(World par1World)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
	}

	public EntityMissile(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.setSize(0.5F, 0.5F);
		this.setPosition(par2, par4, par6);
		this.yOffset = 0.0F;
	}

	public EntityMissile(World par1World, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving, float par4, float par5)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;

		// MCE - cant pick up missiles
		//if (par2EntityLiving instanceof EntityPlayer)
			//{
		//    this.canBePickedUp = 1;
		//}

		this.posY = par2EntityLiving.posY + par2EntityLiving.getEyeHeight() - 0.10000000149011612D;
		double var6 = par3EntityLiving.posX - par2EntityLiving.posX;
		double var8 = par3EntityLiving.boundingBox.minY + par3EntityLiving.height / 3.0F - this.posY;
		double var10 = par3EntityLiving.posZ - par2EntityLiving.posZ;
		double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);

		if (var12 >= 1.0E-7D)
		{
			float var14 = (float)(Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			float var15 = (float)(-(Math.atan2(var8, var12) * 180.0D / Math.PI));
			double var16 = var6 / var12;
			double var18 = var10 / var12;
			this.setLocationAndAngles(par2EntityLiving.posX + var16, this.posY, par2EntityLiving.posZ + var18, var14, var15);
			this.yOffset = 0.0F;
			float var20 = (float)var12 * 0.2F;
			this.setThrowableHeading(var6, var8 + var20, var10, par4, par5);
		}
	}

	public EntityMissile(World par1World, EntityLiving par2EntityLiving, float par3)
	{
		super(par1World);
		this.renderDistanceWeight = 10.0D;
		this.shootingEntity = par2EntityLiving;

		// MCE - cant pick up missiles
		//if (par2EntityLiving instanceof EntityPlayer)
		//{
		//    this.canBePickedUp = 1;
		//}

		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
		this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3, 1.0F);
	}

	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	/**
	 * Uses the provided coordinates as a heading and determines the velocity from it with the set force and random
	 * variance. Args: x, y, z, force, forceVariation
	 */
	@Override
	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
		par3 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
		par5 += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 */
	@Override
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		this.setPosition(par1, par3, par5);
		this.setRotation(par7, par8);
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	public void setVelocity(double par1, double par3, double par5)
	{
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, var7) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, var1) * 180.0D / Math.PI);
		}

		int var16 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);

		if (var16 > 0)
		{
			Block.blocksList[var16].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB var2 = Block.blocksList[var16].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);

			if (var2 != null && var2.isVecInside(this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)))
			{
				this.inGround = true;
			}
		}

		if (this.arrowShake > 0)
		{
			--this.arrowShake;
		}

		if (this.inGround)
		{
			//this.explode();
			int var18 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
			int var19 = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);

			if (var18 == this.inTile && var19 == this.inData)
			{
				this.explode();
			}
			else
			{
				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			}
		}
		else
		{
			++this.ticksInAir;
			Vec3 var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			Vec3 var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition var4 = this.worldObj.rayTraceBlocks_do_do(var17, var3, false, true);
			var17 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
			var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (var4 != null)
			{
				var3 = this.worldObj.getWorldVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
			}

			Entity var5 = null;
			List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double var7 = 0.0D;
			int var9;
			float var11;

			for (var9 = 0; var9 < var6.size(); ++var9)
			{
				Entity var10 = (Entity)var6.get(var9);

				if (var10.canBeCollidedWith() && (var10 != this.shootingEntity || this.ticksInAir >= 5))
				{
					var11 = 0.3F;
					AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
					MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);

					if (var13 != null)
					{
						double var14 = var17.distanceTo(var13.hitVec);

						if (var14 < var7 || var7 == 0.0D)
						{
							var5 = var10;
							var7 = var14;
						}
					}
				}
			}

			if (var5 != null)
			{
				var4 = new MovingObjectPosition(var5);
			}

			if (var4 != null && var4.entityHit != null && var4.entityHit instanceof EntityPlayer)
			{
				EntityPlayer var21 = (EntityPlayer)var4.entityHit;

				if (var21.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).func_96122_a(var21))
				{
					var4 = null;
				}
			}

			float var20;
			float var27;

			if (var4 != null)
			{
				if (var4.entityHit != null)
				{
					var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int var24 = MathHelper.ceiling_double_int(var20 * this.damage);

					if (this.getIsCritical())
					{
						var24 += this.rand.nextInt(var24 / 2 + 2);
					}

					DamageSource var22 = null;

					if (this.shootingEntity == null)
					{
						var22 = (new EntityDamageSourceIndirect("arrow", this, this)).setProjectile();
					}
					else
					{
						var22 = (new EntityDamageSourceIndirect("arrow", this, this.shootingEntity)).setProjectile();
					}

					var4.entityHit.setFire(5);

					if (var4.entityHit.attackEntityFrom(var22, var24))
					{
						if (var4.entityHit instanceof EntityLiving)
						{
							EntityLiving var25 = (EntityLiving)var4.entityHit;

							if (!this.worldObj.isRemote)
							{
								var25.setArrowCountInEntity(var25.getArrowCountInEntity() + 1);
							}

							if (this.knockbackStrength > 0)
							{
								var27 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (var27 > 0.0F)
								{
									var4.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / var27, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / var27);
								}
							}

							//                            if (this.shootingEntity != null)
							//                            {
							//                                EnchantmentThorns.func_92096_a(this.shootingEntity, var25, this.rand);
							//                            }

							if (this.shootingEntity != null && var4.entityHit != this.shootingEntity && var4.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
							{
								((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
							}
						}

						this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
						//this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
						this.explode();
					}
					else
					{
						//this.motionX *= -0.10000000149011612D;
						//this.motionY *= -0.10000000149011612D;
						//this.motionZ *= -0.10000000149011612D;
						//this.rotationYaw += 180.0F;
						//this.prevRotationYaw += 180.0F;
						this.ticksInAir = 0;
					}
				}
				else
				{
					this.xTile = var4.blockX;
					this.yTile = var4.blockY;
					this.zTile = var4.blockZ;
					this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
					this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
					this.motionX = ((float)(var4.hitVec.xCoord - this.posX));
					this.motionY = ((float)(var4.hitVec.yCoord - this.posY));
					this.motionZ = ((float)(var4.hitVec.zCoord - this.posZ));
					var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / var20 * 0.05000000074505806D;
					this.posY -= this.motionY / var20 * 0.05000000074505806D;
					this.posZ -= this.motionZ / var20 * 0.05000000074505806D;
					this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					//this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.inGround = true;
					this.arrowShake = 7;
					this.setIsCritical(false);

					if (this.inTile != 0)
					{
						Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
					}
				}
			}

			// MCE - testing
			/*if (!this.worldObj.isRemote && this.ticksInAir % 2 == 0)
            {
        		try {
        			double var41 = this.posX - this.prevPosX;
        			double var42 = this.posY - this.prevPosY;
        			double var43 = this.posZ - this.prevPosZ;
        			double var44 = this.shootingEntity.posX - this.posX;
        			double var45 = this.shootingEntity.posY - this.posY;
        			double var46 = this.shootingEntity.posZ - this.posZ;
        			System.out.println("MC EntityMissile... ticksInAir " + this.ticksInAir + ", velocity " + (MathHelper.sqrt_double(var41 * var41 + var42 * var42 + var43 * var43) * 10 * 3600) + ", distance " + (MathHelper.sqrt_double(var44 * var44 + var45 * var45 + var46 * var46)));
        			this.prevPosX = this.posX;
        			this.prevPosY = this.posY;
        			this.prevPosZ = this.posZ;
        		} catch (Throwable e) {
        		}
            }*/

			if (this.ticksInAir >= 0)
			{
				double var1 = 0.625D;
				double var2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ + this.motionY * this.motionY);
				this.worldObj.spawnParticle("smoke", this.posX - this.motionX / var2 * var1, this.posY - this.motionY / var2 * var1, this.posZ - this.motionZ / var2 * var1, 0.0D, 0.0D, 0.0D);
				for (int var21 = 0; var21 < 4; ++var21)
				{
					this.worldObj.spawnParticle("missile", this.posX - this.motionX / var2 * var1, this.posY - this.motionY / var2 * var1, this.posZ - this.motionZ / var2 * var1, -this.motionX, -this.motionY, -this.motionZ);
					this.worldObj.spawnParticle("missile2", this.posX - this.motionX / var2 * var1, this.posY - this.motionY / var2 * var1, this.posZ - this.motionZ / var2 * var1, -this.motionX, -this.motionY, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for (this.rotationPitch = (float)(Math.atan2(this.motionY, var20) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			{
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float var23 = 0.9999F;
			var11 = 0.0005F;

			if (this.isInWater())
			{
				for (int var26 = 0; var26 < 4; ++var26)
				{
					var27 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * var27, this.posY - this.motionY * var27, this.posZ - this.motionZ * var27, this.motionX, this.motionY, this.motionZ);
				}

				var23 = 0.8F;
			}

			//this.motionX *= (double)var23;
			//this.motionY *= (double)var23;
			//this.motionZ *= (double)var23;
			//this.motionY -= (double)var11;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short)this.xTile);
		par1NBTTagCompound.setShort("yTile", (short)this.yTile);
		par1NBTTagCompound.setShort("zTile", (short)this.zTile);
		par1NBTTagCompound.setByte("inTile", (byte)this.inTile);
		par1NBTTagCompound.setByte("inData", (byte)this.inData);
		par1NBTTagCompound.setByte("shake", (byte)this.arrowShake);
		par1NBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
		//par1NBTTagCompound.setByte("pickup", (byte)this.canBePickedUp);
		par1NBTTagCompound.setDouble("damage", this.damage);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		this.xTile = par1NBTTagCompound.getShort("xTile");
		this.yTile = par1NBTTagCompound.getShort("yTile");
		this.zTile = par1NBTTagCompound.getShort("zTile");
		this.inTile = par1NBTTagCompound.getByte("inTile") & 255;
		this.inData = par1NBTTagCompound.getByte("inData") & 255;
		this.arrowShake = par1NBTTagCompound.getByte("shake") & 255;
		this.inGround = par1NBTTagCompound.getByte("inGround") == 1;

		if (par1NBTTagCompound.hasKey("damage"))
		{
			this.damage = par1NBTTagCompound.getDouble("damage");
		}

		/*if (par1NBTTagCompound.hasKey("pickup"))
        {
            this.canBePickedUp = par1NBTTagCompound.getByte("pickup");
        }
        else if (par1NBTTagCompound.hasKey("player"))
        {
            this.canBePickedUp = par1NBTTagCompound.getBoolean("player") ? 1 : 0;
        }*/
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
		if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
		{
			this.setDead();
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	public void explode()
	{
		if (!this.worldObj.isRemote)
		{
			MCEnhancements.createExplosion(this, this.worldObj, this.posX, this.posY, this.posZ, 2.0F, 4.0F, true, false);
			this.setDead();
		}
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public float getShadowSize()
	{
		return 0.0F;
	}

	public void setDamage(double par1)
	{
		this.damage = par1;
	}

	public double getDamage()
	{
		return this.damage;
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int par1)
	{
		this.knockbackStrength = par1;
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	@Override
	public boolean canAttackWithItem()
	{
		return false;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public void setIsCritical(boolean par1)
	{
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);

		if (par1)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 1)));
		}
		else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -2)));
		}
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public boolean getIsCritical()
	{
		byte var1 = this.dataWatcher.getWatchableObjectByte(16);
		return (var1 & 1) != 0;
	}
}
