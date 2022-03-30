package edu.wfit.liveteaching.controller;

import edu.wfit.liveteaching.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/**
 * 聊天控制类
 */
@Controller
public class ChatController {

    @GetMapping("/chat/{topicName}")
    public String toChatPage(
            @PathVariable String topicName,
            HttpSession session,
            Model model
    ) {
        model.addAttribute("topicName", topicName);
        String loginUserNickname = session.getAttribute("loginUserNickname").toString();
        model.addAttribute("username", loginUserNickname);
        return "chat";
    }

    /**
     * 发送信息
     * @param chatMessage
     * @return
     */
    @MessageMapping("/chat.sendMessage/{topicName}")
    @SendTo("/topic/public/{topicName}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{topicName}")
    @SendTo("/topic/public/{topicName}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
