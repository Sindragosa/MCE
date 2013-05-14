package sedridor.mce.biomes;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenVolcanicIsland extends BiomeGenBase
{
	public BiomeGenVolcanicIsland(int par1)
	{
		super(par1);
		this.theBiomeDecorator.treesPerChunk = -100;
		this.theBiomeDecorator.flowersPerChunk = -100;
		this.theBiomeDecorator.grassPerChunk = -100;
		this.topBlock = (byte)Block.obsidian.blockID;
		this.fillerBlock = (byte)Block.stone.blockID;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
	}
}
