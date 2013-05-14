package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenSavanna extends BiomeGenBaseMCE
{
	public BiomeGenSavanna(int par1)
	{
		super(par1);
		//this.setDisableRain();
		this.setTemperatureRainfall(1.2F, 0.02F);
		//this.setMinMaxHeight(0.1F, 0.2F);
		this.theBiomeDecorator.treesPerChunk = 0;
		this.theBiomeDecorator.flowersPerChunk = -999;
		this.theBiomeDecorator.grassPerChunk = 8;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(3) == 0 ? new WorldGenShrub(0, 0) : new WorldGenAcacia(false);
	}
}
