package sedridor.mce;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import sedridor.mce.biomes.BiomeGenBaseMCE;
import sedridor.mce.blocks.*;
import sedridor.mce.entities.*;
import sedridor.mce.items.*;
import sedridor.mce.proxy.CommonProxy;
import sedridor.mce.render.*;
import sedridor.mce.tileentities.*;
import sedridor.mce.world.WorldGenMCE;
import sedridor.mce.world.WorldGenMultiLevelMCE;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.EnumOSHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.network.packet.Packet23VehicleSpawn;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingFallEvent;

/*
// Block 1 - 136, Item 0 - 132 (256 - 388)
// Steel:		212 - 566-575, 712 (821 - 830, 968)	1
// Mithril:		200 - 446-455, 700 (701 - 710, 956)	2
// Adamantium:	201 - 456-465, 701 (711 - 720, 957)	3
// Runite:		202 - 466-475, 702 (721 - 730, 958)	4
// Dragonite:	204 - 486-495, 704 (741 - 750, 960)	5
// Obsidium:	208 - 526-535, 708 (781 - 790, 964)	10
// Crystal:		207 - 516-525, 707 (771 - 780, 963)	10
// Silver:		206 - 506-515, 706 (761 - 770, 962)	7
// Blurite:		205 - 496-505, 705 (751 - 760, 961)	8
// Bronze:		203 - 476-485, 703 (731 - 740, 959)	9
// Titanium:	209 - 536-545, 709 (791 - 800, 965)	11
// Elementium:	210 - 546-555, 710 (801 - 810, 966)	12
 */
@Mod(
        modid = "MCE",
        name = "Minecraft Enhancements",
        version = "1.5.1"
)
@NetworkMod(
        clientSideRequired = true,
        serverSideRequired = false//,
        //channels = {"MCE"},
        //packetHandler = PacketHandler.class
)
public class MCEnhancements
{
    @Instance("MCE")
    public static MCEnhancements instance;

    @SidedProxy(
            clientSide = "sedridor.mce.proxy.ClientProxy",
            serverSide = "sedridor.mce.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    private World world;
    private World worldServer;
    private Minecraft mc;

    public static boolean showHud = true;
    public static boolean showInfoScreen = false;
    private static boolean keyPressed;
    public static int moveForward = 0;
    public static boolean modifierKey = false;
    private static boolean fixFlightSpeed;

    public static boolean listSpawns = true;

    private static final String[] blockTextures = new String[] {"oreGold", "blockGold", "oreIron", "blockIron", "blockDiamond", "blockEmerald", "oreDiamond", "ice"};
    private static final String[] itemTextures = new String[] {
    	"pickaxeGold", "hatchetGold", "shovelGold", "hoeGold", "swordGold",
    	"helmetGold", "chestplateGold", "leggingsGold", "bootsGold", "ingotGold", "goldNugget",
    	"pickaxeIron", "hatchetIron", "shovelIron", "hoeIron", "swordIron",
    	"helmetIron", "chestplateIron", "leggingsIron", "bootsIron", "ingotIron",
    	"pickaxeWood", "hatchetWood", "shovelWood", "hoeWood", "swordWood",
    	"pickaxeStone", "hatchetStone", "shovelStone", "hoeStone", "swordStone",
    	"helmetLeather", "chestplateLeather", "leggingsLeather", "bootsLeather", "diamond",
    	"emerald", "plateGold", "legsGold", "bootsGold", "ingotGold", "goldNugget"//, "arcaneCharge"
    };
    private Map<String, Icon> iconMapBlocks = new HashMap();
    private Map<String, Icon> iconMapItems = new HashMap();

    public static Item arcaneCharge;
    public static Item itemIcons;

    public static boolean enableMod = true;

    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        if (!this.enableMod)
        {
            return;
        }

        MCE_Reflect.init();

        /* Initialize mod settings */
    	MCE_Settings.init(new File(event.getModConfigurationDirectory() + File.separator + "MCE-Settings.txt"));

    	/* Register the EntityLiving Handler */
//    	MinecraftForge.TERRAIN_GEN_BUS.register(this);

    	if (FMLCommonHandler.instance().getSide().isClient())
        {
            //StatListMCE.init();
        }
    }

