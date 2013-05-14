package sedridor.mce.blocks;

import java.util.List;
import java.util.Random;
import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sedridor.mce.MCE_Items;
import sedridor.mce.RayTraceVsComplexBlock;

public class BlockPlanter extends Block
{
	public static final float planterWidth = 0.75F;
	public static final float planterHalfWidth = 0.375F;
	public static final float planterBandHeight = 0.3125F;
	public static final float planterBandHalfHeight = 0.15625F;
	public static final int typeUnfired = 0;
	public static final int typeEmpty = 1;
	public static final int typeSoil = 2;
	public static final int typeSoilFertilized = 4;
	public static final int typeGrass0 = 8;
	public static final int typeGrass1 = 10;
	public static final int typeGrass2 = 12;
	public static final int typeGrass3 = 14;
	private Icon iconTopSoil;
	private Icon iconTopGrass;
	private Icon iconTopFertilized;

	public BlockPlanter(int i)
	{
		super(i, Material.glass);
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
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
		return MCE_Items.planterModelID;
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int i)
	{
		if (i == typeGrass1 || i == typeGrass2 || i == typeGrass3)
		{
			i = typeGrass0;
		}

		return i;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return AxisAlignedBB.getAABBPool().getAABB((i), (j), (k), i + 1.0F, j + 1.0F, k + 1.0F);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		int l = this.GetPlanterType(world, i, j, k);

		if (l == typeGrass0 || l == typeGrass1 || l == typeGrass2 || l == typeGrass3)
		{
			int i1 = this.GetGrassGrowthState(world, i, j, k);
			int j1 = 0;
			int k1;

			if (world.isAirBlock(i, j + 1, k) && world.getBlockLightValue(i, j + 1, k) >= 8)
			{
				j1 = i1 + 1;

				if (j1 > 3)
				{
					j1 = 0;
					k1 = random.nextInt(4);

					if (k1 == 0)
					{
						world.setBlock(i, j + 1, k, Block.plantRed.blockID);
					}
					else if (k1 == 1)
					{
						world.setBlock(i, j + 1, k, Block.plantYellow.blockID);
					}
					else
					{
						world.setBlock(i, j + 1, k, Block.tallGrass.blockID, 1, 3);
					}
				}
			}

			if (world.getBlockLightValue(i, j + 1, k) >= 9)
			{
				for (k1 = 0; k1 < 4; ++k1)
				{
					int l1 = i + random.nextInt(3) - 1;
					int i2 = j + random.nextInt(5) - 3;
					int j2 = k + random.nextInt(3) - 1;
					int k2 = world.getBlockId(l1, i2 + 1, j2);

					if (world.getBlockId(l1, i2, j2) == Block.dirt.blockID && world.getBlockLightValue(l1, i2 + 1, j2) >= 4 && Block.lightOpacity[k2] <= 2)
					{
						world.setBlock(l1, i2, j2, Block.grass.blockID);
					}
				}
			}

			if (j1 != i1)
			{
				this.SetGrassGrowthState(world, i, j, k, j1);
			}
		}
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		if (!world.isRemote && !entity.isDead)
		{
			int l = this.GetPlanterType(world, i, j, k);

			if (l == typeSoil && entity instanceof EntityItem)
			{
				EntityItem entityitem = (EntityItem)entity;
				ItemStack itemstack = entityitem.getEntityItem();

				if (itemstack.getItem().itemID == Item.dyePowder.itemID && itemstack.getItemDamage() == 15)
				{
					--itemstack.stackSize;

					if (itemstack.stackSize <= 0)
					{
						entityitem.setDead();
					}

					this.SetPlanterType(world, i, j, k, typeSoilFertilized);
					world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "random.pop", 0.25F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				}
			}
		}
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	 * x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3 vec3, Vec3 vec31)
	{
		RayTraceVsComplexBlock var7 = new RayTraceVsComplexBlock(world, i, j, k, vec3, vec31);
		var7.AddBoxWithLocalCoordsToIntersectionList(0.125D, 0.0D, 0.125D, 0.875D, 0.6875D, 0.875D);
		var7.AddBoxWithLocalCoordsToIntersectionList(0.0D, 0.6875D, 0.0D, 1.0D, 1.0D, 1.0D);
		return var7.GetFirstIntersection();
	}

	public float GetMovementModifier(World world, int i, int j, int k)
	{
		return 1.0F;
	}

	public int GetFacing(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return 0;
	}

	public void SetFacing(World world, int i, int j, int k, int l) {}

	public int GetFacingFromMetadata(int i)
	{
		return 0;
	}

	public int SetFacingInMetadata(int i, int j)
	{
		return i;
	}

	public void RotateAroundJAxis(World world, int i, int j, int k, boolean flag) {}

