package sedridor.mce.blocks;

import sedridor.mce.*;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAutumnLeaves extends BlockLeavesBase
{
	public static final String[] LEAF_TYPES = new String[] {"AutumnLeavesOrange", "AutumnLeavesYellow", "AutumnLeavesRed", "AutumnLeavesBrown"};
	public static final String[][] textureTypes = new String[][] {{"AutumnLeavesOrange", "AutumnLeavesYellow", "AutumnLeavesRed", "AutumnLeavesBrown"}, {"AutumnLeavesOrange2", "AutumnLeavesYellow2", "AutumnLeavesRed2", "AutumnLeavesBrown2"}};
	private Icon[][] iconArray = new Icon[2][];

	private static final int METADATA_BITMASK = 0x3;
	private static final int METADATA_USERPLACEDBIT = 0x4;
	private static final int METADATA_DECAYBIT = 0x8;
	private static final int METADATA_CLEARDECAYBIT = -METADATA_DECAYBIT - 1;

	public static final int metaOrange = 0;
	public static final int metaYellow = 1;
	public static final int metaPurple = 2;
	public static final int metaBrown = 3;

	private int[] adjacentTreeBlocks;

	private static int clearDecayOnMetadata(int metadata)
	{
		return metadata & METADATA_CLEARDECAYBIT;
	}

	private static boolean isDecaying(int metadata)
	{
		return (metadata & METADATA_DECAYBIT) != 0;
	}

	private static boolean isUserPlaced(int metadata)
	{
		return (metadata & METADATA_USERPLACEDBIT) != 0;
	}

	private static int setDecayOnMetadata(int metadata)
	{
		return metadata | METADATA_DECAYBIT;
	}

	private static int unmarkedMetadata(int metadata)
	{
		return metadata & METADATA_BITMASK;
	}

	public BlockAutumnLeaves(int par1)
	{
		super(par1, Material.leaves, false);
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		Block.setBurnProperties(par1, 30, 60);
	}

	@Override
	public int getBlockColor()
	{
		return 0xffffff;
	}

	/**
	 * Returns the color this block should be rendered. Used by leaves.
	 */
	@Override
	public int getRenderColor(int metadata)
	{
		return 0xffffff;
	}

	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		return 0xffffff;
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		int leafDecayRadius = 1;
		int chunkCheckRadius = leafDecayRadius + 1;

		if (par1World.checkChunksExist(par2 - chunkCheckRadius, par3 - chunkCheckRadius, par4 - chunkCheckRadius, par2 + chunkCheckRadius, par3 + chunkCheckRadius, par4 + chunkCheckRadius))
		{
			for (int x1 = -leafDecayRadius; x1 <= leafDecayRadius; ++x1)
			{
				for (int y1 = -leafDecayRadius; y1 <= leafDecayRadius; ++y1)
				{
					for (int z1 = -leafDecayRadius; z1 <= leafDecayRadius; ++z1)
					{
						int id = par1World.getBlockId(par2 + x1, par3 + y1, par4 + z1);

						if (id == Block.leaves.blockID || id == MCE_Biomes.GreenLeaves.blockID || id == MCE_Biomes.AutumnLeaves.blockID)
						{
							int meta = par1World.getBlockMetadata(par2 + x1, par3 + y1, par4 + z1);
							par1World.setBlockMetadataWithNotify(par2 + x1, par3 + y1, par4 + z1, BlockAutumnLeaves.setDecayOnMetadata(meta), 4);
						}
					}
				}
			}
		}
	}

	/*public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlockMetadata(par2, par3, par4, meta | 8);
	}*/

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random) {
		if (!par1World.isRemote)
		{
			int metadata = par1World.getBlockMetadata(x, y, z);

			if (!BlockAutumnLeaves.isUserPlaced(metadata) || BlockAutumnLeaves.isDecaying(metadata))
			{
				int rangeWood = 4;
				int rangeCheckChunk = rangeWood + 1;
				byte var9 = 32;
				int var10 = var9 * var9;
				int var11 = var9 / 2;

				if (this.adjacentTreeBlocks == null)
				{
					this.adjacentTreeBlocks = new int[var9 * var9 * var9];
				}

				if (par1World.checkChunksExist(x - rangeCheckChunk, y - rangeCheckChunk, z - rangeCheckChunk, x + rangeCheckChunk, y + rangeCheckChunk, z + rangeCheckChunk))
				{
					for (int var12 = -rangeWood; var12 <= rangeWood; ++var12)
					{
						for (int var13 = -rangeWood; var13 <= rangeWood; ++var13)
						{
							for (int var14 = -rangeWood; var14 <= rangeWood; ++var14)
							{
								int id = par1World.getBlockId(x + var12, y + var13, z + var14);

								if (MCE_Biomes.isWood(id))
								{
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
								}
								else if (MCE_Biomes.isLeaves(id))
								{
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
								}
								else
								{
									this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
								}
							}
						}
					}

					for (int var12 = 1; var12 <= 4; ++var12)
					{
						for (int var13 = -rangeWood; var13 <= rangeWood; ++var13)
						{
							for (int var14 = -rangeWood; var14 <= rangeWood; ++var14)
							{
								for (int var15 = -rangeWood; var15 <= rangeWood; ++var15)
								{
									if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
									{
										if (this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
										}

										if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
										{
											this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
										}
									}
								}
							}
						}
					}
				}

				if (this.adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11] >= 0)
				{
					par1World.setBlockMetadataWithNotify(x, y, z, BlockAutumnLeaves.clearDecayOnMetadata(metadata), 4);
				}
				else
				{
					this.removeLeaves(par1World, x, y, z);
				}
			}
		}
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && par5Random.nextInt(15) == 1)
		{
			double var6 = par2 + par5Random.nextFloat();
			double var8 = par3 - 0.05D;
			double var10 = par4 + par5Random.nextFloat();
			par1World.spawnParticle("dripWater", var6, var8, var10, 0.0D, 0.0D, 0.0D);
		}
	}

	private void removeLeaves(World world, int x, int y, int z) {
		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(20) == 0 ? 1 : 0;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int metadata, Random par2Random, int par3)
	{
		return MCE_Biomes.Sapling.blockID;
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified items
	 */
	@Override
	public void dropBlockAsItemWithChance(World par1World, int x, int y, int z, int metadata, float chance, int par7)
	{
		if (!par1World.isRemote)
		{
			if (par1World.rand.nextInt(20) == 0)
			{
				this.doSaplingDrop(par1World, x, y, z, metadata, par7);
			}

			if (par1World.rand.nextInt(200) == 0)
			{
				this.dropBlockAsItem_do(par1World, x, y, z, new ItemStack(Item.appleRed, 1, 0));
			}
		}
	}

	private void doSaplingDrop(World par1World, int x, int y, int z, int metadata, int par7) {
		this.dropBlockAsItem_do(par1World, x, y, z, new ItemStack(this.idDropped(metadata, par1World.rand, par7), 1, this.damageDropped(metadata)));
	}

	/**
	 * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
	 * block and l is the block's subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int x, int y, int z, int md)
	{
		if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID)
		{
			par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
			this.dropBlockAsItem_do(par1World, x, y, z, new ItemStack(this.blockID, 1, md & 3));
		}
		else
		{
			super.harvestBlock(par1World, par2EntityPlayer, x, y, z, md);
		}
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int metadata)
	{
		// Autumn saplings and autumn leaves have corresponding metadata
		return BlockAutumnLeaves.unmarkedMetadata(metadata);
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return Block.leaves.isOpaqueCube();
	}

	@Override
	public Icon getIcon(int side, int metadata) {
		return this.iconArray[(this.isOpaqueCube() ? 1 : 0)][BlockAutumnLeaves.unmarkedMetadata(metadata)];
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1BlockAccess, int par2, int par3, int par4, int par5)
	{
		this.graphicsLevel = !Block.leaves.isOpaqueCube(); // fix leaf render bug
		return super.shouldSideBeRendered(par1BlockAccess, par2, par3, par4, par5);
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, BlockAutumnLeaves.metaOrange));
		par3List.add(new ItemStack(par1, 1, BlockAutumnLeaves.metaYellow));
		par3List.add(new ItemStack(par1, 1, BlockAutumnLeaves.metaPurple));
		par3List.add(new ItemStack(par1, 1, BlockAutumnLeaves.metaBrown));
	}

	/**
	 * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	//protected ItemStack createStackedBlock(int par1)
	//{
	//    return new ItemStack(this.blockID, 1, par1 & 3);
	//}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		for (int var2 = 0; var2 < textureTypes.length; ++var2)
		{
			this.iconArray[var2] = new Icon[textureTypes[var2].length];

			for (int var3 = 0; var3 < textureTypes[var2].length; ++var3)
			{
				this.iconArray[var2][var3] = par1IconRegister.registerIcon(textureTypes[var2][var3]);
			}
		}
	}
}
