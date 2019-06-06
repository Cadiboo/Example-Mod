package io.github.cadiboo.examplemod.config;

import io.github.cadiboo.examplemod.ExampleMod;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * For configuration settings that change the behaviour of code on the LOGICAL SERVER.
 * This can be moved to an inner class of ExampleModConfig, but is separate because of personal preference and to keep the code organised
 *
 * @author Cadiboo
 */
final class ServerConfig {

	final ForgeConfigSpec.BooleanValue serverBoolean;
	final ForgeConfigSpec.ConfigValue<List<String>> serverStringList;
	final ForgeConfigSpec.ConfigValue<EnumDyeColor> serverEnumDyeColor;

	ServerConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		serverBoolean = builder
				.comment("An example boolean in the server config")
				.translation(ExampleMod.MODID + ".config.serverBoolean")
				.define("serverBoolean", true);
		serverStringList = builder
				.comment("An example list of Strings in the server config")
				.translation(ExampleMod.MODID + ".config.serverStringList")
				.define("serverStringList", new ArrayList<>());
		serverEnumDyeColor = builder
				.comment("An example EnumDyeColor in the server config")
				.translation(ExampleMod.MODID + ".config.serverEnumColor")
				.defineEnum("serverEnumDyeColor", EnumDyeColor.WHITE);
		builder.pop();
	}

}
