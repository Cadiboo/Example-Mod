package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.ModUtil;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

/**
 * @author Cadiboo
 */
@ObjectHolder(ExampleMod.MODID)
public final class ModTileEntityTypes {

	public static final TileEntityType<?> MINI_MODEL = ModUtil._null();

	public static final TileEntityType<?> HEAT_COLLECTOR = ModUtil._null();

}
