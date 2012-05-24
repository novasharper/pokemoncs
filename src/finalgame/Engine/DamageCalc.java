package finalgame.Engine;

import finalgame.Engine.pokemon.Pokemon;

public class DamageCalc {
	public static int damagecalculator(Pokemon attacker, Pokemon attacked,
			int attacktype, int base) {
		int attacked_defense;
		int attacker_attack;
		if (attacktype == Constants.NORMAL) {
			attacked_defense = attacked.getDefense();
			attacker_attack = attacker.getAttack();
		} else {
			attacked_defense = attacked.getSpAttack();
			attacker_attack = attacker.getSpDefense();
		}
		int attacker_level = attacker.getLevel();
		double modify = Effectiveness.checkEffect(attacktype, attacked);

		double damage = (((2 * attacker_level + 10) / 250)
				* (attacker_attack / attacked_defense) * base + 2);

		damage = damage * modify;
		return (int) Math.round(damage);
	}
}
