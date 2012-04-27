/**
 * (c) 2011 Patrick Long All rights reserved.
 */
package finalgame.GUI;

import java.util.*;

import org.lwjgl.input.*;
import org.newdawn.slick.opengl.Texture;

import finalgame.Engine.Utilities.*;

@SuppressWarnings("unused")
public class GuiFrame extends GuiElement {

	private LinkedList<GuiElement> elements;
	private LinkedList<String> keys;
	private float[] background;
	private boolean drawbg;
	private String focus = "";
	private Random r;
	private boolean hasCallback = false;
	private ButtonListener cb;

	public GuiFrame(int x2, int y2, int width2, int height2) {
		super(x2, y2, width2, height2);
		elements = new LinkedList<GuiElement>();
		keys = new LinkedList<String>();
		r = new Random();
	}

	public void update() {
		boolean mouseEvent = Mouse.isButtonDown(0), focused = false;
		for (GuiElement i : elements) {
			i.update();
			if (mouseEvent && i.shouldHaveFocus()) {
				focus = i.getID();
				focused = true;
			}
		}
		if (mouseEvent && !focused) {
			clearFocus();
		}
		handleTextInput();
	}

	public void requestFocus(GuiElement f) {
		focus = f.getID();
	}

	public void clearFocus() {
		focus = "";
	}

	public void handleTextInput() {
		if (focus == null) {
			for (; Keyboard.next(););
		}
		for (; Keyboard.next(); delegateTextInput(Keyboard.getEventCharacter(), Keyboard.getEventKey()));
	}

	public void delegateTextInput(char c, int i) {
		if (focus.equals("")) {
			return;
		}
		GuiElement focusElement = elements.get(keys.indexOf(focus));
		focusElement.handleTextInput(c, i);
	}

	public void draw() {
		if (visible) {
			LinkedList<GuiElement> later = new LinkedList<GuiElement>();
			for (GuiElement e : elements) {
				if (e instanceof GuiButton || e instanceof GuiLabel || e instanceof GuiTextField) {
					later.add(e);
				} else {
					e.draw();
				}
			}
			for (GuiElement e : later) {
				if (e instanceof GuiButton || e instanceof GuiTextField) {
					e.drawBox();
				}
			}
			for (GuiElement e : later) {
				e.drawText();
			}
		}
	}

	public GuiElement getElement(int index) {
		return elements.get(index);
	}

	public GuiElement getElement(String id) {
		int index = keys.indexOf(id);
		if (index < 0) {
			return null;
		}
		return elements.get(index);
	}

	public String getID(GuiElement e) {
		int index = elements.indexOf(e);
		if (index < 0) {
			return null;
		}
		return keys.get(index);
	}

	public void setListener(ButtonListener cb) {
		this.cb = cb;
		hasCallback = true;
	}

	public boolean add(String id, GuiElement e) {
		if (keys.contains(id)) {
			return false;
		}
		elements.add(e);
		keys.add(id);
		return true;
	}

	public boolean addLabel(int x, int y, String t, Texture font) {
		int rand = r.nextInt(9999) + 10000;
		GuiLabel label = new GuiLabel(x, y, t, font, this, "" + rand);
		boolean succeeded = add("" + rand, label);
		return succeeded;
	}

	public boolean addButton(int x, int y, String t, Texture font) {
		if (!hasCallback) {
			return false;
		}
		int rand = r.nextInt(9999) + 10000;
		GuiButton button = new GuiButton(x, y, t, font, cb, this, "" + rand);
		boolean succeeded = add("" + rand, button);
		return succeeded;
	}

	public boolean addTextField(int x, int y, Texture font) {
		int rand = r.nextInt(9999) + 10000;
		GuiTextField textField = new GuiTextField(x, y, font, cb, this, "" + rand);
		boolean succeeded = add("" + rand, textField);
		return succeeded;
	}

	public boolean addLabel(String id, int x, int y, String t, Texture font) {
		GuiLabel label = new GuiLabel(x, y, t, font, this, id);
		boolean succeeded = add(id, label);
		return succeeded;
	}

	public boolean addButton(String id, int x, int y, String t, Texture font) {
		if (!hasCallback) {
			return false;
		}
		GuiButton button = new GuiButton(x, y, t, font, cb, this, id);
		boolean succeeded = add(id, button);
		return succeeded;
	}

	public boolean addTextField(String id, int x, int y, Texture font) {
		GuiTextField textField = new GuiTextField(x, y, font, cb, this, id);
		boolean succeeded = add(id, textField);
		return succeeded;
	}

	public boolean hasFocus(GuiElement e) {
		return focus.equals(e.getID());
	}
}
