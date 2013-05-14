package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenRedrockDesert extends BiomeGenBaseMCE
{
	public BiomeGenRedrockDesert(int par1)
	{
		super(par1);
		this.setDisableRain();
		this.setTemperatureRainfall(2.0F, 0.0F);
		this.setMinMaxHeight(0.1F, 0.2F);
		this.spawnableCreatureList.clear();
		this.topBlock = (byte)19;
		this.fillerBlock = (byte)19;
		this.theBiomeDecorator.treesPerChunk = -1;
		this.theBiomeDecorator.grassPerChunk = 1;
		this.theBiomeDecorator.deadBushPerChunk = 10;
		this.theBiomeDecorator.reedsPerChunk = 2;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	 @Override
	 public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	 {
		 return par1Random.nextInt(3) == 0 ? new WorldGenShrub(0, 0) : new WorldGenAcacia(false);
	 }

	 /**
	  * Gets a WorldGen appropriate for this biome.
	  */
	 @Override
	 public WorldGenerator getRandomWorldGenForGrass(Random par1Random)
	 {
		 return par1Random.nextInt(10) == 0 ? new WorldGenDeadBush(Block.deadBush.blockID) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
	 }

}
