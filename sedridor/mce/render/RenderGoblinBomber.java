package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import sedridor.mce.entities.*;
import sedridor.mce.models.*;

@SideOnly(Side.CLIENT)
public class RenderGoblinBomber extends RenderLiving
{
	protected ModelGoblinBomber modelBipedMain;

	public RenderGoblinBomber(ModelGoblinBomber par1ModelGoblinBomber, float par2)
	{
		super(par1ModelGoblinBomber, par2);
		this.modelBipedMain = par1ModelGoblinBomber;
	}

	public void render(EntityGoblinBomber par1EntityGoblinBomber, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityGoblinBomber, par2, par4, par6, par8, par9);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.render((EntityGoblinBomber)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
	{
		float var3 = 1.0F;
		GL11.glColor3f(var3, var3, var3);
		super.renderEquippedItems(par1EntityLiving, par2);
		ItemStack var4 = par1EntityLiving.getHeldItem();
		ItemStack var5 = par1EntityLiving.getCurrentArmor(3);
		float var6;

		if (var5 != null)
		{
			GL11.glPushMatrix();
			this.modelBipedMain.bipedHead.postRender(0.0625F);

			if (var5.getItem().itemID < 256)
			{
				if (RenderBlocks.renderItemIn3d(Block.blocksList[var5.itemID].getRenderType()))
				{
					var6 = 0.625F;
					GL11.glTranslatef(0.0F, -0.25F, 0.0F);
					GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(var6, -var6, -var6);
				}

				this.renderManager.itemRenderer.renderItem(par1EntityLiving, var5, 0);
			}
			else if (var5.getItem().itemID == Item.skull.itemID)
			{
				var6 = 1.0625F;
				GL11.glScalef(var6, -var6, -var6);
				String var7 = "";

				if (var5.hasTagCompound() && var5.getTagCompound().hasKey("SkullOwner"))
				{
					var7 = var5.getTagCompound().getString("SkullOwner");
				}

				TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var5.getItemDamage(), var7);
			}

			GL11.glPopMatrix();
		}

		if (var4 != null)
		{
			GL11.glPushMatrix();

			if (this.mainModel.isChild)
			{
				var6 = 0.5F;
				GL11.glTranslatef(0.0F, 0.625F, 0.0F);
				GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
				GL11.glScalef(var6, var6, var6);
			}

			this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

			if (var4.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[var4.itemID].getRenderType()))
			{
				var6 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				var6 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(-var6, -var6, var6);
			}
			else if (var4.itemID == Item.bow.itemID || var4.getItemUseAction() == EnumAction.bow)
			{
				var6 = 0.625F;
				GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
				GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var6, -var6, var6);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}
			else if (Item.itemsList[var4.itemID].isFull3D())
			{
				var6 = 0.625F;

				if (Item.itemsList[var4.itemID].shouldRotateAroundWhenRendering())
				{
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}

				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(var6, -var6, var6);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}
			else
			{
				var6 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(var6, var6, var6);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			this.renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 0);

			if (var4.getItem().requiresMultipleRenderPasses())
			{
				this.renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 1);
			}

			GL11.glPopMatrix();
		}
	}

	/**
	 * Updates color multiplier based on goblin state called by getColorMultiplier
	 */
	protected int updateGoblinColorMultiplier(EntityGoblinBomber par1EntityGoblinBomber, float par2, float par3)
	{
		float var5 = par1EntityGoblinBomber.setGoblinFlashTime(par3);

		if ((int)(var5 * 10.0F) % 2 == 0)
		{
			return 0;
		}
		else
		{
			int var6 = (int)(var5 * 0.2F * 255.0F);

			if (var6 < 0)
			{
				var6 = 0;
			}

			if (var6 > 255)
			{
				var6 = 255;
			}

			short var7 = 255;
			short var8 = 255;
			short var9 = 255;
			return var6 << 24 | var7 << 16 | var8 << 8 | var9;
		}
	}

	/**
	 * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
	 */
	@Override
	protected int getColorMultiplier(EntityLiving par1EntityLiving, float par2, float par3)
	{
		return this.updateGoblinColorMultiplier((EntityGoblinBomber)par1EntityLiving, par2, par3);
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
		this.render((EntityGoblinBomber)par1Entity, par2, par4, par6, par8, par9);
	}
}
