package sedridor.mce.tileentities;

import sedridor.mce.MCE_Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerCraftingMCE extends Container
{
	/** The crafting matrix inventory (5x4). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 5, 4);
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;

	public ContainerCraftingMCE(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
	{
		this.worldObj = par2World;
		this.posX = par3;
		this.posY = par4;
		this.posZ = par5;
		this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 144, 35));
		int i;
		int j;

		for (i = 0; i < 4; ++i)
		{
			for (j = 0; j < 5; ++j)
			{
				this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 5, 8 + j * 18, 8 + i * 18));
			}
		}

		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	 @Override
	 public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		 this.craftResult.setInventorySlotContents(0, ExtendedCraftingHandler.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	}

	 /**
	  * Callback for when the crafting gui is closed.
	  */
	 @Override
	 public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	 {
		 super.onCraftGuiClosed(par1EntityPlayer);

		 if (!this.worldObj.isRemote)
		 {
			 for (int i = 0; i < 20; ++i)
			 {
				 ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

				 if (itemstack != null)
				 {
					 par1EntityPlayer.dropPlayerItem(itemstack);
				 }
			 }
		 }
	 }

	 @Override
	 public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	 {
		 return this.worldObj.getBlockId(this.posX, this.posY, this.posZ) != MCE_Items.craftingTable.blockID ? false : par1EntityPlayer.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64.0D;
	 }

	 /**
	  * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	  */
	 @Override
	 public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	 {
		 ItemStack itemstack = null;
		 Slot slot = (Slot)this.inventorySlots.get(par2);

		 if (slot != null && slot.getHasStack())
		 {
			 ItemStack itemstack1 = slot.getStack();
			 itemstack = itemstack1.copy();

			 if (par2 == 0)
			 {
				 if (!this.mergeItemStack(itemstack1, 21, 57, true))
				 {
					 return null;
				 }

				 slot.onSlotChange(itemstack1, itemstack);
			 }
			 else if (par2 >= 21 && par2 < 48)
			 {
				 if (!this.mergeItemStack(itemstack1, 48, 57, false))
				 {
					 return null;
				 }
			 }
			 else if (par2 >= 48 && par2 < 57)
			 {
				 if (!this.mergeItemStack(itemstack1, 21, 48, false))
				 {
					 return null;
				 }
			 }
			 else if (!this.mergeItemStack(itemstack1, 21, 57, false))
			 {
				 return null;
			 }

			 if (itemstack1.stackSize == 0)
			 {
				 slot.putStack((ItemStack)null);
			 }
			 else
			 {
				 slot.onSlotChanged();
			 }

			 if (itemstack1.stackSize == itemstack.stackSize)
			 {
				 return null;
			 }

			 slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		 }

		 return itemstack;
	 }

	 @Override
	 public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
	 {
		 return par2Slot.inventory != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot);
	 }
}
