package server2package;

import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.ws.Endpoint;

import server2package.server2impl;
import server2package.listernerforserver2;


public class publishserver2 {

	
	public static void main(String args[]) 
	{
		listernerforserver2 tl1=new listernerforserver2(4001);
  		Thread t1=new Thread(tl1);
  		t1.start();
		Endpoint endpoint=Endpoint.publish("http://localhost:8081/cal", new server2impl());
		System.out.println(endpoint.isPublished());
  	/*	Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run()
			  {
				  if(server2impl.a.isEmpty())
				  {
					  
				  }
				  else
				  {
					  
					Set<String> setd= server2impl.a.keySet();
					Iterator itd=setd.iterator();
					while(itd.hasNext())
					{
						String s1=(String)itd.next();
							Set<String> setr =	server2impl.a.get(s1).keySet();
							Iterator itr =setr.iterator();
							while(itr.hasNext())
							{
								String s2=(String)itr.next();
									Set<String> sett =	server2impl.a.get(s1).get(s2).keySet();
									Iterator itt =sett.iterator();
									while(itt.hasNext())
									{
										String s3=(String)itt.next();
										server2impl.a.get(s1).get(s2).put(s3,"Available");
									}
							}
					}
					Set<String> seta= server2impl.d.keySet();
					Iterator ita=seta.iterator();
					while(ita.hasNext())
					{
						String ss1=(String)ita.next();
						server2impl.d.remove(ss1);
					}
					System.out.println("Database reset");
				  }
			  }
			}, 2*60*1000, 2*60*1000);*/
	}
}

