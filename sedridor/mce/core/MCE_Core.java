package sedridor.mce.core;

//import java.io.File;
//import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Arrays;
//import net.minecraft.client.Minecraft;
//import net.minecraft.server.MinecraftServer;
//import net.minecraft.world.WorldServer;
import com.google.common.eventbus.EventBus;
//import com.google.common.eventbus.Subscribe;
//import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
//import cpw.mods.fml.common.event.FMLInitializationEvent;
//import cpw.mods.fml.common.event.FMLPreInitializationEvent;
//import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class MCE_Core extends DummyModContainer
{
	//public static Minecraft mc;
	//public static MinecraftServer mcServer;
	//public static WorldServer worldServer;

	//public static DateFormat dateFormatter = new SimpleDateFormat("d/M/yyyy H:mm");

	public MCE_Core()
	{
        super(new ModMetadata());
        /* ModMetadata is the same as mcmod.info */

        ModMetadata meta = super.getMetadata();
        meta.modId       = "MCE_Core";
        meta.name        = "Minecraft Enhancements Core";
        meta.version     = "1.5.1";
        meta.authorList  = Arrays.asList(new String[] { "Sedridor" });
        //meta.description = "";
        //meta.url         = "";
        //meta.credits     = "";
	}
    
	@Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }

//    @Subscribe
//	public void preInit(FMLPreInitializationEvent evt)
//	{
//	}
//
//    @Subscribe
//	public void init(FMLInitializationEvent evt)
//	{
//		//mc = FMLClientHandler.instance().getClient();
//	}
//
//    @Subscribe
//	public void serverStarting(FMLServerStartingEvent evt)
//	{
//		//mcServer = FMLClientHandler.instance().getServer();
//		//worldServer = FMLClientHandler.instance().getServer().worldServers[0];
//	}
//
//	/**
//	 * Saves the options to the options file.
//	 */
//	public static void saveData(String par1String, String par2String)
//	{
//		File optionsFile = new File("./", par2String);
//		try
//		{
//			PrintWriter var1 = new PrintWriter(new FileWriter(optionsFile));
//			var1.println(par1String);
//			var1.close();
//		}
//		catch (Exception var6)
//		{
//			var6.printStackTrace();
//		}
//	}
}
