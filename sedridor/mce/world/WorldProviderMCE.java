package sedridor.mce.world;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class WorldProviderMCE extends WorldProvider
{
	/** world object being used */
	public World worldObj;

	/** Array for sunrise/sunset colors (RGBA) */
	private float[] colorsSunriseSunset = new float[4];

	/**
	 * Will check if the x, z position specified is alright to be set as the map spawn point
	 */
	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2)
	{
		int k = this.worldObj.getFirstUncoveredBlock(par1, par2);
		return k == Block.grass.blockID;
	}

	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
	 */
	@Override
	public float calculateCelestialAngle(long par1, float par3)
	{
		int j = (int)(par1 % 24000L);
		float f1 = (j + par3) / 24000.0F - 0.25F;

		if (f1 < 0.0F)
		{
			++f1;
		}

		if (f1 > 1.0F)
		{
			--f1;
		}

		float f2 = f1;
		f1 = 1.0F - (float)((Math.cos(f1 * Math.PI) + 1.0D) / 2.0D);
		f1 = f2 + (f1 - f2) / 3.0F;
		return f1;
	}

	@Override
	public int getMoonPhase(long par1)
	{
		return (int)(par1 / 24000L) % 8;
	}

	/**
	 * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
	 */
	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns array with sunrise/sunset colors
	 */
	public float[] calcSunriseSunsetColors(float par1, float par2)
	{
		float f2 = 0.4F;
		float f3 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) - 0.0F;
		float f4 = -0.0F;

		if (f3 >= f4 - f2 && f3 <= f4 + f2)
		{
			float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float)Math.PI)) * 0.99F;
			f6 *= f6;
			this.colorsSunriseSunset[0] = f5 * 0.3F + 0.7F;
			this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[2] = f5 * f5 * 0.0F + 0.2F;
			this.colorsSunriseSunset[3] = f6;
			return this.colorsSunriseSunset;
		}
		else
		{
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Return Vec3D with biome specific fog color
	 */
	public Vec3 getFogColor(float par1, float par2)
	{
		float f2 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

		if (f2 < 0.0F)
		{
			f2 = 0.0F;
		}

		if (f2 > 1.0F)
		{
			f2 = 1.0F;
		}

		float f3 = 0.7529412F;
		float f4 = 0.84705883F;
		float f5 = 1.0F;
		f3 *= f2 * 0.94F + 0.06F;
		f4 *= f2 * 0.94F + 0.06F;
		f5 *= f2 * 0.91F + 0.09F;
		return this.worldObj.getWorldVec3Pool().getVecFromPool(f3, f4, f5);
	}

	/**
	 * True if the player can respawn in this dimension (true = overworld, false = nether).
	 */
	@Override
	public boolean canRespawnHere()
	{
		return true;
	}

	public static WorldProvider getProviderForDimension(int par0)
	{
		return DimensionManager.createProviderFor(par0);
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * the y level at which clouds are rendered.
	 */
	public float getCloudHeight()
	{
		return 78.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isSkyColored()
	{
		return true;
	}

	/**
	 * Gets the hard-coded portal location to use when entering this dimension.
	 */
	@Override
	public ChunkCoordinates getEntrancePortalLocation()
	{
		return null;
	}

	//    public int getAverageGroundLevel()
	//    {
	//        return this.terrainType.getMinimumSpawnHeight(this.worldObj);
	//    }

	//    @SideOnly(Side.CLIENT)

	/**
	 * returns true if this dimension is supposed to display void particles and pull in the far plane based on the
	 * user's Y offset.
	 */
	//    public boolean getWorldHasVoidParticles()
	//    {
	//        return this.terrainType.hasVoidParticles(this.hasNoSky);
	//    }

	//    @SideOnly(Side.CLIENT)

	/**
	 * Returns a double value representing the Y value relative to the top of the map at which void fog is at its
	 * maximum. The default factor of 0.03125 relative to 256, for example, means the void fog will be at its maximum at
	 * (256*0.03125), or 8.
	 */
	//    public double getVoidFogYFactor()
	//    {
	//        return this.terrainType.voidFadeMagnitude();
	//    }

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Returns true if the given X,Z coordinate should show environmental fog.
	 */
	public boolean doesXZShowFog(int par1, int par2)
	{
		return false;
	}
}
