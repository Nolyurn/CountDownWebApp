package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class Util {
	public static String getCookieValue( HttpServletRequest request, String nom ) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( javax.servlet.http.Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
	
	public static void setCookieValue( HttpServletRequest request, String nom, String value) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( javax.servlet.http.Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    cookie.setValue(value);
                }
            }
        }
    }
	
	@SuppressWarnings("deprecation")
	public static String diff(String date) throws ParseException{
		String pattern = "MM/dd/yyyy HH:mm a";
		DateFormat d = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, new Locale("EN","en"));
		Date d1 = new Date();
		
		long diff = d.parse(date).getTime() - d1.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		String res = "";
		if(diffDays>0){
			res += diffDays+" jour(s) ";
		}
		if(diffHours>0){
			res += diffHours+" heure(s) ";
		}
		if(diffMinutes>0){
			res += diffMinutes+" minute(s) ";
		}
		if(diffSeconds>0){
			res += diffSeconds+" seconde(s) ";
		}

		return (res.equals(""))?"Finis : "+date:res;

	}
}