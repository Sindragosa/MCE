package sedridor.mce;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringTranslate;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;
import org.lwjgl.opengl.GL11;
import sedridor.mce.items.*;

public class GuiIngameMCE extends GuiIngame
{
	private final Minecraft mc;
	private World world;
	private WorldServer worldServer;
	private StringTranslate translate = StringTranslate.getInstance();

	private static int posX;
	private static int posY;
	private static int posZ;
	private static float heading;
	private static long worldTime;
	private static String currentTime;
	private static int currentHours;
	private static int currentMins;
	private static int currentDay;
	private static int skyLight;
	private static int blockLight;
	private static String biome;
	private static int arrowCount;
	private static String itemName;
	private static String arrowType;
	private static int hasBowEquipped;
	private static double moveSpeed;
	private static String blockTarget;
	private static String entityTarget;
	private static int itemDamage;
	private static boolean isRaining;
	private static String rainTime;
	private static boolean isThundering;
	private static String thunderTime;
	private static int spawnX;
	private static int spawnY;
	private static int spawnZ;
	private static double coordsX;
	private static double coordsY;
	private static double coordsZ;
	private static double prevX;
	private static double prevY;
	private static double prevZ;
	private static long prevTime;
	private static double prevSpeed;
	private static double prevSpeed4;
	private static int ticksLeft = 50;
	private static int numGolems = 0;
	private static Village closestVillage;
	private static ScaledResolution scaledResolution;

	public GuiIngameMCE(Minecraft par1Minecraft, World par2World)
	{
		super(par1Minecraft);
		this.mc = par1Minecraft;
		this.world = par2World;
		this.worldServer = par1Minecraft.getIntegratedServer().worldServers[0];
		StringTranslate translate = StringTranslate.getInstance();
	}

