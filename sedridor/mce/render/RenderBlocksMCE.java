package sedridor.mce.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.Config;
import net.minecraft.src.ConnectedProperties;
import net.minecraft.src.ConnectedTextures;
import net.minecraft.src.CustomColorizer;
import net.minecraft.src.NaturalProperties;
import net.minecraft.src.NaturalTextures;
import net.minecraft.src.Reflector;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sedridor.mce.*;
import sedridor.mce.blocks.*;
import sedridor.mce.proxy.*;

@SideOnly(Side.CLIENT)
public class RenderBlocksMCE
{
	/** The IBlockAccess used by this instance of RenderBlocks */
	public IBlockAccess blockAccess;

	/**
	 * If set to >=0, all block faces will be rendered using this texture index
	 */
	public Icon overrideBlockTexture = null;

	/**
	 * Set to true if the texture should be flipped horizontally during render*Face
	 */
	public boolean flipTexture = false;

	/**
	 * If true, renders all faces on all blocks rather than using the logic in Block.shouldSideBeRendered.  Unused.
	 */
	public boolean renderAllFaces = false;

	/** Fancy grass side matching biome */
	public static boolean fancyGrass = true;
	public static boolean cfgGrassFix = true;
	public boolean useInventoryTint = true;

	/** The minimum X value for rendering (default 0.0). */
	public double renderMinX;

	/** The maximum X value for rendering (default 1.0). */
	public double renderMaxX;

	/** The minimum Y value for rendering (default 0.0). */
	public double renderMinY;

	/** The maximum Y value for rendering (default 1.0). */
	public double renderMaxY;

	/** The minimum Z value for rendering (default 0.0). */
	public double renderMinZ;

	/** The maximum Z value for rendering (default 1.0). */
	public double renderMaxZ;

	/**
	 * Set by overrideBlockBounds, to keep this class from changing the visual bounding box.
	 */
	public boolean lockBlockBounds = false;
	public boolean partialRenderBounds = false;
	public final Minecraft minecraftRB;
	public int uvRotateNorth = 0;
	public int uvRotateSouth = 0;
	public int uvRotateEast = 0;
	public int uvRotateWest = 0;
	public int uvRotateTop = 0;
	public int uvRotateBottom = 0;

	/** Whether ambient occlusion is enabled or not */
	public boolean enableAO;

	/**
	 * Used as a scratch variable for ambient occlusion on the north/bottom/east corner.
	 */
	public float aoLightValueScratchXYZNNN;

	/**
	 * Used as a scratch variable for ambient occlusion between the bottom face and the north face.
	 */
	public float aoLightValueScratchXYNN;

	/**
	 * Used as a scratch variable for ambient occlusion on the north/bottom/west corner.
	 */
	public float aoLightValueScratchXYZNNP;

	/**
	 * Used as a scratch variable for ambient occlusion between the bottom face and the east face.
	 */
	public float aoLightValueScratchYZNN;

	/**
	 * Used as a scratch variable for ambient occlusion between the bottom face and the west face.
	 */
	public float aoLightValueScratchYZNP;

	/**
	 * Used as a scratch variable for ambient occlusion on the south/bottom/east corner.
	 */
	public float aoLightValueScratchXYZPNN;

	/**
	 * Used as a scratch variable for ambient occlusion between the bottom face and the south face.
	 */
	public float aoLightValueScratchXYPN;

	/**
	 * Used as a scratch variable for ambient occlusion on the south/bottom/west corner.
	 */
	public float aoLightValueScratchXYZPNP;

	/**
	 * Used as a scratch variable for ambient occlusion on the north/top/east corner.
	 */
	public float aoLightValueScratchXYZNPN;

	/**
	 * Used as a scratch variable for ambient occlusion between the top face and the north face.
	 */
	public float aoLightValueScratchXYNP;

	/**
	 * Used as a scratch variable for ambient occlusion on the north/top/west corner.
	 */
	public float aoLightValueScratchXYZNPP;

	/**
	 * Used as a scratch variable for ambient occlusion between the top face and the east face.
	 */
	public float aoLightValueScratchYZPN;

	/**
	 * Used as a scratch variable for ambient occlusion on the south/top/east corner.
	 */
	public float aoLightValueScratchXYZPPN;

	/**
	 * Used as a scratch variable for ambient occlusion between the top face and the south face.
	 */
	public float aoLightValueScratchXYPP;

	/**
	 * Used as a scratch variable for ambient occlusion between the top face and the west face.
	 */
	public float aoLightValueScratchYZPP;

	/**
	 * Used as a scratch variable for ambient occlusion on the south/top/west corner.
	 */
	public float aoLightValueScratchXYZPPP;

	/**
	 * Used as a scratch variable for ambient occlusion between the north face and the east face.
	 */
	public float aoLightValueScratchXZNN;

	/**
	 * Used as a scratch variable for ambient occlusion between the south face and the east face.
	 */
	public float aoLightValueScratchXZPN;

	/**
	 * Used as a scratch variable for ambient occlusion between the north face and the west face.
	 */
	public float aoLightValueScratchXZNP;

	/**
	 * Used as a scratch variable for ambient occlusion between the south face and the west face.
	 */
	public float aoLightValueScratchXZPP;

	/** Ambient occlusion brightness XYZNNN */
	public int aoBrightnessXYZNNN;

	/** Ambient occlusion brightness XYNN */
	public int aoBrightnessXYNN;

	/** Ambient occlusion brightness XYZNNP */
	public int aoBrightnessXYZNNP;

	/** Ambient occlusion brightness YZNN */
	public int aoBrightnessYZNN;

	/** Ambient occlusion brightness YZNP */
	public int aoBrightnessYZNP;

	/** Ambient occlusion brightness XYZPNN */
	public int aoBrightnessXYZPNN;

	/** Ambient occlusion brightness XYPN */
	public int aoBrightnessXYPN;

	/** Ambient occlusion brightness XYZPNP */
	public int aoBrightnessXYZPNP;

	/** Ambient occlusion brightness XYZNPN */
	public int aoBrightnessXYZNPN;

	/** Ambient occlusion brightness XYNP */
	public int aoBrightnessXYNP;

	/** Ambient occlusion brightness XYZNPP */
	public int aoBrightnessXYZNPP;

	/** Ambient occlusion brightness YZPN */
	public int aoBrightnessYZPN;

	/** Ambient occlusion brightness XYZPPN */
	public int aoBrightnessXYZPPN;

	/** Ambient occlusion brightness XYPP */
	public int aoBrightnessXYPP;

	/** Ambient occlusion brightness YZPP */
	public int aoBrightnessYZPP;

	/** Ambient occlusion brightness XYZPPP */
	public int aoBrightnessXYZPPP;

	/** Ambient occlusion brightness XZNN */
	public int aoBrightnessXZNN;

	/** Ambient occlusion brightness XZPN */
	public int aoBrightnessXZPN;

	/** Ambient occlusion brightness XZNP */
	public int aoBrightnessXZNP;

	/** Ambient occlusion brightness XZPP */
	public int aoBrightnessXZPP;

	/** Brightness top left */
	public int brightnessTopLeft;

	/** Brightness bottom left */
	public int brightnessBottomLeft;

	/** Brightness bottom right */
	public int brightnessBottomRight;

	/** Brightness top right */
	public int brightnessTopRight;

	/** Red color value for the top left corner */
	public float colorRedTopLeft;

	/** Red color value for the bottom left corner */
	public float colorRedBottomLeft;

	/** Red color value for the bottom right corner */
	public float colorRedBottomRight;

	/** Red color value for the top right corner */
	public float colorRedTopRight;

	/** Green color value for the top left corner */
	public float colorGreenTopLeft;

	/** Green color value for the bottom left corner */
	public float colorGreenBottomLeft;

	/** Green color value for the bottom right corner */
	public float colorGreenBottomRight;

	/** Green color value for the top right corner */
	public float colorGreenTopRight;

	/** Blue color value for the top left corner */
	public float colorBlueTopLeft;

	/** Blue color value for the bottom left corner */
	public float colorBlueBottomLeft;

	/** Blue color value for the bottom right corner */
	public float colorBlueBottomRight;

	/** Blue color value for the top right corner */
	public float colorBlueTopRight;
	public boolean aoLightValuesCalculated;
	public float aoLightValueOpaque = 0.2F;

	public RenderBlocksMCE(IBlockAccess par1IBlockAccess)
	{
		this.blockAccess = par1IBlockAccess;
		this.minecraftRB = Minecraft.getMinecraft();
		this.aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
	}

	public RenderBlocksMCE()
	{
		this.minecraftRB = Minecraft.getMinecraft();
	}

	/**
	 * Sets overrideBlockTexture
	 */
	public void setOverrideBlockTexture(Icon par1Icon)
	{
		this.overrideBlockTexture = par1Icon;
	}

	/**
	 * Clear override block texture
	 */
	public void clearOverrideBlockTexture()
	{
		this.overrideBlockTexture = null;
	}

	public boolean hasOverrideBlockTexture()
	{
		return this.overrideBlockTexture != null;
	}

