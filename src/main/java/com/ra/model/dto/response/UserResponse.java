package com.ra.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Object data;
    private String username;
    private String fullName;
    private String email;
    private boolean status;
}
