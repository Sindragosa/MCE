package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenGreenSwamp extends BiomeGenBaseMCE
{
	public BiomeGenGreenSwamp(int par1)
	{
		super(par1);
		this.setTemperatureRainfall(0.75F, 0.9F);
		this.setMinMaxHeight(-0.2F, 0.1F);
		this.theBiomeDecorator.treesPerChunk = 4;
		//this.theBiomeDecorator.grassPerChunk = 4;
		this.theBiomeDecorator.flowersPerChunk = 0;
		this.theBiomeDecorator.deadBushPerChunk = 0;
		this.theBiomeDecorator.mushroomsPerChunk = 8;
		this.theBiomeDecorator.reedsPerChunk = 10;
		this.theBiomeDecorator.clayPerChunk = 1;
		this.theBiomeDecorator.waterlilyPerChunk = 4;
		//decorations.add(new BiomeDecoration(10, new WorldGenChunkLeafPile()));
		//decorations.add(new BiomeDecoration(new WorldGenChunkCustomFlower(BlockCustomFlower.metaHydrangea)));

		//metaHydrangea
		//new WorldGenChunkCatTail() = 80;
		//new WorldGenChunkLeafPile() = 10;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(5) == 0 ? this.worldGeneratorSwamp : new WorldGenGreenSwamp();
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	//public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
	//{
	//    return par1Random.nextInt(10) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
	//}
}
