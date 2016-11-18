package org.teniere;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import util.Util;

import modele.Countdown;

public class CountDown extends HttpServlet {

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response )
		throws ServletException, IOException {
		System.out.println("Get");
		
		ArrayList<Countdown> alC = new ArrayList<Countdown>();
		
		
		if(Util.getCookieValue(request, "countdownList")==null){
			JSONObject jsonCL = new JSONObject();
			Cookie cdList = new Cookie("countdownList",jsonCL.toString());
			cdList.setPath("/");
			response.addCookie(cdList);	
		}else{
			JSONObject jsonCL;
			try {
				jsonCL = new JSONObject(Util.getCookieValue(request, "countdownList"));
				//On parcours le JSON on ajoute les compteurs à une ArrayList
				for(int i=0;i<jsonCL.length();i++){
					if(!jsonCL.has(Integer.toString(i))){
						jsonCL.put(Integer.toString(i),jsonCL.getJSONObject(Integer.toString(i+1)));
						jsonCL.remove(Integer.toString(i+1));
					}
				}
				
				Iterator<?> keys = jsonCL.keys();
				while( keys.hasNext() ) {
				    String key = (String)keys.next();
		
				    int id = Integer.parseInt(key);
				    
				    JSONObject obj = jsonCL.getJSONObject(key);
				    String title = obj.getString("title");
				    String date = obj.getString("date");
		
				    Countdown c = new Countdown(id,title,date);
				    alC.add(c);
				
				} 
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute( "countdowns", alC );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
	}
	
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response )
		throws ServletException, IOException {
		System.out.println("Post");
		//Array List qui sera retourné avec les compteurs (ps: plus tards avec web socket on retourne pas la date mais on met à jour le temps)
		ArrayList<Countdown> alC = new ArrayList<Countdown>();
		try {
			//Récupération du JSON dans les cookies (si pas de cookie tout plante)
			JSONObject jsonCL = new JSONObject(Util.getCookieValue(request, "countdownList"));
			String title;
			//On test quel action on fait
			if(request.getServletPath() != null){
				switch(request.getServletPath()){
					case "/countdownDelete":
						System.out.println("DELETE");
	
						jsonCL = new JSONObject(Util.getCookieValue(request, "countdownList"));
						jsonCL.remove(request.getParameter("delete"));
						
						break;
					case "/countdownUpdate":
						System.out.println("UPDATE");
						title = request.getParameter("title");
						title = title.replaceAll("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("'","").replace("\"","");
						
						jsonCL.getJSONObject(request.getParameter("update")).put("title", title);
						jsonCL.getJSONObject(request.getParameter("update")).put("date", request.getParameter("date"));
						break;
					case "/countdownAdd":
						System.out.println("ADD");

						//On récupère la liste des compteurs en JSON
						jsonCL = new JSONObject(Util.getCookieValue(request, "countdownList"));
						
						//On récupère le nouveau compteur, on le met dans le JSON
						title = request.getParameter("title");
						title = title.replaceAll("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("'","").replace("\"","");

						Countdown c1 = new Countdown(title,request.getParameter("date"));
						try {
							String key = Integer.toString(jsonCL.length());
							
							JSONObject titleDate = new JSONObject();
							titleDate.put("title", c1.getTitle());
							titleDate.put("date", c1.getDate());
							jsonCL.put(key, titleDate);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
						break;
				}
			}
				
			//On parcours le JSON on ajoute les compteurs à une ArrayList
			for(int i=0;i<jsonCL.length();i++){
				if(!jsonCL.has(Integer.toString(i))){
					jsonCL.put(Integer.toString(i),jsonCL.getJSONObject(Integer.toString(i+1)));
					jsonCL.remove(Integer.toString(i+1));
				}
			}
			
			Iterator<?> keys = jsonCL.keys();
			while( keys.hasNext() ) {
			    String key = (String)keys.next();
	
			    int id = Integer.parseInt(key);
			    
			    JSONObject obj = jsonCL.getJSONObject(key);
			    String titleJ = obj.getString("title");
			    String dateJ = obj.getString("date");
	
			    Countdown c = new Countdown(id,titleJ,dateJ);
			    alC.add(c);
			}
			
			//On met le JSON dans les cookies
			Util.setCookieValue(request, "countdownList", jsonCL.toString());
			response.addCookie(new Cookie("countdownList",jsonCL.toString()));	
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Retourne l'aL
		request.setAttribute( "countdowns", alC );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
	}
}

