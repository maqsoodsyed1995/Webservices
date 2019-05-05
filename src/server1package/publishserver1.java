package server1package;

import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.ws.Endpoint;

import server1package.server1impl;
import server1package.listernerforserver1;


public class publishserver1 {

	
	public static void main(String args[]) 
	{
		listernerforserver1 tl1=new listernerforserver1(4099);
  		Thread t1=new Thread(tl1);
  		t1.start();
		Endpoint endpoint=Endpoint.publish("http://localhost:8088/cal", new server1impl());
		System.out.println(endpoint.isPublished());
	/*	Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run()
			  {
				  if(server1impl.a.isEmpty())
				  {
					  
				  }
				  else
				  {
					  
					Set<String> setd= server1impl.a.keySet();
					Iterator itd=setd.iterator();
					while(itd.hasNext())
					{
						String s1=(String)itd.next();
							Set<String> setr =	server1impl.a.get(s1).keySet();
							Iterator itr =setr.iterator();
							while(itr.hasNext())
							{
								String s2=(String)itr.next();
									Set<String> sett =	server1impl.a.get(s1).get(s2).keySet();
									Iterator itt =sett.iterator();
									while(itt.hasNext())
									{
										String s3=(String)itt.next();
										server1impl.a.get(s1).get(s2).put(s3,"Available");
									}
							}
					}
					Set<String> seta = server1impl.d.keySet();
					Iterator ita=seta.iterator();
					while(ita.hasNext())
					{
						String ss1=(String)ita.next();
						server1impl.d.remove(ss1);
					}
					System.out.println("Database reset");
				  }
			  }
			}, 2*60*1000, 2*60*1000);*/
	}
}

