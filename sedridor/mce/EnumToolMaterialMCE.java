package sedridor.mce;

import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.EnumHelper;

public class EnumToolMaterialMCE
{
	public static EnumToolMaterial WOOD = EnumHelper.addToolMaterial("WOOD", 0, 59, 2.0F, 0, 15);
	public static EnumToolMaterial STONE = EnumHelper.addToolMaterial("STONE", 1, 131, 4.0F, 1, 5);
	//public static EnumToolMaterial IRON = EnumHelper.addToolMaterial("IRON", 2, 250, 6.0F, 2, 14);
	//public static EnumToolMaterial GOLD = EnumHelper.addToolMaterial("GOLD", 0, 32, 12.0F, 0, 22);
	public static EnumToolMaterial EMERALD = EnumHelper.addToolMaterial("EMERALD", 3, 1561, 8.0F, 3, 10);
	//public static EnumToolMaterial DIAMOND = EnumHelper.addToolMaterial("DIAMOND", 3, 1561, 8.0F, 3, 10);

	public static EnumToolMaterial BRONZE = EnumHelper.addToolMaterial("BRONZE", 1, 150, 5.0F, 1, 6);			//Brown
	public static EnumToolMaterial BLURITE = EnumHelper.addToolMaterial("BLURITE", 2, 80, 8.0F, 2, 17);			//Blue
	public static EnumToolMaterial IRON = EnumHelper.addToolMaterial("IRON", 2, 250, 6.0F, 2, 10);				//DarkGrey
	public static EnumToolMaterial STEEL = EnumHelper.addToolMaterial("STEEL", 2, 480, 6.0F, 2, 12);			//LightGrey
	public static EnumToolMaterial SILVER = EnumHelper.addToolMaterial("SILVER", 1, 64, 12.0F, 0, 20);			//Grey
	public static EnumToolMaterial GOLD = EnumHelper.addToolMaterial("GOLD", 0, 32, 12.0F, 0, 22);				//Yellow

	public static EnumToolMaterial MITHRIL = EnumHelper.addToolMaterial("MITHRIL", 3, 750, 7.0F, 3, 7);			//DarkBlue
	public static EnumToolMaterial ADAMANTITE = EnumHelper.addToolMaterial("ADAMANTITE", 3, 1000, 7.0F, 3, 8);	//DarkGreen
	public static EnumToolMaterial ADAMANTIUM = EnumHelper.addToolMaterial("ADAMANTIUM", 3, 1000, 7.0F, 3, 8);	//DarkGreen
	public static EnumToolMaterial RUNITE = EnumHelper.addToolMaterial("RUNITE", 3, 1560, 8.0F, 3, 10);			//DarkCyan - TITANIUM
	public static EnumToolMaterial DRAGONITE = EnumHelper.addToolMaterial("DRAGONITE", 4, 2000, 14.0F, 4, 14);	//DarkRed - ELEMENTIUM
	public static EnumToolMaterial OBSIDIUM = EnumHelper.addToolMaterial("OBSIDIUM", 4, 1260, 10.0F, 2, 18);	//Black - OBSIDIUM
	public static EnumToolMaterial TITANIUM = EnumHelper.addToolMaterial("TITANIUM", 4, 1560, 8.0F, 3, 12);		//Grey (DarkCyan)
	public static EnumToolMaterial CRYSTAL = EnumHelper.addToolMaterial("CRYSTAL", 5, 2000, 12.0F, 2, 24);		//White

