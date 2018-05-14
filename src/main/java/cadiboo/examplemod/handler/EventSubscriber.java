package cadiboo.examplemod.handler;

import cadiboo.examplemod.ExampleMod;
import cadiboo.examplemod.init.ModBlocks;
import cadiboo.examplemod.init.ModEntities;
import cadiboo.examplemod.init.ModItems;
import cadiboo.examplemod.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

@Mod.EventBusSubscriber(modid = Reference.ID)
public class EventSubscriber {

	@SubscribeEvent
	public static void registerEntities(final RegistryEvent.Register<EntityEntry> event) {
		event.getRegistry().registerAll(ModEntities.ENTITIES);
		ExampleMod.info("Registered Entities");
	}

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS);
		ExampleMod.info("Registered Blocks");
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS);
		ExampleMod.info("Registered Items");

		for (int i = 0; i < ModBlocks.BLOCKS.length; i++) {
			if (ModBlocks.getHiddenBlocks().contains(ModBlocks.BLOCKS[i]))
				continue;
			event.getRegistry().register(new ItemBlock(ModBlocks.BLOCKS[i]).setRegistryName(ModBlocks.BLOCKS[i].getRegistryName()));
		}
		ExampleMod.info("And ItemBlocks");
	}

}