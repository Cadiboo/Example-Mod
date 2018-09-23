package cadiboo.examplemod.client;

import cadiboo.examplemod.ExampleMod;
import cadiboo.examplemod.init.ModBlocks;
import cadiboo.examplemod.init.ModItems;
import cadiboo.examplemod.util.ModReference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModReference.MOD_ID)
public final class ClientEventSubscriber {

	private static final String DEFAULT_VARIANT = "normal";

	@SubscribeEvent
	public static void onRegisterModelsEvent(final ModelRegistryEvent event) {

		registerTileEntitySpecialRenderers();
		ExampleMod.info("Registered tile entity special renderers");

		registerEntityRenderers();
		ExampleMod.info("Registered entity renderers");

		/* item blocks */
		registerItemBlockModel(ModBlocks.EXAMPLE_BLOCK);
		registerItemBlockModel(ModBlocks.EXAMPLE_ORE);

		/* items */
		registerItemModel(ModItems.EXAMPLE_ITEM);

		ExampleMod.info("Registered models");

	}

	private static void registerTileEntitySpecialRenderers() {
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntity___.class, new TileEntity___Renderer());
	}

	private static void registerEntityRenderers() {
//		RenderingRegistry.registerEntityRenderingHandler(Entity___.class, renderManager -> new Entity___Renderer(renderManager));
	}

	private static void registerItemModel(final Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), DEFAULT_VARIANT));
	}

	private static void registerItemBlockModel(final Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), DEFAULT_VARIANT));
	}

}