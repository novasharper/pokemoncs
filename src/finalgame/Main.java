package finalgame;

import finalgame.GUI.battle.BattleScreen;
import finalgame.Graphics.GraphicsConstants;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.reflections.Reflections;

public class Main extends JFrame {
	private static final long serialVersionUID = 4193394252292999582L;
	private Canvas game;
	private Main main;
	private JButton go;
	private JButtonListener listener;
	private Method stop, start;

	public Main(String title) {
		super(title);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Listener, will be used by the launch button
		listener = new JButtonListener(this);

		// Set custom window icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Main.class.getResource("tallgrass.png")));

		// Set layout so that button will be centered
		setLayout(new FlowLayout(FlowLayout.CENTER, 0,
				(GraphicsConstants.HEIGHT - 50) / 2));

		// Add launch button
		go = new JButton("Launch");
		go.addActionListener(this.listener);
		add(go);

		// Allow for game to successfully exit (prevents it from freezing)
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// These are used for reflection b/c Canvas does not contain the start
		// or stop methods
		try {
			stop = Class.forName("finalgame.Game").getMethod("stop", null);
			start = Class.forName("finalgame.Game").getMethod("start", null);
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		// Listen for window closing
		addWindowListener(new WindowListener() {

			public void windowClosing(WindowEvent e) {
				if (game != null) {
					try { // Try to stop game if it is instantiated
						stop.invoke(game, null);
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
						System.exit(0);
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
						System.exit(0);
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
						System.exit(0);
					}
				} else { // Exit otherwise
					System.exit(0);
				}
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowOpened(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}
		});

		// Update title
		setTitle("PokemonCS");
		setSize(GraphicsConstants.WIDTH + getInsets().left + getInsets().right,
				GraphicsConstants.HEIGHT + getInsets().top + getInsets().bottom);
		setResizable(false);
	}

	private void launchApplet() {
		setLayout(null); // Reset the layout so the game adds correctly
		add(game); // Add the game to the form
		game.setSize(GraphicsConstants.WIDTH, GraphicsConstants.HEIGHT);
		setTitle("PokemonCS");

		try {
			start.invoke(game, null);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
			System.exit(0);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
			System.exit(0);
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Hand go button click.
	 */
	public void buttonClicked(JButton buttonObj) {
		if (buttonObj == go) {
			try {
				buttonObj.setVisible(false);
				game = (Canvas) Class.forName("finalgame.Game").newInstance();
				launchApplet();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Main m = new Main("PokemonCS Launcher");
		m.setVisible(true);
	}
}
