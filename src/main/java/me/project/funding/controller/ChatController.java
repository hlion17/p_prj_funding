package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.ChatRoomDTO;
import me.project.funding.service.face.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms() {
        log.info("[/chat/room][GET]");
        return "chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoomDTO> room() {
        log.info("[/chat/rooms][GET]");
        return chatService.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoomDTO createRoom(@RequestParam String name) {
        log.info("[/chat/room][POST]");
        log.info("요청파라미터: {}", name);
        return chatService.createRoom(name);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        log.info("[/chat/room/enter/{}][GET]", roomId);
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomDTO roomInfo(@PathVariable String roomId) {
        log.info("[/chat/room/{}][GET]", roomId);
        return chatService.findById(roomId);
    }
}
