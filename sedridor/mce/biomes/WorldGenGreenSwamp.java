package sedridor.mce.biomes;

import sedridor.mce.*;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGreenSwamp extends WorldGenerator
{
	public WorldGenGreenSwamp()
	{
		super();
	}

	public WorldGenGreenSwamp(boolean doNotify)
	{
		super(doNotify);
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{

		while (world.getBlockMaterial(x, y - 1, z) == Material.water)
		{
			--y;
		}

		int height = rand.nextInt(4) + 6;
		if (y < 1 || y + height + 1 > 256)
		{
			return false;
		}

		for (int y1 = y; y1 <= y + 1 + height; ++y1)
		{

			if (y1 < 0 && y1 >= 256)
			{
				return false;
			}

			byte clearanceNeededAroundTrunk = 1;

			if (y1 == y)
			{
				clearanceNeededAroundTrunk = 0;
			}

			if (y1 >= (y + 1 + height) - 2)
			{
				clearanceNeededAroundTrunk = 3;
			}

			for (int x1 = x - clearanceNeededAroundTrunk; x1 <= x + clearanceNeededAroundTrunk; ++x1)
			{
				for (int x2 = z - clearanceNeededAroundTrunk; x2 <= z + clearanceNeededAroundTrunk; ++x2)
				{
					int id = world.getBlockId(x1, y1, x2);
					if (Block.blocksList[id] == null || MCE_Biomes.isLeaves(id))
					{
						continue;
					}

					if (id == Block.waterStill.blockID || id == Block.waterMoving.blockID)
					{
						if (y1 > y)
						{
							return false;
						}
					}
					else
					{
						return false;
					}
				}
			}
		}

		int id = world.getBlockId(x, y - 1, z);
		if (id != Block.grass.blockID && id != Block.dirt.blockID || y >= 256 - height - 1)
		{
			return false;
		}

		world.setBlock(x, y - 1, z, Block.dirt.blockID);
		for (int y1 = (y - 3) + height; y1 <= y + height; y1++)
		{
			int posTrunk = y1 - (y + height);
			int canopyRadius = 2 - posTrunk / 2;
			for (int x1 = x - canopyRadius; x1 <= x + canopyRadius; ++x1)
			{
				int xOnRadius = x1 - x;
				for (int z1 = z - canopyRadius; z1 <= z + canopyRadius; ++z1)
				{
					int zOnRadius = z1 - z;
					Block block = Block.blocksList[world.getBlockId(x1, y1, z1)];
					int id2 = world.getBlockId(x1, y1, z1);
					if ((Math.abs(xOnRadius) != canopyRadius || Math.abs(zOnRadius) != canopyRadius || rand.nextInt(2) != 0 && posTrunk != 0) && (block == null || MCE_Biomes.canBeReplacedByLeaves(id2)))
					{
						world.setBlock(x1, y1, z1, Block.leaves.blockID);
					}
				}
			}
		}

		for (int y1 = 0; y1 < height; ++y1)
		{
			int id2 = world.getBlockId(x, y + y1, z);
			if (id2 == 0 || id2 == Block.leaves.blockID || id2 == Block.waterMoving.blockID || id2 == Block.waterStill.blockID)
			{
				world.setBlock(x, y + y1, z, Block.wood.blockID);
			}
		}

		for (int y1 = (y - 3) + height; y1 <= y + height; ++y1)
		{
			int posTrunk = y1 - (y + height);
			int canopyRadius = 2 - posTrunk / 2;
			for (int x1 = x - canopyRadius; x1 <= x + canopyRadius; ++x1)
			{
				for (int z1 = z - canopyRadius; z1 <= z + canopyRadius; ++z1)
				{
					if (world.getBlockId(x1, y1, z1) != Block.leaves.blockID)
					{
						continue;
					}

					if (rand.nextInt(4) == 0 && world.getBlockId(x1 - 1, y1, z1) == 0)
					{
						this.generateVines(world, x1 - 1, y1, z1, 8);
					}

					if (rand.nextInt(4) == 0 && world.getBlockId(x1 + 1, y1, z1) == 0)
					{
						this.generateVines(world, x1 + 1, y1, z1, 2);
					}

					if (rand.nextInt(4) == 0 && world.getBlockId(x1, y1, z1 - 1) == 0)
					{
						this.generateVines(world, x1, y1, z1 - 1, 1);
					}

					if (rand.nextInt(4) == 0 && world.getBlockId(x1, y1, z1 + 1) == 0)
					{
						this.generateVines(world, x1, y1, z1 + 1, 4);
					}
				}
			}
		}
		return true;
	}

	private void generateVines(World world, int x, int y, int z, int metadata)
	{
		this.setBlockAndMetadata(world, x, y, z, Block.vine.blockID, metadata);
		for (int i = 4; world.getBlockId(x, --y, z) == 0 && i > 0; --i)
		{
			this.setBlockAndMetadata(world, x, y, z, Block.vine.blockID, metadata);
		}
	}
}
