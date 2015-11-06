package wifi;

import java.io.*;
import java.net.*;
/*
 * @author: lingyun
 * @date:23/9/2015
 */
public class WifiClient {//client

	private int port;      
	private Socket socket; 
	private InputStreamReader inputStream;
	private BufferedReader messagFromRpi;
	private PrintStream messageToRpi;
	
	public WifiClient(int port) throws UnknownHostException, IOException{
		this.port=port;
		//this.socket=new Socket("192.168.19.19",port);
		this.socket=new Socket("localhost",port);
		this.inputStream=new InputStreamReader(socket.getInputStream());
		this.messagFromRpi=new BufferedReader(inputStream);
		this.messageToRpi=new PrintStream (socket.getOutputStream());
	}

	public void sendMessageToRpi(String message){
		System.out.println("sending message:     "+message);
		messageToRpi.println(message);
	}
	
	public String getMessageFromRpi(){
		try {			
			String message=messagFromRpi.readLine();
			if (message!=null)
				System.out.println("received message:    "+message);
			return message;
		} catch (IOException e) {
			System.out.println("IOException");
			return null;
		}
	}
	
	public void closeConnection() throws IOException{
		socket.close();
	}
	
}
