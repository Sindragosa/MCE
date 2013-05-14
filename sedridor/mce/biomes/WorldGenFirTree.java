/**
 * Copyright (c) Scott Killen and MisterFiber, 2012
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license
 * located in /MMPL-1.0.txt
 */

package sedridor.mce.biomes;

import sedridor.mce.*;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFirTree extends WorldGenerator
{
	private static int blockLeaf;
	private static int metaLeaf;
	private static int blockWood;
	private static int metaWood;

	public WorldGenFirTree(boolean doNotify)
	{
		super(doNotify);
		WorldGenFirTree.blockLeaf = MCE_Biomes.GreenLeaves.blockID;
		WorldGenFirTree.metaLeaf = MCE_Biomes.metaFirLeaves;
		WorldGenFirTree.blockWood = Block.wood.blockID;
		WorldGenFirTree.metaWood = 1;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int below = world.getBlockId(x, y - 1, z);
		int height = rand.nextInt(8) + 14;

		if (!MCE_Biomes.treesCanGrowOnID(below) || y >= 256 - height - 1)
		{
			return false;
		}

		if (y < 1 || y + height + 1 > 256)
		{
			return false;
		}

		int j = 1 + rand.nextInt(12);
		int l = 2 + rand.nextInt(6);

		for (int i1 = y; i1 <= y + 1 + height; i1++)
		{
			if (i1 < 0 || i1 >= 256)
			{
				return false;
			}

			int k1 = 1;
			if (i1 - y < j)
			{
				k1 = 0;
			}
			else
			{
				k1 = l;
			}

			for (int x1 = x - k1; x1 <= x + k1; x1++)
			{
				for (int z1 = z - k1; z1 <= z + k1; z1++)
				{
					int id = world.getBlockId(x1, i1, z1);
					if (Block.blocksList[id] != null && !MCE_Biomes.isLeaves(id))
					{
						return false;
					}
				}
			}
		}

		world.setBlock(x, y - 1, z, Block.dirt.blockID);
		int l1 = rand.nextInt(2);
		int j2 = 1;
		boolean flag1 = false;

		for (int i3 = 0; i3 <= height - j; i3++)
		{
			int k3 = y + height - i3;
			for (int i4 = x - l1; i4 <= x + l1; i4++)
			{
				int k4 = i4 - x;
				for (int l4 = z - l1; l4 <= z + l1; l4++)
				{
					int i5 = l4 - z;
					Block block = Block.blocksList[world.getBlockId(i4, k3, l4)];
					if ((Math.abs(k4) != l1 || Math.abs(i5) != l1 || l1 <= 0) && (block == null || !Block.opaqueCubeLookup[world.getBlockId(i4, k3, l4)]))
					{
						setBlockAndMetadata(world, i4, k3, l4, WorldGenFirTree.blockLeaf, WorldGenFirTree.metaLeaf);
					}
				}
			}

			if (l1 >= j2)
			{
				l1 = flag1 ? 1 : 0;
				flag1 = true;
				if (++j2 > l)
				{
					j2 = l;
				}
			}
			else
			{
				l1++;
			}
		}

		int j3 = rand.nextInt(3);
		for (int l3 = 0; l3 < height - j3; l3++)
		{
			int id = world.getBlockId(x, y + l3, z);
			if (Block.blocksList[id] == null || MCE_Biomes.isLeaves(id))
			{
				setBlockAndMetadata(world, x, y + l3, z, WorldGenFirTree.blockWood, WorldGenFirTree.metaWood);
			}
		}

		return true;
	}
}