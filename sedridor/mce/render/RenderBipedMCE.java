package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import sedridor.mce.entities.*;

@SideOnly(Side.CLIENT)
public class RenderBipedMCE extends RenderLiving
{
	protected ModelBiped modelBipedMain;
	protected float field_77070_b;
	protected ModelBiped field_82423_g;
	protected ModelBiped field_82425_h;

	/** List of armor texture filenames. */
	private static final String[] bipedArmorFilenamePrefix = new String[] {"cloth", "chain", "iron", "diamond", "gold"};

	public RenderBipedMCE(ModelBiped par1ModelBiped, float par2)
	{
		this(par1ModelBiped, par2, 1.0F);
	}

	public RenderBipedMCE(ModelBiped par1ModelBiped, float par2, float par3)
	{
		super(par1ModelBiped, par2);
		this.modelBipedMain = par1ModelBiped;
		this.field_77070_b = par3;
		this.func_82421_b();
	}

	protected void func_82421_b()
	{
		this.field_82423_g = new ModelBiped(1.0F);
		this.field_82425_h = new ModelBiped(0.5F);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		ItemStack var4 = par1EntityLiving.getCurrentArmor(3 - par2);
		int var14 = -1;
		var14 = this.renderPoweredModel((EntityMobMCE)par1EntityLiving, par2, par3);

		if (var4 != null)
		{
			Item var5 = var4.getItem();

			if (var5 instanceof ItemArmor)
			{
				ItemArmor var6 = (ItemArmor)var5;
				this.loadTexture("/armor/" + bipedArmorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + ".png");
				ModelBiped var7 = par2 == 2 ? this.field_82425_h : this.field_82423_g;
				var7.bipedHead.showModel = par2 == 0;
				var7.bipedHeadwear.showModel = par2 == 0;
				var7.bipedBody.showModel = par2 == 1 || par2 == 2;
				var7.bipedRightArm.showModel = par2 == 1;
				var7.bipedLeftArm.showModel = par2 == 1;
				var7.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
				var7.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
				this.setRenderPassModel(var7);

				if (var7 != null)
				{
					var7.onGround = this.mainModel.onGround;
				}

				if (var7 != null)
				{
					var7.isRiding = this.mainModel.isRiding;
				}

				if (var7 != null)
				{
					var7.isChild = this.mainModel.isChild;
				}

				float var8 = 1.0F;

				if (var6.getArmorMaterial() == EnumArmorMaterial.CLOTH)
				{
					int var9 = var6.getColor(var4);
					float var10 = (var9 >> 16 & 255) / 255.0F;
					float var11 = (var9 >> 8 & 255) / 255.0F;
					float var12 = (var9 & 255) / 255.0F;
					GL11.glColor3f(var8 * var10, var8 * var11, var8 * var12);

					if (var4.isItemEnchanted())
					{
						return 31;
					}

					return 16;
				}

				GL11.glColor3f(var8, var8, var8);

				if (var4.isItemEnchanted())
				{
					return 15;
				}

				return 1;
			}
		}

		return var14;
	}

	/**
	 * A method used to render a powered form as a pass model.
	 */
	protected int renderPoweredModel(EntityMobMCE par1EntityMobMCE, int par2, float par3)
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
	protected void func_82408_c(EntityLiving par1EntityLiving, int par2, float par3)
	{
		ItemStack var4 = par1EntityLiving.getCurrentArmor(3 - par2);

		if (var4 != null)
		{
			Item var5 = var4.getItem();

			if (var5 instanceof ItemArmor)
			{
				ItemArmor var6 = (ItemArmor)var5;
				this.loadTexture("/armor/" + bipedArmorFilenamePrefix[var6.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + "_b.png");
				float var7 = 1.0F;
				GL11.glColor3f(var7, var7, var7);
			}
		}
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		float var10 = 1.0F;
		GL11.glColor3f(var10, var10, var10);
		ItemStack var11 = par1EntityLiving.getHeldItem();
		this.func_82420_a(par1EntityLiving, var11);
		double var12 = par4 - par1EntityLiving.yOffset;

		if (par1EntityLiving.isSneaking() && !(par1EntityLiving instanceof EntityPlayerSP))
		{
			var12 -= 0.125D;
		}

		super.doRenderLiving(par1EntityLiving, par2, var12, par6, par8, par9);
		this.field_82423_g.aimedBow = this.field_82425_h.aimedBow = this.modelBipedMain.aimedBow = false;
		this.field_82423_g.isSneak = this.field_82425_h.isSneak = this.modelBipedMain.isSneak = false;
		this.field_82423_g.heldItemRight = this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight = 0;
	}

	protected void func_82420_a(EntityLiving par1EntityLiving, ItemStack par2ItemStack)
	{
		this.field_82423_g.heldItemRight = this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight = par2ItemStack != null ? 1 : 0;
		this.field_82423_g.isSneak = this.field_82425_h.isSneak = this.modelBipedMain.isSneak = par1EntityLiving.isSneaking();
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
			else if (var4.itemID == Item.bow.itemID /*|| var4.getItemUseAction() == EnumAction.bow*/)
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
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRenderLiving((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
	}


	protected int func_77061_b(EntityMobMCE par1EntityMobMCE, int par2, float par3)
	{
		return -1;
	}

	@Override
	protected int inheritRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return this.func_77061_b((EntityMobMCE)par1EntityLiving, par2, par3);
	}
}
