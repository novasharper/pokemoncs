package finalgame;

import finalgame.Engine.OptionsManager;
import finalgame.Engine.Utilities.ButtonListener;
import finalgame.GUI.GuiButton;
import finalgame.GUI.GuiFrame;
import finalgame.GUI.NotificationSystem;
import finalgame.GUI.battle.BattleScreen;
import finalgame.GUI.battle.BattleScreenManager;
import finalgame.GUI.stage.Stage;
import finalgame.GUI.stage.StageMap;
import finalgame.Graphics.*;
import finalgame.Resources.ThreadedResourceDownloader;
import java.applet.Applet;
import java.awt.*;
import java.io.File;
import java.util.Stack;

import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Game extends Canvas implements Runnable {

	private boolean gameRunning, quit, initialized;
	private Thread mThread;
	private GuiFrame menu;
	private ButtonListener listener, quitListener;
	private Texture backgroundTexture;
	private NotificationSystem notifications;
	private Stack<RenderObj> graphicsStack;
	
	public static UnicodeFont gameFont, titleFont;
	public static UnicodeFont[] fonts;
	public static Texture gf, icon;
	public static Game game;
	public static int messageHeight, titleHeight;

	public Jukebox jukebox;
	public OptionsManager gameSettings;
	public Stage currentRoom;
	public Stage altStage_1, altStage_2;

	public Game() {
		mThread = null;
		gameRunning = false;
		initialized = false;
		game = this;
	}

	/**
	 * Init the graphics canvas
	 */
	private void initCanvas() {
		this.setFocusable(true);
		this.requestFocus();
		this.setEnabled(true);
		this.setSize(GraphicsConstants.WIDTH, GraphicsConstants.HEIGHT);
		this.setBackground(Color.black);
		this.setIgnoreRepaint(true);
	}

	/**
	 * Update the game resources
	 */
	private void updateResources() {
		ThreadedResourceDownloader t = new ThreadedResourceDownloader(this);
		t.run();
		try {
			t.join();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Init the display.
	 */
	private void initDisplay() {
		try {
			Display.setParent(this);
			Display.setDisplayMode(new DisplayMode(GraphicsConstants.WIDTH,
					GraphicsConstants.HEIGHT));
			Display.setTitle("PokemonCS");
			Graphics g = this.getGraphics();
			if (g != null) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, GraphicsConstants.WIDTH,
						GraphicsConstants.HEIGHT);
				g.dispose();
			}
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

	/**
	 * Init OpenGL
	 */
	private void initGL() {
		glEnable(GL_TEXTURE_2D); // Enable Texture Mapping
		// glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearColor(0, 0, 0, 0); // Black Background
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,
				GL_NEAREST_MIPMAP_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
				GL_NEAREST_MIPMAP_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

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
	}

	/**
	 * General init. Perform init actions that don't fit under any other
	 * category.
	 */
	private void initGeneral() {
		gameSettings = new OptionsManager(this, Util.getWorkingDir());
		RenderEngine.init();
	}

	/**
	 * Initialize the sound system.
	 */
	private void initSoundSystem() {
		jukebox = new Jukebox();
		jukebox.loadSoundSettings(gameSettings);
	}

	/**
	 * Load the resources.
	 */
	private void loadResources() {
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader
					.getResourceAsStream("resources/font/pokemon.ttf", true));
			gameFont = new UnicodeFont(f.deriveFont(12f));
			gameFont.addAsciiGlyphs();
			gameFont.getEffects().add(new ColorEffect(java.awt.Color.black));
			gameFont.loadGlyphs();
			messageHeight = gameFont.getHeight("H");
			titleFont = new UnicodeFont(f.deriveFont(24f));
			titleFont.addAsciiGlyphs();
			titleFont.getEffects().add(new ColorEffect(java.awt.Color.black));
			titleFont.loadGlyphs();
			titleHeight = titleFont.getHeight("H");
			fonts = new UnicodeFont[2];
			fonts[0] = gameFont;
			fonts[1] = titleFont;
			gf = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream(
							"resources/image/font/alpha.png", true));
			backgroundTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream(
							"resources/image/background/background.png", true));
			icon = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/image/block/tallgrass.png",
							true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Installs a resource. Currently only sounds are downloaded so this method
	 * just adds them to the SoundManager.
	 */
	public void installResource(String name, File res) {
		int i = name.indexOf("/");
		String s = name.substring(0, i);
		name = name.substring(i + 1);

		if (s.equalsIgnoreCase("sound")) {
			jukebox.addSound(name, res);
		} else if (s.equalsIgnoreCase("streaming")) {
			jukebox.addStreaming(name, res);
		} else if (s.equalsIgnoreCase("music")) {
			jukebox.addMusic(name, res);
		}
	}

	/**
	 * Initialize the GUI.
	 */
	private void initGUI() {
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
		menu = new GuiFrame(0, 0, GraphicsConstants.WIDTH,
				GraphicsConstants.HEIGHT);
		menu.setListener(listener);
		menu.addButton("start", 100, 100, "Start", gf);
		menu.addButton("exit", 100, 140, "Exit", gf);
		menu.addLabel(10, 10, "PokemonCS", gf);
		notifications = new NotificationSystem("ns");
		graphicsStack = new Stack<RenderObj>();
		currentRoom = StageMap.getStage("safari_zone");
		graphicsStack.push(new RenderPointer(currentRoom));
		//graphicsStack.push(BattleScreenManager.getNewBattleScene("battlewater"));
	}

	/**
	 * Init
	 */
	public void init() {
		System.out.println("Begin init...");
		initCanvas();
		System.out.println("Initialized { canvas } ...");
		updateResources();
		System.out.println("Updated { resources } ...");
		initDisplay();
		System.out.println("Initialized { display } ...");
		initGL();
		System.out.println("Initialized { OpenGL } ...");
		initGeneral();
		System.out.println("Initialized { general } ...");
		initSoundSystem();
		System.out.println("Initialized { sound system } ...");
		loadResources();
		System.out.println("Initialized { resources } ...");
		initGUI();
		System.out.println("Initialized { gui } ...");
		System.out.println("...Done.");
		initialized = true;
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
			gameRunning = true;
			menu.clearFocus();
		} else { // The quit button
			quit = true;
		}
	}
	
	public static void delve(RenderObj toRender) {
		if(toRender != null) game.graphicsStack.add(toRender);
	}
	
	public static void enterStage(Stage newStage) {
		if(newStage != null) game.currentRoom = newStage;
		if(game.graphicsStack.size() > 0)
			((RenderPointer) game.graphicsStack.get(0)).setPointer(game.currentRoom);
	}

	/**
	 * Update the game
	 */
	public void update() {
		jukebox.playRandomMusicIfReady();
		if (notifications.hasNotifications()) {
			notifications.update();
		} else {
			if(Keyboard.isKeyDown(Keyboard.KEY_1)) enterStage(StageMap.getStage("safari_zone"));
			else if (Keyboard.isKeyDown(Keyboard.KEY_2)) enterStage(StageMap.getStage("rte_1"));
			int delta = 4, dx = 0, dy = 0;
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				dy = -delta;
			else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				dy = delta;
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				dx = -delta;
			else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				dx = delta;
			if(!(dx == 0 && dy == 0))
				currentRoom.translate(dx, dy);
		}
	}

	/**
	 * Render the game
	 */
	public void render() {
		if(graphicsStack.size() > 0)
			graphicsStack.peek().render();
		notifications.draw();
	}

	/**
	 * Main thread
	 */
	public void run() {
		if (!initialized) {
			init();
		}
		while (!(Display.isCloseRequested() || quit)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				gameRunning = false;
				for (; Keyboard.next();)
					;
			}
			glClear(GL_COLOR_BUFFER_BIT);
			Render2D.drawBg(backgroundTexture);
			if (gameRunning) { // Update Game
				update();
			} else { // Update Menu
				menu.update();
			}
			render();
			if (!gameRunning) { // Draw menu over game
				Render2D.drawRect(new Location(0, 0), GraphicsConstants.WIDTH,
						GraphicsConstants.HEIGHT,
						new float[] { 0, 0, 0, 0.75f }, false);
				menu.draw();
			}
			/**
			 * ...
			 */
			Display.update();
			Display.sync(GraphicsConstants.FRAMERATE);
		}
		jukebox.closeGame();
		if (quitListener != null) {
			quitListener.doCallback();
		}
		Display.destroy();
		System.exit(0);
	}

	/**
	 * Threading stuff
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
}
