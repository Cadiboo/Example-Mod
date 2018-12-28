package io.github.cadiboo.examplemod.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Holds all enums and enum-related stuff for this mod
 *
 * @author Cadiboo
 */
public final class ModEnums {

	/**
	 * provides some default methods for formatting enum names
	 *
	 * @author Cadiboo
	 */
	public interface IEnumNameFormattable {

		/**
		 * Converts the name to lowercase as per {@link String#toLowerCase()}.
		 */
		default String getNameLowercase() {
			return this.name().toLowerCase();
		}

		/**
		 * Converts the name to uppercase as per {@link String#toUpperCase()}.
		 */
		default String getNameUppercase() {
			return this.getNameLowercase().toUpperCase();
		}

		/**
		 * Capitalizes the name of the material as per {@link StringUtils#capitalize(String)}.
		 */
		default String getNameFormatted() {
			return StringUtils.capitalize(this.getNameLowercase());
		}

		/* not exactly hacky, but this method is provided by enum */
		String name();

	}

}
