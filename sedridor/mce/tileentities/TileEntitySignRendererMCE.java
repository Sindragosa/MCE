package sedridor.mce.tileentities;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;

public class TileEntitySignRendererMCE extends TileEntitySignRenderer
{
	/** The ModelSign instance used by the TileEntitySignRenderer */
	private ModelSign modelSign = new ModelSign();
	// ------------------------------------------------ MCE
	private Minecraft mc = Minecraft.getMinecraft();
	private static final Pattern genPat = Pattern.compile("\\[(.+?)\\]");
	private static final Pattern redPat = Pattern.compile("\\[(.*)\\|(.*)\\]");
	private static final Pattern colPat = Pattern.compile("~([0-9a-fA-Fk])");
	// ------------------------------------------------ MCE

	public void renderTileEntitySignAt(TileEntitySignMCE par1TileEntitySign, double par2, double par4, double par6, float par8)
	{
		Block var9 = par1TileEntitySign.getBlockType();
		GL11.glPushMatrix();
		float var10 = 0.6666667F;
		float var12;

		if (var9 == Block.signPost)
		{
			GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.75F * var10, (float)par6 + 0.5F);
			float var11 = par1TileEntitySign.getBlockMetadata() * 360 / 16.0F;
			GL11.glRotatef(-var11, 0.0F, 1.0F, 0.0F);
			this.modelSign.signStick.showModel = true;
		}
		else
		{
			int var16 = par1TileEntitySign.getBlockMetadata();
			var12 = 0.0F;

			if (var16 == 2)
			{
				var12 = 180.0F;
			}

			if (var16 == 4)
			{
				var12 = 90.0F;
			}

			if (var16 == 5)
			{
				var12 = -90.0F;
			}

			GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 0.75F * var10, (float)par6 + 0.5F);
			GL11.glRotatef(-var12, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
			this.modelSign.signStick.showModel = false;
		}

		this.bindTextureByName("/item/sign.png");
		GL11.glPushMatrix();
		GL11.glScalef(var10, -var10, -var10);
		this.modelSign.renderSign();
		GL11.glPopMatrix();
		FontRenderer var17 = this.getFontRenderer();
		var12 = 0.016666668F * var10;
		GL11.glTranslatef(0.0F, 0.5F * var10, 0.07F * var10);
		GL11.glScalef(var12, -var12, var12);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * var12);
		GL11.glDepthMask(false);
		byte var13 = 0;

		// ------------------------------------------------ MCE
		BiomeGenBase var20 = par1TileEntitySign.worldObj.getBiomeGenForCoords(par1TileEntitySign.xCoord, par1TileEntitySign.zCoord);
		// ------------------------------------------------ MCE

		for (int var14 = 0; var14 < par1TileEntitySign.signText.length; ++var14)
		{
			String var15 = par1TileEntitySign.signText[var14];

			// ------------------------------------------------ MCE

			Matcher var27 = TileEntitySignRendererMCE.redPat.matcher(var15);
			if (var27.find())
			{
				var15 = var27.replaceAll(var27.group(par1TileEntitySign.redStone ? 1 : 2));
			}

			var27 = TileEntitySignRendererMCE.colPat.matcher(var15);

			if (var27.find())
			{
				var15 = var27.replaceAll("\u00a7" + var27.group(1));
			}

			var27 = TileEntitySignRendererMCE.genPat.matcher(var15);
			StringBuffer var18 = new StringBuffer(30);
			while (var27.find())
			{
				String var19 = var27.group(1);

				if (var19.equalsIgnoreCase("time"))
				{
					var27.appendReplacement(var18, getTime(par1TileEntitySign));
				}
				else if (var19.equalsIgnoreCase("x"))
				{
					var27.appendReplacement(var18, Integer.toString(par1TileEntitySign.xCoord));
				}
				else if (var19.equalsIgnoreCase("y"))
				{
					var27.appendReplacement(var18, Integer.toString(par1TileEntitySign.yCoord));
				}
				else if (var19.equalsIgnoreCase("z"))
				{
					var27.appendReplacement(var18, Integer.toString(par1TileEntitySign.zCoord));
				}
				else if (var19.equalsIgnoreCase("l"))
				{
					var27.appendReplacement(var18, Integer.toString(par1TileEntitySign.worldObj.getBlockLightValue(par1TileEntitySign.xCoord, par1TileEntitySign.yCoord, par1TileEntitySign.zCoord)));
				}
				else if (var19.equalsIgnoreCase("light"))
				{
					var27.appendReplacement(var18, Integer.toString(par1TileEntitySign.worldObj.getFullBlockLightValue(par1TileEntitySign.xCoord, par1TileEntitySign.yCoord, par1TileEntitySign.zCoord)));
				}
				else if (var19.equalsIgnoreCase("biome"))
				{
					var27.appendReplacement(var18, var20.biomeName);
				}
				else if (var19.equalsIgnoreCase("temp"))
				{
					var27.appendReplacement(var18, NumberFormat.getPercentInstance().format(var20.temperature));
				}
				else if (var19.equalsIgnoreCase("humid"))
				{
					var27.appendReplacement(var18, NumberFormat.getPercentInstance().format(var20.rainfall));
				}
			}
			var27.appendTail(var18);
			var15 = var18.toString();
			// ------------------------------------------------ MCE

			if (var14 == par1TileEntitySign.lineBeingEdited)
			{
				var15 = "> " + var15 + " <";
				var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - par1TileEntitySign.signText.length * 5, var13);
			}
			else
			{
				var17.drawString(var15, -var17.getStringWidth(var15) / 2, var14 * 10 - par1TileEntitySign.signText.length * 5, var13);
			}
		}

		GL11.glDepthMask(true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderTileEntitySignAt((TileEntitySignMCE)par1TileEntity, par2, par4, par6, par8);
	}

	public static String getTime(TileEntitySignMCE par1TileEntitySign)
	{
		long var2 = par1TileEntitySign.worldObj.getWorldInfo().getWorldTime() + 6000L;
		int var3 = (int)(var2 % 24000L / 1000L);
		int var4 = (int)(var2 % 1000L * 60L / 1000L);
		return String.format("%02d:%02d", var3, var4);
	}
}
