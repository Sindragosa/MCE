package sedridor.mce.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import sedridor.mce.render.*;

public class ModelElfFemale extends ModelBiped
{
	public PlaneRenderer breastsTop;
	public PlaneRenderer breastsFront;
	public PlaneRenderer breastsTop2;
	public PlaneRenderer breastsFront2;
	public PlaneRenderer breastsBottom;
	public PlaneRenderer breastsLeft;
	public PlaneRenderer breastsRight;
	public PlaneRenderer breastsLeft2;
	public PlaneRenderer breastsRight2;
	public PlaneRenderer hair;
	private int breastsType;

	public ModelElfFemale()
	{
		this(0.0F, 0.0F, 64, 32, 2);
	}

	public ModelElfFemale(float par1, float par2, int par3, int par4, int par5)
	{
		super(par1, par2, par3, par4);
		this.breastsType = par5;

		if (par5 == 2)
		{
			this.breastsTop = new PlaneRenderer(this, 56, 0);
			this.breastsTop.addTopPlane(0.0F, 0.0F, 0.0F, 7, 0, 1, par1);
			this.breastsTop.setRotationPoint(-3.5F, 1.8F, -2.7F);
			this.bipedBody.addChild(this.breastsTop);
			this.breastsFront = new PlaneRenderer(this, 56, 1);
			this.breastsFront.addBackPlane(0.0F, 0.0F, 0.0F, 7, 1, 0, par1);
			this.breastsFront.setRotationPoint(-3.5F, 1.8F, -2.7F);
			this.bipedBody.addChild(this.breastsFront);
			this.breastsTop2 = new PlaneRenderer(this, 56, 2);
			this.breastsTop2.addTopPlane(0.0F, 0.0F, 0.0F, 7, 0, 1, par1);
			this.breastsTop2.setRotationPoint(-3.5F, 2.8F, -3.7F);
			this.bipedBody.addChild(this.breastsTop2);
			this.breastsFront2 = new PlaneRenderer(this, 56, 3);
			this.breastsFront2.addBackPlane(0.0F, 0.0F, 0.0F, 7, 2, 0, par1);
			this.breastsFront2.setRotationPoint(-3.5F, 2.8F, -3.7F);
			this.bipedBody.addChild(this.breastsFront2);
			this.breastsBottom = new PlaneRenderer(this, 56, 6);
			this.breastsBottom.addTopPlane(-3.5F, -1.5F, -1.0F, 7, 3, 2, par1);
			this.breastsBottom.setRotationPoint(0.0F, 3.3F, -2.7F);
			this.breastsBottom.rotateAngleZ = (float)Math.PI;
			this.bipedBody.addChild(this.breastsBottom);
			this.breastsLeft = new PlaneRenderer(this, 63, 0);
			this.breastsLeft.addSidePlane(0.0F, 0.0F, 0.0F, 0, 1, 1, par1);
			this.breastsLeft.setRotationPoint(3.5F, 1.8F, -2.7F);
			this.bipedBody.addChild(this.breastsLeft);
			this.breastsRight = new PlaneRenderer(this, 63, 3);
			this.breastsRight.addSidePlane(0.0F, 0.0F, 0.0F, 7, 1, 1, par1);
			this.breastsRight.setRotationPoint(3.5F, 1.8F, -1.7F);
			this.breastsRight.rotateAngleY = (float)Math.PI;
			this.bipedBody.addChild(this.breastsRight);
			this.breastsLeft2 = new PlaneRenderer(this, 62, 0);
			this.breastsLeft2.addSidePlane(0.0F, 0.0F, 0.0F, 0, 2, 2, par1);
			this.breastsLeft2.setRotationPoint(3.5F, 2.8F, -3.7F);
			this.bipedBody.addChild(this.breastsLeft2);
			this.breastsRight2 = new PlaneRenderer(this, 62, 3);
			this.breastsRight2.addSidePlane(0.0F, 0.0F, 0.0F, 7, 2, 2, par1);
			this.breastsRight2.setRotationPoint(3.5F, 2.8F, -1.7F);
			this.breastsRight2.rotateAngleY = (float)Math.PI;
			this.bipedBody.addChild(this.breastsRight2);
		}
		else
		{
			this.breastsTop = new PlaneRenderer(this, 56, 0);
			this.breastsTop.addTopPlane(0.0F, 0.0F, 0.0F, 7, 0, 1, par1);
			this.breastsTop.setRotationPoint(-3.5F, 1.8F, -2.7F);
			this.bipedBody.addChild(this.breastsTop);
			this.breastsFront = new PlaneRenderer(this, 56, 1);
			this.breastsFront.addBackPlane(0.0F, 0.0F, 0.0F, 7, 3, 0, par1);
			this.breastsFront.setRotationPoint(-3.5F, 1.8F, -2.7F);
			this.bipedBody.addChild(this.breastsFront);
			this.breastsBottom = new PlaneRenderer(this, 56, 4);
			this.breastsBottom.addTopPlane(-3.5F, -1.5F, -0.5F, 7, 3, 1, par1);
			this.breastsBottom.setRotationPoint(0.0F, 3.3F, -2.2F);
			this.breastsBottom.rotateAngleZ = (float)Math.PI;
			this.bipedBody.addChild(this.breastsBottom);
			this.breastsLeft = new PlaneRenderer(this, 63, 0);
			this.breastsLeft.addSidePlane(0.0F, 0.0F, 0.0F, 0, 3, 1, par1);
			this.breastsLeft.setRotationPoint(3.5F, 1.8F, -2.7F);
			this.bipedBody.addChild(this.breastsLeft);
			this.breastsRight = new PlaneRenderer(this, 63, 3);
			this.breastsRight.addSidePlane(0.0F, 0.0F, 0.0F, 7, 3, 1, par1);
			this.breastsRight.setRotationPoint(3.5F, 1.8F, -1.7F);
			this.breastsRight.rotateAngleY = (float)Math.PI;
			this.bipedBody.addChild(this.breastsRight);
		}
		this.hair = new PlaneRenderer(this, 56, 20);
		this.hair.addBackPlane(-4.0F, 0.0F, 4.0F, 8, 12);
		this.hair.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bipedHead.addChild(this.hair);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	 @Override
	 public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		float var8 = 0.85F;
		GL11.glPushMatrix();
		GL11.glScalef(var8, var8 - 0.1F, var8);
		GL11.glTranslatef(0.0F, -0.015F, 0.0F);
		this.bipedHead.render(par7);
		this.bipedHeadwear.render(par7);
		GL11.glPopMatrix();
		var8 = 0.74F;
		GL11.glPushMatrix();
		GL11.glScalef(var8, 0.9F, var8);
		GL11.glTranslatef(0.09F, 0.0F, 0.0F);
		this.bipedLeftArm.render(par7);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(var8, 0.9F, var8);
		GL11.glTranslatef(-0.09F, 0.0F, 0.0F);
		this.bipedRightArm.render(par7);
		GL11.glPopMatrix();
		this.bipedBody.render(par7);
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

		 if (this.hair != null)
		 {
			 if (this.bipedHead.rotateAngleX < 0.0F)
			 {
				 this.hair.rotateAngleX = -this.bipedHead.rotateAngleX;

				 if (this.bipedHead.rotateAngleX > -1.0F)
				 {
					 this.hair.rotationPointY = -this.bipedHead.rotateAngleX * 1.5F;
					 this.hair.rotationPointZ = -this.bipedHead.rotateAngleX * 1.5F;
				 }
			 }
			 else
			 {
				 this.hair.rotateAngleX = 0.0F;
				 this.hair.rotationPointY = 0.0F;
				 this.hair.rotationPointZ = 0.0F;
			 }
		 }
	 }

	 public void showBreasts(boolean par1)
	 {
		 this.breastsTop.showModel = par1;
		 this.breastsFront.showModel = par1;
		 this.breastsBottom.showModel = par1;
		 this.breastsLeft.showModel = par1;
		 this.breastsRight.showModel = par1;
		 if (this.breastsType == 2)
		 {
			 this.breastsTop2.showModel = par1;
			 this.breastsFront2.showModel = par1;
			 this.breastsLeft2.showModel = par1;
			 this.breastsRight2.showModel = par1;
		 }
	 }
}
