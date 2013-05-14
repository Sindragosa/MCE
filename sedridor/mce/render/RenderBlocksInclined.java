package sedridor.mce.render;

import sedridor.mce.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.Config;
import net.minecraft.src.ConnectedTextures;
import net.minecraft.src.NaturalProperties;
import net.minecraft.src.NaturalTextures;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBlocksInclined
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

	private Icon blockTexture;
	private int blockMetadata = 0;

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

	/** Light value of the block itself */
	public float lightValueOwn;

	/** Light value one block less in x axis */
	public float aoLightValueXNeg;

	/** Light value one block more in y axis */
	public float aoLightValueYNeg;

	/** Light value one block more in z axis */
	public float aoLightValueZNeg;

	/** Light value one block more in x axis */
	public float aoLightValueXPos;

	/** Light value one block more in y axis */
	public float aoLightValueYPos;

	/** Light value one block more in z axis */
	public float aoLightValueZPos;

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

	/** Ambient occlusion type (0=simple, 1=complex) */
	public int aoType = 1;

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

	/**
	 * Grass flag for ambient occlusion on Center X, Positive Y, and Negative Z
	 */
	public boolean aoGrassXYZCPN;

	/**
	 * Grass flag for ambient occlusion on Positive X, Positive Y, and Center Z
	 */
	public boolean aoGrassXYZPPC;

	/**
	 * Grass flag for ambient occlusion on Negative X, Positive Y, and Center Z
	 */
	public boolean aoGrassXYZNPC;

	/**
	 * Grass flag for ambient occlusion on Center X, Positive Y, and Positive Z
	 */
	public boolean aoGrassXYZCPP;

	/**
	 * Grass flag for ambient occlusion on Negative X, Center Y, and Negative Z
	 */
	public boolean aoGrassXYZNCN;

	/**
	 * Grass flag for ambient occlusion on Positive X, Center Y, and Positive Z
	 */
	public boolean aoGrassXYZPCP;

	/**
	 * Grass flag for ambient occlusion on Negative X, Center Y, and Positive Z
	 */
	public boolean aoGrassXYZNCP;

	/**
	 * Grass flag for ambient occlusion on Positive X, Center Y, and Negative Z
	 */
	public boolean aoGrassXYZPCN;

	/**
	 * Grass flag for ambient occlusion on Center X, Negative Y, and Negative Z
	 */
	public boolean aoGrassXYZCNN;

	/**
	 * Grass flag for ambient occlusion on Positive X, Negative Y, and Center Z
	 */
	public boolean aoGrassXYZPNC;

	/**
	 * Grass flag for ambient occlusion on Negative X, Negative Y, and center Z
	 */
	public boolean aoGrassXYZNNC;

	/**
	 * Grass flag for ambient occlusion on Center X, Negative Y, and Positive Z
	 */
	public boolean aoGrassXYZCNP;

	public boolean aoLightValuesCalculated;
	public float aoLightValueOpaque = 0.2F;

	public int baseBlockInclinedCorner = MCE_Items.FIRST_BLOCKID + MCE_Items.BLOCKID_DISPLACEMENT + 160;
	public int baseBlockInclinedCornerTo = MCE_Items.FIRST_BLOCKID + MCE_Items.BLOCKID_DISPLACEMENT + 160 + MCE_Items.models.length - 1;

	public RenderBlocksInclined(IBlockAccess par1IBlockAccess)
	{
		this.blockAccess = par1IBlockAccess;
		this.minecraftRB = Minecraft.getMinecraft();
		this.aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
	}

	public RenderBlocksInclined() {
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
		this.renderBlockInclined(par1Block, par2, par3, par4);
		this.clearOverrideBlockTexture();
	}
	/**
	 * Renders an inclined block at the given coordinates
	 */
	public boolean renderBlockInclined(Block par1Block, int posX, int posY, int posZ)
	{
		par1Block.setBlockBoundsBasedOnState(this.blockAccess, posX, posY, posZ);
		this.setRenderBoundsFromBlock(par1Block);

		//this.baseBlockInclinedCorner = MCE_Items.FIRST_BLOCKID + MCE_Items.BLOCKID_DISPLACEMENT + 160;
		//this.baseBlockInclinedCornerTo = MCE_Items.FIRST_BLOCKID + MCE_Items.BLOCKID_DISPLACEMENT + 160 + MCE_Items.models.length - 1;

		int var5 = par1Block.colorMultiplier(this.blockAccess, posX, posY, posZ);
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

		int var12 = this.blockAccess.getBlockMetadata(posX, posY, posZ);
		this.blockTexture = par1Block.getIcon(4, var12);
		this.blockMetadata = var12 & 3;
		if (this.blockMetadata == 1)
		{
			// blockMetadata = 1 west
		}
		else if (this.blockMetadata == 2)
		{
			// blockMetadata = 2 south
		}
		else if (this.blockMetadata == 3)
		{
			// blockMetadata = 3 east
		}
		else
		{
			// blockMetadata = 0 north
		}

		//System.out.println("MCE...renderBlockInclined " + par1Block.blockID + " Meta" + var12 + " Texture" + this.blockTexture + " " + par1Block.getBlockName());
		if (par1Block.blockID >= this.baseBlockInclinedCorner && par1Block.blockID <= this.baseBlockInclinedCornerTo)
		{
			return Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[par1Block.blockID] == 0 ? this.renderBlockInclinedCornerWithAmbientOcclusion(par1Block, posX, posY, posZ, var6, var7, var8) : this.renderBlockInclinedCornerWithColorMultiplier(par1Block, posX, posY, posZ, var6, var7, var8);
		}
		else
		{
			return Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[par1Block.blockID] == 0 ? this.renderBlockInclinedWithAmbientOcclusion(par1Block, posX, posY, posZ, var6, var7, var8) : this.renderBlockInclinedWithColorMultiplier(par1Block, posX, posY, posZ, var6, var7, var8);
		}
	}

	/**
	 * Renders an inclined block at the given coordinates, with ambient occlusion.  Args: block, x, y, z, r, g, b
	 */
	public boolean renderBlockInclinedWithAmbientOcclusion(Block par1Block, int posX, int posY, int posZ, float r, float g, float b)
	{
		this.enableAO = true;
		boolean var8 = false;
		float var9 = this.lightValueOwn;
		float var10 = this.lightValueOwn;
		float var11 = this.lightValueOwn;
		float var12 = this.lightValueOwn;
		boolean var14 = true;
		boolean var15 = true;
		boolean var16 = true;
		boolean var17 = true;
		boolean var18 = true;
		this.lightValueOwn = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ);
		this.aoLightValueXNeg = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ);
		this.aoLightValueYNeg = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
		this.aoLightValueZNeg = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ - 1);
		this.aoLightValueXPos = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ);
		this.aoLightValueYPos = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
		this.aoLightValueZPos = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ + 1);
		int var19 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ);
		int var20 = var19;
		int var21 = var19;
		int var22 = var19;
		int var23 = var19;
		int var24 = var19;
		int var25 = var19;

		//int blockMetadata = this.blockAccess.getBlockMetadata(posX, posY, posZ);
		//Icon blockTexture = par1Block.getBlockTextureFromSideAndMetadata(4, blockMetadata);
		//blockMetadata = blockMetadata & 3;

		// Bottom
		if (par1Block.minY <= 0.0D)
		{
			var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
		}

		// Top
		if (par1Block.maxY >= 1.0D)
		{
			var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);
		}

		// West
		if (par1Block.minX <= 0.0D && this.blockMetadata != 3)
		{
			var20 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ);
		}

		// East
		if (par1Block.maxX >= 1.0D && this.blockMetadata != 1)
		{
			var23 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ);
		}

		// North
		if (par1Block.minZ <= 0.0D && this.blockMetadata != 2)
		{
			var22 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1);
		}

		// South
		if (par1Block.maxZ >= 1.0D && this.blockMetadata != 0)
		{
			var25 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1);
		}

		Tessellator var26 = Tessellator.instance;
		var26.setBrightness(983055);
		this.aoGrassXYZPPC = Block.canBlockGrass[this.blockAccess.getBlockId(posX + 1, posY + 1, posZ)];
		this.aoGrassXYZPNC = Block.canBlockGrass[this.blockAccess.getBlockId(posX + 1, posY - 1, posZ)];
		this.aoGrassXYZPCP = Block.canBlockGrass[this.blockAccess.getBlockId(posX + 1, posY, posZ + 1)];
		this.aoGrassXYZPCN = Block.canBlockGrass[this.blockAccess.getBlockId(posX + 1, posY, posZ - 1)];
		this.aoGrassXYZNPC = Block.canBlockGrass[this.blockAccess.getBlockId(posX - 1, posY + 1, posZ)];
		this.aoGrassXYZNNC = Block.canBlockGrass[this.blockAccess.getBlockId(posX - 1, posY - 1, posZ)];
		this.aoGrassXYZNCN = Block.canBlockGrass[this.blockAccess.getBlockId(posX - 1, posY, posZ - 1)];
		this.aoGrassXYZNCP = Block.canBlockGrass[this.blockAccess.getBlockId(posX - 1, posY, posZ + 1)];
		this.aoGrassXYZCPP = Block.canBlockGrass[this.blockAccess.getBlockId(posX, posY + 1, posZ + 1)];
		this.aoGrassXYZCPN = Block.canBlockGrass[this.blockAccess.getBlockId(posX, posY + 1, posZ - 1)];
		this.aoGrassXYZCNP = Block.canBlockGrass[this.blockAccess.getBlockId(posX, posY - 1, posZ + 1)];
		this.aoGrassXYZCNN = Block.canBlockGrass[this.blockAccess.getBlockId(posX, posY - 1, posZ - 1)];

		if (this.blockTexture == TextureUtils.iconGrassSide)
		{
			var18 = false;
			var17 = false;
			var16 = false;
			var15 = false;
			var14 = false;
		}

		if (this.hasOverrideBlockTexture())
		{
			var18 = false;
			var17 = false;
			var16 = false;
			var15 = false;
			var14 = false;
		}

		float var27;
		float var29;
		float var28;
		float var30;

		// Renders bottom face
		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY - 1, posZ, 0))
		{
			if (this.aoType > 0)
			{
				if (par1Block.minY <= 0.0D)
				{
					--posY;
				}

				this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ);
				this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1);
				this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1);
				this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ);
				this.aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ);
				this.aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ - 1);
				this.aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ + 1);
				this.aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ);

				if (!this.aoGrassXYZCNN && !this.aoGrassXYZNNC)
				{
					this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
					this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
				}
				else
				{
					this.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ - 1);
					this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ - 1);
				}

				if (!this.aoGrassXYZCNP && !this.aoGrassXYZNNC)
				{
					this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
					this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
				}
				else
				{
					this.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ + 1);
					this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ + 1);
				}

				if (!this.aoGrassXYZCNN && !this.aoGrassXYZPNC)
				{
					this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
					this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
				}
				else
				{
					this.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ - 1);
					this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ - 1);
				}

				if (!this.aoGrassXYZCNP && !this.aoGrassXYZPNC)
				{
					this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
					this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
				}
				else
				{
					this.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ + 1);
					this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ + 1);
				}

				if (par1Block.minY <= 0.0D)
				{
					++posY;
				}

				var27 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + this.aoLightValueYNeg) / 4.0F;
				var30 = (this.aoLightValueScratchYZNP + this.aoLightValueYNeg + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
				var29 = (this.aoLightValueYNeg + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
				var28 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + this.aoLightValueYNeg + this.aoLightValueScratchYZNN) / 4.0F;
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var21);
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var21);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var21);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var21);
			}
			else
			{
				var30 = this.aoLightValueYNeg;
				var29 = this.aoLightValueYNeg;
				var28 = this.aoLightValueYNeg;
				var27 = this.aoLightValueYNeg;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = this.aoBrightnessXYNN;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var14 ? r : 1.0F) * 0.5F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var14 ? g : 1.0F) * 0.5F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var14 ? b : 1.0F) * 0.5F;
			this.colorRedTopLeft *= var27;
			this.colorGreenTopLeft *= var27;
			this.colorBlueTopLeft *= var27;
			this.colorRedBottomLeft *= var28;
			this.colorGreenBottomLeft *= var28;
			this.colorBlueBottomLeft *= var28;
			this.colorRedBottomRight *= var29;
			this.colorGreenBottomRight *= var29;
			this.colorBlueBottomRight *= var29;
			this.colorRedTopRight *= var30;
			this.colorGreenTopRight *= var30;
			this.colorBlueTopRight *= var30;
			this.renderBottomFace(par1Block, posX, posY, posZ, par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, 0));
			var8 = true;
		}

		Icon var31;

		// Renders north face
		if (this.renderAllFaces || this.blockMetadata == 2 || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY, posZ - 1, 2))
		{
			int var41 = 2;
			if (this.aoType > 0)
			{
				if (par1Block.minZ <= 0.0D)
				{
					--posZ;
				}

				this.aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ);
				this.aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
				this.aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
				this.aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ);
				this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ);
				this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
				this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);
				this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ);

				if (!this.aoGrassXYZNCN && !this.aoGrassXYZCNN)
				{
					this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
					this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
				}
				else
				{
					this.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY - 1, posZ);
					this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY - 1, posZ);
				}

				if (!this.aoGrassXYZNCN && !this.aoGrassXYZCPN)
				{
					this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
					this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
				}
				else
				{
					this.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY + 1, posZ);
					this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY + 1, posZ);
				}

				if (!this.aoGrassXYZPCN && !this.aoGrassXYZCNN)
				{
					this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
					this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
				}
				else
				{
					this.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY - 1, posZ);
					this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY - 1, posZ);
				}

				if (!this.aoGrassXYZPCN && !this.aoGrassXYZCPN)
				{
					this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
					this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
				}
				else
				{
					this.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY + 1, posZ);
					this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY + 1, posZ);
				}

				if (par1Block.minZ <= 0.0D)
				{
					++posZ;
				}

				var27 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + this.aoLightValueZNeg + this.aoLightValueScratchYZPN) / 4.0F;
				var28 = (this.aoLightValueZNeg + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
				var29 = (this.aoLightValueScratchYZNN + this.aoLightValueZNeg + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
				var30 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + this.aoLightValueZNeg) / 4.0F;
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var22);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var22);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var22);
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var22);
			}
			else
			{
				var30 = this.aoLightValueZNeg;
				var29 = this.aoLightValueZNeg;
				var28 = this.aoLightValueZNeg;
				var27 = this.aoLightValueZNeg;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var22;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var15 ? r : 1.0F) * 0.8F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var15 ? g : 1.0F) * 0.8F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var15 ? b : 1.0F) * 0.8F;
			if (this.blockMetadata != 2)
			{
				this.colorRedTopLeft *= var27;
				this.colorGreenTopLeft *= var27;
				this.colorBlueTopLeft *= var27;
				this.colorRedBottomLeft *= var28;
				this.colorGreenBottomLeft *= var28;
				this.colorBlueBottomLeft *= var28;
				this.colorRedBottomRight *= var29;
				this.colorGreenBottomRight *= var29;
				this.colorBlueBottomRight *= var29;
				this.colorRedTopRight *= var30;
				this.colorGreenTopRight *= var30;
				this.colorBlueTopRight *= var30;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var15 || this.blockTexture == TextureUtils.iconGrassSide ? r : 1.0F) * 0.85F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var15 || this.blockTexture == TextureUtils.iconGrassSide ? g : 1.0F) * 0.85F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var15 || this.blockTexture == TextureUtils.iconGrassSide ? b : 1.0F) * 0.85F;
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 2);
			}

			var31 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			//var31 = par1Block.getBlockTextureFromSideAndMetadata(var41, this.blockMetadata);
			this.renderNorthFace(par1Block, posX, posY, posZ, var31);

			if (fancyGrass && var31 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= r;
				this.colorRedBottomLeft *= r;
				this.colorRedBottomRight *= r;
				this.colorRedTopRight *= r;
				this.colorGreenTopLeft *= g;
				this.colorGreenBottomLeft *= g;
				this.colorGreenBottomRight *= g;
				this.colorGreenTopRight *= g;
				this.colorBlueTopLeft *= b;
				this.colorBlueBottomLeft *= b;
				this.colorBlueBottomRight *= b;
				this.colorBlueTopRight *= b;
				this.renderNorthFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		// Renders south face
		if (this.renderAllFaces || this.blockMetadata == 0 || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY, posZ + 1, 3))
		{
			int var41 = 3;
			if (this.aoType > 0)
			{
				if (par1Block.maxZ >= 1.0D)
				{
					++posZ;
				}

				this.aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ);
				this.aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ);
				this.aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
				this.aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
				this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ);
				this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ);
				this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
				this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);

				if (!this.aoGrassXYZNCP && !this.aoGrassXYZCNP)
				{
					this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
					this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
				}
				else
				{
					this.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY - 1, posZ);
					this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY - 1, posZ);
				}

				if (!this.aoGrassXYZNCP && !this.aoGrassXYZCPP)
				{
					this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
					this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
				}
				else
				{
					this.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY + 1, posZ);
					this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY + 1, posZ);
				}

				if (!this.aoGrassXYZPCP && !this.aoGrassXYZCNP)
				{
					this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
					this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
				}
				else
				{
					this.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY - 1, posZ);
					this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY - 1, posZ);
				}

				if (!this.aoGrassXYZPCP && !this.aoGrassXYZCPP)
				{
					this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
					this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
				}
				else
				{
					this.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY + 1, posZ);
					this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY + 1, posZ);
				}

				if (par1Block.maxZ >= 1.0D)
				{
					--posZ;
				}

				var27 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + this.aoLightValueZPos + this.aoLightValueScratchYZPP) / 4.0F;
				var30 = (this.aoLightValueZPos + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
				var29 = (this.aoLightValueScratchYZNP + this.aoLightValueZPos + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
				var28 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + this.aoLightValueZPos) / 4.0F;
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var25);
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var25);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var25);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var25);
			}
			else
			{
				var30 = this.aoLightValueZPos;
				var29 = this.aoLightValueZPos;
				var28 = this.aoLightValueZPos;
				var27 = this.aoLightValueZPos;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var25;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var16 ? r : 1.0F) * 0.8F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var16 ? g : 1.0F) * 0.8F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var16 ? b : 1.0F) * 0.8F;
			if (this.blockMetadata != 0)
			{
				this.colorRedTopLeft *= var27;
				this.colorGreenTopLeft *= var27;
				this.colorBlueTopLeft *= var27;
				this.colorRedBottomLeft *= var28;
				this.colorGreenBottomLeft *= var28;
				this.colorBlueBottomLeft *= var28;
				this.colorRedBottomRight *= var29;
				this.colorGreenBottomRight *= var29;
				this.colorBlueBottomRight *= var29;
				this.colorRedTopRight *= var30;
				this.colorGreenTopRight *= var30;
				this.colorBlueTopRight *= var30;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var16 || this.blockTexture == TextureUtils.iconGrassSide ? r : 1.0F) * 0.85F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var16 || this.blockTexture == TextureUtils.iconGrassSide ? g : 1.0F) * 0.85F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var16 || this.blockTexture == TextureUtils.iconGrassSide ? b : 1.0F) * 0.85F;
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 3);
			}
			var31 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderSouthFace(par1Block, posX, posY, posZ, var31);

			if (fancyGrass && var31 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= r;
				this.colorRedBottomLeft *= r;
				this.colorRedBottomRight *= r;
				this.colorRedTopRight *= r;
				this.colorGreenTopLeft *= g;
				this.colorGreenBottomLeft *= g;
				this.colorGreenBottomRight *= g;
				this.colorGreenTopRight *= g;
				this.colorBlueTopLeft *= b;
				this.colorBlueBottomLeft *= b;
				this.colorBlueBottomRight *= b;
				this.colorBlueTopRight *= b;
				this.renderSouthFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		// Renders west face
		if (this.renderAllFaces || this.blockMetadata == 3 || par1Block.shouldSideBeRendered(this.blockAccess, posX - 1, posY, posZ, 4))
		{
			int var41 = 4;
			if (this.aoType > 0)
			{
				if (par1Block.minX <= 0.0D)
				{
					--posX;
				}

				this.aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
				this.aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ - 1);
				this.aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ + 1);
				this.aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
				this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
				this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1);
				this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1);
				this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);

				if (!this.aoGrassXYZNCN && !this.aoGrassXYZNNC)
				{
					this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
					this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
				}
				else
				{
					this.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ - 1);
					this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ - 1);
				}

				if (!this.aoGrassXYZNCP && !this.aoGrassXYZNNC)
				{
					this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
					this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
				}
				else
				{
					this.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ + 1);
					this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ + 1);
				}

				if (!this.aoGrassXYZNCN && !this.aoGrassXYZNPC)
				{
					this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
					this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
				}
				else
				{
					this.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ - 1);
					this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ - 1);
				}

				if (!this.aoGrassXYZNCP && !this.aoGrassXYZNPC)
				{
					this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
					this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
				}
				else
				{
					this.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ + 1);
					this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ + 1);
				}

				if (par1Block.minX <= 0.0D)
				{
					++posX;
				}

				var30 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + this.aoLightValueXNeg + this.aoLightValueScratchXZNP) / 4.0F;
				var27 = (this.aoLightValueXNeg + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
				var28 = (this.aoLightValueScratchXZNN + this.aoLightValueXNeg + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
				var29 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + this.aoLightValueXNeg) / 4.0F;
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
			}
			else
			{
				var30 = this.aoLightValueXNeg;
				var29 = this.aoLightValueXNeg;
				var28 = this.aoLightValueXNeg;
				var27 = this.aoLightValueXNeg;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var20;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var17 ? r : 1.0F) * 0.6F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var17 ? g : 1.0F) * 0.6F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var17 ? b : 1.0F) * 0.6F;
			if (this.blockMetadata != 3)
			{
				this.colorRedTopLeft *= var27;
				this.colorGreenTopLeft *= var27;
				this.colorBlueTopLeft *= var27;
				this.colorRedBottomLeft *= var28;
				this.colorGreenBottomLeft *= var28;
				this.colorBlueBottomLeft *= var28;
				this.colorRedBottomRight *= var29;
				this.colorGreenBottomRight *= var29;
				this.colorBlueBottomRight *= var29;
				this.colorRedTopRight *= var30;
				this.colorGreenTopRight *= var30;
				this.colorBlueTopRight *= var30;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var17 || this.blockTexture == TextureUtils.iconGrassSide ? r : 1.0F) * 0.75F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var17 || this.blockTexture == TextureUtils.iconGrassSide ? g : 1.0F) * 0.75F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var17 || this.blockTexture == TextureUtils.iconGrassSide ? b : 1.0F) * 0.75F;
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 4);
			}
			var31 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderWestFace(par1Block, posX, posY, posZ, var31);

			if (fancyGrass && var31 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= r;
				this.colorRedBottomLeft *= r;
				this.colorRedBottomRight *= r;
				this.colorRedTopRight *= r;
				this.colorGreenTopLeft *= g;
				this.colorGreenBottomLeft *= g;
				this.colorGreenBottomRight *= g;
				this.colorGreenTopRight *= g;
				this.colorBlueTopLeft *= b;
				this.colorBlueBottomLeft *= b;
				this.colorBlueBottomRight *= b;
				this.colorBlueTopRight *= b;
				this.renderWestFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		// Renders east face
		if (this.renderAllFaces || this.blockMetadata == 1 || par1Block.shouldSideBeRendered(this.blockAccess, posX + 1, posY, posZ, 5))
		{
			int var41 = 5;
			if (this.aoType > 0)
			{
				if (par1Block.maxX >= 1.0D)
				{
					++posX;
				}

				this.aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
				this.aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ - 1);
				this.aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ + 1);
				this.aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
				this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
				this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1);
				this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1);
				this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);

				if (!this.aoGrassXYZPNC && !this.aoGrassXYZPCN)
				{
					this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
					this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
				}
				else
				{
					this.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ - 1);
					this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ - 1);
				}

				if (!this.aoGrassXYZPNC && !this.aoGrassXYZPCP)
				{
					this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
					this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
				}
				else
				{
					this.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ + 1);
					this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ + 1);
				}

				if (!this.aoGrassXYZPPC && !this.aoGrassXYZPCN)
				{
					this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
					this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
				}
				else
				{
					this.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ - 1);
					this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ - 1);
				}

				if (!this.aoGrassXYZPPC && !this.aoGrassXYZPCP)
				{
					this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
					this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
				}
				else
				{
					this.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ + 1);
					this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ + 1);
				}

				if (par1Block.maxX >= 1.0D)
				{
					--posX;
				}

				var27 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + this.aoLightValueXPos + this.aoLightValueScratchXZPP) / 4.0F;
				var30 = (this.aoLightValueXPos + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
				var29 = (this.aoLightValueScratchXZPN + this.aoLightValueXPos + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
				var28 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + this.aoLightValueXPos) / 4.0F;
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var23);
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var23);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var23);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var23);
			}
			else
			{
				var30 = this.aoLightValueXPos;
				var29 = this.aoLightValueXPos;
				var28 = this.aoLightValueXPos;
				var27 = this.aoLightValueXPos;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var23;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var18 ? r : 1.0F) * 0.6F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var18 ? g : 1.0F) * 0.6F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var18 ? b : 1.0F) * 0.6F;
			if (this.blockMetadata != 1)
			{
				this.colorRedTopLeft *= var27;
				this.colorGreenTopLeft *= var27;
				this.colorBlueTopLeft *= var27;
				this.colorRedBottomLeft *= var28;
				this.colorGreenBottomLeft *= var28;
				this.colorBlueBottomLeft *= var28;
				this.colorRedBottomRight *= var29;
				this.colorGreenBottomRight *= var29;
				this.colorBlueBottomRight *= var29;
				this.colorRedTopRight *= var30;
				this.colorGreenTopRight *= var30;
				this.colorBlueTopRight *= var30;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var18 || this.blockTexture == TextureUtils.iconGrassSide ? r : 1.0F) * 0.75F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var18 || this.blockTexture == TextureUtils.iconGrassSide ? g : 1.0F) * 0.75F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var18 || this.blockTexture == TextureUtils.iconGrassSide ? b : 1.0F) * 0.75F;
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 5);
			}
			var31 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderEastFace(par1Block, posX, posY, posZ, var31);

			if (fancyGrass && var31 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= r;
				this.colorRedBottomLeft *= r;
				this.colorRedBottomRight *= r;
				this.colorRedTopRight *= r;
				this.colorGreenTopLeft *= g;
				this.colorGreenBottomLeft *= g;
				this.colorGreenBottomRight *= g;
				this.colorGreenTopRight *= g;
				this.colorBlueTopLeft *= b;
				this.colorBlueBottomLeft *= b;
				this.colorBlueBottomRight *= b;
				this.colorBlueTopRight *= b;
				this.renderEastFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
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

	/**
	 * Renders a standard cube block at the given coordinates, with a given color ratio.  Args: block, x, y, z, r, g, b
	 */
	public boolean renderBlockInclinedWithColorMultiplier(Block par1Block, int posX, int posY, int posZ, float r, float g, float b)
	{
		this.enableAO = false;
		Tessellator var8 = Tessellator.instance;
		boolean var9 = false;
		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var11 * r;
		float var15 = var11 * g;
		float var16 = var11 * b;
		float var17 = var10;
		float var18 = var12;
		float var19 = var13;
		float var20 = var10;
		float var21 = var12;
		float var22 = var13;
		float var23 = var10;
		float var24 = var12;
		float var25 = var13;

		if (par1Block != Block.grass && this.blockTexture != TextureUtils.iconGrassSide)
		{
			var17 = var10 * r;
			var18 = var12 * r;
			var19 = var13 * r;
			var20 = var10 * g;
			var21 = var12 * g;
			var22 = var13 * g;
			var23 = var10 * b;
			var24 = var12 * b;
			var25 = var13 * b;
		}

		int var26 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ);

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY - 1, posZ, 0))
		{
			var8.setBrightness(par1Block.minY <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ) : var26);
			var8.setColorOpaque_F(var17, var20, var23);
			this.renderBottomFace(par1Block, posX, posY, posZ, par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, 0));
			var9 = true;
		}

		Icon var27;

		if (this.renderAllFaces || this.blockMetadata == 2 || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY, posZ - 1, 2))
		{
			int var41 = 2;
			if (this.blockMetadata == 2)
			{
				var8.setBrightness(par1Block.minZ <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ) : var26);
				var8.setColorOpaque_F(0.9F * r, 0.9F * g, 0.9F * b);
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 2);
			}
			else
			{
				var8.setBrightness(par1Block.minZ <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1) : var26);
				var8.setColorOpaque_F(var18, var21, var24);
			}
			var27 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderNorthFace(par1Block, posX, posY, posZ, var27);

			if (fancyGrass && var27 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var18 * r, var21 * g, var24 * b);
				this.renderNorthFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var9 = true;
		}

		if (this.renderAllFaces || this.blockMetadata == 0 || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY, posZ + 1, 3))
		{
			int var41 = 3;
			if (this.blockMetadata == 0)
			{
				var8.setBrightness(par1Block.maxZ >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ) : var26);
				var8.setColorOpaque_F(0.9F * r, 0.9F * g, 0.9F * b);
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 3);
			}
			else
			{
				var8.setBrightness(par1Block.maxZ >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1) : var26);
				var8.setColorOpaque_F(var18, var21, var24);
			}
			var27 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderSouthFace(par1Block, posX, posY, posZ, var27);

			if (fancyGrass && var27 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var18 * r, var21 * g, var24 * b);
				this.renderSouthFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var9 = true;
		}

		if (this.renderAllFaces || this.blockMetadata == 3 || par1Block.shouldSideBeRendered(this.blockAccess, posX - 1, posY, posZ, 4))
		{
			int var41 = 4;
			if (this.blockMetadata == 3)
			{
				var8.setBrightness(par1Block.minX <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ) : var26);
				var8.setColorOpaque_F(0.75F * r, 0.75F * g, 0.75F * b);
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 4);
			}
			else
			{
				var8.setBrightness(par1Block.minX <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ) : var26);
				var8.setColorOpaque_F(var19, var22, var25);
			}
			var27 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderWestFace(par1Block, posX, posY, posZ, var27);

			if (fancyGrass && var27 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var19 * r, var22 * g, var25 * b);
				this.renderWestFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var9 = true;
		}

		if (this.renderAllFaces || this.blockMetadata == 1 || par1Block.shouldSideBeRendered(this.blockAccess, posX + 1, posY, posZ, 5))
		{
			int var28 = 5;
			if (this.blockMetadata == 1)
			{
				var8.setBrightness(par1Block.maxX >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ) : var26);
				var8.setColorOpaque_F(0.75F * r, 0.75F * g, 0.75F * b);
				var28 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 5);
			}
			else
			{
				var8.setBrightness(par1Block.maxX >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ) : var26);
				var8.setColorOpaque_F(var19, var22, var25);
			}
			var27 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var28);
			this.renderEastFace(par1Block, posX, posY, posZ, var27);

			if (fancyGrass && var27 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var19 * r, var22 * g, var25 * b);
				this.renderEastFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var9 = true;
		}

		return var9;
	}

	/**
	 * Renders the given texture to the bottom face of the block. Args: block, x, y, z, texture
	 */
	public void renderBottomFace(Block par1Block, double posX, double posY, double posZ, Icon texID)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			texID = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateBottom == 0)
		{
			texID = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)posX, (int)posY, (int)posZ, 0, texID);
		}

		boolean var6 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateBottom == 0)
		{
			NaturalProperties var7 = NaturalTextures.getNaturalProperties(texID);

			if (var7 != null)
			{
				int var8 = Config.getRandom((int)posX, (int)posY, (int)posZ, 0);

				if (var7.rotation > 1)
				{
					this.uvRotateBottom = var8 & 3;
				}

				if (var7.rotation == 2)
				{
					this.uvRotateBottom = this.uvRotateBottom / 2 * 3;
				}

				if (var7.flip)
				{
					this.flipTexture = (var8 & 4) != 0;
				}

				var6 = true;
			}
		}

		double var12 = texID.getInterpolatedU(this.renderMinX * 16.0D);
		double var14 = texID.getInterpolatedU(this.renderMaxX * 16.0D);
		double var16 = texID.getInterpolatedV(this.renderMinZ * 16.0D);
		double var18 = texID.getInterpolatedV(this.renderMaxZ * 16.0D);

		if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
		{
			var12 = texID.getMinU();
			var14 = texID.getMaxU();
		}

		if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
		{
			var16 = texID.getMinV();
			var18 = texID.getMaxV();
		}

		double var19;

		if (this.flipTexture)
		{
			var19 = var12;
			var12 = var14;
			var14 = var19;
		}

		var19 = var14;

		double var20 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		double var28;

		if (this.uvRotateBottom == 2)
		{
			var12 = texID.getInterpolatedU(this.renderMinZ * 16.0D);
			var16 = texID.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			var14 = texID.getInterpolatedU(this.renderMaxZ * 16.0D);
			var18 = texID.getInterpolatedV(16.0D - this.renderMinX * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var24 = var16;
			var26 = var18;
			var20 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var24;
		}
		else if (this.uvRotateBottom == 1)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var22;
			var24 = var18;
			var26 = var16;
		}
		else if (this.uvRotateBottom == 3)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			var16 = texID.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			var18 = texID.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		if (var6)
		{
			this.uvRotateBottom = 0;
			this.flipTexture = false;
		}

		double minX = posX + this.renderMinX;
		double maxX = posX + this.renderMaxX;
		double minY = posY + this.renderMinY;
		double minZ = posZ + this.renderMinZ;
		double maxZ = posZ + this.renderMaxZ;

		if (this.enableAO)
		{
			var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
			var9.setBrightness(this.brightnessTopLeft);
			var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
			var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
			var9.setBrightness(this.brightnessBottomLeft);
			var9.addVertexWithUV(minX, minY, minZ, var12, var16);
			var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
			var9.setBrightness(this.brightnessBottomRight);
			var9.addVertexWithUV(maxX, minY, minZ, var20, var24);
			var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
			var9.setBrightness(this.brightnessTopRight);
			var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
		}
		else
		{
			var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
			var9.addVertexWithUV(minX, minY, minZ, var12, var16);
			var9.addVertexWithUV(maxX, minY, minZ, var20, var24);
			var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
		}
	}

	/**
	 * Renders the given texture to the top face of the block. Args: block, x, y, z, texture
	 */
	public void renderTopFace(Block par1Block, double posX, double posY, double posZ, Icon texID)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			texID = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateTop == 0)
		{
			texID = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)posX, (int)posY, (int)posZ, 1, texID);
		}

		boolean var6 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateTop == 0)
		{
			NaturalProperties var7 = NaturalTextures.getNaturalProperties(texID);

			if (var7 != null)
			{
				int var8 = Config.getRandom((int)posX, (int)posY, (int)posZ, 1);

				if (var7.rotation > 1)
				{
					this.uvRotateTop = var8 & 3;
				}

				if (var7.rotation == 2)
				{
					this.uvRotateTop = this.uvRotateTop / 2 * 3;
				}

				if (var7.flip)
				{
					this.flipTexture = (var8 & 4) != 0;
				}

				var6 = true;
			}
		}

		double var12 = texID.getInterpolatedU(this.renderMinX * 16.0D);
		double var14 = texID.getInterpolatedU(this.renderMaxX * 16.0D);
		double var16 = texID.getInterpolatedV(this.renderMinZ * 16.0D);
		double var18 = texID.getInterpolatedV(this.renderMaxZ * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var12;
			var12 = var14;
			var14 = var19;
		}

		if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
		{
			var12 = texID.getMinU();
			var14 = texID.getMaxU();
		}

		if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
		{
			var16 = texID.getMinV();
			var18 = texID.getMaxV();
		}

		double var20 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		double var28;

		if (this.uvRotateTop == 1)
		{
			var12 = texID.getInterpolatedU(this.renderMinZ * 16.0D);
			var16 = texID.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
			var14 = texID.getInterpolatedU(this.renderMaxZ * 16.0D);
			var18 = texID.getInterpolatedV(16.0D - this.renderMinX * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var24 = var16;
			var26 = var18;
			var20 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var24;
		}
		else if (this.uvRotateTop == 2)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var22;
			var24 = var18;
			var26 = var16;
		}
		else if (this.uvRotateTop == 3)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			var16 = texID.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			var18 = texID.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		if (var6)
		{
			this.uvRotateTop = 0;
			this.flipTexture = false;
		}

		double minX = posX + this.renderMinX;
		double maxX = posX + this.renderMaxX;
		double maxY = posY + this.renderMaxY;
		double minZ = posZ + this.renderMinZ;
		double maxZ = posZ + this.renderMaxZ;

		if (this.enableAO)
		{
			if (this.blockMetadata == 1)
			{
				// blockMetadata = 1 west
						var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				var9.setBrightness(this.brightnessTopLeft);
				var9.addVertexWithUV(maxX, maxY, minZ, var14, var18);
				var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
				var9.setBrightness(this.brightnessBottomLeft);
				var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
				var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
				var9.setBrightness(this.brightnessBottomRight);
				var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
				var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				var9.setBrightness(this.brightnessTopRight);
				var9.addVertexWithUV(maxX, maxY, maxZ, var22, var26);
			}
			else if (this.blockMetadata == 2)
			{
				// blockMetadata = 2 south
						var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
						var9.setBrightness(this.brightnessTopLeft);
						var9.addVertexWithUV(minX, maxY, minZ, var14, var18);
						var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
						var9.setBrightness(this.brightnessBottomLeft);
						var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
						var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
						var9.setBrightness(this.brightnessBottomRight);
						var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
						var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
						var9.setBrightness(this.brightnessTopRight);
						var9.addVertexWithUV(maxX, maxY, minZ, var22, var26);
			}
			else if (this.blockMetadata == 3)
			{
				// blockMetadata = 3 east
				var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				var9.setBrightness(this.brightnessTopLeft);
				var9.addVertexWithUV(minX, maxY, maxZ, var14, var18);
				var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
				var9.setBrightness(this.brightnessBottomLeft);
				var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
				var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
				var9.setBrightness(this.brightnessBottomRight);
				var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
				var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				var9.setBrightness(this.brightnessTopRight);
				var9.addVertexWithUV(minX, maxY, minZ, var22, var26);
			}
			else
			{
				// blockMetadata = 0 north
				var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
				var9.setBrightness(this.brightnessTopLeft);
				var9.addVertexWithUV(maxX, maxY, maxZ, var14, var18);
				var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
				var9.setBrightness(this.brightnessBottomLeft);
				var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
				var9.setBrightness(this.brightnessBottomRight);
				var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
				var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
				var9.setBrightness(this.brightnessTopRight);
				var9.addVertexWithUV(minX, maxY, maxZ, var22, var26);
			}
		}
		else
		{
			if (this.blockMetadata == 1)
			{
				// blockMetadata = 1 west
				var9.addVertexWithUV(maxX, maxY, minZ, var14, var18);
				var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
				var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
				var9.addVertexWithUV(maxX, maxY, maxZ, var22, var26);
			}
			else if (this.blockMetadata == 2)
			{
				// blockMetadata = 2 south
				var9.addVertexWithUV(minX, maxY, minZ, var14, var18);
				var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
				var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
				var9.addVertexWithUV(maxX, maxY, minZ, var22, var26);
			}
			else if (this.blockMetadata == 3)
			{
				// blockMetadata = 3 east
				var9.addVertexWithUV(minX, maxY, maxZ, var14, var18);
				var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
				var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
				var9.addVertexWithUV(minX, maxY, minZ, var22, var26);
			}
			else
			{
				// blockMetadata = 0 north
				var9.addVertexWithUV(maxX, maxY, maxZ, var14, var18);
				var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
				var9.addVertexWithUV(minX, maxY, maxZ, var22, var26);
			}
		}
	}

	/**
	 * Renders the given texture to the north (z-negative) face of the block.  Args: block, x, y, z, texture
	 */
	public void renderNorthFace(Block par1Block, double posX, double posY, double posZ, Icon texID)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			texID = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateNorth == 0)
		{
			texID = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)posX, (int)posY, (int)posZ, 2, texID);
		}

		boolean var6 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateNorth == 0)
		{
			NaturalProperties var7 = NaturalTextures.getNaturalProperties(texID);

			if (var7 != null)
			{
				int var8 = Config.getRandom((int)posX, (int)posY, (int)posZ, 2);

				if (var7.rotation > 1)
				{
					this.uvRotateNorth = var8 & 3;
				}

				if (var7.rotation == 2)
				{
					this.uvRotateNorth = this.uvRotateNorth / 2 * 3;
				}

				if (var7.flip)
				{
					this.flipTexture = (var8 & 4) != 0;
				}

				var6 = true;
			}
		}

		double var12 = texID.getInterpolatedU(this.renderMinX * 16.0D);
		double var14 = texID.getInterpolatedU(this.renderMaxX * 16.0D);
		double var16 = texID.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double var18 = texID.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var12;
			var12 = var14;
			var14 = var19;
		}

		if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
		{
			var12 = texID.getMinU();
			var14 = texID.getMaxU();
		}

		if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
		{
			var16 = texID.getMinV();
			var18 = texID.getMaxV();
		}

		double var20 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		double var28;

		if (this.uvRotateNorth == 2)
		{
			var12 = texID.getInterpolatedU(this.renderMinY * 16.0D);
			var16 = texID.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(this.renderMaxY * 16.0D);
			var18 = texID.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var24 = var16;
			var26 = var18;
			var20 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var24;
		}
		else if (this.uvRotateNorth == 1)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMaxX * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMinX * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var22;
			var24 = var18;
			var26 = var16;
		}
		else if (this.uvRotateNorth == 3)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMaxY * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMinY * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		if (var6)
		{
			this.uvRotateNorth = 0;
			this.flipTexture = false;
		}

		double minX = posX + this.renderMinX;
		double maxX = posX + this.renderMaxX;
		double minY = posY + this.renderMinY;
		double maxY = posY + this.renderMaxY;
		double minZ = posZ + this.renderMinZ;
		double maxZ = posZ + this.renderMaxZ;

		if (par1Block.blockID >= this.baseBlockInclinedCorner && par1Block.blockID <= this.baseBlockInclinedCornerTo)
		{
			if (this.enableAO)
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
							var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else
				{
					// blockMetadata = 0 north
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
			}
			else
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else
				{
					// blockMetadata = 0 north
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
			}
		}
		else
		{
			if (this.enableAO)
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
							var9.setBrightness(this.brightnessTopLeft);
							var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
							var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
							var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
							var9.setBrightness(this.brightnessBottomRight);
							var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
							var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
							var9.setBrightness(this.brightnessTopRight);
							var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else
				{
					// blockMetadata = 0 north
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
			}
			else
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
				else
				{
					// blockMetadata = 0 north
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, minZ, var14, var18);
				}
			}
		}
	}

	/**
	 * Renders the given texture to the south (z-positive) face of the block.  Args: block, x, y, z, texture
	 */
	public void renderSouthFace(Block par1Block, double posX, double posY, double posZ, Icon texID)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			texID = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateSouth == 0)
		{
			texID = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)posX, (int)posY, (int)posZ, 3, texID);
		}

		boolean var6 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateSouth == 0)
		{
			NaturalProperties var7 = NaturalTextures.getNaturalProperties(texID);

			if (var7 != null)
			{
				int var8 = Config.getRandom((int)posX, (int)posY, (int)posZ, 3);

				if (var7.rotation > 1)
				{
					this.uvRotateSouth = var8 & 3;
				}

				if (var7.rotation == 2)
				{
					this.uvRotateSouth = this.uvRotateSouth / 2 * 3;
				}

				if (var7.flip)
				{
					this.flipTexture = (var8 & 4) != 0;
				}

				var6 = true;
			}
		}

		double var12 = texID.getInterpolatedU(this.renderMinX * 16.0D);
		double var14 = texID.getInterpolatedU(this.renderMaxX * 16.0D);
		double var16 = texID.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double var18 = texID.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var12;
			var12 = var14;
			var14 = var19;
		}

		if (this.renderMinX < 0.0D || this.renderMaxX > 1.0D)
		{
			var12 = texID.getMinU();
			var14 = texID.getMaxU();
		}

		if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
		{
			var16 = texID.getMinV();
			var18 = texID.getMaxV();
		}

		double var20 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		double var28;

		if (this.uvRotateSouth == 1)
		{
			var12 = texID.getInterpolatedU(this.renderMinY * 16.0D);
			var16 = texID.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(this.renderMaxY * 16.0D);
			var18 = texID.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var24 = var16;
			var26 = var18;
			var20 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var24;
		}
		else if (this.uvRotateSouth == 2)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMaxX * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var22;
			var24 = var18;
			var26 = var16;
		}
		else if (this.uvRotateSouth == 3)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMaxY * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMinY * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		if (var6)
		{
			this.uvRotateSouth = 0;
			this.flipTexture = false;
		}

		double minX = posX + this.renderMinX;
		double maxX = posX + this.renderMaxX;
		double minY = posY + this.renderMinY;
		double maxY = posY + this.renderMaxY;
		double minZ = posZ + this.renderMinZ;
		double maxZ = posZ + this.renderMaxZ;

		if (par1Block.blockID >= this.baseBlockInclinedCorner && par1Block.blockID <= this.baseBlockInclinedCornerTo)
		{
			if (this.enableAO)
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
							var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				}
				else
				{
					// blockMetadata = 0 north
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
				}
			}
			else
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				}
				else
				{
					// blockMetadata = 0 north
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
				}
			}
		}
		else
		{
			if (this.enableAO)
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
							var9.setBrightness(this.brightnessTopLeft);
							var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
							var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
							var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
							var9.setBrightness(this.brightnessBottomLeft);
							var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
							var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
							var9.setBrightness(this.brightnessBottomRight);
							var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
				}
				else
				{
					// blockMetadata = 0 north
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				}
			}
			else
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
				}
				else
				{
					// blockMetadata = 0 north
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, maxZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				}
			}
		}
	}

	/**
	 * Renders the given texture to the west (x-negative) face of the block.  Args: block, x, y, z, texture
	 */
	public void renderWestFace(Block par1Block, double posX, double posY, double posZ, Icon texID)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			texID = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateWest == 0)
		{
			texID = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)posX, (int)posY, (int)posZ, 4, texID);
		}

		boolean var6 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateWest == 0)
		{
			NaturalProperties var7 = NaturalTextures.getNaturalProperties(texID);

			if (var7 != null)
			{
				int var8 = Config.getRandom((int)posX, (int)posY, (int)posZ, 4);

				if (var7.rotation > 1)
				{
					this.uvRotateWest = var8 & 3;
				}

				if (var7.rotation == 2)
				{
					this.uvRotateWest = this.uvRotateWest / 2 * 3;
				}

				if (var7.flip)
				{
					this.flipTexture = (var8 & 4) != 0;
				}

				var6 = true;
			}
		}

		double var12 = texID.getInterpolatedU(this.renderMinZ * 16.0D);
		double var14 = texID.getInterpolatedU(this.renderMaxZ * 16.0D);
		double var16 = texID.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double var18 = texID.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var12;
			var12 = var14;
			var14 = var19;
		}

		if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
		{
			var12 = texID.getMinU();
			var14 = texID.getMaxU();
		}

		if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
		{
			var16 = texID.getMinV();
			var18 = texID.getMaxV();
		}

		double var20 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		double var28;

		if (this.uvRotateWest == 1)
		{
			var12 = texID.getInterpolatedU(this.renderMinY * 16.0D);
			var16 = texID.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
			var14 = texID.getInterpolatedU(this.renderMaxY * 16.0D);
			var18 = texID.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var24 = var16;
			var26 = var18;
			var20 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var24;
		}
		else if (this.uvRotateWest == 2)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMinZ * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMaxZ * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var22;
			var24 = var18;
			var26 = var16;
		}
		else if (this.uvRotateWest == 3)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMaxY * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMinY * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		if (var6)
		{
			this.uvRotateWest = 0;
			this.flipTexture = false;
		}

		double minX = posX + this.renderMinX;
		double maxX = posX + this.renderMaxX;
		double minY = posY + this.renderMinY;
		double maxY = posY + this.renderMaxY;
		double minZ = posZ + this.renderMinZ;
		double maxZ = posZ + this.renderMaxZ;

		if (par1Block.blockID >= this.baseBlockInclinedCorner && par1Block.blockID <= this.baseBlockInclinedCornerTo)
		{
			if (this.enableAO)
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else
				{
					// blockMetadata = 0 north
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
			}
			else
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else
				{
					// blockMetadata = 0 north
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
			}
		}
		else
		{
			if (this.enableAO)
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
							var9.setBrightness(this.brightnessTopLeft);
							var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
							var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
							var9.setBrightness(this.brightnessBottomLeft);
							var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
							var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
							var9.setBrightness(this.brightnessBottomRight);
							var9.addVertexWithUV(minX, minY, minZ, var22, var26);
							var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
							var9.setBrightness(this.brightnessTopRight);
							var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else
				{
					// blockMetadata = 0 north
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
			}
			else
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.addVertexWithUV(maxX, maxY, maxZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
				else
				{
					// blockMetadata = 0 north
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, minZ, var12, var16);
					var9.addVertexWithUV(minX, minY, minZ, var22, var26);
					var9.addVertexWithUV(minX, minY, maxZ, var14, var18);
				}
			}
		}
	}

	/**
	 * Renders the given texture to the east (x-positive) face of the block.  Args: block, x, y, z, texture
	 */
	public void renderEastFace(Block par1Block, double posX, double posY, double posZ, Icon texID)
	{
		Tessellator var9 = Tessellator.instance;

		if (this.hasOverrideBlockTexture())
		{
			texID = this.overrideBlockTexture;
		}

		if (Config.isConnectedTextures() && this.overrideBlockTexture == null && this.uvRotateEast == 0)
		{
			texID = ConnectedTextures.getConnectedTexture(this.blockAccess, par1Block, (int)posX, (int)posY, (int)posZ, 5, texID);
		}

		boolean var6 = false;

		if (Config.isNaturalTextures() && this.overrideBlockTexture == null && this.uvRotateEast == 0)
		{
			NaturalProperties var7 = NaturalTextures.getNaturalProperties(texID);

			if (var7 != null)
			{
				int var8 = Config.getRandom((int)posX, (int)posY, (int)posZ, 5);

				if (var7.rotation > 1)
				{
					this.uvRotateEast = var8 & 3;
				}

				if (var7.rotation == 2)
				{
					this.uvRotateEast = this.uvRotateEast / 2 * 3;
				}

				if (var7.flip)
				{
					this.flipTexture = (var8 & 4) != 0;
				}

				var6 = true;
			}
		}

		double var12 = texID.getInterpolatedU(this.renderMinZ * 16.0D);
		double var14 = texID.getInterpolatedU(this.renderMaxZ * 16.0D);
		double var16 = texID.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
		double var18 = texID.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
		double var19;

		if (this.flipTexture)
		{
			var19 = var12;
			var12 = var14;
			var14 = var19;
		}

		if (this.renderMinZ < 0.0D || this.renderMaxZ > 1.0D)
		{
			var12 = texID.getMinU();
			var14 = texID.getMaxU();
		}

		if (this.renderMinY < 0.0D || this.renderMaxY > 1.0D)
		{
			var16 = texID.getMinV();
			var18 = texID.getMaxV();
		}

		double var20 = var14;
		double var22 = var12;
		double var24 = var16;
		double var26 = var18;
		double var28;

		if (this.uvRotateEast == 2)
		{
			var12 = texID.getInterpolatedU(this.renderMinY * 16.0D);
			var16 = texID.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
			var14 = texID.getInterpolatedU(this.renderMaxY * 16.0D);
			var18 = texID.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var24 = var16;
			var26 = var18;
			var20 = var12;
			var22 = var14;
			var16 = var18;
			var18 = var24;
		}
		else if (this.uvRotateEast == 1)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMaxZ * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMinZ * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var12 = var14;
			var14 = var22;
			var24 = var18;
			var26 = var16;
		}
		else if (this.uvRotateEast == 3)
		{
			var12 = texID.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
			var14 = texID.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
			var16 = texID.getInterpolatedV(this.renderMaxY * 16.0D);
			var18 = texID.getInterpolatedV(this.renderMinY * 16.0D);

			if (this.flipTexture)
			{
				var28 = var12;
				var12 = var14;
				var14 = var28;
			}
			var20 = var14;
			var22 = var12;
			var24 = var16;
			var26 = var18;
		}

		if (var6)
		{
			this.uvRotateEast = 0;
			this.flipTexture = false;
		}

		double minX = posX + this.renderMinX;
		double maxX = posX + this.renderMaxX;
		double minY = posY + this.renderMinY;
		double maxY = posY + this.renderMaxY;
		double minZ = posZ + this.renderMinZ;
		double maxZ = posZ + this.renderMaxZ;

		if (par1Block.blockID >= this.baseBlockInclinedCorner && par1Block.blockID <= this.baseBlockInclinedCornerTo)
		{
			if (this.enableAO)
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				}
				else
				{
					// blockMetadata = 0 north
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
				}
			}
			else
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				}
				else
				{
					// blockMetadata = 0 north
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
				}
			}
		}
		else
		{
			if (this.enableAO)
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
							var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
							var9.setBrightness(this.brightnessTopLeft);
							var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
							var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
							var9.setBrightness(this.brightnessBottomLeft);
							var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
							var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
							var9.setBrightness(this.brightnessBottomRight);
							var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
							var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
							var9.setBrightness(this.brightnessTopRight);
							var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
					var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
					var9.setBrightness(this.brightnessTopRight);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
				}
				else
				{
					// blockMetadata = 0 north
					var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
					var9.setBrightness(this.brightnessTopLeft);
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
					var9.setBrightness(this.brightnessBottomLeft);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
					var9.setBrightness(this.brightnessBottomRight);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				}
			}
			else
			{
				if (this.blockMetadata == 1)
				{
					// blockMetadata = 1 west
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.addVertexWithUV(minX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(minX, maxY, maxZ, var12, var16);
				}
				else if (this.blockMetadata == 2)
				{
					// blockMetadata = 2 south
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
				}
				else if (this.blockMetadata == 3)
				{
					// blockMetadata = 3 east
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, maxZ, var12, var16);
				}
				else
				{
					// blockMetadata = 0 north
					var9.addVertexWithUV(maxX, minY, maxZ, var22, var26);
					var9.addVertexWithUV(maxX, minY, minZ, var14, var18);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
					var9.addVertexWithUV(maxX, maxY, minZ, var20, var24);
				}
			}
		}
	}

	/**
	 * Renders a standard cube block at the given coordinates, with ambient occlusion.  Args: block, x, y, z, r, g, b
	 */
	public boolean renderBlockInclinedCornerWithAmbientOcclusion(Block par1Block, int posX, int posY, int posZ, float r, float g, float b)
	{
		this.enableAO = true;
		boolean var8 = false;
		float var9 = this.lightValueOwn;
		float var10 = this.lightValueOwn;
		float var11 = this.lightValueOwn;
		float var12 = this.lightValueOwn;
		boolean var14 = true;
		boolean var15 = true;
		boolean var16 = true;
		boolean var17 = true;
		boolean var18 = true;
		this.lightValueOwn = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ);
		this.aoLightValueXNeg = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ);
		this.aoLightValueYNeg = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
		this.aoLightValueZNeg = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ - 1);
		this.aoLightValueXPos = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ);
		this.aoLightValueYPos = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
		this.aoLightValueZPos = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ + 1);
		int var19 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ);
		int var20 = var19;
		int var21 = var19;
		int var22 = var19;
		int var23 = var19;
		int var24 = var19;
		int var25 = var19;

		//int blockMetadata = this.blockAccess.getBlockMetadata(posX, posY, posZ);
		//Icon blockTexture = par1Block.getBlockTextureFromSideAndMetadata(4, blockMetadata);
		//blockMetadata = blockMetadata & 3;

		// Bottom
		if (par1Block.minY <= 0.0D)
		{
			var21 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
		}

		// Top
		if (par1Block.maxY >= 1.0D)
		{
			var24 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);
		}

		// West
		if (par1Block.minX <= 0.0D && this.blockMetadata != 3 && this.blockMetadata != 2)
		{
			var20 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ);
		}

		// East
		if (par1Block.maxX >= 1.0D && this.blockMetadata != 1 && this.blockMetadata != 0)
		{
			var23 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ);
		}

		// North
		if (par1Block.minZ <= 0.0D && this.blockMetadata != 2 && this.blockMetadata != 1)
		{
			var22 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1);
		}

		// South
		if (par1Block.maxZ >= 1.0D && this.blockMetadata != 0 && this.blockMetadata != 3)
		{
			var25 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1);
		}

		Tessellator var26 = Tessellator.instance;
		var26.setBrightness(983055);
		this.aoGrassXYZPPC = Block.canBlockGrass[this.blockAccess.getBlockId(posX + 1, posY + 1, posZ)];
		this.aoGrassXYZPNC = Block.canBlockGrass[this.blockAccess.getBlockId(posX + 1, posY - 1, posZ)];
		this.aoGrassXYZPCP = Block.canBlockGrass[this.blockAccess.getBlockId(posX + 1, posY, posZ + 1)];
		this.aoGrassXYZPCN = Block.canBlockGrass[this.blockAccess.getBlockId(posX + 1, posY, posZ - 1)];
		this.aoGrassXYZNPC = Block.canBlockGrass[this.blockAccess.getBlockId(posX - 1, posY + 1, posZ)];
		this.aoGrassXYZNNC = Block.canBlockGrass[this.blockAccess.getBlockId(posX - 1, posY - 1, posZ)];
		this.aoGrassXYZNCN = Block.canBlockGrass[this.blockAccess.getBlockId(posX - 1, posY, posZ - 1)];
		this.aoGrassXYZNCP = Block.canBlockGrass[this.blockAccess.getBlockId(posX - 1, posY, posZ + 1)];
		this.aoGrassXYZCPP = Block.canBlockGrass[this.blockAccess.getBlockId(posX, posY + 1, posZ + 1)];
		this.aoGrassXYZCPN = Block.canBlockGrass[this.blockAccess.getBlockId(posX, posY + 1, posZ - 1)];
		this.aoGrassXYZCNP = Block.canBlockGrass[this.blockAccess.getBlockId(posX, posY - 1, posZ + 1)];
		this.aoGrassXYZCNN = Block.canBlockGrass[this.blockAccess.getBlockId(posX, posY - 1, posZ - 1)];

		if (this.blockTexture == TextureUtils.iconGrassSide)
		{
			var18 = false;
			var17 = false;
			var16 = false;
			var15 = false;
			var14 = false;
		}

		if (this.hasOverrideBlockTexture())
		{
			var18 = false;
			var17 = false;
			var16 = false;
			var15 = false;
			var14 = false;
		}

		float var27;
		float var29;
		float var28;
		float var30;

		// Renders bottom face
		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY - 1, posZ, 0))
		{
			if (this.aoType > 0)
			{
				if (par1Block.minY <= 0.0D)
				{
					--posY;
				}

				this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ);
				this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1);
				this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1);
				this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ);
				this.aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ);
				this.aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ - 1);
				this.aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ + 1);
				this.aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ);

				if (!this.aoGrassXYZCNN && !this.aoGrassXYZNNC)
				{
					this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
					this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
				}
				else
				{
					this.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ - 1);
					this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ - 1);
				}

				if (!this.aoGrassXYZCNP && !this.aoGrassXYZNNC)
				{
					this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
					this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
				}
				else
				{
					this.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ + 1);
					this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ + 1);
				}

				if (!this.aoGrassXYZCNN && !this.aoGrassXYZPNC)
				{
					this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
					this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
				}
				else
				{
					this.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ - 1);
					this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ - 1);
				}

				if (!this.aoGrassXYZCNP && !this.aoGrassXYZPNC)
				{
					this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
					this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
				}
				else
				{
					this.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ + 1);
					this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ + 1);
				}

				if (par1Block.minY <= 0.0D)
				{
					++posY;
				}

				var27 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + this.aoLightValueYNeg) / 4.0F;
				var30 = (this.aoLightValueScratchYZNP + this.aoLightValueYNeg + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
				var29 = (this.aoLightValueYNeg + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
				var28 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + this.aoLightValueYNeg + this.aoLightValueScratchYZNN) / 4.0F;
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var21);
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var21);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var21);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var21);
			}
			else
			{
				var30 = this.aoLightValueYNeg;
				var29 = this.aoLightValueYNeg;
				var28 = this.aoLightValueYNeg;
				var27 = this.aoLightValueYNeg;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = this.aoBrightnessXYNN;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var14 ? r : 1.0F) * 0.5F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var14 ? g : 1.0F) * 0.5F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var14 ? b : 1.0F) * 0.5F;
			this.colorRedTopLeft *= var27;
			this.colorGreenTopLeft *= var27;
			this.colorBlueTopLeft *= var27;
			this.colorRedBottomLeft *= var28;
			this.colorGreenBottomLeft *= var28;
			this.colorBlueBottomLeft *= var28;
			this.colorRedBottomRight *= var29;
			this.colorGreenBottomRight *= var29;
			this.colorBlueBottomRight *= var29;
			this.colorRedTopRight *= var30;
			this.colorGreenTopRight *= var30;
			this.colorBlueTopRight *= var30;
			this.renderBottomFace(par1Block, posX, posY, posZ, par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, 0));
			var8 = true;
		}

		Icon var31;

		// Renders north face
		if (this.renderAllFaces || this.blockMetadata == 2 || this.blockMetadata == 1 || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY, posZ - 1, 2))
		{
			int var41 = 2;
			if (this.aoType > 0)
			{
				if (par1Block.minZ <= 0.0D)
				{
					--posZ;
				}

				this.aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ);
				this.aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
				this.aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
				this.aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ);
				this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ);
				this.aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
				this.aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);
				this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ);

				if (!this.aoGrassXYZNCN && !this.aoGrassXYZCNN)
				{
					this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
					this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
				}
				else
				{
					this.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY - 1, posZ);
					this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY - 1, posZ);
				}

				if (!this.aoGrassXYZNCN && !this.aoGrassXYZCPN)
				{
					this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
					this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
				}
				else
				{
					this.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY + 1, posZ);
					this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY + 1, posZ);
				}

				if (!this.aoGrassXYZPCN && !this.aoGrassXYZCNN)
				{
					this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
					this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
				}
				else
				{
					this.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY - 1, posZ);
					this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY - 1, posZ);
				}

				if (!this.aoGrassXYZPCN && !this.aoGrassXYZCPN)
				{
					this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
					this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
				}
				else
				{
					this.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY + 1, posZ);
					this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY + 1, posZ);
				}

				if (par1Block.minZ <= 0.0D)
				{
					++posZ;
				}

				var27 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + this.aoLightValueZNeg + this.aoLightValueScratchYZPN) / 4.0F;
				var28 = (this.aoLightValueZNeg + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
				var29 = (this.aoLightValueScratchYZNN + this.aoLightValueZNeg + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
				var30 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + this.aoLightValueZNeg) / 4.0F;
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var22);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var22);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var22);
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var22);
			}
			else
			{
				var30 = this.aoLightValueZNeg;
				var29 = this.aoLightValueZNeg;
				var28 = this.aoLightValueZNeg;
				var27 = this.aoLightValueZNeg;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var22;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var15 ? r : 1.0F) * 0.8F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var15 ? g : 1.0F) * 0.8F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var15 ? b : 1.0F) * 0.8F;
			if (this.blockMetadata != 2 && this.blockMetadata != 1)
			{
				this.colorRedTopLeft *= var27;
				this.colorGreenTopLeft *= var27;
				this.colorBlueTopLeft *= var27;
				this.colorRedBottomLeft *= var28;
				this.colorGreenBottomLeft *= var28;
				this.colorBlueBottomLeft *= var28;
				this.colorRedBottomRight *= var29;
				this.colorGreenBottomRight *= var29;
				this.colorBlueBottomRight *= var29;
				this.colorRedTopRight *= var30;
				this.colorGreenTopRight *= var30;
				this.colorBlueTopRight *= var30;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var15 || this.blockTexture == TextureUtils.iconGrassSide ? r : 1.0F) * 0.85F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var15 || this.blockTexture == TextureUtils.iconGrassSide ? g : 1.0F) * 0.85F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var15 || this.blockTexture == TextureUtils.iconGrassSide ? b : 1.0F) * 0.85F;
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 2);
			}

			var31 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			//var31 = par1Block.getBlockTextureFromSideAndMetadata(var41, this.blockMetadata);
			this.renderNorthFace(par1Block, posX, posY, posZ, var31);

			if (fancyGrass && var31 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= r;
				this.colorRedBottomLeft *= r;
				this.colorRedBottomRight *= r;
				this.colorRedTopRight *= r;
				this.colorGreenTopLeft *= g;
				this.colorGreenBottomLeft *= g;
				this.colorGreenBottomRight *= g;
				this.colorGreenTopRight *= g;
				this.colorBlueTopLeft *= b;
				this.colorBlueBottomLeft *= b;
				this.colorBlueBottomRight *= b;
				this.colorBlueTopRight *= b;
				this.renderNorthFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		// Renders south face
		if (this.renderAllFaces || this.blockMetadata == 0 || this.blockMetadata == 3 || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY, posZ + 1, 3))
		{
			int var41 = 3;
			if (this.aoType > 0)
			{
				if (par1Block.maxZ >= 1.0D)
				{
					++posZ;
				}

				this.aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY, posZ);
				this.aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY, posZ);
				this.aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
				this.aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
				this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ);
				this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ);
				this.aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
				this.aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);

				if (!this.aoGrassXYZNCP && !this.aoGrassXYZCNP)
				{
					this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
					this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
				}
				else
				{
					this.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY - 1, posZ);
					this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY - 1, posZ);
				}

				if (!this.aoGrassXYZNCP && !this.aoGrassXYZCPP)
				{
					this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
					this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
				}
				else
				{
					this.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX - 1, posY + 1, posZ);
					this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY + 1, posZ);
				}

				if (!this.aoGrassXYZPCP && !this.aoGrassXYZCNP)
				{
					this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
					this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
				}
				else
				{
					this.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY - 1, posZ);
					this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY - 1, posZ);
				}

				if (!this.aoGrassXYZPCP && !this.aoGrassXYZCPP)
				{
					this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
					this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
				}
				else
				{
					this.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX + 1, posY + 1, posZ);
					this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY + 1, posZ);
				}

				if (par1Block.maxZ >= 1.0D)
				{
					--posZ;
				}

				var27 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + this.aoLightValueZPos + this.aoLightValueScratchYZPP) / 4.0F;
				var30 = (this.aoLightValueZPos + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
				var29 = (this.aoLightValueScratchYZNP + this.aoLightValueZPos + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
				var28 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + this.aoLightValueZPos) / 4.0F;
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var25);
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var25);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var25);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var25);
			}
			else
			{
				var30 = this.aoLightValueZPos;
				var29 = this.aoLightValueZPos;
				var28 = this.aoLightValueZPos;
				var27 = this.aoLightValueZPos;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var25;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var16 ? r : 1.0F) * 0.8F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var16 ? g : 1.0F) * 0.8F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var16 ? b : 1.0F) * 0.8F;
			if (this.blockMetadata != 0 && this.blockMetadata != 3)
			{
				this.colorRedTopLeft *= var27;
				this.colorGreenTopLeft *= var27;
				this.colorBlueTopLeft *= var27;
				this.colorRedBottomLeft *= var28;
				this.colorGreenBottomLeft *= var28;
				this.colorBlueBottomLeft *= var28;
				this.colorRedBottomRight *= var29;
				this.colorGreenBottomRight *= var29;
				this.colorBlueBottomRight *= var29;
				this.colorRedTopRight *= var30;
				this.colorGreenTopRight *= var30;
				this.colorBlueTopRight *= var30;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var16 || this.blockTexture == TextureUtils.iconGrassSide ? r : 1.0F) * 0.85F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var16 || this.blockTexture == TextureUtils.iconGrassSide ? g : 1.0F) * 0.85F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var16 || this.blockTexture == TextureUtils.iconGrassSide ? b : 1.0F) * 0.85F;
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 3);
			}
			var31 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderSouthFace(par1Block, posX, posY, posZ, var31);

			if (fancyGrass && var31 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= r;
				this.colorRedBottomLeft *= r;
				this.colorRedBottomRight *= r;
				this.colorRedTopRight *= r;
				this.colorGreenTopLeft *= g;
				this.colorGreenBottomLeft *= g;
				this.colorGreenBottomRight *= g;
				this.colorGreenTopRight *= g;
				this.colorBlueTopLeft *= b;
				this.colorBlueBottomLeft *= b;
				this.colorBlueBottomRight *= b;
				this.colorBlueTopRight *= b;
				this.renderSouthFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		// Renders west face
		if (this.renderAllFaces || this.blockMetadata == 3 || this.blockMetadata == 2 || par1Block.shouldSideBeRendered(this.blockAccess, posX - 1, posY, posZ, 4))
		{
			int var41 = 4;
			if (this.aoType > 0)
			{
				if (par1Block.minX <= 0.0D)
				{
					--posX;
				}

				this.aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
				this.aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ - 1);
				this.aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ + 1);
				this.aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
				this.aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
				this.aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1);
				this.aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1);
				this.aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);

				if (!this.aoGrassXYZNCN && !this.aoGrassXYZNNC)
				{
					this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
					this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
				}
				else
				{
					this.aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ - 1);
					this.aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ - 1);
				}

				if (!this.aoGrassXYZNCP && !this.aoGrassXYZNNC)
				{
					this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
					this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
				}
				else
				{
					this.aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ + 1);
					this.aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ + 1);
				}

				if (!this.aoGrassXYZNCN && !this.aoGrassXYZNPC)
				{
					this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
					this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
				}
				else
				{
					this.aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ - 1);
					this.aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ - 1);
				}

				if (!this.aoGrassXYZNCP && !this.aoGrassXYZNPC)
				{
					this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
					this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
				}
				else
				{
					this.aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ + 1);
					this.aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ + 1);
				}

				if (par1Block.minX <= 0.0D)
				{
					++posX;
				}

				var30 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + this.aoLightValueXNeg + this.aoLightValueScratchXZNP) / 4.0F;
				var27 = (this.aoLightValueXNeg + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
				var28 = (this.aoLightValueScratchXZNN + this.aoLightValueXNeg + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
				var29 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + this.aoLightValueXNeg) / 4.0F;
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
			}
			else
			{
				var30 = this.aoLightValueXNeg;
				var29 = this.aoLightValueXNeg;
				var28 = this.aoLightValueXNeg;
				var27 = this.aoLightValueXNeg;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var20;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var17 ? r : 1.0F) * 0.6F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var17 ? g : 1.0F) * 0.6F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var17 ? b : 1.0F) * 0.6F;
			if (this.blockMetadata != 3 && this.blockMetadata != 2)
			{
				this.colorRedTopLeft *= var27;
				this.colorGreenTopLeft *= var27;
				this.colorBlueTopLeft *= var27;
				this.colorRedBottomLeft *= var28;
				this.colorGreenBottomLeft *= var28;
				this.colorBlueBottomLeft *= var28;
				this.colorRedBottomRight *= var29;
				this.colorGreenBottomRight *= var29;
				this.colorBlueBottomRight *= var29;
				this.colorRedTopRight *= var30;
				this.colorGreenTopRight *= var30;
				this.colorBlueTopRight *= var30;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var17 || this.blockTexture == TextureUtils.iconGrassSide ? r : 1.0F) * 0.75F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var17 || this.blockTexture == TextureUtils.iconGrassSide ? g : 1.0F) * 0.75F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var17 || this.blockTexture == TextureUtils.iconGrassSide ? b : 1.0F) * 0.75F;
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 4);
			}
			var31 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderWestFace(par1Block, posX, posY, posZ, var31);

			if (fancyGrass && var31 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= r;
				this.colorRedBottomLeft *= r;
				this.colorRedBottomRight *= r;
				this.colorRedTopRight *= r;
				this.colorGreenTopLeft *= g;
				this.colorGreenBottomLeft *= g;
				this.colorGreenBottomRight *= g;
				this.colorGreenTopRight *= g;
				this.colorBlueTopLeft *= b;
				this.colorBlueBottomLeft *= b;
				this.colorBlueBottomRight *= b;
				this.colorBlueTopRight *= b;
				this.renderWestFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		// Renders east face
		if (this.renderAllFaces || this.blockMetadata == 1 || this.blockMetadata == 0 || par1Block.shouldSideBeRendered(this.blockAccess, posX + 1, posY, posZ, 5))
		{
			int var41 = 5;
			if (this.aoType > 0)
			{
				if (par1Block.maxX >= 1.0D)
				{
					++posX;
				}

				this.aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ);
				this.aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ - 1);
				this.aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY, posZ + 1);
				this.aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ);
				this.aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ);
				this.aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1);
				this.aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1);
				this.aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ);

				if (!this.aoGrassXYZPNC && !this.aoGrassXYZPCN)
				{
					this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
					this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
				}
				else
				{
					this.aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ - 1);
					this.aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ - 1);
				}

				if (!this.aoGrassXYZPNC && !this.aoGrassXYZPCP)
				{
					this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
					this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
				}
				else
				{
					this.aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY - 1, posZ + 1);
					this.aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ + 1);
				}

				if (!this.aoGrassXYZPPC && !this.aoGrassXYZPCN)
				{
					this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
					this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
				}
				else
				{
					this.aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ - 1);
					this.aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ - 1);
				}

				if (!this.aoGrassXYZPPC && !this.aoGrassXYZPCP)
				{
					this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
					this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
				}
				else
				{
					this.aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(this.blockAccess, posX, posY + 1, posZ + 1);
					this.aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY + 1, posZ + 1);
				}

				if (par1Block.maxX >= 1.0D)
				{
					--posX;
				}

				var27 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + this.aoLightValueXPos + this.aoLightValueScratchXZPP) / 4.0F;
				var30 = (this.aoLightValueXPos + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
				var29 = (this.aoLightValueScratchXZPN + this.aoLightValueXPos + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
				var28 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + this.aoLightValueXPos) / 4.0F;
				this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var23);
				this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var23);
				this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var23);
				this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var23);
			}
			else
			{
				var30 = this.aoLightValueXPos;
				var29 = this.aoLightValueXPos;
				var28 = this.aoLightValueXPos;
				var27 = this.aoLightValueXPos;
				this.brightnessTopLeft = this.brightnessBottomLeft = this.brightnessBottomRight = this.brightnessTopRight = var23;
			}

			this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var18 ? r : 1.0F) * 0.6F;
			this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var18 ? g : 1.0F) * 0.6F;
			this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var18 ? b : 1.0F) * 0.6F;
			if (this.blockMetadata != 1 && this.blockMetadata != 0)
			{
				this.colorRedTopLeft *= var27;
				this.colorGreenTopLeft *= var27;
				this.colorBlueTopLeft *= var27;
				this.colorRedBottomLeft *= var28;
				this.colorGreenBottomLeft *= var28;
				this.colorBlueBottomLeft *= var28;
				this.colorRedBottomRight *= var29;
				this.colorGreenBottomRight *= var29;
				this.colorBlueBottomRight *= var29;
				this.colorRedTopRight *= var30;
				this.colorGreenTopRight *= var30;
				this.colorBlueTopRight *= var30;
			}
			else
			{
				this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = (var18 || this.blockTexture == TextureUtils.iconGrassSide ? r : 1.0F) * 0.75F;
				this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = (var18 || this.blockTexture == TextureUtils.iconGrassSide ? g : 1.0F) * 0.75F;
				this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = (var18 || this.blockTexture == TextureUtils.iconGrassSide ? b : 1.0F) * 0.75F;
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 5);
			}
			var31 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderEastFace(par1Block, posX, posY, posZ, var31);

			if (fancyGrass && var31 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				this.colorRedTopLeft *= r;
				this.colorRedBottomLeft *= r;
				this.colorRedBottomRight *= r;
				this.colorRedTopRight *= r;
				this.colorGreenTopLeft *= g;
				this.colorGreenBottomLeft *= g;
				this.colorGreenBottomRight *= g;
				this.colorGreenTopRight *= g;
				this.colorBlueTopLeft *= b;
				this.colorBlueBottomLeft *= b;
				this.colorBlueBottomRight *= b;
				this.colorBlueTopRight *= b;
				this.renderEastFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var8 = true;
		}

		this.enableAO = false;
		return var8;
	}

	/**
	 * Renders a standard cube block at the given coordinates, with a given color ratio.  Args: block, x, y, z, r, g, b
	 */
	public boolean renderBlockInclinedCornerWithColorMultiplier(Block par1Block, int posX, int posY, int posZ, float r, float g, float b)
	{
		this.enableAO = false;
		Tessellator var8 = Tessellator.instance;
		boolean var9 = false;
		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var11 * r;
		float var15 = var11 * g;
		float var16 = var11 * b;
		float var17 = var10;
		float var18 = var12;
		float var19 = var13;
		float var20 = var10;
		float var21 = var12;
		float var22 = var13;
		float var23 = var10;
		float var24 = var12;
		float var25 = var13;

		if (par1Block != Block.grass && this.blockTexture != TextureUtils.iconGrassSide)
		{
			var17 = var10 * r;
			var18 = var12 * r;
			var19 = var13 * r;
			var20 = var10 * g;
			var21 = var12 * g;
			var22 = var13 * g;
			var23 = var10 * b;
			var24 = var12 * b;
			var25 = var13 * b;
		}

		int var26 = par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ);

		if (this.renderAllFaces || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY - 1, posZ, 0))
		{
			var8.setBrightness(par1Block.minY <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY - 1, posZ) : var26);
			var8.setColorOpaque_F(var17, var20, var23);
			this.renderBottomFace(par1Block, posX, posY, posZ, par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, 0));
			var9 = true;
		}

		Icon var27;

		if (this.renderAllFaces || this.blockMetadata == 2 || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY, posZ - 1, 2))
		{
			int var41 = 2;
			if (this.blockMetadata == 2)
			{
				var8.setBrightness(par1Block.minZ <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ) : var26);
				var8.setColorOpaque_F(0.9F * r, 0.9F * g, 0.9F * b);
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 2);
			}
			else
			{
				var8.setBrightness(par1Block.minZ <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ - 1) : var26);
				var8.setColorOpaque_F(var18, var21, var24);
			}
			var27 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderNorthFace(par1Block, posX, posY, posZ, var27);

			if (fancyGrass && var27 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var18 * r, var21 * g, var24 * b);
				this.renderNorthFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var9 = true;
		}

		if (this.renderAllFaces || this.blockMetadata == 0 || par1Block.shouldSideBeRendered(this.blockAccess, posX, posY, posZ + 1, 3))
		{
			int var41 = 3;
			if (this.blockMetadata == 0)
			{
				var8.setBrightness(par1Block.maxZ >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ) : var26);
				var8.setColorOpaque_F(0.9F * r, 0.9F * g, 0.9F * b);
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 3);
			}
			else
			{
				var8.setBrightness(par1Block.maxZ >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ + 1) : var26);
				var8.setColorOpaque_F(var18, var21, var24);
			}
			var27 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderSouthFace(par1Block, posX, posY, posZ, var27);

			if (fancyGrass && var27 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var18 * r, var21 * g, var24 * b);
				this.renderSouthFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var9 = true;
		}

		if (this.renderAllFaces || this.blockMetadata == 3 || par1Block.shouldSideBeRendered(this.blockAccess, posX - 1, posY, posZ, 4))
		{
			int var41 = 4;
			if (this.blockMetadata == 3)
			{
				var8.setBrightness(par1Block.minX <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ) : var26);
				var8.setColorOpaque_F(0.75F * r, 0.75F * g, 0.75F * b);
				var41 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 4);
			}
			else
			{
				var8.setBrightness(par1Block.minX <= 0.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX - 1, posY, posZ) : var26);
				var8.setColorOpaque_F(var19, var22, var25);
			}
			var27 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var41);
			this.renderWestFace(par1Block, posX, posY, posZ, var27);

			if (fancyGrass && var27 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var19 * r, var22 * g, var25 * b);
				this.renderWestFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var9 = true;
		}

		if (this.renderAllFaces || this.blockMetadata == 1 || par1Block.shouldSideBeRendered(this.blockAccess, posX + 1, posY, posZ, 5))
		{
			int var28 = 5;
			if (this.blockMetadata == 1)
			{
				var8.setBrightness(par1Block.maxX >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX, posY, posZ) : var26);
				var8.setColorOpaque_F(0.75F * r, 0.75F * g, 0.75F * b);
				var28 = (this.blockTexture == TextureUtils.iconGrassSide ? 1 : 5);
			}
			else
			{
				var8.setBrightness(par1Block.maxX >= 1.0D ? par1Block.getMixedBrightnessForBlock(this.blockAccess, posX + 1, posY, posZ) : var26);
				var8.setColorOpaque_F(var19, var22, var25);
			}
			var27 = par1Block.getBlockTexture(this.blockAccess, posX, posY, posZ, var28);
			this.renderEastFace(par1Block, posX, posY, posZ, var27);

			if (fancyGrass && var27 == TextureUtils.iconGrassSide && !this.hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var19 * r, var22 * g, var25 * b);
				this.renderEastFace(par1Block, posX, posY, posZ, BlockGrass.getIconSideOverlay());
			}

			var9 = true;
		}

		return var9;
	}

	/**
	 * Is called to render the image of a block on an inventory, as a held item, or as a an item on the ground
	 */
	public void renderBlockAsItem(Block par1Block, int metaData, float par3Color)
	{
		Tessellator var4 = Tessellator.instance;
		Icon var5 = par1Block.getIcon(4, metaData);
		boolean isGrassBlock = par1Block.blockID == Block.grass.blockID || var5 == TextureUtils.iconGrassSide;
		this.blockTexture = var5;
		this.blockMetadata = metaData & 3;
		int var6;
		float var7;
		float var8;
		float var9;

		if (this.useInventoryTint)
		{
			var6 = par1Block.getRenderColor(metaData);

			if (isGrassBlock)
			{
				var6 = 16777215;
			}

			var7 = (var6 >> 16 & 255) / 255.0F;
			var8 = (var6 >> 8 & 255) / 255.0F;
			var9 = (var6 & 255) / 255.0F;
			GL11.glColor4f(var7 * par3Color, var8 * par3Color, var9 * par3Color, 1.0F);
		}

		var6 = par1Block.getRenderType();
		this.setRenderBoundsFromBlock(par1Block);
		int var14;

		if (var6 == MCE_Items.inclinedModelID)
		{
			//par1Block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			par1Block.setBlockBoundsForItemRender();
			this.setRenderBoundsFromBlock(par1Block);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(0, metaData));
			var4.draw();

			if (isGrassBlock && this.useInventoryTint)
			{
				var14 = Block.grass.getRenderColor(0);
				var8 = (var14 >> 16 & 255) / 255.0F;
				var9 = (var14 >> 8 & 255) / 255.0F;
				float var10 = (var14 & 255) / 255.0F;
				GL11.glColor4f(var8 * par3Color, var9 * par3Color, var10 * par3Color, 1.0F);
			}
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(isGrassBlock ? 1 : 5, metaData));
			var4.draw();
			if (isGrassBlock && this.useInventoryTint)
			{
				GL11.glColor4f(par3Color, par3Color, par3Color, 1.0F);
			}

			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(2, metaData));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(3, metaData));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(4, metaData));
			var4.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
		else if (var6 == MCE_Items.inclinedCornerModelID)
		{
			//par1Block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			par1Block.setBlockBoundsForItemRender();
			this.setRenderBoundsFromBlock(par1Block);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(0, metaData));
			var4.draw();

			if (isGrassBlock && this.useInventoryTint)
			{
				var14 = Block.grass.getRenderColor(0);
				var8 = (var14 >> 16 & 255) / 255.0F;
				var9 = (var14 >> 8 & 255) / 255.0F;
				float var10 = (var14 & 255) / 255.0F;
				GL11.glColor4f(var8 * par3Color, var9 * par3Color, var10 * par3Color, 1.0F);
			}

			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(isGrassBlock ? 1 : 5, metaData));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(isGrassBlock ? 1 : 2, metaData));
			var4.draw();

			if (isGrassBlock && this.useInventoryTint)
			{
				GL11.glColor4f(par3Color, par3Color, par3Color, 1.0F);
			}

			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(3, metaData));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(4, metaData));
			var4.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
		else
		{
			par1Block.setBlockBoundsForItemRender();
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			this.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(0, metaData));
			var4.draw();

			if (isGrassBlock && this.useInventoryTint)
			{
				var14 = par1Block.getRenderColor(metaData);
				var8 = (var14 >> 16 & 255) / 255.0F;
				var9 = (var14 >> 8 & 255) / 255.0F;
				float var10 = (var14 & 255) / 255.0F;
				GL11.glColor4f(var8 * par3Color, var9 * par3Color, var10 * par3Color, 1.0F);
			}

			var4.startDrawingQuads();
			var4.setNormal(0.0F, 1.0F, 0.0F);
			this.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(1, metaData));
			var4.draw();

			if (isGrassBlock && this.useInventoryTint)
			{
				GL11.glColor4f(par3Color, par3Color, par3Color, 1.0F);
			}

			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			this.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(2, metaData));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			this.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(3, metaData));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			this.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(4, metaData));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			this.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(5, metaData));
			var4.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
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

	private float getAmbientOcclusionLightValue(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		Block var5 = Block.blocksList[par1IBlockAccess.getBlockId(par2, par3, par4)];

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

	private Icon fixAoSideGrassTexture(Icon par1Icon, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
	{
		if (par1Icon == TextureUtils.iconGrassSide || par1Icon == TextureUtils.iconMycelSide)
		{
			par1Icon = Config.getSideGrassTexture(this.blockAccess, par2, par3, par4, par5, par1Icon);

			if (par1Icon == TextureUtils.iconGrassTop)
			{
				this.colorRedTopLeft *= par6;
				this.colorRedBottomLeft *= par6;
				this.colorRedBottomRight *= par6;
				this.colorRedTopRight *= par6;
				this.colorGreenTopLeft *= par7;
				this.colorGreenBottomLeft *= par7;
				this.colorGreenBottomRight *= par7;
				this.colorGreenTopRight *= par7;
				this.colorBlueTopLeft *= par8;
				this.colorBlueBottomLeft *= par8;
				this.colorBlueBottomRight *= par8;
				this.colorBlueTopRight *= par8;
			}
		}

		if (par1Icon == TextureUtils.iconSnowSide)
		{
			par1Icon = Config.getSideSnowGrassTexture(this.blockAccess, par2, par3, par4, par5);
		}

		return par1Icon;
	}
}
