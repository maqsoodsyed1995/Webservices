package server1package;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.rmi.RemoteException;

import server1package.server1impl;

public class listernerforserver1 extends Thread
{
	public int count;
	private int c=0,d=0;
	String date ;
	public listernerforserver1 ( int d)
	{
		this.c=c;
		this.d=d;
		this.date=date;
		 
	}
	
	public void run()
	{ 
		int e=0;
		System.out.println("Inside the listerner of server 1 ");
		int c=0;
	
		DatagramSocket dSocket = null;
		try  
		{      
				dSocket = new DatagramSocket(d); 
				server1impl s1i=new server1impl();
				while(true)
				{
						byte[] buffer4 = new byte[1000];
						DatagramPacket request4 = new DatagramPacket(buffer4, buffer4.length);
						dSocket.receive(request4);
					    String req=new String(request4.getData());
					    System.out.println(new String(request4.getData()) );
					    System.out.println(req.length());
					    if((req.substring(0,3). equals(new String("DVL")))||(req.substring(0,3).equals(new String("KKL")))||(req.substring(0,3).equals(new String("WST"))))
					    {
					    	System.out.println("inside the if of 29");
					    	String campusname=req.substring(0,3);
					    	String date=req.substring(3,13);
					    	String roomnumber=req.substring(13,16);
					    	String timeslot=req.substring(16,20);
					    	String uid=req.substring(20,29);
					    	String rvalue=s1i.bookroom(campusname, roomnumber, date, timeslot, uid);
					    	buffer4=rvalue.getBytes();
					    	DatagramPacket reply = new DatagramPacket(buffer4,buffer4.length, request4.getAddress(), request4.getPort());
							System.out.println("SENDING DATA FROM BOOKROOM OF DVL  "+new String(reply.getData()));
							dSocket.send(reply);
					      
					      
					    }	
					    else if((req.charAt(2))=='-')
					    {
					    	String date1=new String(request4.getData());
					    	date1=date1.substring(0,10);
					    	String rvalue1=new String();
					    	rvalue1 = s1i.localcount(date1);
							buffer4=rvalue1.getBytes();
					    	DatagramPacket reply = new DatagramPacket(buffer4,buffer4.length, request4.getAddress(), request4.getPort());
							System.out.println(new String(reply.getData()));
							dSocket.send(reply);
							String s1=new String(reply.getData());
						    System.out.println("SENDING AVAILABLE TIMELSOTS FROM DVL  "+new String(reply.getData()));
						    System.out.println("LENGTH OF THE SENT MESSAGE FROM DVL  "+s1.length());
					    	
					    }
					    else
					    {
					    	String cb=new String(request4.getData());
					    	String bookid=cb.substring(0,36);
					    	String userid=cb.substring(36,45);
					        String rvalue2=s1i.localcancelBooking(bookid,userid);
					    	buffer4=rvalue2.getBytes();
					    	DatagramPacket reply = new DatagramPacket(buffer4,buffer4.length, request4.getAddress(), request4.getPort());
							dSocket.send(reply);
							System.out.println("SENDING DATA FROM CANCELBOOKING OF DVL  "+new String(reply.getData()));

					    	
					    }
				
				}
		}
		catch (SocketException m)
		{
				System.out.println("Socket: " + m.getMessage());
		}
		catch (IOException m)
		{
			System.out.println("IO: " + m.getMessage());
			
		}
	
	    finally 
	    {
	    	if(dSocket != null) 
			dSocket.close();
	    }
   }
	
}
	

