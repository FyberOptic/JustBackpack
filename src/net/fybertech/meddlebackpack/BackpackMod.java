package net.fybertech.meddlebackpack;

import net.fybertech.meddleapi.MeddleAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;


public class BackpackMod
{

	public static final ItemBackpack backpackItem = new ItemBackpack();	
	public static CommonProxy proxy;
	
	
	public void init()
	{		
		MeddleAPI.registerItem(5000, "meddleBackpack", backpackItem);
		
		proxy = (CommonProxy)MeddleAPI.createProxyInstance("net.fybertech.meddlebackpack.CommonProxy", "net.fybertech.meddlebackpack.ClientProxy");
		proxy.init();
	}

}

