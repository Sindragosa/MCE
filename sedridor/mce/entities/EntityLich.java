package sedridor.mce.entities;

import sedridor.mce.*;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityLich extends EntityMob
{
	public EntityLich masterLich;
	private static final ItemStack[] heldItems = new ItemStack[] {new ItemStack(MCE_Items.TwilightWand, 1), new ItemStack(Item.swordGold, 1)};

	public EntityLich(World par1World)
	{
		super(par1World);
		this.texture = "/MCE/mobs/twilightlich.png";
		this.setSize(1.1F, 2.5F);
		this.setShadowClone(false);
		this.masterLich = null;
		this.isImmuneToFire = true;
		this.setShieldStrength(5);
		this.setMinionsToSummon(4);
		this.experienceValue = 220;
	}

	public EntityLich(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		this.setPosition(par2, par4, par6);
	}

	public EntityLich(World par1World, EntityLich par2EntityLich)
	{
		this(par1World);
		this.setShadowClone(true);
		this.masterLich = par2EntityLich;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
		this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
		this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
	}

	@Override
	public int getMaxHealth()
	{
		return 100;
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	 @Override
	 public int getAttackStrength(Entity par1Entity)
	 {
		 return 6;
	 }

	 /**
	  * Returns the item that this EntityLiving is holding, if any.
	  */
	 @Override
	 public ItemStack getHeldItem()
	 {
		 return heldItems[this.getPhase() - 1];
	 }

	 /**
	  * Drop 0-2 items of this living's type
	  */
	 @Override
	 protected void dropFewItems(boolean par1, int par2)
	 {
		 int var3 = this.rand.nextInt(4 + par2) + 2;
		 int var4;
		 for (var4 = 0; var4 < var3; ++var4)
		 {
			 this.dropItem(Item.enderPearl.itemID, 1);
		 }

		 var3 = this.rand.nextInt(5 + par2) + 5;
		 for (var4 = 0; var4 < var3; ++var4)
		 {
			 this.dropItem(Item.bone.itemID, 1);
		 }
	 }

	 @Override
	 protected void dropRareDrop(int par1)
	 {
		 switch (this.rand.nextInt(2))
		 {
		 case 0:
			 this.entityDropItem(new ItemStack(MCE_Items.TwilightWand), 0.0F);
			 break;

		 case 1:
			 this.dropGoldThing();
			 break;
		 }
	 }

	 private void dropGoldThing()
	 {
		 int var2 = this.rand.nextInt(5);
		 ItemStack var1;

		 if (var2 == 0)
		 {
			 var1 = new ItemStack(Item.swordGold);
		 }
		 else if (var2 == 1)
		 {
			 var1 = new ItemStack(Item.helmetGold);
		 }
		 else if (var2 == 2)
		 {
			 var1 = new ItemStack(Item.plateGold);
		 }
		 else if (var2 == 3)
		 {
			 var1 = new ItemStack(Item.legsGold);
		 }
		 else
		 {
			 var1 = new ItemStack(Item.bootsGold);
		 }

		 EnchantmentHelper.addRandomEnchantment(this.rand, var1, 10 + this.rand.nextInt(30));
		 this.entityDropItem(var1, 0.0F);
	 }

	 /**
	  * Determines if an entity can be despawned, used on idle far away entities
	  */
	  @Override
	  protected boolean canDespawn()
	 {
		  return false;
	 }

	 /**
	  * Will return how many at most can spawn in a chunk at once.
	  */
	  @Override
	  public int getMaxSpawnedInChunk()
	  {
		  return 1;
	  }

	  public boolean getSpawnRarity(int par1)
	  {
		  return this.rand.nextInt(10000) < par1;
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
		  return this.getSpawnRarity(2) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 4;
	  }

	  /**
	   * Whether or not the current entity is in lava
	   */
	  @Override
	  public boolean handleLavaMovement()
	  {
		  return false;
	  }

	  /**
	   * Checks if this entity is inside water (if inWater field is true as a result of handleWaterMovement() returning
	   * true)
	   */
	  @Override
	  public boolean isInWater()
	  {
		  return false;
	  }

	  public int getPhase()
	  {
		  return !this.isShadowClone() && this.getShieldStrength() <= 0 ? (this.getMinionsToSummon() > 0 ? 2 : 3) : 1;
	  }

	  /**
	   * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	   * use this to react to sunlight and start to burn.
	   */
	  @Override
	  public void onLivingUpdate()
	  {
		  float var1 = this.renderYawOffset * (float)Math.PI / 180.0F;
		  double var2 = this.posX + MathHelper.cos(var1) * 0.65D;
		  double var4 = this.posY + this.height * 0.94D;
		  double var6 = this.posZ + MathHelper.sin(var1) * 0.65D;
		  int var8 = (80 - this.attackTime) / 10;
		  int var9 = var8 > 0 ? this.rand.nextInt(var8) : 1;

		  for (int var10 = 0; var10 < var9; ++var10)
		  {
			  float var11 = 1.0F - (this.attackTime + 1.0F) / 60.0F;
			  var11 *= var11;
			  float var12 = 0.37F * var11;
			  float var13 = 0.99F * var11;
			  float var14 = 0.89F * var11;
			  this.worldObj.spawnParticle("mobSpell", var2 + this.rand.nextGaussian() * 0.025D, var4 + this.rand.nextGaussian() * 0.025D, var6 + this.rand.nextGaussian() * 0.025D, var12, var13, var14);
		  }

		  if (this.isShadowClone())
		  {
			  this.checkForMaster();
		  }

		  super.onLivingUpdate();
	  }

	  /**
	   * Called when the entity is attacked.
	   */
	  @Override
	  public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	  {
		  if (par1DamageSource.getDamageType() == "inWall" && this.entityToAttack != null)
		  {
			  this.teleportToSightOfEntity(this.entityToAttack);
		  }

		  if (this.isShadowClone())
		  {
			  this.worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			  return false;
		  }
		  else
		  {
			  Entity var3 = this.entityToAttack;

			  if (par1DamageSource.getEntity() instanceof EntityLich)
			  {
				  return false;
			  }
			  else if (this.getShieldStrength() > 0 && !par1DamageSource.isUnblockable())
			  {
				  this.worldObj.playSoundAtEntity(this, "random.break", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);

				  if (par1DamageSource.getEntity() instanceof EntityPlayer)
				  {
					  this.entityToAttack = par1DamageSource.getEntity();
				  }

				  return false;
			  }
			  else if (super.attackEntityFrom(par1DamageSource, par2))
			  {
				  if (this.entityToAttack instanceof EntityLich)
				  {
					  this.entityToAttack = var3;
				  }

				  if (this.getShieldStrength() > 0)
				  {
					  this.setShieldStrength(this.getShieldStrength() - 1);
					  this.worldObj.playSoundAtEntity(this, "random.break", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				  }

				  return true;
			  }
			  else
			  {
				  return false;
			  }
		  }
	  }

	  /**
	   * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	   */
	  @Override
	  protected void attackEntity(Entity par1Entity, float par2)
	  {
		  if (!this.isShadowClone() && this.attackTime % 15 == 0)
		  {
			  this.popNearbyMob();
		  }

		  if (this.getPhase() == 1)
		  {
			  if (this.attackTime == 60 && !this.worldObj.isRemote)
			  {
				  this.teleportToSightOfEntity(par1Entity);

				  if (!this.isShadowClone())
				  {
					  this.checkAndSpawnClones(par1Entity);
				  }
			  }

			  if (this.canEntityBeSeen(par1Entity) && this.attackTime == 0 && par2 < 20.0F)
			  {
				  this.launchBoltAt(par1Entity);
				  this.attackTime = 100;
			  }

			  this.hasAttacked = true;
		  }

		  if (this.getPhase() == 2 && !this.isShadowClone())
		  {
			  this.despawnClones();

			  if (this.attackTime % 15 == 0)
			  {
				  this.checkAndSpawnMinions(par1Entity);
			  }

			  if (this.attackTime == 0)
			  {
				  if (par2 < 2.0F)
				  {
					  this.attackEntityAsMob(par1Entity);
					  this.attackTime = 20;
				  }
				  else if (par2 < 20.0F && this.canEntityBeSeen(par1Entity))
				  {
					  this.launchBoltAt(par1Entity);
					  this.attackTime = 60;
				  }
				  else
				  {
					  this.teleportToSightOfEntity(par1Entity);
					  this.attackTime = 20;
				  }
			  }

			  this.hasAttacked = true;
		  }

		  if (this.getPhase() == 3 && this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
		  {
			  this.attackTime = 20;
			  this.attackEntityAsMob(par1Entity);
			  this.hasAttacked = true;
		  }
	  }

	  protected void launchBoltAt(Entity par1Entity)
	  {
		  float var2 = this.renderYawOffset * (float)Math.PI / 180.0F;
		  double var3 = this.posX + MathHelper.cos(var2) * 0.65D;
		  double var5 = this.posY + this.height * 0.82D;
		  double var7 = this.posZ + MathHelper.sin(var2) * 0.65D;
		  double var9 = par1Entity.posX - var3;
		  double var11 = par1Entity.boundingBox.minY + par1Entity.height / 2.0F - (this.posY + this.height / 2.0F);
		  double var13 = par1Entity.posZ - var7;
		  this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
		  EntityLichBolt var15 = new EntityLichBolt(this.worldObj, this);
		  var15.setThrowableHeading(var9, var11, var13, var15.func_40077_c(), 1.0F);
		  var15.setLocationAndAngles(var3, var5, var7, this.rotationYaw, this.rotationPitch);
		  System.out.println("MC launchBoltAt... " + var15);
		  this.worldObj.spawnEntityInWorld(var15);
	  }

	  protected void popNearbyMob()
	  {
		  List var1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32.0D, 16.0D, 32.0D));
		  Iterator var2 = var1.iterator();

		  while (var2.hasNext())
		  {
			  Entity var3 = (Entity)var2.next();

			  if (var3 instanceof EntityLiving && this.canPop(var3) && this.canEntityBeSeen(var3))
			  {
				  EntityLiving var4 = (EntityLiving)var3;

				  if (!this.worldObj.isRemote)
				  {
					  var4.setDead();
					  var4.spawnExplosionParticle();
				  }

				  this.makeRedMagicTrail(var4.posX, var4.posY + var4.height / 2.0D, var4.posZ, this.posX, this.posY + this.height / 2.0D, this.posZ);
				  break;
			  }
		  }
	  }

	  protected boolean canPop(Entity par1Entity)
	  {
		  Class[] var2 = new Class[] {EntitySkeleton.class, EntityZombie.class, EntityEnderman.class, EntitySpider.class, EntityCreeper.class};
		  Class[] var3 = var2;
		  int var4 = var2.length;

		  for (int var5 = 0; var5 < var4; ++var5)
		  {
			  Class var6 = var3[var5];

			  if (par1Entity.getClass() == var6)
			  {
				  return true;
			  }
		  }

		  return false;
	  }

	  protected void checkAndSpawnClones(Entity par1Entity)
	  {
		  if (this.countMyClones() < 2)
		  {
			  this.spawnShadowClone(par1Entity);
		  }
	  }

	  protected int countMyClones()
	  {
		  List var1 = this.worldObj.getEntitiesWithinAABB(EntityLich.class, AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32.0D, 16.0D, 32.0D));
		  int var2 = 0;
		  Iterator var3 = var1.iterator();

		  while (var3.hasNext())
		  {
			  EntityLich var4 = (EntityLich)var3.next();

			  if (var4.isShadowClone() && var4.masterLich == this)
			  {
				  ++var2;
			  }
		  }

		  return var2;
	  }

	  public boolean wantsNewClone(EntityLich par1EntityLich)
	  {
		  return par1EntityLich.isShadowClone() && this.countMyClones() < 2;
	  }

	  protected void spawnShadowClone(Entity par1Entity)
	  {
		  Vec3 var2 = this.findVecInLOSOf(par1Entity);
		  EntityLich var3 = new EntityLich(this.worldObj, this);
		  var3.setPosition(var2.xCoord, var2.yCoord, var2.zCoord);
		  this.worldObj.spawnEntityInWorld(var3);
		  var3.entityToAttack = par1Entity;
		  var3.attackTime = 60 + this.rand.nextInt(3) - this.rand.nextInt(3);
		  this.makeTeleportTrail(this.posX, this.posY, this.posZ, var2.xCoord, var2.yCoord, var2.zCoord);
	  }

	  protected void despawnClones()
	  {
		  List var1 = this.worldObj.getEntitiesWithinAABB(this.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32.0D, 16.0D, 32.0D));
		  Iterator var2 = var1.iterator();

		  while (var2.hasNext())
		  {
			  EntityLich var3 = (EntityLich)var2.next();

			  if (var3.isShadowClone())
			  {
				  var3.isDead = true;
			  }
		  }
	  }

	  protected void checkAndSpawnMinions(Entity par1Entity)
	  {
		  if (!this.worldObj.isRemote && this.getMinionsToSummon() > 0)
		  {
			  int var2 = this.countMyMinions();

			  if (var2 < 3)
			  {
				  this.spawnMinionAt((EntityLiving)par1Entity);
				  this.setMinionsToSummon(this.getMinionsToSummon() - 1);
			  }
		  }
	  }

	  protected int countMyMinions()
	  {
		  List var1 = this.worldObj.getEntitiesWithinAABB(EntityLichMinion.class, AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32.0D, 16.0D, 32.0D));
		  int var2 = 0;
		  Iterator var3 = var1.iterator();

		  while (var3.hasNext())
		  {
			  EntityLichMinion var4 = (EntityLichMinion)var3.next();

			  if (var4.master == this)
			  {
				  ++var2;
			  }
		  }

		  return var2;
	  }

	  protected void spawnMinionAt(EntityLiving par1EntityLiving)
	  {
		  Vec3 var2 = this.findVecInLOSOf(par1EntityLiving);
		  EntityLichMinion var3 = new EntityLichMinion(this.worldObj, this);
		  var3.setPosition(var2.xCoord, var2.yCoord, var2.zCoord);
		  this.worldObj.spawnEntityInWorld(var3);
		  var3.setAttackTarget(par1EntityLiving);
		  var3.spawnExplosionParticle();
		  this.worldObj.playSoundAtEntity(var3, "random.pop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
		  this.makeTwilightMagicTrail(this.posX, this.posY + this.getEyeHeight(), this.posZ, var2.xCoord, var2.yCoord + var3.height / 2.0D, var2.zCoord);
	  }

	  public boolean wantsNewMinion(EntityLichMinion par1EntityLichMinion)
	  {
		  return this.countMyMinions() < 3;
	  }

	  protected void checkForMaster()
	  {
		  if (this.masterLich == null)
		  {
			  this.findNewMaster();
		  }

		  if (!this.worldObj.isRemote)
		  {
			  if (this.masterLich != null && !this.masterLich.isDead)
			  {
				  double var1 = (this.posX - this.masterLich.posX) * (this.posX - this.masterLich.posX) + (this.posY - this.masterLich.posY) * (this.posY - this.masterLich.posY) + (this.posZ - this.masterLich.posZ) * (this.posZ - this.masterLich.posZ);
			  }
			  else
			  {
				  this.isDead = true;
			  }
		  }
	  }

	  private void findNewMaster()
	  {
		  List var1 = this.worldObj.getEntitiesWithinAABB(EntityLich.class, AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D).expand(32.0D, 16.0D, 32.0D));
		  Iterator var2 = var1.iterator();

		  while (var2.hasNext())
		  {
			  EntityLich var3 = (EntityLich)var2.next();

			  if (!var3.isShadowClone() && var3.wantsNewClone(this))
			  {
				  this.masterLich = var3;
				  this.makeTeleportTrail(this.posX, this.posY, this.posZ, var3.posX, var3.posY, var3.posZ);
				  this.setAttackTarget(this.masterLich.getAttackTarget());
				  break;
			  }
		  }
	  }

	  protected void teleportToSightOfEntity(Entity par1Entity)
	  {
		  Vec3 var2 = this.findVecInLOSOf(par1Entity);
		  double var3 = this.posX;
		  double var5 = this.posY;
		  double var7 = this.posZ;

		  if (var2 != null)
		  {
			  this.teleportToNoChecks(var2.xCoord, var2.yCoord, var2.zCoord);
			  this.faceEntity(par1Entity, 100.0F, 100.0F);
			  this.renderYawOffset = this.rotationYaw;

			  if (!this.canEntityBeSeen(par1Entity))
			  {
				  this.teleportToNoChecks(var3, var5, var7);
			  }
		  }
	  }

	  protected Vec3 findVecInLOSOf(Entity par1Entity)
	  {
		  double var2 = 0.0D;
		  double var4 = 0.0D;
		  double var6 = 0.0D;
		  byte var8 = 100;

		  for (int var9 = 0; var9 < var8; ++var9)
		  {
			  var2 = par1Entity.posX + this.rand.nextGaussian() * 16.0D;
			  var4 = par1Entity.posY + this.rand.nextGaussian() * 8.0D;
			  var6 = par1Entity.posZ + this.rand.nextGaussian() * 16.0D;
			  boolean var10 = false;
			  int var11 = MathHelper.floor_double(var2);
			  int var12 = MathHelper.floor_double(var4);
			  int var13 = MathHelper.floor_double(var6);

			  while (!var10 && var4 > 0.0D)
			  {
				  int var14 = this.worldObj.getBlockId(var11, var12 - 1, var13);

				  if (var14 != 0 && Block.blocksList[var14].blockMaterial.isSolid())
				  {
					  var10 = true;
				  }
				  else
				  {
					  --var4;
					  --var12;
				  }
			  }

			  if (var12 != 0 && this.canEntitySee(par1Entity, var2, var4, var6))
			  {
				  float var16 = this.width / 2.0F;
				  AxisAlignedBB var15 = AxisAlignedBB.getBoundingBox(var2 - var16, var4 - this.yOffset + this.ySize, var6 - var16, var2 + var16, var4 - this.yOffset + this.ySize + this.height, var6 + var16);

				  if (this.worldObj.getCollidingBoundingBoxes(this, var15).size() <= 0 && !this.worldObj.isAnyLiquid(var15))
				  {
					  break;
				  }
			  }
		  }

		  return var8 == 99 ? null : this.worldObj.getWorldVec3Pool().getVecFromPool(var2, var4, var6);
	  }

	  protected boolean canEntitySee(Entity par1Entity, double par2, double par4, double par6)
	  {
		  return this.worldObj.rayTraceBlocks(this.worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY + par1Entity.getEyeHeight(), par1Entity.posZ), this.worldObj.getWorldVec3Pool().getVecFromPool(par2, par4, par6)) == null;
	  }

	  protected boolean teleportToNoChecks(double par1, double par3, double par5)
	  {
		  double var7 = this.posX;
		  double var9 = this.posY;
		  double var11 = this.posZ;
		  this.setPosition(par1, par3, par5);
		  this.makeTeleportTrail(var7, var9, var11, par1, par3, par5);
		  this.worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
		  this.worldObj.playSoundAtEntity(this, "mob.endermen.portal", 1.0F, 1.0F);
		  this.isJumping = false;
		  return true;
	  }

	  protected void makeTeleportTrail(double par1, double par3, double par5, double par7, double par9, double par11)
	  {
		  short var13 = 128;

		  for (int var14 = 0; var14 < var13; ++var14)
		  {
			  double var15 = var14 / (var13 - 1.0D);
			  float var17 = (this.rand.nextFloat() - 0.5F) * 0.2F;
			  float var18 = (this.rand.nextFloat() - 0.5F) * 0.2F;
			  float var19 = (this.rand.nextFloat() - 0.5F) * 0.2F;
			  double var20 = par1 + (par7 - par1) * var15 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
			  double var22 = par3 + (par9 - par3) * var15 + this.rand.nextDouble() * this.height;
			  double var24 = par5 + (par11 - par5) * var15 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
			  this.worldObj.spawnParticle("spell", var20, var22, var24, var17, var18, var19);
		  }
	  }

	  protected void makeRedMagicTrail(double par1, double par3, double par5, double par7, double par9, double par11)
	  {
		  byte var13 = 32;

		  for (int var14 = 0; var14 < var13; ++var14)
		  {
			  double var15 = var14 / (var13 - 1.0D);
			  float var17 = 1.0F;
			  float var18 = 0.5F;
			  float var19 = 0.5F;
			  double var20 = par1 + (par7 - par1) * var15 + this.rand.nextGaussian() * 0.005D;
			  double var22 = par3 + (par9 - par3) * var15 + this.rand.nextGaussian() * 0.005D;
			  double var24 = par5 + (par11 - par5) * var15 + this.rand.nextGaussian() * 0.005D;
			  this.worldObj.spawnParticle("mobSpell", var20, var22, var24, var17, var18, var19);
		  }
	  }

	  protected void makeTwilightMagicTrail(double par1, double par3, double par5, double par7, double par9, double par11)
	  {
		  byte var13 = 32;

		  for (int var14 = 0; var14 < var13; ++var14)
		  {
			  double var15 = var14 / (var13 - 1.0D);
			  float var17 = 0.2F;
			  float var18 = 0.2F;
			  float var19 = 0.2F;
			  double var20 = par1 + (par7 - par1) * var15 + this.rand.nextGaussian() * 0.005D;
			  double var22 = par3 + (par9 - par3) * var15 + this.rand.nextGaussian() * 0.005D;
			  double var24 = par5 + (par11 - par5) * var15 + this.rand.nextGaussian() * 0.005D;
			  this.worldObj.spawnParticle("mobSpell", var20, var22, var24, var17, var18, var19);
		  }
	  }

	  public boolean isShadowClone()
	  {
		  return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	  }

	  public void setShadowClone(boolean par1)
	  {
		  byte var2 = this.dataWatcher.getWatchableObjectByte(16);

		  if (par1)
		  {
			  this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 2)));
		  }
		  else
		  {
			  this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & -3)));
		  }
	  }

	  public byte getShieldStrength()
	  {
		  return this.dataWatcher.getWatchableObjectByte(17);
	  }

	  public void setShieldStrength(int par1)
	  {
		  this.dataWatcher.updateObject(17, Byte.valueOf((byte)par1));
	  }

	  public byte getMinionsToSummon()
	  {
		  return this.dataWatcher.getWatchableObjectByte(18);
	  }

	  public void setMinionsToSummon(int par1)
	  {
		  this.dataWatcher.updateObject(18, Byte.valueOf((byte)par1));
	  }

	  /**
	   * Returns the sound this mob makes while it's alive.
	   */
	  @Override
	  protected String getLivingSound()
	  {
		  return "mob.blaze.breathe";
	  }

	  /**
	   * Returns the sound this mob makes when it is hurt.
	   */
	  @Override
	  protected String getHurtSound()
	  {
		  return "mob.blaze.hit";
	  }

	  /**
	   * Returns the sound this mob makes on death.
	   */
	  @Override
	  protected String getDeathSound()
	  {
		  return "mob.blaze.death";
	  }

	  /**
	   * (abstract) Protected helper method to write subclass entity data to NBT.
	   */
	  @Override
	  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	  {
		  super.writeEntityToNBT(par1NBTTagCompound);
		  par1NBTTagCompound.setBoolean("ShadowClone", this.isShadowClone());
		  par1NBTTagCompound.setByte("ShieldStrength", this.getShieldStrength());
		  par1NBTTagCompound.setByte("MinionsToSummon", this.getMinionsToSummon());
	  }

	  /**
	   * (abstract) Protected helper method to read subclass entity data from NBT.
	   */
	  @Override
	  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	  {
		  super.readEntityFromNBT(par1NBTTagCompound);
		  this.setShadowClone(par1NBTTagCompound.getBoolean("ShadowClone"));
		  this.setShieldStrength(par1NBTTagCompound.getByte("ShieldStrength"));
		  this.setMinionsToSummon(par1NBTTagCompound.getByte("MinionsToSummon"));
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
			  //((EntityPlayer)par1DamageSource.getSourceOfDamage()).triggerAchievement(AchievementPage.twilightLich);
		  }
	  }
}
