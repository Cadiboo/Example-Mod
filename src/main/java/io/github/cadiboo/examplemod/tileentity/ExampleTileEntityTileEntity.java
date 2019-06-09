package io.github.cadiboo.examplemod.tileentity;

import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * @author Cadiboo
 */
public class ExampleTileEntityTileEntity extends TileEntity {

	// Constructor without hardcoded TileEntityType so that subclasses can use their own.
	public ExampleTileEntityTileEntity(final TileEntityType<?> type) {
		super(type);
	}

	public ExampleTileEntityTileEntity() {
		this(ModTileEntityTypes.EXAMPLE_TILE_ENTITY);
	}

}
