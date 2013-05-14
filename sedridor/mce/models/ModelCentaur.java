package sedridor.mce.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelCentaur extends ModelSkeleton
{
	ModelRenderer body = (new ModelRenderer(this, 0, 40)).setTextureSize(64, 64);
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer tail;
	ModelRenderer torso;
	ModelRenderer head;
	ModelRenderer armleft;
	ModelRenderer helm;

	public ModelCentaur()
	{
		this.body.addBox(-5.0F, -2.0F, -9.0F, 10, 6, 18);
		this.body.setRotationPoint(0.0F, 8.0F, -1.0F);
		this.setRotation(this.body, 0.0F, 0.0F, 0.0F);
		this.leg1 = (new ModelRenderer(this, 0, 40)).setTextureSize(64, 64);
		this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
		this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
		this.setRotation(this.leg1, 0.0F, 0.0F, 0.0F);
		this.leg2 = (new ModelRenderer(this, 0, 40)).setTextureSize(64, 64);
		this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
		this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
		this.setRotation(this.leg2, 0.0F, 0.0F, 0.0F);
		this.leg2.mirror = true;
		this.leg3 = (new ModelRenderer(this, 0, 23)).setTextureSize(64, 64);
		this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
		this.leg3.setRotationPoint(-3.0F, 12.0F, -7.0F);
		this.setRotation(this.leg3, 0.0F, 0.0F, 0.0F);
		this.leg4 = (new ModelRenderer(this, 0, 23)).setTextureSize(64, 64);
		this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
		this.leg4.setRotationPoint(3.0F, 12.0F, -7.0F);
		this.setRotation(this.leg4, 0.0F, 0.0F, 0.0F);
		this.leg4.mirror = true;
		this.tail = (new ModelRenderer(this, 58, 55)).setTextureSize(64, 64);
		this.tail.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 1);
		this.tail.setRotationPoint(0.0F, 7.0F, 7.0F);
		this.setRotation(this.tail, ((float)Math.PI / 4F), 0.0F, 0.0F);
		this.torso = (new ModelRenderer(this, 17, 23)).setTextureSize(64, 64);
		this.torso.addBox(-5.0F, -10.0F, -2.0F, 8, 12, 4);
		this.torso.setRotationPoint(1.0F, 4.0F, -8.0F);
		this.setRotation(this.torso, 0.0F, 0.0F, 0.0F);
		this.head = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
		this.head.setRotationPoint(0.0F, -6.0F, -8.0F);
		this.setRotation(this.head, 0.0F, 0.0F, 0.0F);
		this.bipedRightArm = (new ModelRenderer(this, 42, 23)).setTextureSize(64, 64);
		this.bipedRightArm.addBox(-1.0F, -1.0F, -2.0F, 4, 12, 4);
		this.bipedRightArm.setRotationPoint(5.0F, -5.0F, -8.0F);
		this.setRotation(this.bipedRightArm, 0.0F, 0.0F, 0.0F);
		this.armleft = (new ModelRenderer(this, 39, 40)).setTextureSize(64, 64);
		this.armleft.addBox(-3.0F, -1.0F, -2.0F, 4, 12, 4);
		this.armleft.setRotationPoint(-5.0F, -5.0F, -8.0F);
		this.setRotation(this.armleft, 0.0F, 0.0F, 0.0F);
		this.helm = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
		this.helm.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
		this.helm.setRotationPoint(0.0F, -6.0F, -8.0F);
		this.setRotation(this.helm, 0.0F, 0.0F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	 @Override
	 public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7)
	{
		this.setRotationAngles(var2, var3, var4, var5, var6, var7);
		this.body.render(var7);
		this.leg1.render(var7);
		this.leg2.render(var7);
		this.leg3.render(var7);
		this.leg4.render(var7);
		this.tail.render(var7);
		this.torso.render(var7);
		this.head.render(var7);
		this.bipedRightArm.render(var7);
		this.armleft.render(var7);
		this.helm.render(var7);
	}

	 /**
	  * Sets the models various rotation angles.
	  */
	 public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6)
	 {
		 this.head.rotateAngleX = var5 / (180F / (float)Math.PI);
		 this.head.rotateAngleY = var4 / (180F / (float)Math.PI);
		 this.helm.rotateAngleX = this.head.rotateAngleX;
		 this.helm.rotateAngleY = this.head.rotateAngleY;
		 this.leg1.rotateAngleX = MathHelper.cos(var1 * 0.6662F) * 1.4F * var2;
		 this.leg2.rotateAngleX = MathHelper.cos(var1 * 0.6662F + (float)Math.PI) * 1.4F * var2;
		 this.leg3.rotateAngleX = MathHelper.cos(var1 * 0.6662F + (float)Math.PI) * 1.4F * var2;
		 this.leg4.rotateAngleX = MathHelper.cos(var1 * 0.6662F) * 1.4F * var2;
		 this.bipedRightArm.rotateAngleX = MathHelper.cos(var1 * 0.6662F + (float)Math.PI) * 2.0F * var2 * 0.5F;
		 this.armleft.rotateAngleX = MathHelper.cos(var1 * 0.6662F) * 2.0F * var2 * 0.5F;
	 }

	 private void setRotation(ModelRenderer var1, float var2, float var3, float var4)
	 {
		 var1.rotateAngleX = var2;
		 var1.rotateAngleY = var3;
		 var1.rotateAngleZ = var4;
	 }
}
