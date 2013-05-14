package sedridor.mce.blocks;

import sedridor.mce.*;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInclined extends Block
{
	public static final String[] blockType = new String[] {
		"stone", "cobblestone", "brick", "stoneBrick",
		"sand", "grass", "dirt", "gravel",
		"sandStone", "sandStone", "sandStone", "blockClay",
		"planks", "planks", "planks", "planks",
		"blockSnow", "ice", "whiteStone", "obsidian"
	};

	// blockHardness, blockResistance
	public BlockInclined(int par1, Block par2Block)
	{
		super(par1, par2Block.blockMaterial);
		this.setHardness(par2Block.getBlockHardness(null, 0, 0, 0));
		this.setResistance(par2Block.getExplosionResistance(null) * 5.0F / 3.0F);
		this.setStepSound(par2Block.stepSound);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.slipperiness = par2Block.slipperiness;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return MCE_Items.inclinedModelID;
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public Icon getIcon(int par1, int par2)
	{
		/**
		 * Which side was hit. If its -1 then it went the full length of the ray trace. Bottom = 0, Top = 1, East = 2, West
		 * = 3, North = 4, South = 5.
		 */
		//System.out.println("MCE...getBlockTextureFromSideAndMetadata " + this.blockID + " Meta" + par2 + " " + this.getBlockName());
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		int modelMeta = MCE_Items.modelsMeta[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		return modelBlock.getIcon(par1, modelMeta);
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	//    public Icon getBlockTextureFromSide(int par1)
	//    {
	//        return super.getBlockTextureFromSide(par1);
	//    }

	/**
	 * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	 */
	@Override
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		int modelMeta = MCE_Items.modelsMeta[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			return modelBlock.getIcon(par5, modelMeta);
		}
		else
		{
			return super.getIcon(par5, 0);
		}
	}

	@Override
	public int getBlockColor()
	{
		return super.getBlockColor();
	}

	/**
	 * Returns the color this block should be rendered. Used by leaves.
	 */
	@Override
	public int getRenderColor(int par1)
	{
		return this.getBlockColor();
	}

	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock == Block.grass)
		{
			return Block.grass.colorMultiplier(par1IBlockAccess, par2, par3, par4);
		}
		else
		{
			return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
		}
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			modelBlock.randomDisplayTick(par1World, par2, par3, par4, par5Random);
		}
		else
		{
			super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
		}
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			modelBlock.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
		}
		else
		{
			super.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
		}
	}

	/**
	 * Called right before the block is destroyed by a player.  Args: world, x, y, z, metaData
	 */
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			modelBlock.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
		}
		else
		{
			super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
		}
	}

	/**
	 * Returns the block hardness at a location. Args: world, x, y, z
	 */
	@Override
	public float getBlockHardness(World par1World, int par2, int par3, int par4)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			return modelBlock.getBlockHardness(par1World, par2, par3, par4);
		}
		else
		{
			return super.getBlockHardness(par1World, par2, par3, par4);
		}
	}

	/**
	 * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
	 * EntityPlayer.
	 */
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		int modelMeta = MCE_Items.modelsMeta[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			float var6 = this.getBlockHardness(par2World, par3, par4, par5);
			return var6 < 0.0F ? 0.0F : (!par1EntityPlayer.canHarvestBlock(modelBlock) ? par1EntityPlayer.getCurrentPlayerStrVsBlock(modelBlock, false, modelMeta) / var6 / 100.0F : par1EntityPlayer.getCurrentPlayerStrVsBlock(modelBlock, true, modelMeta) / var6 / 30.0F);
		}
		else
		{
			float var6 = this.getBlockHardness(par2World, par3, par4, par5);
			return var6 < 0.0F ? 0.0F : (!par1EntityPlayer.canHarvestBlock(this) ? par1EntityPlayer.getCurrentPlayerStrVsBlock(this, false, 0) / var6 / 100.0F : par1EntityPlayer.getCurrentPlayerStrVsBlock(this, true, 0) / var6 / 30.0F);
		}
	}

	/**
	 * Returns how much this block can resist explosions from the passed in entity.
	 */
	public float getExplosionResistance(Entity par1Entity, World par2World, int par3, int par4, int par5)
	{
		int blockID = par2World.getBlockId(par3, par4, par5);
		Block modelBlock = MCE_Items.models[(blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140)];
		if (modelBlock != null)
		{
			return modelBlock.getExplosionResistance(par1Entity);
		}
		else
		{
			return 30.0F / 5.0F;
		}
	}

	/**
	 * if the specified block is in the given AABB, add its collision bounding box to the given list
	 */
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		int var8 = par1World.getBlockMetadata(par2, par3, par4);
		int var9 = var8 & 3;
		float var41 = 0.25F;
		float var42 = 0.5F;
		float var43 = 0.75F;
		float var44 = 1.0F;

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var41, 1.0F);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);

		if (var9 == 0)
		{
			this.setBlockBounds(0.0F, var41, 0.0F, 1.0F, var42, var43);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			this.setBlockBounds(0.0F, var42, 0.0F, 1.0F, var43, var42);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			this.setBlockBounds(0.0F, var43, 0.0F, 1.0F, var44, var41);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		else if (var9 == 1)
		{
			this.setBlockBounds(0.0F, var41, 0.0F, var43, var42, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			this.setBlockBounds(0.0F, var42, 0.0F, var42, var43, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			this.setBlockBounds(0.0F, var43, 0.0F, var41, var44, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		else if (var9 == 2)
		{
			this.setBlockBounds(0.0F, var41, var41, 1.0F, var42, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			this.setBlockBounds(0.0F, var42, var42, 1.0F, var43, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			this.setBlockBounds(0.0F, var43, var43, 1.0F, var44, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		else if (var9 == 3)
		{
			this.setBlockBounds(var41, var41, 0.0F, 1.0F, var42, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			this.setBlockBounds(var42, var42, 0.0F, 1.0F, var43, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			this.setBlockBounds(var43, var43, 0.0F, 1.0F, var44, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Goes straight to getLightBrightnessForSkyBlocks for Blocks, does some fancy computing for Fluids
	 */
	@Override
	public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			return modelBlock.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
		}
		else
		{
			return super.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
		}
	}

	/**
	 * How bright to render this block based on the light its receiving. Args: iBlockAccess, x, y, z
	 */
	@Override
	public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			return modelBlock.getBlockBrightness(par1IBlockAccess, par2, par3, par4);
		}
		else
		{
			return super.getBlockBrightness(par1IBlockAccess, par2, par3, par4);
		}
	}

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			return modelBlock.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
		}
		else
		{
			return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
		}
	}

	/**
	 * Can add to the passed in vector for a movement vector to be applied to the entity. Args: x, y, z, entity, vec3d
	 */
	@Override
	public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			modelBlock.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
		}
		else
		{
			super.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3);
		}
	}

	/**
	 * Returns if this block is collidable (only used by Fire). Args: x, y, z
	 */
	@Override
	public boolean isCollidable()
	{
		return super.isCollidable();
	}

	/**
	 * Returns whether this block is collideable based on the arguments passed in Args: blockMetaData, unknownFlag
	 */
	@Override
	public boolean canCollideCheck(int par1, boolean par2)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			return modelBlock.canCollideCheck(par1, par2);
		}
		else
		{
			return super.canCollideCheck(par1, par2);
		}
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			return modelBlock.canPlaceBlockAt(par1World, par2, par3, par4);
		}
		else
		{
			return super.canPlaceBlockAt(par1World, par2, par3, par4);
		}
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		this.onNeighborBlockChange(par1World, par2, par3, par4, 0);
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			if (modelBlock == Block.grass)
			{
				this.setTickRandomly(true);
			}
			else
			{
				this.setTickRandomly(false);
			}
			modelBlock.onBlockAdded(par1World, par2, par3, par4);
		}
		else
		{
			super.onBlockAdded(par1World, par2, par3, par4);
		}
	}

	/**
	 * Called whenever an entity is walking on top of this block. Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			modelBlock.onEntityWalking(par1World, par2, par3, par4, par5Entity);
		}
		else
		{
			super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			if (modelBlock == Block.grass)
			{
				this.growGrass(par1World, par2, par3, par4, par5Random);
			}
			else
			{
				modelBlock.updateTick(par1World, par2, par3, par4, par5Random);
			}
		}
		else
		{
			super.updateTick(par1World, par2, par3, par4, par5Random);
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void growGrass(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
		{
			int metaData = par1World.getBlockMetadata(par2, par3, par4);
			int var12 = par1World.getBlockId(par2, par3 + 1, par4);
			if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && Block.lightOpacity[var12] > 2)
			{
				par1World.setBlock(par2, par3, par4, this.blockID, metaData + 4, 2);
			}
			else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
			{
				for (int var6 = 0; var6 < 4; ++var6)
				{
					int var7 = par2 + par5Random.nextInt(3) - 1;
					int var8 = par3 + par5Random.nextInt(5) - 3;
					int var9 = par4 + par5Random.nextInt(3) - 1;
					int var10 = par1World.getBlockId(var7, var8 + 1, var9);

					int var14 = par1World.getBlockMetadata(var7, var8, var9);
					if (par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2)
					{
						if (par1World.getBlockId(var7, var8, var9) == this.blockID && (var14 >> 2) == 2)
						{
							par1World.setBlock(var7, var8, var9, this.blockID, var14 - 4, 2);
						}
						else if (par1World.getBlockId(var7, var8, var9) == Block.dirt.blockID)
						{
							par1World.setBlock(var7, var8, var9, Block.grass.blockID, 0, 2);
						}
					}
				}
			}
		}
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			return modelBlock.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0F, 0.0F, 0.0F);
		}
		else
		{
			return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, 0, 0.0F, 0.0F, 0.0F);
		}
	}

	/**
	 * Called upon the block being destroyed by an explosion
	 */
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock != null)
		{
			modelBlock.onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
		}
		else
		{
			super.onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
		}
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
	{
		int var6 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int var7 = par1World.getBlockMetadata(par2, par3, par4);
		int var8 = var7 & 4;
		//System.out.println("MC onBlockPlacedBy... META " + var7);

		if (var6 == 0) // South
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | var7, 2);
		}

		if (var6 == 1) // West
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | var7, 2);
		}

		if (var6 == 2) // North
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | var7, 2);
		}

		if (var6 == 3) // East
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | var7, 2);
		}
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		if (par5 == 0 || par5 != 1 && par7 > 0.5D)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, par9 | 4, 2);
		}
		return par9;
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int par1)
	{
		Block modelBlock = MCE_Items.models[this.blockID - MCE_Items.FIRST_BLOCKID - MCE_Items.BLOCKID_DISPLACEMENT - 140];
		if (modelBlock == Block.grass)
		{
			return 8;
		}
		else
		{
			return par1 >> 2;
		}
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
	}
}
