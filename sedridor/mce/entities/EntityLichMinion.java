package sedridor.mce.entities;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityLichMinion extends EntitySkeleton
{
	public EntityLich master;

	public EntityLichMinion(World par1World)
	{
		super(par1World);
		this.master = null;
	}

	public EntityLichMinion(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		this.setPosition(par2, par4, par6);
	}

	public EntityLichMinion(World par1World, EntityLich par2EntityLich)
	{
		super(par1World);
		this.master = par2EntityLich;
	}

	/**
	 * Called when the entity is attacked.
	 */
	 @Override
	 public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	 {
		 EntityLiving var3 = this.getAttackTarget();

		 if (super.attackEntityFrom(par1DamageSource, par2))
		 {
			 if (par1DamageSource.getEntity() instanceof EntityLich)
			 {
				 this.setAttackTarget(var3);
				 this.setRevengeTarget(var3);
				 this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 200, 4));
				 this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 200, 1));
			 }

			 return true;
		 }
		 else
		 {
			 return false;
		 }
	 }

	 /**
	  * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	  * use this to react to sunlight and start to burn.
	  */
	 @Override
	 public void onLivingUpdate()
	 {
		 if (this.master == null)
		 {
			 this.findNewMaster();
		 }

		 if (this.master == null || this.master.isDead)
		 {
			 this.health = 0;
		 }

		 super.onLivingUpdate();
	 }

	 private void findNewMaster()
	 {
		 List var1 = this.worldObj.getEntitiesWithinAABB(EntityLich.class, AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32.0D, 16.0D, 32.0D));
		 Iterator var2 = var1.iterator();

		 while (var2.hasNext())
		 {
			 EntityLich var3 = (EntityLich)var2.next();

			 if (!var3.isShadowClone() && var3.wantsNewMinion(this))
			 {
				 this.master = var3;
				 this.master.makeTwilightMagicTrail(this.posX, this.posY + this.getEyeHeight(), this.posZ, this.master.posX, this.master.posY + this.master.getEyeHeight(), this.master.posZ);
				 this.setAttackTarget(this.master.getAttackTarget());
				 break;
			 }
		 }
	 }

	 /**
	  * Called when the mob's health reaches 0.
	  */
	  @Override
	  public void onDeath(DamageSource par1DamageSource)
	  {
		  super.onDeath(par1DamageSource);

		  if (par1DamageSource.getSourceOfDamage() instanceof EntityPlayer)
		  {
			  //((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(AchievementPage.twilightHunter);
		  }
	  }
}
