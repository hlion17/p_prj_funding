package me.project.funding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter @Setter @ToString
public class ChatRoomDTO {

    private String roomId;
    private String roomName;

    public static ChatRoomDTO create(String name) {
        ChatRoomDTO room = new ChatRoomDTO();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;
        return room;
    }
}
