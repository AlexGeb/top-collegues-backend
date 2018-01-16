package dta.api.services;

import static dta.api.security.SecurityConstants.SECRET;
import static dta.api.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.jsonwebtoken.Jwts;

@Component
public class HistoriqueWebsocketService {
	private List<WebSocketSession> sessions = new ArrayList<>();
	private List<WebSocketSession> authorizedSessions = new ArrayList<>();
	public void addSession(WebSocketSession session) {
		sessions.add(session);
	}

	public void sendMessage(TextMessage msg) {
		authorizedSessions.forEach(session -> {
			try {
				session.sendMessage(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void removeSession(WebSocketSession session) {
		sessions.remove(session);
		authorizedSessions.remove(session);
	}
	
	public boolean authorizeSession(WebSocketSession session,String access_token) {
		String user = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(access_token.replace(TOKEN_PREFIX, ""))
				.getBody().getSubject();
		if(user!=null) {
			authorizedSessions.add(session);
			return true;
		}
		return false;
	}
}
