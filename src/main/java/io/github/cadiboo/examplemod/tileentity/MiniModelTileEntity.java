package io.github.cadiboo.examplemod.tileentity;

import io.github.cadiboo.examplemod.client.render.MiniModel;
import io.github.cadiboo.examplemod.init.ModTileEntityTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

/**
 * This is the TileEntity for our MiniModel.
 * It stores the render data for its surroundings.
 * <p>
 * Rendering code only exists on the client distribution so @OnlyIn is used a lot.
 * The @OnlyIn annotation makes it so that a class/field/method annotated with it does not
 * exist on the other distribution. This is important as TileEntities exist on both distributions
 * but rendering only happens on the client.
 * As a general rule you should avoid using @OnlyIn unless it is absolutely necessary.
 *
 * @author Cadiboo
 */
public class MiniModelTileEntity extends TileEntity {

	@Nullable // May be accessed before onLoad
	// @OnlyIn(Dist.CLIENT) Makes it so this field will be removed from the class on the PHYSICAL SERVER
	// This is because we only want the MiniModel on the physical client - its rendering only.
	@OnlyIn(Dist.CLIENT)
	public MiniModel miniModel;

	// Constructor without hardcoded TileEntityType so that subclasses can use their own.
	// Alternatively, subclasses can also override getType if a hardcoded type is used in a superclass' constructor
	public MiniModelTileEntity(final TileEntityType<?> type) {
		super(type);
	}

	public MiniModelTileEntity() {
		this(ModTileEntityTypes.MINI_MODEL.get());
	}

	// @OnlyIn(Dist.CLIENT) Makes it so this method will be removed from the class on the PHYSICAL SERVER
	// This is because we only want the MiniModel on the physical client - its rendering only.
	@OnlyIn(Dist.CLIENT)
	@Override
	public void onLoad() {
		super.onLoad();
		World world = getWorld();
		if (world == null || !world.isRemote)
			return; // Return if the world is null or if we are on the logical server
		miniModel = MiniModel.forTileEntity(this);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		// This, combined with isGlobalRenderer in the TileEntityRenderer makes it so that the
		// render does not disappear if the player can't see the block
		// This is useful for rendering larger models or dynamically sized models
		return INFINITE_EXTENT_AABB;
	}

}
