package io.github.cadiboo.examplemod.util;

import io.github.cadiboo.examplemod.ExampleMod;
import org.apache.commons.lang3.StringUtils;

/**
 * Holds mod-wide constant values
 *
 * @author Cadiboo
 */
public final class ModReference {

	/**
	 * This is our Mod's Name.
	 */
	public static final String MOD_NAME = "Cadiboo's Example Mod";

	/**
	 * This is our Mod's Mod Id that is used for stuff like resource locations.
	 */
	public static final String MOD_ID = "examplemod";
	/**
	 * The fully qualified name of the version of IProxy that gets injected into {@link ExampleMod#proxy} on a PHYSICAL CLIENT
	 */
	public static final String CLIENT_PROXY_CLASS = "io.github.cadiboo.examplemod.client.ClientProxy";
	/**
	 * The fully qualified name of the version of IProxy that gets injected into {@link ExampleMod#proxy} on a PHYSICAL/DEDICATED SERVER
	 */
	public static final String SERVER_PROXY_CLASS = "io.github.cadiboo.examplemod.server.ServerProxy";
	/**
	 * @see "https://maven.apache.org/enforcer/enforcer-rules/versionRanges.html"
	 */
	public static final String ACCEPTED_VERSIONS = "[1.12.2]";
	/**
	 * @see "https://maven.apache.org/enforcer/enforcer-rules/versionRanges.html"
	 */
	public static final String DEPENDENCIES = "" +
			"required-after:minecraft;" +
			"required-after:forge@[14.23.4.2704,);" +
			"";
	static {
		final String modId = StringUtils.substring(MOD_ID, 0, 64);
		if (modId.length() != MOD_ID.length()) {
			// Mod ID CAN'T be longer than 64 characters due to Forge's system
			throw new RuntimeException("Mod ID can't be longer than 64 characters! " + MOD_ID + ", " + modId);
		}
	}

	/**
	 * @author Cadiboo
	 * @see <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/">Forge Versioning Docs</a>
	 */
	// TODO: Remove in 1.13 and put in mods.toml
	public static final class Version {

		/**
		 * This is the Minecraft version we're modding for.<br>
		 * It is changed every time we start modding for a new Minecraft version.<br>
		 * It is never reset.
		 */
		public static final String MINECRAFT_VERSION = "1.12.2";

		/**
		 * This is our Mod's Major Version.<br>
		 * It is changed when modify game mechanics or remove items, blocks, tile entities, etc.<br>
		 * It is never reset.
		 */
		public static final int MAJOR_VERSION = 0;
		/**
		 * This is our Mod's Minor Version.<br>
		 * It is changed when add new mechanics or add items, blocks, tile entities, etc. It is also changed when we depreciate public methods in our API (This is because the change not considered a Major API change since it doesn’t break the API).<br>
		 * It is reset when we update to a new Minecraft version or our Major Version increments.
		 */
		public static final int MINOR_VERSION = 0;

		/**
		 * This is our Mod's Patch Version.<br>
		 * It is changed when we patch small problems that do not cause a change to any greater versions.<br>
		 * It is reset when we update to a new Minecraft version, our Major Version increments or our Minor Version increments.
		 */
		public static final int PATCH_VERSION = 0;

		/**
		 * This is our Mod's PreRelease Version.<br>
		 * It is changed when we add new features that are not quite done yet.<br>
		 * It is reset when we update to a new Minecraft version, our Major Version increments, our Minor Version increments or our Patch Version increments.
		 */
		public static final int PRE_RELEASE_VERSION = 0;

		/**
		 * This is our Mod's Suffix including our PreRelease Version.<br>
		 * It can be any one of the following values:
		 * <ul>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#final-release">final</a>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#pre-releases">alpha</a>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#pre-releases">beta</a>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#pre-releases">betaX</a>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#release-candidates">rcX</a>
		 * </ul>
		 */
		public static final String VERSION_SUFFIX = "alpha" + (PRE_RELEASE_VERSION > 0 ? PRE_RELEASE_VERSION : "");

		/**
		 * This is our Mod's Version.<br>
		 * It is our Mod's Name, our Mod's Major Mod version, our Mod's Minor Mod version and our Mod's Patch version in the format <code>MAJOR.MINOR.PATCH</code>
		 */
		public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION;

		/**
		 * This is our Mod's full Version.<br>
		 * It is our Mod's Name, our Mod's Minecraft Version, our Mod's Major version, our Mod's Minor version, our Mod's Patch version and our Mod's Suffix in the format <code>MODNAME-MCVERSION-MAJOR.MINOR.PATCH[-SUFFIX]</code>
		 */
		public static final String FULL_VERSION = MOD_NAME + "-" + MINECRAFT_VERSION + "-" + VERSION + (VERSION_SUFFIX.length() > 0 ? "-" + VERSION_SUFFIX : "");

	}

}
