package sedridor.mce;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.util.StatCollector;

/*
 */
public class mod_BlocksItems extends BaseMod
{
    private final Minecraft mc = Minecraft.getMinecraft();
    private File optionsFile;
    private static boolean keyPressed;

    public mod_BlocksItems()
    {
        ModLoader.setInGameHook(this, true, false);
    }

	public void load()
    {
        this.optionsFile = new File(this.mc.mcDataDir, "MCE-BlocksItems.txt");
    }

    public boolean onTickInGame(float f, Minecraft minecraft)
    {
        if (Keyboard.getEventKeyState())
        {
            if (Keyboard.getEventKey() == Keyboard.KEY_B && (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) && !this.keyPressed)
            {
            	this.keyPressed = true;
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
                {
                    this.saveOptions();
                }
            }
       }
        else
        {
            this.keyPressed = false;
        }

        return true;
    }

    /**
     * Saves the options to the options file.
     */
//	public void saveOptions()
//    {
//        try
//        {
//            PrintWriter var1 = new PrintWriter(new FileWriter(this.optionsFile));
//            ArrayList emptyIDs = new ArrayList();
//            ArrayList emptyShiftedIDs = new ArrayList();
//            var1.println("BLOCKS");
//            var1.println("------");
//            for (int var2 = 1; var2 < 2048; ++var2)
//            {
//                if (Item.itemsList[var2] != null)
//                {
//                	Item var4 = Item.itemsList[var2];
//                    if (var2 < 256)
//                    {
//                        var1.println(var4.itemID + "	[" + StatCollector.translateToLocal(var4.getUnlocalizedName() + ".name") + "]");
//                    }
//                    else
//                    {
//                        var1.println((var4.itemID - 256) + "	(" + var4.itemID + ")	[" + StatCollector.translateToLocal(var4.getUnlocalizedName() + ".name") + "]");
//                    }
//
//                    ItemStack itemStack = new ItemStack(Item.itemsList[var2], 1, 0);
//                    List itemInfo = getItemNameandInformation(itemStack);
//                    boolean hasSubtypes = itemStack.getHasSubtypes();
//                    if (!itemInfo.isEmpty() && hasSubtypes)
//                    {
//                        String itemName = (String)itemInfo.get(0);
//                        var1.println("-	0		" + itemName);
//
//                        ArrayList items = new ArrayList();
//                        items.add(itemName);
//
//                        for (int var5 = 0; var5 < 2048; ++var5)
//                        {
//                            itemStack = new ItemStack(Item.itemsList[var2], 1, var5);
//                            itemInfo = getItemNameandInformation(itemStack);
//
//                            itemName = (String)itemInfo.get(0);
//                            if (itemName != null && itemName.length() > 0 && !items.contains(itemName))
//                            {
//                                items.add(itemName);
//                                var1.println("-	" + var5 + "		" + itemName);
//                            }
//                        }
//                        if (var2 == 383)
//                        {
//                            for (int var5 = -255; var5 < 0; ++var5)
//                            {
//                                itemStack = new ItemStack(Item.itemsList[var2], 1, var5);
//                                itemInfo = getItemNameandInformation(itemStack);
//
//                                itemName = (String)itemInfo.get(0);
//                                if (itemName != null && itemName.length() > 0 && !items.contains(itemName))
//                                {
//                                    items.add(itemName);
//                                    var1.println("-	" + var5 + "		" + itemName);
//                                }
//                            }
//                        }
//                    }
//                }
//                else
//                {
//                	emptyIDs.add(var2 - (var2 < 256 ? 0 : 256));
//                    if (var2 > 255)
//                    {
//                    	emptyShiftedIDs.add(var2);
//                    }
//                }
//                if (var2 == 255)
//                {
//                    var1.println("");
//                    var1.println("-- Empty IDs: " + getEmptyIDs(emptyIDs));
//                    var1.println("");
//                    var1.println("");
//                    var1.println("ITEMS");
//                    var1.println("-----");
//                    emptyIDs = new ArrayList();
//                }
//            }
//            var1.println("");
//            var1.println("-- Empty IDs: " + getEmptyIDs(emptyIDs));
//            var1.println("-- Empty Shifted IDs: " + getEmptyIDs(emptyShiftedIDs));
//            var1.close();
//        }
//        catch (Exception var6)
//        {
//            System.out.println("MinecraftEnhanced: Failed to save data");
//            var6.printStackTrace();
//        }
//    }

