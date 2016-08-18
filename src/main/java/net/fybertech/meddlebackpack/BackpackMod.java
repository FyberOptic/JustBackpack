package net.fybertech.meddlebackpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.fybertech.meddle.Meddle;
import net.fybertech.meddleapi.MeddleAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

// TODO - Get EntityPlayerSP from Minecraft, use that to patch displayGui for custom IInteractionObjects



// version 1.0.2
public class BackpackMod
{

	public static final ItemBackpack backpackItem = new ItemBackpack();	
	public static CommonProxy proxy;
	
	private static final int DEFAULT_ITEM_ID = 5150;
	private int backpackID = -1;
	
	
	private void loadConfig()
	{
		File configFile = new File(Meddle.getConfigDir(), "justbackpack.cfg");
		
		if (!configFile.exists())
		{			
			try {
				PrintWriter pw = new PrintWriter(configFile);
				pw.println("# Just Backpack config");
				pw.println("backpackID = " + DEFAULT_ITEM_ID);
				pw.println();
				pw.close();
			} catch (IOException e) {
				Meddle.LOGGER.error("[JustBackpack] Error generating default config");
			}			
		}
		
		try {			
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("#")) continue;
				
				String[] split = line.split("=");
				if (split.length != 2) continue;
				
				String key = split[0].trim();
				String val = split[1].trim();
				
				if (key.equalsIgnoreCase("backpackID")) {
					try {
						backpackID = Integer.parseInt(val);
					}
					catch (NumberFormatException e) {}
				}
			}
			br.close();			
		} catch (IOException e) {
			Meddle.LOGGER.error("[JustBackpack] Error loading config");
		}		
		
		if (backpackID == -1) backpackID = DEFAULT_ITEM_ID;
	}
	
	
	public void init()
	{
		loadConfig();	
		Meddle.LOGGER.info("[JustBackpack] Backpack using item id " + backpackID);
		
		MeddleAPI.registerItem(backpackID, "meddleBackpack", backpackItem);
		
		CraftingManager.getInstance().addRecipe(new ItemStack(backpackItem), "LLL", "LCL", "LLL", Character.valueOf('L'), Items.leather, Character.valueOf('C'), Blocks.chest);
		
		proxy = (CommonProxy)MeddleAPI.createProxyInstance("net.fybertech.meddlebackpack.CommonProxy", "net.fybertech.meddlebackpack.ClientProxy");
		proxy.init();		
	}

}

