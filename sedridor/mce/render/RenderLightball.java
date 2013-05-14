package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sedridor.mce.MCEnhancements;
import sedridor.mce.entities.*;

@SideOnly(Side.CLIENT)
public class RenderLightball extends Render
{
	private float scale = 0.8F;

	public RenderLightball()
	{
	}

	public RenderLightball(float par1)
	{
		this.scale = par1;
	}

	public void doRenderLightball(EntityLightball par1EntityLightball, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float var10 = this.scale;
		GL11.glScalef(var10 / 1.0F, var10 / 1.0F, var10 / 1.0F);
		Icon var11 = MCEnhancements.itemIcons.getIconFromDamage(0);
		this.loadTexture("/gui/items.png");
		Tessellator var12 = Tessellator.instance;
		int var5 = 15728880;
		int var6 = var5 % 65536;
		int var7 = var5 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
		float var13 = var11.getMinU();
		float var14 = var11.getMaxU();
		float var15 = var11.getMinV();
		float var16 = var11.getMaxV();
		float var17 = 1.0F;
		float var18 = 0.5F;
		float var19 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		var12.startDrawingQuads();
		var12.setNormal(0.0F, 1.0F, 0.0F);
		var12.addVertexWithUV(0.0F - var18, 0.0F - var19, 0.0D, var13, var16);
		var12.addVertexWithUV(var17 - var18, 0.0F - var19, 0.0D, var14, var16);
		var12.addVertexWithUV(var17 - var18, 1.0F - var19, 0.0D, var14, var15);
		var12.addVertexWithUV(0.0F - var18, 1.0F - var19, 0.0D, var13, var15);
		var12.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRenderLightball((EntityLightball)par1Entity, par2, par4, par6, par8, par9);
	}
}
