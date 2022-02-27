package TradingGame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerRequest extends Thread{ //priceprovider로 보내는 입장
	String name;
	Socket socket;
	Scanner scanner;

	
	public PlayerRequest(String name, Socket socket) {
		this.name = name;
		this.socket = socket;
		scanner = new Scanner(System.in);
	}

	@Override
	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(name +"님이 입장하셨습니다.");
			out.flush();
//			출력문을 받는대로 flush로 바로 내보내줘야 실시간으로 받아진다.
			
			Loop: while(true) {
				String tmp = scanner.nextLine(); //매수 매도 확인용
				
				if(tmp!=null) {
					out.println(tmp);
					out.flush();
//			출력문을 받는대로 flush로 바로 내보내줘야 실시간으로 받아진다.
					
					if(tmp.equals("exit")) {
						System.out.println("종료됩니다. 자동 저장됩니다.");
						break Loop;
					}
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
