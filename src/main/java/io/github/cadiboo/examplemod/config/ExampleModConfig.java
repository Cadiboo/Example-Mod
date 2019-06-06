package io.github.cadiboo.examplemod.config;

import net.minecraft.item.EnumDyeColor;

import java.util.List;

/**
 * This holds the baked (runtime) values for our config.
 * These values should never be from changed outside this package.
 * This can be split into
 *
 * @author Cadiboo
 */
public final class ExampleModConfig {

	// Client
	public static boolean clientBoolean;
	public static List<String> clientStringList;
	public static EnumDyeColor clientEnumDyeColor;

	// Server
	public static boolean serverBoolean;
	public static List<String> serverStringList;
	public static EnumDyeColor serverEnumDyeColor;

}
