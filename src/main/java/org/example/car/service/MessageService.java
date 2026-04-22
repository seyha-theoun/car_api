package org.example.car.service;

import org.example.car.dto.message.MessageRequest;
import org.example.car.dto.message.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse sendMessage(MessageRequest request, String currentUserEmail);

    List<MessageResponse> getConversation(Long otherUserId, String currentUserEmail);
}

