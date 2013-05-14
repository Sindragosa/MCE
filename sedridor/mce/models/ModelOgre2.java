package sedridor.mce.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelOgre2 extends ModelBiped
{
	public ModelOgre2()
	{
		super(0.0F, 0.0F, 64, 32);
		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(1.0F, -5.0F, -4.0F, 1, 1, 1, 0.0F);
		this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedEars = new ModelRenderer(this, 0, 0);
		this.bipedEars.addBox(-4.0F, -1.0F, -4.0F, 1, 1, 1, 0.5F);
		this.bipedEars.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedBody = new ModelRenderer(this, 0, 16);
		this.bipedBody.addBox(-6.0F, -3.0F, -5.0F, 12, 8, 8, 2.3F);
		this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 40, 0);
		this.bipedRightArm.addBox(-7.0F, -2.0F, -2.0F, 4, 18, 4, 1.5F);
		this.bipedRightArm.setRotationPoint(-5.0F, -14.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 40, 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(3.0F, -2.0F, -2.0F, 4, 18, 4, 1.5F);
		this.bipedLeftArm.setRotationPoint(5.0F, -14.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 0);
		this.bipedRightLeg.addBox(-2.0F, 9.0F, -6.0F, 4, 2, 8, 1.5F);
		this.bipedRightLeg.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 0);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-2.0F, 9.0F, -6.0F, 4, 2, 8, 1.5F);
		this.bipedLeftLeg.setRotationPoint(4.0F, 0.0F, 0.0F);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	 @Override
	 public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	 {
		 this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		 this.bipedBody.render(par7);
		 this.bipedRightArm.render(par7);
		 this.bipedLeftArm.render(par7);
		 this.bipedRightLeg.render(par7);
		 this.bipedLeftLeg.render(par7);
	 }

	 /**
	  * Sets the models various rotation angles.
	  */
	 @Override
	 public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	 {
		 super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		 this.bipedEars.rotateAngleY = this.bipedHead.rotateAngleY;
		 this.bipedEars.rotateAngleX = this.bipedHead.rotateAngleX;
	 }

}