	public int RotateMetadataAroundJAxis(int i, boolean flag)
	{
		return i;
	}

	public boolean ToggleFacing(World world, int i, int j, int k, boolean flag)
	{
		return false;
	}

	public boolean CanPlantGrowOnBlock(World world, int i, int j, int k, Block block)
	{
		int l = this.GetPlanterType(world, i, j, k);
		return block.blockID == Block.waterlily.blockID ? false : ((l == typeGrass0 || l == typeGrass1 || l == typeGrass2 || l == typeGrass3) && (block.blockID == Block.plantRed.blockID || block.blockID == Block.plantYellow.blockID || block.blockID == Block.tallGrass.blockID || block.blockID == Block.sapling.blockID) ? true : l == typeSoil || l == typeSoilFertilized);
	}

	public boolean IsPlantGrowthMaximizedOnBlock(World world, int i, int j, int k, Block block)
	{
		int l = this.GetPlanterType(world, i, j, k);
		return l == typeSoil || l == typeSoilFertilized;
	}

	public boolean IsBlockHydrated(World world, int i, int j, int k)
	{
		int l = this.GetPlanterType(world, i, j, k);
		return l == typeSoil || l == typeSoilFertilized;
	}

	public boolean IsBlockConsideredNeighbouringWater(World world, int i, int j, int k)
	{
		int l = this.GetPlanterType(world, i, j, k);
		return l == typeSoil || l == typeSoilFertilized;
	}

	public float GetGrowthMultiplier(World world, int i, int j, int k, Block block)
	{
		int l = this.GetPlanterType(world, i, j, k);
		return l == typeSoilFertilized ? 2.0F : 1.0F;
	}

	public void NotifyOfPlantGrowth(World world, int i, int j, int k, Block block)
	{
		int l = this.GetPlanterType(world, i, j, k);
		if (l == typeSoilFertilized)
		{
			this.SetPlanterType(world, i, j, k, typeSoil);
		}
	}

	public boolean DoesBlockHaveSolidTop(IBlockAccess iblockaccess, int i, int j, int k)
	{
		int l = this.GetPlanterType(iblockaccess, i, j, k);
		return l > typeEmpty;
	}

	public boolean DoesBlockNeighbourOnStem(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return Block.blocksList[iblockaccess.getBlockId(i + 1, j, k)] instanceof BlockStem ? true : (Block.blocksList[iblockaccess.getBlockId(i - 1, j, k)] instanceof BlockStem ? true : (Block.blocksList[iblockaccess.getBlockId(i, j, k + 1)] instanceof BlockStem ? true : Block.blocksList[iblockaccess.getBlockId(i, j, k - 1)] instanceof BlockStem));
	}

	public int GetPlanterType(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return this.GetPlanterTypeFromMetadata(iblockaccess.getBlockMetadata(i, j, k));
	}

	public void SetPlanterType(World world, int i, int j, int k, int l)
	{
		world.setBlockMetadataWithNotify(i, j, k, l, 3);
	}

	public int GetPlanterTypeFromMetadata(int i)
	{
		return i;
	}

	public int GetGrassGrowthState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		int l = this.GetPlanterType(iblockaccess, i, j, k);