    @PostInit
	//public boolean onTickInGame(float f, Minecraft minecraft)
    public void postInit(FMLPostInitializationEvent event)
    {
        if (!this.enableMod)
        {
            return;
        }
        /* Replace ItemRenderer */
        if (!(RenderManager.instance.itemRenderer instanceof ItemRendererMCE))
        {
      	    RenderManager.instance.itemRenderer = new ItemRendererMCE(this.mc);
        }
        /* Replace EntityRenderer */
        if (!(this.mc.entityRenderer instanceof EntityRendererMCE))
        {
        	this.mc.entityRenderer = new EntityRendererMCE(this.mc);
        }
        /* Replace EffectRenderer */
//        if (!(this.mc.effectRenderer instanceof EffectRendererMCE))
//        {
//        	this.mc.effectRenderer = new EffectRendererMCE(this.world, this.mc.renderEngine);
//        }

        /* Replace achievement popup */
        if (MCE_Settings.HideAchievements.equalsIgnoreCase("yes") && !(this.mc.guiAchievement instanceof GuiAchievementMCE))
        {
        	this.mc.guiAchievement = new GuiAchievementMCE(this.mc);
        }
        if (!MCE_Settings.HideAchievements.equalsIgnoreCase("yes") && (this.mc.guiAchievement instanceof GuiAchievementMCE))
        {
        	this.mc.guiAchievement = new GuiAchievement(this.mc);
        }

        /* Initialize mod biomes */
        if (MCE_Settings.Biomes.equalsIgnoreCase("yes"))
        {
        	BiomeGenBaseMCE.init();
        	MCE_Biomes.init();
        }
        MCE_Biomes.createBiomeMaps();

        /* Initialize mod mobs */
        if (MCE_Settings.Mobs.equalsIgnoreCase("yes"))
        {
        	MCE_Mobs.init(this);
        }

        /* Initialize mod NPCs */
        if (MCE_Settings.NPCs.equalsIgnoreCase("yes"))
        {
        	MCE_NPCs.init(this);
        }
    }

    @Init
    public void load(FMLInitializationEvent event)
    {   /* MC Enhancements */
        System.out.println("MC Enhancements... " + (Integer.valueOf(-4)));
        if (!this.enableMod)
        {
            return;
        }

        /* Save Minecraft instance */
        this.mc = FMLClientHandler.instance().getClient();
        //this.mc = FMLClientHandler.instance().getServer();

        /* Replace RenderGlobal */
        if (!(this.mc.renderGlobal instanceof RenderGlobalMCE))
        {
        	this.mc.renderGlobal = new RenderGlobalMCE(this.mc, this.mc.renderEngine);
        }

        /* Initialize mod blocks and items */
        if (MCE_Settings.Items.equalsIgnoreCase("yes"))
        {
        	MCE_Items.init();
        }

        /* Initialize mod World */
        if (MCE_Settings.World.equalsIgnoreCase("yes"))
        {
        	MCE_World.init(this.mc);
        }

        /* FORGE Start */
        proxy.init();

        /* Initialize mod tile entities */
        proxy.registerTileEntities();

        /* Initialize custom rendering */
        proxy.registerRenderers();

        /* Register the Gui Handler */
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);

        /* Register the Fuel Handler */
        //GameRegistry.registerFuelHandler(new FuelHandler());

        /* Register the Crafting Handler */
        //GameRegistry.registerCraftingHandler(new CraftingHandler());

        GameRegistry.registerWorldGenerator(new WorldGenMultiLevelMCE());
        //MinecraftForge.EVENT_BUS.register(new EventHandler());

        //StatListMCE.register();

        /* Replace vanilla icons */
        this.updateVanillaIcons(this.mc.renderEngine.textureMapBlocks);
        this.updateVanillaIcons(this.mc.renderEngine.textureMapItems);

        /* Register texture for Arcane Charge */
        this.arcaneCharge = (new Item(31400)).setUnlocalizedName("arcaneCharge");
        this.itemIcons = (new Item(31401)).setUnlocalizedName("fireball");

        /* Replace sign */
        Block.blocksList[63] = null;
    	Block sign = (new BlockSignMCE(63, TileEntitySignMCE.class, true)).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("sign");
    	GameRegistry.registerBlock(sign, "sign");
    	Block.blocksList[68] = null;
    	Block signWall = (new BlockSignMCE(68, TileEntitySignMCE.class, false)).setHardness(1.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("sign");
    	GameRegistry.registerBlock(signWall, "signWall");
        MCE_Reflect.setValue(Block.class, null, 83, sign);
        MCE_Reflect.setValue(Block.class, null, 88, signWall);
        Item signItem = (new ItemSignMCE(67)).setUnlocalizedName("sign");
        ClientRegistry.registerTileEntity(TileEntitySignMCE.class, "SignMCE", new TileEntitySignRendererMCE());
    }

