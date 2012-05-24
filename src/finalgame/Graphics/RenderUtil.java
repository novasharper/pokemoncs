package finalgame.Graphics;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

public class RenderUtil {
	public static void toggle2dMode(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0, (float) width, (float) height, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glEnable(GL11.GL_BLEND);
	}

	public ArrayList<Texture> getSprites(Texture tex, int tw, int th) {
		ArrayList<Texture> ss = new ArrayList<Texture>();
		SpriteSheet temp = new SpriteSheet(new Image(tex), tw, th);
		for (int x = 0; x < tw; x++)
			for (int y = 0; y < th; y++)
				ss.add(temp.getSprite(x, y).getTexture());
		return ss;
	}
}
