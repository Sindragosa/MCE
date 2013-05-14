package sedridor.mce.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelPygmy extends ModelSkeleton
{
	public ModelRenderer bipedHead;
	public ModelRenderer bipedHeadwear;
	public ModelRenderer bipedRightLeg = new ModelRenderer(this, 27, 16);
	public ModelRenderer bipedLeftLeg;
	public ModelRenderer stomach;

	public ModelPygmy()
	{
		this.bipedRightLeg.addBox(-2.0F, 0.0F, -1.0F, 3, 5, 3);
		this.bipedRightLeg.setRotationPoint(-1.0F, 19.0F, 0.0F);
		this.setRotation(this.bipedRightLeg, 0.0F, 0.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 27, 24);
		this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 3, 5, 3);
		this.bipedLeftLeg.setRotationPoint(1.0F, 19.0F, 0.0F);
		this.setRotation(this.bipedLeftLeg, 0.0F, 0.0F, 0.0F);
		this.stomach = new ModelRenderer(this, 42, 22);
		this.stomach.addBox(-3.0F, 0.0F, -3.0F, 6, 5, 5);
		this.stomach.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.setRotation(this.stomach, 0.0F, 0.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 43, 16);
		this.bipedBody.addBox(-3.0F, -3.0F, -1.0F, 6, 3, 3);
		this.bipedBody.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.setRotation(this.bipedBody, 0.0F, 0.0F, 0.0F);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
		this.bipedHead.setRotationPoint(0.0F, 11.0F, 0.0F);
		this.setRotation(this.bipedHead, 0.0F, 0.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 13, 20);
		this.bipedRightArm.addBox(-3.0F, -1.0F, -1.0F, 3, 9, 3);
		this.bipedRightArm.setRotationPoint(-3.0F, 12.0F, 0.0F);
		this.setRotation(this.bipedRightArm, -1.0F, 0.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 0, 20);
		this.bipedLeftArm.addBox(0.0F, -1.0F, -1.0F, 3, 9, 3);
		this.bipedLeftArm.setRotationPoint(3.0F, 12.0F, 0.0F);
		this.setRotation(this.bipedLeftArm, 0.0F, 0.0F, 0.0F);
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		this.bipedHeadwear.setRotationPoint(0.0F, 11.0F, 0.0F);
		this.setRotation(this.bipedHeadwear, 0.0F, 0.0F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	 @Override
	 public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7)
	{
		 this.setRotationAngles(var2, var3, var4, var5, var6, var7);
		 this.stomach.render(var7);
		 this.bipedHead.render(var7);
		 this.bipedBody.render(var7);
		 this.bipedRightArm.render(var7);
		 this.bipedLeftArm.render(var7);
		 this.bipedRightLeg.render(var7);
		 this.bipedLeftLeg.render(var7);
		 this.bipedHeadwear.render(var7);
	}

	 private void setRotation(ModelRenderer var1, float var2, float var3, float var4)
	 {
		 var1.rotateAngleX = var2;
		 var1.rotateAngleY = var3;
		 var1.rotateAngleZ = var4;
	 }

	 /**
	  * Sets the models various rotation angles.
	  */
	 public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6)
	 {
		 this.bipedHead.rotateAngleY = var4 / (180F / (float)Math.PI);
		 this.bipedHead.rotateAngleX = var5 / (180F / (float)Math.PI);
		 this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		 this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		 this.bipedLeftArm.rotateAngleX = MathHelper.cos(var1 * 0.6662F) * 2.0F * var2 * 0.5F;
		 this.bipedLeftArm.rotateAngleZ = 0.0F;
		 this.bipedRightLeg.rotateAngleX = MathHelper.cos(var1 * 0.6662F) * 1.4F * var2;
		 this.bipedLeftLeg.rotateAngleX = MathHelper.cos(var1 * 0.6662F + (float)Math.PI) * 1.4F * var2;
		 this.bipedRightLeg.rotateAngleY = 0.0F;
		 this.bipedLeftLeg.rotateAngleY = 0.0F;
		 float var7 = MathHelper.sin(this.onGround * (float)Math.PI);
		 float var8 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * (float)Math.PI);
		 this.bipedRightArm.rotateAngleZ = 0.0F;
		 this.bipedRightArm.rotateAngleY = -(0.1F - var7 * 0.6F);
		 this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F);
		 this.bipedRightArm.rotateAngleX -= var7 * 1.2F - var8 * 0.4F;
		 this.bipedRightArm.rotateAngleZ += MathHelper.cos(var3 * 0.09F) * 0.05F + 0.05F;
		 this.bipedRightArm.rotateAngleX += MathHelper.sin(var3 * 0.067F) * 0.05F;
	 }
}
