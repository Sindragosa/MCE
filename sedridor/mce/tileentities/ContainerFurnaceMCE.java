package sedridor.mce.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerFurnaceMCE extends Container
{
	private TileEntityFurnaceMCE furnace;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;

	public ContainerFurnaceMCE(InventoryPlayer par1InventoryPlayer, TileEntityFurnaceMCE par2TileEntityFurnace)
	{
		this.furnace = par2TileEntityFurnace;
		this.addSlotToContainer(new Slot(par2TileEntityFurnace, 0, 44, 17));
		this.addSlotToContainer(new Slot(par2TileEntityFurnace, 3, 69, 17));
		this.addSlotToContainer(new Slot(par2TileEntityFurnace, 1, 56, 53));
		this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityFurnace, 2, 116, 35));
		int i;

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	 @Override
	 public void detectAndSendChanges()
	{
		 super.detectAndSendChanges();

		 for (int i = 0; i < this.crafters.size(); ++i)
		 {
			 ICrafting icrafting = (ICrafting)this.crafters.get(i);

			 if (this.lastCookTime != this.furnace.furnaceCookTime)
			 {
				 icrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
			 }

			 if (this.lastBurnTime != this.furnace.furnaceBurnTime)
			 {
				 icrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
			 }

			 if (this.lastItemBurnTime != this.furnace.currentItemBurnTime)
			 {
				 icrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
			 }
		 }

		 this.lastCookTime = this.furnace.furnaceCookTime;
		 this.lastBurnTime = this.furnace.furnaceBurnTime;
		 this.lastItemBurnTime = this.furnace.currentItemBurnTime;
	}

	 @Override
	 @SideOnly(Side.CLIENT)
	 public void updateProgressBar(int par1, int par2)
	 {
		 if (par1 == 0)
		 {
			 this.furnace.furnaceCookTime = par2;
		 }

		 if (par1 == 1)
		 {
			 this.furnace.furnaceBurnTime = par2;
		 }

		 if (par1 == 2)
		 {
			 this.furnace.currentItemBurnTime = par2;
		 }
	 }

	 @Override
	 public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	 {
		 return this.furnace.isUseableByPlayer(par1EntityPlayer);
	 }

	 /**
	  * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	  */
	 @Override
	 public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	 {
		 byte var3 = 4;
		 ItemStack itemstack = null;
		 Slot var5 = (Slot)this.inventorySlots.get(par2);

		 if (var5 != null && var5.getHasStack())
		 {
			 ItemStack var6 = var5.getStack();
			 itemstack = var6.copy();

			 if (par2 < var3)
			 {
				 if (!this.mergeItemStack(var6, var3, 36 + var3, true))
				 {
					 return null;
				 }

				 var5.onSlotChange(var6, itemstack);
			 }
			 else
			 {
				 if (!this.mergeItemStack(var6, 0, var3 - 1, false))
				 {
					 return null;
				 }

				 var5.onSlotChange(var6, itemstack);
			 }

			 if (var6.stackSize == 0)
			 {
				 var5.putStack((ItemStack)null);
			 }
			 else
			 {
				 var5.onSlotChanged();
			 }

			 if (var6.stackSize == itemstack.stackSize)
			 {
				 return null;
			 }

			 var5.onPickupFromSlot(par1EntityPlayer, var6);
		 }

		 return itemstack;
	 }
}