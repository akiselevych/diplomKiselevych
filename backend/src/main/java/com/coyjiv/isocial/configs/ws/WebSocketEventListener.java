package com.coyjiv.isocial.configs.ws;

import com.coyjiv.isocial.auth.EmailPasswordAuthProvider;
import com.coyjiv.isocial.cache.OnlineUsersCache;
import com.coyjiv.isocial.service.user.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

  private final EmailPasswordAuthProvider authProvider;
  private final IUserService userService;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectEvent event) throws IllegalAccessException {
    StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
    String token = headers.getFirstNativeHeader("Authorization");

    if (token != null && !Objects.equals(token, "Bearer null")) {
      userService.handleConnect(token.substring(7));
      OnlineUsersCache.putUserId(headers.getSessionId(),authProvider.getAuthenticationPrincipal());
    }
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
    Long userId = OnlineUsersCache.getUserId(headers.getSessionId());
    userService.handleDisconnect(userId);
  }
}
