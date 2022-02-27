package TradingGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PlayerReceivable extends Thread {
	Socket sockek;

	public PlayerReceivable(Socket sockek) {
		this.sockek = sockek;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(sockek.getInputStream()));
			
			while(true) {
				
				String tmp = in.readLine();
					if(tmp.equals("exit")) {
						in.close();
						break;
					}
				System.out.println(tmp);
				
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
}
