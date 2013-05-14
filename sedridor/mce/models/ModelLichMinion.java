package sedridor.mce.models;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import org.lwjgl.opengl.GL11;

import sedridor.mce.entities.*;

public class ModelLichMinion extends ModelZombie
{
	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4)
	{
		EntityLichMinion var5 = (EntityLichMinion)par1EntityLiving;
		if (var5.getActivePotionEffect(Potion.damageBoost) != null)
		{
			GL11.glColor3f(0.25F, 2.0F, 0.25F);
		}
		else
		{
			GL11.glColor3f(0.5F, 1.0F, 0.5F);
		}
	}
}