	/**
     * The strength of this tool material against blocks which it is effective against.
     */
    public void init()
    {
    }
    /*WOOD(0, 59, 2.0F, 0, 15),
    STONE(1, 131, 4.0F, 1, 5),
    //IRON(2, 250, 6.0F, 2, 14),
    //DIAMOND(3, 1561, 8.0F, 3, 10),
    EMERALD(3, 1561, 8.0F, 3, 10),
    //GOLD(0, 32, 12.0F, 0, 22);

    // ------------------------------------------------ MCE
    BRONZE(1, 150, 5.0F, 1, 6),				//Brown
    BLURITE(2, 80, 8.0F, 2, 17),			//Blue
    IRON(2, 250, 6.0F, 2, 10),				//DarkGrey
    STEEL(2, 480, 6.0F, 2, 12),				//LightGrey
    SILVER(1, 64, 12.0F, 0, 20),			//Grey
    GOLD(0, 32, 12.0F, 0, 22),				//Yellow

    MITHRIL(3, 750, 7.0F, 3, 7),			//DarkBlue
    ADAMANTITE(3, 1000, 7.0F, 3, 8),		//DarkGreen
    ADAMANTIUM(3, 1000, 7.0F, 3, 8),		//DarkGreen
    RUNITE(3, 1560, 8.0F, 3, 10),			//DarkCyan - TITANIUM
    DRAGONITE(4, 2000, 14.0F, 4, 14),		//DarkRed - ELEMENTIUM
    OBSIDIUM(4, 1260, 10.0F, 2, 18),		//Black - OBSIDIUM
    TITANIUM(4, 1560, 8.0F, 3, 12),			//Grey (DarkCyan)
    CRYSTAL(5, 2000, 12.0F, 2, 24);			//White
*/
    /*
     * The level of material this tool can harvest (3 = DIAMOND, 2 = IRON, 1 = STONE, 0 = WOOD/GOLD)
	harvestLevel, maxUses, efficiencyOnProperMaterial, damageVsEntity, enchantability
    ----------------------------------------
    *WOOD(0, 59, 2.0F, 0, 15),				DarkRed
    *STONE(1, 131, 4.0F, 1, 5),

    BRONZE(1, 150, 5.0F, 1, 6),				Brown
    BLURITE(2, 80, 8.0F, 2, 17),			Blue
    *IRON(2, 250, 6.0F, 2, 10),				DarkGrey
    STEEL(2, 480, 6.0F, 2, 12),				LightGrey
    SILVER(1, 64, 12.0F, 0, 20),			Grey
    *GOLD(0, 32, 12.0F, 0, 22),				Yellow

    MITHRIL(3, 750, 7.0F, 3, 7),			DarkBlue
    ADAMANTITE(3, 1000, 7.0F, 3, 8),		DarkGreen
    *RUNITE(3, 1560, 8.0F, 3, 10),			DarkCyan - TITANIUM
    DRAGONITE(4, 2000, 14.0F, 4, 14);		DarkRed - ELEMENTIUM
    //ONYX(4, 1260, 10.0F, 2, 18),			Black
    //CRYSTAL(5, 2000, 12.0F, 2, 24);		White
    ----------------------------------------

    ----------------------------------------
    *WOOD(0, 59, 2.0F, 0, 15),				DarkRed
    *STONE(1, 131, 4.0F, 1, 5),

    BRONZE(1, 150, 5.0F, 1, 6),				Brown
    *IRON(2, 250, 6.0F, 2, 10),				DarkGrey
    STEEL(2, 400, 6.0F, 2, 12),				LightGrey
    SILVER(1, 64, 12.0F, 0, 20),			Grey
    *GOLD(0, 32, 12.0F, 0, 22),				Yellow

    MITHRIL(3, 540, 7.0F, 3, 7),			DarkBlue
    THORIUM(2, 700, 6.0F, 3, 5),			DarkGrey
    ADAMANTIUM(3, 800, 7.0F, 3, 8),			DarkGreen
    COBALT(3, 1000, 6.0F, 3, 6),			Blue
    SARONITE(2, 1260, 5.0F, 3, 6),			DarkCyan
    *TITANIUM(3, 1560, 8.0F, 3, 10),		Grey (DarkCyan)
    ELEMENTIUM(4, 2000, 14.0F, 4, 14);		Grey (DarkRed)
    //OBSIDIUM(4, 1260, 10.0F, 2, 18),		Black
    //CRYSTAL(5, 2000, 12.0F, 2, 24);		White
    ----------------------------------------

    ----------------------------------------
    *WOOD(0, 59, 2.0F, 0, 15),				DarkRed
    *STONE(1, 131, 4.0F, 1, 5),

    BRONZE(1, 150, 5.0F, 1, 6),				Brown
    *IRON(2, 250, 6.0F, 2, 10),				DarkGrey
    STEEL(2, 400, 6.0F, 2, 12),				LightGrey
    SILVER(1, 64, 12.0F, 0, 20),			Grey
    *GOLD(0, 32, 12.0F, 0, 22),				Yellow

    MITHRIL(3, 600, 7.0F, 3, 7),			DarkBlue
    --THORIUM(3, 800, 6.0F, 3, 6),			DarkGrey
    ADAMANTIUM(4, 1000, 7.0F, 3, 8),		DarkGreen
    *TITANIUM(4, 1560, 8.0F, 3, 10),		Grey (DarkCyan)
    ELEMENTIUM(5, 2000, 14.0F, 4, 14);		Grey (DarkRed)
    //OBSIDIUM(4, 1260, 10.0F, 2, 18),		Black
    //CRYSTAL(5, 2000, 12.0F, 2, 24);		White
    ----------------------------------------
	harvestLevel, maxUses, efficiencyOnProperMaterial, damageVsEntity, enchantability
     */
    // ------------------------------------------------ MCE
}
