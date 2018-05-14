package cadiboo.examplemod.server;

import cadiboo.examplemod.ExampleMod;
import cadiboo.examplemod.util.IProxy;
import net.minecraft.util.text.translation.I18n;

public class Proxy implements IProxy {

	@Override
	public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}

	@Override
	public void logLogicalSide() {
		ExampleMod.info("Logical Side: Server");
	}
	
	@Override
	public String getSide() {
		return "Server";
	}

}