package io.github.cadiboo.examplemod.init;

import io.github.cadiboo.examplemod.ExampleMod;
import io.github.cadiboo.examplemod.entity.WildBoarEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Holds a list of all our {@link EntityType}s.
 * Suppliers that create EntityTypes are added to the DeferredRegister.
 * The DeferredRegister is then added to our mod event bus in our constructor.
 * When the EntityType Registry Event is fired by Forge and it is time for the mod to
 * register its EntityTypes, our EntityTypes are created and registered by the DeferredRegister.
 * The EntityType Registry Event will always be called after the Block and Item registries are filled.
 * Note: This supports registry overrides.
 *
 * @author Cadiboo
 */
public final class ModEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, ExampleMod.MODID);

	public static final String WILD_BOAR_NAME = "wild_boar";
	public static final RegistryObject<EntityType<WildBoarEntity>> WILD_BOAR = ENTITY_TYPES.register(WILD_BOAR_NAME, () ->
			EntityType.Builder.<WildBoarEntity>create(WildBoarEntity::new, EntityClassification.CREATURE)
					.size(EntityType.PIG.getWidth(), EntityType.PIG.getHeight())
					.build(new ResourceLocation(ExampleMod.MODID, WILD_BOAR_NAME).toString())
	);

}
