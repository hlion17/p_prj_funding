package me.project.funding.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    public enum MessageType {
        ENTER, TALK, EXIT
    }

    private MessageType type;
    // 채팅방 ID
    private String roomId;
    // 보내는 사람
    private String sender;
    // 내용
    private String message;
    // 대상 유저
    private String targetUser;
}
