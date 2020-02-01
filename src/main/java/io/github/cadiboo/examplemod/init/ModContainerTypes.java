package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.container.ElectricFurnaceContainer;
import io.github.cadiboo.examplemod.container.HeatCollectorContainer;
import io.github.cadiboo.examplemod.container.ModFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds a list of all our {@link ContainerType}s.
 * Suppliers that create ContainerTypes are added to the DeferredRegister.
 * The DeferredRegister is then added to our mod event bus in our constructor.
 * When the ContainerType Registry Event is fired by Forge and it is time for the mod to
 * register its ContainerTypes, our ContainerTypes are created and registered by the DeferredRegister.
 * The ContainerType Registry Event will always be called after the Block and Item registries are filled.
 * Note: This supports registry overrides.
 *
 * @author Cadiboo
 */
public final class ModContainerTypes {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, ExampleMod.MODID);

	public static final RegistryObject<ContainerType<HeatCollectorContainer>> HEAT_COLLECTOR = CONTAINER_TYPES.register("heat_collector", () -> IForgeContainerType.create(HeatCollectorContainer::new));
	public static final RegistryObject<ContainerType<ElectricFurnaceContainer>> ELECTRIC_FURNACE = CONTAINER_TYPES.register("electric_furnace", () -> IForgeContainerType.create(ElectricFurnaceContainer::new));
	public static final RegistryObject<ContainerType<ModFurnaceContainer>> MOD_FURNACE = CONTAINER_TYPES.register("mod_furnace", () -> IForgeContainerType.create(ModFurnaceContainer::new));

}
