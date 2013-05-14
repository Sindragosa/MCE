package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenWoodlands extends BiomeGenBaseMCE
{
	public BiomeGenWoodlands(int par1)
	{
		super(par1);
		this.setTemperatureRainfall(0.7F, 0.8F);
		this.setMinMaxHeight(0.2F, 0.4F);
		this.setEnableSnow();
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 7, 4, 4));
		this.theBiomeDecorator.treesPerChunk = 14;
		this.theBiomeDecorator.grassPerChunk = 20;
		//decorations.add(new BiomeDecoration(30, new WorldGenChunkLeafPile()));
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(4) == 0 ? this.worldGeneratorTrees : (par1Random.nextInt(6) == 0 ? new WorldGenTaiga4(false) : new WorldGenTaiga2(false));
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
	{
		return par1Random.nextInt(10) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
	}
}
