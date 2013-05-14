package sedridor.mce.core;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.client.FMLClientHandler;
import sedridor.mce.MCE_Settings;

public class MCE_Features
{
//	/** timerTick is used for modified timer scales. */
//	private static int timerTick = 1;
//
//	public static void tickC()
//	{
//		if (MCE_Settings.TimerScale != 1 && MCE_Settings.TimerScale != 0)
//		{
//			int timerScale = MCE_Settings.TimerScale;
//			timerTick++;
//			if (timerTick > timerScale)
//			{
//				timerTick = 1;
//			}
//			MCE_Core.mc.theWorld.setWorldTime(MCE_Core.mc.theWorld.getWorldTime() + MathHelper.floor_float((1.0F / timerScale) * timerTick));
//		}
//		else
//		{
//			MCE_Core.mc.theWorld.setWorldTime(MCE_Core.mc.theWorld.getWorldTime() + 1L);
//		}
//	}
//
//	public static void tickS()
//	{
//		if (MCE_Core.mcServer == null)
//		{
//			MCE_Core.mcServer = FMLClientHandler.instance().getServer();
//			MCE_Core.worldServer = FMLClientHandler.instance().getServer().worldServers[0];
//		}
//
//		if (MCE_Core.mcServer != null && MCE_Core.mcServer.worldServers[0] != null && MCE_Core.mcServer.worldServers[0].getWorldInfo() != null)
//		{
//			if (MCE_Settings.TimerScale != 1 && MCE_Settings.TimerScale != 0)
//			{
//				int timerScale = MCE_Settings.TimerScale;
//				timerTick++;
//				if (timerTick > timerScale)
//				{
//					timerTick = 1;
//				}
//				MCE_Core.mcServer.worldServers[0].getWorldInfo().setWorldTime(MCE_Core.mcServer.worldServers[0].getWorldInfo().getWorldTime() + MathHelper.floor_float((1.0F / timerScale) * timerTick));
//			}
//			else
//			{
//				MCE_Core.mcServer.worldServers[0].getWorldInfo().setWorldTime(MCE_Core.mcServer.worldServers[0].getWorldInfo().getWorldTime() + 1L);
//			}
//		}
//	}
//
//	public static DateFormat func_82315_h(GuiSelectWorld par0GuiSelectWorld)
//	{
//		return MCE_Core.dateFormatter;
//	}

	public static void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, RenderEngine par2RenderEngine, ItemStack par3ItemStack, int par4, int par5, String par6Str)
	{
		if (par3ItemStack != null)
		{
			if (par3ItemStack.stackSize > 1 || par6Str != null)
			{
				String var7 = par6Str == null ? String.valueOf(par3ItemStack.stackSize) : par6Str;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				// ------------------------------------------------ MCE
				if (MCE_Settings.ItemStackCount.equalsIgnoreCase("yes"))
				{
					int var12 = 0;
					if (par3ItemStack.stackSize > 9)
					{
						var12 = 3;
					}
					GL11.glPushMatrix();
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					par1FontRenderer.drawStringWithShadow(var7, (par4 + 19 - 1 + var12 - par1FontRenderer.getStringWidth(var7)) * 2, (par5 + 6 + 5) * 2, 0xffffff);
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
				if (MCE_Settings.ItemDamageCount.equalsIgnoreCase("yes"))
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
					GL11.glPushMatrix();
					GL11.glScalef(0.5F, 0.5F, 0.5F);
					par1FontRenderer.drawStringWithShadow(var11, (par4 + 1) * 2, (par5 + 6 + 5) * 2, var12);
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
					renderQuad(var9, par4 + 2, par5 + 13, 13, 2, 0);
					renderQuad(var9, par4 + 2, par5 + 13, 12, 1, var11);
					renderQuad(var9, par4 + 2, par5 + 13, var12, 1, var10);
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
	private static void renderQuad(Tessellator par1Tessellator, int par2, int par3, int par4, int par5, int par6)
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
