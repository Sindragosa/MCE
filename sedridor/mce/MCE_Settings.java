package sedridor.mce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.BiomeGenBase;

public class MCE_Settings
{
    public static String Mithril = "yes";
    public static String Adamantium = "yes";
    public static String Runite = "yes";
    public static String Dragonite = "yes";
    public static String Crystal = "yes";
    public static String Silver = "yes";
    public static String Blurite = "yes";
    public static String Bronze = "yes";
    public static String Steel = "yes";
    public static String Obsidium = "yes";
    public static String Titanium = "yes";
    public static String Elementium = "yes";

    public static int displacedBlockID = 2500;
    public static int firstBlockID = 200;
    public static int firstItemID = 17000;

    public static int GreenLeavesID = 2791;
    public static int AutumnLeavesID = 2792;
    public static int SaplingID = 2795;
    public static int CatTailID = 2793;
    public static int FlowerID = 2794;
    public static int LeafPileID = 2796;
    public static int BlastFurnaceID = 2797;
    public static int ExtendedCraftingTableID = 2799;

    public static String InfoScreen = "yes";
    public static String InfoScreenPlayer = "yes";
    public static String InfoScreenBiome = "yes";
    public static String InfoScreenChunk = "yes";
    public static String InfoScreenCoords = "yes";
    public static String InfoScreenHeading = "yes";
    public static String InfoScreenSkylight = "yes";
    public static String InfoScreenLight = "yes";
    public static String InfoScreenVelocity = "yes";
    public static String InfoScreenSeed = "yes";
    public static String InfoScreenSpawn = "yes";
    public static String InfoScreenTime = "yes";
    public static String InfoScreenRain = "yes";
    public static String InfoScreenThunder = "yes";
    public static String InfoScreenEquipped = "yes";
    public static String InfoScreenDurability = "yes";
    public static String InfoScreenArrows = "yes";
    public static String InfoScreenVillageInfo = "no";
    public static String InfoScreenBlockTarget = "yes";
    public static String InfoScreenEntityTarget = "yes";

    public static String HUD = "yes";
    public static String HudBiome = "yes";
    public static String HudCoords = "yes";
    public static String HudHeading = "yes";
    public static String HudSkylight = "yes";
    public static String HudLight = "yes";
    public static String HudVelocity = "yes";
    public static String HudSpawn = "yes";
    public static String HudTime = "yes";
    public static String HudRain = "yes";
    public static String HudThunder = "yes";
    public static String HudEquipped = "yes";
    public static String HudDurability = "yes";
    public static String HudArrows = "yes";
    public static String HudBlockTarget = "yes";
    public static String HudEntityTarget = "yes";
    public static int HudCoordsType = 0;

    public static String HideAchievements = "yes";
    public static String ItemStackCount = "yes";
    public static String ItemDamageCount = "yes";
    public static String PowerOverlay = "no";
    public static String PowerTexture = "no";

    public static int HudKey = Keyboard.KEY_F6;
    public static int InfoKey = Keyboard.KEY_F4;
    public static String ArrowsMovement = "yes";
    public static int ArrowsTurnrate = 30;

    public static String Mobs = "yes";
    public static String Biomes = "yes";
    public static String Items = "yes";
    public static String NPCs = "yes";
    public static String World = "yes";

    public static Map<String, Integer> biomeGeneratorSettings = new LinkedHashMap<String, Integer>();

    public static int TimerScale;

    public static double SlopeScale;
    public static double HeightScale;
    public static float GenCavesRarity;
    public static float GenRavineRarity;
    public static int GenDungeons;
    public static float GenWaterInDesert;
    public static String MultiLayerOreGeneration = "yes";

    public static Map<String, int[]> entitySettings = new HashMap<String, int[]>();
    public static Map<String, String[]> entityBiomes = new HashMap<String, String[]>();

    private static Minecraft mc;
    private static File optionsFile;
    private static boolean hasInit = false;

    public static void init(File options)
    {
    	mc = Minecraft.getMinecraft();
        if (!hasInit)
        {
        	hasInit = true;
            initDefaults();

          optionsFile = new File(mc.mcDataDir, "MCE-Settings.txt");
          loadOptions();
        }
    }

