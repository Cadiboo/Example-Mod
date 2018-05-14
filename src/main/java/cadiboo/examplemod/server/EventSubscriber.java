package cadiboo.examplemod.server;

import cadiboo.examplemod.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.SERVER, modid = Reference.ID)
public class EventSubscriber {

	@SubscribeEvent
	public void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
	}

}
