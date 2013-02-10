package ticketmanagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Route {
	
	private String routeName;
	private short totalNumberOfSeats;
	
	public void setRoute(String r)
	{
		routeName = r;
	}
	
	public String getRoute()
	{
		return routeName;
	}
	
	public void setTotalNumberOfSeats(short seats)
	{
		totalNumberOfSeats = seats;
	}
	
	public short getTotalNumberOfSeats()
	{
		return totalNumberOfSeats;
	}
	
	protected void insertRoute() 
	{
		try
		{
			//open connection to DB
			Connection con = DatabaseInteraction.connect();
			
			Statement statement = con.createStatement(); 
		       
	        statement.setQueryTimeout(30);  // set timeout to 30 sec.
	        //insert route info to DB
	        statement.executeUpdate("Insert INTO routes (routeName,totalNumberOfSeats) Values ( '" + routeName + "', " + totalNumberOfSeats + ")");
	        //close connection to DB
	        DatabaseInteraction.disconnect(con);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
