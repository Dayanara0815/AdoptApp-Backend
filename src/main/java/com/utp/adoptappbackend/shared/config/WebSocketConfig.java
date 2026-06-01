package com.utp.adoptappbackend.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.uri-front}")
    private String uriFront;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint para WebSocket nativo
        registry.addEndpoint("/ws")
                .setAllowedOrigins(uriFront);

        // Endpoint con SockJS (fallback)
        registry.addEndpoint("/ws")
                .setAllowedOrigins(uriFront)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}