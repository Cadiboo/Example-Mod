package cadiboo.examplemod.server;

import static cadiboo.examplemod.util.ModReference.MOD_ID;
import static net.minecraftforge.fml.relauncher.Side.CLIENT;

import net.minecraftforge.fml.common.Mod;

/**
 * Subscribe to events that should be handled on the PHYSICAL/DEDICATED SERVER in this class
 */
@Mod.EventBusSubscriber(modid = MOD_ID, value = CLIENT)
public final class ServerEventSubscriber {

}