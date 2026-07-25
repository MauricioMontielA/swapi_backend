package com.swapi.chat;

import java.net.URI;
import java.util.Map;

import org.hibernate.tool.schema.internal.IndividuallySchemaValidatorImpl;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		URI uri = request.getURI();
		String token =  extractTokenFromUri(uri);

		return false;
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}
	
	private String extractTokenFromUri(URI uri) {
		// TODO Auto-generated method stub
		return "";
	}
}
