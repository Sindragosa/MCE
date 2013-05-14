package sedridor.mce.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityLichBolt extends EntityThrowable
{
	private int ticksInAir = 0;

	public EntityLichBolt(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}

	public EntityLichBolt(World par1World, EntityLiving par2EntityLiving)
	{
		super(par1World, par2EntityLiving);
	}

	public EntityLichBolt(World par1World)
	{
		super(par1World);
	}

	protected float func_40077_c()
	{
		return 0.5F;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (!this.worldObj.isRemote && this.ticksInAir > 300)
		{
			this.setDead();
		}
		else
		{
			++this.ticksInAir;
			this.makeTrail();
		}
	}

	public void makeTrail()
	{
		for (int var1 = 0; var1 < 5; ++var1)
		{
			double var2 = this.posX + 0.5D * (this.rand.nextDouble() - this.rand.nextDouble());
			double var4 = this.posY + 0.5D * (this.rand.nextDouble() - this.rand.nextDouble());
			double var6 = this.posZ + 0.5D * (this.rand.nextDouble() - this.rand.nextDouble());
			double var8 = (this.rand.nextFloat() * 0.5F + 0.5F) * 0.17F;
			double var10 = (this.rand.nextFloat() * 0.5F + 0.5F) * 0.8F;
			double var12 = (this.rand.nextFloat() * 0.5F + 0.5F) * 0.69F;
			this.worldObj.spawnParticle("mobSpell", var2, var4, var6, var8, var10, var12);
		}
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public float getCollisionBorderSize()
	{
		return 1.0F;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		this.setBeenAttacked();

		if (par1DamageSource.getEntity() != null)
		{
			Vec3 var3 = par1DamageSource.getEntity().getLookVec();

			if (var3 != null)
			{
				this.setThrowableHeading(var3.xCoord, var3.yCoord, var3.zCoord, 1.5F, 0.1F);
			}

			if (par1DamageSource.getEntity() instanceof EntityLiving)
			{
				//this.thrower = (EntityLiving)par1DamageSource.getEntity();
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity()
	{
		return 0.0F;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		boolean var2 = false;

		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLichBolt)
		{
			var2 = true;
		}

		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLiving)
		{
			if (par1MovingObjectPosition.entityHit instanceof EntityLich)
			{
				EntityLich var3 = (EntityLich)par1MovingObjectPosition.entityHit;

				if (var3.isShadowClone())
				{
					var2 = true;
				}
			}

			if (!var2 && par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), 6))
			{
				;
			}
		}

		if (!var2)
		{
			for (int var4 = 0; var4 < 8; ++var4)
			{
				this.worldObj.spawnParticle("iconcrack_" + Item.enderPearl.itemID, this.posX, this.posY, this.posZ, this.rand.nextGaussian() * 0.05D, this.rand.nextDouble() * 0.2D, this.rand.nextGaussian() * 0.05D);
			}

			if (!this.worldObj.isRemote)
			{
				this.setDead();
			}
		}
	}
}
