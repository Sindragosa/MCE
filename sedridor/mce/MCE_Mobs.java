package sedridor.mce;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.world.biome.BiomeGenBase;
import sedridor.mce.biomes.*;
import sedridor.mce.entities.*;
import sedridor.mce.models.*;
import sedridor.mce.render.*;

public class MCE_Mobs
{
	private static boolean entityDwarf = true;
	private static boolean entityElf = true;
	private static boolean entityOgre = true;
	private static boolean entityOrc = true;
	private static boolean entityCrusader = true;
	//private static boolean entityTerrorist = false;
	private static boolean entityGoblin = false;
	private static boolean entitySamurai = true;
	private static boolean entityBattleMonk = true;
	private static boolean entityMerchant = true;
	private static boolean entityDesertMerchant = true;
	private static boolean entityGladiator = true;
	private static boolean entityBandit = true;
	private static boolean entityGuard = true;
	private static boolean entityPirate = true;
	private static boolean entityReptilian = false;
	private static boolean entityRanger = true;
	private static boolean entityDovahkiin = true;
	private static boolean entityAssassin = true;
	private static boolean entityDarkKnight = true;
	private static boolean entityDarkMerchant = true;
	private static boolean entitySkeletonNinja = true;
	private static boolean entitySkeletonWarrior = true;
	private static boolean entityViking = true;
	private static boolean entityWarriorPrincess = true;
	//private static boolean entityNinja = false;
	private static boolean entityGoblinBomber = false;
	private static boolean entityGoblinShaman = true;
	private static boolean entityGoblinNinja = true;
	private static boolean entityGoblinRanger = true;
	private static boolean entityMinotaur = false;
	private static boolean entityGhost = false;
	private static boolean entityGiant = false;
	private static boolean entityRedDragon = true;
	private static boolean entityLich = !true;
	private static boolean entityCentaur = false;
	private static boolean entityPygmy = false;

	private static BiomeGenBase vanillaBiomes[] = new BiomeGenBase[] {
        BiomeGenBase.ocean,
    	BiomeGenBase.plains,
    	BiomeGenBase.desert,
    	BiomeGenBase.extremeHills,
    	BiomeGenBase.forest,
    	BiomeGenBase.taiga,
    	BiomeGenBase.swampland,
        BiomeGenBase.river,
        BiomeGenBase.frozenOcean,
        BiomeGenBase.frozenRiver,
        BiomeGenBase.icePlains,
        BiomeGenBase.iceMountains,
        BiomeGenBase.mushroomIsland,
        BiomeGenBase.mushroomIslandShore,
        BiomeGenBase.beach,
    	BiomeGenBase.desertHills,
    	BiomeGenBase.forestHills,
    	BiomeGenBase.taigaHills,
    	BiomeGenBase.extremeHillsEdge,
    	BiomeGenBase.jungle,
        BiomeGenBase.jungleHills
    };
    private static MCEnhancements instance;

    public static BiomeGenBase[] getBiomesToSpawnIn(String entity)
	{
    	String[] biomes = MCE_Settings.entityBiomes.get(entity);
        if (biomes == null) { return null; }

        List<BiomeGenBase> biomeList = new ArrayList<BiomeGenBase>();
        for (int i = 0; i < biomes.length; ++i)
        {
        	if (MCE_Biomes.biomeNameToBiomeID.containsKey(biomes[i]))
        	{
        		int id = MCE_Biomes.biomeNameToBiomeID.get(biomes[i]);
        		biomeList.add(BiomeGenBase.biomeList[id]);
        	}
            else if (MCE_Biomes.packageNameToBiomeID.containsKey(biomes[i]))
            {
        		int id = MCE_Biomes.packageNameToBiomeID.get(biomes[i]);
        		biomeList.add(BiomeGenBase.biomeList[id]);
            }
        }

        return biomeList.toArray(new BiomeGenBase[biomeList.size()]);
	}

