package com.studentsupport.service;

import com.studentsupport.dto.message.CreateMessageDTO;
import com.studentsupport.entity.*;
import com.studentsupport.exception.*;
import com.studentsupport.repository.*;
import com.studentsupport.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messages;
    private final SupportRequestRepository requests;
    private final UserRepository users;

    public MessageService(MessageRepository messages, SupportRequestRepository requests, UserRepository users) {
        this.messages = messages;
        this.requests = requests;
        this.users = users;
    }

    private User me() {
        return users.findByEmail(SecurityUtil.currentEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private SupportRequest req(Long id) {
        return requests.findById(id).orElseThrow(() -> new NotFoundException("Request not found"));
    }

    private void ensureMember(SupportRequest r, User u) {
        boolean isOwner = r.getRequester().getId().equals(u.getId());
        boolean isHelper = r.getHelper() != null && r.getHelper().getId().equals(u.getId());
        if (!isOwner && !isHelper) throw new ForbiddenException("Not allowed");
    }

    public List<Message> list(Long requestId) {
        User u = me();
        SupportRequest r = req(requestId);
        ensureMember(r, u);
        return messages.findByRequestOrderByCreatedAtAsc(r);
    }

    public Message send(Long requestId, CreateMessageDTO dto) {
        User u = me();
        SupportRequest r = req(requestId);
        ensureMember(r, u);

        Message m = Message.builder()
                .request(r)
                .sender(u)
                .text(dto.getText())
                .createdAt(Instant.now())
                .build();
        return messages.save(m);
    }
}
