package sedridor.mce.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import sedridor.mce.render.*;

public class ModelDwarfMale extends ModelBiped
{
	public PlaneRenderer beard;

	public ModelDwarfMale()
	{
		this(0.0F);
	}

	public ModelDwarfMale(float par1)
	{
		this(par1, 0.0F, 64, 32);
	}

	public ModelDwarfMale(float par1, float par2, int par3, int par4)
	{
		super(par1, par2, par3, par4);
		this.beard = new PlaneRenderer(this, 56, 20);
		this.beard.addBackPlane(-4.0F, 0.0F, -4.0F, 8, 12);
		this.beard.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addChild(this.beard);
	}

	/**
	 * Sets the models various rotation angles.
	 */
	 @Override
	 public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	 {
		 super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);

		 if (this.beard != null)
		 {
			 if (this.bipedHead.rotateAngleX > 0.0F)
			 {
				 this.beard.rotateAngleX = -this.bipedHead.rotateAngleX;
			 }
			 else
			 {
				 this.beard.rotateAngleX = 0.0F;
			 }
		 }
	 }

	 /**
	  * Sets the models various rotation angles then renders the model.
	  */
	 @Override
	 public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	 {
		 this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		 this.bipedHead.render(par7);
		 this.bipedHeadwear.render(par7);
		 GL11.glPushMatrix();
		 GL11.glScalef(1.1F, 1.0F, 1.0F);
		 this.bipedLeftArm.render(par7);
		 this.bipedRightArm.render(par7);
		 this.bipedRightLeg.render(par7);
		 this.bipedLeftLeg.render(par7);
		 GL11.glPopMatrix();
		 GL11.glPushMatrix();
		 GL11.glScalef(1.1F, 1.0F, 1.55F);
		 this.bipedBody.render(par7);
		 GL11.glPopMatrix();
	 }
}
