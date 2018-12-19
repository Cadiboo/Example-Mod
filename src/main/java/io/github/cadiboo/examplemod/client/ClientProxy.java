package io.github.cadiboo.examplemod.client;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.util.IProxy;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;

/**
 * The version of IProxy that gets injected into {@link ExampleMod#proxy} on a PHYSICAL CLIENT
 *
 * @author Cadiboo
 */
public final class ClientProxy implements IProxy {

	@Override
	public String localize(final String unlocalized) {
		return this.localizeAndFormat(unlocalized);
	}

	@Override
	public String localizeAndFormat(final String unlocalized, final Object... args) {
		return I18n.format(unlocalized, args);
	}

	@Override
	public Side getPhysicalSide() {
		return Side.CLIENT;
	}

}
