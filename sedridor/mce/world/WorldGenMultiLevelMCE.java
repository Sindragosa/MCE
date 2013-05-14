package sedridor.mce.world;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import sedridor.mce.MCE_Items;
import sedridor.mce.MCE_Settings;

public class WorldGenMultiLevelMCE implements IWorldGenerator
{
	private boolean multiLayerOreGeneration;
	private boolean generateMithril;
	private boolean generateAdamantium;
	private boolean generateRunite;
	private boolean generateDragonite;
	private boolean generateCrystal;
	private boolean generateSilver;
	private boolean generateBronze;
	private boolean generateBlurite;
	private boolean generateObsidium;
	private boolean generateTitanium;
	//private boolean generateElementium;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId == 0)
		{
			this.generateSurface(world, random, chunkX << 4, chunkZ << 4);
		}
	}

	public WorldGenMultiLevelMCE()
	{
		this.multiLayerOreGeneration = MCE_Settings.MultiLayerOreGeneration.equalsIgnoreCase("yes");
		this.generateMithril = MCE_Settings.Mithril.equalsIgnoreCase("yes");
		this.generateAdamantium = MCE_Settings.Adamantium.equalsIgnoreCase("yes");
		this.generateRunite = MCE_Settings.Runite.equalsIgnoreCase("yes");
		this.generateDragonite = MCE_Settings.Dragonite.equalsIgnoreCase("yes");
		this.generateCrystal = MCE_Settings.Crystal.equalsIgnoreCase("yes");
		this.generateSilver = MCE_Settings.Silver.equalsIgnoreCase("yes");
		this.generateBronze = MCE_Settings.Bronze.equalsIgnoreCase("yes");
		this.generateBlurite = MCE_Settings.Blurite.equalsIgnoreCase("yes");
		this.generateObsidium = MCE_Settings.Obsidium.equalsIgnoreCase("yes");
		this.generateTitanium = MCE_Settings.Titanium.equalsIgnoreCase("yes");
		//this.generateElementium = MCE_Settings.Elementium.equalsIgnoreCase("yes");
	}

	/*
    // dirt:		rarity 20, veinSize 32, minHeight 0, maxHeight 128
    // gravel:		rarity 10, veinSize 32, minHeight 0, maxHeight 128
    // diamond:		rarity 1, veinSize 7, minHeight 0, maxHeight 16
    // gold:		rarity 2, veinSize 8, minHeight 0, maxHeight 32
    // redstone:	rarity 8, veinSize 7, minHeight 0, maxHeight 16
    // iron:		rarity 20, veinSize 8, minHeight 0, maxHeight 64
    // coal:		rarity 20, veinSize 16, minHeight 0, maxHeight 128
    // lapis:		rarity 1, veinSize 6, minHeight 16, maxHeight 16

	// Mithril:		rarity 4, veinSize 8, minHeight 0, maxHeight 28
	// Adamantium:	rarity 4, veinSize 8, minHeight 0, maxHeight 20
	// Runite:		rarity 1, veinSize 7, minHeight 0, maxHeight 16
	// Dragonite:	rarity 1, veinSize 4, minHeight 0, maxHeight 16
	// Obsidium:	rarity 1, veinSize 2, minHeight 0, maxHeight 12
	// Crystal:		rarity 1, veinSize 2, minHeight 0, maxHeight 12
	// Silver:		rarity 4, veinSize 8, minHeight 0, maxHeight 64
	// Blurite:		rarity 2, veinSize 8, minHeight 0, maxHeight 64
	// Bronze:		rarity 20, veinSize 8, minHeight 0, maxHeight 64
	// --Titanium:		rarity 1, veinSize 7, minHeight 0, maxHeight 16
	// --Elementium:	rarity 1, veinSize 4, minHeight 0, maxHeight 16
	 */
	public void generateSurface(World par1World, Random par2Random, int chunkX, int chunkZ)
	{
		if (this.generateMithril)
		{
			this.generateOres(MCE_Items.MithrilOre.blockID, 8, 2, 28, par1World, par2Random, chunkX, chunkZ);
			if (this.multiLayerOreGeneration)
			{
				if (par2Random.nextInt(4) == 0)
				{
					this.generateOres(MCE_Items.MithrilOre.blockID, 8, 2, 28, 48, par1World, par2Random, chunkX, chunkZ);
				}
				if (par2Random.nextInt(10) == 0)
				{
					this.generateOres(MCE_Items.MithrilOre.blockID, 4, 1, 48, 80, par1World, par2Random, chunkX, chunkZ);
				}
			}
		}
		if (this.generateAdamantium)
		{
			this.generateOres(MCE_Items.AdamantiumOre.blockID, 8, 2, 20, par1World, par2Random, chunkX, chunkZ);
			if (this.multiLayerOreGeneration)
			{
				if (par2Random.nextInt(4) == 0)
				{
					this.generateOres(MCE_Items.AdamantiumOre.blockID, 8, 2, 20, 44, par1World, par2Random, chunkX, chunkZ);
				}
				if (par2Random.nextInt(10) == 0)
				{
					this.generateOres(MCE_Items.AdamantiumOre.blockID, 4, 1, 44, 80, par1World, par2Random, chunkX, chunkZ);
				}
			}
		}
		if (this.generateRunite)
		{
			this.generateOres(MCE_Items.RuniteOre.blockID, 7, 1, 20, par1World, par2Random, chunkX, chunkZ);
			if (this.multiLayerOreGeneration)
			{
				if (par2Random.nextInt(4) == 0)
				{
					this.generateOres(MCE_Items.RuniteOre.blockID, 4, 1, 20, 48, par1World, par2Random, chunkX, chunkZ);
				}
				if (par2Random.nextInt(10) == 0)
				{
					this.generateOres(MCE_Items.RuniteOre.blockID, 2, 1, 48, 80, par1World, par2Random, chunkX, chunkZ);
				}
			}
		}
		if (this.generateDragonite)
		{
			this.generateOres(MCE_Items.DragoniteOre.blockID, 2, 1, 20, par1World, par2Random, chunkX, chunkZ);
			if (this.multiLayerOreGeneration)
			{
				if (par2Random.nextInt(4) == 0)
				{
					this.generateOres(MCE_Items.DragoniteOre.blockID, 2, 1, 20, 48, par1World, par2Random, chunkX, chunkZ);
				}
				if (par2Random.nextInt(10) == 0)
				{
					this.generateOres(MCE_Items.DragoniteOre.blockID, 1, 1, 48, 64, par1World, par2Random, chunkX, chunkZ);
				}
			}
		}
		if (this.generateCrystal)
		{
			this.generateOres(MCE_Items.CrystalOre.blockID, 2, 1, 12, par1World, par2Random, chunkX, chunkZ);
			if (this.multiLayerOreGeneration)
			{
				if (par2Random.nextInt(4) == 0)
				{
					this.generateOres(MCE_Items.CrystalOre.blockID, 2, 1, 12, 44, par1World, par2Random, chunkX, chunkZ);
				}
				if (par2Random.nextInt(10) == 0)
				{
					this.generateOres(MCE_Items.CrystalOre.blockID, 1, 1, 44, 64, par1World, par2Random, chunkX, chunkZ);
				}
			}
		}
		if (this.generateSilver)
		{
			this.generateOres(MCE_Items.SilverOre.blockID, 8, 4, 64, par1World, par2Random, chunkX, chunkZ);
			if (this.multiLayerOreGeneration)
			{
				if (par2Random.nextInt(4) == 0)
				{
					this.generateOres(MCE_Items.SilverOre.blockID, 4, 4, 64, 96, par1World, par2Random, chunkX, chunkZ);
				}
			}
		}
		if (this.generateBronze)
		{
			this.generateOres(MCE_Items.CopperOre.blockID, 8, 20, 64, par1World, par2Random, chunkX, chunkZ);
			this.generateOres(MCE_Items.TinOre.blockID, 8, 20, 64, par1World, par2Random, chunkX, chunkZ);
			if (this.multiLayerOreGeneration)
			{
				if (par2Random.nextInt(4) == 0)
				{
					this.generateOres(MCE_Items.CopperOre.blockID, 8, 20, 64, 96, par1World, par2Random, chunkX, chunkZ);
					this.generateOres(MCE_Items.TinOre.blockID, 8, 20, 64, 96, par1World, par2Random, chunkX, chunkZ);
				}
			}
		}
		if (this.generateBlurite)
		{
			// Ice only
			String s = par1World.getBiomeGenForCoords(chunkX + 8, chunkZ + 8).biomeName;
			if (s.contains("Taiga") || s.contains("Tundra") || s.contains("Ice Plains") || s.contains("Ice Mountains") || s.contains("Frozen"))
			{
				this.generateOres(MCE_Items.BluriteOre.blockID, 8, 2, 64, par1World, par2Random, chunkX, chunkZ);
				if (this.multiLayerOreGeneration)
				{
					if (par2Random.nextInt(4) == 0)
					{
						this.generateOres(MCE_Items.BluriteOre.blockID, 4, 2, 64, 80, par1World, par2Random, chunkX, chunkZ);
					}
				}
			}
		}
		if (this.generateObsidium)
		{
			// Desert only
			String s = par1World.getBiomeGenForCoords(chunkX + 8, chunkZ + 8).biomeName;
			if (s.contains("Desert") || s.contains("Volcanic"))
			{
				this.generateOres(MCE_Items.ObsidiumOre.blockID, 2, 1, 12, par1World, par2Random, chunkX, chunkZ);
				if (this.multiLayerOreGeneration)
				{
					if (par2Random.nextInt(4) == 0)
					{
						this.generateOres(MCE_Items.ObsidiumOre.blockID, 2, 1, 12, 44, par1World, par2Random, chunkX, chunkZ);
					}
					if (par2Random.nextInt(10) == 0)
					{
						this.generateOres(MCE_Items.ObsidiumOre.blockID, 1, 1, 44, 64, par1World, par2Random, chunkX, chunkZ);
					}
				}
			}
		}
		if (this.generateTitanium)
		{
			this.generateOres(MCE_Items.TitaniumOre.blockID, 7, 1, 20, par1World, par2Random, chunkX, chunkZ);
			if (this.multiLayerOreGeneration)
			{
				if (par2Random.nextInt(4) == 0)
				{
					this.generateOres(MCE_Items.TitaniumOre.blockID, 4, 1, 12, 44, par1World, par2Random, chunkX, chunkZ);
				}
				if (par2Random.nextInt(10) == 0)
				{
					this.generateOres(MCE_Items.TitaniumOre.blockID, 2, 1, 44, 64, par1World, par2Random, chunkX, chunkZ);
				}
			}
		}
		/*if (this.generateElementium)
        {
        	this.generateOres(MCE_Items.ElementiumOre.blockID, 2, 1, 12, par1World, par2Random, chunkX, chunkZ);
            if (this.multiLayerOreGeneration)
            {
                if (par2Random.nextInt(4) == 0)
                {
                	this.generateOres(MCE_Items.ElementiumOre.blockID, 2, 1, 12, 44, par1World, par2Random, chunkX, chunkZ);
                }
                if (par2Random.nextInt(10) == 0)
                {
                	this.generateOres(MCE_Items.ElementiumOre.blockID, 1, 1, 44, 64, par1World, par2Random, chunkX, chunkZ);
                }
            }
        }*/
	}

	/**
	 * Generate ore with the given parameters at the given location
	 *
	 * @param blockID The blockID of the ore
	 * @param veinSize The size of the ore vein
	 * @param rarity The rarity of the ore
	 * @param minHeight Min height for ore generation
	 * @param maxHeight Max height for ore generation
	 */
	private void generateOres(int blockID, int veinSize, int rarity, int minHeight, int maxHeight, World par1World, Random par2Random, int chunkX, int chunkZ)
	{
		for (int i = 0; i < rarity; ++i)
		{
			int randomPosX = chunkX + par2Random.nextInt(16);
			int randomPosY = par2Random.nextInt(maxHeight - minHeight) + minHeight;
			int randomPosZ = chunkZ + par2Random.nextInt(16);
			(new WorldGenMinable(blockID, veinSize)).generate(par1World, par2Random, randomPosX, randomPosY, randomPosZ);
		}
	}
	/**
	 * Generate ore with the given parameters at the given location
	 *
	 * @param blockID The blockID of the ore
	 * @param veinSize The size of the ore vein
	 * @param rarity The rarity of the ore
	 * @param height Max height for ore generation
	 */
	private void generateOres(int blockID, int veinSize, int rarity, int height, World par1World, Random par2Random, int chunkX, int chunkZ)
	{
		this.generateOres(blockID, veinSize, rarity, 0, height, par1World, par2Random, chunkX, chunkZ);
	}
}
