package sedridor.mce.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGoblin extends EntityMobMCE
{
	/** The ItemStack that any Goblin holds (a runite sword). */
	private static final ItemStack defaultHeldItem = new ItemStack(Item.axeWood, 1);

	public EntityGoblin(World par1World)
	{
		super(par1World);
		this.texture = this.getModelTexture();
		this.setSize(0.9F, 0.8F);
		this.moveSpeed = 0.4F;
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
		this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
		this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
		this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(17, Integer.valueOf(0));
	}

	@Override
	public int getMaxHealth()
	{
		return 14;
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	 @Override
	 public int getAttackStrength(Entity par1Entity)
	{
		 return 2;
	}

	 private String getModelTexture()
	 {
		 int var1 = this.rand.nextInt(2);
		 this.dataWatcher.updateObject(17, Integer.valueOf(var1));
		 return this.getTexture();
	 }

	 @Override
	 public String getTexture()
	 {
		 int var1 = this.dataWatcher.getWatchableObjectInt(17);
		 if (var1 == 0)
		 {
			 return "/MCE/mobs/Goblin2.png";
		 }
		 else
		 {
			 return "/MCE/mobs/Goblin.png";
		 }
	 }

	 /**
	  * (abstract) Protected helper method to write subclass entity data to NBT.
	  */
	 @Override
	 public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	 {
		 super.writeEntityToNBT(par1NBTTagCompound);
		 par1NBTTagCompound.setInteger("texture", this.dataWatcher.getWatchableObjectInt(17));
	 }

	 /**
	  * (abstract) Protected helper method to read subclass entity data from NBT.
	  */
	 @Override
	 public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	 {
		 super.readEntityFromNBT(par1NBTTagCompound);
		 if (par1NBTTagCompound.hasKey("texture"))
		 {
			 this.dataWatcher.updateObject(17, Integer.valueOf(par1NBTTagCompound.getInteger("texture")));
		 }
	 }

	 /**
	  * Returns the sound this mob makes while it's alive.
	  */
	 @Override
	 protected String getLivingSound()
	 {
		 return "goblinsounds.goblin";
	 }

	 /**
	  * Returns the sound this mob makes when it is hurt.
	  */
	 @Override
	 protected String getHurtSound()
	 {
		 return "goblinsounds.goblinhurt";
	 }

	 /**
	  * Returns the sound this mob makes on death.
	  */
	 @Override
	 protected String getDeathSound()
	 {
		 return "goblinsounds.goblindead";
	 }

	 /**
	  * Returns the volume for the sounds this mob makes.
	  */
	 @Override
	 protected float getSoundVolume()
	 {
		 return 0.4F;
	 }

	 /**
	  * Returns the item ID for the item the mob drops on death.
	  */
	 @Override
	 protected int getDropItemId()
	 {
		 return Item.leather.itemID;
	 }

	 /**
	  * Drop 0-2 items of this living's type
	  */
	 @Override
	 protected void dropFewItems(boolean par1, int par2)
	 {
		 int var3 = this.rand.nextInt(2);
		 for (int var4 = 0; var4 < var3; ++var4)
		 {
			 this.dropItem(Item.axeWood.itemID, 1);
		 }

		 var3 = this.rand.nextInt(2);
		 for (int var4 = 0; var4 < var3; ++var4)
		 {
			 this.dropItem(Item.leather.itemID, 1);
		 }
	 }

	 /**
	  * Drop rare items of this living's type
	  * superRare = 1, rare = 0
	  */
	 @Override
	 protected void dropRareDrop(int par1)
	 {
		 if (par1 > 0)
		 {
			 this.entityDropItem(new ItemStack(Item.potion.itemID, 1, 16274), 0.0F);
		 }
		 else
		 {
			 this.dropItem(Item.emerald.itemID, 1);
		 }
	 }

	 /**
	  * Will return how many at most can spawn in a chunk at once.
	  */
	 @Override
	 public int getMaxSpawnedInChunk()
	 {
		 return 4;
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
		 return this.getSpawnRarity(10) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8;
	 }

	 /**
	  * Returns the item that this EntityLiving is holding, if any.
	  */
	 @Override
	 public ItemStack getHeldItem()
	 {
		 return defaultHeldItem;
	 }
}
