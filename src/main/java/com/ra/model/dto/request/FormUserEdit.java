package com.ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormUserEdit {
    private String fullName;
    private String email;
    private String avatar;
    private String phone;
}
