package sedridor.mce.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public abstract class EntityMobMCE extends EntityMob
{
	public EntityMobMCE(World par1World)
	{
		super(par1World);
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		int var1 = this.dataWatcher.getWatchableObjectInt(14);
		if (var1 > 0)
		{
			//System.out.println("onLivingUpdate... powered: " + var1 + " " + this.entityId);
			--var1;
			this.dataWatcher.updateObject(14, Integer.valueOf(var1));
		}

		super.onLivingUpdate();
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(14, new Integer(0));
	}

	/**
	 * Returns true if the entity is powered.
	 */
	//public int getTicksPowered()
	//{
	//    return this.dataWatcher.getWatchableObjectInt(14);
	//}

	/**
	 * Returns true if the entity is powered.
	 */
	public boolean getPowered()
	{
		//System.out.println("getPowered... powered: " + (this.dataWatcher.getWatchableObjectInt(14) > 0) + " " + this.entityId);
		return this.dataWatcher.getWatchableObjectInt(14) > 0;
	}

	/**
	 * Returns true if the entity is powered.
	 */
	public void onImpact(Entity par1Entity)
	{
		if (!this.worldObj.isRemote)
		{
			this.dataWatcher.updateObject(14, Integer.valueOf(40));
		}
	}

	/**
	 * Returns the entity's probability to spawn.
	 */
	public boolean getSpawnRarity(int par1)
	{
		return this.rand.nextInt(100) < par1;
	}
}
