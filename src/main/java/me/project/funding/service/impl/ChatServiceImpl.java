package me.project.funding.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.ChatRoomDTO;
import me.project.funding.service.face.ChatService;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    // 임시 DB로 메모리 사용
    // 동시성 문제 있어서 이렇게 사용하면 안됨
    private Map<String, ChatRoomDTO> chatRooms = new LinkedHashMap<>();

    // 채팅방 불러오기
    @Override
    public List<ChatRoomDTO> findAllRoom() {
        List<ChatRoomDTO> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);
        log.info("채팅방 목록: {}", result);
        return result;
    }

    // 채팅방 생성
    @Override
    public ChatRoomDTO createRoom(String name) {
        ChatRoomDTO chatRoom = ChatRoomDTO.create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        log.info("생성된 채팅방: {}", chatRoom);
        return chatRoom;
    }

    // 채팅방 하나 불러오기
    @Override
    public ChatRoomDTO findById(String roomId) {
        log.info("조회된 채팅방: {}", chatRooms.get(roomId));
        return chatRooms.get(roomId);
    }
}
