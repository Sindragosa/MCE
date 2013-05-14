package sedridor.mce;

import sedridor.mce.blocks.*;
import sedridor.mce.render.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRenderingHandler implements ISimpleBlockRenderingHandler
{
	private static RenderBlocksMCE renderBlocksMCE;

	@Override
	public void renderInventoryBlock(Block block, int metaID, int modelID, RenderBlocks renderBlocks)
	{
		if (!(BlockRenderingHandler.renderBlocksMCE instanceof RenderBlocksMCE))
		{
			BlockRenderingHandler.renderBlocksMCE = new RenderBlocksMCE(renderBlocks.blockAccess);
		}
		if (BlockRenderingHandler.renderBlocksMCE.blockAccess != renderBlocks.blockAccess)
		{
			BlockRenderingHandler.renderBlocksMCE.blockAccess = renderBlocks.blockAccess;
		}
		if (modelID == MCE_Items.titaniumLampModelID)
		{
			(new RenderBlocksMCE(renderBlocks.blockAccess)).renderBlockAsItem(block, metaID, 1.0F);
		}
		else if (modelID == MCE_Items.thinGlassModelID)
		{
			BlockRenderingHandler.renderBlocksMCE.renderBlockAsItem(block, metaID, 1.0F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int posX, int posY, int posZ, Block block, int modelId, RenderBlocks renderBlocks)
	{
		if (!(BlockRenderingHandler.renderBlocksMCE instanceof RenderBlocksMCE))
		{
			BlockRenderingHandler.renderBlocksMCE = new RenderBlocksMCE(world);
		}
		if (BlockRenderingHandler.renderBlocksMCE.blockAccess != world)
		{
			BlockRenderingHandler.renderBlocksMCE.blockAccess = world;
		}
		if (modelId == MCE_Items.titaniumLampModelID)
		{
			return (new RenderBlocksMCE(world)).renderBlockLamp(block, posX, posY, posZ);
		}
		else if (modelId == MCE_Items.thinGlassModelID)
		{
			return BlockRenderingHandler.renderBlocksMCE.renderBlockPane((BlockPane2)block, posX, posY, posZ);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		System.out.println("MC getRenderId... " + (MCE_Items.titaniumLampModelID));
		return MCE_Items.titaniumLampModelID;
	}
}
