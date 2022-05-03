package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
public class MessageController {

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    // setApplicationDestinationPrefixes() 설정에 따라
    // 실질적인 경로는 "/app/chat/message" 가 된다.
    // 해당 경로로 보내는 메시지 처리
    @MessageMapping("/chat/message")
    public void enter(ChatMessageDTO message
            , @Payload String payload
            , Message m
            , MessageHeaders messageHeaders
            , MessageHeaderAccessor messageHeaderAccessor
            //, @Header String header
            , @Headers Map<String, String> headerMap
            //, @DestinationVariable String destination
            , Principal principal) {
        // 메시지 타입이 ENTER 인 경우 환영 메시지 출력
        if (ChatMessageDTO.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하였습니다.");
        }
        if (ChatMessageDTO.MessageType.EXIT.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 퇴장하셨습니다.");
        }
        // "/topic/chat/room/{roomId}" 를 구독하고 있는 클라이언트에게 메시지 전달
        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
        log.info("구독 클라이언트에게 메시지 전달");

        // 파라미터 학습용
//        log.info("Message: {}", m);
//        log.info("MessageHeaders: {}", messageHeaders);
//        log.info("MessageHeadersAccessor: {}", messageHeaderAccessor);
//        //log.info("Header: {}", header);
//        log.info("HeaderMap: {}", headerMap);
//        log.info("페이로드: {}", payload);
//        //log.info("Destination: {}", destination);
//        log.info("Principal: {}", principal);
    }


}
