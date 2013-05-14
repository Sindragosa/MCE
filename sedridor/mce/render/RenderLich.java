package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

import sedridor.mce.entities.*;
import sedridor.mce.models.*;

@SideOnly(Side.CLIENT)
public class RenderLich extends RenderBiped
{
	public RenderLich(ModelBiped par1ModelBiped, float par2)
	{
		super(par1ModelBiped, par2);
		this.setRenderPassModel(new ModelLich(true));
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		EntityLich var4 = (EntityLich)par1EntityLiving;

		if (par2 == 2)
		{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			if (var4.isShadowClone())
			{
				float var5 = 0.33F;
				GL11.glColor4f(var5, var5, var5, 0.8F);
				return 2;
			}
			else
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				return 1;
			}
		}
		else
		{
			return 0;
		}
	}
}
