package sedridor.mce.biomes;

import sedridor.mce.*;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAutumnTree extends WorldGenerator {

	private final int baseHeight = 4;
	private final int canopyHeight = 3;
	private final int canopyRadius_extraRadius = 0;
	private final int maxVarianceHeight = 2;

	private final int leaf;
	private final int wood;
	private final int leafMeta;
	private final int woodMeta;

	public WorldGenAutumnTree(boolean doNotify, int leafMeta, int woodMeta)
	{
		super(doNotify);
		this.leaf = Block.leaves.blockID;
		this.wood = Block.wood.blockID;
		this.leafMeta = leafMeta;
		this.woodMeta = woodMeta;
	}

	public WorldGenAutumnTree(boolean doNotify, int leaf, int leafMeta, int wood, int woodMeta)
	{
		super(doNotify);
		this.leaf = leaf;
		this.wood = wood;
		this.leafMeta = leafMeta;
		this.woodMeta = woodMeta;
	}

	private static boolean isBlockSuitableForGrowing(World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
		return id == Block.grass.blockID || id == Block.dirt.blockID;
	}

	private static boolean isRoomToGrow(World world, int x, int y, int z, int height)
	{
		for (int i = y; i <= y + 1 + height; ++i)
		{
			if (i < 0 || i >= 256)
			{
				return false;
			}

			int radius = 1;

			if (i == y)
			{
				radius = 0;
			}

			if (i >= y + 1 + height - 2)
			{
				radius = 2;
			}

			for (int x1 = x - radius; x1 <= x + radius; ++x1)
			{
				for (int z1 = z - radius; z1 <= z + radius; ++z1)
				{
					int id = world.getBlockId(x1, i, z1);
					if (Block.blocksList[id] != null && !MCE_Biomes.isLeaves(id) && id != Block.grass.blockID && id != Block.dirt.blockID && !MCE_Biomes.isWood(id))
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int height = rand.nextInt(this.maxVarianceHeight + 1) + this.baseHeight;

		if (y < 1 || y + height + 1 > 256)
		{
			return false;
		}

		if (!WorldGenAutumnTree.isBlockSuitableForGrowing(world, x, y - 1, z))
		{
			return false;
		}

		if (!WorldGenAutumnTree.isRoomToGrow(world, x, y, z, height))
		{
			return false;
		}

		world.setBlock(x, y - 1, z, Block.dirt.blockID);
		this.growLeaves(world, rand, x, y, z, height);
		this.growTrunk(world, x, y, z, height);

		return true;
	}

	private void growLeaves(World world, Random rand, int x, int y, int z, int height) {
		for (int y1 = y - this.canopyHeight + height; y1 <= y + height; ++y1)
		{
			int canopyRow = y1 - (y + height);
			int radius = this.canopyRadius_extraRadius + 1 - canopyRow / 2;

			for (int x1 = x - radius; x1 <= x + radius; ++x1)
			{
				int xDistanceFromTrunk = x1 - x;
				for (int z1 = z - radius; z1 <= z + radius; ++z1)
				{
					int zDistanceFromTrunk = z1 - z;
					Block block = Block.blocksList[world.getBlockId(x1, y1, z1)];

					if ((Math.abs(xDistanceFromTrunk) != radius || Math.abs(zDistanceFromTrunk) != radius || rand.nextInt(2) != 0 && canopyRow != 0) && (block == null || !Block.opaqueCubeLookup[world.getBlockId(x1, y1, z1)]))
					{
						this.setBlockAndMetadata(world, x1, y1, z1, this.leaf, this.leafMeta);
					}
				}
			}
		}
	}

	private void growTrunk(World world, int x, int y, int z, int height)
	{
		for (int y1 = 0; y1 < height; ++y1)
		{
			int id = world.getBlockId(x, y + y1, z);
			if (Block.blocksList[id] == null || MCE_Biomes.isLeaves(id))
			{
				this.setBlockAndMetadata(world, x, y + y1, z, this.wood, this.woodMeta);
			}
		}
	}
}


/*
	import extrabiomes.blocks.BlockAutumnLeaves;
	import extrabiomes.blocks.BlockCatTail;
	import extrabiomes.blocks.BlockCustomFlower;
	import extrabiomes.blocks.BlockCustomSapling;
	import extrabiomes.blocks.BlockCustomTallGrass;
	import extrabiomes.blocks.BlockGreenLeaves;
	import extrabiomes.blocks.BlockLeafPile;
	import extrabiomes.blocks.BlockRedRock;

	Proxy.addRecipe(new ItemStack(Block.leaves), new Object[] {"###", "###", "###", Character.valueOf('#'), new ItemStack(ExtrabiomesBlock.leafPile)});
	Proxy.addName(ExtrabiomesBlock.catTail, "Cat Tail");
	Proxy.addName(ExtrabiomesBlock.leafPile, "Leaf Pile");

	Proxy.addName(new ItemStack(ExtrabiomesBlock.redRock, 1, BlockRedRock.metaRedRock), "Red Rock");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.redRock, 1, BlockRedRock.metaRedCobble), "Red Cobblestone");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.redRock, 1, BlockRedRock.metaRedRockBrick), "Red Rock Brick");

	Proxy.addName(new ItemStack(ExtrabiomesBlock.autumnLeaves, 1, 0), "Brown Autumn Leaves");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.autumnLeaves, 1, 1), "Orange Autumn Leaves");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.autumnLeaves, 1, 2), "Purple Autumn Leaves");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.autumnLeaves, 1, 3), "Yellow Autumn Leaves");

	Proxy.addName(new ItemStack(ExtrabiomesBlock.flower, 1, 0), "Autumn Shrub");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.flower, 1, 1), "Hydrangea");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.flower, 1, 2), "Orange Flower");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.flower, 1, 3), "Purple Flower");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.flower, 1, 4), "Tiny Cactus");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.flower, 1, 5), "Root");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.flower, 1, 6), "Toad Stool");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.flower, 1, 7), "White Flower");

	Proxy.addName(new ItemStack(ExtrabiomesBlock.greenLeaves, 1, 0), "Fir Leaves");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.greenLeaves, 1, 1), "Redwood Leaves");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.greenLeaves, 1, 2), "Acacia Leaves");

	Proxy.addName(new ItemStack(ExtrabiomesBlock.sapling, 1, 0), "Brown Autumn Sapling");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.sapling, 1, 1), "Orange Autumn Sapling");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.sapling, 1, 2), "Purple Autumn Sapling");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.sapling, 1, 3), "Yellow Autumn Sapling");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.sapling, 1, 4), "Fir Sapling");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.sapling, 1, 5), "Redwood Sapling");
	Proxy.addName(new ItemStack(ExtrabiomesBlock.sapling, 1, 6), "Acacia Sapling");

	Property redRock = Config.getProperty("redrock.id", Configuration.CATEGORY_BLOCK, String.valueOf(redRockID));
	redRock.value = String.valueOf(redRockID);
	redRock.comment = "Due to a hole in the 4096 patch redrock.id must be set to a value less than 256.";

	ExtrabiomesBlock.autumnLeaves = new BlockAutumnLeaves(autumnLeavesID).setBlockName("autumnleaves");
	ExtrabiomesBlock.greenLeaves = new BlockGreenLeaves(greenLeavesID).setBlockName("greenleaves");
	ExtrabiomesBlock.flower = new BlockCustomFlower(flowerID).setBlockName("flower");
	ExtrabiomesBlock.grass = new BlockCustomTallGrass(grassID).setBlockName("grass");
	ExtrabiomesBlock.sapling = new BlockCustomSapling(saplingID).setBlockName("sapling");
	ExtrabiomesBlock.catTail = new BlockCatTail(catTailID).setBlockName("cattail");
	ExtrabiomesBlock.leafPile = new BlockLeafPile(leafPileID).setBlockName("leafpile");

	Proxy.registerBlock(ExtrabiomesBlock.leafPile);

	Proxy.registerBlock(ExtrabiomesBlock.redRock, MultiItemBlock.class);
	Proxy.registerBlock(ExtrabiomesBlock.autumnLeaves, ItemCustomLeaves.class);
	Proxy.registerBlock(ExtrabiomesBlock.catTail, ItemCatTail.class);
	Proxy.registerBlock(ExtrabiomesBlock.flower, MultiItemBlock.class);
	Proxy.registerBlock(ExtrabiomesBlock.grass, MultiItemBlock.class);
	Proxy.registerBlock(ExtrabiomesBlock.greenLeaves, ItemCustomLeaves.class);
	Proxy.registerBlock(ExtrabiomesBlock.sapling, MultiItemBlock.class);


	decorations.add(new BiomeDecoration(2, new WorldGenChunkCustomFlower(BlockCustomFlower.metaAutumnShrub)));

	greenHillsDecorations
	{
		decorations.add(new BiomeDecoration(new WorldGenChunkCustomFlower(BlockCustomFlower.metaOrange)));
		decorations.add(new BiomeDecoration(new WorldGenChunkCustomFlower(BlockCustomFlower.metaWhite)));
	}

	greenSwamp
		decorations.add(new BiomeDecoration(new WorldGenChunkCustomFlower(BlockCustomFlower.metaHydrangea)));
		decorations.add(new BiomeDecoration(15, new WorldGenChunkCustomFlower(BlockCustomFlower.metaRoot)));
		decorations.add(new BiomeDecoration(999, new WorldGenChunkCatTail()));
		decorations.add(new BiomeDecoration(10, new WorldGenChunkLeafPile()));

	savanna
		decorations.add(new BiomeDecoration(new WorldGenChunkCustomFlower(BlockCustomFlower.metaPurple)));

	newVillageSpawnBiomes.addAll(MapGenVillage.villageSpawnBiomes);

	forestedHills
	forestedIsland
	glacier
	greenHills
	greenSwamp
	pineForest
	rainForest
	savanna
	tundra
	redwoodForest
	autumnWoods

	autumnLeaves
 * 0 - Brown
 * 1 - Orange
 * 2 - Purple
 * 3 - Yellow

	catTail

	flower
 * 0 - Autumn Shrub
 * 1 - Hydrangea
 * 2 - Orange Flower
 * 3 - Purple Flower
 * 4 - Tiny Cactus
 * 5 - Root
 * 6 - Toadstool
 * 7 - White Flower

	greenLeaves
 * 0 - Fir
 * 1 - Redwood
 * 2 - Acacia

	leafPile
	redRock

	sapling
 * 0 - Brown Autumn
 * 1 - Orange Autumn
 * 2 - Purple Autumn
 * 3 - Yellow Autumn
 * 4 - Fir
 * 5 - Redwood
 * 6 - Acacia
 */