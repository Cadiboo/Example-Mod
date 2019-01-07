package io.github.cadiboo.examplemod.util

import io.github.cadiboo.examplemod.ExampleMod

/**
 * Holds mod-wide constant values
 *
 * @author Cadiboo
 */
@SuppressWarnings("WeakerAccess")
object ModReference {

    /**
     * This is our Mod's Name.
     */
    const val MOD_NAME = "Cadiboo's Example Mod"

    /**
     * This is our Mod's Mod Id that is used for stuff like resource locations.
     */
    const val MOD_ID = "examplemod"

    /**
     * The fully qualified name of the version of IProxy that gets injected into [ExampleMod.proxy] on a PHYSICAL CLIENT
     */
    const val CLIENT_PROXY_CLASS = "io.github.cadiboo.examplemod.client.ClientProxy"

    /**
     * The fully qualified name of the version of IProxy that gets injected into [ExampleMod.proxy] on a PHYSICAL/DEDICATED SERVER
     */
    const val SERVER_PROXY_CLASS = "io.github.cadiboo.examplemod.server.ServerProxy"

    /**
     * @see "https://maven.apache.org/enforcer/enforcer-rules/versionRanges.html"
     */
    const val ACCEPTED_VERSIONS = "[1.12.2]"

    /**
     * @see "https://maven.apache.org/enforcer/enforcer-rules/versionRanges.html"
     */
    const val DEPENDENCIES = "" +
            "required-after:minecraft;" +
            "required-after:forge@[14.23.5.2768,);" +
            ""

    /**
     * "@VERSION@" is replaced by build.gradle with the actual version
     *
     * @see [Forge Versioning Docs](https://mcforge.readthedocs.io/en/latest/conventions/versioning/)
     */
    const val VERSION = "@VERSION@"

    /**
     * "@FINGERPRINT@" is replaced by build.gradle with the actual fingerprint
     *
     * @see "https://tutorials.darkhax.net/tutorials/jar_signing/"
     */
    const val CERTIFICATE_FINGERPRINT = "@FINGERPRINT@"

}
