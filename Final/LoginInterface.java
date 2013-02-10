package ticketmanagement;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class LoginInterface implements ActionListener {
	
	

	JLabel titleLabel;
	JTextField username,password;
	JLabel wrongDetails;
	JFrame frame;
	JButton loginButton;
	
	
	
	public String getUsername() {
		return username.getText();
	}

	public void setUsername(String username) {
		this.username.setText(username);
	}

	public String getPassword() {
		return password.getText();
	}

	public void setPassword(String password) {
		this.password.setText(password);
		
	}

	public JButton getLoginButton(){
		return loginButton;
	}
	
	public String getWrongDetails(){
		return wrongDetails.getText();
	}
	
	
	
	public void setupFrame()
	{
		//title of frame
		frame = new JFrame("Ticket Management System");
		
		//panel to add labels username & password
		JPanel userPanel = new JPanel();
		//panel to add textfields for user input
	    JPanel usertextFields = new JPanel();
		//main panel to add the two above
		JPanel panel = new JPanel();
				
		//textfield for user input
		username = new JTextField(15);
		password = new JTextField(15);
		
		//button for user login
		loginButton = new JButton("Login");
		titleLabel = new JLabel();
		JLabel usernameLabel = new JLabel();
		JLabel passwordLabel = new JLabel();
		wrongDetails = new JLabel();
		//set panel to null so we can set it wherever we want
		panel.setLayout(null);
		
		//adding login label and setting up font
		Font title = titleLabel.getFont().deriveFont(Font.BOLD,26.0f);
		titleLabel.setFont(title);	
		titleLabel.setText("Login Screen");
		//set the location of the label and the size.
		titleLabel.setLocation(90,20);
		titleLabel.setSize(290,30);
		titleLabel.setHorizontalAlignment(0);
		//add it to the panel
		panel.add(titleLabel);
		
		//setting userpanel layout to null so we can give custom position and size for it
		userPanel.setLayout(null);
		userPanel.setLocation(60,35);
		userPanel.setSize(150,200);
		panel.add(userPanel);
		
		usernameLabel.setText("Username:");
		usernameLabel.setLocation(50,35);
		usernameLabel.setSize(70,80);
		userPanel.add(usernameLabel);
		
		passwordLabel.setText("Password:");
		passwordLabel.setLocation(50,85);
		passwordLabel.setSize(70,80);
		userPanel.add(passwordLabel);
		
		
		//setting usertextfield panel layout to null so we can give custom position and size for it
		usertextFields.setLayout(null);
		usertextFields.setLocation(180,35);
		usertextFields.setSize(200,200);
		panel.add(usertextFields);
		
		//setting username textfield layout to null so we can give custom position and size for it
		username.setLayout(null);
		username.setSize(200,30);
		username.setLocation(30,60);
		usertextFields.add(username);
		
		//setting password textfield layout to null so we can give custom position and size for it
		password.setLayout(null);
		password.setSize(200,30);
		password.setLocation(30,110);
		usertextFields.add(password);
		
		//setting custom position and size for login button
		loginButton.setLocation(180,280);
		loginButton.setSize(140,50);
		loginButton.addActionListener(this);
		panel.add(loginButton);
		
		//a label in case a user mistupes login information or user doesn't exist
		wrongDetails.setText("");
		wrongDetails.setLocation(180,350);
		wrongDetails.setSize(140,120);
		panel.add(wrongDetails);
		
		frame.getContentPane().add(panel);
		//set size and visibility of frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
		//center frame to computer screen
		frame.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		//get username and password for JTextFields
		String _username = username.getText().trim().toString();
		String _password = password.getText().trim().toString();
		
		if(_username != null && _password != null )
		{
			SystemInterface system = new SystemInterface();
			//connect to DB
			Connection con = DatabaseInteraction.connect();
			//find if user exists in DB
			User user = findUserinDB(con,_username);
			//close DB connection
			DatabaseInteraction.disconnect(con);
			//if user exists
			if(user.getUsername() != null)
			{
				//and user equals with the infomation given
				if(user.getUsername().equals(_username) && user.getPassword().equals(_password))
				{
					//hide login screen
					frame.setVisible(false);
					//show system interface screen
					system.setupInterface(user);
					
				}
			}
			//else show label Wrong user details!
			else
				wrongDetails.setText("Wrong user details!");
			
		}
		else
		{
			wrongDetails.setText("Wrong user details!");
		}
		
	}
    
	public User findUserinDB(Connection connection,String username)
	{
	User	userFound = new User();
		
		try 
		{
			String user_name = "";
        	String name = "";
        	String surname = "";  
        	String pass = "";
        	Boolean isAdmin, isCashier;
        	
			//prepare statement
        	Statement statement = connection.createStatement(); 
	       
	        statement.setQueryTimeout(30);  // set timeout to 30 sec.
	        //select from table users where username = username user input
	        ResultSet rs = statement.executeQuery("Select * From Users WHERE username = \"" + username + "\"");
	       
	        if (statement.execute("Select * From Users WHERE username = \"" + username + "\"")) {
	            //get results
	        	rs = statement.getResultSet();
	            //while result set has next add user information
	        	while(rs.next())
	            {
	                        		
	            	name = rs.getString("name");
	            	surname = rs.getString("surname");
	    			user_name = rs.getString("username");
	    			pass = rs.getString("password");
	    			isAdmin = rs.getBoolean("isAdmin");
	    			isCashier = rs.getBoolean("isCashier");
	            	//if user is an admin instantiate an admin object
            		if(isAdmin)
	            	{
            			userFound = new Admin();	
	            	}
	            	else if(isCashier)
	            	{
	            		userFound = new Cashier();
	            	}
	            	else
	            	{
	            		System.out.println("User not valid!");
	            	}
            		
            		userFound.setName(name);
            		userFound.setSurname(surname);
            		userFound.setUsername(user_name);
            		userFound.setPassword(pass);
	            }
	        }
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		
		return userFound;
	}
}
