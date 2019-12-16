package io.github.cadiboo.examplemod.container;

import net.minecraft.util.IntReferenceHolder;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

/**
 * An {@link IntReferenceHolder} that uses {@link IntSupplier}s for its getter and setter
 *
 * @author Cadiboo
 */
public class FunctionalIntReferenceHolder extends IntReferenceHolder {

	private final IntSupplier getter;
	private final IntConsumer setter;

	public FunctionalIntReferenceHolder(final IntSupplier getter, final IntConsumer setter) {
		this.getter = getter;
		this.setter = setter;
	}

	@Override
	public int get() {
		return this.getter.getAsInt();
	}

	@Override
	public void set(final int newValue) {
		this.setter.accept(newValue);
	}

}
