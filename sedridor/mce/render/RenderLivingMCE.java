package sedridor.mce.render;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import sedridor.mce.entities.*;
import sedridor.mce.models.*;

@SideOnly(Side.CLIENT)
public class RenderLivingMCE extends RenderLiving
{
	private static Minecraft mc;
	protected ModelBiped modelBipedMain;
	protected float field_77070_b;

	public RenderLivingMCE(ModelBiped par1ModelBiped, float par2)
	{
		this(par1ModelBiped, par2, 1.0F);
		RenderLivingMCE.mc = FMLClientHandler.instance().getClient();
		this.modelBipedMain = par1ModelBiped;
	}

	public RenderLivingMCE(ModelBiped par1ModelBiped, float par2, float par3)
	{
		super(par1ModelBiped, par2);
		RenderLivingMCE.mc = FMLClientHandler.instance().getClient();
		this.modelBipedMain = par1ModelBiped;
		this.field_77070_b = par3;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
	{
		float scaleX = 0.9075F;
		float scaleY = 0.9075F;
		float scaleZ = 0.9075F;
		if (this.mainModel instanceof ModelDwarfMale)
		{
			scaleX = 0.85F;
			scaleY = 0.6875F;
			scaleZ = 0.85F;
		}
		else if (this.mainModel instanceof ModelDwarfFemale)
		{
			scaleX = 0.75F;
			scaleY = 0.6275F;
			scaleZ = 0.75F;
		}
		else if (this.mainModel instanceof ModelElfMale)
		{
			scaleX = 0.85F;
			scaleY = 1.07F;
			scaleZ = 0.85F;
		}
		else if (this.mainModel instanceof ModelElfFemale)
		{
			scaleX = 0.8F;
			scaleY = 1.0F;
			scaleZ = 0.8F;
		}
		else if (this.mainModel instanceof ModelOrcMale)
		{
			scaleX = 1.0F;
			scaleY = 1.0F;
			scaleZ = 1.0F;
		}
		else if (this.mainModel instanceof ModelOrcFemale)
		{
			scaleX = 1.0F;
			scaleY = 1.0F;
			scaleZ = 1.0F;
		}
		else if (this.mainModel instanceof ModelNagaMale)
		{
			scaleX = 1.0F;
			scaleY = 1.0F;
			scaleZ = 1.0F;
		}
		else if (this.mainModel instanceof ModelNagaFemale)
		{
			scaleX = 1.0F;
			scaleY = 1.0F;
			scaleZ = 1.0F;
		}
		GL11.glScalef(scaleX, scaleY, scaleZ);
	}

	public void renderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		if (this.mainModel instanceof ModelHumanFemale)
		{
			((ModelHumanFemale)this.mainModel).showBreasts(true);
		}
		if (this.mainModel instanceof ModelElfFemale)
		{
			((ModelElfFemale)this.mainModel).showBreasts(true);
		}
		super.doRenderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
	{
		super.renderEquippedItems(par1EntityLiving, par2);
		ItemStack var3 = par1EntityLiving.getHeldItem();

		if (var3 != null)
		{
			GL11.glPushMatrix();
			this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			float var4;

			if (var3.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var3.itemID].getRenderType()))
			{
				var4 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				var4 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
			}
			else if (var3.itemID == Item.bow.itemID || var3.getItemUseAction() == EnumAction.bow)
			{
				var4 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}
			else if (Item.itemsList[var3.itemID].isFull3D())
			{
				var4 = 0.625F;
				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}
			else
			{
				var4 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(var4, var4, var4);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, 0);

			if (var3.getItem().requiresMultipleRenderPasses())
			{
				this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, 1);
			}

			GL11.glPopMatrix();
		}
	}

	/**
	 * A method used to render a powered form as a pass model.
	 */
	 protected int renderPassModel(EntityMobMCE par1EntityMobMCE, int par2, float par3)
	{
		if (par1EntityMobMCE.getPowered())
		{
			if (par2 == 1)
			{
				float var4 = par1EntityMobMCE.ticksExisted + par3;
				this.loadTexture("/armor/power.png");
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				float var5 = var4 * 0.01F;
				float var6 = var4 * 0.01F;
				GL11.glTranslatef(var5, var6, 0.0F);
				int var8 = 15728880;
				int var9 = var8 % 65536;
				int var10 = var8 / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var9 / 1.0F, var10 / 1.0F);
				this.setRenderPassModel(this.modelBipedMain);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_BLEND);
				float var7 = 0.5F;
				GL11.glColor4f(var7, var7, var7, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
				return 1;
			}

			if (par2 == 2)
			{
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
			}
		}

		return -1;
	}

	 @Override
	 public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	 {
		 this.renderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
	 }

	 /**
	  * Queries whether should render the specified pass or not.
	  */
	 @Override
	 protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	 {
		 return this.renderPassModel((EntityMobMCE)par1EntityLiving, par2, par3);
	 }

	 /**
	  * Passes the specialRender and renders it
	  */
	 @Override
	 protected void passSpecialRender(EntityLiving par1EntityLiving, double par2, double par4, double par6)
	 {
		 if (RenderLivingMCE.mc.gameSettings.showDebugInfo)
		 {
			 this.renderLivingLabel(par1EntityLiving, par1EntityLiving.getEntityName() + " (ID " + par1EntityLiving.entityId + ")", par2, par4, par6, 64);
		 }
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
		 this.renderLiving((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
	 }
}
