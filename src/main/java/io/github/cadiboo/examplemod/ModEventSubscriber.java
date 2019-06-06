package io.github.cadiboo.examplemod;

import com.google.common.base.Preconditions;
import io.github.cadiboo.examplemod.config.ConfigHelper;
import io.github.cadiboo.examplemod.config.ConfigHolder;
import io.github.cadiboo.examplemod.init.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Objects;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
				setup(new Block(Block.Properties.create(Material.ROCK)), "example_ore"),
				setup(new Block(Block.Properties.create(Material.IRON)), "example_block")
		);

		// Register TileEntities

	}

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		final ModConfig config = event.getConfig();
		// Rebake the configs when they change
		if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
			ConfigHelper.bakeClient(config);
		} else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
			ConfigHelper.bakeServer(config);
		}
	}
	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		registry.registerAll(
				setup(new Item(new Item.Properties()), "example_item")
		);
		ForgeRegistries.BLOCKS.getValues().stream()
				.filter(block -> Objects.requireNonNull(block.getRegistryName(), "Registry Name of Block \"" + block + "\" is null! This is not allowed!").getNamespace().equals(ExampleMod.MODID))
//				.filter(block -> block instanceof NoAutomaticItemBlock) // If you have blocks that don't have a corresponding ItemBlock, uncomment this code and create an Interface - or even better an Annotation - called NoAutomaticItemBlock with no methods and implement it on your blocks that shouldn't have ItemBlocks
				.forEach(block -> registry.register(setup(new ItemBlock(block, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), block.getRegistryName())));
	}

	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name) {
		Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
		return setup(entry, new ResourceLocation(ExampleMod.MODID, name));
	}

	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
		Preconditions.checkNotNull(entry, "Entry cannot be null!");
		Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
		entry.setRegistryName(registryName);
		return entry;
	}

}
