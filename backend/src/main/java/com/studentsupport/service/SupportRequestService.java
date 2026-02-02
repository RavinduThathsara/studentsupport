package com.studentsupport.service;

import com.studentsupport.dto.request.*;
import com.studentsupport.entity.*;
import com.studentsupport.entity.enums.RequestStatus;
import com.studentsupport.exception.*;
import com.studentsupport.repository.*;
import com.studentsupport.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SupportRequestService {

    private final SupportRequestRepository requests;
    private final UserRepository users;

    public SupportRequestService(SupportRequestRepository requests, UserRepository users) {
        this.requests = requests;
        this.users = users;
    }

    private User me() {
        String email = SecurityUtil.currentEmail();
        return users.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private SupportRequest get(Long id) {
        return requests.findById(id).orElseThrow(() -> new NotFoundException("Request not found"));
    }

    private SupportRequestResponseDTO toDTO(SupportRequest r) {
        return SupportRequestResponseDTO.builder()
                .id(r.getId())
                .title(r.getTitle())
                .category(r.getCategory())
                .description(r.getDescription())
                .status(r.getStatus())
                .requesterId(r.getRequester().getId())
                .requesterEmail(r.getRequester().getEmail())
                .helperId(r.getHelper() == null ? null : r.getHelper().getId())
                .helperEmail(r.getHelper() == null ? null : r.getHelper().getEmail())
                .createdAt(r.getCreatedAt())
                .build();
    }

    public SupportRequestResponseDTO create(CreateSupportRequestDTO dto) {
        User requester = me();
        SupportRequest r = SupportRequest.builder()
                .requester(requester)
                .helper(null)
                .title(dto.getTitle())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .status(RequestStatus.OPEN)
                .createdAt(Instant.now())
                .build();
        return toDTO(requests.save(r));
    }

    public List<SupportRequestResponseDTO> listAll() {
        return requests.findAll().stream().map(this::toDTO).toList();
    }

    public SupportRequestResponseDTO getOne(Long id) {
        return toDTO(get(id));
    }

    public SupportRequestResponseDTO update(Long id, UpdateSupportRequestDTO dto) {
        SupportRequest r = get(id);
        User requester = me();

        if (!r.getRequester().getId().equals(requester.getId())) {
            throw new ForbiddenException("Only owner can edit request");
        }
        if (r.getStatus() != RequestStatus.OPEN) {
            throw new BadRequestException("Only OPEN requests can be edited");
        }

        r.setTitle(dto.getTitle());
        r.setCategory(dto.getCategory());
        r.setDescription(dto.getDescription());
        return toDTO(requests.save(r));
    }

    public void delete(Long id) {
        SupportRequest r = get(id);
        User requester = me();
        if (!r.getRequester().getId().equals(requester.getId())) {
            throw new ForbiddenException("Only owner can delete request");
        }
        requests.delete(r);
    }

    public SupportRequestResponseDTO accept(Long id) {
        SupportRequest r = get(id);
        User helper = me();

        if (r.getStatus() != RequestStatus.OPEN) {
            throw new BadRequestException("Request is not OPEN");
        }
        if (r.getRequester().getId().equals(helper.getId())) {
            throw new BadRequestException("You cannot accept your own request");
        }

        r.setHelper(helper);
        r.setStatus(RequestStatus.IN_PROGRESS);
        return toDTO(requests.save(r));
    }

    public SupportRequestResponseDTO changeStatus(Long id, RequestStatus status) {
        SupportRequest r = get(id);
        User user = me();

        boolean isOwner = r.getRequester().getId().equals(user.getId());
        boolean isHelper = r.getHelper() != null && r.getHelper().getId().equals(user.getId());

        if (!isOwner && !isHelper) {
            throw new ForbiddenException("Only requester/helper can change status");
        }

        r.setStatus(status);
        return toDTO(requests.save(r));
    }
}
