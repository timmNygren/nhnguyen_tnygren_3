package pex3;

/**
 * Demonstration of creating a table via a JDBC/MySQL connection.
 * 
 * @author Randy Bower
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class NetflipDatabase
{
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	public NetflipDatabase () {
		try {
			con = DriverManager.getConnection( "jdbc:mysql://localhost:8889/sakila", "root", "root" );
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getAllMovies(){
		String query = "select * from film order by title";

		try
		{
			rs = stmt.executeQuery( query );	
		}
		catch( SQLException e )
		{
			e.printStackTrace();
		}
		return rs;
	}
	
}