    public static void init(MCEnhancements mod)
    {
    	instance = mod;
        int[] entitySet;
        if (MCE_Settings.Mobs.equalsIgnoreCase("yes"))
        {
            if (entityDwarf)
            {
                entitySet = MCE_Settings.entitySettings.get("Dwarf");
            	registerModEntity(EntityDwarf.class, "Dwarf", 1701, mod, 80, 3, true, 0x755434, 0xb59c98);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Dwarf");
            	addSpawn(EntityDwarf.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Dwarf", "Dwarf");
            }

            if (entityElf)
            {
            	entitySet = MCE_Settings.entitySettings.get("Elf");
            	registerModEntity(EntityElf.class, "Elf", 1702, mod, 80, 3, true, 0x489f36, 0xfff87e);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Elf");
            	addSpawn(EntityElf.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Elf", "Elf");
            }

            if (entityOgre)
            {
            	entitySet = MCE_Settings.entitySettings.get("Ogre");
            	registerModEntity(EntityOgre.class, "Ogre", 1703, mod, 80, 3, true, 0x6b9f6b, 0xbd8c72);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Ogre");
            	addSpawn(EntityOgre.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Ogre", "Ogre");
            }

            if (entityOrc)
            {
            	entitySet = MCE_Settings.entitySettings.get("OrcFemale");
            	registerModEntity(EntityOrcFemale.class, "OrcFemale", 1704, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("OrcFemale");
            	addSpawn(EntityOrcFemale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("OrcFemale", "Orc Female");

            	entitySet = MCE_Settings.entitySettings.get("OrcMale");
            	registerModEntity(EntityOrcMale.class, "OrcMale", 1705, mod, 80, 3, true, 0x6b9f12, 0xc22c2c);
            	addSpawn(EntityOrcMale.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("OrcMale", "Orc Male");
            }

            if (entityCrusader)
            {
            	entitySet = MCE_Settings.entitySettings.get("Crusader");
            	registerModEntity(EntityCrusader.class, "Crusader", 1706, mod, 80, 3, true, 0xf8f0e3, 0x6f6f6f);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Crusader");
            	addSpawn(EntityCrusader.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Crusader", "Crusader");

            }

            //if (entityTerrorist)
            //{
            //}

            if (entityGoblin)
            {
            	entitySet = MCE_Settings.entitySettings.get("Goblin");
            	EntityRegistry.registerModEntity(EntityGoblin.class, "Goblin", 1707, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Goblin");
            	addSpawn(EntityGoblin.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Goblin", "Goblin");
            }

            if (entitySamurai)
            {
            	entitySet = MCE_Settings.entitySettings.get("Samurai");
            	registerModEntity(EntitySamurai.class, "Samurai", 1708, mod, 80, 3, true, 0x6bbfd1, 0xc2c2c2);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Samurai");
            	addSpawn(EntitySamurai.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Samurai", "Samurai");
            }

            if (entityBattleMonk)
            {
            	entitySet = MCE_Settings.entitySettings.get("BattleMonk");
            	registerModEntity(EntityBattleMonk.class, "BattleMonk", 1709, mod, 80, 3, true, 0x6c575d, 0xc8b8a5);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("BattleMonk");
            	addSpawn(EntityBattleMonk.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("BattleMonk", "Battle Monk");
            }

            if (entityMerchant)
            {
            	entitySet = MCE_Settings.entitySettings.get("Merchant");
            	registerModEntity(EntityMerchant.class, "Merchant", 1710, mod, 80, 3, true, 0xf27c08, 0xddad64);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Merchant");
            	addSpawn(EntityMerchant.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Merchant", "Merchant");
            }

            if (entityDesertMerchant)
            {
            	entitySet = MCE_Settings.entitySettings.get("DesertMerchant");
            	registerModEntity(EntityDesertMerchant.class, "DesertMerchant", 1711, mod, 80, 3, true, 0xf7e9a4, 0xbababa);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("DesertMerchant");
            	addSpawn(EntityDesertMerchant.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("DesertMerchant", "Desert Merchant");
            }

            if (entityGladiator)
            {
            	entitySet = MCE_Settings.entitySettings.get("Gladiator");
            	registerModEntity(EntityGladiator.class, "Gladiator", 1712, mod, 80, 3, true, 0x9f0f10, 0xb9b9b9);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Gladiator");
            	addSpawn(EntityGladiator.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Gladiator", "Gladiator");
            }

            if (entityBandit)
            {
            	entitySet = MCE_Settings.entitySettings.get("Bandit");
            	registerModEntity(EntityBandit.class, "Bandit", 1713, mod, 80, 3, true, 0x755435, 0x63472d);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Bandit");
            	addSpawn(EntityBandit.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Bandit", "Bandit");
            }

            if (entityGuard)
            {
            	entitySet = MCE_Settings.entitySettings.get("Guard");
            	registerModEntity(EntityGuard.class, "Guard", 1714, mod, 80, 3, true, 0xfff87e, 0xbfbfbf);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Guard");
            	addSpawn(EntityGuard.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Guard", "Guard");
            }

            if (entityPirate)
            {
            	entitySet = MCE_Settings.entitySettings.get("Pirate");
            	registerModEntity(EntityPirate.class, "Pirate", 1715, mod, 80, 3, true, 0x2c2c2c, 0xf8f8f8);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Pirate");
            	addSpawn(EntityPirate.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Pirate", "Pirate");
            }

            if (entityReptilian)
            {
            	entitySet = MCE_Settings.entitySettings.get("Reptilian");
            	registerModEntity(EntityReptilian.class, "Reptilian", 1716, mod, 80, 3, true, 0x6b9f12, 0x5a854c);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Reptilian");
                addSpawn(EntityReptilian.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Reptilian", "Reptilian");
            }

            if (entityRanger)
            {
            	entitySet = MCE_Settings.entitySettings.get("Ranger");
            	registerModEntity(EntityRanger.class, "Ranger", 1717, mod, 80, 3, true, 0x6b9f6b, 0x2c2c2c);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Ranger");
            	addSpawn(EntityRanger.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Ranger", "Ranger");
            }

            if (entityDovahkiin)
            {
            	entitySet = MCE_Settings.entitySettings.get("Dovahkiin");
            	registerModEntity(EntityDovahkiin.class, "Dovahkiin", 1718, mod, 80, 3, true, 0xf27b08, 0xb0604);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Dovahkiin");
                addSpawn(EntityDovahkiin.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Dovahkiin", "Dovahkiin");
            }

            if (entityAssassin)
            {
            	entitySet = MCE_Settings.entitySettings.get("Assassin");
            	registerModEntity(EntityAssassin.class, "Assassin", 1719, mod, 80, 3, true, 0x9f419f, 0x2b2b2b);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Assassin");
                addSpawn(EntityAssassin.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Assassin", "Assassin");
            }

            if (entityDarkKnight)
            {
            	entitySet = MCE_Settings.entitySettings.get("DarkKnight");
            	registerModEntity(EntityDarkKnight.class, "DarkKnight", 1720, mod, 80, 3, true, 0x5f1e1e, 0x6f6f6f);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("DarkKnight");
                addSpawn(EntityDarkKnight.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("DarkKnight", "Dark Knight");
            }

            if (entityDarkMerchant)
            {
            	entitySet = MCE_Settings.entitySettings.get("DarkMerchant");
            	registerModEntity(EntityDarkMerchant.class, "DarkMerchant", 1721, mod, 80, 3, true, 0x5f1e1e, 0xbababa);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("DarkMerchant");
                addSpawn(EntityMerchant.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("DarkMerchant", "Dark Merchant");
            }

            if (entitySkeletonNinja)
            {
            	entitySet = MCE_Settings.entitySettings.get("SkeletonNinja");
            	registerModEntity(EntitySkeletonNinja.class, "SkeletonNinja", 1724, mod, 80, 3, true, 0x292929, 0xc1c1c1);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("SkeletonNinja");
                addSpawn(EntitySkeletonNinja.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("SkeletonNinja", "Skeleton Ninja");
            }

            if (entitySkeletonWarrior)
            {
            	entitySet = MCE_Settings.entitySettings.get("SkeletonWarrior");
            	registerModEntity(EntitySkeletonWarrior.class, "SkeletonWarrior", 1725, mod, 80, 3, true, 0x595959, 0xc1c1c1);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("SkeletonWarrior");
                addSpawn(EntitySkeletonWarrior.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("SkeletonWarrior", "Skeleton Warrior");
            }

            if (entityViking)
            {
            	entitySet = MCE_Settings.entitySettings.get("Viking");
            	registerModEntity(EntityViking.class, "Viking", 1726, mod, 80, 3, true, 0x755434, 0xfff87e);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Viking");
                addSpawn(EntityViking.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Viking", "Viking");
            }

            if (entityWarriorPrincess)
            {
            	entitySet = MCE_Settings.entitySettings.get("WarriorPrincess");
            	registerModEntity(EntityWarriorPrincess.class, "WarriorPrincess", 1727, mod, 80, 3, true, 0x4d4dd4, 0xfff87e);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("WarriorPrincess");
                addSpawn(EntityWarriorPrincess.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("WarriorPrincess", "Warrior Princess");
            }

            if (entityRedDragon)
            {
            	entitySet = MCE_Settings.entitySettings.get("RedDragon");
            	registerModEntity(EntityRedDragon.class, "RedDragon", 1728, mod, 80, 3, true, 0x9f1010, 0x202020);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("RedDragon");
                addSpawn(EntityRedDragon.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("RedDragon", "Red Dragon");
            }

            if (entityRanger)
            {
            }


            if (entityGoblinBomber)
            {
            	entitySet = MCE_Settings.entitySettings.get("GoblinBomber");
            	EntityRegistry.registerModEntity(EntityGoblinBomber.class, "GoblinBomber", 1729, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("GoblinBomber");
            	addSpawn(EntityGoblinBomber.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("GoblinBomber", "Goblin Bomber");
            }

            if (entityGoblinShaman)
            {
            	entitySet = MCE_Settings.entitySettings.get("GoblinShaman");
            	EntityRegistry.registerModEntity(EntityGoblinShaman.class, "GoblinShaman", 1730, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("GoblinShaman");
            	addSpawn(EntityGoblinShaman.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("GoblinShaman", "Goblin Shaman");
            	Minecraft.getMinecraft().installResource("sound3/appear.ogg", new File(Minecraft.getMinecraft().mcDataDir,"resources/mod/sound/appear.ogg"));
            	Minecraft.getMinecraft().installResource("sound3/missile.ogg", new File(Minecraft.getMinecraft().mcDataDir,"resources/mod/sound/missile.ogg"));

            	EntityRegistry.registerModEntity(EntityArcaneCharge.class, "ArcaneCharge", 2700, mod, 64, 20, true);
                EntityRegistry.registerModEntity(EntityLightball.class, "Lightball", 2701, mod, 64, 20, true);
                EntityRegistry.registerModEntity(EntityMissile.class, "Missile", 2709, mod, 64, 20, true);
            }

            if (entityGoblinNinja)
            {
            	entitySet = MCE_Settings.entitySettings.get("GoblinNinja");
            	EntityRegistry.registerModEntity(EntityGoblinNinja.class, "GoblinNinja", 1731, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("GoblinNinja");
            	addSpawn(EntityGoblinNinja.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("GoblinNinja", "Goblin Ninja");
                EntityRegistry.registerModEntity(EntityShuriken.class, "Shuriken", 2702, mod, 64, 20, true);
            }

            if (entityGoblinRanger)
            {
            	entitySet = MCE_Settings.entitySettings.get("GoblinRanger");
            	EntityRegistry.registerModEntity(EntityGoblinRanger.class, "GoblinRanger", 1732, mod, 80, 3, true);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("GoblinRanger");
            	addSpawn(EntityGoblinRanger.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("GoblinRanger", "Goblin Ranger");
            }

            //if (entityNinja)
            //{
            //}

            if (entityMinotaur)
            {
            	entitySet = MCE_Settings.entitySettings.get("Minotaur");
            	registerModEntity(EntityMinotaur.class, "Minotaur", 1733, mod, 80, 3, true, 0x6c571d, 0xc8b8a3);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Minotaur");
                addSpawn(EntityMinotaur.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Minotaur", "Minotaur");
            }

            if (entityGhost)
            {
            	entitySet = MCE_Settings.entitySettings.get("Ghost");
            	registerModEntity(EntityGhost.class, "Ghost", 1734, mod, 80, 3, true, 0xf8f8f8, 0xbcbcbc);
            	BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Ghost");
                addSpawn(EntityGhost.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
            	addName("Ghost", "Ghost");
            }

            if (entityGiant)
            {
            	entitySet = MCE_Settings.entitySettings.get("Giant");
            	registerModEntity(EntityGiant.class, "Giant", 1735, mod, 80, 3, true, 0x6c541d, 0xc8b8a9);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Giant");
                addSpawn(EntityGiant.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Giant", "Giant");
            }
            if (entityLich)
            {
            	entitySet = MCE_Settings.entitySettings.get("Lich");
            	EntityRegistry.registerModEntity(EntityLich.class, "Lich", 1736, mod, 80, 3, true);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Lich");
                EntityRegistry.addSpawn(EntityLich.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Lich", "Lich");

                EntityRegistry.registerModEntity(EntityLichMinion.class, "LichMinion", 1737, mod, 80, 3, true);
                addSpawn(EntityLichMinion.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("LichMinion", "Lich Minion");

                EntityRegistry.registerModEntity(EntityTwilightBolt.class, "TwilightBolt", 2703, mod, 64, 20, true);
            	EntityRegistry.registerModEntity(EntityLichBolt.class, "LichBolt", 2704, mod, 64, 20, true);
            	EntityRegistry.registerModEntity(EntityNatureBolt.class, "NatureBolt", 2705, mod, 64, 20, true);
            }
            if (entityCentaur)
            {
            	entitySet = MCE_Settings.entitySettings.get("Centaur");
            	registerModEntity(EntityCentaur.class, "Centaur", 1738, mod, 80, 3, true, 0x5c3a24, 0x574b43);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Centaur");
                addSpawn(EntityCentaur.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Centaur", "Centaur");
            }
            if (entityPygmy)
            {
            	entitySet = MCE_Settings.entitySettings.get("Pygmy");
            	registerModEntity(EntityPygmy.class, "Pygmy", 1740, mod, 80, 3, true, 0x90583e, 0x1e8fd4);
                BiomeGenBase biomesToSpawnIn[] = getBiomesToSpawnIn("Pygmy");
                addSpawn(EntityPygmy.class, entitySet[0], entitySet[1], entitySet[2], EnumCreatureType.monster, biomesToSpawnIn);
                addName("Pygmy", "Pygmy");
            }
            EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.monster, vanillaBiomes);
        }
    }

    /**
     * Register the mod entity type with FML
     * @param entityClass The entity class
     * @param entityName A unique name for the entity
     * @param id A mod specific ID for the entity
     * @param mod The mod
     * @param trackingRange The range at which MC will send tracking updates
     * @param updateFrequency The frequency of tracking updates
     * @param sendsVelocityUpdates Whether to send velocity information packets as well
     * @param backgroundEggColour 
     * @param foregroundEggColour 
     */
    public static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
    	EntityRegistry.registerModEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    	EntityList.addMapping(entityClass, entityName, id);
    }

    public static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int backgroundEggColour, int foregroundEggColour)
    {
    	EntityRegistry.registerModEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    	EntityList.addMapping(entityClass, entityName, id, backgroundEggColour, foregroundEggColour);
    }

    public static void addName(String par1, String par2)
    {
    	LanguageRegistry.instance().addStringLocalization("entity." + par1 + ".name", "en_US", par2);
    }

    public static void addSpawn(Class<? extends EntityLiving> entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
    {
    	if (biomes.length == 0)
    	{
        	EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature);
    	}
        else
        {
        	EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
        }
    }

    public static void addRenderer()
    {
        if (entityDwarf)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityDwarf.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityElf)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityElf.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityOgre)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityOgre.class, new RenderOgre(new ModelOgre2(), new ModelOgre1(), 1.5F));
        }

        if (entityOrc)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityOrcFemale.class, new RenderLivingMCE(new ModelOrcFemale(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(EntityOrcMale.class, new RenderLivingMCE(new ModelOrcMale(), 0.5F));
        }

        if (entityCrusader)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityCrusader.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        //if (entityTerrorist)
        //{
        //}

        if (entityGoblin)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGoblin.class, new RenderGoblin(new ModelGoblin(), 0.5F));
        }

        if (entitySamurai)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntitySamurai.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityBattleMonk)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityBattleMonk.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityMerchant)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityMerchant.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityDesertMerchant)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityDesertMerchant.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityGladiator)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGladiator.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityBandit)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityBandit.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityGuard)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGuard.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityPirate)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityPirate.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityReptilian)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityReptilian.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityRanger)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityRanger.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityDovahkiin)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityDovahkiin.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityAssassin)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityAssassin.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityDarkKnight)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityDarkKnight.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityDarkMerchant)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityDarkMerchant.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entitySkeletonNinja)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonNinja.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entitySkeletonWarrior)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonWarrior.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityViking)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityViking.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }

        if (entityWarriorPrincess)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityWarriorPrincess.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }
        if (entityRedDragon)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityRedDragon.class, new RenderRedDragon());
        }
        if (entityRanger)
        {
        }
        if (entityGoblinBomber)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGoblinBomber.class, new RenderGoblinBomber(new ModelGoblinBomber(), 0.5F));
        }
        if (entityGoblinShaman)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGoblinShaman.class, new RenderGoblinShaman(new ModelGoblinShaman(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(EntityArcaneCharge.class, new RenderArcaneCharge());
            RenderingRegistry.registerEntityRenderingHandler(EntityLightball.class, new RenderLightball());
            RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class, new RenderMissile());
        }
        if (entityGoblinNinja)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGoblinNinja.class, new RenderGoblinNinja(new ModelGoblin(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(EntityShuriken.class, new RenderShuriken());
        }
        if (entityGoblinRanger)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGoblinRanger.class, new RenderGoblinRanger(new ModelGoblinRanger(), 0.5F));
        }

        //if (entityNinja)
        //{
        //}
        if (entityMinotaur)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityMinotaur.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }
        if (entityGhost)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, new RenderBipedMCE(new ModelBiped(), 0.5F));
        }
        if (entityGiant)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityGiant.class, new RenderGiant(new ModelBiped(), 0.5F));
        }
        if (entityLich)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityLich.class, new RenderLich(new ModelLich(), 1.0F));
            RenderingRegistry.registerEntityRenderingHandler(EntityLichMinion.class, new RenderBipedMCE(new ModelLichMinion(), 1.0F));
            RenderingRegistry.registerEntityRenderingHandler(EntityTwilightBolt.class, new RenderSnowball(Item.enderPearl));
            RenderingRegistry.registerEntityRenderingHandler(EntityLichBolt.class, new RenderSnowball(Item.enderPearl));
            RenderingRegistry.registerEntityRenderingHandler(EntityNatureBolt.class, new RenderSnowball(Item.seeds));
        }
        if (entityCentaur)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityCentaur.class, new RenderBipedMCE(new ModelCentaur(), 0.7F));
        }
        if (entityPygmy)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityPygmy.class, new RenderBipedMCE(new ModelPygmy(), 0.4F));
        }
    }
}
