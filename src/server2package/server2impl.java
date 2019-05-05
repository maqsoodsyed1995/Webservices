package server2package;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import org.omg.CORBA.ORB;



import server2package.listernerforserver2;
import server2package.threadsender2;

import server3package.threadsender3;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(endpointInterface="server2package.server2interface")
@SOAPBinding(style=Style.RPC)
public class server2impl implements server2interface 
{
	
	static HashMap<String,HashMap<String,String>> b=new HashMap<String,HashMap<String,String>>();
	static HashMap<String,String> c=new HashMap<String,String>();   
	static HashMap<String,HashMap<String,HashMap<String,String>>> a=new HashMap<String,HashMap<String,HashMap<String,String>>>();
	static HashMap<String,ArrayList<String>> d=new HashMap<String,ArrayList<String>>();
	static ArrayList<String> e=new ArrayList<String>();
	String bookingid;
	double w1;
	int i=0;
	String date;
	String rno;
	String timeslot;
	
	public static String fcount;
	
	
	 

	public server2impl() 
	{
		super();
	}  
	
	void fwriter(String data,String path)throws IOException
	{
		File f=new File("C:\\adminfile\\"+path+".txt");
		if(!f.exists())
			f.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(f,true));
		
	    writer.write(data);
       
	    writer.write(System.getProperty( "line.separator" ));
	  
	    writer.close();

		
	}
	public String localcount(String date)
	{
		int lcount=0;
		String s3="Available"; 
		if(!a.isEmpty())
		{    
			if(a.containsKey(date))
			{
				Set<String> set= a.get(date).keySet();
			
			Iterator it=set.iterator();
			while(it.hasNext())
			{
				String s =(String)it.next();
				Set<String> set1 =	a.get(date).get(s).keySet();
				Iterator it1 =set1.iterator();
				while(it1.hasNext())
				{
					String s1=(String)it1.next();
					if(s3.equals(a.get(date).get(s).get(s1)))
					{
						System.out.println(s3.equals(a.get(date).get(s).get(s1)));
						
							System.out.println(lcount);
						
						lcount++;
						System.out.println(lcount);
						}
				}
	
			}
		}}
		String s=Integer.toString(lcount);
		return s;
	}
	
	public boolean createroom(String rno,String date,String timeslot)
	{
		
		if(a.isEmpty())
		{       
			synchronized(this)
			{
				a.put(date, new HashMap<String,HashMap<String,String>>());
				a.get(date).put(rno, new HashMap<String,String>());
				a.get(date).get(rno).put(timeslot,"Available");
			}
				System.out.println(a);
				try 
				{
					
					fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","KKLA12345");
					fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","KKLSERVER");
				} 
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
				return true;
		}
		else
		{
			Set<String> setd= a.keySet();
			Iterator itd=setd.iterator();
			while(itd.hasNext())
			{
				String s1=(String)itd.next();
				if(date.equals(s1))
				{
					Set<String> setr =	a.get(date).keySet();
					Iterator itr =setr.iterator();
					while(itr.hasNext())
					{
						String s2=(String)itr.next();
						if(rno.equals(s2))
						{
							Set<String> sett =	a.get(date).get(rno).keySet();
							Iterator itt =sett.iterator();
							while(itt.hasNext())
							{
								String s3=(String)itt.next();
								if(timeslot.equals(s3))
								{
									System.out.println("Timeslot already exists");
									System.out.println(a);
									try 
									{
										
										fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation Unsuccesful)","KKLA12345");
										fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation Unsuccesful)","KKLSERVER");
									} 
									catch (IOException e) 
									{
										
										e.printStackTrace();
									}
									return false;
									
								}
							}
							synchronized(this)
							{
							a.get(date).get(rno).put(timeslot,"Available");
							}
							System.out.println(a);
							try 
							{
								
								fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","KKLA12345");
								fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","KKLSERVER");
							} 
							catch (IOException e) 
							{
								
								e.printStackTrace();
							}
							return true;
						}
					}
					synchronized(this)
					{
					a.get(date).put(rno,new HashMap<String,String>());
					a.get(date).get(rno).put(timeslot,"Available");
					}
					System.out.println(a);
					try 
					{
						
						fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","KKLA12345");
						fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","KKLSERVER");
					} 
					catch (IOException e) 
					{
						
						e.printStackTrace();
					}
					return true;
				}
			}
			synchronized(this)
			{
			a.put(date, new HashMap<String,HashMap<String,String>>());
			a.get(date).put(rno, new HashMap<String,String>());
			a.get(date).get(rno).put(timeslot,"Available");
			}
			System.out.println(a);
			try 
			{
				
				fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","KKLA12345");
				fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","KKLSERVER");
			} 
			catch (IOException e) 
			{
				
				e.printStackTrace();
			}
			return true;
		}				
			
		
		
	}	
	public boolean deleteroom(String rno, String date, String timeslot)
	{
		if(a.containsKey(date))
		{	
			if(a.get(date).containsKey(rno))
			{
				if(a.get(date).get(rno).containsKey(timeslot))
				{
					synchronized(this)
					{
					a.get(date).get(rno).remove(timeslot);
					}
					try 
					{	
						fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion succesful)","KKLA12345");
						fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion succesful)","KKLSERVER");
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
		
					return true;
				}
			}
		}	
			try 
			{	
				fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion unsuccesful)","KKLA12345");
				fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion unsuccesful)","KKLSERVER");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return false;
	}

