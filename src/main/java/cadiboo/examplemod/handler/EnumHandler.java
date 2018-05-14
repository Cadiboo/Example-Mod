package cadiboo.examplemod.handler;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {

	public static enum ResourceBlocks implements IStringSerializable {

		EXAMPLE(0, "example");

		private int		ID;
		private String	name;

		private ResourceBlocks(int id, String name) {
			this.ID = id;
			this.name = name;
		}

		public static ResourceBlocks byID(int i) {
			return ResourceBlocks.values()[i];
		}

		@Override
		public String getName() {
			return this.name;
		}

		public int getID() {
			return this.ID;
		}

	}

	public static enum Ores implements IStringSerializable {

		EXAMPLE(0, "example");

		private int		ID;
		private String	name;

		private Ores(int id, String name) {
			this.ID = id;
			this.name = name;
		}

		public static Ores byID(int i) {
			return Ores.values()[i];
		}

		@Override
		public String getName() {
			return this.name;
		}

		public int getID() {
			return this.ID;
		}

	}

}