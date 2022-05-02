package me.project.funding.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor  // ?
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 소켓 연결 URI
        // 소켓 CORS 설정 **
        // 웹소켓 지원하지 않는 브라우저 SockJS 사용
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
        log.info("웹소켓 엔드포인트 설정");
    }

    // STOMP 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 주고 받을 경로 설정, 스프링 내장 브로커 사용
        // "/queue", "/topic" 이 prefix 로 붙은 경우 메시지 브로커가 해당 경로를 가로챈다.
        // 가로챈 메시지를 채팅방을 구독하고 있는 클라이언트에게 전달
        // "/queue" 는 주로 1:1 메시징, "/topic" 은 주로 1:N 메시징에 사용
        registry.enableSimpleBroker("/queue", "/topic");
        log.info("스프링 내장 브로커 사용");
        // 메시지를 보낼 때 관련 경로를 설정
        // 클라이언트가 메시지를 보낼 떄 "/app"이 붙어 있으면 메시지 브로커로 보내진다.
        registry.setApplicationDestinationPrefixes("/app");
        log.info("/app prefix 적용");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        //registration.interceptors();
    }

}
