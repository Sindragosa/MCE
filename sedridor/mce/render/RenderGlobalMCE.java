package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.CallableParticlePositionInfo;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.Item;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColorizer;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IWorldAccess;

import sedridor.mce.entities.*;

@SideOnly(Side.CLIENT)
public class RenderGlobalMCE extends RenderGlobal implements IWorldAccess
{
	public RenderGlobalMCE(Minecraft par1Minecraft, RenderEngine par2RenderEngine)
	{
		super(par1Minecraft, par2RenderEngine);
	}

	private void renderStars()
	{
		Random var1 = new Random(10842L);
		Tessellator var2 = Tessellator.instance;
		var2.startDrawingQuads();

		for (int var3 = 0; var3 < 1500; ++var3)
		{
			double var4 = var1.nextFloat() * 2.0F - 1.0F;
			double var6 = var1.nextFloat() * 2.0F - 1.0F;
			double var8 = var1.nextFloat() * 2.0F - 1.0F;
			double var10 = 0.15F + var1.nextFloat() * 0.1F;
			double var12 = var4 * var4 + var6 * var6 + var8 * var8;

			if (var12 < 1.0D && var12 > 0.01D)
			{
				var12 = 1.0D / Math.sqrt(var12);
				var4 *= var12;
				var6 *= var12;
				var8 *= var12;
				double var14 = var4 * 100.0D;
				double var16 = var6 * 100.0D;
				double var18 = var8 * 100.0D;
				double var20 = Math.atan2(var4, var8);
				double var22 = Math.sin(var20);
				double var24 = Math.cos(var20);
				double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
				double var28 = Math.sin(var26);
				double var30 = Math.cos(var26);
				double var32 = var1.nextDouble() * Math.PI * 2.0D;
				double var34 = Math.sin(var32);
				double var36 = Math.cos(var32);

				for (int var38 = 0; var38 < 4; ++var38)
				{
					double var39 = 0.0D;
					double var41 = ((var38 & 2) - 1) * var10;
					double var43 = ((var38 + 1 & 2) - 1) * var10;
					double var45 = var41 * var36 - var43 * var34;
					double var47 = var43 * var36 + var41 * var34;
					double var49 = var45 * var28 + var39 * var30;
					double var51 = var39 * var28 - var45 * var30;
					double var53 = var51 * var22 - var47 * var24;
					double var55 = var47 * var22 + var51 * var24;
					var2.addVertex(var14 + var53, var16 + var49, var18 + var55);
				}
			}
		}

		var2.draw();
	}

