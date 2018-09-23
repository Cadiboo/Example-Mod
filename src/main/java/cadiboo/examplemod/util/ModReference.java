package cadiboo.examplemod.util;

/**
 * Holds mod-wide constant values
 *
 * @author Cadiboo
 */
public final class ModReference {

	/** This is our Mod's Name. */
	public static final String	MOD_NAME	= "Cadiboo's Example Mod";

	/** This is our Mod's Mod Id that is used for stuff like resource locations. */
	public static final String	MOD_ID		= "examplemod";

	/**
	 * @author Cadiboo
	 * @see <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/">Forge Versioning Docs</a>
	 */
	public static final class Version {
		/**
		 * This is the Minecraft version we're modding for.<br>
		 * It is changed every time we start modding for a new Minecraft version.<br>
		 * It is never reset.
		 */
		public static final String	MINECRAFT_VERSION	= "1.12.2";

		/**
		 * This is our Mod's Major Mod Version.<br>
		 * It is changed when modify game mechanics or remove items, blocks, tile entities, etc.<br>
		 * It is never reset.
		 */
		public static final int		MAJOR_MOD_VERSION	= 0;

		/**
		 * This is our Mod's Major API Version.<br>
		 * It is changed when modify API mechanics by changing the order or variables of enums, changing return types of methods or remove public methods altogether.<br>
		 * It is reset when we update to a new Minecraft version or our Major Mod Version increments.
		 */
		public static final int		MAJOR_API_VERSION	= 0;

		/**
		 * This is our Mod's Minor Mod Version.<br>
		 * It is changed when add new mechanics or add items, blocks, tile entities, etc. It is also changed when we depreciate public methods in our API (This is because the change not considered a Major API change since it doesn’t break the API).<br>
		 * It is reset when we update to a new Minecraft version, our Major Mod Version increments or our Major API Version increments.
		 */
		public static final int		MINOR_MOD_VERSION	= 0;

		/**
		 * This is our Mod's Patch Version.<br>
		 * It is changed when we patch small problems that do not cause a change to any greater versions.<br>
		 * It is reset when we update to a new Minecraft version, our Major Mod Version increments, our Major API Version increments or our Minor Mod Version increments.
		 */
		public static final int		PATCH_VERSION		= 0;

		/**
		 * This is our Mod's PreRelease Version.<br>
		 * It is changed when we add new features that are not quite done yet.<br>
		 * It is reset when we update to a new Minecraft version, our Major Mod Version increments, our Major API Version increments, our Minor Mod Version increments or our Patch Version increments.
		 */
		public static final int		PRE_RELEASE_VERSION	= 0;

		/**
		 * This is our Mod's Suffix including our PreRelease Version.<br>
		 * It can be any one of the following values:
		 * <ul>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#final-release">final</a><br>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#pre-releases">alpha</a><br>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#pre-releases">beta</a><br>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#pre-releases">betaX</a><br>
		 * <a href= "https://mcforge.readthedocs.io/en/latest/conventions/versioning/#release-candidates">rcX</a><br>
		 * </ul>
		 */
		@SuppressWarnings("unused")
		public static final String	VERSION_SUFFIX		= "alpha" + (PRE_RELEASE_VERSION > 0 ? PRE_RELEASE_VERSION : "");

		/**
		 * This is our Mod's Version.<br>
		 * It is our Mod's Name, our Mod's Major Mod version, our Mod's Major API version, our Mod's Minor Mod version and our Mod's Patch version in the format <code>MAJORMOD.MAJORAPI.MINOR.PATCH</code>
		 */
		public static final String	VERSION				= MAJOR_MOD_VERSION + "." + MAJOR_API_VERSION + "." + MINOR_MOD_VERSION + "." + PATCH_VERSION;

		/**
		 * This is our Mod's full Version.<br>
		 * It is our Mod's Name, our Mod's Minecraft Version, our Mod's Major Mod version, our Mod's Major API version, our Mod's Minor Mod version, our Mod's Patch version and our Mod's Suffix in the format <code>MODNAME MCVERSION-MAJORMOD.MAJORAPI.MINOR.PATCH[-SUFFIX]</code>
		 */
		public static final String	FULL_VERSION		= MOD_NAME + "" + MINECRAFT_VERSION + "-" + VERSION + (VERSION_SUFFIX.length() > 0 ? "-" + VERSION_SUFFIX : "");

	}

	public static final String	CLIENT_PROXY_CLASS	= "cadiboo.examplemod.client.ClientProxy";
	public static final String	SERVER_PROXY_CLASS	= "cadiboo.examplemod.server.ServerProxy";

	public static final String	ACCEPTED_VERSIONS	= "[1.12.2]";
	public static final String	DEPENDENCIES		= "required-after:minecraft;" + "required-after:forge@[14.23.4.2704,);" + "";

}
