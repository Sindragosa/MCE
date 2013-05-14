package sedridor.mce;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraftforge.common.EnumHelper;

public class EnumArmorMaterialMCE
{
	public static EnumArmorMaterial CLOTH = EnumHelper.addArmorMaterial("CLOTH", 5, new int[]{1, 3, 2, 1}, 15);
	public static EnumArmorMaterial CHAIN = EnumHelper.addArmorMaterial("CHAIN", 15, new int[]{2, 5, 4, 1}, 12);
	//public static EnumArmorMaterial IRON = EnumHelper.addArmorMaterial("IRON", 15, new int[]{2, 6, 5, 2}, 9);
	//public static EnumArmorMaterial GOLD = EnumHelper.addArmorMaterial("GOLD", 7, new int[]{2, 5, 3, 1}, 25);
	public static EnumArmorMaterial DIAMOND = EnumHelper.addArmorMaterial("DIAMOND", 33, new int[]{3, 8, 6, 3}, 10);

	public static EnumArmorMaterial BRONZE = EnumHelper.addArmorMaterial("BRONZE", 15, new int[]{2, 5, 4, 1}, 4);			//Brown
	public static EnumArmorMaterial BLURITE = EnumHelper.addArmorMaterial("BLURITE", 18, new int[]{2, 6, 5, 2}, 9);			//Blue
	public static EnumArmorMaterial IRON = EnumHelper.addArmorMaterial("IRON", 20, new int[]{2, 6, 5, 2}, 5);				//DarkGrey
	public static EnumArmorMaterial STEEL = EnumHelper.addArmorMaterial("STEEL", 24, new int[]{2, 7, 5, 2}, 6);				//LightGrey
	public static EnumArmorMaterial SILVER = EnumHelper.addArmorMaterial("SILVER", 6, new int[]{2, 6, 4, 2}, 21);			//Grey
	public static EnumArmorMaterial GOLD = EnumHelper.addArmorMaterial("GOLD", 7, new int[]{2, 5, 3, 1}, 25);				//Yellow

	public static EnumArmorMaterial MITHRIL = EnumHelper.addArmorMaterial("MITHRIL", 28, new int[]{3, 8, 6, 2}, 7);			//DarkBlue
	public static EnumArmorMaterial ADAMANTITE = EnumHelper.addArmorMaterial("ADAMANTITE", 30, new int[]{3, 8, 6, 2}, 9);	//DarkGreen
	public static EnumArmorMaterial ADAMANTIUM = EnumHelper.addArmorMaterial("ADAMANTIUM", 30, new int[]{3, 8, 6, 2}, 9);	//DarkGreen
	public static EnumArmorMaterial RUNITE = EnumHelper.addArmorMaterial("RUNITE", 34, new int[]{3, 8, 6, 3}, 10);			//DarkCyan - TITANIUM
	public static EnumArmorMaterial DRAGONITE = EnumHelper.addArmorMaterial("DRAGONITE", 40, new int[]{4, 10, 8, 4}, 14);	//DarkRed - ELEMENTIUM
	public static EnumArmorMaterial ONYX = EnumHelper.addArmorMaterial("ONYX", 35, new int[]{3, 8, 6, 2}, 18);				//Black - OBSIDIUM
	public static EnumArmorMaterial CRYSTAL = EnumHelper.addArmorMaterial("CRYSTAL", 36, new int[]{3, 6, 6, 2}, 20);		//White
	public static EnumArmorMaterial TITANIUM = EnumHelper.addArmorMaterial("TITANIUM", 34, new int[]{3, 8, 6, 3}, 12);		//Grey (DarkCyan)

