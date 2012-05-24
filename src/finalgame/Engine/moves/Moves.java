package finalgame.Engine.moves;

import finalgame.Engine.DamageCalc;
import finalgame.Engine.pokemon.Pokemon;

public class Moves {
	private int base;
	private int attacktype;
	public Moves(){
		base = 0;
		attacktype = 0;
	}
	public Moves(int b, int a){
		base = b;
		attacktype = a;	
	}
	public Pokemon use(Pokemon attacker, Pokemon attacked){
		int damage_done = DamageCalc.damagecalculator(attacker, attacked, attacktype, base);
		attacked.removeHP(damage_done);
		return attacked;
	}
	
	public int getBase(){
		return base;
	}
	
	public int getAttacktype(){
		return attacktype;
	}
}
