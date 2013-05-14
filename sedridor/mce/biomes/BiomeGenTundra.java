package sedridor.mce.biomes;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenTundra extends BiomeGenBase
{
	public BiomeGenTundra(int par1)
	{
		super(par1);
		//this.spawnableCreatureList.clear();
		this.topBlock = (byte)Block.stone.blockID;
		this.fillerBlock = (byte)Block.gravel.blockID;
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.flowersPerChunk = -999;
		this.theBiomeDecorator.grassPerChunk = -999;
		this.theBiomeDecorator.sandPerChunk = -999;
		this.theBiomeDecorator.sandPerChunk2 = -999;
	}
}
