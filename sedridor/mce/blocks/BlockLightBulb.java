package sedridor.mce.blocks;

import sedridor.mce.*;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class BlockLightBulb extends Block
{
	public static final String[][] textureTypes = new String[][] {{"LightBulb_side", "LightBulb_bot", "LightBulb_top"}, {"LightBulb_side_lit", "LightBulb_bot_lit", "LightBulb_top_lit"}};
	private Icon[] iconArray = new Icon[3];

	/** Whether this lamp block is the powered version. */
	private boolean powered;
	private int side = 0;
	private int meta = 0;

	public BlockLightBulb(int par1, boolean par2)
	{
		super(par1, Material.redstoneLight);
		this.powered = par2;

		if (par2)
		{
			this.setLightValue(1.0F);
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return MCE_Items.lightBulbModelID;
	}

	/**
	 * Gets if we can place a torch on a block.
	 */
	private boolean canPlaceTorchOn(World par1World, int par2, int par3, int par4)
	{
		if (par1World.doesBlockHaveSolidTopSurface(par2, par3, par4))
		{
			return true;
		}
		else
		{
			int var5 = par1World.getBlockId(par2, par3, par4);
			return var5 == Block.fence.blockID || var5 == Block.netherFence.blockID || var5 == Block.glass.blockID;
		}
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) ? true : (par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) ? true : (par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) ? true : (par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) ? true : (par1World.isBlockNormalCubeDefault(par2, par3 + 1, par4, true) ? true : this.canPlaceTorchOn(par1World, par2, par3 - 1, par4)))));
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		int var9 = par9;

		if (par9 == 0)
		{
			this.side = par5;
			if (par5 == 1 && this.canPlaceTorchOn(par1World, par2, par3 - 1, par4))
			{
				var9 = 5;
			}

			if (par5 == 2 && par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
			{
				var9 = 4;
			}

			if (par5 == 3 && par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
			{
				var9 = 3;
			}

			if (par5 == 4 && par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
			{
				var9 = 2;
			}

			if (par5 == 5 && par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
			{
				var9 = 1;
			}

			if (par5 == 0 && par1World.isBlockNormalCubeDefault(par2, par3 + 1, par4, true))
			{
				var9 = 6;
			}
			this.meta = var9;
		}

		return var9;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if (!par1World.isRemote)
		{
			if (this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
			{
				par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
			}
			else if (!this.powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
			{
				par1World.setBlock(par2, par3, par4, MCE_Items.LightBulbActive.blockID, this.meta, 2);
			}
		}
		this.dropTorchIfCantStay(par1World, par2, par3, par4);
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if (!par1World.isRemote)
		{
			if (this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
			{
				par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
			}
			else if (!this.powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
			{
				par1World.setBlock(par2, par3, par4, MCE_Items.LightBulbActive.blockID, par1World.getBlockMetadata(par2, par3, par4), 2);
			}
		}

		if (this.dropTorchIfCantStay(par1World, par2, par3, par4))
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			boolean var7 = false;

			if (!par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) && var6 == 1)
			{
				var7 = true;
			}

			if (!par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) && var6 == 2)
			{
				var7 = true;
			}

			if (!par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) && var6 == 3)
			{
				var7 = true;
			}

			if (!par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) && var6 == 4)
			{
				var7 = true;
			}

			if (!this.canPlaceTorchOn(par1World, par2, par3 - 1, par4) && var6 == 5)
			{
				var7 = true;
			}

			if (!this.canPlaceTorchOn(par1World, par2, par3 + 1, par4) && var6 == 6)
			{
				var7 = true;
			}

			if (var7)
			{
				this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}

	/**
	 * Tests if the block can remain at its current location and will drop as an item if it is unable to stay. Returns
	 * True if it can stay and False if it drops. Args: world, x, y, z
	 */
	private boolean dropTorchIfCantStay(World par1World, int par2, int par3, int par4)
	{
		if (!this.canPlaceBlockAt(par1World, par2, par3, par4))
		{
			if (par1World.getBlockId(par2, par3, par4) == this.blockID)
			{
				this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				par1World.setBlockToAir(par2, par3, par4);
			}

			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote && this.powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
		{
			par1World.setBlock(par2, par3, par4, MCE_Items.LightBulbIdle.blockID, par1World.getBlockMetadata(par2, par3, par4), 2);
		}
		else
		{
			super.updateTick(par1World, par2, par3, par4, par5Random);
			if (par1World.getBlockMetadata(par2, par3, par4) == 0)
			{
				this.onBlockAdded(par1World, par2, par3, par4);
			}
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return MCE_Items.LightBulbIdle.blockID;
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public boolean isPowered()
	{
		return this.powered;
	}

	/**
	 * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
	 */
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return MCE_Items.LightBulbIdle.blockID;
	}

	/**
	 * Returns true if the given block ID is equivalent to this one. Example: redstoneTorchOn matches itself and
	 * redstoneTorchOff, and vice versa. Most blocks only match themselves.
	 */
	@Override
	public boolean isAssociatedBlockID(int par1)
	{
		return par1 == MCE_Items.LightBulbIdle.blockID || par1 == MCE_Items.LightBulbActive.blockID;
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public Icon getIcon(int side, int metadata)
	{
		/**
		 * Which side was hit. If its -1 then it went the full length of the ray trace. Bottom = 0, Top = 1, South = 2, North
		 * = 3, East = 4, West = 5.
		 */
		if (metadata == 1)
		{
			return (side == 5 ? this.iconArray[2] : (side == 4 ? this.iconArray[1] : this.iconArray[0]));
		}
		else if (metadata == 2)
		{
			return (side == 4 ? this.iconArray[2] : (side == 5 ? this.iconArray[1] : this.iconArray[0]));
		}
		else if (metadata == 3)
		{
			return (side == 3 ? this.iconArray[2] : (side == 2 ? this.iconArray[1] : this.iconArray[0]));
		}
		else if (metadata == 4)
		{
			return (side == 2 ? this.iconArray[2] : (side == 3 ? this.iconArray[1] : this.iconArray[0]));
		}
		else if (metadata == 5)
		{
			return (side == 1 ? this.iconArray[2] : (side == 0 ? this.iconArray[1] : this.iconArray[0]));
		}
		else
		{
			return (side == 0 ? this.iconArray[2] : (side == 1 ? this.iconArray[1] : this.iconArray[0]));
		}
	}

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		int var2 = (this.powered ? 1 : 0);
		this.iconArray = new Icon[textureTypes[var2].length];

		for (int var3 = 0; var3 < textureTypes[var2].length; ++var3)
		{
			this.iconArray[var3] = par1IconRegister.registerIcon(textureTypes[var2][var3]);
		}
	}

	/**
	 * if the specified block is in the given AABB, add its collision bounding box to the given list
	 */
	/*public void addCollidingBlockToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        int var8 = par1World.getBlockMetadata(par2, par3, par4);
        int var9 = var8 & 7;

        if (var9 == 1)
        {
            this.setBlockBounds(0.5625F, 0.5F, 0.5625F, 0.4375F, 1.0F, 0.4375F);
            super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (var9 == 2)
        {
            this.setBlockBounds(0.5625F, 0.5F, 0.5625F, 0.4375F, 1.0F, 0.4375F);
            super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (var9 == 3)
        {
            this.setBlockBounds(0.5625F, 0.5F, 0.5625F, 0.4375F, 1.0F, 0.4375F);
            super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (var9 == 4)
        {
            this.setBlockBounds(0.5625F, 0.5F, 0.5625F, 0.4375F, 1.0F, 0.4375F);
            super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else if (var9 == 5)
        {
            this.setBlockBounds(0.5625F, 0.0F, 0.5625F, 0.4375F, 0.5F, 0.4375F);
            super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
        else
        {
            this.setBlockBounds(0.5625F, 0.5F, 0.5625F, 0.4375F, 1.0F, 0.4375F);
            super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }*/

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
	 * x, y, z, startVec, endVec
	 */
	@Override
	public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		int var7 = par1World.getBlockMetadata(par2, par3, par4) & 7;
		float var8 = 0.5F;

		if (var7 == 1)
		{
			this.setBlockBounds(0.0F, 0.4375F, 0.4375F, 0.25F, 0.5625F, 0.5625F);
		}
		else if (var7 == 2)
		{
			this.setBlockBounds(0.75F, 0.4375F, 0.4375F, 1.0F, 0.5625F, 0.5625F);
		}
		else if (var7 == 3)
		{
			this.setBlockBounds(0.4375F, 0.4375F, 0.0F, 0.5625F, 0.5625F, 0.25F);
		}
		else if (var7 == 4)
		{
			this.setBlockBounds(0.4375F, 0.4375F, 0.75F, 0.5625F, 0.5625F, 1.0F);
		}
		else if (var7 == 5)
		{
			this.setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.25F, 0.5625F);
		}
		else
		{
			this.setBlockBounds(0.4375F, 0.75F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
		}

		return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	}

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
	}

	public static void RenderBlockInInv(RenderBlocks renderBlocks, Block block, int metaID, int modelID)
	{
		Tessellator var4 = Tessellator.instance;

		GL11.glScalef(4.0F, 4.0F, 4.0F);
		GL11.glTranslatef(-0.5F, -0.9F, -0.5F);
		for (int var5 = 0; var5 < 2; ++var5)
		{
			if (var5 == 0)
			{
				renderBlocks.setRenderBounds(0.5625F, 0.9375F, 0.5625F, 0.4375F, 1.0F, 0.4375F);
			}
			else if (var5 == 1)
			{
				renderBlocks.setRenderBounds(0.5625F, 0.75F, 0.5625F, 0.4375F, 0.9375F, 0.4375F);
			}

			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			renderBlocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metaID));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 1.0F, 0.0F);
			renderBlocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metaID));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			renderBlocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, metaID));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			renderBlocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, metaID));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			renderBlocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, metaID));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			renderBlocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, metaID));
			var4.draw();
		}
		GL11.glTranslatef(0.5F, 0.9F, 0.5F);
		GL11.glScalef(1.0F, 1.0F, 1.0F);

		renderBlocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
}
