package cadiboo.examplemod.client;

import cadiboo.examplemod.util.IProxy;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;

/**
 * The version of IProxy that gets injected into {@link cadiboo.examplemod.ExampleMod#proxy} on a PHYSICAL CLIENT
 *
 * @author Cadiboo
 */
public class ClientProxy implements IProxy {

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
