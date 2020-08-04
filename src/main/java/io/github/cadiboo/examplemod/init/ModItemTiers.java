package io.github.cadiboo.examplemod.init;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;

/**
 * This class holds all our ItemTiers.
 * Static initialisers are fine here.
 *
 * @author Cadiboo
 */
public class ModItemTiers {

	public static final IItemTier CRYSTAL = new ModItemTier(500, 6.0F, 2.0F, 2, 10, () -> Ingredient.fromItems(ModItems.EXAMPLE_CRYSTAL_SHARD.get()));

	/**
	 * Simple implementation of an IItemTier.
	 * Duplicates the functionality of {@link ItemTier}
	 *
	 * @author Cadiboo
	 */
	public static final class ModItemTier implements IItemTier {

		private int maxUses;
		private float efficiency;
		private float attackDamage;
		private int harvestLevel;
		private int enchantability;
		private Lazy<Ingredient> repairMaterial;

		public ModItemTier(final int maxUses, final float efficiency, final float attackDamage, final int harvestLevel, final int enchantability, final NonNullSupplier<Ingredient> repairMaterial) {
			this.maxUses = maxUses;
			this.efficiency = efficiency;
			this.attackDamage = attackDamage;
			this.harvestLevel = harvestLevel;
			this.enchantability = enchantability;
			this.repairMaterial = Lazy.of(repairMaterial::get);
		}

		@Override
		public int getMaxUses() {
			return maxUses;
		}

		@Override
		public float getEfficiency() {
			return efficiency;
		}

		@Override
		public float getAttackDamage() {
			return attackDamage;
		}

		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}

		@Override
		public int getEnchantability() {
			return enchantability;
		}

		@Override
		public Ingredient getRepairMaterial() {
			return repairMaterial.get();
		}

	}

}
