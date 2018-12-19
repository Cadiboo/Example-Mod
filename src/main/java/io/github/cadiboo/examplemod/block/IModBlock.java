package io.github.cadiboo.examplemod.block;

/**
 * Exists to allow us to easily identify our blocks with instanceof checks
 *
 * @author Cadiboo
 */
public interface IModBlock {

	default boolean hasItemBlock() {
		return true;
	}

}
