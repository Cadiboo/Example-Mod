package io.github.cadiboo.examplemod.config;

import net.minecraftforge.fml.config.ModConfig;

/**
 * This bakes the config values to normal fields
 *
 * @author Cadiboo
 * It can be merged into the main ExampleModConfig class, but is separate because of personal preference and to keep the code organised
 */
public final class ConfigHelper {

	public static void bakeClient(final ModConfig config) {
		ExampleModConfig.clientBoolean = ConfigHolder.CLIENT.clientBoolean.get();
		ExampleModConfig.clientStringList = ConfigHolder.CLIENT.clientStringList.get();
		ExampleModConfig.clientDyeColorEnum = ConfigHolder.CLIENT.clientDyeColorEnum.get();

		ExampleModConfig.modelTranslucency = ConfigHolder.CLIENT.modelTranslucency.get();
		ExampleModConfig.modelScale = ConfigHolder.CLIENT.modelScale.get().floatValue();
	}

	public static void bakeServer(final ModConfig config) {
		ExampleModConfig.serverBoolean = ConfigHolder.SERVER.serverBoolean.get();
		ExampleModConfig.serverStringList = ConfigHolder.SERVER.serverStringList.get();
		ExampleModConfig.serverEnumDyeColor = ConfigHolder.SERVER.serverEnumDyeColor.get();

		ExampleModConfig.electricFurnaceEnergySmeltCostPerTick = ConfigHolder.SERVER.electricFurnaceEnergySmeltCostPerTick.get();
		ExampleModConfig.heatCollectorTransferAmountPerTick = ConfigHolder.SERVER.heatCollectorTransferAmountPerTick.get();
	}

}
