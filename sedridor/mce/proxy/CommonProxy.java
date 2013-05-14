package sedridor.mce.proxy;

import sedridor.mce.tileentities.ContainerCraftingMCE;
import sedridor.mce.tileentities.ContainerFurnaceMCE;
import sedridor.mce.tileentities.GuiCraftingMCE;
import sedridor.mce.tileentities.GuiFurnaceMCE;
import sedridor.mce.tileentities.TileEntityFurnaceMCE;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler
{
	public static String ARMOR_MUD1_PNG = "/mods/BiomesOPlenty/textures/armor/mud_1.png";
	public static String ARMOR_MUD2_PNG = "/mods/BiomesOPlenty/textures/armor/mud_2.png";
	public static String ARMOR_AMETHYST1_PNG = "/mods/BiomesOPlenty/textures/armor/amethyst_1.png";
	public static String ARMOR_AMETHYST2_PNG = "/mods/BiomesOPlenty/textures/armor/amethyst_2.png";

	public void init() {}

	public void registerRenderers() {}

	@Override
	public Object getClientGuiElement(int par1, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6)
	{
		TileEntity var7 = par3World.getBlockTileEntity(par4, par5, par6);
		int var8 = par3World.getBlockMetadata(par4, par5, par6);

		switch (par1)
		{
		case 0:
			return new GuiFurnaceMCE(par2EntityPlayer.inventory, (TileEntityFurnaceMCE)var7);
		case 1:
			return new GuiCraftingMCE(par2EntityPlayer.inventory, par3World, par4, par5, par6);
		default:
			return null;
		}
	}

	@Override
	public Object getServerGuiElement(int par1, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6)
	{
		TileEntity var7 = par3World.getBlockTileEntity(par4, par5, par6);
		int var8 = par3World.getBlockMetadata(par4, par5, par6);

		switch (par1)
		{
		case 0:
			return new ContainerFurnaceMCE(par2EntityPlayer.inventory, (TileEntityFurnaceMCE)var7);
		case 1:
			return new ContainerCraftingMCE(par2EntityPlayer.inventory, par3World, par4, par5, par6);
		default:
			return null;
		}
	}

	public int addArmor(String var1)
	{
		return 0;
	}

	public void registerTileEntities()
	{
		System.out.println("MC Enhancements... registerTileEntities" + (Integer.valueOf(-4)));
		//GameRegistry.registerTileEntity(TileEntityLantern.class, "Lantern");
		GameRegistry.registerTileEntity(TileEntityFurnaceMCE.class, "BlastFurnace");
	}

	//@SideOnly(Side.CLIENT)
	//public void spawnMud(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {}
}
