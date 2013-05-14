package sedridor.mce.models;

import sedridor.mce.entities.*;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ModelLich extends ModelSkeleton
{
	ModelRenderer collar;
	ModelRenderer cloak;
	ModelRenderer shieldBelt;
	boolean renderPass;

	public ModelLich()
	{
		this.renderPass = false;
		this.textureWidth = 64;
		this.textureHeight = 64;
		float var1 = 0.0F;
		this.bipedBody = new ModelRenderer(this, 8, 16);
		this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 24, 4, var1);
		this.bipedBody.setRotationPoint(0.0F, -4.0F + var1, 0.0F);
		this.bipedBody.setTextureSize(64, 64);
		this.bipedRightArm = new ModelRenderer(this, 0, 16);
		this.bipedRightArm.addBox(-2.0F, -2.0F, -1.0F, 2, 12, 2, var1);
		this.bipedRightArm.setTextureSize(64, 64);
		this.bipedRightArm.setRotationPoint(-5.0F, -2.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 0, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-2.0F, -2.0F, -1.0F, 2, 12, 2, var1);
		this.bipedLeftArm.setRotationPoint(5.0F, -2.0F, 0.0F);
		this.bipedLeftArm.setTextureSize(64, 64);
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4.0F, -12.0F, -4.0F, 8, 8, 8, var1 + 0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.bipedHeadwear.setTextureSize(64, 64);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.bipedHead.setTextureSize(64, 64);
		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2);
		this.bipedRightLeg.setRotationPoint(-2.0F, 9.5F, 0.0F);
		this.bipedRightLeg.setTextureSize(64, 64);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2);
		this.bipedLeftLeg.setRotationPoint(2.0F, 9.5F, 0.0F);
		this.bipedLeftLeg.setTextureSize(64, 64);
		this.bipedLeftLeg.mirror = true;
		this.collar = new ModelRenderer(this, 32, 16);
		this.collar.addBox(-6.0F, 0.0F, 0.0F, 12, 12, 1);
		this.collar.setRotationPoint(0.0F, -3.0F, -1.0F);
		this.collar.setTextureSize(64, 64);
		this.setRotation(this.collar, 2.164208F, 0.0F, 0.0F);
		this.cloak = new ModelRenderer(this, 0, 44);
		this.cloak.addBox(-6.0F, 0.0F, 0.0F, 12, 19, 1);
		this.cloak.setRotationPoint(0.0F, -4.0F, 2.5F);
		this.cloak.setTextureSize(64, 64);
		this.setRotation(this.cloak, 0.0F, 0.0F, 0.0F);
		this.shieldBelt = new ModelRenderer(this);
		this.shieldBelt.setRotationPoint(0.0F, 0.0F, 0.0F);
	}

	public ModelLich(boolean par1)
	{
		this();
		this.renderPass = par1;
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	 @Override
	 public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		EntityLich var8 = (EntityLich)par1Entity;

		if (!this.renderPass)
		{
			if (!var8.isShadowClone())
			{
				super.render(par1Entity, par2, par3, par4, par5, par6, par7 * 1.125F);
				this.collar.render(par7 * 1.125F);
				this.cloak.render(par7 * 1.125F);
			}
		}
		else if (var8.isShadowClone())
		{
			super.render(par1Entity, par2, par3, par4, par5, par6, par7 * 1.125F);
		}
		else if (var8.getShieldStrength() > 0)
		{
			this.shieldBelt.render(par7 * 1.125F);
		}
	}

	 /**
	  * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	  * and third as in the setRotationAngles method.
	  */
	 @Override
	 public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
	 {
		 EntityLich var5 = (EntityLich)par1EntityLiving;
		 byte var6 = var5.getShieldStrength();

		 if (!var5.isShadowClone() && var6 > 0)
		 {
			 if (this.shieldBelt.childModels == null || this.shieldBelt.childModels.size() != var6)
			 {
				 if (this.shieldBelt.childModels != null)
				 {
					 this.shieldBelt.childModels.clear();
				 }

				 for (int var8 = 0; var8 < var6; ++var8)
				 {
					 Vec3 var7 = par1EntityLiving.worldObj.getWorldVec3Pool().getVecFromPool(11.0D, 0.0D, 0.0D);
					 float var9 = var8 * (360.0F / var6) * (float)Math.PI / 180.0F;
					 var7.rotateAroundY(var9);
					 ModelRenderer var10 = new ModelRenderer(this, 26, 40);
					 var10.addBox(0.5F, -6.0F, -6.0F, 1, 12, 12);
					 var10.setRotationPoint((float)var7.xCoord, (float)var7.yCoord, (float)var7.zCoord);
					 var10.setTextureSize(64, 64);
					 var10.rotateAngleY = var9;
					 this.shieldBelt.addChild(var10);
				 }
			 }

			 this.shieldBelt.rotateAngleY = (var5.ticksExisted + par4) / 5.0F;
			 this.shieldBelt.rotateAngleX = MathHelper.sin((var5.ticksExisted + par4) / 5.0F) / 4.0F;
			 this.shieldBelt.rotateAngleZ = MathHelper.cos((var5.ticksExisted + par4) / 5.0F) / 4.0F;
		 }
	 }

	 /**
	  * Sets the models various rotation angles.
	  */
	 @Override
	 public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	 {
		 this.aimedBow = false;
		 super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		 float var7 = MathHelper.sin(this.onGround * (float)Math.PI);
		 float var8 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * (float)Math.PI);
		 this.bipedRightArm.rotateAngleZ = 0.0F;
		 this.bipedLeftArm.rotateAngleZ = 0.5F;
		 this.bipedRightArm.rotateAngleY = -(0.1F - var7 * 0.6F);
		 this.bipedLeftArm.rotateAngleY = 0.1F - var7 * 0.6F;
		 this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F);
		 this.bipedLeftArm.rotateAngleX = -(float)Math.PI;
		 this.bipedRightArm.rotateAngleX -= var7 * 1.2F - var8 * 0.4F;
		 this.bipedLeftArm.rotateAngleX -= var7 * 1.2F - var8 * 0.4F;
		 this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.26F) * 0.15F + 0.05F;
		 this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.26F) * 0.15F + 0.05F;
		 this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.167F) * 0.15F;
		 this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.167F) * 0.15F;
		 this.bipedHead.rotationPointY = -4.0F;
		 this.bipedRightLeg.rotationPointY = 9.5F;
		 this.bipedLeftLeg.rotationPointY = 9.5F;
	 }

	 private void setRotation(ModelRenderer par1ModelRenderer, float par2, float par3, float par4)
	 {
		 par1ModelRenderer.rotateAngleX = par2;
		 par1ModelRenderer.rotateAngleY = par3;
		 par1ModelRenderer.rotateAngleZ = par4;
	 }
}
