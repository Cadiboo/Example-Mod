package cadiboo.examplemod.config;

import cadiboo.examplemod.util.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.common.config.Config(modid = Reference.ID)
@net.minecraftforge.common.config.Config.LangKey(Reference.ID + ".config.title")
public class Configuration {

	@Comment("Numbers")
	public static final Numbers numbers = new Numbers(0, 1,2);

	public static class Numbers {

		public Numbers(/*long long_, */int int_, float float_, double double_) {
			//this.long_ = long_;
			this.int_ = int_;
			this.float_ = float_;
			this.double_ = double_;
		}

//		@Comment("A Long type number")
//		public long	long_;
		@Comment("An Integer (int) type number")
		public int	int_;
		@Comment("A Float (floating point) type number")
		public float	 float_;
		@Comment("A Double (double length floating point) type number")
		public double	double_;
	}

	@Mod.EventBusSubscriber(modid = Reference.ID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been
		 * changed from the GUI.
		 *
		 * @param event
		 *            The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.ID)) {
				ConfigManager.sync(Reference.ID, Config.Type.INSTANCE);
			}
		}
	}

}
