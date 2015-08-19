package net.fybertech.meddlebackpack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ClientProxy extends CommonProxy 
{

	@Override
	public void init() 
	{
		super.init();
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BackpackMod.backpackItem, 0, new ModelResourceLocation("meddleBackpack", "inventory"));
	}
	
}
