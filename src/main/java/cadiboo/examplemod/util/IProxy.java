package cadiboo.examplemod.util;

public interface IProxy {
	public String localize(String unlocalized, Object... args);

	public void logLogicalSide();

	public String getSide();
}
