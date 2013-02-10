package ticketmanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Ticket {
	
	private String routeName;
	private String time;
	private String date;
	private int seatNumber;
	

	public void setRouteName(String r)
	{
		routeName = r;
	}

	public String getRouteName()
	{
		return routeName;
	}
	
	public void setDate(String d)
	{
		date = d;
	}
	
	public String getDate()
	{
		return date;
	}
	public void setTime(String t)
	{
		time = t;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public void setSeatNumber(int s)
	{
		seatNumber = s;
	}
	
	public int getSeatNumber()
	{
		return seatNumber;
	}
	
	protected void insertTicket(int totalSeatNumber) 
	{
			
        try 
        {
        	Connection con = DatabaseInteraction.connect();
			
			Statement statement = con.createStatement(); 
		       
	        statement.setQueryTimeout(30);  // set timeout to 30 sec.
	        
			statement.executeUpdate("Insert INTO tickets (routeName,date,time,seat) Values ( '" +  routeName + "','" + date + "','" + time + "'," + seatNumber + ")");
			
			statement.executeUpdate("Update routes SET totalNumberOfSeats = '" + (totalSeatNumber - 1) + "'" + "Where routeName = " + "\"" + routeName + "\"");
		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
	}

	protected ArrayList<Ticket> getTicket() {
		ArrayList<Ticket> ticketSet = new ArrayList<Ticket>();
		try 
	        {
	        	Connection con = DatabaseInteraction.connect();
				
				Statement statement = con.createStatement(); 
			    
				 statement.setQueryTimeout(30);  // set timeout to 30 sec.
		        
		        ResultSet rs = statement.executeQuery("Select * From Tickets");
		        
		        while(rs.next())
		        {
		        	Ticket ticket = new Ticket();
				    
		        	ticket.setRouteName(rs.getString("routeName"));
		        	ticket.setDate(rs.getString("date"));
		        	ticket.setTime(rs.getString("time"));
		        	ticket.setSeatNumber(rs.getInt("seat"));
		        	
		        	ticketSet.add(ticket);
		        	
		        }
		        
			} 
	        catch (SQLException e) 
	        {
				e.printStackTrace();
			}
		
		return ticketSet;
	}
	
	
    @Override
    public String toString() {
        
    	String label = "Route: " + routeName + " Date: " + date + " Time: " + time + " Seat: " + seatNumber;
    	
    	return label;
    }

}
