package io.github.cadiboo.examplemod.tileentity;

import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author Cadiboo
 */
public class MiniModelTileEntity extends TileEntity {

	// Constructor without hardcoded TileEntityType so that subclasses can use their own.
	public MiniModelTileEntity(final TileEntityType<?> type) {
		super(type);
	}

	public MiniModelTileEntity() {
		this(ModTileEntityTypes.MINI_MODEL);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		// This, combined with isGlobalRenderer in the TileEntityRenderer makes it so that the
		// render does not disappear if the player can't see the block
		// This would be useful for rendering larger models or dynamic models
		return INFINITE_EXTENT_AABB;
	}

}
