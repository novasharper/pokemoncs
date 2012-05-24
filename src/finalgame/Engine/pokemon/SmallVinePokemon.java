package finalgame.Engine.pokemon;

import finalgame.Engine.Constants;

public class SmallVinePokemon extends Pokemon{
	public static final int pokemonNumber = 001;
	public SmallVinePokemon(int level){
		super(Constants.GRASSTYPE, new double[]{10.0, 10.0, 10.0, 10.0, 10.0, 10.0}, new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, level);
		addMove("moveScratch", 0);
		if(level >= 4){addMove("moveLeafblade", 1);}
	}
	public void render(){
		
	}
}
