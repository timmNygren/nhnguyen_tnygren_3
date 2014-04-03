package pex3;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DescriptionPanel extends JPanel {
	private JLabel description;
	private JTextArea dBox;
	private JButton addtoQueue;
	private JButton moviesinQueue;
	
	public DescriptionPanel(){
		super(new BorderLayout());
		JPanel dpanel= new JPanel();
		setBorder(new TitledBorder(new EtchedBorder(), "Description"));
		this.dBox= new JTextArea(2,20);
		JScrollPane scrollPane = new JScrollPane(dBox); 
		this.addtoQueue = new JButton("Add to Queue");
		this.moviesinQueue = new JButton("Movies in Queue");
		this.add(dBox,BorderLayout.CENTER);
		this.add(scrollPane, BorderLayout.WEST );
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(moviesinQueue);
		buttonPanel.add(addtoQueue);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		//this.add(dpanel, BorderLayout.CENTER);
	
	}
}


