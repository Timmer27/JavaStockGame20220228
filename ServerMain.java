package TradingGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(9010);
			System.out.println("접속자 대기 중...");
			
			PriceGenerator2 ge = new PriceGenerator2();
			Thread generator2 = new Thread(ge);
			generator2.start(); //자동으로 가격 생성 중
			
			Socket socket = null;
			
			while(true) {
				socket = server.accept();
				Bank b = new Bank(ge, socket);
				b.ReadPrevious(); // 사용자가 접속할 때 저장했던 객체 정보를 불러올 수 있게 해주는 메서드
				
				System.out.println("접속: " + socket.getInetAddress()); //접속자의 IP주소 받아옴
				
				Thread provider = new PriceProvider(socket, ge, b); // 사용자 매수&매도신호 받는 중
				provider.start();
			}
		} catch (IOException e) {
		}
	}

}
