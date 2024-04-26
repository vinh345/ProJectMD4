package com.ra.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FormRegister {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
    @Size(max = 255)
    private String fullName;
    @Email
    @Size(max = 255)
    private String email;
    @NotBlank
    @Size(min = 5, max = 20)
    private String password;
    @Pattern(regexp = "^[0-9]*$")
    private String phone;
    private String Address;
}
