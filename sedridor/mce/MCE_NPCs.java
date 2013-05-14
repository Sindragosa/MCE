package sedridor.mce;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import sedridor.mce.biomes.*;
import sedridor.mce.entities.*;
import sedridor.mce.models.*;
import sedridor.mce.render.*;

public class MCE_NPCs
{
	private static boolean entityDwarf = true;
	private static boolean entityElf = true;
	private static boolean entityHumanFemale = !true;
	private static boolean entityOgre = true;
	private static boolean entityOrc = true;
	private static boolean entityNaga = true;
	//private static boolean entityViking = true;

    private static MCEnhancements instance;

    public static void init(MCEnhancements mod)
    {
    	instance = mod;
        int[] entitySet;
        if (MCE_Settings.NPCs.equalsIgnoreCase("yes"))
        {
            if (entityDwarf)
            {
            	entitySet = MCE_Settings.entitySettings.get("DwarfFemale");
            	EntityRegistry.registerModEntity(EntityDwarfFemale.class, "DwarfFemale", 1756, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = MCE_Mobs.getBiomesToSpawnIn("DwarfFemale");
            	MCE_Mobs.addSpawn(EntityDwarfFemale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	MCE_Mobs.addName("DwarfFemale", "Dwarf Female");

            	entitySet = MCE_Settings.entitySettings.get("DwarfMale");
            	EntityRegistry.registerModEntity(EntityDwarfMale.class, "DwarfMale", 1755, mod, 80, 3, true);
            	MCE_Mobs.addSpawn(EntityDwarfMale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	MCE_Mobs.addName("DwarfMale", "Dwarf Male");
            }

            if (entityElf)
            {
            	entitySet = MCE_Settings.entitySettings.get("ElfFemale");
            	EntityRegistry.registerModEntity(EntityElfFemale.class, "ElfFemale", 1754, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = MCE_Mobs.getBiomesToSpawnIn("ElfFemale");
            	MCE_Mobs.addSpawn(EntityElfFemale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	MCE_Mobs.addName("ElfFemale", "Elf Female");

            	entitySet = MCE_Settings.entitySettings.get("ElfMale");
            	EntityRegistry.registerModEntity(EntityElfMale.class, "ElfMale", 1753, mod, 80, 3, true);
            	MCE_Mobs.addSpawn(EntityElfMale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	MCE_Mobs.addName("ElfMale", "Elf Male");
            }

            if (entityHumanFemale)
            {
            	entitySet = MCE_Settings.entitySettings.get("HumanFemale");
            	EntityRegistry.registerModEntity(EntityHumanFemale.class, "HumanFemale", 1757, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = MCE_Mobs.getBiomesToSpawnIn("HumanFemale");
            	MCE_Mobs.addSpawn(EntityHumanFemale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	MCE_Mobs.addName("HumanFemale", "Human Female");
            }

            if (entityOgre)
            {
            	//entitySet = MCE_Settings.entitySettings.get("Ogre");
            	//registerModEntity(EntityOgre.class, "Ogre2", 1758, mod, 80, 3, true, 0x6b9f6b, 0xbd8c72);
            	//BiomeGenBase biomesToSpawnIn[] = MCE_Mobs.getBiomesToSpawnIn("Ogre2");
            	//MCE_Mobs.addSpawn(EntityOgre.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	//MCE_Mobs.addName("Ogre2", "en_US", "Ogre2", 0);
            }

            if (entityOrc)
            {
            }

            if (entityNaga)
            {
            	entitySet = MCE_Settings.entitySettings.get("NagaFemale");
            	MCE_Mobs.registerModEntity(EntityNagaFemale.class, "NagaFemale", 1752, mod, 80, 3, true, 0x6b9f12, 0xc22c2c);
            	BiomeGenBase biomesToSpawnIn[] = MCE_Mobs.getBiomesToSpawnIn("NagaFemale");
            	MCE_Mobs.addSpawn(EntityNagaFemale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	MCE_Mobs.addName("NagaFemale", "Naga Female");

            	entitySet = MCE_Settings.entitySettings.get("NagaMale");
            	MCE_Mobs.registerModEntity(EntityNagaMale.class, "NagaMale", 1751, mod, 80, 3, true, 0x6b9f12, 0xc22c2c);
            	MCE_Mobs.addSpawn(EntityNagaMale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	MCE_Mobs.addName("NagaMale", "Naga Male");
            }
            /*if (entityViking)
            {
            	entitySet = MCE_Settings.entitySettings.get("Viking");
            	registerModEntity(EntityViking.class, "Viking", 1759, mod, 80, 3, true, 0x755434, 0xfff87e);
                BiomeGenBase biomesToSpawnIn[] = MCE_Mobs.getBiomesToSpawnIn("Viking");
                MCE_Mobs.addSpawn(EntityViking.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Viking", "en_US", "Viking", 0);
            }*/
        }
    }

    public static void addRenderer()
    {
        if (entityDwarf)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityDwarfFemale.class, new RenderLivingMCE(new ModelDwarfFemale(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(EntityDwarfMale.class, new RenderLivingMCE(new ModelDwarfMale(), 0.5F));
        }

        if (entityElf)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityElfFemale.class, new RenderLivingMCE(new ModelElfFemale(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(EntityElfMale.class, new RenderLivingMCE(new ModelElfMale(), 0.5F));
        }

        if (entityHumanFemale)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityHumanFemale.class, new RenderLivingMCE(new ModelHumanFemale(), 0.5F));
        }

        if (entityOgre)
        {
        }

        if (entityOrc)
        {
        }

        if (entityNaga)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityNagaFemale.class, new RenderLivingMCE(new ModelNagaFemale(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(EntityNagaMale.class, new RenderLivingMCE(new ModelNagaMale(), 0.5F));
        }

        //if (entityViking)
        //{
        //    RenderingRegistry.registerEntityRenderingHandler(EntityViking.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        //}
    }
}
