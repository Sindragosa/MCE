package sedridor.mce;

import sedridor.mce.blocks.*;
import sedridor.mce.render.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class Block3dRenderingHandler implements ISimpleBlockRenderingHandler
{
	private static RenderBlocksInclined renderBlocksInclined;
	private static RenderBlocksMCE renderBlocksMCE;

	@Override
	public void renderInventoryBlock(Block block, int metaID, int modelID, RenderBlocks renderBlocks)
	{
		if (!(Block3dRenderingHandler.renderBlocksMCE instanceof RenderBlocksMCE))
		{
			Block3dRenderingHandler.renderBlocksMCE = new RenderBlocksMCE(renderBlocks.blockAccess);
		}
		if (Block3dRenderingHandler.renderBlocksMCE.blockAccess != renderBlocks.blockAccess)
		{
			Block3dRenderingHandler.renderBlocksMCE.blockAccess = renderBlocks.blockAccess;
		}
		if (modelID == MCE_Items.inclinedModelID || modelID == MCE_Items.inclinedCornerModelID)
		{
			Block3dRenderingHandler.renderBlocksInclined = new RenderBlocksInclined(renderBlocks.blockAccess);
			Block3dRenderingHandler.renderBlocksInclined.renderBlockAsItem(block, metaID, 1.0F);
		}
		else if (modelID == MCE_Items.lightBulbModelID)
		{
			BlockLightBulb.RenderBlockInInv(renderBlocks, block, metaID, modelID);
		}
		else if (modelID == MCE_Items.glassCubeModelID)
		{
			Block3dRenderingHandler.renderBlocksMCE.renderBlockAsItem(block, metaID, 1.0F);
		}
		else if (modelID == MCE_Items.copperWireModelID)
		{
			BlockCopperWire.RenderBlockInInv(renderBlocks, block, metaID, modelID);
		}
		else if (modelID == MCE_Items.blastFurnaceModelID)
		{
			Block3dRenderingHandler.renderBlocksMCE.renderBlockAsItem(block, 3, 1.0F);
		}
		else if (modelID == MCE_Items.dirtSlabModelID)
		{
			Block3dRenderingHandler.renderBlocksMCE.renderBlockAsItem(block, 0, 1.0F);
		}
		else if (modelID == MCE_Items.planterModelID)
		{
			BlockPlanter.RenderInvBlock(renderBlocks, block, metaID, 1.0F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int posX, int posY, int posZ, Block block, int modelId, RenderBlocks renderBlocks)
	{
		if (!(Block3dRenderingHandler.renderBlocksMCE instanceof RenderBlocksMCE))
		{
			Block3dRenderingHandler.renderBlocksMCE = new RenderBlocksMCE(world);
		}
		if (Block3dRenderingHandler.renderBlocksMCE.blockAccess != world)
		{
			Block3dRenderingHandler.renderBlocksMCE.blockAccess = world;
		}
		if (modelId == MCE_Items.inclinedModelID || modelId == MCE_Items.inclinedCornerModelID)
		{
			Block3dRenderingHandler.renderBlocksInclined = new RenderBlocksInclined(world);
			Icon overrideBlockTexture = renderBlocks.overrideBlockTexture;
			if (overrideBlockTexture != null)
			{
				Block3dRenderingHandler.renderBlocksInclined.renderBlockUsingTexture(block, posX, posY, posZ, overrideBlockTexture);
				return true;
			}

			return Block3dRenderingHandler.renderBlocksInclined.renderBlockInclined(block, posX, posY, posZ);
		}
		else if (modelId == MCE_Items.lightBulbModelID)
		{
			return (new RenderBlocksMCE(world)).renderLightBulb((BlockLightBulb)block, posX, posY, posZ);
		}
		else if (modelId == MCE_Items.glassCubeModelID)
		{
			return Block3dRenderingHandler.renderBlocksMCE.renderBlockGlass(block, posX, posY, posZ);
		}
		else if (modelId == MCE_Items.copperWireModelID)
		{
			return BlockCopperWire.RenderBlockInWorld(renderBlocks, world, posX, posY, posZ, (BlockCopperWire)block);
		}
		else if (modelId == MCE_Items.blastFurnaceModelID)
		{
			block.setBlockBoundsBasedOnState(renderBlocks.blockAccess, posX, posY, posZ);
			renderBlocks.setRenderBoundsFromBlock(block);
			return renderBlocks.renderStandardBlock(block, posX, posY, posZ);
		}
		else if (modelId == MCE_Items.dirtSlabModelID)
		{
			block.setBlockBoundsBasedOnState(renderBlocks.blockAccess, posX, posY, posZ);
			renderBlocks.setRenderBoundsFromBlock(block);
			return BlockDirtSlab.RenderDirtSlab(renderBlocks, world, posX, posY, posZ, block);
		}
		else if (modelId == MCE_Items.planterModelID)
		{
			block.setBlockBoundsBasedOnState(renderBlocks.blockAccess, posX, posY, posZ);
			return BlockPlanter.RenderBlock(renderBlocks, world, posX, posY, posZ, block);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return MCE_Items.glassCubeModelID;
	}
}
