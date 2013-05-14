package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import sedridor.mce.entities.*;
import sedridor.mce.models.*;

@SideOnly(Side.CLIENT)
public class RenderRedDragon extends RenderLiving
{
	/**
	 * The entity instance of the dragon. Note: This is a static field in RenderDragon because there is only supposed to
	 * be one dragon
	 */
	public static EntityRedDragon entityDragon;

	/**
	 * Reloads the dragon model if not equal to 4. Presumably a leftover debugging field.
	 */
	private static int updateModelState = 0;

	/** An instance of the dragon model in RenderDragon */
	protected ModelRedDragon modelDragon;

	public RenderRedDragon()
	{
		super(new ModelRedDragon(0.0F), 0.5F);
		this.modelDragon = (ModelRedDragon)this.mainModel;
		this.setRenderPassModel(this.mainModel);
	}

	/**
	 * Used to rotate the dragon as a whole in RenderDragon. It's called in the rotateCorpse method.
	 */
	protected void rotateDragonBody(EntityRedDragon par1EntityRedDragon, float par2, float par3, float par4)
	{
		float var5 = (float)par1EntityRedDragon.getMovementOffsets(7, par4)[0];
		float var6 = (float)(par1EntityRedDragon.getMovementOffsets(5, par4)[1] - par1EntityRedDragon.getMovementOffsets(10, par4)[1]);
		GL11.glRotatef(-var5, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.0F, 1.0F);

		if (par1EntityRedDragon.deathTime > 0)
		{
			float var7 = (par1EntityRedDragon.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
			var7 = MathHelper.sqrt_float(var7);

			if (var7 > 1.0F)
			{
				var7 = 1.0F;
			}

			GL11.glRotatef(var7 * this.getDeathMaxRotation(par1EntityRedDragon), 0.0F, 0.0F, 1.0F);
		}
	}

	/**
	 * Renders the dragon model. Called by renderModel.
	 */
	protected void renderDragonModel(EntityRedDragon par1EntityRedDragon, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		if (par1EntityRedDragon.deathTicks > 0)
		{
			float var8 = par1EntityRedDragon.deathTicks / 200.0F;
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GREATER, var8);
			this.loadDownloadableImageTexture(par1EntityRedDragon.skinUrl, "/mob/enderdragon/shuffle.png");
			this.mainModel.render(par1EntityRedDragon, par2, par3, par4, par5, par6, par7);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glDepthFunc(GL11.GL_EQUAL);
		}

		this.loadDownloadableImageTexture(par1EntityRedDragon.skinUrl, par1EntityRedDragon.getTexture());
		this.mainModel.render(par1EntityRedDragon, par2, par3, par4, par5, par6, par7);

		if (par1EntityRedDragon.hurtTime > 0)
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
			this.mainModel.render(par1EntityRedDragon, par2, par3, par4, par5, par6, par7);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}

	/**
	 * Renders the dragon, along with its dying animation
	 */
	public void renderDragon(EntityRedDragon par1EntityRedDragon, double par2, double par4, double par6, float par8, float par9)
	{
		entityDragon = par1EntityRedDragon;

		if (updateModelState != 4)
		{
			this.mainModel = new ModelRedDragon(0.0F);
			updateModelState = 4;
		}

		super.doRenderLiving(par1EntityRedDragon, par2, par4, par6, par8, par9);
	}

	/**
	 * Renders the animation for when an enderdragon dies
	 */
	protected void renderDragonDying(EntityRedDragon par1EntityRedDragon, float par2)
	{
		super.renderEquippedItems(par1EntityRedDragon, par2);
		Tessellator var3 = Tessellator.instance;

		if (par1EntityRedDragon.deathTicks > 0)
		{
			RenderHelper.disableStandardItemLighting();
			float var4 = (par1EntityRedDragon.deathTicks + par2) / 200.0F;
			float var5 = 0.0F;

			if (var4 > 0.8F)
			{
				var5 = (var4 - 0.8F) / 0.2F;
			}

			Random var6 = new Random(432L);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, -1.0F, -2.0F);

			for (int var7 = 0; var7 < (var4 + var4 * var4) / 2.0F * 60.0F; ++var7)
			{
				GL11.glRotatef(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(var6.nextFloat() * 360.0F + var4 * 90.0F, 0.0F, 0.0F, 1.0F);
				var3.startDrawing(6);
				float var8 = var6.nextFloat() * 20.0F + 5.0F + var5 * 10.0F;
				float var9 = var6.nextFloat() * 2.0F + 1.0F + var5 * 2.0F;
				var3.setColorRGBA_I(16777215, (int)(255.0F * (1.0F - var5)));
				var3.addVertex(0.0D, 0.0D, 0.0D);
				var3.setColorRGBA_I(16711935, 0);
				var3.addVertex(-0.866D * var9, var8, -0.5F * var9);
				var3.addVertex(0.866D * var9, var8, -0.5F * var9);
				var3.addVertex(0.0D, var8, 1.0F * var9);
				var3.addVertex(-0.866D * var9, var8, -0.5F * var9);
				var3.draw();
			}

			GL11.glPopMatrix();
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			RenderHelper.enableStandardItemLighting();
		}
	}

	/**
	 * Renders the overlay for glowing eyes and the mouth. Called by shouldRenderPass.
	 */
	protected int renderGlow(EntityRedDragon par1EntityRedDragon, int par2, float par3)
	{
		if (par2 == 1)
		{
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		if (par2 != 0)
		{
			return -1;
		}
		else
		{
			this.loadTexture("/MCE/mobs/red_eyes.png");
			float var4 = 1.0F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_EQUAL);
			char var5 = 61680;
			int var6 = var5 % 65536;
			int var7 = var5 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
			return 1;
		}
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return this.renderGlow((EntityRedDragon)par1EntityLiving, par2, par3);
	}

	@Override
	protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
	{
		this.renderDragonDying((EntityRedDragon)par1EntityLiving, par2);
	}

	@Override
	protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
	{
		this.rotateDragonBody((EntityRedDragon)par1EntityLiving, par2, par3, par4);
	}

	/**
	 * Renders the model in RenderLiving
	 */
	@Override
	protected void renderModel(EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.renderDragonModel((EntityRedDragon)par1EntityLiving, par2, par3, par4, par5, par6, par7);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderDragon((EntityRedDragon)par1EntityLiving, par2, par4, par6, par8, par9);
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
		this.renderDragon((EntityRedDragon)par1Entity, par2, par4, par6, par8, par9);
	}
}
