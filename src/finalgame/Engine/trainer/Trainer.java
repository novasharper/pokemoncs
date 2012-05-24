package finalgame.Engine.trainer;

import finalgame.Engine.pokemon.Pokemon;

public class Trainer {
	private Pokemon[] pokemon;
	private int pokemonNumber;
	private int activePokemon;
	public Trainer(){
		pokemonNumber = 0;
		pokemon = new Pokemon[6];
		activePokemon = 0;
	}
	public Trainer(Pokemon[] sentPokemon) {		
		pokemon = sentPokemon;
		pokemonNumber = sentPokemon.length;
		activePokemon = 0;
	}

	public void switchPokemon(int newactive) {
		activePokemon = newactive;
	}

	public void addPokemon(Pokemon newpokemon) {
		pokemon[pokemonNumber] = newpokemon;
		pokemonNumber++;
	}
	public Pokemon getCurrentPokemon() {
		return pokemon[activePokemon];
	}
	
	public Pokemon[] getPokemonInventory(){
		return pokemon;
	}
	
	public int getNumberOfPokemon(){
		return pokemonNumber;
	}
}
