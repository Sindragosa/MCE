package sedridor.mce;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/* Tick handler, used for rendering berry bushes between fast/fancy graphics */
public class TickHandler implements ITickHandler
{
	private Minecraft mc;

	public TickHandler()
	{
		this.mc = Minecraft.getMinecraft();
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (type.equals(EnumSet.of(TickType.RENDER)))
		{
			GuiScreen guiscreen = this.mc.currentScreen;
			if (guiscreen == null)
			{
				this.onTick();
			}
		}
		else if (type.equals(EnumSet.of(TickType.CLIENT)))
		{
			GuiScreen guiscreen = this.mc.currentScreen;
			if (guiscreen != null)
			{
				this.onTickInGUI(guiscreen);
			}
			else
			{
				this.onTickInGame();
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel()
	{
		return null;
	}

	public void onTick()
	{
		MCEnhancements.instance.getInput(this.mc);
	}

	public void onTickInGUI(GuiScreen guiscreen)
	{
	}

	public void onTickInGame()
	{
		MCEnhancements.instance.onTickInGame();
	}
}