	/**
	 * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
	 */
	 @Override
	 public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		try
		{
			this.doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
		}
		catch (Throwable var17)
		{
			CrashReport var15 = CrashReport.makeCrashReport(var17, "Exception while adding particle");
			CrashReportCategory var16 = var15.makeCategory("Particle being added");
			var16.addCrashSection("Name", par1Str);
			var16.addCrashSectionCallable("Position", new CallableParticlePositionInfo(this, par2, par4, par6));
			throw new ReportedException(var15);
		}
	}

	 /**
	  * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
	  */
	 @Override
	 public EntityFX doSpawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
	 {
		 if (this.mc != null && this.mc.renderViewEntity != null && this.mc.effectRenderer != null)
		 {
			 int var14 = this.mc.gameSettings.particleSetting;

			 if (var14 == 1 && this.theWorld.rand.nextInt(3) == 0)
			 {
				 var14 = 2;
			 }

			 double var15 = this.mc.renderViewEntity.posX - par2;
			 double var17 = this.mc.renderViewEntity.posY - par4;
			 double var19 = this.mc.renderViewEntity.posZ - par6;
			 EntityFX var21 = null;

			 if (par1Str.equals("hugeexplosion"))
			 {
				 if (Config.isAnimatedExplosion())
				 {
					 this.mc.effectRenderer.addEffect(var21 = new EntityHugeExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12));
				 }
			 }
			 else if (par1Str.equals("largeexplode"))
			 {
				 if (Config.isAnimatedExplosion())
				 {
					 this.mc.effectRenderer.addEffect(var21 = new EntityLargeExplodeFX(this.renderEngine, this.theWorld, par2, par4, par6, par8, par10, par12));
				 }
			 }
			 else if (par1Str.equals("fireworksSpark"))
			 {
				 this.mc.effectRenderer.addEffect(var21 = new EntityFireworkSparkFX(this.theWorld, par2, par4, par6, par8, par10, par12, this.mc.effectRenderer));
			 }

			 if (var21 != null)
			 {
				 return var21;
			 }
			 else
			 {
				 double var22 = 16.0D;
				 double var24 = 16.0D;

				 if (par1Str.equals("crit"))
				 {
					 var22 = 196.0D;
				 }

				 if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
				 {
					 return null;
				 }
				 else if (var14 > 1)
				 {
					 return null;
				 }
				 else
				 {
					 if (par1Str.equals("bubble"))
					 {
						 var21 = new EntityBubbleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 CustomColorizer.updateWaterFX(var21, this.theWorld);
					 }
					 else if (par1Str.equals("suspended"))
					 {
						 if (Config.isWaterParticles())
						 {
							 var21 = new EntitySuspendFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 }
					 }
					 else if (par1Str.equals("depthsuspend"))
					 {
						 if (Config.isVoidParticles())
						 {
							 var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 }
					 }
					 else if (par1Str.equals("townaura"))
					 {
						 var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 CustomColorizer.updateMyceliumFX(var21);
					 }
					 else if (par1Str.equals("crit"))
					 {
						 var21 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
					 }
					 else if (par1Str.equals("magicCrit"))
					 {
						 var21 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 var21.setRBGColorF(var21.getRedColorF() * 0.3F, var21.getGreenColorF() * 0.8F, var21.getBlueColorF());
						 var21.nextTextureIndexX();
					 }
					 else if (par1Str.equals("smoke"))
					 {
						 if (Config.isAnimatedSmoke())
						 {
							 var21 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 }
					 }
					 else if (par1Str.equals("mobSpell"))
					 {
						 var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
						 var21.setRBGColorF((float)par8, (float)par10, (float)par12);
					 }
					 else if (par1Str.equals("mobSpellAmbient"))
					 {
						 var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0D, 0.0D, 0.0D);
						 var21.setAlphaF(0.15F);
						 var21.setRBGColorF((float)par8, (float)par10, (float)par12);
					 }
					 else if (par1Str.equals("spell"))
					 {
						 var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
					 }
					 else if (par1Str.equals("instantSpell"))
					 {
						 var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 ((EntitySpellParticleFX)var21).setBaseSpellTextureIndex(144);
					 }
					 else if (par1Str.equals("spell2"))
					 {
						 var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
					 }
					 else if (par1Str.equals("magic"))
					 {
						 var21 = new EntitySpell2FX_MCE(this.theWorld, par2, par4, par6, par8, par10, par12);
						 var21.setRBGColorF(var21.getRedColorF() * 0.5F, var21.getGreenColorF() * 0.85F, var21.getBlueColorF());
					 }
					 else if (par1Str.equals("magicImpact"))
					 {
						 var21 = new EntitySpell2FX_MCE(this.theWorld, par2, par4, par6, par8, par10, par12);
						 var21.setRBGColorF(0.5F, 0.85F, 1.0F);
					 }
					 else if (par1Str.equals("witchMagic"))
					 {
						 var21 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 ((EntitySpellParticleFX)var21).setBaseSpellTextureIndex(144);
						 float var26 = this.theWorld.rand.nextFloat() * 0.5F + 0.35F;
						 var21.setRBGColorF(1.0F * var26, 0.0F * var26, 1.0F * var26);
					 }
					 else if (par1Str.equals("note"))
					 {
						 var21 = new EntityNoteFX(this.theWorld, par2, par4, par6, par8, par10, par12);
					 }
					 else if (par1Str.equals("portal"))
					 {
						 var21 = new EntityPortalFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 CustomColorizer.updatePortalFX(var21);
					 }
					 else if (par1Str.equals("enchantmenttable"))
					 {
						 var21 = new EntityEnchantmentTableParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
					 }
					 else if (par1Str.equals("missile"))
					 {
						 var21 = new EntityMissileFX_MCE(this.theWorld, par2, par4, par6, par8, par10, par12);
						 var21.setRBGColorF(var21.getRedColorF() * 1.0F, var21.getGreenColorF() * 1.0F, var21.getBlueColorF() * 0.5F);
					 }
					 else if (par1Str.equals("missile2"))
					 {
						 var21 = new EntityMissileFX_MCE(this.theWorld, par2, par4, par6, par8, par10, par12);
						 var21.setRBGColorF(var21.getRedColorF() * 1.0F, var21.getGreenColorF() * 0.7F, var21.getBlueColorF() * 0.2F);
					 }
					 else if (par1Str.equals("explode"))
					 {
						 if (Config.isAnimatedExplosion())
						 {
							 var21 = new EntityExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 }
					 }
					 else if (par1Str.equals("flame"))
					 {
						 if (Config.isAnimatedFlame())
						 {
							 var21 = new EntityFlameFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 }
					 }
					 else if (par1Str.equals("lava"))
					 {
						 var21 = new EntityLavaFX(this.theWorld, par2, par4, par6);
					 }
					 else if (par1Str.equals("footstep"))
					 {
						 var21 = new EntityFootStepFX(this.renderEngine, this.theWorld, par2, par4, par6);
					 }
					 else if (par1Str.equals("splash"))
					 {
						 var21 = new EntitySplashFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 CustomColorizer.updateWaterFX(var21, this.theWorld);
					 }
					 else if (par1Str.equals("largesmoke"))
					 {
						 if (Config.isAnimatedSmoke())
						 {
							 var21 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12, 2.5F);
						 }
					 }
					 else if (par1Str.equals("cloud"))
					 {
						 var21 = new EntityCloudFX(this.theWorld, par2, par4, par6, par8, par10, par12);
					 }
					 else if (par1Str.equals("reddust"))
					 {
						 if (Config.isAnimatedRedstone())
						 {
							 var21 = new EntityReddustFX(this.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
							 CustomColorizer.updateReddustFX(var21, this.theWorld, var15, var17, var19);
						 }
					 }
					 else if (par1Str.equals("snowballpoof"))
					 {
						 var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Item.snowball, this.renderEngine);
					 }
					 else if (par1Str.equals("dripWater"))
					 {
						 if (Config.isDrippingWaterLava())
						 {
							 var21 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.water);
							 var21.setRBGColorF(0.5F, 0.5F, 0.5F);
						 }
					 }
					 else if (par1Str.equals("dripLava"))
					 {
						 if (Config.isDrippingWaterLava())
						 {
							 var21 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.lava);
						 }
					 }
					 else if (par1Str.equals("snowshovel"))
					 {
						 var21 = new EntitySnowShovelFX(this.theWorld, par2, par4, par6, par8, par10, par12);
					 }
					 else if (par1Str.equals("slime"))
					 {
						 var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Item.slimeBall, this.renderEngine);
					 }
					 else if (par1Str.equals("heart"))
					 {
						 var21 = new EntityHeartFX(this.theWorld, par2, par4, par6, par8, par10, par12);
					 }
					 else if (par1Str.equals("angryVillager"))
					 {
						 var21 = new EntityHeartFX(this.theWorld, par2, par4 + 0.5D, par6, par8, par10, par12);
						 var21.setParticleTextureIndex(81);
						 var21.setRBGColorF(1.0F, 1.0F, 1.0F);
					 }
					 else if (par1Str.equals("happyVillager"))
					 {
						 var21 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
						 var21.setParticleTextureIndex(82);
						 var21.setRBGColorF(1.0F, 1.0F, 1.0F);
					 }
					 else if (par1Str.startsWith("iconcrack_"))
					 {
						 int var30 = Integer.parseInt(par1Str.substring(par1Str.indexOf("_") + 1));
						 var21 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[var30], this.renderEngine);
					 }
					 else if (par1Str.startsWith("tilecrack_"))
					 {
						 String[] var29 = par1Str.split("_", 3);
						 int var27 = Integer.parseInt(var29[1]);
						 int var28 = Integer.parseInt(var29[2]);
						 var21 = (new EntityDiggingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Block.blocksList[var27], 0, var28, this.renderEngine)).applyRenderColor(var28);
						 //var21 = (new EntityDiggingFX_MCE(this.theWorld, par2, par4, par6, par8, par10, par12, Block.blocksList[var27], 0, var28, this.renderEngine)).applyRenderColor(var28);
					 }

					 if (var21 != null)
					 {
						 this.mc.effectRenderer.addEffect(var21);
					 }

					 return var21;
				 }
			 }
		 }
		 else
		 {
			 return null;
		 }
	 }
}
