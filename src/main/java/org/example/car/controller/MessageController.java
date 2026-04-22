package org.example.car.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.car.dto.message.MessageRequest;
import org.example.car.dto.message.MessageResponse;
import org.example.car.service.MessageService;
import org.example.car.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.sendMessage(request, SecurityUtils.currentUserEmail()));
    }

    @GetMapping("/conversation/{userId}")
    public ResponseEntity<List<MessageResponse>> getConversation(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getConversation(userId, SecurityUtils.currentUserEmail()));
    }
}

