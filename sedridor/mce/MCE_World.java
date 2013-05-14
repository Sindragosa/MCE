package sedridor.mce;

import java.util.Iterator;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

public class MCE_World
{
    /** Default world type. */
    public static WorldType DEFAULT;

    /** Large Biome world Type. */
    public static WorldType LARGE_BIOMES;

    /** Large Biome world Type. */
    public static WorldType HUGE_BIOMES;

    private static World world;
    private static World worldServer;
    private static Minecraft mc;
    private static MCEnhancements instance;

    public static void onTickInGame(Minecraft minecraft)
    {
    	world = minecraft.theWorld;
    	worldServer = minecraft.getIntegratedServer().worldServers[0];
    }

	public static void init(Minecraft minecraft)
    {
        if (MCE_Settings.World.equalsIgnoreCase("yes"))
        {
            mc = minecraft;
        	instance = MCEnhancements.instance;

            DEFAULT = (new WorldType(5, "default2", 1));
            LARGE_BIOMES = new WorldType(6, "largeBiomes2");
            HUGE_BIOMES = new WorldType(7, "hugeBiomes");
            LanguageRegistry.instance().addStringLocalization("generator.default2", "MCE Default");
            LanguageRegistry.instance().addStringLocalization("generator.largeBiomes2", "MCE Large Biomes");
            LanguageRegistry.instance().addStringLocalization("generator.hugeBiomes", "Huge Biomes");
        }
    }
}
