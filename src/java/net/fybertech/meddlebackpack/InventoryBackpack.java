package net.fybertech.meddlebackpack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;

public class InventoryBackpack extends InventoryBasic implements IInteractionObject
{

	final NBTTagCompound backpackTag;
	int slotNum = -1;


	public InventoryBackpack(String title, boolean customName, int slotCount, NBTTagCompound tag)
	{
		super(title, customName, slotCount);
		backpackTag = tag;
		this.loadInventoryFromNBT(tag.getTagList("inventory", 10));
	}


	public void loadInventoryFromNBT(NBTTagList tag)
	{
		int inventorySize = this.getSizeInventory();
		for (int n = 0; n < inventorySize; n++)
		{
			this.setInventorySlotContents(n, null);
		}

		int tagCount = tag.tagCount();
		for (int n = 0; n < tagCount; n++)
		{
			NBTTagCompound slotTag = tag.getCompoundTagAt(n);

			int slotNum = slotTag.getByte("Slot") & 255;
			if (slotNum < 0 || slotNum >= inventorySize) continue;

			this.setInventorySlotContents(slotNum, ItemStack.loadItemStackFromNBT(slotTag));
		}
	}


	public NBTTagList saveInventoryToNBT()
	{
		NBTTagList list = new NBTTagList();

		int inventorySize = this.getSizeInventory();
		for (int n = 0; n < inventorySize; n++)
		{
			ItemStack stack = this.getStackInSlot(n);
			if (stack == null) continue;

			NBTTagCompound slotTag = new NBTTagCompound();
			slotTag.setByte("Slot", (byte)n);
			stack.writeToNBT(slotTag);
			list.appendTag(slotTag);
		}

		return list;
	}


	@Override
	public void markDirty() {
		super.markDirty();

		NBTTagList inventory = this.saveInventoryToNBT();
		backpackTag.setTag("inventory", inventory);
	}


	@Override
	public Container createContainer(InventoryPlayer arg0, EntityPlayer arg1) 
	{
		return new ContainerChest(arg0, this, arg1);
	}


	@Override
	public String getGuiID() {
		return "meddlebackpack:backpack";
	}


	public void setSlotNum(int slot) {
		this.slotNum = slot;
	}
	
	
	@Override
	public IChatComponent getDisplayName()
	{
		//return new ChatComponentText("slot:" + slotNum);
		return new ChatComponentText(slotNum + "|" + getName());
	}

}
