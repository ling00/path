package wifi;

import java.io.IOException;
import java.net.UnknownHostException;
/*
 * @author: lingyun
 * @date:23/9/2015
 */
public class runServer {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

		MyServer server=new MyServer(8000);
		
		
		while (true){
		
			server.sendMessageToRpi("Hi PC! I am Rpi!  ");
			String pcResponse=server.getMessageFromRpi();
			if(pcResponse!=null){
				System.out.println("PC:");
				System.out.println(pcResponse);
			}
			
		}
		
		
	}

}
