package pex3;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class SearchPanel extends JPanel {

	private NetflipDatabase db;
	private JButton search;
	private JTextField searchBox;
	private JButton aSearch;
	private JComboBox<String> option;
	private JComboBox<String> ratingBox;
	private JComboBox<String> categorieBox;
	private String[] sOption = {"Title/Actor", "Actor", "Title"};

	public SearchPanel(ArrayList<String> ratings, ArrayList<String> categories){
		JPanel sPanel= new JPanel();
		JPanel advPanel= new JPanel();
		sPanel.setBorder(new TitledBorder(new EtchedBorder(), "WELCOME TO NETFLIP"));
		advPanel.setBorder(new TitledBorder(new EtchedBorder(), "Advange Search"));
		this.search = new JButton( "Search" );
		this.aSearch = new JButton( "Advance Search");
		this.searchBox= new JTextField(20);
		this.categorieBox = new JComboBox<String>();
		this.ratingBox = new JComboBox<String>();
		this.option = new JComboBox<String>(sOption);
		sPanel.add(this.searchBox);
		sPanel.add(this.search);
		sPanel.add(aSearch);
		this.add(sPanel);
		advPanel.add(this.option);
		advPanel.add(this.ratingBox);
		advPanel.add(this.categorieBox);
		this.add(advPanel);


		// Add elements to the ComboBox
		categorieBox.addItem("Categories");
		for (String category : categories) {
			categorieBox.addItem( category );	

		}
		ratingBox.addItem("Ratings");
		for (String rating : ratings) {
			ratingBox.addItem( rating );	
		}

	}


	private static final long serialVersionUID = 1L;
}
