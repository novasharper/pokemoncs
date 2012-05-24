package finalgame.Graphics;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

public class RenderEngine {
	private static Map<String, Renderable> renderObj;
	private static Map<String, Texture> sprites;
	private static boolean init = false;
	
	static {
		init();
	}
	
	public static void init() {
		if(init) return;
		System.out.println("Render Engine init");
		renderObj = new HashMap<String, Renderable>();
		sprites = new HashMap<String, Texture>();
		init = true;
	}

	public static void register(Renderable r) {
		renderObj.put(r.getRenderID(), r);
	}

	public static void forget(String rid) {
		renderObj.remove(rid);
	}

	public static void render() {
		if (!Display.isCreated())
			throw new RuntimeException("Display Not Created");
		// ???
		// Issues: How to ensure that when sprites overlap, only one in front is
		// drawn???
	}

	public static Texture getSprite(String name) {
		return sprites.get(name);
	}

	public static void registerSprite(String name, Texture tex) {
		sprites.put(name, tex);
	}

	public static void registerSpriteSheet(String baseName, String[] names,
			Texture tex, int tw, int th) {
		int w = tex.getImageWidth() / tw, h = tex.getImageHeight() / th;
		SpriteSheet ss = new SpriteSheet(new Image(tex), tw, th);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				registerSprite(baseName + names[y * w + x], ss.getSprite(x, y)
						.getTexture());
			}
		}
	}

	public static void forgetSprite(String name) {
		sprites.remove(name);
	}
}
