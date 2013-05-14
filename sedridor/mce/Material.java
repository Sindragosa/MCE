package sedridor.mce;

import net.minecraft.block.material.MapColor;

public class Material extends net.minecraft.block.material.Material
{
	public static final Material redrockMaterial = (new Material(MCE_Biomes.redrockColor)).setRequiresTool();

	/** Bool defining if the block can burn or not. */
	private boolean canBurn;

	/**
	 * Determines whether blocks with this material can be "overwritten" by other blocks when placed - eg snow, vines
	 * and tall grass.
	 */
	private boolean replaceable;

	/** Indicates if the material is translucent */
	private boolean isTranslucent;

	/** The color index used to draw the blocks of this material on maps. */
	public final MapColor materialMapColor;

	/**
	 * Determines if the material can be harvested without a tool (or with the wrong tool)
	 */
	private boolean requiresNoTool = true;

	/**
	 * Mobility information flag. 0 indicates that this block is normal, 1 indicates that it can't push other blocks, 2
	 * indicates that it can't be pushed.
	 */
	private int mobilityFlag;
	private boolean field_85159_M;

	public Material(MapColor par1MapColor)
	{
		super(par1MapColor);
		this.materialMapColor = par1MapColor;
	}

	/**
	 * Returns if blocks of these materials are liquids.
	 */
	@Override
	public boolean isLiquid()
	{
		return false;
	}

	@Override
	public boolean isSolid()
	{
		return true;
	}

	/**
	 * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
	 */
	@Override
	public boolean getCanBlockGrass()
	{
		return true;
	}

	/**
	 * Returns if this material is considered solid or not
	 */
	@Override
	public boolean blocksMovement()
	{
		return true;
	}

	/**
	 * Marks the material as translucent
	 */
	private Material setTranslucent()
	{
		this.isTranslucent = true;
		return this;
	}

	/**
	 * Makes blocks with this material require the correct tool to be harvested.
	 */
	@Override
	protected Material setRequiresTool()
	{
		this.requiresNoTool = false;
		return this;
	}

	/**
	 * Set the canBurn bool to True and return the current object.
	 */
	@Override
	protected Material setBurning()
	{
		this.canBurn = true;
		return this;
	}

	/**
	 * Returns if the block can burn or not.
	 */
	@Override
	public boolean getCanBurn()
	{
		return this.canBurn;
	}

	/**
	 * Sets {@link #replaceable} to true.
	 */
	@Override
	public Material setReplaceable()
	{
		this.replaceable = true;
		return this;
	}

	/**
	 * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
	 */
	@Override
	public boolean isReplaceable()
	{
		return this.replaceable;
	}

	/**
	 * Indicate if the material is opaque
	 */
	@Override
	public boolean isOpaque()
	{
		return this.isTranslucent ? false : this.blocksMovement();
	}

	/**
	 * Returns true if the material can be harvested without a tool (or with the wrong tool)
	 */
	@Override
	public boolean isToolNotRequired()
	{
		return this.requiresNoTool;
	}

	/**
	 * Returns the mobility information of the material, 0 = free, 1 = can't push but can move over, 2 = total
	 * immobility and stop pistons.
	 */
	@Override
	public int getMaterialMobility()
	{
		return this.mobilityFlag;
	}

	/**
	 * This type of material can't be pushed, but pistons can move over it.
	 */
	@Override
	protected Material setNoPushMobility()
	{
		this.mobilityFlag = 1;
		return this;
	}

	/**
	 * This type of material can't be pushed, and pistons are blocked to move.
	 */
	@Override
	protected Material setImmovableMobility()
	{
		this.mobilityFlag = 2;
		return this;
	}

	/**
	 * Set as harvestable in any case.
	 */
	@Override
	protected Material setAlwaysHarvested()
	{
		this.field_85159_M = true;
		return this;
	}

	/**
	 * Check to see if we can harvest it in any case.
	 */
	@Override
	public boolean isAlwaysHarvested()
	{
		return this.field_85159_M;
	}
}
