package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenShrubland extends BiomeGenBaseMCE
{
	public BiomeGenShrubland(int par1)
	{
		super(par1);
		this.setTemperatureRainfall(0.6F, 0.2F);
		//this.setMinMaxHeight(0.2F, 0.3F);
		this.theBiomeDecorator.flowersPerChunk = 10;
		//this.theBiomeDecorator.redFlowersPerChunk = 5;
		this.theBiomeDecorator.grassPerChunk = 50;
		this.theBiomeDecorator.treesPerChunk = 0;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return new WorldGenShrub(0, 0);
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
	{
		return par1Random.nextInt(8) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 0) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
	}
}
