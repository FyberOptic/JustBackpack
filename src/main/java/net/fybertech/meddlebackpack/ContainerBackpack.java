package net.fybertech.meddlebackpack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBackpack extends Container
{
	final IInventory backpackInventory;
	final int numRows;
	
	public ContainerBackpack(IInventory playerInventory, IInventory chestInventory, EntityPlayer player)
	{
		this.backpackInventory = chestInventory;
        this.numRows = chestInventory.getSizeInventory() / 9;
        chestInventory.openInventory(player);
        
        int var4 = (this.numRows - 4) * 18;
        int var5;
        int var6;

        for (var5 = 0; var5 < this.numRows; ++var5)
        {
            for (var6 = 0; var6 < 9; ++var6)
            {
                this.addSlotToContainer(new Slot(chestInventory, var6 + var5 * 9, 8 + var6 * 18, 18 + var5 * 18));
            }
        }

        for (var5 = 0; var5 < 3; ++var5)
        {
            for (var6 = 0; var6 < 9; ++var6)
            {
                int slotNum = var6 + var5 * 9 + 9;
                this.addSlotToContainer(new Slot(playerInventory, slotNum, 8 + var6 * 18, 103 + var5 * 18 + var4));
            }
        }

        for (var5 = 0; var5 < 9; ++var5)
        {        	
        	this.addSlotToContainer(new Slot(playerInventory, var5, 8 + var5 * 18, 161 + var4));
        }
	}
	
	
	@Override
	public boolean canInteractWith(EntityPlayer arg0) 
	{
		return true;
	}
	
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
    {
        ItemStack stack = null;
        Slot slot = this.inventorySlots.get(slotNum);

        if (slot != null && slot.getHasStack())
        {
            stack = slot.getStack().copy();

            if (slotNum < this.numRows * 9)
            {
                if (!this.mergeItemStack(stack, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return ItemStack.NULL_STACK;
                }
            }
            else if (!this.mergeItemStack(stack, 0, this.numRows * 9, false))
            {
                return ItemStack.NULL_STACK;
            }

            if (stack.getStackSize() == 0)
            {
                slot.putStack(ItemStack.NULL_STACK);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return stack == null ? ItemStack.NULL_STACK : stack;
    }

    
	@Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        this.backpackInventory.closeInventory(player);
    }

}
