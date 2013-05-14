package sedridor.mce.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntitySpell2FX_MCE extends EntityFX
{
	float field_70561_a;
	private RenderEngine renderer;

	public EntitySpell2FX_MCE(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		this(par1World, par2, par4, par6, par8, par10, par12, 1.0F);
	}

	public EntitySpell2FX_MCE(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		this.renderer = Minecraft.getMinecraft().renderEngine;
		this.motionX *= 0.1D;
		this.motionY *= 0.1D;
		this.motionZ *= 0.1D;
		this.motionX += par8 * 0.4D;
		this.motionY += par10 * 0.4D;
		this.motionZ += par12 * 0.4D;
		this.particleScale *= 0.75F;
		this.particleScale *= par14;
		this.field_70561_a = this.particleScale;
		this.particleMaxAge = (int)(6.0D / (Math.random() * 0.8D + 0.6D));
		this.particleMaxAge = (int)(this.particleMaxAge * par14);
		this.noClip = false;
		this.setParticleTextureIndex(67);
		this.onUpdate();
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (this.particleAge + par2) / this.particleMaxAge * 32.0F;

		if (var8 < 0.0F)
		{
			var8 = 0.0F;
		}

		if (var8 > 1.0F)
		{
			var8 = 1.0F;
		}

		this.particleScale = this.field_70561_a * var8;
		this.renderer.bindTexture(this.renderer.getTexture("/particles2.png"));
		int var5 = 15728880;
		int var6 = var5 % 65536;
		int var7 = var5 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
		par1Tessellator.startDrawingQuads();
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
		par1Tessellator.draw();
	}

	@Override
	public int getFXLayer()
	{
		return 3;
	}

	/**
	 * Public method to set private field particleTextureIndex.
	 */
	@Override
	public void setParticleTextureIndex(int par1)
	{
		this.particleTextureIndexX = par1 % 16;
		this.particleTextureIndexY = par1 / 16;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setDead();
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.7D;
		this.motionY *= 0.7D;
		this.motionZ *= 0.7D;
		this.motionY -= 0.02D;

		if (this.onGround)
		{
			this.motionX *= 0.7D;
			this.motionZ *= 0.7D;
		}
	}
}