public  String bookroom(String campusName,String rno,String date,String timeslot,String UID)
{
	int i=0;
	  
		if(d.isEmpty())
		{  
			synchronized(this)
			{
			d.put(UID,new ArrayList<String>());	
			}
			System.out.println("in 1st if");
		}
		if(!d.containsKey(UID))
		{	synchronized(this)
			{
			d.put(UID,new ArrayList<String>());
			}
		}
		
		if(d.get(UID).size()>=3)
		{	System.out.println("You have reached the booking limit already");
		try 
		{	
			fwriter(rno+"	"+date+"	"+timeslot+" 	limit reached",UID);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			return "limit reached";
		}
		else
		{
			System.out.println("in else part");
			System.out.println(a);
			if(campusName.equals(new String("KKL")))
			{
				if(a.containsKey(date))
				{	
					if(a.get(date).containsKey(rno))
					{
						if(a.get(date).get(rno).containsKey(timeslot))
						{
							System.out.println(a);
				
							if((a.get(date).get(rno).get(timeslot)).equals("Available"))
							{
								bookingid = UUID.randomUUID().toString();	
								synchronized(this)
								{
								a.get(date).get(rno).put(timeslot, bookingid);
								System.out.println(a);
								System.out.println("booked");
								d.get(UID).add(bookingid);
								}
								System.out.println(d);
								try 
								{	
									fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	booking successful","KKLS12345");
									fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	booking successful","KKLSERVER");
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
								}
								return bookingid;
							}
						
							else
							{
								System.out.println("its already booked");
					
								try 
								{	
									fwriter(rno+"	"+date+"	"+timeslot+" 	already booked",UID);
									fwriter(rno+"	"+date+"	"+timeslot+" 	already booked","KKLSERVER");
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
								}
								return "its already booked";
							}
						}
						return "room doesn't exist";
					}
					return "room doesn't exist";
				}
				return "room doesn't exist";
				
			}
				
	
		else if(campusName.equals(new String("DVL")))
			{
				
			String s1=new String("DVL");
			String s2=new String(date);
			String s3=new String(rno);
			String s4=new String(timeslot);
			String s5=new String(UID);
			String s7=new String("BR");
			String s6 =new String();
		   s6=s6.concat(s1);
			s6=s6.concat(s2);
			s6=s6.concat(s3);
			s6=s6.concat(s4);
			s6=s6.concat(s5);
			s6=s6.concat(s7);
			threadsender2 ts1=new threadsender2(s6,4000);
			Thread t1=new Thread(ts1);
			t1.start();
			try {
				t1.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Returned value from DVL"+ts1.dstring);
			//bookingid=ts1.dstring.substring(0,36);
			if(ts1.dstring.matches("limit reached(.*)"))
			{
				bookingid=ts1.dstring.substring(0,13);
				return bookingid;
			}
			else if(ts1.dstring.matches("its already booked(.*)"))
			{
				bookingid=ts1.dstring.substring(0,18);
				return bookingid;
			}
			else if(ts1.dstring.matches("room doesn't exist(.*)"))
			{	
				bookingid=ts1.dstring.substring(0,18);
				return bookingid;
			}
			else
			{
				bookingid=ts1.dstring.substring(0,36);
			}	
			try 
			{	
				fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	room booked in DVL",UID);
				fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	room booked in DVL","KKLSERVER");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			}
			else if(campusName.equals(new String("WST")))
			{
				String s1=new String("WST");
				String s2=new String(date);
				String s3=new String(rno);
				String s4=new String(timeslot);
				String s5=new String(UID);
				String s7=new String("BR");
				String s6 =new String();
			   s6=s6.concat(s1);
				s6=s6.concat(s2);
				s6=s6.concat(s3);
				s6=s6.concat(s4);
				s6=s6.concat(s5);
				s6=s6.concat(s7);
				threadsender2 ts2=new threadsender2(s6,4002);
				Thread t2=new Thread(ts2);
				t2.start();
				try {
					t2.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Returned value from WST"+ts2.dstring);
				//bookingid=ts2.dstring.substring(0,36);
				if(ts2.dstring.matches("limit reached(.*)"))
				{
					bookingid=ts2.dstring.substring(0,13);
					return bookingid;
				}
				else if(ts2.dstring.matches("its already booked(.*)"))
				{
					bookingid=ts2.dstring.substring(0,18);
					return bookingid;
				}
				else if(ts2.dstring.matches("room doesn't exist(.*)"))
				{	
					bookingid=ts2.dstring.substring(0,18);
					return bookingid;
				}
				else
				{
					bookingid=ts2.dstring.substring(0,36);
				}
				try 
				{	
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	room booked in WST",UID);
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	room booked in WST","KKLSERVER");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			
			}
		}
		d.get(UID).add((bookingid));
		System.out.println(d);
		
return bookingid;
}
	
	public  String getAvailableTimeSlot ( String date)
	{	
	
		int count1=0,count2=0;
		int sc1=0,sc2=0,sc3=0;
		String s3="Available";
		String cou1=localcount(date);
		
		System.out.println("local count of KKL  "+cou1);
		threadsender2 ts1=new threadsender2(date,4000); 
		threadsender2 ts2=new threadsender2(date,4002);
		Thread t1=new Thread(ts1);
		Thread t2=new Thread(ts2);
		
		t1.start();
		t2.start();
		String j=new String("");
		String k=new String("");
			try {
				t1.join();
				t2.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		j=ts1.dstring;
		j=j.substring(0,1);
		System.out.println("After j in DVL and the value from KKL is  "+j);
		k=ts2.dstring;
		k=k.substring(0,1);
		System.out.println("After K in DVL and the value from KKL is  "+k);	
		//String z=Integer.toString(cou);
			String z=cou1;
		
      return "KKL("+z+")  "+"DVL("+j+")  "+"WST("+k+")";
		
}
	  
	  public  String  cancelBooking (String bookingID, String userid)
	  {
		  	int c=0;
			String s3=bookingID;
			Set<String> set= a.keySet();
			Iterator it=set.iterator();
			while(it.hasNext())
			{
				String s =(String)it.next();
				Set<String> set1 =	a.get(s).keySet();
				Iterator it1 =set1.iterator();
				while(it1.hasNext())
				{
					String s1=(String)it1.next();
					Set<String> set2=a.get(s).get(s1).keySet();
					Iterator it2=set2.iterator();
					while(it2.hasNext())
					{
						String s2=(String)it2.next();
						if(s3.equals(a.get(s).get(s1).get(s2)))
						{	
							Set<String> setb= d.keySet();
							Iterator idb=setb.iterator();
							while(idb.hasNext())
							{
								
								String s9=(String)idb.next();
								System.out.println(s9+" s9");
								System.out.println(d.get(s9)+" d.get(s9)");
								//System.out.println(d.get(s9).get(1)+" d.get(s9).get(1)");
								
								Iterator setn1=d.get(s9).iterator();
								while(setn1.hasNext())
								{
									String sn1=(String)setn1.next();
									if((s9.equals(userid)) && sn1.equals(bookingID))
									{	
										synchronized(this)
										{
				  						a.get(s).get(s1).put(s2,"Available");
				  						System.out.println(a.get(s).get(s1).get(s2));	  						
				  						System.out.println("Booking cancellation was successful");
				  					
				  								System.out.println(d);
				  									d.get(userid).remove(bookingID);
										}
				  								System.out.println(d);
				  								try 
												{	
													fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in KKL",userid);
													fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in KKL","KKLSERVER");
												} 
												catch (IOException e) 
												{
													e.printStackTrace();
												}
				  								
				  								return "cancelled";
				  							}
				  										
				  						
				  						//return "cancelled";
				  					
				  					else
								{
									System.out.println("Invalid Access");
								}
							}
						}
					}
					}
			}}
			String w1=new String();
			String w2=new String(bookingID);
			String w3=new String(userid);
			w1=w1.concat(w2);
			w1=w1.concat(w3);
			System.out.println(w1);
			threadsender2 ts3=new threadsender2(w1,4000);
			Thread t3=new Thread(ts3);
			t3.start();
			try 
			{
				t3.join();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String w4=ts3.dstring;
			String con=w4.substring(0,9);
			String con1=w4.substring(0,12);
			System.out.println(w4);
			System.out.println(w4.length());
			if(con.equals(new String("cancelled")))
			{
				try 
				{
					
					fwriter(bookingID+"	(Cancelled booking)",userid);
					fwriter(bookingID+"	(Cancelled booking)","KKLSERVER");
				} 
				catch (IOException e) 
				{
					
					e.printStackTrace();
				}
				d.get(userid).remove(bookingID);
				return "cancelled";
			}
			else
			{     
					System.out.println(w1);
			    	threadsender2 ts4=new threadsender2(w1,4002);
			    	Thread t4=new Thread(ts4);
			    	t4.start();
			    	try 
			    	{
						t4.join();
					} 
			    	catch (InterruptedException e) 
			    	{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	String w5=ts4.dstring;
					 con=w5.substring(0,12);

			    	System.out.println(w5);
			    	if(con.equals(new String("cancelled")))
					{
			    		try 
						{
							
							fwriter(bookingID+"	(Cancelled booking)",userid);
							fwriter(bookingID+"	(Cancelled booking)","KKLSERVER");
						} 
						catch (IOException e) 
						{
							
							e.printStackTrace();
						}
			    		d.get(userid).remove(bookingID);
					    	return "cancelled";
					 }
			    	
			 }
			
	  
			System.out.println("before the return of not cancelled");
			try 
			{
				
				fwriter(bookingID+"	(booking cancellation was not succesful)",userid);
				fwriter(bookingID+"	(booking cancellation was not succesful)","KKLSERVER");
				
			} 
			catch (IOException e) 
			{
				
				e.printStackTrace();
			}
			return "notcancelled";
	  }

public  String  localcancelBooking (String bookingID, String userid)
{
	int c=0;

String s3=bookingID;
Set<String> set= a.keySet();
Iterator it=set.iterator();
while(it.hasNext())
{
	String s =(String)it.next();
	Set<String> set1 =	a.get(s).keySet();
	Iterator it1 =set1.iterator();
	while(it1.hasNext())
	{
		String s1=(String)it1.next();
		Set<String> set2=a.get(s).get(s1).keySet();
		Iterator it2=set2.iterator();
		while(it2.hasNext())
		{
			String s2=(String)it2.next();
			if(s3.equals(a.get(s).get(s1).get(s2)))
			{	
				Set<String> setb= d.keySet();
				Iterator idb=setb.iterator();
				while(idb.hasNext())
				{
					
					String s9=(String)idb.next();
					System.out.println(s9 +" S9");
					System.out.println(d.get(s9)+" d.get(s9)");
				//	System.out.println(d.get(s9).get(1)+" d.get(s9).get(1)");
					Iterator setn1=d.get(s9).iterator();
					while(setn1.hasNext())
					{
						String sn1=(String)setn1.next();
						if((s9.equals(userid)) && sn1.equals(bookingID))
						{	
	  						a.get(s).get(s1).put(s2,"Available");
	  						System.out.println(a.get(s).get(s1).get(s2));	  						
	  						System.out.println("Booking cancellation was successful");
	  						/*Set<String> set3= d.keySet();
	  						Iterator it3=set3.iterator();
	  						while(it3.hasNext())
	  						{
	  							String s4=(String)it3.next();
	  							for(i=0;i<d.get(s4).size();i++)
	  							{*/
	  								System.out.println(d);
	  									d.get(userid).remove(bookingID);
	  								System.out.println(d);
	  								try 
									{	
										fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in KKL",userid);
									} 
									catch (IOException e) 
									{
										e.printStackTrace();
									}
	  								
	  								return "cancelled";
	  							}
	  										
	  						
	  						//return "cancelled";
	  					
	  					else
					{
						System.out.println("Invalid Access");
					}
				}
			}
		}
	
	}
}
}	
return "notcancelled";
}
public String changeReservation(String studentid,String booking_id,String new_date, String new_campus_name, String new_room_no, String new_time_slot) 
{
	  String ret=new String();
	Set<String> sb1=d.keySet();
	Iterator ib1=sb1.iterator();
	while(ib1.hasNext())
	{
		String s1=(String)ib1.next();
		Iterator setn1=d.get(s1).iterator();
		while(setn1.hasNext())
		{
			String sn1=(String)setn1.next();
			if((s1.equals(studentid)) && sn1.equals(booking_id))
			{
				System.out.println("After finding bookingID");
				if(d.get(studentid).size()==3)
				{   
					synchronized(this)
					{
					d.get(studentid).remove(booking_id);
					ret=bookroom( new_campus_name,new_room_no, new_date,new_time_slot,studentid);
					d.get(studentid).add(booking_id);
					}
					System.out.println(a);
					System.out.println("After if of size check");
					if(ret.equals("its already booked"))
		            {
		        	    
		        	    try 
						{
							
							fwriter(booking_id+"	change reservation was not successful as the new room the student waants to book is already booked",studentid);
							fwriter(booking_id+"	change reservation was not successful as the new room the student waants to book is already booked","KKLSERVER");
						} 
						catch (IOException e) 
						{
							
							e.printStackTrace();
						}
		        	    return "its already booked";
		           }
		           
		           else
		           {
		        	   String cancel=cancelBooking(booking_id,studentid);
		        	   System.out.println("After else of cancel of size check");
		        	   System.out.println(a);
		        	   try 
						{
							
							fwriter(new_date+"		"+new_campus_name+" 	"+new_room_no+" 		"+new_time_slot+"		"+ret+" 		"+"(reservation changed)",studentid);
							fwriter(new_date+"		"+new_campus_name+" 	"+new_room_no+" 		"+new_time_slot+"		"+ret+" 		"+"(reservation changed)","KKLSERVER");
						} 
						catch (IOException e) 
						{
							
							e.printStackTrace();
						}
		        	   return ret;
		           }
				}
				else
				{
					ret=bookroom(new_campus_name,new_room_no, new_date,new_time_slot,studentid);
					System.out.println(a);
					System.out.println(ret);
					System.out.println("After else  of booking without doing sie check");
		           if(ret.equals("its already booked"))
		           {
		        	   System.out.println(a);
		        	   try 
						{
							
							fwriter(booking_id+"	change reservation was not successful as the new room the student waants to book is already booked",studentid);
							fwriter(booking_id+"	change reservation was not successful as the new room the student waants to book is already booked","KKLSERVER");
						} 
						catch (IOException e) 
						{
							
							e.printStackTrace();
						}  
		        	   return "its already booked";
		           }
		           
		           else
		           {
						System.out.println("before else  of cancel without doing sie check");

		        	   String cancel=cancelBooking(booking_id,studentid);
		        	   System.out.println(a);
		        	   System.out.println(cancel);
		        	 //  System.out.println(a);
						System.out.println("After else  of cancel without doing sie check");
						System.out.println(d);
						 try 
							{
								
								fwriter(new_date+"		"+new_campus_name+" 	"+new_room_no+" 		"+new_time_slot+"		"+ret+" 		"+"(reservation changed)",studentid);
								fwriter(new_date+"		"+new_campus_name+" 	"+new_room_no+" 		"+new_time_slot+"		"+ret+" 		"+"(reservation changed)","DVLSERVER");
							} 
							catch (IOException e) 
							{
								
								e.printStackTrace();
							}
						return ret;

		           }
				
				
				}
			}	
		
	}
	}
	return ret;
	  
}
}