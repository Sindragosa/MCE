package sedridor.mce;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiAchievement;

public class GuiAchievementMCE extends GuiAchievement
{
	public GuiAchievementMCE(Minecraft par1Minecraft)
	{
		super(par1Minecraft);
	}

	/**
	 * Updates the small achievement tooltip window, showing a queued achievement if is needed.
	 */
	@Override
	public void updateAchievementWindow() {}
}
