package io.github.cadiboo.examplemod;

import com.google.common.base.Preconditions;
import io.github.cadiboo.examplemod.block.BlockExampleTileEntity;
import io.github.cadiboo.examplemod.block.BlockModOre;
import io.github.cadiboo.examplemod.block.BlockResource;
import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.item.ItemExampleIngot;
import io.github.cadiboo.examplemod.tileentity.TileEntityExampleTileEntity;
import io.github.cadiboo.examplemod.util.ModReference;
import io.github.cadiboo.examplemod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Subscribe to events that should be handled on both PHYSICAL sides in this class
 */
@Mod.EventBusSubscriber(modid = ModReference.MOD_ID)
public final class EventSubscriber {

	private static int entityId = 0;

	/* register blocks */
	@SubscribeEvent
	public static void onRegisterBlocksEvent(@Nonnull final RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(new BlockResource("example_block"));
		registry.register(new BlockModOre("example_ore"));
		registry.register(new BlockExampleTileEntity("example_tile_entity"));

		ExampleMod.EXAMPLE_MOD_LOG.debug("Registered blocks");

		registerTileEntities();

		ExampleMod.EXAMPLE_MOD_LOG.debug("Registered tile entities");

	}

	private static void registerTileEntities() {
		registerTileEntity(TileEntityExampleTileEntity.class, "example_tile_entity");
	}

	private static void registerTileEntity(@Nonnull final Class<? extends TileEntity> clazz, String name) {
		try {
			GameRegistry.registerTileEntity(clazz, new ResourceLocation(ModReference.MOD_ID, name));
		} catch (final Exception exception) {
			CrashReport crashReport = new CrashReport("Error registering Tile Entity " + clazz.getSimpleName(), exception);
			crashReport.makeCategory("Registering Tile Entity");
			throw new ReportedException(crashReport);
		}
	}

	/* register items */
	@SubscribeEvent
	public static void onRegisterItemsEvent(@Nonnull final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		// item blocks
		// make an array of all the blocks we want to have items
		Arrays.stream(new Block[]{

				ModBlocks.EXAMPLE_BLOCK,
				ModBlocks.EXAMPLE_ORE,
				ModBlocks.EXAMPLE_TILE_ENTITY

		}).forEach(block -> {
			Preconditions.checkNotNull(block.getRegistryName(), "Registry name cannot be null!");
			registry.register(
					ModUtil.setCreativeTab( // set it's creative tab to our creativetab (Optional)
							ModUtil.setRegistryNames( //set its name
									new ItemBlock(block), //make the itemblock
									block.getRegistryName())
					)
			);
		});

		// items
		// you can also instantiate items like this, however its not often used for a number of reasons
		registry.register(ModUtil.setCreativeTab(ModUtil.setRegistryNames(new Item(), "example_item")));
		registry.register(new ItemExampleIngot("example_ingot"));

		ExampleMod.EXAMPLE_MOD_LOG.debug("Registered items");

	}

	/* register entities */
	@SubscribeEvent
	public static void onRegisterEntitiesEvent(@Nonnull final RegistryEvent.Register<EntityEntry> event) {
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
