package bro.chat.controller;

import bro.chat.repositroy.ChatRoom;
import bro.chat.repositroy.ChatRoomForm;
import bro.chat.repositroy.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor

public class ChatController {
    private final ChatRoomRepository chatRoomRepository;


    @GetMapping("/startchat")
    public String rooms(Model model){
        model.addAttribute("rooms",chatRoomRepository.findAllRoom());
        return "rooms";
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable String id, Model model){
        ChatRoom room = chatRoomRepository.findRoomById(id);
        model.addAttribute("room",room);
        return "room";
    }

    @GetMapping("/new")
    public String make(Model model){
        ChatRoomForm form = new ChatRoomForm();
        model.addAttribute("form",form);
        return "newRoom";
    }

    @PostMapping("/room/new")
    public String makeRoom(ChatRoomForm form){
        chatRoomRepository.createChatRoom(form.getRoomTitle(), form.getUserName(), form.getRoomPassword());
        return "redirect:/startchat";
    }

}