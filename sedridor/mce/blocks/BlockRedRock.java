package sedridor.mce.blocks;

import sedridor.mce.*;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockRedRock extends Block
{
	public static final String[] textureTypes = new String[] {"RedRock", "RedRockCobblestone", "RedRockBrick"};
	private Icon[] iconArray;

	public static final int	metaRedRock = 0;
	public static final int	metaRedCobble = 1;
	public static final int	metaRedRockBrick = 2;

	public BlockRedRock(int par1) {
		super(par1, MCE_Biomes.redrockMaterial);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public Icon getIcon(int side, int metadata)
	{
		if (metadata < 0 || metadata >= this.iconArray.length)
		{
			metadata = 0;
		}
		return this.iconArray[metadata];
	}

	@Override
	public float getExplosionResistance(Entity par1Entity)
	{
		return super.getExplosionResistance(par1Entity);
	}

	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4)
	{
		int metadata = par1World.getBlockMetadata(par2, par3, par4);
		return metadata == BlockRedRock.metaRedCobble ? 2.0F : super.getBlockHardness(par1World, par2, par3, par4);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata == BlockRedRock.metaRedRockBrick ? BlockRedRock.metaRedRockBrick : BlockRedRock.metaRedCobble;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return this.blockID;
	}

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return this.blockID;
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, BlockRedRock.metaRedRock));
		par3List.add(new ItemStack(par1, 1, BlockRedRock.metaRedCobble));
		par3List.add(new ItemStack(par1, 1, BlockRedRock.metaRedRockBrick));
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.iconArray = new Icon[BlockRedRock.textureTypes.length];
		for (int var2 = 0; var2 < this.iconArray.length; ++var2)
		{
			this.iconArray[var2] = par1IconRegister.registerIcon(BlockRedRock.textureTypes[var2]);
		}
	}
}
