package sedridor.mce.entities;

import sedridor.mce.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDwarf extends EntityMobMCE
{
	/** The ItemStack that any Dwarf holds (a pickaxe). */
	private static final ItemStack defaultHeldItem = new ItemStack(Item.pickaxeIron, 1);;

	public EntityDwarf(World par1World)
	{
		super(par1World);
		this.texture = "/MCE/mobs/Dwarf.png";
		this.moveSpeed = 0.27F;
		this.isImmuneToFire = true;
		this.setSize(this.width * 0.75F, this.height * 0.75F);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
		this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
		this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}

	@Override
	public int getMaxHealth()
	{
		return 20;
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
		return 7;
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
		return Item.ingotIron.itemID;
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
		if (par1 > 0)
		{
			switch (this.rand.nextInt(4))
			{
			case 0:
				this.dropItem(MCE_Items.MithrilSword.itemID, 1);
				break;

			case 1:
				this.dropItem(MCE_Items.MithrilHelmet.itemID, 1);
				break;

			case 2:
				this.dropItem(MCE_Items.MithrilPickaxe.itemID, 1);
				break;

			case 3:
				this.dropItem(MCE_Items.MithrilShovel.itemID, 1);
				break;
			}
		}
		else
		{
			switch (this.rand.nextInt(4))
			{
			case 0:
				this.dropItem(Item.swordIron.itemID, 1);
				break;

			case 1:
				this.dropItem(Item.helmetIron.itemID, 1);
				break;

			case 2:
				this.dropItem(Item.pickaxeIron.itemID, 1);
				break;

			case 3:
				this.dropItem(Item.shovelIron.itemID, 1);
				break;
			}
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
		 return this.getSpawnRarity(10) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8;
	 }
}
