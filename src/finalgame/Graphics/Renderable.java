package finalgame.Graphics;

import java.util.UUID;

public abstract class Renderable implements RenderObj {
	private Location myLoc;
	protected String renderID;
	protected String name;

	public Renderable() {
		renderID = UUID.randomUUID().toString();
	}

	public Location getLocation() {
		return myLoc;
	}

	public abstract void render();

	public String getRenderID() {
		return renderID;
	}

	public String getFront() {
		return name + ".front";
	}

	public String getBack() {
		return name + ".back";
	}

	public String getOther(String c) {
		return name + "." + c;
	}
}
