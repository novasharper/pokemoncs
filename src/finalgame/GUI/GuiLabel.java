/**
 * (c) 2011 Patrick Long
 * All rights reserved.
 */

package finalgame.GUI;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.*;

import finalgame.Engine.Utilities.Utilities;
import finalgame.Graphics.*;

@SuppressWarnings("unused")
public class GuiLabel extends GuiElement {
	private String text;
	private Texture f;

	public GuiLabel(int x, int y, String t, Texture font, GuiElement superior) {
		super(x, y, Utilities.getStringWidth(t), GraphicsConstants.LETTER_SIZE);
		this.text = t;
		this.f = font;
		this.superior = superior;
	}

	public GuiLabel(int x, int y, String t, Texture font, GuiElement superior,
			String id) {
		super(x, y, Utilities.getStringWidth(t), GraphicsConstants.LETTER_SIZE,
				id);
		this.text = t;
		this.f = font;
		this.superior = superior;
	}

	public GuiLabel(int x, int y, String t, Texture font) {
		super(x, y, Utilities.getStringWidth(t), GraphicsConstants.LETTER_SIZE);
		this.text = t;
		this.f = font;
		this.superior = null;
	}

	public GuiLabel(int x, int y, String t, Texture font, String id) {
		super(x, y, Utilities.getStringWidth(t), GraphicsConstants.LETTER_SIZE,
				id);
		this.text = t;
		this.f = font;
		this.superior = null;
	}

	public void setText(String s) {
		this.width = s.length() * GraphicsConstants.LETTER_SIZE;
		this.height = GraphicsConstants.LETTER_SIZE;
		this.text = s;
	}

	public String getText() {
		return this.text;
	}

	public void draw() {
		if (visible) {
			Render2D.renderString(x, y, text, f);
		}
	}

	public void drawText() {
		draw();
	}

	public void update() {
	}
}
