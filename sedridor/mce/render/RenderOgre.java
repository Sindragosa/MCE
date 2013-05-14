package sedridor.mce.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

import sedridor.mce.entities.*;
import sedridor.mce.models.*;

@SideOnly(Side.CLIENT)
public class RenderOgre extends RenderLiving
{
	public RenderOgre(ModelOgre2 par1ModelOgre2, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelOgre2, par3);
		this.setRenderPassModel(par2ModelBase);
	}

	protected int setWoolColorAndRender(EntityOgre par1EntityOgre2, int par2)
	{
		this.loadTexture("/MCE/mobs/OgreB.png");
		return 1;
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
	{
		return this.setWoolColorAndRender((EntityOgre)par1EntityLiving, par2);
	}
}
