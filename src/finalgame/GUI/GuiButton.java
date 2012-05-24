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

import finalgame.Engine.Utilities.ButtonListener;
import finalgame.Engine.Utilities.Utilities;
import finalgame.Graphics.*;

@SuppressWarnings("unused")
public class GuiButton extends GuiElement {
	private String text;
	private Texture f;
	private float[] defaultColor, overColor, clickColor;
	private byte state = 0; // 0 (default), 1 (mouseover), 2 (click)
	private ButtonListener cb;

	public GuiButton(int x, int y, String t, Texture font, ButtonListener cb,
			GuiElement superior) {
		super(x, y, Utilities.getStringWidth(t) + 10,
				GraphicsConstants.LETTER_SIZE + 10);
		text = t;
		f = font;
		defaultColor = new float[] { 0.9f, 0.9f, 0.9f };
		overColor = new float[] { 0.7f, 0.7f, 0.7f };
		clickColor = new float[] { 0.3f, 0.3f, 0.3f };
		this.cb = cb;
		this.superior = superior;
	}

	public GuiButton(int x, int y, String t, Texture font, ButtonListener cb,
			GuiElement superior, String id) {
		super(x, y, Utilities.getStringWidth(t) + 10,
				GraphicsConstants.LETTER_SIZE + 10, id);
		text = t;
		f = font;
		defaultColor = new float[] { 0.9f, 0.9f, 0.9f };
		overColor = new float[] { 0.7f, 0.7f, 0.7f };
		clickColor = new float[] { 0.3f, 0.3f, 0.3f };
		this.cb = cb;
		this.superior = superior;
	}

	public void draw() {
		if (visible) {
			drawBox();
			drawText();
		}
	}

	public void drawText() {
		if (visible) {
			Render2D.renderString(x + 5, y + 5, text, f);
		}
	}

	public void drawBox() {
		if (visible) {
			float[] renderColor;
			if (state == 0)
				renderColor = defaultColor;
			else if (state == 1)
				renderColor = overColor;
			else
				renderColor = clickColor;
			Render2D.drawRect(new Location(x, y), width, height, renderColor,
					false);
		}
	}

	public void setText(String s) {
		this.width = s.length() * GraphicsConstants.LETTER_SIZE;
		this.height = GraphicsConstants.LETTER_SIZE;
		this.text = s;
	}

	public String getText() {
		return this.text;
	}

	public void update() {
		int x = Mouse.getX(), y = GraphicsConstants.HEIGHT - Mouse.getY();
		if (this.x <= x && this.x + width >= x && this.y <= y
				&& this.y + height >= y) {
			if (Mouse.isButtonDown(0)) {
				state = 2;

			} else {
				if (state == 2)
					cb.doCallback(this);
				state = 1;
			}
		} else {
			state = 0;
		}
	}
}
