package sedridor.mce.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelGoblinRanger extends ModelBase
{
	public ModelRenderer quiver;
	public ModelRenderer bipedHead = new ModelRenderer(this, 0, 0);
	public ModelRenderer bipedBody;
	public ModelRenderer bipedRightArm;
	public ModelRenderer bipedLeftArm;
	public ModelRenderer bipedRightLeg;
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer ear1;
	public ModelRenderer ear2;
	public ModelRenderer nose1;
	public ModelRenderer nose2;

	public ModelGoblinRanger()
	{
		this.bipedHead.addBox(-2.0F, -5.0F, -2.0F, 5, 5, 4, 0.0F);
		this.bipedHead.setRotationPoint(0.0F, 9.0F, -1.0F);
		this.bipedHead.rotateAngleX = 0.0F;
		this.bipedHead.rotateAngleY = 0.0F;
		this.bipedHead.rotateAngleZ = 0.0F;
		this.bipedHead.mirror = false;
		this.bipedBody = new ModelRenderer(this, 0, 9);
		this.bipedBody.addBox(-3.0F, 0.0F, -2.0F, 7, 9, 4, 0.0F);
		this.bipedBody.setRotationPoint(0.0F, 9.0F, -1.0F);
		this.bipedBody.rotateAngleX = 0.0F;
		this.bipedBody.rotateAngleY = 0.0F;
		this.bipedBody.rotateAngleZ = 0.0F;
		this.bipedBody.mirror = false;
		this.bipedRightArm = new ModelRenderer(this, 0, 20);
		this.bipedRightArm.addBox(-2.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
		this.bipedRightArm.setRotationPoint(-3.0F, 9.0F, -1.0F);
		this.bipedRightArm.rotateAngleX = 0.0F;
		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedRightArm.mirror = false;
		this.bipedLeftArm = new ModelRenderer(this, 0, 20);
		this.bipedLeftArm.addBox(0.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
		this.bipedLeftArm.setRotationPoint(4.0F, 9.0F, -1.0F);
		this.bipedLeftArm.rotateAngleX = 0.0F;
		this.bipedLeftArm.rotateAngleY = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.mirror = false;
		this.bipedRightLeg = new ModelRenderer(this, 16, 20);
		this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 3, 6, 2, 0.0F);
		this.bipedRightLeg.setRotationPoint(-2.0F, 18.0F, -1.0F);
		this.bipedRightLeg.rotateAngleX = 0.0F;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedRightLeg.rotateAngleZ = 0.0F;
		this.bipedRightLeg.mirror = false;
		this.bipedLeftLeg = new ModelRenderer(this, 16, 20);
		this.bipedLeftLeg.addBox(-2.0F, 0.0F, -1.0F, 3, 6, 2, 0.0F);
		this.bipedLeftLeg.setRotationPoint(3.0F, 18.0F, -1.0F);
		this.bipedLeftLeg.rotateAngleX = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleZ = 0.0F;
		this.bipedLeftLeg.mirror = true;
		this.ear1 = new ModelRenderer(this, 18, 8);
		this.ear1.addBox(3.0F, -7.0F, 0.0F, 1, 4, 1, 0.0F);
		this.ear1.setRotationPoint(0.0F, 9.0F, -1.0F);
		this.ear1.rotateAngleX = 0.0F;
		this.ear1.rotateAngleY = 0.0F;
		this.ear1.rotateAngleZ = 0.0F;
		this.ear1.mirror = false;
		this.ear2 = new ModelRenderer(this, 18, 8);
		this.ear2.addBox(-3.0F, -7.0F, 0.0F, 1, 4, 1, 0.0F);
		this.ear2.setRotationPoint(0.0F, 9.0F, -1.0F);
		this.ear2.rotateAngleX = 0.0F;
		this.ear2.rotateAngleY = 0.0F;
		this.ear2.rotateAngleZ = 0.0F;
		this.ear2.mirror = false;
		this.nose1 = new ModelRenderer(this, 8, 26);
		this.nose1.addBox(0.0F, -3.0F, -3.0F, 1, 1, 1, 0.0F);
		this.nose1.setRotationPoint(0.0F, 9.0F, -1.0F);
		this.nose1.rotateAngleX = 0.0F;
		this.nose1.rotateAngleY = 0.0F;
		this.nose1.rotateAngleZ = 0.0F;
		this.nose1.mirror = false;
		this.nose2 = new ModelRenderer(this, 8, 22);
		this.nose2.addBox(0.0F, -2.0F, -5.0F, 1, 1, 3, 0.0F);
		this.nose2.setRotationPoint(0.0F, 9.0F, -1.0F);
		this.nose2.rotateAngleX = 0.0F;
		this.nose2.rotateAngleY = 0.0F;
		this.nose2.rotateAngleZ = 0.0F;
		this.nose2.mirror = false;
		this.quiver = new ModelRenderer(this, 21, 0);
		this.quiver.addBox(-1.0F, 0.0F, 1.0F, 3, 7, 2, 0.0F);
		this.quiver.setRotationPoint(0.0F, 10.0F, -1.0F);
		this.quiver.rotateAngleX = 0.0F;
		this.quiver.rotateAngleY = 0.0F;
		this.quiver.rotateAngleZ = 0.0F;
		this.quiver.mirror = false;
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
		this.bipedRightArm.render(par7);
		this.bipedLeftArm.render(par7);
		this.bipedRightLeg.render(par7);
		this.bipedLeftLeg.render(par7);
		this.ear1.render(par7);
		this.ear2.render(par7);
		this.nose1.render(par7);
		this.nose2.render(par7);
		this.quiver.render(par7);
	}

	 /**
	  * Sets the models various rotation angles.
	  */
	 @Override
	 public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	 {
		 super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		 this.bipedHead.rotateAngleY = par4 / (180F / (float)Math.PI);
		 this.bipedHead.rotateAngleX = par5 / (180F / (float)Math.PI);
		 this.ear1.rotateAngleY = this.bipedHead.rotateAngleY;
		 this.ear1.rotateAngleX = this.bipedHead.rotateAngleX;
		 this.ear2.rotateAngleY = this.bipedHead.rotateAngleY;
		 this.ear2.rotateAngleX = this.bipedHead.rotateAngleX;
		 this.nose1.rotateAngleY = this.bipedHead.rotateAngleY;
		 this.nose1.rotateAngleX = this.bipedHead.rotateAngleX;
		 this.nose2.rotateAngleY = this.bipedHead.rotateAngleY;
		 this.nose2.rotateAngleX = this.bipedHead.rotateAngleX;
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

		 this.bipedRightArm.rotateAngleY = 0.0F;
		 this.bipedLeftArm.rotateAngleY = 0.0F;

		 if (this.onGround > -9990.0F)
		 {
			 float var7 = this.onGround;
			 this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(var7) * (float)Math.PI * 2.0F) * 0.2F;
			 this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			 this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			 this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
			 var7 = 1.0F - this.onGround;
			 var7 *= var7;
			 var7 *= var7;
			 var7 = 1.0F - var7;
			 float var8 = MathHelper.sin(var7 * (float)Math.PI);
			 float var9 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
			 this.bipedRightArm.rotateAngleX = (float)(this.bipedRightArm.rotateAngleX - (var8 * 1.2D + var9));
			 this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			 this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
		 }

		 this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		 this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
		 this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
		 this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
	 }
}
