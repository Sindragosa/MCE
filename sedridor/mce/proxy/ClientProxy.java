package sedridor.mce.proxy;

import sedridor.mce.*;
import sedridor.mce.tileentities.TileEntityFurnaceMCE;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class ClientProxy extends CommonProxy
{
	public static Minecraft mc = Minecraft.getMinecraft();
	public static int renderPass;

	@Override
	public void init()
	{
		if (!MCEnhancements.enableMod)
		{
			return;
		}
		MCE_Mobs.addRenderer();
		MCE_NPCs.addRenderer();
		TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);

		// register and load key bindings
		//KeyBindingRegistry.registerKeyBinding(new KeyHandlerMCE(this));
	}

	@Override
	public void registerRenderers()
	{
		if (!MCEnhancements.enableMod)
		{
			return;
		}
		RenderingRegistry.registerBlockHandler(MCE_Items.titaniumLampModelID, new BlockRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.thinGlassModelID, new BlockRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.lightBulbModelID, new Block3dRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.inclinedModelID, new Block3dRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.inclinedCornerModelID, new Block3dRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.glassCubeModelID, new Block3dRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.copperWireModelID, new Block3dRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.blastFurnaceModelID, new Block3dRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.dirtSlabModelID, new Block3dRenderingHandler());
		RenderingRegistry.registerBlockHandler(MCE_Items.planterModelID, new Block3dRenderingHandler());
	}

	@Override
	public void registerTileEntities()
	{
		if (!MCEnhancements.enableMod)
		{
			return;
		}
		//GameRegistry.registerTileEntity(TileEntityLantern.class, "Lantern", new TileEntityLanternRenderer());
		GameRegistry.registerTileEntity(TileEntityFurnaceMCE.class, "BlastFurnace");
	}

	//@SideOnly(Side.CLIENT)
	//public void spawnMud(World var1, double var2, double var4, double var6, double var8, double var10, double var12)
	//{
	//}
}