	/**
	 * Sets the bounding box for the block to draw in, e.g. 0.25-0.75 on all axes for a half-size, centered block.
	 */
	public void setRenderBounds(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		if (!this.lockBlockBounds)
		{
			this.renderMinX = par1;
			this.renderMaxX = par7;
			this.renderMinY = par3;
			this.renderMaxY = par9;
			this.renderMinZ = par5;
			this.renderMaxZ = par11;
			this.partialRenderBounds = this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0D || this.renderMaxX < 1.0D || this.renderMinY > 0.0D || this.renderMaxY < 1.0D || this.renderMinZ > 0.0D || this.renderMaxZ < 1.0D);
		}
	}

	/**
	 * Like setRenderBounds, but automatically pulling the bounds from the given Block.
	 */
	public void setRenderBoundsFromBlock(Block par1Block)
	{
		if (!this.lockBlockBounds)
		{
			this.renderMinX = par1Block.getBlockBoundsMinX();
			this.renderMaxX = par1Block.getBlockBoundsMaxX();
			this.renderMinY = par1Block.getBlockBoundsMinY();
			this.renderMaxY = par1Block.getBlockBoundsMaxY();
			this.renderMinZ = par1Block.getBlockBoundsMinZ();
			this.renderMaxZ = par1Block.getBlockBoundsMaxZ();
			this.partialRenderBounds = this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0D || this.renderMaxX < 1.0D || this.renderMinY > 0.0D || this.renderMaxY < 1.0D || this.renderMinZ > 0.0D || this.renderMaxZ < 1.0D);
		}
	}

	/**
	 * Like setRenderBounds, but locks the values so that RenderBlocks won't change them.  If you use this, you must
	 * call unlockBlockBounds after you finish rendering!
	 */
	public void overrideBlockBounds(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		this.renderMinX = par1;
		this.renderMaxX = par7;
		this.renderMinY = par3;
		this.renderMaxY = par9;
		this.renderMinZ = par5;
		this.renderMaxZ = par11;
		this.lockBlockBounds = true;
		this.partialRenderBounds = this.minecraftRB.gameSettings.ambientOcclusion >= 2 && (this.renderMinX > 0.0D || this.renderMaxX < 1.0D || this.renderMinY > 0.0D || this.renderMaxY < 1.0D || this.renderMinZ > 0.0D || this.renderMaxZ < 1.0D);
	}

	/**
	 * Unlocks the visual bounding box so that RenderBlocks can change it again.
	 */
	public void unlockBlockBounds()
	{
		this.lockBlockBounds = false;
	}

	/**
	 * Renders a block using the given texture instead of the block's own default texture
	 */
	public void renderBlockUsingTexture(Block par1Block, int par2, int par3, int par4, Icon par5Icon)
	{
		this.setOverrideBlockTexture(par5Icon);
		this.renderBlockByRenderType(par1Block, par2, par3, par4);
		this.clearOverrideBlockTexture();
	}

	/**
	 * Render all faces of a block
	 */
	public void renderBlockAllFaces(Block par1Block, int par2, int par3, int par4)
	{
		this.renderAllFaces = true;
		this.renderBlockByRenderType(par1Block, par2, par3, par4);
		this.renderAllFaces = false;
	}

	/**
	 * Renders the block at the given coordinates using the block's rendering type
	 */
	public boolean renderBlockByRenderType(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = par1Block.getRenderType();

		if (var5 == -1)
		{
			return false;
		}
		else
		{
			par1Block.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);

			if (Config.isBetterSnow() && par1Block == Block.signPost && this.hasSnowNeighbours(par2, par3, par4))
			{
				this.renderSnow(par2, par3, par4, Block.snow.maxY);
			}

			this.setRenderBoundsFromBlock(par1Block);

			return var5 == 0 ? this.renderStandardBlock(par1Block, par2, par3, par4) : (var5 == MCE_Items.lightBulbModelID ? this.renderLightBulb((BlockLightBulb)par1Block, par2, par3, par4) : false);
			/*switch (var5)
            {
                case 0:
                    return this.renderStandardBlock(par1Block, par2, par3, par4);

                case 1:
                    return this.renderCrossedSquares(par1Block, par2, par3, par4);

                case 2:
                    return this.renderBlockTorch(par1Block, par2, par3, par4);

                case 18:
                    return this.renderBlockPane((BlockPane)par1Block, par2, par3, par4);

                default:
                    if (Reflector.ModLoader.exists())
                    {
                        return Reflector.callBoolean(Reflector.ModLoader_renderWorldBlock, new Object[] {this, this.blockAccess, Integer.valueOf(par2), Integer.valueOf(par3), Integer.valueOf(par4), par1Block, Integer.valueOf(var5)});
                    }
                    else
                    {
                        if (Reflector.FMLRenderAccessLibrary.exists())
                        {
                            return Reflector.callBoolean(Reflector.FMLRenderAccessLibrary_renderWorldBlock, new Object[] {this, this.blockAccess, Integer.valueOf(par2), Integer.valueOf(par3), Integer.valueOf(par4), par1Block, Integer.valueOf(var5)});
                        }

                        return false;
                    }

                case 31:
                    return this.renderBlockLog(par1Block, par2, par3, par4);

                case 34:
                    return this.renderBlockBeacon((BlockBeacon)par1Block, par2, par3, par4);
            }*/
		}
	}

	public boolean renderLightBulb(BlockLightBulb block, int posX, int posY, int posZ)
	{
		boolean powered = block.isPowered();

		int metaID = this.blockAccess.getBlockMetadata(posX, posY, posZ);
		if (metaID == 1)
		{
			this.uvRotateBottom = 1;
			this.uvRotateTop = 2;
			this.uvRotateNorth = 1;
			this.uvRotateSouth = 2;
			this.uvRotateWest = 0;
			this.uvRotateEast = 0;
			this.setRenderBounds(0.0F, 0.4375F, 0.4375F, 0.0625F, 0.5625F, 0.5625F);
			this.renderStandardBlock(block, posX, posY, posZ);

			this.setRenderBounds(0.0625F, 0.4375F, 0.4375F, 0.25F, 0.5625F, 0.5625F);
			//this.renderStandardBlock(block, posX, posY, posZ);
		}
		else if (metaID == 2)
		{
			this.uvRotateBottom = 2;
			this.uvRotateTop = 1;
			this.uvRotateNorth = 2;
			this.uvRotateSouth = 1;
			this.uvRotateWest = 0;
			this.uvRotateEast = 0;
			this.setRenderBounds(0.9375F, 0.4375F, 0.4375F, 1.0F, 0.5625F, 0.5625F);
			this.renderStandardBlock(block, posX, posY, posZ);

			this.setRenderBounds(0.75F, 0.4375F, 0.4375F, 0.9375F, 0.5625F, 0.5625F);
			//this.renderStandardBlock(block, posX, posY, posZ);
		}
		else if (metaID == 3)
		{
			this.uvRotateBottom = 0;
			this.uvRotateTop = 0;
			this.uvRotateNorth = 0;
			this.uvRotateSouth = 0;
			this.uvRotateWest = 2;
			this.uvRotateEast = 1;
			this.setRenderBounds(0.4375F, 0.4375F, 0.0F, 0.5625F, 0.5625F, 0.0625F);
			this.renderStandardBlock(block, posX, posY, posZ);

			this.setRenderBounds(0.4375F, 0.4375F, 0.0625F, 0.5625F, 0.5625F, 0.25F);
			//this.renderStandardBlock(block, posX, posY, posZ);
		}
		else if (metaID == 4)
		{
			this.uvRotateBottom = 3;
			this.uvRotateTop = 3;
			this.uvRotateNorth = 0;
			this.uvRotateSouth = 0;
			this.uvRotateWest = 1;
			this.uvRotateEast = 2;
			this.setRenderBounds(0.4375F, 0.4375F, 0.9375F, 0.5625F, 0.5625F, 1.0F);
			this.renderStandardBlock(block, posX, posY, posZ);

			this.setRenderBounds(0.4375F, 0.4375F, 0.75F, 0.5625F, 0.5625F, 0.9375F);
			//this.renderStandardBlock(block, posX, posY, posZ);
		}
		else if (metaID == 5)
		{
			this.uvRotateBottom = 0;
			this.uvRotateTop = 0;
			this.uvRotateNorth = 3;
			this.uvRotateSouth = 3;
			this.uvRotateWest = 3;
			this.uvRotateEast = 3;
			this.setRenderBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.0625F, 0.5625F);
			this.renderStandardBlock(block, posX, posY, posZ);

			this.setRenderBounds(0.4375F, 0.0625F, 0.4375F, 0.5625F, 0.25F, 0.5625F);
			//this.renderStandardBlock(block, posX, posY, posZ);
		}
		else
		{
			this.setRenderBounds(0.4375F, 0.9375F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
			this.renderStandardBlock(block, posX, posY, posZ);

			this.setRenderBounds(0.4375F, 0.75F, 0.4375F, 0.5625F, 0.9375F, 0.5625F);
			//this.renderStandardBlock(block, posX, posY, posZ);
		}

		metaID = metaID & 7;

		Tessellator tessellator = Tessellator.instance;
		tessellator.draw();

		int var8 = 12582912;
		double var9 = 0.0005D;
		if (powered)
		{
			var8 = 15728880;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
			int var5 = 15728880;
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
		}
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		tessellator.setBrightness(var8);
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		this.renderBottomFace(block, posX, posY + var9, posZ, block.getIcon(0, metaID));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.setBrightness(var8);
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		this.renderTopFace(block, posX, posY - var9, posZ, block.getIcon(1, metaID));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		tessellator.setBrightness(var8);
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		this.renderNorthFace(block, posX, posY, posZ + var9, block.getIcon(2, metaID));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		tessellator.setBrightness(var8);
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		this.renderSouthFace(block, posX, posY, posZ - var9, block.getIcon(3, metaID));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		tessellator.setBrightness(var8);
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		this.renderWestFace(block, posX + var9, posY, posZ, block.getIcon(4, metaID));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		tessellator.setBrightness(var8);
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		this.renderEastFace(block, posX - var9, posY, posZ, block.getIcon(5, metaID));
		tessellator.draw();
		this.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		if (powered)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		tessellator.startDrawingQuads();
		return true;
	}

	/**
	 * Renders a lamp block at the given coordinates
	 */
	public boolean renderBlockLamp(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
		Tessellator var6 = Tessellator.instance;
		var6.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
		var6.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		double var7 = 0.4D;
		double var9 = 0.5D - var7;
		double var11 = 0.2D;

		if (var5 == 1)
		{
			this.renderLampAtAngle(par1Block, par2 - var9, par3 + var11, par4, -var7, 0.0D, var5);
		}
		else if (var5 == 2)
		{
			this.renderLampAtAngle(par1Block, par2 + var9, par3 + var11, par4, var7, 0.0D, var5);
		}
		else if (var5 == 3)
		{
			this.renderLampAtAngle(par1Block, par2, par3 + var11, par4 - var9, 0.0D, -var7, var5);
		}
		else if (var5 == 4)
		{
			this.renderLampAtAngle(par1Block, par2, par3 + var11, par4 + var9, 0.0D, var7, var5);
		}
		else if (var5 == 5)
		{
			this.renderLampAtAngle(par1Block, par2, par3, par4, 0.0D, 0.0D, var5);
		}
		else
		{
			this.renderLampAtAngle(par1Block, par2, par3, par4, 0.0D, 0.0D, var5);
		}

		return true;
	}

	/**
	 * Renders a lamp at the given coordinates, with the base slanting at the given delta
	 */
	public void renderLampAtAngle(Block par1Block, double par2, double par4, double par6, double par8, double par10, int par12)
	{
		Tessellator var12 = Tessellator.instance;
		Icon var14 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par12);

		if (this.hasOverrideBlockTexture())
		{
			var14 = this.overrideBlockTexture;
		}

		double var16 = var14.getMinU();
		double var17 = var14.getMaxU();
		double var18 = var14.getMinV();
		double var19 = var14.getMaxV();
		double var20 = var14.getInterpolatedU(7.0D);
		double var22 = var14.getInterpolatedV(6.0D);
		double var24 = var14.getInterpolatedU(9.0D);
		double var26 = var14.getInterpolatedV(8.0D);
		double var40 = var14.getInterpolatedV(14.0D);
		double var42 = var14.getInterpolatedV(16.0D);
		par2 += 0.5D;
		par6 += 0.5D;
		double var28 = par2 - 0.5D;
		double var30 = par2 + 0.5D;
		double var32 = par6 - 0.5D;
		double var34 = par6 + 0.5D;
		double var36 = 0.0625D;
		double var38 = 0.625D;

		double var44 = 0.0005D;
		double posX = par2;
		double posY = par4;
		double posZ = par6;
		if (par12 < 6)
		{
			par4 = posY - var44;
			var12.addVertexWithUV(par2 + par8 * (1.0D - var38) - var36, par4 + var38, par6 + par10 * (1.0D - var38) - var36, par12 == 4 || par12 == 1 ? var24 : var20, par12 == 4 || par12 == 1 ? var26 : var22);
			var12.addVertexWithUV(par2 + par8 * (1.0D - var38) - var36, par4 + var38, par6 + par10 * (1.0D - var38) + var36, par12 == 4 || par12 == 2 ? var24 : var20, par12 == 4 || par12 == 2 ? var22 : var26);
			var12.addVertexWithUV(par2 + par8 * (1.0D - var38) + var36, par4 + var38, par6 + par10 * (1.0D - var38) + var36, par12 == 4 || par12 == 1 ? var20 : var24, par12 == 4 || par12 == 1 ? var22 : var26);
			var12.addVertexWithUV(par2 + par8 * (1.0D - var38) + var36, par4 + var38, par6 + par10 * (1.0D - var38) - var36, par12 == 4 || par12 == 2 ? var20 : var24, par12 == 4 || par12 == 2 ? var26 : var22);

			par4 = posY + var44;
			var12.addVertexWithUV(par2 + var36 + par8, par4, par6 - var36 + par10, (par12 == 4 || par12 == 2 ? var20 : var24), par12 == 4 || par12 == 2 ? var42 : var40);
			var12.addVertexWithUV(par2 + var36 + par8, par4, par6 + var36 + par10, (par12 == 4 || par12 == 1 ? var20 : var24), par12 == 4 || par12 == 1 ? var40 : var42);
			var12.addVertexWithUV(par2 - var36 + par8, par4, par6 + var36 + par10, (par12 == 4 || par12 == 2 ? var24 : var20), par12 == 4 || par12 == 2 ? var40 : var42);
			var12.addVertexWithUV(par2 - var36 + par8, par4, par6 - var36 + par10, (par12 == 4 || par12 == 1 ? var24 : var20), par12 == 4 || par12 == 1 ? var42 : var40);
			par4 = posY;

			par2 = posX + var44;
			var12.addVertexWithUV(par2 - var36, par4 + 1.0D, var32, var16, var18);	// West
			var12.addVertexWithUV(par2 - var36 + par8, par4 + 0.0D, var32 + par10, var16, var19);
			var12.addVertexWithUV(par2 - var36 + par8, par4 + 0.0D, var34 + par10, var17, var19);
			var12.addVertexWithUV(par2 - var36, par4 + 1.0D, var34, var17, var18);

			par2 = posX - var44;
			var12.addVertexWithUV(par2 + var36, par4 + 1.0D, var34, var16, var18);	// East
			var12.addVertexWithUV(par2 + par8 + var36, par4 + 0.0D, var34 + par10, var16, var19);
			var12.addVertexWithUV(par2 + par8 + var36, par4 + 0.0D, var32 + par10, var17, var19);
			var12.addVertexWithUV(par2 + var36, par4 + 1.0D, var32, var17, var18);
			par2 = posX;

			par6 = posZ - var44;
			var12.addVertexWithUV(var28, par4 + 1.0D, par6 + var36, var16, var18);	// South
			var12.addVertexWithUV(var28 + par8, par4 + 0.0D, par6 + var36 + par10, var16, var19);
			var12.addVertexWithUV(var30 + par8, par4 + 0.0D, par6 + var36 + par10, var17, var19);
			var12.addVertexWithUV(var30, par4 + 1.0D, par6 + var36, var17, var18);

			par6 = posZ + var44;
			var12.addVertexWithUV(var30, par4 + 1.0D, par6 - var36, var16, var18);	// North
			var12.addVertexWithUV(var30 + par8, par4 + 0.0D, par6 - var36 + par10, var16, var19);
			var12.addVertexWithUV(var28 + par8, par4 + 0.0D, par6 - var36 + par10, var17, var19);
			var12.addVertexWithUV(var28, par4 + 1.0D, par6 - var36, var17, var18);
			par6 = posZ;
		}
		else
		{
			par4 = posY - var44;
			var12.addVertexWithUV(par2 + par8 * (1.0D - var38) - var36, par4 + 1.0D, par6 + par10 * (1.0D - var38) - var36, var24, var40);
			var12.addVertexWithUV(par2 + par8 * (1.0D - var38) - var36, par4 + 1.0D, par6 + par10 * (1.0D - var38) + var36, var24, var42);
			var12.addVertexWithUV(par2 + par8 * (1.0D - var38) + var36, par4 + 1.0D, par6 + par10 * (1.0D - var38) + var36, var20, var42);
			var12.addVertexWithUV(par2 + par8 * (1.0D - var38) + var36, par4 + 1.0D, par6 + par10 * (1.0D - var38) - var36, var20, var40);

			par4 = posY + var44;
			var12.addVertexWithUV(par2 + var36 + par8, par4 + 1.0D - var38, par6 - var36 + par10, var20, var22);
			var12.addVertexWithUV(par2 + var36 + par8, par4 + 1.0D - var38, par6 + var36 + par10, var20, var26);
			var12.addVertexWithUV(par2 - var36 + par8, par4 + 1.0D - var38, par6 + var36 + par10, var24, var26);
			var12.addVertexWithUV(par2 - var36 + par8, par4 + 1.0D - var38, par6 - var36 + par10, var24, var22);
			par4 = posY;

			par2 = posX + var44;
			var12.addVertexWithUV(par2 - var36, par4 + 1.0D, var32, var16, var19);	// West
			var12.addVertexWithUV(par2 - var36 + par8, par4 + 0.0D, var32 + par10, var16, var18);
			var12.addVertexWithUV(par2 - var36 + par8, par4 + 0.0D, var34 + par10, var17, var18);
			var12.addVertexWithUV(par2 - var36, par4 + 1.0D, var34, var17, var19);

			par2 = posX - var44;
			var12.addVertexWithUV(par2 + var36, par4 + 1.0D, var34, var16, var19);	// East
			var12.addVertexWithUV(par2 + par8 + var36, par4 + 0.0D, var34 + par10, var16, var18);
			var12.addVertexWithUV(par2 + par8 + var36, par4 + 0.0D, var32 + par10, var17, var18);
			var12.addVertexWithUV(par2 + var36, par4 + 1.0D, var32, var17, var19);
			par2 = posX;

			par6 = posZ - var44;
			var12.addVertexWithUV(var28, par4 + 1.0D, par6 + var36, var16, var19);	// South
			var12.addVertexWithUV(var28 + par8, par4 + 0.0D, par6 + var36 + par10, var16, var18);
			var12.addVertexWithUV(var30 + par8, par4 + 0.0D, par6 + var36 + par10, var17, var18);
			var12.addVertexWithUV(var30, par4 + 1.0D, par6 + var36, var17, var19);

			par6 = posZ + var44;
			var12.addVertexWithUV(var30, par4 + 1.0D, par6 - var36, var16, var19);	// North
			var12.addVertexWithUV(var30 + par8, par4 + 0.0D, par6 - var36 + par10, var16, var18);
			var12.addVertexWithUV(var28 + par8, par4 + 0.0D, par6 - var36 + par10, var17, var18);
			var12.addVertexWithUV(var28, par4 + 1.0D, par6 - var36, var17, var19);
			par6 = posZ;
		}
	}

	/**
	 * Renders a redstone wire block at the given coordinates
	 */
	public boolean renderBlockRedstoneWire(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		int var6 = this.blockAccess.getBlockMetadata(par2, par3, par4);
		Icon var7 = BlockRedstoneWire.func_94409_b("redstoneDust_cross");
		Icon var8 = BlockRedstoneWire.func_94409_b("redstoneDust_line");
		Icon var9 = BlockRedstoneWire.func_94409_b("redstoneDust_cross_overlay");
		Icon var10 = BlockRedstoneWire.func_94409_b("redstoneDust_line_overlay");
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
		float var11 = 1.0F;
		float var12 = var6 / 15.0F;
		float var13 = var12 * 0.6F + 0.4F;

		if (var6 == 0)
		{
			var13 = 0.3F;
		}

		float var14 = var12 * var12 * 0.7F - 0.5F;
		float var15 = var12 * var12 * 0.6F - 0.7F;

		if (var14 < 0.0F)
		{
			var14 = 0.0F;
		}

		if (var15 < 0.0F)
		{
			var15 = 0.0F;
		}

		int var16 = CustomColorizer.getRedstoneColor(var6);

		if (var16 != -1)
		{
			int var17 = var16 >> 16 & 255;
		int var18 = var16 >> 8 & 255;
		int var19 = var16 & 255;
		var13 = var17 / 255.0F;
		var14 = var18 / 255.0F;
		var15 = var19 / 255.0F;
		}

		var5.setColorOpaque_F(var13, var14, var15);
		boolean var30 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3, par4, 1) || !this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3 - 1, par4, -1);
		boolean var32 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3, par4, 3) || !this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3 - 1, par4, -1);
		boolean var31 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3, par4 - 1, 2) || !this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 - 1, par4 - 1, -1);
		boolean var20 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3, par4 + 1, 0) || !this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 - 1, par4 + 1, -1);

		if (!this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4))
		{
			if (this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 - 1, par3 + 1, par4, -1))
			{
				var30 = true;
			}

			if (this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2 + 1, par3 + 1, par4, -1))
			{
				var32 = true;
			}

			if (this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 + 1, par4 - 1, -1))
			{
				var31 = true;
			}

			if (this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, par2, par3 + 1, par4 + 1, -1))
			{
				var20 = true;
			}
		}

		float var21 = par2 + 0;
		float var22 = par2 + 1;
		float var23 = par4 + 0;
		float var24 = par4 + 1;
		boolean var25 = false;

		if ((var30 || var32) && !var31 && !var20)
		{
			var25 = true;
		}

		if ((var31 || var20) && !var32 && !var30)
		{
			var25 = true;
		}

		if (!var25)
		{
			int var26 = 0;
			int var27 = 0;
			int var28 = 16;
			int var29 = 16;

			if (!var30)
			{
				var21 += 0.3125F;
			}

			if (!var30)
			{
				var26 += 5;
			}

			if (!var32)
			{
				var22 -= 0.3125F;
			}

			if (!var32)
			{
				var28 -= 5;
			}

			if (!var31)
			{
				var23 += 0.3125F;
			}

			if (!var31)
			{
				var27 += 5;
			}

			if (!var20)
			{
				var24 -= 0.3125F;
			}

			if (!var20)
			{
				var29 -= 5;
			}

			var5.addVertexWithUV(var22, par3 + 0.015625D, var24, var7.getInterpolatedU(var28), var7.getInterpolatedV(var29));
			var5.addVertexWithUV(var22, par3 + 0.015625D, var23, var7.getInterpolatedU(var28), var7.getInterpolatedV(var27));
			var5.addVertexWithUV(var21, par3 + 0.015625D, var23, var7.getInterpolatedU(var26), var7.getInterpolatedV(var27));
			var5.addVertexWithUV(var21, par3 + 0.015625D, var24, var7.getInterpolatedU(var26), var7.getInterpolatedV(var29));
			var5.setColorOpaque_F(var11, var11, var11);
			var5.addVertexWithUV(var22, par3 + 0.015625D, var24, var9.getInterpolatedU(var28), var9.getInterpolatedV(var29));
			var5.addVertexWithUV(var22, par3 + 0.015625D, var23, var9.getInterpolatedU(var28), var9.getInterpolatedV(var27));
			var5.addVertexWithUV(var21, par3 + 0.015625D, var23, var9.getInterpolatedU(var26), var9.getInterpolatedV(var27));
			var5.addVertexWithUV(var21, par3 + 0.015625D, var24, var9.getInterpolatedU(var26), var9.getInterpolatedV(var29));
		}
		else if (var25)
		{
			var5.addVertexWithUV(var22, par3 + 0.015625D, var24, var8.getMaxU(), var8.getMaxV());
			var5.addVertexWithUV(var22, par3 + 0.015625D, var23, var8.getMaxU(), var8.getMinV());
			var5.addVertexWithUV(var21, par3 + 0.015625D, var23, var8.getMinU(), var8.getMinV());
			var5.addVertexWithUV(var21, par3 + 0.015625D, var24, var8.getMinU(), var8.getMaxV());
			var5.setColorOpaque_F(var11, var11, var11);
			var5.addVertexWithUV(var22, par3 + 0.015625D, var24, var10.getMaxU(), var10.getMaxV());
			var5.addVertexWithUV(var22, par3 + 0.015625D, var23, var10.getMaxU(), var10.getMinV());
			var5.addVertexWithUV(var21, par3 + 0.015625D, var23, var10.getMinU(), var10.getMinV());
			var5.addVertexWithUV(var21, par3 + 0.015625D, var24, var10.getMinU(), var10.getMaxV());
		}
		else
		{
			var5.addVertexWithUV(var22, par3 + 0.015625D, var24, var8.getMaxU(), var8.getMaxV());
			var5.addVertexWithUV(var22, par3 + 0.015625D, var23, var8.getMinU(), var8.getMaxV());
			var5.addVertexWithUV(var21, par3 + 0.015625D, var23, var8.getMinU(), var8.getMinV());
			var5.addVertexWithUV(var21, par3 + 0.015625D, var24, var8.getMaxU(), var8.getMinV());
			var5.setColorOpaque_F(var11, var11, var11);
			var5.addVertexWithUV(var22, par3 + 0.015625D, var24, var10.getMaxU(), var10.getMaxV());
			var5.addVertexWithUV(var22, par3 + 0.015625D, var23, var10.getMinU(), var10.getMaxV());
			var5.addVertexWithUV(var21, par3 + 0.015625D, var23, var10.getMinU(), var10.getMinV());
			var5.addVertexWithUV(var21, par3 + 0.015625D, var24, var10.getMaxU(), var10.getMinV());
		}

		if (!this.blockAccess.isBlockNormalCube(par2, par3 + 1, par4))
		{
			if (this.blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4) == Block.redstoneWire.blockID)
			{
				var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 1 + 0.021875F, par4 + 1, var8.getMaxU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 0, par4 + 1, var8.getMinU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 0, par4 + 0, var8.getMinU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 1 + 0.021875F, par4 + 0, var8.getMaxU(), var8.getMaxV());
				var5.setColorOpaque_F(var11, var11, var11);
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 1 + 0.021875F, par4 + 1, var10.getMaxU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 0, par4 + 1, var10.getMinU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 0, par4 + 0, var10.getMinU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 1 + 0.021875F, par4 + 0, var10.getMaxU(), var10.getMaxV());
			}

			if (this.blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4) == Block.redstoneWire.blockID)
			{
				var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 0, par4 + 1, var8.getMinU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 1 + 0.021875F, par4 + 1, var8.getMaxU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 1 + 0.021875F, par4 + 0, var8.getMaxU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 0, par4 + 0, var8.getMinU(), var8.getMinV());
				var5.setColorOpaque_F(var11, var11, var11);
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 0, par4 + 1, var10.getMinU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 1 + 0.021875F, par4 + 1, var10.getMaxU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 1 + 0.021875F, par4 + 0, var10.getMaxU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 0, par4 + 0, var10.getMinU(), var10.getMinV());
			}

			if (this.blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1) == Block.redstoneWire.blockID)
			{
				var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
				var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 0.015625D, var8.getMinU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875F, par4 + 0.015625D, var8.getMaxU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875F, par4 + 0.015625D, var8.getMaxU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 0.015625D, var8.getMinU(), var8.getMinV());
				var5.setColorOpaque_F(var11, var11, var11);
				var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 0.015625D, var10.getMinU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875F, par4 + 0.015625D, var10.getMaxU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875F, par4 + 0.015625D, var10.getMaxU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 0.015625D, var10.getMinU(), var10.getMinV());
			}

			if (this.blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1) == Block.redstoneWire.blockID)
			{
				var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
				var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875F, par4 + 1 - 0.015625D, var8.getMaxU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - 0.015625D, var8.getMinU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - 0.015625D, var8.getMinU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875F, par4 + 1 - 0.015625D, var8.getMaxU(), var8.getMaxV());
				var5.setColorOpaque_F(var11, var11, var11);
				var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875F, par4 + 1 - 0.015625D, var10.getMaxU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - 0.015625D, var10.getMinU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - 0.015625D, var10.getMinU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875F, par4 + 1 - 0.015625D, var10.getMaxU(), var10.getMaxV());
			}
		}

		if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
		{
			this.renderSnow(par2, par3, par4, 0.01D);
		}

		return true;
	}

	public boolean renderBlockPane(BlockPane2 par1BlockPane, int par2, int par3, int par4)
	{
		par1BlockPane.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
		this.setRenderBoundsFromBlock(par1BlockPane);

		// Which side was hit. If its -1 then it went the full length of the ray trace.
		// Bottom = 0, Top = 1, South = 2, North = 3, East = 4, West = 5.
		boolean var62 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 - 1));
		boolean var63 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 + 1));
		boolean var60 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 - 1, par3, par4));
		boolean var61 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 + 1, par3, par4));

		if ((var62 || var63) && (!var60 && !var61) || (var62 && var63) && (!var60 || !var61))
		{
			this.setRenderBounds(0.5F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
		}
		else
		{
			this.setRenderBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 0.5625F);
		}
		this.renderStandardBlock(par1BlockPane, par2, par3, par4);
		this.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		return true;
	}

	public boolean renderBlockPane2(BlockPane2 par1BlockPane, int par2, int par3, int par4)
	{
		Tessellator var6 = Tessellator.instance;
		var6.setBrightness(par1BlockPane.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
		float var7 = 1.0F;
		int var8 = par1BlockPane.colorMultiplier(this.blockAccess, par2, par3, par4);
		float var9 = (var8 >> 16 & 255) / 255.0F;
		float var10 = (var8 >> 8 & 255) / 255.0F;
		float var11 = (var8 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
			float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
			float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
			var9 = var12;
			var10 = var13;
			var11 = var14;
		}

		var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
		int var17;
		Icon var18;
		Icon var19;

		if (this.hasOverrideBlockTexture())
		{
			var18 = this.overrideBlockTexture;
			var19 = this.overrideBlockTexture;
		}
		else
		{
			var17 = this.blockAccess.getBlockMetadata(par2, par3, par4);
			var18 = par1BlockPane.getIcon(0, var17);
			var19 = par1BlockPane.getSideTextureIndex();
		}

		var17 = var18.getOriginX();
		int var15 = var18.getOriginY();
		double var50 = var18.getMinU();
		double var52 = var18.getMaxU();
		double var70 = var18.getMinV();
		double var72 = var18.getMaxV();

		double var20 = par2;
		double var22 = par2 + 0.5D;
		double var24 = par2 + 1;
		double var40 = par4;
		double var44 = par4 + 0.5D;
		double var48 = par4 + 1;

		boolean var58 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 - 1));
		boolean var59 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 + 1));
		boolean var60 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 - 1, par3, par4));
		boolean var61 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 + 1, par3, par4));
		boolean var62 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1);
		boolean var63 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0);

		if ((var58 || var59) && (!var60 && !var61) || (var58 && var59) && (!var60 || !var61))
		{
			var6.addVertexWithUV(var22, par3 + 1, var48, var50, var70);	// Central North-South
			var6.addVertexWithUV(var22, par3 + 0, var48, var50, var72);
			var6.addVertexWithUV(var22, par3 + 0, var40, var52, var72);
			var6.addVertexWithUV(var22, par3 + 1, var40, var52, var70);
		}
		else
		{
			var6.addVertexWithUV(var20, par3 + 1, var44, var50, var70);	// Central West-East
			var6.addVertexWithUV(var20, par3 + 0, var44, var50, var72);
			var6.addVertexWithUV(var24, par3 + 0, var44, var52, var72);
			var6.addVertexWithUV(var24, par3 + 1, var44, var52, var70);
		}

		return true;
	}

	public boolean renderBlockPaneX(BlockPane par1BlockPane, int par2, int par3, int par4)
	{
		int var5 = this.blockAccess.getHeight();
		Tessellator var6 = Tessellator.instance;
		var6.setBrightness(par1BlockPane.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
		float var7 = 1.0F;
		int var8 = par1BlockPane.colorMultiplier(this.blockAccess, par2, par3, par4);
		float var9 = (var8 >> 16 & 255) / 255.0F;
		float var10 = (var8 >> 8 & 255) / 255.0F;
		float var11 = (var8 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
			float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
			float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
			var9 = var12;
			var10 = var13;
			var11 = var14;
		}

		var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
		ConnectedProperties var15 = null;
		int var106;
		Icon var104;
		Icon var105;

		if (this.hasOverrideBlockTexture())
		{
			var104 = this.overrideBlockTexture;
			var105 = this.overrideBlockTexture;
		}
		else
		{
			var106 = this.blockAccess.getBlockMetadata(par2, par3, par4);
			var104 = this.getBlockIconFromSideAndMetadata(par1BlockPane, 0, var106);
			var105 = par1BlockPane.getSideTextureIndex();

			if (Config.isConnectedTextures())
			{
				var15 = ConnectedTextures.getConnectedProperties(this.blockAccess, par1BlockPane, par2, par3, par4, -1, var104);
			}
		}

		Icon var16 = var104;
		Icon var17 = var104;
		Icon var18 = var104;
		int var19;

		if (var15 != null)
		{
			var19 = par1BlockPane.blockID;
			int var20 = this.blockAccess.getBlockId(par2 + 1, par3, par4);
			int var21 = this.blockAccess.getBlockId(par2 - 1, par3, par4);
			int var22 = this.blockAccess.getBlockId(par2, par3 + 1, par4);
			int var23 = this.blockAccess.getBlockId(par2, par3 - 1, par4);
			int var24 = this.blockAccess.getBlockId(par2, par3, par4 + 1);
			int var25 = this.blockAccess.getBlockId(par2, par3, par4 - 1);
			boolean var26 = var20 == var19;
			boolean var27 = var21 == var19;
			boolean var28 = var22 == var19;
			boolean var29 = var23 == var19;
			boolean var30 = var24 == var19;
			boolean var31 = var25 == var19;
			int var32 = ConnectedTextures.getPaneTextureIndex(var26, var27, var28, var29);
			int var33 = ConnectedTextures.getReversePaneTextureIndex(var32);
			int var34 = ConnectedTextures.getPaneTextureIndex(var30, var31, var28, var29);
			int var35 = ConnectedTextures.getReversePaneTextureIndex(var34);
			var104 = ConnectedTextures.getCtmTexture(var15, var32, var104);
			var16 = ConnectedTextures.getCtmTexture(var15, var33, var16);
			var17 = ConnectedTextures.getCtmTexture(var15, var34, var17);
			var18 = ConnectedTextures.getCtmTexture(var15, var35, var18);
		}

		var106 = var104.getOriginX();
		var19 = var104.getOriginY();
		double var107 = var104.getMinU();
		double var108 = var104.getInterpolatedU(8.0D);
		double var109 = var104.getMaxU();
		double var111 = var104.getMinV();
		double var110 = var104.getMaxV();
		int var112 = var16.getOriginX();
		int var113 = var16.getOriginY();
		double var114 = var16.getMinU();
		double var115 = var16.getInterpolatedU(8.0D);
		double var36 = var16.getMaxU();
		double var38 = var16.getMinV();
		double var40 = var16.getMaxV();
		int var42 = var17.getOriginX();
		int var43 = var17.getOriginY();
		double var44 = var17.getMinU();
		double var46 = var17.getInterpolatedU(8.0D);
		double var48 = var17.getMaxU();
		double var50 = var17.getMinV();
		double var52 = var17.getMaxV();
		int var54 = var18.getOriginX();
		int var55 = var18.getOriginY();
		double var56 = var18.getMinU();
		double var58 = var18.getInterpolatedU(8.0D);
		double var60 = var18.getMaxU();
		double var62 = var18.getMinV();
		double var64 = var18.getMaxV();
		int var66 = var105.getOriginX();
		int var67 = var105.getOriginY();
		double var68 = var105.getInterpolatedU(7.0D);
		double var70 = var105.getInterpolatedU(9.0D);
		double var72 = var105.getMinV();
		double var74 = var105.getInterpolatedV(8.0D);
		double var76 = var105.getMaxV();
		double var78 = par2;
		double var80 = par2 + 0.5D;
		double var82 = par2 + 1;
		double var84 = par4;
		double var86 = par4 + 0.5D;
		double var88 = par4 + 1;
		double var90 = par2 + 0.5D - 0.0625D;
		double var92 = par2 + 0.5D + 0.0625D;
		double var94 = par4 + 0.5D - 0.0625D;
		double var96 = par4 + 0.5D + 0.0625D;
		boolean var98 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 - 1));
		boolean var99 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2, par3, par4 + 1));
		boolean var100 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 - 1, par3, par4));
		boolean var101 = par1BlockPane.canThisPaneConnectToThisBlockID(this.blockAccess.getBlockId(par2 + 1, par3, par4));
		boolean var102 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1);
		boolean var103 = par1BlockPane.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0);

		if ((!var100 || !var101) && (var100 || var101 || var98 || var99))
		{
			if (var100 && !var101)
			{
				var6.addVertexWithUV(var78, par3 + 1, var86, var107, var111);
				var6.addVertexWithUV(var78, par3 + 0, var86, var107, var110);
				var6.addVertexWithUV(var80, par3 + 0, var86, var108, var110);
				var6.addVertexWithUV(var80, par3 + 1, var86, var108, var111);
				var6.addVertexWithUV(var80, par3 + 1, var86, var115, var38);
				var6.addVertexWithUV(var80, par3 + 0, var86, var115, var40);
				var6.addVertexWithUV(var78, par3 + 0, var86, var36, var40);
				var6.addVertexWithUV(var78, par3 + 1, var86, var36, var38);

				if (!var99 && !var98)
				{
					var6.addVertexWithUV(var80, par3 + 1, var96, var68, var72);
					var6.addVertexWithUV(var80, par3 + 0, var96, var68, var76);
					var6.addVertexWithUV(var80, par3 + 0, var94, var70, var76);
					var6.addVertexWithUV(var80, par3 + 1, var94, var70, var72);
					var6.addVertexWithUV(var80, par3 + 1, var94, var68, var72);
					var6.addVertexWithUV(var80, par3 + 0, var94, var68, var76);
					var6.addVertexWithUV(var80, par3 + 0, var96, var70, var76);
					var6.addVertexWithUV(var80, par3 + 1, var96, var70, var72);
				}

				if (var102 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 - 1, par3 + 1, par4))
				{
					var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var96, var70, var76);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var94, var68, var76);
					var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var96, var70, var76);
					var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var94, var68, var76);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var94, var68, var74);
				}

				if (var103 || par3 > 1 && this.blockAccess.isAirBlock(par2 - 1, par3 - 1, par4))
				{
					var6.addVertexWithUV(var78, par3 - 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var80, par3 - 0.01D, var96, var70, var76);
					var6.addVertexWithUV(var80, par3 - 0.01D, var94, var68, var76);
					var6.addVertexWithUV(var78, par3 - 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var80, par3 - 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var78, par3 - 0.01D, var96, var70, var76);
					var6.addVertexWithUV(var78, par3 - 0.01D, var94, var68, var76);
					var6.addVertexWithUV(var80, par3 - 0.01D, var94, var68, var74);
				}
			}
			else if (!var100 && var101)
			{
				var6.addVertexWithUV(var80, par3 + 1, var86, var108, var111);
				var6.addVertexWithUV(var80, par3 + 0, var86, var108, var110);
				var6.addVertexWithUV(var82, par3 + 0, var86, var109, var110);
				var6.addVertexWithUV(var82, par3 + 1, var86, var109, var111);
				var6.addVertexWithUV(var82, par3 + 1, var86, var114, var38);
				var6.addVertexWithUV(var82, par3 + 0, var86, var114, var40);
				var6.addVertexWithUV(var80, par3 + 0, var86, var115, var40);
				var6.addVertexWithUV(var80, par3 + 1, var86, var115, var38);

				if (!var99 && !var98)
				{
					var6.addVertexWithUV(var80, par3 + 1, var94, var68, var72);
					var6.addVertexWithUV(var80, par3 + 0, var94, var68, var76);
					var6.addVertexWithUV(var80, par3 + 0, var96, var70, var76);
					var6.addVertexWithUV(var80, par3 + 1, var96, var70, var72);
					var6.addVertexWithUV(var80, par3 + 1, var96, var68, var72);
					var6.addVertexWithUV(var80, par3 + 0, var96, var68, var76);
					var6.addVertexWithUV(var80, par3 + 0, var94, var70, var76);
					var6.addVertexWithUV(var80, par3 + 1, var94, var70, var72);
				}

				if (var102 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 + 1, par3 + 1, par4))
				{
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var96, var70, var72);
					var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var94, var68, var72);
					var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var96, var70, var72);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var94, var68, var72);
				}

				if (var103 || par3 > 1 && this.blockAccess.isAirBlock(par2 + 1, par3 - 1, par4))
				{
					var6.addVertexWithUV(var80, par3 - 0.01D, var96, var70, var72);
					var6.addVertexWithUV(var82, par3 - 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var82, par3 - 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var80, par3 - 0.01D, var94, var68, var72);
					var6.addVertexWithUV(var82, par3 - 0.01D, var96, var70, var72);
					var6.addVertexWithUV(var80, par3 - 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var80, par3 - 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var82, par3 - 0.01D, var94, var68, var72);
				}
			}
		}
		else
		{
			var6.addVertexWithUV(var78, par3 + 1, var86, var107, var111);
			var6.addVertexWithUV(var78, par3 + 0, var86, var107, var110);
			var6.addVertexWithUV(var82, par3 + 0, var86, var109, var110);
			var6.addVertexWithUV(var82, par3 + 1, var86, var109, var111);
			var6.addVertexWithUV(var82, par3 + 1, var86, var114, var38);
			var6.addVertexWithUV(var82, par3 + 0, var86, var114, var40);
			var6.addVertexWithUV(var78, par3 + 0, var86, var36, var40);
			var6.addVertexWithUV(var78, par3 + 1, var86, var36, var38);

			if (var102)
			{
				var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var96, var70, var76);
				var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var96, var70, var72);
				var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var94, var68, var72);
				var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var94, var68, var76);
				var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var96, var70, var76);
				var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var96, var70, var72);
				var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var94, var68, var72);
				var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var94, var68, var76);
			}
			else
			{
				if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 - 1, par3 + 1, par4))
				{
					var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var96, var70, var76);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var94, var68, var76);
					var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var96, var70, var76);
					var6.addVertexWithUV(var78, par3 + 1 + 0.01D, var94, var68, var76);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var94, var68, var74);
				}

				if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2 + 1, par3 + 1, par4))
				{
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var96, var70, var72);
					var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var94, var68, var72);
					var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var96, var70, var72);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var80, par3 + 1 + 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var82, par3 + 1 + 0.01D, var94, var68, var72);
				}
			}

			if (var103)
			{
				var6.addVertexWithUV(var78, par3 - 0.01D, var96, var70, var76);
				var6.addVertexWithUV(var82, par3 - 0.01D, var96, var70, var72);
				var6.addVertexWithUV(var82, par3 - 0.01D, var94, var68, var72);
				var6.addVertexWithUV(var78, par3 - 0.01D, var94, var68, var76);
				var6.addVertexWithUV(var82, par3 - 0.01D, var96, var70, var76);
				var6.addVertexWithUV(var78, par3 - 0.01D, var96, var70, var72);
				var6.addVertexWithUV(var78, par3 - 0.01D, var94, var68, var72);
				var6.addVertexWithUV(var82, par3 - 0.01D, var94, var68, var76);
			}
			else
			{
				if (par3 > 1 && this.blockAccess.isAirBlock(par2 - 1, par3 - 1, par4))
				{
					var6.addVertexWithUV(var78, par3 - 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var80, par3 - 0.01D, var96, var70, var76);
					var6.addVertexWithUV(var80, par3 - 0.01D, var94, var68, var76);
					var6.addVertexWithUV(var78, par3 - 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var80, par3 - 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var78, par3 - 0.01D, var96, var70, var76);
					var6.addVertexWithUV(var78, par3 - 0.01D, var94, var68, var76);
					var6.addVertexWithUV(var80, par3 - 0.01D, var94, var68, var74);
				}

				if (par3 > 1 && this.blockAccess.isAirBlock(par2 + 1, par3 - 1, par4))
				{
					var6.addVertexWithUV(var80, par3 - 0.01D, var96, var70, var72);
					var6.addVertexWithUV(var82, par3 - 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var82, par3 - 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var80, par3 - 0.01D, var94, var68, var72);
					var6.addVertexWithUV(var82, par3 - 0.01D, var96, var70, var72);
					var6.addVertexWithUV(var80, par3 - 0.01D, var96, var70, var74);
					var6.addVertexWithUV(var80, par3 - 0.01D, var94, var68, var74);
					var6.addVertexWithUV(var82, par3 - 0.01D, var94, var68, var72);
				}
			}
		}

		if ((!var98 || !var99) && (var100 || var101 || var98 || var99))
		{
			if (var98 && !var99)
			{
				var6.addVertexWithUV(var80, par3 + 1, var84, var44, var50);
				var6.addVertexWithUV(var80, par3 + 0, var84, var44, var52);
				var6.addVertexWithUV(var80, par3 + 0, var86, var46, var52);
				var6.addVertexWithUV(var80, par3 + 1, var86, var46, var50);
				var6.addVertexWithUV(var80, par3 + 1, var86, var58, var62);
				var6.addVertexWithUV(var80, par3 + 0, var86, var58, var64);
				var6.addVertexWithUV(var80, par3 + 0, var84, var60, var64);
				var6.addVertexWithUV(var80, par3 + 1, var84, var60, var62);

				if (!var101 && !var100)
				{
					var6.addVertexWithUV(var90, par3 + 1, var86, var68, var72);
					var6.addVertexWithUV(var90, par3 + 0, var86, var68, var76);
					var6.addVertexWithUV(var92, par3 + 0, var86, var70, var76);
					var6.addVertexWithUV(var92, par3 + 1, var86, var70, var72);
					var6.addVertexWithUV(var92, par3 + 1, var86, var68, var72);
					var6.addVertexWithUV(var92, par3 + 0, var86, var68, var76);
					var6.addVertexWithUV(var90, par3 + 0, var86, var70, var76);
					var6.addVertexWithUV(var90, par3 + 1, var86, var70, var72);
				}

				if (var102 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 - 1))
				{
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var84, var70, var72);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var86, var70, var74);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var86, var68, var74);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var84, var68, var72);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var86, var70, var72);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var84, var70, var74);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var84, var68, var74);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var86, var68, var72);
				}

				if (var103 || par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 - 1))
				{
					var6.addVertexWithUV(var90, par3 - 0.005D, var84, var70, var72);
					var6.addVertexWithUV(var90, par3 - 0.005D, var86, var70, var74);
					var6.addVertexWithUV(var92, par3 - 0.005D, var86, var68, var74);
					var6.addVertexWithUV(var92, par3 - 0.005D, var84, var68, var72);
					var6.addVertexWithUV(var90, par3 - 0.005D, var86, var70, var72);
					var6.addVertexWithUV(var90, par3 - 0.005D, var84, var70, var74);
					var6.addVertexWithUV(var92, par3 - 0.005D, var84, var68, var74);
					var6.addVertexWithUV(var92, par3 - 0.005D, var86, var68, var72);
				}
			}
			else if (!var98 && var99)
			{
				var6.addVertexWithUV(var80, par3 + 1, var86, var46, var50);
				var6.addVertexWithUV(var80, par3 + 0, var86, var46, var52);
				var6.addVertexWithUV(var80, par3 + 0, var88, var48, var52);
				var6.addVertexWithUV(var80, par3 + 1, var88, var48, var50);
				var6.addVertexWithUV(var80, par3 + 1, var88, var56, var62);
				var6.addVertexWithUV(var80, par3 + 0, var88, var56, var64);
				var6.addVertexWithUV(var80, par3 + 0, var86, var58, var64);
				var6.addVertexWithUV(var80, par3 + 1, var86, var58, var62);

				if (!var101 && !var100)
				{
					var6.addVertexWithUV(var92, par3 + 1, var86, var68, var72);
					var6.addVertexWithUV(var92, par3 + 0, var86, var68, var76);
					var6.addVertexWithUV(var90, par3 + 0, var86, var70, var76);
					var6.addVertexWithUV(var90, par3 + 1, var86, var70, var72);
					var6.addVertexWithUV(var90, par3 + 1, var86, var68, var72);
					var6.addVertexWithUV(var90, par3 + 0, var86, var68, var76);
					var6.addVertexWithUV(var92, par3 + 0, var86, var70, var76);
					var6.addVertexWithUV(var92, par3 + 1, var86, var70, var72);
				}

				if (var102 || par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 + 1))
				{
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var86, var68, var74);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var88, var68, var76);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var88, var70, var76);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var86, var70, var74);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var88, var68, var74);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var86, var68, var76);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var86, var70, var76);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var88, var70, var74);
				}

				if (var103 || par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 + 1))
				{
					var6.addVertexWithUV(var90, par3 - 0.005D, var86, var68, var74);
					var6.addVertexWithUV(var90, par3 - 0.005D, var88, var68, var76);
					var6.addVertexWithUV(var92, par3 - 0.005D, var88, var70, var76);
					var6.addVertexWithUV(var92, par3 - 0.005D, var86, var70, var74);
					var6.addVertexWithUV(var90, par3 - 0.005D, var88, var68, var74);
					var6.addVertexWithUV(var90, par3 - 0.005D, var86, var68, var76);
					var6.addVertexWithUV(var92, par3 - 0.005D, var86, var70, var76);
					var6.addVertexWithUV(var92, par3 - 0.005D, var88, var70, var74);
				}
			}
		}
		else
		{
			var6.addVertexWithUV(var80, par3 + 1, var88, var56, var62);
			var6.addVertexWithUV(var80, par3 + 0, var88, var56, var64);
			var6.addVertexWithUV(var80, par3 + 0, var84, var60, var64);
			var6.addVertexWithUV(var80, par3 + 1, var84, var60, var62);
			var6.addVertexWithUV(var80, par3 + 1, var84, var44, var50);
			var6.addVertexWithUV(var80, par3 + 0, var84, var44, var52);
			var6.addVertexWithUV(var80, par3 + 0, var88, var48, var52);
			var6.addVertexWithUV(var80, par3 + 1, var88, var48, var50);

			if (var102)
			{
				var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var88, var70, var76);
				var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var84, var70, var72);
				var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var84, var68, var72);
				var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var88, var68, var76);
				var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var84, var70, var76);
				var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var88, var70, var72);
				var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var88, var68, var72);
				var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var84, var68, var76);
			}
			else
			{
				if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 - 1))
				{
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var84, var70, var72);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var86, var70, var74);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var86, var68, var74);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var84, var68, var72);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var86, var70, var72);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var84, var70, var74);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var84, var68, var74);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var86, var68, var72);
				}

				if (par3 < var5 - 1 && this.blockAccess.isAirBlock(par2, par3 + 1, par4 + 1))
				{
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var86, var68, var74);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var88, var68, var76);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var88, var70, var76);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var86, var70, var74);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var88, var68, var74);
					var6.addVertexWithUV(var90, par3 + 1 + 0.005D, var86, var68, var76);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var86, var70, var76);
					var6.addVertexWithUV(var92, par3 + 1 + 0.005D, var88, var70, var74);
				}
			}

			if (var103)
			{
				var6.addVertexWithUV(var92, par3 - 0.005D, var88, var70, var76);
				var6.addVertexWithUV(var92, par3 - 0.005D, var84, var70, var72);
				var6.addVertexWithUV(var90, par3 - 0.005D, var84, var68, var72);
				var6.addVertexWithUV(var90, par3 - 0.005D, var88, var68, var76);
				var6.addVertexWithUV(var92, par3 - 0.005D, var84, var70, var76);
				var6.addVertexWithUV(var92, par3 - 0.005D, var88, var70, var72);
				var6.addVertexWithUV(var90, par3 - 0.005D, var88, var68, var72);
				var6.addVertexWithUV(var90, par3 - 0.005D, var84, var68, var76);
			}
			else
			{
				if (par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 - 1))
				{
					var6.addVertexWithUV(var90, par3 - 0.005D, var84, var70, var72);
					var6.addVertexWithUV(var90, par3 - 0.005D, var86, var70, var74);
					var6.addVertexWithUV(var92, par3 - 0.005D, var86, var68, var74);
					var6.addVertexWithUV(var92, par3 - 0.005D, var84, var68, var72);
					var6.addVertexWithUV(var90, par3 - 0.005D, var86, var70, var72);
					var6.addVertexWithUV(var90, par3 - 0.005D, var84, var70, var74);
					var6.addVertexWithUV(var92, par3 - 0.005D, var84, var68, var74);
					var6.addVertexWithUV(var92, par3 - 0.005D, var86, var68, var72);
				}

				if (par3 > 1 && this.blockAccess.isAirBlock(par2, par3 - 1, par4 + 1))
				{
					var6.addVertexWithUV(var90, par3 - 0.005D, var86, var68, var74);
					var6.addVertexWithUV(var90, par3 - 0.005D, var88, var68, var76);
					var6.addVertexWithUV(var92, par3 - 0.005D, var88, var70, var76);
					var6.addVertexWithUV(var92, par3 - 0.005D, var86, var70, var74);
					var6.addVertexWithUV(var90, par3 - 0.005D, var88, var68, var74);
					var6.addVertexWithUV(var90, par3 - 0.005D, var86, var68, var76);
					var6.addVertexWithUV(var92, par3 - 0.005D, var86, var70, var76);
					var6.addVertexWithUV(var92, par3 - 0.005D, var88, var70, var74);
				}
			}
		}

		if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
		{
			this.renderSnow(par2, par3, par4, Block.snow.maxY);
		}

		return true;
	}

	/**
	 * Renders any block requiring croseed squares such as reeds, flowers, and mushrooms
	 */
	public boolean renderCrossedSquares(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4));
		float var6 = 1.0F;
		int var7 = CustomColorizer.getColorMultiplier(par1Block, this.blockAccess, par2, par3, par4);
		float var8 = (var7 >> 16 & 255) / 255.0F;
		float var9 = (var7 >> 8 & 255) / 255.0F;
		float var10 = (var7 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
			float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
			float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
			var8 = var11;
			var9 = var12;
			var10 = var13;
		}

		var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
		double var19 = par2;
		double var20 = par3;
		double var15 = par4;

		if (par1Block == Block.tallGrass)
		{
			long var17 = par2 * 3129871 ^ par4 * 116129781L ^ par3;
			var17 = var17 * var17 * 42317861L + var17 * 11L;
			var19 += ((var17 >> 16 & 15L) / 15.0F - 0.5D) * 0.5D;
			var20 += ((var17 >> 20 & 15L) / 15.0F - 1.0D) * 0.2D;
			var15 += ((var17 >> 24 & 15L) / 15.0F - 0.5D) * 0.5D;
		}

		this.drawCrossedSquares(par1Block, this.blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F);

		if (Config.isBetterSnow() && this.hasSnowNeighbours(par2, par3, par4))
		{
			this.renderSnow(par2, par3, par4, Block.snow.maxY);
		}

		return true;
	}

	/**
	 * Utility function to draw crossed swuares
	 */
	public void drawCrossedSquares(Block par1Block, int par2, double par3, double par5, double par7, float par9)
	{
		Tessellator var10 = Tessellator.instance;
		Icon var11 = this.getBlockIconFromSideAndMetadata(par1Block, 0, par2);

		if (this.hasOverrideBlockTexture())
		{
			var11 = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null)
		{
			var11 = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par3, (int)par5, (int)par7, -1, var11);
		}

		double var12 = var11.getMinU();
		double var14 = var11.getMinV();
		double var16 = var11.getMaxU();
		double var18 = var11.getMaxV();
		double var20 = 0.45D * par9;
		double var22 = par3 + 0.5D - var20;
		double var24 = par3 + 0.5D + var20;
		double var26 = par7 + 0.5D - var20;
		double var28 = par7 + 0.5D + var20;
		var10.addVertexWithUV(var22, par5 + par9, var26, var12, var14);
		var10.addVertexWithUV(var22, par5 + 0.0D, var26, var12, var18);
		var10.addVertexWithUV(var24, par5 + 0.0D, var28, var16, var18);
		var10.addVertexWithUV(var24, par5 + par9, var28, var16, var14);
		var10.addVertexWithUV(var24, par5 + par9, var28, var12, var14);
		var10.addVertexWithUV(var24, par5 + 0.0D, var28, var12, var18);
		var10.addVertexWithUV(var22, par5 + 0.0D, var26, var16, var18);
		var10.addVertexWithUV(var22, par5 + par9, var26, var16, var14);
		var10.addVertexWithUV(var22, par5 + par9, var28, var12, var14);
		var10.addVertexWithUV(var22, par5 + 0.0D, var28, var12, var18);
		var10.addVertexWithUV(var24, par5 + 0.0D, var26, var16, var18);
		var10.addVertexWithUV(var24, par5 + par9, var26, var16, var14);
		var10.addVertexWithUV(var24, par5 + par9, var26, var12, var14);
		var10.addVertexWithUV(var24, par5 + 0.0D, var26, var12, var18);
		var10.addVertexWithUV(var22, par5 + 0.0D, var28, var16, var18);
		var10.addVertexWithUV(var22, par5 + par9, var28, var16, var14);
	}

	/**
	 * Renders a log block at the given coordinates
	 */
	public boolean renderBlockLog(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = this.blockAccess.getBlockMetadata(par2, par3, par4);
		int var6 = var5 & 12;

		if (var6 == 4)
		{
			this.uvRotateNorth = 1;
			this.uvRotateSouth = 1;
			this.uvRotateTop = 1;
			this.uvRotateBottom = 1;
		}
		else if (var6 == 8)
		{
			this.uvRotateEast = 1;
			this.uvRotateWest = 1;
		}

		boolean var7 = this.renderStandardBlock(par1Block, par2, par3, par4);
		this.uvRotateEast = 0;
		this.uvRotateNorth = 0;
		this.uvRotateSouth = 0;
		this.uvRotateWest = 0;
		this.uvRotateTop = 0;
		this.uvRotateBottom = 0;
		return var7;
	}

	/**
	 * Renders a standard cube block at the given coordinates
	 */
	public boolean renderBlockGlass(Block par1Block, int par2, int par3, int par4)
	{
		par1Block.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
		this.setRenderBoundsFromBlock(par1Block);
		if (ClientProxy.renderPass == 0)
		{
			this.renderGlassBlock(par1Block, par2, par3, par4);
		}
		else
		{
			this.renderStandardBlock(par1Block, par2, par3, par4);
		}

		return true;
	}

	/**
	 * Renders a standard cube block at the given coordinates
	 */
	public boolean renderGlassBlock(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = par1Block.colorMultiplier(this.blockAccess, par2, par3, par4);
		float var6 = (var5 >> 16 & 255) / 255.0F;
		float var7 = (var5 >> 8 & 255) / 255.0F;
		float var8 = (var5 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
			float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
			float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
			var6 = var9;
			var7 = var10;
			var8 = var11;
		}

		return Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[par1Block.blockID] == 0 ? (this.partialRenderBounds ? this.renderGlassBlockWithfunc_102027_b(par1Block, par2, par3, par4, var6, var7, var8) : this.renderGlassBlockWithAmbientOcclusion(par1Block, par2, par3, par4, var6, var7, var8)) : this.renderStandardBlockWithColorMultiplier(par1Block, par2, par3, par4, var6, var7, var8);
	}

	public boolean renderGlassBlockWithAmbientOcclusion(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		this.enableAO = true;
		boolean var8 = Tessellator.instance.defaultTexture;
		boolean var9 = Config.isBetterGrass() && var8;
		boolean var10 = par1Block == Block.glass;
		boolean var11 = false;
		float var12 = 0.0F;
		float var13 = 0.0F;
		float var14 = 0.0F;
		float var15 = 0.0F;
		boolean var16 = true;
		int var17 = -1;
		Tessellator var18 = Tessellator.instance;
		var18.setBrightness(983055);

		if (par1Block == Block.grass)
		{
			var16 = false;
		}
		else if (this.hasOverrideBlockTexture())
		{
			var16 = false;
		}

		boolean var19;
		boolean var21;
		boolean var20;
		float var23;
		boolean var22;
		int var24;

		if (this.renderAllFaces || true) // MCE
		{
			if (this.renderMinY <= 0.0D)
			{
				--par3;
			}

			this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

			if (!var21 && !var19)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!var22 && !var19)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!var21 && !var20)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!var22 && !var20)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (this.renderMinY <= 0.0D)
			{
				++par3;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMinY <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			var12 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var23) / 4.0F;
			var15 = (this.aoLightValueScratchYZNP + var23 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
			var14 = (var23 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
			var13 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var23 + this.aoLightValueScratchYZNN) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var24);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.5F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			// ------------------------------------------------ MCE
			if (par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
			{
				this.renderBottomFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
			}
			// ------------------------------------------------ MCE
			this.renderBottomFace(par1Block, par2, par3 + 0.99D, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
			var11 = true;
		}

		if (this.renderAllFaces || true)
		{
			if (this.renderMaxY >= 1.0D)
			{
				++par3;
			}

			this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];

			if (!var21 && !var19)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!var21 && !var20)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!var22 && !var19)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!var22 && !var20)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (this.renderMaxY >= 1.0D)
			{
				--par3;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMaxY >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			var15 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var23) / 4.0F;
			var12 = (this.aoLightValueScratchYZPP + var23 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
			var13 = (var23 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
			var14 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var23 + this.aoLightValueScratchYZPN) / 4.0F;
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var24);
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7;
			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			// ------------------------------------------------ MCE
			if (par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
			{
				this.renderTopFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
			}
			// ------------------------------------------------ MCE
			this.renderTopFace(par1Block, par2, par3 - 0.99D, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
			var11 = true;
		}

		Icon var25;

		if (this.renderAllFaces || true)
		{
			if (this.renderMinZ <= 0.0D)
			{
				--par4;
			}

			this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

			if (!var19 && !var21)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!var19 && !var22)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!var20 && !var21)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!var20 && !var22)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (this.renderMinZ <= 0.0D)
			{
				++par4;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMinZ <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			var12 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var23 + this.aoLightValueScratchYZPN) / 4.0F;
			var13 = (var23 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
			var14 = (this.aoLightValueScratchYZNN + var23 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
			var15 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var23) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var24);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);

			if (var9)
			{
				var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 2, par5, par6, par7);
			}

			if (par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
			{
				this.renderNorthFace(par1Block, par2, par3, par4, var25);

				if (var8 && fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
				{
					this.colorRedTopLeft *= par5;
					this.colorRedBottomLeft *= par5;
					this.colorRedBottomRight *= par5;
					this.colorRedTopRight *= par5;
					this.colorGreenTopLeft *= par6;
					this.colorGreenBottomLeft *= par6;
					this.colorGreenBottomRight *= par6;
					this.colorGreenTopRight *= par6;
					this.colorBlueTopLeft *= par7;
					this.colorBlueBottomLeft *= par7;
					this.colorBlueBottomRight *= par7;
					this.colorBlueTopRight *= par7;
					this.renderNorthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
				}

				this.renderNorthFace(par1Block, par2, par3, par4 + 0.99D, var25);
			}

			var11 = true;
		}

		if (this.renderAllFaces || true)
		{
			if (this.renderMaxZ >= 1.0D)
			{
				++par4;
			}

			this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];

			if (!var19 && !var21)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!var19 && !var22)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!var20 && !var21)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!var20 && !var22)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (this.renderMaxZ >= 1.0D)
			{
				--par4;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMaxZ >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			var12 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var23 + this.aoLightValueScratchYZPP) / 4.0F;
			var15 = (var23 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			var14 = (this.aoLightValueScratchYZNP + var23 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
			var13 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var23) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var24);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);

			if (var9)
			{
				var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 3, par5, par6, par7);
			}

			if (par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
			{
				this.renderSouthFace(par1Block, par2, par3, par4, var25);

				if (var8 && fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
				{
					this.colorRedTopLeft *= par5;
					this.colorRedBottomLeft *= par5;
					this.colorRedBottomRight *= par5;
					this.colorRedTopRight *= par5;
					this.colorGreenTopLeft *= par6;
					this.colorGreenBottomLeft *= par6;
					this.colorGreenBottomRight *= par6;
					this.colorGreenTopRight *= par6;
					this.colorBlueTopLeft *= par7;
					this.colorBlueBottomLeft *= par7;
					this.colorBlueBottomRight *= par7;
					this.colorBlueTopRight *= par7;
					this.renderSouthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
				}
			}
			this.renderSouthFace(par1Block, par2, par3, par4 - 0.99D, var25);

			var11 = true;
		}

		if (this.renderAllFaces || true)
		{
			if (this.renderMinX <= 0.0D)
			{
				--par2;
			}

			this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];

			if (!var22 && !var19)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!var21 && !var19)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!var22 && !var20)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!var21 && !var20)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (this.renderMinX <= 0.0D)
			{
				++par2;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMinX <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			var15 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var23 + this.aoLightValueScratchXZNP) / 4.0F;
			var12 = (var23 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
			var13 = (this.aoLightValueScratchXZNN + var23 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
			var14 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var23) / 4.0F;
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var24);
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);

			if (var9)
			{
				var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 4, par5, par6, par7);
			}

			if (par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
			{
				this.renderWestFace(par1Block, par2, par3, par4, var25);

				if (var8 && fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
				{
					this.colorRedTopLeft *= par5;
					this.colorRedBottomLeft *= par5;
					this.colorRedBottomRight *= par5;
					this.colorRedTopRight *= par5;
					this.colorGreenTopLeft *= par6;
					this.colorGreenBottomLeft *= par6;
					this.colorGreenBottomRight *= par6;
					this.colorGreenTopRight *= par6;
					this.colorBlueTopLeft *= par7;
					this.colorBlueBottomLeft *= par7;
					this.colorBlueBottomRight *= par7;
					this.colorBlueTopRight *= par7;
					this.renderWestFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
				}
			}
			this.renderWestFace(par1Block, par2 + 0.99D, par3, par4, var25);

			var11 = true;
		}

		if (this.renderAllFaces || true)
		{
			if (this.renderMaxX >= 1.0D)
			{
				++par2;
			}

			this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];

			if (!var19 && !var21)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!var19 && !var22)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!var20 && !var21)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!var20 && !var22)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (this.renderMaxX >= 1.0D)
			{
				--par2;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMaxX >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			var12 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var23 + this.aoLightValueScratchXZPP) / 4.0F;
			var13 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var23) / 4.0F;
			var14 = (this.aoLightValueScratchXZPN + var23 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
			var15 = (var23 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var24);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);

			if (var9)
			{
				var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 5, par5, par6, par7);
			}

			if (par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
			{
				this.renderEastFace(par1Block, par2, par3, par4, var25);

				if (var8 && fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
				{
					this.colorRedTopLeft *= par5;
					this.colorRedBottomLeft *= par5;
					this.colorRedBottomRight *= par5;
					this.colorRedTopRight *= par5;
					this.colorGreenTopLeft *= par6;
					this.colorGreenBottomLeft *= par6;
					this.colorGreenBottomRight *= par6;
					this.colorGreenTopRight *= par6;
					this.colorBlueTopLeft *= par7;
					this.colorBlueBottomLeft *= par7;
					this.colorBlueBottomRight *= par7;
					this.colorBlueTopRight *= par7;
					this.renderEastFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
				}
			}
			this.renderEastFace(par1Block, par2 - 0.99D, par3, par4, var25);

			var11 = true;
		}

		this.enableAO = false;
		return var11;
	}

	public boolean renderGlassBlockWithfunc_102027_b(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		this.enableAO = true;
		boolean var8 = false;
		float var9 = 0.0F;
		float var10 = 0.0F;
		float var11 = 0.0F;
		float var12 = 0.0F;
		boolean var13 = true;
		int var14 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
		Tessellator var15 = Tessellator.instance;
		var15.setBrightness(983055);

		if (par1Block == Block.grass)
		{
			var13 = false;
		}
		else if (this.hasOverrideBlockTexture())
		{
			var13 = false;
		}

		boolean var17;
		boolean var16;
		boolean var19;
		boolean var18;
		int var21;
		float var20;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
		{
			if (this.renderMinY <= 0.0D)
			{
				--par3;
			}

			this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

			if (!var18 && !var16)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!var19 && !var16)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!var18 && !var17)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!var19 && !var17)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (this.renderMinY <= 0.0D)
			{
				++par3;
			}

			var21 = var14;

			if (this.renderMinY <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var20) / 4.0F;
			var12 = (this.aoLightValueScratchYZNP + var20 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
			var11 = (var20 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
			var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var20 + this.aoLightValueScratchYZNN) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var21);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var21);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var21);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var21);

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.5F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			this.renderBottomFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
			var8 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
		{
			if (this.renderMaxY >= 1.0D)
			{
				++par3;
			}

			this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];

			if (!var18 && !var16)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!var18 && !var17)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!var19 && !var16)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!var19 && !var17)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (this.renderMaxY >= 1.0D)
			{
				--par3;
			}

			var21 = var14;

			if (this.renderMaxY >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var20) / 4.0F;
			var9 = (this.aoLightValueScratchYZPP + var20 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
			var10 = (var20 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
			var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var20 + this.aoLightValueScratchYZPN) / 4.0F;
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var21);
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var21);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var21);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var21);
			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7;
			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			this.renderTopFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
			var8 = true;
		}

		float var23;
		float var22;
		float var25;
		float var24;
		int var27;
		int var26;
		int var29;
		int var28;
		Icon var30;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
		{
			if (this.renderMinZ <= 0.0D)
			{
				--par4;
			}

			this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

			if (!var16 && !var18)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!var16 && !var19)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!var17 && !var18)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!var17 && !var19)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (this.renderMinZ <= 0.0D)
			{
				++par4;
			}

			var21 = var14;

			if (this.renderMinZ <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			var23 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var20 + this.aoLightValueScratchYZPN) / 4.0F;
			var22 = (var20 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
			var25 = (this.aoLightValueScratchYZNN + var20 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
			var24 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var20) / 4.0F;
			var9 = (float)(var23 * this.renderMaxY * (1.0D - this.renderMinX) + var22 * this.renderMinY * this.renderMinX + var25 * (1.0D - this.renderMaxY) * this.renderMinX + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			var10 = (float)(var23 * this.renderMaxY * (1.0D - this.renderMaxX) + var22 * this.renderMaxY * this.renderMaxX + var25 * (1.0D - this.renderMaxY) * this.renderMaxX + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			var11 = (float)(var23 * this.renderMinY * (1.0D - this.renderMaxX) + var22 * this.renderMinY * this.renderMaxX + var25 * (1.0D - this.renderMinY) * this.renderMaxX + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			var12 = (float)(var23 * this.renderMinY * (1.0D - this.renderMinX) + var22 * this.renderMinY * this.renderMinX + var25 * (1.0D - this.renderMinY) * this.renderMinX + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
			var27 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var21);
			var26 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var21);
			var29 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var21);
			var28 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var21);
			this.brightnessTopLeft = this.mixAoBrightness(var27, var26, var29, var28, this.renderMaxY * (1.0D - this.renderMinX), this.renderMaxY * this.renderMinX, (1.0D - this.renderMaxY) * this.renderMinX, (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			this.brightnessBottomLeft = this.mixAoBrightness(var27, var26, var29, var28, this.renderMaxY * (1.0D - this.renderMaxX), this.renderMaxY * this.renderMaxX, (1.0D - this.renderMaxY) * this.renderMaxX, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			this.brightnessBottomRight = this.mixAoBrightness(var27, var26, var29, var28, this.renderMinY * (1.0D - this.renderMaxX), this.renderMinY * this.renderMaxX, (1.0D - this.renderMinY) * this.renderMaxX, (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			this.brightnessTopRight = this.mixAoBrightness(var27, var26, var29, var28, this.renderMinY * (1.0D - this.renderMinX), this.renderMinY * this.renderMinX, (1.0D - this.renderMinY) * this.renderMinX, (1.0D - this.renderMinY) * (1.0D - this.renderMinX));

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
			this.renderNorthFace(par1Block, par2, par3, par4, var30);

			if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderNorthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
		{
			if (this.renderMaxZ >= 1.0D)
			{
				++par4;
			}

			this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];

			if (!var16 && !var18)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!var16 && !var19)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!var17 && !var18)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!var17 && !var19)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (this.renderMaxZ >= 1.0D)
			{
				--par4;
			}

			var21 = var14;

			if (this.renderMaxZ >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			var23 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var20 + this.aoLightValueScratchYZPP) / 4.0F;
			var22 = (var20 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			var25 = (this.aoLightValueScratchYZNP + var20 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
			var24 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var20) / 4.0F;
			var9 = (float)(var23 * this.renderMaxY * (1.0D - this.renderMinX) + var22 * this.renderMaxY * this.renderMinX + var25 * (1.0D - this.renderMaxY) * this.renderMinX + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			var10 = (float)(var23 * this.renderMinY * (1.0D - this.renderMinX) + var22 * this.renderMinY * this.renderMinX + var25 * (1.0D - this.renderMinY) * this.renderMinX + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
			var11 = (float)(var23 * this.renderMinY * (1.0D - this.renderMaxX) + var22 * this.renderMinY * this.renderMaxX + var25 * (1.0D - this.renderMinY) * this.renderMaxX + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			var12 = (float)(var23 * this.renderMaxY * (1.0D - this.renderMaxX) + var22 * this.renderMaxY * this.renderMaxX + var25 * (1.0D - this.renderMaxY) * this.renderMaxX + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			var27 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var21);
			var26 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var21);
			var29 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var21);
			var28 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var21);
			this.brightnessTopLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * this.renderMinX, this.renderMaxY * this.renderMinX);
			this.brightnessBottomLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * this.renderMinX, this.renderMinY * this.renderMinX);
			this.brightnessBottomRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
			this.brightnessTopRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * this.renderMaxX, this.renderMaxY * this.renderMaxX);

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
			this.renderSouthFace(par1Block, par2, par3, par4, var30);

			if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderSouthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
		{
			if (this.renderMinX <= 0.0D)
			{
				--par2;
			}

			this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];

			if (!var19 && !var16)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!var18 && !var16)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!var19 && !var17)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!var18 && !var17)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (this.renderMinX <= 0.0D)
			{
				++par2;
			}

			var21 = var14;

			if (this.renderMinX <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			var23 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var20 + this.aoLightValueScratchXZNP) / 4.0F;
			var22 = (var20 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
			var25 = (this.aoLightValueScratchXZNN + var20 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
			var24 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var20) / 4.0F;
			var9 = (float)(var22 * this.renderMaxY * this.renderMaxZ + var25 * this.renderMaxY * (1.0D - this.renderMaxZ) + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + var23 * (1.0D - this.renderMaxY) * this.renderMaxZ);
			var10 = (float)(var22 * this.renderMaxY * this.renderMinZ + var25 * this.renderMaxY * (1.0D - this.renderMinZ) + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + var23 * (1.0D - this.renderMaxY) * this.renderMinZ);
			var11 = (float)(var22 * this.renderMinY * this.renderMinZ + var25 * this.renderMinY * (1.0D - this.renderMinZ) + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + var23 * (1.0D - this.renderMinY) * this.renderMinZ);
			var12 = (float)(var22 * this.renderMinY * this.renderMaxZ + var25 * this.renderMinY * (1.0D - this.renderMaxZ) + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + var23 * (1.0D - this.renderMinY) * this.renderMaxZ);
			var27 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var21);
			var26 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var21);
			var29 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var21);
			var28 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var21);
			this.brightnessTopLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * this.renderMaxZ, this.renderMaxY * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * this.renderMaxZ);
			this.brightnessBottomLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * this.renderMinZ, this.renderMaxY * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * this.renderMinZ);
			this.brightnessBottomRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * this.renderMinZ, this.renderMinY * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * this.renderMinZ);
			this.brightnessTopRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * this.renderMaxZ, this.renderMinY * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * this.renderMaxZ);

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
			this.renderWestFace(par1Block, par2, par3, par4, var30);

			if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderWestFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
		{
			if (this.renderMaxX >= 1.0D)
			{
				++par2;
			}

			this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];

			if (!var16 && !var18)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!var16 && !var19)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!var17 && !var18)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!var17 && !var19)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (this.renderMaxX >= 1.0D)
			{
				--par2;
			}

			var21 = var14;

			if (this.renderMaxX >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			var23 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var20 + this.aoLightValueScratchXZPP) / 4.0F;
			var22 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var20) / 4.0F;
			var25 = (this.aoLightValueScratchXZPN + var20 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
			var24 = (var20 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			var9 = (float)(var23 * (1.0D - this.renderMinY) * this.renderMaxZ + var22 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + var25 * this.renderMinY * (1.0D - this.renderMaxZ) + var24 * this.renderMinY * this.renderMaxZ);
			var10 = (float)(var23 * (1.0D - this.renderMinY) * this.renderMinZ + var22 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + var25 * this.renderMinY * (1.0D - this.renderMinZ) + var24 * this.renderMinY * this.renderMinZ);
			var11 = (float)(var23 * (1.0D - this.renderMaxY) * this.renderMinZ + var22 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + var25 * this.renderMaxY * (1.0D - this.renderMinZ) + var24 * this.renderMaxY * this.renderMinZ);
			var12 = (float)(var23 * (1.0D - this.renderMaxY) * this.renderMaxZ + var22 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + var25 * this.renderMaxY * (1.0D - this.renderMaxZ) + var24 * this.renderMaxY * this.renderMaxZ);
			var27 = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var21);
			var26 = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var21);
			var29 = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var21);
			var28 = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var21);
			this.brightnessTopLeft = this.mixAoBrightness(var27, var28, var29, var26, (1.0D - this.renderMinY) * this.renderMaxZ, (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), this.renderMinY * (1.0D - this.renderMaxZ), this.renderMinY * this.renderMaxZ);
			this.brightnessBottomLeft = this.mixAoBrightness(var27, var28, var29, var26, (1.0D - this.renderMinY) * this.renderMinZ, (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), this.renderMinY * (1.0D - this.renderMinZ), this.renderMinY * this.renderMinZ);
			this.brightnessBottomRight = this.mixAoBrightness(var27, var28, var29, var26, (1.0D - this.renderMaxY) * this.renderMinZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), this.renderMaxY * (1.0D - this.renderMinZ), this.renderMaxY * this.renderMinZ);
			this.brightnessTopRight = this.mixAoBrightness(var27, var28, var29, var26, (1.0D - this.renderMaxY) * this.renderMaxZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), this.renderMaxY * (1.0D - this.renderMaxZ), this.renderMaxY * this.renderMaxZ);

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
			this.renderEastFace(par1Block, par2, par3, par4, var30);

			if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderEastFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		this.enableAO = false;
		return var8;
	}

	/**
	 * Renders a standard cube block at the given coordinates, with a given color ratio.  Args: block, x, y, z, r, g, b
	 */
	public boolean renderGlassBlockWithColorMultiplier(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		this.enableAO = false;
		boolean var8 = Tessellator.instance.defaultTexture;
		boolean var9 = Config.isBetterGrass() && var8;
		Tessellator var10 = Tessellator.instance;
		boolean var11 = false;
		int var12 = -1;
		float var13;
		float var14;
		float var15;
		float var16;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var13 = 0.5F;
			var14 = var13;
			var15 = var13;
			var16 = var13;

			if (par1Block != Block.grass)
			{
				var14 = var13 * par5;
				var15 = var13 * par6;
				var16 = var13 * par7;
			}

			var10.setBrightness(this.renderMinY > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
			var10.setColorOpaque_F(var14, var15, var16);
			this.renderBottomFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var13 = 1.0F;
			var14 = var13 * par5;
			var15 = var13 * par6;
			var16 = var13 * par7;
			var10.setBrightness(this.renderMaxY < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
			var10.setColorOpaque_F(var14, var15, var16);
			this.renderTopFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
			var11 = true;
		}

		float var17;
		Icon var18;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var14 = 0.8F;
			var15 = var14;
			var16 = var14;
			var17 = var14;

			if (par1Block != Block.grass)
			{
				var15 = var14 * par5;
				var16 = var14 * par6;
				var17 = var14 * par7;
			}

			var10.setBrightness(this.renderMinZ > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
			var10.setColorOpaque_F(var15, var16, var17);
			var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);

			if (var9)
			{
				if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide)
				{
					var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 2, var18);

					if (var18 == TextureUtils.iconGrassTop)
					{
						var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
					}
				}

				if (var18 == TextureUtils.iconSnowSide)
				{
					var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 2);
				}
			}

			this.renderNorthFace(par1Block, par2, par3, par4, var18);

			if (var8 && fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
				this.renderNorthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var14 = 0.8F;
			var15 = var14;
			var16 = var14;
			var17 = var14;

			if (par1Block != Block.grass)
			{
				var15 = var14 * par5;
				var16 = var14 * par6;
				var17 = var14 * par7;
			}

			var10.setBrightness(this.renderMaxZ < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
			var10.setColorOpaque_F(var15, var16, var17);
			var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);

			if (var9)
			{
				if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide)
				{
					var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 3, var18);

					if (var18 == TextureUtils.iconGrassTop)
					{
						var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
					}
				}

				if (var18 == TextureUtils.iconSnowSide)
				{
					var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 3);
				}
			}

			this.renderSouthFace(par1Block, par2, par3, par4, var18);

			if (var8 && fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
				this.renderSouthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var14 = 0.6F;
			var15 = var14;
			var16 = var14;
			var17 = var14;

			if (par1Block != Block.grass)
			{
				var15 = var14 * par5;
				var16 = var14 * par6;
				var17 = var14 * par7;
			}

			var10.setBrightness(this.renderMinX > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
			var10.setColorOpaque_F(var15, var16, var17);
			var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);

			if (var9)
			{
				if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide)
				{
					var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 4, var18);

					if (var18 == TextureUtils.iconGrassTop)
					{
						var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
					}
				}

				if (var18 == TextureUtils.iconSnowSide)
				{
					var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 4);
				}
			}

			this.renderWestFace(par1Block, par2, par3, par4, var18);

			if (var8 && fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
				this.renderWestFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var14 = 0.6F;
			var15 = var14;
			var16 = var14;
			var17 = var14;

			if (par1Block != Block.grass)
			{
				var15 = var14 * par5;
				var16 = var14 * par6;
				var17 = var14 * par7;
			}

			var10.setBrightness(this.renderMaxX < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
			var10.setColorOpaque_F(var15, var16, var17);
			var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);

			if (var9)
			{
				if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide)
				{
					var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 5, var18);

					if (var18 == TextureUtils.iconGrassTop)
					{
						var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
					}
				}

				if (var18 == TextureUtils.iconSnowSide)
				{
					var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 5);
				}
			}

			this.renderEastFace(par1Block, par2, par3, par4, var18);

			if (var8 && fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
				this.renderEastFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		return var11;
	}


	/**
	 * Renders a standard cube block at the given coordinates
	 */
	public boolean renderStandardBlock(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = CustomColorizer.getColorMultiplier(par1Block, this.blockAccess, par2, par3, par4);
		float var6 = (var5 >> 16 & 255) / 255.0F;
		float var7 = (var5 >> 8 & 255) / 255.0F;
		float var8 = (var5 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
			float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
			float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
			var6 = var9;
			var7 = var10;
			var8 = var11;
		}

		return Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[par1Block.blockID] == 0 ? (this.partialRenderBounds ? this.func_102027_b(par1Block, par2, par3, par4, var6, var7, var8) : this.renderStandardBlockWithAmbientOcclusion(par1Block, par2, par3, par4, var6, var7, var8)) : this.renderStandardBlockWithColorMultiplier(par1Block, par2, par3, par4, var6, var7, var8);
	}

	public boolean renderStandardBlockWithAmbientOcclusion(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		this.enableAO = true;
		boolean var8 = Tessellator.instance.defaultTexture;
		boolean var9 = Config.isBetterGrass() && var8;
		boolean var10 = par1Block == Block.glass;
		boolean var11 = false;
		float var12 = 0.0F;
		float var13 = 0.0F;
		float var14 = 0.0F;
		float var15 = 0.0F;
		boolean var16 = true;
		int var17 = -1;
		Tessellator var18 = Tessellator.instance;
		var18.setBrightness(983055);

		if (par1Block == Block.grass)
		{
			var16 = false;
		}
		else if (this.hasOverrideBlockTexture())
		{
			var16 = false;
		}

		boolean var19;
		boolean var21;
		boolean var20;
		float var23;
		boolean var22;
		int var24;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
		{
			if (this.renderMinY <= 0.0D)
			{
				--par3;
			}

			this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

			if (!var21 && !var19)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!var22 && !var19)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!var21 && !var20)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!var22 && !var20)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (this.renderMinY <= 0.0D)
			{
				++par3;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMinY <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			var12 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var23) / 4.0F;
			var15 = (this.aoLightValueScratchYZNP + var23 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
			var14 = (var23 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
			var13 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var23 + this.aoLightValueScratchYZNN) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var24);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.5F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			this.renderBottomFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
		{
			if (this.renderMaxY >= 1.0D)
			{
				++par3;
			}

			this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];

			if (!var21 && !var19)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!var21 && !var20)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!var22 && !var19)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!var22 && !var20)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (this.renderMaxY >= 1.0D)
			{
				--par3;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMaxY >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			var15 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var23) / 4.0F;
			var12 = (this.aoLightValueScratchYZPP + var23 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
			var13 = (var23 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
			var14 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var23 + this.aoLightValueScratchYZPN) / 4.0F;
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var24);
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7;
			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			this.renderTopFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
			var11 = true;
		}

		Icon var25;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
		{
			if (this.renderMinZ <= 0.0D)
			{
				--par4;
			}

			this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

			if (!var19 && !var21)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!var19 && !var22)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!var20 && !var21)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!var20 && !var22)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (this.renderMinZ <= 0.0D)
			{
				++par4;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMinZ <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			var12 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var23 + this.aoLightValueScratchYZPN) / 4.0F;
			var13 = (var23 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
			var14 = (this.aoLightValueScratchYZNN + var23 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
			var15 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var23) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var24);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);

			if (var9)
			{
				var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 2, par5, par6, par7);
			}

			this.renderNorthFace(par1Block, par2, par3, par4, var25);

			if (var8 && fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderNorthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
		{
			if (this.renderMaxZ >= 1.0D)
			{
				++par4;
			}

			this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];

			if (!var19 && !var21)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!var19 && !var22)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!var20 && !var21)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!var20 && !var22)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (this.renderMaxZ >= 1.0D)
			{
				--par4;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMaxZ >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			var12 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var23 + this.aoLightValueScratchYZPP) / 4.0F;
			var15 = (var23 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			var14 = (this.aoLightValueScratchYZNP + var23 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
			var13 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var23) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var24);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);

			if (var9)
			{
				var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 3, par5, par6, par7);
			}

			this.renderSouthFace(par1Block, par2, par3, par4, var25);

			if (var8 && fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderSouthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
		{
			if (this.renderMinX <= 0.0D)
			{
				--par2;
			}

			this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];

			if (!var22 && !var19)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!var21 && !var19)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!var22 && !var20)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!var21 && !var20)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (this.renderMinX <= 0.0D)
			{
				++par2;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMinX <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			var15 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var23 + this.aoLightValueScratchXZNP) / 4.0F;
			var12 = (var23 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
			var13 = (this.aoLightValueScratchXZNN + var23 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
			var14 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var23) / 4.0F;
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var24);
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);

			if (var9)
			{
				var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 4, par5, par6, par7);
			}

			this.renderWestFace(par1Block, par2, par3, par4, var25);

			if (var8 && fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderWestFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
		{
			if (this.renderMaxX >= 1.0D)
			{
				++par2;
			}

			this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var20 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var22 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var21 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];

			if (!var19 && !var21)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!var19 && !var22)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!var20 && !var21)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!var20 && !var22)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (this.renderMaxX >= 1.0D)
			{
				--par2;
			}

			if (var17 < 0)
			{
				var17 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var24 = var17;

			if (this.renderMaxX >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			}

			var23 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			var12 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var23 + this.aoLightValueScratchXZPP) / 4.0F;
			var13 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var23) / 4.0F;
			var14 = (this.aoLightValueScratchXZPN + var23 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
			var15 = (var23 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var24);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var24);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var24);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var24);

			if (var10)
			{
				var13 = var23;
				var14 = var23;
				var15 = var23;
				var12 = var23;
				this.brightnessTopLeft = this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var24;
			}

			if (var16)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= var12;
			this.colorGreenTopLeft *= var12;
			this.colorBlueTopLeft *= var12;
			this.colorRedBottomLeft *= var13;
			this.colorGreenBottomLeft *= var13;
			this.colorBlueBottomLeft *= var13;
			this.colorRedBottomRight *= var14;
			this.colorGreenBottomRight *= var14;
			this.colorBlueBottomRight *= var14;
			this.colorRedTopRight *= var15;
			this.colorGreenTopRight *= var15;
			this.colorBlueTopRight *= var15;
			var25 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);

			if (var9)
			{
				var25 = this.fixAoSideGrassTexture(var25, par2, par3, par4, 5, par5, par6, par7);
			}

			this.renderEastFace(par1Block, par2, par3, par4, var25);

			if (var8 && fancyGrass && var25 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderEastFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		this.enableAO = false;
		return var11;
	}

	public boolean func_102027_b(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		this.enableAO = true;
		boolean var8 = false;
		float var9 = 0.0F;
		float var10 = 0.0F;
		float var11 = 0.0F;
		float var12 = 0.0F;
		boolean var13 = true;
		int var14 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
		Tessellator var15 = Tessellator.instance;
		var15.setBrightness(983055);

		if (par1Block == Block.grass)
		{
			var13 = false;
		}
		else if (this.hasOverrideBlockTexture())
		{
			var13 = false;
		}

		boolean var17;
		boolean var16;
		boolean var19;
		boolean var18;
		int var21;
		float var20;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
		{
			if (this.renderMinY <= 0.0D)
			{
				--par3;
			}

			this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

			if (!var18 && !var16)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!var19 && !var16)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
				this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!var18 && !var17)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!var19 && !var17)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
				this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (this.renderMinY <= 0.0D)
			{
				++par3;
			}

			var21 = var14;

			if (this.renderMinY <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var20) / 4.0F;
			var12 = (this.aoLightValueScratchYZNP + var20 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
			var11 = (var20 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
			var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var20 + this.aoLightValueScratchYZNN) / 4.0F;
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var21);
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var21);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var21);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var21);

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.5F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			this.renderBottomFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
			var8 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
		{
			if (this.renderMaxY >= 1.0D)
			{
				++par3;
			}

			this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];

			if (!var18 && !var16)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 - 1);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 - 1);
			}

			if (!var18 && !var17)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 - 1);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 - 1);
			}

			if (!var19 && !var16)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4 + 1);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4 + 1);
			}

			if (!var19 && !var17)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4 + 1);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4 + 1);
			}

			if (this.renderMaxY >= 1.0D)
			{
				--par3;
			}

			var21 = var14;

			if (this.renderMaxY >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var20) / 4.0F;
			var9 = (this.aoLightValueScratchYZPP + var20 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
			var10 = (var20 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
			var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var20 + this.aoLightValueScratchYZPN) / 4.0F;
			this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var21);
			this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var21);
			this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var21);
			this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var21);
			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7;
			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			this.renderTopFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
			var8 = true;
		}

		float var23;
		float var22;
		float var25;
		float var24;
		int var27;
		int var26;
		int var29;
		int var28;
		Icon var30;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
		{
			if (this.renderMinZ <= 0.0D)
			{
				--par4;
			}

			this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchYZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchYZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];

			if (!var16 && !var18)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!var16 && !var19)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!var17 && !var18)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!var17 && !var19)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (this.renderMinZ <= 0.0D)
			{
				++par4;
			}

			var21 = var14;

			if (this.renderMinZ <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			var23 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var20 + this.aoLightValueScratchYZPN) / 4.0F;
			var22 = (var20 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
			var25 = (this.aoLightValueScratchYZNN + var20 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
			var24 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var20) / 4.0F;
			var9 = (float)(var23 * this.renderMaxY * (1.0D - this.renderMinX) + var22 * this.renderMinY * this.renderMinX + var25 * (1.0D - this.renderMaxY) * this.renderMinX + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			var10 = (float)(var23 * this.renderMaxY * (1.0D - this.renderMaxX) + var22 * this.renderMaxY * this.renderMaxX + var25 * (1.0D - this.renderMaxY) * this.renderMaxX + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			var11 = (float)(var23 * this.renderMinY * (1.0D - this.renderMaxX) + var22 * this.renderMinY * this.renderMaxX + var25 * (1.0D - this.renderMinY) * this.renderMaxX + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			var12 = (float)(var23 * this.renderMinY * (1.0D - this.renderMinX) + var22 * this.renderMinY * this.renderMinX + var25 * (1.0D - this.renderMinY) * this.renderMinX + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
			var27 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var21);
			var26 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var21);
			var29 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var21);
			var28 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var21);
			this.brightnessTopLeft = this.mixAoBrightness(var27, var26, var29, var28, this.renderMaxY * (1.0D - this.renderMinX), this.renderMaxY * this.renderMinX, (1.0D - this.renderMaxY) * this.renderMinX, (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			this.brightnessBottomLeft = this.mixAoBrightness(var27, var26, var29, var28, this.renderMaxY * (1.0D - this.renderMaxX), this.renderMaxY * this.renderMaxX, (1.0D - this.renderMaxY) * this.renderMaxX, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			this.brightnessBottomRight = this.mixAoBrightness(var27, var26, var29, var28, this.renderMinY * (1.0D - this.renderMaxX), this.renderMinY * this.renderMaxX, (1.0D - this.renderMinY) * this.renderMaxX, (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			this.brightnessTopRight = this.mixAoBrightness(var27, var26, var29, var28, this.renderMinY * (1.0D - this.renderMinX), this.renderMinY * this.renderMinX, (1.0D - this.renderMinY) * this.renderMinX, (1.0D - this.renderMinY) * (1.0D - this.renderMinX));

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);
			this.renderNorthFace(par1Block, par2, par3, par4, var30);

			if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderNorthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
		{
			if (this.renderMaxZ >= 1.0D)
			{
				++par4;
			}

			this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			this.aoLightValueScratchYZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchYZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];

			if (!var16 && !var18)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 - 1, par4);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 - 1, par4);
			}

			if (!var16 && !var19)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3 + 1, par4);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3 + 1, par4);
			}

			if (!var17 && !var18)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 - 1, par4);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 - 1, par4);
			}

			if (!var17 && !var19)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3 + 1, par4);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3 + 1, par4);
			}

			if (this.renderMaxZ >= 1.0D)
			{
				--par4;
			}

			var21 = var14;

			if (this.renderMaxZ >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			var23 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var20 + this.aoLightValueScratchYZPP) / 4.0F;
			var22 = (var20 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			var25 = (this.aoLightValueScratchYZNP + var20 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
			var24 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var20) / 4.0F;
			var9 = (float)(var23 * this.renderMaxY * (1.0D - this.renderMinX) + var22 * this.renderMaxY * this.renderMinX + var25 * (1.0D - this.renderMaxY) * this.renderMinX + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
			var10 = (float)(var23 * this.renderMinY * (1.0D - this.renderMinX) + var22 * this.renderMinY * this.renderMinX + var25 * (1.0D - this.renderMinY) * this.renderMinX + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
			var11 = (float)(var23 * this.renderMinY * (1.0D - this.renderMaxX) + var22 * this.renderMinY * this.renderMaxX + var25 * (1.0D - this.renderMinY) * this.renderMaxX + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
			var12 = (float)(var23 * this.renderMaxY * (1.0D - this.renderMaxX) + var22 * this.renderMaxY * this.renderMaxX + var25 * (1.0D - this.renderMaxY) * this.renderMaxX + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
			var27 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var21);
			var26 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var21);
			var29 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var21);
			var28 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var21);
			this.brightnessTopLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * this.renderMinX, this.renderMaxY * this.renderMinX);
			this.brightnessBottomLeft = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * this.renderMinX, this.renderMinY * this.renderMinX);
			this.brightnessBottomRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMinY * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
			this.brightnessTopRight = this.mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * this.renderMaxX, this.renderMaxY * this.renderMaxX);

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.8F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);
			this.renderSouthFace(par1Block, par2, par3, par4, var30);

			if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderSouthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
		{
			if (this.renderMinX <= 0.0D)
			{
				--par2;
			}

			this.aoLightValueScratchXYNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchXZNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchXZNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];

			if (!var19 && !var16)
			{
				this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
				this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!var18 && !var16)
			{
				this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
				this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!var19 && !var17)
			{
				this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
				this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
			}
			else
			{
				this.aoLightValueScratchXYZNPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
				this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!var18 && !var17)
			{
				this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
				this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
			}
			else
			{
				this.aoLightValueScratchXYZNPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
				this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (this.renderMinX <= 0.0D)
			{
				++par2;
			}

			var21 = var14;

			if (this.renderMinX <= 0.0D || !this.blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 - 1, par3, par4);
			var23 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var20 + this.aoLightValueScratchXZNP) / 4.0F;
			var22 = (var20 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
			var25 = (this.aoLightValueScratchXZNN + var20 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
			var24 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var20) / 4.0F;
			var9 = (float)(var22 * this.renderMaxY * this.renderMaxZ + var25 * this.renderMaxY * (1.0D - this.renderMaxZ) + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + var23 * (1.0D - this.renderMaxY) * this.renderMaxZ);
			var10 = (float)(var22 * this.renderMaxY * this.renderMinZ + var25 * this.renderMaxY * (1.0D - this.renderMinZ) + var24 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + var23 * (1.0D - this.renderMaxY) * this.renderMinZ);
			var11 = (float)(var22 * this.renderMinY * this.renderMinZ + var25 * this.renderMinY * (1.0D - this.renderMinZ) + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + var23 * (1.0D - this.renderMinY) * this.renderMinZ);
			var12 = (float)(var22 * this.renderMinY * this.renderMaxZ + var25 * this.renderMinY * (1.0D - this.renderMaxZ) + var24 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + var23 * (1.0D - this.renderMinY) * this.renderMaxZ);
			var27 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var21);
			var26 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var21);
			var29 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var21);
			var28 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var21);
			this.brightnessTopLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * this.renderMaxZ, this.renderMaxY * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * this.renderMaxZ);
			this.brightnessBottomLeft = this.mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * this.renderMinZ, this.renderMaxY * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * this.renderMinZ);
			this.brightnessBottomRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * this.renderMinZ, this.renderMinY * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * this.renderMinZ);
			this.brightnessTopRight = this.mixAoBrightness(var26, var29, var28, var27, this.renderMinY * this.renderMaxZ, this.renderMinY * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * this.renderMaxZ);

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);
			this.renderWestFace(par1Block, par2, par3, par4, var30);

			if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderWestFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
		{
			if (this.renderMaxX >= 1.0D)
			{
				++par2;
			}

			this.aoLightValueScratchXYPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4);
			this.aoLightValueScratchXZPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 - 1);
			this.aoLightValueScratchXZPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3, par4 + 1);
			this.aoLightValueScratchXYPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4);
			this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4);
			this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1);
			this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1);
			this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4);
			var17 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var16 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var19 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var18 = Block.canBlockGrass[this.blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];

			if (!var16 && !var18)
			{
				this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPNN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 - 1);
				this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 - 1);
			}

			if (!var16 && !var19)
			{
				this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPNP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 - 1, par4 + 1);
				this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4 + 1);
			}

			if (!var17 && !var18)
			{
				this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
				this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
			}
			else
			{
				this.aoLightValueScratchXYZPPN = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 - 1);
				this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 - 1);
			}

			if (!var17 && !var19)
			{
				this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
				this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
			}
			else
			{
				this.aoLightValueScratchXYZPPP = this.getAmbientOcclusionLightValue(this.blockAccess, par2, par3 + 1, par4 + 1);
				this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4 + 1);
			}

			if (this.renderMaxX >= 1.0D)
			{
				--par2;
			}

			var21 = var14;

			if (this.renderMaxX >= 1.0D || !this.blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4);
			}

			var20 = this.getAmbientOcclusionLightValue(this.blockAccess, par2 + 1, par3, par4);
			var23 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var20 + this.aoLightValueScratchXZPP) / 4.0F;
			var22 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var20) / 4.0F;
			var25 = (this.aoLightValueScratchXZPN + var20 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
			var24 = (var20 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
			var9 = (float)(var23 * (1.0D - this.renderMinY) * this.renderMaxZ + var22 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + var25 * this.renderMinY * (1.0D - this.renderMaxZ) + var24 * this.renderMinY * this.renderMaxZ);
			var10 = (float)(var23 * (1.0D - this.renderMinY) * this.renderMinZ + var22 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + var25 * this.renderMinY * (1.0D - this.renderMinZ) + var24 * this.renderMinY * this.renderMinZ);
			var11 = (float)(var23 * (1.0D - this.renderMaxY) * this.renderMinZ + var22 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + var25 * this.renderMaxY * (1.0D - this.renderMinZ) + var24 * this.renderMaxY * this.renderMinZ);
			var12 = (float)(var23 * (1.0D - this.renderMaxY) * this.renderMaxZ + var22 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + var25 * this.renderMaxY * (1.0D - this.renderMaxZ) + var24 * this.renderMaxY * this.renderMaxZ);
			var27 = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var21);
			var26 = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var21);
			var29 = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var21);
			var28 = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var21);
			this.brightnessTopLeft = this.mixAoBrightness(var27, var28, var29, var26, (1.0D - this.renderMinY) * this.renderMaxZ, (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), this.renderMinY * (1.0D - this.renderMaxZ), this.renderMinY * this.renderMaxZ);
			this.brightnessBottomLeft = this.mixAoBrightness(var27, var28, var29, var26, (1.0D - this.renderMinY) * this.renderMinZ, (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), this.renderMinY * (1.0D - this.renderMinZ), this.renderMinY * this.renderMinZ);
			this.brightnessBottomRight = this.mixAoBrightness(var27, var28, var29, var26, (1.0D - this.renderMaxY) * this.renderMinZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), this.renderMaxY * (1.0D - this.renderMinZ), this.renderMaxY * this.renderMinZ);
			this.brightnessTopRight = this.mixAoBrightness(var27, var28, var29, var26, (1.0D - this.renderMaxY) * this.renderMaxZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), this.renderMaxY * (1.0D - this.renderMaxZ), this.renderMaxY * this.renderMaxZ);

			if (var13)
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = par5 * 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = par6 * 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = par7 * 0.6F;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
			}

			this.colorRedTopLeft *= var9;
			this.colorGreenTopLeft *= var9;
			this.colorBlueTopLeft *= var9;
			this.colorRedBottomLeft *= var10;
			this.colorGreenBottomLeft *= var10;
			this.colorBlueBottomLeft *= var10;
			this.colorRedBottomRight *= var11;
			this.colorGreenBottomRight *= var11;
			this.colorBlueBottomRight *= var11;
			this.colorRedTopRight *= var12;
			this.colorGreenTopRight *= var12;
			this.colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);
			this.renderEastFace(par1Block, par2, par3, par4, var30);

			if (fancyGrass && var30.getIconName().equals("grass_side") && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= par5;
				this.colorRedBottomLeft *= par5;
				this.colorRedBottomRight *= par5;
				this.colorRedTopRight *= par5;
				this.colorGreenTopLeft *= par6;
				this.colorGreenBottomLeft *= par6;
				this.colorGreenBottomRight *= par6;
				this.colorGreenTopRight *= par6;
				this.colorBlueTopLeft *= par7;
				this.colorBlueBottomLeft *= par7;
				this.colorBlueBottomRight *= par7;
				this.colorBlueTopRight *= par7;
				this.renderEastFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		this.enableAO = false;
		return var8;
	}

	/**
	 * Get ambient occlusion brightness
	 */
	public int getAoBrightness(int par1, int par2, int par3, int par4)
	{
		if (par1 == 0)
		{
			par1 = par4;
		}

		if (par2 == 0)
		{
			par2 = par4;
		}

		if (par3 == 0)
		{
			par3 = par4;
		}

		return par1 + par2 + par3 + par4 >> 2 & 16711935;
	}

	public int mixAoBrightness(int par1, int par2, int par3, int par4, double par5, double par7, double par9, double par11)
	{
		int var13 = (int)((par1 >> 16 & 255) * par5 + (par2 >> 16 & 255) * par7 + (par3 >> 16 & 255) * par9 + (par4 >> 16 & 255) * par11) & 255;
		int var14 = (int)((par1 & 255) * par5 + (par2 & 255) * par7 + (par3 & 255) * par9 + (par4 & 255) * par11) & 255;
		return var13 << 16 | var14;
	}

	/**
	 * Renders a standard cube block at the given coordinates, with a given color ratio.  Args: block, x, y, z, r, g, b
	 */
	public boolean renderStandardBlockWithColorMultiplier(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		this.enableAO = false;
		boolean var8 = Tessellator.instance.defaultTexture;
		boolean var9 = Config.isBetterGrass() && var8;
		Tessellator var10 = Tessellator.instance;
		boolean var11 = false;
		int var12 = -1;
		float var13;
		float var14;
		float var15;
		float var16;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 - 1, par4, 0))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var13 = 0.5F;
			var14 = var13;
			var15 = var13;
			var16 = var13;

			if (par1Block != Block.grass)
			{
				var14 = var13 * par5;
				var15 = var13 * par6;
				var16 = var13 * par7;
			}

			var10.setBrightness(this.renderMinY > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 - 1, par4));
			var10.setColorOpaque_F(var14, var15, var16);
			this.renderBottomFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 0));
			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3 + 1, par4, 1))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var13 = 1.0F;
			var14 = var13 * par5;
			var15 = var13 * par6;
			var16 = var13 * par7;
			var10.setBrightness(this.renderMaxY < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3 + 1, par4));
			var10.setColorOpaque_F(var14, var15, var16);
			this.renderTopFace(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 1));
			var11 = true;
		}

		float var17;
		Icon var18;

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 - 1, 2))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var14 = 0.8F;
			var15 = var14;
			var16 = var14;
			var17 = var14;

			if (par1Block != Block.grass)
			{
				var15 = var14 * par5;
				var16 = var14 * par6;
				var17 = var14 * par7;
			}

			var10.setBrightness(this.renderMinZ > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 - 1));
			var10.setColorOpaque_F(var15, var16, var17);
			var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 2);

			if (var9)
			{
				if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide)
				{
					var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 2, var18);

					if (var18 == TextureUtils.iconGrassTop)
					{
						var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
					}
				}

				if (var18 == TextureUtils.iconSnowSide)
				{
					var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 2);
				}
			}

			this.renderNorthFace(par1Block, par2, par3, par4, var18);

			if (var8 && fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
				this.renderNorthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2, par3, par4 + 1, 3))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var14 = 0.8F;
			var15 = var14;
			var16 = var14;
			var17 = var14;

			if (par1Block != Block.grass)
			{
				var15 = var14 * par5;
				var16 = var14 * par6;
				var17 = var14 * par7;
			}

			var10.setBrightness(this.renderMaxZ < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4 + 1));
			var10.setColorOpaque_F(var15, var16, var17);
			var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 3);

			if (var9)
			{
				if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide)
				{
					var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 3, var18);

					if (var18 == TextureUtils.iconGrassTop)
					{
						var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
					}
				}

				if (var18 == TextureUtils.iconSnowSide)
				{
					var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 3);
				}
			}

			this.renderSouthFace(par1Block, par2, par3, par4, var18);

			if (var8 && fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
				this.renderSouthFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 - 1, par3, par4, 4))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var14 = 0.6F;
			var15 = var14;
			var16 = var14;
			var17 = var14;

			if (par1Block != Block.grass)
			{
				var15 = var14 * par5;
				var16 = var14 * par6;
				var17 = var14 * par7;
			}

			var10.setBrightness(this.renderMinX > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 - 1, par3, par4));
			var10.setColorOpaque_F(var15, var16, var17);
			var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 4);

			if (var9)
			{
				if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide)
				{
					var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 4, var18);

					if (var18 == TextureUtils.iconGrassTop)
					{
						var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
					}
				}

				if (var18 == TextureUtils.iconSnowSide)
				{
					var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 4);
				}
			}

			this.renderWestFace(par1Block, par2, par3, par4, var18);

			if (var8 && fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
				this.renderWestFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, par2 + 1, par3, par4, 5))
		{
			if (var12 < 0)
			{
				var12 = par1Block.getMixedBrightnessForBlock(this.blockAccess, par2, par3, par4);
			}

			var14 = 0.6F;
			var15 = var14;
			var16 = var14;
			var17 = var14;

			if (par1Block != Block.grass)
			{
				var15 = var14 * par5;
				var16 = var14 * par6;
				var17 = var14 * par7;
			}

			var10.setBrightness(this.renderMaxX < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(this.blockAccess, par2 + 1, par3, par4));
			var10.setColorOpaque_F(var15, var16, var17);
			var18 = this.getBlockIcon(par1Block, this.blockAccess, par2, par3, par4, 5);

			if (var9)
			{
				if (var18 == TextureUtils.iconGrassSide || var18 == TextureUtils.iconMycelSide)
				{
					var18 = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, 5, var18);

					if (var18 == TextureUtils.iconGrassTop)
					{
						var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
					}
				}

				if (var18 == TextureUtils.iconSnowSide)
				{
					var18 = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, 5);
				}
			}

			this.renderEastFace(par1Block, par2, par3, par4, var18);

			if (var8 && fancyGrass && var18 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var10.setColorOpaque_F(var15 * par5, var16 * par6, var17 * par7);
				this.renderEastFace(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}

			var11 = true;
		}

		return var11;
	}

	/**
	 * Renders beacon block
	 */
	public boolean renderBlockBeacon(BlockBeacon par1BlockBeacon, int par2, int par3, int par4)
	{
		float var5 = 0.1875F;
		this.setOverrideBlockTexture(this.getBlockIcon(Block.obsidian));
		this.setRenderBounds(0.125D, 0.0062500000931322575D, 0.125D, 0.875D, var5, 0.875D);
		this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
		this.setOverrideBlockTexture(this.getBlockIcon(Block.glass));
		this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
		this.setOverrideBlockTexture(par1BlockBeacon.func_94446_i());
		this.setRenderBounds(0.1875D, var5, 0.1875D, 0.8125D, 0.875D, 0.8125D);
		this.renderStandardBlock(par1BlockBeacon, par2, par3, par4);
		this.clearOverrideBlockTexture();
		return true;
	}

	/**
	 * Renders the given texture to the bottom face of the block. Args: block, x, y, z, texture
	 */
	public void renderBottomFace(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			par8Icon = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateBottom == 0)
		{
			par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 0, par8Icon);
		}

		boolean var10 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateBottom == 0)
		{
			NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);

			if (var11 != null)
			{
				int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 0);

				if (var11.rotation > 1)
				{
					this.uvRotateBottom = var12 & 3;
				}

				if (var11.rotation == 2)
				{
					this.uvRotateBottom = this.uvRotateBottom / 2 * 3;
				}

				if (var11.flip)
				{
					this.flipTexture = (var12 & 4) != 0;
				}

				var10 = true;
			}
		}

		double var42 = par8Icon.getInterpolatedU(this.renderMinX * 16.0D);
		double var44 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0D);
		double var46 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0D);
		double var48 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0D);

		if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
		{
			var42 = par8Icon.getMinU();
			var44 = par8Icon.getMaxU();
		}

		if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
		{
			var46 = par8Icon.getMinV();
			var48 = par8Icon.getMaxV();
		}

		if (par1Block == MCE_Items.thinGlass)
		{
			if (this.renderMinX > 0.01D)
			{
				var42 = par8Icon.getMaxU();
				var44 = par8Icon.getInterpolatedU(15.0D);
				var46 = par8Icon.getMaxV();
				var48 = par8Icon.getMinV();
			}
			else
			{
				var42 = par8Icon.getMinU();
				var44 = par8Icon.getMaxU();
				var46 = par8Icon.getInterpolatedV(15.0D);
				var48 = par8Icon.getMaxV();
			}
		}

		double var19;

		if (this.flipTexture)
		{
			var19 = var42;
			var42 = var44;
			var44 = var19;
		}

		var19 = var44;
		double var21 = var42;
		double var23 = var46;
		double var25 = var48;
		double var27;

		if (this.uvRotateBottom == 2)
		{
			var42 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0D);
			var46 = par8Icon.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			var44 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0D);
			var48 = par8Icon.getInterpolatedV(16.0D - this.renderMinX * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var23 = var46;
			var25 = var48;
			var19 = var42;
			var21 = var44;
			var46 = var48;
			var48 = var23;
		}
		else if (this.uvRotateBottom == 1)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var42 = var44;
			var44 = var21;
			var23 = var48;
			var25 = var46;
		}
		else if (this.uvRotateBottom == 3)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			var46 = par8Icon.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			var48 = par8Icon.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var23 = var46;
			var25 = var48;
		}

		if (var10)
		{
			this.uvRotateBottom = 0;
			this.flipTexture = false;
		}

		var27 = par2 + this.renderMinX;
		double var29 = par2 + this.renderMaxX;
		double var31 = par4 + this.renderMinY;
		double var33 = par6 + this.renderMinZ;
		double var35 = par6 + this.renderMaxZ;

		if (this.enableAO)
		{
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var27, var31, var35, var21, var25);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var27, var31, var33, var42, var46);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var29, var31, var33, var19, var23);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var29, var31, var35, var44, var48);
		}
		else
		{
			var9.addVertexWithUV(var27, var31, var35, var21, var25);
			var9.addVertexWithUV(var27, var31, var33, var42, var46);
			var9.addVertexWithUV(var29, var31, var33, var19, var23);
			var9.addVertexWithUV(var29, var31, var35, var44, var48);
		}
	}

	/**
	 * Renders the given texture to the top face of the block. Args: block, x, y, z, texture
	 */
	public void renderTopFace(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			par8Icon = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateTop == 0)
		{
			par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 1, par8Icon);
		}

		boolean var10 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateTop == 0)
		{
			NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);

			if (var11 != null)
			{
				int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 1);

				if (var11.rotation > 1)
				{
					this.uvRotateTop = var12 & 3;
				}

				if (var11.rotation == 2)
				{
					this.uvRotateTop = this.uvRotateTop / 2 * 3;
				}

				if (var11.flip)
				{
					this.flipTexture = (var12 & 4) != 0;
				}

				var10 = true;
			}
		}

		double var42 = par8Icon.getInterpolatedU(this.renderMinX * 16.0D);
		double var44 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0D);
		double var46 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0D);
		double var48 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var42;
			var42 = var44;
			var44 = var19;
		}

		if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
		{
			var42 = par8Icon.getMinU();
			var44 = par8Icon.getMaxU();
		}

		if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
		{
			var46 = par8Icon.getMinV();
			var48 = par8Icon.getMaxV();
		}

		if (par1Block == MCE_Items.thinGlass)
		{
			if (this.renderMinX > 0.01D)
			{
				var42 = par8Icon.getInterpolatedU(1.0D);
				var44 = par8Icon.getMinU();
				var46 = par8Icon.getMaxV();
				var48 = par8Icon.getMinV();
			}
			else
			{
				var42 = par8Icon.getMinU();
				var44 = par8Icon.getMaxU();
				var46 = par8Icon.getMinV();
				var48 = par8Icon.getInterpolatedV(1.0D);
			}
		}

		var19 = var44;
		double var21 = var42;
		double var23 = var46;
		double var25 = var48;
		double var27;

		if (this.uvRotateTop == 1)
		{
			var42 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0D);
			var46 = par8Icon.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			var44 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0D);
			var48 = par8Icon.getInterpolatedV(16.0D - this.renderMinX * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var23 = var46;
			var25 = var48;
			var19 = var42;
			var21 = var44;
			var46 = var48;
			var48 = var23;
		}
		else if (this.uvRotateTop == 2)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var42 = var44;
			var44 = var21;
			var23 = var48;
			var25 = var46;
		}
		else if (this.uvRotateTop == 3)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			var46 = par8Icon.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			var48 = par8Icon.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var23 = var46;
			var25 = var48;
		}

		if (var10)
		{
			this.uvRotateTop = 0;
			this.flipTexture = false;
		}

		var27 = par2 + this.renderMinX;
		double var29 = par2 + this.renderMaxX;
		double var31 = par4 + this.renderMaxY;
		double var33 = par6 + this.renderMinZ;
		double var35 = par6 + this.renderMaxZ;

		if (this.enableAO)
		{
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var29, var31, var35, var44, var48);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var29, var31, var33, var19, var23);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var27, var31, var33, var42, var46);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var27, var31, var35, var21, var25);
		}
		else
		{
			var9.addVertexWithUV(var29, var31, var35, var44, var48);
			var9.addVertexWithUV(var29, var31, var33, var19, var23);
			var9.addVertexWithUV(var27, var31, var33, var42, var46);
			var9.addVertexWithUV(var27, var31, var35, var21, var25);
		}
	}

	/**
	 * Renders the given texture to the north (z-negative) face of the block.  Args: block, x, y, z, texture
	 */
	public void renderNorthFace(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			par8Icon = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateNorth == 0)
		{
			par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 2, par8Icon);
		}

		boolean var10 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateNorth == 0)
		{
			NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);

			if (var11 != null)
			{
				int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 2);

				if (var11.rotation > 1)
				{
					this.uvRotateNorth = var12 & 3;
				}

				if (var11.rotation == 2)
				{
					this.uvRotateNorth = this.uvRotateNorth / 2 * 3;
				}

				if (var11.flip)
				{
					this.flipTexture = (var12 & 4) != 0;
				}

				var10 = true;
			}
		}

		double var42 = par8Icon.getInterpolatedU(this.renderMinX * 16.0D);
		double var44 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0D);
		double var46 = par8Icon.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double var48 = par8Icon.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var42;
			var42 = var44;
			var44 = var19;
		}

		if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
		{
			var42 = par8Icon.getMinU();
			var44 = par8Icon.getMaxU();
		}

		if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
		{
			var46 = par8Icon.getMinV();
			var48 = par8Icon.getMaxV();
		}

		if (par1Block == MCE_Items.thinGlass)
		{
			if (this.renderMinX > 0.01D)
			{
				var42 = par8Icon.getInterpolatedU(15.0D);
				var44 = par8Icon.getMaxU();
				var46 = par8Icon.getMinV();
				var48 = par8Icon.getMaxV();
			}
		}

		var19 = var44;
		double var21 = var42;
		double var23 = var46;
		double var25 = var48;
		double var27;

		if (this.uvRotateNorth == 2)
		{
			var42 = par8Icon.getInterpolatedU(this.renderMinY * 16.0D);
			var46 = par8Icon.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0D);
			var48 = par8Icon.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var23 = var46;
			var25 = var48;
			var19 = var42;
			var21 = var44;
			var46 = var48;
			var48 = var23;
		}
		else if (this.uvRotateNorth == 1)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMinX * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var42 = var44;
			var44 = var21;
			var23 = var48;
			var25 = var46;
		}
		else if (this.uvRotateNorth == 3)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMinY * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var23 = var46;
			var25 = var48;
		}

		if (var10)
		{
			this.uvRotateNorth = 0;
			this.flipTexture = false;
		}

		var27 = par2 + this.renderMinX;
		double var29 = par2 + this.renderMaxX;
		double var31 = par4 + this.renderMinY;
		double var33 = par4 + this.renderMaxY;
		double var35 = par6 + this.renderMinZ;

		if (this.enableAO)
		{
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var27, var33, var35, var19, var23);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var29, var33, var35, var42, var46);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var29, var31, var35, var21, var25);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var27, var31, var35, var44, var48);
		}
		else
		{
			var9.addVertexWithUV(var27, var33, var35, var19, var23);
			var9.addVertexWithUV(var29, var33, var35, var42, var46);
			var9.addVertexWithUV(var29, var31, var35, var21, var25);
			var9.addVertexWithUV(var27, var31, var35, var44, var48);
		}
	}

	/**
	 * Renders the given texture to the south (z-positive) face of the block.  Args: block, x, y, z, texture
	 */
	public void renderSouthFace(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			par8Icon = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateSouth == 0)
		{
			par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 3, par8Icon);
		}

		boolean var10 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateSouth == 0)
		{
			NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);

			if (var11 != null)
			{
				int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 3);

				if (var11.rotation > 1)
				{
					this.uvRotateSouth = var12 & 3;
				}

				if (var11.rotation == 2)
				{
					this.uvRotateSouth = this.uvRotateSouth / 2 * 3;
				}

				if (var11.flip)
				{
					this.flipTexture = (var12 & 4) != 0;
				}

				var10 = true;
			}
		}

		double var42 = par8Icon.getInterpolatedU(this.renderMinX * 16.0D);
		double var44 = par8Icon.getInterpolatedU(this.renderMaxX * 16.0D);
		double var46 = par8Icon.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double var48 = par8Icon.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var42;
			var42 = var44;
			var44 = var19;
		}

		if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
		{
			var42 = par8Icon.getMinU();
			var44 = par8Icon.getMaxU();
		}

		if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
		{
			var46 = par8Icon.getMinV();
			var48 = par8Icon.getMaxV();
		}

		if (par1Block == MCE_Items.thinGlass)
		{
			if (this.renderMinX > 0.01D)
			{
				var42 = par8Icon.getMinU();
				var44 = par8Icon.getInterpolatedU(1.0D);
				var46 = par8Icon.getMinV();
				var48 = par8Icon.getMaxV();
			}
		}

		var19 = var44;
		double var21 = var42;
		double var23 = var46;
		double var25 = var48;
		double var27;

		if (this.uvRotateSouth == 1)
		{
			var42 = par8Icon.getInterpolatedU(this.renderMinY * 16.0D);
			var48 = par8Icon.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0D);
			var46 = par8Icon.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var23 = var46;
			var25 = var48;
			var19 = var42;
			var21 = var44;
			var46 = var48;
			var48 = var23;
		}
		else if (this.uvRotateSouth == 2)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var42 = var44;
			var44 = var21;
			var23 = var48;
			var25 = var46;
		}
		else if (this.uvRotateSouth == 3)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMinY * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var23 = var46;
			var25 = var48;
		}

		if (var10)
		{
			this.uvRotateSouth = 0;
			this.flipTexture = false;
		}

		var27 = par2 + this.renderMinX;
		double var29 = par2 + this.renderMaxX;
		double var31 = par4 + this.renderMinY;
		double var33 = par4 + this.renderMaxY;
		double var35 = par6 + this.renderMaxZ;

		if (this.enableAO)
		{
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var27, var33, var35, var42, var46);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var27, var31, var35, var21, var25);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var29, var31, var35, var44, var48);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var29, var33, var35, var19, var23);
		}
		else
		{
			var9.addVertexWithUV(var27, var33, var35, var42, var46);
			var9.addVertexWithUV(var27, var31, var35, var21, var25);
			var9.addVertexWithUV(var29, var31, var35, var44, var48);
			var9.addVertexWithUV(var29, var33, var35, var19, var23);
		}
	}

	/**
	 * Renders the given texture to the west (x-negative) face of the block.  Args: block, x, y, z, texture
	 */
	public void renderWestFace(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			par8Icon = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateWest == 0)
		{
			par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 4, par8Icon);
		}

		boolean var10 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateWest == 0)
		{
			NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);

			if (var11 != null)
			{
				int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 4);

				if (var11.rotation > 1)
				{
					this.uvRotateWest = var12 & 3;
				}

				if (var11.rotation == 2)
				{
					this.uvRotateWest = this.uvRotateWest / 2 * 3;
				}

				if (var11.flip)
				{
					this.flipTexture = (var12 & 4) != 0;
				}

				var10 = true;
			}
		}

		double var42 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0D);
		double var44 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0D);
		double var46 = par8Icon.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double var48 = par8Icon.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var42;
			var42 = var44;
			var44 = var19;
		}

		if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
		{
			var42 = par8Icon.getMinU();
			var44 = par8Icon.getMaxU();
		}

		if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
		{
			var46 = par8Icon.getMinV();
			var48 = par8Icon.getMaxV();
		}

		if (par1Block == MCE_Items.thinGlass)
		{
			if (this.renderMinZ > 0.01D)
			{
				var42 = par8Icon.getMinU();
				var44 = par8Icon.getInterpolatedU(1.0D);
				var46 = par8Icon.getMinV();
				var48 = par8Icon.getMaxV();
			}
		}

		var19 = var44;
		double var21 = var42;
		double var23 = var46;
		double var25 = var48;
		double var27;

		if (this.uvRotateWest == 1)
		{
			var42 = par8Icon.getInterpolatedU(this.renderMinY * 16.0D);
			var46 = par8Icon.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
			var44 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0D);
			var48 = par8Icon.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var23 = var46;
			var25 = var48;
			var19 = var42;
			var21 = var44;
			var46 = var48;
			var48 = var23;
		}
		else if (this.uvRotateWest == 2)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var42 = var44;
			var44 = var21;
			var23 = var48;
			var25 = var46;
		}
		else if (this.uvRotateWest == 3)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMinY * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var23 = var46;
			var25 = var48;
		}

		if (var10)
		{
			this.uvRotateWest = 0;
			this.flipTexture = false;
		}

		var27 = par2 + this.renderMinX;
		double var29 = par4 + this.renderMinY;
		double var31 = par4 + this.renderMaxY;
		double var33 = par6 + this.renderMinZ;
		double var35 = par6 + this.renderMaxZ;

		if (this.enableAO)
		{
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var27, var31, var35, var19, var23);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var27, var31, var33, var42, var46);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var27, var29, var33, var21, var25);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var27, var29, var35, var44, var48);
		}
		else
		{
			var9.addVertexWithUV(var27, var31, var35, var19, var23);
			var9.addVertexWithUV(var27, var31, var33, var42, var46);
			var9.addVertexWithUV(var27, var29, var33, var21, var25);
			var9.addVertexWithUV(var27, var29, var35, var44, var48);
		}
	}

	/**
	 * Renders the given texture to the east (x-positive) face of the block.  Args: block, x, y, z, texture
	 */
	public void renderEastFace(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			par8Icon = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateEast == 0)
		{
			par8Icon = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)par2, (int)par4, (int)par6, 5, par8Icon);
		}

		boolean var10 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateEast == 0)
		{
			NaturalProperties var11 = NaturalTextures.getNaturalProperties(par8Icon);

			if (var11 != null)
			{
				int var12 = Config.getRandom((int)par2, (int)par4, (int)par6, 5);

				if (var11.rotation > 1)
				{
					this.uvRotateEast = var12 & 3;
				}

				if (var11.rotation == 2)
				{
					this.uvRotateEast = this.uvRotateEast / 2 * 3;
				}

				if (var11.flip)
				{
					this.flipTexture = (var12 & 4) != 0;
				}

				var10 = true;
			}
		}

		double var42 = par8Icon.getInterpolatedU(this.renderMinZ * 16.0D);
		double var44 = par8Icon.getInterpolatedU(this.renderMaxZ * 16.0D);
		double var46 = par8Icon.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double var48 = par8Icon.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var42;
			var42 = var44;
			var44 = var19;
		}

		if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
		{
			var42 = par8Icon.getMinU();
			var44 = par8Icon.getMaxU();
		}

		if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
		{
			var46 = par8Icon.getMinV();
			var48 = par8Icon.getMaxV();
		}

		if (par1Block == MCE_Items.thinGlass)
		{
			if (this.renderMinZ > 0.01D)
			{
				var42 = par8Icon.getInterpolatedU(15.0D);
				var44 = par8Icon.getMaxU();
				var46 = par8Icon.getMinV();
				var48 = par8Icon.getMaxV();
			}
		}

		var19 = var44;
		double var21 = var42;
		double var23 = var46;
		double var25 = var48;
		double var27;

		if (this.uvRotateEast == 2)
		{
			var42 = par8Icon.getInterpolatedU(this.renderMinY * 16.0D);
			var46 = par8Icon.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			var44 = par8Icon.getInterpolatedU(this.renderMaxY * 16.0D);
			var48 = par8Icon.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var23 = var46;
			var25 = var48;
			var19 = var42;
			var21 = var44;
			var46 = var48;
			var48 = var23;
		}
		else if (this.uvRotateEast == 1)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMaxZ * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMinZ * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var42 = var44;
			var44 = var21;
			var23 = var48;
			var25 = var46;
		}
		else if (this.uvRotateEast == 3)
		{
			var42 = par8Icon.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			var44 = par8Icon.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			var46 = par8Icon.getInterpolatedV(this.renderMaxY * 16.0D);
			var48 = par8Icon.getInterpolatedV(this.renderMinY * 16.0D);

			if (this.flipTexture)
			{
				var27 = var42;
				var42 = var44;
				var44 = var27;
			}

			var19 = var44;
			var21 = var42;
			var23 = var46;
			var25 = var48;
		}

		if (var10)
		{
			this.uvRotateEast = 0;
			this.flipTexture = false;
		}

		var27 = par2 + this.renderMaxX;
		double var29 = par4 + this.renderMinY;
		double var31 = par4 + this.renderMaxY;
		double var33 = par6 + this.renderMinZ;
		double var35 = par6 + this.renderMaxZ;

		if (this.enableAO)
		{
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(var27, var29, var35, var21, var25);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(var27, var29, var33, var44, var48);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(var27, var31, var33, var19, var23);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(var27, var31, var35, var42, var46);
		}
		else
		{
			var9.addVertexWithUV(var27, var29, var35, var21, var25);
			var9.addVertexWithUV(var27, var29, var33, var44, var48);
			var9.addVertexWithUV(var27, var31, var33, var19, var23);
			var9.addVertexWithUV(var27, var31, var35, var42, var46);
		}
	}

	/**
	 * Is called to render the image of a block on an inventory, as a held item, or as a an item on the ground
	 */
	public void renderBlockAsItem(Block par1Block, int par2, float par3)
	{
		Tessellator var4 = Tessellator.instance;
		boolean var5 = par1Block.blockID == Block.grass.blockID;

		int var6;
		float var7;
		float var8;
		float var9;

		if (this.useInventoryTint)
		{
			var6 = par1Block.getRenderColor(par2);

			if (var5)
			{
				var6 = 16777215;
			}

			var7 = (var6 >> 16 & 255) / 255.0F;
			var8 = (var6 >> 8 & 255) / 255.0F;
			var9 = (var6 & 255) / 255.0F;
			GL11.glColor4f(var7 * par3, var8 * par3, var9 * par3, 1.0F);
		}

		var6 = par1Block.getRenderType();
		this.setRenderBoundsFromBlock(par1Block);
		int var10;

		if (var6 == MCE_Items.titaniumLampModelID)
		{
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			this.renderLampAtAngle(par1Block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D, par2);
			var4.draw();
		}
		else
		{
			par1Block.setBlockBoundsForItemRender();
			this.setRenderBoundsFromBlock(par1Block);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 0, par2));
			var4.draw();

			if (var5 && this.useInventoryTint)
			{
				var10 = par1Block.getRenderColor(par2);
				var8 = (var10 >> 16 & 255) / 255.0F;
				var9 = (var10 >> 8 & 255) / 255.0F;
				float var11 = (var10 & 255) / 255.0F;
				GL11.glColor4f(var8 * par3, var9 * par3, var11 * par3, 1.0F);
			}

			var4.startDrawingQuads();
			var4.setNormal(0.0F, 1.0F, 0.0F);
			this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 1, par2));
			var4.draw();

			if (var5 && this.useInventoryTint)
			{
				GL11.glColor4f(par3, par3, par3, 1.0F);
			}

			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 2, par2));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 3, par2));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 4, par2));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, this.getBlockIconFromSideAndMetadata(par1Block, 5, par2));
			var4.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}

	/**
	 * Checks to see if the item's render type indicates that it should be rendered as a regular block or not.
	 */
	public static boolean renderItemIn3d(int par1)
	{
		return Reflector.ModLoader.exists() ? Reflector.callBoolean(Reflector.ModLoader_renderBlockIsItemFull3D, new Object[] {Integer.valueOf(par1)}): (Reflector.FMLRenderAccessLibrary.exists() ? Reflector.callBoolean(Reflector.FMLRenderAccessLibrary_renderItemAsFull3DBlock, new Object[] {Integer.valueOf(par1)}): false);
	}

	public Icon getBlockIcon(Block par1Block, IBlockAccess par2IBlockAccess, int par3, int par4, int par5, int par6)
	{
		return this.getIconSafe(par1Block.getBlockTexture(par2IBlockAccess, par3, par4, par5, par6));
	}

	public Icon getBlockIconFromSideAndMetadata(Block par1Block, int par2, int par3)
	{
		return this.getIconSafe(par1Block.getIcon(par2, par3));
	}

	public Icon getBlockIconFromSide(Block par1Block, int par2)
	{
		return this.getIconSafe(par1Block.getBlockTextureFromSide(par2));
	}

	public Icon getBlockIcon(Block par1Block)
	{
		return this.getIconSafe(par1Block.getBlockTextureFromSide(1));
	}

	public Icon getIconSafe(Icon par1Icon)
	{
		return par1Icon == null ? this.minecraftRB.renderEngine.getMissingIcon(0) : par1Icon;
	}

	private float getAmbientOcclusionLightValue(IBlockAccess var1, int var2, int var3, int var4)
	{
		Block var5 = Block.blocksList[var1.getBlockId(var2, var3, var4)];

		if (var5 == null)
		{
			return 1.0F;
		}
		else
		{
			boolean var6 = var5.blockMaterial.blocksMovement() && var5.renderAsNormalBlock();
			return var6 ? this.aoLightValueOpaque : 1.0F;
		}
	}

	private Icon fixAoSideGrassTexture(Icon var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
	{
		if (var1 == TextureUtils.iconGrassSide || var1 == TextureUtils.iconMycelSide)
		{
			var1 = Config.getSideGrassTexture(this.blockAccess, var2, var3, var4, var5, var1);

			if (var1 == TextureUtils.iconGrassTop)
			{
				this.colorRedTopLeft *= var6;
				this.colorRedBottomLeft *= var6;
				this.colorRedBottomRight *= var6;
				this.colorRedTopRight *= var6;
				this.colorGreenTopLeft *= var7;
				this.colorGreenBottomLeft *= var7;
				this.colorGreenBottomRight *= var7;
				this.colorGreenTopRight *= var7;
				this.colorBlueTopLeft *= var8;
				this.colorBlueBottomLeft *= var8;
				this.colorBlueBottomRight *= var8;
				this.colorBlueTopRight *= var8;
			}
		}

		if (var1 == TextureUtils.iconSnowSide)
		{
			var1 = Config.getSideSnowGrassTexture(this.blockAccess, var2, var3, var4, var5);
		}

		return var1;
	}

	private boolean hasSnowNeighbours(int var1, int var2, int var3)
	{
		int var4 = Block.snow.blockID;
		return this.blockAccess.getBlockId(var1 - 1, var2, var3) != var4 && this.blockAccess.getBlockId(var1 + 1, var2, var3) != var4 && this.blockAccess.getBlockId(var1, var2, var3 - 1) != var4 && this.blockAccess.getBlockId(var1, var2, var3 + 1) != var4 ? false : this.blockAccess.isBlockOpaqueCube(var1, var2 - 1, var3);
	}

	private void renderSnow(int var1, int var2, int var3, double var4)
	{
		this.setRenderBoundsFromBlock(Block.snow);
		this.renderMaxY = var4;
		this.renderStandardBlock(Block.snow, var1, var2, var3);
	}
}
