package finalgame;

import finalgame.Engine.Utilities.ButtonListener;
import finalgame.GUI.GuiButton;
import finalgame.GUI.GuiFrame;
import finalgame.Graphics.GraphicsConstants;
import finalgame.Graphics.Render2D;
import finalgame.Graphics.RenderUtil;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Main implements Runnable {

	private boolean game, quit;
	private Thread mThread;
	private GuiFrame menu;
	private ButtonListener listener, quitListener;
	private Texture gameFont;
	private Texture backgroundTexture;

	public Main() {
		mThread = null;
		game = true;
	}

	private void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(GraphicsConstants.WIDTH,
					GraphicsConstants.HEIGHT));
			Display.setTitle("PokemonCS");
			try {
				Display.create();
			} catch (LWJGLException e) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException ie) {
				}
				Display.create();
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private void initGL() {
		glEnable(GL_TEXTURE_2D);	// Enable Texture Mapping
		glShadeModel(GL_SMOOTH);	// Enable Smooth Shading
		glClearColor(0, 0, 0, 1);	// Black Background

		// Enable alpha
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glViewport(0, 0, GraphicsConstants.WIDTH, GraphicsConstants.HEIGHT);
		glMatrixMode(GL_MODELVIEW);

		glClearDepth(1.0);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		GLU.gluOrtho2D(0, GraphicsConstants.WIDTH, GraphicsConstants.HEIGHT, 0);
		glMatrixMode(GL_MODELVIEW);

		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}

	private void loadResources() {
		try {
			gameFont = TextureLoader.getTexture(
					"PNG",
					ResourceLoader.getResourceAsStream(
					"resources/image/font/alpha.png", true));
			backgroundTexture = TextureLoader.getTexture(
					"PNG",
					ResourceLoader.getResourceAsStream(
					"resources/image/background/background.png", true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initGeneral() {
		// init callbacks
		listener = new ButtonListener() {

			public void doCallback() {
			}

			public void doCallback(Object caller) {
				buttonClicked((GuiButton) caller);
			}

			public void doCallback(Object caller, int eventCode) {
			}
		};
		menu = new GuiFrame(0, 0, GraphicsConstants.WIDTH, GraphicsConstants.HEIGHT);
		menu.setListener(listener);
		menu.addButton("start", 100, 100, "Start", gameFont);
		menu.addButton("exit", 100, 140, "Exit", gameFont);
		menu.addLabel(10, 10, "PokemonCS", gameFont);
	}

	private void initSoundSystem() {
	}

	/**
	 * Init
	 */
	public void init() {
		initDisplay();
		initGL();
		loadResources();
		initGeneral();
		initSoundSystem();
	}

	/**
	 * Button clicked callback
	 */
	public void buttonClicked(GuiButton btn) {
		// Get the button ID
		String name = menu.getID(btn);
		// No button
		if (name == null) {
			return;
		}
		// The start button
		if (name.equals("start")) {
			game = true;
			menu.clearFocus();
		} else { // The quit button
			quit = true;
		}
	}

	/**
	 * Update the game
	 */
	public void update() {
	}

	/**
	 * Render the game
	 */
	public void render() {
	}

	/**
	 * Main thread
	 */
	public void run() {
		init();
		while (!(Display.isCloseRequested() || quit)) {
			glClear(GL_COLOR_BUFFER_BIT);
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				game = false;
				RenderUtil.toggle2dMode(GraphicsConstants.WIDTH, GraphicsConstants.HEIGHT);
				for (; Keyboard.next();) ;
			}
			Render2D.drawBg(backgroundTexture);
			if (game) { // Update Game
				update();
			} else { // Update Menu
				menu.update();
			}
			render();
			if (!game) { // Draw menu over game
				menu.draw();
			}
			Display.update();
			Display.sync(GraphicsConstants.FRAMERATE);
		}
		Display.destroy();
		if (quitListener != null) {
			quitListener.doCallback();
		}
		System.exit(0);
	}

	/**
	 * ***********************************************************************
	 */
	/*
	 * THREADING STUFF
	 */
	/**
	 * ***********************************************************************
	 */
	/**
	 * Quit
	 */
	public void quit() {
		quit = true;
	}

	/**
	 * Set callback for cleaning up any additional stuff
	 */
	public void setQuitListener(ButtonListener listener) {
		quitListener = listener;
	}

	/**
	 * Start the thread
	 */
	public void start() {
		if (mThread != null) {
			return;
		} else {
			mThread = new Thread(this, "main-thread");
			mThread.start();
		}
	}

	/**
	 * Stop the thread
	 */
	public void stop() {
		this.quit();
	}

	/**
	 * ***********************************************************************
	 */
	/*
	 * Main Entry point
	 */
	/**
	 * ***********************************************************************
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
}
