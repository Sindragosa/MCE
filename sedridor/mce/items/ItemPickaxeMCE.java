package sedridor.mce.items;

import sedridor.mce.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemPickaxeMCE extends ItemTool
{
	/** an array of the blocks this pickaxe is effective against */
	private static Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered, Block.railActivator,
		MCE_Items.inclined[0], MCE_Items.inclined[1], MCE_Items.inclined[2], MCE_Items.inclined[3], MCE_Items.inclined[4],
		MCE_Items.inclined[5], MCE_Items.inclined[6], MCE_Items.inclined[7], MCE_Items.inclined[8], MCE_Items.inclined[9],
		MCE_Items.inclined[10], MCE_Items.inclined[11], MCE_Items.inclined[12], MCE_Items.inclined[13], MCE_Items.inclined[14],
		MCE_Items.inclined[15], MCE_Items.inclined[16], MCE_Items.inclined[17], MCE_Items.inclined[18], MCE_Items.inclined[19],
		MCE_Items.inclinedCorner[0], MCE_Items.inclinedCorner[1], MCE_Items.inclinedCorner[2], MCE_Items.inclinedCorner[3], MCE_Items.inclinedCorner[4],
		MCE_Items.inclinedCorner[5], MCE_Items.inclinedCorner[6], MCE_Items.inclinedCorner[7], MCE_Items.inclinedCorner[8], MCE_Items.inclinedCorner[9],
		MCE_Items.inclinedCorner[10], MCE_Items.inclinedCorner[11], MCE_Items.inclinedCorner[12], MCE_Items.inclinedCorner[13], MCE_Items.inclinedCorner[14],
		MCE_Items.inclinedCorner[15], MCE_Items.inclinedCorner[16], MCE_Items.inclinedCorner[17], MCE_Items.inclinedCorner[18], MCE_Items.inclinedCorner[19],
		MCE_Biomes.RedRock
	};

	public ItemPickaxeMCE(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, 2, par2EnumToolMaterial, blocksEffectiveAgainst);
	}

	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	@Override
	public boolean canHarvestBlock(Block par1Block)
	{
		if (par1Block == Block.obsidian)
		{
			return this.toolMaterial.getHarvestLevel() >= 3;
		}
		else if (par1Block == Block.blockDiamond || par1Block == Block.oreDiamond)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == Block.oreEmerald)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == Block.blockGold || par1Block == Block.blockGold)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == Block.blockIron || par1Block == Block.oreIron)
		{
			return this.toolMaterial.getHarvestLevel() >= 1;
		}
		else if (par1Block == Block.blockLapis || par1Block == Block.oreLapis)
		{
			return this.toolMaterial.getHarvestLevel() >= 1;
		}
		else if (par1Block == Block.oreRedstone || par1Block == Block.oreRedstoneGlowing)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == MCE_Items.MithrilOre || par1Block == MCE_Items.MithrilBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == MCE_Items.AdamantiumOre || par1Block == MCE_Items.AdamantiumBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == MCE_Items.RuniteOre || par1Block == MCE_Items.RuniteBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == MCE_Items.DragoniteOre || par1Block == MCE_Items.DragoniteBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 3;
		}
		else if (par1Block == MCE_Items.CrystalOre || par1Block == MCE_Items.CrystalBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 4;
		}
		else if (par1Block == MCE_Items.SilverOre || par1Block == MCE_Items.SilverBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == MCE_Items.CopperOre || par1Block == MCE_Items.TinOre || par1Block == MCE_Items.BronzeBlock || par1Block == MCE_Items.SteelBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 1;
		}
		else if (par1Block == MCE_Items.BluriteOre || par1Block == MCE_Items.BluriteBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 2;
		}
		else if (par1Block == MCE_Items.ObsidiumOre || par1Block == MCE_Items.ObsidiumBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 3;
		}
		else if (par1Block == MCE_Items.TitaniumOre || par1Block == MCE_Items.TitaniumBlock)
		{
			return this.toolMaterial.getHarvestLevel() >= 3;
		}
		else if (par1Block.blockMaterial == Material.rock || par1Block.blockMaterial == MCE_Biomes.redrockMaterial)
		{
			return true;
		}
		else
		{
			return par1Block.blockMaterial == Material.iron || par1Block.blockMaterial == Material.anvil;
		}
	}

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		return par2Block != null && (par2Block.blockMaterial == Material.iron || par2Block.blockMaterial == Material.anvil || par2Block.blockMaterial == Material.rock || par2Block.blockMaterial == MCE_Biomes.redrockMaterial) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
	}
}
