package me.project.funding.service.face;

import me.project.funding.dto.ChatRoomDTO;

import java.util.List;

public interface ChatService {
    /**
     * 모든 채팅방 리스트 반환
     * @return
     */
    List<ChatRoomDTO> findAllRoom();

    /**
     * 채팅방 생성
     * @param name 생성할 채팅방 이름
     * @return
     */
    ChatRoomDTO createRoom(String name);

    /**
     * 아이디로 채팅방 조회
     * @param roomId 조회할 채팅방 아이디
     * @return
     */
    ChatRoomDTO findById(String roomId);
}
