package sedridor.mce.world;

import java.util.Arrays;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.world.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldTypeMCE extends WorldType
{
	//public static final BiomeGenBase[] base11Biomes = new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga};
	//public static final BiomeGenBase[] base12Biomes = ObjectArrays.concat(base11Biomes, BiomeGenBase.jungle);

	/** List of world types. */
	//public static final WorldTypeMCE[] worldTypes = new WorldTypeMCE[16];

	/** Default world type. */
	//public static final WorldTypeMCE DEFAULT = (new WorldTypeMCE(0, "default", 1)).setVersioned();

	/** Flat world type. */
	//public static final WorldTypeMCE FLAT = new WorldTypeMCE(1, "flat");

	/** Large Biome world Type. */
	//public static final WorldTypeMCE LARGE_BIOMES = new WorldTypeMCE(2, "largeBiomes");

	/** Default (1.1) world type. */
	//public static final WorldTypeMCE DEFAULT_1_1 = (new WorldTypeMCE(8, "default_1_1", 0)).setCanBeCreated(false);

	protected BiomeGenBase[] biomesForWorldType;

	public WorldTypeMCE(int par1, String par2Str)
	{
		this(par1, par2Str, 0);
	}

	public WorldTypeMCE(int par1, String par2Str, int par3)
	{
		super(par1, par2Str, par3);
		//        this.worldType = par2Str;
		//        this.generatorVersion = par3;
		//        this.canBeCreated = true;
		//        this.worldTypeId = par1;
		//        worldTypes[par1] = this;
		//
		//        switch (par1)
		//        {
		//            case 8:
		//                biomesForWorldType = base11Biomes;
		//                break;
		//            default:
		//                biomesForWorldType = base12Biomes;
		//        }
	}

	@Override
	public WorldChunkManager getChunkManager(World world)
	{
		if (this == FLAT)
		{
			FlatGeneratorInfo flatgeneratorinfo = FlatGeneratorInfo.createFlatGeneratorFromString(world.getWorldInfo().getGeneratorOptions());
			return new WorldChunkManagerHell(BiomeGenBase.biomeList[flatgeneratorinfo.getBiome()], 0.5F, 0.5F);
		}
		else
		{
			return new WorldChunkManager(world);
		}
	}

	@Override
	public IChunkProvider getChunkGenerator(World world, String generatorOptions)
	{
		return (this == FLAT ? new ChunkProviderFlat(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions) : new ChunkProviderGenerate(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled()));
	}

	@Override
	public int getMinimumSpawnHeight(World world)
	{
		return this == FLAT ? 4 : 64;
	}

	@Override
	public double getHorizon(World world)
	{
		return this == FLAT ? 0.0D : 63.0D;
	}

	@Override
	public boolean hasVoidParticles(boolean flag)
	{
		return this != FLAT && !flag;
	}

	@Override
	public double voidFadeMagnitude()
	{
		return this == FLAT ? 1.0D : 0.03125D;
	}

	@Override
	public BiomeGenBase[] getBiomesForWorldType() {
		return biomesForWorldType;
	}

	@Override
	public void addNewBiome(BiomeGenBase biome)
	{
		Set<BiomeGenBase> newBiomesForWorld = Sets.newLinkedHashSet(Arrays.asList(biomesForWorldType));
		newBiomesForWorld.add(biome);
		biomesForWorldType = newBiomesForWorld.toArray(new BiomeGenBase[0]);
	}

	@Override
	public void removeBiome(BiomeGenBase biome)
	{
		Set<BiomeGenBase> newBiomesForWorld = Sets.newLinkedHashSet(Arrays.asList(biomesForWorldType));
		newBiomesForWorld.remove(biome);
		biomesForWorldType = newBiomesForWorld.toArray(new BiomeGenBase[0]);
	}

	/**
	 * Called when 'Create New World' button is pressed before starting game
	 */
	 @Override
	 public void onGUICreateWorldPress() { }

	 /**
	  * Gets the spawn fuzz for players who join the world.
	  * Useful for void world types.
	  * @return Fuzz for entity initial spawn in blocks.
	  */
	 @Override
	 public int getSpawnFuzz()
	 {
		 return 20;
	 }
}
