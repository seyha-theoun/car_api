package org.example.car.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.car.dto.message.MessageRequest;
import org.example.car.dto.message.MessageResponse;
import org.example.car.entity.Car;
import org.example.car.entity.Message;
import org.example.car.entity.User;
import org.example.car.exception.ResourceNotFoundException;
import org.example.car.repository.CarRepository;
import org.example.car.repository.MessageRepository;
import org.example.car.repository.UserRepository;
import org.example.car.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    @Transactional
    public MessageResponse sendMessage(MessageRequest request, String currentUserEmail) {
        User sender = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));
        Car car = carRepository.findByIdAndDeletedFalse(request.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .car(car)
                .content(request.getContent())
                .build();

        return toResponse(messageRepository.save(message));
    }

    @Override
    public List<MessageResponse> getConversation(Long otherUserId, String currentUserEmail) {
        User me = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.findById(otherUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Other user not found"));

        return messageRepository.findConversation(me.getId(), otherUserId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private MessageResponse toResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getName())
                .receiverId(message.getReceiver().getId())
                .receiverName(message.getReceiver().getName())
                .carId(message.getCar().getId())
                .content(message.getContent())
                .createdAt(message.getCreatedAt())
                .build();
    }
}

