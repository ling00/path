package wifi;

import java.io.IOException;
import java.net.UnknownHostException;
/*
 * @author: lingyun
 * @date:23/9/2015
 */
public class runClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub

		WifiClient pc=new WifiClient(1234);
		String close="end";
		pc.sendMessageToRpi("Hi Rpi!");
		while (true){
			String rpiResponse=pc.getMessageFromRpi();
			if(rpiResponse==null)continue;
			if(rpiResponse.equals(close)){		
				//System.out.println("no message received");
				System.out.println("terminate");
				break;		
			}
			else {
				pc.sendMessageToRpi("Hi Rpi! I received ur msg!  ");
				}
		}
		
		
	}
}
