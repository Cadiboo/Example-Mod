package io.github.cadiboo.examplemod.server

import io.github.cadiboo.examplemod.ExampleMod
import io.github.cadiboo.examplemod.util.IProxy
import net.minecraft.util.text.translation.I18n
import static net.minecraftforge.fml.relauncher.Side.SERVER

/**
 * The version of IProxy that gets injected into [ExampleMod.proxy] on a PHYSICAL/DEDICATED SERVER
 */
class ServerProxy : IProxy {

    override fun  getPhysicalSide() {
        return SERVER
    }

    override fun localize(unlocalized: String): String {
        return I18n.translateToLocal(unlocalized)
    }

    override fun localizeAndFormat(unlocalized: String, vararg args: Object): String {
        return I18n.translateToLocalFormatted(unlocalized, args)
    }

}
