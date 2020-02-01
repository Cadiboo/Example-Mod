package io.github.cadiboo.examplemod.entity;

import io.github.cadiboo.examplemod.init.ModEntityTypes;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.world.World;

/**
 * A Wild Boar entity.
 * Literally just a pig (with a different texture).
 * TODO: Will have more stuff added to it soon.
 *
 * @author Cadiboo
 */
public class WildBoarEntity extends PigEntity {

	public WildBoarEntity(final EntityType<? extends WildBoarEntity> entityType, final World world) {
		super(entityType, world);
	}

	public WildBoarEntity(final World world) {
		super(ModEntityTypes.WILD_BOAR.get(), world);
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();

		final double baseSpeed = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
		final double baseHealth = this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
		// Multiply base health and base speed by one and a half
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(baseSpeed * 1.5D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(baseHealth * 1.5D);
	}

	@Override
	public WildBoarEntity createChild(AgeableEntity parent) {
		// Use getType to support overrides in subclasses
		return (WildBoarEntity) getType().create(this.world);
	}

}
