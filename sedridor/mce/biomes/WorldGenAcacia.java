package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAcacia extends WorldGenerator
{
	/** The minimum height of a generated tree. */
	private final int minTreeHeight;

	/** True if this tree should grow Vines. */
	private final boolean vinesGrow;

	/** The metadata value of the wood to use in tree generation. */
	private final int metaWood;

	/** The metadata value of the leaves to use in tree generation. */
	private final int metaLeaves;

	public WorldGenAcacia(boolean par1)
	{
		this(par1, 4, 0, 0, false);
	}

	public WorldGenAcacia(boolean doNotify, int minTreeHeight, int metaWood, int metaLeaves, boolean vinesGrow)
	{
		super(doNotify);
		this.minTreeHeight = minTreeHeight;
		this.metaWood = metaWood;
		this.metaLeaves = metaLeaves;
		this.vinesGrow = vinesGrow;
	}

	@Override
	public boolean generate(World par1World, Random par2Random, int posX, int posY, int posZ)
	{
		int height = par2Random.nextInt(6) + this.minTreeHeight;
		boolean canGrow = true;

		if (posY >= 1 && posY + height + 1 <= 256)
		{
			int var8;
			byte clearance;
			int var11;
			int distanceFromTop;

			for (var8 = posY; var8 <= posY + 1 + height; ++var8)
			{
				clearance = 1;

				if (var8 == posY)
				{
					clearance = 0;
				}

				if (var8 >= posY + 1 + height - 2)
				{
					clearance = 2;
				}

				for (int var10 = posX - clearance; var10 <= posX + clearance && canGrow; ++var10)
				{
					for (var11 = posZ - clearance; var11 <= posZ + clearance && canGrow; ++var11)
					{
						if (var8 >= 0 && var8 < 256)
						{
							distanceFromTop = par1World.getBlockId(var10, var8, var11);

							if (distanceFromTop != 0 && distanceFromTop != Block.leaves.blockID && distanceFromTop != Block.grass.blockID && distanceFromTop != Block.dirt.blockID && distanceFromTop != Block.wood.blockID)
							{
								canGrow = false;
							}
						}
						else
						{
							canGrow = false;
						}
					}
				}
			}

			if (!canGrow)
			{
				return false;
			}
			else
			{
				var8 = par1World.getBlockId(posX, posY - 1, posZ);

				if ((var8 == Block.grass.blockID || var8 == Block.dirt.blockID) && posY < 256 - height - 1)
				{
					par1World.setBlock(posX, posY - 1, posZ, Block.dirt.blockID);
					clearance = 3;
					byte minCanopyRadius = 0;
					int canopyRadius;
					int var14;
					int var15;

					for (var11 = posY - clearance + height; var11 <= posY + height; ++var11)
					{
						distanceFromTop = var11 - (posY + height);
						canopyRadius = minCanopyRadius + 1 - distanceFromTop;

						for (var14 = posX - canopyRadius; var14 <= posX + canopyRadius; ++var14)
						{
							var15 = var14 - posX;

							for (int var16 = posZ - canopyRadius; var16 <= posZ + canopyRadius; ++var16)
							{
								int var17 = var16 - posZ;

								if ((Math.abs(var15) != canopyRadius || Math.abs(var17) != canopyRadius || par2Random.nextInt(2) != 0 && distanceFromTop != 0) && !Block.opaqueCubeLookup[par1World.getBlockId(var14, var11, var16)])
								{
									this.setBlockAndMetadata(par1World, var14, var11, var16, Block.leaves.blockID, this.metaLeaves);
								}
							}
						}
					}

					for (var11 = 0; var11 < height; ++var11)
					{
						distanceFromTop = par1World.getBlockId(posX, posY + var11, posZ);

						if (distanceFromTop == 0 || distanceFromTop == Block.leaves.blockID)
						{
							this.setBlockAndMetadata(par1World, posX, posY + var11, posZ, Block.wood.blockID, this.metaWood);

							if (this.vinesGrow && var11 > 0)
							{
								if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(posX - 1, posY + var11, posZ))
								{
									this.setBlockAndMetadata(par1World, posX - 1, posY + var11, posZ, Block.vine.blockID, 8);
								}

								if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(posX + 1, posY + var11, posZ))
								{
									this.setBlockAndMetadata(par1World, posX + 1, posY + var11, posZ, Block.vine.blockID, 2);
								}

								if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(posX, posY + var11, posZ - 1))
								{
									this.setBlockAndMetadata(par1World, posX, posY + var11, posZ - 1, Block.vine.blockID, 1);
								}

								if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(posX, posY + var11, posZ + 1))
								{
									this.setBlockAndMetadata(par1World, posX, posY + var11, posZ + 1, Block.vine.blockID, 4);
								}
							}
						}
					}

					if (this.vinesGrow)
					{
						for (var11 = posY - 3 + height; var11 <= posY + height; ++var11)
						{
							distanceFromTop = var11 - (posY + height);
							canopyRadius = 2 - distanceFromTop / 2;

							for (var14 = posX - canopyRadius; var14 <= posX + canopyRadius; ++var14)
							{
								for (var15 = posZ - canopyRadius; var15 <= posZ + canopyRadius; ++var15)
								{
									if (par1World.getBlockId(var14, var11, var15) == Block.leaves.blockID)
									{
										if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14 - 1, var11, var15) == 0)
										{
											this.growVines(par1World, var14 - 1, var11, var15, 8);
										}

										if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14 + 1, var11, var15) == 0)
										{
											this.growVines(par1World, var14 + 1, var11, var15, 2);
										}

										if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14, var11, var15 - 1) == 0)
										{
											this.growVines(par1World, var14, var11, var15 - 1, 1);
										}

										if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var14, var11, var15 + 1) == 0)
										{
											this.growVines(par1World, var14, var11, var15 + 1, 4);
										}
									}
								}
							}
						}
					}

					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * Grows vines downward from the given block for a given length. Args: World, x, starty, z, vine-length
	 */
	 private void growVines(World par1World, int posX, int posY, int posZ, int vineLength)
	{
		this.setBlockAndMetadata(par1World, posX, posY, posZ, Block.vine.blockID, vineLength);
		int var6 = 4;

		while (true)
		{
			--posY;

			if (par1World.getBlockId(posX, posY, posZ) != 0 || var6 <= 0)
			{
				return;
			}

			this.setBlockAndMetadata(par1World, posX, posY, posZ, Block.vine.blockID, vineLength);
			--var6;
		}
	}
}
