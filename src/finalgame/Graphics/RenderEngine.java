package finalgame.Graphics;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.Display;

public class RenderEngine {

	private Map<String, Renderable> renderObj;

	public RenderEngine() {
		renderObj = new HashMap<String, Renderable>();
	}

	public void register(Renderable r) {
		renderObj.put(r.getRenderID(), r);
	}

	public void forget(String rid) {
		renderObj.remove(rid);
	}

	public void render() {
		if(!Display.isCreated()) throw new RuntimeException("Display Not Created");
		
	}
}
