package clientpackage;
import java.io.*;

import server1package.server1interface;
import server2package.server2interface;
import server3package.server3interface;




public class adminclass {
	

	String rno;
	int No,No1;
	String date;
	String timeslot;
	boolean response;
	public void admin(String Id,server1interface serv1,server2interface serv2,server3interface serv3)throws IOException
	{
		boolean check1=true;
		while(check1)
		{
        String s1="DVL";
		String s2="KKL";
		String s3="WST";
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	String campuss=Id.substring(0,3);
	System.out.println("Enter 1 to create a reccord ,enter 2 to delete a record ,enter 3 to exit");
	No1=Integer.parseInt(br.readLine());
	  if(!(No1==1||No1==2||No1==3))
      {
      	System.out.println("Enter valid input");
      	break;
      }
	switch(No1)
	{
	case 1:
	
	{
		System.out.println("Enter Room No");
	     rno=(br.readLine());
	     System.out.println("Enter date");
	     date=br.readLine();
	    System.out.println("Enter the timeslot ");
		timeslot=br.readLine();
		if(campuss.equals(s1))
    	{	
			response=serv1.createroom(rno,date,timeslot);
			if(response)
			{
				System.out.println("Room Creation Was Succesful");
			}
			else
			{
				System.out.println("Room Creation Was Unsuccesful");
			}
			
    	}
    	else if(campuss.equals(s2))
    	{
    		
    		response=serv2.createroom(rno,date,timeslot);
    		if(response)
			{
				System.out.println("Room Creation Was Succesful");
			}
			else
			{
				System.out.println("Room Creation Was Unsuccesful");
			}
    	}
    	else if(campuss.equals(s3))
    	{
    		response=serv3.createroom(rno,date,timeslot);
    		if(response)
			{
				System.out.println("Room Creation Was Succesful");
			}
			else
			{
				System.out.println("Room Creation Was Unsuccesful");
			}
    	}
	
	break;
	}
	case 2:
	{System.out.println("Enter Room No");
	rno=(br.readLine());
	System.out.println("Enter  date");
	date=br.readLine();
	
	System.out.println("Enter  the timeslot");
	 
		timeslot=br.readLine(); 
		if(campuss.equals(s1))
    	{	
			response=serv1.deleteroom(rno,date,timeslot);
			if(response)
			{
				System.out.println("Room Deletion Was Succesful");
			}
			else
			{
				System.out.println("Room Deletion Was Unsuccesful");
			}
    	}
    	else if(campuss.equals(s2))
    	{
    		response=serv2.deleteroom(rno,date,timeslot);
    		if(response)
			{
				System.out.println("Room Deletion Was Succesful");
			}
			else
			{
				System.out.println("Room Deletion Was Unsuccesful");
			}
    	}
    	else if(campuss.equals(s3))
    	{
    		response=serv3.deleteroom(rno,date,timeslot);
    		if(response)
			{
				System.out.println("Room Deletion Was Succesful");
			}
			else
			{
				System.out.println("Room Deletion Was Unsuccesful");
			}
    	}
	break;
	}
	case 3:
	{
		check1=false;
		break;
	}
}

	}
}
}