package sedridor.mce;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import sedridor.mce.blocks.*;
import sedridor.mce.items.*;
import sedridor.mce.items.ItemBlockWithMetadata;
import sedridor.mce.tileentities.BlastFurnaceRecipes;
import sedridor.mce.tileentities.ExtendedCraftingHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockOreStorage;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;

/*
//Block		1 - 158, Item 0 - 152 (256 - 408)
//Steel:		212 - 566-575, 712 (17120-)	1
//Mithril:		200 - 446-455, 700 (17000-)	2
//Adamantium:	201 - 456-465, 701 (17010-)	3
//Runite:		202 - 466-475, 702 (17020-)	4
//Dragonite:	204 - 486-495, 704 (17400-)	5
//Obsidium:		208 - 526-535, 708 (17080-)	9
//Crystal:		207 - 516-525, 707 (17070-)	12
//Silver:		206 - 506-515, 706 (17060-)	6
//Blurite:		205 - 496-505, 705 (17050-)	7
//Bronze:		203 - 476-485, 703 (17030-)	8
//Titanium:		209 - 536-545, 709 (17090-)	10
//Elementium:	210 - 546-555, 710 (17100-)	11
*/
public class MCE_Items
{
    public static Block MithrilOre;
	public static Block MithrilBlock;
    public static Item MithrilIngot;
    public static Item MithrilPickaxe;
    public static Item MithrilAxe;
    public static Item MithrilSword;
    public static Item MithrilShovel;
    public static Item MithrilHoe;
    public static Item MithrilBow;
    public static Item MithrilHelmet;
    public static Item MithrilChest;
    public static Item MithrilLegs;
    public static Item MithrilBoots;
    public static Item MithrilSkirt;
    public static Item MithrilWarhammer;
    public static Item MithrilBattleaxe;
    public static Item MithrilDagger;
    public static Item MithrilScimitar;
    public static Item MithrilMace;
    public static Item MithrilDust;
    public static Item MithrilStick;

    public static Block AdamantiumOre;
	public static Block AdamantiumBlock;
    public static Item AdamantiumIngot;
    public static Item AdamantiumPickaxe;
    public static Item AdamantiumAxe;
    public static Item AdamantiumSword;
    public static Item AdamantiumShovel;
    public static Item AdamantiumHoe;
    public static Item AdamantiumBow;
    public static Item AdamantiumHelmet;
    public static Item AdamantiumChest;
    public static Item AdamantiumLegs;
    public static Item AdamantiumBoots;
    public static Item AdamantiumSkirt;
    public static Item AdamantiumWarhammer;
    public static Item AdamantiumBattleaxe;
    public static Item AdamantiumDagger;
    public static Item AdamantiumScimitar;
    public static Item AdamantiumMace;
    public static Item AdamantiumDust;
    public static Item AdamantiumStick;

	public static Block RuniteOre;
	public static Block RuniteBlock;
    public static Item RuniteIngot;
    public static Item RunitePickaxe;
    public static Item RuniteAxe;
    public static Item RuniteSword;
    public static Item RuniteShovel;
    public static Item RuniteHoe;
    public static Item RuniteBow;
    public static Item RuniteHelmet;
    public static Item RuniteChest;
    public static Item RuniteLegs;
    public static Item RuniteBoots;
    public static Item RuniteSkirt;
    public static Item RuniteWarhammer;
    public static Item RuniteBattleaxe;
    public static Item RuniteDagger;
    public static Item RuniteScimitar;
    public static Item RuniteMace;
    public static Item RuniteDust;
    public static Item RuniteStick;

	public static Block DragoniteOre;
	public static Block DragoniteBlock;
    public static Item DragoniteIngot;
    public static Item DragoniteShard;
    public static Item DragonitePickaxe;
    public static Item DragoniteAxe;
    public static Item DragoniteSword;
    public static Item DragoniteShovel;
    public static Item DragoniteHoe;
    public static Item DragoniteBow;
    public static Item DragoniteHelmet;
    public static Item DragoniteChest;
    public static Item DragoniteLegs;
    public static Item DragoniteBoots;
    public static Item DragoniteSkirt;
    public static Item DragoniteWarhammer;
    public static Item DragoniteBattleaxe;
    public static Item DragoniteDagger;
    public static Item DragoniteScimitar;
    public static Item DragoniteMace;
    public static Item DragoniteDust;
    public static Item DragoniteStick;

	public static Block CrystalOre;
	public static Block CrystalBlock;
    public static Item CrystalShard;
    public static Item CrystalFused;
    public static Item CrystalPickaxe;
    public static Item CrystalAxe;
    public static Item CrystalSword;
    public static Item CrystalShovel;
    public static Item CrystalHoe;
    public static Item CrystalBow;
    //public static Item CrystalHelmet;
    //public static Item CrystalChest;
    //public static Item CrystalLegs;
    //public static Item CrystalBoots;
    //public static Item CrystalSkirt;
    public static Item CrystalWarhammer;
    public static Item CrystalBattleaxe;
    public static Item CrystalDagger;
    public static Item CrystalScimitar;
    public static Item CrystalMace;
    public static Item CrystalDust;
    public static Item CrystalStick;

	public static Block SilverOre;
	public static Block SilverBlock;
    public static Item SilverIngot;
    public static Item SilverPickaxe;
    public static Item SilverAxe;
    public static Item SilverSword;
    public static Item SilverShovel;
    public static Item SilverHoe;
    public static Item SilverBow;
    public static Item SilverHelmet;
    public static Item SilverChest;
    public static Item SilverLegs;
    public static Item SilverBoots;
    public static Item SilverSkirt;
    public static Item SilverWarhammer;
    public static Item SilverBattleaxe;
    public static Item SilverDagger;
    public static Item SilverScimitar;
    public static Item SilverMace;
    public static Item SilverDust;
    public static Item SilverStick;

	public static Block BronzeBlock;
    public static Item BronzeIngot;
    public static Item BronzePickaxe;
    public static Item BronzeAxe;
    public static Item BronzeSword;
    public static Item BronzeShovel;
    public static Item BronzeHoe;
    public static Item BronzeBow;
    public static Item BronzeHelmet;
    public static Item BronzeChest;
    public static Item BronzeLegs;
    public static Item BronzeBoots;
    public static Item BronzeSkirt;
    public static Item BronzeWarhammer;
    public static Item BronzeBattleaxe;
    public static Item BronzeDagger;
    public static Item BronzeScimitar;
    public static Item BronzeMace;
	public static Block CopperOre;
    public static Item CopperIngot;
	public static Block CopperBlock;
	public static Block CopperWire;
	public static Item CopperWireItem;
	public static Block TinOre;
    public static Item TinIngot;
	public static Block TinBlock;
    public static Item TinDust;
    public static Item CopperDust;
    public static Item BronzeStick;
    public static Item CopperStick;

	public static Block BluriteOre;
	public static Block BluriteBlock;
    public static Item BluriteIngot;
    public static Item BluritePickaxe;
    public static Item BluriteAxe;
    public static Item BluriteSword;
    public static Item BluriteShovel;
    public static Item BluriteHoe;
    public static Item BluriteBow;
    //public static Item BluriteHelmet;
    //public static Item BluriteChest;
    //public static Item BluriteLegs;
    //public static Item BluriteBoots;
    //public static Item BluriteSkirt;
    public static Item BluriteWarhammer;
    public static Item BluriteBattleaxe;
    public static Item BluriteDagger;
    public static Item BluriteScimitar;
    public static Item BluriteMace;
    //public static Item BluriteDust;
    //public static Item BluriteStick;

	public static Block SteelBlock;
    public static Item SteelIngot;
    public static Item SteelPickaxe;
    public static Item SteelAxe;
    public static Item SteelSword;
    public static Item SteelShovel;
    public static Item SteelHoe;
    public static Item SteelBow;
    public static Item SteelHelmet;
    public static Item SteelChest;
    public static Item SteelLegs;
    public static Item SteelBoots;
    public static Item SteelSkirt;
    public static Item SteelWarhammer;
    public static Item SteelBattleaxe;
    public static Item SteelDagger;
    public static Item SteelScimitar;
    public static Item SteelMace;
    public static Item SteelDust;
    public static Item SteelStick;

	public static Block ObsidiumOre;
	public static Block ObsidiumBlock;
    public static Item ObsidiumIngot;
    public static Item ObsidiumPickaxe;
    public static Item ObsidiumAxe;
    public static Item ObsidiumSword;
    public static Item ObsidiumShovel;
    public static Item ObsidiumHoe;
    public static Item ObsidiumBow;
    public static Item ObsidiumHelmet;
    public static Item ObsidiumChest;
    public static Item ObsidiumLegs;
    public static Item ObsidiumBoots;
    public static Item ObsidiumSkirt;
    public static Item ObsidiumWarhammer;
    public static Item ObsidiumBattleaxe;
    public static Item ObsidiumDagger;
    public static Item ObsidiumScimitar;
    public static Item ObsidiumMace;
    public static Item ObsidiumDust;
    public static Item ObsidiumStick;

    public static Block TitaniumOre;
	public static Block TitaniumBlock;
    public static Item TitaniumIngot;
    public static Item TitaniumPickaxe;
    public static Item TitaniumAxe;
    public static Item TitaniumSword;
    public static Item TitaniumShovel;
    public static Item TitaniumHoe;
    public static Item TitaniumBow;
    public static Item TitaniumHelmet;
    public static Item TitaniumChest;
    public static Item TitaniumLegs;
    public static Item TitaniumBoots;
    public static Item TitaniumSkirt;
    public static Item TitaniumWarhammer;
    public static Item TitaniumBattleaxe;
    public static Item TitaniumDagger;
    public static Item TitaniumScimitar;
    public static Item TitaniumMace;
    public static Item TitaniumDust;
    public static Item TitaniumStick;

//    public static Block ElementiumOre;
//    public static Block ElementiumBlock;
//    public static Item ElementiumIngot;
//    public static Item ElementiumPickaxe;
//    public static Item ElementiumAxe;
//    public static Item ElementiumSword;
//    public static Item ElementiumShovel;
//    public static Item ElementiumHoe;
//    public static Item ElementiumBow;
//    public static Item ElementiumHelmet;
//    public static Item ElementiumChest;
//    public static Item ElementiumLegs;
//    public static Item ElementiumBoots;
//    public static Item ElementiumSkirt;
//    public static Item ElementiumWarhammer;
//    public static Item ElementiumBattleaxe;
//    public static Item ElementiumDagger;
//    public static Item ElementiumScimitar;
//    public static Item ElementiumMace;
//    public static Item ElementiumDust;
//    public static Item ElementiumStick;

    public static int MithrilArmourRendererPrefix;
    public static int AdamantiumArmourRendererPrefix;
    public static int RuniteArmourRendererPrefix;
    public static int DragoniteArmourRendererPrefix;
//    public static int CrystalArmourRendererPrefix;
    public static int SilverArmourRendererPrefix;
    public static int BronzeArmourRendererPrefix;
//    public static int BluriteArmourRendererPrefix;
    public static int SteelArmourRendererPrefix;
//    public static int ObsidiumArmourRendererPrefix;
    public static int TitaniumArmourRendererPrefix;
//    public static int ElementiumArmourRendererPrefix;
    
    public static Block LightBulbActive;
    public static Block LightBulbIdle;
    public static Block TitaniumLampActive;
    public static Block TitaniumLampIdle;
    public static Block thinGlass;
    public static Block glassCube;

    public static Item NightVision;
    public static Item JetPack;
    public static Item MissileLauncher;
    public static Item Missile;

    public static Item DiamondBow;
    public static Item DiamondSkirt;
    public static Item DiamondWarhammer;
    public static Item DiamondBattleaxe;
    public static Item DiamondDagger;
    public static Item DiamondScimitar;
    public static Item DiamondMace;
    public static Item GoldBow;
    public static Item GoldSkirt;
    public static Item GoldWarhammer;
    public static Item GoldBattleaxe;
    public static Item GoldDagger;
    public static Item GoldScimitar;
    public static Item GoldMace;
    public static Item GoldStick;
    public static Item IronBow;
    public static Item IronSkirt;
    public static Item IronWarhammer;
    public static Item IronBattleaxe;
    public static Item IronDagger;
    public static Item IronScimitar;
    public static Item IronMace;
    public static Item IronStick;

    public static Item NinjaStar;
    public static Item KatanaSword;
    public static Item Cutlass;
    //public static Item MysticSword;
    
    public static Block woolCarpet;
    public static Block clayHardened;
    public static Block blastFurnaceIdle;
    public static Block blastFurnaceBurning;
    public static Block craftingTable;
    public static Block dirtSlab;
    public static Block planter;
    public static Block vase;

    public static Item StaffBlue;
    public static Item StaffNature;
    public static Item StaffLightning;
    public static Item StaffTeleport;
    public static Item TwilightWand;

    public static Item essenceRune;
    public static Item waterRune;
    public static Item airRune;
    public static Item earthRune;
    public static Item fireRune;
    public static Item cosmicRune;
    public static Item chaosRune;
    public static Item astralRune;
    public static Item natureRune;
    //public static Item natureRune;
    public static Item bloodRune;

    public static final Block[] models = new Block[] {
        Block.stone, Block.cobblestone, Block.brick, Block.stoneBrick,
        Block.sand, Block.grass, Block.dirt, Block.gravel,
        Block.sandStone, Block.sandStone, Block.sandStone, Block.blockClay,
        Block.planks, Block.planks, Block.planks, Block.planks,
        Block.blockSnow, Block.ice, Block.whiteStone, Block.obsidian
    };
    public static final int[] modelsMeta = new int[] {
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 1, 2, 0,
        0, 1, 2, 3,
        0, 0, 0, 0
    };
    public static Block[] inclined = new Block[models.length];
    public static Block[] inclinedCorner = new Block[models.length];

    public static int inclinedModelID;
    public static int inclinedCornerModelID;

    public static int lightBulbModelID;
    public static int titaniumLampModelID;
    public static int copperWireModelID;
    public static int thinGlassModelID;
    public static int glassCubeModelID;
    public static int blastFurnaceModelID;
    public static int planterModelID;
    public static int vaseModelID;
    public static int dirtSlabModelID;

    private static World world;
    private static World worldServer;
    private static Minecraft mc;
    private static MCEnhancements instance;

    public static int BLOCKID_DISPLACEMENT = 2500;
    public static int FIRST_BLOCKID = 200;
    public static int FIRST_ITEMID = 17000 - 256;

    public static void onTickInGame(Minecraft minecraft)
    {
    	world = minecraft.theWorld;
    	worldServer = minecraft.getIntegratedServer().worldServers[0];
    }

