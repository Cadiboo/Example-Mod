package io.github.cadiboo.examplemod.server;

import io.github.cadiboo.examplemod.util.ModReference;
import net.minecraftforge.fml.common.Mod;

import static io.github.cadiboo.examplemod.util.ModReference.*;
import static net.minecraftforge.fml.relauncher.Side.SERVER;

/**
 * Subscribe to events that should be handled on the PHYSICAL/DEDICATED SERVER in this class
 */
@Mod.EventBusSubscriber(modid = MOD_ID, value = SERVER)
public final class ServerEventSubscriber {

}
