package com.studentsupport.controller;

import com.studentsupport.dto.message.CreateMessageDTO;
import com.studentsupport.entity.Message;
import com.studentsupport.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests/{requestId}/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Message>> list(@PathVariable Long requestId) {
        return ResponseEntity.ok(service.list(requestId));
    }

    @PostMapping
    public ResponseEntity<Message> send(@PathVariable Long requestId, @Valid @RequestBody CreateMessageDTO dto) {
        return ResponseEntity.ok(service.send(requestId, dto));
    }
}
