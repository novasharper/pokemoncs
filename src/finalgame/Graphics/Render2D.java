package finalgame.Graphics;

import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

@SuppressWarnings("unused")
public class Render2D {

	public static void toggle2dMode(int width, int height) {
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
		GL11.glLoadIdentity(); // Reset The Projection Matrix
		GL11.glOrtho(0, width, height, 0, 0, 100);
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	private static double[] getLetter(String c, Texture font) {
		c = c.toLowerCase();
		char letter = c.charAt(0);
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		String other = "0123456789,.;:'\"-!()";
		int hIndex = alpha.indexOf(letter) >= 0 ? alpha.indexOf(letter) : other
				.indexOf(letter);
		int vIndex = alpha.indexOf(letter) >= 0 ? 0 : 1;
		return new double[] { hIndex / 26.0 * font.getWidth(),
				vIndex / 2.0 * font.getHeight(),
				(hIndex + 1) / 26.0 * font.getWidth(),
				(vIndex + 1) / 2.0 * font.getHeight() };
	}

	/**
	 * Draw a string
	 * 
	 * @param x
	 * @param y
	 * @param message
	 * @param font
	 */
	public static void renderString(double x, double y, String message,
			Texture font) {
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		Color.white.bind();
		font.bind();
		for (int pos = 0; pos < message.length(); pos++) {
			String letter = String.valueOf(message.charAt(pos));
			if (letter == " ") {
				continue;
			}
			double[] spriteSheetPos = getLetter(letter, font);

			// draw quad
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(spriteSheetPos[0], spriteSheetPos[1]);
			GL11.glVertex2d(0, 0);
			GL11.glTexCoord2d(spriteSheetPos[2], spriteSheetPos[1]);
			GL11.glVertex2d(GraphicsConstants.LETTER_SIZE, 0);
			GL11.glTexCoord2d(spriteSheetPos[2], spriteSheetPos[3]);
			GL11.glVertex2d(GraphicsConstants.LETTER_SIZE,
					GraphicsConstants.LETTER_SIZE);
			GL11.glTexCoord2d(spriteSheetPos[0], spriteSheetPos[3]);
			GL11.glVertex2d(0, GraphicsConstants.LETTER_SIZE);
			GL11.glEnd();
			GL11.glTranslated(GraphicsConstants.LETTER_SIZE, 0, 0);
		}
		GL11.glPopMatrix();
	}

	public static void drawSquare(double x, double y, int side, float[] color) {
		drawSquare(x, y, 0, side, color);
	}

	public static void drawSquare(double x, double y, int side, float[] color,
			boolean centered) {
		drawRect(new Location(x, y, 0), side, side, color, false);
	}

	public static void drawSquare(double x, double y, double rot, int side,
			float[] color) {
		drawSquare(new Location(x, y, rot), side, color);
	}

	public static void drawSquare(Location l, int side, float[] color) {
		drawRect(l, side, side, color, true);
	}

	public static void drawRect(Location l, int width, int height,
			float[] color, boolean centered) {
		double cX = l.x(), cY = l.y(), rot = l.rot();
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glTranslated(cX, cY, 0);
		if (centered) {
			GL11.glTranslated(-width / 2, -height / 2, 0);
		}
		GL11.glRotatef((float) rot, 0, 0, 1);
		Color c = new Color(color[0], color[1], color[2]);
		if (color.length > 3) {
			c.a = color[3];
		}
		c.bind();

		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d((0), (0));
		GL11.glVertex2d((width), (0));
		GL11.glVertex2d((width), (height));
		GL11.glVertex2d((0), (height));
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static void drawTex(double x, double y, Texture tex) {
		double width = tex.getImageWidth(), height = tex.getImageHeight(), rot = 0;
		drawTex(x, y, width, height, rot, width / 2, height / 2, tex);
	}

	public static void drawTex(double x, double y, double rot, double cX,
			double cY, Texture tex) {
		double width = tex.getImageWidth(), height = tex.getImageHeight();
		drawTex(x, y, width, height, rot, cX, cY, tex);
	}

	public static void drawTex(double x, double y, double width, double height,
			Texture tex) {
		double rot = 0;
		drawTex(x, y, width, height, rot, width / 2, height / 2, tex);
	}

	public static void drawTex(double x, double y, double width, double height,
			double rot, double cX, double cY, Texture tex) {
		double tw = tex.getWidth(), th = tex.getHeight();
		drawTex(new double[] {x, y},
				new double[] {width, height},
				0,
				new double[] {width / 2, height / 2},
				new double[] {tw / 2, th / 2},
				new double[] {tw, th},
				tex);
	}

	public static void drawTex(double[] loc, double[] dim, double rot,
			double[] cloc, double[] tloc, double[] tdim, Texture tex) {
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
		GL11.glTranslated(loc[0], loc[1], 0);
		GL11.glRotatef((float) rot, 0, 0, 1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		Color.white.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		tex.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,
				GL13.GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,
				GL13.GL_CLAMP_TO_BORDER);

		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(tloc[0] - tdim[0] / 2, tloc[1] - tdim[1] / 2);
		GL11.glVertex2d((-dim[0] / 2 - (cloc[0] - dim[0] / 2)),
				(-dim[1] / 2 - (cloc[1] - dim[1] / 2)));
		GL11.glTexCoord2d(tloc[0] + tdim[0] / 2, tloc[1] - tdim[1] / 2);
		GL11.glVertex2d((dim[0] / 2 - (cloc[0] - dim[0] / 2)),
				(-dim[1] / 2 - (cloc[1] - dim[1] / 2)));
		GL11.glTexCoord2d(tloc[0] + tdim[0] / 2, tloc[1] + tdim[1] / 2);
		GL11.glVertex2d((dim[0] / 2 - (cloc[0] - dim[0] / 2)),
				(dim[1] / 2 - (cloc[1] - dim[1] / 2)));
		GL11.glTexCoord2d(tloc[0] - tdim[0] / 2, tloc[1] + tdim[1] / 2);
		GL11.glVertex2d((-dim[0] / 2 - (cloc[0] - dim[0] / 2)),
				(dim[1] / 2 - (cloc[1] - dim[1] / 2)));
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public static void drawBg(Texture tex) {
		// set the color of the quad (R,G,B,A)
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		Color.white.bind();
		// GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		tex.bind();

		// draw quad
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(0, 0);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(GraphicsConstants.WIDTH / tex.getWidth(), 0);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(GraphicsConstants.WIDTH / tex.getWidth(),
				GraphicsConstants.HEIGHT / tex.getHeight());
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(0, GraphicsConstants.HEIGHT / tex.getHeight());
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}
