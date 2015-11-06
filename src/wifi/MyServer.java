package wifi;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * @author: lingyun
 * @date:23/9/2015
 */

public class MyServer {
	private ServerSocket socketServer;
	private int port;      
	private Socket socket; 
	private InputStreamReader inputStream;
	private BufferedReader messagFromPC;
	private PrintStream messageToPC;
	
	public MyServer(int port) throws UnknownHostException, IOException{
		socketServer = new ServerSocket(port);
		this.port=port;
		socket=socketServer.accept();
		this.inputStream=new InputStreamReader(socket.getInputStream());
		this.messagFromPC=new BufferedReader(inputStream);
		this.messageToPC=new PrintStream(socket.getOutputStream());
		System.out.println("aa"+socket.getLocalAddress());
	
	}

	public void sendMessageToRpi(String message){
		messageToPC.println(message);
		System.out.println("Sending "+message+" ...");
	}
	
	public String getMessageFromRpi(){
		try {
			String message=messagFromPC.readLine();
			System.out.println("Received messsge "+message+" ...");
			return message;
		} catch (IOException e) {

			return "no message received";
		}
	}
	
	public void closeConnection() throws IOException{
		socket.close();
	}
}
