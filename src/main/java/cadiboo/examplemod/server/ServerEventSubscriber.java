package cadiboo.examplemod.server;

/**you can also import constants directly*/
import static cadiboo.examplemod.util.ModReference.MOD_ID;
import static net.minecraftforge.fml.relauncher.Side.CLIENT;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MOD_ID, value = CLIENT)
public final class ServerEventSubscriber {

}