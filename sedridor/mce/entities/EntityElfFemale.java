package sedridor.mce.entities;

import sedridor.mce.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityElfFemale extends EntityMobMCE
{
	/** The ItemStack that any Elf holds (a bow). */
	private static final ItemStack defaultHeldItem = new ItemStack(MCE_Items.CrystalBow, 1);

	public EntityElfFemale(World par1World)
	{
		super(par1World);
		this.texture = this.getModelTexture();
		this.moveSpeed = 0.3F;
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAIArrowAttack_MCE(this, this.moveSpeed, 1, 60));
		this.tasks.addTask(5, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(17, Integer.valueOf(0));
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	 @Override
	 public boolean isAIEnabled()
	 {
		 return true;
	 }

	 @Override
	 public int getMaxHealth()
	 {
		 return 28;
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
			 return "/MCE/mobs/New/ElfFemale2.png";
		 }
		 else
		 {
			 return "/MCE/mobs/New/ElfFemale.png";
		 }
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
	  * Get this Entity's EnumCreatureAttribute
	  */
	 @Override
	 public EnumCreatureAttribute getCreatureAttribute()
	 {
		 return EnumCreatureAttribute.UNDEFINED;
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
		 int var3 = this.rand.nextInt(3 + par2);
		 int var4;

		 for (var4 = 0; var4 < var3; ++var4)
		 {
			 this.dropItem(Item.arrow.itemID, 1);
		 }

		 var3 = this.rand.nextInt(3 + par2);

		 for (var4 = 0; var4 < var3; ++var4)
		 {
			 this.dropItem(Item.leather.itemID, 1);
		 }
	 }

	 @Override
	 protected void dropRareDrop(int par1)
	 {
		 if (par1 > 0)
		 {
			 ItemStack var2 = new ItemStack(MCE_Items.MithrilBow);
			 EnchantmentHelper.addRandomEnchantment(this.rand, var2, 5);
			 this.entityDropItem(var2, 0.0F);
		 }
		 else
		 {
			 this.dropItem(MCE_Items.MithrilBow.itemID, 1);
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
		 return this.getSpawnRarity(4) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8;
	 }
}
