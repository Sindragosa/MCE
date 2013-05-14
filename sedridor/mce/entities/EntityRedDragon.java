package sedridor.mce.entities;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRedDragon extends EntityRedDragonBase
{
	public double targetX;
	public double targetY;
	public double targetZ;

	/**
	 * Ring buffer array for the last 64 Y-positions and yaw rotations. Used to calculate offsets for the animations.
	 */
	public double[][] ringBuffer = new double[64][3];

	/**
	 * Index into the ring buffer. Incremented once per tick and restarts at 0 once it reaches the end of the buffer.
	 */
	public int ringBufferIndex = -1;

	/** An array containing all body parts of this dragon */
	public EntityRedDragonPart[] dragonPartArray;

	/** The head bounding box of a dragon */
	public EntityRedDragonPart dragonPartHead;

	/** The body bounding box of a dragon */
	public EntityRedDragonPart dragonPartBody;
	public EntityRedDragonPart dragonPartTail1;
	public EntityRedDragonPart dragonPartTail2;
	public EntityRedDragonPart dragonPartTail3;
	public EntityRedDragonPart dragonPartWing1;
	public EntityRedDragonPart dragonPartWing2;

	/** Animation time at previous tick. */
	public float prevAnimTime = 0.0F;

	/**
	 * Animation time, used to control the speed of the animation cycles (wings flapping, jaw opening, etc.)
	 */
	public float animTime = 0.0F;

	/** Force selecting a new flight target at next tick if set to true. */
	public boolean forceNewTarget = false;

	/**
	 * Activated if the dragon is flying though obsidian, white stone or bedrock. Slows movement and animation speed.
	 */
	public boolean slowed = false;
	private Entity target;
	public int deathTicks = 0;

	public EntityRedDragon(World par1World)
	{
		super(par1World);
		this.dragonPartArray = new EntityRedDragonPart[] {this.dragonPartHead = new EntityRedDragonPart(this, "head", 6.0F, 6.0F), this.dragonPartBody = new EntityRedDragonPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new EntityRedDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new EntityRedDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new EntityRedDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new EntityRedDragonPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new EntityRedDragonPart(this, "wing", 4.0F, 4.0F)};
		this.setEntityHealth(this.getMaxHealth());
		this.texture = "/MCE/mobs/red.png";
		this.setSize(16.0F, 8.0F);
		this.isImmuneToFire = true;
		this.targetY = 100.0D;
		this.ignoreFrustumCheck = true;
		this.moveSpeed = 0.75F;
		//this.tasks.addTask(0, new EntityAIFlying(this));
		//this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
		//this.tasks.addTask(2, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
		//this.tasks.addTask(3, new EntityAIWander(this, this.moveSpeed));
		//this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		//this.tasks.addTask(7, new EntityAILookIdle(this));
		//this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		//this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
	}

	@Override
	public int getMaxHealth()
	{
		return 20;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, new Integer(this.getMaxHealth()));
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	//public boolean isAIEnabled()
	//{
	//    return true;
	//}

	/**
	 * Returns a double[3] array with movement offsets, used to calculate trailing tail/neck positions. [0] = yaw
	 * offset, [1] = y offset, [2] = unused, always 0. Parameters: buffer index offset, partial ticks.
	 */
	public double[] getMovementOffsets(int par1, float par2)
	{
		if (this.health <= 0)
		{
			par2 = 0.0F;
		}

		par2 = 1.0F - par2;
		int var3 = this.ringBufferIndex - par1 * 1 & 63;
		int var4 = this.ringBufferIndex - par1 * 1 - 1 & 63;
		double[] var5 = new double[3];
		double var6 = this.ringBuffer[var3][0];
		double var8 = MathHelper.wrapAngleTo180_double(this.ringBuffer[var4][0] - var6);
		var5[0] = var6 + var8 * par2;
		var6 = this.ringBuffer[var3][1];
		var8 = this.ringBuffer[var4][1] - var6;
		var5[1] = var6 + var8 * par2;
		var5[2] = this.ringBuffer[var3][2] + (this.ringBuffer[var4][2] - this.ringBuffer[var3][2]) * par2;
		return var5;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		float var1;
		float var2;

		if (!this.worldObj.isRemote)
		{
			this.dataWatcher.updateObject(16, Integer.valueOf(this.health));
		}
		else
		{
			var1 = MathHelper.cos(this.animTime * (float)Math.PI * 2.0F);
			var2 = MathHelper.cos(this.prevAnimTime * (float)Math.PI * 2.0F);

			if (var2 <= -0.3F && var1 >= -0.3F)
			{
				this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
			}
		}

		this.prevAnimTime = this.animTime;
		float var3;

		if (this.health <= 0)
		{
			var1 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			var2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
			var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			this.worldObj.spawnParticle("largeexplode", this.posX + var1, this.posY + 2.0D + var2, this.posZ + var3, 0.0D, 0.0D, 0.0D);
		}
		else
		{
			var1 = 0.2F / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
			var1 *= (float)Math.pow(2.0D, this.motionY);

			this.animTime += 0.05F;

			this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);

			if (this.ringBufferIndex < 0)
			{
				for (int var25 = 0; var25 < this.ringBuffer.length; ++var25)
				{
					this.ringBuffer[var25][0] = this.rotationYaw;
					this.ringBuffer[var25][1] = this.posY;
				}
			}

			if (++this.ringBufferIndex == this.ringBuffer.length)
			{
				this.ringBufferIndex = 0;
			}

			this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
			this.ringBuffer[this.ringBufferIndex][1] = this.posY;
			double var4;
			double var6;
			double var8;
			double var26;
			float var33;

			if (this.worldObj.isRemote /*|| this.health > 0*/)
			{
				if (this.newPosRotationIncrements > 0)
				{
					var26 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
					var4 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
					var6 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
					var8 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
					this.rotationYaw = (float)(this.rotationYaw + var8 / this.newPosRotationIncrements);
					this.rotationPitch = (float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
					--this.newPosRotationIncrements;
					this.setPosition(var26, var4, var6);
					this.setRotation(this.rotationYaw, this.rotationPitch);
				}
			}
			else
			{
				var26 = this.targetX - this.posX;
				var4 = this.targetY - this.posY;
				var6 = this.targetZ - this.posZ;
				var8 = var26 * var26 + var4 * var4 + var6 * var6;

				if (this.target != null)
				{
					this.targetX = this.target.posX;
					this.targetZ = this.target.posZ;
					double var10 = this.targetX - this.posX;
					double var12 = this.targetZ - this.posZ;
					double var14 = Math.sqrt(var10 * var10 + var12 * var12);
					double var16 = 0.4D + var14 / 80.0D - 1.0D;

					if (var16 > 10.0D)
					{
						var16 = 10.0D;
					}

					this.targetY = this.target.boundingBox.minY + var16;
				}
				else
				{
					this.targetX += this.rand.nextGaussian() * 2.0D;
					this.targetZ += this.rand.nextGaussian() * 2.0D;
				}

				if (this.forceNewTarget || var8 < 100.0D || var8 > 22500.0D || this.isCollidedHorizontally || this.isCollidedVertically)
				{
					this.setNewTarget();
				}

				var4 /= MathHelper.sqrt_double(var26 * var26 + var6 * var6);
				var33 = 0.6F;

				if (var4 < (-var33))
				{
					var4 = (-var33);
				}

				if (var4 > var33)
				{
					var4 = var33;
				}

				this.motionY += var4 * 0.1D;
				this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
				double var11 = 180.0D - Math.atan2(var26, var6) * 180.0D / Math.PI;
				double var13 = MathHelper.wrapAngleTo180_double(var11 - this.rotationYaw);

				if (var13 > 50.0D)
				{
					var13 = 50.0D;
				}

				if (var13 < -50.0D)
				{
					var13 = -50.0D;
				}

				Vec3 var15 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ).normalize();
				Vec3 var40 = this.worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F), this.motionY, (-MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F))).normalize();
				float var17 = (float)(var40.dotProduct(var15) + 0.5D) / 1.5F;

				if (var17 < 0.0F)
				{
					var17 = 0.0F;
				}

				this.randomYawVelocity *= 0.8F;
				float var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0F + 1.0F;
				double var19 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D + 1.0D;

				if (var19 > 40.0D)
				{
					var19 = 40.0D;
				}

				this.randomYawVelocity = (float)(this.randomYawVelocity + var13 * (0.7D / var19 / var18));
				this.rotationYaw += this.randomYawVelocity * 0.1F;
				float var21 = (float)(2.0D / (var19 + 1.0D));
				float var22 = 0.06F;
				this.moveFlying(0.0F, -1.0F, var22 * (var17 * var21 + (1.0F - var21)));

				if (this.slowed)
				{
					this.moveEntity(this.motionX * 0.8D, this.motionY * 0.8D, this.motionZ * 0.8D);
				}
				else
				{
					this.moveEntity(this.motionX * 0.25D, this.motionY * 0.25D, this.motionZ * 0.25D);
				}

				Vec3 var23 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.motionX, this.motionY, this.motionZ).normalize();
				float var24 = (float)(var23.dotProduct(var40) + 1.0D) / 2.0F;
				var24 = 0.8F + 0.15F * var24;
				this.motionX *= var24;
				this.motionZ *= var24;
				this.motionY *= 0.91D;
			}

			this.renderYawOffset = this.rotationYaw;
			this.dragonPartHead.width = this.dragonPartHead.height = 3.0F;
			this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F;
			this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F;
			this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F;
			this.dragonPartBody.height = 3.0F;
			this.dragonPartBody.width = 5.0F;
			this.dragonPartWing1.height = 2.0F;
			this.dragonPartWing1.width = 4.0F;
			this.dragonPartWing2.height = 3.0F;
			this.dragonPartWing2.width = 4.0F;
			var2 = (float)(this.getMovementOffsets(5, 1.0F)[1] - this.getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * (float)Math.PI;
			var3 = MathHelper.cos(var2);
			float var28 = -MathHelper.sin(var2);
			float var5 = this.rotationYaw * (float)Math.PI / 180.0F;
			float var27 = MathHelper.sin(var5);
			float var7 = MathHelper.cos(var5);
			this.dragonPartBody.onUpdate();
			this.dragonPartBody.setLocationAndAngles(this.posX + var27 * 0.5F, this.posY, this.posZ - var7 * 0.5F, 0.0F, 0.0F);
			this.dragonPartWing1.onUpdate();
			this.dragonPartWing1.setLocationAndAngles(this.posX + var7 * 4.5F, this.posY + 2.0D, this.posZ + var27 * 4.5F, 0.0F, 0.0F);
			this.dragonPartWing2.onUpdate();
			this.dragonPartWing2.setLocationAndAngles(this.posX - var7 * 4.5F, this.posY + 2.0D, this.posZ - var27 * 4.5F, 0.0F, 0.0F);

			if (!this.worldObj.isRemote && this.hurtTime == 0)
			{
				//this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.boundingBox.expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
				//this.collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.boundingBox.expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
				//this.attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.boundingBox.expand(1.0D, 1.0D, 1.0D)));
			}

			double[] var29 = this.getMovementOffsets(5, 1.0F);
			double[] var9 = this.getMovementOffsets(0, 1.0F);
			var33 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F - this.randomYawVelocity * 0.01F);
			float var32 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F - this.randomYawVelocity * 0.01F);
			this.dragonPartHead.onUpdate();
			this.dragonPartHead.setLocationAndAngles(this.posX + var33 * 5.5F * var3, this.posY + (var9[1] - var29[1]) * 1.0D + var28 * 5.5F, this.posZ - var32 * 5.5F * var3, 0.0F, 0.0F);

			for (int var30 = 0; var30 < 3; ++var30)
			{
				EntityRedDragonPart var31 = null;

				if (var30 == 0)
				{
					var31 = this.dragonPartTail1;
				}

				if (var30 == 1)
				{
					var31 = this.dragonPartTail2;
				}

				if (var30 == 2)
				{
					var31 = this.dragonPartTail3;
				}

				double[] var35 = this.getMovementOffsets(12 + var30 * 2, 1.0F);
				float var34 = this.rotationYaw * (float)Math.PI / 180.0F + this.simplifyAngle(var35[0] - var29[0]) * (float)Math.PI / 180.0F * 1.0F;
				float var38 = MathHelper.sin(var34);
				float var37 = MathHelper.cos(var34);
				float var36 = 1.5F;
				float var40 = (var30 + 1) * 2.0F;
				var31.onUpdate();
				var31.setLocationAndAngles(this.posX - (var27 * var36 + var38 * var40) * var3, this.posY + (var35[1] - var29[1]) * 1.0D - (var40 + var36) * var28 + 1.5D, this.posZ + (var7 * var36 + var37 * var40) * var3, 0.0F, 0.0F);
			}
			//super.onLivingUpdate();
		}
	}

	/**
	 * Pushes all entities inside the list away from the enderdragon.
	 */
	private void collideWithEntities(List par1List)
	{
		double var2 = (this.dragonPartBody.boundingBox.minX + this.dragonPartBody.boundingBox.maxX) / 2.0D;
		double var4 = (this.dragonPartBody.boundingBox.minZ + this.dragonPartBody.boundingBox.maxZ) / 2.0D;
		Iterator var6 = par1List.iterator();

		while (var6.hasNext())
		{
			Entity var7 = (Entity)var6.next();

			if (var7 instanceof EntityLiving)
			{
				double var8 = var7.posX - var2;
				double var10 = var7.posZ - var4;
				double var12 = var8 * var8 + var10 * var10;
				var7.addVelocity(var8 / var12 * 4.0D, 0.20000000298023224D, var10 / var12 * 4.0D);
			}
		}
	}

	/**
	 * Attacks all entities inside this list, dealing 5 hearts of damage.
	 */
	private void attackEntitiesInList(List par1List)
	{
		Iterator var2 = par1List.iterator();

		while (var2.hasNext())
		{
			Entity var3 = (Entity)var2.next();

			if (var3 instanceof EntityLiving)
			{
				var3.attackEntityFrom(DamageSource.causeMobDamage(this), 10);
			}
		}
	}

	/**
	 * Sets a new target for the flight AI. It can be a random coordinate or a nearby player.
	 */
	private void setNewTarget()
	{
		this.forceNewTarget = false;

		/*if (this.rand.nextInt(2) == 0 && !this.worldObj.playerEntities.isEmpty())
        {
            this.target = (Entity)this.worldObj.playerEntities.get(this.rand.nextInt(this.worldObj.playerEntities.size()));
            System.out.println("setNewTarget " + this.target.entityId + " " + this.target.getEntityName());
        }
        else
        {*/
		boolean var1 = false;

		do
		{
			this.targetX = 0.0D;
			this.targetY = 70.0F + this.rand.nextFloat() * 50.0F;
			this.targetZ = 0.0D;
			this.targetX += this.rand.nextFloat() * 120.0F - 60.0F;
			this.targetZ += this.rand.nextFloat() * 120.0F - 60.0F;
			double var2 = this.posX - this.targetX;
			double var4 = this.posY - this.targetY;
			double var6 = this.posZ - this.targetZ;
			var1 = var2 * var2 + var4 * var4 + var6 * var6 > 100.0D;
		}
		while (!var1);

		this.target = null;
		//}
}

	/**
	 * Simplifies the value of a number by adding/subtracting 180 to the point that the number is between -180 and 180.
	 */
	private float simplifyAngle(double par1)
	{
		return (float)MathHelper.wrapAngleTo180_double(par1);
	}

	@Override
	public boolean attackEntityFromPart(EntityRedDragonPart par1EntityRedDragonPart, DamageSource par2DamageSource, int par3)
	{
		if (par1EntityRedDragonPart != this.dragonPartHead)
		{
			par3 = par3 / 4 + 1;
		}

		float var4 = this.rotationYaw * (float)Math.PI / 180.0F;
		float var5 = MathHelper.sin(var4);
		float var6 = MathHelper.cos(var4);
		this.targetX = this.posX + var5 * 5.0F + (this.rand.nextFloat() - 0.5F) * 2.0F;
		this.targetY = this.posY + this.rand.nextFloat() * 3.0F + 1.0D;
		this.targetZ = this.posZ - var6 * 5.0F + (this.rand.nextFloat() - 0.5F) * 2.0F;
		this.target = null;

		if (par2DamageSource.getEntity() instanceof EntityPlayer || par2DamageSource.isExplosion())
		{
			this.superAttackFrom(par2DamageSource, par3);
		}

		return true;
	}

	/**
	 * handles entity death timer, experience orb and particle creation
	 */
	 @Override
	 protected void onDeathUpdate()
	 {
		 ++this.deathTicks;

		 if (this.deathTicks >= 180 && this.deathTicks <= 200)
		 {
			 float var1 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			 float var2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
			 float var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			 this.worldObj.spawnParticle("hugeexplosion", this.posX + var1, this.posY + 2.0D + var2, this.posZ + var3, 0.0D, 0.0D, 0.0D);
		 }

		 int var4;
		 int var5;

		 if (!this.worldObj.isRemote && this.deathTicks > 150 && this.deathTicks % 5 == 0)
		 {
			 var4 = 100;

			 while (var4 > 0)
			 {
				 var5 = EntityXPOrb.getXPSplit(var4);
				 var4 -= var5;
				 this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
			 }
		 }

		 this.moveEntity(0.0D, 0.1D, 0.0D);
		 this.renderYawOffset = this.rotationYaw += 20.0F;

		 if (this.deathTicks == 200 && !this.worldObj.isRemote)
		 {
			 var4 = 200;

			 while (var4 > 0)
			 {
				 var5 = EntityXPOrb.getXPSplit(var4);
				 var4 -= var5;
				 this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
			 }

			 this.setDead();
		 }
	 }

	 /**
	  * Makes the entity despawn if requirements are reached
	  */
	 @Override
	 protected void despawnEntity() {}

	 /**
	  * Return the Entity parts making up this Entity (currently only for dragons)
	  */
	 @Override
	 public Entity[] getParts()
	 {
		 return this.dragonPartArray;
	 }

	 /**
	  * Returns true if other Entities should be prevented from moving through this Entity.
	  */
	 @Override
	 public boolean canBeCollidedWith()
	 {
		 return false;
	 }

	 /**
	  * Returns the health points of the dragon.
	  */
	 public int getDragonHealth()
	 {
		 return this.dataWatcher.getWatchableObjectInt(16);
	 }
}
