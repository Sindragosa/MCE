package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import sedridor.mce.*;

@SideOnly(Side.CLIENT)
public class RenderItemMCE extends RenderItem
{
	public RenderItemMCE()
	{
		super();
	}

	/**
	 * Renders the item's overlay information. Examples being stack count or damage on top of the item's image at the
	 * specified position.
	 */
	@Override
	public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, RenderEngine par2RenderEngine, ItemStack par3ItemStack, int par4, int par5)
	{
		this.renderItemStack(par1FontRenderer, par2RenderEngine, par3ItemStack, par4, par5, (String)null);
	}

	public void renderItemStack(FontRenderer par1FontRenderer, RenderEngine par2RenderEngine, ItemStack par3ItemStack, int par4, int par5, String par6Str)
	{
		if (par3ItemStack != null)
		{
			ScaledResolution scaledResolution = MCEnhancements.instance.getScaledResolution();
			float scale = scaledResolution.getScaleFactor() == 4 ? 2.0F : 1.0F;
			if (par3ItemStack.stackSize > 1 || par6Str != null)
			{
				String var7 = par6Str == null ? String.valueOf(par3ItemStack.stackSize) : par6Str;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				// ------------------------------------------------ MCE
				if (MCE_Settings.ItemStackCount.equalsIgnoreCase("yes") && scaledResolution.getScaleFactor() != 1)
				{
					int var12 = 0;
					if (par3ItemStack.stackSize > 9)
					{
						var12 = 3;
					}
					int var14 = 0;
					int var15 = 0;
					if (scaledResolution.getScaleFactor() == 3)
					{
						var14 = 1;
						var15 = 2;
						var12 = var12 == 3 ? 4 : 0;
					}
					GL11.glPushMatrix();
	                GL11.glScaled(1.0D / (double)scaledResolution.getScaleFactor(), 1.0D / (double)scaledResolution.getScaleFactor(), 1.0D);
					GL11.glScalef(scale, scale, 1.0F);
					par1FontRenderer.drawStringWithShadow(var7, (int)((par4 + 19 - 1 + var12 + var14 - par1FontRenderer.getStringWidth(var7)) * ((float)scaledResolution.getScaleFactor() / scale)), (int)((par5 + 6 + 5 + var15) * ((float)scaledResolution.getScaleFactor() / scale)), 0xffffff);
					GL11.glPopMatrix();
				}
				else
				{
					// ------------------------------------------------ MCE
					par1FontRenderer.drawStringWithShadow(var7, par4 + 19 - 2 - par1FontRenderer.getStringWidth(var7), par5 + 6 + 3, 16777215);
				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}

			if (par3ItemStack.isItemDamaged())
			{
				// ------------------------------------------------ MCE
				if (MCE_Settings.ItemDamageCount.equalsIgnoreCase("yes") && scaledResolution.getScaleFactor() != 1)
				{
					int var7 = (int)Math.round(255.0D - par3ItemStack.getItemDamageForDisplay() * 255.0D / par3ItemStack.getMaxDamage());
					String var11 = "" + (par3ItemStack.getMaxDamage() - par3ItemStack.getItemDamageForDisplay() + 1);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					int var12 = var7;
					if (var7 < 128)
					{
						var12 = var7 << 9 | 16711680;
					}
					if (var12 < 256)
					{
						var12 = 255 - (var12 << 1) << 16 | 65280;
					}
					int var14 = 0;
					int var15 = 0;
					if (scaledResolution.getScaleFactor() == 3)
					{
						var14 = 0;
						var15 = 2;
					}
					GL11.glPushMatrix();
	                GL11.glScaled(1.0D / (double)scaledResolution.getScaleFactor(), 1.0D / (double)scaledResolution.getScaleFactor(), 1.0D);
					GL11.glScalef(scale, scale, 1.0F);
					par1FontRenderer.drawStringWithShadow(var11, (int)((par4 + 1 + var14) * ((float)scaledResolution.getScaleFactor() / scale)), (int)((par5 + 6 + 5 + var15) * ((float)scaledResolution.getScaleFactor() / scale)), var12);
					GL11.glPopMatrix();

					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
				else
				{
					// ------------------------------------------------ MCE
					int var12 = (int)Math.round(13.0D - par3ItemStack.getItemDamageForDisplay() * 13.0D / par3ItemStack.getMaxDamage());
					int var8 = (int)Math.round(255.0D - par3ItemStack.getItemDamageForDisplay() * 255.0D / par3ItemStack.getMaxDamage());
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_DEPTH_TEST);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					Tessellator var9 = Tessellator.instance;
					int var10 = 255 - var8 << 16 | var8 << 8;
					int var11 = (255 - var8) / 4 << 16 | 16128;
					this.renderQuad(var9, par4 + 2, par5 + 13, 13, 2, 0);
					this.renderQuad(var9, par4 + 2, par5 + 13, 12, 1, var11);
					this.renderQuad(var9, par4 + 2, par5 + 13, var12, 1, var10);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_DEPTH_TEST);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		}
	}

	/**
	 * Adds a quad to the tesselator at the specified position with the set width and height and color.  Args:
	 * tessellator, x, y, width, height, color
	 */
	private void renderQuad(Tessellator par1Tessellator, int par2, int par3, int par4, int par5, int par6)
	{
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setColorOpaque_I(par6);
		par1Tessellator.addVertex(par2 + 0, par3 + 0, 0.0D);
		par1Tessellator.addVertex(par2 + 0, par3 + par5, 0.0D);
		par1Tessellator.addVertex(par2 + par4, par3 + par5, 0.0D);
		par1Tessellator.addVertex(par2 + par4, par3 + 0, 0.0D);
		par1Tessellator.draw();
	}
}
