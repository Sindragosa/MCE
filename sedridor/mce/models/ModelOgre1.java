package sedridor.mce.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelOgre1 extends ModelBiped
{
	public ModelOgre1()
	{
		super(0.0F, 0.0F, 64, 32);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, -25.0F, -6.0F, 8, 8, 8, 0.0F);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedEars = new ModelRenderer(this, 41, 24);
		this.bipedEars.addBox(-4.0F, -31.0F, -3.0F, 8, 6, 1, 0.5F);
		this.bipedEars.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 16, 16);
		this.bipedBody.addBox(-4.0F, -14.0F, -2.0F, 8, 12, 4, 3.0F);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 0, 16);
		this.bipedRightArm.addBox(-7.0F, -2.0F, -2.0F, 1, 1, 1, 0.0F);
		this.bipedRightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 0, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
		this.bipedLeftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 40, 0);
		this.bipedRightLeg.addBox(-2.0F, -8.0F, -2.0F, 4, 18, 4, 1.5F);
		this.bipedRightLeg.setRotationPoint(-4.0F, 12.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 40, 0);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-2.0F, -8.0F, -2.0F, 4, 18, 4, 1.5F);
		this.bipedLeftLeg.setRotationPoint(4.0F, 12.0F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	 @Override
	 public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	 {
		 this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		 this.bipedHead.render(par7);
		 this.bipedBody.render(par7);
		 this.bipedRightLeg.render(par7);
		 this.bipedLeftLeg.render(par7);
		 this.bipedEars.render(par7);
	 }

	 /**
	  * Sets the models various rotation angles.
	  */
	 @Override
	 public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	 {
		 this.bipedHead.rotateAngleY = par4 / (180F / (float)Math.PI);
		 this.bipedEars.rotateAngleY = this.bipedHead.rotateAngleY;
		 this.bipedEars.rotateAngleX = this.bipedHead.rotateAngleX;
		 this.bipedRightArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F;
		 this.bipedLeftArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F;
		 this.bipedRightArm.rotateAngleZ = 0.0F;
		 this.bipedLeftArm.rotateAngleZ = 0.0F;
		 this.bipedRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		 this.bipedLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		 this.bipedRightLeg.rotateAngleY = 0.0F;
		 this.bipedLeftLeg.rotateAngleY = 0.0F;

		 if (this.isRiding)
		 {
			 this.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
			 this.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
			 this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
			 this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
			 this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
			 this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
		 }

		 if (this.heldItemLeft != 0)
		 {
			 this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
		 }

		 if (this.heldItemRight != 0)
		 {
			 this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
		 }

		 this.bipedRightArm.rotateAngleY = 0.0F;
		 this.bipedLeftArm.rotateAngleY = 0.0F;

		 if (this.onGround > -9990.0F)
		 {
			 float var7 = this.onGround;
			 this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI * 2.0F) * 0.2F;
			 this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			 this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			 this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			 this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			 this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			 this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			 this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
			 var7 = 1.0F - this.onGround;
			 var7 *= var7;
			 var7 *= var7;
			 var7 = 1.0F - var7;
			 float var8 = MathHelper.sin(var7 * (float)Math.PI);
			 float var9 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
			 ModelRenderer var10 = this.bipedRightArm;
			 var10.rotateAngleX = (float)(var10.rotateAngleX - (var8 * 1.2D + var9));
			 this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			 this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
		 }

		 if (this.isSneak)
		 {
			 this.bipedBody.rotateAngleX = 0.5F;
			 this.bipedRightLeg.rotateAngleX -= 0.0F;
			 this.bipedLeftLeg.rotateAngleX -= 0.0F;
			 this.bipedRightArm.rotateAngleX += 0.4F;
			 this.bipedLeftArm.rotateAngleX += 0.4F;
			 this.bipedRightLeg.rotationPointZ = 4.0F;
			 this.bipedLeftLeg.rotationPointZ = 4.0F;
			 this.bipedRightLeg.rotationPointY = 9.0F;
			 this.bipedLeftLeg.rotationPointY = 9.0F;
			 this.bipedHead.rotationPointY = 1.0F;
		 }
		 else
		 {
			 this.bipedBody.rotateAngleX = 0.0F;
			 this.bipedRightLeg.rotationPointZ = 0.0F;
			 this.bipedLeftLeg.rotationPointZ = 0.0F;
			 this.bipedRightLeg.rotationPointY = 12.0F;
			 this.bipedLeftLeg.rotationPointY = 12.0F;
			 this.bipedHead.rotationPointY = 0.0F;
		 }

		 this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		 this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		 this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
		 this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
	 }
}
