package com.example.santhuongmai.model.request;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePolicyRequest {
	
	@NotNull(message="Ảnh đang rỗng")
    @NotEmpty(message="Ảnh đang rỗng")
    private Long imageId;
	private String title;
	private String content;
	private int kieu;
}
