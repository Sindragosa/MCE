package sedridor.mce.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntitySpellFX_MCE extends EntityFX
{
	private int particleTextureIndex = 144;
	private RenderEngine renderer;

	public EntitySpellFX_MCE(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		this.renderer = Minecraft.getMinecraft().renderEngine;
		this.motionY *= 0.2D;

		if (par8 == 0.0D && par12 == 0.0D)
		{
			this.motionX *= 0.1D;
			this.motionZ *= 0.1D;
		}

		this.particleScale *= 0.75F;
		this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
		this.noClip = false;
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

		this.setParticleTextureIndex(this.particleTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
		this.motionY += 0.004D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		if (this.posY == this.prevPosY)
		{
			this.motionX *= 1.1D;
			this.motionZ *= 1.1D;
		}

		this.motionX *= 0.96D;
		this.motionY *= 0.96D;
		this.motionZ *= 0.96D;

		if (this.onGround)
		{
			this.motionX *= 0.7D;
			this.motionZ *= 0.7D;
		}
	}

	public void func_70589_b(int par1)
	{
		this.particleTextureIndex = par1;
	}
}
