package io.github.cadiboo.examplemod.client;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.client.render.tileentity.MiniModelTileEntityRenderer;
import io.github.cadiboo.examplemod.tileentity.MiniModelTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ClientForgeEventSubscriber {

	@SubscribeEvent
	public static void onRegisterModels(final ModelRegistryEvent event) {

		// Register TileEntity Renderers
		ClientRegistry.bindTileEntitySpecialRenderer(MiniModelTileEntity.class, new MiniModelTileEntityRenderer());

		// Register Entity Renderers

	}

}
