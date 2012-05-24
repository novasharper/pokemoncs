/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame.Engine;

public enum EnumOptions {
	MUSIC("options.music", true, false), SOUND("options.sound", true, false);

	private final boolean isFloat;
	private final boolean isBoolean;
	private final String propertyName;

	public static EnumOptions getEnumOptions(int index) {
		EnumOptions vals[] = values();
		int len = vals.length;

		for (int i = 0; i < len; i++) {
			EnumOptions enumoptions = vals[i];

			if (enumoptions.returnOrdinal() == index) {
				return enumoptions;
			}
		}

		return null;
	}

	private EnumOptions(String name, boolean isFloat, boolean isBoolean) {
		propertyName = name;
		this.isFloat = isFloat;
		this.isBoolean = isBoolean;
	}

	/**
	 * Is the option a float
	 * 
	 * @return
	 */
	public boolean getIsFloat() {
		return isFloat;
	}

	/**
	 * Is the option a boolean
	 * 
	 * @return
	 */
	public boolean getIsBoolean() {
		return isBoolean;
	}

	public int returnOrdinal() {
		return ordinal();
	}

	public String getPropertyName() {
		return propertyName;
	}
}