		switch (l)
		{
		case typeGrass0:
			return 0;
		default:
			return 0;
		case typeGrass1:
			return 1;
		case typeGrass2:
			return 2;
		case typeGrass3:
			return 3;
		}
	}

	public void SetGrassGrowthState(World world, int i, int j, int k, int l)
	{
		byte var1 = typeGrass0;

		if (l == 1)
		{
			var1 = typeGrass1;
		}
		else if (l == 2)
		{
			var1 = typeGrass2;
		}
		else if (l == 3)
		{
			var1 = typeGrass3;
		}

		this.SetPlanterType(world, i, j, k, var1);
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	 @Override
	 public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
	{
		 return true;
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	 @Override
	 public void registerIcons(IconRegister iconregister)
	{
		 super.registerIcons(iconregister);
		 this.iconTopSoil = iconregister.registerIcon("planter_top_soil");
		 this.iconTopGrass = iconregister.registerIcon("planter_top_grass");
		 this.iconTopFertilized = iconregister.registerIcon("planter_top_fertilized");
	}

	 /**
	  * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	  */
	 @Override
	 public Icon getIcon(int i, int j)
	 {
		 int k = this.GetPlanterTypeFromMetadata(j);
		 return i == 1 && k > typeEmpty ? (k == typeSoil ? this.iconTopSoil : (k == typeSoilFertilized ? this.iconTopFertilized : this.iconTopGrass)) : this.blockIcon;
	 }

	 /**
	  * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	  */
	 @Override
	 public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	 {
		 par3List.add(new ItemStack(par1, 1, BlockPlanter.typeUnfired));
		 par3List.add(new ItemStack(par1, 1, BlockPlanter.typeEmpty));
	 }

	 public static boolean RenderBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block)
	 {
		 BlockPlanter blockPlanter = (BlockPlanter)block;
		 int l = blockPlanter.GetPlanterType(iblockaccess, i, j, k);
		 return l <= typeEmpty ? RenderEmptyPlanterBlock(renderblocks, iblockaccess, i, j, k, block) : RenderFilledPlanterBlock(renderblocks, iblockaccess, i, j, k, block);
	 }

	 public static boolean RenderEmptyPlanterBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block)
	 {
		 renderblocks.setRenderBounds(0.125D, 0.0D, 0.125D, 0.25D, 0.6875D, 0.75D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.125D, 0.0D, 0.75D, 0.75D, 0.6875D, 0.875D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.75D, 0.0D, 0.25D, 0.875D, 0.6875D, 0.875D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.25D, 0.0D, 0.125D, 0.875D, 0.6875D, 0.25D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 0.125D, 0.75D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.0D, 0.6875D, 0.0D, 0.125D, 1.0D, 0.875D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.0D, 0.6875D, 0.875D, 0.875D, 1.0D, 1.0D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.875D, 0.6875D, 0.125D, 1.0D, 1.0D, 1.0D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.125D, 0.6875D, 0.0D, 1.0D, 1.0D, 0.125D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 return true;
	 }

	 public static boolean RenderFilledPlanterBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block)
	 {
		 renderblocks.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.6875D, 0.875D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 renderblocks.setRenderBounds(0.0D, 0.6875D, 0.0D, 1.0D, 1.0D, 1.0D);
		 renderblocks.renderStandardBlock(block, i, j, k);
		 return true;
	 }

	 public static void RenderInvBlock(RenderBlocks renderblocks, Block block, int i, float j)
	 {
		 if (i <= typeEmpty)
		 {
			 RenderEmptyPlanterInvBlock(renderblocks, block, i, j);
		 }
		 else
		 {
			 RenderFilledPlanterInvBlock(renderblocks, block, i, j);
		 }
	 }

	 public static void RenderEmptyPlanterInvBlock(RenderBlocks renderblocks, Block block, int i, float j)
	 {
		 renderblocks.setRenderBounds(0.125D, 0.0D, 0.125D, 0.25D, 0.6875D, 0.75D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.125D, 0.0D, 0.75D, 0.75D, 0.6875D, 0.875D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.75D, 0.0D, 0.25D, 0.875D, 0.6875D, 0.875D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.25D, 0.0D, 0.125D, 0.875D, 0.6875D, 0.25D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 0.125D, 0.75D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.0D, 0.6875D, 0.0D, 0.125D, 1.0D, 0.875D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.0D, 0.6875D, 0.875D, 0.875D, 1.0D, 1.0D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.875D, 0.6875D, 0.125D, 1.0D, 1.0D, 1.0D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.125D, 0.6875D, 0.0D, 1.0D, 1.0D, 0.125D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
	 }

	 public static void RenderFilledPlanterInvBlock(RenderBlocks renderblocks, Block block, int i, float j)
	 {
		 renderblocks.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.6875D, 0.875D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
		 renderblocks.setRenderBounds(0.0D, 0.6875D, 0.0D, 1.0D, 1.0D, 1.0D);
		 RenderInvBlockWithMetadata(renderblocks, block, -0.5F, -0.5F, -0.5F, i);
	 }

	 public static void RenderInvBlockWithMetadata(RenderBlocks renderblocks, Block block, float f, float f1, float f2, int i)
	 {
		 Tessellator tessellator = Tessellator.instance;
		 GL11.glTranslatef(f, f1, f2);
		 tessellator.startDrawingQuads();
		 tessellator.setNormal(0.0F, -1.0F, 0.0F);
		 renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, i));
		 tessellator.draw();
		 tessellator.startDrawingQuads();
		 tessellator.setNormal(0.0F, 1.0F, 0.0F);
		 renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, i));
		 tessellator.draw();
		 tessellator.startDrawingQuads();
		 tessellator.setNormal(0.0F, 0.0F, -1.0F);
		 renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, i));
		 tessellator.draw();
		 tessellator.startDrawingQuads();
		 tessellator.setNormal(0.0F, 0.0F, 1.0F);
		 renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, i));
		 tessellator.draw();
		 tessellator.startDrawingQuads();
		 tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		 renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, i));
		 tessellator.draw();
		 tessellator.startDrawingQuads();
		 tessellator.setNormal(1.0F, 0.0F, 0.0F);
		 renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, i));
		 tessellator.draw();
		 GL11.glTranslatef(-f, -f1, -f2);
	 }
}
