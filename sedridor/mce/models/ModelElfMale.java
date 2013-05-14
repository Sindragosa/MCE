package sedridor.mce.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelElfMale extends ModelBiped
{
	public ModelElfMale()
	{
		this(0.0F);
	}

	public ModelElfMale(float par1)
	{
		this(par1, 0.0F, 64, 32);
	}

	public ModelElfMale(float par1, float par2, int par3, int par4)
	{
		super(par1, par2, par3, par4);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		GL11.glPushMatrix();
		GL11.glScalef(1.0F, 0.88F, 1.0F);
		this.bipedHead.render(par7);
		this.bipedHeadwear.render(par7);
		GL11.glPopMatrix();
		this.bipedLeftArm.render(par7);
		this.bipedRightArm.render(par7);
		this.bipedRightLeg.render(par7);
		this.bipedLeftLeg.render(par7);
		this.bipedBody.render(par7);
	}
}
