package pex3;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class SearchPanel extends JPanel {

	private JLabel searchPrompt;
	private JButton search;
	private JTextArea searchBox;
	private JCheckBox actor, titlebox;
	private JComboBox rating;
	private JComboBox categories;
	public SearchPanel(){
		setBorder(new TitledBorder(new EtchedBorder(), "Welcome to Netflip"));
		this.searchPrompt = new JLabel( "Search Your Favorite Movie Here:" );
		this.search = new JButton( "Search" );
		this.searchBox= new JTextArea(2,20);
		this.titlebox = new JCheckBox("By Title");
		this.actor = new JCheckBox("By Actor");
		this.categories = new JComboBox();
		this.rating = new JComboBox();
		JPanel panel = new JPanel();
		this.add( this.searchPrompt );
		this.add(this.searchBox);
		this.add(this.search);
		this.add(this.titlebox);
		this.add(this.actor);
		this.add(this.rating);
		this.add(this.categories);
	}
}
