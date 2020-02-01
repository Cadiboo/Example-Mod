package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.tileentity.ElectricFurnaceTileEntity;
import io.github.cadiboo.examplemod.tileentity.HeatCollectorTileEntity;
import io.github.cadiboo.examplemod.tileentity.MiniModelTileEntity;
import io.github.cadiboo.examplemod.tileentity.ModFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds a list of all our {@link TileEntityType}s.
 * Suppliers that create TileEntityTypes are added to the DeferredRegister.
 * The DeferredRegister is then added to our mod event bus in our constructor.
 * When the TileEntityType Registry Event is fired by Forge and it is time for the mod to
 * register its TileEntityTypes, our TileEntityTypes are created and registered by the DeferredRegister.
 * The TileEntityType Registry Event will always be called after the Block and Item registries are filled.
 * Note: This supports registry overrides.
 *
 * @author Cadiboo
 */
public final class ModTileEntityTypes {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ExampleMod.MODID);

	// We don't have a datafixer for our TileEntities, so we pass null into build.
	public static final RegistryObject<TileEntityType<MiniModelTileEntity>> MINI_MODEL = TILE_ENTITY_TYPES.register("mini_model", () ->
			TileEntityType.Builder.create(MiniModelTileEntity::new, ModBlocks.MINI_MODEL.get())
					.build(null)
	);
	public static final RegistryObject<TileEntityType<HeatCollectorTileEntity>> HEAT_COLLECTOR = TILE_ENTITY_TYPES.register("heat_collector", () ->
			TileEntityType.Builder.create(HeatCollectorTileEntity::new, ModBlocks.HEAT_COLLECTOR.get())
					.build(null)
	);
	public static final RegistryObject<TileEntityType<ElectricFurnaceTileEntity>> ELECTRIC_FURNACE = TILE_ENTITY_TYPES.register("electric_furnace", () ->
			TileEntityType.Builder.create(ElectricFurnaceTileEntity::new, ModBlocks.ELECTRIC_FURNACE.get())
					.build(null)
	);
	public static final RegistryObject<TileEntityType<ModFurnaceTileEntity>> MOD_FURNACE = TILE_ENTITY_TYPES.register("mod_furnace", () ->
			TileEntityType.Builder.create(ModFurnaceTileEntity::new, ModBlocks.MOD_FURNACE.get())
					.build(null)
	);

}
