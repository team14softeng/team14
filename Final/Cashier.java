package ticketmanagement;

public class Cashier extends User{
	
	private int tamiasId;
	
	public void setCashierID(int id)
	{
		tamiasId = id;
	}
	
	public int getCashierID()
	{
		return tamiasId;
	}
}
