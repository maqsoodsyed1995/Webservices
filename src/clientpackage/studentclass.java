package clientpackage;

import java.io.*;
	
import java.lang.*;

import server1package.server1interface;
import server2package.server2interface;
import server3package.server3interface;


public class studentclass {

		
  public void student(String Id, server1interface serv1,server2interface serv2,server3interface serv3)throws IOException, InterruptedException
		{
			String rno;
			String date; 
			int No1;
			String No2=new String();
			String campusname;
			String timeslot;
			String bookingid;
			BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
			boolean check=true;
 while(check)
 { 
	
	     System.out.println("press 1 to bookroom \n press 2 to get available timeslot \n press 3 to cancelbooking\n press 4 to chnage reservation press 5 to exit ");
	     
        String campuss=Id.substring(0,3);
        No1=Integer.parseInt(br.readLine());
        if(!(No1==1||No1==2||No1==3||No1==4||No1==5))
        {
        	System.out.println("Enter valid input");
        	break;
        }
        	switch(No1)
        {
        case 1:
        	{
        		
        		
        		
        		System.out.println("Enter campus name");
        	campusname=br.readLine();
        	if(!(campusname.equals("KKL")||campusname.equals("DVL")||campusname.equals("WST")))
        	{
        		System.out.println("Enter the campus name correctly");
        		break;
        	}
        		System.out.println("Enter date");
        	
        	date=br.readLine();
        	System.out.println("Enter roomnumber");
        	rno=br.readLine();
        	if(!rno.matches("[0-9][0-9][0-9]"))
        	{
        		System.out.println("Enter the room no as a 3 digit number");
        		break;
        	}
        	System.out.println("Enter timeslot");
        	timeslot=br.readLine();
        	if(!timeslot.matches("[0-9][0-9]-[0-9]"))
        	{
        		System.out.println("Enter the timeslot as XX-X");
        		break;
        	}
        	
        		
        	if(campuss.equals(new String("DVL")))
        	{	
        		bookingid=serv1.bookroom(campusname, rno, date, timeslot,Id);
        		if(bookingid.equals("its already booked"))
        			System.out.println("its already booked");
        		else if(bookingid.equals("limit reached"))
        		System.out.println("you have reached the booking limit");
        		else if(bookingid.equals("room doesn't exist"))
        			System.out.println("Room doesn't exist");
        		else
        			System.out.println("Your booking id is "+bookingid);
        	}
        	else if(campuss.equals(new String("KKL")))
        	{
        		bookingid=serv2.bookroom(campusname, rno, date,timeslot,Id);
        		if(bookingid.equals("its already booked"))
        			System.out.println("its already booked");
        		else if(bookingid.equals("limit reached"))
        		System.out.println("you have reached the booking limit");
        		else if(bookingid.equals("room doesn't exist"))
        			System.out.println("Room doesn't exist");
        		else
        			System.out.println("Your booking id is "+bookingid);
        		}
        	else if(campuss.equals(new String("WST")))
        	{
        		bookingid=serv3.bookroom(campusname, rno, date, timeslot,Id);
        		if(bookingid.equals("its already booked"))
        			System.out.println("its already booked");
        		else if(bookingid.equals("limit reached"))
        		System.out.println("you have reached the booking limit");
        		else if(bookingid.equals("room doesn't exist"))
        			System.out.println("Room doesn't exist");
        		else
        			System.out.println("Your booking id is "+bookingid);
        	}
        	
        	
        	break;
        	}
        	
        case 2:
        	{
        		System.out.println("Enter  the date");
        	date=br.readLine();
        	if(campuss.equals(new String("DVL")))
        	{	
        		No2=serv1.getAvailableTimeSlot(date);
        		System.out.println(No2);
        	}
        	else if(campuss.equals(new String("KKL")))
        	{
        		No2=serv2.getAvailableTimeSlot(date);
        		System.out.println(No2);
        	}
        	else if(campuss.equals(new String("WST")))
        	{
        		No2=serv3.getAvailableTimeSlot(date);
        		System.out.println(No2);
        	}
        	
        	break;
        	}
        case 3:
        	{
        		System.out.println("Enter the bookind id given at the time of booking");
        		bookingid=br.readLine();
        		if(campuss.equals(new String("DVL")))
        	{	
        		String result=	serv1.cancelBooking(bookingid,Id);	
        		if(result.equals(new String("cancelled")))
        		{
        			System.out.println("cancellation was succesful");
        		
        		}
        		else
        		{
        			System.out.println("cancellation was not succesful");
        		}
        			
        	}
        		
        	else if(campuss.equals(new String("KKL")))
        	{

        		String result=serv2.cancelBooking(bookingid,Id);	
        		if(result.equals(new String("cancelled")))
        		{
        			System.out.println("cancellation was succesful");
        		
        		}
        		else
        		{
        			System.out.println("cancellation was not succesful");
        		}
        			
        	}
        	else if(campuss.equals(new String("WST")))
        	{
        		String result =serv3.cancelBooking(bookingid,Id);	
        		if(result.equals(new String("cancelled")))
        		{
        			System.out.println("cancellation was succesful");
        		
        		}
        		else
        		{
        			System.out.println("cancellation was not succesful");
        		}
        			
        	}
        	
        	
        	break;}
        case 5 :
        {
        	check=false;
        	break;
        	
        	
        }
        case 4 :
        {  
        	System.out.println("enter the booking id ");
            String bookid=br.readLine();
        	System.out.println("Enter campus name");
        	campusname=br.readLine();
        	if(!(campusname.equals("KKL")||campusname.equals("DVL")||campusname.equals("WST")))
        	{
        		System.out.println("Enter the campus name correctly");
        		break;
        	}
        		System.out.println("Enter date");
        	
        	date=br.readLine();
        	System.out.println("Enter roomnumber");
        	rno=br.readLine();
        	if(!rno.matches("[0-9][0-9][0-9]"))
        	{
        		System.out.println("Enter the room no as a 3 digit number");
        		break;
        	}
        	System.out.println("Enter timeslot");
        	timeslot=br.readLine();
        	if(!timeslot.matches("[0-9][0-9]-[0-9]"))
        	{
        		System.out.println("Enter the timeslot as XX-X");
        		break;
        	}
        	
        		
        	if(campuss.equals(new String("DVL")))
        	{	
        		bookingid=serv1.changeReservation(Id,bookid,date, campusname, rno, timeslot);
        		if(bookingid.equals("its already booked"))
        			System.out.println("its already booked");
        	
        		else
        			System.out.println("Your booking id is "+bookingid);
        	}
        	else if(campuss.equals(new String("KKL")))
        	{
        		bookingid=serv2.changeReservation(Id,bookid,date, campusname, rno, timeslot);
        		if(bookingid.equals("its already booked"))
        			System.out.println("its already booked");
        		
        		else
        			System.out.println("Your booking id is "+bookingid);
        		}
        	else if(campuss.equals(new String("WST")))
        	{
        		bookingid=serv3.changeReservation(Id,bookid,date, campusname, rno, timeslot);
        		if(bookingid.equals("its already booked"))
        			System.out.println("its already booked");
        		
        		else
        			System.out.println("Your booking id is "+bookingid);
        	}
        	
        	
        	break;
        }
        }
        	
 }
		
		}		// TODO Auto-generated method stub

	}
