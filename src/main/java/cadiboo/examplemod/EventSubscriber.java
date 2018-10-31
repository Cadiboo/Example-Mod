package cadiboo.examplemod;

import cadiboo.examplemod.block.BlockModOre;
import cadiboo.examplemod.block.BlockResource;
import cadiboo.examplemod.entity.IModEntity;
import cadiboo.examplemod.init.ModBlocks;
import cadiboo.examplemod.util.ModReference;
import cadiboo.examplemod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModReference.MOD_ID)
public final class EventSubscriber {

	private static int entityId = 0;

	/* register blocks */
	@SubscribeEvent
	public static void onRegisterBlocksEvent(final RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(new BlockResource("example_block"));

		registry.register(new BlockModOre("example_ore"));

		ExampleMod.info("Registered blocks");

		registerTileEntities();

		ExampleMod.debug("Registered tile entities");

	}

	private static void registerTileEntities() {
//		registerTileEntity(TileEntity___.class);
	}

	private static void registerTileEntity(final Class<? extends TileEntity> clazz) {
		try {
			GameRegistry.registerTileEntity(clazz, new ResourceLocation(ModReference.MOD_ID, ModUtil.getRegistryNameForClass(clazz, "TileEntity")));
		} catch (final Exception e) {
			ExampleMod.error("Error registering Tile Entity " + clazz.getSimpleName());
			e.printStackTrace();
		}
	}

	/* register items */
	@SubscribeEvent
	public static void onRegisterItemsEvent(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		// item blocks

		registry.register(ModUtil.setRegistryNames(new ItemBlock(ModBlocks.EXAMPLE_BLOCK), ModBlocks.EXAMPLE_BLOCK.getRegistryName()));

		registry.register(ModUtil.setRegistryNames(new ItemBlock(ModBlocks.EXAMPLE_ORE), ModBlocks.EXAMPLE_ORE.getRegistryName()));

		// items

		registry.register(ModUtil.setRegistryNames(new Item(), "example_item"));

		ExampleMod.info("Registered items");

	}

	/* register entities */
	@SubscribeEvent
	public static void onRegisterEntitiesEvent(final RegistryEvent.Register<EntityEntry> event) {
		final IForgeRegistry<EntityEntry> registry = event.getRegistry();

//		event.getRegistry().register(buildEntityEntryFromClass(Entity___.class, hasEgg, range, updateFrequency, shouldSendVelocityUpdates));

		ExampleMod.info("Registered entities");

	}

	private static <T extends Entity & IModEntity> EntityEntry buildEntityEntryFromClass(final Class<T> clazz, final boolean hasEgg, final int range, final int updateFrequency, final boolean sendVelocityUpdates) {
		return buildEntityEntryFromClassWithName(clazz, new ResourceLocation(ModReference.MOD_ID, ModUtil.getRegistryNameForClass(clazz, "Entity")), hasEgg, range, updateFrequency, sendVelocityUpdates);
	}

	private static <T extends Entity & IModEntity> EntityEntry buildEntityEntryFromClassWithName(final Class<T> clazz, final ResourceLocation registryName, final boolean hasEgg, final int range, final int updateFrequency, final boolean sendVelocityUpdates) {
		EntityEntryBuilder<Entity> builder = EntityEntryBuilder.create();
		builder = builder.entity(clazz);
		builder = builder.id(registryName, entityId++);
		builder = builder.name(registryName.getPath());
		builder = builder.tracker(range, updateFrequency, sendVelocityUpdates);

		if (hasEgg) {
			builder = builder.egg(0xFFFFFF, 0xAAAAAA);
		}

		return builder.build();
	}

}