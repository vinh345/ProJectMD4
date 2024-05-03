package com.ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormUserPasswordChange {
	private String oldPass;
	private String newPass;
	private String confirmNewPass;
}
