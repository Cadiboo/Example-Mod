package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.ModUtil;
import io.github.cadiboo.examplemod.container.HeatCollectorContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

/**
 * @author Cadiboo
 */
@ObjectHolder(ExampleMod.MODID)
public final class ModContainerTypes {

	public static final ContainerType<HeatCollectorContainer> HEAT_COLLECTOR = ModUtil._null();

}
