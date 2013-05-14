package sedridor.mce.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.Session;
import net.minecraft.world.World;

public class EntityPlayerSP_MCE extends EntityPlayerSP
{
	protected Minecraft mc;

	public EntityPlayerSP_MCE(Minecraft par1Minecraft, World par2World, Session par3Session, int par4)
	{
		super(par1Minecraft, par2World, par3Session, par4);
		this.mc = par1Minecraft;
	}

	/**
	 * Displays the GUI for editing a sign. Args: tileEntitySign
	 */
	@Override
	public void displayGUIEditSign(TileEntity par1TileEntity)
	{
		if (par1TileEntity instanceof TileEntitySign)
		{
			this.mc.displayGuiScreen(new GuiEditSign((TileEntitySign)par1TileEntity));
		}
		else if (par1TileEntity instanceof TileEntityCommandBlock)
		{
			this.mc.displayGuiScreen(new GuiCommandBlock((TileEntityCommandBlock)par1TileEntity));
		}
	}

	/**
	 * Displays the GUI for editing a sign. Args: tileEntitySign
	 */
	public float getSpeedOnGround()
	{
		return this.speedOnGround;
	}
}