    public static void initDefaults()
    {
        // Desert, Forest, ExtremeHills, Swampland, Plains, Taiga, Jungle;
        // biomeChances = {15, 30, 5, 5, 25, 10, 10};
        // Savanna, Rainforest, Shrubland, SeasonalForest, Woodlands, GreenHills, PineForest, GreenSwamplands, Redrock, Tundra;
        // biomeChances = {15, 10, 10, 5, 25, 10, 15, 5, 5, 0};
    	biomeGeneratorSettings.put("Desert", 15);
    	biomeGeneratorSettings.put("Forest", 30);
    	biomeGeneratorSettings.put("ExtremeHills", 5);
    	biomeGeneratorSettings.put("Swampland", 5);
    	biomeGeneratorSettings.put("Plains", 25);
    	biomeGeneratorSettings.put("Taiga", 10);
    	biomeGeneratorSettings.put("Jungle", 10);

        biomeGeneratorSettings.put("Savanna", 15);
        biomeGeneratorSettings.put("Rainforest", 30);
        biomeGeneratorSettings.put("Shrubland", 5);
        biomeGeneratorSettings.put("SeasonalForest", 5);
        biomeGeneratorSettings.put("Woodlands", 25);
        biomeGeneratorSettings.put("GreenHills", 10);
        biomeGeneratorSettings.put("PineForest", 10);
        biomeGeneratorSettings.put("GreenSwamplands", 15);
        biomeGeneratorSettings.put("Redrock", 30);
        biomeGeneratorSettings.put("Tundra", 5);

//        BiomeGenBase biome;
//        for (int i = 0; i < BiomeGenBase.biomeList.length; ++i)
//        {
//            biome = BiomeGenBase.biomeList[i];
//            if (biome == null || !(biome instanceof BiomeGenBase)) { continue; }
//
//            if (biomeGeneratorSettings.containsKey(biome.biomeName))
//            {
//            	//String biomeClassName = biome.getClass().getName();
//                //System.out.println("MC... " + biomeClassName.substring(biomeClassName.lastIndexOf(".BiomeGen") + 9));
//            	//biomeGeneratorSettings.put(biomeClassName.substring(biomeClassName.lastIndexOf(".BiomeGen") + 9), 15);
//            }
//            else
//            {
//            	String biomeClassName = biome.getClass().getName();
//                System.out.println("MC... " + biomeClassName.substring(biomeClassName.lastIndexOf(".BiomeGen") + 9));
//            	biomeGeneratorSettings.put(biomeClassName.substring(biomeClassName.lastIndexOf(".BiomeGen") + 9), 15);
//            }
//    	}

        TimerScale = 1;

        Mithril = "yes";
        Adamantium = "yes";
        Runite = "yes";
        Dragonite = "yes";
        Crystal = "yes";
        Silver = "yes";
        Blurite = "yes";
        Bronze = "yes";
        Steel = "yes";
        Obsidium = "yes";
        Titanium = "yes";
        Elementium = "yes";

        displacedBlockID = 2500;
        firstBlockID = 200;
        firstItemID = 17000;

        GreenLeavesID = 2791;
        AutumnLeavesID = 2792;
        SaplingID = 2795;
        CatTailID = 2793;
        FlowerID = 2794;
        LeafPileID = 2796;
        BlastFurnaceID = 2797;
        ExtendedCraftingTableID = 2799;

        InfoScreen = "yes";
//      InfoScreenPlayer = "yes";
//      InfoScreenBiome = "yes";
//      InfoScreenChunk = "yes";
//      InfoScreenCoords = "yes";
//      InfoScreenHeading = "yes";
//      InfoScreenSkylight = "yes";
//      InfoScreenLight = "yes";
//      InfoScreenVelocity = "yes";
//      InfoScreenSeed = "yes";
//      InfoScreenSpawn = "yes";
//      InfoScreenTime = "yes";
//      InfoScreenRain = "yes";
//      InfoScreenThunder = "yes";
//      InfoScreenEquipped = "yes";
//      InfoScreenDurability = "yes";
//      InfoScreenArrows = "yes";
        InfoScreenVillageInfo = "no";
//      InfoScreenBlockTarget = "yes";
//      InfoScreenEntityTarget = "yes";

        HUD = "yes";
        HudBiome = "yes";
        HudCoords = "yes";
        HudHeading = "yes";
        HudSkylight = "yes";
        HudLight = "yes";
        HudVelocity = "yes";
        HudSpawn = "yes";
        HudTime = "yes";
        HudRain = "yes";
        HudThunder = "yes";
        HudEquipped = "yes";
        HudDurability = "yes";
        HudArrows = "yes";
        HudBlockTarget = "yes";
        HudEntityTarget = "yes";
        HudCoordsType = 0;

        HideAchievements = "yes";
        ItemStackCount = "yes";
        ItemDamageCount = "yes";
        PowerOverlay = "no";
        PowerTexture = "no";

        HudKey = Keyboard.KEY_F6;
        InfoKey = Keyboard.KEY_F4;
        ArrowsMovement = "yes";
        ArrowsTurnrate = 30;

        Mobs = "yes";
        Biomes = "yes";
        Items = "yes";
        NPCs = "yes";
        World = "yes";

        SlopeScale = 10.0D;
        HeightScale = 12.0D;
        GenCavesRarity = 0.25F;
        GenRavineRarity = 0.1F;
        GenDungeons = 8;
        GenWaterInDesert = 0.2F;
        MultiLayerOreGeneration = "yes";

        entitySettings.put("DwarfFemale", new int[] { 2, 0, 2 });
        entitySettings.put("DwarfMale", new int[] { 2, 0, 2 });
        entitySettings.put("ElfFemale", new int[] { 2, 0, 2 });
        entitySettings.put("ElfMale", new int[] { 2, 0, 2 });
        entitySettings.put("HumanFemale", new int[] { 2, 0, 2 });
        //entitySettings.put("HumanMale", new int[] { 0, 0, 1 });
        //entitySettings.put("Ogre2", new int[] { 0, 0, 1 });
        entitySettings.put("NagaFemale", new int[] { 1, 0, 3 });
        entitySettings.put("NagaMale", new int[] { 1, 0, 3 });

        entitySettings.put("Dwarf", new int[] { 2, 0, 2 });
        entitySettings.put("Elf", new int[] { 2, 0, 2 });
        entitySettings.put("Ogre", new int[] { 1, 0, 2 });
        entitySettings.put("OrcFemale", new int[] { 2, 0, 3 });
        entitySettings.put("OrcMale", new int[] { 2, 0, 3 });
        entitySettings.put("Crusader", new int[] { 1, 0, 2 });
        entitySettings.put("Goblin", new int[] { 1, 0, 4 });
        entitySettings.put("Samurai", new int[] { 1, 0, 2 });
        entitySettings.put("BattleMonk", new int[] { 1, 0, 1 });
        entitySettings.put("Merchant", new int[] { 1, 0, 1 });
        entitySettings.put("DesertMerchant", new int[] { 1, 0, 1 });
        entitySettings.put("Gladiator", new int[] { 1, 0, 1 });
        entitySettings.put("Bandit", new int[] { 1, 0, 1 });
        entitySettings.put("Guard", new int[] { 1, 0, 2 });
        entitySettings.put("Pirate", new int[] { 1, 0, 4 });
        entitySettings.put("Reptilian", new int[] { 1, 0, 3 });
        entitySettings.put("Ranger", new int[] { 1, 0, 2 });
        entitySettings.put("Dovahkiin", new int[] { 1, 0, 1 });
        entitySettings.put("Assassin", new int[] { 1, 0, 1 });
        entitySettings.put("DarkKnight", new int[] { 1, 0, 1 });
        entitySettings.put("DarkMerchant", new int[] { 1, 0, 1 });
        entitySettings.put("SkeletonNinja", new int[] { 1, 0, 1 });
        entitySettings.put("SkeletonWarrior", new int[] { 1, 0, 2 });
        entitySettings.put("Viking", new int[] { 1, 0, 2 });
        entitySettings.put("WarriorPrincess", new int[] { 1, 0, 1 });
        entitySettings.put("RedDragon", new int[] { 1, 0, 1 });
        entitySettings.put("GoblinBomber", new int[] { 1, 0, 2 });
        entitySettings.put("GoblinShaman", new int[] { 1, 0, 2 });
        entitySettings.put("GoblinNinja", new int[] { 1, 0, 2 });
        entitySettings.put("GoblinRanger", new int[] { 1, 0, 2 });
        //entitySettings.put("Ninja", new int[] { 1, 0, 1 });
        entitySettings.put("Minotaur", new int[] { 1, 0, 1 });
        entitySettings.put("Ghost", new int[] { 1, 0, 1 });
        entitySettings.put("Giant", new int[] { 1, 0, 1 });
        entitySettings.put("Lich", new int[] { 1, 0, 1 });
        entitySettings.put("Centaur", new int[] { 1, 0, 1 });
        entitySettings.put("Pygmy", new int[] { 1, 0, 4 });
        
        entityBiomes.put("DwarfFemale", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Ice Plains", "Ice Mountains", "Extreme Hills", "Extreme Hills Edge", "Plains" });
        entityBiomes.put("DwarfMale", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Ice Plains", "Ice Mountains", "Extreme Hills", "Extreme Hills Edge", "Plains" });
        entityBiomes.put("ElfFemale", new String[] { "Forest", "ForestHills", "Jungle", "Taiga", "River" });
        entityBiomes.put("ElfMale", new String[] { "Forest", "ForestHills", "Jungle", "Taiga", "River" });
        entityBiomes.put("HumanFemale", new String[] { "Forest", "ForestHills", "Jungle", "Taiga", "Plains", "Beach", "River" });
        //entityBiomes.put("HumanMale", new String[] { "Forest", "ForestHills", "Jungle", "Taiga", "Plains", "Beach", "River" });
        //entityBiomes.put("Ogre2", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Plains", "Swampland" });
        entityBiomes.put("NagaFemale", new String[] { "Beach", "River", "Ocean", "FrozenOcean", "FrozenRiver", "Swampland", "VolcanicIslandShore", "ForestedIsland" });
        entityBiomes.put("NagaMale", new String[] { "Beach", "River", "Ocean", "FrozenOcean", "FrozenRiver", "Swampland", "VolcanicIslandShore", "ForestedIsland" });

        entityBiomes.put("Dwarf", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Ice Plains", "Ice Mountains", "Extreme Hills", "Extreme Hills Edge", "Plains" });
        entityBiomes.put("Elf", new String[] { "Forest", "ForestHills", "Jungle", "JungleHills" });
        entityBiomes.put("Ogre", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Plains", "Swampland" });
        entityBiomes.put("OrcFemale", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Jungle", "JungleHills", "Plains", "Desert" });
        entityBiomes.put("OrcMale", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Jungle", "JungleHills", "Plains", "Desert" });
        entityBiomes.put("Crusader", new String[] { "Desert", "Taiga", "TaigaHills" });
        entityBiomes.put("Goblin", new String[] { "*" });
        entityBiomes.put("Samurai", new String[] { "Desert", "ForestHills", "Plains" });
        entityBiomes.put("BattleMonk", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Swampland" });
        entityBiomes.put("Merchant", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills" });
        entityBiomes.put("DesertMerchant", new String[] { "Desert" });
        entityBiomes.put("Gladiator", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills" });
        entityBiomes.put("Bandit", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Jungle", "JungleHills", "Plains", "Desert" });
        entityBiomes.put("Guard", new String[] { "Plains", "Taiga", "TaigaHills" });
        entityBiomes.put("Pirate", new String[] { "Beach", "River" });
        entityBiomes.put("Reptilian", new String[] { "Beach", "River", "Swampland", "Jungle", "JungleHills" });
        entityBiomes.put("Ranger", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Plains" });
        entityBiomes.put("Dovahkiin", new String[] { "Taiga", "TaigaHills" });
        entityBiomes.put("Assassin", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills" });
        entityBiomes.put("DarkKnight", new String[] { "Hell" });
        entityBiomes.put("DarkMerchant", new String[] { "Hell" });
        entityBiomes.put("SkeletonNinja", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Jungle", "JungleHills" });
        entityBiomes.put("SkeletonWarrior", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Jungle", "JungleHills" });
        entityBiomes.put("Viking", new String[] { "Taiga", "TaigaHills", "Ice Plains", "Ice Mountains" });
        entityBiomes.put("WarriorPrincess", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Jungle", "JungleHills" });
        entityBiomes.put("RedDragon", new String[] { "*" });
        entityBiomes.put("GoblinBomber", new String[] { "*" });
        entityBiomes.put("GoblinShaman", new String[] { "*" });
        entityBiomes.put("GoblinNinja", new String[] { "*" });
        entityBiomes.put("GoblinRanger", new String[] { "*" });
        //entityBiomes.put("Ninja", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Extreme Hills", "Extreme Hills Edge" });
        entityBiomes.put("Minotaur", new String[] { "Forest", "ForestHills", "Taiga", "TaigaHills", "Jungle", "JungleHills" });
        entityBiomes.put("Ghost", new String[] { "*" });
        entityBiomes.put("Giant", new String[] { "Plains", "Swampland" });
        entityBiomes.put("Lich", new String[] { "Taiga", "TaigaHills", "Ice Plains", "Ice Mountains" });
        entityBiomes.put("Centaur", new String[] { "Desert", "DesertHills", "Savanna" });
        entityBiomes.put("Pygmy", new String[] { "Desert", "DesertHills" });
    }

    /**
     * Loads the options from the options file.
     */
	public static void loadOptions() {
        try
        {
            if (!optionsFile.exists())
            {
            	saveOptions();
                return;
            }

            BufferedReader var1 = new BufferedReader(new FileReader(optionsFile));
            String var2 = "";

        	int entitySets = 0;
            while ((var2 = var1.readLine()) != null)
            {
                try
                {
                    if (var2.startsWith("#") && var2.replace("\n", "").startsWith("# Biomes for Entities"))
                    {
                    	++entitySets;
                    }
                    if (var2.startsWith("#") || var2.length() == 0 || !var2.contains(":")) { continue; }
                    String var3 = var2.substring(0, var2.indexOf(":")).trim();
                    String[] var4 = var2.substring(var2.indexOf(":") + 1).trim().split(":");
                    for (int i = 0; i < var4.length; ++i)
                    {
                    	var4[i] = var4[i].trim();
                    }

                    if (var3.equals("Mithril")) { Mithril = parseOption(var4[0], Mithril); }
                    else if (var3.equals("Adamantium")) { Adamantium = parseOption(var4[0], Adamantium); }
                    else if (var3.equals("Runite")) { Runite = parseOption(var4[0], Runite); }
                    else if (var3.equals("Dragonite")) { Dragonite = parseOption(var4[0], Dragonite); }
                    else if (var3.equals("Crystal")) { Crystal = parseOption(var4[0], Crystal); }
                    else if (var3.equals("Silver")) { Silver = parseOption(var4[0], Silver); }
                    else if (var3.equals("Blurite")) { Blurite = parseOption(var4[0], Blurite); }
                    else if (var3.equals("Bronze")) { Bronze = parseOption(var4[0], Bronze); }
                    else if (var3.equals("Steel")) { Steel = parseOption(var4[0], Steel); }
                    else if (var3.equals("Obsidium")) { Obsidium = parseOption(var4[0], Obsidium); }
                    else if (var3.equals("Titanium")) { Titanium = parseOption(var4[0], Titanium); }
                    else if (var3.equals("Elementium")) { Elementium = parseOption(var4[0], Elementium); }
                    else if (var3.equals("BlockID Displacement")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 300 || value > 3400) { displacedBlockID = 2500; }
                		else { displacedBlockID = value; }
                    }
                    else if (var3.equals("Base BlockID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 220) { firstBlockID = 200; }
                		else { firstBlockID = value; }
                    }
                    else if (var3.equals("Base ItemID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 400 || value > 31000) { firstItemID = 17000; }
                		else { firstItemID = value; }
                    }

                    else if (var3.equals("GreenLeavesID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 4095) { GreenLeavesID = 2791; }
                		else { GreenLeavesID = value; }
                    }
                    else if (var3.equals("AutumnLeavesID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 4095) { AutumnLeavesID = 2792; }
                		else { AutumnLeavesID = value; }
                    }
                    else if (var3.equals("SaplingID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 4095) { SaplingID = 2795; }
                		else { SaplingID = value; }
                    }
                    else if (var3.equals("CatTailID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 4095) { CatTailID = 2793; }
                		else { CatTailID = value; }
                    }
                    else if (var3.equals("FlowerID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 4095) { FlowerID = 2794; }
                		else { FlowerID = value; }
                    }
                    else if (var3.equals("LeafPileID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 4095) { LeafPileID = 2796; }
                		else { LeafPileID = value; }
                    }
                    else if (var3.equals("BlastFurnaceID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 4095) { BlastFurnaceID = 2797; }
                		else { BlastFurnaceID = value; }
                    }
                    else if (var3.equals("ExtendedCraftingTableID")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 180 || value > 4095) { ExtendedCraftingTableID = 2799; }
                		else { ExtendedCraftingTableID = value; }
                    }

                    else if (var3.equals("InfoScreen")) { InfoScreen = parseOption(var4[0], InfoScreen); }
                    //else if (var3.equals("InfoScreenPlayer")) { InfoScreenPlayer = parseOption(var4[0], InfoScreenPlayer); }
                    //else if (var3.equals("InfoScreenBiome")) { InfoScreenBiome = parseOption(var4[0], InfoScreenBiome); }
                    //else if (var3.equals("InfoScreenChunk")) { InfoScreenChunk = parseOption(var4[0], InfoScreenChunk); }
                    //else if (var3.equals("InfoScreenCoords")) { InfoScreenCoords = parseOption(var4[0], InfoScreenCoords); }
                    //else if (var3.equals("InfoScreenHeading")) { InfoScreenHeading = parseOption(var4[0], InfoScreenHeading); }
                    //else if (var3.equals("InfoScreenSkylight")) { InfoScreenSkylight = parseOption(var4[0], InfoScreenSkylight); }
                    //else if (var3.equals("InfoScreenLight")) { InfoScreenLight = parseOption(var4[0], InfoScreenLight); }
                    //else if (var3.equals("InfoScreenVelocity")) { InfoScreenVelocity = parseOption(var4[0], InfoScreenVelocity); }
                    //else if (var3.equals("InfoScreenSeed")) { InfoScreenSeed = parseOption(var4[0], InfoScreenSeed); }
                    //else if (var3.equals("InfoScreenSpawn")) { InfoScreenSpawn = parseOption(var4[0], InfoScreenSpawn); }
                    //else if (var3.equals("InfoScreenTime")) { InfoScreenTime = parseOption(var4[0], InfoScreenTime); }
                    //else if (var3.equals("InfoScreenRain")) { InfoScreenRain = parseOption(var4[0], InfoScreenRain); }
                    //else if (var3.equals("InfoScreenThunder")) { InfoScreenThunder = parseOption(var4[0], InfoScreenThunder); }
                    //else if (var3.equals("InfoScreenEquipped")) { InfoScreenEquipped = parseOption(var4[0], InfoScreenEquipped); }
                    //else if (var3.equals("InfoScreenDurability")) { InfoScreenDurability = parseOption(var4[0], InfoScreenDurability); }
                    //else if (var3.equals("InfoScreenArrows")) { InfoScreenArrows = parseOption(var4[0], InfoScreenArrows); }
                    else if (var3.equals("InfoScreenVillageInfo")) { InfoScreenVillageInfo = parseOption(var4[0], InfoScreenVillageInfo); }
                    //else if (var3.equals("InfoScreenBlockTarget")) { InfoScreenBlockTarget = parseOption(var4[0], InfoScreenBlockTarget); }
                    //else if (var3.equals("InfoScreenEntityTarget")) { InfoScreenEntityTarget = parseOption(var4[0], InfoScreenEntityTarget); }

                    else if (var3.equals("HUD")) { HUD = parseOption(var4[0], HUD); }
                    else if (var3.equals("HudBiome")) { HudBiome = parseOption(var4[0], HudBiome); }
                    else if (var3.equals("HudCoords")) { HudCoords = parseOption(var4[0], HudCoords); }
                    else if (var3.equals("HudHeading")) { HudHeading = parseOption(var4[0], HudHeading); }
                    else if (var3.equals("HudSkylight")) { HudSkylight = parseOption(var4[0], HudSkylight); }
                    else if (var3.equals("HudLight")) { HudLight = parseOption(var4[0], HudLight); }
                    else if (var3.equals("HudVelocity")) { HudVelocity = parseOption(var4[0], HudVelocity); }
                    else if (var3.equals("HudSpawn")) { HudSpawn = parseOption(var4[0], HudSpawn); }
                    else if (var3.equals("HudTime")) { HudTime = parseOption(var4[0], HudTime); }
                    else if (var3.equals("HudRain")) { HudRain = parseOption(var4[0], HudRain); }
                    else if (var3.equals("HudThunder")) { HudThunder = parseOption(var4[0], HudThunder); }
                    else if (var3.equals("HudEquipped")) { HudEquipped = parseOption(var4[0], HudEquipped); }
                    else if (var3.equals("HudDurability")) { HudDurability = parseOption(var4[0], HudDurability); }
                    else if (var3.equals("HudArrows")) { HudArrows = parseOption(var4[0], HudArrows); }
                    else if (var3.equals("HudBlockTarget")) { HudBlockTarget = parseOption(var4[0], HudBlockTarget); }
                    else if (var3.equals("HudEntityTarget")) { HudEntityTarget = parseOption(var4[0], HudEntityTarget); }
                    else if (var3.equals("HudCoordsType")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 0 || value > 2) { HudCoordsType = 0; }
                		else { HudCoordsType = value; }
                    }

                    else if (var3.equals("HideAchievements")) { HideAchievements = parseOption(var4[0], HideAchievements); }
                    else if (var3.equals("ItemStackCount")) { ItemStackCount = parseOption(var4[0], ItemStackCount); }
                    else if (var3.equals("ItemDamageCount")) { ItemDamageCount = parseOption(var4[0], ItemDamageCount); }
                    else if (var3.equals("PowerOverlay")) { PowerOverlay = parseOption(var4[0], PowerOverlay); }
                    else if (var3.equals("PowerTexture")) { PowerTexture = parseOption(var4[0], PowerTexture); }
                    else if (var3.equals("HudKey")) {
                        if (Keyboard.getKeyIndex(var4[0]) < 1 || Keyboard.getKeyIndex(var4[0]) > 88) { HudKey = Keyboard.KEY_F6; }
                		else { HudKey = Keyboard.getKeyIndex(var4[0]); }
                    }
                    else if (var3.equals("InfoKey")) {
                        if (Keyboard.getKeyIndex(var4[0]) < 1 || Keyboard.getKeyIndex(var4[0]) > 88 || Keyboard.getKeyIndex(var4[0]) == HudKey) { InfoKey = Keyboard.KEY_F4; }
                		else { InfoKey = Keyboard.getKeyIndex(var4[0]); }
                    }
                    else if (var3.equals("ArrowsMovement")) { ArrowsMovement = parseOption(var4[0], ArrowsMovement); }
                    else if (var3.equals("ArrowsTurnrate")) {
                    	int value = Integer.parseInt(var4[0]);
                        if (value < 5) { ArrowsTurnrate = 5; }
                        else if (value > 60) { ArrowsTurnrate = 60; }
                		else { ArrowsTurnrate = value; }
                    }

                    else if (var3.equals("Mobs")) { Mobs = parseOption(var4[0], Mobs); }
                    else if (var3.equals("Biomes")) { Biomes = parseOption(var4[0], Biomes); }
                    else if (var3.equals("Items")) { Items = parseOption(var4[0], Items); }
                    else if (var3.equals("NPCs")) { NPCs = parseOption(var4[0], NPCs); }
                    else if (var3.equals("World")) { World = parseOption(var4[0], World); }

                    else if (var3.equals("DesertChance")) { biomeGeneratorSettings.put("Desert", Integer.parseInt(var4[0])); }
                    else if (var3.equals("ForestChance")) { biomeGeneratorSettings.put("Forest", Integer.parseInt(var4[0])); }
                    else if (var3.equals("ExtremeHillsChance")) { biomeGeneratorSettings.put("ExtremeHills", Integer.parseInt(var4[0])); }
                    else if (var3.equals("SwamplandChance")) { biomeGeneratorSettings.put("Swampland", Integer.parseInt(var4[0])); }
                    else if (var3.equals("PlainsChance")) { biomeGeneratorSettings.put("Plains", Integer.parseInt(var4[0])); }
                    else if (var3.equals("TaigaChance")) { biomeGeneratorSettings.put("Taiga", Integer.parseInt(var4[0])); }
                    else if (var3.equals("JungleChance")) { biomeGeneratorSettings.put("Jungle", Integer.parseInt(var4[0])); }

                    else if (var3.equals("SavannaChance")) { biomeGeneratorSettings.put("Savanna", Integer.parseInt(var4[0])); }
                    else if (var3.equals("RainforestChance")) { biomeGeneratorSettings.put("Rainforest", Integer.parseInt(var4[0])); }
                    else if (var3.equals("ShrublandChance")) { biomeGeneratorSettings.put("Shrubland", Integer.parseInt(var4[0])); }
                    else if (var3.equals("SeasonalForestChance")) { biomeGeneratorSettings.put("SeasonalForest", Integer.parseInt(var4[0])); }
                    else if (var3.equals("WoodlandsChance")) { biomeGeneratorSettings.put("Woodlands", Integer.parseInt(var4[0])); }
                    else if (var3.equals("GreenHillsChance")) { biomeGeneratorSettings.put("GreenHills", Integer.parseInt(var4[0])); }
                    else if (var3.equals("PineForestChance")) { biomeGeneratorSettings.put("PineForest", Integer.parseInt(var4[0])); }
                    else if (var3.equals("GreenSwamplandsChance")) { biomeGeneratorSettings.put("GreenSwamplands", Integer.parseInt(var4[0])); }
                    else if (var3.equals("RedrockChance")) { biomeGeneratorSettings.put("Redrock", Integer.parseInt(var4[0])); }
                    else if (var3.equals("TundraChance")) { biomeGeneratorSettings.put("Tundra", Integer.parseInt(var4[0])); }
                    else if (var3.endsWith("Chance"))
                    {
                        String var5 = var3.substring(0, var2.indexOf("Chance"));
                        if (var5.length() != 0)
                        {
                        	biomeGeneratorSettings.put(var5, Integer.parseInt(var4[0]));
                        }
                    }

                    else if (var3.equals("SlopeScale")) { SlopeScale = Double.parseDouble(var4[0]); }
                    else if (var3.equals("HeightScale")) { HeightScale = Double.parseDouble(var4[0]); }
                    else if (var3.equals("GenCavesRarity")) { GenCavesRarity = Float.parseFloat(var4[0]); }
                    else if (var3.equals("GenRavineRarity")) { GenRavineRarity = Float.parseFloat(var4[0]); }
                    else if (var3.equals("GenDungeons")) { GenDungeons = Integer.parseInt(var4[0]); }
                    else if (var3.equals("GenWaterInDesert")) { GenWaterInDesert = Float.parseFloat(var4[0]); }
                    else if (var3.equals("MultiLayerOreGeneration")) { MultiLayerOreGeneration = parseOption(var4[0], MultiLayerOreGeneration); }

                    else if (var3.equals("TimerScale"))
                    {
                    	TimerScale = Integer.parseInt(var4[0]);
                    	if (TimerScale > 72) { TimerScale = 72; }
                        else if (TimerScale < 0) { TimerScale = 1; }
                    }

                    if (entitySets == 0)
                    {
                        if (var3.equals("DwarfFemale")) { entitySettings.put("DwarfFemale", parseEntityOption(var4[0], "DwarfFemale")); }
                        else if (var3.equals("DwarfMale")) { entitySettings.put("DwarfMale", parseEntityOption(var4[0], "DwarfMale")); }
                        else if (var3.equals("ElfFemale")) { entitySettings.put("ElfFemale", parseEntityOption(var4[0], "ElfFemale")); }
                        else if (var3.equals("ElfMale")) { entitySettings.put("ElfMale", parseEntityOption(var4[0], "ElfMale")); }
                        else if (var3.equals("HumanFemale")) { entitySettings.put("HumanFemale", parseEntityOption(var4[0], "HumanFemale")); }
                        //else if (var3.equals("HumanMale")) { entitySettings.put("HumanMale", parseEntityOption(var4[0], "HumanMale")); }
                        //else if (var3.equals("Ogre2")) { entitySettings.put("Ogre2", parseEntityOption(var4[0], "Ogre2")); }
                        else if (var3.equals("NagaFemale")) { entitySettings.put("NagaFemale", parseEntityOption(var4[0], "NagaFemale")); }
                        else if (var3.equals("NagaMale")) { entitySettings.put("NagaMale", parseEntityOption(var4[0], "NagaMale")); }

                        else if (var3.equals("Dwarf")) { entitySettings.put("Dwarf", parseEntityOption(var4[0], "Dwarf")); }
                        else if (var3.equals("Elf")) { entitySettings.put("Elf", parseEntityOption(var4[0], "Elf")); }
                        else if (var3.equals("Ogre")) { entitySettings.put("Ogre", parseEntityOption(var4[0], "Ogre")); }
                        else if (var3.equals("OrcFemale")) { entitySettings.put("OrcFemale", parseEntityOption(var4[0], "OrcFemale")); }
                        else if (var3.equals("OrcMale")) { entitySettings.put("OrcMale", parseEntityOption(var4[0], "OrcMale")); }
                        else if (var3.equals("Crusader")) { entitySettings.put("Crusader", parseEntityOption(var4[0], "Crusader")); }
                        else if (var3.equals("Goblin")) { entitySettings.put("Goblin", parseEntityOption(var4[0], "Goblin")); }
                        else if (var3.equals("Samurai")) { entitySettings.put("Samurai", parseEntityOption(var4[0], "Samurai")); }
                        else if (var3.equals("BattleMonk")) { entitySettings.put("BattleMonk", parseEntityOption(var4[0], "BattleMonk")); }
                        else if (var3.equals("Merchant")) { entitySettings.put("Merchant", parseEntityOption(var4[0], "Merchant")); }
                        else if (var3.equals("DesertMerchant")) { entitySettings.put("DesertMerchant", parseEntityOption(var4[0], "DesertMerchant")); }
                        else if (var3.equals("Gladiator")) { entitySettings.put("Gladiator", parseEntityOption(var4[0], "Gladiator")); }
                        else if (var3.equals("Bandit")) { entitySettings.put("Bandit", parseEntityOption(var4[0], "Bandit")); }
                        else if (var3.equals("Guard")) { entitySettings.put("Guard", parseEntityOption(var4[0], "Guard")); }
                        else if (var3.equals("Pirate")) { entitySettings.put("Pirate", parseEntityOption(var4[0], "Pirate")); }
                        else if (var3.equals("Reptilian")) { entitySettings.put("Reptilian", parseEntityOption(var4[0], "Reptilian")); }
                        else if (var3.equals("Ranger")) { entitySettings.put("Ranger", parseEntityOption(var4[0], "Ranger")); }
                        else if (var3.equals("Dovahkiin")) { entitySettings.put("Dovahkiin", parseEntityOption(var4[0], "Dovahkiin")); }
                        else if (var3.equals("Assassin")) { entitySettings.put("Assassin", parseEntityOption(var4[0], "Assassin")); }
                        else if (var3.equals("DarkKnight")) { entitySettings.put("DarkKnight", parseEntityOption(var4[0], "DarkKnight")); }
                        else if (var3.equals("DarkMerchant")) { entitySettings.put("DarkMerchant", parseEntityOption(var4[0], "DarkMerchant")); }
                        else if (var3.equals("SkeletonNinja")) { entitySettings.put("SkeletonNinja", parseEntityOption(var4[0], "SkeletonNinja")); }
                        else if (var3.equals("SkeletonWarrior")) { entitySettings.put("SkeletonWarrior", parseEntityOption(var4[0], "SkeletonWarrior")); }
                        else if (var3.equals("Viking")) { entitySettings.put("Viking", parseEntityOption(var4[0], "Viking")); }
                        else if (var3.equals("WarriorPrincess")) { entitySettings.put("WarriorPrincess", parseEntityOption(var4[0], "WarriorPrincess")); }
                        else if (var3.equals("RedDragon")) { entitySettings.put("RedDragon", parseEntityOption(var4[0], "RedDragon")); }
                        else if (var3.equals("GoblinBomber")) { entitySettings.put("GoblinBomber", parseEntityOption(var4[0], "GoblinBomber")); }
                        else if (var3.equals("GoblinShaman")) { entitySettings.put("GoblinShaman", parseEntityOption(var4[0], "GoblinShaman")); }
                        else if (var3.equals("GoblinNinja")) { entitySettings.put("GoblinNinja", parseEntityOption(var4[0], "GoblinNinja")); }
                        else if (var3.equals("GoblinRanger")) { entitySettings.put("GoblinRanger", parseEntityOption(var4[0], "GoblinRanger")); }
                        //else if (var3.equals("Ninja")) { entitySettings.put("Ninja", parseEntityOption(var4[0], "Ninja")); }
                        else if (var3.equals("Minotaur")) { entitySettings.put("Minotaur", parseEntityOption(var4[0], "Minotaur")); }
                        else if (var3.equals("Ghost")) { entitySettings.put("Ghost", parseEntityOption(var4[0], "Ghost")); }
                        else if (var3.equals("Giant")) { entitySettings.put("Giant", parseEntityOption(var4[0], "Giant")); }
                        else if (var3.equals("Lich")) { entitySettings.put("Lich", parseEntityOption(var4[0], "Lich")); }
                        else if (var3.equals("Centaur")) { entitySettings.put("Centaur", parseEntityOption(var4[0], "Centaur")); }
                        else if (var3.equals("Pygmy")) { entitySettings.put("Pygmy", parseEntityOption(var4[0], "Pygmy")); }
                    }
            		else
            		{
                        if (var3.equals("DwarfFemale")) { entityBiomes.put("DwarfFemale", parseBiomesOption(var4[0], "DwarfFemale")); }
                        else if (var3.equals("DwarfMale")) { entityBiomes.put("DwarfMale", parseBiomesOption(var4[0], "DwarfMale")); }
                        else if (var3.equals("ElfFemale")) { entityBiomes.put("ElfFemale", parseBiomesOption(var4[0], "ElfFemale")); }
                        else if (var3.equals("ElfMale")) { entityBiomes.put("ElfMale", parseBiomesOption(var4[0], "ElfMale")); }
                        else if (var3.equals("HumanFemale")) { entityBiomes.put("HumanFemale", parseBiomesOption(var4[0], "HumanFemale")); }
                        //else if (var3.equals("HumanMale")) { entityBiomes.put("HumanMale", parseBiomesOption(var4[0], "HumanMale")); }
                        //else if (var3.equals("Ogre2")) { entityBiomes.put("Ogre2", parseBiomesOption(var4[0], "Ogre2")); }
                        else if (var3.equals("NagaFemale")) { entityBiomes.put("NagaFemale", parseBiomesOption(var4[0], "NagaFemale")); }
                        else if (var3.equals("NagaMale")) { entityBiomes.put("NagaMale", parseBiomesOption(var4[0], "NagaMale")); }

            			else if (var3.equals("Dwarf")) { entityBiomes.put("Dwarf", parseBiomesOption(var4[0], "Dwarf")); }
            			else if (var3.equals("Elf")) { entityBiomes.put("Elf", parseBiomesOption(var4[0], "Elf")); }
            			else if (var3.equals("Ogre")) { entityBiomes.put("Ogre", parseBiomesOption(var4[0], "Ogre")); }
            			else if (var3.equals("OrcFemale")) { entityBiomes.put("OrcFemale", parseBiomesOption(var4[0], "OrcFemale")); }
            			else if (var3.equals("OrcMale")) { entityBiomes.put("OrcMale", parseBiomesOption(var4[0], "OrcMale")); }
            			else if (var3.equals("Crusader")) { entityBiomes.put("Crusader", parseBiomesOption(var4[0], "Crusader")); }
            			else if (var3.equals("Goblin")) { entityBiomes.put("Goblin", parseBiomesOption(var4[0], "Goblin")); }
            			else if (var3.equals("Samurai")) { entityBiomes.put("Samurai", parseBiomesOption(var4[0], "Samurai")); }
            			else if (var3.equals("BattleMonk")) { entityBiomes.put("BattleMonk", parseBiomesOption(var4[0], "BattleMonk")); }
            			else if (var3.equals("Merchant")) { entityBiomes.put("Merchant", parseBiomesOption(var4[0], "Merchant")); }
            			else if (var3.equals("DesertMerchant")) { entityBiomes.put("DesertMerchant", parseBiomesOption(var4[0], "DesertMerchant")); }
            			else if (var3.equals("Gladiator")) { entityBiomes.put("Gladiator", parseBiomesOption(var4[0], "Gladiator")); }
            			else if (var3.equals("Bandit")) { entityBiomes.put("Bandit", parseBiomesOption(var4[0], "Bandit")); }
            			else if (var3.equals("Guard")) { entityBiomes.put("Guard", parseBiomesOption(var4[0], "Guard")); }
            			else if (var3.equals("Pirate")) { entityBiomes.put("Pirate", parseBiomesOption(var4[0], "Pirate")); }
            			else if (var3.equals("Reptilian")) { entityBiomes.put("Reptilian", parseBiomesOption(var4[0], "Reptilian")); }
            			else if (var3.equals("Ranger")) { entityBiomes.put("Ranger", parseBiomesOption(var4[0], "Ranger")); }
            			else if (var3.equals("Dovahkiin")) { entityBiomes.put("Dovahkiin", parseBiomesOption(var4[0], "Dovahkiin")); }
            			else if (var3.equals("Assassin")) { entityBiomes.put("Assassin", parseBiomesOption(var4[0], "Assassin")); }
            			else if (var3.equals("DarkKnight")) { entityBiomes.put("DarkKnight", parseBiomesOption(var4[0], "DarkKnight")); }
            			else if (var3.equals("DarkMerchant")) { entityBiomes.put("DarkMerchant", parseBiomesOption(var4[0], "DarkMerchant")); }
            			else if (var3.equals("SkeletonNinja")) { entityBiomes.put("SkeletonNinja", parseBiomesOption(var4[0], "SkeletonNinja")); }
            			else if (var3.equals("SkeletonWarrior")) { entityBiomes.put("SkeletonWarrior", parseBiomesOption(var4[0], "SkeletonWarrior")); }
            			else if (var3.equals("Viking")) { entityBiomes.put("Viking", parseBiomesOption(var4[0], "Viking")); }
            			else if (var3.equals("WarriorPrincess")) { entityBiomes.put("WarriorPrincess", parseBiomesOption(var4[0], "WarriorPrincess")); }
            			else if (var3.equals("RedDragon")) { entityBiomes.put("RedDragon", parseBiomesOption(var4[0], "RedDragon")); }
            			else if (var3.equals("GoblinBomber")) { entityBiomes.put("GoblinBomber", parseBiomesOption(var4[0], "GoblinBomber")); }
            			else if (var3.equals("GoblinShaman")) { entityBiomes.put("GoblinShaman", parseBiomesOption(var4[0], "GoblinShaman")); }
            			else if (var3.equals("GoblinNinja")) { entityBiomes.put("GoblinNinja", parseBiomesOption(var4[0], "GoblinNinja")); }
            			else if (var3.equals("GoblinRanger")) { entityBiomes.put("GoblinRanger", parseBiomesOption(var4[0], "GoblinRanger")); }
            			//else if (var3.equals("Ninja")) { entityBiomes.put("Ninja", parseBiomesOption(var4[0], "Ninja")); }
            			else if (var3.equals("Minotaur")) { entityBiomes.put("Minotaur", parseBiomesOption(var4[0], "Minotaur")); }
            			else if (var3.equals("Ghost")) { entityBiomes.put("Ghost", parseBiomesOption(var4[0], "Ghost")); }
            			else if (var3.equals("Giant")) { entityBiomes.put("Giant", parseBiomesOption(var4[0], "Giant")); }
            			else if (var3.equals("Lich")) { entityBiomes.put("Lich", parseBiomesOption(var4[0], "Lich")); }
            			else if (var3.equals("Centaur")) { entityBiomes.put("Centaur", parseBiomesOption(var4[0], "Centaur")); }
            			else if (var3.equals("Pygmy")) { entityBiomes.put("Pygmy", parseBiomesOption(var4[0], "Pygmy")); }
            		}
                }
                catch (Exception var8)
                {
                    System.out.println("Skipping bad option: " + var2);
                    var8.printStackTrace();
                }
            }
            var1.close();

            verifyBiomeChances();
            saveOptions();
        }
        catch (Exception var9)
        {
            System.out.println("MinecraftEnhanced: Failed to load options");
            var9.printStackTrace();
        }
	}
	private static String parseOption(String value, String defaultValue)
	{
		if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("no"))
		{
			return value.toLowerCase();
		}
		else
		{
			return defaultValue;
		}
	}

	private static void verifyBiomeChances()
	{
    	for (Map.Entry<String, Integer> entry : biomeGeneratorSettings.entrySet())
    	{
    		if (entry.getValue() < 0)
    		{
    			entry.setValue(0);
    		}
    	}

        if (SlopeScale < 0.0D) { SlopeScale = 0.0D; }
        if (HeightScale < 0.0D) { HeightScale = 0.0D; }
        if (GenCavesRarity < 0.0F) { GenCavesRarity = 0.0F; }
        if (GenRavineRarity < 0.0F) { GenRavineRarity = 0.0F; }
        if (GenDungeons < 0) { GenDungeons = 0; }
        if (GenWaterInDesert < 0.0F) { GenWaterInDesert = 0.0F; }
	}

	private static String[] parseBiomesOption(String value, String entity)
	{
    	if (entity == null || entity.length() == 0) { return new String[] {}; }
        try
        {
        	String[] newValue = value.replace("[", "").replace("]", "").split(",");
        	for (int i = 0; i < newValue.length; ++i)
        	{
        		newValue[i] = newValue[i].trim();
        	}

        	List<String> biomeList = new ArrayList<String>(Arrays.asList(newValue));
        	for (int i = 0; i < biomeList.size(); ++i)
        	{
        		if (biomeList.get(i) == null || biomeList.get(i).length() == 0)
        		{
        			biomeList.remove(i);
        		}
        	}
        	return biomeList.toArray(new String[biomeList.size()]);
    	}
        catch (Exception var1)
        {
    		return entityBiomes.get(entity);
        }
	}

	private static int[] parseEntityOption(String value, String entity)
	{
    	if (entity == null || entity.length() == 0) { return new int[] { 0, 0, 0 }; }
        try
        {
        	String[] newValue = value.replace("[", "").replace("]", "").split(",");
        	int weight = Integer.parseInt(newValue[0].trim());
        	int minGroupCount = Integer.parseInt(newValue[1].trim());
        	int maxGroupCount = Integer.parseInt(newValue[2].trim());

        	if (weight < 0 || weight > 100) { weight = 0; }
        	if (minGroupCount < 0 || minGroupCount > 10) { minGroupCount = 0; }
        	if (maxGroupCount < 0 || maxGroupCount > 10) { maxGroupCount = 0; }
    		
    		return new int[] { weight, minGroupCount, maxGroupCount };
    	}
        catch (Exception var1)
        {
    		return entitySettings.get(entity);
        }
	}

	private static String toDataString(int[] data)
	{
		return "[" + data[0] + ", " + data[1] + ", " + data[2] + "]";
	}

	private static String biomesToDataString(String[] data)
	{
        String value = "[";
        for (int i = 0; i < data.length; ++i)
        {
    		if (i == 0)
    		{
            	value += data[i];
    		}
    		else
    		{
            	value += ", " + data[i];
    		}
    	}
	    return value + "]";
	}

	public static boolean isEnabled(String value)
	{
		return value.equalsIgnoreCase("yes") ? true : false;
	}

	/**
     * Saves the options to the options file.
     */
	public static void saveOptions()
    {
        System.out.println("MC saveOptions...");
        try
        {
            PrintWriter var1 = new PrintWriter(new FileWriter(optionsFile));
            var1.println("# MinecraftEnhanced 1.5.1 - Settings");
            var1.println("# " + (new SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss z")).format(new Date()));
            var1.println("\n# Item Types");
            var1.println("Mithril: " + Mithril);
            var1.println("Adamantium: " + Adamantium);
            var1.println("Runite: " + Runite);
            var1.println("Dragonite: " + Dragonite);
            var1.println("Crystal: " + Crystal);
            var1.println("Silver: " + Silver);
            var1.println("Blurite: " + Blurite);
            var1.println("Bronze: " + Bronze);
            var1.println("Steel: " + Steel);
            var1.println("Obsidium: " + Obsidium);
            var1.println("Titanium: " + Titanium);
            var1.println("Elementium: " + Elementium);

            var1.println("\n# ID Settings");
            var1.println("BlockID Displacement: " + displacedBlockID);
            var1.println("Base BlockID: " + firstBlockID);
            var1.println("Base ItemID: " + firstItemID);

            var1.println("GreenLeavesID: " + GreenLeavesID);
            var1.println("AutumnLeavesID: " + AutumnLeavesID);
            var1.println("SaplingID: " + SaplingID);
            var1.println("CatTailID: " + CatTailID);
            var1.println("FlowerID: " + FlowerID);
            var1.println("LeafPileID: " + LeafPileID);
            var1.println("BlastFurnaceID: " + BlastFurnaceID);
            var1.println("ExtendedCraftingTableID: " + ExtendedCraftingTableID);

            var1.println("\n# Info Settings");
            var1.println("InfoScreen: " + InfoScreen);
            //var1.println("InfoScreenPlayer: " + InfoScreenPlayer);
            //var1.println("InfoScreenBiome: " + InfoScreenBiome);
            //var1.println("InfoScreenChunk: " + InfoScreenChunk);
            //var1.println("InfoScreenCoords: " + InfoScreenCoords);
            //var1.println("InfoScreenHeading: " + InfoScreenHeading);
            //var1.println("InfoScreenSkylight: " + InfoScreenSkylight);
            //var1.println("InfoScreenLight: " + InfoScreenLight);
            //var1.println("InfoScreenVelocity: " + InfoScreenVelocity);
            //var1.println("InfoScreenSeed: " + InfoScreenSeed);
            //var1.println("InfoScreenSpawn: " + InfoScreenSpawn);
            //var1.println("InfoScreenTime: " + InfoScreenTime);
            //var1.println("InfoScreenRain: " + InfoScreenRain);
            //var1.println("InfoScreenThunder: " + InfoScreenThunder);
            //var1.println("InfoScreenEquipped: " + InfoScreenEquipped);
            //var1.println("InfoScreenDurability: " + InfoScreenDurability);
            //var1.println("InfoScreenArrows: " + InfoScreenArrows);
            var1.println("InfoScreenVillageInfo: " + InfoScreenVillageInfo);
            //var1.println("InfoScreenBlockTarget: " + InfoScreenBlockTarget);
            //var1.println("InfoScreenEntityTarget: " + InfoScreenEntityTarget);

            var1.println("\n# HUD Settings");
            var1.println("HUD: " + HUD);
            var1.println("HudBiome: " + HudBiome);
            var1.println("HudCoords: " + HudCoords);
            var1.println("HudHeading: " + HudHeading);
            var1.println("HudSkylight: " + HudSkylight);
            var1.println("HudLight: " + HudLight);
            var1.println("HudVelocity: " + HudVelocity);
            var1.println("HudSpawn: " + HudSpawn);
            var1.println("HudTime: " + HudTime);
            var1.println("HudRain: " + HudRain);
            var1.println("HudThunder: " + HudThunder);
            var1.println("HudEquipped: " + HudEquipped);
            var1.println("HudDurability: " + HudDurability);
            var1.println("HudArrows: " + HudArrows);
            var1.println("HudBlockTarget: " + HudBlockTarget);
            var1.println("HudEntityTarget: " + HudEntityTarget);
            var1.println("HudCoordsType: " + HudCoordsType);

            var1.println("\n# Misc Settings");
            var1.println("HideAchievements: " + HideAchievements);
            var1.println("ItemStackCount: " + ItemStackCount);
            var1.println("ItemDamageCount: " + ItemDamageCount);
            var1.println("PowerOverlay: " + PowerOverlay);
            var1.println("PowerTexture: " + PowerTexture);
            var1.println("HudKey: " + Keyboard.getKeyName(HudKey));
            var1.println("InfoKey: " + Keyboard.getKeyName(InfoKey));
            var1.println("ArrowsMovement: " + ArrowsMovement);
            var1.println("ArrowsTurnrate: " + ArrowsTurnrate);
            var1.println("TimerScale: " + TimerScale);

            var1.println("\n# Modules");
            var1.println("Mobs: " + Mobs);
            var1.println("Biomes: " + Biomes);
            var1.println("Items: " + Items);
            var1.println("NPCs: " + NPCs);
            var1.println("World: " + World);

            var1.println("\n# WorldGenerator");
            for (Map.Entry<String, Integer> entry : biomeGeneratorSettings.entrySet())
            {
                System.out.println("MC... " + entry.getKey() + ", " + entry.getValue());
            	if (entry.getValue() >= 0)
            	{
            		var1.println(entry.getKey() + "Chance: " + entry.getValue());
            	}
            	if (entry.getKey() == "Jungle")
            	{
                    var1.println("");
            	}
            }

            var1.println("");
            var1.println("SlopeScale: " + SlopeScale);
            var1.println("HeightScale: " + HeightScale);
            var1.println("GenCavesRarity: " + GenCavesRarity);
            var1.println("GenRavineRarity: " + GenRavineRarity);
            var1.println("GenDungeons: " + GenDungeons);
            var1.println("GenWaterInDesert: " + GenWaterInDesert);
            var1.println("MultiLayerOreGeneration: " + MultiLayerOreGeneration);

            var1.println("\n# Entities");
            var1.println("DwarfFemale: " + toDataString(entitySettings.get("DwarfFemale")));
            var1.println("DwarfMale: " + toDataString(entitySettings.get("DwarfMale")));
            var1.println("ElfFemale: " + toDataString(entitySettings.get("ElfFemale")));
            var1.println("ElfMale: " + toDataString(entitySettings.get("ElfMale")));
            var1.println("HumanFemale: " + toDataString(entitySettings.get("HumanFemale")));
            //var1.println("HumanMale: " + toDataString(entitySettings.get("HumanMale")));
            //var1.println("Ogre2: " + toDataString(entitySettings.get("Ogre2")));
            var1.println("NagaFemale: " + toDataString(entitySettings.get("NagaFemale")));
            var1.println("NagaMale: " + toDataString(entitySettings.get("NagaMale")));
            var1.println("");
            var1.println("Dwarf: " + toDataString(entitySettings.get("Dwarf")));
            var1.println("Elf: " + toDataString(entitySettings.get("Elf")));
            var1.println("Ogre: " + toDataString(entitySettings.get("Ogre")));
            var1.println("OrcFemale: " + toDataString(entitySettings.get("OrcFemale")));
            var1.println("OrcMale: " + toDataString(entitySettings.get("OrcMale")));
            var1.println("Crusader: " + toDataString(entitySettings.get("Crusader")));
            var1.println("Goblin: " + toDataString(entitySettings.get("Goblin")));
            var1.println("Samurai: " + toDataString(entitySettings.get("Samurai")));
            var1.println("BattleMonk: " + toDataString(entitySettings.get("BattleMonk")));
            var1.println("Merchant: " + toDataString(entitySettings.get("Merchant")));
            var1.println("DesertMerchant: " + toDataString(entitySettings.get("DesertMerchant")));
            var1.println("Gladiator: " + toDataString(entitySettings.get("Gladiator")));
            var1.println("Bandit: " + toDataString(entitySettings.get("Bandit")));
            var1.println("Guard: " + toDataString(entitySettings.get("Guard")));
            var1.println("Pirate: " + toDataString(entitySettings.get("Pirate")));
            var1.println("Reptilian: " + toDataString(entitySettings.get("Reptilian")));
            var1.println("Ranger: " + toDataString(entitySettings.get("Ranger")));
            var1.println("Dovahkiin: " + toDataString(entitySettings.get("Dovahkiin")));
            var1.println("Assassin: " + toDataString(entitySettings.get("Assassin")));
            var1.println("DarkKnight: " + toDataString(entitySettings.get("DarkKnight")));
            var1.println("DarkMerchant: " + toDataString(entitySettings.get("DarkMerchant")));
            var1.println("SkeletonNinja: " + toDataString(entitySettings.get("SkeletonNinja")));
            var1.println("SkeletonWarrior: " + toDataString(entitySettings.get("SkeletonWarrior")));
            var1.println("Viking: " + toDataString(entitySettings.get("Viking")));
            var1.println("WarriorPrincess: " + toDataString(entitySettings.get("WarriorPrincess")));
            var1.println("RedDragon: " + toDataString(entitySettings.get("RedDragon")));
            var1.println("GoblinBomber: " + toDataString(entitySettings.get("GoblinBomber")));
            var1.println("GoblinShaman: " + toDataString(entitySettings.get("GoblinShaman")));
            var1.println("GoblinNinja: " + toDataString(entitySettings.get("GoblinNinja")));
            var1.println("GoblinRanger: " + toDataString(entitySettings.get("GoblinRanger")));
            //var1.println("Ninja: " + toDataString(entitySettings.get("Ninja")));
            var1.println("Minotaur: " + toDataString(entitySettings.get("Minotaur")));
            var1.println("Ghost: " + toDataString(entitySettings.get("Ghost")));
            var1.println("Giant: " + toDataString(entitySettings.get("Giant")));
            var1.println("Lich: " + toDataString(entitySettings.get("Lich")));
            var1.println("Centaur: " + toDataString(entitySettings.get("Centaur")));
            var1.println("Pygmy: " + toDataString(entitySettings.get("Pygmy")));

            var1.println("\n# Biomes for Entities");
            var1.println("DwarfFemale: " + biomesToDataString(entityBiomes.get("DwarfFemale")));
            var1.println("DwarfMale: " + biomesToDataString(entityBiomes.get("DwarfMale")));
            var1.println("ElfFemale: " + biomesToDataString(entityBiomes.get("ElfFemale")));
            var1.println("ElfMale: " + biomesToDataString(entityBiomes.get("ElfMale")));
            var1.println("HumanFemale: " + biomesToDataString(entityBiomes.get("HumanFemale")));
            //var1.println("HumanMale: " + biomesToDataString(entityBiomes.get("HumanMale")));
            //var1.println("Ogre2: " + biomesToDataString(entityBiomes.get("Ogre2")));
            var1.println("NagaFemale: " + biomesToDataString(entityBiomes.get("NagaFemale")));
            var1.println("NagaMale: " + biomesToDataString(entityBiomes.get("NagaMale")));
            var1.println("");
            var1.println("Dwarf: " + biomesToDataString(entityBiomes.get("Dwarf")));
            var1.println("Elf: " + biomesToDataString(entityBiomes.get("Elf")));
            var1.println("Ogre: " + biomesToDataString(entityBiomes.get("Ogre")));
            var1.println("OrcFemale: " + biomesToDataString(entityBiomes.get("OrcFemale")));
            var1.println("OrcMale: " + biomesToDataString(entityBiomes.get("OrcMale")));
            var1.println("Crusader: " + biomesToDataString(entityBiomes.get("Crusader")));
            var1.println("Goblin: " + biomesToDataString(entityBiomes.get("Goblin")));
            var1.println("Samurai: " + biomesToDataString(entityBiomes.get("Samurai")));
            var1.println("BattleMonk: " + biomesToDataString(entityBiomes.get("BattleMonk")));
            var1.println("Merchant: " + biomesToDataString(entityBiomes.get("Merchant")));
            var1.println("DesertMerchant: " + biomesToDataString(entityBiomes.get("DesertMerchant")));
            var1.println("Gladiator: " + biomesToDataString(entityBiomes.get("Gladiator")));
            var1.println("Bandit: " + biomesToDataString(entityBiomes.get("Bandit")));
            var1.println("Guard: " + biomesToDataString(entityBiomes.get("Guard")));
            var1.println("Pirate: " + biomesToDataString(entityBiomes.get("Pirate")));
            var1.println("Reptilian: " + biomesToDataString(entityBiomes.get("Reptilian")));
            var1.println("Ranger: " + biomesToDataString(entityBiomes.get("Ranger")));
            var1.println("Dovahkiin: " + biomesToDataString(entityBiomes.get("Dovahkiin")));
            var1.println("Assassin: " + biomesToDataString(entityBiomes.get("Assassin")));
            var1.println("DarkKnight: " + biomesToDataString(entityBiomes.get("DarkKnight")));
            var1.println("DarkMerchant: " + biomesToDataString(entityBiomes.get("DarkMerchant")));
            var1.println("SkeletonNinja: " + biomesToDataString(entityBiomes.get("SkeletonNinja")));
            var1.println("SkeletonWarrior: " + biomesToDataString(entityBiomes.get("SkeletonWarrior")));
            var1.println("Viking: " + biomesToDataString(entityBiomes.get("Viking")));
            var1.println("WarriorPrincess: " + biomesToDataString(entityBiomes.get("WarriorPrincess")));
            var1.println("RedDragon: " + biomesToDataString(entityBiomes.get("RedDragon")));
            var1.println("GoblinBomber: " + biomesToDataString(entityBiomes.get("GoblinBomber")));
            var1.println("GoblinShaman: " + biomesToDataString(entityBiomes.get("GoblinShaman")));
            var1.println("GoblinNinja: " + biomesToDataString(entityBiomes.get("GoblinNinja")));
            var1.println("GoblinRanger: " + biomesToDataString(entityBiomes.get("GoblinRanger")));
            //var1.println("Ninja: " + biomesToDataString(entityBiomes.get("Ninja")));
            var1.println("Minotaur: " + biomesToDataString(entityBiomes.get("Minotaur")));
            var1.println("Ghost: " + biomesToDataString(entityBiomes.get("Ghost")));
            var1.println("Giant: " + biomesToDataString(entityBiomes.get("Giant")));
            var1.println("Lich: " + biomesToDataString(entityBiomes.get("Lich")));
            var1.println("Centaur: " + biomesToDataString(entityBiomes.get("Centaur")));
            var1.println("Pygmy: " + biomesToDataString(entityBiomes.get("Pygmy")));

            var1.close();
        }
        catch (Exception var6)
        {
            System.out.println("MinecraftEnhanced: Failed to save options");
            var6.printStackTrace();
        }
    }
}
