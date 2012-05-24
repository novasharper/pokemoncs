/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame.GUI;

import finalgame.Game;
import finalgame.Graphics.GraphicsConstants;
import finalgame.Graphics.Location;
import finalgame.Graphics.Render2D;
import finalgame.Main;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;

/**
 * 
 * @author Pat
 */
public class Notification extends GuiElement {
	private String[] msg;
	private String disp;
	private int index;
	private int height;
	private UnicodeFont mf;
	private String speaker;
	private static final int LINE_SPACING = 10;
	private static final int X_PADDING = 4;
	private static final int Y_PADDING = 9;

	public Notification(int x, int y, String msg, String speaker,
			UnicodeFont font) {
		this(x, y, msg, font);
		setSpeaker(speaker);
	}

	public Notification(int x, int y, String msg, UnicodeFont font) {
		super(x, y, 0, 0);
		mf = font;
		this.msg = msg.split("\n");
		height = Game.messageHeight + Game.titleHeight + LINE_SPACING;
		disp = this.msg[0];
		speaker = "Anonymous";
	}

	/**
	 * Draw notification
	 */
	@Override
	public void draw() {
		// Draw border
		Render2D.drawRect(new Location(0, 0), GraphicsConstants.WIDTH, height
				+ y * 2, new float[] { 0, 0, 0, 0.9f }, false);
		// Draw background
		Render2D.drawRect(new Location(x - X_PADDING, y - Y_PADDING),
				GraphicsConstants.WIDTH - 2 * (x - X_PADDING), Y_PADDING * 2
						+ height, new float[] { 1, 1, 1, 0.9f }, false);
		// Draw speaker
		Game.titleFont.drawString(x, y, speaker + ":");
		// Draw message
		Game.gameFont.drawString(x, y + Game.titleHeight + LINE_SPACING, disp);
	}

	@Override
	public void update() {
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public boolean advance() {
		if (index >= msg.length - 1)
			return true;
		index++;
		disp = msg[index];
		return false;
	}

	public boolean reverse() {
		if (index <= 0)
			return true;
		index--;
		disp = msg[index];
		return false;
	}
}
