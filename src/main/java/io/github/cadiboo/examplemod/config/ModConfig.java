package io.github.cadiboo.examplemod.config;

import io.github.cadiboo.examplemod.util.ModReference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Our Mod's configuration
 *
 * @author Cadiboo
 */
@Config(modid = ModReference.MOD_ID)
@LangKey(ModReference.MOD_ID + ".config.title")
public class ModConfig {

	@Comment("Numbers")
	public static final Numbers numbers = new Numbers(1, 2.5f, 3.1d);

	public static class Numbers {

		public Numbers(final int int_, final float float_, final double double_) {
			this.int_ = int_;
			this.float_ = float_;
			this.double_ = double_;
		}

		@Comment("An Integer (int) type number")
		public int int_;
		@Comment("A Float (floating point) type number")
		public float float_;
		@Comment("A Double (double length floating point) type number")
		public double double_;

	}

	@Mod.EventBusSubscriber(modid = ModReference.MOD_ID)
	private static class EventHandler {

		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(ModReference.MOD_ID)) {
				ConfigManager.sync(ModReference.MOD_ID, Config.Type.INSTANCE);
			}
		}

	}

}
