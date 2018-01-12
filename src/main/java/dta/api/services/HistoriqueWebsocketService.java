package dta.api.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class HistoriqueWebsocketService {
	private List<WebSocketSession> sessions = new ArrayList<>();

	public void addSession(WebSocketSession session) {
		sessions.add(session);
	}

	public void sendMessage(TextMessage msg) {
		sessions.forEach(session -> {
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
	}
}
