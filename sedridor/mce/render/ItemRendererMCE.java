package sedridor.mce.render;

import sedridor.mce.*;
import sedridor.mce.items.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class ItemRendererMCE extends ItemRenderer
{
	/** A reference to the Minecraft object. */
	private Minecraft mc;

	/** Instance of RenderBlocks. */
	private RenderBlocks renderBlocksInstance = new RenderBlocks();

	public ItemRendererMCE(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
		this.mc = par1Minecraft;
	}

	/**
	 * Renders the item stack for being in an entity's hand Args: itemStack
	 */
	@Override
	public void renderItem(EntityLiving par1EntityLiving, ItemStack par2ItemStack, int par3)
	{
		GL11.glPushMatrix();
		Block var4 = null;

		if (par2ItemStack.itemID < Block.blocksList.length)
		{
			var4 = Block.blocksList[par2ItemStack.itemID];
		}

		if (par2ItemStack.getItemSpriteNumber() == 0 && var4 != null && RenderBlocks.renderItemIn3d(var4.getRenderType()))
		{
			this.mc.renderEngine.bindTexture("/terrain.png");
			this.renderBlocksInstance.renderBlockAsItem(Block.blocksList[par2ItemStack.itemID], par2ItemStack.getItemDamage(), 1.0F);
		}
		else
		{
			Icon var5;
			if (par2ItemStack.getItemUseAction() == EnumAction.bow && par2ItemStack.itemID != Item.bow.itemID)
			{
				var5 = this.getItemIcon(par1EntityLiving, par2ItemStack, par3);
			}
			else
			{
				var5 = par1EntityLiving.getItemIcon(par2ItemStack, par3);
			}

			if (var5 == null)
			{
				GL11.glPopMatrix();
				return;
			}

			if (par2ItemStack.getItemSpriteNumber() == 0)
			{
				this.mc.renderEngine.bindTexture("/terrain.png");
			}
			else
			{
				this.mc.renderEngine.bindTexture("/gui/items.png");
			}

			Tessellator var6 = Tessellator.instance;
			float var7 = var5.getMinU();
			float var8 = var5.getMaxU();
			float var9 = var5.getMinV();
			float var10 = var5.getMaxV();
			float var11 = 0.0F;
			float var12 = 0.3F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslatef(-var11, -var12, 0.0F);
			float var13 = 1.5F;
			GL11.glScalef(var13, var13, var13);
			GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
			renderItemIn2D(var6, var8, var9, var7, var10, var5.getSheetWidth(), var5.getSheetHeight(), 0.0625F);

			if (par2ItemStack != null && par2ItemStack.hasEffect() && par3 == 0)
			{
				GL11.glDepthFunc(GL11.GL_EQUAL);
				GL11.glDisable(GL11.GL_LIGHTING);
				this.mc.renderEngine.bindTexture("%blur%/misc/glint.png");
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
				float var14 = 0.76F;
				GL11.glColor4f(0.5F * var14, 0.25F * var14, 0.8F * var14, 1.0F);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glPushMatrix();
				float var15 = 0.125F;
				GL11.glScalef(var15, var15, var15);
				float var16 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
				GL11.glTranslatef(var16, 0.0F, 0.0F);
				GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
				renderItemIn2D(var6, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glScalef(var15, var15, var15);
				var16 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
				GL11.glTranslatef(-var16, 0.0F, 0.0F);
				GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
				renderItemIn2D(var6, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
				GL11.glPopMatrix();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}

		GL11.glPopMatrix();
	}

	/**
	 * Renders an item held in hand as a 2D texture with thickness
	 */
	public static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, int par5, int par6, float par7)
	{
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
		par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, par1, par4);
		par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, par3, par4);
		par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, par3, par2);
		par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, par1, par2);
		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
		par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - par7, par1, par2);
		par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0F - par7, par3, par2);
		par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0F - par7, par3, par4);
		par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - par7, par1, par4);
		par0Tessellator.draw();
		float var8 = par5 * (par1 - par3);
		float var9 = par6 * (par4 - par2);
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		int var10;
		float var11;
		float var12;

		for (var10 = 0; var10 < var8; ++var10)
		{
			var11 = var10 / var8;
			var12 = par1 + (par3 - par1) * var11 - 0.5F / par5;
			par0Tessellator.addVertexWithUV(var11, 0.0D, 0.0F - par7, var12, par4);
			par0Tessellator.addVertexWithUV(var11, 0.0D, 0.0D, var12, par4);
			par0Tessellator.addVertexWithUV(var11, 1.0D, 0.0D, var12, par2);
			par0Tessellator.addVertexWithUV(var11, 1.0D, 0.0F - par7, var12, par2);
		}

		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(1.0F, 0.0F, 0.0F);
		float var13;

		for (var10 = 0; var10 < var8; ++var10)
		{
			var11 = var10 / var8;
			var12 = par1 + (par3 - par1) * var11 - 0.5F / par5;
			var13 = var11 + 1.0F / var8;
			par0Tessellator.addVertexWithUV(var13, 1.0D, 0.0F - par7, var12, par2);
			par0Tessellator.addVertexWithUV(var13, 1.0D, 0.0D, var12, par2);
			par0Tessellator.addVertexWithUV(var13, 0.0D, 0.0D, var12, par4);
			par0Tessellator.addVertexWithUV(var13, 0.0D, 0.0F - par7, var12, par4);
		}

		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 1.0F, 0.0F);

		for (var10 = 0; var10 < var9; ++var10)
		{
			var11 = var10 / var9;
			var12 = par4 + (par2 - par4) * var11 - 0.5F / par6;
			var13 = var11 + 1.0F / var9;
			par0Tessellator.addVertexWithUV(0.0D, var13, 0.0D, par1, var12);
			par0Tessellator.addVertexWithUV(1.0D, var13, 0.0D, par3, var12);
			par0Tessellator.addVertexWithUV(1.0D, var13, 0.0F - par7, par3, var12);
			par0Tessellator.addVertexWithUV(0.0D, var13, 0.0F - par7, par1, var12);
		}

		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, -1.0F, 0.0F);

		for (var10 = 0; var10 < var9; ++var10)
		{
			var11 = var10 / var9;
			var12 = par4 + (par2 - par4) * var11 - 0.5F / par6;
			par0Tessellator.addVertexWithUV(1.0D, var11, 0.0D, par3, var12);
			par0Tessellator.addVertexWithUV(0.0D, var11, 0.0D, par1, var12);
			par0Tessellator.addVertexWithUV(0.0D, var11, 0.0F - par7, par1, var12);
			par0Tessellator.addVertexWithUV(1.0D, var11, 0.0F - par7, par3, var12);
		}

		par0Tessellator.draw();
	}

	public Icon getItemIcon(EntityLiving par1EntityLiving, ItemStack par2ItemStack, int par3)
	{
		Icon var4 = par1EntityLiving.getItemIcon(par2ItemStack, par3);

		if (par2ItemStack.getItem().requiresMultipleRenderPasses())
		{
			return par2ItemStack.getItem().getIconFromDamageForRenderPass(par2ItemStack.getItemDamage(), par3);
		}

		Item var5 = Item.itemsList[par2ItemStack.itemID];

		ItemStack itemInUse = (ItemStack)MCE_Reflect.getValue(EntityPlayer.class, par1EntityLiving, 31);
		Integer itemInUseCount = (Integer)MCE_Reflect.getValue(EntityPlayer.class, par1EntityLiving, 32);
		if (itemInUse != null && itemInUseCount != null && par2ItemStack.getItemUseAction() == EnumAction.bow && var5 instanceof ItemMetalBow)
		{
			int var6 = par2ItemStack.getMaxItemUseDuration() - itemInUseCount;
			ItemMetalBow var7 = (ItemMetalBow)Item.itemsList[par2ItemStack.itemID];

			if (var6 >= 18)
			{
				return var7.getIcon(2);
			}

			if (var6 > 13)
			{
				return var7.getIcon(1);
			}

			if (var6 > 0)
			{
				return var7.getIcon(0);
			}
		}
		return var4;
	}
}
