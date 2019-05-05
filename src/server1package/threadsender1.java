package server1package;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

import server2package.server2impl;

public class threadsender1 extends Thread
{
    public  String c ;
    public int count,d;
    public boolean ready = false;
    public String dstring=new String("");
    
    public threadsender1(String c, int d)
    {
    	this.c=c;
    	this.d=d;
    }
   
	public void run()
	{
		
		System.out.println(" Inside the sender of server 1");
		DatagramSocket aSocket = null;
		try 
		{
			aSocket = new DatagramSocket();
			byte [] m1 = c.getBytes();
			InetAddress aHost = InetAddress.getByName("localhost");
			int serverPort = d;
			DatagramPacket request =new DatagramPacket(m1, c.length(), aHost, serverPort);
			aSocket.send(request);
			byte[] buffer1 = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer1, buffer1.length);
			aSocket.receive(reply);
			String d1=new String(reply.getData());
			
			System.out.println("MESSAGE RECIEVED IN THREADSENDER1  "+d1);
			System.out.println("LENGTH OF THE MESSAGE RECIEVED IN THREADSENDER1  "+d1.length());
			synchronized(this)
			{
				dstring=d1;
				//server1impl.fcount=d1;
			}
			ready=true;
		}
		catch (SocketException e)
		{	System.out.println("Socket: " + e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("IO: " + e.getMessage());
		}
		finally 
		{
				if(aSocket != null)
				aSocket.close();
		}
	}
}
