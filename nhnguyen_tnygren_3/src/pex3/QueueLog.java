package pex3;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class QueueLog extends JDialog {
	public JButton remove;
	public QueueLog(){
		
	setTitle("Your Movies Queue");
	setSize(300,300);
	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(new EtchedBorder(), "Movie Titles"));
	remove = new JButton("Remove From Queue");
	add(remove, BorderLayout.SOUTH);
	
	
	}

}
