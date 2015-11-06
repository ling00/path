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
		int count =0;
		String close="end";
		server.sendMessageToRpi("start");	
		server.sendMessageToRpi(close);
		
	}

}
