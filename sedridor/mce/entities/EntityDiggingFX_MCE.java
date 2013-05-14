package sedridor.mce.entities;

import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityDiggingFX_MCE extends EntityFX
{
	private Block blockInstance;
	private int side;

	private String blockTexture;
	private int blockMetadata;

	public EntityDiggingFX_MCE(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Block par14Block, int par15, int par16, RenderEngine par17RenderEngine)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		this.blockInstance = par14Block;
		//this.setParticleTextureIndex(par14Block.getBlockTextureFromSideAndMetadata(0, par16));
		this.setParticleIcon(par17RenderEngine, par14Block.getIcon(0, par16));
		this.particleGravity = par14Block.blockParticleGravity;
		this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
		this.particleScale /= 2.0F;
		this.side = par15;
		//this.blockTexture = par14Block.getTextureFile();
		//this.blockMetadata = par16;
	}

	public EntityDiggingFX_MCE func_70596_a(int par1, int par2, int par3)
	{
		if (this.blockInstance == Block.grass && this.side != 1)
		{
			return this;
		}
		else
		{
			int var4 = this.blockInstance.colorMultiplier(this.worldObj, par1, par2, par3);
			this.particleRed *= (var4 >> 16 & 255) / 255.0F;
			this.particleGreen *= (var4 >> 8 & 255) / 255.0F;
			this.particleBlue *= (var4 & 255) / 255.0F;
			return this;
		}
	}

	/**
	 * Creates a new EntityDiggingFX with the block render color applied to the base particle color
	 */
	public EntityDiggingFX_MCE applyRenderColor(int par1)
	{
		if (this.blockInstance == Block.grass)
		{
			return this;
		}
		else
		{
			int j = this.blockInstance.getRenderColor(par1);
			this.particleRed *= (j >> 16 & 255) / 255.0F;
			this.particleGreen *= (j >> 8 & 255) / 255.0F;
			this.particleBlue *= (j & 255) / 255.0F;
			return this;
		}
	}

	@Override
	public int getFXLayer()
	{
		return 1;
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		if (this.blockTexture != null)
		{
			par1Tessellator.draw();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(this.blockTexture));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			par1Tessellator.startDrawingQuads();
			par1Tessellator.setBrightness(this.getBrightnessForRender(par2));
		}
		//float var8 = ((float)(this.getParticleTextureIndex() % 16) + this.particleTextureJitterX / 4.0F) / 16.0F;
		//float var9 = var8 + 0.015609375F;
		//float var10 = ((float)(this.getParticleTextureIndex() / 16) + this.particleTextureJitterY / 4.0F) / 16.0F;
		//float var11 = var10 + 0.015609375F;
		float var12 = 0.1F * this.particleScale;
		float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
		float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
		float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
		float var16 = 1.0F;
		par1Tessellator.setColorOpaque_F(var16 * this.particleRed, var16 * this.particleGreen, var16 * this.particleBlue);
		//par1Tessellator.addVertexWithUV((double)(var13 - par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 - par5 * var12 - par7 * var12), (double)var8, (double)var11);
		//par1Tessellator.addVertexWithUV((double)(var13 - par3 * var12 + par6 * var12), (double)(var14 + par4 * var12), (double)(var15 - par5 * var12 + par7 * var12), (double)var8, (double)var10);
		//par1Tessellator.addVertexWithUV((double)(var13 + par3 * var12 + par6 * var12), (double)(var14 + par4 * var12), (double)(var15 + par5 * var12 + par7 * var12), (double)var9, (double)var10);
		//par1Tessellator.addVertexWithUV((double)(var13 + par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 + par5 * var12 - par7 * var12), (double)var9, (double)var11);
	}
}
