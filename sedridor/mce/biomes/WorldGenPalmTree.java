package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenPalmTree extends WorldGenerator
{
	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		while (par1World.isAirBlock(par3, par4, par5) && par4 > 2)
		{
			--par4;
		}

		int var6 = par1World.getBlockId(par3, par4, par5);

		if (var6 != Block.grass.blockID)
		{
			return false;
		}
		else
		{
			for (int var7 = -2; var7 <= 2; ++var7)
			{
				for (int var8 = -2; var8 <= 2; ++var8)
				{
					if (par1World.isAirBlock(par3 + var7, par4 - 1, par5 + var8) && par1World.isAirBlock(par3 + var7, par4 - 2, par5 + var8))
					{
						return false;
					}
				}
			}

			this.setBlockAndMetadata(par1World, par3, par4 + 1, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 2, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 3, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 4, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 5, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 6, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 6, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 7, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 8, par5, Block.wood.blockID, 3);
			//this.setBlockAndMetadata(par1World, par3, par4 + 10, par5, Block.wood.blockID, 3);
			//this.setBlockAndMetadata(par1World, par3, par4 + 11, par5, Block.wood.blockID, 3);
			//this.setBlockAndMetadata(par1World, par3, par4 + 12, par5, Block.wood.blockID, 3);
			//this.setBlockAndMetadata(par1World, par3, par4 + 13, par5, Block.wood.blockID, 3);
			//this.setBlockAndMetadata(par1World, par3, par4 + 14, par5, Block.wood.blockID, 3);
			//this.setBlockAndMetadata(par1World, par3, par4 + 15, par5, Block.wood.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 10, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 1, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 2, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 3, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 4, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 5, par4 + 8, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5 + 1, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5 + 2, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5 + 3, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5 + 4, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 8, par5 + 5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 1, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 2, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 3, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 4, par4 + 9, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 5, par4 + 8, par5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5 - 1, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5 - 2, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5 - 3, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 9, par5 - 4, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3, par4 + 8, par5 - 5, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 1, par4 + 9, par5 + 1, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 1, par4 + 9, par5 - 1, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 1, par4 + 9, par5 + 1, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 1, par4 + 9, par5 - 1, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 2, par4 + 9, par5 + 2, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 2, par4 + 9, par5 - 2, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 2, par4 + 9, par5 + 2, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 2, par4 + 9, par5 - 2, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 3, par4 + 8, par5 + 3, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 + 3, par4 + 8, par5 - 3, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 3, par4 + 8, par5 + 3, Block.leaves.blockID, 3);
			this.setBlockAndMetadata(par1World, par3 - 3, par4 + 8, par5 - 3, Block.leaves.blockID, 3);
			return true;
		}
	}
}
