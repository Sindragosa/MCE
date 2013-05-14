package sedridor.mce.blocks;

import sedridor.mce.*;
import sedridor.mce.biomes.*;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockSaplingMCE extends BlockFlower
{
	public static final String[] textureTypes = new String[] {"AutumnSaplingOrange", "AutumnSaplingYellow", "AutumnSaplingRed", "AutumnSaplingBrown", "SaplingFir", "SaplingRedwood", "SaplingAcacia"};
	private Icon[] iconArray;

	private static final int METADATA_BITMASK = 0x7;
	private static final int METADATA_MARKBIT = 0x8;

	public static final int metaOrange = 0;
	public static final int metaYellow = 1;
	public static final int metaPurple = 2;
	public static final int metaBrown = 3;
	public static final int metaFir = 4;
	public static final int metaRedWood = 5;
	public static final int metaAcacia = 6;

	private static boolean isMarkedMetadata(int metadata)
	{
		return (metadata & METADATA_MARKBIT) != 0;
	}

	private static int markedMetadata(int metadata)
	{
		return metadata | METADATA_MARKBIT;
	}

	private static int unmarkedMetadata(int metadata)
	{
		return metadata & METADATA_BITMASK;
	}

	/**
	 * The base index in terrain.png corresponding to the fancy version of the leaf texture. This is stored so we can
	 * switch the displayed version between fancy and fast graphics (fast is this index + 1).
	 */
	private int baseIndexInPNG;

	public BlockSaplingMCE(int par1)
	{
		super(par1);
		float var3 = 0.4F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
		{
			super.updateTick(par1World, par2, par3, par4, par5Random);

			if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0)
			{
				int metadata = par1World.getBlockMetadata(par2, par3, par4);

				if (!BlockSaplingMCE.isMarkedMetadata(metadata))
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, BlockSaplingMCE.markedMetadata(metadata), 4);
				}
				else
				{
					this.growTree(par1World, par2, par3, par4, par5Random);
				}
			}
		}
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public Icon getIcon(int side, int metadata)
	{
		if (metadata < 0 || metadata >= this.iconArray.length)
		{
			metadata = 0;
		}
		return this.iconArray[metadata];
	}

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
	 * blockID passed in. Args: blockID
	 */
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int id)
	{
		return id == Block.grass.blockID || id == Block.dirt.blockID;
	}

	/**
	 * Attempts to grow a sapling into a tree
	 */
	public void growTree(World par1World, int x, int y, int z, Random par5Random)
	{
		int metadata = BlockSaplingMCE.unmarkedMetadata(par1World.getBlockMetadata(x, y, z));
		WorldGenerator tree = null;
		int x1 = 0;
		int z1 = 0;
		boolean isHuge = false;

		int soilId = par1World.getBlockId(x, y - 1, z);
		int soilMeta = par1World.getBlockMetadata(x, y - 1, z);

		if (metadata == BlockSaplingMCE.metaOrange)
		{
			if (par5Random.nextInt(20) == 0)
			{
				tree = new WorldGenBigAutumnTree(true, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaOrangeAutumnLeaves, Block.wood.blockID, 0);
			}
			else
			{
				tree = new WorldGenAutumnTree(true, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaOrangeAutumnLeaves, Block.wood.blockID, 0);
			}
		}
		else if (metadata == BlockSaplingMCE.metaYellow)
		{
			if (par5Random.nextInt(20) == 0)
			{
				tree = new WorldGenBigAutumnTree(true, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaYellowAutumnLeaves, Block.wood.blockID, 0);
			}
			else
			{
				tree = new WorldGenAutumnTree(true, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaYellowAutumnLeaves, Block.wood.blockID, 0);
			}
		}

		else if (metadata == BlockSaplingMCE.metaPurple)
		{
			if (par5Random.nextInt(20) == 0)
			{
				tree = new WorldGenBigAutumnTree(true, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaPurpleAutumnLeaves, Block.wood.blockID, 0);
			}
			else
			{
				tree = new WorldGenAutumnTree(true, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaPurpleAutumnLeaves, Block.wood.blockID, 0);
			}
		}

		else if (metadata == BlockSaplingMCE.metaBrown)
		{
			if (par5Random.nextInt(20) == 0)
			{
				tree = new WorldGenBigAutumnTree(true, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaBrownAutumnLeaves, Block.wood.blockID, 0);
			}
			else
			{
				tree = new WorldGenAutumnTree(true, MCE_Biomes.AutumnLeaves.blockID, MCE_Biomes.metaBrownAutumnLeaves, Block.wood.blockID, 0);
			}
		}
		else if (metadata == BlockSaplingMCE.metaAcacia)
		{
			tree = new WorldGenAcacia(true);
		}
		else
		{
			// Check for 2x2 firs and redwoods
			for (x1 = 0; x1 >= -1; --x1)
			{
				for (z1 = 0; z1 >= -1; --z1)
				{
					if (this.isSameSapling(par1World, x + x1, y, z + z1, metadata) && this.isSameSapling(par1World, x + x1 + 1, y, z + z1, metadata) && this.isSameSapling(par1World, x + x1, y, z + z1 + 1, metadata) && this.isSameSapling(par1World, x + x1 + 1, y, z + z1 + 1, metadata))
					{
						if (metadata == BlockSaplingMCE.metaFir)
						{
							tree = new WorldGenFirTree2(true);
						}
						isHuge = true;
						break;
					}
				}
				if (tree != null)
				{
					break;
				}
			}
			if (tree == null && metadata == BlockSaplingMCE.metaFir)
			{
				// Single fir sapling generates 1x1 tree
				z1 = 0;
				x1 = 0;
				tree = new WorldGenFirTree(true);
			}
		}

		if (tree != null)
		{
			if (isHuge)
			{
				par1World.setBlock(x + x1, y, z + z1, 0, 0, 4);
				par1World.setBlock(x + x1 + 1, y, z + z1, 0, 0, 4);
				par1World.setBlock(x + x1, y, z + z1 + 1, 0, 0, 4);
				par1World.setBlock(x + x1 + 1, y, z + z1 + 1, 0, 0, 4);
			}
			else
			{
				par1World.setBlock(x, y, z, 0);
			}

			int offset = isHuge ? 1 : 0;

			if (!tree.generate(par1World, par5Random, x + x1 + offset, y, z + z1 + offset))
			{
				if (isHuge)
				{
					par1World.setBlock(x + x1, y, z + z1, this.blockID, metadata, 4);
					par1World.setBlock(x + x1 + 1, y, z + z1, this.blockID, metadata, 4);
					par1World.setBlock(x + x1, y, z + z1 + 1, this.blockID, metadata, 4);
					par1World.setBlock(x + x1 + 1, y, z + z1 + 1, this.blockID, metadata, 4);
				}
				else
				{
					par1World.setBlock(x, y, z, this.blockID, metadata, 4);
				}
			}
		}
	}

	public boolean isSameSapling(World world, int x, int y, int z, int metadata)
	{
		return world.getBlockId(x, y, z) == this.blockID && BlockSaplingMCE.unmarkedMetadata(world.getBlockMetadata(x, y, z)) == metadata;
	}

	@Override
	public int damageDropped(int metadata)
	{
		return BlockSaplingMCE.unmarkedMetadata(metadata);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (!par1World.isRemote)
		{
			ItemStack equippedItem = par5EntityPlayer.getCurrentEquippedItem();
			int itemDamage = 0;

			if (equippedItem != null && equippedItem.itemID == Item.dyePowder.itemID && equippedItem.getItemDamage() == 15)
			{
				this.growTree(par1World, par2, par3, par4, par1World.rand);
				if (!par5EntityPlayer.capabilities.isCreativeMode)
				{
					--equippedItem.stackSize;
				}
			}
			return true;
		}
		else
		{
			return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0F, 0.0F, 0.0F);
		}
	}

	public boolean onUseBonemeal(World world, int blockID, int x, int y, int z)
	{
		if (blockID == this.blockID)
		{
			if (!world.isRemote)
			{
				this.growTree(world, x, y, z, world.rand);
			}
			return true;
		}
		return false;
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.iconArray = new Icon[BlockSaplingMCE.textureTypes.length];

		for (int var2 = 0; var2 < this.iconArray.length; ++var2)
		{
			this.iconArray[var2] = par1IconRegister.registerIcon(BlockSaplingMCE.textureTypes[var2]);
		}
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, BlockSaplingMCE.metaOrange));
		par3List.add(new ItemStack(par1, 1, BlockSaplingMCE.metaYellow));
		par3List.add(new ItemStack(par1, 1, BlockSaplingMCE.metaPurple));
		par3List.add(new ItemStack(par1, 1, BlockSaplingMCE.metaBrown));
		par3List.add(new ItemStack(par1, 1, BlockSaplingMCE.metaFir));
		par3List.add(new ItemStack(par1, 1, BlockSaplingMCE.metaRedWood));
		par3List.add(new ItemStack(par1, 1, BlockSaplingMCE.metaAcacia));
	}
}
