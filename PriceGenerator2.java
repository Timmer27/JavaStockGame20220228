package TradingGame;

import java.text.DecimalFormat;
import java.util.Random;

public class PriceGenerator2 extends Thread{
	private int price;
	Random random = new Random();;
	private int currentPrice;
	int priceVar;
	
	public PriceGenerator2() {
		this.setPrice(random.nextInt(10000)+1000);
		currentPrice = price;
		priceVar = 0; 
	}

	public void run() {
		DecimalFormat df = new DecimalFormat("#,### ₩");
		System.out.print("처음 가격: ");
		System.out.println(df.format(price));
		
		while(true) {
			priceVar = (int)(currentPrice * 0.1);
			price += (int)((Math.random()*(priceVar*1.7))-priceVar);
			currentPrice = price;
			try {
				Thread.sleep(2000);
				System.out.println(df.format(currentPrice));
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public synchronized int getPrice() {
		return price;
	}

	public synchronized void setPrice(int price) {
		this.price = price;
	}
	public synchronized int getCurrentPrice() {
		return currentPrice;
	}
	
	public synchronized void setCurrentPrice(int price) {
		this.currentPrice = price;
	}
	
	
	
	
	

}
