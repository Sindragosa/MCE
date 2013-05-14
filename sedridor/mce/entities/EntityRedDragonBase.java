package sedridor.mce.entities;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRedDragonBase extends EntityMobMCE
{
	/** The maximum health of the Entity. */
	protected int maxHealth = 100;

	public EntityRedDragonBase(World par1World)
	{
		super(par1World);
	}
	@Override
	public int getMaxHealth()
	{
		return this.maxHealth;
	}

	//public void onLivingUpdate()
	//{
	//    super.onLivingUpdate();
	//}

	public boolean attackEntityFromPart(EntityRedDragonPart par1EntityRedDragonPart, DamageSource par2DamageSource, int par3)
	{
		return this.attackEntityFrom(par2DamageSource, par3);
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		return false;
	}

	/**
	 * Returns a super of attackEntityFrom in EntityDragonBase, because the normal attackEntityFrom is overriden
	 */
	protected boolean superAttackFrom(DamageSource par1DamageSource, int par2)
	{
		return super.attackEntityFrom(par1DamageSource, par2);
	}
}
