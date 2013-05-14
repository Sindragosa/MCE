package sedridor.mce.biomes;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTaiga3 extends WorldGenerator
{
	public WorldGenTaiga3(boolean par1)
	{
		super(par1);
	}

	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		int var6 = par2Random.nextInt(15) + 35;
		int var7 = par2Random.nextInt(5) + 10;
		int var8 = var6 - var7;
		int var9 = 3 + par2Random.nextInt(1);
		boolean var10 = true;

		if (par4 >= 1 && par4 + var6 + 1 <= 256)
		{
			int var11;
			int var13;
			int var14;
			int var15;
			int var24;

			for (var11 = par4; var11 <= par4 + 1 + var6 && var10; ++var11)
			{
				boolean var12 = true;

				if (var11 - par4 < var7)
				{
					var24 = 0;
				}
				else
				{
					var24 = var9;
				}

				for (var13 = par3 - var24; var13 <= par3 + var24 && var10; ++var13)
				{
					for (var14 = par5 - var24; var14 <= par5 + var24 && var10; ++var14)
					{
						if (var11 >= 0 && var11 < 256)
						{
							var15 = par1World.getBlockId(var13, var11, var14);

							if (var15 != 0 && var15 != Block.leaves.blockID)
							{
								var10 = false;
							}
						}
						else
						{
							var10 = false;
						}
					}
				}
			}

			if (!var10)
			{
				return false;
			}
			else
			{
				var11 = par1World.getBlockId(par3, par4 - 1, par5);
				var24 = par1World.getBlockId(par3 - 1, par4 - 1, par5);
				var13 = par1World.getBlockId(par3, par4 - 1, par5 - 1);
				var14 = par1World.getBlockId(par3 - 1, par4 - 1, par5 - 1);

				if ((var11 == Block.grass.blockID || var11 == Block.dirt.blockID) && par4 < 256 - var6 - 1)
				{
					if ((var24 == Block.grass.blockID || var24 == Block.dirt.blockID) && par4 < 256 - var6 - 1)
					{
						if ((var13 == Block.grass.blockID || var24 == Block.dirt.blockID) && par4 < 256 - var6 - 1)
						{
							if ((var14 == Block.grass.blockID || var24 == Block.dirt.blockID) && par4 < 256 - var6 - 1)
							{
								this.setBlock(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
								this.setBlock(par1World, par3 - 1, par4 - 1, par5, Block.dirt.blockID);
								this.setBlock(par1World, par3, par4 - 1, par5 - 1, Block.dirt.blockID);
								this.setBlock(par1World, par3 - 1, par4 - 1, par5 - 1, Block.dirt.blockID);
								var15 = par2Random.nextInt(2);
								int var16 = 1;
								byte var17 = 0;
								int var19;
								int var18;
								int var20;

								for (var18 = 0; var18 <= var8; ++var18)
								{
									var19 = par4 + var6 - var18;

									for (var20 = par3 - var15; var20 <= par3 + var15; ++var20)
									{
										int var21 = var20 - par3;

										for (int var22 = par5 - var15; var22 <= par5 + var15; ++var22)
										{
											int var23 = var22 - par5;

											if ((Math.abs(var21) != var15 || Math.abs(var23) != var15 || var15 <= 0) && !Block.opaqueCubeLookup[par1World.getBlockId(var20, var19, var22)])
											{
												this.setBlockAndMetadata(par1World, var20, var19, var22, Block.leaves.blockID, 1);
												this.setBlockAndMetadata(par1World, var20 - 1, var19, var22, Block.leaves.blockID, 1);
												this.setBlockAndMetadata(par1World, var20, var19, var22 - 1, Block.leaves.blockID, 1);
												this.setBlockAndMetadata(par1World, var20 - 1, var19, var22 - 1, Block.leaves.blockID, 1);
											}
										}
									}

									if (var15 >= var16)
									{
										var15 = var17;
										var17 = 1;
										++var16;

										if (var16 > var9)
										{
											var16 = var9;
										}
									}
									else
									{
										++var15;
									}
								}

								var18 = par2Random.nextInt(3);

								for (var19 = 0; var19 < var6 - var18; ++var19)
								{
									var20 = par1World.getBlockId(par3, par4 + var19, par5);

									if (var20 == 0 || var20 == Block.leaves.blockID)
									{
										this.setBlockAndMetadata(par1World, par3, par4 + var19, par5, Block.wood.blockID, 1);
										this.setBlockAndMetadata(par1World, par3 - 1, par4 + var19, par5, Block.wood.blockID, 1);
										this.setBlockAndMetadata(par1World, par3, par4 + var19, par5 - 1, Block.wood.blockID, 1);
										this.setBlockAndMetadata(par1World, par3 - 1, par4 + var19, par5 - 1, Block.wood.blockID, 1);
									}
								}

								return true;
							}
							else
							{
								return false;
							}
						}
						else
						{
							return false;
						}
					}
					else
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
		else
		{
			return false;
		}
	}
}
