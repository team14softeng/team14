package ticketmanagement;

import java.sql.*;


public class DatabaseInteraction {
	 public static Connection connect()
	 {
		 Connection connection = null;
		    try {
		    	    
				// Load the JDBC driver
			    Class.forName("com.mysql.jdbc.Driver").newInstance();

			    // Create a connection to the database		      
			    String url = "jdbc:mysql://localhost:3306/tms?useUnicode=true&characterEncoding=utf8";
			    String username = "pete"; //username toy xristi gia sindensi stin vasi
			    String password = "petelian"; //password
			    connection = DriverManager.getConnection(url, username, password);
			    System.out.println("connected");
			        		
			    } 
		    catch (ClassNotFoundException e) 
		    {
			    // Could not find the database driver
		    	System.out.println("Faulty");
			} 
		    catch (SQLException e) 
		    {
			   // Could not connect to the database
			   System.out.println(e);
			   System.out.println("no connection....");
			    
		    } 
		    catch (InstantiationException e) 
		    {
					e.printStackTrace();
			}
		    catch (IllegalAccessException e) 
		    {
					e.printStackTrace();
			}
		    return connection;
	 }
	 
	 public static void disconnect(Connection connection)
	 {
		 if (connection !=null)
		 {
			 try 
			 {
				//close connection to database
				connection.close();
			 }  
			 catch (SQLException e) 
			 {
				e.printStackTrace();
			 }
		 }
	 }
}