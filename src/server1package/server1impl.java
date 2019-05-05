package server1package;
import java.net.*;
import java.nio.ByteBuffer;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Timestamp;
import java.util.*;
import org.omg.CORBA.ORB;
import server1package.listernerforserver1;
import  server1package.threadsender1;
import  server2package.threadsender2;
import  server3package.threadsender3;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(endpointInterface="server1package.server1interface")
@SOAPBinding(style=Style.RPC)
public class server1impl implements server1interface{
	
	
	//static HashMap<String,HashMap<String,String>> b=new HashMap<String,HashMap<String,String>>();
	//static HashMap<String,String> c=new HashMap<String,String>();   
	static HashMap<String,HashMap<String,HashMap<String,String>>> a=new HashMap<String,HashMap<String,HashMap<String,String>>>();
	static HashMap<String,ArrayList<String>> d=new HashMap<String,ArrayList<String>>();
	static ArrayList<String> e=new ArrayList<String>();
	String bookingid;
	double w1;
	int i=0,cou=0;
	String date;
	String rno;
	String timeslot;
	static String fcount;
	public server1impl() 
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
	
	public boolean createroom(String rno,String date,String timeslot)
	{
		
		
		if(a.isEmpty())
		{
			synchronized(this)
			{	a.put(date, new HashMap<String,HashMap<String,String>>());
				a.get(date).put(rno, new HashMap<String,String>());
				a.get(date).get(rno).put(timeslot,"Available");
			}
				System.out.println(a);
				try 
				{
					
					fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","DVLA12345");
					fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","DVLSERVER");
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
										
										fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation Unsuccesful)","DVLA12345");
										fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation Unsuccesful)","DVLSERVER");
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
							System.out.println(a);
							}
							try 
							{
								
								fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","DVLA12345");
								fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","DVLSERVER");
								
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
						
						fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","DVLA12345");
						fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","DVLSERVER");
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
			System.out.println(a);
			}
			try 
			{
				
				fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","DVLA12345");
				fwriter(rno+"	"+date+"	"+timeslot+" 	(Creation succesful)","DVLSERVER");
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
						fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion succesful)","DVLA12345");
						fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion succesful)","DVLSERVER");
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
				fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion unsuccesful)","DVLA12345");
				fwriter(rno+"	"+date+"	"+timeslot+" 	(deletion unsuccesful)","DVLSERVER");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return false;
	}

	public  String bookroom(String campusName,String rno,String date,String timeslot,String UID)
	{
		if(d.isEmpty())
		{
			synchronized(this)
			{
			d.put(UID,new ArrayList<String>());
			}
			
		}
		if(!d.containsKey(UID))
		{	synchronized(this)
			{
			d.put(UID,new ArrayList<String>());
			}
		}
		
		if(d.get(UID).size()>=3)
		{	
			System.out.println("You have reached the booking limit already");
			try 
			{	
				fwriter(rno+"	"+date+"	"+timeslot+" 	(booking limit reached)",UID);
				fwriter(rno+"	"+date+"	"+timeslot+" 	(booking limit reached)","DVLSERVER");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return "limit reached";
		}
		else
		{
			
			if(campusName.equals(new String("DVL")))
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
								System.out.println(d);
								}
								try 
								{	
									fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	booking successful",UID);
									fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	booking successful","DVLSERVER");
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
									fwriter(rno+"	"+date+"	"+timeslot+" 	already booked","DVLSERVER");
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
			else if(campusName.equals(new String("KKL")))
	
			{
				String s1=new String("KKL");
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
				threadsender1 ts1=new threadsender1(s6,4001);
			    Thread t1=new Thread(ts1);
				t1.start();
		         try {
					t1.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				System.out.println("Returned value from KKL"+ts1.dstring);
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
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	room booked in KKL",UID);
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	room booked in KKL","DVLSERVER");
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
				threadsender1 ts2=new threadsender1(s6,4002);
				Thread t2=new Thread(ts2);
				t2.start();
				try {
					t2.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Returned value from WST"+ts2.dstring);
				//bookingid=ts2.dstring.substring(0,35);
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
					fwriter(rno+"	"+date+"	"+timeslot+" 	"+bookingid+"	room booked in WST","DVLSERVER");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		d.get(UID).add(bookingid);
		System.out.println(d);
		
return bookingid;
}

	public  String getAvailableTimeSlot ( String date)
	{ 
		
	
		
	
		
		String s3="Available";
		cou=0;
		String cou1=localcount(date);
	
		System.out.println("local count of DVL  "+cou1);
		threadsender1 ts1=new threadsender1(date,4001);
		threadsender1 ts2=new threadsender1(date,4002);
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
	
		  return "DVL("+cou1+")  "+"KKL("+j+")  "+"WST("+k+")";
		
		
	}
	public String localcount(String date)
	{
		int lcount=0;
		String s3="Available";
         if(!a.isEmpty())
		{
        	 if(a.containsKey(date))
        	 { Set<String> set= a.get(date).keySet();
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
				lcount++;
			}
	
		}}}
         String s=Integer.toString(lcount);
		return s;
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
								System.out.println(s9);
								//System.out.println(d.get(s9));
								//System.out.println(d.get(s9).get(1));
								System.out.println(userid);
								System.out.println(bookingID);

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
				  						/*Set<String> set3= d.keySet();
				  						Iterator it3=set3.iterator();
				  						while(it3.hasNext())
				  						{
				  							String s4=(String)it3.next();
				  							for(i=0;i<d.get(s4).size();i++)
				  							{*/
				  								System.out.println(d);
				  									d.get(userid).remove(bookingID);
										}
				  								System.out.println(d);
				  								try 
												{	
													fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in DVL",userid);
													fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in DVL","DVLSERVER");
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
			String w1=new String();
			String w2=new String(bookingID);
			String w3=new String(userid);
			w1=w1.concat(w2);
			w1=w1.concat(w3);
			System.out.println(w1);
			threadsender1 ts3=new threadsender1(w1,4001);
			Thread t3=new Thread(ts3);
			t3.start();
			try 
			{
				t3.join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			String w4=ts3.dstring;
			String con=w4.substring(0,9);
			System.out.println(w4);
			if(con.equals(new String("cancelled")))
			{
				try 
				{
					
					fwriter(bookingID+"	(Cancelled booking)",userid);
					fwriter(bookingID+"	(Cancelled booking)","DVLSERVER");
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
			    	threadsender1 ts4=new threadsender1(w1,4002);
			    	Thread t4=new Thread(ts4);
			    	t4.start();
			    	try 
			    	{
						t4.join();
					} 
			    	catch (InterruptedException e) 
			    	{
					
						e.printStackTrace();
					}
			    	String w5=ts4.dstring;
			    	String con1=w5.substring(0,9);
			    	System.out.println(w5);
			    	if(con1.equals(new String("cancelled")))
					{
			    		try 
						{
							
							fwriter(bookingID+"	(Cancelled booking)",userid);
							fwriter(bookingID+"	(Cancelled booking)","DVLSERVER");
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
				
				fwriter(bookingID+"	( booking cancellation failed)",userid);
				fwriter(bookingID+"	( booking cancellation failed)","DVLSERVER");
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
	  		boolean b1=false,b2=false;

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
	  					System.out.println(s9);
	  					System.out.println(s9.length());
	  					System.out.println(d.get(s9));
	  					//System.out.println(d.get(s9).get(1).length());
	  					//System.out.println(d.get(s9).get(1));
	  					System.out.println(userid);
	  					System.out.println(userid.length());
						System.out.println(bookingID);
						System.out.println(bookingID.length());
						Iterator setn1=d.get(s9).iterator();
						while(setn1.hasNext())
						{
							String sn1=(String)setn1.next();
							if((b1=s9.equals(userid)) && sn1.equals(bookingID))
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
											fwriter(s+"	"+s1+"	"+s2+" 	booking cancellation successful in DVL",userid);
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
	  		
	  if(b1)
	  {
		  System.out.println("value of b1");
	  }
	  if(b2)
	  {
		  System.out.println("value of b2");
	  }
	  return "notcancelled";
	  }
	  public String  changeReservation(String studentid,String booking_id,String new_date, String new_campus_name, String new_room_no, String new_time_slot)
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
				if(d.get(studentid).size()==3)
				{ 
					synchronized(this)
					{
					d.get(studentid).remove(booking_id);
					ret=bookroom(new_campus_name,new_room_no, new_date,new_time_slot,studentid);
					d.get(studentid).add(booking_id);
					}
			           if(ret.equals("its already booked"))
			           {
			        	   
			        	   try 
							{
								
								fwriter(booking_id+"	change reservation was not successful as the new room the student waants to book is already booked",studentid);
								fwriter(booking_id+"	change reservation was not successful as the new room the student waants to book is already booked","DVLSERVER");
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
			        	   System.out.println(a);
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
				else
				{
					ret=bookroom(new_campus_name,new_room_no, new_date,new_time_slot,studentid);
			           if(ret.equals("its already booked"))
			           {
			        	   try 
							{
								
								fwriter(booking_id+"	change reservation was not successful as the new room the student waants to book is already booked",studentid);
								fwriter(booking_id+"	change reservation was not successful as the new room the student waants to book is already booked","DVLSERVER");
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
			        	   System.out.println(d);
			        	   System.out.println(a);
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