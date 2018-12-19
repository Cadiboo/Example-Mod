package cadiboo.examplemod;

import cadiboo.examplemod.block.BlockExampleTileEntity;
import cadiboo.examplemod.block.BlockModOre;
import cadiboo.examplemod.block.BlockResource;
import cadiboo.examplemod.block.IModBlock;
import cadiboo.examplemod.item.ItemExampleIngot;
import cadiboo.examplemod.item.ItemExampleItem;
import cadiboo.examplemod.tileentity.TileEntityExampleTileEntity;
import cadiboo.examplemod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static cadiboo.examplemod.util.ModReference.MOD_ID;

/**
 * Subscribe to events that should be handled on both PHYSICAL sides in this class
 */
@Mod.EventBusSubscriber(modid = MOD_ID)
public final class EventSubscriber {

	private static int entityId = 0;

	/* register blocks */
	@SubscribeEvent
	public static void onRegisterBlocksEvent(final RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(new BlockResource("example_block"));
		registry.register(new BlockModOre("example_ore"));
		registry.register(new BlockExampleTileEntity("example_tile_entity"));

		ExampleMod.EXAMPLE_MOD_LOG.debug("Registered blocks");

		registerTileEntities();

		ExampleMod.EXAMPLE_MOD_LOG.debug("Registered tile entities");

	}

	private static void registerTileEntities() {
		registerTileEntity(TileEntityExampleTileEntity.class);
	}

	private static void registerTileEntity(final Class<? extends TileEntity> clazz) {
		try {
			GameRegistry.registerTileEntity(clazz, new ResourceLocation(MOD_ID, ModUtil.getRegistryNameForClass(clazz, "TileEntity")));
		} catch (final Exception e) {
			FMLCommonHandler.instance().raiseException(e, "Error registering Tile Entity " + clazz.getSimpleName(), true);
		}
	}

	/* register items */
	@SubscribeEvent
	public static void onRegisterItemsEvent(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		// item blocks
		ForgeRegistries.BLOCKS.getValuesCollection().stream()
				.filter(block -> block instanceof IModBlock && ((IModBlock) block).hasItemBlock())
				.forEach(block ->
						registry.register(ModUtil.setRegistryNames(new ItemBlock(block), block.getRegistryName()))
				);

		// items
		registry.register(ModUtil.setRegistryNames(new ItemExampleItem(), "example_item"));
		registry.register(new ItemExampleIngot("example_ingot"));

		ExampleMod.EXAMPLE_MOD_LOG.debug("Registered items");

	}

	/* register entities */
	@SubscribeEvent
	public static void onRegisterEntitiesEvent(final RegistryEvent.Register<EntityEntry> event) {
		final IForgeRegistry<EntityEntry> registry = event.getRegistry();

		{
//			final Class clazz = Entity____.class;
//			final ResourceLocation registryName = new ResourceLocation(MOD_ID, ModUtil.getRegistryNameForClass(clazz, "Entity"));
//			registry.register(
//					EntityEntryBuilder.create()
//							.entity(clazz)
//							.id(registryName, entityId++)
//							.name(registryName.getPath())
//							.tracker(range, updateFrequency, sendVelocityUpdates)
//							.egg(primaryColor, secondaryColor)
//							.build()
//			);
		}

		ExampleMod.EXAMPLE_MOD_LOG.debug("Registered entities");

	}

}
