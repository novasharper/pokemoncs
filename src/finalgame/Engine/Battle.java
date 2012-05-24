package finalgame.Engine;

import finalgame.Engine.pokemon.Pokemon;
import finalgame.Engine.trainer.Trainer;

public class Battle {
	private Trainer player, opponent;
	private Pokemon playerPokemon, opposingPokemon;
	
	public Battle(Trainer trainerP, Trainer trainerO){
		player = trainerP;
		opponent = opponent;
		playerPokemon = player.getCurrentPokemon();
		opposingPokemon = opponent.getCurrentPokemon();
	}
	public Battle(Trainer trainerP, Pokemon oppPokemon){
		player = trainerP;
		playerPokemon = trainerP.getCurrentPokemon();
		opposingPokemon = oppPokemon;
	}
}
