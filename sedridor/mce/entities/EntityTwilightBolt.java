package sedridor.mce.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTwilightBolt extends EntityThrowable
{
	private int ticksInAir = 0;

	public EntityTwilightBolt(World par1World)
	{
		super(par1World);
	}

	public EntityTwilightBolt(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}

	public EntityTwilightBolt(World par1World, EntityLiving par2EntityLiving)
	{
		super(par1World, par2EntityLiving);
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
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity()
	{
		return 0.003F;
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLiving && par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), 6))
		{
			;
		}

		for (int var2 = 0; var2 < 8; ++var2)
		{
			this.worldObj.spawnParticle("iconcrack_" + Item.enderPearl.itemID, this.posX, this.posY, this.posZ, this.rand.nextGaussian() * 0.05D, this.rand.nextDouble() * 0.2D, this.rand.nextGaussian() * 0.05D);
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}
}
