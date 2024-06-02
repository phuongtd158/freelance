package com.example.santhuongmai.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAboutRequest {
	
	private String title;
	private String noidung;
	 @NotNull(message="Ảnh đang rỗng")
	    @NotEmpty(message="Ảnh đang rỗng")
	    private Long imageId;
}
