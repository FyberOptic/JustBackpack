package net.fybertech.meddlebackpack;

import java.util.Arrays;
import java.util.List;

import net.fybertech.meddleapi.MeddleClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.inventory.EnumContainerAction;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;

public class ClientProxy extends CommonProxy implements MeddleClient.IDisplayGui
{

	@Override
	public void init() 
	{
		super.init();
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BackpackMod.backpackItem, 0, new ModelResourceLocation("meddleBackpack", "inventory"));
		
		MeddleClient.registerGuiHandler(this);
	}

	
	@Override
	//public void onOpenGui(EntityPlayerSP player, IInteractionObject guiObject)
	public void onOpenGui(EntityPlayerSP player, String guiID, IChatComponent displayName, int slots)
	{		
		String backpackName = displayName.c();
		int slotNum = -1;
		
		if (backpackName.contains("|")) {
			String[] split = backpackName.split("\\|");
			if (split.length > 1) {				
				backpackName = backpackName.substring(split[0].length() + 1);
				try {
					slotNum = Integer.parseInt(split[0]);
				}
				catch (NumberFormatException e) {}
			}
		}
		
		IInventory backpackInventory = new InventoryBasic(backpackName, true, 9 * 3);		
		final int finalSlot = slotNum;
		
		Minecraft.getMinecraft().displayGuiScreen(new GuiChest(player.inventory, backpackInventory) {
			@Override
			protected void handleMouseClick(Slot param0, int param1, int param2, EnumContainerAction param3) {				
				if (param0 != null && finalSlot == param0.slotIndex) return;
				else super.handleMouseClick(param0, param1, param2, param3);
			}
		});
	}

	@Override
	public List<String> getHandledGuiIDs() {
		return Arrays.asList(new String[] { "meddlebackpack:backpack" });
	}


}
