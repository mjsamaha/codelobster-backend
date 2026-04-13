package com.mjsamaha.codelobster.user.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Size(max = 1000) String bio,
        String country) {
}
