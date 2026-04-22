package org.example.car.dto.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageRequest {

    @NotNull
    private Long receiverId;

    @NotNull
    private Long carId;

    @NotBlank
    private String content;
}

