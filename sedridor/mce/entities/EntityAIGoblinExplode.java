package sedridor.mce.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIGoblinExplode extends EntityAIBase
{
	/** The goblin that is exploding. */
	EntityGoblinBomber explodingGoblin;

	/**
	 * The goblin's attack target. This is used for the changing of the goblin's state.
	 */
	EntityLiving goblinAttackTarget;

	public EntityAIGoblinExplode(EntityGoblinBomber par1EntityGoblinBomber)
	{
		this.explodingGoblin = par1EntityGoblinBomber;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		EntityLiving var1 = this.explodingGoblin.getAttackTarget();
		return this.explodingGoblin.getGoblinState() > 0 || var1 != null && this.explodingGoblin.getDistanceSqToEntity(var1) < 9.0D;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		this.explodingGoblin.getNavigator().clearPathEntity();
		this.goblinAttackTarget = this.explodingGoblin.getAttackTarget();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
		this.goblinAttackTarget = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		if (this.goblinAttackTarget == null)
		{
			this.explodingGoblin.setGoblinState(-1);
		}
		else if (this.explodingGoblin.getDistanceSqToEntity(this.goblinAttackTarget) > 49.0D)
		{
			this.explodingGoblin.setGoblinState(-1);
		}
		else if (!this.explodingGoblin.getEntitySenses().canSee(this.goblinAttackTarget))
		{
			this.explodingGoblin.setGoblinState(-1);
		}
		else
		{
			this.explodingGoblin.setGoblinState(1);
		}
	}
}
