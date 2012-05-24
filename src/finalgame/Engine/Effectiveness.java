package finalgame.Engine;

import finalgame.Engine.pokemon.Pokemon;

public class Effectiveness {

	// find the damage modifier for the different types of pokemon
	// if super effective returns 2
	// if not effective returns .5
	// if no modifier returns 1
	public static double checkEffect(int attacktype, Pokemon attacked) {
		int attackedtype = attacked.getType();

		if (attacktype == Constants.FIRETYPE
				&& attackedtype == Constants.GRASSTYPE) {
			return 2;
		} else if (attacktype == Constants.FIRETYPE
				&& attackedtype == Constants.WATERTYPE) {
			return 0.5;
		} else if (attacktype == Constants.WATERTYPE
				&& attackedtype == Constants.FIRETYPE) {
			return 2;
		} else if (attacktype == Constants.WATERTYPE
				&& attackedtype == Constants.ELECTYPE) {
			return .5;
		} else if (attacktype == Constants.GRASSTYPE
				&& attackedtype == Constants.ROCKTYPE) {
			return 2;
		} else if (attacktype == Constants.GRASSTYPE
				&& attackedtype == Constants.FIRETYPE) {
			return .5;
		} else if (attacktype == Constants.ELECTYPE
				&& attackedtype == Constants.WATERTYPE) {
			return 2;
		} else if (attacktype == Constants.ELECTYPE
				&& attackedtype == Constants.ROCKTYPE) {
			return .5;
		} else if (attacktype == Constants.ROCKTYPE
				&& attackedtype == Constants.ELECTYPE) {
			return 2;
		} else if (attacktype == Constants.ROCKTYPE
				&& attackedtype == Constants.GRASSTYPE) {
			return .5;
		} else {
			return 1;
		}
	}
}