	public static void init()
    {
	    BLOCKID_DISPLACEMENT = MCE_Settings.displacedBlockID;
	    FIRST_BLOCKID = MCE_Settings.firstBlockID;
	    FIRST_ITEMID = MCE_Settings.firstItemID - 256;

	    if (MCE_Settings.Items.equalsIgnoreCase("yes"))
        {
            mc = FMLClientHandler.instance().getClient();
        	instance = MCEnhancements.instance;

            inclinedModelID = RenderingRegistry.getNextAvailableRenderId();
            inclinedCornerModelID = RenderingRegistry.getNextAvailableRenderId();
            for (int var1 = 0; var1 < models.length; ++var1)
            {
                inclined[var1] = (new BlockInclined(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 140 + var1, models[var1])).setUnlocalizedName("inclined." + BlockInclined.blockType[var1] + modelsMeta[var1]);
                LanguageRegistry.addName(inclined[var1], "Inclined " + getBlockInfo(models[var1], modelsMeta[var1]));
                Item.itemsList[inclined[var1].blockID] = (new ItemBlockInclined(inclined[var1].blockID - 256, inclined[var1])).setUnlocalizedName("inclined." + BlockInclined.blockType[var1] + modelsMeta[var1]);
            	GameRegistry.addRecipe(new ItemStack(inclined[var1], 2), new Object[] {"   ", "  #", "   ", '#', models[var1]});

                inclinedCorner[var1] = (new BlockInclinedCorner(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 160 + var1, models[var1])).setUnlocalizedName("inclinedCorner." + BlockInclinedCorner.blockType[var1] + modelsMeta[var1]);
                LanguageRegistry.addName(inclinedCorner[var1], "Inclined Corner " + getBlockInfo(models[var1], modelsMeta[var1]));
                Item.itemsList[inclinedCorner[var1].blockID] = (new ItemBlockInclinedCorner(inclinedCorner[var1].blockID - 256, inclinedCorner[var1])).setUnlocalizedName("inclinedCorner." + BlockInclinedCorner.blockType[var1] + modelsMeta[var1]);
            	GameRegistry.addRecipe(new ItemStack(inclinedCorner[var1], 2), new Object[] {"  #", "   ", "   ", '#', models[var1]});
            }

            if (MCE_Settings.Titanium.equalsIgnoreCase("yes"))
            {
            	TitaniumArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Titanium");
            	TitaniumOre = (new BlockOre(FIRST_BLOCKID + 9)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("TitaniumOre");
            	TitaniumBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 29)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("TitaniumBlock");
            	TitaniumIngot = (new Item(FIRST_ITEMID + 90)).setUnlocalizedName("TitaniumIngot").setCreativeTab(CreativeTabs.tabMaterials);
            	TitaniumPickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 91, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumPickaxe");
            	TitaniumAxe = (new ItemAxe(FIRST_ITEMID + 92, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumAxe");
            	TitaniumSword = (new ItemSword(FIRST_ITEMID + 94, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumSword");
            	TitaniumShovel = (new ItemSpade(FIRST_ITEMID + 93, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumShovel");
            	TitaniumHoe = (new ItemHoe(FIRST_ITEMID + 95, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumHoe");
            	TitaniumBow = (new ItemMetalBow(FIRST_ITEMID + 290, 1024, 7.0F)).setUnlocalizedName("TitaniumBow");
            	TitaniumHelmet = (new ItemArmor(FIRST_ITEMID + 96, EnumArmorMaterialMCE.TITANIUM, TitaniumArmourRendererPrefix, 0)).setUnlocalizedName("TitaniumHelmet");
            	TitaniumChest = (new ItemArmor(FIRST_ITEMID + 97, EnumArmorMaterialMCE.TITANIUM, TitaniumArmourRendererPrefix, 1)).setUnlocalizedName("TitaniumChest");
            	TitaniumLegs = (new ItemArmor(FIRST_ITEMID + 98, EnumArmorMaterialMCE.TITANIUM, TitaniumArmourRendererPrefix, 2)).setUnlocalizedName("TitaniumLegs");
            	TitaniumBoots = (new ItemArmor(FIRST_ITEMID + 99, EnumArmorMaterialMCE.TITANIUM, TitaniumArmourRendererPrefix, 3)).setUnlocalizedName("TitaniumBoots");
            	TitaniumSkirt = (new ItemArmor(FIRST_ITEMID + 298, EnumArmorMaterialMCE.TITANIUM, TitaniumArmourRendererPrefix, 2)).setUnlocalizedName("TitaniumSkirt");
            	TitaniumWarhammer = (new ItemWarhammer(FIRST_ITEMID + 291, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumWarhammer");
            	TitaniumBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 292, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumBattleaxe");
            	TitaniumDagger = (new ItemDagger(FIRST_ITEMID + 293, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumDagger");
            	TitaniumScimitar = (new ItemScimitar(FIRST_ITEMID + 295, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumScimitar");
            	TitaniumMace = (new ItemMace(FIRST_ITEMID + 294, EnumToolMaterialMCE.TITANIUM)).setUnlocalizedName("TitaniumMace");
            	TitaniumStick = (new Item(FIRST_ITEMID + 179)).setFull3D().setUnlocalizedName("TitaniumStick").setCreativeTab(CreativeTabs.tabMaterials);
            	loadTitanium();
            }
            if (MCE_Settings.Mithril.equalsIgnoreCase("yes"))
            {
            	MithrilArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Mithril");
            	MithrilOre = (new BlockOre(FIRST_BLOCKID + 0)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("MithrilOre");
            	MithrilBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 20)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("MithrilBlock").setCreativeTab(CreativeTabs.tabBlock);
            	MithrilIngot = (new Item(FIRST_ITEMID + 0)).setUnlocalizedName("MithrilIngot").setCreativeTab(CreativeTabs.tabMaterials);
            	MithrilPickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 1, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilPickaxe");
            	MithrilAxe = (new ItemAxe(FIRST_ITEMID + 2, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilAxe");
            	MithrilSword = (new ItemSword(FIRST_ITEMID + 4, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilSword");
            	MithrilShovel = (new ItemSpade(FIRST_ITEMID + 3, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilShovel");
            	MithrilHoe = (new ItemHoe(FIRST_ITEMID + 5, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilHoe");
            	MithrilBow = (new ItemMetalBow(FIRST_ITEMID + 200, 768, 4.0F)).setUnlocalizedName("MithrilBow");
            	MithrilHelmet = (new ItemArmor(FIRST_ITEMID + 6, EnumArmorMaterialMCE.MITHRIL, MithrilArmourRendererPrefix, 0)).setUnlocalizedName("MithrilHelmet");
            	MithrilChest = (new ItemArmor(FIRST_ITEMID + 7, EnumArmorMaterialMCE.MITHRIL, MithrilArmourRendererPrefix, 1)).setUnlocalizedName("MithrilChest");
            	MithrilLegs = (new ItemArmor(FIRST_ITEMID + 8, EnumArmorMaterialMCE.MITHRIL, MithrilArmourRendererPrefix, 2)).setUnlocalizedName("MithrilLegs");
            	MithrilBoots = (new ItemArmor(FIRST_ITEMID + 9, EnumArmorMaterialMCE.MITHRIL, MithrilArmourRendererPrefix, 3)).setUnlocalizedName("MithrilBoots");
            	MithrilSkirt = (new ItemArmor(FIRST_ITEMID + 208, EnumArmorMaterialMCE.MITHRIL, MithrilArmourRendererPrefix, 2)).setUnlocalizedName("MithrilSkirt");
            	MithrilWarhammer = (new ItemWarhammer(FIRST_ITEMID + 201, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilWarhammer");
            	MithrilBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 202, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilBattleaxe");
            	MithrilDagger = (new ItemDagger(FIRST_ITEMID + 203, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilDagger");
            	MithrilScimitar = (new ItemScimitar(FIRST_ITEMID + 205, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilScimitar");
            	MithrilMace = (new ItemMace(FIRST_ITEMID + 204, EnumToolMaterialMCE.MITHRIL)).setUnlocalizedName("MithrilMace");
            	MithrilStick = (new Item(FIRST_ITEMID + 170)).setFull3D().setUnlocalizedName("MithrilStick").setCreativeTab(CreativeTabs.tabMaterials);
            	loadMithril();
            }
            if (MCE_Settings.Adamantium.equalsIgnoreCase("yes"))
            {
            	AdamantiumArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Adamantium");
                AdamantiumOre = (new BlockOre(FIRST_BLOCKID + 1)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("AdamantiumOre");
            	AdamantiumBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 21)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("AdamantiumBlock").setCreativeTab(CreativeTabs.tabBlock);
                AdamantiumIngot = (new Item(FIRST_ITEMID + 10)).setUnlocalizedName("AdamantiumIngot").setCreativeTab(CreativeTabs.tabMaterials);
                AdamantiumPickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 11, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumPickaxe");
                AdamantiumAxe = (new ItemAxe(FIRST_ITEMID + 12, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumAxe");
                AdamantiumSword = (new ItemSword(FIRST_ITEMID + 14, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumSword");
                AdamantiumShovel = (new ItemSpade(FIRST_ITEMID + 13, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumShovel");
                AdamantiumHoe = (new ItemHoe(FIRST_ITEMID + 15, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumHoe");
                AdamantiumBow = (new ItemMetalBow(FIRST_ITEMID + 210, 800, 5.0F)).setUnlocalizedName("AdamantiumBow");
                AdamantiumHelmet = (new ItemArmor(FIRST_ITEMID + 16, EnumArmorMaterialMCE.ADAMANTIUM, AdamantiumArmourRendererPrefix, 0)).setUnlocalizedName("AdamantiumHelmet");
                AdamantiumChest = (new ItemArmor(FIRST_ITEMID + 17, EnumArmorMaterialMCE.ADAMANTIUM, AdamantiumArmourRendererPrefix, 1)).setUnlocalizedName("AdamantiumChest");
                AdamantiumLegs = (new ItemArmor(FIRST_ITEMID + 18, EnumArmorMaterialMCE.ADAMANTIUM, AdamantiumArmourRendererPrefix, 2)).setUnlocalizedName("AdamantiumLegs");
                AdamantiumBoots = (new ItemArmor(FIRST_ITEMID + 19, EnumArmorMaterialMCE.ADAMANTIUM, AdamantiumArmourRendererPrefix, 3)).setUnlocalizedName("AdamantiumBoots");
            	AdamantiumSkirt = (new ItemArmor(FIRST_ITEMID + 218, EnumArmorMaterialMCE.ADAMANTIUM, AdamantiumArmourRendererPrefix, 2)).setUnlocalizedName("AdamantiumSkirt");
                AdamantiumWarhammer = (new ItemWarhammer(FIRST_ITEMID + 211, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumWarhammer");
                AdamantiumBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 212, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumBattleaxe");
                AdamantiumDagger = (new ItemDagger(FIRST_ITEMID + 213, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumDagger");
            	AdamantiumScimitar = (new ItemScimitar(FIRST_ITEMID + 215, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumScimitar");
                AdamantiumMace = (new ItemMace(FIRST_ITEMID + 214, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("AdamantiumMace");
                AdamantiumStick = (new Item(FIRST_ITEMID + 171)).setFull3D().setUnlocalizedName("AdamantiumStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadAdamantium();
            }
            if (MCE_Settings.Runite.equalsIgnoreCase("yes"))
            {
            	RuniteArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Runite");
            	RuniteOre = (new BlockOre(FIRST_BLOCKID + 2)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("RuniteOre");
            	RuniteBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 22)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("RuniteBlock").setCreativeTab(CreativeTabs.tabBlock);
                RuniteIngot = (new Item(FIRST_ITEMID + 20)).setUnlocalizedName("RuniteIngot").setCreativeTab(CreativeTabs.tabMaterials);
                RunitePickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 21, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RunitePickaxe");
                RuniteAxe = (new ItemAxe(FIRST_ITEMID + 22, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteAxe");
                RuniteSword = (new ItemSword(FIRST_ITEMID + 24, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteSword");
                RuniteShovel = (new ItemSpade(FIRST_ITEMID + 23, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteShovel");
                RuniteHoe = (new ItemHoe(FIRST_ITEMID + 25, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteHoe");
                RuniteBow = (new ItemMetalBow(FIRST_ITEMID + 220, 912, 6.0F)).setUnlocalizedName("RuniteBow");
                RuniteHelmet = (new ItemArmor(FIRST_ITEMID + 26, EnumArmorMaterialMCE.RUNITE, RuniteArmourRendererPrefix, 0)).setUnlocalizedName("RuniteHelmet");
                RuniteChest = (new ItemArmor(FIRST_ITEMID + 27, EnumArmorMaterialMCE.RUNITE, RuniteArmourRendererPrefix, 1)).setUnlocalizedName("RuniteChest");
                RuniteLegs = (new ItemArmor(FIRST_ITEMID + 28, EnumArmorMaterialMCE.RUNITE, RuniteArmourRendererPrefix, 2)).setUnlocalizedName("RuniteLegs");
                RuniteBoots = (new ItemArmor(FIRST_ITEMID + 29, EnumArmorMaterialMCE.RUNITE, RuniteArmourRendererPrefix, 3)).setUnlocalizedName("RuniteBoots");
            	RuniteSkirt = (new ItemArmor(FIRST_ITEMID + 228, EnumArmorMaterialMCE.RUNITE, RuniteArmourRendererPrefix, 2)).setUnlocalizedName("RuniteSkirt");
                RuniteWarhammer = (new ItemWarhammer(FIRST_ITEMID + 221, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteWarhammer");
                RuniteBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 222, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteBattleaxe");
                RuniteDagger = (new ItemDagger(FIRST_ITEMID + 223, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteDagger");
            	RuniteScimitar = (new ItemScimitar(FIRST_ITEMID + 225, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteScimitar");
                RuniteMace = (new ItemMace(FIRST_ITEMID + 224, EnumToolMaterialMCE.RUNITE)).setUnlocalizedName("RuniteMace");
                RuniteStick = (new Item(FIRST_ITEMID + 172)).setFull3D().setUnlocalizedName("RuniteStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadRunite();
            }
            if (MCE_Settings.Dragonite.equalsIgnoreCase("yes"))
            {
            	DragoniteArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Dragonite");
                DragoniteOre = (new BlockDragonite(FIRST_BLOCKID + 4)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("DragoniteOre");
            	DragoniteBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 24)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("DragoniteBlock").setCreativeTab(CreativeTabs.tabBlock);
                DragoniteIngot = (new Item(FIRST_ITEMID + 40)).setUnlocalizedName("DragoniteIngot").setCreativeTab(CreativeTabs.tabMaterials);
                DragoniteShard = (new Item(FIRST_ITEMID + 150)).setUnlocalizedName("DragoniteShard").setCreativeTab(CreativeTabs.tabMaterials);
                DragonitePickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 41, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragonitePickaxe");
                DragoniteAxe = (new ItemAxe(FIRST_ITEMID + 42, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteAxe");
                DragoniteSword = (new ItemSword(FIRST_ITEMID + 44, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteSword");
                DragoniteShovel = (new ItemSpade(FIRST_ITEMID + 43, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteShovel");
                DragoniteHoe = (new ItemHoe(FIRST_ITEMID + 45, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteHoe");
                DragoniteBow = (new ItemMetalBow(FIRST_ITEMID + 240, 1024, 7.0F)).setUnlocalizedName("DragoniteBow");
                DragoniteHelmet = (new ItemArmor(FIRST_ITEMID + 46, EnumArmorMaterialMCE.DRAGONITE, DragoniteArmourRendererPrefix, 0)).setUnlocalizedName("DragoniteHelmet");
                DragoniteChest = (new ItemArmor(FIRST_ITEMID + 47, EnumArmorMaterialMCE.DRAGONITE, DragoniteArmourRendererPrefix, 1)).setUnlocalizedName("DragoniteChest");
                DragoniteLegs = (new ItemArmor(FIRST_ITEMID + 48, EnumArmorMaterialMCE.DRAGONITE, DragoniteArmourRendererPrefix, 2)).setUnlocalizedName("DragoniteLegs");
                DragoniteBoots = (new ItemArmor(FIRST_ITEMID + 49, EnumArmorMaterialMCE.DRAGONITE, DragoniteArmourRendererPrefix, 3)).setUnlocalizedName("DragoniteBoots");
            	DragoniteSkirt = (new ItemArmor(FIRST_ITEMID + 248, EnumArmorMaterialMCE.DRAGONITE, DragoniteArmourRendererPrefix, 2)).setUnlocalizedName("DragoniteSkirt");
                DragoniteWarhammer = (new ItemWarhammer(FIRST_ITEMID + 241, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteWarhammer");
                DragoniteBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 242, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteBattleaxe");
                DragoniteDagger = (new ItemDagger(FIRST_ITEMID + 243, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteDagger");
            	DragoniteScimitar = (new ItemScimitar(FIRST_ITEMID + 245, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteScimitar");
                DragoniteMace = (new ItemMace(FIRST_ITEMID + 244, EnumToolMaterialMCE.DRAGONITE)).setUnlocalizedName("DragoniteMace");
                DragoniteStick = (new Item(FIRST_ITEMID + 174)).setFull3D().setUnlocalizedName("DragoniteStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadDragonite();
            }
            if (MCE_Settings.Crystal.equalsIgnoreCase("yes"))
            {
            	//CrystalArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Crystal");
            	CrystalOre = (new BlockCrystal(FIRST_BLOCKID + 7)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("CrystalOre");
            	CrystalBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 27)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("CrystalBlock").setCreativeTab(CreativeTabs.tabBlock);
                CrystalShard = (new Item(FIRST_ITEMID + 152)).setUnlocalizedName("CrystalShard").setCreativeTab(CreativeTabs.tabMaterials);
                CrystalFused = (new Item(FIRST_ITEMID + 70)).setUnlocalizedName("CrystalFused").setCreativeTab(CreativeTabs.tabMaterials);
                CrystalPickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 71, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalPickaxe");
                CrystalAxe = (new ItemAxe(FIRST_ITEMID + 72, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalAxe");
                CrystalSword = (new ItemSword(FIRST_ITEMID + 74, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalSword");
                CrystalShovel = (new ItemSpade(FIRST_ITEMID + 73, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalShovel");
                CrystalHoe = (new ItemHoe(FIRST_ITEMID + 75, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalHoe");
                CrystalBow = (new ItemMetalBow(FIRST_ITEMID + 270, 2048, 8.0F)).setUnlocalizedName("CrystalBow");
                CrystalWarhammer = (new ItemWarhammer(FIRST_ITEMID + 271, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalWarhammer");
                CrystalBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 272, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalBattleaxe");
                CrystalDagger = (new ItemDagger(FIRST_ITEMID + 273, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalDagger");
            	CrystalScimitar = (new ItemScimitar(FIRST_ITEMID + 275, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalScimitar");
                CrystalMace = (new ItemMace(FIRST_ITEMID + 274, EnumToolMaterialMCE.CRYSTAL)).setUnlocalizedName("CrystalMace");
                //CrystalHelmet = (new ItemArmor(FIRST_ITEMID + 76, EnumArmorMaterialMCE.CRYSTAL, CrystalArmourRendererPrefix, 0)).setUnlocalizedName("CrystalHelmet");
                //CrystalChest = (new ItemArmor(FIRST_ITEMID + 77, EnumArmorMaterialMCE.CRYSTAL, CrystalArmourRendererPrefix, 1)).setUnlocalizedName("CrystalChest");
                //CrystalLegs = (new ItemArmor(FIRST_ITEMID + 78, EnumArmorMaterialMCE.CRYSTAL, CrystalArmourRendererPrefix, 2)).setUnlocalizedName("CrystalLegs");
                //CrystalBoots = (new ItemArmor(FIRST_ITEMID + 79, EnumArmorMaterialMCE.CRYSTAL, CrystalArmourRendererPrefix, 3)).setUnlocalizedName("CrystalBoots");
            	//CrystalSkirt = (new ItemArmor(FIRST_ITEMID + 278, EnumArmorMaterialMCE.CRYSTAL, CrystalArmourRendererPrefix, 2)).setUnlocalizedName("CrystalSkirt");
                CrystalStick = (new Item(FIRST_ITEMID + 177)).setFull3D().setUnlocalizedName("CrystalStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadCrystal();
            }
            if (MCE_Settings.Silver.equalsIgnoreCase("yes"))
            {
            	SilverArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Silver");
            	SilverOre = (new BlockOre(FIRST_BLOCKID + 6)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("SilverOre");
            	SilverBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 26)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("SilverBlock").setCreativeTab(CreativeTabs.tabBlock);
                SilverIngot = (new Item(FIRST_ITEMID + 60)).setUnlocalizedName("SilverIngot").setCreativeTab(CreativeTabs.tabMaterials);
                SilverPickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 61, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverPickaxe");
                SilverAxe = (new ItemAxe(FIRST_ITEMID + 62, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverAxe");
                SilverSword = (new ItemSword(FIRST_ITEMID + 64, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverSword");
                SilverShovel = (new ItemSpade(FIRST_ITEMID + 63, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverShovel");
                SilverHoe = (new ItemHoe(FIRST_ITEMID + 65, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverHoe");
                SilverBow = (new ItemMetalBow(FIRST_ITEMID + 260, 384, 2.5F)).setUnlocalizedName("SilverBow");
                SilverHelmet = (new ItemArmor(FIRST_ITEMID + 66, EnumArmorMaterialMCE.SILVER, SilverArmourRendererPrefix, 0)).setUnlocalizedName("SilverHelmet");
                SilverChest = (new ItemArmor(FIRST_ITEMID + 67, EnumArmorMaterialMCE.SILVER, SilverArmourRendererPrefix, 1)).setUnlocalizedName("SilverChest");
                SilverLegs = (new ItemArmor(FIRST_ITEMID + 68, EnumArmorMaterialMCE.SILVER, SilverArmourRendererPrefix, 2)).setUnlocalizedName("SilverLegs");
                SilverBoots = (new ItemArmor(FIRST_ITEMID + 69, EnumArmorMaterialMCE.SILVER, SilverArmourRendererPrefix, 3)).setUnlocalizedName("SilverBoots");
            	SilverSkirt = (new ItemArmor(FIRST_ITEMID + 268, EnumArmorMaterialMCE.SILVER, SilverArmourRendererPrefix, 2)).setUnlocalizedName("SilverSkirt");
                SilverWarhammer = (new ItemWarhammer(FIRST_ITEMID + 261, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverWarhammer");
                SilverBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 262, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverBattleaxe");
                SilverDagger = (new ItemDagger(FIRST_ITEMID + 263, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverDagger");
            	SilverScimitar = (new ItemScimitar(FIRST_ITEMID + 265, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverScimitar");
                SilverMace = (new ItemMace(FIRST_ITEMID + 264, EnumToolMaterialMCE.SILVER)).setUnlocalizedName("SilverMace");
                SilverStick = (new Item(FIRST_ITEMID + 176)).setFull3D().setUnlocalizedName("SilverStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadSilver();
            }
            if (MCE_Settings.Bronze.equalsIgnoreCase("yes"))
            {
            	BronzeArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Bronze");
            	BronzeBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 23)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("BronzeBlock").setCreativeTab(CreativeTabs.tabBlock);
                BronzeIngot = (new Item(FIRST_ITEMID + 30)).setUnlocalizedName("BronzeIngot").setCreativeTab(CreativeTabs.tabMaterials);
                BronzePickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 31, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzePickaxe");
                BronzeAxe = (new ItemAxe(FIRST_ITEMID + 32, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeAxe");
                BronzeSword = (new ItemSword(FIRST_ITEMID + 34, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeSword");
                BronzeShovel = (new ItemSpade(FIRST_ITEMID + 33, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeShovel");
                BronzeHoe = (new ItemHoe(FIRST_ITEMID + 35, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeHoe");
                BronzeBow = (new ItemMetalBow(FIRST_ITEMID + 230, 256, 2.0F)).setUnlocalizedName("BronzeBow");
                BronzeHelmet = (new ItemArmor(FIRST_ITEMID + 36, EnumArmorMaterialMCE.BRONZE, BronzeArmourRendererPrefix, 0)).setUnlocalizedName("BronzeHelmet");
                BronzeChest = (new ItemArmor(FIRST_ITEMID + 37, EnumArmorMaterialMCE.BRONZE, BronzeArmourRendererPrefix, 1)).setUnlocalizedName("BronzeChest");
                BronzeLegs = (new ItemArmor(FIRST_ITEMID + 38, EnumArmorMaterialMCE.BRONZE, BronzeArmourRendererPrefix, 2)).setUnlocalizedName("BronzeLegs");
                BronzeBoots = (new ItemArmor(FIRST_ITEMID + 39, EnumArmorMaterialMCE.BRONZE, BronzeArmourRendererPrefix, 3)).setUnlocalizedName("BronzeBoots");
                BronzeSkirt = (new ItemArmor(FIRST_ITEMID + 238, EnumArmorMaterialMCE.BRONZE, BronzeArmourRendererPrefix, 2)).setUnlocalizedName("BronzeSkirt");
                BronzeWarhammer = (new ItemWarhammer(FIRST_ITEMID + 231, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeWarhammer");
                BronzeBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 232, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeBattleaxe");
                BronzeDagger = (new ItemDagger(FIRST_ITEMID + 233, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeDagger");
                BronzeScimitar = (new ItemScimitar(FIRST_ITEMID + 235, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeScimitar");
                BronzeMace = (new ItemMace(FIRST_ITEMID + 234, EnumToolMaterialMCE.BRONZE)).setUnlocalizedName("BronzeMace");
            	CopperOre = (new BlockOre(FIRST_BLOCKID + 3)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("CopperOre");
                CopperIngot = (new Item(FIRST_ITEMID + 110)).setUnlocalizedName("CopperIngot").setCreativeTab(CreativeTabs.tabMaterials);
            	CopperBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 33)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("CopperBlock").setCreativeTab(CreativeTabs.tabBlock);
            	CopperWire = (new BlockCopperWire(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 35)).setHardness(0.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("CopperWire");
            	CopperWireItem = (new ItemCopperWire(FIRST_ITEMID + 236)).setUnlocalizedName("CopperWireItem").setCreativeTab(CreativeTabs.tabRedstone).setPotionEffect(PotionHelper.redstoneEffect);
            	TinOre = (new BlockOre(FIRST_BLOCKID + 11)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("TinOre");
                TinIngot = (new Item(FIRST_ITEMID + 111)).setUnlocalizedName("TinIngot").setCreativeTab(CreativeTabs.tabMaterials);
            	TinBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 34)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("TinBlock").setCreativeTab(CreativeTabs.tabBlock);
            	CopperStick = (new Item(FIRST_ITEMID + 183)).setFull3D().setUnlocalizedName("CopperStick").setCreativeTab(CreativeTabs.tabMaterials);
            	BronzeStick = (new Item(FIRST_ITEMID + 173)).setFull3D().setUnlocalizedName("BronzeStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadBronze();
            }
            if (MCE_Settings.Blurite.equalsIgnoreCase("yes"))
            {
            	//BluriteArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Blurite");
            	BluriteOre = (new BlockOre(FIRST_BLOCKID + 5)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("BluriteOre");
            	BluriteBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 25)).setHardness(3.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("BluriteBlock").setCreativeTab(CreativeTabs.tabBlock);
                BluriteIngot = (new Item(FIRST_ITEMID + 50)).setUnlocalizedName("BluriteIngot").setCreativeTab(CreativeTabs.tabMaterials);
                BluritePickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 51, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluritePickaxe");
                BluriteAxe = (new ItemAxe(FIRST_ITEMID + 52, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteAxe");
                BluriteSword = (new ItemSword(FIRST_ITEMID + 54, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteSword");
                BluriteShovel = (new ItemSpade(FIRST_ITEMID + 53, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteShovel");
                BluriteHoe = (new ItemHoe(FIRST_ITEMID + 55, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteHoe");
                BluriteBow = (new ItemMetalBow(FIRST_ITEMID + 250, 384, 2.5F)).setUnlocalizedName("BluriteBow");
                BluriteWarhammer = (new ItemWarhammer(FIRST_ITEMID + 251, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteWarhammer");
                BluriteBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 252, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteBattleaxe");
                BluriteDagger = (new ItemDagger(FIRST_ITEMID + 253, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteDagger");
            	BluriteScimitar = (new ItemScimitar(FIRST_ITEMID + 255, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteScimitar");
                BluriteMace = (new ItemMace(FIRST_ITEMID + 254, EnumToolMaterialMCE.BLURITE)).setUnlocalizedName("BluriteMace");
                //BluriteHelmet = (new ItemArmor(FIRST_ITEMID + 56, EnumArmorMaterialMCE.BLURITE, BluriteArmourRendererPrefix, 0)).setUnlocalizedName("BluriteHelmet");
                //BluriteChest = (new ItemArmor(FIRST_ITEMID + 57, EnumArmorMaterialMCE.BLURITE, BluriteArmourRendererPrefix, 1)).setUnlocalizedName("BluriteChest");
                //BluriteLegs = (new ItemArmor(FIRST_ITEMID + 58, EnumArmorMaterialMCE.BLURITE, BluriteArmourRendererPrefix, 2)).setUnlocalizedName("BluriteLegs");
                //BluriteBoots = (new ItemArmor(FIRST_ITEMID + 59, EnumArmorMaterialMCE.BLURITE, BluriteArmourRendererPrefix, 3)).setUnlocalizedName("BluriteBoots");
            	//BluriteSkirt = (new ItemArmor(FIRST_ITEMID + 258, EnumArmorMaterialMCE.BLURITE, BluriteArmourRendererPrefix, 2)).setUnlocalizedName("BluriteSkirt");
                //BluriteStick = (new Item(FIRST_ITEMID + 175)).setFull3D().setUnlocalizedName("BluriteStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadBlurite();
            }
            if (MCE_Settings.Steel.equalsIgnoreCase("yes"))
            {
            	SteelArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Steel");
            	SteelBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 32)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("SteelBlock").setCreativeTab(CreativeTabs.tabBlock);
                SteelIngot = (new Item(FIRST_ITEMID + 120)).setUnlocalizedName("SteelIngot").setCreativeTab(CreativeTabs.tabMaterials);
                SteelPickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 121, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelPickaxe");
                SteelAxe = (new ItemAxe(FIRST_ITEMID + 122, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelAxe");
                SteelSword = (new ItemSword(FIRST_ITEMID + 124, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelSword");
                SteelShovel = (new ItemSpade(FIRST_ITEMID + 123, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelShovel");
                SteelHoe = (new ItemHoe(FIRST_ITEMID + 125, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelHoe");
                SteelBow = (new ItemMetalBow(FIRST_ITEMID + 320, 512, 3.0F)).setUnlocalizedName("SteelBow");
                SteelHelmet = (new ItemArmor(FIRST_ITEMID + 126, EnumArmorMaterialMCE.STEEL, SteelArmourRendererPrefix, 0)).setUnlocalizedName("SteelHelmet");
                SteelChest = (new ItemArmor(FIRST_ITEMID + 127, EnumArmorMaterialMCE.STEEL, SteelArmourRendererPrefix, 1)).setUnlocalizedName("SteelChest");
                SteelLegs = (new ItemArmor(FIRST_ITEMID + 128, EnumArmorMaterialMCE.STEEL, SteelArmourRendererPrefix, 2)).setUnlocalizedName("SteelLegs");
                SteelBoots = (new ItemArmor(FIRST_ITEMID + 129, EnumArmorMaterialMCE.STEEL, SteelArmourRendererPrefix, 3)).setUnlocalizedName("SteelBoots");
            	SteelSkirt = (new ItemArmor(FIRST_ITEMID + 328, EnumArmorMaterialMCE.STEEL, SteelArmourRendererPrefix, 2)).setUnlocalizedName("SteelSkirt");
                SteelWarhammer = (new ItemWarhammer(FIRST_ITEMID + 321, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelWarhammer");
                SteelBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 322, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelBattleaxe");
                SteelDagger = (new ItemDagger(FIRST_ITEMID + 323, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelDagger");
            	SteelScimitar = (new ItemScimitar(FIRST_ITEMID + 325, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelScimitar");
                SteelMace = (new ItemMace(FIRST_ITEMID + 324, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("SteelMace");
                SteelStick = (new Item(FIRST_ITEMID + 182)).setFull3D().setUnlocalizedName("SteelStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadSteel();
            }
            if (MCE_Settings.Obsidium.equalsIgnoreCase("yes"))
            {
            	//ObsidiumArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Obsidium");
            	ObsidiumOre = (new BlockOre(FIRST_BLOCKID + 8)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("ObsidiumOre");
            	ObsidiumBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 28)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("ObsidiumBlock").setCreativeTab(CreativeTabs.tabBlock);
                ObsidiumIngot = (new Item(FIRST_ITEMID + 80)).setUnlocalizedName("ObsidiumIngot").setCreativeTab(CreativeTabs.tabMaterials);
                ObsidiumPickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 81, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumPickaxe");
                ObsidiumAxe = (new ItemAxe(FIRST_ITEMID + 82, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumAxe");
                ObsidiumSword = (new ItemSword(FIRST_ITEMID + 84, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumSword");
                ObsidiumShovel = (new ItemSpade(FIRST_ITEMID + 83, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumShovel");
                ObsidiumHoe = (new ItemHoe(FIRST_ITEMID + 85, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumHoe");
                ObsidiumBow = (new ItemMetalBow(FIRST_ITEMID + 280, 1560, 7.5F)).setUnlocalizedName("ObsidiumBow");
                ObsidiumWarhammer = (new ItemWarhammer(FIRST_ITEMID + 281, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumWarhammer");
                ObsidiumBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 282, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumBattleaxe");
                ObsidiumDagger = (new ItemDagger(FIRST_ITEMID + 283, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumDagger");
            	ObsidiumScimitar = (new ItemScimitar(FIRST_ITEMID + 285, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumScimitar");
                ObsidiumMace = (new ItemMace(FIRST_ITEMID + 284, EnumToolMaterialMCE.OBSIDIUM)).setUnlocalizedName("ObsidiumMace");
                //ObsidiumHelmet = (new ItemArmor(FIRST_ITEMID + 86, EnumArmorMaterialMCE.ONYX, ObsidiumArmourRendererPrefix, 0)).setUnlocalizedName("ObsidiumHelmet");
                //ObsidiumChest = (new ItemArmor(FIRST_ITEMID + 87, EnumArmorMaterialMCE.ONYX, ObsidiumArmourRendererPrefix, 1)).setUnlocalizedName("ObsidiumChest");
                //ObsidiumLegs = (new ItemArmor(FIRST_ITEMID + 88, EnumArmorMaterialMCE.ONYX, ObsidiumArmourRendererPrefix, 2)).setUnlocalizedName("ObsidiumLegs");
                //ObsidiumBoots = (new ItemArmor(FIRST_ITEMID + 89, EnumArmorMaterialMCE.ONYX, ObsidiumArmourRendererPrefix, 3)).setUnlocalizedName("ObsidiumBoots");
            	//ObsidiumSkirt = (new ItemArmor(FIRST_ITEMID + 288, EnumArmorMaterialMCE.ONYX, ObsidiumArmourRendererPrefix, 2)).setUnlocalizedName("ObsidiumSkirt");
                ObsidiumStick = (new Item(FIRST_ITEMID + 178)).setFull3D().setUnlocalizedName("ObsidiumStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadObsidium();
            }
            /*if (MCE_Settings.Elementium.equalsIgnoreCase("yes"))
            {
            	ElementiumArmourRendererPrefix = RenderingRegistry.addNewArmourRendererPrefix("Elementium");
                ElementiumOre = (new BlockOre(FIRST_BLOCKID + 10)).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("ElementiumOre");
            	ElementiumBlock = (new BlockOreStorage(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 30)).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("ElementiumBlock").setCreativeTab(CreativeTabs.tabBlock);
                ElementiumIngot = (new Item(FIRST_ITEMID + 100)).setUnlocalizedName("ElementiumIngot").setCreativeTab(CreativeTabs.tabMaterials);
                ElementiumPickaxe = (new ItemPickaxeMCE(FIRST_ITEMID + 101, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumPickaxe");
                ElementiumAxe = (new ItemAxe(FIRST_ITEMID + 102, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumAxe");
                ElementiumSword = (new ItemSword(FIRST_ITEMID + 104, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumSword");
                ElementiumShovel = (new ItemSpade(FIRST_ITEMID + 103, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumShovel");
                ElementiumHoe = (new ItemHoe(FIRST_ITEMID + 105, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumHoe");
                ElementiumBow = (new ItemMetalBow(FIRST_ITEMID + 300, 2048, 8.0F)).setUnlocalizedName("ElementiumBow");
                ElementiumHelmet = (new ItemArmor(FIRST_ITEMID + 105, EnumArmorMaterialMCE.ELEMENTIUM, ElementiumArmourRendererPrefix, 0)).setUnlocalizedName("ElementiumHelmet");
                ElementiumChest = (new ItemArmor(FIRST_ITEMID + 106, EnumArmorMaterialMCE.ELEMENTIUM, ElementiumArmourRendererPrefix, 1)).setUnlocalizedName("ElementiumChest");
                ElementiumLegs = (new ItemArmor(FIRST_ITEMID + 107, EnumArmorMaterialMCE.ELEMENTIUM, ElementiumArmourRendererPrefix, 2)).setUnlocalizedName("ElementiumLegs");
                ElementiumBoots = (new ItemArmor(FIRST_ITEMID + 108, EnumArmorMaterialMCE.ELEMENTIUM, ElementiumArmourRendererPrefix, 3)).setUnlocalizedName("ElementiumBoots");
            	ElementiumSkirt = (new ItemArmor(FIRST_ITEMID + 308, EnumArmorMaterialMCE.ELEMENTIUM, ElementiumArmourRendererPrefix, 2)).setUnlocalizedName("ElementiumSkirt");
                ElementiumWarhammer = (new ItemWarhammer(FIRST_ITEMID + 301, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumWarhammer");
                ElementiumBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 302, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumBattleaxe");
                ElementiumDagger = (new ItemDagger(FIRST_ITEMID + 303, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumDagger");
            	ElementiumScimitar = (new ItemScimitar(FIRST_ITEMID + 305, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumScimitar");
                ElementiumMace = (new ItemMace(FIRST_ITEMID + 304, EnumToolMaterialMCE.ELEMENTIUM)).setUnlocalizedName("ElementiumMace");
                ElementiumStick = (new Item(FIRST_ITEMID + 180)).setFull3D().setUnlocalizedName("ElementiumStick").setCreativeTab(CreativeTabs.tabMaterials);
                loadElementium();
            }*/
            if (MCE_Settings.Items.equalsIgnoreCase("yes"))
            {
                DiamondBow = (new ItemMetalBow(FIRST_ITEMID + 314, 768, 4.5F)).setUnlocalizedName("DiamondBow");
                //DiamondScimitar = (new ItemScimitar(FIRST_ITEMID + 340, EnumToolMaterial.EMERALD)).setUnlocalizedName("DiamondScimitar");
            	DiamondSkirt = (new ItemArmor(FIRST_ITEMID + 342, EnumArmorMaterial.DIAMOND, 3, 2)).setUnlocalizedName("DiamondSkirt");
                IronBow = (new ItemMetalBow(FIRST_ITEMID + 311, 384, 2.5F)).setUnlocalizedName("IronBow");
                IronWarhammer = (new ItemWarhammer(FIRST_ITEMID + 331, EnumToolMaterialMCE.IRON)).setUnlocalizedName("IronWarhammer");
                IronBattleaxe = (new ItemBattleaxe(FIRST_ITEMID + 332, EnumToolMaterialMCE.IRON)).setUnlocalizedName("IronBattleaxe");
                IronDagger = (new ItemDagger(FIRST_ITEMID + 333, EnumToolMaterialMCE.IRON)).setUnlocalizedName("IronDagger");
            	IronScimitar = (new ItemScimitar(FIRST_ITEMID + 335, EnumToolMaterialMCE.IRON)).setUnlocalizedName("IronScimitar");
                IronMace = (new ItemMace(FIRST_ITEMID + 334, EnumToolMaterialMCE.IRON)).setUnlocalizedName("IronMace");
            	IronSkirt = (new ItemArmor(FIRST_ITEMID + 330, EnumArmorMaterial.IRON, 2, 2)).setUnlocalizedName("IronSkirt");
                GoldBow = (new ItemMetalBow(FIRST_ITEMID + 315, 384, 2.5F)).setUnlocalizedName("GoldBow");
            	//GoldScimitar = (new ItemScimitar(FIRST_ITEMID + 341, EnumToolMaterial.GOLD)).setUnlocalizedName("GoldScimitar");
            	GoldSkirt = (new ItemArmor(FIRST_ITEMID + 343, EnumArmorMaterial.GOLD, 4, 2)).setUnlocalizedName("GoldSkirt");

                GoldStick = (new Item(FIRST_ITEMID + 185)).setFull3D().setUnlocalizedName("GoldStick").setCreativeTab(CreativeTabs.tabMaterials);
                IronStick = (new Item(FIRST_ITEMID + 184)).setFull3D().setUnlocalizedName("IronStick").setCreativeTab(CreativeTabs.tabMaterials);

                LanguageRegistry.addName(DiamondBow, "Diamond Bow");
                LanguageRegistry.addName(DiamondSkirt, "Diamond Skirt");
                //LanguageRegistry.addName(DiamondWarhammer, "Diamond Warhammer");
                //LanguageRegistry.addName(DiamondBattleaxe, "Diamond Battleaxe");
                //LanguageRegistry.addName(DiamondDagger, "Diamond Dagger");
                //LanguageRegistry.addName(DiamondScimitar, "Diamond Scimitar");
                //LanguageRegistry.addName(DiamondMace, "Diamond Mace");
                LanguageRegistry.addName(IronBow, "Iron Bow");
                LanguageRegistry.addName(IronSkirt, "Iron Skirt");
                LanguageRegistry.addName(IronWarhammer, "Iron Warhammer");
                LanguageRegistry.addName(IronBattleaxe, "Iron Battleaxe");
                LanguageRegistry.addName(IronDagger, "Iron Dagger");
                LanguageRegistry.addName(IronScimitar, "Iron Scimitar");
                LanguageRegistry.addName(IronMace, "Iron Mace");
                LanguageRegistry.addName(IronStick, "Iron Stick");
                LanguageRegistry.addName(GoldBow, "Gold Bow");
                LanguageRegistry.addName(GoldSkirt, "Gold Skirt");
                //LanguageRegistry.addName(GoldWarhammer, "Gold Warhammer");
                //LanguageRegistry.addName(GoldBattleaxe, "Gold Battleaxe");
                //LanguageRegistry.addName(GoldDagger, "Gold Dagger");
                //LanguageRegistry.addName(GoldScimitar, "Gold Scimitar");
                //LanguageRegistry.addName(GoldMace, "Gold Mace");
                LanguageRegistry.addName(GoldStick, "Gold Stick");

                GameRegistry.addRecipe(new ItemStack(DiamondBow, 1), new Object[] {" #X", "# X", " #X", '#', Item.diamond, 'X', Item.silk});
                GameRegistry.addRecipe(new ItemStack(DiamondSkirt, 1), new Object[] {"###", "###", '#', Item.diamond});
                //GameRegistry.addRecipe(new ItemStack(DiamondWarhammer, 1), new Object[] {"###", "###", " X ", '#', Item.diamond, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(DiamondBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', Item.diamond, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(DiamondDagger, 1), new Object[] {" # ", " X ", '#', Item.diamond, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(DiamondDagger, 1), new Object[] {"   ", " # ", " X ", '#', Item.diamond, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(DiamondMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', Item.diamond, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(DiamondMace, 1), new Object[] {"  #", "  X", "  X", '#', Item.diamond, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(DiamondScimitar, 1), new Object[] {"  #", " # ", " X ", '#', Item.diamond, 'X', Item.stick});

                GameRegistry.addRecipe(new ItemStack(IronBow, 1), new Object[] {" #X", "# X", " #X", '#', Item.ingotIron, 'X', Item.silk});
                GameRegistry.addRecipe(new ItemStack(IronSkirt, 1), new Object[] {"###", "###", '#', Item.ingotIron});
                GameRegistry.addRecipe(new ItemStack(IronWarhammer, 1), new Object[] {"###", "###", " X ", '#', Item.ingotIron, 'X', Item.stick});
                GameRegistry.addRecipe(new ItemStack(IronBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', Item.ingotIron, 'X', Item.stick});
                GameRegistry.addRecipe(new ItemStack(IronDagger, 1), new Object[] {" # ", " X ", '#', Item.ingotIron, 'X', Item.stick});
                GameRegistry.addRecipe(new ItemStack(IronDagger, 1), new Object[] {"   ", " # ", " X ", '#', Item.ingotIron, 'X', Item.stick});
                GameRegistry.addRecipe(new ItemStack(IronMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', Item.ingotIron, 'X', Item.stick});
                GameRegistry.addRecipe(new ItemStack(IronMace, 1), new Object[] {"  #", "  X", "  X", '#', Item.ingotIron, 'X', Item.stick});
                GameRegistry.addRecipe(new ItemStack(IronScimitar, 1), new Object[] {"  #", " # ", " X ", '#', Item.ingotIron, 'X', Item.stick});
                GameRegistry.addRecipe(new ItemStack(IronStick, 4), new Object[] {"#", "#", '#', Item.ingotIron});
                GameRegistry.addRecipe(new ItemStack(Block.fenceIron, 16), new Object[] {"# #", "###", "# #", '#', Item.ingotIron});

                GameRegistry.addRecipe(new ItemStack(GoldBow, 1), new Object[] {" #X", "# X", " #X", '#', Item.ingotGold, 'X', Item.silk});
                GameRegistry.addRecipe(new ItemStack(GoldSkirt, 1), new Object[] {"###", "###", '#', Item.ingotGold});
                //GameRegistry.addRecipe(new ItemStack(GoldWarhammer, 1), new Object[] {"###", "###", " X ", '#', Item.ingotGold, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(GoldBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', Item.ingotGold, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(GoldDagger, 1), new Object[] {" # ", " X ", '#', Item.ingotGold, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(GoldDagger, 1), new Object[] {"   ", " # ", " X ", '#', Item.ingotGold, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(GoldMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', Item.ingotGold, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(GoldMace, 1), new Object[] {"  #", "  X", "  X", '#', Item.ingotGold, 'X', Item.stick});
                //GameRegistry.addRecipe(new ItemStack(GoldScimitar, 1), new Object[] {"  #", " # ", " X ", '#', Item.ingotGold, 'X', Item.stick});
                GameRegistry.addRecipe(new ItemStack(GoldStick, 4), new Object[] {"#", "#", '#', Item.ingotGold});

                OreDictionary.registerOre("GoldOre", new ItemStack(Block.oreGold, 1, 0));
                OreDictionary.registerOre("oreGold", new ItemStack(Block.oreGold, 1, 0));
                OreDictionary.registerOre("GoldIngot", new ItemStack(Item.ingotGold, 1, 0));
                OreDictionary.registerOre("ingotGold", new ItemStack(Item.ingotGold, 1, 0));
                OreDictionary.registerOre("GoldStick", new ItemStack(GoldStick, 1, 0));
                OreDictionary.registerOre("stickGold", new ItemStack(GoldStick, 1, 0));
                OreDictionary.registerOre("IronOre", new ItemStack(Block.oreIron, 1, 0));
                OreDictionary.registerOre("oreIron", new ItemStack(Block.oreIron, 1, 0));
                OreDictionary.registerOre("IronIngot", new ItemStack(Item.ingotIron, 1, 0));
                OreDictionary.registerOre("ingotIron", new ItemStack(Item.ingotIron, 1, 0));
                OreDictionary.registerOre("IronStick", new ItemStack(IronStick, 1, 0));
                OreDictionary.registerOre("stickIron", new ItemStack(IronStick, 1, 0));

                lightBulbModelID = RenderingRegistry.getNextAvailableRenderId();
                titaniumLampModelID = RenderingRegistry.getNextAvailableRenderId();
            	LightBulbActive = (new BlockLightBulb(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 17, true)).setHardness(0.1F).setLightOpacity(0).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("LightBulb");
            	LightBulbIdle = (new BlockLightBulb(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 18, false)).setHardness(0.1F).setLightOpacity(0).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("LightBulb").setCreativeTab(CreativeTabs.tabRedstone);
                TitaniumLampActive = (new BlockTitaniumLamp(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 14, true)).setHardness(0.1F).setLightOpacity(0).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("TitaniumLamp");
                TitaniumLampIdle = (new BlockTitaniumLamp(FIRST_BLOCKID + BLOCKID_DISPLACEMENT + 15, false)).setHardness(0.1F).setLightOpacity(0).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("TitaniumLamp").setCreativeTab(CreativeTabs.tabRedstone);

                woolCarpet = (new BlockCarpet(171)).setHardness(0.1F).setLightOpacity(0).setStepSound(Block.soundClothFootstep).setUnlocalizedName("woolCarpet");
                clayHardened = (new Block(172, Material.rock)).setHardness(1.25F).setResistance(8.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("clayHardened").setCreativeTab(CreativeTabs.tabBlock);
                blastFurnaceBurning = (new BlockFurnaceMCE(MCE_Settings.BlastFurnaceID + 1, true)).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.875F).setUnlocalizedName("blastFurnace");
                blastFurnaceIdle = (new BlockFurnaceMCE(MCE_Settings.BlastFurnaceID, false)).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("blastFurnace").setCreativeTab(CreativeTabs.tabDecorations);
                craftingTable = (new BlockCraftingMCE(MCE_Settings.ExtendedCraftingTableID)).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("crafting");
                dirtSlab = (new BlockDirtSlab(173)).setHardness(0.5F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("dirtSlab");
                planter = (new BlockPlanter(177)).setHardness(0.6F).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("planter");

                NightVision = (new ItemArmor(FIRST_ITEMID + 400, EnumArmorMaterialMCE.STEEL, RenderingRegistry.addNewArmourRendererPrefix("NightVision"), 0)).setUnlocalizedName("NightVision").setCreativeTab(CreativeTabs.tabMaterials);
                JetPack = (new ItemArmor(FIRST_ITEMID + 401, EnumArmorMaterialMCE.STEEL, RenderingRegistry.addNewArmourRendererPrefix("JetPack"), 1)).setUnlocalizedName("JetPack").setCreativeTab(CreativeTabs.tabMaterials);
                MissileLauncher = (new ItemMissileLauncher(FIRST_ITEMID + 402, 100, 2.0F)).setUnlocalizedName("MissileLauncher");
                Missile = (new Item(FIRST_ITEMID + 403)).setUnlocalizedName("MissileRed").setCreativeTab(CreativeTabs.tabCombat);

                NinjaStar = (new ItemShuriken(FIRST_ITEMID + 410)).setUnlocalizedName("NinjaStar");
                KatanaSword = (new ItemSword(FIRST_ITEMID + 430, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("Katana");
                Cutlass = (new ItemSword(FIRST_ITEMID + 310, EnumToolMaterialMCE.STEEL)).setUnlocalizedName("Cutlass");
                //MysticSword = (new ItemSword(FIRST_ITEMID + 312, EnumToolMaterialMCE.ADAMANTIUM)).setUnlocalizedName("MysticSword");

                StaffBlue = (new ItemStaffBlue(FIRST_ITEMID + 720)).setUnlocalizedName("StaffBlue").setCreativeTab(CreativeTabs.tabCombat);
                StaffNature = (new ItemStaffNature(FIRST_ITEMID + 721)).setUnlocalizedName("StaffNature").setCreativeTab(CreativeTabs.tabCombat);
                StaffLightning = (new ItemStaffLightning(FIRST_ITEMID + 722)).setUnlocalizedName("StaffLightning").setCreativeTab(CreativeTabs.tabCombat);
                StaffTeleport = (new ItemStaffTeleport(FIRST_ITEMID + 723)).setUnlocalizedName("StaffTeleport").setCreativeTab(CreativeTabs.tabCombat);
                TwilightWand = (new ItemTwilightWand(FIRST_ITEMID + 729)).setUnlocalizedName("TwilightWand").setCreativeTab(CreativeTabs.tabCombat);

//                essenceRune = (new ItemRune(FIRST_ITEMID + 700)).setUnlocalizedName("essenceRune").setCreativeTab(CreativeTabs.tabMaterials);
//                waterRune = (new ItemRune(FIRST_ITEMID + 701)).setUnlocalizedName("waterRune").setCreativeTab(CreativeTabs.tabMaterials);
//                airRune = (new ItemRune(FIRST_ITEMID + 702)).setUnlocalizedName("airRune").setCreativeTab(CreativeTabs.tabMaterials);
//                earthRune = (new ItemRune(FIRST_ITEMID + 703)).setUnlocalizedName("earthRune").setCreativeTab(CreativeTabs.tabMaterials);
//                fireRune = (new ItemRune(FIRST_ITEMID + 704)).setUnlocalizedName("fireRune").setCreativeTab(CreativeTabs.tabMaterials);
//                cosmicRune = (new ItemRune(FIRST_ITEMID + 705)).setUnlocalizedName("cosmicRune").setCreativeTab(CreativeTabs.tabMaterials);
//                chaosRune = (new ItemRune(FIRST_ITEMID + 706)).setUnlocalizedName("chaosRune").setCreativeTab(CreativeTabs.tabMaterials);
//                astralRune = (new ItemRune(FIRST_ITEMID + 707)).setUnlocalizedName("astralRune").setCreativeTab(CreativeTabs.tabMaterials);
//                natureRune = (new ItemRune(FIRST_ITEMID + 708)).setUnlocalizedName("natureRune").setCreativeTab(CreativeTabs.tabMaterials);
//                natureRune = (new ItemRune(FIRST_ITEMID + 709)).setUnlocalizedName("natureRune").setCreativeTab(CreativeTabs.tabMaterials);
//                bloodRune = (new ItemRune(FIRST_ITEMID + 710)).setUnlocalizedName("bloodRune").setCreativeTab(CreativeTabs.tabMaterials);

                GameRegistry.registerBlock(LightBulbActive, "LightBulbActive");
                GameRegistry.registerBlock(LightBulbIdle, "LightBulbIdle");
        		GameRegistry.registerBlock(TitaniumLampActive, "TitaniumLampActive");
                GameRegistry.registerBlock(TitaniumLampIdle, "TitaniumLampIdle");
                Item.itemsList[woolCarpet.blockID] = (new ItemCloth(woolCarpet.blockID - 256)).setUnlocalizedName("woolCarpet");
            	GameRegistry.registerBlock(clayHardened, "clayHardened");
        		GameRegistry.registerBlock(blastFurnaceIdle, "blastFurnaceIdle");
                GameRegistry.registerBlock(blastFurnaceBurning, "blastFurnaceBurning");
        		GameRegistry.registerBlock(craftingTable, "craftingTable");
        		GameRegistry.registerBlock(dirtSlab, "dirtSlab");
        		GameRegistry.registerBlock(planter, ItemBlockWithMetadata.class, "planter");

            	LanguageRegistry.instance().addStringLocalization("container.BlastFurnace", "Blast Furnace");
            	LanguageRegistry.addName(blastFurnaceIdle, "Blast Furnace");
            	LanguageRegistry.addName(craftingTable, "Extended Crafting Table");
            	LanguageRegistry.addName(dirtSlab, "Dirt Slab");
            	//LanguageRegistry.addName(planter, "Planter");
            	LanguageRegistry.addName(new ItemStack(planter, 1, 0), "Unfired Planter");
            	LanguageRegistry.addName(new ItemStack(planter, 1, 1), "Planter");

            	LanguageRegistry.addName(LightBulbActive, "Lightbulb");
                LanguageRegistry.addName(LightBulbIdle, "Lightbulb");
                LanguageRegistry.addName(TitaniumLampActive, "Titanium Lamp");
                LanguageRegistry.addName(TitaniumLampIdle, "Titanium Lamp");
                LanguageRegistry.addName(clayHardened, "Hardened Clay");
                LanguageRegistry.addName(NightVision, "Night Vision");
                LanguageRegistry.addName(JetPack, "JetPack");
                LanguageRegistry.addName(MissileLauncher, "Missile Launcher");
                LanguageRegistry.addName(Missile, "Missile");
                LanguageRegistry.addName(NinjaStar, "Ninja Star");
                LanguageRegistry.addName(KatanaSword, "Katana");
                LanguageRegistry.addName(Cutlass, "Cutlass");
                //LanguageRegistry.addName(MysticSword, "Mystic Sword");
                LanguageRegistry.addName(StaffBlue, "Arcane Staff");
                LanguageRegistry.addName(StaffNature, "Scepter of Life");
                LanguageRegistry.addName(StaffLightning, "Lightning Staff");
                LanguageRegistry.addName(StaffTeleport, "Teleportation Staff");
                LanguageRegistry.addName(TwilightWand, "Twilight Wand");

//              LanguageRegistry.addName(essenceRune, "Rune essence");
//              LanguageRegistry.addName(waterRune, "Water Rune");
//              LanguageRegistry.addName(airRune, "Air Rune");
//              LanguageRegistry.addName(earthRune, "Earth Rune");
//              LanguageRegistry.addName(fireRune, "Fire Rune");
//              LanguageRegistry.addName(cosmicRune, "Cosmic Rune");
//              LanguageRegistry.addName(chaosRune, "Chaos Rune");
//              LanguageRegistry.addName(astralRune, "Astral Rune");
//              LanguageRegistry.addName(natureRune, "Nature Rune");
//              LanguageRegistry.addName(natureRune, "Nature Rune");
//              LanguageRegistry.addName(bloodRune, "Blood Rune");

                blastFurnaceModelID = RenderingRegistry.getNextAvailableRenderId();
                thinGlassModelID = RenderingRegistry.getNextAvailableRenderId();
            	Block.blocksList[102] = null;
            	thinGlass = (new BlockPane2(102, Material.glass, false)).setHardness(0.3F).setLightOpacity(0).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("thinGlass");
            	GameRegistry.registerBlock(thinGlass, "thinGlass");
                LanguageRegistry.addName(thinGlass, "Glass Pane");
                glassCubeModelID = RenderingRegistry.getNextAvailableRenderId();
            	Block.blocksList[20] = null;
                glassCube = (new BlockGlass2(20, Material.glass, false)).setHardness(0.3F).setLightOpacity(0).setStepSound(Block.soundGlassFootstep).setUnlocalizedName("glass");
                GameRegistry.registerBlock(glassCube, "glass");
                LanguageRegistry.addName(glassCube, "Glass");
                MCE_Reflect.setValue(Block.class, null, 40, glassCube);
                planterModelID = RenderingRegistry.getNextAvailableRenderId();
                dirtSlabModelID = RenderingRegistry.getNextAvailableRenderId();

            	FurnaceRecipes.smelting().addSmelting(Block.blockClay.blockID, new ItemStack(clayHardened, 1, 0), 0.1F);
                BlastFurnaceRecipes.smelting().addSmelting(new ItemStack(planter, 1, 0), new ItemStack(planter, 1, 1), 0.1F);

            	GameRegistry.addRecipe(new ItemStack(LightBulbIdle, 1), new Object[] {" # ", "#R#", " O ", '#', Block.glass, 'O', Item.ingotIron, 'R', Item.redstone});
                GameRegistry.addRecipe(new ItemStack(LightBulbIdle, 1), new Object[] {" # ", "#C#", " O ", '#', Block.glass, 'O', Item.ingotIron, 'C', CopperWireItem});
                GameRegistry.addRecipe(new ItemStack(TitaniumLampIdle, 1), new Object[] {" C ", " # ", " # ", '#', TitaniumIngot, 'C', CopperWireItem});
                GameRegistry.addRecipe(new ItemStack(NinjaStar, 4), new Object[] {" # ", "###", " # ", '#', SteelIngot});
                GameRegistry.addRecipe(new ItemStack(woolCarpet, 4), new Object[] {"##", '#', Block.cloth});
                GameRegistry.addRecipe(new ItemStack(blastFurnaceIdle, 1), new Object[] {"###", "OOO", "###", '#', Item.ingotIron, 'O', Block.furnaceIdle});
                GameRegistry.addRecipe(new ItemStack(craftingTable, 1), new Object[] {"###", "XXX", "###", '#', Block.planks, 'X', Block.blocksList[58]});
                GameRegistry.addRecipe(new ItemStack(dirtSlab, 2), new Object[] {"#", '#', Block.dirt});
                GameRegistry.addRecipe(new ItemStack(Block.dirt, 1), new Object[] {"#", "#", '#', dirtSlab});
                GameRegistry.addRecipe(new ItemStack(planter, 1, 0), new Object[] {"##", "##", '#', Block.blockClay});
                GameRegistry.addRecipe(new ItemStack(planter, 1, 2), new Object[] {"X", "J", "#", 'X', Block.dirt, 'J', new ItemStack(Item.dyePowder, 1, 15), '#', new ItemStack(planter, 1, 1)});
                GameRegistry.addRecipe(new ItemStack(planter, 1, 8), new Object[] {"X", "J", "#", 'X', Block.grass, 'J', new ItemStack(Item.dyePowder, 1, 15), '#', new ItemStack(planter, 1, 1)});

                ExtendedCraftingHandler.getInstance().addRecipe(new ItemStack(StaffBlue, 1), new Object[] {"   O", "  # ", " #  ", "#   ", '#', CrystalFused, 'O', Item.netherStar});
                ExtendedCraftingHandler.getInstance().addRecipe(new ItemStack(StaffNature, 1), new Object[] {"   O", "  # ", " #  ", "#   ", '#', CrystalFused, 'O', Item.fireballCharge});
                ExtendedCraftingHandler.getInstance().addRecipe(new ItemStack(StaffLightning, 1), new Object[] {"   O", "  # ", " #  ", "#   ", '#', CrystalFused, 'O', Item.fireballCharge});
                ExtendedCraftingHandler.getInstance().addRecipe(new ItemStack(StaffTeleport, 1), new Object[] {"   O", "  # ", " #  ", "#   ", '#', CrystalFused, 'O', Item.enderPearl});
                ExtendedCraftingHandler.getInstance().addRecipe(new ItemStack(MissileLauncher, 1), new Object[] {"####", "####", " O  ", '#', SteelIngot, 'O', Item.redstoneRepeater});
                ExtendedCraftingHandler.getInstance().addRecipe(new ItemStack(Missile, 2), new Object[] {"####", "XXOO", '#', SteelIngot, 'X', Block.tnt, 'O', Item.redstone});
            }
            //this.loadTextures();
        	//this.loadBlockItems();
        }
    }




    private static void loadMithril()
    {
        GameRegistry.registerBlock(MithrilOre, "MithrilOre");
        GameRegistry.registerBlock(MithrilBlock, "MithrilBlock");

        LanguageRegistry.addName(MithrilOre, "Mithril Ore");
        LanguageRegistry.addName(MithrilBlock, "Mithril Block");
        LanguageRegistry.addName(MithrilIngot, "Mithril Bar");
        LanguageRegistry.addName(MithrilPickaxe, "Mithril Pickaxe");
        LanguageRegistry.addName(MithrilAxe, "Mithril Axe");
        LanguageRegistry.addName(MithrilSword, "Mithril Sword");
        LanguageRegistry.addName(MithrilShovel, "Mithril Shovel");
        LanguageRegistry.addName(MithrilHoe, "Mithril Hoe");
        LanguageRegistry.addName(MithrilHelmet, "Mithril Helmet");
        LanguageRegistry.addName(MithrilChest, "Mithril Chestplate");
        LanguageRegistry.addName(MithrilLegs, "Mithril Legs");
        LanguageRegistry.addName(MithrilBoots, "Mithril Boots");
        LanguageRegistry.addName(MithrilSkirt, "Mithril Skirt");
        LanguageRegistry.addName(MithrilWarhammer, "Mithril Warhammer");
        LanguageRegistry.addName(MithrilBattleaxe, "Mithril Battleaxe");
        LanguageRegistry.addName(MithrilDagger, "Mithril Dagger");
        LanguageRegistry.addName(MithrilScimitar, "Mithril Scimitar");
        LanguageRegistry.addName(MithrilMace, "Mithril Mace");
        LanguageRegistry.addName(MithrilBow, "Mithril Bow");
        LanguageRegistry.addName(MithrilStick, "Mithril Stick");

        GameRegistry.addSmelting(MithrilOre.blockID, new ItemStack(MithrilIngot, 1), 1.0F);
        GameRegistry.addRecipe(new ItemStack(MithrilBlock, 1), new Object[] {"###", "###", "###", '#', MithrilIngot});
        GameRegistry.addRecipe(new ItemStack(MithrilIngot, 9), new Object[] {"#", '#', MithrilBlock});
        GameRegistry.addRecipe(new ItemStack(MithrilPickaxe, 1), new Object[] {"###", " X ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilAxe, 1), new Object[] {"## ", "#X ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilAxe, 1), new Object[] {" ##", " X#", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilSword, 1), new Object[] {" # ", " # ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilShovel, 1), new Object[] {" # ", " X ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilHoe, 1), new Object[] {"## ", " X ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilHoe, 1), new Object[] {" ##", " X ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilHelmet, 1), new Object[] {"###", "# #", '#', MithrilIngot});
        GameRegistry.addRecipe(new ItemStack(MithrilChest, 1), new Object[] {"# #", "###", "###", '#', MithrilIngot});
        GameRegistry.addRecipe(new ItemStack(MithrilLegs, 1), new Object[] {"###", "# #", "# #", '#', MithrilIngot});
        GameRegistry.addRecipe(new ItemStack(MithrilBoots, 1), new Object[] {"# #", "# #", '#', MithrilIngot});
        GameRegistry.addRecipe(new ItemStack(MithrilSkirt, 1), new Object[] {"###", "###", '#', MithrilIngot});
        GameRegistry.addRecipe(new ItemStack(MithrilWarhammer, 1), new Object[] {"###", "###", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilDagger, 1), new Object[] {" # ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilDagger, 1), new Object[] {"   ", " # ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilMace, 1), new Object[] {"  #", "  X", "  X", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilBow, 1), new Object[] {" #X", "# X", " #X", '#', MithrilIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(MithrilScimitar, 1), new Object[] {"  #", " # ", " X ", '#', MithrilIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(MithrilStick, 4), new Object[] {"#", "#", '#', MithrilIngot});

        OreDictionary.registerOre("MithrilOre", new ItemStack(MithrilOre, 1, 0));
        OreDictionary.registerOre("oreMithril", new ItemStack(MithrilOre, 1, 0));
        OreDictionary.registerOre("MithrilIngot", new ItemStack(MithrilIngot, 1, 0));
        OreDictionary.registerOre("ingotMithril", new ItemStack(MithrilIngot, 1, 0));
        OreDictionary.registerOre("MithrilStick", new ItemStack(MithrilStick, 1, 0));
        OreDictionary.registerOre("stickMithril", new ItemStack(MithrilStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(MithrilOre, "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(MithrilBlock, "pickaxe", 2);
        MinecraftForge.setToolClass(MithrilPickaxe, "pickaxe", 3);
        MinecraftForge.setToolClass(MithrilAxe, "axe", 3);
        MinecraftForge.setToolClass(MithrilShovel, "shovel", 3);
    }

	private static void loadAdamantium()
    {
        GameRegistry.registerBlock(AdamantiumOre, "AdamantiumOre");
        GameRegistry.registerBlock(AdamantiumBlock, "AdamantiumBlock");

        LanguageRegistry.addName(AdamantiumOre, "Adamantium Ore");
        LanguageRegistry.addName(AdamantiumBlock, "Adamantium Block");
        LanguageRegistry.addName(AdamantiumIngot, "Adamantium Bar");
        LanguageRegistry.addName(AdamantiumPickaxe, "Adamantium Pickaxe");
        LanguageRegistry.addName(AdamantiumAxe, "Adamantium Axe");
        LanguageRegistry.addName(AdamantiumSword, "Adamantium Sword");
        LanguageRegistry.addName(AdamantiumShovel, "Adamantium Shovel");
        LanguageRegistry.addName(AdamantiumHoe, "Adamantium Hoe");
        LanguageRegistry.addName(AdamantiumHelmet, "Adamantium Helmet");
        LanguageRegistry.addName(AdamantiumChest, "Adamantium Chestplate");
        LanguageRegistry.addName(AdamantiumLegs, "Adamantium Legs");
        LanguageRegistry.addName(AdamantiumBoots, "Adamantium Boots");
        LanguageRegistry.addName(AdamantiumSkirt, "Adamantium Skirt");
        LanguageRegistry.addName(AdamantiumWarhammer, "Adamantium Warhammer");
        LanguageRegistry.addName(AdamantiumBattleaxe, "Adamantium Battleaxe");
        LanguageRegistry.addName(AdamantiumDagger, "Adamantium Dagger");
        LanguageRegistry.addName(AdamantiumScimitar, "Adamantium Scimitar");
        LanguageRegistry.addName(AdamantiumMace, "Adamantium Mace");
        LanguageRegistry.addName(AdamantiumBow, "Adamantium Bow");
        LanguageRegistry.addName(AdamantiumStick, "Adamantium Stick");

        GameRegistry.addSmelting(AdamantiumOre.blockID, new ItemStack(AdamantiumIngot, 1), 1.0F);
        GameRegistry.addRecipe(new ItemStack(AdamantiumBlock, 1), new Object[] {"###", "###", "###", '#', AdamantiumIngot});
        GameRegistry.addRecipe(new ItemStack(AdamantiumIngot, 9), new Object[] {"#", '#', AdamantiumBlock});
        GameRegistry.addRecipe(new ItemStack(AdamantiumPickaxe, 1), new Object[] {"###", " X ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumAxe, 1), new Object[] {"## ", "#X ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumAxe, 1), new Object[] {" ##", " X#", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumSword, 1), new Object[] {" # ", " # ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumShovel, 1), new Object[] {" # ", " X ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumHoe, 1), new Object[] {"## ", " X ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumHoe, 1), new Object[] {" ##", " X ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumHelmet, 1), new Object[] {"###", "# #", '#', AdamantiumIngot});
        GameRegistry.addRecipe(new ItemStack(AdamantiumChest, 1), new Object[] {"# #", "###", "###", '#', AdamantiumIngot});
        GameRegistry.addRecipe(new ItemStack(AdamantiumLegs, 1), new Object[] {"###", "# #", "# #", '#', AdamantiumIngot});
        GameRegistry.addRecipe(new ItemStack(AdamantiumBoots, 1), new Object[] {"# #", "# #", '#', AdamantiumIngot});
        GameRegistry.addRecipe(new ItemStack(AdamantiumSkirt, 1), new Object[] {"###", "###", '#', AdamantiumIngot});
        GameRegistry.addRecipe(new ItemStack(AdamantiumWarhammer, 1), new Object[] {"###", "###", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumDagger, 1), new Object[] {" # ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumDagger, 1), new Object[] {"   ", " # ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumMace, 1), new Object[] {"  #", "  X", "  X", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumBow, 1), new Object[] {" #X", "# X", " #X", '#', AdamantiumIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(AdamantiumScimitar, 1), new Object[] {"  #", " # ", " X ", '#', AdamantiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(AdamantiumStick, 4), new Object[] {"#", "#", '#', AdamantiumIngot});

        OreDictionary.registerOre("AdamantiumOre", new ItemStack(AdamantiumOre, 1, 0));
        OreDictionary.registerOre("oreAdamantium", new ItemStack(AdamantiumOre, 1, 0));
        OreDictionary.registerOre("AdamantiumIngot", new ItemStack(AdamantiumIngot, 1, 0));
        OreDictionary.registerOre("ingotAdamantium", new ItemStack(AdamantiumIngot, 1, 0));
        OreDictionary.registerOre("AdamantiumStick", new ItemStack(AdamantiumStick, 1, 0));
        OreDictionary.registerOre("stickAdamantium", new ItemStack(AdamantiumStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(AdamantiumOre,   "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(AdamantiumBlock,   "pickaxe", 2);
        MinecraftForge.setToolClass(AdamantiumPickaxe, "pickaxe", 3);
        MinecraftForge.setToolClass(AdamantiumAxe, "axe", 3);
        MinecraftForge.setToolClass(AdamantiumShovel, "shovel", 3);
    }

	private static void loadRunite()
    {
        GameRegistry.registerBlock(RuniteOre, "RuniteOre");
        GameRegistry.registerBlock(RuniteBlock, "RuniteBlock");

        LanguageRegistry.addName(RuniteOre, "Runite Ore");
        LanguageRegistry.addName(RuniteBlock, "Runite Block");
        LanguageRegistry.addName(RuniteIngot, "Runite Bar");
        LanguageRegistry.addName(RunitePickaxe, "Runite Pickaxe");
        LanguageRegistry.addName(RuniteAxe, "Runite Axe");
        LanguageRegistry.addName(RuniteSword, "Runite Sword");
        LanguageRegistry.addName(RuniteShovel, "Runite Shovel");
        LanguageRegistry.addName(RuniteHoe, "Runite Hoe");
        LanguageRegistry.addName(RuniteHelmet, "Runite Helmet");
        LanguageRegistry.addName(RuniteChest, "Runite Chestplate");
        LanguageRegistry.addName(RuniteLegs, "Runite Legs");
        LanguageRegistry.addName(RuniteBoots, "Runite Boots");
        LanguageRegistry.addName(RuniteSkirt, "Runite Skirt");
        LanguageRegistry.addName(RuniteWarhammer, "Runite Warhammer");
        LanguageRegistry.addName(RuniteBattleaxe, "Runite Battleaxe");
        LanguageRegistry.addName(RuniteDagger, "Runite Dagger");
        LanguageRegistry.addName(RuniteScimitar, "Runite Scimitar");
        LanguageRegistry.addName(RuniteMace, "Runite Mace");
        LanguageRegistry.addName(RuniteBow, "Runite Bow");
        LanguageRegistry.addName(RuniteStick, "Runite Stick");

        GameRegistry.addSmelting(RuniteOre.blockID, new ItemStack(RuniteIngot, 1), 1.0F);
        GameRegistry.addRecipe(new ItemStack(RuniteBlock, 1), new Object[] {"###", "###", "###", '#', RuniteIngot});
        GameRegistry.addRecipe(new ItemStack(RuniteIngot, 9), new Object[] {"#", '#', RuniteBlock});
        GameRegistry.addRecipe(new ItemStack(RunitePickaxe, 1), new Object[] {"###", " X ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteAxe, 1), new Object[] {"## ", "#X ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteAxe, 1), new Object[] {" ##", " X#", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteSword, 1), new Object[] {" # ", " # ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteShovel, 1), new Object[] {" # ", " X ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteHoe, 1), new Object[] {"## ", " X ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteHoe, 1), new Object[] {" ##", " X ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteHelmet, 1), new Object[] {"###", "# #", '#', RuniteIngot});
        GameRegistry.addRecipe(new ItemStack(RuniteChest, 1), new Object[] {"# #", "###", "###", '#', RuniteIngot});
        GameRegistry.addRecipe(new ItemStack(RuniteLegs, 1), new Object[] {"###", "# #", "# #", '#', RuniteIngot});
        GameRegistry.addRecipe(new ItemStack(RuniteBoots, 1), new Object[] {"# #", "# #", '#', RuniteIngot});
        GameRegistry.addRecipe(new ItemStack(RuniteSkirt, 1), new Object[] {"###", "###", '#', RuniteIngot});
        GameRegistry.addRecipe(new ItemStack(RuniteWarhammer, 1), new Object[] {"###", "###", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteDagger, 1), new Object[] {" # ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteDagger, 1), new Object[] {"   ", " # ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteMace, 1), new Object[] {"  #", "  X", "  X", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteBow, 1), new Object[] {" #X", "# X", " #X", '#', RuniteIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(RuniteScimitar, 1), new Object[] {"  #", " # ", " X ", '#', RuniteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(RuniteStick, 4), new Object[] {"#", "#", '#', RuniteIngot});

        OreDictionary.registerOre("RuniteOre", new ItemStack(RuniteOre, 1, 0));
        OreDictionary.registerOre("oreRunite", new ItemStack(RuniteOre, 1, 0));
        OreDictionary.registerOre("RuniteIngot", new ItemStack(RuniteIngot, 1, 0));
        OreDictionary.registerOre("ingotRunite", new ItemStack(RuniteIngot, 1, 0));
        OreDictionary.registerOre("RuniteStick", new ItemStack(RuniteStick, 1, 0));
        OreDictionary.registerOre("stickRunite", new ItemStack(RuniteStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(RuniteOre,   "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(RuniteBlock,   "pickaxe", 2);
        MinecraftForge.setToolClass(RunitePickaxe, "pickaxe", 3);
        MinecraftForge.setToolClass(RuniteAxe, "axe", 3);
        MinecraftForge.setToolClass(RuniteShovel, "shovel", 3);
    }

	private static void loadDragonite()
    {
        GameRegistry.registerBlock(DragoniteOre, "DragoniteOre");
        GameRegistry.registerBlock(DragoniteBlock, "DragoniteBlock");

        LanguageRegistry.addName(DragoniteOre, "Dragonite Ore");
        LanguageRegistry.addName(DragoniteBlock, "Dragonite Block");
        LanguageRegistry.addName(DragoniteShard, "Dragonite Shard");
        LanguageRegistry.addName(DragoniteIngot, "Dragonite Bar");
        LanguageRegistry.addName(DragonitePickaxe, "Dragonite Pickaxe");
        LanguageRegistry.addName(DragoniteAxe, "Dragonite Axe");
        LanguageRegistry.addName(DragoniteSword, "Dragonite Sword");
        LanguageRegistry.addName(DragoniteShovel, "Dragonite Shovel");
        LanguageRegistry.addName(DragoniteHoe, "Dragonite Hoe");
        LanguageRegistry.addName(DragoniteHelmet, "Dragonite Helmet");
        LanguageRegistry.addName(DragoniteChest, "Dragonite Chestplate");
        LanguageRegistry.addName(DragoniteLegs, "Dragonite Legs");
        LanguageRegistry.addName(DragoniteBoots, "Dragonite Boots");
        LanguageRegistry.addName(DragoniteSkirt, "Dragonite Skirt");
        LanguageRegistry.addName(DragoniteWarhammer, "Dragonite Warhammer");
        LanguageRegistry.addName(DragoniteBattleaxe, "Dragonite Battleaxe");
        LanguageRegistry.addName(DragoniteDagger, "Dragonite Dagger");
        LanguageRegistry.addName(DragoniteScimitar, "Dragonite Scimitar");
        LanguageRegistry.addName(DragoniteMace, "Dragonite Mace");
        LanguageRegistry.addName(DragoniteBow, "Dragonite Bow");
        LanguageRegistry.addName(DragoniteStick, "Dragonite Stick");

        GameRegistry.addSmelting(DragoniteOre.blockID, new ItemStack(DragoniteShard, 1), 1.0F);
        GameRegistry.addRecipe(new ItemStack(DragoniteBlock, 1), new Object[] {"###", "###", "###", '#', DragoniteIngot});
        GameRegistry.addRecipe(new ItemStack(DragoniteIngot, 9), new Object[] {"#", '#', DragoniteBlock});
        GameRegistry.addRecipe(new ItemStack(DragoniteIngot, 1), new Object[] {"## ", "## ", '#', DragoniteShard});
        GameRegistry.addRecipe(new ItemStack(DragonitePickaxe, 1), new Object[] {"###", " X ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});//TitaniumIngot
        GameRegistry.addRecipe(new ItemStack(DragoniteAxe, 1), new Object[] {"## ", "#X ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteAxe, 1), new Object[] {" ##", " X#", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteSword, 1), new Object[] {" # ", " # ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteShovel, 1), new Object[] {" # ", " X ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteHoe, 1), new Object[] {"## ", " X ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteHoe, 1), new Object[] {" ##", " X ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteHelmet, 1), new Object[] {"###", "# #", '#', DragoniteIngot});
        GameRegistry.addRecipe(new ItemStack(DragoniteChest, 1), new Object[] {"# #", "###", "###", '#', DragoniteIngot});
        GameRegistry.addRecipe(new ItemStack(DragoniteLegs, 1), new Object[] {"###", "# #", "# #", '#', DragoniteIngot});
        GameRegistry.addRecipe(new ItemStack(DragoniteBoots, 1), new Object[] {"# #", "# #", '#', DragoniteIngot});
        GameRegistry.addRecipe(new ItemStack(DragoniteSkirt, 1), new Object[] {"###", "###", '#', DragoniteIngot});
        GameRegistry.addRecipe(new ItemStack(DragoniteWarhammer, 1), new Object[] {"###", "###", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteDagger, 1), new Object[] {" # ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteDagger, 1), new Object[] {"   ", " # ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteMace, 1), new Object[] {"  #", "  X", "  X", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteBow, 1), new Object[] {" #X", "# X", " #X", '#', DragoniteIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(DragoniteScimitar, 1), new Object[] {"  #", " # ", " X ", '#', DragoniteIngot, 'X', TitaniumStick});
        GameRegistry.addRecipe(new ItemStack(DragoniteStick, 4), new Object[] {"#", "#", '#', DragoniteIngot});

        OreDictionary.registerOre("DragoniteOre", new ItemStack(DragoniteOre, 1, 0));
        OreDictionary.registerOre("oreDragonite", new ItemStack(DragoniteOre, 1, 0));
        OreDictionary.registerOre("DragoniteIngot", new ItemStack(DragoniteIngot, 1, 0));
        OreDictionary.registerOre("ingotDragonite", new ItemStack(DragoniteIngot, 1, 0));
        OreDictionary.registerOre("DragoniteShard", new ItemStack(DragoniteShard, 1, 0));
        OreDictionary.registerOre("shardDragonite", new ItemStack(DragoniteShard, 1, 0));
        OreDictionary.registerOre("DragoniteStick", new ItemStack(DragoniteStick, 1, 0));
        OreDictionary.registerOre("stickDragonite", new ItemStack(DragoniteStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(DragoniteOre,   "pickaxe", 3);
        MinecraftForge.setBlockHarvestLevel(DragoniteBlock,   "pickaxe", 3);
        MinecraftForge.setToolClass(DragonitePickaxe, "pickaxe", 4);
        MinecraftForge.setToolClass(DragoniteAxe, "axe", 4);
        MinecraftForge.setToolClass(DragoniteShovel, "shovel", 4);
    }

	private static void loadCrystal()
    {
        GameRegistry.registerBlock(CrystalOre, "CrystalOre");
        GameRegistry.registerBlock(CrystalBlock, "CrystalBlock");

        LanguageRegistry.addName(CrystalOre, "Crystal Ore");
        LanguageRegistry.addName(CrystalBlock, "Crystal Block");
        LanguageRegistry.addName(CrystalShard, "Crystal Shard");
        LanguageRegistry.addName(CrystalFused, "Crystal");
        LanguageRegistry.addName(CrystalPickaxe, "Crystal Pickaxe");
        LanguageRegistry.addName(CrystalAxe, "Crystal Axe");
        LanguageRegistry.addName(CrystalSword, "Crystal Sword");
        LanguageRegistry.addName(CrystalShovel, "Crystal Shovel");
        LanguageRegistry.addName(CrystalHoe, "Crystal Hoe");
        /*
        LanguageRegistry.addName(CrystalHelmet, "Crystal Helmet");
        LanguageRegistry.addName(CrystalChest, "Crystal Chestplate");
        LanguageRegistry.addName(CrystalLegs, "Crystal Legs");
        LanguageRegistry.addName(CrystalBoots, "Crystal Boots");
        LanguageRegistry.addName(CrystalSkirt, "Crystal Skirt");
        */
        LanguageRegistry.addName(CrystalWarhammer, "Crystal Warhammer");
        LanguageRegistry.addName(CrystalBattleaxe, "Crystal Battleaxe");
        LanguageRegistry.addName(CrystalDagger, "Crystal Dagger");
        LanguageRegistry.addName(CrystalScimitar, "Crystal Scimitar");
        LanguageRegistry.addName(CrystalMace, "Crystal Mace");
        LanguageRegistry.addName(CrystalBow, "Crystal Bow");
        LanguageRegistry.addName(CrystalStick, "Crystal Stick");

        GameRegistry.addSmelting(CrystalOre.blockID, new ItemStack(CrystalShard, 1), 1.0F);
        //GameRegistry.addRecipe(new ItemStack(CrystalBlock, 1), new Object[] {"###", "###", "###", '#', CrystalShard});
        //GameRegistry.addRecipe(new ItemStack(CrystalShard, 9), new Object[] {"#", '#', CrystalBlock});
        GameRegistry.addRecipe(new ItemStack(CrystalFused, 1), new Object[] {"##", "##", '#', CrystalShard});
        GameRegistry.addRecipe(new ItemStack(CrystalBlock, 1), new Object[] {"###", "###", "###", '#', CrystalFused});
        GameRegistry.addRecipe(new ItemStack(CrystalFused, 9), new Object[] {"#", '#', CrystalBlock});
        GameRegistry.addRecipe(new ItemStack(CrystalPickaxe, 1), new Object[] {"###", " X ", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalAxe, 1), new Object[] {"## ", "#X ", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalAxe, 1), new Object[] {" ##", " X#", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalSword, 1), new Object[] {" # ", " # ", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalShovel, 1), new Object[] {" # ", " X ", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalHoe, 1), new Object[] {"## ", " X ", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalHoe, 1), new Object[] {" ##", " X ", " X ", '#', CrystalFused, 'X', CrystalStick});
        /*
        GameRegistry.addRecipe(new ItemStack(CrystalHelmet, 1), new Object[] {"###", "# #", '#', CrystalFused});
        GameRegistry.addRecipe(new ItemStack(CrystalChest, 1), new Object[] {"# #", "###", "###", '#', CrystalFused});
        GameRegistry.addRecipe(new ItemStack(CrystalLegs, 1), new Object[] {"###", "# #", "# #", '#', CrystalFused});
        GameRegistry.addRecipe(new ItemStack(CrystalBoots, 1), new Object[] {"# #", "# #", '#', CrystalFused});
        GameRegistry.addRecipe(new ItemStack(CrystalSkirt, 1), new Object[] {"###", "###", '#', CrystalFused});
        */
        GameRegistry.addRecipe(new ItemStack(CrystalWarhammer, 1), new Object[] {"###", "###", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalDagger, 1), new Object[] {" # ", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalDagger, 1), new Object[] {"   ", " # ", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalMace, 1), new Object[] {"II#", "X  ", "X  ", '#', CrystalFused, 'X', CrystalStick, 'I', Item.silk});
        GameRegistry.addRecipe(new ItemStack(CrystalMace, 1), new Object[] {"#II", "  X", "  X", '#', CrystalFused, 'X', CrystalStick, 'I', Item.silk});
        GameRegistry.addRecipe(new ItemStack(CrystalBow, 1), new Object[] {" #X", "# X", " #X", '#', CrystalFused, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(CrystalScimitar, 1), new Object[] {"  #", " # ", " X ", '#', CrystalFused, 'X', CrystalStick});
        GameRegistry.addRecipe(new ItemStack(CrystalStick, 4), new Object[] {"#", "#", '#', CrystalFused});

        OreDictionary.registerOre("CrystalOre", new ItemStack(CrystalOre, 1, 0));
        OreDictionary.registerOre("oreCrystal", new ItemStack(CrystalOre, 1, 0));
        OreDictionary.registerOre("CrystalFused", new ItemStack(CrystalFused, 1, 0));
        OreDictionary.registerOre("fusedCrystal", new ItemStack(CrystalFused, 1, 0));
        OreDictionary.registerOre("CrystalShard", new ItemStack(CrystalShard, 1, 0));
        OreDictionary.registerOre("shardCrystal", new ItemStack(CrystalShard, 1, 0));
        OreDictionary.registerOre("CrystalStick", new ItemStack(CrystalStick, 1, 0));
        OreDictionary.registerOre("stickCrystal", new ItemStack(CrystalStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(CrystalOre,   "pickaxe", 4);
        MinecraftForge.setBlockHarvestLevel(CrystalBlock,   "pickaxe", 4);
        MinecraftForge.setToolClass(CrystalPickaxe, "pickaxe", 5);
        MinecraftForge.setToolClass(CrystalAxe, "axe", 5);
        MinecraftForge.setToolClass(CrystalShovel, "shovel", 5);
    }

	private static void loadSilver()
    {
        GameRegistry.registerBlock(SilverOre, "SilverOre");
        GameRegistry.registerBlock(SilverBlock, "SilverBlock");

        LanguageRegistry.addName(SilverOre, "Silver Ore");
        LanguageRegistry.addName(SilverBlock, "Silver Block");
        LanguageRegistry.addName(SilverIngot, "Silver Bar");
        LanguageRegistry.addName(SilverPickaxe, "Silver Pickaxe");
        LanguageRegistry.addName(SilverAxe, "Silver Axe");
        LanguageRegistry.addName(SilverSword, "Silver Sword");
        LanguageRegistry.addName(SilverShovel, "Silver Shovel");
        LanguageRegistry.addName(SilverHoe, "Silver Hoe");
        LanguageRegistry.addName(SilverHelmet, "Silver Helmet");
        LanguageRegistry.addName(SilverChest, "Silver Chestplate");
        LanguageRegistry.addName(SilverLegs, "Silver Legs");
        LanguageRegistry.addName(SilverBoots, "Silver Boots");
        LanguageRegistry.addName(SilverSkirt, "Silver Skirt");
        LanguageRegistry.addName(SilverWarhammer, "Silver Warhammer");
        LanguageRegistry.addName(SilverBattleaxe, "Silver Battleaxe");
        LanguageRegistry.addName(SilverDagger, "Silver Dagger");
        LanguageRegistry.addName(SilverScimitar, "Silver Scimitar");
        LanguageRegistry.addName(SilverMace, "Silver Mace");
        LanguageRegistry.addName(SilverBow, "Silver Bow");
        LanguageRegistry.addName(SilverStick, "Silver Stick");

        GameRegistry.addSmelting(SilverOre.blockID, new ItemStack(SilverIngot, 1), 1.0F);
        GameRegistry.addRecipe(new ItemStack(SilverBlock, 1), new Object[] {"###", "###", "###", '#', SilverIngot});
        GameRegistry.addRecipe(new ItemStack(SilverIngot, 9), new Object[] {"#", '#', SilverBlock});
        GameRegistry.addRecipe(new ItemStack(SilverPickaxe, 1), new Object[] {"###", " X ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverAxe, 1), new Object[] {"## ", "#X ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverAxe, 1), new Object[] {" ##", " X#", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverSword, 1), new Object[] {" # ", " # ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverShovel, 1), new Object[] {" # ", " X ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverHoe, 1), new Object[] {"## ", " X ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverHoe, 1), new Object[] {" ##", " X ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverHelmet, 1), new Object[] {"###", "# #", '#', SilverIngot});
        GameRegistry.addRecipe(new ItemStack(SilverChest, 1), new Object[] {"# #", "###", "###", '#', SilverIngot});
        GameRegistry.addRecipe(new ItemStack(SilverLegs, 1), new Object[] {"###", "# #", "# #", '#', SilverIngot});
        GameRegistry.addRecipe(new ItemStack(SilverBoots, 1), new Object[] {"# #", "# #", '#', SilverIngot});
        GameRegistry.addRecipe(new ItemStack(SilverSkirt, 1), new Object[] {"###", "###", '#', SilverIngot});
        GameRegistry.addRecipe(new ItemStack(SilverWarhammer, 1), new Object[] {"###", "###", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverDagger, 1), new Object[] {" # ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverDagger, 1), new Object[] {"   ", " # ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverMace, 1), new Object[] {"  #", "  X", "  X", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverBow, 1), new Object[] {" #X", "# X", " #X", '#', SilverIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(SilverScimitar, 1), new Object[] {"  #", " # ", " X ", '#', SilverIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SilverStick, 4), new Object[] {"#", "#", '#', SilverIngot});

        OreDictionary.registerOre("SilverOre", new ItemStack(SilverOre, 1, 0));
        OreDictionary.registerOre("oreSilver", new ItemStack(SilverOre, 1, 0));
        OreDictionary.registerOre("SilverIngot", new ItemStack(SilverIngot, 1, 0));
        OreDictionary.registerOre("ingotSilver", new ItemStack(SilverIngot, 1, 0));
        OreDictionary.registerOre("SilverStick", new ItemStack(SilverStick, 1, 0));
        OreDictionary.registerOre("stickSilver", new ItemStack(SilverStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(SilverOre,   "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(SilverBlock,   "pickaxe", 2);
        MinecraftForge.setToolClass(SilverPickaxe, "pickaxe", 1);
        MinecraftForge.setToolClass(SilverAxe, "axe", 1);
        MinecraftForge.setToolClass(SilverShovel, "shovel", 1);
    }

	private static void loadBronze()
    {
        GameRegistry.registerBlock(BronzeBlock, "BronzeBlock");
        GameRegistry.registerBlock(TinBlock, "TinBlock");
        GameRegistry.registerBlock(TinOre, "TinOre");
        GameRegistry.registerBlock(CopperBlock, "CopperBlock");
        GameRegistry.registerBlock(CopperOre, "CopperOre");
        GameRegistry.registerBlock(CopperWire, "CopperWire");

        LanguageRegistry.addName(BronzeBlock, "Bronze Block");
        LanguageRegistry.addName(BronzeIngot, "Bronze Bar");
        LanguageRegistry.addName(TinBlock, "Tin Block");
        LanguageRegistry.addName(TinOre, "Tin Ore");
        LanguageRegistry.addName(TinIngot, "Tin Ingot");
        LanguageRegistry.addName(CopperBlock, "Copper Block");
        LanguageRegistry.addName(CopperOre, "Copper Ore");
        LanguageRegistry.addName(CopperIngot, "Copper Ingot");
        LanguageRegistry.addName(BronzePickaxe, "Bronze Pickaxe");
        LanguageRegistry.addName(BronzeAxe, "Bronze Axe");
        LanguageRegistry.addName(BronzeSword, "Bronze Sword");
        LanguageRegistry.addName(BronzeShovel, "Bronze Shovel");
        LanguageRegistry.addName(BronzeHoe, "Bronze Hoe");
        LanguageRegistry.addName(BronzeHelmet, "Bronze Helmet");
        LanguageRegistry.addName(BronzeChest, "Bronze Chestplate");
        LanguageRegistry.addName(BronzeLegs, "Bronze Legs");
        LanguageRegistry.addName(BronzeBoots, "Bronze Boots");
        LanguageRegistry.addName(BronzeSkirt, "Bronze Skirt");
        LanguageRegistry.addName(BronzeWarhammer, "Bronze Warhammer");
        LanguageRegistry.addName(BronzeBattleaxe, "Bronze Battleaxe");
        LanguageRegistry.addName(BronzeDagger, "Bronze Dagger");
        LanguageRegistry.addName(BronzeScimitar, "Bronze Scimitar");
        LanguageRegistry.addName(BronzeMace, "Bronze Mace");
        LanguageRegistry.addName(BronzeBow, "Bronze Bow");
        LanguageRegistry.addName(BronzeStick, "Bronze Stick");
        LanguageRegistry.addName(CopperStick, "Copper Stick");
        LanguageRegistry.addName(CopperWire, "Copper Wire");
        LanguageRegistry.addName(CopperWireItem, "Copper Wire");
        copperWireModelID = RenderingRegistry.getNextAvailableRenderId();

        GameRegistry.addSmelting(TinOre.blockID, new ItemStack(TinIngot, 1), 0.2F);
        GameRegistry.addSmelting(CopperOre.blockID, new ItemStack(CopperIngot, 1), 0.2F);
        BlastFurnaceRecipes.smelting().addSmelting(new ItemStack(TinIngot, 1, 0), new ItemStack(CopperIngot, 1, 0), new ItemStack(BronzeIngot, 1, 0), 0.3F);
        //GameRegistry.addShapelessRecipe(new ItemStack(BronzeIngot, 1), new Object[] {CopperIngot, TinIngot});
        GameRegistry.addRecipe(new ItemStack(BronzeBlock, 1), new Object[] {"###", "###", "###", '#', BronzeIngot});
        GameRegistry.addRecipe(new ItemStack(BronzeIngot, 9), new Object[] {"#", '#', BronzeBlock});
        GameRegistry.addRecipe(new ItemStack(TinBlock, 1), new Object[] {"###", "###", "###", '#', TinIngot});
        GameRegistry.addRecipe(new ItemStack(TinIngot, 9), new Object[] {"#", '#', TinBlock});
        GameRegistry.addRecipe(new ItemStack(CopperBlock, 1), new Object[] {"###", "###", "###", '#', CopperIngot});
        GameRegistry.addRecipe(new ItemStack(CopperIngot, 9), new Object[] {"#", '#', CopperBlock});
        GameRegistry.addRecipe(new ItemStack(BronzePickaxe, 1), new Object[] {"###", " X ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeAxe, 1), new Object[] {"## ", "#X ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeAxe, 1), new Object[] {" ##", " X#", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeSword, 1), new Object[] {" # ", " # ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeShovel, 1), new Object[] {" # ", " X ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeHoe, 1), new Object[] {"## ", " X ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeHoe, 1), new Object[] {" ##", " X ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeHelmet, 1), new Object[] {"###", "# #", '#', BronzeIngot});
        GameRegistry.addRecipe(new ItemStack(BronzeChest, 1), new Object[] {"# #", "###", "###", '#', BronzeIngot});
        GameRegistry.addRecipe(new ItemStack(BronzeLegs, 1), new Object[] {"###", "# #", "# #", '#', BronzeIngot});
        GameRegistry.addRecipe(new ItemStack(BronzeBoots, 1), new Object[] {"# #", "# #", '#', BronzeIngot});
        GameRegistry.addRecipe(new ItemStack(BronzeSkirt, 1), new Object[] {"###", "###", '#', BronzeIngot});
        GameRegistry.addRecipe(new ItemStack(BronzeWarhammer, 1), new Object[] {"###", "###", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeDagger, 1), new Object[] {" # ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeDagger, 1), new Object[] {"   ", " # ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeMace, 1), new Object[] {"  #", "  X", "  X", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeBow, 1), new Object[] {" #X", "# X", " #X", '#', BronzeIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(BronzeScimitar, 1), new Object[] {"  #", " # ", " X ", '#', BronzeIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BronzeStick, 4), new Object[] {"#", "#", '#', BronzeIngot});
        GameRegistry.addRecipe(new ItemStack(CopperStick, 4), new Object[] {"#", "#", '#', CopperIngot});

        OreDictionary.registerOre("BronzeIngot", new ItemStack(BronzeIngot, 1, 0));
        OreDictionary.registerOre("ingotBronze", new ItemStack(BronzeIngot, 1, 0));
        OreDictionary.registerOre("CopperOre", new ItemStack(CopperOre, 1, 0));
        OreDictionary.registerOre("oreCopper", new ItemStack(CopperOre, 1, 0));
        OreDictionary.registerOre("CopperIngot", new ItemStack(CopperIngot, 1, 0));
        OreDictionary.registerOre("ingotCopper", new ItemStack(CopperIngot, 1, 0));
        OreDictionary.registerOre("TinOre", new ItemStack(TinOre, 1, 0));
        OreDictionary.registerOre("oreTin", new ItemStack(TinOre, 1, 0));
        OreDictionary.registerOre("TinIngot", new ItemStack(TinIngot, 1, 0));
        OreDictionary.registerOre("ingotTin", new ItemStack(TinIngot, 1, 0));
        OreDictionary.registerOre("BronzeStick", new ItemStack(BronzeStick, 1, 0));
        OreDictionary.registerOre("stickBronze", new ItemStack(BronzeStick, 1, 0));
        OreDictionary.registerOre("CopperStick", new ItemStack(CopperStick, 1, 0));
        OreDictionary.registerOre("stickCopper", new ItemStack(CopperStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(TinOre,   "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(TinBlock,   "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(CopperOre,   "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(CopperBlock,   "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(BronzeBlock,   "pickaxe", 1);
        MinecraftForge.setToolClass(BronzePickaxe, "pickaxe", 1);
        MinecraftForge.setToolClass(BronzeAxe, "axe", 1);
        MinecraftForge.setToolClass(BronzeShovel, "shovel", 1);
    }

	private static void loadBlurite()
    {
        GameRegistry.registerBlock(BluriteOre, "BluriteOre");
        GameRegistry.registerBlock(BluriteBlock, "BluriteBlock");

        LanguageRegistry.addName(BluriteOre, "Blurite Ore");
        LanguageRegistry.addName(BluriteBlock, "Blurite Block");
        LanguageRegistry.addName(BluriteIngot, "Blurite Bar");
        LanguageRegistry.addName(BluritePickaxe, "Blurite Pickaxe");
        LanguageRegistry.addName(BluriteAxe, "Blurite Axe");
        LanguageRegistry.addName(BluriteSword, "Blurite Sword");
        LanguageRegistry.addName(BluriteShovel, "Blurite Shovel");
        LanguageRegistry.addName(BluriteHoe, "Blurite Hoe");
//        LanguageRegistry.addName(BluriteHelmet, "Blurite Helmet");
//        LanguageRegistry.addName(BluriteChest, "Blurite Chestplate");
//        LanguageRegistry.addName(BluriteLegs, "Blurite Legs");
//        LanguageRegistry.addName(BluriteBoots, "Blurite Boots");
//        LanguageRegistry.addName(BluriteSkirt, "Blurite Skirt");
        LanguageRegistry.addName(BluriteWarhammer, "Blurite Warhammer");
        LanguageRegistry.addName(BluriteBattleaxe, "Blurite Battleaxe");
        LanguageRegistry.addName(BluriteDagger, "Blurite Dagger");
        LanguageRegistry.addName(BluriteScimitar, "Blurite Scimitar");
        LanguageRegistry.addName(BluriteMace, "Blurite Mace");
        LanguageRegistry.addName(BluriteBow, "Blurite Bow");
        //LanguageRegistry.addName(BluriteStick, "Blurite Stick");

        GameRegistry.addSmelting(BluriteOre.blockID, new ItemStack(BluriteIngot, 1), 0.7F);
        GameRegistry.addRecipe(new ItemStack(BluriteBlock, 1), new Object[] {"###", "###", "###", '#', BluriteIngot});
        GameRegistry.addRecipe(new ItemStack(BluriteIngot, 9), new Object[] {"#", '#', BluriteBlock});
        GameRegistry.addRecipe(new ItemStack(BluritePickaxe, 1), new Object[] {"###", " X ", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteAxe, 1), new Object[] {"## ", "#X ", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteAxe, 1), new Object[] {" ##", " X#", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteSword, 1), new Object[] {" # ", " # ", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteShovel, 1), new Object[] {" # ", " X ", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteHoe, 1), new Object[] {"## ", " X ", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteHoe, 1), new Object[] {" ##", " X ", " X ", '#', BluriteIngot, 'X', Item.stick});
//        GameRegistry.addRecipe(new ItemStack(BluriteHelmet, 1), new Object[] {"###", "# #", '#', BluriteIngot});
//        GameRegistry.addRecipe(new ItemStack(BluriteChest, 1), new Object[] {"# #", "###", "###", '#', BluriteIngot});
//        GameRegistry.addRecipe(new ItemStack(BluriteLegs, 1), new Object[] {"###", "# #", "# #", '#', BluriteIngot});
//        GameRegistry.addRecipe(new ItemStack(BluriteBoots, 1), new Object[] {"# #", "# #", '#', BluriteIngot});
//        GameRegistry.addRecipe(new ItemStack(BluriteSkirt, 1), new Object[] {"###", "###", '#', BluriteIngot});
        GameRegistry.addRecipe(new ItemStack(BluriteWarhammer, 1), new Object[] {"###", "###", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteDagger, 1), new Object[] {" # ", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteDagger, 1), new Object[] {"   ", " # ", " X ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteMace, 1), new Object[] {"  #", "  X", "  X", '#', BluriteIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(BluriteBow, 1), new Object[] {" #X", "# X", " #X", '#', BluriteIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(BluriteScimitar, 1), new Object[] {"  #", " # ", " X ", '#', BluriteIngot, 'X', Item.stick});
        //GameRegistry.addRecipe(new ItemStack(BluriteStick, 4), new Object[] {"#", "#", '#', BluriteIngot});

        OreDictionary.registerOre("BluriteOre", new ItemStack(BluriteOre, 1, 0));
        OreDictionary.registerOre("oreBlurite", new ItemStack(BluriteOre, 1, 0));
        OreDictionary.registerOre("BluriteIngot", new ItemStack(BluriteIngot, 1, 0));
        OreDictionary.registerOre("ingotBlurite", new ItemStack(BluriteIngot, 1, 0));
        //OreDictionary.registerOre("BluriteStick", new ItemStack(BluriteStick, 1, 0));
        //OreDictionary.registerOre("stickBlurite", new ItemStack(BluriteStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(BluriteOre,   "pickaxe", 2);
        MinecraftForge.setBlockHarvestLevel(BluriteBlock,   "pickaxe", 2);
        MinecraftForge.setToolClass(BluritePickaxe, "pickaxe", 2);
        MinecraftForge.setToolClass(BluriteAxe, "axe", 2);
        MinecraftForge.setToolClass(BluriteShovel, "shovel", 2);
    }

	private static void loadSteel()
    {
        GameRegistry.registerBlock(SteelBlock, "SteelBlock");

        LanguageRegistry.addName(SteelBlock, "Steel Block");
        LanguageRegistry.addName(SteelIngot, "Steel Bar");
        LanguageRegistry.addName(SteelPickaxe, "Steel Pickaxe");
        LanguageRegistry.addName(SteelAxe, "Steel Axe");
        LanguageRegistry.addName(SteelSword, "Steel Sword");
        LanguageRegistry.addName(SteelShovel, "Steel Shovel");
        LanguageRegistry.addName(SteelHoe, "Steel Hoe");
        LanguageRegistry.addName(SteelHelmet, "Steel Helmet");
        LanguageRegistry.addName(SteelChest, "Steel Chestplate");
        LanguageRegistry.addName(SteelLegs, "Steel Legs");
        LanguageRegistry.addName(SteelBoots, "Steel Boots");
        LanguageRegistry.addName(SteelSkirt, "Steel Skirt");
        LanguageRegistry.addName(SteelWarhammer, "Steel Warhammer");
        LanguageRegistry.addName(SteelBattleaxe, "Steel Battleaxe");
        LanguageRegistry.addName(SteelDagger, "Steel Dagger");
        LanguageRegistry.addName(SteelScimitar, "Steel Scimitar");
        LanguageRegistry.addName(SteelMace, "Steel Mace");
        LanguageRegistry.addName(SteelBow, "Steel Bow");
        LanguageRegistry.addName(SteelStick, "Steel Stick");

        GameRegistry.addSmelting(Item.ingotIron.itemID, new ItemStack(SteelIngot, 1), 0.7F);
        GameRegistry.addRecipe(new ItemStack(SteelBlock, 1), new Object[] {"###", "###", "###", '#', SteelIngot});
        GameRegistry.addRecipe(new ItemStack(SteelIngot, 9), new Object[] {"#", '#', SteelBlock});
        GameRegistry.addRecipe(new ItemStack(SteelPickaxe, 1), new Object[] {"###", " X ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelAxe, 1), new Object[] {"## ", "#X ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelAxe, 1), new Object[] {" ##", " X#", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelSword, 1), new Object[] {" # ", " # ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelShovel, 1), new Object[] {" # ", " X ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelHoe, 1), new Object[] {"## ", " X ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelHoe, 1), new Object[] {" ##", " X ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelHelmet, 1), new Object[] {"###", "# #", '#', SteelIngot});
        GameRegistry.addRecipe(new ItemStack(SteelChest, 1), new Object[] {"# #", "###", "###", '#', SteelIngot});
        GameRegistry.addRecipe(new ItemStack(SteelLegs, 1), new Object[] {"###", "# #", "# #", '#', SteelIngot});
        GameRegistry.addRecipe(new ItemStack(SteelBoots, 1), new Object[] {"# #", "# #", '#', SteelIngot});
        GameRegistry.addRecipe(new ItemStack(SteelSkirt, 1), new Object[] {"###", "###", '#', SteelIngot});
        GameRegistry.addRecipe(new ItemStack(SteelWarhammer, 1), new Object[] {"###", "###", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelDagger, 1), new Object[] {" # ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelDagger, 1), new Object[] {"   ", " # ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelMace, 1), new Object[] {"  #", "  X", "  X", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelBow, 1), new Object[] {" #X", "# X", " #X", '#', SteelIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(SteelScimitar, 1), new Object[] {"  #", " # ", " X ", '#', SteelIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(SteelStick, 4), new Object[] {"#", "#", '#', SteelIngot});

        OreDictionary.registerOre("SteelIngot", new ItemStack(SteelIngot, 1, 0));
        OreDictionary.registerOre("ingotSteel", new ItemStack(SteelIngot, 1, 0));
        OreDictionary.registerOre("SteelStick", new ItemStack(SteelStick, 1, 0));
        OreDictionary.registerOre("stickSteel", new ItemStack(SteelStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(SteelBlock,   "pickaxe", 1);
        MinecraftForge.setToolClass(SteelPickaxe, "pickaxe", 2);
        MinecraftForge.setToolClass(SteelAxe, "axe", 2);
        MinecraftForge.setToolClass(SteelShovel, "shovel", 2);
    }

	private static void loadObsidium()
    {
        GameRegistry.registerBlock(ObsidiumOre, "ObsidiumOre");
        GameRegistry.registerBlock(ObsidiumBlock, "ObsidiumBlock");

        LanguageRegistry.addName(ObsidiumOre, "Obsidium Ore");
        LanguageRegistry.addName(ObsidiumBlock, "Obsidium Block");
        LanguageRegistry.addName(ObsidiumIngot, "Obsidium Bar");
        LanguageRegistry.addName(ObsidiumPickaxe, "Obsidium Pickaxe");
        LanguageRegistry.addName(ObsidiumAxe, "Obsidium Axe");
        LanguageRegistry.addName(ObsidiumSword, "Obsidium Sword");
        LanguageRegistry.addName(ObsidiumShovel, "Obsidium Shovel");
        LanguageRegistry.addName(ObsidiumHoe, "Obsidium Hoe");
//        LanguageRegistry.addName(ObsidiumHelmet, "Obsidium Helmet");
//        LanguageRegistry.addName(ObsidiumChest, "Obsidium Chestplate");
//        LanguageRegistry.addName(ObsidiumLegs, "Obsidium Legs");
//        LanguageRegistry.addName(ObsidiumBoots, "Obsidium Boots");
//        LanguageRegistry.addName(ObsidiumSkirt, "Obsidium Skirt");
        LanguageRegistry.addName(ObsidiumWarhammer, "Obsidium Warhammer");
        LanguageRegistry.addName(ObsidiumBattleaxe, "Obsidium Battleaxe");
        LanguageRegistry.addName(ObsidiumDagger, "Obsidium Dagger");
        LanguageRegistry.addName(ObsidiumScimitar, "Obsidium Scimitar");
        LanguageRegistry.addName(ObsidiumMace, "Obsidium Mace");
        LanguageRegistry.addName(ObsidiumBow, "Obsidium Bow");
        LanguageRegistry.addName(ObsidiumStick, "Obsidium Stick");

        GameRegistry.addSmelting(ObsidiumOre.blockID, new ItemStack(ObsidiumIngot, 1), 1.0F);
        GameRegistry.addRecipe(new ItemStack(ObsidiumBlock, 1), new Object[] {"###", "###", "###", '#', ObsidiumIngot});
        GameRegistry.addRecipe(new ItemStack(ObsidiumIngot, 9), new Object[] {"#", '#', ObsidiumBlock});
        GameRegistry.addRecipe(new ItemStack(ObsidiumPickaxe, 1), new Object[] {"###", " X ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumAxe, 1), new Object[] {"## ", "#X ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumAxe, 1), new Object[] {" ##", " X#", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumSword, 1), new Object[] {" # ", " # ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumShovel, 1), new Object[] {" # ", " X ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumHoe, 1), new Object[] {"## ", " X ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumHoe, 1), new Object[] {" ##", " X ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
//        GameRegistry.addRecipe(new ItemStack(ObsidiumHelmet, 1), new Object[] {"###", "# #", '#', ObsidiumIngot});
//        GameRegistry.addRecipe(new ItemStack(ObsidiumChest, 1), new Object[] {"# #", "###", "###", '#', ObsidiumIngot});
//        GameRegistry.addRecipe(new ItemStack(ObsidiumLegs, 1), new Object[] {"###", "# #", "# #", '#', ObsidiumIngot});
//        GameRegistry.addRecipe(new ItemStack(ObsidiumBoots, 1), new Object[] {"# #", "# #", '#', ObsidiumIngot});
//        GameRegistry.addRecipe(new ItemStack(ObsidiumSkirt, 1), new Object[] {"###", "###", '#', ObsidiumIngot});
        GameRegistry.addRecipe(new ItemStack(ObsidiumWarhammer, 1), new Object[] {"###", "###", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumDagger, 1), new Object[] {" # ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumDagger, 1), new Object[] {"   ", " # ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumMace, 1), new Object[] {"  #", "  X", "  X", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumBow, 1), new Object[] {" #X", "# X", " #X", '#', ObsidiumIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(ObsidiumScimitar, 1), new Object[] {"  #", " # ", " X ", '#', ObsidiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ObsidiumStick, 4), new Object[] {"#", "#", '#', ObsidiumIngot});

        OreDictionary.registerOre("ObsidiumOre", new ItemStack(ObsidiumOre, 1, 0));
        OreDictionary.registerOre("oreObsidium", new ItemStack(ObsidiumOre, 1, 0));
        OreDictionary.registerOre("ObsidiumIngot", new ItemStack(ObsidiumIngot, 1, 0));
        OreDictionary.registerOre("ingotObsidium", new ItemStack(ObsidiumIngot, 1, 0));
        OreDictionary.registerOre("ObsidiumStick", new ItemStack(ObsidiumStick, 1, 0));
        OreDictionary.registerOre("stickObsidium", new ItemStack(ObsidiumStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(ObsidiumOre,   "pickaxe", 3);
        MinecraftForge.setBlockHarvestLevel(ObsidiumBlock,   "pickaxe", 3);
        MinecraftForge.setToolClass(ObsidiumPickaxe, "pickaxe", 4);
        MinecraftForge.setToolClass(ObsidiumAxe, "axe", 4);
        MinecraftForge.setToolClass(ObsidiumShovel, "shovel", 4);
    }

	private static void loadTitanium()
    {
        GameRegistry.registerBlock(TitaniumOre, "TitaniumOre");
        GameRegistry.registerBlock(TitaniumBlock, "TitaniumBlock");

        LanguageRegistry.addName(TitaniumOre, "Titanium Ore");
        LanguageRegistry.addName(TitaniumBlock, "Titanium Block");
        LanguageRegistry.addName(TitaniumIngot, "Titanium Bar");
        LanguageRegistry.addName(TitaniumPickaxe, "Titanium Pickaxe");
        LanguageRegistry.addName(TitaniumAxe, "Titanium Axe");
        LanguageRegistry.addName(TitaniumSword, "Titanium Sword");
        LanguageRegistry.addName(TitaniumShovel, "Titanium Shovel");
        LanguageRegistry.addName(TitaniumHoe, "Titanium Hoe");
        LanguageRegistry.addName(TitaniumHelmet, "Titanium Helmet");
        LanguageRegistry.addName(TitaniumChest, "Titanium Chestplate");
        LanguageRegistry.addName(TitaniumLegs, "Titanium Legs");
        LanguageRegistry.addName(TitaniumBoots, "Titanium Boots");
        LanguageRegistry.addName(TitaniumSkirt, "Titanium Skirt");
        LanguageRegistry.addName(TitaniumWarhammer, "Titanium Warhammer");
        LanguageRegistry.addName(TitaniumBattleaxe, "Titanium Battleaxe");
        LanguageRegistry.addName(TitaniumDagger, "Titanium Dagger");
        LanguageRegistry.addName(TitaniumScimitar, "Titanium Scimitar");
        LanguageRegistry.addName(TitaniumMace, "Titanium Mace");
        LanguageRegistry.addName(TitaniumBow, "Titanium Bow");
        LanguageRegistry.addName(TitaniumStick, "Titanium Stick");

        GameRegistry.addSmelting(TitaniumOre.blockID, new ItemStack(TitaniumIngot, 1), 1.0F);
        GameRegistry.addRecipe(new ItemStack(TitaniumBlock, 1), new Object[] {"###", "###", "###", '#', TitaniumIngot});
        GameRegistry.addRecipe(new ItemStack(TitaniumIngot, 9), new Object[] {"#", '#', TitaniumBlock});
        GameRegistry.addRecipe(new ItemStack(TitaniumPickaxe, 1), new Object[] {"###", " X ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumAxe, 1), new Object[] {"## ", "#X ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumAxe, 1), new Object[] {" ##", " X#", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumSword, 1), new Object[] {" # ", " # ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumShovel, 1), new Object[] {" # ", " X ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumHoe, 1), new Object[] {"## ", " X ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumHoe, 1), new Object[] {" ##", " X ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumHelmet, 1), new Object[] {"###", "# #", '#', TitaniumIngot});
        GameRegistry.addRecipe(new ItemStack(TitaniumChest, 1), new Object[] {"# #", "###", "###", '#', TitaniumIngot});
        GameRegistry.addRecipe(new ItemStack(TitaniumLegs, 1), new Object[] {"###", "# #", "# #", '#', TitaniumIngot});
        GameRegistry.addRecipe(new ItemStack(TitaniumBoots, 1), new Object[] {"# #", "# #", '#', TitaniumIngot});
        GameRegistry.addRecipe(new ItemStack(TitaniumSkirt, 1), new Object[] {"###", "###", '#', TitaniumIngot});
        GameRegistry.addRecipe(new ItemStack(TitaniumWarhammer, 1), new Object[] {"###", "###", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumDagger, 1), new Object[] {" # ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumDagger, 1), new Object[] {"   ", " # ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumMace, 1), new Object[] {"  #", "  X", "  X", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumBow, 1), new Object[] {" #X", "# X", " #X", '#', TitaniumIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(TitaniumScimitar, 1), new Object[] {"  #", " # ", " X ", '#', TitaniumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(TitaniumStick, 4), new Object[] {"#", "#", '#', TitaniumIngot});

        OreDictionary.registerOre("TitaniumOre", new ItemStack(TitaniumOre, 1, 0));
        OreDictionary.registerOre("oreTitanium", new ItemStack(TitaniumOre, 1, 0));
        OreDictionary.registerOre("TitaniumIngot", new ItemStack(TitaniumIngot, 1, 0));
        OreDictionary.registerOre("ingotTitanium", new ItemStack(TitaniumIngot, 1, 0));
        OreDictionary.registerOre("TitaniumStick", new ItemStack(TitaniumStick, 1, 0));
        OreDictionary.registerOre("stickTitanium", new ItemStack(TitaniumStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(TitaniumOre,   "pickaxe", 3);
        MinecraftForge.setBlockHarvestLevel(TitaniumBlock,   "pickaxe", 3);
        MinecraftForge.setToolClass(TitaniumPickaxe, "pickaxe", 4);
        MinecraftForge.setToolClass(TitaniumAxe, "axe", 4);
        MinecraftForge.setToolClass(TitaniumShovel, "shovel", 4);
    }

	/*private static void loadElementium()
    {
        GameRegistry.registerBlock(ElementiumOre, "ElementiumOre");
        GameRegistry.registerBlock(ElementiumBlock, "ElementiumBlock");

        LanguageRegistry.addName(ElementiumOre, "Elementium Ore");
        LanguageRegistry.addName(ElementiumBlock, "Elementium Block");
        LanguageRegistry.addName(ElementiumIngot, "Elementium Bar");
        LanguageRegistry.addName(ElementiumPickaxe, "Elementium Pickaxe");
        LanguageRegistry.addName(ElementiumAxe, "Elementium Axe");
        LanguageRegistry.addName(ElementiumSword, "Elementium Sword");
        LanguageRegistry.addName(ElementiumShovel, "Elementium Shovel");
        LanguageRegistry.addName(ElementiumHoe, "Elementium Hoe");
        LanguageRegistry.addName(ElementiumHelmet, "Elementium Helmet");
        LanguageRegistry.addName(ElementiumChest, "Elementium Chestplate");
        LanguageRegistry.addName(ElementiumLegs, "Elementium Legs");
        LanguageRegistry.addName(ElementiumBoots, "Elementium Boots");
        LanguageRegistry.addName(ElementiumSkirt, "Elementium Skirt");
        LanguageRegistry.addName(ElementiumWarhammer, "Elementium Warhammer");
        LanguageRegistry.addName(ElementiumBattleaxe, "Elementium Battleaxe");
        LanguageRegistry.addName(ElementiumDagger, "Elementium Dagger");
        LanguageRegistry.addName(ElementiumScimitar, "Elementium Scimitar");
        LanguageRegistry.addName(ElementiumMace, "Elementium Mace");
        LanguageRegistry.addName(ElementiumBow, "Elementium Bow");
        LanguageRegistry.addName(ElementiumStick, "Elementium Stick");

        GameRegistry.addSmelting(ElementiumOre.blockID, new ItemStack(ElementiumIngot, 1), 1.0F);
        GameRegistry.addRecipe(new ItemStack(ElementiumBlock, 1), new Object[] {"###", "###", "###", '#', ElementiumIngot});
        GameRegistry.addRecipe(new ItemStack(ElementiumIngot, 9), new Object[] {"#", '#', ElementiumBlock});
        GameRegistry.addRecipe(new ItemStack(ElementiumPickaxe, 1), new Object[] {"###", " X ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumAxe, 1), new Object[] {"## ", "#X ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumAxe, 1), new Object[] {" ##", " X#", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumSword, 1), new Object[] {" # ", " # ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumShovel, 1), new Object[] {" # ", " X ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumHoe, 1), new Object[] {"## ", " X ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumHoe, 1), new Object[] {" ##", " X ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumHelmet, 1), new Object[] {"###", "# #", '#', ElementiumIngot});
        GameRegistry.addRecipe(new ItemStack(ElementiumChest, 1), new Object[] {"# #", "###", "###", '#', ElementiumIngot});
        GameRegistry.addRecipe(new ItemStack(ElementiumLegs, 1), new Object[] {"###", "# #", "# #", '#', ElementiumIngot});
        GameRegistry.addRecipe(new ItemStack(ElementiumBoots, 1), new Object[] {"# #", "# #", '#', ElementiumIngot});
        GameRegistry.addRecipe(new ItemStack(ElementiumSkirt, 1), new Object[] {"###", "###", '#', ElementiumIngot});
        GameRegistry.addRecipe(new ItemStack(ElementiumWarhammer, 1), new Object[] {"###", "###", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumBattleaxe, 1), new Object[] {"###", "#X#", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumDagger, 1), new Object[] {" # ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumDagger, 1), new Object[] {"   ", " # ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumMace, 1), new Object[] {"#  ", "X  ", "X  ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumMace, 1), new Object[] {"  #", "  X", "  X", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumBow, 1), new Object[] {" #X", "# X", " #X", '#', ElementiumIngot, 'X', Item.silk});
        GameRegistry.addRecipe(new ItemStack(ElementiumScimitar, 1), new Object[] {"  #", " # ", " X ", '#', ElementiumIngot, 'X', Item.stick});
        GameRegistry.addRecipe(new ItemStack(ElementiumStick, 4), new Object[] {"#", "#", '#', ElementiumIngot});

        OreDictionary.registerOre("ElementiumOre", new ItemStack(ElementiumOre, 1, 0));
        OreDictionary.registerOre("oreElementium", new ItemStack(ElementiumOre, 1, 0));
        OreDictionary.registerOre("ElementiumIngot", new ItemStack(ElementiumIngot, 1, 0));
        OreDictionary.registerOre("ingotElementium", new ItemStack(ElementiumIngot, 1, 0));
        OreDictionary.registerOre("ElementiumStick", new ItemStack(ElementiumStick, 1, 0));
        OreDictionary.registerOre("stickElementium", new ItemStack(ElementiumStick, 1, 0));

        MinecraftForge.setBlockHarvestLevel(ElementiumOre,   "pickaxe", 3);
        MinecraftForge.setBlockHarvestLevel(ElementiumBlock,   "pickaxe", 3);
        MinecraftForge.setToolClass(ElementiumPickaxe, "pickaxe", 4);
        MinecraftForge.setToolClass(ElementiumAxe, "axe", 4);
        MinecraftForge.setToolClass(ElementiumShovel, "shovel", 4);
    }*/

	private static String getBlockInfo(Block block, int metaID)
    {
    	int blockId = block.blockID;
    	int blockMeta = metaID;
    	if (blockId > 0)
    	{
    		ItemStack itemStack = new ItemStack(Block.blocksList[blockId], 1, blockMeta);
    		List itemInfo = getItemNameandInformation(itemStack);
    		boolean hasSubtypes = itemStack.getHasSubtypes();
    		if (!itemInfo.isEmpty())
    		{
    			String blockName = (String)itemInfo.get(0);
    			return (new StringBuilder()).append(blockName).toString();
    		}
    	}
        return null;
    }

	/**
     * gets a list of strings representing the item name and successive extra data, eg Enchantments and potion effects
     */
	private static List getItemNameandInformation(ItemStack itemStack)
    {
        ArrayList var1 = new ArrayList();
        Item var2 = Item.itemsList[itemStack.itemID];
        var1.add(itemStack.getDisplayName());
        var2.addInformation(itemStack, mc.thePlayer, var1, mc.gameSettings.advancedItemTooltips);

        if (itemStack.hasTagCompound())
        {
            NBTTagList var3 = itemStack.getEnchantmentTagList();

            if (var3 != null)
            {
                for (int var4 = 0; var4 < var3.tagCount(); ++var4)
                {
                    short var5 = ((NBTTagCompound)var3.tagAt(var4)).getShort("id");
                    short var6 = ((NBTTagCompound)var3.tagAt(var4)).getShort("lvl");

                    if (Enchantment.enchantmentsList[var5] != null)
                    {
                        var1.add(Enchantment.enchantmentsList[var5].getTranslatedName(var6));
                    }
                }
            }
        }

        return var1;
    }
}
