package TradingGame;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Scanner;

public class PriceProvider extends Thread{ // request에서 받아서 출력해줌
	Socket socket;
	PrintWriter out;
	PriceGenerator2 ge;
	Bank b;
	Scanner scanner;
	ObjectOutputStream Oout;
	
	public PriceProvider(Socket socket, PriceGenerator2 ge, Bank b) {
		this.socket = socket;
		this.ge = ge;
		this.b = b;
		scanner = new Scanner(System.in);
		try {
			out = new PrintWriter(socket.getOutputStream());
			Oout = new ObjectOutputStream(new FileOutputStream("C:\\Users\\gram\\Desktop\\Stock.ser"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			DecimalFormat df = new DecimalFormat("#,### ₩");
			DecimalFormat df2 = new DecimalFormat("#,### 개");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				out.println("매수 1번");
				out.println("매도 2번");
				out.flush();
				String tmp = in.readLine();
				
				if(tmp.equals("exit")) {
					Bank bsave = new Bank(b.getBalance(), b.getStockN());
//					Oout = new ObjectOutputStream(new FileOutputStream("C:\\Users\\gram\\Desktop\\Stock.ser"));
					Oout.writeObject(bsave);
					Oout.close();
				}
				
				if(tmp != null) {
//					여기서 null을 연산자로 비교해서 String에도 생각없이 그대로 연산자로 적용시켰다. :(
						
					if(tmp.equals("1")) {
						out.println("매수 수량 입력");
						out.flush();
						
						String b2 = in.readLine();
						
						b.buy(Integer.valueOf(b2));
						out.printf("남은 금액: %s, 남은 잔고 %s\n",df.format(b.getBalance()), df2.format(b.getStockN()));
						out.flush();
					}
						
					else if(tmp.equals("2")) {
						out.println("매도 수량 입력");
						out.flush();
						
						String b2 = in.readLine();
						
						b.sell(Integer.valueOf(b2));
						out.printf("남은 금액: %s, 남은 잔고 %s\n",df.format(b.getBalance()), df2.format(b.getStockN()));
						out.flush();
					}
					else {
						out.println(tmp);
						out.flush();
					}
				}
			}
		}
		catch (Exception e) {
//			System.out.println("종료");
			out.println("종료");
			out.close();
		}
	}
}
