package com.studentsupport.service;

import com.studentsupport.dto.rating.CreateRatingDTO;
import com.studentsupport.entity.*;
import com.studentsupport.entity.enums.RequestStatus;
import com.studentsupport.exception.*;
import com.studentsupport.repository.*;
import com.studentsupport.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RatingService {

    private final RatingRepository ratings;
    private final SupportRequestRepository requests;
    private final UserRepository users;

    public RatingService(RatingRepository ratings, SupportRequestRepository requests, UserRepository users) {
        this.ratings = ratings;
        this.requests = requests;
        this.users = users;
    }

    private User me() {
        return users.findByEmail(SecurityUtil.currentEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Rating rate(Long requestId, CreateRatingDTO dto) {
        User rater = me();
        SupportRequest r = requests.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request not found"));

        if (r.getStatus() != RequestStatus.COMPLETED) {
            throw new BadRequestException("You can rate only after COMPLETED");
        }
        if (!r.getRequester().getId().equals(rater.getId())) {
            throw new ForbiddenException("Only requester can rate");
        }
        if (r.getHelper() == null) {
            throw new BadRequestException("No helper to rate");
        }
        if (ratings.findByRequest(r).isPresent()) {
            throw new BadRequestException("Already rated");
        }

        Rating rating = Rating.builder()
                .request(r)
                .rater(rater)
                .ratee(r.getHelper())
                .stars(dto.getStars())
                .comment(dto.getComment())
                .createdAt(Instant.now())
                .build();

        return ratings.save(rating);
    }
}
