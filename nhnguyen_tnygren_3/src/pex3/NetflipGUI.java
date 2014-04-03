package pex3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class NetflipGUI extends JPanel implements ListSelectionListener, ActionListener
{
	private DefaultListModel<String> listModel;
	private JList<String> list;
	
	

	public NetflipGUI()
	{
		super( new BorderLayout() );
		NetflipDatabase db = new NetflipDatabase();

		ResultSet rs = db.getAllMovies();

		// Create the list model and add elements to it.
		this.listModel = new DefaultListModel<String>();

		try {
			while (rs.next()) {
				this.listModel.addElement( rs.getString("title"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create the JList and put it in the center of the applet.
		
		setBorder(new TitledBorder(new EtchedBorder(), "Movie Titles"));
		this.list = new JList<String>( this.listModel );
		this.list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.list.addListSelectionListener( this );
		this.add( new JScrollPane( list ), BorderLayout.WEST );
		
		
	}

	// Called whenever the item selected in the JList changes.
	@Override
	public void valueChanged( ListSelectionEvent evt )
	{
		// This method seems to be called twice for each click,
		// so only do something when the value actually changes.
		if( evt.getValueIsAdjusting() )
		{
			this.searchPrompt.setText( list.getSelectedValue() );
		}
	}

	// Called when the user clicks the Remove button.
	@Override
	public void actionPerformed( ActionEvent evt )
	{
		// Probably should do some error checking here to be sure an item is selected, etc.
		listModel.removeElementAt( list.getSelectedIndex() );
	}

	private static final long serialVersionUID = 1L;
	
	
}

/*
 * For an explanation of this constant, see
 * 
 *   http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
 * 
 * Basically, it makes the complier happy, so just leave it here.
 */
//private static final long serialVersionUID = 1L;
