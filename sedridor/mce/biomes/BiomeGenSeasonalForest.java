package sedridor.mce.biomes;

import sedridor.mce.*;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenSeasonalForest extends BiomeGenBaseMCE
{
	public BiomeGenSeasonalForest(int par1)
	{
		super(par1);
		this.setTemperatureRainfall(0.7F, 0.8F);
		this.setMinMaxHeight(0.2F, 0.4F);
		//this.setEnableSnow();
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 7, 4, 4));
		this.theBiomeDecorator.treesPerChunk = 10;
		this.theBiomeDecorator.grassPerChunk = 20;
		//decorations.add(new BiomeDecoration(2, new WorldGenChunkCustomFlower(BlockCustomFlower.metaAutumnShrub)));
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		boolean bigTree = (par1Random.nextInt(10) == 0);
		if (par1Random.nextInt(3) == 0)
		{
			// Orange
			return bigTree ? new WorldGenBigAutumnTree(false, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaOrangeAutumnLeaves, Block.wood.blockID, 0) : new WorldGenAutumnTree(false, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaOrangeAutumnLeaves, Block.wood.blockID, 0);
		}
		else if (par1Random.nextInt(3) == 0)
		{
			// Yellow
			return bigTree ? new WorldGenBigAutumnTree(false, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaYellowAutumnLeaves, Block.wood.blockID, 0) : new WorldGenAutumnTree(false, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaYellowAutumnLeaves, Block.wood.blockID, 0);
		}
		else if (par1Random.nextInt(3) == 0)
		{
			// Purple
			return bigTree ? new WorldGenBigAutumnTree(false, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaPurpleAutumnLeaves, Block.wood.blockID, 0) : new WorldGenAutumnTree(false, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaPurpleAutumnLeaves, Block.wood.blockID, 0);
		}
		else if (par1Random.nextInt(3) == 0)
		{
			// Brown
			return bigTree ? new WorldGenBigAutumnTree(false, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaBrownAutumnLeaves, Block.wood.blockID, 0) : new WorldGenAutumnTree(false, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaBrownAutumnLeaves, Block.wood.blockID, 0);
		}

		return bigTree ? new WorldGenBigTree(false) : new WorldGenTrees(false);
		//return (WorldGenerator)(par1Random.nextInt(5) == 0 ? this.worldGeneratorForest : (par1Random.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees));
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
