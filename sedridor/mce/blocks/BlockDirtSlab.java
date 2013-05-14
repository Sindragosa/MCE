package sedridor.mce.blocks;

import java.util.Random;
import sedridor.mce.MCE_Items;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDirtSlab extends BlockSlab
{
	public static final int subtypeDirt = 0;
	public static final int subtypeGrass = 1;
	public static final int subtypeMycelium = 2;
	public static final int subtypePackedEarth = 3;
	public static final int numSubtypes = 4;
	private static Icon iconGrassSideOverlay;
	private static Icon iconGrassSideOverlayHalf;
	private Icon iconGrassTop;
	private Icon iconGrassSide;
	private Icon iconGrassSideHalf;
	private Icon iconPackedEarth;
	private Icon iconGrassWithSnowSide;
	private Icon iconGrassWithSnowSideHalf;

	public BlockDirtSlab(int i)
	{
		super(i, Material.ground);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setTickRandomly(true);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return MCE_Items.dirtSlabModelID;
	}

	/**
	 * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
	 */
	@Override
	protected boolean canSilkHarvest()
	{
		return false;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		boolean flag = this.GetIsUpsideDown(world, i, j, k);
		boolean flag1 = false;
		int l;

		if (!flag)
		{
			l = world.getBlockId(i, j - 1, k);

			if (l == Block.grass.blockID)
			{
				world.setBlock(i, j - 1, k, Block.dirt.blockID);
			}
		}
		else
		{
			l = world.getBlockId(i, j + 1, k);

			if (l == this.blockID && !this.GetIsUpsideDown(world, i, j + 1, k))
			{
				flag1 = true;
			}
		}

		l = this.GetSubtype(world, i, j, k);
		int i1;
		int j1;
		int k1;
		int l1;
		int i2;

		if (l == 1)
		{
			if ((world.getBlockLightValue(i, j + 1, k) >= 4 || Block.lightOpacity[world.getBlockId(i, j + 1, k)] <= 2) && !flag1)
			{
				if (world.provider.dimensionId != 1 && world.getBlockLightValue(i, j + 1, k) >= 9)
				{
					i1 = i + random.nextInt(3) - 1;
					j1 = j + random.nextInt(5) - 3;
					k1 = k + random.nextInt(3) - 1;
					l1 = world.getBlockId(i1, j1, k1);
					i2 = world.getBlockId(i1, j1 + 1, k1);
					boolean flag2 = false;

					if (i2 == MCE_Items.dirtSlab.blockID)
					{
						flag2 = !this.GetIsUpsideDown(world, i1, j1 + 1, k1);
					}

					if (l1 == Block.dirt.blockID)
					{
						if (world.getBlockLightValue(i1, j1 + 1, k1) >= 4 && Block.lightOpacity[i2] <= 2 && !flag2)
						{
							world.setBlock(i1, j1, k1, Block.grass.blockID);
						}
					}
					else if (l1 == MCE_Items.dirtSlab.blockID)
					{
						if (world.getBlockLightValue(i1, j1 + 1, k1) >= 4 && Block.lightOpacity[i2] <= 2)
						{
							int j2 = this.GetSubtype(world, i1, j1, k1);

							if (j2 == 0 && (!flag2 || !this.GetIsUpsideDown(world, i1, j1, k1)))
							{
								this.SetSubtype(world, i1, j1, k1, 1);
							}
						}
					}
					else if (world.getBlockId(i1, j1, k1) == Block.tilledField.blockID && random.nextInt(3) == 0 && world.isAirBlock(i1, j1 + 1, k1) && world.getBlockLightValue(i1, j1 + 1, k1) >= 4)
					{
						world.setBlock(i1, j1 + 1, k1, Block.tallGrass.blockID, 1, 3);
					}
				}
			}
			else
			{
				this.SetSubtype(world, i, j, k, 0);
			}
		}
		else if (l == 0 && world.provider.dimensionId != 1 && random.nextInt(5) == 0 && !flag1 && world.getBlockLightValue(i, j + 1, k) >= 4 && Block.lightOpacity[world.getBlockId(i, j + 1, k)] <= 2)
		{
			i1 = i + random.nextInt(3) - 1;
			j1 = j + random.nextInt(5) - 3;
			k1 = k + random.nextInt(3) - 1;
			l1 = world.getBlockId(i1, j1, k1);
			i2 = world.getBlockId(i1, j1 + 1, k1);

			if (l1 == Block.grass.blockID && world.getBlockLightValue(i1, j1 + 1, k1) >= 9 && Block.lightOpacity[i2] <= 2)
			{
				this.SetSubtype(world, i, j, k, 1);
			}
		}
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int i)
	{
		int j = i >> 1;
			return j == 3 ? j : 0;
	}

	public float GetMovementModifier(World world, int i, int j, int k)
	{
		float f = 1.0F;
		int l = this.GetSubtype(world, i, j, k);

		if (l == 3)
		{
			f = 1.2F;
		}

		return f;
	}

	public StepSound GetStepSound(World world, int i, int j, int k)
	{
		int l = this.GetSubtype(world, i, j, k);
		return l != 0 && l != 3 ? this.stepSound : soundGravelFootstep;
	}

	@Override
	public boolean GetIsUpsideDown(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return (iblockaccess.getBlockMetadata(i, j, k) & 1) > 0;
	}

	@Override
	public void SetIsUpsideDown(World world, int i, int j, int k, boolean flag)
	{
		int l = world.getBlockMetadata(i, j, k);
		world.setBlockMetadataWithNotify(i, j, k, this.SetIsUpsideDownInMetadata(l, flag), 3);
	}

	@Override
	public int SetIsUpsideDownInMetadata(int i, boolean flag)
	{
		if (flag)
		{
			i |= 1;
		}
		else
		{
			i &= -2;
		}

		return i;
	}

	public int GetSubtype(IBlockAccess iblockaccess, int i, int j, int k)
	{
		return (iblockaccess.getBlockMetadata(i, j, k) & -2) >> 1;
	}

	public void SetSubtype(World world, int i, int j, int k, int l)
	{
		int i1 = world.getBlockMetadata(i, j, k) & 1;
		i1 |= l << 1;
		world.setBlock(i, j, k, i1);
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	 @Override
	 public void registerIcons(IconRegister iconregister)
	 {
		 this.blockIcon = iconregister.registerIcon("dirt");
		 this.iconGrassSide = iconregister.registerIcon("grass_side");
		 BlockDirtSlab.iconGrassSideOverlay = iconregister.registerIcon("grass_side_overlay");
		 this.iconGrassTop = iconregister.registerIcon("grass_top");
		 this.iconGrassSideHalf = iconregister.registerIcon("dirtSlab_grass_side");
		 BlockDirtSlab.iconGrassSideOverlayHalf = iconregister.registerIcon("dirtSlab_grass_side_overlay");
		 this.iconPackedEarth = iconregister.registerIcon("dirtSlab_PackedEarth");
		 this.iconGrassWithSnowSide = iconregister.registerIcon("snow_side");
		 this.iconGrassWithSnowSideHalf = iconregister.registerIcon("dirtSlab_grass_snow_side");
	 }

	 /**
	  * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	  */
	 @Override
	 public Icon getIcon(int i, int j)
	 {
		 int k = (j & -2) >> 1;

		if (k == 1 && i != 0)
		{
			if (i != 1)
			{
				boolean flag = (j & 1) > 0;
				return flag ? this.iconGrassSide : this.iconGrassSideHalf;
			}
			else
			{
				return this.iconGrassTop;
			}
		}
		else
		{
			return k == 3 ? this.iconPackedEarth : this.blockIcon;
		}
	 }

	 /**
	  * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
	  */
	 @Override
	 public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
	 {
		 int i1 = iblockaccess.getBlockMetadata(i, j, k);
		 int j1 = (i1 & -2) >> 1;

		 if (j1 == 1 && l > 1 && iblockaccess.getBlockId(i, j + 1, k) == Block.snow.blockID)
		 {
			 boolean flag = (i1 & 1) > 0;
			 return flag ? this.iconGrassWithSnowSide : this.iconGrassWithSnowSideHalf;
		 }
		 else
		 {
			 return this.getIcon(l, i1);
		 }
	 }

	 public static boolean RenderDirtSlab(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block)
	 {
		 int l = ((BlockDirtSlab)MCE_Items.dirtSlab).GetSubtype(iblockaccess, i, j, k);

		 if (l == 1 && iblockaccess.getBlockId(i, j + 1, k) != Block.snow.blockID)
		 {
			 int i1 = block.colorMultiplier(iblockaccess, i, j, k);
			 float f = (i1 >> 16 & 255) / 255.0F;
			 float f1 = (i1 >> 8 & 255) / 255.0F;
			 float f2 = (i1 & 255) / 255.0F;

			 if (EntityRenderer.anaglyphEnable)
			 {
				 float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
				 float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
				 float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
				 f = f3;
				 f1 = f4;
				 f2 = f5;
			 }

			 return Minecraft.isAmbientOcclusionEnabled() ? renderCustomGrassBlockWithAmbientOcclusion(renderblocks, iblockaccess, block, i, j, k, f, f1, f2) : renderCustomGrassBlockWithColorMultiplier(renderblocks, iblockaccess, block, i, j, k, f, f1, f2);
		 }
		 else
		 {
			 return renderblocks.renderStandardBlock(block, i, j, k);
		 }
	 }

	 /**
	  * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	  * when first determining what to render.
	  */
	  @Override
	  public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k)
	  {
		  int l = this.GetSubtype(iblockaccess, i, j, k);

		  if (l == 1 && iblockaccess.getBlockId(i, j + 1, k) != Block.snow.blockID)
		  {
			  int i1 = 0;
			  int j1 = 0;
			  int k1 = 0;

			  for (int l1 = -1; l1 <= 1; ++l1)
			  {
				  for (int i2 = -1; i2 <= 1; ++i2)
				  {
					  int j2 = iblockaccess.getBiomeGenForCoords(i + i2, k + l1).getBiomeGrassColor();
					  i1 += (j2 & 16711680) >> 16;
				  j1 += (j2 & 65280) >> 8;
				  k1 += j2 & 255;
				  }
			  }

			  return (i1 / 9 & 255) << 16 | (j1 / 9 & 255) << 8 | k1 / 9 & 255;
		  }
		  else
		  {
			  return super.colorMultiplier(iblockaccess, i, j, k);
		  }
	  }

	  public static boolean renderCustomGrassBlockWithAmbientOcclusion(RenderBlocks renderblocks, IBlockAccess iblockaccess, Block block, int i, int j, int k, float f, float f1, float f2)
	  {
		  renderblocks.enableAO = true;
		  boolean flag = false;
		  float f3 = 0.0F;
		  float f4 = 0.0F;
		  float f5 = 0.0F;
		  float f6 = 0.0F;
		  boolean flag1 = true;
		  int l = block.getMixedBrightnessForBlock(iblockaccess, i, j, k);
		  Tessellator tessellator = Tessellator.instance;
		  tessellator.setBrightness(983055);
		  flag1 = false;
		  Icon icon = iconGrassSideOverlay;

		  if (!((BlockDirtSlab)MCE_Items.dirtSlab).GetIsUpsideDown(iblockaccess, i, j, k))
		  {
			  icon = iconGrassSideOverlayHalf;
		  }

		  boolean flag2;
		  boolean flag3;
		  boolean flag4;
		  int i1;
		  boolean flag5;
		  float f7;

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i, j - 1, k, 0))
		  {
			  if (renderblocks.renderMinY <= 0.0D)
			  {
				  --j;
			  }

			  renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k);
			  renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(iblockaccess, i, j, k - 1);
			  renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(iblockaccess, i, j, k + 1);
			  renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k);
			  renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k);
			  renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k - 1);
			  renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k + 1);
			  renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k);
			  flag2 = Block.canBlockGrass[iblockaccess.getBlockId(i + 1, j - 1, k)];
			  flag4 = Block.canBlockGrass[iblockaccess.getBlockId(i - 1, j - 1, k)];
			  flag3 = Block.canBlockGrass[iblockaccess.getBlockId(i, j - 1, k + 1)];
			  flag5 = Block.canBlockGrass[iblockaccess.getBlockId(i, j - 1, k - 1)];

			  if (!flag5 && !flag4)
			  {
				  renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXYNN;
				  renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXYNN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k - 1);
				  renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k - 1);
			  }

			  if (!flag3 && !flag4)
			  {
				  renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXYNN;
				  renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXYNN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k + 1);
				  renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k + 1);
			  }

			  if (!flag5 && !flag2)
			  {
				  renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXYPN;
				  renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXYPN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k - 1);
				  renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k - 1);
			  }

			  if (!flag3 && !flag2)
			  {
				  renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXYPN;
				  renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXYPN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k + 1);
				  renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k + 1);
			  }

			  if (renderblocks.renderMinY <= 0.0D)
			  {
				  ++j;
			  }

			  i1 = l;

			  if (renderblocks.renderMinY <= 0.0D || !iblockaccess.isBlockOpaqueCube(i, j - 1, k))
			  {
				  i1 = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k);
			  }

			  f7 = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k);
			  f3 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchYZNP + f7) / 4.0F;
			  f6 = (renderblocks.aoLightValueScratchYZNP + f7 + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXYPN) / 4.0F;
			  f5 = (f7 + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNN) / 4.0F;
			  f4 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNN + f7 + renderblocks.aoLightValueScratchYZNN) / 4.0F;
			  renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessYZNP, i1);
			  renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXYPN, i1);
			  renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNN, i1);
			  renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessYZNN, i1);

			  if (flag1)
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = f * 0.5F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = f1 * 0.5F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = f2 * 0.5F;
			  }
			  else
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = 0.5F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = 0.5F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = 0.5F;
			  }

			  renderblocks.colorRedTopLeft *= f3;
			  renderblocks.colorGreenTopLeft *= f3;
			  renderblocks.colorBlueTopLeft *= f3;
			  renderblocks.colorRedBottomLeft *= f4;
			  renderblocks.colorGreenBottomLeft *= f4;
			  renderblocks.colorBlueBottomLeft *= f4;
			  renderblocks.colorRedBottomRight *= f5;
			  renderblocks.colorGreenBottomRight *= f5;
			  renderblocks.colorBlueBottomRight *= f5;
			  renderblocks.colorRedTopRight *= f6;
			  renderblocks.colorGreenTopRight *= f6;
			  renderblocks.colorBlueTopRight *= f6;
			  renderblocks.renderBottomFace(block, i, j, k, renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 0));
			  flag = true;
		  }

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i, j + 1, k, 1))
		  {
			  if (renderblocks.renderMaxY >= 1.0D)
			  {
				  ++j;
			  }

			  renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k);
			  renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k);
			  renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(iblockaccess, i, j, k - 1);
			  renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(iblockaccess, i, j, k + 1);
			  renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k);
			  renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k);
			  renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k - 1);
			  renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k + 1);
			  flag2 = Block.canBlockGrass[iblockaccess.getBlockId(i + 1, j + 1, k)];
			  flag4 = Block.canBlockGrass[iblockaccess.getBlockId(i - 1, j + 1, k)];
			  flag3 = Block.canBlockGrass[iblockaccess.getBlockId(i, j + 1, k + 1)];
			  flag5 = Block.canBlockGrass[iblockaccess.getBlockId(i, j + 1, k - 1)];

			  if (!flag5 && !flag4)
			  {
				  renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXYNP;
				  renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXYNP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k - 1);
				  renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k - 1);
			  }

			  if (!flag5 && !flag2)
			  {
				  renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXYPP;
				  renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXYPP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k - 1);
				  renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k - 1);
			  }

			  if (!flag3 && !flag4)
			  {
				  renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXYNP;
				  renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXYNP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k + 1);
				  renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k + 1);
			  }

			  if (!flag3 && !flag2)
			  {
				  renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXYPP;
				  renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXYPP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k + 1);
				  renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k + 1);
			  }

			  if (renderblocks.renderMaxY >= 1.0D)
			  {
				  --j;
			  }

			  i1 = l;

			  if (renderblocks.renderMaxY >= 1.0D || !iblockaccess.isBlockOpaqueCube(i, j + 1, k))
			  {
				  i1 = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k);
			  }

			  f7 = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k);
			  f6 = (renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchYZPP + f7) / 4.0F;
			  f3 = (renderblocks.aoLightValueScratchYZPP + f7 + renderblocks.aoLightValueScratchXYZPPP + renderblocks.aoLightValueScratchXYPP) / 4.0F;
			  f4 = (f7 + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
			  f5 = (renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPN + f7 + renderblocks.aoLightValueScratchYZPN) / 4.0F;
			  renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessYZPP, i1);
			  renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXYZPPP, renderblocks.aoBrightnessXYPP, i1);
			  renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPN, i1);
			  renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, i1);
			  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = f;
			  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = f1;
			  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = f2;
			  renderblocks.colorRedTopLeft *= f3;
			  renderblocks.colorGreenTopLeft *= f3;
			  renderblocks.colorBlueTopLeft *= f3;
			  renderblocks.colorRedBottomLeft *= f4;
			  renderblocks.colorGreenBottomLeft *= f4;
			  renderblocks.colorBlueBottomLeft *= f4;
			  renderblocks.colorRedBottomRight *= f5;
			  renderblocks.colorGreenBottomRight *= f5;
			  renderblocks.colorBlueBottomRight *= f5;
			  renderblocks.colorRedTopRight *= f6;
			  renderblocks.colorGreenTopRight *= f6;
			  renderblocks.colorBlueTopRight *= f6;
			  renderblocks.renderTopFace(block, i, j, k, renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 1));
			  flag = true;
		  }

		  Icon icon1;

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i, j, k - 1, 2))
		  {
			  if (renderblocks.renderMinZ <= 0.0D)
			  {
				  --k;
			  }

			  renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k);
			  renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k);
			  renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k);
			  renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k);
			  renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k);
			  renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k);
			  renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k);
			  renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k);
			  flag2 = Block.canBlockGrass[iblockaccess.getBlockId(i + 1, j, k - 1)];
			  flag4 = Block.canBlockGrass[iblockaccess.getBlockId(i - 1, j, k - 1)];
			  flag3 = Block.canBlockGrass[iblockaccess.getBlockId(i, j + 1, k - 1)];
			  flag5 = Block.canBlockGrass[iblockaccess.getBlockId(i, j - 1, k - 1)];

			  if (!flag4 && !flag5)
			  {
				  renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
				  renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j - 1, k);
				  renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j - 1, k);
			  }

			  if (!flag4 && !flag3)
			  {
				  renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
				  renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j + 1, k);
				  renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j + 1, k);
			  }

			  if (!flag2 && !flag5)
			  {
				  renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
				  renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j - 1, k);
				  renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j - 1, k);
			  }

			  if (!flag2 && !flag3)
			  {
				  renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
				  renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j + 1, k);
				  renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j + 1, k);
			  }

			  if (renderblocks.renderMinZ <= 0.0D)
			  {
				  ++k;
			  }

			  i1 = l;

			  if (renderblocks.renderMinZ <= 0.0D || !iblockaccess.isBlockOpaqueCube(i, j, k - 1))
			  {
				  i1 = block.getMixedBrightnessForBlock(iblockaccess, i, j, k - 1);
			  }

			  f7 = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k - 1);
			  f3 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchXYZNPN + f7 + renderblocks.aoLightValueScratchYZPN) / 4.0F;
			  f4 = (f7 + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
			  f5 = (renderblocks.aoLightValueScratchYZNN + f7 + renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXZPN) / 4.0F;
			  f6 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchYZNN + f7) / 4.0F;
			  renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, i1);
			  renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, i1);
			  renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXZPN, i1);
			  renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessYZNN, i1);

			  if (flag1)
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = f * 0.8F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = f1 * 0.8F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = f2 * 0.8F;
			  }
			  else
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = 0.8F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = 0.8F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = 0.8F;
			  }

			  renderblocks.colorRedTopLeft *= f3;
			  renderblocks.colorGreenTopLeft *= f3;
			  renderblocks.colorBlueTopLeft *= f3;
			  renderblocks.colorRedBottomLeft *= f4;
			  renderblocks.colorGreenBottomLeft *= f4;
			  renderblocks.colorBlueBottomLeft *= f4;
			  renderblocks.colorRedBottomRight *= f5;
			  renderblocks.colorGreenBottomRight *= f5;
			  renderblocks.colorBlueBottomRight *= f5;
			  renderblocks.colorRedTopRight *= f6;
			  renderblocks.colorGreenTopRight *= f6;
			  renderblocks.colorBlueTopRight *= f6;
			  icon1 = renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 2);
			  renderblocks.renderNorthFace(block, i, j, k, icon1);

			  if (RenderBlocks.fancyGrass)
			  {
				  renderblocks.colorRedTopLeft *= f;
				  renderblocks.colorRedBottomLeft *= f;
				  renderblocks.colorRedBottomRight *= f;
				  renderblocks.colorRedTopRight *= f;
				  renderblocks.colorGreenTopLeft *= f1;
				  renderblocks.colorGreenBottomLeft *= f1;
				  renderblocks.colorGreenBottomRight *= f1;
				  renderblocks.colorGreenTopRight *= f1;
				  renderblocks.colorBlueTopLeft *= f2;
				  renderblocks.colorBlueBottomLeft *= f2;
				  renderblocks.colorBlueBottomRight *= f2;
				  renderblocks.colorBlueTopRight *= f2;
				  renderblocks.renderNorthFace(block, i, j, k, icon);
			  }

			  flag = true;
		  }

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i, j, k + 1, 3))
		  {
			  if (renderblocks.renderMaxZ >= 1.0D)
			  {
				  ++k;
			  }

			  renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k);
			  renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k);
			  renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k);
			  renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k);
			  renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k);
			  renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k);
			  renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k);
			  renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k);
			  flag2 = Block.canBlockGrass[iblockaccess.getBlockId(i + 1, j, k + 1)];
			  flag4 = Block.canBlockGrass[iblockaccess.getBlockId(i - 1, j, k + 1)];
			  flag3 = Block.canBlockGrass[iblockaccess.getBlockId(i, j + 1, k + 1)];
			  flag5 = Block.canBlockGrass[iblockaccess.getBlockId(i, j - 1, k + 1)];

			  if (!flag4 && !flag5)
			  {
				  renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
				  renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j - 1, k);
				  renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j - 1, k);
			  }

			  if (!flag4 && !flag3)
			  {
				  renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
				  renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j + 1, k);
				  renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j + 1, k);
			  }

			  if (!flag2 && !flag5)
			  {
				  renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
				  renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j - 1, k);
				  renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j - 1, k);
			  }

			  if (!flag2 && !flag3)
			  {
				  renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
				  renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j + 1, k);
				  renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j + 1, k);
			  }

			  if (renderblocks.renderMaxZ >= 1.0D)
			  {
				  --k;
			  }

			  i1 = l;

			  if (renderblocks.renderMaxZ >= 1.0D || !iblockaccess.isBlockOpaqueCube(i, j, k + 1))
			  {
				  i1 = block.getMixedBrightnessForBlock(iblockaccess, i, j, k + 1);
			  }

			  f7 = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k + 1);
			  f3 = (renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYZNPP + f7 + renderblocks.aoLightValueScratchYZPP) / 4.0F;
			  f6 = (f7 + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
			  f5 = (renderblocks.aoLightValueScratchYZNP + f7 + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXZPP) / 4.0F;
			  f4 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchYZNP + f7) / 4.0F;
			  renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessYZPP, i1);
			  renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYZPPP, i1);
			  renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, i1);
			  renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessYZNP, i1);

			  if (flag1)
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = f * 0.8F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = f1 * 0.8F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = f2 * 0.8F;
			  }
			  else
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = 0.8F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = 0.8F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = 0.8F;
			  }

			  renderblocks.colorRedTopLeft *= f3;
			  renderblocks.colorGreenTopLeft *= f3;
			  renderblocks.colorBlueTopLeft *= f3;
			  renderblocks.colorRedBottomLeft *= f4;
			  renderblocks.colorGreenBottomLeft *= f4;
			  renderblocks.colorBlueBottomLeft *= f4;
			  renderblocks.colorRedBottomRight *= f5;
			  renderblocks.colorGreenBottomRight *= f5;
			  renderblocks.colorBlueBottomRight *= f5;
			  renderblocks.colorRedTopRight *= f6;
			  renderblocks.colorGreenTopRight *= f6;
			  renderblocks.colorBlueTopRight *= f6;
			  renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 3);
			  renderblocks.renderSouthFace(block, i, j, k, renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 3));

			  if (RenderBlocks.fancyGrass)
			  {
				  renderblocks.colorRedTopLeft *= f;
				  renderblocks.colorRedBottomLeft *= f;
				  renderblocks.colorRedBottomRight *= f;
				  renderblocks.colorRedTopRight *= f;
				  renderblocks.colorGreenTopLeft *= f1;
				  renderblocks.colorGreenBottomLeft *= f1;
				  renderblocks.colorGreenBottomRight *= f1;
				  renderblocks.colorGreenTopRight *= f1;
				  renderblocks.colorBlueTopLeft *= f2;
				  renderblocks.colorBlueBottomLeft *= f2;
				  renderblocks.colorBlueBottomRight *= f2;
				  renderblocks.colorBlueTopRight *= f2;
				  renderblocks.renderSouthFace(block, i, j, k, icon);
			  }

			  flag = true;
		  }

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i - 1, j, k, 4))
		  {
			  if (renderblocks.renderMinX <= 0.0D)
			  {
				  --i;
			  }

			  renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k);
			  renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k - 1);
			  renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k + 1);
			  renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k);
			  renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k);
			  renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(iblockaccess, i, j, k - 1);
			  renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(iblockaccess, i, j, k + 1);
			  renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k);
			  flag2 = Block.canBlockGrass[iblockaccess.getBlockId(i - 1, j + 1, k)];
			  flag4 = Block.canBlockGrass[iblockaccess.getBlockId(i - 1, j - 1, k)];
			  flag3 = Block.canBlockGrass[iblockaccess.getBlockId(i - 1, j, k - 1)];
			  flag5 = Block.canBlockGrass[iblockaccess.getBlockId(i - 1, j, k + 1)];

			  if (!flag3 && !flag4)
			  {
				  renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
				  renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k - 1);
				  renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k - 1);
			  }

			  if (!flag5 && !flag4)
			  {
				  renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
				  renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k + 1);
				  renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k + 1);
			  }

			  if (!flag3 && !flag2)
			  {
				  renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
				  renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k - 1);
				  renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k - 1);
			  }

			  if (!flag5 && !flag2)
			  {
				  renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
				  renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k + 1);
				  renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k + 1);
			  }

			  if (renderblocks.renderMinX <= 0.0D)
			  {
				  ++i;
			  }

			  i1 = l;

			  if (renderblocks.renderMinX <= 0.0D || !iblockaccess.isBlockOpaqueCube(i - 1, j, k))
			  {
				  i1 = block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k);
			  }

			  f7 = block.getAmbientOcclusionLightValue(iblockaccess, i - 1, j, k);
			  f6 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNP + f7 + renderblocks.aoLightValueScratchXZNP) / 4.0F;
			  f3 = (f7 + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPP) / 4.0F;
			  f4 = (renderblocks.aoLightValueScratchXZNN + f7 + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueScratchXYNP) / 4.0F;
			  f5 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXZNN + f7) / 4.0F;
			  renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, i1);
			  renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPP, i1);
			  renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessXYNP, i1);
			  renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXZNN, i1);

			  if (flag1)
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = f * 0.6F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = f1 * 0.6F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = f2 * 0.6F;
			  }
			  else
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = 0.6F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = 0.6F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = 0.6F;
			  }

			  renderblocks.colorRedTopLeft *= f3;
			  renderblocks.colorGreenTopLeft *= f3;
			  renderblocks.colorBlueTopLeft *= f3;
			  renderblocks.colorRedBottomLeft *= f4;
			  renderblocks.colorGreenBottomLeft *= f4;
			  renderblocks.colorBlueBottomLeft *= f4;
			  renderblocks.colorRedBottomRight *= f5;
			  renderblocks.colorGreenBottomRight *= f5;
			  renderblocks.colorBlueBottomRight *= f5;
			  renderblocks.colorRedTopRight *= f6;
			  renderblocks.colorGreenTopRight *= f6;
			  renderblocks.colorBlueTopRight *= f6;
			  icon1 = renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 4);
			  renderblocks.renderWestFace(block, i, j, k, icon1);

			  if (RenderBlocks.fancyGrass)
			  {
				  renderblocks.colorRedTopLeft *= f;
				  renderblocks.colorRedBottomLeft *= f;
				  renderblocks.colorRedBottomRight *= f;
				  renderblocks.colorRedTopRight *= f;
				  renderblocks.colorGreenTopLeft *= f1;
				  renderblocks.colorGreenBottomLeft *= f1;
				  renderblocks.colorGreenBottomRight *= f1;
				  renderblocks.colorGreenTopRight *= f1;
				  renderblocks.colorBlueTopLeft *= f2;
				  renderblocks.colorBlueBottomLeft *= f2;
				  renderblocks.colorBlueBottomRight *= f2;
				  renderblocks.colorBlueTopRight *= f2;
				  renderblocks.renderWestFace(block, i, j, k, icon);
			  }

			  flag = true;
		  }

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i + 1, j, k, 5))
		  {
			  if (renderblocks.renderMaxX >= 1.0D)
			  {
				  ++i;
			  }

			  renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k);
			  renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k - 1);
			  renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(iblockaccess, i, j, k + 1);
			  renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k);
			  renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k);
			  renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(iblockaccess, i, j, k - 1);
			  renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(iblockaccess, i, j, k + 1);
			  renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k);
			  flag2 = Block.canBlockGrass[iblockaccess.getBlockId(i + 1, j + 1, k)];
			  flag4 = Block.canBlockGrass[iblockaccess.getBlockId(i + 1, j - 1, k)];
			  flag3 = Block.canBlockGrass[iblockaccess.getBlockId(i + 1, j, k + 1)];
			  flag5 = Block.canBlockGrass[iblockaccess.getBlockId(i + 1, j, k - 1)];

			  if (!flag4 && !flag5)
			  {
				  renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
				  renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k - 1);
				  renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k - 1);
			  }

			  if (!flag4 && !flag3)
			  {
				  renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
				  renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(iblockaccess, i, j - 1, k + 1);
				  renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k + 1);
			  }

			  if (!flag2 && !flag5)
			  {
				  renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
				  renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k - 1);
				  renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k - 1);
			  }

			  if (!flag2 && !flag3)
			  {
				  renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
				  renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
			  }
			  else
			  {
				  renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(iblockaccess, i, j + 1, k + 1);
				  renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k + 1);
			  }

			  if (renderblocks.renderMaxX >= 1.0D)
			  {
				  --i;
			  }

			  i1 = l;

			  if (renderblocks.renderMaxX >= 1.0D || !iblockaccess.isBlockOpaqueCube(i + 1, j, k))
			  {
				  i1 = block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k);
			  }

			  f7 = block.getAmbientOcclusionLightValue(iblockaccess, i + 1, j, k);
			  f3 = (renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNP + f7 + renderblocks.aoLightValueScratchXZPP) / 4.0F;
			  f4 = (renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXZPN + f7) / 4.0F;
			  f5 = (renderblocks.aoLightValueScratchXZPN + f7 + renderblocks.aoLightValueScratchXYZPPN + renderblocks.aoLightValueScratchXYPP) / 4.0F;
			  f6 = (f7 + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
			  renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, i1);
			  renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPP, i1);
			  renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, renderblocks.aoBrightnessXYPP, i1);
			  renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXZPN, i1);

			  if (flag1)
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = f * 0.6F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = f1 * 0.6F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = f2 * 0.6F;
			  }
			  else
			  {
				  renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = 0.6F;
				  renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = 0.6F;
				  renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = 0.6F;
			  }

			  renderblocks.colorRedTopLeft *= f3;
			  renderblocks.colorGreenTopLeft *= f3;
			  renderblocks.colorBlueTopLeft *= f3;
			  renderblocks.colorRedBottomLeft *= f4;
			  renderblocks.colorGreenBottomLeft *= f4;
			  renderblocks.colorBlueBottomLeft *= f4;
			  renderblocks.colorRedBottomRight *= f5;
			  renderblocks.colorGreenBottomRight *= f5;
			  renderblocks.colorBlueBottomRight *= f5;
			  renderblocks.colorRedTopRight *= f6;
			  renderblocks.colorGreenTopRight *= f6;
			  renderblocks.colorBlueTopRight *= f6;
			  icon1 = renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 5);
			  renderblocks.renderEastFace(block, i, j, k, icon1);

			  if (RenderBlocks.fancyGrass)
			  {
				  renderblocks.colorRedTopLeft *= f;
				  renderblocks.colorRedBottomLeft *= f;
				  renderblocks.colorRedBottomRight *= f;
				  renderblocks.colorRedTopRight *= f;
				  renderblocks.colorGreenTopLeft *= f1;
				  renderblocks.colorGreenBottomLeft *= f1;
				  renderblocks.colorGreenBottomRight *= f1;
				  renderblocks.colorGreenTopRight *= f1;
				  renderblocks.colorBlueTopLeft *= f2;
				  renderblocks.colorBlueBottomLeft *= f2;
				  renderblocks.colorBlueBottomRight *= f2;
				  renderblocks.colorBlueTopRight *= f2;
				  renderblocks.renderEastFace(block, i, j, k, icon);
			  }

			  flag = true;
		  }

		  renderblocks.enableAO = false;
		  return flag;
	  }

	  public static boolean renderCustomGrassBlockWithColorMultiplier(RenderBlocks renderblocks, IBlockAccess iblockaccess, Block block, int i, int j, int k, float f, float f1, float f2)
	  {
		  renderblocks.enableAO = false;
		  Tessellator tessellator = Tessellator.instance;
		  boolean flag = false;
		  float f3 = 0.5F;
		  float f4 = 1.0F;
		  float f5 = 0.8F;
		  float f6 = 0.6F;
		  float f7 = f4 * f;
		  float f8 = f4 * f1;
		  float f9 = f4 * f2;
		  Icon icon = iconGrassSideOverlay;

		  if (!((BlockDirtSlab)MCE_Items.dirtSlab).GetIsUpsideDown(iblockaccess, i, j, k))
		  {
			  icon = iconGrassSideOverlayHalf;
		  }

		  int l = block.getMixedBrightnessForBlock(iblockaccess, i, j, k);

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i, j - 1, k, 0))
		  {
			  tessellator.setBrightness(renderblocks.renderMinY > 0.0D ? l : block.getMixedBrightnessForBlock(iblockaccess, i, j - 1, k));
			  tessellator.setColorOpaque_F(f3, f3, f3);
			  renderblocks.renderBottomFace(block, i, j, k, renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 0));
			  flag = true;
		  }

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i, j + 1, k, 1))
		  {
			  tessellator.setBrightness(renderblocks.renderMaxY < 1.0D ? l : block.getMixedBrightnessForBlock(iblockaccess, i, j + 1, k));
			  tessellator.setColorOpaque_F(f7, f8, f9);
			  renderblocks.renderTopFace(block, i, j, k, renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 1));
			  flag = true;
		  }

		  Icon icon1;

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i, j, k - 1, 2))
		  {
			  tessellator.setBrightness(renderblocks.renderMinZ > 0.0D ? l : block.getMixedBrightnessForBlock(iblockaccess, i, j, k - 1));
			  tessellator.setColorOpaque_F(f5, f5, f5);
			  icon1 = renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 2);
			  renderblocks.renderNorthFace(block, i, j, k, icon1);

			  if (RenderBlocks.cfgGrassFix)
			  {
				  tessellator.setColorOpaque_F(f5 * f, f5 * f1, f5 * f2);
				  renderblocks.renderNorthFace(block, i, j, k, icon);
			  }

			  flag = true;
		  }

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i, j, k + 1, 3))
		  {
			  tessellator.setBrightness(renderblocks.renderMaxZ < 1.0D ? l : block.getMixedBrightnessForBlock(iblockaccess, i, j, k + 1));
			  tessellator.setColorOpaque_F(f5, f5, f5);
			  icon1 = renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 3);
			  renderblocks.renderSouthFace(block, i, j, k, icon1);

			  if (RenderBlocks.cfgGrassFix)
			  {
				  tessellator.setColorOpaque_F(f5 * f, f5 * f1, f5 * f2);
				  renderblocks.renderSouthFace(block, i, j, k, icon);
			  }

			  flag = true;
		  }

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i - 1, j, k, 4))
		  {
			  tessellator.setBrightness(renderblocks.renderMinX > 0.0D ? l : block.getMixedBrightnessForBlock(iblockaccess, i - 1, j, k));
			  tessellator.setColorOpaque_F(f6, f6, f6);
			  icon1 = renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 4);
			  renderblocks.renderWestFace(block, i, j, k, icon1);

			  if (RenderBlocks.cfgGrassFix)
			  {
				  tessellator.setColorOpaque_F(f6 * f, f6 * f1, f6 * f2);
				  renderblocks.renderWestFace(block, i, j, k, icon);
			  }

			  flag = true;
		  }

		  if (renderblocks.renderAllFaces || block.shouldSideBeRendered(iblockaccess, i + 1, j, k, 5))
		  {
			  tessellator.setBrightness(renderblocks.renderMaxX < 1.0D ? l : block.getMixedBrightnessForBlock(iblockaccess, i + 1, j, k));
			  tessellator.setColorOpaque_F(f6, f6, f6);
			  icon1 = renderblocks.getBlockIcon(block, iblockaccess, i, j, k, 5);
			  renderblocks.renderEastFace(block, i, j, k, icon1);

			  if (RenderBlocks.cfgGrassFix)
			  {
				  tessellator.setColorOpaque_F(f6 * f, f6 * f1, f6 * f2);
				  renderblocks.renderEastFace(block, i, j, k, icon);
			  }

			  flag = true;
		  }

		  return flag;
	  }
}
