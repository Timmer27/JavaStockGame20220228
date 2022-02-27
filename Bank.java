package TradingGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.text.DecimalFormat;

public class Bank implements Serializable{
	private int balance;
	private int StockN;
	PriceGenerator2 ge;
	PrintWriter out; 
	Socket socket;
	DecimalFormat df;
	DecimalFormat df2;
	File file = new File("C:\\Users\\gram\\Desktop\\Stock.user");
	

	public Bank(int balance, int StockN) {
		this.balance = balance;
		this.StockN = StockN;
	}
	
	
	public Bank(PriceGenerator2 ge, Socket socket) {
		this.balance = 100000;
		StockN = 0;
		this.ge = ge;
		try {
			this.out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
		}
		this.socket = socket;
		df = new DecimalFormat("#,### ₩");
		df2 = new DecimalFormat("#,### 개");
	}

	public synchronized void buy(int n) {
		if(balance > (n*ge.getCurrentPrice())) {
			balance -= ge.getCurrentPrice() * n;
			StockN += n;
			
			out.println(df.format(ge.getCurrentPrice() * n) + " 매수 완료");
			out.flush();
			
		}
		else {
			out.println("현재 잔액 부족");
			out.flush();
		}
	}
	
	public synchronized void sell(int n) {
		if(StockN>=n) {
			balance += (ge.getCurrentPrice() * n);
			StockN -= n;
			
			out.println(df.format(ge.getCurrentPrice() * n) + " 매도 완료");
			out.flush();
		}
		else {
			out.println("잔고가 없습니다.");
			out.flush();
		}
		
	}
	
	public void showCurrentBalance() {
		System.out.println("현재 잔액 : " + getBalance());
		System.out.println("현재 보유 개수 : " + getStockN());
	}
	
	public synchronized int getBalance() {
		return balance;
	}

	public  void setBalance(int balance) {
		this.balance = balance;
	}

	public synchronized int getStockN() {
		return StockN;
	}

	public void setStockN(int stockN) {
		StockN = stockN;
	}
	
	public void ReadPrevious() { //파일을 저장할 때 쓰는 메서드 (저장 불러오기)
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\gram\\Desktop\\Stock.ser"));
			Bank b = (Bank)in.readObject();
			balance = b.getBalance();
			StockN = b.StockN;
			
			in.close();
			
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("저장된 파일이 없습니다.");
		}
		
	}

}
