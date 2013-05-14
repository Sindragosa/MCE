package sedridor.mce.biomes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.biome.BiomeGenBase;
import sedridor.mce.MCE_Settings;

//Savanna:			23
//Rainforest:		24
//Shrubland:		25
//Seasonal Forest:	26
//Tundra:			27
//Temperate Forest:	28
//Green Hills:		29
//Pine Forest:		30
//Green Swamp:		31
//Red Desert:		32
//Mountains:		33
//Redwood Forest:	34

public class BiomeGenBaseMCE extends BiomeGenBase
{
	public static final BiomeGenBase savanna = (new BiomeGenSavanna(23)).setColor(0xbfa243).setBiomeName("Savanna");
	public static final BiomeGenBase rainforest = (new BiomeGenRainforest(24)).setColor(0x0bd626).setBiomeName("Rainforest");
	public static final BiomeGenBase shrubland = (new BiomeGenShrubland(25)).setColor(0x52b57d).setBiomeName("Shrubland");
	public static final BiomeGenBase seasonalForest = (new BiomeGenSeasonalForest(26)).setColor(0xf29c11).setBiomeName("SeasonalForest");
	public static final BiomeGenBase woodlands = (new BiomeGenWoodlands(27)).setColor(0xf29c11).setBiomeName("Woodlands");
	public static final BiomeGenBase forestedIsland = (new BiomeGenForestedIsland(28)).setColor(0x0bd626).setBiomeName("ForestedIsland");
	public static final BiomeGenBase greenHills = (new BiomeGenGreenHills(29)).setColor(0x67c474).setBiomeName("GreenHills");
	public static final BiomeGenBase pineForest = (new BiomeGenPineForest(30)).setColor(0xf29c11).setBiomeName("PineForest");
	public static final BiomeGenBase greenSwamp = (new BiomeGenGreenSwamp(31)).setColor(0x67c474).setBiomeName("GreenSwamplands");
	public static final BiomeGenBase redrockDesert = (new BiomeGenRedrockDesert(32)).setColor(0x469c7e).setBiomeName("RedrockDesert");
	//public static final BiomeGenBase mountains = (new BiomeGenMountains(23)).setColor(0xbfa243).setBiomeName("Mountains");
	//public static final BiomeGenBase tundra = (new BiomeGenTundra(34)).setColor(0xbfa243).setBiomeName("Tundra");

	public static final BiomeGenBase volcanicIsland = (new BiomeGenVolcanicIsland(14)).setColor(0x404040).setBiomeName("VolcanicIsland");
	public static final BiomeGenBase volcanicIslandShore = (new BiomeGenVolcanicIsland(15)).setColor(0x404040).setBiomeName("VolcanicIslandShore");


	protected BiomeGenBaseMCE(int par1)
	{
		super(par1);
		this.spawnableMonsterList.remove(1);
	}

	public static void init()
	{
		if (MCE_Settings.Biomes.equalsIgnoreCase("yes"))
		{
			GameRegistry.addBiome(BiomeGenBaseMCE.savanna);
			GameRegistry.addBiome(BiomeGenBaseMCE.rainforest);
			GameRegistry.addBiome(BiomeGenBaseMCE.shrubland);
			GameRegistry.addBiome(BiomeGenBaseMCE.seasonalForest);
			GameRegistry.addBiome(BiomeGenBaseMCE.woodlands);
			GameRegistry.addBiome(BiomeGenBaseMCE.forestedIsland);
			GameRegistry.addBiome(BiomeGenBaseMCE.greenHills);
			GameRegistry.addBiome(BiomeGenBaseMCE.pineForest);
			GameRegistry.addBiome(BiomeGenBaseMCE.greenSwamp);
			GameRegistry.addBiome(BiomeGenBaseMCE.redrockDesert);
			//EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.monster);
		}
	}
	/**
	 * Sets the temperature and rainfall of this biome.
	 */
	@Override
	public BiomeGenBase setTemperatureRainfall(float par1, float par2)
	{
		if (par1 > 0.1F && par1 < 0.2F)
		{
			throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
		}
		else
		{
			this.temperature = par1;
			this.rainfall = par2;
			return this;
		}
	}

	/**
	 * Sets the minimum and maximum height of this biome. Seems to go from -2.0 to 2.0.
	 */
	@Override
	public BiomeGenBase setMinMaxHeight(float par1, float par2)
	{
		this.minHeight = par1;
		this.maxHeight = par2;
		return this;
	}

	//    /**
	//     * Disable the rain for the biome.
	//     */
	//    public BiomeGenBase setDisableRain()
	//    {
	//        //this.enableRain = false;
	//		Field enabledRainField;
	//		try {
	//			enabledRainField = BiomeGenBase.class.getDeclaredField("S"); // enableRain
	//			enabledRainField.setAccessible(true);
	//			enabledRainField.setBoolean(this, false);
	//		} catch (Throwable e) {
	//	        System.out.println("BiomeGenBaseMCE... Field setDisableRain: " + e);
	//			try {
	//				enabledRainField = BiomeGenBase.class.getDeclaredField("enableRain"); // enableRain
	//				enabledRainField.setAccessible(true);
	//				enabledRainField.setBoolean(this, false);
	//			} catch (Throwable e2) {
	//		        System.out.println("BiomeGenBaseMCE... Field setDisableRain: " + e2);
	//			}
	//		}
	//		return this;
	//	}
	//
	//    /**
	//     * sets enableSnow to true during biome initialization. returns BiomeGenBase.
	//     */
	//    public BiomeGenBase setEnableSnow()
	//    {
	//        //this.enableSnow = true;
	//		Field enabledSnowField;
	//		try {
	//			enabledSnowField = BiomeGenBase.class.getDeclaredField("R"); // enableSnow
	//			enabledSnowField.setAccessible(true);
	//			enabledSnowField.setBoolean(this, true);
	//		} catch (Throwable e) {
	//	        System.out.println("BiomeGenBaseMCE... Field setEnableSnow: " + e);
	//			try {
	//				enabledSnowField = BiomeGenBase.class.getDeclaredField("enableSnow"); // enableSnow
	//				enabledSnowField.setAccessible(true);
	//				enabledSnowField.setBoolean(this, true);
	//			} catch (Throwable e2) {
	//		        System.out.println("BiomeGenBaseMCE... Field setEnableSnow: " + e2);
	//			}
	//		}
	//        return this;
	//    }

	@Override
	public BiomeGenBase setBiomeName(String par1Str)
	{
		this.biomeName = par1Str;
		return this;
	}

	@Override
	public BiomeGenBase func_76733_a(int par1)
	{
		this.field_76754_C = par1;
		return this;
	}

	@Override
	public BiomeGenBase setColor(int par1)
	{
		this.color = par1;
		return this;
	}
}
