package net.fybertech.meddlebackpack;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ItemUseResult;
import net.minecraft.util.MainOrOffHand;
import net.minecraft.util.ObjectActionHolder;
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
	public ItemUseResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, MainOrOffHand hand,
			EnumFacing facing, float x, float y, float z)
	{
		return super.onItemUse(stack,  player,  world,  pos,  hand,  facing,  x,  y,  z);
	}

	
	@Override
	public ObjectActionHolder<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, MainOrOffHand hand)
	{
		if (!world.isRemote)
		{
			IInventory backpackInventory = getInventory(stack);
			if (backpackInventory != null) player.displayGUIChest(backpackInventory);
		}
		return new ObjectActionHolder<ItemStack>(ItemUseResult.SUCCESS, stack);
	}


	public static IInventory getInventory(ItemStack stack)
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


	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		return 0x955E3B;
	}
	
	
	// Use this until we can support language files
	@Override
	public String getItemStackDisplayName(ItemStack param0) {
		return "Backpack";
	}

}
