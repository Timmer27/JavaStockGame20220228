package TradingGame;

import java.io.IOException;
import java.net.Socket;

public class Player {
	public static void main(String[] args) {
		String name = "이종호";
		try {
			Socket socket = new Socket("localhost", 9010);
			
			Thread playThread = new PlayerRequest(name, socket);
			Thread receiveThread = new PlayerReceivable(socket);
			
			receiveThread.start();
			playThread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
