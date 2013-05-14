package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PlaneRenderer extends ModelRenderer
{
	private PositionTextureVertex[] corners;
	private TexturedQuad[] faces;

	/** The X offset into the texture used for displaying this model */
	private int textureOffsetX;

	/** The Y offset into the texture used for displaying this model */
	private int textureOffsetY;
	public float rotationPointX_PR;
	public float rotationPointY_PR;
	public float rotationPointZ_PR;
	public float rotateAngleX_PR;
	public float rotateAngleY_PR;
	public float rotateAngleZ_PR;
	private boolean compiled;

	/** The GL display list rendered by the Tessellator for this model */
	private int displayList;

	public PlaneRenderer(ModelBase par1ModelBase, int par2, int par3)
	{
		super(par1ModelBase, par2, par3);
		this.textureWidth = 64.0F;
		this.textureHeight = 32.0F;
		this.compiled = false;
		this.displayList = 0;
		this.mirror = false;
		this.showModel = true;
		this.isHidden = false;
		this.textureOffsetX = par2;
		this.textureOffsetY = par3;
		par1ModelBase.boxList.add(this);
	}

	/**
	 * Creates a textured back plane. Args: originX, originY, originZ, width, height.
	 */
	public void addBackPlane(float par1, float par2, float par3, int par4, int par5)
	{
		this.addBackPlane(par1, par2, par3, par4, par5, 0, 0.0F);
	}

	/**
	 * Creates a textured side plane. Args: originX, originY, originZ, height, depth.
	 */
	public void addSidePlane(float par1, float par2, float par3, int par4, int par5)
	{
		this.addSidePlane(par1, par2, par3, 0, par4, par5, 0.0F);
	}

	/**
	 * Creates a textured top plane. Args: originX, originY, originZ, width, depth.
	 */
	public void addTopPlane(float par1, float par2, float par3, int par4, int par5)
	{
		this.addTopPlane(par1, par2, par3, par4, 0, par5, 0.0F);
	}

	/**
	 * Creates a textured back plane. Args: originX, originY, originZ, width, height, depth, scaleFactor.
	 */
	public void addBackPlane(float par1, float par2, float par3, int par4, int par5, int par6, float par7)
	{
		this.rotationPointX_PR = par1;
		this.rotationPointY_PR = par2;
		this.rotationPointZ_PR = par3;
		this.rotateAngleX_PR = par1 + par4;
		this.rotateAngleY_PR = par2 + par5;
		this.rotateAngleZ_PR = par3 + par6;
		this.corners = new PositionTextureVertex[8];
		this.faces = new TexturedQuad[1];
		float var8 = par1 + par4;
		float var9 = par2 + par5;
		float var10 = par3 + par6;
		par1 -= par7;
		par2 -= par7;
		par3 -= par7;
		var8 += par7;
		var9 += par7;
		var10 += par7;

		if (this.mirror)
		{
			float var11 = var8;
			var8 = par1;
			par1 = var11;
		}

		PositionTextureVertex var19 = new PositionTextureVertex(par1, par2, par3, 0.0F, 0.0F);
		PositionTextureVertex var12 = new PositionTextureVertex(var8, par2, par3, 0.0F, 8.0F);
		PositionTextureVertex var13 = new PositionTextureVertex(var8, var9, par3, 8.0F, 8.0F);
		PositionTextureVertex var14 = new PositionTextureVertex(par1, var9, par3, 8.0F, 0.0F);
		PositionTextureVertex var15 = new PositionTextureVertex(par1, par2, var10, 0.0F, 0.0F);
		PositionTextureVertex var16 = new PositionTextureVertex(var8, par2, var10, 0.0F, 8.0F);
		PositionTextureVertex var17 = new PositionTextureVertex(var8, var9, var10, 8.0F, 8.0F);
		PositionTextureVertex var18 = new PositionTextureVertex(par1, var9, var10, 8.0F, 0.0F);
		this.corners[0] = var19;
		this.corners[1] = var12;
		this.corners[2] = var13;
		this.corners[3] = var14;
		this.corners[4] = var15;
		this.corners[5] = var16;
		this.corners[6] = var17;
		this.corners[7] = var18;
		this.faces[0] = new TexturedQuad(new PositionTextureVertex[] {var12, var19, var14, var13}, this.textureOffsetX, this.textureOffsetY, this.textureOffsetX + par4, this.textureOffsetY + par5, this.textureWidth, this.textureHeight);

		if (this.mirror)
		{
			this.faces[0].flipFace();
		}
	}

	/**
	 * Creates a textured side plane. Args: originX, originY, originZ, width, height, depth, scaleFactor.
	 */
	public void addSidePlane(float par1, float par2, float par3, int par4, int par5, int par6, float par7)
	{
		this.rotationPointX_PR = par1;
		this.rotationPointY_PR = par2;
		this.rotationPointZ_PR = par3;
		this.rotateAngleX_PR = par1 + par4;
		this.rotateAngleY_PR = par2 + par5;
		this.rotateAngleZ_PR = par3 + par6;
		this.corners = new PositionTextureVertex[8];
		this.faces = new TexturedQuad[1];
		float var8 = par1 + par4;
		float var9 = par2 + par5;
		float var10 = par3 + par6;
		par1 -= par7;
		par2 -= par7;
		par3 -= par7;
		var8 += par7;
		var9 += par7;
		var10 += par7;

		if (this.mirror)
		{
			float var11 = var8;
			var8 = par1;
			par1 = var11;
		}

		PositionTextureVertex var19 = new PositionTextureVertex(par1, par2, par3, 0.0F, 0.0F);
		PositionTextureVertex var12 = new PositionTextureVertex(var8, par2, par3, 0.0F, 8.0F);
		PositionTextureVertex var13 = new PositionTextureVertex(var8, var9, par3, 8.0F, 8.0F);
		PositionTextureVertex var14 = new PositionTextureVertex(par1, var9, par3, 8.0F, 0.0F);
		PositionTextureVertex var15 = new PositionTextureVertex(par1, par2, var10, 0.0F, 0.0F);
		PositionTextureVertex var16 = new PositionTextureVertex(var8, par2, var10, 0.0F, 8.0F);
		PositionTextureVertex var17 = new PositionTextureVertex(var8, var9, var10, 8.0F, 8.0F);
		PositionTextureVertex var18 = new PositionTextureVertex(par1, var9, var10, 8.0F, 0.0F);
		this.corners[0] = var19;
		this.corners[1] = var12;
		this.corners[2] = var13;
		this.corners[3] = var14;
		this.corners[4] = var15;
		this.corners[5] = var16;
		this.corners[6] = var17;
		this.corners[7] = var18;
		this.faces[0] = new TexturedQuad(new PositionTextureVertex[] {var16, var12, var13, var17}, this.textureOffsetX, this.textureOffsetY, this.textureOffsetX + par6, this.textureOffsetY + par5, this.textureWidth, this.textureHeight);

		if (this.mirror)
		{
			this.faces[0].flipFace();
		}
	}

	/**
	 * Creates a textured top plane. Args: originX, originY, originZ, width, height, depth, scaleFactor.
	 */
	public void addTopPlane(float par1, float par2, float par3, int par4, int par5, int par6, float par7)
	{
		this.rotationPointX_PR = par1;
		this.rotationPointY_PR = par2;
		this.rotationPointZ_PR = par3;
		this.rotateAngleX_PR = par1 + par4;
		this.rotateAngleY_PR = par2 + par5;
		this.rotateAngleZ_PR = par3 + par6;
		this.corners = new PositionTextureVertex[8];
		this.faces = new TexturedQuad[1];
		float var8 = par1 + par4;
		float var9 = par2 + par5;
		float var10 = par3 + par6;
		par1 -= par7;
		par2 -= par7;
		par3 -= par7;
		var8 += par7;
		var9 += par7;
		var10 += par7;

		if (this.mirror)
		{
			float var11 = var8;
			var8 = par1;
			par1 = var11;
		}

		PositionTextureVertex var19 = new PositionTextureVertex(par1, par2, par3, 0.0F, 0.0F);
		PositionTextureVertex var12 = new PositionTextureVertex(var8, par2, par3, 0.0F, 8.0F);
		PositionTextureVertex var13 = new PositionTextureVertex(var8, var9, par3, 8.0F, 8.0F);
		PositionTextureVertex var14 = new PositionTextureVertex(par1, var9, par3, 8.0F, 0.0F);
		PositionTextureVertex var15 = new PositionTextureVertex(par1, par2, var10, 0.0F, 0.0F);
		PositionTextureVertex var16 = new PositionTextureVertex(var8, par2, var10, 0.0F, 8.0F);
		PositionTextureVertex var17 = new PositionTextureVertex(var8, var9, var10, 8.0F, 8.0F);
		PositionTextureVertex var18 = new PositionTextureVertex(par1, var9, var10, 8.0F, 0.0F);
		this.corners[0] = var19;
		this.corners[1] = var12;
		this.corners[2] = var13;
		this.corners[3] = var14;
		this.corners[4] = var15;
		this.corners[5] = var16;
		this.corners[6] = var17;
		this.corners[7] = var18;
		this.faces[0] = new TexturedQuad(new PositionTextureVertex[] {var16, var15, var19, var12}, this.textureOffsetX, this.textureOffsetY, this.textureOffsetX + par4, this.textureOffsetY + par6, this.textureWidth, this.textureHeight);

		if (this.mirror)
		{
			this.faces[0].flipFace();
		}
	}

	@Override
	public void setRotationPoint(float par1, float par2, float par3)
	{
		this.rotationPointX = par1;
		this.rotationPointY = par2;
		this.rotationPointZ = par3;
	}

	@Override
	public void render(float par1)
	{
		if (!this.isHidden)
		{
			if (this.showModel)
			{
				if (!this.compiled)
				{
					this.compileDisplayList(par1);
				}

				if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
				{
					if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
					{
						GL11.glCallList(this.displayList);
					}
					else
					{
						GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
						GL11.glCallList(this.displayList);
						GL11.glTranslatef(-this.rotationPointX * par1, -this.rotationPointY * par1, -this.rotationPointZ * par1);
					}
				}
				else
				{
					GL11.glPushMatrix();
					GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);

					if (this.rotateAngleZ != 0.0F)
					{
						GL11.glRotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
					}

					if (this.rotateAngleY != 0.0F)
					{
						GL11.glRotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
					}

					if (this.rotateAngleX != 0.0F)
					{
						GL11.glRotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					}

					GL11.glCallList(this.displayList);
					GL11.glPopMatrix();
				}
			}
		}
	}

	@Override
	public void renderWithRotation(float par1)
	{
		if (!this.isHidden)
		{
			if (this.showModel)
			{
				if (!this.compiled)
				{
					this.compileDisplayList(par1);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);

				if (this.rotateAngleY != 0.0F)
				{
					GL11.glRotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if (this.rotateAngleX != 0.0F)
				{
					GL11.glRotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				}

				if (this.rotateAngleZ != 0.0F)
				{
					GL11.glRotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
				}

				GL11.glCallList(this.displayList);
				GL11.glPopMatrix();
			}
		}
	}

	/**
	 * Allows the changing of Angles after a box has been rendered
	 */
	 @Override
	 public void postRender(float par1)
	{
		if (!this.isHidden)
		{
			if (this.showModel)
			{
				if (!this.compiled)
				{
					this.compileDisplayList(par1);
				}

				if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
				{
					if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F)
					{
						GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
					}
				}
				else
				{
					GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);

					if (this.rotateAngleZ != 0.0F)
					{
						GL11.glRotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
					}

					if (this.rotateAngleY != 0.0F)
					{
						GL11.glRotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
					}

					if (this.rotateAngleX != 0.0F)
					{
						GL11.glRotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					}
				}
			}
		}
	}

	 private void compileDisplayList(float par1)
	 {
		 this.displayList = GLAllocation.generateDisplayLists(1);
		 GL11.glNewList(this.displayList, GL11.GL_COMPILE);
		 Tessellator var2 = Tessellator.instance;

		 for (int var3 = 0; var3 < this.faces.length; ++var3)
		 {
			 this.faces[var3].draw(var2, par1);
		 }

		 GL11.glEndList();
		 this.compiled = true;
	 }

	 public void setToModel(ModelRenderer par1ModelRenderer)
	 {
		 this.rotationPointX = par1ModelRenderer.rotationPointX;
		 this.rotationPointY = par1ModelRenderer.rotationPointY;
		 this.rotationPointZ = par1ModelRenderer.rotationPointZ;
		 this.rotateAngleX = par1ModelRenderer.rotateAngleX;
		 this.rotateAngleY = par1ModelRenderer.rotateAngleY;
		 this.rotateAngleZ = par1ModelRenderer.rotateAngleZ;
	 }
}
