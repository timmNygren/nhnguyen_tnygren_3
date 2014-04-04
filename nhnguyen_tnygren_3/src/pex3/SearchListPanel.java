package pex3;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class SearchListPanel extends JPanel implements ListSelectionListener
{
	private NetflipDatabase db;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private DescriptionPanel descriptionPanel;
	private boolean searchByActor = false;

	public SearchListPanel(NetflipDatabase db)
	{
		super( new BorderLayout() );
		this.db = db;
		// Create the list model and add elements to it.
		this.listModel = new DefaultListModel<String>();

		addInitialList();

		setBorder(new TitledBorder(new EtchedBorder(), "Search Results"));
		this.list = new JList<String>( this.listModel );
		this.list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.list.addListSelectionListener( this );
		this.add( new JScrollPane( list ), BorderLayout.CENTER );


	}
	
	public void addInitialList() {
		ArrayList<String> movies = this.db.getAllMovies();
		for (String movie : movies) {
			this.listModel.addElement(movie);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	@Override
	public void valueChanged( ListSelectionEvent evt ){
		// There are three selections that are applied during a value change
		// 1. Initial button press, getValueIsAdjusting = true
		// 2. Mouse dragging, getValueIsAdjusting = true for each list value the mouse drags over
		// 3. When the mouse is released getValueIsAdjusting = false
		// Using the third event to determine what is selected from the search list
		if( !evt.getValueIsAdjusting() ){
			this.descriptionPanel.displayInfo(list.getSelectedValue(), searchByActor);
		}
	}

	
	public void setDescriptionLink(DescriptionPanel descriptionPanel) {
		this.descriptionPanel = descriptionPanel;
	}

	/*
	 * For an explanation of this constant, see
	 * 
	 *   http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
