package ticketmanagement;

public class User {

	private String name;
	private String surname;
	private String address;
	private String username;
	private String password;
	private String email;
	private int phoneNumber;
	
	
	public User(String name, String surname, String username,
			String password) {
		
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		
	}
	
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	public void setName(String n)
	{
		name = n;
	}
	
	
	public String getName()
	{
		return name;
	}
	
	public void setSurname(String sn)
	{
		surname = sn;
	}
	
	public String getSurname()
	{
		return surname;
	}
	
	public void setAddress(String a)
	{
		address = a;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setUsername(String u)
	{
		username = u;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setPassword(String pass)
	{
		password = pass;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setEmail(String e)
	{
		email = e;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setPhoneNumber(int ph)
	{
		phoneNumber = ph;
	}
	
	public int getPhoneNumber()
	{
		return phoneNumber;
	}
	
}
