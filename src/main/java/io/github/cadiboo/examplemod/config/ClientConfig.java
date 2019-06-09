package io.github.cadiboo.examplemod.config;

import io.github.cadiboo.examplemod.ExampleMod;
import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * For configuration settings that change the behaviour of code on the LOGICAL CLIENT.
 * This can be moved to an inner class of ExampleModConfig, but is separate because of personal preference and to keep the code organised
 *
 * @author Cadiboo
 */
final class ClientConfig {

	final ForgeConfigSpec.BooleanValue clientBoolean;
	final ForgeConfigSpec.ConfigValue<List<String>> clientStringList;
	final ForgeConfigSpec.ConfigValue<DyeColor> clientEnumDyeColor;

	ClientConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		clientBoolean = builder
				.comment("An example boolean in the client config")
				.translation(ExampleMod.MODID + ".config.clientBoolean")
				.define("clientBoolean", true);
		clientStringList = builder
				.comment("An example list of Strings in the client config")
				.translation(ExampleMod.MODID + ".config.clientStringList")
				.define("clientStringList", new ArrayList<>());
		clientEnumDyeColor = builder
				.comment("An example enum DyeColor in the client config")
				.translation(ExampleMod.MODID + ".config.clientEnumDyeColor")
				.defineEnum("clientEnumDyeColor", DyeColor.WHITE);
		builder.pop();
	}

}
