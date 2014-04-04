package pex3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SearchPanel extends JPanel implements ActionListener {

	// Link with the search list panel to send search information
	private SearchListPanel slp;
	
	// Normal Search options
	private JTextField searchBox;
	private JButton searchButton;
	private JButton advancedSearchButton;
	
	// Advanced Search options
	private JComboBox<String> option;
	private JComboBox<String> ratingBox;
	private JComboBox<String> categorieBox;
	private String[] advSearchOption = {"Title/Actor", "Actor", "Title"};

	public SearchPanel(ArrayList<String> ratings, ArrayList<String> categories){
		// Create the normal search panel
		JPanel searchPanel= new JPanel();
		searchPanel.setBorder(new TitledBorder(new EtchedBorder(), "WELCOME TO NETFLIP"));
		
		// Create the buttons and search box field
		this.searchButton = new JButton( "Search" );
		this.advancedSearchButton = new JButton( "Advance Search");
		this.searchBox= new JTextField(20);
		// Add components to the normal search panel
		searchPanel.add(this.searchBox);
		searchPanel.add(this.searchButton);
		searchPanel.add(advancedSearchButton);
		
		// Create an advance search panel
		JPanel advPanel= new JPanel();
		advPanel.setBorder(new TitledBorder(new EtchedBorder(), "Advange Search"));
		
		// Create advanced search components
		this.option = new JComboBox<String>(advSearchOption);
		this.categorieBox = new JComboBox<String>();
		this.ratingBox = new JComboBox<String>();
		// Add advanced search options to the advanced panel
		advPanel.add(this.option);
		advPanel.add(this.ratingBox);
		advPanel.add(this.categorieBox);
		
		// Add the Search panel and advanced panels to this main search panel
		this.add(searchPanel);
		this.add(advPanel);

		// Add a listener to both buttons
		searchButton.addActionListener(this);
		advancedSearchButton.addActionListener(this);
		
		// Add elements to the ComboBoxes
		categorieBox.addItem("Categories");
		for (String category : categories) {
			categorieBox.addItem( category );	

		}
		ratingBox.addItem("Ratings");
		for (String rating : ratings) {
			ratingBox.addItem( rating );	
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource() == searchButton ) {
			
			if (searchBox.getText().length() == 0) {
				slp.addInitialList();
			} else {
				slp.search(searchBox.getText());
			}
		} else if ( e.getSource() == advancedSearchButton ) {
			slp.advancedSearch();
		}
	}
	
	public void setSearchListLink(SearchListPanel slp) {
		this.slp = slp;
	}

	private static final long serialVersionUID = 1L;


}
