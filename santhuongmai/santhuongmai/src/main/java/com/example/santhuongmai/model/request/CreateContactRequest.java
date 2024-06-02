package com.example.santhuongmai.model.request;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContactRequest {
	@NotNull(message="Nội dung rỗng")
    @NotEmpty(message="Nội dung rỗng")
    @Size(min=5,message="Độ dài nội dung tối thiểu từ 5 ký tự")
    private String content;
	private String name;
	private String gmail;
}
