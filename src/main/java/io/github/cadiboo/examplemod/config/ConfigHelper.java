package io.github.cadiboo.examplemod.config;

import net.minecraftforge.fml.config.ModConfig;

/**
 * This bakes the config values to normal fields
 *
 * @author Cadiboo
 * It can be merged into the main ExampleModConfig class, but is separate because of personal preference and to keep the code organised
 */
public final class ConfigHelper {

	// We store a reference to the ModConfigs here to be able to change the values in them from our code
	// (For example from a config GUI)
	private static ModConfig clientConfig;
	private static ModConfig serverConfig;

	public static void bakeClient(final ModConfig config) {
		clientConfig = config;

		ExampleModConfig.clientBoolean = ConfigHolder.CLIENT.clientBoolean.get();
		ExampleModConfig.clientStringList = ConfigHolder.CLIENT.clientStringList.get();
		ExampleModConfig.clientEnumDyeColor = ConfigHolder.CLIENT.clientEnumDyeColor.get();

	}

	public static void bakeServer(final ModConfig config) {
		serverConfig = config;

		ExampleModConfig.serverBoolean = ConfigHolder.SERVER.serverBoolean.get();
		ExampleModConfig.serverStringList = ConfigHolder.SERVER.serverStringList.get();
		ExampleModConfig.serverEnumDyeColor = ConfigHolder.SERVER.serverEnumDyeColor.get();

	}

	private static void setValueAndSave(final ModConfig modConfig, final String path, final Object newValue) {
		modConfig.getConfigData().set(path, newValue);
		modConfig.save();
	}

}
