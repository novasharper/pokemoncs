package finalgame.GUI.stage;

import java.io.File;

import org.newdawn.slick.opengl.Texture;

import finalgame.Game;
import finalgame.Graphics.GraphicsConstants;
import finalgame.Graphics.Location;
import finalgame.Graphics.Render2D;
import finalgame.Graphics.RenderObj;

public class Stage implements RenderObj {
	public static final Location defaultDrawLoc = new Location(250, 220);
	public static final Location defaultWindow = new Location(400, 400);

	private double windowWidth, windowHeight;
	private Texture bg;
	private Location vLoc, dLoc, pLoc, vpLoc, spawn;
	private double vx, vy;
	private double winw, winh; // Width of the window in terms of the texture
	private double scale;
	private double insetx, vinsetx, insety, vinsety;
	int min_border = 20;

	public Stage(Texture tex) {
		this((int) defaultWindow.x(), (int) defaultWindow.y(),
				(int) defaultDrawLoc.x(), (int) defaultDrawLoc.y(), tex);
	}

	public Stage(int w, int h, Texture tex) {
		this(w, h, (int) defaultDrawLoc.x(), (int) defaultDrawLoc.y(), tex);
	}

	public Stage(int w, int h, int x, int y, Texture tex) {
		windowWidth = w;
		windowHeight = h;
		dLoc = new Location(x, y);
		vLoc = new Location(0, 0);
		vpLoc = new Location(0, 0);
		pLoc = new Location(0, 0);
		bg = tex;
		scale = GraphicsConstants.SCALE;
		if (windowWidth > tex.getImageWidth() * scale)
			windowWidth = bg.getImageWidth();
		if (windowHeight > tex.getImageHeight() * scale)
			windowHeight = bg.getImageHeight();
		setScale(GraphicsConstants.SCALE);
		setLoc(0, 0);
	}

	public void setScale(double scale) {
		if (scale <= 0)
			return;
		this.scale = scale;
		winw = windowWidth * bg.getWidth() / (bg.getImageWidth() * scale);
		winh = windowHeight * bg.getHeight() / (bg.getImageHeight() * scale);
		vinsetx = winw / 2;
		vinsety = winh / 2;
		insety = windowHeight * bg.getHeight() / (2 * scale);
		insetx = windowWidth * bg.getWidth() / (2 * scale);
	}

	public void setScaleAndResize(double scale) {
		if (scale <= 0)
			return;
		this.scale = scale;
		windowWidth *= scale;
		windowHeight *= scale;
		winw = windowWidth * bg.getWidth() / (bg.getImageWidth() * scale);
		winh = windowHeight * bg.getHeight() / (bg.getImageHeight() * scale);
		vinsetx = winw / 2;
		vinsety = winh / 2;
		insety = windowHeight * bg.getHeight() / (2 * scale);
		insetx = windowWidth * bg.getWidth() / (2 * scale);
	}

	public void resize(int w, int h) {
		windowWidth = w;
		windowHeight = h;
		setScale(scale);
	}

	public void setLoc(double nx, double ny) {
		if (nx < min_border * bg.getWidth() * scale)
			nx = min_border * bg.getWidth() * scale;
		else if (nx > (bg.getImageWidth() - min_border) * bg.getWidth() * scale)
			nx = (bg.getImageWidth() - min_border) * bg.getWidth() * scale;

		if (ny < min_border * bg.getHeight() * scale)
			ny = min_border * bg.getHeight() * scale;
		else if (ny > (bg.getImageHeight() - min_border) * bg.getHeight()
				* scale)
			ny = (bg.getImageHeight() - min_border) * bg.getHeight() * scale;
		pLoc.y(ny);
		pLoc.x(nx);

		if (nx < insetx * scale)
			nx = insetx * scale;
		else if (nx > (bg.getImageWidth() * bg.getWidth() - insetx) * scale)
			nx = (bg.getImageWidth() * bg.getWidth() - insetx) * scale;
		if (ny < insety * scale)
			ny = insety * scale;
		else if (ny > (bg.getImageHeight() * bg.getHeight() - insety) * scale)
			ny = (bg.getImageHeight() * bg.getHeight() - insety) * scale;
		vpLoc.x(nx);
		vpLoc.y(ny);
		vLoc.x(nx / (bg.getImageWidth() * scale));
		vLoc.y(ny / (bg.getImageHeight() * scale));
	}

	public void translate(double dx, double dy) {
		setLoc(pLoc.x() + dx * bg.getWidth(), pLoc.y() + dy * bg.getHeight());
	}

	public Location getPRenderLoc() {
		Location delta = new Location(pLoc.x() - vpLoc.x(), pLoc.y()
				- vpLoc.y());
		delta.x(delta.x() / (bg.getWidth()) + dLoc.x());
		delta.y(delta.y() / (bg.getHeight()) + dLoc.y());
		return delta;
	}
	
	public Location test() {
		Location delta = new Location(pLoc.x() - vpLoc.x(), pLoc.y()
				- vpLoc.y());
		return delta;
	}

	public void render() {
		Render2D.drawTex(new double[] { dLoc.x(), dLoc.y() }, new double[] {
				windowWidth, windowHeight }, 0, new double[] { windowWidth / 2,
				windowHeight / 2 }, new double[] { vLoc.x(), vLoc.y() },
				new double[] { winw, winh }, bg);
		Location iLoc = getPRenderLoc();
		Render2D.drawTex(iLoc.x(), iLoc.y(), Game.icon);
		Location tLoc = test();
	}

	public void reset() {
		setLoc(spawn.x(), spawn.y());
	}
}
