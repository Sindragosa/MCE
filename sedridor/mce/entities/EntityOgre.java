package sedridor.mce.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOgre extends EntityMob
{
	/** The ItemStack that any Ogre holds (a book). */
	//private static final ItemStack defaultHeldItem = new ItemStack(Item.axeWood, 1);
	protected double attackRange;

	public EntityOgre(World par1World)
	{
		super(par1World);
		this.texture = "/MCE/mobs/Ogre.png";
		this.moveSpeed = 0.25F;
		this.attackRange = 12.0D;
		this.setSize(1.5F, 4.0F);
		//        this.tasks.addTask(1, new EntityAISwimming(this));
		//        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
		//        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
		//        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
		//        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
		//        this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		//        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		//        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
		//        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
	}

	@Override
	public int getMaxHealth()
	{
		return 35;
	}

	/**
	 * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue()
	{
		return 2;
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	@Override
	public int getAttackStrength(Entity par1Entity)
	{
		return 12;
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	//protected boolean isAIEnabled()
	//{
	//    return true;
	//}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate()
	{
		if (!this.worldObj.isRemote)
		{
			if (this.entityToAttack != null)
			{
			}
		}

		super.onLivingUpdate();
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2)
	{
		if (par2 < 2.5F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY && this.worldObj.difficultySetting > 0)
		{
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength(par1Entity));
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if (super.attackEntityFrom(par1DamageSource, par2))
		{
			Entity var3 = par1DamageSource.getEntity();

			if (this.riddenByEntity != var3 && this.ridingEntity != var3)
			{
				if (var3 != this && this.worldObj.difficultySetting > 0)
				{
					this.entityToAttack = var3;
				}

				return true;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	//    public boolean attackEntityAsMob(Entity entity)
	//    {
	//        if (super.attackEntityAsMob(entity))
	//        {
	//            if (entity instanceof EntityLiving)
	//            {
	//                byte byte0 = 0;
	//
	//                if (this.worldObj.difficultySetting > 1)
	//                {
	//                    if (this.worldObj.difficultySetting == 2)
	//                    {
	//                        byte0 = 7;
	//                    }
	//                    else if (this.worldObj.difficultySetting == 3)
	//                    {
	//                        byte0 = 15;
	//                    }
	//                }
	//
	//                if (byte0 > 0)
	//                {
	//                    ((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.poison.id, byte0 * 20, 0));
	//                }
	//            }
	//
	//            return true;
	//        }
	//        else
	//        {
	//            return false;
	//        }
	//    }

	/**
	 * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
	 * (Animals, Spiders at day, peaceful PigZombies).
	 */
	@Override
	protected Entity findPlayerToAttack()
	{
		float var1 = this.getBrightness(1.0F);

		if (var1 < 0.5F)
		{
			EntityPlayer var2 = this.worldObj.getClosestVulnerablePlayerToEntity(this, this.attackRange);

			if (var2 != null && this.worldObj.difficultySetting > 0)
			{
				return var2;
			}
		}

		return null;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "ogre";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "ogrehurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "ogredying";
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
		return Item.flint.itemID;
	}

	@Override
	protected void dropRareDrop(int par1)
	{
		switch (this.rand.nextInt(2))
		{
		case 0:
			this.dropItem(Item.ingotIron.itemID, 1);
			break;

		case 1:
			this.dropItem(Block.obsidian.blockID, 1);
			break;
		}
	}

	/**
	 * Returns the item that this EntityLiving is holding, if any.
	 */
	 //public ItemStack getHeldItem()
	//{
	//    return defaultHeldItem;
	//}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnedInChunk()
	{
		return 3;
	}

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
		return this.getSpawnRarity(2) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.getBlockPathWeight(var1, var2, var3) >= 0.0F && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 4;
	}
}
