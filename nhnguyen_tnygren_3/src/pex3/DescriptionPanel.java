package pex3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DescriptionPanel extends JPanel {

	private NetflipDatabase db;
	private JTextArea dBox;
	private JButton addtoQueue;
	private JButton moviesinQueue;

	public DescriptionPanel(NetflipDatabase db) {
		super(new BorderLayout());
		this.db = db;
		setBorder(new TitledBorder(new EtchedBorder(), "Description"));
		this.dBox= new JTextArea(2,20);
		this.addtoQueue = new JButton("Add to Queue");
		this.moviesinQueue = new JButton("Movies in Queue");
		this.add(dBox,BorderLayout.CENTER);
		JPanel infoPanel = new JPanel(new GridLayout(2,2));
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(moviesinQueue);
		buttonPanel.add(addtoQueue);
		this.add(buttonPanel, BorderLayout.SOUTH);
		moviesinQueue.addActionListener(new QueueButtonListener());
	}

	public void displayInfo(String name, boolean searchByActor) {
		
		if (searchByActor) {
			// We display info different from movies
		} else {
			displayMovieInfo(db.getMovieDetails(name));
		}
	}
	
	public void displayMovieInfo(MovieItem movie) {
		
	}
	
	public class QueueButtonListener implements ActionListener{
		QueueLog qLog = new QueueLog();
		public void actionPerformed(ActionEvent e){
			if(e.getSource()== moviesinQueue){
				qLog.setVisible(true);

			}
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}


