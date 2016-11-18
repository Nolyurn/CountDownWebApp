package modele;

import java.util.concurrent.atomic.AtomicInteger;

public class Countdown {
	private static final AtomicInteger count = new AtomicInteger(0);
	private final int id;
	private String title;
	private String date;
	
	public Countdown(String title, String date){
		this.id = count.incrementAndGet(); 
		this.title = title;
		this.date = date;
	}
	
	public Countdown(int id,String title, String date){
		this.id = id; 
		this.title = title;
		this.date = date;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setDate(String date){
		this.date = date;
	}
}