	@Override
	public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
	{
		super.renderGameOverlay(par1, par2, par3, par4);

		if (this.getHudData())
		{
			this.scaledResolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			this.showHUD(MCE_Settings.HUD.equalsIgnoreCase("yes") && MCEnhancements.showHud && !MCEnhancements.showInfoScreen);
			this.showInfo(MCE_Settings.InfoScreen.equalsIgnoreCase("yes") && MCEnhancements.showInfoScreen);
		}
		if (this.mc.gameSettings.showDebugInfo)
		{
			FontRenderer fontRenderer = this.mc.fontRenderer;
			int var47 = MathHelper.floor_double(this.mc.thePlayer.posX);
			int var22 = MathHelper.floor_double(this.mc.thePlayer.posY);
			int var23 = MathHelper.floor_double(this.mc.thePlayer.posZ);
			this.drawString(fontRenderer, String.format("x: %.5f (%d) // c: %d (%d) (east)", new Object[] {Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(var47), Integer.valueOf(var47 >> 4), Integer.valueOf(var47 & 15)}), 2, 64, 14737632);
			this.drawString(fontRenderer, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY)}), 2, 72, 14737632);
			this.drawString(fontRenderer, String.format("z: %.5f (%d) // c: %d (%d) (south)", new Object[] {Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(var23), Integer.valueOf(var23 >> 4), Integer.valueOf(var23 & 15)}), 2, 80, 14737632);
			double heading = (this.mc.thePlayer.rotationYaw + 180.0F) % 360.0F;
			if (heading < 0)
			{
				heading = heading + 360.0F;
			}
			int facing = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			this.drawString(fontRenderer, "f: " + facing + " (" + Direction.directions[facing] + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw) + " (" + MathHelper.floor_double(heading) + ")", 2, 88, 14737632);

			if (this.mc.theWorld != null && this.mc.theWorld.blockExists(var47, var22, var23))
			{
				Chunk var48 = this.mc.theWorld.getChunkFromBlockCoords(var47, var23);
				this.drawString(fontRenderer, "lc: " + (var48.getTopFilledSegment() + 15) + " b: " + var48.getBiomeGenForWorldCoords(var47 & 15, var23 & 15, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var48.getSavedLightValue(EnumSkyBlock.Block, var47 & 15, var22, var23 & 15) + " sl: " + var48.getSavedLightValue(EnumSkyBlock.Sky, var47 & 15, var22, var23 & 15) + " rl: " + var48.getBlockLightValue(var47 & 15, var22, var23 & 15, 0), 2, 96, 14737632);
			}

			this.drawString(fontRenderer, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] {Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround), Integer.valueOf(this.mc.theWorld.getHeightValue(var47, var23))}), 2, 104, 14737632);

			long worldTime = this.mc.theWorld.getWorldTime();
			int varHours = (int)((worldTime + 6000L) % 24000L / 1000L);
			int varMins = (int)(worldTime % 1000L * 60L / 1000L);
			this.drawString(fontRenderer, String.format("Time: %02d:%02d (%d)", varHours, varMins, worldTime), 2, 120, 14737632);
			this.drawString(fontRenderer, String.format("Spawn: %d, %d, %d", this.mc.theWorld.getWorldInfo().getSpawnX(), this.mc.theWorld.getWorldInfo().getSpawnY(), this.mc.theWorld.getWorldInfo().getSpawnZ()), 2, 128, 14737632);

			World currentServer = this.mc.getIntegratedServer().worldServers[0];
			if (currentServer.getSeed() != 0)
			{
				this.drawString(fontRenderer, "Seed: " + currentServer.getSeed(), 2, 144, 14737632);
			}
		}
	}

	private boolean getHudData()
	{
		this.world = this.mc.theWorld;
		this.worldServer = this.mc.getIntegratedServer().worldServers[0];
		if (this.world != null)
		{
			EntityClientPlayerMP thePlayer = this.mc.thePlayer;
			WorldInfo worldInfo = this.world.getWorldInfo();
			FontRenderer fontRenderer = this.mc.fontRenderer;
			int posX = MathHelper.floor_double(thePlayer.posX);
			int posY = MathHelper.floor_double(thePlayer.posY);
			int posZ = MathHelper.floor_double(thePlayer.posZ);
			float heading = (thePlayer.rotationYaw + 180.0F) % 360.0F;
			if (heading < 0)
			{
				heading = heading + 360.0F;
			}

			long worldTime = worldInfo.getWorldTime();
			//String currentTime = this.getCurrentTime(worldTime);
			int varHours = (int)((worldTime + 6000L) % 24000L / 1000L);
			int varMins = (int)(worldTime % 1000L * 60L / 1000L);
			int d = (int)((worldTime + 6000L) / 24000L) + 1;
			String currentTime = String.format("%02d:%02d (%d) \u00a7r- Day:\u00a7f " + d, varHours, varMins, GuiIngameMCE.worldTime);

			int skyLight = EnumSkyBlock.Sky.defaultLightValue - this.world.calculateSkylightSubtracted(1.0F);
			int blockLight = this.worldServer.getBlockLightValue(posX, posY, posZ);
			String biome = this.worldServer.getBiomeGenForCoords(posX, posZ).biomeName;

			boolean isRaining = this.worldServer.getWorldInfo().isRaining();
			boolean isThundering = this.worldServer.getWorldInfo().isThundering();
			int rainS = this.worldServer.getWorldInfo().getRainTime() / 20;
			int rainM = rainS / 60;
			String rainTime = (rainM > 0 ? rainM + "m " + (rainS % 60) + "s" : rainS + "s");
			int thunderS = this.worldServer.getWorldInfo().getThunderTime() / 20;
			int thunderM = thunderS / 60;
			String thunderTime = (thunderM > 0 ? thunderM + "m " + (thunderS % 60) + "s" : thunderS + "s");

			ItemStack equippedItem = thePlayer.getCurrentEquippedItem();
			int itemDamage = 0;
			String itemName = "";

			if (equippedItem != null)
			{
				if (equippedItem.getMaxDamage() > 0)
				{
					itemDamage = equippedItem.getMaxDamage() - equippedItem.getItemDamage() + 1;
				}

				itemName = this.getItemNameandInformation(equippedItem).get(0) + " (ID " + equippedItem.itemID + (equippedItem.getHasSubtypes() ? " : " + equippedItem.getItemDamage() : "") + ")";

				if (itemName == null || itemName.equalsIgnoreCase(""))
				{
					itemName = "Unknown item";
				}
			}
			int hasBowEquipped = 0;
			if (equippedItem != null)
			{
				hasBowEquipped = (equippedItem.getItem() instanceof ItemBow || equippedItem.getItem() instanceof ItemMetalBow ? 1 : /*equippedItem.getItem() instanceof ItemMissileLauncher ? 2 :*/ 0);
			}

			int arrowCount = 0;
			ItemStack[] mainInventory = thePlayer.inventory.mainInventory;
			int itemCount = mainInventory.length;
			int item = (hasBowEquipped == 2 ? MCE_Items.Missile.itemID : Item.arrow.itemID);
			String arrowType = (hasBowEquipped == 2 ? "Missiles" : "Arrows");
			for (int i = 0; i < itemCount; ++i)
			{
				ItemStack theItem = mainInventory[i];
				if (theItem != null && theItem.itemID == item)
				{
					arrowCount += theItem.stackSize;
				}
			}

			if (thePlayer.ridingEntity != null)
			{
				double motionX = thePlayer.ridingEntity.posX - thePlayer.ridingEntity.lastTickPosX;
				double motionY = thePlayer.ridingEntity.posY - thePlayer.ridingEntity.lastTickPosY;
				double motionZ = thePlayer.ridingEntity.posZ - thePlayer.ridingEntity.lastTickPosZ;
				GuiIngameMCE.moveSpeed = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ + (!this.mc.thePlayer.ridingEntity.onGround ? motionY * motionY : 0)) * 20;
			}
			else
			{
				double motionX = thePlayer.posX - thePlayer.lastTickPosX;
				double motionY = thePlayer.posY - thePlayer.lastTickPosY;
				double motionZ = thePlayer.posZ - thePlayer.lastTickPosZ;
				GuiIngameMCE.moveSpeed = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ + (!this.mc.thePlayer.onGround ? motionY * motionY : 0)) * 20;
			}

			if (MCE_Settings.HudBlockTarget.equalsIgnoreCase("yes") || MCE_Settings.InfoScreenBlockTarget.equalsIgnoreCase("yes"))
			{
				GuiIngameMCE.blockTarget = this.getBlockTarget();
			}
			if (MCE_Settings.HudEntityTarget.equalsIgnoreCase("yes") || MCE_Settings.InfoScreenEntityTarget.equalsIgnoreCase("yes"))
			{
				GuiIngameMCE.entityTarget = this.getEntityTarget();
			}

			GuiIngameMCE.posX = posX;
			GuiIngameMCE.posY = posY;
			GuiIngameMCE.posZ = posZ;
			//this.coordsX = thePlayer.posX;
			//this.coordsY = thePlayer.boundingBox.minY;
			//this.coordsZ = thePlayer.posZ;
			GuiIngameMCE.heading = heading;
			GuiIngameMCE.worldTime = worldTime;
			GuiIngameMCE.currentTime = currentTime;
			GuiIngameMCE.currentHours = varHours;
			GuiIngameMCE.currentMins = varMins;
			GuiIngameMCE.currentDay = d;
			GuiIngameMCE.skyLight = skyLight;
			GuiIngameMCE.blockLight = blockLight;
			GuiIngameMCE.biome = biome;
			GuiIngameMCE.arrowCount = arrowCount;
			GuiIngameMCE.itemName = itemName;
			GuiIngameMCE.arrowType = arrowType;
			GuiIngameMCE.hasBowEquipped = hasBowEquipped;
			GuiIngameMCE.itemDamage = itemDamage;
			GuiIngameMCE.isRaining = isRaining;
			GuiIngameMCE.rainTime = rainTime;
			GuiIngameMCE.isThundering = isThundering;
			GuiIngameMCE.thunderTime = thunderTime;
			//this.spawnX = worldInfo.getSpawnX();
			//this.spawnY = worldInfo.getSpawnY();
			//this.spawnZ = worldInfo.getSpawnZ();
			return true;
		}
		return false;
	}

	private void showHUD(boolean show)
	{
		if (show && !this.mc.gameSettings.showDebugInfo)
		{
			if (this.world != null)
			{
				EntityClientPlayerMP thePlayer = this.mc.thePlayer;
				WorldInfo worldInfo = this.world.getWorldInfo();
				FontRenderer fontRenderer = this.mc.fontRenderer;

				GL11.glPushMatrix();
                GL11.glScaled(1.0D / (double)this.scaledResolution.getScaleFactor(), 1.0D / (double)this.scaledResolution.getScaleFactor(), 1.0D);
				float scale = MCEnhancements.instance.getScale(this.scaledResolution);
				GL11.glScalef(scale, scale, 1.0F);
				int prevPos = 0;
				if (MCE_Settings.HudBiome.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Biome:\u00a7f " + GuiIngameMCE.biome, 4, 40, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.HudCoords.equalsIgnoreCase("yes"))
				{
					if (MCE_Settings.HudCoordsType == 1)
					{
						fontRenderer.drawStringWithShadow(String.format("Coords:\u00a7f %s %.2f, %.2f, %s %.2f", thePlayer.posX < 0 ? "W" : "E", MathHelper.abs((float)thePlayer.posX), thePlayer.boundingBox.minY, thePlayer.posZ < 0 ? "N" : "S", MathHelper.abs((float)thePlayer.posZ)), 4, 40 + prevPos, 0xffe000);
					}
					else if (MCE_Settings.HudCoordsType == 2)
					{
						long var2 = System.currentTimeMillis();
						float posX = MathHelper.abs((float)thePlayer.posX) / 100.0F;
						float posX1 = (posX % 1) * 60.0F;
						float posX2 = (posX1 % 1) * 60.0F;
						float posZ = MathHelper.abs((float)thePlayer.posZ) / 100.0F;
						float posZ1 = (posZ % 1) * 60.0F;
						float posZ2 = (posZ1 % 1) * 60.0F;
						fontRenderer.drawStringWithShadow(String.format("Coords:\u00a7f %s %03d %02d %02d - %s %03d %02d %02d - %.2f", thePlayer.posZ < 0 ? "N" : "S", MathHelper.floor_float(posZ), MathHelper.floor_float(posZ1), MathHelper.floor_float(posZ2), thePlayer.posX < 0 ? "W" : "E", MathHelper.floor_float(posX), MathHelper.floor_float(posX1), MathHelper.floor_float(posX2), thePlayer.boundingBox.minY), 4, 40 + prevPos, 0xffe000);
					}
					else
					{
						fontRenderer.drawStringWithShadow(String.format("Coords:\u00a7f %.2f, %.2f, %.2f", thePlayer.posX, thePlayer.boundingBox.minY, thePlayer.posZ), 4, 40 + prevPos, 0xffe000);
					}
					prevPos += 10;
				}
				if (MCE_Settings.HudHeading.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Heading:\u00a7f %d", (int)GuiIngameMCE.heading), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.HudSkylight.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Skylight level:\u00a7f " + GuiIngameMCE.skyLight, 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.HudLight.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Light level:\u00a7f " + GuiIngameMCE.blockLight, 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.HudVelocity.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Velocity:\u00a7f %.3fm/s (%.3fkmh)", GuiIngameMCE.moveSpeed, GuiIngameMCE.moveSpeed * 3.6F), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.HudSpawn.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Spawn:\u00a7f %d, %d, %d", worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ()), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				prevPos += 10;
				if (MCE_Settings.HudTime.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Time:\u00a7f %02d:%02d (%d) \u00a7r- Day:\u00a7f %d", GuiIngameMCE.currentHours, GuiIngameMCE.currentMins, GuiIngameMCE.worldTime, GuiIngameMCE.currentDay), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.HudRain.equalsIgnoreCase("yes"))
				{
					if (GuiIngameMCE.rainTime != null)
					{
						fontRenderer.drawStringWithShadow("Rain " + (GuiIngameMCE.isRaining ? "ends" : "starts") + ":\u00a7f " + GuiIngameMCE.rainTime, 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
				}
				if (MCE_Settings.HudThunder.equalsIgnoreCase("yes"))
				{
					if (GuiIngameMCE.thunderTime != null)
					{
						fontRenderer.drawStringWithShadow("Thunder " + (GuiIngameMCE.isThundering ? "ends" : "starts") + ":\u00a7f " + GuiIngameMCE.thunderTime, 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
				}
				prevPos += 10;
				if (MCE_Settings.HudEquipped.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Equipped:\u00a7f " + GuiIngameMCE.itemName/* + (this.itemDamage > 0 ? " - \u00a7rDurability:\u00a7f " + this.itemDamage : "") + (this.hasBowEquipped ? " - \u00a7rArrows:\u00a7f " + this.arrowCount : "")*/, 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
					if ((MCE_Settings.HudDurability.equalsIgnoreCase("yes")) && (GuiIngameMCE.itemDamage > 0 || GuiIngameMCE.hasBowEquipped > 0))
					{
						fontRenderer.drawStringWithShadow((GuiIngameMCE.itemDamage > 0 ? "Durability:\u00a7f " + GuiIngameMCE.itemDamage : "") + (MCE_Settings.HudArrows.equalsIgnoreCase("yes") && GuiIngameMCE.hasBowEquipped > 0 ? (GuiIngameMCE.itemDamage > 0 ? " - \u00a7r" : "") + GuiIngameMCE.arrowType + ":\u00a7f " + GuiIngameMCE.arrowCount : ""), 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
					prevPos += 10;
				}

				if (MCE_Settings.HudBlockTarget.equalsIgnoreCase("yes"))
				{
					if (GuiIngameMCE.blockTarget != null)
					{
						fontRenderer.drawStringWithShadow(GuiIngameMCE.blockTarget, 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
				}
				if (MCE_Settings.HudEntityTarget.equalsIgnoreCase("yes"))
				{
					if (GuiIngameMCE.entityTarget != null)
					{
						fontRenderer.drawStringWithShadow(GuiIngameMCE.entityTarget, 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
				}

				GL11.glPopMatrix();
			}
		}
	}

	private void showInfo(boolean show)
	{
		if (show && !this.mc.gameSettings.showDebugInfo)
		{
			if (this.world != null)
			{
				EntityClientPlayerMP thePlayer = this.mc.thePlayer;
				WorldInfo worldInfo = this.world.getWorldInfo();
				FontRenderer fontRenderer = this.mc.fontRenderer;

				GL11.glPushMatrix();
                GL11.glScaled(1.0D / (double)this.scaledResolution.getScaleFactor(), 1.0D / (double)this.scaledResolution.getScaleFactor(), 1.0D);
				float scale = MCEnhancements.instance.getScale(this.scaledResolution);
				GL11.glScalef(scale, scale, 1.0F);
				int prevPos = 0;
				if (MCE_Settings.InfoScreenPlayer.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Player:\u00a7f " + thePlayer.getEntityName() + " (ID " + thePlayer.entityId + ")", 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenBiome.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Biome:\u00a7f " + GuiIngameMCE.biome, 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenChunk.equalsIgnoreCase("yes"))
				{
					Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(GuiIngameMCE.posX, GuiIngameMCE.posZ);
					fontRenderer.drawStringWithShadow(String.format("Chunk:\u00a7f %d, %d", chunk.xPosition, chunk.zPosition), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenCoords.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Coords:\u00a7f %.2f, %.2f, %.2f", thePlayer.posX, thePlayer.boundingBox.minY, thePlayer.posZ), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenHeading.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Heading:\u00a7f %d", (int)GuiIngameMCE.heading), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenSkylight.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Skylight level:\u00a7f " + GuiIngameMCE.skyLight, 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenLight.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Light level:\u00a7f " + GuiIngameMCE.blockLight + " (" + this.worldServer.getFullBlockLightValue(posX, posY, posZ) + ")", 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenVelocity.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Velocity:\u00a7f %.3fm/s (%.3fkmh)", GuiIngameMCE.moveSpeed, GuiIngameMCE.moveSpeed * 3.6F), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenSpawn.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Spawn:\u00a7f %d, %d, %d", worldInfo.getSpawnX(), worldInfo.getSpawnY(), worldInfo.getSpawnZ()), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenSeed.equalsIgnoreCase("yes") && this.worldServer.getSeed() != 0)
				{
					fontRenderer.drawStringWithShadow("Seed:\u00a7f " + this.worldServer.getSeed(), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
					fontRenderer.drawStringWithShadow("World:\u00a7f " + this.worldServer.getWorldInfo().getWorldName(), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
					fontRenderer.drawStringWithShadow("WorldType:\u00a7f " + this.translate.translateKey(this.worldServer.getWorldInfo().getTerrainType().getTranslateName()), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				prevPos += 10;
				if (MCE_Settings.InfoScreenTime.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("Time:\u00a7f %02d:%02d (%d) \u00a7r- Day:\u00a7f %d", GuiIngameMCE.currentHours, GuiIngameMCE.currentMins, GuiIngameMCE.worldTime, GuiIngameMCE.currentDay), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenTime.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow(String.format("TotalWorldTime:\u00a7f %d", this.world.getTotalWorldTime()), 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
				}
				if (MCE_Settings.InfoScreenRain.equalsIgnoreCase("yes"))
				{
					if (GuiIngameMCE.rainTime != null)
					{
						fontRenderer.drawStringWithShadow("Rain " + (GuiIngameMCE.isRaining ? "ends" : "starts") + ":\u00a7f " + GuiIngameMCE.rainTime, 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
				}
				if (MCE_Settings.InfoScreenThunder.equalsIgnoreCase("yes"))
				{
					if (GuiIngameMCE.thunderTime != null)
					{
						fontRenderer.drawStringWithShadow("Thunder " + (GuiIngameMCE.isThundering ? "ends" : "starts") + ":\u00a7f " + GuiIngameMCE.thunderTime, 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
				}
				prevPos += 10;
				if (MCE_Settings.InfoScreenEquipped.equalsIgnoreCase("yes"))
				{
					fontRenderer.drawStringWithShadow("Equipped:\u00a7f " + GuiIngameMCE.itemName/* + (this.itemDamage > 0 ? " - \u00a7rDurability:\u00a7f " + this.itemDamage : "") + (this.hasBowEquipped ? " - \u00a7rArrows:\u00a7f " + this.arrowCount : "")*/, 4, 40 + prevPos, 0xffe000);
					prevPos += 10;
					if ((MCE_Settings.InfoScreenDurability.equalsIgnoreCase("yes")) && (GuiIngameMCE.itemDamage > 0 || GuiIngameMCE.hasBowEquipped > 0))
					{
						fontRenderer.drawStringWithShadow((GuiIngameMCE.itemDamage > 0 ? "Durability:\u00a7f " + GuiIngameMCE.itemDamage : "") + (MCE_Settings.InfoScreenArrows.equalsIgnoreCase("yes") && GuiIngameMCE.hasBowEquipped > 0 ? (GuiIngameMCE.itemDamage > 0 ? " - \u00a7r" : "") + GuiIngameMCE.arrowType + ":\u00a7f " + GuiIngameMCE.arrowCount : ""), 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
					prevPos += 10;
				}

				if (MCE_Settings.InfoScreenBlockTarget.equalsIgnoreCase("yes"))
				{
					if (GuiIngameMCE.blockTarget != null)
					{
						fontRenderer.drawStringWithShadow(this.getBlockTarget(true), 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
				}
				if (MCE_Settings.InfoScreenEntityTarget.equalsIgnoreCase("yes"))
				{
					if (GuiIngameMCE.entityTarget != null)
					{
						fontRenderer.drawStringWithShadow(GuiIngameMCE.entityTarget, 4, 40 + prevPos, 0xffe000);
						prevPos += 10;
					}
				}
				if (MCE_Settings.InfoScreenVillageInfo.equalsIgnoreCase("yes"))
				{
					if (this.mc.isSingleplayer())
					{
						if (--GuiIngameMCE.ticksLeft < 0)
						{
							GuiIngameMCE.ticksLeft = 50;
							VillageCollection villageCollection = this.worldServer.villageCollectionObj;
							GuiIngameMCE.closestVillage = villageCollection.findNearestVillage(MathHelper.floor_double(this.mc.thePlayer.posX), MathHelper.floor_double(this.mc.thePlayer.posY), MathHelper.floor_double(this.mc.thePlayer.posZ), 500);
							GuiIngameMCE.numGolems = 0;
							if (GuiIngameMCE.closestVillage != null)
							{
								List var1 = this.worldServer.getEntitiesWithinAABB(EntityIronGolem.class, AxisAlignedBB.getAABBPool().getAABB(GuiIngameMCE.closestVillage.getCenter().posX - GuiIngameMCE.closestVillage.getVillageRadius(), GuiIngameMCE.closestVillage.getCenter().posY - 4, GuiIngameMCE.closestVillage.getCenter().posZ - GuiIngameMCE.closestVillage.getVillageRadius(), GuiIngameMCE.closestVillage.getCenter().posX + GuiIngameMCE.closestVillage.getVillageRadius(), GuiIngameMCE.closestVillage.getCenter().posY + 4, GuiIngameMCE.closestVillage.getCenter().posZ + GuiIngameMCE.closestVillage.getVillageRadius()));
								GuiIngameMCE.numGolems = var1.size();
							}
						}

						int color = 14737632;
						prevPos += 10;

						if (GuiIngameMCE.closestVillage != null)
						{
							int villageRadius = GuiIngameMCE.closestVillage.getVillageRadius();
							ChunkCoordinates chunkCoords = GuiIngameMCE.closestVillage.getCenter();
							double distanceToVillage = Math.sqrt(chunkCoords.getDistanceSquared(MathHelper.floor_double(this.mc.thePlayer.posX), MathHelper.floor_double(this.mc.thePlayer.posY), MathHelper.floor_double(this.mc.thePlayer.posZ)));
							double var40 = distanceToVillage - villageRadius;
							boolean villageClose = false;
							int var62;

							if (var40 < 0.0D)
							{
								fontRenderer.drawStringWithShadow("Inside Village (ID: " + GuiIngameMCE.closestVillage.hashCode() + ")", 4, 40 + prevPos, color);
								prevPos += 10;
								villageClose = true;
							}
							else if (var40 < 32.0D)
							{
								fontRenderer.drawStringWithShadow("Nearby Village found (ID: " + GuiIngameMCE.closestVillage.hashCode() + ")", 4, 40 + prevPos, color);
								prevPos += 10;
								villageClose = true;
							}
							else
							{
								fontRenderer.drawStringWithShadow("Distant Village found (" + GuiIngameMCE.closestVillage.hashCode() + ")", 4, 40 + prevPos, color);
								prevPos += 10;
							}

							fontRenderer.drawStringWithShadow("Center: (" + chunkCoords.posX + ", " + chunkCoords.posY + ", " + chunkCoords.posZ + "), Radius: " + villageRadius, 4, 40 + prevPos, color);
							prevPos += 10;
							fontRenderer.drawStringWithShadow("Distance to Center: " + (int)distanceToVillage, 4, 40 + prevPos, color);
							prevPos += 10;

							if (villageClose)
							{
								int villagers = (int)((GuiIngameMCE.closestVillage.getNumVillageDoors()) * 0.35D);
								fontRenderer.drawStringWithShadow("Villagers: " + GuiIngameMCE.closestVillage.getNumVillagers() + " (" + villagers + "), Golems: " + GuiIngameMCE.numGolems + " (" + GuiIngameMCE.closestVillage.getNumVillagers() / 16 + ")", 4, 40 + prevPos, color);
								prevPos += 10;
								int housesColor = GuiIngameMCE.closestVillage.getNumVillageDoors() > 20 ? 1105936 : 14684176;
								fontRenderer.drawStringWithShadow("Houses: " + GuiIngameMCE.closestVillage.getNumVillageDoors(), 4, 40 + prevPos, housesColor);
								prevPos += 10;

								boolean golemSpawnZone = true;
								if (MathHelper.floor_double(this.mc.thePlayer.posX) > GuiIngameMCE.closestVillage.getCenter().posX + 7)
								{
									golemSpawnZone = false;
								}
								if (MathHelper.floor_double(this.mc.thePlayer.posX) < GuiIngameMCE.closestVillage.getCenter().posX - 8)
								{
									golemSpawnZone = false;
								}
								if (MathHelper.floor_double(this.mc.thePlayer.posY - 1.0D) > GuiIngameMCE.closestVillage.getCenter().posY + 2)
								{
									golemSpawnZone = false;
								}
								if (MathHelper.floor_double(this.mc.thePlayer.posY - 1.0D) < GuiIngameMCE.closestVillage.getCenter().posY - 3)
								{
									golemSpawnZone = false;
								}
								if (MathHelper.floor_double(this.mc.thePlayer.posZ) > GuiIngameMCE.closestVillage.getCenter().posZ + 7)
								{
									golemSpawnZone = false;
								}
								if (MathHelper.floor_double(this.mc.thePlayer.posZ) < GuiIngameMCE.closestVillage.getCenter().posZ - 8)
								{
									golemSpawnZone = false;
								}
								if (golemSpawnZone)
								{
									fontRenderer.drawStringWithShadow("Inside Golem Spawn Zone", 4, 40 + prevPos, 14737632);
									prevPos += 10;
								}
							}
						}
						else
						{
							fontRenderer.drawStringWithShadow("No Village found", 4, 40 + prevPos, color);
							prevPos += 10;
						}
					}
				}
				GL11.glPopMatrix();
			}
		}
	}

	private String getCurrentTime(long worldTime)
	{
		int h = (int)((worldTime / 1000L + 6L) % 24L);
		float m = worldTime % 24000L;
		m %= 1000.0F;
		m = (m / 1000.0F * 60.0F);
		String s;
		if (m < 10)
		{
			StringBuilder sBuilder = new StringBuilder(h);
			sBuilder.append(":");
			sBuilder.append("0");
			sBuilder.append((int)m);
			s = sBuilder.toString();
		}
		else
		{
			StringBuilder sBuilder = new StringBuilder(h);
			sBuilder.append(":");
			sBuilder.append((int)m);
			s = sBuilder.toString();
		}
		return s;
	}

	private String getBlockTarget()
	{
		return this.getBlockTarget(false);
	}

	private String getBlockTarget(boolean showMeta)
	{
		if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
		{
			EntityClientPlayerMP entityPlayer = this.mc.thePlayer;
			MovingObjectPosition movingObjectPosition = this.mc.objectMouseOver;

			int blockId = this.world.getBlockId(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
			int blockMeta = this.world.getBlockMetadata(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
			if (blockId > 0)
			{
				ItemStack itemStack = new ItemStack(Block.blocksList[blockId], 1, blockMeta);
				if (itemStack != null)
				{
					List itemInfo = this.getItemNameandInformation(itemStack);
					boolean hasSubtypes = itemStack.getHasSubtypes();
					if (!itemInfo.isEmpty())
					{
						String blockName = (String)itemInfo.get(0);
						return "Block:\u00a7f " + blockName + " (ID " + blockId + (hasSubtypes || showMeta ? " : " + blockMeta : "") + ")";
					}
				}
			}
		}
		return null;
	}

	private String getEntityTarget()
	{
		if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY)
		{
			Entity entityHit = this.mc.objectMouseOver.entityHit;
			if (entityHit instanceof EntityLiving)
			{
				//EntityLiving entityLiving = (EntityLiving)entityHit;
				int entityId = entityHit.entityId;
				EntityLiving entityLiving = (EntityLiving)this.worldServer.getEntityByID(entityId);
				if (entityLiving != null)
				{
					String entityName = entityLiving.getEntityName();
					String health = (entityLiving.getHealth() > 0 ? entityLiving.getHealth() : 0) + "/" + entityLiving.getMaxHealth();
					return "Entity:\u00a7f " + entityName + " (ID " + entityLiving.entityId + ")" + " \u00a7r- Health:\u00a7f " + health;
				}
			}
		}
		return null;
	}
	/**
	 * gets a list of strings representing the item name and successive extra data, eg Enchantments and potion effects
	 */
	private List getItemNameandInformation(ItemStack itemStack)
	{
		ArrayList var1 = new ArrayList();
		Item var2 = Item.itemsList[itemStack.itemID];
		if (var2 != null)
		{
			var1.add(itemStack.getDisplayName());
			var2.addInformation(itemStack, this.mc.thePlayer, var1, this.mc.gameSettings.advancedItemTooltips);

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
		}

		return var1;
	}
	private String getBlockBrightness()
	{
		if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE)
		{
			MovingObjectPosition movingObjectPosition = this.mc.objectMouseOver;
			int blockId = this.world.getBlockId(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
			if (blockId > 0)
			{
				int brightness = Block.blocksList[blockId].getMixedBrightnessForBlock(this.world, movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
				float brightness2 = Block.blocksList[blockId].getBlockBrightness(this.world, movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
				return "Block Brightness:\u00a7f " + brightness + " " + brightness2;
			}
		}
		return "Block Brightness:\u00a7f Unknown";
	}
}
