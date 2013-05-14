package sedridor.mce.entities;

import sedridor.mce.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNagaFemale extends EntityMobMCE
{
	/** The ItemStack that any Naga holds (a book). */
	private static final ItemStack defaultHeldItem = new ItemStack(MCE_Items.AdamantiumSword, 1);

	public EntityNagaFemale(World par1World)
	{
		super(par1World);
		this.texture = "/MCE/mobs/New/NagaFemale.png";
		this.moveSpeed = 0.25F;
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
	public int getMaxHealth()
	{
		return 30;
	}

	/**
	 * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	 */
	@Override
	public int getTotalArmorValue()
	{
		return 5;
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
		return "manliving2.ogg";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "grunt.ogg";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mandeath.ogg";
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
	 * Drop 0-2 items of this living's type
	 * recentlyHit > 0, lootingModifier
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
		int var4;

		for (var4 = 0; var4 < var3; ++var4)
		{
			this.dropItem(Item.ingotIron.itemID, 1);
		}
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
	 * Drop rare items of this living's type
	 * superRare = 1, rare = 0
	 */
	@Override
	protected void dropRareDrop(int par1)
	{
		if (par1 > 0)
		{
			switch (this.rand.nextInt(2))
			{
			case 0:
				this.dropItem(MCE_Items.AdamantiumBattleaxe.itemID, 1);
				break;

			case 1:
				this.dropItem(MCE_Items.AdamantiumSword.itemID, 1);
				break;
			}
		}
		else
		{
			switch (this.rand.nextInt(2))
			{
			case 0:
				this.dropItem(MCE_Items.AdamantiumIngot.itemID, 1);
				break;

			case 1:
				this.dropItem(MCE_Items.MithrilIngot.itemID, 1);
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
	 * Returns if any of the blocks within the aabb are liquids. Args: aabb
	 */
	public boolean isAnyLava(AxisAlignedBB par1AxisAlignedBB)
	{
		int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);

		if (par1AxisAlignedBB.minX < 0.0D)
		{
			--var2;
		}

		if (par1AxisAlignedBB.minY < 0.0D)
		{
			--var4;
		}

		if (par1AxisAlignedBB.minZ < 0.0D)
		{
			--var6;
		}

		for (int var8 = var2; var8 < var3; ++var8)
		{
			for (int var9 = var4; var9 < var5; ++var9)
			{
				for (int var10 = var6; var10 < var7; ++var10)
				{
					Block var11 = Block.blocksList[this.worldObj.getBlockId(var8, var9, var10)];

					if (var11 != null && var11.blockMaterial == Material.lava)
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Returns the entity's probability to spawn.
	 */
	@Override
	public boolean getSpawnRarity(int par1)
	{
		return this.rand.nextInt(100) < par1;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY);
		int var3 = MathHelper.floor_double(this.posZ);
		return this.getSpawnRarity(10) && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.isAnyLava(this.boundingBox) && this.worldObj.getFullBlockLightValue(var1, var2, var3) > 8;
	}
}
