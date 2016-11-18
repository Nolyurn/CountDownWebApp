package CountdownTest;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import modele.Countdown;
import util.Util;

public class CountDownAppTest {
	@Test
	 public void countdownGetTitle() {
		String title = "coucou";
		String date = "";
	 	Countdown c= new Countdown(1,title,date);
	 	assertEquals("coucou",c.getTitle());
	 }
	
	@Test
	public void countdownGetDate(){
		String title = "";
		String date = "02/11/2016 17:30:00";
	 	Countdown c= new Countdown(1,title,date);
	 	assertEquals("02/11/2016 17:30:00",c.getDate());
	}
	
	@Test
	 public void countdownSetTitle() {
		String title = "";
		String date = "";
	 	Countdown c= new Countdown(1,title,date);
	 	
	 	String newTitle = "coucou";
	 	c.setTitle(newTitle);
	 	assertEquals(newTitle,c.getTitle());
	 }
	
	@Test
	 public void countdownSetDate() {
		String title = "";
		String date = "";
	 	Countdown c= new Countdown(1,title,date);
	 	
	 	String newDate = "02/11/2016 17:30:00";
	 	c.setDate(newDate);
	 	assertEquals(newDate,c.getDate());
	 }
}