    /**
     * Saves the options to the options file.
     */
	public void saveOptions()
    {
        ArrayList blocksList = new ArrayList();
        ArrayList itemsList = new ArrayList();
        ArrayList emptyBlockIDs = new ArrayList();
        ArrayList emptyItemIDs = new ArrayList();
        ArrayList emptyShiftedIDs = new ArrayList();

        for (int var2 = 1; var2 < 32000; ++var2)
        {
            if (Item.itemsList[var2] != null)
            {
                boolean blockItem = var2 < 4096 && Block.blocksList[var2] != null && !Block.blocksList[var2].getUnlocalizedName().equals("tile.ForgeFiller");
            	Item var4 = Item.itemsList[var2];
                if (blockItem)
                {
                	blocksList.add(var4.itemID + "	[" + StatCollector.translateToLocal(var4.getUnlocalizedName() + ".name") + "]");
                }
                else
                {
                	itemsList.add((var4.itemID - 256) + "	(" + var4.itemID + ")	[" + StatCollector.translateToLocal(var4.getUnlocalizedName() + ".name") + "]");
                }

                ItemStack itemStack = new ItemStack(Item.itemsList[var2], 1, 0);
                List itemInfo = getItemNameandInformation(itemStack);
                boolean hasSubtypes = itemStack.getHasSubtypes();
                if (!itemInfo.isEmpty() && hasSubtypes)
                {
                    String itemName = (String)itemInfo.get(0);
                    if (blockItem)
                    {
                    	blocksList.add("-	0		" + itemName);
                    }
                    else
                    {
                        itemsList.add("-	0		" + itemName);
                    }

                    ArrayList items = new ArrayList();
                    items.add(itemName);

                    int metaRange = blockItem ? 16 : 2048;
                    for (int var5 = 0; var5 < metaRange; ++var5)
                    {
                        itemStack = new ItemStack(Item.itemsList[var2], 1, var5);
                        itemInfo = getItemNameandInformation(itemStack);

                        itemName = (String)itemInfo.get(0);
                        if (itemName != null && itemName.length() > 0 && !items.contains(itemName))
                        {
                            items.add(itemName);
                            if (blockItem)
                            {
                            	blocksList.add("-	" + var5 + "		" + itemName);
                            }
                            else
                            {
                                itemsList.add("-	" + var5 + "		" + itemName);
                            }
                        }
                    }
                    if (var2 == 383)
                    {
                        for (int var5 = -255; var5 < 0; ++var5)
                        {
                            itemStack = new ItemStack(Item.itemsList[var2], 1, var5);
                            itemInfo = getItemNameandInformation(itemStack);

                            itemName = (String)itemInfo.get(0);
                            if (itemName != null && itemName.length() > 0 && !items.contains(itemName))
                            {
                                items.add(itemName);
                                itemsList.add("-	" + var5 + "		" + itemName);
                            }
                        }
                    }
                }
            }
            else
            {
                if (var2 < 4096)
                {
                	emptyBlockIDs.add(var2);
                }
                if (var2 > 255)
                {
                	emptyItemIDs.add(var2 - 256);
                	emptyShiftedIDs.add(var2);
                }
            }
        }

        try
        {
            PrintWriter var1 = new PrintWriter(new FileWriter(this.optionsFile));
            var1.println("BLOCKS");
            var1.println("------");
            for (int var5 = 0; var5 < blocksList.size(); ++var5)
            {
                var1.println(blocksList.get(var5));
            }
            var1.println("");
            var1.println("-- Empty IDs: " + getEmptyIDs(emptyBlockIDs));
            var1.println("");
            var1.println("");
            var1.println("ITEMS");
            var1.println("-----");
            for (int var5 = 0; var5 < itemsList.size(); ++var5)
            {
                var1.println(itemsList.get(var5));
            }
            var1.println("");
            var1.println("-- Empty IDs: " + getEmptyIDs(emptyItemIDs));
            var1.println("-- Empty Shifted IDs: " + getEmptyIDs(emptyShiftedIDs));
            var1.close();
        }
        catch (Exception var6)
        {
            System.out.println("MinecraftEnhanced: Failed to save data");
            var6.printStackTrace();
        }
    }

	private String getEmptyIDs(ArrayList emptyIDs)
    {
    	StringBuilder idList = new StringBuilder();
        int prev1 = 0;
        int prev2 = 0;
        for (int var2 = 0; var2 < emptyIDs.size(); ++var2)
        {
            int item = (Integer)emptyIDs.get(var2);
            if (var2 == 0)
            {
            	idList.append(item);
            	prev1 = item;
            	prev2 = item;
            }
            else if (item - 1 == prev2)
            {
            	prev2 = item;
            }
            else
            {
                if (prev2 == prev1)
                {
                	idList.append(", " + item);
                }
                else
                {
                	idList.append("-" + prev2 + ", " + item);
                }
            	prev1 = item;
            	prev2 = item;
            }
            if (var2 == emptyIDs.size() - 1 && prev2 != prev1)
            {
            	idList.append("-" + item);
            }
        }
        return "[" + idList.toString() + "]";
    }

	/**
     * gets a list of strings representing the item name and successive extra data, eg Enchantments and potion effects
     */
    private List getItemNameandInformation(ItemStack itemStack)
    {
        ArrayList var1 = new ArrayList();
        Item var2 = Item.itemsList[itemStack.itemID];
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

        return var1;
    }

    public String getVersion()
    {
        return "1.5.2";
    }
}
