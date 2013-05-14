package sedridor.mce.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import sedridor.mce.render.*;

public class ModelNagaMale extends ModelBiped
{
	ModelRenderer leg;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer leg5;

	public ModelNagaMale()
	{
		this(0.0F);
	}

	public ModelNagaMale(float par1)
	{
		this(par1, 0.0F, 64, 32);
	}

	public ModelNagaMale(float par1, float par2, int par3, int par4)
	{
		super(par1, par2, par3, par4);
		this.bipedRightLeg = new ModelRenderer(this, 0, 0);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 0);
		this.leg = new ModelRenderer(this, 0, 0);
		ModelRenderer var3 = new ModelRenderer(this, 0, 16);
		var3.addBox(0.0F, -2.0F, -2.0F, 4, 4, 4);
		var3.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.leg.addChild(var3);
		var3 = new ModelRenderer(this, 0, 16);
		var3.mirror = true;
		var3.addBox(0.0F, -2.0F, -2.0F, 4, 4, 4);
		this.leg.addChild(var3);
		this.leg2 = new ModelRenderer(this, 0, 0);
		this.leg2.childModels = this.leg.childModels;
		this.leg3 = new ModelRenderer(this, 0, 0);
		PlaneRenderer var4 = new PlaneRenderer(this, 4, 24);
		var4.addBackPlane(0.0F, -2.0F, 0.0F, 4, 4);
		var4.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 4, 24);
		var4.mirror = true;
		var4.addBackPlane(0.0F, -2.0F, 0.0F, 4, 4);
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 8, 24);
		var4.addBackPlane(0.0F, -2.0F, 6.0F, 4, 4);
		var4.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 8, 24);
		var4.mirror = true;
		var4.addBackPlane(0.0F, -2.0F, 6.0F, 4, 4);
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 4, 26);
		var4.addTopPlane(0.0F, -2.0F, -6.0F, 4, 6);
		var4.setRotationPoint(-4.0F, 0.0F, 0.0F);
		var4.rotateAngleX = (float)Math.PI;
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 4, 26);
		var4.mirror = true;
		var4.addTopPlane(0.0F, -2.0F, -6.0F, 4, 6);
		var4.rotateAngleX = (float)Math.PI;
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 8, 26);
		var4.addTopPlane(0.0F, -2.0F, 0.0F, 4, 6);
		var4.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 8, 26);
		var4.mirror = true;
		var4.addTopPlane(0.0F, -2.0F, 0.0F, 4, 6);
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 0, 26);
		var4.rotateAngleX = ((float)Math.PI / 2F);
		var4.addSidePlane(0.0F, 0.0F, -2.0F, 6, 4);
		var4.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.leg3.addChild(var4);
		var4 = new PlaneRenderer(this, 0, 26);
		var4.rotateAngleX = ((float)Math.PI / 2F);
		var4.addSidePlane(4.0F, 0.0F, -2.0F, 6, 4);
		this.leg3.addChild(var4);
		this.leg4 = new ModelRenderer(this, 0, 0);
		this.leg4.childModels = this.leg3.childModels;
		this.leg5 = new ModelRenderer(this, 0, 0);
		var3 = new ModelRenderer(this, 56, 20);
		var3.addBox(0.0F, 0.0F, -2.0F, 2, 5, 2);
		var3.setRotationPoint(-2.0F, 0.0F, 0.0F);
		var3.rotateAngleX = ((float)Math.PI / 2F);
		this.leg5.addChild(var3);
		var3 = new ModelRenderer(this, 56, 20);
		var3.mirror = true;
		var3.addBox(0.0F, 0.0F, -2.0F, 2, 5, 2);
		var3.rotateAngleX = ((float)Math.PI / 2F);
		this.leg5.addChild(var3);
		this.defaultRotation();
	}

	private void defaultRotation()
	{
		this.leg.setRotationPoint(0.0F, 14.0F, 0.0F);
		this.leg2.setRotationPoint(0.0F, 18.0F, 0.6F);
		this.leg3.setRotationPoint(0.0F, 22.0F, -0.3F);
		this.leg4.setRotationPoint(0.0F, 22.0F, 5.0F);
		this.leg5.setRotationPoint(0.0F, 22.0F, 10.0F);
		this.leg.rotateAngleX = 0.0F;
		this.leg2.rotateAngleX = 0.0F;
		this.leg3.rotateAngleX = 0.0F;
		this.leg4.rotateAngleX = 0.0F;
		this.leg5.rotateAngleX = 0.0F;
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	 @Override
	 public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		this.leg.render(par7);
		this.leg3.render(par7);

		if (!this.isRiding)
		{
			this.leg2.render(par7);
		}

		GL11.glPushMatrix();
		GL11.glScalef(0.64F, 0.7F, 0.85F);
		GL11.glTranslatef(this.leg3.rotateAngleY, 0.66F, 0.06F);
		this.leg4.render(par7);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(this.leg3.rotateAngleY + this.leg4.rotateAngleY, 0.0F, 0.0F);
		this.leg5.render(par7);
		GL11.glPopMatrix();
	}

	 public void setExtraOption1(int par1)
	 {
		 ModelRenderer var2;

		 if (par1 == 0)
		 {
			 var2 = new ModelRenderer(this, 24, 0);
			 var2.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1);
			 var2.setRotationPoint(-2.0F, -3.0F, -5.0F);
			 this.bipedHead.addChild(var2);
		 }
		 else if (par1 == 1)
		 {
			 var2 = new ModelRenderer(this, 24, 0);
			 var2.addBox(0.0F, 0.0F, 0.0F, 4, 3, 2);
			 var2.setRotationPoint(-2.0F, -3.0F, -6.0F);
			 this.bipedHead.addChild(var2);
		 }
		 else if (par1 == 2)
		 {
			 var2 = new ModelRenderer(this, 24, 0);
			 var2.addBox(0.0F, 0.0F, 0.0F, 4, 3, 3);
			 var2.setRotationPoint(-2.0F, -3.0F, -7.0F);
			 this.bipedHead.addChild(var2);
		 }
	 }

	 /**
	  * Sets the models various rotation angles.
	  */
	  @Override
	  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	  {
		  super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		  this.leg.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 0.26F * par2;
		  this.leg2.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 0.5F * par2;
		  this.leg3.rotateAngleY = MathHelper.cos(par1 * 0.6662F) * 0.26F * par2;
		  this.leg4.rotateAngleY = -MathHelper.cos(par1 * 0.6662F) * 0.16F * par2;
		  this.leg5.rotateAngleY = -MathHelper.cos(par1 * 0.6662F) * 0.3F * par2;
		  this.defaultRotation();

		  if (this.isRiding)
		  {
			  --this.leg.rotationPointY;
			  this.leg.rotateAngleX = -0.19634955F;
			  this.leg.rotationPointZ = -1.0F;
			  this.leg2.rotationPointY -= 4.0F;
			  this.leg2.rotationPointZ = -1.0F;
			  this.leg3.rotationPointY -= 9.0F;
			  --this.leg3.rotationPointZ;
			  this.leg4.rotationPointY -= 13.0F;
			  --this.leg4.rotationPointZ;
			  this.leg5.rotationPointY -= 9.0F;
			  --this.leg5.rotationPointZ;
		  }
	  }
}
