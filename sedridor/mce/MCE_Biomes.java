package sedridor.mce;

import sedridor.mce.blocks.*;
import sedridor.mce.items.*;
import sedridor.mce.render.RenderGlobalMCE;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTextureTile;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;

public class MCE_Biomes
{
    public static BlockGreenLeaves GreenLeaves;
	public static BlockAutumnLeaves AutumnLeaves;
    public static Block Sapling;
    public static Block CatTail;
    public static BlockFlowerMCE Flower;
    public static Block LeafPile;
    public static Block RedRock;
    public static Block blockRedRock = Block.sand;

    public static final MapColor redrockColor = newMapColor(14, 11755314);
    public static final Material redrockMaterial = (new Material(redrockColor)).setRequiresTool();

	public static int metaFirLeaves = 0;
	public static int metaRedwoodLeaves = 1;
	public static int metaAcaciaLeaves = 2;

	public static int metaOrangeAutumnLeaves = 0;
	public static int metaYellowAutumnLeaves = 1;
	public static int metaPurpleAutumnLeaves = 2;
	public static int metaBrownAutumnLeaves = 3;

	public static String[] textureGreenLeaves = new String[] {"LeavesFir", "LeavesRedwood", "LeavesAcacia"};
	public static String[] textureAutumnLeaves = new String[] {"AutumnLeavesOrange", "AutumnLeavesYellow", "AutumnLeavesRed", "AutumnLeavesBrown"};
	public static String[] textureSapling = new String[] {"AutumnSaplingOrange", "AutumnSaplingYellow", "AutumnSaplingRed", "AutumnSaplingBrown"};
	public static String[] textureFlowers = new String[] {"AutumnShrub", "Hydrangea", "FlowerOrange", "FlowerPurple", "FlowerWhite"};
	public static String[] textureRedRock = new String[] {"RedRock", "RedRockCobblestone", "RedRockBrick"};

    public static int GreenLeavesID = 191;
    public static int AutumnLeavesID = 192;
    public static int SaplingID = 195;
    public static int CatTailID = 193;
    public static int FlowerID = 194;
    public static int LeafPileID = 196;

    public static Map<String, Integer> biomeNameToBiomeID = new HashMap<String, Integer>();
    public static Map<String, Integer> packageNameToBiomeID = new HashMap<String, Integer>();
    public static Map<Integer, String> biomeIDToPackageName = new HashMap<Integer, String>();

