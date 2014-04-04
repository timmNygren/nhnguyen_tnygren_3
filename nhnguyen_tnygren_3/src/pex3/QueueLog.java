package pex3;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Used to view and manage the queue
 *
 *
 */
public class QueueLog extends JDialog {

	public JButton removeButton;

	public QueueLog() {

		setTitle("Your Movies Queue");
		setSize(300, 300);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Movie Titles"));
		removeButton = new JButton("Remove From Queue");
		add(removeButton, BorderLayout.SOUTH);

	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
