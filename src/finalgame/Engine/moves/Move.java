package finalgame.Engine.moves;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.reflections.Reflections;

import finalgame.Game;
import finalgame.ResourceLoader;
import finalgame.Engine.DamageCalc;
import finalgame.Engine.pokemon.Pokemon;
import finalgame.GUI.battle.BattleScreen;
import finalgame.Graphics.RenderEngine;

public class Move {
	private static Map<String, Class<? extends Move>> moves;
	private static boolean init = false;

	static {
		System.out.println("Moves init");
		moves = new LinkedHashMap<String, Class<? extends Move>>();
		loadMoves();
		init = true;
	}
	
	private static void loadMoves() {
		if(init) return;
		Reflections reflections = new Reflections("finalgame",
				Game.class.getClassLoader());
		Set<Class<? extends Move>> classes = reflections
				.getSubTypesOf(Move.class);
		for (Class clazz : classes) {
			String clazzName = clazz.getSimpleName().toLowerCase().substring(4);
			moves.put(clazzName, clazz);
		}
	}
	
	public static Class<? extends Move> getMove(String moveName) {
		return moves.get(moveName);
	}
	
	public static Move getNewMove(String moveName) {
		try {
			Class<? extends Move> move = moves.get(moveName);
			return (Move) move.newInstance();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private int base;
	private int attacktype;

	public Move() {
		base = 0;
		attacktype = 0;
	}

	public Move(int b, int a) {
		base = b;
		attacktype = a;
	}

	public void use(Pokemon attacker, Pokemon attacked) {
		int damage_done = DamageCalc.damagecalculator(attacker, attacked,
				attacktype, base);
		attacked.removeHP(damage_done);
	}

	public int getBase() {
		return base;
	}

	public int getAttacktype() {
		return attacktype;
	}
}
