package sedridor.mce.entities;

import sedridor.mce.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDovahkiin extends EntityMobMCE
{
	/** The ItemStack that any Dovahkiin holds (a sword). */
	private static final ItemStack defaultHeldItem = new ItemStack(MCE_Items.CrystalSword, 1);

	public EntityDovahkiin(World par1World)
	{
		super(par1World);
		this.texture = "/MCE/mobs/Dovahkiin.png";
		this.moveSpeed = 0.26F;
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
		this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
		this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
		this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
	}

	@Override
	public int getMaxHealth()
	{
		return 34;
	}

	/**
	 * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue()
	{
		return 4;
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	@Override
	public int getAttackStrength(Entity par1Entity)
	{
		return 10;
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
		{
			float var1 = this.getBrightness(1.0F);

			if (var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				if (this.rand.nextFloat() * 30.0F >= (var1 - 0.4F) * 2.0F) {
					;
				}
			}
		}

		super.onLivingUpdate();
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.villager.default";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.villager.defaulthurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.villager.defaultdeath";
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId()
	{
		return MCE_Items.CrystalShard.itemID;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
		int var4;

		for (var4 = 0; var4 < var3; ++var4)
		{
			this.dropItem(MCE_Items.CrystalShard.itemID, 1);
		}

		var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEFINED;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		switch (this.rand.nextInt(2))
		{
		case 0:
			this.dropItem(MCE_Items.CrystalBow.itemID, 1);
			break;

		case 1:
			this.dropItem(MCE_Items.CrystalAxe.itemID, 1);
			break;
		}
	}

	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	 @Override
	 public ItemStack getHeldItem()
	 {
		 return defaultHeldItem;
	 }

	 /**
	  * Returns the entity's probability to spawn.
	  */
	 @Override
	 public boolean getSpawnRarity(int par1)
	 {
		 return this.rand.nextInt(100) < par1;
	 }

	 /**
	  * Checks if the entity's current position is a valid location to spawn this entity.
	  */
	 @Override
	 public boolean getCanSpawnHere()
	 {
		 int var1 = MathHelper.floor_double(this.posX);
		 int var2 = MathHelper.floor_double(this.boundingBox.minY);
		 int var3 = MathHelper.floor_double(this.posZ);
		 return this.getSpawnRarity(2) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8;
	 }
}
