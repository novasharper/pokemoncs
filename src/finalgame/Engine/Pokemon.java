package finalgame.Engine;

import finalgame.Graphics.Renderable;

public abstract class Pokemon extends Renderable {
	protected String Nickname;
	protected int type;
	protected int experience, level;
	protected Boolean hasLeveled;
	// 0 = hitpoints, 1 = attack, 2 = defense, 3 = spattack, 4 = spdefense, 5 =
	// speed
	protected double[] growths;
	protected double[] stats;
	protected int currentHP;

	public Pokemon(int t, double[] grow, double[] stat, int l) {
		type = t;
		level = l;
		experience = 0;
		growths = grow;
		stats = new double[6];
		for (int i = 0; i < grow.length; i++) {
			stats[i] = stat[i] + level * grow[i];
		}
		currentHP = (int) stats[0];
	}

	public int getHP() {
		return currentHP;
	}

	// Removes passed in HP from Pokemon object, returns true if Pokemon faints,
	// false otherwise
	public Boolean removeHP(int amountRemoved) {
		currentHP -= amountRemoved;
		if (currentHP <= 0) {
			currentHP = 0;
			return true;
		}
		return false;
	}

	// Recursive method that checks if Pokemon should level up and levels up
	// until experience < experience needed to level again
	public Boolean LevelUp() {
		if (experience >= expToLevel()) {
			experience -= expToLevel();
			level++;
			addGrowths();
			LevelUp();
			return true;
		}
		return false;
	}

	private void addGrowths() {
		for (int i = 0; i < stats.length; i++) {
			stats[i] += growths[i];
		}
	}

	private int expToLevel() {
		return (int) (level * 3);
	}

	public void addExp(int gain) {
		experience += gain;
	}

	public int expGiven() {
		return (expToLevel() / 2) + 1;
	}

	public void setNick(String n) {
		Nickname = n;
	}

	public int getAttack() {
		return (int) stats[1];
	}

	public int getDefense() {
		return (int) stats[2];
	}

	public int getSpAttack() {
		return (int) stats[3];
	}

	public int getSpDefense() {
		return (int) stats[4];
	}

	public int getSpeed() {
		return (int) stats[5];
	}

	public int getType() {
		return type;
	}

	public int getLevel() {
		return level;
	}
}
