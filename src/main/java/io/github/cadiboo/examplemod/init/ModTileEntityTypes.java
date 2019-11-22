package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.ModUtil;
import io.github.cadiboo.examplemod.tileentity.HeatCollectorTileEntity;
import io.github.cadiboo.examplemod.tileentity.MiniModelTileEntity;
import io.github.cadiboo.examplemod.tileentity.ModFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

/**
 * @author Cadiboo
 */
@ObjectHolder(ExampleMod.MODID)
public final class ModTileEntityTypes {

	public static final TileEntityType<MiniModelTileEntity> MINI_MODEL = ModUtil._null();
	public static final TileEntityType<HeatCollectorTileEntity> HEAT_COLLECTOR = ModUtil._null();
	public static final TileEntityType<ModFurnaceTileEntity> MOD_FURNACE = ModUtil._null();

}
