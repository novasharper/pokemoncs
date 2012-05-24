/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * 
 * @author Pat
 */
class JButtonListener implements ActionListener {

	private Main frame;

	public JButtonListener(Main m) {
		this.frame = m;
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		this.frame.buttonClicked(b);
	}
}
