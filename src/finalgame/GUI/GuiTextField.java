/**
 * (c) 2011 Patrick Long
 * All rights reserved.
 */

package finalgame.GUI;

import finalgame.Engine.Constants;
import finalgame.Engine.Utilities.ButtonListener;
import finalgame.Engine.Utilities.Utilities;
import finalgame.Graphics.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

public class GuiTextField extends GuiElement {
	private String text;
	private Texture f;

	public GuiTextField(int x, int y, Texture font, ButtonListener cb, GuiElement superior) {
		super(x, y, GraphicsConstants.LETTER_WIDTH * (Constants.MAX_TEXT_LEN + 1) + 10,
				GraphicsConstants.LETTER_WIDTH  + 10);
		text = "";
		f = font;
		this.superior = superior;
	}
	
	public GuiTextField(int x, int y, Texture font, ButtonListener cb, GuiElement superior, String id) {
		super(x, y, GraphicsConstants.LETTER_WIDTH * (Constants.MAX_TEXT_LEN + 1) + 10,
				GraphicsConstants.LETTER_WIDTH  + 10, id);
		text = "";
		f = font;
		this.superior = superior;
	}
	
	public void draw() {
		if(visible) {
			drawBox();
			drawText();
		}
	}
	
	public void drawBox() {
		if(visible) {
			Render2D.drawRect(new Location(x - 2, y - 2), width + 4, height + 4,
					new float[] { 0f, 0f, 0f }, false);
			Render2D.drawRect(new Location(x, y), width, height, new float[] { 0.9f, 0.9f, 0.9f }, false);
			if(((GuiFrame) superior).hasFocus(this)) {
				Render2D.drawRect(new Location(Utilities.getStringWidth(text) + 10 + x,
						GraphicsConstants.LETTER_WIDTH + y + 3),
						GraphicsConstants.LETTER_WIDTH - 3, 2, new float[] { 0f, 0f, 0f }, false);
			}
		}
	}
	
	public void drawText() {
		if(visible) {
			Render2D.renderString(x + 5, y + 5, text, f); 
		}
	}
	
	public void setText(String s) {
		this.width = s.length() * GraphicsConstants.LETTER_WIDTH;
		this.height = GraphicsConstants.LETTER_WIDTH;
		this.text = s;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void update() {
		
	}
	
	public boolean shouldHaveFocus() {
		int x = Mouse.getX(), y = 699 - Mouse.getY();
		if(Mouse.isButtonDown(0)) {
			if(this.x <= x && this.x + width >= x && this.y <= y && this.y + height >= y) {
				return true;
			}
		}
		return false;
	}
	
	public void handleTextInput(char c, int i) {
		String append = String.valueOf(c).toLowerCase();
		if(i == 14 && text.length() > 0 && Keyboard.isKeyDown(14)) { //backspace
			text = text.substring(0, text.length() - 1);
		} else if(text.length() < Constants.MAX_TEXT_LEN && c != 0) { //add character
			if("abcdefghijklmnopqrstuvwxyz0123456789,.;:'\"-!() ".contains(append))
				text += c;
		}
	}

}