    public static void init()
    {
        if (MCE_Settings.Biomes.equalsIgnoreCase("yes"))
        {
        	GreenLeavesID = MCE_Settings.GreenLeavesID;
        	AutumnLeavesID = MCE_Settings.AutumnLeavesID;
        	SaplingID = MCE_Settings.SaplingID;
        	CatTailID = MCE_Settings.CatTailID;
        	FlowerID = MCE_Settings.FlowerID;
        	LeafPileID = MCE_Settings.LeafPileID;

        	GreenLeaves = (BlockGreenLeaves)(new BlockGreenLeaves(GreenLeavesID)).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("GreenLeaves");
        	AutumnLeaves = (BlockAutumnLeaves)(new BlockAutumnLeaves(AutumnLeavesID)).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("AutumnLeaves");
        	Sapling = (new BlockSaplingMCE(SaplingID)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("Sapling");
        	CatTail = (new BlockCatTail(CatTailID)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("CatTail");
        	Flower = (BlockFlowerMCE)(new BlockFlowerMCE(FlowerID)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("Flower");
        	LeafPile = (BlockLeafPile)(new BlockLeafPile(LeafPileID)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("LeafPile");
        	Block.blocksList[19] = null;
        	RedRock = (new BlockRedRock(19)).setHardness(1.5F).setResistance(2.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("RedRock");

        	loadBlockItems();

            MinecraftForge.addGrassPlant(Flower, 0, 5);
            MinecraftForge.addGrassPlant(Flower, 1, 5);
            MinecraftForge.addGrassPlant(Flower, 2, 5);
            MinecraftForge.addGrassPlant(Flower, 3, 5);
            MinecraftForge.addGrassPlant(Flower, 4, 2);
            MinecraftForge.setBlockHarvestLevel(RedRock, "pickaxe", 0);
        }
    }

	public static boolean isLeaves(int blockID)
	{
		return blockID == Block.leaves.blockID || blockID == GreenLeaves.blockID || blockID == AutumnLeaves.blockID;
	}

    public static boolean isWood(int blockID)
	{
		return blockID == Block.wood.blockID;
	}

    public static boolean treesCanGrowOnID(int blockID)
	{
		return blockID == Block.grass.blockID || blockID == Block.dirt.blockID;
	}

    public static boolean canBeReplacedByLeaves(int blockID)
	{
		return !Block.opaqueCubeLookup[blockID];
	}
    public static void createBiomeMaps()
	{
        biomeNameToBiomeID.clear();
        packageNameToBiomeID.clear();
        for (BiomeGenBase biome : BiomeGenBase.biomeList) {
            if (biome == null || !(biome instanceof BiomeGenBase)) { continue; }
            if (biomeNameToBiomeID.containsKey(biome.biomeName))
            {
                biomeNameToBiomeID.put(biome.biomeName + biome.biomeID, biome.biomeID);
            }
            else
            {
                biomeNameToBiomeID.put(biome.biomeName, biome.biomeID);
            }
            packageNameToBiomeID.put(biome.getClass().getName() + "." + biome.biomeName, biome.biomeID);
            biomeIDToPackageName.put(biome.biomeID, biome.getClass().getName() + "." + biome.biomeName);
        }
	}

    public static int getBiomeIDFromBiomeName(String biomeName)
	{
		return biomeNameToBiomeID.get(biomeName);
	}

    public static String getBiomeNameFromBiomeID(int biomeID)
	{
        if (BiomeGenBase.biomeList[biomeID] == null || !(BiomeGenBase.biomeList[biomeID] instanceof BiomeGenBase)) { return null; }
		return BiomeGenBase.biomeList[biomeID].biomeName;
	}

    public static int getBiomeIDFromBiomePackageName(String biomePackageName)
	{
		return packageNameToBiomeID.get(biomePackageName);
	}

    public static String getBiomePackageNameFromBiomeID(int biomeID)
	{
		return biomeIDToPackageName.get(biomeID);
	}

    private static void loadBlockItems()
    {
        Item.itemsList[GreenLeaves.blockID] = (new ItemMultiTextureTile(GreenLeaves.blockID - 256, GreenLeaves, BlockGreenLeaves.LEAF_TYPES)).setUnlocalizedName("GreenLeaves");
        Item.itemsList[AutumnLeaves.blockID] = (new ItemMultiTextureTile(AutumnLeaves.blockID - 256, AutumnLeaves, BlockAutumnLeaves.LEAF_TYPES)).setUnlocalizedName("AutumnLeaves");
        Item.itemsList[Sapling.blockID] = (new ItemMultiTextureTile(Sapling.blockID - 256, Sapling, BlockSaplingMCE.textureTypes)).setUnlocalizedName("Sapling");
        GameRegistry.registerBlock(CatTail, "CatTail");
        Item.itemsList[Flower.blockID] = (new ItemMultiTextureTile(Flower.blockID - 256, Flower, BlockFlowerMCE.textureTypes)).setUnlocalizedName("Flower");
        GameRegistry.registerBlock(LeafPile, "LeafPile");
        Item.itemsList[RedRock.blockID] = (new ItemMultiTextureTile(RedRock.blockID - 256, RedRock, BlockRedRock.textureTypes)).setUnlocalizedName("RedRock");

        LanguageRegistry.addName(GreenLeaves, "Green Leaves");
        LanguageRegistry.addName(AutumnLeaves, "Autumn Leaves");
        LanguageRegistry.addName(Sapling, "Sapling");
        LanguageRegistry.addName(CatTail, "Cat Tail");
        LanguageRegistry.addName(Flower, "Flower");
        LanguageRegistry.addName(LeafPile, "Leaf Pile");
        LanguageRegistry.addName(RedRock, "Red Rock");

    	LanguageRegistry.addName(new ItemStack(GreenLeaves, 1, 0), "Fir Leaves");
    	LanguageRegistry.addName(new ItemStack(GreenLeaves, 1, 1), "Redwood Leaves");
    	LanguageRegistry.addName(new ItemStack(GreenLeaves, 1, 2), "Acacia Leaves");

    	LanguageRegistry.addName(new ItemStack(AutumnLeaves, 1, 0), "Orange Autumn Leaves");
    	LanguageRegistry.addName(new ItemStack(AutumnLeaves, 1, 1), "Yellow Autumn Leaves");
    	LanguageRegistry.addName(new ItemStack(AutumnLeaves, 1, 2), "Purple Autumn Leaves");
    	LanguageRegistry.addName(new ItemStack(AutumnLeaves, 1, 3), "Brown Autumn Leaves");

    	LanguageRegistry.addName(new ItemStack(Sapling, 1, 0), "Orange Autumn Sapling");
    	LanguageRegistry.addName(new ItemStack(Sapling, 1, 1), "Yellow Autumn Sapling");
    	LanguageRegistry.addName(new ItemStack(Sapling, 1, 2), "Purple Autumn Sapling");
    	LanguageRegistry.addName(new ItemStack(Sapling, 1, 3), "Brown Autumn Sapling");
    	LanguageRegistry.addName(new ItemStack(Sapling, 1, 4), "Fir Sapling");
    	LanguageRegistry.addName(new ItemStack(Sapling, 1, 5), "Redwood Sapling");
    	LanguageRegistry.addName(new ItemStack(Sapling, 1, 6), "Acacia Sapling");

    	LanguageRegistry.addName(new ItemStack(Flower, 1, 0), "Autumn Shrub");
    	LanguageRegistry.addName(new ItemStack(Flower, 1, 1), "Hydrangea");
    	LanguageRegistry.addName(new ItemStack(Flower, 1, 2), "Orange Flower");
    	LanguageRegistry.addName(new ItemStack(Flower, 1, 3), "Purple Flower");
    	LanguageRegistry.addName(new ItemStack(Flower, 1, 4), "White Flower");

    	LanguageRegistry.addName(new ItemStack(RedRock, 1, 0), "Red Rock");
    	LanguageRegistry.addName(new ItemStack(RedRock, 1, 1), "Red Cobblestone");
    	LanguageRegistry.addName(new ItemStack(RedRock, 1, 2), "Red Rock Brick");

    	FurnaceRecipes.smelting().addSmelting(RedRock.blockID, 1, new ItemStack(RedRock, 1, 0), 0.1F);
    	GameRegistry.addRecipe(new ItemStack(RedRock, 1, 2), new Object[] {"##", "##", '#', new ItemStack(RedRock, 1, 0)});

    	GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 1, 12), new Object[] {new ItemStack(Flower, 1, 1)});
    	GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 1, 14), new Object[] {new ItemStack(Flower, 1, 2)});
    	GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 1, 13), new Object[] {new ItemStack(Flower, 1, 3)});
    	GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 1, 7), new Object[] {new ItemStack(Flower, 1, 4)});

    	GameRegistry.addRecipe(new ItemStack(Block.leaves), new Object[] {"###", "###", "###", '#', new ItemStack(LeafPile, 1, 0)});
    }

    private static MapColor newMapColor(int par1, int par2)
    {
        try
        {
        	Constructor methodMapColor = MapColor.class.getDeclaredConstructor(new Class[] {Integer.TYPE, Integer.TYPE});
        	methodMapColor.setAccessible(true);
        	MapColor mapColor = (MapColor)methodMapColor.newInstance(new Object[] {par1, par2});
           	return mapColor;
        }
        catch (NoSuchMethodException var1)
        {
            try
            {
            	Constructor methodMapColor = Class.forName("aih").getDeclaredConstructor(new Class[] {Integer.TYPE, Integer.TYPE});
            	methodMapColor.setAccessible(true);
            	MapColor mapColor = (MapColor)methodMapColor.newInstance(new Object[] {par1, par2});
               	return mapColor;
            }
            catch (NoSuchMethodException var2)
            {
            	var2.printStackTrace();
            }
            catch (Exception var2)
            {
                var2.printStackTrace();
            }
        }
        catch (Exception var1)
        {
            var1.printStackTrace();
        }
    	return null;
    }
}
