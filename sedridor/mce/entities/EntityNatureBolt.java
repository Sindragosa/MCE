package sedridor.mce.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityNatureBolt extends EntityThrowable
{
	private int ticksInAir = 0;

	public EntityNatureBolt(World par1World, double par2, double par3, double par4)
	{
		super(par1World, par2, par3, par4);
	}

	public EntityNatureBolt(World par1World, EntityLiving par2EntityLiving)
	{
		super(par1World, par2EntityLiving);
	}

	public EntityNatureBolt(World par1World)
	{
		super(par1World);
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

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	@Override
	protected float getGravityVelocity()
	{
		return 0.003F;
	}

	public void makeTrail()
	{
		for (int var1 = 0; var1 < 5; ++var1)
		{
			double var2 = this.posX + 0.5D * (this.rand.nextDouble() - this.rand.nextDouble());
			double var4 = this.posY + 0.5D * (this.rand.nextDouble() - this.rand.nextDouble());
			double var6 = this.posZ + 0.5D * (this.rand.nextDouble() - this.rand.nextDouble());
			this.worldObj.spawnParticle("crit", var2, var4, var6, 0.0D, 0.0D, 0.0D);
		}
	}

	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		int var2;

		if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityLiving)
		{
			if (par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.getThrower()), 2))
			{
				byte var5 = (byte)(this.worldObj.difficultySetting == 0 ? 0 : (this.worldObj.difficultySetting == 2 ? 3 : 7));

				if (var5 > 0)
				{
					((EntityLiving)par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.poison.id, var5 * 20, 0));
				}
			}
		}
		else if (par1MovingObjectPosition != null)
		{
			var2 = MathHelper.floor_double(par1MovingObjectPosition.blockX);
			int var3 = MathHelper.floor_double(par1MovingObjectPosition.blockY);
			int var4 = MathHelper.floor_double(par1MovingObjectPosition.blockZ);

			if (this.worldObj.getBlockMaterial(var2, var3, var4).isSolid())
			{
				this.worldObj.setBlock(var2, var3, var4, Block.leaves.blockID, 2, 3);
			}
		}

		for (var2 = 0; var2 < 8; ++var2)
		{
			this.worldObj.spawnParticle("iconcrack_" + Block.leaves.blockID, this.posX, this.posY, this.posZ, this.rand.nextGaussian() * 0.05D, this.rand.nextDouble() * 0.2D, this.rand.nextGaussian() * 0.05D);
		}

		if (!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}
}
