package com.ra.model.dto.response;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponse {
    private final String type = "Bearer";
    private String accessToken ;
    private String fullName;
    private String email;
    private Collection<? extends GrantedAuthority> roleSet;
    private boolean status;
}

