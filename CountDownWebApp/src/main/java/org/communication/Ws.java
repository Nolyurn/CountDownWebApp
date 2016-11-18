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
 
    /**
     * Callback hook for Connection open events. This method will be invoked when a 
     * client requests for a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        userSessions.add(userSession);
    }
     
    /**
     * Callback hook for Connection close events. This method will be invoked when a
     * client closes a WebSocket connection.
     * @param userSession the userSession which is opened.
     */
    @OnClose
    public void onClose(Session userSession) {
        userSessions.remove(userSession);
    }
     
    /**
     * Callback hook for Message Events. This method will be invoked when a client
     * send a message.
     * @param message The text message
     * @param userSession The session of the client
     * @throws JSONException 
     * @throws ParseException 
     * @throws InterruptedException 
     */
    @OnMessage
    public void onMessage(String message, Session userSession) throws JSONException, ParseException, InterruptedException {
    	
    	JSONObject countdownList = new JSONObject(message);
    	JSONObject jsCountdowns = new JSONObject();
    	
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