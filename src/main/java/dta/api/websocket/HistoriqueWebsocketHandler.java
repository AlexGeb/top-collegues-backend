package dta.api.websocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dta.api.services.HistoriqueWebsocketService;

@Component
public class HistoriqueWebsocketHandler extends TextWebSocketHandler {
	@Autowired
	HistoriqueWebsocketService historiqueWebSocketSvc;
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		historiqueWebSocketSvc.addSession(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		historiqueWebSocketSvc.removeSession(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String jsonString = message.getPayload();
		Map<String, String> jsonObj = objectMapper.readValue(jsonString, new TypeReference<Map<String, String>>() {
		});
		if (jsonObj.containsKey("token")) {
			historiqueWebSocketSvc.authorizeSession(session, jsonObj.get("token"));
		}
	}
}
