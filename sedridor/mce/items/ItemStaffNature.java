package sedridor.mce.items;

import sedridor.mce.*;
import sedridor.mce.blocks.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStaffNature extends Item
{
	public ItemStaffNature(int par1)
	{
		super(par1);
		this.setMaxDamage(49);
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
		{
			return false;
		}
		else
		{
			int var11 = par3World.getBlockId(par4, par5, par6);
			int var12;

			if (var11 == Block.sapling.blockID)
			{
				if (!par3World.isRemote)
				{
					((BlockSapling)Block.sapling).growTree(par3World, par4, par5, par6, par3World.rand);
					par1ItemStack.damageItem(1, par2EntityPlayer);
				}

				return true;
			}
			if (var11 == MCE_Biomes.Sapling.blockID)
			{
				if (!par3World.isRemote)
				{
					((BlockSaplingMCE)MCE_Biomes.Sapling).growTree(par3World, par4, par5, par6, par3World.rand);
					par1ItemStack.damageItem(1, par2EntityPlayer);
				}

				return true;
			}
			if (var11 == Block.mushroomBrown.blockID || var11 == Block.mushroomRed.blockID)
			{
				if (!par3World.isRemote && ((BlockMushroom)Block.blocksList[var11]).fertilizeMushroom(par3World, par4, par5, par6, par3World.rand))
				{
					par1ItemStack.damageItem(1, par2EntityPlayer);
				}

				return true;
			}

			if (var11 == Block.melonStem.blockID || var11 == Block.pumpkinStem.blockID)
			{
				if (par3World.getBlockMetadata(par4, par5, par6) == 7)
				{
					return false;
				}

				if (!par3World.isRemote)
				{
					((BlockStem)Block.blocksList[var11]).fertilizeStem(par3World, par4, par5, par6);
					par1ItemStack.damageItem(1, par2EntityPlayer);
				}

				return true;
			}

			if (var11 == Block.crops.blockID)
			{
				if (par3World.getBlockMetadata(par4, par5, par6) == 7)
				{
					return false;
				}

				if (!par3World.isRemote)
				{
					((BlockCrops)Block.crops).fertilize(par3World, par4, par5, par6);
					par1ItemStack.damageItem(1, par2EntityPlayer);
				}

				return true;
			}

			if (var11 == Block.cocoaPlant.blockID)
			{
				if (!par3World.isRemote)
				{
					par3World.setBlockMetadataWithNotify(par4, par5, par6, 8 | BlockDirectional.getDirection(par3World.getBlockMetadata(par4, par5, par6)), 2);
					par1ItemStack.damageItem(1, par2EntityPlayer);
				}

				return true;
			}

			if (var11 == Block.grass.blockID)
			{
				if (!par3World.isRemote)
				{
					par1ItemStack.damageItem(1, par2EntityPlayer);
					label135:

						for (var12 = 0; var12 < 128; ++var12)
						{
							int var13 = par4;
							int var14 = par5 + 1;
							int var15 = par6;

							for (int var16 = 0; var16 < var12 / 16; ++var16)
							{
								var13 += itemRand.nextInt(3) - 1;
								var14 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
								var15 += itemRand.nextInt(3) - 1;

								if (par3World.getBlockId(var13, var14 - 1, var15) != Block.grass.blockID || par3World.isBlockNormalCube(var13, var14, var15))
								{
									continue label135;
								}
							}

							if (par3World.getBlockId(var13, var14, var15) == 0)
							{
								if (itemRand.nextInt(8) != 0)
								{
									if (Block.tallGrass.canBlockStay(par3World, var13, var14, var15))
									{
										par3World.setBlock(var13, var14, var15, Block.tallGrass.blockID, 1, 3);
									}
								}
								else if (itemRand.nextInt(4) != 0)
								{
									if (Block.plantYellow.canBlockStay(par3World, var13, var14, var15))
									{
										par3World.setBlock(var13, var14, var15, Block.plantYellow.blockID);
									}
								}
								else if (itemRand.nextInt(3) != 0)
								{
									if (Block.plantRed.canBlockStay(par3World, var13, var14, var15))
									{
										par3World.setBlock(var13, var14, var15, Block.plantRed.blockID);
									}
								}
								else if (MCE_Biomes.Flower.canBlockStay(par3World, var13, var14, var15))
								{
									if (itemRand.nextInt(4) != 0)
									{
										par3World.setBlock(var13, var14, var15, MCE_Biomes.Flower.blockID, 0, 3);
									}
									else if (itemRand.nextInt(4) != 0)
									{
										par3World.setBlock(var13, var14, var15, MCE_Biomes.Flower.blockID, 2, 3);
									}
									else if (itemRand.nextInt(2) != 0)
									{
										par3World.setBlock(var13, var14, var15, MCE_Biomes.Flower.blockID, 3, 3);
									}
									else if (itemRand.nextInt(2) != 0)
									{
										par3World.setBlock(var13, var14, var15, MCE_Biomes.Flower.blockID, 4, 3);
									}
									else
									{
										par3World.setBlock(var13, var14, var15, MCE_Biomes.Flower.blockID, 1, 3);
									}
								}
							}
						}
				}

				return true;
			}
			return false;
		}
	}
}
