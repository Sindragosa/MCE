package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenRainforest extends BiomeGenBaseMCE
{
	public BiomeGenRainforest(int par1)
	{
		super(par1);
		this.setTemperatureRainfall(1.1F, 1.4F);
		this.setMinMaxHeight(0.2F, 0.4F);
		this.theBiomeDecorator.treesPerChunk = 15;
		this.theBiomeDecorator.flowersPerChunk = 17;
		this.theBiomeDecorator.grassPerChunk = 17;
		//this.theBiomeDecorator.melonsPerChunk = 1;
		this.theBiomeDecorator.mushroomsPerChunk = 15;
		this.theBiomeDecorator.reedsPerChunk = 20;
		//this.theBiomeDecorator.clayPerChunk = 50;
		//this.theBiomeDecorator.sandPerChunk = -999;
		//this.theBiomeDecorator.sandPerChunk2 = -999;
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(3) == 0 ? this.worldGeneratorSwamp : new WorldGenPalmTree();
	}

	/**
	 * Gets a WorldGen appropriate for this biome.
	 */
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random var1)
	{
		return new WorldGenTallGrass(Block.tallGrass.blockID, 2);
	}

	@Override
	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		WorldGenVines var5 = new WorldGenVines();

		for (int var6 = 0; var6 < 50; ++var6)
		{
			int var7 = par3 + par2Random.nextInt(16) + 8;
			byte var8 = 64;
			int var9 = par4 + par2Random.nextInt(16) + 8;
			var5.generate(par1World, par2Random, var7, var8, var9);
		}
	}
}
