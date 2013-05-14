package sedridor.mce.world;

import net.minecraft.world.WorldProviderSurface;

public class WorldProviderSurfaceMCE extends WorldProviderSurface
{
	/**
	 * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
	 */
	@Override
	public String getDimensionName()
	{
		return "Overworld";
	}
}