	public void onTickInGame() 
	{
        if (!this.enableMod)
        {
            return;
        }

        /* Save world instance */
    	this.world = this.mc.theWorld;
    	this.worldServer = this.mc.getIntegratedServer().worldServers[0];

        /* Replace GuiIngame */
        if (!(this.mc.ingameGUI instanceof GuiIngameMCE))
        {
        	MCE_Reflect.setValue(GuiIngame.class, this.mc.ingameGUI, 0, new RenderItemMCE());
        	this.mc.ingameGUI = new GuiIngameMCE(this.mc, world);
        }

        /* Modify flight speed */
        if (!this.fixFlightSpeed)
        {
        	this.fixFlightSpeed = true;
            this.overrideFlightSpeed(0.1F);
        }

        /* Send tick to MCE_Items */
        if (MCE_Settings.Items.equalsIgnoreCase("yes"))
        {
        	MCE_Items.onTickInGame(this.mc);
        }
        /* Send tick to MCE_World */
        if (MCE_Settings.World.equalsIgnoreCase("yes"))
        {
        	MCE_World.onTickInGame(this.mc);
        }

        /* Replace player texture */
        if (!this.mc.thePlayer.getTexture().equalsIgnoreCase("/mob/char2.png"))
        {
        	//this.mc.thePlayer.texture = "/mob/char2.png";
        	MCE_Reflect.setValue(EntityLiving.class, this.mc.thePlayer, "texture", "/mob/char2.png"); //16
        }
	}

