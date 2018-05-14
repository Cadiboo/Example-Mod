package cadiboo.examplemod.client;


import cadiboo.examplemod.ExampleMod;
import cadiboo.examplemod.creativetab.ModCreativeTab;
import cadiboo.examplemod.init.ModBlocks;
import cadiboo.examplemod.init.ModItems;
import cadiboo.examplemod.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.ID)
public class EventSubscriber {

	@SubscribeEvent
	public static void registerModels(final ModelRegistryEvent event) {

		blockItemModels: for (int i = 0; i < ModBlocks.BLOCKS.length; i++) {
			if (ModBlocks.getHiddenBlocks().contains(ModBlocks.BLOCKS[i]))
				continue blockItemModels;

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.BLOCKS[i]), 0, new ModelResourceLocation(ModBlocks.BLOCKS[i].getRegistryName(), "inventory"));
			ModBlocks.BLOCKS[i].setCreativeTab(ModCreativeTab.TAB);
		}

		for (int i = 0; i < ModItems.ITEMS.length; i++) {
			ExampleMod.info(ModItems.ITEMS[i]);
			if (!ModItems.ITEMS[i].getHasSubtypes()) {
				ModelLoader.setCustomModelResourceLocation(ModItems.ITEMS[i], 0, new ModelResourceLocation(ModItems.ITEMS[i].getRegistryName(), "inventory"));
			} else {
				//handle items with metadata's textures
			}
			ModItems.ITEMS[i].setCreativeTab(ModCreativeTab.TAB);
		}
		ExampleMod.info("Registered models");

		//Register TESRs
		//Register Entity Renderers
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onTooltipEvent(final ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();

		
		//allows you to put item.example_item.tooltip=An Example
		//and have it display
		if (stack.getItem().getRegistryName().getResourceDomain().equalsIgnoreCase(Reference.ID)) {
			String itemTooltip = ExampleMod.proxy.localize(stack.getUnlocalizedName() + ".tooltip", new Object[0]);
			if (!itemTooltip.equalsIgnoreCase(stack.getUnlocalizedName() + ".tooltip"))
				setTooltip(event, itemTooltip);
		}

		if (stack.getCapability(CapabilityEnergy.ENERGY, null) != null && stack.getItem().getRegistryName().getResourceDomain().equals(Reference.ID)) {
			setTooltip(event, ExampleMod.proxy.localize("energy", new Object[0]) + ": " + stack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored() + "/" + stack.getCapability(CapabilityEnergy.ENERGY, null).getMaxEnergyStored());
		}

	}

	private static void setTooltip(final ItemTooltipEvent event, final String tooltip) {
		for (int i = 0; i < event.getToolTip().size(); i++) {
			if (StringUtils.stripControlCodes(event.getToolTip().get(i)).equals(event.getItemStack().getItem().getRegistryName().toString())) {
				event.getToolTip().add(i, tooltip);
				return;
			}
		}
		event.getToolTip().add(tooltip);
	}

}
