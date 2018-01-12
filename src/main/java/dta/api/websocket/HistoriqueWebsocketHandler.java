package dta.api.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import dta.api.services.HistoriqueWebsocketService;

@Component
public class HistoriqueWebsocketHandler extends TextWebSocketHandler {
	@Autowired HistoriqueWebsocketService historiqueWebSocketSvc;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		historiqueWebSocketSvc.addSession(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		historiqueWebSocketSvc.removeSession(session);
	}
}
