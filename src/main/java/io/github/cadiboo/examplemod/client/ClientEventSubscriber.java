package io.github.cadiboo.examplemod.client;

import com.google.common.base.Preconditions;
import io.github.cadiboo.examplemod.block.IModBlock;
import io.github.cadiboo.examplemod.client.render.tileentity.RenderExampleTileEntity;
import io.github.cadiboo.examplemod.init.ModBlocks;
import io.github.cadiboo.examplemod.item.IModItem;
import io.github.cadiboo.examplemod.tileentity.TileEntityExampleTileEntity;
import io.github.cadiboo.examplemod.util.ModReference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

/**
 * Subscribe to events that should be handled on the PHYSICAL CLIENT in this class
 *
 * @author Cadiboo
 */
@Mod.EventBusSubscriber(modid = ModReference.MOD_ID, value = CLIENT)
public final class ClientEventSubscriber {

	private static final Logger LOGGER = LogManager.getLogger();
	private static final String DEFAULT_VARIANT = "normal";

	@SubscribeEvent
	public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event) {

		registerTileEntitySpecialRenderers();
		LOGGER.debug("Registered tile entity special renderers");

		registerEntityRenderers();
		LOGGER.debug("Registered entity renderers");

		ForgeRegistries.BLOCKS.getValuesCollection().stream()
				.filter(block -> block instanceof IModBlock)
				.forEach(ClientEventSubscriber::registerItemBlockModel);

		ForgeRegistries.ITEMS.getValuesCollection().stream()
				.filter(item -> item instanceof IModItem)
				.forEach(ClientEventSubscriber::registerItemModel);

		LOGGER.debug("Registered models");

	}

	private static void registerTileEntitySpecialRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExampleTileEntity.class, new RenderExampleTileEntity());
	}

	private static void registerEntityRenderers() {
//		RenderingRegistry.registerEntityRenderingHandler(Entity___.class, renderManager -> new Entity___Renderer(renderManager));
	}

	private static void registerItemModel(@Nonnull final Item item) {
		Preconditions.checkNotNull(item, "Item cannot be null!");
		final ResourceLocation registryName = item.getRegistryName();
		Preconditions.checkNotNull(registryName, "Item Registry Name cannot be null!");
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), DEFAULT_VARIANT));
	}

	private static void registerItemBlockModel(@Nonnull final Block block) {
		Preconditions.checkNotNull(block, "Block cannot be null!");
		final ResourceLocation registryName = block.getRegistryName();
		Preconditions.checkNotNull(registryName, "Block Registry Name cannot be null!");
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), DEFAULT_VARIANT));
	}

	@SubscribeEvent
	public static void onTextureStitchEvent(@Nonnull final TextureStitchEvent event) {
		// register texture for example tile entity
		final ResourceLocation registryName = ModBlocks.EXAMPLE_TILE_ENTITY.getRegistryName();
		event.getMap().registerSprite(new ResourceLocation(registryName.getNamespace(), "block/" + registryName.getPath()));
	}

}
