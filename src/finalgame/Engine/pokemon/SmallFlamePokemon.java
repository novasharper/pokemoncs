package finalgame.Engine.pokemon;

import finalgame.Engine.Constants;

public class SmallFlamePokemon extends Pokemon{
	public static final int pokemonNumber = 004;
	public SmallFlamePokemon(int level){
		super(Constants.FIRETYPE, new double[]{10.0, 10.0, 10.0, 10.0, 10.0, 10.0}, new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, level);
		addMove("moveBite", 0);
		if(level >= 4){addMove("moveEmber", 1);}
	}
	public void render(){
		
	}
}
