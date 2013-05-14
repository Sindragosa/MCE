package sedridor.mce.render;

import sedridor.mce.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.src.EntityRendererProxy;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@SideOnly(Side.CLIENT)
public class EntityRendererMCE extends EntityRendererProxy
{
	/** A reference to the Minecraft object. */
	private Minecraft mc;

	/** Pointed entity */
	private Entity pointedEntity = null;

	public EntityRendererMCE(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
		this.mc = par1Minecraft;
		if (!(this.itemRenderer instanceof ItemRendererMCE))
		{
			this.itemRenderer = new ItemRendererMCE(par1Minecraft);
		}
	}

	/**
	 * Updates the entity renderer
	 */
	@Override
	public void updateRenderer()
	{
		super.updateRenderer();
		this.updateFovModifierHand();
	}

	/**
	 * Finds what block or object the mouse is over at the specified partial tick time. Args: partialTickTime
	 */
	@Override
	public void getMouseOver(float par1)
	{
		//super.getMouseOver(par1);
		if (this.mc.renderViewEntity != null)
		{
			if (this.mc.theWorld != null)
			{
				this.mc.pointedEntityLiving = null;
				double var2 = this.mc.playerController.getBlockReachDistance();
				this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(var2, par1);
				double var4 = var2;
				Vec3 var6 = this.mc.renderViewEntity.getPosition(par1);

				//                if (this.mc.playerController.extendedReach())
				//                {
				//                    var2 = 6.0D;
				//                    var4 = 6.0D;
				//                }
				//                else
				//                {
				//                    if (var2 > 3.0D)
				//                    {
				//                        var4 = 3.0D;
				//                    }
				//
				//                    var2 = var4;
				//                }
				var4 = 200.0D;
				var2 = var4;
				if (this.mc.objectMouseOver != null)
				{
					var4 = this.mc.objectMouseOver.hitVec.distanceTo(var6);
				}

				Vec3 var7 = this.mc.renderViewEntity.getLook(par1);
				Vec3 var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
				this.pointedEntity = null;
				float var9 = 1.0F;
				List var10 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expand(var9, var9, var9));
				double var11 = var4;

				for (int var13 = 0; var13 < var10.size(); ++var13)
				{
					Entity var14 = (Entity)var10.get(var13);

					if (var14.canBeCollidedWith())
					{
						float var15 = var14.getCollisionBorderSize();
						AxisAlignedBB var16 = var14.boundingBox.expand(var15, var15, var15);
						MovingObjectPosition var17 = var16.calculateIntercept(var6, var8);

						if (var16.isVecInside(var6))
						{
							if (0.0D < var11 || var11 == 0.0D)
							{
								this.pointedEntity = var14;
								var11 = 0.0D;
							}
						}
						else if (var17 != null)
						{
							double var18 = var6.distanceTo(var17.hitVec);

							if (var18 < var11 || var11 == 0.0D)
							{
								this.pointedEntity = var14;
								var11 = var18;
							}
						}
					}
				}

				if (this.pointedEntity != null && (var11 < var4 || this.mc.objectMouseOver == null))
				{
					this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity);
				}
			}
		}
	}

	/**
	 * Update FOV modifier hand
	 */
	private void updateFovModifierHand()
	{
		float fovModifierHandPrev = (Float)MCE_Reflect.getValue(EntityRenderer.class, this.mc.entityRenderer, 31);
		float fovModifierHand;
		float fovMultiplierTemp;

		if (this.mc.renderViewEntity instanceof EntityPlayerSP)
		{
			EntityPlayerSP var1 = (EntityPlayerSP)this.mc.renderViewEntity;
			if (var1.isUsingItem() && (var1.getItemInUse().getItemUseAction() != EnumAction.bow || var1.getItemInUse().itemID == Item.bow.itemID))
			{
				return;
			}
			fovMultiplierTemp = this.getFOVMultiplier(var1);
			//mod_MCEnhancements.setValueF(EntityRenderer.class, this.mc.entityRenderer, 32, fovMultiplierTemp);
		}
		else
		{
			fovMultiplierTemp = this.getFOVMultiplier(this.mc.thePlayer);
			if (this.mc.thePlayer.isUsingItem() && (this.mc.thePlayer.getItemInUse().getItemUseAction() != EnumAction.bow || this.mc.thePlayer.getItemInUse().itemID == Item.bow.itemID))
			{
				return;
			}
			//mod_MCEnhancements.setValueF(EntityRenderer.class, this.mc.entityRenderer, 32, fovMultiplierTemp);
		}

		fovModifierHand = fovModifierHandPrev;
		fovModifierHand += (fovMultiplierTemp - fovModifierHand) * 0.5F;

		if (fovModifierHand > 1.5F)
		{
			fovModifierHand = 1.5F;
		}

		if (fovModifierHand < 0.1F)
		{
			fovModifierHand = 0.1F;
		}
		MCE_Reflect.setValue(EntityRenderer.class, this.mc.entityRenderer, 30, fovModifierHand);
	}

	/**
	 * Gets the player's field of view multiplier. (ex. when flying)
	 */
	private float getFOVMultiplier(EntityPlayerSP par1EntityPlayerSP)
	{
		float speedOnGround = (Float)MCE_Reflect.getValue(EntityPlayer.class, par1EntityPlayerSP, 33);
		float var1 = 1.0F;

		if (par1EntityPlayerSP.capabilities.isFlying)
		{
			var1 *= 1.1F;
		}

		var1 *= (par1EntityPlayerSP.landMovementFactor * par1EntityPlayerSP.getSpeedModifier() / speedOnGround + 1.0F) / 2.0F;

		if (par1EntityPlayerSP.isUsingItem() && par1EntityPlayerSP.getItemInUse().getItemUseAction() == EnumAction.bow)
		{
			int var2 = par1EntityPlayerSP.getItemInUseDuration();
			float var3 = var2 / 20.0F;

			if (var3 > 1.0F)
			{
				var3 = 1.0F;
			}
			else
			{
				var3 *= var3;
			}

			var1 *= 1.0F - var3 * 0.15F;
		}

		return var1;
	}
}
