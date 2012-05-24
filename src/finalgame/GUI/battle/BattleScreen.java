package finalgame.GUI.battle;

import org.newdawn.slick.opengl.Texture;

import finalgame.Engine.pokemon.Pokemon;
import finalgame.Graphics.Render2D;
import finalgame.Graphics.RenderEngine;
import finalgame.Graphics.RenderObj;

public class BattleScreen implements RenderObj {
	private int hometurf;
	private String name;
	private Pokemon a, b;
	
	public BattleScreen(int type) {
		hometurf = type;
		this.name = getClass().getSimpleName().toLowerCase();
	}
	
	public void render() {
		Texture bg = RenderEngine.getSprite(name + ".bg");
		Render2D.drawTex(20, 20, bg);
		/*Render2D.drawTex(new double[] { dLoc.x(), dLoc.y() }, new double[] {
				windowWidth, windowHeight }, 0, new double[] { windowWidth / 2,
				windowHeight / 2 }, new double[] { vLoc.x(), vLoc.y() },
				new double[] { winw, winh }, bg);*/
	}
}