	protected void getInput(Minecraft minecraft)
    {
        if (!this.enableMod)
        {
            return;
        }
        if (Keyboard.getEventKeyState())
        {
            if (Keyboard.getEventKey() == MCE_Settings.HudKey && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && !this.keyPressed)
            {
            	this.keyPressed = true;
                this.showHud = !this.showHud;
            	//MCE_Settings.HUD = !MCE_Settings.HUD.equalsIgnoreCase("yes") ? "yes" : "no";
                //MCE_Settings.saveOptions();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_C && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && !this.keyPressed)
            {
            	this.keyPressed = true;
            	MCE_Settings.HudCoordsType++;
                if (MCE_Settings.HudCoordsType > 2)
                {
                	MCE_Settings.HudCoordsType = 0;
                }
                MCE_Settings.saveOptions();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_F && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && !this.keyPressed)
            {
            	this.keyPressed = true;
                this.setFlight();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_P && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && !this.keyPressed)
            {
            	this.keyPressed = true;
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
                {
                	MCE_Settings.PowerOverlay = !MCE_Settings.PowerOverlay.equalsIgnoreCase("yes") ? "yes" : "no";
                }
                else
                {
                	MCE_Settings.PowerTexture = !MCE_Settings.PowerTexture.equalsIgnoreCase("yes") ? "yes" : "no";
                }
                MCE_Settings.saveOptions();
            	this.mc.renderGlobal.loadRenderers();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_R && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && !this.keyPressed)
            {
            	this.keyPressed = true;
            	this.mc.renderGlobal.loadRenderers();
            }
            if (Keyboard.getEventKey() == MCE_Settings.InfoKey && !this.keyPressed)
            {
            	this.keyPressed = true;
                this.showInfoScreen = !this.showInfoScreen;
            	//MCE_Settings.InfoScreen = !MCE_Settings.InfoScreen.equalsIgnoreCase("yes") ? "yes" : "no";
                //MCE_Settings.saveOptions();
            }
        }
        else
        {
            this.keyPressed = false;
        }
        if (MCE_Settings.ArrowsMovement.equalsIgnoreCase("yes"))
        {
            if (minecraft.currentScreen == null)
            {
                if (!(this.mc.thePlayer.movementInput instanceof MovementInputFromOptionsMCE))
                {
                	this.mc.thePlayer.movementInput = new MovementInputFromOptionsMCE(this.mc.gameSettings);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_UP))
                {
                	this.moveForward = 1;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                {
                	this.moveForward = -1;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
                {
                	this.mc.thePlayer.setAngles(MCE_Settings.ArrowsTurnrate, 0);
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
                {
                	this.mc.thePlayer.setAngles(-MCE_Settings.ArrowsTurnrate, 0);
                }
                if (!Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                {
                	this.moveForward = 0;
                }
                if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) && minecraft.gameSettings.keyBindSneak.keyCode == Keyboard.KEY_LSHIFT)
                {
                	this.modifierKey = true;
                }
                else
                {
                	this.modifierKey = false;
                }
            }
            else
            {
            	this.moveForward = 0;
            	this.modifierKey = false;
            }
        }
	}

//	@ForgeSubscribe
//    public void replaceBlocksForBiome(ChunkProviderEvent.ReplaceBiomeBlocks event)
//    {
//    	if (event)
//    	{
//    		event.biome.topBlock = (byte)Block.bed.blockID;
//    	}
//    }

	public ScaledResolution getScaledResolution()
	{
		return new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
	}

	public float getScale(ScaledResolution scaledResolution)
    {
		int scale = scaledResolution.getScaleFactor() + 1 >> 1;
		if (this.mc.gameSettings.guiScale == 2)
		{
	        int var2 = 1;
            while (this.mc.displayWidth >= (var2 + 1) * 320 && this.mc.displayHeight >= (var2 + 1) * 240)
            {
                ++var2;
            }
			scale = var2 + 1 >> 1;
		}
        return (float)scale;
	}

    private void setFlight()
    {
		this.mc.thePlayer.capabilities.allowFlying = !this.mc.thePlayer.capabilities.allowFlying;
	}

    private void overrideFlightSpeed(float par1)
    {
    	boolean var1 = MCE_Reflect.setValue(PlayerCapabilities.class, this.mc.thePlayer.capabilities, 5, par1);
    	if (!var1)
    	{
			MCE_Reflect.setValue(PlayerCapabilities.class, this.mc.thePlayer.capabilities, "flySpeed", par1);
    	}
	}

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    /*protected void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[this.blockTextures.length];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon(this.blockTextures[var2]);
        }
    }*/

    protected void updateVanillaIcons(IconRegister par1IconRegister)
    {
        for (int var1 = 0; var1 < this.blockTextures.length; ++var1)
        {
            this.iconMapBlocks.put(this.blockTextures[var1], par1IconRegister.registerIcon(this.blockTextures[var1]));
        }
        for (int var2 = 0; var2 < this.itemTextures.length; ++var2)
        {
            this.iconMapItems.put(this.itemTextures[var2], par1IconRegister.registerIcon(this.itemTextures[var2]));
        }
    }

    protected Icon getIcon(String textureID)
    {
        return this.iconMapItems.get(textureID);
	}

    /**
     * Creates an explosion. Args: entity, x, y, z, strength
     */
    public static ExplosionMCE createExplosion(Entity par1Entity, World par2World, double par4, double par5, double par6, float par8, float par9, boolean par10, boolean par11)
    {
        ExplosionMCE var12 = new ExplosionMCE(par2World, par1Entity, par4, par5, par6, par8, par9);
        var12.isFlaming = par10;
        var12.destroyBlocks = par11;
        var12.doExplosionA();
        var12.doExplosionB(true);
        return var12;
    }

//    public Entity spawnEntity(int entityId, World worldClient, double posX, double posY, double posZ)
//    {
//        if (entityId == 2700)
//        {
//            return new EntityArcaneCharge(worldClient, posX, posY, posZ);
//        }
//        else if (entityId == 2702)
//        {
//            return new EntityShuriken(worldClient, posX, posY, posZ);
//        }
//        else if (entityId == 2701)
//        {
//            return new EntityLightball(worldClient, posX, posY, posZ);
//        }
//        else if (entityId == 2703)
//        {
//            return new EntityTwilightBolt(worldClient, posX, posY, posZ);
//        }
//        else if (entityId == 2704)
//        {
//            return new EntityLichBolt(worldClient, posX, posY, posZ);
//        }
//        else if (entityId == 2705)
//        {
//            return new EntityNatureBolt(worldClient, posX, posY, posZ);
//        }
//        else if (entityId == 2709)
//        {
//            return new EntityMissile(worldClient, posX, posY, posZ);
//        }
//        return null;
//	}
//
//    public Packet23VehicleSpawn getSpawnPacket(Entity entity, int type)
//    {
//        if ((entity instanceof EntityArcaneCharge) || (entity instanceof EntityShuriken) || (entity instanceof EntityLightball))
//        {
//        	return new Packet23VehicleSpawn(entity, type);
//        }
//        if ((entity instanceof EntityTwilightBolt) || (entity instanceof EntityLichBolt) || (entity instanceof EntityNatureBolt))
//        {
//        	return new Packet23VehicleSpawn(entity, type);
//        }
//        if (entity instanceof EntityMissile)
//        {
//            Entity var1 = ((EntityMissile)entity).shootingEntity;
//        	return new Packet23VehicleSpawn(entity, type, var1 != null ? var1.entityId : entity.entityId);
//        }
//        return null;
//	}
}
