/**
 * (c) 2011 Patrick Long
 * All rights reserved.
 */

package finalgame.GUI;

public abstract class GuiElement {
	protected int x, y, width, height;
	protected boolean visible;
	protected GuiElement superior;
	protected String id;

	public GuiElement(int x2, int y2, int width2, int height2) {
		this.x = x2;
		this.y = y2;
		this.width = width2;
		this.height = height2;
		this.visible = true;
		this.id = "0EF";
	}

	public GuiElement(int x2, int y2, int width2, int height2, String id) {
		this.x = x2;
		this.y = y2;
		this.width = width2;
		this.height = height2;
		this.visible = true;
		this.id = id;
	}

	public int[] getLocation() {
		return new int[] { x, y };
	}

	public int[] getCenter() {
		int centerX = (int) (x + (width) / 2.0);
		int centerY = (int) (y + (height) / 2.0);
		return new int[] { centerX, centerY };
	}

	public void setVisible(boolean visibility) {
		visible = visibility;
	}

	public abstract void draw();

	public abstract void update();

	public void drawText() {
	}

	public void drawBox() {
	}

	public void handleTextInput(char c, int i) {
	}

	public void requestFocus(GuiElement f) {
	}

	public boolean shouldHaveFocus() {
		return false;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getID() {
		return this.id;
	}
}
