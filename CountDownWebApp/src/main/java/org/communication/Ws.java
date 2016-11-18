/**
 * ChatServerEndPoint.java
 * http://programmingforliving.com
 */
package org.communication;
 
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;
import util.Util;
 

@ServerEndpoint(value="/ws")
public class Ws {
    
    private Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());
 
    @OnOpen
    public void onOpen(Session userSession) {
        userSessions.add(userSession);
    }
     
    @OnClose
    public void onClose(Session userSession) {
        userSessions.remove(userSession);
    }

    @OnMessage
    public void onMessage(String message, Session userSession) throws JSONException, ParseException, InterruptedException {
    	//Message devrait contenir le cookie countdownList
    	JSONObject countdownList = new JSONObject(message);
    	//jsCountdowns contiendra le temps mis à jour
    	JSONObject jsCountdowns = new JSONObject();
    	
    	//Envoie de message toutes les secondes avec la liste de compte à rebours mis à jour
    	while(true){
    		Thread.sleep(1000);
	    	Iterator<?> keys = countdownList.keys();
			while( keys.hasNext() ) {
			    String key = (String)keys.next();
			    JSONObject obj = countdownList.getJSONObject(key);
	
			    String date = obj.getString("date");
			    jsCountdowns.put(key, Util.diff(date));
			}
	    	userSession.getAsyncRemote().sendText(jsCountdowns.toString());
    	}
    }
}