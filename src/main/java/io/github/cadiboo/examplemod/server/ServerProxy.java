package io.github.cadiboo.examplemod.server;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.util.IProxy;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;

/**
 * The version of IProxy that gets injected into {@link ExampleMod#proxy} on a PHYSICAL/DEDICATED SERVER
 */
public class ServerProxy implements IProxy {

	@Override
	public String localize(final String unlocalized) {
		return I18n.translateToLocal(unlocalized);
	}

	@Override
	public String localizeAndFormat(final String unlocalized, final Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}

	@Override
	public Side getPhysicalSide() {
		return Side.SERVER;
	}

}
