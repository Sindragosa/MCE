package sedridor.mce;

import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EffectRendererMCE extends EffectRenderer
{
	/** Reference to the World object. */
	protected World worldObj;
	private Random rand = new Random();

	public EffectRendererMCE(World par1World, RenderEngine par2RenderEngine)
	{
		super(par1World, par2RenderEngine);
		if (par1World != null)
		{
			this.worldObj = par1World;
		}
	}

	@Override
	public void addBlockDestroyEffects(int par1, int par2, int par3, int par4, int par5)
	{
		if (par4 != 0)
		{
			Block var6 = Block.blocksList[par4];
			byte var7 = 4;

			for (int var8 = 0; var8 < var7; ++var8)
			{
				for (int var9 = 0; var9 < var7; ++var9)
				{
					for (int var10 = 0; var10 < var7; ++var10)
					{
						double var11 = par1 + (var8 + 0.5D) / var7;
						double var13 = par2 + (var9 + 0.5D) / var7;
						double var15 = par3 + (var10 + 0.5D) / var7;
						int var17 = this.rand.nextInt(6);
						//this.addEffect((new EntityDiggingFX_MCE(this.worldObj, var11, var13, var15, var11 - (double)par1 - 0.5D, var13 - (double)par2 - 0.5D, var15 - (double)par3 - 0.5D, var6, var17, par5)).func_70596_a(par1, par2, par3));
					}
				}
			}
		}
	}

	/**
	 * Adds block hit particles for the specified block. Args: x, y, z, sideHit
	 */
	@Override
	public void addBlockHitEffects(int par1, int par2, int par3, int par4)
	{
		int var5 = this.worldObj.getBlockId(par1, par2, par3);

		if (var5 != 0)
		{
			Block var6 = Block.blocksList[var5];
			float var7 = 0.1F;
			double var8 = par1 + this.rand.nextDouble() * (var6.getBlockBoundsMaxX() - var6.getBlockBoundsMinX() - var7 * 2.0F) + var7 + var6.getBlockBoundsMinX();
			double var10 = par2 + this.rand.nextDouble() * (var6.getBlockBoundsMaxY() - var6.getBlockBoundsMinY() - var7 * 2.0F) + var7 + var6.getBlockBoundsMinY();
			double var12 = par3 + this.rand.nextDouble() * (var6.getBlockBoundsMaxZ() - var6.getBlockBoundsMinZ() - var7 * 2.0F) + var7 + var6.getBlockBoundsMinZ();

			if (par4 == 0)
			{
				var10 = par2 + var6.getBlockBoundsMinY() - var7;
			}

			if (par4 == 1)
			{
				var10 = par2 + var6.getBlockBoundsMaxY() + var7;
			}

			if (par4 == 2)
			{
				var12 = par3 + var6.getBlockBoundsMinZ() - var7;
			}

			if (par4 == 3)
			{
				var12 = par3 + var6.getBlockBoundsMaxZ() + var7;
			}

			if (par4 == 4)
			{
				var8 = par1 + var6.getBlockBoundsMinX() - var7;
			}

			if (par4 == 5)
			{
				var8 = par1 + var6.getBlockBoundsMaxX() + var7;
			}

			//this.addEffect((new EntityDiggingFX_MCE(this.worldObj, var8, var10, var12, 0.0D, 0.0D, 0.0D, var6, par4, this.worldObj.getBlockMetadata(par1, par2, par3))).func_70596_a(par1, par2, par3).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
		}
	}
}
