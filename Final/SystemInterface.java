package ticketmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.*;

public class SystemInterface {
	
	JFrame mainFrame;
	JPanel mainPanel = new JPanel();
	

	public void setupInterface(User user)
	{
		//setup common user interface
		setupMainFrame(user);
		
		//if user is an admin
		if(user instanceof Admin)
		{
			//setup admin screen
			setupAdmin(user);
		}
		else if(user instanceof Cashier)
		{
			//setup cashier screen
			setupCashier(user);
		}
		//else return to login screen
		else
		{
			LoginInterface window = new LoginInterface();
			window.setupFrame();
		}
		
	}

	
	private void setupCashier(User user) 
	{
		//instantiate three panels for placing the Cashier functionalities
		JPanel panelChooseRouteTicket = new JPanel();
		JPanel panelChoosePrintTicket = new JPanel();
		JPanel panelChooseAdditionalInfo = new JPanel();
		
		//set panel layout to null so we can put a custom size and location for it
		panelChooseRouteTicket.setLayout(null);
		panelChooseRouteTicket.setSize(500,150);
		panelChooseRouteTicket.setLocation(0,10);
		
		//set button layout to null so we can put a custom size and location for it
		JButton chooseRouteTicket = new JButton();
		chooseRouteTicket.setLayout(null);
		chooseRouteTicket.setSize(250,70);
		chooseRouteTicket.setLocation(130,50);
		chooseRouteTicket.setText("Choose Route,Ticket,Hour & Date");
		
		//set actionlistener for button
		chooseRouteTicket.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JLabel routeTitle = new JLabel("Choose Route");
				//get registered routes from DB
				HashMap<String,Integer> routes = populateRoutes();
				//put route Names to routeSetList
				Object[] routeSetList =  routes.keySet().toArray();
				JComboBox<Object> routeList = new JComboBox<Object>(routeSetList);
				
				//set up  JSpinner for changing easily Date and Time
				JLabel dateTitle = new JLabel("Choose Date");
				JSpinner dateSpinner = new JSpinner( new SpinnerDateModel() );
				JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner,"d:M:y");
				dateSpinner.setEditor(dateEditor);
				dateSpinner.setValue(new Date());
							
				JLabel timeTitle = new JLabel("Choose Time");
				JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
				JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
				timeSpinner.setEditor(timeEditor);
				timeSpinner.setValue(new Date());
				
				//TextField for entering seatNumber
				JTextField seatNumber = new JTextField();
				int seatsNumberLeft = 0;			
				//get route total number of seats
				String route = routeList.getSelectedItem().toString();
				//if it exists on hash map then get the number of seats left
				if(routes.containsKey(route))
				{
					seatsNumberLeft = routes.get(route);
				}
				JLabel seatTitle = new JLabel("Choose Seat Number: there are " + seatsNumberLeft +" left");
				//object array that sets up all JLabel, JButton etc and puts it on JOptionPane
				Object[] ob = {routeTitle,routeList,dateTitle,dateSpinner,timeTitle,timeSpinner,seatTitle,seatNumber};
				//Format time to HH:MM
				Date time = (Date) timeSpinner.getValue();
				Calendar c = Calendar.getInstance();
				c.setTime(time);
				String time_parsed = String.valueOf(c.get(Calendar.HOUR))+ ":" + String.valueOf(c.get(Calendar.MINUTE));
				//Format date to dd/mm/yyyy
				Date date = (Date)dateSpinner.getValue();
				c.setTime(date);
				String date_parsed = String.valueOf(c.get(Calendar.DAY_OF_MONTH))+ "/" + String.valueOf(c.get(Calendar.MONTH))+ "/"  + String.valueOf(c.get(Calendar.YEAR));
				//Show JOptionPAne
				JOptionPane.showConfirmDialog(null, ob, "Insert Ticket Infomation Please", JOptionPane.OK_CANCEL_OPTION);
				
