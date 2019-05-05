package server2package;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.RPC)
public interface server2interface 
{

	public boolean createroom(String rno,String date,String timeslot);
	public boolean deleteroom(String rno, String date, String timeslot);
	public  String bookroom(String campusName,String rno,String date,String timeslot,String UID);
	public  String getAvailableTimeSlot ( String date);
	public  String  cancelBooking (String bookingID, String userid);
	public String  changeReservation(String studentid,String booking_id,String new_date, String new_campus_name, String new_room_no, String new_time_slot);
	
	
}
