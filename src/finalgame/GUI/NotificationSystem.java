/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame.GUI;

import finalgame.Graphics.GraphicsConstants;
import finalgame.Graphics.RenderObj;
import finalgame.Game;
import finalgame.Main;
import java.util.LinkedList;
import java.util.Queue;
import org.lwjgl.input.Keyboard;

/**
 * 
 * @author Pat
 */
public class NotificationSystem extends GuiElement implements RenderObj {
	private boolean a_down, b_down;
	private Queue<Notification> notifications;

	public NotificationSystem(String id) {
		super(5, 10, GraphicsConstants.WIDTH, GraphicsConstants.LETTER_SIZE, id);
		a_down = false;
		b_down = false;
		notifications = new LinkedList<Notification>();
	}

	public void draw() {
		if (notifications.size() > 0)
			notifications.peek().draw();
	}
	
	public void render() {
		draw();
	}

	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			a_down = true;
		else if (a_down) {
			if (notifications.size() > 0) {
				boolean done = notifications.peek().advance();
				if (done)
					notifications.remove();
			}
			a_down = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_B))
			b_down = true;
		else if (b_down) {
			if (notifications.size() > 0) {
				notifications.peek().reverse();
			}
			b_down = false;
		}
	}

	public boolean hasNotifications() {
		return notifications.size() > 0;
	}

	public void addNotification(String notification) {
		notifications.add(new Notification(this.x, this.y, notification,
				Game.game.gameFont));
	}

	public void addNotification(String notification, String speaker) {
		notifications.add(new Notification(this.x, this.y, notification,
				speaker, Game.game.gameFont));
	}
}
