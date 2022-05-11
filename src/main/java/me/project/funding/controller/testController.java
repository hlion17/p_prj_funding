package me.project.funding.controller;

import lombok.extern.slf4j.Slf4j;
import me.project.funding.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class testController {

    @Autowired
    SimpMessageSendingOperations template;

    @GetMapping("/test")
    public void test(HttpSession session) {
        log.info("테스트 컨트롤러");
        ChatMessageDTO message = new ChatMessageDTO();
        message.setMessage("테스트 메시지");
        message.setTargetUser("test2");
        template.convertAndSend("/queue/user-" + message.getTargetUser(), message);
    }

    @GetMapping("/editor/test")
    public ModelAndView test1() {
        ModelAndView mav = new ModelAndView("project/editor/basic");
        return mav;
    }
}
