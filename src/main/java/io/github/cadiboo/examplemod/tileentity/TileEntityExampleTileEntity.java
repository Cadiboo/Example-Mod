package io.github.cadiboo.examplemod.tileentity;

import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * @author Cadiboo
 */
public class TileEntityExampleTileEntity extends TileEntity {

	public TileEntityExampleTileEntity(final TileEntityType<?> type) {
		super(type);
	}
	public TileEntityExampleTileEntity() {
		super(ModTileEntityTypes.EXAMPLE_TILE_ENTITY);
	}

}
