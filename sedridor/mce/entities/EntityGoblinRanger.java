package sedridor.mce.entities;

import sedridor.mce.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGoblinRanger extends EntityMobMCE
{
	/** The ItemStack that any Goblin holds (a bow). */
	private static final ItemStack defaultHeldItem = new ItemStack(Item.bow, 1);

	public EntityGoblinRanger(World par1World)
	{
		super(par1World);
		this.texture = this.getModelTexture();
		this.setSize(0.9F, 0.8F);
		this.moveSpeed = 0.7F;
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
		return 15;
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	 @Override
	 public int getAttackStrength(Entity par1Entity)
	{
		 return 3;
	}

	 @Override
	 public float getEyeHeight()
	 {
		 return this.height * 0.06F;
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
			 return "/MCE/mobs/GoblinRanger2.png";
		 }
		 else
		 {
			 return "/MCE/mobs/GoblinRanger.png";
		 }
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
	  * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	  */
	 @Override
	 protected void attackEntity(Entity par1, float par2)
	 {
		 if (par2 < 12.0F)
		 {
			 double var3 = par1.posX - this.posX;
			 double var5 = par1.posZ - this.posZ;

			 if (this.attackTime == 0)
			 {
				 EntityArrow var7 = new EntityArrow(this.worldObj, this, 1.0F);
				 double var8 = par1.posY + par1.getEyeHeight() - 0.7D - var7.posY;
				 float var10 = MathHelper.sqrt_double(var3 * var3 + var5 * var5) * 0.2F;
				 this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
				 this.worldObj.spawnEntityInWorld(var7);
				 var7.setThrowableHeading(var3, var8 + var10, var5, 1.6F, 12.0F);
				 this.attackTime = 60;
			 }

			 this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0D / Math.PI) - 90.0F;
			 this.hasAttacked = true;
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
	  * Will return how many at most can spawn in a chunk at once.
	  */
	 @Override
	 public int getMaxSpawnedInChunk()
	 {
		 return 4;
	 }

	 /**
	  * Returns the item ID for the item the mob drops on death.
	  */
	 @Override
	 protected int getDropItemId()
	 {
		 return Item.arrow.itemID;
	 }

	 /**
	  * Drop 0-2 items of this living's type
	  */
	 @Override
	 protected void dropFewItems(boolean par1, int par2)
	 {
		 int var3 = this.rand.nextInt(2);
		 int var4;

		 for (var4 = 0; var4 < var3; ++var4)
		 {
			 this.dropItem(Item.bow.itemID, 1);
		 }

		 var3 = this.rand.nextInt(2) + 1;

		 for (var4 = 0; var4 < var3; ++var4)
		 {
			 this.dropItem(Item.arrow.itemID, 1);
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
			 this.entityDropItem(new ItemStack(Item.potion.itemID, 1, 16370), 0.0F);
		 }
		 else
		 {
			 this.dropItem(MCE_Items.SteelBow.itemID, 1);
		 }
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
		 return this.getSpawnRarity(4) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8;
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
