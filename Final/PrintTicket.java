package ticketmanagement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PrintTicket extends Print{

	public void printSelectedItem(Ticket ticket) 
	{
		//label for routename
		JLabel ticketRouteName = new JLabel();
		ticketRouteName.setText("Route: " + ticket.getRouteName());
		
		//label for date
		JLabel ticketDate = new JLabel();
		ticketDate.setText("Date: " + ticket.getDate());
		
		//label for time
		JLabel ticketTime = new JLabel();
		ticketTime.setText("Time: " + ticket.getTime());
		
		//label for seat
		JLabel ticketSeat = new JLabel();
		ticketSeat.setText("Seat #: " + String.valueOf(ticket.getSeatNumber()));
		
		
		Object[] ob = {ticketRouteName,ticketDate,ticketTime,ticketSeat};
		JOptionPane.showConfirmDialog(null, ob, "Print Ticket", JOptionPane.PLAIN_MESSAGE);
		
		
	}

}
