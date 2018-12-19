package io.github.cadiboo.examplemod.server;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

import io.github.cadiboo.examplemod.util.ModReference;
import net.minecraftforge.fml.common.Mod;

/**
 * Subscribe to events that should be handled on the PHYSICAL/DEDICATED SERVER in this class
 */
@Mod.EventBusSubscriber(modid = ModReference.MOD_ID, value = CLIENT)
public final class ServerEventSubscriber {

}