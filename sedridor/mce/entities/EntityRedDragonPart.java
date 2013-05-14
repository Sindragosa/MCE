package sedridor.mce.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class EntityRedDragonPart extends Entity
{
	/** The dragon entity this dragon part belongs to */
	public final EntityRedDragonBase entityDragonObj;

	/** The name of the Dragon Part */
	public final String name;

	public EntityRedDragonPart(EntityRedDragonBase par1EntityRedDragonBase, String par2Str, float par3, float par4)
	{
		super(par1EntityRedDragonBase.worldObj);
		this.setSize(par3, par4);
		this.entityDragonObj = par1EntityRedDragonBase;
		this.name = par2Str;
	}

	@Override
	protected void entityInit() {}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith()
	{
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		return this.entityDragonObj.attackEntityFromPart(this, par1DamageSource, par2);
	}

	/**
	 * Returns true if Entity argument is equal to this Entity
	 */
	@Override
	public boolean isEntityEqual(Entity par1Entity)
	{
		return this == par1Entity || this.entityDragonObj == par1Entity;
	}
}
