package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    // setApplicationDestinationPrefixes() 설정에 따라
    // 실질적인 경로는 "/app/chat/message" 가 된다.
    // 해당 경로로 보내는 메시지 처리
    @MessageMapping("/chat/message")
    public void enter(ChatMessageDTO message) {
        // 메시지 타입이 ENTER 인 경우 환영 메시지 출력
        if (ChatMessageDTO.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 임장하였습니다.");
            log.info("메시지 타입이 ENTER");
        }
        // "/topic/chat/room/{roomId}" 를 구독하고 있는 클라이언트에게 메시지 전달
        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
        log.info("구독 클라이언트에게 메시지 전달");
    }


}
