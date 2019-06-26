package io.github.cadiboo.examplemod;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * Subscribe to events from the FORGE EventBus that should be handled on both PHYSICAL sides in this class
 *
 * @author Cadiboo
 */
@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {

}
