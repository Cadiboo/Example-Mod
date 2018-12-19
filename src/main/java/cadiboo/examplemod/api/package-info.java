/**
 * API (Application Programming Interface) to allow other Mods to do stuff with/to our mod
 */

@API(apiVersion = VERSION, owner = MOD_ID, provides = MOD_NAME + " API")
package cadiboo.examplemod.api;

import net.minecraftforge.fml.common.API;

import static cadiboo.examplemod.util.ModReference.MOD_ID;
import static cadiboo.examplemod.util.ModReference.MOD_NAME;
import static cadiboo.examplemod.util.ModReference.Version.VERSION;