	/**
     * Return the enchantability factor of the material.
     */
    public void init()
    {
    }
    /*CLOTH(5, new int[]{1, 3, 2, 1}, 15),
    CHAIN(15, new int[]{2, 5, 4, 1}, 12),
    //IRON(15, new int[]{2, 6, 5, 2}, 9),
    //GOLD(7, new int[]{2, 5, 3, 1}, 25),
    DIAMOND(33, new int[]{3, 8, 6, 3}, 10),

    // ------------------------------------------------ MCE
    BRONZE(15, new int[]{2, 5, 4, 1}, 4),		//Brown
    BLURITE(18, new int[]{2, 6, 5, 2}, 9),		//Blue
    IRON(20, new int[]{2, 6, 5, 2}, 5),			//DarkGrey
    STEEL(24, new int[]{2, 7, 5, 2}, 6),		//LightGrey
    SILVER(6, new int[]{2, 6, 4, 2}, 21),		//Grey
    GOLD(7, new int[]{2, 5, 3, 1}, 25),			//Yellow

    MITHRIL(28, new int[]{3, 8, 6, 2}, 7),		//DarkBlue
    ADAMANTITE(30, new int[]{3, 8, 6, 2}, 9),	//DarkGreen
    ADAMANTIUM(30, new int[]{3, 8, 6, 2}, 9),	//DarkGreen
    RUNITE(34, new int[]{3, 8, 6, 3}, 10),		//DarkCyan - TITANIUM
    DRAGONITE(40, new int[]{4, 10, 8, 4}, 14),	//DarkRed - ELEMENTIUM
    //ONYX(35, new int[]{3, 8, 6, 2}, 18),		//Black - OBSIDIUM
    //CRYSTAL(36, new int[]{3, 6, 6, 2}, 20);	//White
    TITANIUM(34, new int[]{3, 8, 6, 3}, 12);	//Grey (DarkCyan)
    */
    /*
    maxDamageFactor, {damageReductionAmountArray}, enchantability
    ----------------------------------------
    *CLOTH(5, new int[]{1, 3, 2, 1}, 15),		DarkRed
    *CHAIN(15, new int[]{2, 5, 4, 1}, 12),

    BRONZE(15, new int[]{2, 5, 4, 1}, 4),		Brown
    BLURITE(18, new int[]{2, 6, 5, 2}, 9),		Blue
    *IRON(20, new int[]{2, 6, 5, 2}, 5),		DarkGrey
    STEEL(24, new int[]{2, 7, 5, 2}, 6),		LightGrey
    SILVER(6, new int[]{2, 6, 4, 2}, 21),		Grey
    *GOLD(7, new int[]{2, 5, 3, 1}, 25),		Yellow

    MITHRIL(28, new int[]{3, 8, 6, 2}, 7),		DarkBlue
    ADAMANTITE(30, new int[]{3, 8, 6, 2}, 9),	DarkGreen
    *RUNITE(34, new int[]{3, 8, 6, 3}, 10),		DarkCyan - TITANIUM
    DRAGONITE(40, new int[]{4, 10, 8, 4}, 14);	DarkRed - ELEMENTIUM
    //ONYX(35, new int[]{3, 8, 6, 2}, 18),		Black
    //CRYSTAL(36, new int[]{3, 6, 6, 2}, 20);	White
    ----------------------------------------

    ----------------------------------------
    *CLOTH(5, new int[]{1, 3, 2, 1}, 15),		DarkRed
    *CHAIN(15, new int[]{2, 5, 4, 1}, 12),

    BRONZE(15, new int[]{2, 5, 4, 1}, 4),		Brown
    *IRON(15, new int[]{2, 6, 5, 2}, 5),		DarkGrey
    STEEL(15, new int[]{2, 7, 5, 2}, 6),		LightGrey
    SILVER(6, new int[]{2, 6, 4, 2}, 21);		Grey
    *GOLD(7, new int[]{2, 5, 3, 1}, 25),		Yellow

    MITHRIL(28, new int[]{3, 8, 6, 2}, 5),		DarkBlue
    THORIUM(18, new int[]{2, 6, 5, 2}, 6),		DarkGrey
    ADAMANTIUM(22, new int[]{3, 8, 6, 2}, 7),	DarkGreen
    COBALT(24, new int[]{3, 8, 6, 2}, 8),		Blue
    SARONITE(26, new int[]{3, 8, 6, 2}, 9),		DarkCyan
    *TITANIUM(32, new int[]{3, 8, 6, 3}, 12),	Grey (DarkCyan)
    ELEMENTIUM(36, new int[]{4, 10, 8, 4}, 14);	Grey (DarkRed)
    //OBSIDIUM(34, new int[]{3, 8, 6, 2}, 18),	Black
    //CRYSTAL(40, new int[]{3, 6, 6, 2}, 20);	White
    ----------------------------------------

    ----------------------------------------
    *CLOTH(5, new int[]{1, 3, 2, 1}, 15),		DarkRed
    *CHAIN(15, new int[]{2, 5, 4, 1}, 12),

    BRONZE(15, new int[]{2, 5, 4, 1}, 4),		Brown
    *IRON(18, new int[]{2, 6, 5, 2}, 5),		DarkGrey
    STEEL(20, new int[]{2, 7, 5, 2}, 6),		LightGrey
    SILVER(6, new int[]{2, 6, 4, 2}, 21);		Grey
    *GOLD(7, new int[]{2, 5, 3, 1}, 25),		Yellow

    MITHRIL(22, new int[]{3, 8, 6, 2}, 7),		DarkBlue
    THORIUM(26, new int[]{2, 6, 5, 2}, 8),		DarkGrey
    ADAMANTIUM(30, new int[]{3, 8, 6, 2}, 9),	DarkGreen
    *TITANIUM(36, new int[]{3, 8, 6, 3}, 10),	Grey (DarkCyan)
    ELEMENTIUM(40, new int[]{4, 10, 8, 4}, 14);	Grey (DarkRed)
    //OBSIDIUM(34, new int[]{3, 8, 6, 2}, 18),	Black
    //CRYSTAL(40, new int[]{3, 6, 6, 2}, 20);	White
    ----------------------------------------
    maxDamageFactor, {damageReductionAmountArray}, enchantability
     */
}
