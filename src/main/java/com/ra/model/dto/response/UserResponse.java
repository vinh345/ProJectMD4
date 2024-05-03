package com.ra.model.dto.response;

import com.ra.model.entity.Address;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponse {
    private String username;
    private String fullName;
    private String email;
    private String avatar;
    private String phone;
    private Set<Address> addresses;
}
