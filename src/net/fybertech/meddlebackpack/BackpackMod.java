package net.fybertech.meddlebackpack;

import net.fybertech.meddleapi.MeddleAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;


public class BackpackMod
{

	public static final ItemBackpack backpackItem = new ItemBackpack();	
	public static CommonProxy proxy;
	
	
	public void init()
	{		
		MeddleAPI.registerItem(5000, "meddleBackpack", backpackItem);
		
		CraftingManager.getInstance().addRecipe(new ItemStack(backpackItem), "LLL", "LCL", "LLL", Character.valueOf('L'), Items.leather, Character.valueOf('C'), Blocks.chest);
		
		proxy = (CommonProxy)MeddleAPI.createProxyInstance("net.fybertech.meddlebackpack.CommonProxy", "net.fybertech.meddlebackpack.ClientProxy");
		proxy.init();
	}

}

