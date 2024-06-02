package com.example.santhuongmai.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSettingRequest {
	private String id;
	private String gmail;
	private String phone;
	private String logo;
	private String facebook;
	private String youtube;
	private String telegram;
	private String instagram;
	private String diachi;
}