				//get ticket information and put it in a ticket object
				Ticket ticket = new Ticket();
				ticket.setRouteName(routeList.getSelectedItem().toString());
				ticket.setTime(time_parsed);
				ticket.setDate(date_parsed);
				ticket.setSeatNumber(Integer.parseInt(seatNumber.getText()));
				//insert Ticket to Database
				ticket.insertTicket(seatsNumberLeft);
				}});
		
		//add button to panel
		panelChooseRouteTicket.add(chooseRouteTicket);
		
		//set panel layout to null so we can put a custom size and location for it	
		panelChoosePrintTicket.setLayout(null);
		panelChoosePrintTicket.setSize(500,150);
		panelChoosePrintTicket.setLocation(0,160);
		
		//set panel button to null so we can put a custom size and location for it
		JButton choosePrintTicket = new JButton();
		choosePrintTicket.setLayout(null);
		choosePrintTicket.setSize(250,70);
		choosePrintTicket.setLocation(130,50);
		choosePrintTicket.setText("Print Ticket");
		
		//set actionlistener for printing ticket button
		choosePrintTicket.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//setup a title
				JLabel title = new JLabel();
				title.setText("Choose one ticket to print.");
				
				Ticket ticket = new Ticket();
				//get availiable tickets from DB
				ArrayList<Ticket> tickets = ticket.getTicket();
				Ticket[] ticketArray = new Ticket[tickets.size()];
				for(int i=0; i < tickets.size();i++)
				{
					ticketArray[i] = tickets.get(i);
				}
				
				JComboBox<Ticket> ticketList = new JComboBox<Ticket>(ticketArray);
				Object[] ob = {title,ticketList};
				
				JOptionPane.showConfirmDialog(null, ob, "Print Ticket", JOptionPane.OK_CANCEL_OPTION);
				//instantiate PrintTicket
				PrintTicket printTicket = new PrintTicket();
				//print selected ticket from JComboBox
				printTicket.printSelectedItem((Ticket)ticketList.getSelectedItem());
				
			}});
		//add button to panel
		panelChoosePrintTicket.add(choosePrintTicket);
		
		//set panel layout to null so we can put a custom size and location for it
		panelChooseAdditionalInfo.setLayout(null);
		panelChooseAdditionalInfo.setSize(500,150);
		panelChooseAdditionalInfo.setLocation(0,310);
		
		//set button layout to null so we can put a custom size and location for it
		JButton chooseAdditionalInfo = new JButton();
		chooseAdditionalInfo.setLayout(null);
		chooseAdditionalInfo.setSize(250,70);
		chooseAdditionalInfo.setLocation(130,50);
		chooseAdditionalInfo.setText("Print Additional Info");
		//actionlistener for printing additional info
		chooseAdditionalInfo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}});
		
		//add button to panel
		panelChooseAdditionalInfo.add(chooseAdditionalInfo);
		
		mainFrame.getContentPane().add(panelChooseRouteTicket);
		mainFrame.getContentPane().add(panelChoosePrintTicket);
		mainFrame.getContentPane().add(panelChooseAdditionalInfo);
		mainFrame.getContentPane().add(mainPanel);
	}

	private HashMap<String, Integer> populateRoutes() {
		HashMap<String,Integer> routeMap = new HashMap<String,Integer>();
		
		try
		{
			//open up connection to DB
			Connection con = DatabaseInteraction.connect();
			
			Statement statement = con.createStatement(); 
		       
	        statement.setQueryTimeout(30);  // set timeout to 30 sec.
	        //get result set with routes table information
	        ResultSet rs = statement.executeQuery("Select * From Routes");
	        
	        while(rs.next())
	        {	
	        	//add to Hash map routeName and totalNumberOfSeats infomation
	        	routeMap.put(rs.getString("routeName"), rs.getInt("totalNumberOfSeats"));
	        }
	        //close connection to DB      	       
	        DatabaseInteraction.disconnect(con);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return routeMap;
	}
	private void setupAdmin(User user) 
	{
		//setup panels
		JPanel panel_addRouteAndSeats = new JPanel();
		JPanel panel_addAnnouncement = new JPanel();
		JPanel mainPanel = new JPanel();
		
		//set panel layout to null so we can put a custom size and location for it
		panel_addRouteAndSeats.setLayout(null);
		panel_addRouteAndSeats.setLocation(0,10);
		panel_addRouteAndSeats.setSize(500,250);
		
		//set panel layout to null so we can put a custom size and location for it
		panel_addAnnouncement.setLayout(null);
		panel_addAnnouncement.setLocation(0,260);
		panel_addAnnouncement.setSize(500,150);
		
		//set button layout to null so we can put a custom size and location for it
		JButton addRouteAndSeats = new JButton();
		addRouteAndSeats.setLayout(null);
		addRouteAndSeats.setSize(250,100);
		addRouteAndSeats.setLocation(125,75);
		addRouteAndSeats.setText("Add Route and Total Number of Seats");
		//add button to panel
		panel_addRouteAndSeats.add(addRouteAndSeats);
		
		addRouteAndSeats.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//admin enters routeName and number of seats info
				String route = JOptionPane.showInputDialog(null, "Enter Route: ", "", 1);
				String numberOfSeats = JOptionPane.showInputDialog(null, "Enter Number Of Seats: ", "", 1);
				
				//create a route object and assign the values
				Route r = new Route();
				r.setRoute(route);
				short totalNumber = Short.parseShort(numberOfSeats);
				r.setTotalNumberOfSeats(totalNumber);
				//insert route to DB
				r.insertRoute();			
			}});
		
		//set button layout to null so we can put a custom size and location for it
		JButton addAnnouncement = new JButton();
		addAnnouncement.setLayout(null);
		addAnnouncement.setSize(200,80);
		addAnnouncement.setLocation(140,35);
		addAnnouncement.setText("Add Announcement");
		//add button to panel
		panel_addAnnouncement.add(addAnnouncement);
		//add actionlister for adding an announcement
		addAnnouncement.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//add label title and textfield for inserting a title
				JLabel title = new JLabel("Add Title");
				JTextField titleText = new JTextField();
				//body of announcement title and textarea
				JLabel body = new JLabel("Body");
				JTextArea bodyText = new JTextArea(15,30);
				
				Object[] ob = {title,titleText,body,bodyText};
				
				 JOptionPane.showConfirmDialog(null, ob, "Please Add an Announcement", JOptionPane.OK_CANCEL_OPTION);
				 //insert Annoucement to DB
				 insertAnnouncement(titleText.getText(),bodyText.getText());
			}

			private void insertAnnouncement(String title, String body) {
				
				try
				{
					//open connection to DB
					Connection con = DatabaseInteraction.connect();
					
					Statement statement = con.createStatement(); 
				       
			        statement.setQueryTimeout(30);  // set timeout to 30 sec.
			        //insert info to annoucement table
			        statement.executeUpdate("Insert INTO announcement (title,body) Values ( '" +  title + "', '" + body + "')");
			        //close DB connection
			        DatabaseInteraction.disconnect(con);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				
			}});
		
		mainFrame.getContentPane().add(panel_addRouteAndSeats);
		mainFrame.getContentPane().add(panel_addAnnouncement);
		mainFrame.getContentPane().add(mainPanel);
	}


	public void setupMainFrame(User user)
	{
		mainFrame = new JFrame();
	    
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(500,500);
		mainFrame.setVisible(true);
		mainFrame.setLocationRelativeTo(null);
		JPanel messagepanel = new JPanel();
		messagepanel.setLayout(null);
		messagepanel.setLocation(0,0);
		messagepanel.setSize(500,40);
		
		JLabel message = new JLabel();
		//add logout button
		JButton logout = new JButton();
		logout.setLayout(null);
		logout.setLocation(400,10);
		logout.setSize(80,20);
		logout.setText("Logout");
		
		//actionlistener of logout button
		logout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//if logout button is pressed hide mainFrame
				mainFrame.setVisible(false);
				//return to login screen
				LoginInterface login = new LoginInterface();
				login.setupFrame();
				
			}});
		message.setLayout(null);
		message.setLocation(2,10);
		message.setSize(250,20);
		
		message.setText("Welcome, "+ user.getName() + " " + user.getSurname());
		messagepanel.add(message);
		messagepanel.add(logout);
		mainFrame.getContentPane().add(messagepanel);
		
	}
	
	
}
