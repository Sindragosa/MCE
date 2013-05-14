package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenGreenHills extends BiomeGenBaseMCE
{
	public BiomeGenGreenHills(int par1)
	{
		super(par1);
		this.setTemperatureRainfall(0.6F, 0.9F);
		this.setMinMaxHeight(0.2F, 0.6F);
		this.theBiomeDecorator.treesPerChunk = 1;
		this.theBiomeDecorator.grassPerChunk = 4;
		//decorations.add(new BiomeDecoration(new WorldGenChunkCustomFlower(BlockCustomFlower.metaOrange)));
		//decorations.add(new BiomeDecoration(new WorldGenChunkCustomFlower(BlockCustomFlower.metaWhite)));
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(5) == 0 ? this.worldGeneratorForest : (par1Random.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees);
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
