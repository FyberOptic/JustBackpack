package net.fybertech.meddlebackpack;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ItemUseResult;
import net.minecraft.util.MainOrOffHand;
import net.minecraft.util.ObjectActionHolder;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class ItemBackpack extends Item
{

	public ItemBackpack()
	{
		this.setUnlocalizedName("meddleBackpack");
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	
	@Override
	public ItemUseResult onItemUse(EntityPlayer player, World world, BlockPos pos, MainOrOffHand hand,
			EnumFacing facing, float x, float y, float z)
	{
		return super.onItemUse(player,  world,  pos,  hand,  facing,  x,  y,  z);
	}

	
	@Override
	public ObjectActionHolder<ItemStack> onItemRightClick(World world, EntityPlayer player, MainOrOffHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		
		if (!world.isRemote)
		{
			InventoryBackpack backpackInventory = getInventory(stack);
			
			int size = player.inventory.getSizeInventory();
			int slotNum = -1;
			for (int n = 0; n < size; n++) {
				if (player.inventory.getStackInSlot(n) == stack) { slotNum = n; break; }
			}			
			backpackInventory.setSlotNum(slotNum);
			
			//if (backpackInventory != null) player.displayGui(new BackpackInteractionObject(player, backpackInventory));
			if (backpackInventory != null) player.displayGUIChest(backpackInventory);
		}
		return new ObjectActionHolder<ItemStack>(ItemUseResult.SUCCESS, stack);
	}


	public static InventoryBackpack getInventory(ItemStack stack)
	{
		if (stack == null || !(stack.getItem() instanceof ItemBackpack)) return null;

		if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound tag = stack.getTagCompound();

		String name = stack.getDisplayName();
		if (name == null || name.length() < 1) name = "Backpack";

		int slotCount = 9 * 3;
		InventoryBackpack inventory = new InventoryBackpack(name, true, slotCount, tag);

		return inventory;
	}


	//@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		return 0x955E3B;
	}
	
	
	// Use this until we can support language files
	@Override
	public String getItemStackDisplayName(ItemStack param0) {
		return "Backpack";
	}
	
	
	public class BackpackInteractionObject implements IInteractionObject
	{
		public final InventoryBackpack backpackInventory;
		public int slotNum = -1;
		
		public BackpackInteractionObject(EntityPlayer player, InventoryBackpack inventory)
		{			
			this.backpackInventory = inventory;			
			slotNum = inventory.slotNum;
		}		
		
		@Override
		public IChatComponent getDisplayName() {
			//return backpackInventory.getDisplayName();
			return new ChatComponentText(slotNum + "|" + getName());
		}

		@Override
		public String getName() {
			return backpackInventory.getName();
		}

		@Override
		public boolean hasCustomName() {
			return backpackInventory.hasCustomName();
		}

		@Override
		public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
			return new ContainerBackpack(inventoryPlayer, backpackInventory, player);
		}

		@Override
		public String getGuiID() {
			return "meddlebackpack:backpack";
		}
		
	}

}
